package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.ALICE;
import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.BOB;
import static org.jcryptool.visual.signalencryption.util.ToHex.toHex;

import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.visual.signalencryption.SignalEncryptionPlugin;
import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;
import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.AliceSendingStep;

/**
 * All state changes necesssary to represent Bob sending a message to Alice.
 * I know that this class is kind of redundant, but I don't have the time to refactor it.
 *
 * @see DoubleRatchetAliceSendingLogic
 */
public class DoubleRatchetBobSendingLogic {

    public enum BobSendingStep implements DoubleRatchetStep {
        /**
         * Initial, blank step. Showing Bob's view
         */
        STEP_0(0, BOB) {
            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showBobView();
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                swtParent.showBobSendingLabel();
                swtParent.showAliceWaitingLabel();
                // Hide these Elements
                aliceContent.setAllVisible(false);
                bobContent.setAllVisible(false);

                // Hide Steps
                bobContent.showStep(this);
                aliceContent.showStep(this);
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                swtParent.switchSenderReceiver();
                AlgorithmState.get().getCommunication().prev();
                AliceSendingStep.STEP_9.switchState(swtParent);
                return AliceSendingStep.STEP_9;
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_1;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return AliceSendingStep.STEP_9;
            }
        },
        /**
         * Show Diffie-Hellman calculation.
         */
        STEP_1(1, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                // On this transition, update all key details as well
                updateSendingKeyDisplayInformation(swtParent);
                swtParent.showBobSending();

                // Show these elements
                bobContent.setDiffieHellmanRatchetVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setRootChainVisible(false);

                // Set plain- and ciphertext in case the user is jumping around.
                bobContent.txt_plainText.setText(AlgorithmState.get().getPlainTextMessage());
                bobContent.txt_cipherText.setText(AlgorithmState.get().getEncryptedMessage());
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_2;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_0;
            }
        },
        /**
         * Show the root chain calculation.
         */
        STEP_2(2, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();

                // Show these labels
                bobContent.setRootChainVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setSendingChainVisible(false);
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_3;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_1;
            }
        },

        /**
         * Show the sending chain calculation.
         */
        STEP_3(3, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();

                // Show these labels
                bobContent.setSendingChainVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setMessageBoxVisible(false);

            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_4;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_2;
            }
        },
        /**
         * Show the message input box. Set previously entered text if entered. The
         * <b>message encryption</b> happens when going forward from here. If already
         * encrypted, the message box may not be changed anymore.
         */
        STEP_4(4, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                bobContent.showOnlyMessagePlaintext();
                bobContent.showStep(this);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setEncryptedMessageBoxVisible(false);

                if (AlgorithmState.get().allowMessageEntering()) {
                    bobContent.txt_plainText.setEnabled(true);
                    bobContent.txt_plainText.setFocus();
                } else {
                    bobContent.txt_plainText.setEnabled(false);
                }

                // Set the text for the user message input.
                // Since the MessageContext uses the default message upon construction,
                // it is correctly display on first hitting this state.
                // Afterwards, if a user goes forward/backwards, this ensures that always the
                // correct message is set.
                String userInput = AlgorithmState.get().getCommunication().current().getMessage();
                bobContent.txt_plainText.setText(userInput);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                var communication = AlgorithmState.get().getCommunication();

                if (!communication.current().isAlreadyEncrypted()) {
                    passMessageToEncryption(swtParent);
                }

                STEP_5_SENDING.switchState(swtParent);
                return STEP_5_SENDING;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                // Save state of the input field if it has not been encrypted yet.
                if (AlgorithmState.get().allowMessageEntering()) {
                    var currentInput = swtParent.getBobSendingContent().txt_plainText.getText();
                    AlgorithmState.get().getCommunication().current().setMessage(currentInput);
                }
                STEP_3.switchState(swtParent);
                return STEP_3;
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_5_SENDING;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_3;
            }
        },
        /**
         * Showing the final, encrypted message and "send" it to Alice. Showing Bob's
         * perspective of the step.
         */
        STEP_5_SENDING(5, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showBobView();
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                swtParent.showAliceReceiving();
                // Show these elements
                bobContent.setMessageBoxVisible(true);
                aliceContent.setEncryptedMessageBoxVisible(true);
                aliceContent.showStep(this);
                bobContent.showStep(this);

                // Hide these Elements
                aliceContent.setDiffieHellmanRatchetVisible(false);

                // State
                bobContent.txt_plainText.setEnabled(false);
                var communication = AlgorithmState.get().getCommunication();
                var ciphertextOptional = communication.current().getCiphertextMessage();
                var ciphertextAsBytes = ciphertextOptional.orElse("An error occured".getBytes());
                var ciphertextAsString = toHex(ciphertextAsBytes);
                aliceContent.txt_cipherText.setText(ciphertextAsString);
                bobContent.txt_cipherText.setText(ciphertextAsString);

                // After encrypting the message, we have access to the receiving keys
                updateReceivingKeyDisplayInformation(swtParent);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_5_RECEIVING.switchState(swtParent);
                return STEP_5_RECEIVING;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_4.switchState(swtParent);
                return STEP_4;
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_5_RECEIVING;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_4;
            }
        },
        /**
         * Showing the final, encrypted message and "send" it to Alice. Showing Alices's
         * perspective of the step.
         */
        STEP_5_RECEIVING(5, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showAliceView();
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                swtParent.showAliceReceiving();
                // Show these elements
                bobContent.setMessageBoxVisible(true);
                aliceContent.setEncryptedMessageBoxVisible(true);
                aliceContent.showStep(this);
                bobContent.showStep(this);

                // Hide these Elements
                aliceContent.setDiffieHellmanRatchetVisible(false);

                // State
                bobContent.txt_plainText.setEnabled(false);
                var communication = AlgorithmState.get().getCommunication();
                var ciphertextOptional = communication.current().getCiphertextMessage();
                var ciphertextAsBytes = ciphertextOptional.orElse("An error occured".getBytes());
                var ciphertextAsString = toHex(ciphertextAsBytes);
                aliceContent.txt_cipherText.setText(ciphertextAsString);
                bobContent.txt_cipherText.setText(ciphertextAsString);

                // After encrypting the message, we have access to the receiving keys
                updateReceivingKeyDisplayInformation(swtParent);
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_6;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_5_SENDING;
            }
        },
        /**
         * Switch to Alice's view and "receive" the encrypted message. Show the
         * encrypted message text box and the Diffie-Hellman calculation.
         */
        STEP_6(6, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setDiffieHellmanRatchetVisible(true);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setRootChainVisible(false);
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_7;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_5_RECEIVING;
            }
        },
        /**
         * Show the root chain calculation.
         */
        STEP_7(7, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setRootChainVisible(true);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setReceivingChainVisible(false);

            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_8;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_6;
            }
        },
        /**
         * Show the receiving chain calculation.
         */
        STEP_8(8, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setReceivingChainVisible(true);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setDecryptedMessageboxVisible(false);
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_9;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_7;
            }
        },
        /**
         * Show the decrypted message.
         */
        STEP_9(9, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                swtParent.showAliceReceiving();
                // Show these Elements
                aliceContent.setAllVisible(true);
                bobContent.setAllVisible(true);

                // Show Steps
                aliceContent.showStep(this);
                bobContent.showStep(this);

                // Set plain- and ciphertext in case the user is jumping around.
                bobContent.txt_plainText.setText(AlgorithmState.get().getPlainTextMessage());
                bobContent.txt_cipherText.setText(AlgorithmState.get().getEncryptedMessage());
                var decryptedMessage = AlgorithmState.get().getCommunication().current().getDecryptedMessage();
                if (decryptedMessage.isPresent()) {
                    aliceContent.txt_plainText.setText(decryptedMessage.get());
                } else {
                    LogUtil.logError(new SignalAlgorithmException(), true);
                }
                // On this transition, update all key details as well
                updateSendingKeyDisplayInformation(swtParent);
                updateReceivingKeyDisplayInformation(swtParent);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                swtParent.switchSenderReceiver();
                AlgorithmState.get().getCommunication().next();
                AliceSendingStep.STEP_0.switchState(swtParent);
                return AliceSendingStep.STEP_0;
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return AliceSendingStep.STEP_0;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_8;
            }

        };

        private int id;
        private CommunicationEntity entityToShow;

        BobSendingStep(int id, CommunicationEntity entityToShow) {
            this.id = id;
            this.entityToShow = entityToShow;
        }

        @Override
        public CommunicationEntity shouldShowEntity() {
            return entityToShow;
        }

        @Override
        public int getStepIndex() {
            return id;
        }

        public BobSendingStep setInitialState(DoubleRatchetView swtParent) {
            STEP_0.switchState(swtParent);
            return STEP_0;
        }
    }

    /**
     * Get user input from UI and give it to the encryption algorithm.
     */
    private static void passMessageToEncryption(DoubleRatchetView view) {
        var message = view.getBobSendingContent().txt_plainText.getText();
        var communication = AlgorithmState.get().getCommunication();
        try {
            communication.encrypt(message);
        } catch (SignalAlgorithmException e) {
            LogUtil.logError(SignalEncryptionPlugin.PLUGIN_ID, "Sorry, that shouldn't have happened, must restart", e,
                    true);
            view.resetView();
        }
    }

    private static void updateSendingKeyDisplayInformation(DoubleRatchetView view) {
        var bobContent = view.getBobSendingContent();
        var ctx = AlgorithmState.get().getCommunication().current();
        bobContent.txt_diffieHellmanTop.getPopupProvider().setValue(ctx.diffieHellmanSenderPublicKey());
        bobContent.txt_diffieHellmanMid.getPopupProvider().setValue(ctx.diffieHellmanSenderOutput());
        bobContent.txt_diffieHellmanBot.getPopupProvider().setValue(ctx.diffieHellmanSenderPrivateKey());
        bobContent.txt_rootChainTop.getPopupProvider().setValue(ctx.senderRootChainKey());
        bobContent.txt_rootChainConst.getPopupProvider().setValue(ctx.senderRootConstantInput());
        bobContent.txt_rootChainMid.getPopupProvider().setValues(ctx.senderRootOutput());
        bobContent.txt_rootChainBot.getPopupProvider().setValue(ctx.senderRootNewRootChainKey());
        bobContent.txt_sendingChainTop.getPopupProvider().setValue(ctx.senderChainChainKey());
        bobContent.txt_sendingChainConst.getPopupProvider().setValue(ctx.senderChainConstantInput());
        bobContent.txt_sendingChainMid.getPopupProvider().setValue(ctx.senderChainOutput());
        bobContent.txt_sendingChainBot.getPopupProvider().setValue(ctx.senderChainNewChainKey());
        bobContent.txt_messageKeys.getPopupProvider().setValues(ctx.senderChainMessageKey());
    }

    private static void updateReceivingKeyDisplayInformation(DoubleRatchetView view) {
        var aliceContent = view.getAliceReceivingContent();
        var ctx = AlgorithmState.get().getCommunication().current();
        aliceContent.txt_diffieHellmanTop.getPopupProvider().setValue(ctx.diffieHellmanReceiverPublicKey());
        aliceContent.txt_diffieHellmanMid.getPopupProvider().setValue(ctx.diffieHellmanReceiverOutput());
        aliceContent.txt_diffieHellmanBot.getPopupProvider().setValue(ctx.diffieHellmanReceiverPrivateKey());
        aliceContent.txt_rootChainTop.getPopupProvider().setValue(ctx.receiverRootChainKey());
        aliceContent.txt_rootChainConst.getPopupProvider().setValue(ctx.receiverRootConstantInput());
        aliceContent.txt_rootChainMid.getPopupProvider().setValues(ctx.receiverRootOutput());
        aliceContent.txt_rootChainBot.getPopupProvider().setValue(ctx.receiverRootNewRootChainKey());
        aliceContent.txt_receivingChainTop.getPopupProvider().setValue(ctx.receiverChainChainKey());
        aliceContent.txt_receivingChainConst.getPopupProvider().setValue(ctx.receiverChainConstantInput());
        aliceContent.txt_receivingChainMid.getPopupProvider().setValue(ctx.receiverChainOutput());
        aliceContent.txt_receivingChainBot.getPopupProvider().setValue(ctx.receiverChainNewChainKey());
        aliceContent.txt_messageKeys.getPopupProvider().setValues(ctx.receiverChainMessageKey());
    }
}
