package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.ALICE;
import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.BOB;

import org.eclipse.swt.SWT;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.visual.signalencryption.SignalEncryptionPlugin;
import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetBobSendingLogic.BobSendingStep;
import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
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
		STEP_0 {
			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				swtParent.showAliceView();
				updateSenderKeyDisplayInformation(swtParent);
				var bobContent = swtParent.getBobReceivingContent();
				var aliceContent = swtParent.getAliceSendingContent();
				// Hide these Elements
				swtParent.grp_bobAlgorithm.setVisible(false);
				swtParent.grp_aliceAlgorithm.setVisible(false);
				aliceContent.setAllVisible(false);
				bobContent.setAllVisible(false);

				// Hide Steps
				aliceContent.txt_aliceSendingStep1.setVisible(false);
				aliceContent.txt_aliceSendingStep2.setVisible(false);
				aliceContent.txt_aliceSendingStep3.setVisible(false);
				aliceContent.txt_aliceSendingStep4.setVisible(false);
				aliceContent.txt_aliceSendingStep5.setVisible(false);
				bobContent.txt_bobReceivingStep5.setVisible(false);
				bobContent.txt_bobReceivingStep6.setVisible(false);
				bobContent.txt_bobReceivingStep7.setVisible(false);
				bobContent.txt_bobReceivingStep8.setVisible(false);
				bobContent.txt_bobReceivingStep9.setVisible(false);

				// Initial value only valid for initial message
				if (AlgorithmState.get().getCommunication().isBeginning()) {
					aliceContent.txt_aliceSendingStep0.setText(Messages.SignalEncryption_aliceDescriptionText0);
				} else {
					aliceContent.txt_aliceSendingStep0.setText("Alice sendet eine Nachricht an Bob");
				}
			}

			@Override
			public DoubleRatchetStep next(DoubleRatchetView swtParent) {
				STEP_1.switchState(swtParent);
				return STEP_1;
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

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Show Diffie-Hellman calculation.
		 */
		STEP_1 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();
				// On this transition, update all key details as well
				updateSenderKeyDisplayInformation(swtParent);

				// Show these elements
				swtParent.grp_aliceAlgorithm.setVisible(true);
				aliceContent.setDiffieHellmanRatchetVisible(true);
				aliceContent.txt_aliceSendingStep1.setVisible(true);

				// Hide these Elements
				aliceContent.setRootChainVisible(false);
				aliceContent.txt_aliceSendingStep2.setVisible(false);

				// Pick up user input text
				aliceContent.txt_cipherText.setText(AlgorithmState.get().getAliceEncryptedMessage());
				
				// Notify the UI that the user has already progressed and viewed values in this tab
				AlgorithmState.get().getCommunication().setInProgress();
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

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_2;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_0;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Show the root chain calculation.
		 */
		STEP_2 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();

				// Show these labels
				aliceContent.setRootChainVisible(true);
				aliceContent.txt_aliceSendingStep2.setVisible(true);

				// Hide these Elements
				aliceContent.setSendingChainVisible(false);
				aliceContent.txt_aliceSendingStep3.setVisible(false);
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

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_3;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_1;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Show the sending chain calculation.
		 */
		STEP_3 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();
				var bobContent = swtParent.getBobReceivingContent();

				// Show these labels
				aliceContent.setSendingChainVisible(true);
				aliceContent.txt_aliceSendingStep3.setVisible(true);

				// Hide these Elements
				aliceContent.setMessageBoxVisible(false);
				aliceContent.txt_aliceSendingStep4.setVisible(false);

				bobContent.grp_receivingChain.setVisible(false);

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

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_4;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_2;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Show the message input box. Set previously entered text if entered. The
		 * <b>message encryption</b> happens when going forward from here. If already
		 * encrypted, the message box may not be changed anymore.
		 */
		STEP_4 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();
				var bobContent = swtParent.getBobReceivingContent();

				// Show these labels
				aliceContent.showOnlyMessagePlaintext();
				aliceContent.txt_aliceSendingStep4.setVisible(true);

				// Hide these Elements
				swtParent.grp_bobAlgorithm.setVisible(false);
				aliceContent.txt_aliceSendingStep5.setVisible(false);
				bobContent.setEncryptedMessageBoxVisible(false);
				bobContent.txt_bobReceivingStep5.setVisible(false);

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
					encryptMessage(swtParent);
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

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Showing the final, encrypted message and "send" it to Bob. Showing Alice's
		 * view (important if going backwards).
		 */
		STEP_5_SENDING {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();
				var bobContent = swtParent.getBobReceivingContent();
				// Show these elements
				swtParent.grp_bobAlgorithm.setVisible(true);
				aliceContent.setMessageBoxVisible(true);
				bobContent.setEncryptedMessageBoxVisible(true);
				aliceContent.txt_aliceSendingStep5.setVisible(true);
				bobContent.txt_bobReceivingStep5.setVisible(true);

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

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return ALICE;
			}
		},
		/**
		 * Showing the final, encrypted message and "send" it to Bob. Showing Alice's
		 * view (important if going backwards).
		 */
		STEP_5_RECEIVING {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var aliceContent = swtParent.getAliceSendingContent();
				var bobContent = swtParent.getBobReceivingContent();
				// Show these elements
				swtParent.grp_bobAlgorithm.setVisible(true);
				aliceContent.setMessageBoxVisible(true);
				bobContent.setEncryptedMessageBoxVisible(true);
				aliceContent.txt_aliceSendingStep5.setVisible(true);
				bobContent.txt_bobReceivingStep5.setVisible(true);

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
			public DoubleRatchetStep next(DoubleRatchetView swtParent) {
				STEP_6.switchState(swtParent);
				return STEP_6;
			}

			@Override
			public DoubleRatchetStep back(DoubleRatchetView swtParent) {
				STEP_5_SENDING.switchState(swtParent);
				return STEP_5_SENDING;
			}

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_6;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_5_SENDING;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return BOB;
			}
		},

		/**
		 * Switch to Bob's view and "receive" the encrypted message. Show the encrypted
		 * message text box and the Diffie-Hellman calculation.
		 */
		STEP_6 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var bobContent = swtParent.getBobReceivingContent();

				// Show these labels
				bobContent.setDiffieHellmanRatchetVisible(true);
				bobContent.txt_bobReceivingStep6.setVisible(true);

				// Hide these Elements
				bobContent.setRootChainVisible(false);
				bobContent.txt_bobReceivingStep7.setVisible(false);

				// Initial value only valid for initial message
				if (AlgorithmState.get().getCommunication().isBeginning()) {
					bobContent.txt_bobReceivingStep0.setText(Messages.SignalEncryption_bobDescriptionText0);
				} else {
					bobContent.txt_bobReceivingStep0.setText("Bob wartet auf eine Nachricht von Alice");
				}
			}

			@Override
			public DoubleRatchetStep next(DoubleRatchetView swtParent) {
				STEP_7.switchState(swtParent);
				return STEP_7;
			}

			@Override
			public DoubleRatchetStep back(DoubleRatchetView swtParent) {
				STEP_5_RECEIVING.switchState(swtParent);
				return STEP_5_RECEIVING;
			}

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_7;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_5_RECEIVING;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return BOB;
			}
		},
		/**
		 * Show the root chain calculation.
		 */
		STEP_7 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var bobContent = swtParent.getBobReceivingContent();

				// Show these labels
				bobContent.setRootChainVisible(true);
				bobContent.txt_bobReceivingStep7.setVisible(true);

				// Hide these Elements
				bobContent.setReceivingChainVisible(false);
				bobContent.txt_bobReceivingStep8.setVisible(false);

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

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_8;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_6;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return BOB;
			}
		},
		/**
		 * Show the receiving chain calculation.
		 */
		STEP_8 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var bobContent = swtParent.getBobReceivingContent();

				// Show these labels
				bobContent.setReceivingChainVisible(true);
				bobContent.txt_bobReceivingStep8.setVisible(true);

				// Hide these Elements
				bobContent.setDecryptedMessageboxVisible(false);
				bobContent.txt_bobReceivingStep9.setVisible(false);
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

			@Override
			public DoubleRatchetStep peekForward() {
				return STEP_9;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_7;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return BOB;
			}
		},
		/**
		 * Show the decrypted message.
		 */
		STEP_9 {

			@Override
			protected void switchState(DoubleRatchetView swtParent) {
				var bobContent = swtParent.getBobReceivingContent();
				var aliceContent = swtParent.getAliceSendingContent();
				// Show these Elements
				swtParent.grp_bobAlgorithm.setVisible(true);
				swtParent.grp_aliceAlgorithm.setVisible(true);
				aliceContent.setAllVisible(true);
				bobContent.setAllVisible(true);

				// Show Steps
				aliceContent.txt_aliceSendingStep1.setVisible(true);
				aliceContent.txt_aliceSendingStep2.setVisible(true);
				aliceContent.txt_aliceSendingStep3.setVisible(true);
				aliceContent.txt_aliceSendingStep4.setVisible(true);
				aliceContent.txt_aliceSendingStep5.setVisible(true);
				bobContent.txt_bobReceivingStep5.setVisible(true);
				bobContent.txt_bobReceivingStep6.setVisible(true);
				bobContent.txt_bobReceivingStep7.setVisible(true);
				bobContent.txt_bobReceivingStep8.setVisible(true);
				bobContent.txt_bobReceivingStep9.setVisible(true);

				// If going back to initial message, this may be required to update
				if (AlgorithmState.get().getCommunication().isBeginning()) {
					bobContent.txt_bobReceivingStep0.setText(Messages.SignalEncryption_bobDescriptionText0);
					aliceContent.txt_aliceSendingStep0.setText(Messages.SignalEncryption_aliceDescriptionText0);
				}
				// Show these labels
				bobContent.txt_plainText.setVisible(true);
				bobContent.txt_bobReceivingStep9.setVisible(true);
				var decryptedMessage = AlgorithmState.get().getCommunication().current().getDecryptedMessage();
				if (decryptedMessage.isPresent()) {
					bobContent.txt_plainText.setText(decryptedMessage.get());
				} else {
					LogUtil.logError(new SignalAlgorithmException(), true);
				}
				// On this transition, update all key steps as well
				updateSenderKeyDisplayInformation(swtParent);
				updateReceivingKeyDisplayInformation(swtParent);
			}

			@Override
			public DoubleRatchetStep next(DoubleRatchetView swtParent) {
				swtParent.switchSenderReceiver();
				AlgorithmState.get().getCommunication().next();
				BobSendingStep.STEP_0.switchState(swtParent);
				return BobSendingStep.STEP_0;
			}

			@Override
			public DoubleRatchetStep back(DoubleRatchetView swtParent) {
				STEP_8.switchState(swtParent);
				return STEP_8;
			}

			@Override
			public DoubleRatchetStep peekForward() {
				return BobSendingStep.STEP_0;
			}

			@Override
			public DoubleRatchetStep peekBackward() {
				return STEP_8;
			}

			@Override
			public CommunicationEntity shouldShowThisEntity() {
				return BOB;
			}
		};

		protected abstract void switchState(DoubleRatchetView swtParent);

		public AliceSendingStep setInitialState(DoubleRatchetView swtParent) {
			STEP_0.switchState(swtParent);
			return STEP_0;
		}
	}

	public void stepForward(DoubleRatchetView swtParent) {
		if (correctPerspectiveForward(swtParent)) {
			currentStep = currentStep.next(swtParent);
		}
	}

	public void stepBack(DoubleRatchetView swtParent) {
		 if (correctPerspectiveBackward(swtParent)) {
			currentStep = currentStep.back(swtParent);
		}
	}

	public void reset(DoubleRatchetView swtParent) {
		currentStep = AliceSendingStep.STEP_0.setInitialState(swtParent);
	}

	public DoubleRatchetStep getCurrentStep() {
		return currentStep;
	}
	
	private boolean showingWrongView(DoubleRatchetView swtParent, Direction direction) {
		if (direction == Direction.FORWARD) {
			return (swtParent.isShowingAlice() && currentStep.peekForward().shouldShowThisEntity() == BOB)
					|| (swtParent.isShowingBob() && currentStep.peekForward().shouldShowThisEntity() == ALICE);
		} else {
			return (swtParent.isShowingAlice() && currentStep.peekBackward().shouldShowThisEntity() == BOB)
					|| (swtParent.isShowingBob() && currentStep.peekBackward().shouldShowThisEntity() == ALICE);
		}
	}
	
	/** Returns true if instead of a forward/backwards operation a switch view operation is required */
	private boolean correctPerspectiveForward(DoubleRatchetView swtParent) {
		if (swtParent.isShowingAlice() && currentStep.peekForward().shouldShowThisEntity() == BOB) {
			swtParent.showBobView();
			if (currentStep == AliceSendingStep.STEP_5_SENDING) {
				return true;
			}
			return false;
		} else if (swtParent.isShowingBob() && currentStep.peekForward().shouldShowThisEntity() == ALICE) {
			swtParent.showAliceView();
			if (currentStep == BobSendingStep.STEP_5_SENDING) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	/** Returns true if instead of a forward/backwards operation a switch view operation is required */
	private boolean correctPerspectiveBackward(DoubleRatchetView swtParent) {
		if (swtParent.isShowingAlice() && currentStep.peekBackward().shouldShowThisEntity() == BOB) {
			swtParent.showBobView();
			if (currentStep == BobSendingStep.STEP_5_RECEIVING) {
				return true;
			}
			return false;
		} else if (swtParent.isShowingBob() && currentStep.peekBackward().shouldShowThisEntity() == ALICE) {
			swtParent.showAliceView();
			if (currentStep == AliceSendingStep.STEP_5_RECEIVING) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Get user input from UI and give it to the encryption algorithm.
	 */
	private static void encryptMessage(DoubleRatchetView view) {
		var message = view.getAliceSendingContent().txt_plainText.getText();
		var communication = AlgorithmState.get().getCommunication();
		try {
			communication.encrypt(message);
		} catch (SignalAlgorithmException e) {
			LogUtil.logError(SignalEncryptionPlugin.PLUGIN_ID, "Sorry, that shouldn't have happened, must restart", e,
					true);
			view.resetAll();
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

	public void checkIfViewChangeRequiresStateChange(DoubleRatchetView swtParent) {
		if (swtParent.isShowingBob() && getCurrentStep() == AliceSendingStep.STEP_5_SENDING) {
			stepForward(swtParent);
		} else if (swtParent.isShowingAlice() && getCurrentStep() == AliceSendingStep.STEP_5_RECEIVING) {
			stepBack(swtParent);
		}
	}
	
	private static enum Direction {
		FORWARD, BACKWARD;
	}
}
