package org.jcryptool.visual.signalencryption.algorithm;

import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.whispersystems.libsignal.*;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import org.whispersystems.libsignal.state.IdentityKeyStore;
import org.whispersystems.libsignal.state.IdentityKeyStore.Direction;
import org.whispersystems.libsignal.state.PreKeyBundle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages keys, sessions and identities - the heavy lifting with libsignal happens here.
 */
public class EncryptionAlgorithm {

    private static final int ALICE_DEVICE_ID = 42;
    private static final int BOB_DEVICE_ID = 43;
    private static final String ALICE_NAME = "Alice";
    private static final String BOB_NAME = "Bob";

    private final SignalProtocolAddress aliceAddress;
    private final SignalProtocolAddress bobAddress;

    private SessionStores aliceSessionStores;
    private SessionStores bobSessionStores;

    private SessionCipher aliceSessionCipher;
    private SessionCipher bobSessionCipher;

    public EncryptionAlgorithm() {
        this.aliceAddress = new SignalProtocolAddress(ALICE_NAME, ALICE_DEVICE_ID);
        this.bobAddress = new SignalProtocolAddress(BOB_NAME, BOB_DEVICE_ID);
        newIdentities();
    }

    /** Creates new identities / keys for both entities (device ID stays the same) */
    public void newIdentities() {
        newIdentityAlice();
        newIdentityBob();
    }

    /** Creates a new identity / keys for Alice (device ID stays the same) */
    public void newIdentityAlice() {
        try {
            AlgorithmBaseParameters aliceParameters = new AlgorithmBaseParameters(getAliceAddress().getDeviceId());
            aliceSessionStores = new SessionStores(aliceParameters);
            aliceSessionStores.uploadPreKeyBundle(getAliceAddress(), aliceParameters);
            aliceSessionCipher = null; // Reset any existing connections
        } catch (InvalidKeyException e) {
        	throw new SignalAlgorithmException();
        }
    }

    /** Creates a new identity / keys for Bob (device ID stays the same) */
    public void newIdentityBob() {
        try {
        	IdentityKey oldIdentity = bobSessionStores != null ? bobSessionStores.getIdentityKeyStore().getIdentityKeyPair().getPublicKey() : null;
            AlgorithmBaseParameters bobParameters = new AlgorithmBaseParameters(getBobAddress().getDeviceId());
            bobSessionStores = new SessionStores(bobParameters);
            bobSessionStores.uploadPreKeyBundle(getBobAddress(), bobParameters);
            bobSessionCipher = null; // Reset any existing connections
            notifiySendersAboutChangedKey(getBobAddress(), List.of(getAliceAddress()), oldIdentity, bobParameters.getIdentityKey());
        } catch (InvalidKeyException e) {
        	throw new SignalAlgorithmException();
        }
    }

    /**
     * Creates a new session for Alice towards the target address.
     * It can be accessed via {@link #getAliceSessionCipher()}
     * 
     * @throws SignalAlgorithmException
     */
    public JCrypToolCapturer aliceSessionTo(SignalProtocolAddress other) {
        var capturer = new JCrypToolCapturer();
        try {
            aliceSessionCipher = aliceSessionStores.createSession(FakeSignalServer.retrievePreKeyBundle(other), other, capturer);
        } catch (UntrustedIdentityException | InvalidKeyException e) {
            throw new SignalAlgorithmException(e);
        }
        return capturer;
    }

    /**
     * Creates a new session for Bob towards the sender address with the given message.
     * It can be accessed via {@link #getBobSessionCipher()}
     */
    public SessionCipher createSessionCipherForBobFromMessage(SignalProtocolAddress sender, PreKeySignalMessage message) {
        bobSessionCipher = new SessionCipher(
                bobSessionStores.getSessionStore(),
                bobSessionStores.getPreKeyStore(),
                bobSessionStores.getSignedPreKeyStore(),
                bobSessionStores.getIdentityKeyStore(),
                sender
        );
        return bobSessionCipher;
    }


    /** Print a message if the identity changed - then trust it. */
    public void checkIdentityTrustedByAlice(IdentityKey theirIdentity) {
        // Since we know that only alice and bob communicate in this plug-in, we shortcut here a little.
        SignalProtocolAddress theirAddress = getBobAddress();

        if (!aliceSessionStores.getIdentityKeyStore().isTrustedIdentity(theirAddress, theirIdentity, IdentityKeyStore.Direction.RECEIVING)) {
            System.out.printf("Security key with %s changed. Please verify this is correct%n", theirAddress);
            aliceSessionStores.getIdentityKeyStore().saveIdentity(theirAddress, theirIdentity);
        }
    }

    /** Print a message if the identity changed - then trust it. */
    public void checkIdentityTrustedByBob(IdentityKey theirIdentity) {
        // Since we know that only alice and bob communicate in this plug-in, we shortcut here a little.
        SignalProtocolAddress theirAddress = getAliceAddress();

        if (!bobSessionStores.getIdentityKeyStore().isTrustedIdentity(theirAddress, theirIdentity, IdentityKeyStore.Direction.RECEIVING)) {
            System.out.printf("Security key with %s changed. Please verify this is correct%n", theirAddress);
            bobSessionStores.getIdentityKeyStore().saveIdentity(theirAddress, theirIdentity);
        }
    }

    /** Can be null if the session was reset or no message was received yet. */
    public Optional<SessionCipher> getAliceSessionCipher() {
        return Optional.ofNullable(aliceSessionCipher);
    }

    /** Can be null if the session was reset or no message was received yet. */
    public Optional<SessionCipher> getBobSessionCipher() {
        return Optional.ofNullable(bobSessionCipher);
    }

    public Map.Entry<SessionCipher, JCrypToolCapturer> initializeAliceSessionCipherToBob() {
        var capturer = aliceSessionTo(getBobAddress());
        return Map.entry(aliceSessionCipher, capturer);
    }

    public SignalProtocolAddress getBobAddress() {
        return bobAddress;
    }

    public SignalProtocolAddress getAliceAddress() {
        return aliceAddress;
    }
    
    public PreKeyBundle getAlicePreKeyBundle() {
    	return FakeSignalServer.peekPreKeyBundle(aliceAddress);
    }

    public PreKeyBundle getBobPreKeyBundle() {
        return FakeSignalServer.peekPreKeyBundle(bobAddress);
    }

    private void notifiySendersAboutChangedKey(
            SignalProtocolAddress origin,
            List<SignalProtocolAddress> senders,
            IdentityKey oldIdentity,
            IdentityKey newIdentity) {

        for (var sender : senders) {
            if (sender.equals(getAliceAddress())
                    && aliceSessionStores.getIdentityKeyStore().getIdentity(origin) != null) {
                aliceSessionCipher = null;
                aliceSessionStores.getIdentityKeyStore().saveIdentity(origin, newIdentity);
            }
            // This case is not gonna happen in the plugin, because bob never sends a
            // message first. But I leave it for future improvements and ssake
            // of completeness.
            if (sender.equals(getBobAddress()) && bobSessionStores.getIdentityKeyStore().getIdentity(origin) != null) {
                bobSessionCipher = null;
                bobSessionStores.getIdentityKeyStore().saveIdentity(origin, newIdentity);
            }
        }

    }
}
