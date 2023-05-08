package org.jcryptool.visual.signalencryption.communication;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.jcryptool.visual.signalencryption.algorithm.EncryptionAlgorithm;
import org.jcryptool.visual.signalencryption.algorithm.JCrypToolCapturer;
import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.jcryptool.visual.signalencryption.ui.AlgorithmState;
import org.whispersystems.libsignal.*;
import org.whispersystems.libsignal.protocol.CiphertextMessage;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import org.whispersystems.libsignal.protocol.SignalMessage;

/**
 * Interface between UI "frontend" and algorithm "backend".
 *
 * Handles multiple messages and the keys used to encryt/decrypt them. Provides a list-like interface
 * to advance through the messages (this is important if the user switches forth and back between states).
 */
public class SignalCommunication {

    private List<MessageContext> messageContexts;
    private JCrypToolCapturer preInitializedCapturer;
    private boolean inProgress = false;
    private int i;
    private EncryptionAlgorithm algorithm;

    /**
     * Interface between UI "frontend" and algorithm "backend".
     *
     * Handle multiple messages and the keys used to encryt/decrypt them. Provide as
     * a simple-to-use class with methods similar to an iterator.
     */
    public SignalCommunication(EncryptionAlgorithm algorithm) {
        this.algorithm = algorithm;
        messageContexts = new LinkedList<>();

        messageContexts.add(initialContextWithSender(CommunicationEntity.ALICE));
        i = 0;
        newPreInitializedCapturer();
    }

    /**
     * Retrieve the object the communication is currently pointing at.
     *
     * @return the {@link MessageContext}
     */
    public MessageContext current() {
        return messageContexts.get(i);
    }

    /**
     * Set the pointer to the previous element and return the object there.
     *
     * @return the new {@link MessageContext} after moving the pointer. Return the
     *         same object if already at the beginning.
     * @see #begin()
     * @see #isBeginning()
     */
    public MessageContext prev() {
        if ((i - 1) >= 0) {
            i--;
        }
        return current();
    }

    /**
     * Set the pointer to the next element and return the object there.
     *
     * @return The new {@link MessageContext} after moving the pointer. A new object
     *         is automatically appended at the end if already at the end.
     * @see #end()
     * @see #isEnd()
     */
    public MessageContext next() {
        if ((i + 1) == messageContexts.size()) {
            // If next is not created yet, do so.
            if (current().isAliceSending()) {
                // If in the previous context Alice was sending, let Bob do the next one.
                messageContexts.add(newContextWithSender(CommunicationEntity.BOB, getPreInitializedCapturer()));
            } else {
                // If in the previous context Bob was sending, Alice is next.
                messageContexts.add(newContextWithSender(CommunicationEntity.ALICE, getPreInitializedCapturer()));
            }
        }
        newPreInitializedCapturer();
        i++;
        return current();
    }

    /**
     * Set the pointer to the first element and return the object there.
     *
     * @return The new {@link MessageContext} after moving the pointer.
     */
    public MessageContext begin() {
        i = 0;
        return current();
    }

    /**
     * Set the pointer to the last element and return the object there.
     *
     * @return The new {@link MessageContext} after moving the pointer.
     */
    public MessageContext end() {
        i = messageContexts.size() - 1;
        return current();
    }

    /**
     * Check the state of the pointer.
     *
     * @return True if the pointer is currently at the begin.
     */
    public boolean isBeginning() {
        return i == 0;
    }

    public boolean isBobsFirstMessage() {
        return i == 1;
    }

    /**
     * Check the state of the pointer.
     *
     * @return True if the pointer is currently at the end.
     */
    public boolean isEnd() {
        return i == messageContexts.size() - 1;
    }

    public boolean inProgress() {
        return inProgress;
    }

    public void setInProgress() {
        inProgress = true;
    }

    /**
     * Encrypt the message set in the current {@link MessageContext} and update it.
     *
     * This makes the encrypted and decrypted data available in the context object.
     * <p>
     * THERE IS NO SEPARATE DECRYPT METHOD AS THIS IS DONE DIRECTLY HERE
     *
     * @throws SignalAlgorithmException if something goes wrong on the algorithm
     *                                  side.
     * @throws IllegalStateException    if the current MessageContext is already
     *                                  encrypted.
     */
    public void encrypt() throws SignalAlgorithmException {
        checkInvalidEncryptionState();
        var message = current().getMessage();
        var alice = current().isAliceSending();
        var decrypt = (alice ? algorithm.getBobSessionCipher() : algorithm.getAliceSessionCipher()).orElse(null);

        //
        Consumer<IdentityKey> trustVerifier = (alice ? algorithm::checkIdentityTrustedByBob : algorithm::checkIdentityTrustedByAlice);

        // We encrypt and decrypt in the same go. The user doesn't know that and doesn't
        // have to. In the end, it makes our lives easier.
        var cipherTextContext = doEncryptionDecryption(
                current(),
                decrypt,
                message.getBytes(),
                trustVerifier,
                getPreInitializedCapturer()
        );
        current().setEncryptedMessageAndSeal(cipherTextContext.ciphertextMessage(),
                cipherTextContext.decryptedMessage());
    }

    /**
     * Update the message before calling {@linkplain #encrypt()}.
     *
     * <p>
     * See the docstring of {@link #encrypt()}.
     */
    public void encrypt(String message) throws SignalAlgorithmException {
        checkInvalidEncryptionState();
        current().setMessage(message);
        encrypt();
    }

    /**
     * Throw an error if {@link #encrypt()} is called although the object is already
     * encrypted.
     */
    private void checkInvalidEncryptionState() {
        if (current().isAlreadyEncrypted()) {
            throw new IllegalStateException("Illegal state to encrypt");
        }
    }

    /**
     * Helper method to encrypt and decrypt a given message.
     *
     * It also handles preKeyMessages. This does not care who Alice or Bob is, as
     * long as their ciphers are set correctly in the parameters.
     *
     * @param context          the encryptionContext
     * @param decryptingCipher the keys to use for decryption
     * @param message          the plaintext to encrypt
     * @param trustVerifier    a method which prevents {@link UntrustedIdentityException}
     *                         communication (a so called PreKeyMessage)
     * @return a data object containing the encrypted and the already decrypted
     *         message.
     * @throws SignalAlgorithmException if an algorithm error occurs.
     */
    private CipherTextContext doEncryptionDecryption(
            MessageContext context,
            SessionCipher decryptingCipher,
            byte[] message,
            Consumer<IdentityKey> trustVerifier,
            JCrypToolCapturer nextSendCapturer
    ) throws SignalAlgorithmException {
        CiphertextMessage ciphertextMessage;
        String decryptedMessage;
        byte[] cipherTextMessageBytes;
        try {
            ciphertextMessage = context.getEncryptHandler().doEncrypt(message);
            cipherTextMessageBytes = ciphertextMessage.serialize();
            var receivingCapture = context.getReceivingCapture();

            boolean isPreKeyMessage = ciphertextMessage.getType() == CiphertextMessage.PREKEY_TYPE;

            if (isPreKeyMessage) {
                var preKeyMessage = new PreKeySignalMessage(cipherTextMessageBytes);
                decryptingCipher = algorithm.createSessionCipherForBobFromMessage(
                        algorithm.getAliceAddress(),
                        preKeyMessage
                );

                // If the other side changed their keys, we could run into an UntrustedIdentity exception.
                // We would rather just notify that the key changed, as the messengers do as well.
                // Note that this is currently not consumed by the UI.
                trustVerifier.accept(preKeyMessage.getIdentityKey());

                decryptedMessage = new String(
                        decryptingCipher.decrypt(preKeyMessage, receivingCapture, nextSendCapturer));
                return new CipherTextContext(preKeyMessage, decryptedMessage);
            } else {
                var signalMessage = new SignalMessage(cipherTextMessageBytes);
                decryptedMessage = new String(
                        decryptingCipher.decrypt(signalMessage, receivingCapture, nextSendCapturer));
                return new CipherTextContext(signalMessage, decryptedMessage);
            }
        } catch (UntrustedIdentityException | InvalidMessageException | InvalidVersionException
                | DuplicateMessageException | LegacyMessageException | InvalidKeyIdException | InvalidKeyException
                | NoSessionException e) {
            throw new SignalAlgorithmException(e);
        }

    }

    private JCrypToolCapturer getPreInitializedCapturer() {
        return preInitializedCapturer;
    }

    private void newPreInitializedCapturer() {
        this.preInitializedCapturer = new JCrypToolCapturer();
    }

    private MessageContext newContextWithSender(
            CommunicationEntity sendingEntity,
            JCrypToolCapturer preInitializedSendingCapturer
    ) {
        SessionCipher aliceCipher;
        SessionCipher bobCipher = algorithm.getBobSessionCipher().orElse(null);
        if (algorithm.getAliceSessionCipher().isEmpty()) {
            var newSession = algorithm.initializeAliceSessionCipherToBob();
            preInitializedSendingCapturer = newSession.getValue();
            aliceCipher = newSession.getKey();
        } else {
            aliceCipher = algorithm.getAliceSessionCipher().get();
        }
        return new MessageContext.Builder(sendingEntity).sendingCapture(preInitializedSendingCapturer)
                .aliceSessionCipher(aliceCipher).bobSessionCipher(bobCipher)
                .build();
    }

    private MessageContext initialContextWithSender(CommunicationEntity sendingEntity) {
        var initialSession = algorithm.initializeAliceSessionCipherToBob();
        // Some explanation why we call encrypt here without message.
        // This call to encrypt more or less runs through the whole algorithm
        // calculating all values and ending up with the symmetric key.
        // It does however, not apply the final symmetric key but returns
        // a handler. This allows us to use the values already before
        // actually encrypting anything. The callback is set in the context
        // and can be accessed and used to get the cipher-text.
        return new MessageContext.Builder(sendingEntity)
                .sendingCapture(initialSession.getValue())
                .receivingCapture(JCrypToolCapturer.DEFERED_CAPTURE)
                .aliceSessionCipher(initialSession.getKey())
                .bobSessionCipher(SessionCipher.DEFERED_SESSION_CIPHER)
                .build();
    }

    /**
     * Simple data class holding a pair of information.
     */
    private class CipherTextContext {

        private byte[] ciphertextMessage;
        private String decryptedMessage;

        public CipherTextContext(PreKeySignalMessage preKeySignalMessage, String decryptedMessage) {
            this.ciphertextMessage = preKeySignalMessage.serialize();
            this.decryptedMessage = decryptedMessage;
        }

        public CipherTextContext(SignalMessage signalMessage, String decryptedMessage) {
            this.ciphertextMessage = signalMessage.serialize();
            this.decryptedMessage = decryptedMessage;
        }

        public byte[] ciphertextMessage() {
            return ciphertextMessage;
        }

        public String decryptedMessage() {
            return decryptedMessage;
        }
    }

}
