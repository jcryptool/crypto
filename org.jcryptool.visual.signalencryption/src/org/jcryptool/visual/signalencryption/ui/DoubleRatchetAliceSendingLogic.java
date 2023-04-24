package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.ALICE;
import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.BOB;
import static org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.Direction.BACKWARD;
import static org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.Direction.FORWARD;

import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.visual.signalencryption.SignalEncryptionPlugin;
import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;
import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetBobSendingLogic.BobSendingStep;
import org.jcryptool.visual.signalencryption.util.ToHex;

public class DoubleRatchetAliceSendingLogic {

    private DoubleRatchetStep currentStep = AliceSendingStep.STEP_0;

    public DoubleRatchetAliceSendingLogic(DoubleRatchetView swtParent) {
        currentStep = AliceSendingStep.STEP_0.setInitialState(swtParent);
    }

    public enum AliceSendingStep implements DoubleRatchetStep {
        /**
         * Initial, blank step. Showing Alice's view
         */
        STEP_0(0, ALICE) {
            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showAliceView();
                updateSenderKeyDisplayInformation(swtParent);
                var bobContent = swtParent.getBobReceivingContent();
                var aliceContent = swtParent.getAliceSendingContent();

                // Hide these Elements
                aliceContent.setAllVisible(false);
                bobContent.setAllVisible(false);


                // Initial value only valid for initial message
                if (AlgorithmState.get().getCommunication().isBeginning()) {
                    swtParent.showAliceSendingInitialLabel();
                    swtParent.showBobWaitingInitialLabel();
                    aliceContent.setInitialStepDescriptions();
                    bobContent.setInitialStepDescriptions();
                } else {
                    swtParent.showAliceSendingLabel();
                    swtParent.showBobWaitingLabel();
                    aliceContent.setNormalStepDescriptions();
                    bobContent.setNormalStepDescriptions();
                }
                // Hide Steps (this is done after setting the initial/normal descriptions to prevent weird behaviour)
                aliceContent.showStep(this);
                bobContent.showStep(this);
            }

            @Override
            public DoubleRatchetStep back(DoubleRatchetView swtParent) {
                if (AlgorithmState.get().getCommunication().isBeginning()) {
                    return STEP_0;
                } else {
                    swtParent.switchSenderReceiver();
                    AlgorithmState.get().getCommunication().prev();
                    BobSendingStep.STEP_9.switchState(swtParent);
                    return BobSendingStep.STEP_9;
                }
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return STEP_1;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                if (AlgorithmState.get().getCommunication().isBeginning()) {
                    return STEP_0;
                } else {
                    return BobSendingStep.STEP_9;
                }
            }
        },
        /**
         * Show Diffie-Hellman calculation.
         */
        STEP_1(1, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceSendingContent();
                // On this transition, update all key details as well
                updateSenderKeyDisplayInformation(swtParent);

                // Show these elements
                swtParent.showAliceSending();
                aliceContent.setDiffieHellmanRatchetVisible(true);
                aliceContent.showStep(this);

                // TODO verify this line is needed
                // Pick up user input text
                aliceContent.txt_cipherText.setText(AlgorithmState.get().getAliceEncryptedMessage());

                // Notify the UI that the user has already progressed and viewed values in this
                // tab
                AlgorithmState.get().getCommunication().setInProgress();
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
        STEP_2(2, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceSendingContent();

                // Show these labels
                aliceContent.setRootChainVisible(true);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setSendingChainVisible(false);
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
        STEP_3(3, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceSendingContent();

                // Show these labels
                aliceContent.setSendingChainVisible(true);
                aliceContent.showStep(this);

                // Hide these Elements
                aliceContent.setMessageBoxVisible(false);
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
        STEP_4(4, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var aliceContent = swtParent.getAliceSendingContent();
                var bobContent = swtParent.getBobReceivingContent();
                if (AlgorithmState.get().getCommunication().isBeginning()) {
                    swtParent.showBobWaitingInitialLabel();
                } else {
                    swtParent.showBobWaitingLabel();
                }

                // Show these labels
                aliceContent.showOnlyMessagePlaintext();
                aliceContent.showStep(this);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setEncryptedMessageBoxVisible(false);

                if (AlgorithmState.get().allowMessageEntering()) {
                    aliceContent.txt_plainText.setEnabled(true);
                } else {
                    aliceContent.txt_plainText.setEnabled(false);

                }
                // Set the text for the user message input.
                // Since the MessageContext uses the default message upon construction,
                // it is correctly display on first hitting this state.
                // Afterwards, if a user goes forward/backwards, this ensures that always the
                // correct message is set.
                String userInput = AlgorithmState.get().getCommunication().current().getMessage();
                aliceContent.txt_plainText.setText(userInput);
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
                    var currentInput = swtParent.getAliceSendingContent().txt_plainText.getText();
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
         * Showing the final, encrypted message and "send" it to Bob. Showing Alice's
         * view (important if going backwards).
         */
        STEP_5_SENDING(5, ALICE) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showAliceView();
                var aliceContent = swtParent.getAliceSendingContent();
                var bobContent = swtParent.getBobReceivingContent();
                // Show these elements
                swtParent.showBobReceiving();
                aliceContent.setMessageBoxVisible(true);
                bobContent.setEncryptedMessageBoxVisible(true);
                aliceContent.showStep(this);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setDiffieHellmanRatchetVisible(false);
                bobContent.txt_bobReceivingStep6.setVisible(false);

                // State
                aliceContent.txt_plainText.setEnabled(false);
                var communication = AlgorithmState.get().getCommunication();
                var ciphertextOptional = communication.current().getCiphertextMessage();
                var ciphertextAsBytes = ciphertextOptional.orElse("An error occured".getBytes());
                var ciphertextAsString = ToHex.toHex(ciphertextAsBytes);
                aliceContent.txt_cipherText.setText(ciphertextAsString);
                bobContent.txt_cipherText.setText(ciphertextAsString);

                // After encrypting the message, we have access to the receiving keys
                updateReceivingKeyDisplayInformation(swtParent);
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
         * Showing the final, encrypted message and "send" it to Bob. Showing Alice's
         * view (important if going backwards).
         */
        STEP_5_RECEIVING(5, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                swtParent.showBobView();
                var aliceContent = swtParent.getAliceSendingContent();
                var bobContent = swtParent.getBobReceivingContent();
                // Show these elements
                swtParent.showBobReceiving();
                aliceContent.setMessageBoxVisible(true);
                bobContent.setEncryptedMessageBoxVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setDiffieHellmanRatchetVisible(false);

                // State
                aliceContent.txt_plainText.setEnabled(false);
                var communication = AlgorithmState.get().getCommunication();
                var ciphertextOptional = communication.current().getCiphertextMessage();
                var ciphertextAsBytes = ciphertextOptional.orElse("An error occured".getBytes());
                var ciphertextAsString = ToHex.toHex(ciphertextAsBytes);
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
         * Switch to Bob's view and "receive" the encrypted message. Show the encrypted
         * message text box and the Diffie-Hellman calculation.
         */
        STEP_6(6, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobReceivingContent();

                // Show these labels
                bobContent.setDiffieHellmanRatchetVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setRootChainVisible(false);
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
        STEP_7(7, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobReceivingContent();

                // Show these labels
                bobContent.setRootChainVisible(true);
                bobContent.showStep(this);

                // Hide these Elements
                bobContent.setReceivingChainVisible(false);

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
        STEP_8(8, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobReceivingContent();
                bobContent.showStep(this);

                // Show these labels
                bobContent.setReceivingChainVisible(true);

                // Hide these Elements
                bobContent.setDecryptedMessageboxVisible(false);
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
        STEP_9(9, BOB) {

            @Override
            public void switchState(DoubleRatchetView swtParent) {
                var bobContent = swtParent.getBobReceivingContent();
                var aliceContent = swtParent.getAliceSendingContent();
                swtParent.showBobReceiving();
                // Show these Elements
                aliceContent.setAllVisible(true);
                bobContent.setAllVisible(true);

                var decryptedMessage = AlgorithmState.get().getCommunication().current().getDecryptedMessage();
                if (decryptedMessage.isPresent()) {
                    bobContent.txt_plainText.setText(decryptedMessage.get());
                } else {
                    LogUtil.logError(new SignalAlgorithmException(), true);
                }
                // On this transition, update all key steps as well
                updateSenderKeyDisplayInformation(swtParent);
                updateReceivingKeyDisplayInformation(swtParent);

                if (AlgorithmState.get().getCommunication().isBeginning()) {
                    aliceContent.setInitialStepDescriptions();
                    bobContent.setInitialStepDescriptions();
                } else {
                    aliceContent.setNormalStepDescriptions();
                    bobContent.setNormalStepDescriptions();
                }
                // Show Steps (this is done after setting the initial/normal descriptions to prevent weird behaviour)
                aliceContent.showStep(this);
                bobContent.showStep(this);
            }

            @Override
            public DoubleRatchetStep next(DoubleRatchetView swtParent) {
                swtParent.switchSenderReceiver();
                AlgorithmState.get().getCommunication().next();
                BobSendingStep.STEP_0.switchState(swtParent);
                return BobSendingStep.STEP_0;
            }

            @Override
            public DoubleRatchetStep peekForward() {
                return BobSendingStep.STEP_0;
            }

            @Override
            public DoubleRatchetStep peekBackward() {
                return STEP_8;
            }
        };

        private int id;
        private CommunicationEntity entityToShow;

        AliceSendingStep(int id, CommunicationEntity entityToShow) {
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

        public AliceSendingStep setInitialState(DoubleRatchetView swtParent) {
            STEP_0.switchState(swtParent);
            return STEP_0;
        }
    }

    public void stepForward(DoubleRatchetView swtParent) {
        if (isShowingCorrectView(swtParent, FORWARD) || stateChangeIncludesViewChange(FORWARD)) {
            currentStep = currentStep.next(swtParent);
        } else {
            showCorrectView(swtParent, FORWARD);
        }
    }

    public void stepBack(DoubleRatchetView swtParent) {
        if (isShowingCorrectView(swtParent, BACKWARD) || stateChangeIncludesViewChange(BACKWARD)) {
            currentStep = currentStep.back(swtParent);
        } else {
            showCorrectView(swtParent, BACKWARD);
        }
    }

    public void reset(DoubleRatchetView swtParent) {
        currentStep = AliceSendingStep.STEP_0.setInitialState(swtParent);
    }

    public DoubleRatchetStep getCurrentStep() {
        return currentStep;
    }

    /**
     * Check if the state change into direction causes a natural view change. This
     * more or less means: next-button becomes showAliceView/showBobView button
     */
    private boolean stateChangeIncludesViewChange(Direction direction) {
        if (direction == BACKWARD) {
            return currentStep.requiresViewSwitchBackward();
        } else {
            return currentStep.requiresViewSwitchForward();
        }
    }

    /**
     * Some states only differ if they show Alice or Bob - switch state if the user
     * manually switches
     * <p/>
     * Example:
     *
     * <pre>
     *    Reaching end of alice view (next-button would switch to bob's view)
     *    User manually switches to bob view (next-button logic switches to showing next step of bob)
     * </pre>
     */
    public void doStateChangeIfRequiredByViewChange(DoubleRatchetView swtParent, CommunicationEntity showingEntity) {
        if (currentStep.requiresViewSwitchForward() && currentStep.peekForward().shouldShowEntity() == showingEntity) {
            stepForward(swtParent);
        }
        if (currentStep.requiresViewSwitchBackward()
                && currentStep.peekBackward().shouldShowEntity() == showingEntity) {
            stepBack(swtParent);
        }
    }

    private boolean isShowingCorrectView(DoubleRatchetView view, Direction direction) {
        return direction.peek(currentStep).shouldShowEntity() == view.getCurrentlyShowingEntity();
    }

    private void showCorrectView(DoubleRatchetView swtParent, Direction direction) {
        switchViewTo(direction.peek(currentStep).shouldShowEntity(), swtParent);
    }

    /**
     * Get user input from UI and give it to the encryption algorithm.
     */
    private static void passMessageToEncryption(DoubleRatchetView view) {
        var message = view.getAliceSendingContent().txt_plainText.getText();
        var communication = AlgorithmState.get().getCommunication();
        try {
            communication.encrypt(message);
        } catch (SignalAlgorithmException e) {
            LogUtil.logError(SignalEncryptionPlugin.PLUGIN_ID,
                    "Sorry, that shouldn't have happened. Please restart the plug-in", e, true);
            view.resetView();
        }
    }

    private static void updateSenderKeyDisplayInformation(DoubleRatchetView view) {
        var aliceContent = view.getAliceSendingContent();
        var ctx = AlgorithmState.get().getCommunication().current();
        aliceContent.txt_diffieHellmanTop.getPopupProvider().setValue(ctx.diffieHellmanSenderPublicKey());
        aliceContent.txt_diffieHellmanMid.getPopupProvider().setValue(ctx.diffieHellmanSenderOutput());
        aliceContent.txt_diffieHellmanBot.getPopupProvider().setValue(ctx.diffieHellmanSenderPrivateKey());
        aliceContent.txt_rootChainTop.getPopupProvider().setValue(ctx.senderRootChainKey());
        aliceContent.txt_rootChainConst.getPopupProvider().setValue(ctx.senderRootConstantInput());
        aliceContent.txt_rootChainMid.getPopupProvider().setValues(ctx.senderRootOutput());
        aliceContent.txt_rootChainBot.getPopupProvider().setValue(ctx.senderRootNewRootChainKey());
        aliceContent.txt_sendingChainTop.getPopupProvider().setValue(ctx.senderChainChainKey());
        aliceContent.txt_sendingChainConst.getPopupProvider().setValue(ctx.senderChainConstantInput());
        aliceContent.txt_sendingChainMid.getPopupProvider().setValue(ctx.senderChainOutput());
        aliceContent.txt_sendingChainBot.getPopupProvider().setValue(ctx.senderChainNewChainKey());
        aliceContent.txt_messageKeys.getPopupProvider().setValues(ctx.senderChainMessageKey());
    }

    private static void updateReceivingKeyDisplayInformation(DoubleRatchetView view) {
        var bobContent = view.getBobReceivingContent();
        var ctx = AlgorithmState.get().getCommunication().current();
        bobContent.txt_diffieHellmanTop.getPopupProvider().setValue(ctx.diffieHellmanReceiverPublicKey());
        bobContent.txt_diffieHellmanMid.getPopupProvider().setValue(ctx.diffieHellmanReceiverOutput());
        bobContent.txt_diffieHellmanBot.getPopupProvider().setValue(ctx.diffieHellmanReceiverPrivateKey());
        bobContent.txt_rootChainTop.getPopupProvider().setValue(ctx.receiverRootChainKey());
        bobContent.txt_rootChainConst.getPopupProvider().setValue(ctx.receiverRootConstantInput());
        bobContent.txt_rootChainMid.getPopupProvider().setValues(ctx.receiverRootOutput());
        bobContent.txt_rootChainBot.getPopupProvider().setValue(ctx.receiverRootNewRootChainKey());
        bobContent.txt_receivingChainTop.getPopupProvider().setValue(ctx.receiverChainChainKey());
        bobContent.txt_receivingChainConst.getPopupProvider().setValue(ctx.receiverChainConstantInput());
        bobContent.txt_receivingChainMid.getPopupProvider().setValue(ctx.receiverChainOutput());
        bobContent.txt_receivingChainBot.getPopupProvider().setValue(ctx.receiverChainNewChainKey());
        bobContent.txt_messageKeys.getPopupProvider().setValues(ctx.receiverChainMessageKey());
    }

    public static enum Direction {
        FORWARD {
            @Override
            DoubleRatchetStep peek(DoubleRatchetStep current) {
                return current.peekForward();
            }
        },
        BACKWARD {
            @Override
            DoubleRatchetStep peek(DoubleRatchetStep current) {
                return current.peekBackward();
            }
        };

        abstract DoubleRatchetStep peek(DoubleRatchetStep current);
    }

    private void switchViewTo(CommunicationEntity entity, DoubleRatchetView view) {
        if (entity == ALICE) {
            view.showAliceView();
        } else {
            view.showBobView();
        }
    }
}
