package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.util.ToHex.toHex;

import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.visual.signalencryption.SignalEncryptionPlugin;
import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.AliceSendingStep;
import org.jcryptool.visual.signalencryption.util.ToHex;

public class DoubleRatchetBobSendingLogic {

    private DoubleRatchetStep currentStep = BobSendingStep.STEP_0;

    public DoubleRatchetBobSendingLogic(DoubleRatchetView swtParent) {
        currentStep = BobSendingStep.STEP_0.setInitialState(swtParent);
    }

    public enum BobSendingStep implements DoubleRatchetStep {
        /**
         * Initial, blank step. Showing Bob's view
         */
        STEP_0 {
            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                swtParent.showBobView();
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                // Hide these Elements
                swtParent.grp_aliceAlgorithm.setVisible(false);
                swtParent.grp_bobAlgorithm.setVisible(false);
                aliceContent.setAllVisible(false);
                bobContent.setAllVisible(false);

                // Hide Steps
                bobContent.txt_bobSendingStep1.setVisible(false);
                bobContent.txt_bobSendingStep2.setVisible(false);
                bobContent.txt_bobSendingStep3.setVisible(false);
                bobContent.txt_bobSendingStep4.setVisible(false);
                bobContent.txt_bobSendingStep5.setVisible(false);
                aliceContent.txt_aliceReceivingStep5.setVisible(false);
                aliceContent.txt_aliceReceivingStep6.setVisible(false);
                aliceContent.txt_aliceReceivingStep7.setVisible(false);
                aliceContent.txt_aliceReceivingStep8.setVisible(false);
                aliceContent.txt_aliceReceivingStep9.setVisible(false);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_1.switchState(swtParent);
                return STEP_1;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                swtParent.switchSenderReceiver();
                AlgorithmState.get().getCommunication().prev();
                AliceSendingStep.STEP_9.switchState(swtParent);
                return AliceSendingStep.STEP_9;
            }
        },
        /**
         * Show Diffie-Hellman calculation.
         */
        STEP_1 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                // On this transition, update all key details as well
                updateSendingKeyDisplayInformation(swtParent);

                // Show these elements
                swtParent.grp_bobAlgorithm.setVisible(true);
                bobContent.setDiffieHellmanChainVisible(true);
                bobContent.txt_bobSendingStep1.setVisible(true);

                // Hide these Elements
                bobContent.setRootChainVisible(false);
				bobContent.txt_bobSendingStep2.setVisible(false);

                // Pick up user input text
                bobContent.txt_bobCipherText.setText(AlgorithmState.get().getAliceEncryptedMessage());
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_2.switchState(swtParent);
                return STEP_2;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_0.switchState(swtParent);
                return STEP_0;
            }
        },
        /**
         * Show the root chain calculation.
         */
        STEP_2 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();

                // Show these labels
                bobContent.grp_bobRootChain.setVisible(true);
                bobContent.setRootChainVisible(true);
                bobContent.txt_bobSendingStep2.setVisible(true);

                // Hide these Elements
                bobContent.setSendingChainVisible(false);
                bobContent.txt_bobSendingStep3.setVisible(false);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_3.switchState(swtParent);
                return STEP_3;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_1.switchState(swtParent);
                return STEP_1;
            }
        },
        /**
         * Show the sending chain calculation.
         */
        STEP_3 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                bobContent.setSendingChainVisible(true);
                bobContent.txt_bobSendingStep3.setVisible(true);

                // Hide these Elements
                bobContent.setMessageBoxVisible(false);
                bobContent.txt_bobSendingStep4.setVisible(false);

                aliceContent.grp_aliceReceivingChain.setVisible(false);

            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_4.switchState(swtParent);
                return STEP_4;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_2.switchState(swtParent);
                return STEP_2;
            }
        },
        /**
         * Show the message input box. Set previously entered text if entered.
         * The <b>message encryption</b> happens when going forward from here.
         * If already encrypted, the message box may not be changed anymore.
         */
        STEP_4 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                bobContent.showOnlyMessagePlaintext();
                bobContent.txt_bobSendingStep4.setVisible(true);

                // Hide these Elements
                swtParent.grp_aliceAlgorithm.setVisible(false);
                bobContent.txt_bobSendingStep5.setVisible(false);
                aliceContent.txt_aliceReceivingStep5.setVisible(false);
                aliceContent.setEncryptedMessageBoxVisible(false);

                if (AlgorithmState.get().allowMessageEntering()) {
                    bobContent.txt_bobPlainText.setEnabled(true);
                } else {
                    bobContent.txt_bobPlainText.setEnabled(false);
                }

                // Set the text for the user message input.
                // Since the MessageContext uses the default message upon construction,
                // it is correctly display on first hitting this state.
                // Afterwards, if a user goes forward/backwards, this ensures that always the
                // correct message is set.
                String userInput = AlgorithmState.get().getCommunication().current().getMessage();
                bobContent.txt_bobPlainText.setText(userInput);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                var communication = AlgorithmState.get().getCommunication();

                if (!communication.current().isAlreadyEncrypted()) {
                    encryptMessage(swtParent);
                }

                // TODO: Update keys on first tab
                STEP_5.switchState(swtParent);
                return STEP_5;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                // Save state of the input field if it has not been encrypted yet.
                if (AlgorithmState.get().allowMessageEntering()) {
                    var currentInput = swtParent.getBobSendingContent().txt_bobPlainText.getText();
                    AlgorithmState.get().getCommunication().current().setMessage(currentInput);
                }
                // TODO: Update keys on first tab
                STEP_3.switchState(swtParent);
                return STEP_3;
            }

        },
        /**
         * Showing the final, encrypted message and "send" it to Alice.
         * Showing Bob's view (important if going backwards).
         */
        STEP_5 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                // Show these elements
                swtParent.grp_aliceAlgorithm.setVisible(true);
                bobContent.setMessageBoxVisible(true);
                aliceContent.setEncryptedMessageBoxVisible(true);
                bobContent.txt_bobSendingStep5.setVisible(true);
                aliceContent.txt_aliceReceivingStep5.setVisible(true);

                swtParent.showBobView();

                // Hide these Elements
                aliceContent.setDiffieHellmanChainVisible(false);
                aliceContent.txt_aliceReceivingStep6.setVisible(false);

                // State
                bobContent.txt_bobPlainText.setEnabled(false);
                var communication = AlgorithmState.get().getCommunication();
                var ciphertextOptional = communication.current().getCiphertextMessage();
                var ciphertextAsBytes = ciphertextOptional.orElse("An error occured".getBytes());
                var ciphertextAsString = toHex(ciphertextAsBytes);
                aliceContent.txt_aliceCipherText.setText(ciphertextAsString);
                bobContent.txt_bobCipherText.setText(ciphertextAsString);
                
                
				// After encrypting the message, we have access to the receiving keys
                updateReceivingKeyDisplayInformation(swtParent);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_6.switchState(swtParent);
                return STEP_6;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_4.switchState(swtParent);
                return STEP_4;
            }
        },
        /**
         * Switch to Alice's view and "receive" the encrypted message.
         * Show the encrypted message text box and the Diffie-Hellman calculation.
         */
        STEP_6 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                swtParent.showAliceView();
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setDiffieHellmanChainVisible(true);
                aliceContent.txt_aliceReceivingStep6.setVisible(true);

                // Hide these Elements
                aliceContent.setRootChainVisible(true);
                aliceContent.txt_aliceReceivingStep7.setVisible(false);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_7.switchState(swtParent);
                return STEP_7;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_5.switchState(swtParent);
                return STEP_5;
            }
        },
        /**
         * Show the root chain calculation.
         */
        STEP_7 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setRootChainVisible(true);;
                aliceContent.txt_aliceReceivingStep7.setVisible(true);

                // Hide these Elements
                aliceContent.setReceivingChainVisible(false);
                aliceContent.txt_aliceReceivingStep8.setVisible(false);

            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_8.switchState(swtParent);
                return STEP_8;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_6.switchState(swtParent);
                return STEP_6;
            }
        },
        /**
         * Show the receiving chain calculation.
         */
        STEP_8 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceReceivingContent();

                // Show these labels
                aliceContent.setReceivingChainVisible(true);
                aliceContent.txt_aliceReceivingStep8.setVisible(true);

                // Hide these Elements
                aliceContent.setDecryptedMessageboxVisible(false);
                aliceContent.txt_aliceReceivingStep9.setVisible(false);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                STEP_9.switchState(swtParent);
                return STEP_9;
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_7.switchState(swtParent);
                return STEP_7;
            }
        },
        /**
         * Show the decrypted message.
         */
        STEP_9 {

            @Override
            protected void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobSendingContent();
                var aliceContent = swtParent.getAliceReceivingContent();
                swtParent.showAliceView();
                // Show these Elements
                swtParent.grp_aliceAlgorithm.setVisible(true);
                swtParent.grp_aliceSteps.setVisible(true);
                aliceContent.setAllVisible(true);
                bobContent.setAllVisible(true);

                // Show Steps
                bobContent.txt_bobSendingStep1.setVisible(true);
                bobContent.txt_bobSendingStep2.setVisible(true);
                bobContent.txt_bobSendingStep3.setVisible(true);
                bobContent.txt_bobSendingStep4.setVisible(true);
                bobContent.txt_bobSendingStep5.setVisible(true);
                aliceContent.txt_aliceReceivingStep5.setVisible(true);
                aliceContent.txt_aliceReceivingStep6.setVisible(true);
                aliceContent.txt_aliceReceivingStep7.setVisible(true);
                aliceContent.txt_aliceReceivingStep8.setVisible(true);
                aliceContent.txt_aliceReceivingStep8.setVisible(true);

                // Show these labels
                aliceContent.txt_alicePlainText.setVisible(true);
                aliceContent.txt_aliceReceivingStep9.setVisible(true);
                var decryptedMessage = AlgorithmState.get().getCommunication().current()
                        .getDecryptedMessage();
                if (decryptedMessage.isPresent()) {
                    aliceContent.txt_alicePlainText.setText(decryptedMessage.get());
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
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                STEP_8.switchState(swtParent);
                return STEP_8;
            }
        };

        protected abstract void switchState(DoubleRatchetView swtParent);

        public BobSendingStep setInitialState(DoubleRatchetView swtParent) {
            STEP_0.switchState(swtParent);
            return STEP_0;
        }
    }

    public void stepForward(DoubleRatchetView swtParent) {
        currentStep = currentStep.next(swtParent);
    }

    public void stepBack(DoubleRatchetView swtParent) {
        currentStep = currentStep.back(swtParent);
    }

    public void reset(DoubleRatchetView swtParent) {
        currentStep = BobSendingStep.STEP_0.setInitialState(swtParent);
    }

    public DoubleRatchetStep getCurrentState() {
        return currentStep;
    }

    /**
     * Get user input from UI and give it to the encryption algorithm.
     */
    private static void encryptMessage(DoubleRatchetView view) {
        var message = view.getBobSendingContent().txt_bobPlainText.getText();
        var communication = AlgorithmState.get().getCommunication();
        try {
            communication.encrypt(message);
        } catch (SignalAlgorithmException e) {
            LogUtil.logError(SignalEncryptionPlugin.PLUGIN_ID,
                    "Sorry, that shouldn't have happened, must restart", e, true);
            view.resetAll();
        }
    }
    
    private static void updateSendingKeyDisplayInformation(DoubleRatchetView view) {
		var bobContent = view.getBobSendingContent();
		var ctx = AlgorithmState.get().getCommunication().current();
		bobContent.txt_bobSendingChain5.getPopupProvider().setValue(ctx.senderChainNewChainKey());
		bobContent.txt_bobSendingChain4.getPopupProvider().setValues(ctx.senderChainMessageKey());
		bobContent.txt_bobSendingChain3.getPopupProvider().setValue(ctx.senderChainOutput());
		bobContent.txt_bobSendingChain2.getPopupProvider().setValue(ctx.senderChainConstantInput());
		bobContent.txt_bobSendingChain1.getPopupProvider().setValue(ctx.senderChainChainKey());
		bobContent.txt_bobRootChain4.getPopupProvider().setValue(ctx.senderRootNewRootChainKey());
		bobContent.txt_bobRootChain3.getPopupProvider().setValues(ctx.senderRootOutput());
		bobContent.txt_bobRootChain2.getPopupProvider().setValue(ctx.senderRootChainKey());
		bobContent.txt_bobRootChain1.getPopupProvider().setValue(ctx.senderRootConstantInput());
		bobContent.txt_bobDiffieHellman3.getPopupProvider().setValue(ctx.diffieHellmanSenderPrivateKey());
		bobContent.txt_bobDiffieHellman2.getPopupProvider().setValue(ctx.diffieHellmanSenderOutput());
		bobContent.txt_bobDiffieHellman1.getPopupProvider().setValue(ctx.diffieHellmanSenderPublicKey());
    }
	private static void updateReceivingKeyDisplayInformation(DoubleRatchetView view) {
		var aliceContent = view.getAliceReceivingContent();
		var ctx = AlgorithmState.get().getCommunication().current();
		aliceContent.txt_aliceReceivingChain5.getPopupProvider().setValue(ctx.receiverChainNewChainKey());
		aliceContent.txt_aliceReceivingChain4.getPopupProvider().setValues(ctx.receiverChainMessageKey());
		aliceContent.txt_aliceReceivingChain3.getPopupProvider().setValue(ctx.receiverChainOutput());
		aliceContent.txt_aliceReceivingChain2.getPopupProvider().setValue(ctx.receiverChainConstantInput());
		aliceContent.txt_aliceReceivingChain1.getPopupProvider().setValue(ctx.receiverChainChainKey());
		aliceContent.txt_aliceRootChain4.getPopupProvider().setValue(ctx.receiverRootNewRootChainKey());
		aliceContent.txt_aliceRootChain3.getPopupProvider().setValues(ctx.receiverRootOutput());
		aliceContent.txt_aliceRootChain2.getPopupProvider().setValue(ctx.receiverRootChainKey());
		aliceContent.txt_aliceRootChain1.getPopupProvider().setValue(ctx.receiverRootConstantInput());
		aliceContent.txt_aliceDiffieHellman3.getPopupProvider().setValue(ctx.diffieHellmanReceiverPrivateKey());
		aliceContent.txt_aliceDiffieHellman2.getPopupProvider().setValue(ctx.diffieHellmanReceiverOutput());
		aliceContent.txt_aliceDiffieHellman1.getPopupProvider().setValue(ctx.diffieHellmanReceiverPublicKey());
	}
}
