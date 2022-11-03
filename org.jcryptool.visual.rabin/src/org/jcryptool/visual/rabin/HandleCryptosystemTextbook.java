package org.jcryptool.visual.rabin;

import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.crypto.ui.textsource.TextInputWithSource;
import org.jcryptool.visual.rabin.ui.CryptosystemTextbookComposite;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;






public class HandleCryptosystemTextbook {
	
	
	private ArrayList<String> ciphertextList;
	private ArrayList<ArrayList<String>> plaintexts;
	private ArrayList<ArrayList<Boolean>> clickedPlaintexts; 
	private LinkedHashMap<String, String> currentSelectedPlaintexts;
	private int[] countClicksForPlaintexts; 
	private RabinFirstTabComposite rftc;
	
	public String oldTextselectorTextEncryptionMode;
	public boolean firstTextselectorAccessEncryptionMode = false;
	public String oldTextselectorTextDecryptionMode;
	public boolean firstTextselectorAccessDecryptionMode = false;
	
	private HandleFirstTab guiHandler;
	
	public int oldIdxChoosePadding;
	
	
	public HandleCryptosystemTextbook(RabinFirstTabComposite rftc) {
		this.rftc = rftc;
		this.guiHandler = rftc.guiHandler;
		countClicksForPlaintexts = new int[] {0, 0, 0, 0};
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
	}
	
	public void showControl(Control c) {
		guiHandler.showControl(c);
	}
	
	public void hideControl(Control c) {
		guiHandler.hideControl(c);
	}
	
	
	
	public void btnEncryptAction(CryptosystemTextbookComposite cstb) {
		if(cstb.textSelector.getText() == null || cstb.textSelector.getText().getText().isEmpty() || guiHandler.rabinFirst.getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on \"Load text...\" to load a plaintext you want to encrypt";
			cstb.txtEncryptionWarning.setText(strLoadTextWarning);
			showControl(cstb.txtEncryptionWarning);
			return;
		}

		hideControl(cstb.txtEncryptionWarning);
		
		
		
		String plaintext = cstb.textSelector.getText().getText();
		//int maxBytesPerBlock = guiHandler.bytesPerBlock;
		
		int maxBytesPerBlock = guiHandler.bytesPerBlock;
		int byteLenOfPlaintext = plaintext.length() * 2;
		
		
		if(byteLenOfPlaintext < maxBytesPerBlock)
			maxBytesPerBlock = byteLenOfPlaintext;
		
		
		int iblocklengthFinal = guiHandler.blocklength;
		if((byteLenOfPlaintext * 2) < iblocklengthFinal)
			iblocklengthFinal = byteLenOfPlaintext * 2;
		
		
		
		String plaintextHex = guiHandler.rabinFirst.bytesToString(plaintext.getBytes());
		String paddingScheme = cstb.cmbChooseBlockPadding.getItem(cstb.cmbChooseBlockPadding.getSelectionIndex());
		
		//String paddedPlaintextHex = guiHandler.rabinFirst.getPaddedPlaintext(plaintextHex, guiHandler.bytesPerBlock, paddingScheme);
		String paddedPlaintextHex = guiHandler.rabinFirst.getPaddedPlaintext(plaintextHex, maxBytesPerBlock, paddingScheme);
		ArrayList<String> plaintextsHex = guiHandler.rabinFirst.parseString(paddedPlaintextHex, maxBytesPerBlock);
		
		// test plaintext with prefix padding
		//ArrayList<String> prefixPaddedPlaintextblocks = guiHandler.rabinFirst.getPaddedPlaintextblocks(plaintextsHex, guiHandler.blocklength);
		ArrayList<String> prefixPaddedPlaintextblocks = guiHandler.rabinFirst.getPaddedPlaintextblocks(plaintextsHex, iblocklengthFinal);
		paddedPlaintextHex = guiHandler.rabinFirst.getArrayListToString(prefixPaddedPlaintextblocks);
		cstb.txtPlaintext.setText(paddedPlaintextHex);
		
		//ciphertextList = guiHandler.rabinFirst.getCiphertextblocksAsList(plaintext, paddingScheme, guiHandler.bytesPerBlock, guiHandler.blocklength, guiHandler.radix);
		ciphertextList = guiHandler.rabinFirst.getEncryptedListOfStrings(plaintextsHex, guiHandler.radix);
		ArrayList<String> paddedCiphertextsHex = guiHandler.rabinFirst.getPaddedCiphertextblocks(ciphertextList, iblocklengthFinal);
		ciphertextList = paddedCiphertextsHex;
		String ciphertext = guiHandler.rabinFirst.getArrayListToString(ciphertextList);
		//String ciphertext = guiHandler.rabinFirst.getArrayListToString(paddedCiphertextsHex);
		cstb.txtCiphertext.setText(ciphertext);
		//System.out.println(ciphertextList.size());
		cstb.btnEncrypt.setEnabled(false);
	}
	
	
	private void initializeClickedPlaintexts(int size, int sizePlaintexts) {
		clickedPlaintexts = new ArrayList<ArrayList<Boolean>>();
		for(int i = 0; i < size; i++) {
			ArrayList<Boolean> initState = new ArrayList<Boolean>();
			for(int j = 0; j < sizePlaintexts; j++) {
				initState.add(false);
			}
			clickedPlaintexts.add(initState);
		}
	}
	
		
	
	private void setChooseBlock(Combo cmbChooseBlock, int size) {
		cmbChooseBlock.removeAll();
		for(int i = 1; i <= size; i++) {
			cmbChooseBlock.add(String.valueOf(i));
		}
	}
	
	
	private void markCiphertext(StyledText ciphertext, int elem) {
		int startIdx = guiHandler.getStartIdx(elem, ciphertextList);
		int endIdx = guiHandler.getEndIdx(startIdx, elem, ciphertextList);
		ciphertext.setSelection(startIdx, endIdx);
	}
	
	
	private void setPossiblePlaintexts(int idx, Text txtFirstPlaintext, Text txtSecondPlaintext, 
			Text txtThirdPlaintext, Text txtFourthPlaintext) {
		ArrayList<String> allPossiblePlaintextsForIdx = plaintexts.get(idx);
		txtFirstPlaintext.setText(allPossiblePlaintextsForIdx.get(0));
		txtSecondPlaintext.setText(allPossiblePlaintextsForIdx.get(1));
		txtThirdPlaintext.setText(allPossiblePlaintextsForIdx.get(2));
		txtFourthPlaintext.setText(allPossiblePlaintextsForIdx.get(3));
	}
	
		
	
	public void btnDecryptInEncryptionModeAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
			
		guiHandler.hideControl(cstb.txtEncryptionWarning);
		
		cstb.btnRadioEncrypt.setSelection(false);
		cstb.btnRadioDecrypt.setSelection(true);
		
		
		plaintexts = guiHandler.rabinFirst.getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.radix);
		
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		String ciphertextblocksWithSeparator = guiHandler.rabinFirst.getStringWithSepForm(ciphertextList, guiHandler.separator);
		cstb.txtCiphertextSegments.setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cstb.cmbChooseBlock, plaintexts.size());
		cstb.cmbChooseBlock.select(0);
		
		
		int idx = cstb.cmbChooseBlock.getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.txtCiphertextSegments, elem);	
		
		setPossiblePlaintexts(idx, cstb.txtFirstPlaintext, cstb.txtSecondPlaintext, cstb.txtThirdPlaintext, cstb.txtFourthPlaintext);
		
		
		updateChosenPlaintexts(idx, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.txtChosenPlaintexts.setText("");
		
		cstb.txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_decrypt"));
		
		hideControl(cstb.compHoldEncryptionProcess);
		showControl(cstb.compHoldDecryptionProcess);
		hideControl(cstb.txtDecryptWarning);
		cstb.grpEncryptDecrypt.setText("Decryption");
				
	}
	
	
	private void updateChosenPlaintexts(int idx, CryptosystemTextbookComposite cstb, Color colorToSetBG, Color colorToResetBG, Color colorToSetFG, Color colorToResetFG) {
		ArrayList<Boolean> clickedPlaintextsOfIdx = clickedPlaintexts.get(idx);
		for(int i = 0; i < clickedPlaintextsOfIdx.size(); i++) {
			if(clickedPlaintextsOfIdx.get(i)) {
				countClicksForPlaintexts[i] = 1;
				setColorPlaintext(cstb, i, colorToSetBG, colorToSetFG);
			}
			else {
				countClicksForPlaintexts[i] = 0;
				setColorPlaintext(cstb, i, colorToResetBG, colorToResetFG);
			}
		}
	}
	

	
	public void btnNextBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = (cstb.cmbChooseBlock.getSelectionIndex() + 1)
				% ciphertextList.size();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.txtCiphertextSegments, elem);
		
		setPossiblePlaintexts(nextIdx, cstb.txtFirstPlaintext, cstb.txtSecondPlaintext, 
				cstb.txtThirdPlaintext, cstb.txtFourthPlaintext);
		
		cstb.cmbChooseBlock.select(nextIdx);
		
		
		// update chosen plainetxts and colors
		updateChosenPlaintexts(nextIdx, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	
	public void btnPrevBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = BigInteger.valueOf(
				cstb.cmbChooseBlock.getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								ciphertextList.size())).intValue();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.txtCiphertextSegments, elem);
		
		setPossiblePlaintexts(nextIdx, cstb.txtFirstPlaintext, cstb.txtSecondPlaintext, 
				cstb.txtThirdPlaintext, cstb.txtFourthPlaintext);
		
		cstb.cmbChooseBlock.select(nextIdx);
		
		
		updateChosenPlaintexts(nextIdx, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	public void cmbChooseBlockAction(CryptosystemTextbookComposite cstb) {
		int idx = cstb.cmbChooseBlock.getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.txtCiphertextSegments, elem);
		setPossiblePlaintexts(idx, cstb.txtFirstPlaintext, cstb.txtSecondPlaintext, 
				cstb.txtThirdPlaintext, cstb.txtFourthPlaintext);
	}
	
	
	
	public void fixSelection(StyledText stxt, Combo cmb) {
		int idx = cmb.getSelectionIndex();
		if(idx == -1)
			return;
		
		int elem = idx + 1;
		markCiphertext(stxt, elem);
	}
	
	
	
	public void btnDecryptionAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		if(cstb.textSelector.getText() == null || cstb.textSelector.getText().getText().isEmpty() || guiHandler.rabinFirst.getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on\n\"Load text...\" to load a ciphertext you want to decrypt";
			cstb.txtDecryptWarning.setText(strLoadTextWarning);
			showControl(cstb.txtDecryptWarning);
			return;
		}

		String pattern = "[0-9a-fA-F]+"; 
		String ciphertext = cstb.textSelector.getText().getText();
		ciphertext = ciphertext.replaceAll("\\s+", ""); 
		if(!ciphertext.matches(pattern)) {
			String strOnlyHexAllowed = Messages.HandleFirstTab_strOnlyHexAllowed;
			cstb.txtDecryptWarning.setText(strOnlyHexAllowed);
			showControl(cstb.txtDecryptWarning);
			if(!cstb.txtCiphertextSegments.getText().isEmpty()) {
				Display.getDefault().timerExec(3000, new Runnable() {
					
					@Override
					public void run() {
						hideControl(cstb.txtDecryptWarning);
					}
				});
				
			}
			
			return;
		}
		
		boolean checkLength = ciphertext.length() % guiHandler.blocklength == 0;
		
		if(!checkLength) {
			String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
			cstb.txtDecryptWarning.setText(MessageFormat.format(
					strLengthCipher,
					(guiHandler.blocklength / 2)));
			showControl(cstb.txtDecryptWarning);
			return;
		}
		
		hideControl(cstb.txtDecryptWarning);
		
		ciphertextList = guiHandler.rabinFirst.parseString(ciphertext, guiHandler.blocklength);
		try {
			plaintexts = guiHandler.rabinFirst.getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.radix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			cstb.txtEncryptionWarning.setText("Attention: the computation takes too much time. Please generate a greater N.");
			guiHandler.showControl(cstb.txtEncryptionWarning);

		}
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		String ciphertextblocksWithSeparator = guiHandler.rabinFirst.getStringWithSepForm(ciphertextList, guiHandler.separator);
		cstb.txtCiphertextSegments.setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cstb.cmbChooseBlock, plaintexts.size());
		cstb.cmbChooseBlock.select(0);
		
		
		int idx = cstb.cmbChooseBlock.getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.txtCiphertextSegments, elem);
			
		setPossiblePlaintexts(idx, cstb.txtFirstPlaintext, cstb.txtSecondPlaintext, 
				cstb.txtThirdPlaintext, cstb.txtFourthPlaintext);
		
		updateChosenPlaintexts(idx, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.txtChosenPlaintexts.setText("");
	}
		
	
	
	private void resetChosenPlaintexts(int idx, CryptosystemTextbookComposite cstb) {
		updateChosenPlaintexts(idx, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
		cstb.txtChosenPlaintexts.setText("");
	}
	
	
	
	private String getCurrentSelectedPlaintexts() {
		String ret = "";
		Set<String> keys = currentSelectedPlaintexts.keySet();
		for(String key : keys) {
			ret += currentSelectedPlaintexts.get(key);
		}
		return ret;
	}
	
	
	
	private void setColorPlaintext(CryptosystemTextbookComposite cstb, int idxToSet, Color colorBG, Color colorFG) {
		switch(idxToSet) {
			case 0:
				cstb.txtFirstPlaintext.setBackground(colorBG);
				cstb.txtFirstPlaintext.setForeground(colorFG);
				break;
			
			case 1:
				cstb.txtSecondPlaintext.setBackground(colorBG);
				cstb.txtSecondPlaintext.setForeground(colorFG);
				break;
			
			case 2:
				cstb.txtThirdPlaintext.setBackground(colorBG);
				cstb.txtThirdPlaintext.setForeground(colorFG);
				break;
				
			case 3:
				cstb.txtFourthPlaintext.setBackground(colorBG);
				cstb.txtFourthPlaintext.setForeground(colorFG);
				break;
			
			default:
				break;
		}
	}
	
	
	
	private boolean isOnePlaintextSelected(int idx) {
		ArrayList<Boolean> clickedPlaintextsAtIdx = clickedPlaintexts.get(idx);
		for(boolean b : clickedPlaintextsAtIdx) {
			if(b == true)
				return true;
		}
		
		return false;
	}
	
	
	
	
	
	private void txtPlaintextMouseDownAction(int idxToSet, CryptosystemTextbookComposite cstb, Color colorToSetBG, Color colorToResetBG, Color colorToSetFG, Color colorToResetFG) {
		
		int idx = cstb.cmbChooseBlock.getSelectionIndex();
		if(isOnePlaintextSelected(idx) && !clickedPlaintexts.get(idx).get(idxToSet))
			return;
		
		countClicksForPlaintexts[idxToSet] = (countClicksForPlaintexts[idxToSet] + 1) % 2;
		
		if(countClicksForPlaintexts[idxToSet] == 1) {
			clickedPlaintexts.get(idx).set(idxToSet, true);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			String val = plaintexts.get(idx).get(idxToSet);
			currentSelectedPlaintexts.put(key, val);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.txtChosenPlaintexts.setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToSetBG, colorToSetFG);
		}
		
		if(countClicksForPlaintexts[idxToSet] == 0) {
			clickedPlaintexts.get(idx).set(idxToSet, false);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			currentSelectedPlaintexts.remove(key);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.txtChosenPlaintexts.setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToResetBG, colorToResetFG);
		}
	}
	
	
	
	public void txtFirstPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 0;
		txtPlaintextMouseDownAction(idxToSet, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	public void txtSecondPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 1;
		txtPlaintextMouseDownAction(idxToSet, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	public void txtThirdPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 2;
		txtPlaintextMouseDownAction(idxToSet, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	
	public void txtFourthPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 3;
		txtPlaintextMouseDownAction(idxToSet, cstb, GUIHandler.colorSelectControlBG, GUIHandler.colorDeselectControlBG, GUIHandler.colorSelectControlFG, GUIHandler.colorDeselectControlFG);
	}
	
	
	
	public void btnWriteToJCTeditor(CryptosystemTextbookComposite cstb) throws PartInitException {
		String ciphertext = cstb.txtCiphertext.getText();
		int len = ciphertext.length();
		String ciphertextForFile= ""; //$NON-NLS-1$
		int lineLimit = 100;
		
		if(len < 100) {
			ciphertextForFile = ciphertext;
		}
		else {
		
			for(int i = 0; i < len; i++) {
				ciphertextForFile += ciphertext.charAt(i);
				if((i+1) % lineLimit == 0)
					ciphertextForFile += "\n"; //$NON-NLS-1$
			}
		}
		
		
		IEditorInput customEditorInput = AbstractEditorService.createOutputFile(ciphertextForFile);
		EditorsManager.getInstance().openNewTextEditor(customEditorInput);
	
	}
	
	
	
	public void btnWriteToJCTeditorDecryptMode(CryptosystemTextbookComposite cstb) throws PartInitException {
		String plaintext = cstb.txtChosenPlaintexts.getText();
		IEditorInput customEditorInput = AbstractEditorService.createOutputFile(plaintext);
		EditorsManager.getInstance().openNewTextEditor(customEditorInput);
	}
	
	
	
	
	public void txtCiphertextModifyAction(CryptosystemTextbookComposite cstb) {
		String ciphertext = cstb.txtCiphertext.getText();
		
		if(ciphertext.isEmpty()) {
			cstb.btnDecryptInEncryptionMode.setEnabled(false);
			cstb.btnWriteToJCTeditor.setEnabled(false);
			return;
		}
		
		cstb.btnDecryptInEncryptionMode.setEnabled(true);
		cstb.btnWriteToJCTeditor.setEnabled(true);
	}
	
	
	
	public void txtCiphertextSegmentsModifyAction(CryptosystemTextbookComposite cstb) {
		String ciphertext = cstb.txtCiphertextSegments.getText();
		
		if(ciphertext.isEmpty()) {
			cstb.btnNextBlock.setEnabled(false);
			cstb.btnPrevBlock.setEnabled(false);
			return;
		}
		
		cstb.btnNextBlock.setEnabled(true);
		cstb.btnPrevBlock.setEnabled(true);
	}
	
	
	
	
	public void btnResetChosenPlaintextsAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		
		for(int i = 0; i < clickedPlaintexts.size(); i++) {
			resetChosenPlaintexts(i, cstb);
		}
	}
	
		
	public void txtChosenPlaintextsModifyAction(CryptosystemTextbookComposite cstb) {
		String chosenPlaintexts = cstb.txtChosenPlaintexts.getText();
		
		if(chosenPlaintexts.isEmpty()) {
			cstb.btnResetChosenPlaintexts.setEnabled(false);
			cstb.btnWriteToJCTeditorDecryptMode.setEnabled(false);
			return;
		}
		
		cstb.btnResetChosenPlaintexts.setEnabled(true);
		cstb.btnWriteToJCTeditorDecryptMode.setEnabled(true);
	}
	
	
	
	public void btnRadioEncryptAction(CryptosystemTextbookComposite cstb) {
		hideControl(cstb.compHoldDecryptionProcess);
		showControl(cstb.compHoldEncryptionProcess);
		cstb.grpEncryptDecrypt.setText("Encryption");
		cstb.txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_encrypt"));
		cstb.grpEncryptDecrypt.setText("5 \u2212 Encryption");
		
		
		/*if(cstb.textSelector.getText() != null) {
			this.hideControl(cstb.txtLoadTextWarning);
			
			String newTextselectorText = null;
			
			// maybe uncomment again, atm its just testing
			//if(firstTextselectorAccessEncryptionMode) {
				//oldTextselectorTextEncryptionMode = cstb.textSelector.getText().getText();
				//firstTextselectorAccessEncryptionMode = true;
				//return;
			//}
			
			newTextselectorText = cstb.textSelector.getText().getText();
			if(newTextselectorText != oldTextselectorTextEncryptionMode) {
				cstb.txtCiphertext.setText("");
			}
			
			
			
			if(!cstb.rftc.txtModN.getText().isEmpty() && !cstb.textSelector.getText().getText().isEmpty()) {
				if(newTextselectorText != oldTextselectorTextEncryptionMode) {
					cstb.btnEncrypt.setEnabled(true);
				}
				else {
					if(cstb.txtCiphertext.getText().isEmpty())
						cstb.btnEncrypt.setEnabled(true);
					else {
						cstb.btnEncrypt.setEnabled(false);
					}
				}
			}
			else {
				cstb.btnEncrypt.setEnabled(false);
				
				if(cstb.textSelector.getText().getText().isEmpty()) {
					cstb.txtLoadTextWarning.setText("Attention: load a non-empty plaintext");
					this.showControl(cstb.txtLoadTextWarning);
				}
			}
		}*/
		
		
		
			
			if(cstb.textSelector.getText() != null) {
				this.hideControl(cstb.txtLoadTextWarning);
				
				String newTextselectorText = null;
				
				// maybe uncomment again, atm its just testing
				/*if(firstTextselectorAccessEncryptionMode) {
					oldTextselectorTextEncryptionMode = cstb.textSelector.getText().getText();
					firstTextselectorAccessEncryptionMode = true;
					return;
				}*/
				
				newTextselectorText = cstb.textSelector.getText().getText();
				if(newTextselectorText != oldTextselectorTextEncryptionMode) {
					cstb.txtCiphertext.setText("");
					cstb.txtPlaintext.setText("");
				}
				
				
				
				if(!cstb.rftc.txtModN.getText().isEmpty() && !cstb.textSelector.getText().getText().isEmpty()) {
					if(newTextselectorText != oldTextselectorTextEncryptionMode) {
						cstb.btnEncrypt.setEnabled(true);
					}
					else {
						if(cstb.txtCiphertext.getText().isEmpty())
							cstb.btnEncrypt.setEnabled(true);
						else {
							cstb.btnEncrypt.setEnabled(false);
						}
					}
				}
				else {
					cstb.btnEncrypt.setEnabled(false);
					
					if(cstb.textSelector.getText().getText().isEmpty()) {
						cstb.txtLoadTextWarning.setText("Attention: load a non-empty plaintext");
						this.showControl(cstb.txtLoadTextWarning);
					}
				}
				
			}
		
		
		
		/*if(!cstb.rftc.txtModN.getText().isEmpty() && cstb.textSelector.getText() != null && !cstb.textSelector.getText().getText().isEmpty()) {
			String newTextselectorText = cstb.textSelector.getText().getText();
			if(newTextselectorText != oldTextselectorTextEncryptionMode) {
				cstb.btnEncrypt.setEnabled(true);
			}
			else {
				if(cstb.txtCiphertext.getText().isEmpty())
					cstb.btnEncrypt.setEnabled(true);
				else
					cstb.btnEncrypt.setEnabled(false);
			}
		}
		else {
			cstb.btnEncrypt.setEnabled(false);
		}*/
	}
	
	
	
	public void btnRadioDecryptAction(CryptosystemTextbookComposite cstb) {
		hideControl(cstb.compHoldEncryptionProcess);
		showControl(cstb.compHoldDecryptionProcess);
		cstb.grpEncryptDecrypt.setText("Decryption");
		cstb.txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_decrypt"));
		cstb.grpEncryptDecrypt.setText("5 \u2212 Decryption");
		
		
			
		if(cstb.textSelector.getText() != null) {
			this.hideControl(cstb.txtLoadTextWarning);
			
			if(cstb.txtCiphertextSegments.getText().isEmpty()) {
				if(cstb.rftc.txtModN.getText().isEmpty()) {
					cstb.txtLoadTextWarning.setText("Attention: generate a key pair first");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				if(cstb.textSelector.getText().getText().isEmpty()) {
					cstb.txtLoadTextWarning.setText("Attention: load a non-empty ciphertext");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				String pattern = "[0-9a-fA-F]+"; 
				if(!cstb.textSelector.getText().getText().matches(pattern)) {
					cstb.txtLoadTextWarning.setText("Attention: only hexadecimal numbers (0-f) are allowed");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				boolean checkLength = cstb.textSelector.getText().getText().length() % guiHandler.blocklength == 0;
				
				if(!checkLength) {
					String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
					cstb.txtLoadTextWarning.setText(MessageFormat.format(
							strLengthCipher,
							(guiHandler.blocklength / 2)));
					showControl(cstb.txtLoadTextWarning);
					return;
				}
				
			}
			else
				return;
			
			String newTextselectorText = null;
			/*if(firstTextselectorAccessDecryptionMode) {
				oldTextselectorTextDecryptionMode = cstb.textSelector.getText().getText();
				firstTextselectorAccessDecryptionMode = true;
				return;
			}*/
			
			newTextselectorText = cstb.textSelector.getText().getText();
			if(newTextselectorText != oldTextselectorTextDecryptionMode) {
				cstb.txtCiphertextSegments.setText("");
				cstb.cmbChooseBlock.removeAll();
				cstb.txtChosenPlaintexts.setText("");
				cstb.txtFirstPlaintext.setText("");
				cstb.txtSecondPlaintext.setText("");
				cstb.txtThirdPlaintext.setText("");
				cstb.txtFourthPlaintext.setText("");
			}
			
			
			
			if(!cstb.rftc.txtModN.getText().isEmpty() && !cstb.textSelector.getText().getText().isEmpty()) {
				if(newTextselectorText != oldTextselectorTextDecryptionMode) {
					cstb.btnDecryption.setEnabled(true);
				}
				else {
					if(cstb.txtCiphertextSegments.getText().isEmpty())
						cstb.btnDecryption.setEnabled(true);
					else
						cstb.btnDecryption.setEnabled(false);
				}
			}
			else {
				cstb.btnDecryption.setEnabled(false);
			}
		}
		
		
		/*if(cstb.btnRadioDecrypt.getSelection()) {
			cstb.txtCiphertextSegments.setText("");
			if(!cstb.rftc.txtModN.getText().isEmpty() && cstb.textSelector.getText() != null && !cstb.textSelector.getText().getText().isEmpty()) {
				String pattern = "[0-9a-fA-F]+";
				
				if(!cstb.textSelector.getText().getText().matches(pattern)) {
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				String newTextselectorText = cstb.textSelector.getText().getText();
				if(newTextselectorText != oldTextselectorTextDecryptionMode) {
					cstb.btnDecryption.setEnabled(true);
				}
				else {
					if(cstb.txtCiphertextSegments.getText().isEmpty())
						cstb.btnDecryption.setEnabled(true);
					else
						cstb.btnDecryption.setEnabled(false);
				}
			}
			else {
				cstb.btnDecryption.setEnabled(false);
			}
				
		}*/
	}
	
	
	public void textSelectorAction(CryptosystemTextbookComposite cstb) {
		if(cstb.btnRadioEncrypt.getSelection()) {
			
			if(cstb.textSelector.getText() != null) {
				this.hideControl(cstb.txtLoadTextWarning);
				
				String newTextselectorText = null;
				
				// maybe uncomment again, atm its just testing
				/*if(firstTextselectorAccessEncryptionMode) {
					oldTextselectorTextEncryptionMode = cstb.textSelector.getText().getText();
					firstTextselectorAccessEncryptionMode = true;
					return;
				}*/
				
				newTextselectorText = cstb.textSelector.getText().getText();
				if(newTextselectorText != oldTextselectorTextEncryptionMode) {
					cstb.txtCiphertext.setText("");
					cstb.txtPlaintext.setText("");
				}
				
				
				
				if(!cstb.rftc.txtModN.getText().isEmpty() && !cstb.textSelector.getText().getText().isEmpty()) {
					cstb.cmbChooseBlockPadding.setEnabled(true);
					if(newTextselectorText != oldTextselectorTextEncryptionMode) {
						cstb.btnEncrypt.setEnabled(true);
					}
					else {
						if(cstb.txtCiphertext.getText().isEmpty())
							cstb.btnEncrypt.setEnabled(true);
						else {
							cstb.btnEncrypt.setEnabled(false);
						}
					}
				}
				else {
					cstb.cmbChooseBlockPadding.setEnabled(false);
					cstb.btnEncrypt.setEnabled(false);
					
					if(cstb.textSelector.getText().getText().isEmpty()) {
						cstb.txtLoadTextWarning.setText("Attention: load a non-empty plaintext");
						this.showControl(cstb.txtLoadTextWarning);
					}
				}
				
				oldTextselectorTextEncryptionMode = newTextselectorText;
			}
		}
		
		if(cstb.btnRadioDecrypt.getSelection()) {
			
			if(cstb.textSelector.getText() != null) {
				this.hideControl(cstb.txtLoadTextWarning);
				
				if(cstb.rftc.txtModN.getText().isEmpty()) {
					cstb.txtLoadTextWarning.setText("Attention: generate a key pair first");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				if(cstb.textSelector.getText().getText().isEmpty()) {
					cstb.txtLoadTextWarning.setText("Attention: load a non-empty ciphertext");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					cstb.txtCiphertextSegments.setText("");
					cstb.cmbChooseBlock.removeAll();
					cstb.txtChosenPlaintexts.setText("");
					cstb.txtFirstPlaintext.setText("");
					cstb.txtSecondPlaintext.setText("");
					cstb.txtThirdPlaintext.setText("");
					cstb.txtFourthPlaintext.setText("");
					return;
				}
				
				String pattern = "[0-9a-fA-F]+"; 
				if(!cstb.textSelector.getText().getText().matches(pattern)) {
					cstb.txtLoadTextWarning.setText("Attention: only hexadecimal numbers (0-f) are allowed");
					this.showControl(cstb.txtLoadTextWarning);
					cstb.btnDecryption.setEnabled(false);
					return;
				}
				
				boolean checkLength = cstb.textSelector.getText().getText().length() % guiHandler.blocklength == 0;
				
				if(!checkLength) {
					String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
					cstb.txtLoadTextWarning.setText(MessageFormat.format(
							strLengthCipher,
							(guiHandler.blocklength / 2)));
					showControl(cstb.txtLoadTextWarning);
					return;
				}
				
				String newTextselectorText = null;
				/*if(firstTextselectorAccessDecryptionMode) {
					oldTextselectorTextDecryptionMode = cstb.textSelector.getText().getText();
					firstTextselectorAccessDecryptionMode = true;
					return;
				}*/
				
				newTextselectorText = cstb.textSelector.getText().getText();
				if(newTextselectorText != oldTextselectorTextDecryptionMode) {
					cstb.txtCiphertextSegments.setText("");
					cstb.cmbChooseBlock.removeAll();
					cstb.txtChosenPlaintexts.setText("");
					cstb.txtFirstPlaintext.setText("");
					cstb.txtSecondPlaintext.setText("");
					cstb.txtThirdPlaintext.setText("");
					cstb.txtFourthPlaintext.setText("");
				}
				
				
				
				if(!cstb.rftc.txtModN.getText().isEmpty() && !cstb.textSelector.getText().getText().isEmpty()) {
					if(newTextselectorText != oldTextselectorTextDecryptionMode) {
						cstb.btnDecryption.setEnabled(true);
					}
					else {
						if(cstb.txtCiphertextSegments.getText().isEmpty())
							cstb.btnDecryption.setEnabled(true);
						else
							cstb.btnDecryption.setEnabled(false);
					}
				}
				else {
					cstb.btnDecryption.setEnabled(false);
				}
				
				oldTextselectorTextDecryptionMode = newTextselectorText;
			}
		}
	}
	
	
	public void cmbChooseBlockPaddingAction(CryptosystemTextbookComposite cstb) {
		int idx = cstb.cmbChooseBlockPadding.getSelectionIndex();
		if(idx != oldIdxChoosePadding) {
			cstb.txtPlaintext.setText("");
			cstb.txtCiphertext.setText("");
			cstb.btnEncrypt.setEnabled(true);
		}
		oldIdxChoosePadding = idx;
	}
	

}
