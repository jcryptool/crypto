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
import org.jcryptool.visual.rabin.ui.CryptosystemTextbookComposite;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;






public class HandleCryptosystemTextbook {
	
	
	private ArrayList<String> ciphertextList;
	private ArrayList<ArrayList<String>> plaintexts;
	private ArrayList<ArrayList<Boolean>> clickedPlaintexts; 
	//private ArrayList<Boolean> 
	
	//private boolean[] clickedPlaintexts;
	
	private LinkedHashMap<String, String> currentSelectedPlaintexts;
	private int[] countClicksForPlaintexts; 
	private RabinFirstTabComposite rftc;
	
	
	
	
	//private GUIHandler guiHandler;
	private HandleFirstTab guiHandler;
	
	public HandleCryptosystemTextbook(RabinFirstTabComposite rftc) {
		this.rftc = rftc;
		this.guiHandler = rftc.getGUIHandler();
		countClicksForPlaintexts = new int[] {0, 0, 0, 0};
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
	}
	
	public void showControl(Control c) {
		guiHandler.showControl(c);
	}
	
	public void hideControl(Control c) {
		guiHandler.hideControl(c);
	}
	
	
	/*private ArrayList<Boolean> getClickedPlaintextsState(int idx){
		ArrayList<Boolean> clickedPlaintextsState = new ArrayList<Boolean>();
		int counter = 4;
		int startIdx = idx * counter; 
				
		//startIdx = (int) Math.pow(2, idx + 1);
		
		int endIdx = startIdx + counter;
		
		for(int i = startIdx; i < endIdx; i++) {
			clickedPlaintextsState.add(clickedPlaintexts[i]);
		}
		
		return clickedPlaintextsState;
	}*/
	
	
	
	/*private void setClickedPlaintextsAtIdx(int idx1, int idx2, boolean val) {
		int startIdx = idx1 * 4;
		
		//if(idx1 == 0)
			//startIdx = 0;
		//else
			//startIdx = (int) Math.pow(2, idx1 + 1);
		
		int totalIdx = startIdx + idx2;
		
		clickedPlaintexts[totalIdx] = val;
	}*/
	
	
	
	public void btnEncryptAction(TextLoadController textSelector, Text txtToEncrypt, Text txtEncryptWarning) {
		if(textSelector.getText() == null || textSelector.getText().getText().isEmpty() || guiHandler.getRabinFirst().getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on \"Load text...\" to load a plaintext you want to encrypt";
			txtEncryptWarning.setText(strLoadTextWarning);
			showControl(txtEncryptWarning);
			return;
		}

		hideControl(txtEncryptWarning);
		
		String plaintext = textSelector.getText().getText();
		ciphertextList = guiHandler.getRabinFirst().getCiphertextblocksAsList(plaintext, guiHandler.getBytesPerBlock(), guiHandler.getBlocklength(), guiHandler.getRadix());
		String ciphertext = guiHandler.getRabinFirst().getArrayListToString(ciphertextList);
		txtToEncrypt.setText(ciphertext);
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
	
	
	/*private void initializeClickedPlaintexts(int size) {
		clickedPlaintexts = new boolean[size];
		for(int i = 0; i < size; i++) {
			clickedPlaintexts[i] = false;
		}
	}*/
	
	
	
	
	private void setChooseBlock(Combo cmbChooseBlock, int size) {
		cmbChooseBlock.removeAll();
		for(int i = 1; i <= size; i++) {
			cmbChooseBlock.add(String.valueOf(i));
		}
	}
	
	
	private void markCiphertext(StyledText ciphertext, int elem, ArrayList<String> list) {
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
	
	
	/*private void setPossiblePlaintexts(int idx, Text txtFirstPlaintext, Text txtSecondPlaintext, 
			Text txtThirdPlaintext, Text txtFourthPlaintext) {
		String ciphertextblockAtIdx = ciphertextList.get(idx);
		BigInteger biCiphertextblockAtIdx = new BigInteger(ciphertextblockAtIdx, 16);
		
		ArrayList<String> allPossiblePlaintextsForIdx = guiHandler.getRabinFirst().getPossiblePlaintextsEncoded(biCiphertextblockAtIdx);
		
		txtFirstPlaintext.setText(allPossiblePlaintextsForIdx.get(0));
		txtSecondPlaintext.setText(allPossiblePlaintextsForIdx.get(1));
		txtThirdPlaintext.setText(allPossiblePlaintextsForIdx.get(2));
		txtFourthPlaintext.setText(allPossiblePlaintextsForIdx.get(3));
	}*/
	
	
	
	
	public void btnDecryptInEncryptionModeAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize){
		
		
		cstb.getBtnRadioEncrypt().setSelection(false);
		cstb.getBtnRadioDecrypt().setSelection(true);
		
		plaintexts = guiHandler.getRabinFirst().getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.getRadix());
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		String ciphertextblocksWithSeparator = guiHandler.getRabinFirst().getStringWithSepForm(ciphertextList, guiHandler.getSeparator());
		cstb.getTxtCiphertextSegments().setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cstb.getCmbChooseBlock(), plaintexts.size());
		cstb.getCmbChooseBlock().select(0);
		
		
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		//int startIdx = guiHandler.getStartIdx(elem, ciphertextList);
		//int endIdx = guiHandler.getEndIdx(startIdx, elem, ciphertextList);
		//txtCiphertextSegments.setSelection(startIdx, endIdx);
		
		
		setPossiblePlaintexts(idx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		//resetChosenPlaintexts(idx, cstb);
		
		updateChosenPlaintexts(idx, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.getTxtChosenPlaintexts().setText("");
		
		cstb.getTxtInfoEncryptionDecryption().setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_decrypt"));
		
		hideControl(cstb.getCompHoldEncryptionProcess());
		showControl(cstb.getCompHoldDecryptionProcess());
		hideControl(cstb.getDecryptWarning());
		cstb.getGrpEncryptDecrypt().setText("Decryption");
		
	}
	
	
	
	
	/*public void btnDecryptInEncryptionModeAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize){
		
		
		cstb.getBtnRadioEncrypt().setSelection(false);
		cstb.getBtnRadioDecrypt().setSelection(true);
		
		//plaintexts = guiHandler.getRabinFirst().getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.getRadix());
		initializeClickedPlaintexts(ciphertextList.size() * 4);
		String ciphertextblocksWithSeparator = guiHandler.getRabinFirst().getStringWithSepForm(ciphertextList, guiHandler.getSeparator());
		cstb.getTxtCiphertextSegments().setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cstb.getCmbChooseBlock(), ciphertextList.size());
		cstb.getCmbChooseBlock().select(0);
		
		
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		//int startIdx = guiHandler.getStartIdx(elem, ciphertextList);
		//int endIdx = guiHandler.getEndIdx(startIdx, elem, ciphertextList);
		//txtCiphertextSegments.setSelection(startIdx, endIdx);
		
		
		setPossiblePlaintexts(idx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		//resetChosenPlaintexts(idx, cstb);
		
		updateChosenPlaintexts(idx, cstb, guiHandler.getColorSelectControl(), guiHandler.getColorDeselectControl());
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.getTxtChosenPlaintexts().setText("");
		
		hideControl(cstb.getCompHoldEncryptionProcess());
		showControl(cstb.getCompHoldDecryptionProcess());
		hideControl(cstb.getDecryptWarning());
	
	}*/

	
	
	
	
	
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
	
	
	/*private void updateChosenPlaintexts(int idx, CryptosystemTextbookComposite cstb, Color colorToSet, Color colorToReset) {
		ArrayList<Boolean> clickedPlaintextsOfIdx = getClickedPlaintextsState(idx);
		for(int i = 0; i < clickedPlaintextsOfIdx.size(); i++) {
			if(clickedPlaintextsOfIdx.get(i)) {
				countClicksForPlaintexts[i] = 1;
				setColorPlaintext(cstb, i, colorToSet);
			}
			else {
				countClicksForPlaintexts[i] = 0;
				setColorPlaintext(cstb, i, colorToReset);
			}
		}
	}*/
	
	
	public void btnNextBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = (cstb.getCmbChooseBlock().getSelectionIndex() + 1)
				% ciphertextList.size();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		
		setPossiblePlaintexts(nextIdx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), 
				cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		cstb.getCmbChooseBlock().select(nextIdx);
		
		
		// update chosen plainetxts and colors
		updateChosenPlaintexts(nextIdx, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	
	public void btnPrevBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = BigInteger.valueOf(
				cstb.getCmbChooseBlock().getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								ciphertextList.size())).intValue();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		
		setPossiblePlaintexts(nextIdx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), 
				cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		cstb.getCmbChooseBlock().select(nextIdx);
		
		
		updateChosenPlaintexts(nextIdx, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	public void cmbChooseBlockAction(CryptosystemTextbookComposite cstb) {
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		setPossiblePlaintexts(idx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), 
				cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
	}
	
	
	
	public void fixSelection(StyledText stxt, Combo cmb) {
		int idx = cmb.getSelectionIndex();
		if(idx == -1)
			return;
		
		int elem = idx + 1;
		markCiphertext(stxt, elem, ciphertextList);
	}
	
	
	
	public void btnDecryptionAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		if(cstb.getTextSelector().getText() == null || cstb.getTextSelector().getText().getText().isEmpty() || guiHandler.getRabinFirst().getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on\n\"Load text...\" to load a ciphertext you want to decrypt";
			cstb.getDecryptWarning().setText(strLoadTextWarning);
			showControl(cstb.getDecryptWarning());
			return;
		}

		String pattern = "[0-9a-fA-F]+"; 
		String ciphertext = cstb.getTextSelector().getText().getText();
		ciphertext = ciphertext.replaceAll("\\s+", ""); 
		if(!ciphertext.matches(pattern)) {
			String strOnlyHexAllowed = Messages.HandleFirstTab_strOnlyHexAllowed;
			cstb.getDecryptWarning().setText(strOnlyHexAllowed);
			showControl(cstb.getDecryptWarning());
			if(!cstb.getTxtCiphertextSegments().getText().isEmpty()) {
				Display.getDefault().timerExec(3000, new Runnable() {
					
					@Override
					public void run() {
						hideControl(cstb.getDecryptWarning());
					}
				});
				
				//hideControl(cstb.getDecryptWarning());
			}
			
			return;
		}
		
		boolean checkLength = ciphertext.length() % guiHandler.getBlocklength() == 0;
		
		if(!checkLength) {
			String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
			cstb.getDecryptWarning().setText(MessageFormat.format(
					strLengthCipher,
					(guiHandler.getBlocklength() / 2)));
			showControl(cstb.getDecryptWarning());
			return;
		}
		
		hideControl(cstb.getDecryptWarning());
		
		ciphertextList = guiHandler.getRabinFirst().parseString(ciphertext, guiHandler.getBlocklength());
		plaintexts = guiHandler.getRabinFirst().getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.getRadix());
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		String ciphertextblocksWithSeparator = guiHandler.getRabinFirst().getStringWithSepForm(ciphertextList, guiHandler.getSeparator());
		cstb.getTxtCiphertextSegments().setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cstb.getCmbChooseBlock(), plaintexts.size());
		cstb.getCmbChooseBlock().select(0);
		
		
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
			
		setPossiblePlaintexts(idx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), 
				cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		//resetChosenPlaintexts(idx, cstb);
		updateChosenPlaintexts(idx, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.getTxtChosenPlaintexts().setText("");
	}
	
	
	
	
	/*public void btnDecryptionAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		if(cstb.getTextSelector().getText() == null || cstb.getTextSelector().getText().getText().isEmpty() || guiHandler.getRabinFirst().getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on \"Load text...\" to load a ciphertext you want to decrypt";
			cstb.getDecryptWarning().setText(strLoadTextWarning);
			showControl(cstb.getDecryptWarning());
			return;
		}

		String pattern = "[0-9a-fA-F]+"; 
		String ciphertext = cstb.getTextSelector().getText().getText();
		ciphertext = ciphertext.replaceAll("\\s+", ""); 
		if(!ciphertext.matches(pattern)) {
			String strOnlyHexAllowed = Messages.HandleFirstTab_strOnlyHexAllowed;
			cstb.getDecryptWarning().setText(strOnlyHexAllowed);
			showControl(cstb.getDecryptWarning());
			if(!cstb.getTxtCiphertextSegments().getText().isEmpty()) {
				Display.getDefault().timerExec(3000, new Runnable() {
					
					@Override
					public void run() {
						hideControl(cstb.getDecryptWarning());
					}
				});
				
				//hideControl(cstb.getDecryptWarning());
			}
			
			return;
		}
		
		boolean checkLength = ciphertext.length() % guiHandler.getBlocklength() == 0;
		
		if(!checkLength) {
			String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
			cstb.getDecryptWarning().setText(MessageFormat.format(
					strLengthCipher,
					(guiHandler.getBlocklength() / 2)));
			showControl(cstb.getDecryptWarning());
			return;
		}
		
		hideControl(cstb.getDecryptWarning());
		
		ciphertextList = guiHandler.getRabinFirst().parseString(ciphertext, guiHandler.getBlocklength());
		//plaintexts = guiHandler.getRabinFirst().getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.getRadix());
		//initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		initializeClickedPlaintexts(ciphertextList.size() * 4);
		
		String ciphertextblocksWithSeparator = guiHandler.getRabinFirst().getStringWithSepForm(ciphertextList, guiHandler.getSeparator());
		cstb.getTxtCiphertextSegments().setText(ciphertextblocksWithSeparator);
		
		//setChooseBlock(cstb.getCmbChooseBlock(), plaintexts.size());
		setChooseBlock(cstb.getCmbChooseBlock(), ciphertextList.size());
		cstb.getCmbChooseBlock().select(0);
		
		
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
			
		setPossiblePlaintexts(idx, cstb.getTxtFirstPlaintext(), cstb.getTxtSecondPlaintext(), 
				cstb.getTxtThirdPlaintext(), cstb.getTxtFourthPlaintext());
		
		//resetChosenPlaintexts(idx, cstb);
		updateChosenPlaintexts(idx, cstb, guiHandler.getColorSelectControl(), guiHandler.getColorDeselectControl());
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		cstb.getTxtChosenPlaintexts().setText("");
	}*/

	
	
	
	
	private void resetChosenPlaintexts(int idx, CryptosystemTextbookComposite cstb) {
		updateChosenPlaintexts(idx, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
		cstb.getTxtChosenPlaintexts().setText("");
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
				cstb.getTxtFirstPlaintext().setBackground(colorBG);
				cstb.getTxtFirstPlaintext().setForeground(colorFG);
				break;
			
			case 1:
				cstb.getTxtSecondPlaintext().setBackground(colorBG);
				cstb.getTxtSecondPlaintext().setForeground(colorFG);
				break;
			
			case 2:
				cstb.getTxtThirdPlaintext().setBackground(colorBG);
				cstb.getTxtThirdPlaintext().setForeground(colorFG);
				break;
				
			case 3:
				cstb.getTxtFourthPlaintext().setBackground(colorBG);
				cstb.getTxtFourthPlaintext().setForeground(colorFG);
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
	
	
	
	/*private void txtPlaintextMouseDownAction(int idxToSet, CryptosystemTextbookComposite cstb, Color colorToSetBG, Color colorToResetBG, Color colorToSetFG, Color colorToResetFG) {
		
		countClicksForPlaintexts[idxToSet] = (countClicksForPlaintexts[idxToSet] + 1) % 2;
		
		if(countClicksForPlaintexts[idxToSet] == 1) {
			int idx = cstb.getCmbChooseBlock().getSelectionIndex();
			clickedPlaintexts.get(idx).set(idxToSet, true);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			String val = plaintexts.get(idx).get(idxToSet);
			currentSelectedPlaintexts.put(key, val);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToSetBG, colorToSetFG);
		}
		
		if(countClicksForPlaintexts[idxToSet] == 0) {
			int idx = cstb.getCmbChooseBlock().getSelectionIndex();
			clickedPlaintexts.get(idx).set(idxToSet, false);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			currentSelectedPlaintexts.remove(key);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToResetBG, colorToResetFG);
		}
	}*/
	
	
	
	
	
	
	
	private void txtPlaintextMouseDownAction(int idxToSet, CryptosystemTextbookComposite cstb, Color colorToSetBG, Color colorToResetBG, Color colorToSetFG, Color colorToResetFG) {
		
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		if(isOnePlaintextSelected(idx) && !clickedPlaintexts.get(idx).get(idxToSet))
			return;
		
		countClicksForPlaintexts[idxToSet] = (countClicksForPlaintexts[idxToSet] + 1) % 2;
		
		if(countClicksForPlaintexts[idxToSet] == 1) {
			clickedPlaintexts.get(idx).set(idxToSet, true);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			String val = plaintexts.get(idx).get(idxToSet);
			currentSelectedPlaintexts.put(key, val);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToSetBG, colorToSetFG);
		}
		
		if(countClicksForPlaintexts[idxToSet] == 0) {
			clickedPlaintexts.get(idx).set(idxToSet, false);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			currentSelectedPlaintexts.remove(key);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToResetBG, colorToResetFG);
		}
	}
	
	
	private String getPlaintext(int idx, CryptosystemTextbookComposite cstb) {
		String plaintext = null;
		
		switch(idx) {
			case 0:
				plaintext = cstb.getTxtFirstPlaintext().getText();
				break;
			
			case 1:
				plaintext = cstb.getTxtSecondPlaintext().getText();
				break;
				
			case 2:
				plaintext = cstb.getTxtThirdPlaintext().getText();
				break;
				
			case 3:
				plaintext = cstb.getTxtFourthPlaintext().getText();
				break;
	
			default:
				break;
		}
		
		return plaintext;
	}
	
	
	/*private void txtPlaintextMouseDownAction(int idxToSet, CryptosystemTextbookComposite cstb, Color colorToSet, Color colorToReset) {
		countClicksForPlaintexts[idxToSet] = (countClicksForPlaintexts[idxToSet] + 1) % 2;
		
		if(countClicksForPlaintexts[idxToSet] == 1) {
			int idx = cstb.getCmbChooseBlock().getSelectionIndex();
			//clickedPlaintexts.get(idx).set(idxToSet, true);
			setClickedPlaintextsAtIdx(idx, idxToSet, true);
			
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			//String val = plaintexts.get(idx).get(idxToSet);
			String val = getPlaintext(idxToSet, cstb);
			currentSelectedPlaintexts.put(key, val);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToSet);
		}
		
		if(countClicksForPlaintexts[idxToSet] == 0) {
			int idx = cstb.getCmbChooseBlock().getSelectionIndex();
			//clickedPlaintexts.get(idx).set(idxToSet, false);
			setClickedPlaintextsAtIdx(idx, idxToSet, false);
			String key = String.valueOf(idx) + String.valueOf(idxToSet);
			//int key = Integer.parseInt(keyAsStr);
			currentSelectedPlaintexts.remove(key);
			String currentSelectedPlaintextsAsStr = getCurrentSelectedPlaintexts();
			cstb.getTxtChosenPlaintexts().setText(currentSelectedPlaintextsAsStr);
			setColorPlaintext(cstb, idxToSet, colorToReset);
		}
	}*/
	
	
	
	public void txtFirstPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 0;
		txtPlaintextMouseDownAction(idxToSet, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	public void txtSecondPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 1;
		txtPlaintextMouseDownAction(idxToSet, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	public void txtThirdPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 2;
		txtPlaintextMouseDownAction(idxToSet, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	
	public void txtFourthPlaintextMouseDownAction(CryptosystemTextbookComposite cstb) {
		if(clickedPlaintexts == null)
			return;
		
		int idxToSet = 3;
		txtPlaintextMouseDownAction(idxToSet, cstb, guiHandler.getColorSelectControlBG(), guiHandler.getColorDeselectControlBG(), guiHandler.getColorSelectControlFG(), guiHandler.getColorDeselectControlFG());
	}
	
	
	
	public void btnWriteToJCTeditor(CryptosystemTextbookComposite cstb) throws PartInitException {
		String ciphertext = cstb.getTxtCiphertext().getText();
		int len = ciphertext.length();
		String ciphertextForFile= ""; //$NON-NLS-1$
		int limit = 100;
		
		if(len < 100) {
			ciphertextForFile = ciphertext;
		}
		else {
		
			for(int i = 0; i < len; i++) {
				ciphertextForFile += ciphertext.charAt(i);
				if((i+1) % limit == 0)
					ciphertextForFile += "\n"; //$NON-NLS-1$
			}
		}
		
		
		IEditorInput customEditorInput = AbstractEditorService.createOutputFile(ciphertextForFile);
		EditorsManager.getInstance().openNewTextEditor(customEditorInput);
	
	}
	
	
	
	public void btnWriteToJCTeditorDecryptMode(CryptosystemTextbookComposite cstb) throws PartInitException {
		String plaintext = cstb.getTxtChosenPlaintexts().getText();
		IEditorInput customEditorInput = AbstractEditorService.createOutputFile(plaintext);
		EditorsManager.getInstance().openNewTextEditor(customEditorInput);
	}
	
	
	
	
	public void txtCiphertextModifyAction(CryptosystemTextbookComposite cstb) {
		String ciphertext = cstb.getTxtCiphertext().getText();
		
		if(ciphertext.isEmpty()) {
			cstb.getBtnDecryptInEncryptionMode().setEnabled(false);
			cstb.getBtnWriteToJCTeditor().setEnabled(false);
			return;
		}
		
		cstb.getBtnDecryptInEncryptionMode().setEnabled(true);
		cstb.getBtnWriteToJCTeditor().setEnabled(true);
	}
	
	
	
	public void txtCiphertextSegmentsModifyAction(CryptosystemTextbookComposite cstb) {
		String ciphertext = cstb.getTxtCiphertextSegments().getText();
		
		if(ciphertext.isEmpty()) {
			cstb.getBtnNextBlock().setEnabled(false);
			cstb.getBtnPrevBlock().setEnabled(false);
			return;
		}
		
		cstb.getBtnNextBlock().setEnabled(true);
		cstb.getBtnPrevBlock().setEnabled(true);
	}
	
	
	
	
	public void btnResetChosenPlaintextsAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintextsSize);
		
		for(int i = 0; i < clickedPlaintexts.size(); i++) {
			resetChosenPlaintexts(i, cstb);
		}
	}
	
	
	
	/*public void btnResetChosenPlaintextsAction(CryptosystemTextbookComposite cstb, int possiblePlaintextsSize) {
		currentSelectedPlaintexts = new LinkedHashMap<String, String>();
		initializeClickedPlaintexts(ciphertextList.size() * possiblePlaintextsSize);
		
		for(int i = 0; i < ciphertextList.size(); i++) {
			resetChosenPlaintexts(i, cstb);
		}
	}*/
	
	
	
	public void txtChosenPlaintextsModifyAction(CryptosystemTextbookComposite cstb) {
		String chosenPlaintexts = cstb.getTxtChosenPlaintexts().getText();
		
		if(chosenPlaintexts.isEmpty()) {
			cstb.getBtnResetChosenPlaintexts().setEnabled(false);
			cstb.getBtnWriteToJCTeditorDecryptMode().setEnabled(false);
			return;
		}
		
		cstb.getBtnResetChosenPlaintexts().setEnabled(true);
		cstb.getBtnWriteToJCTeditorDecryptMode().setEnabled(true);
	}
	
	
	
	public void btnRadioEncryptAction(CryptosystemTextbookComposite cstb) {
		hideControl(cstb.getCompHoldDecryptionProcess());
		showControl(cstb.getCompHoldEncryptionProcess());
		cstb.getGrpEncryptDecrypt().setText("Encryption");
		cstb.getTxtInfoEncryptionDecryption().setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_encrypt"));
	}
	
	
	
	public void btnRadioDecryptAction(CryptosystemTextbookComposite cstb) {
		hideControl(cstb.getCompHoldEncryptionProcess());
		showControl(cstb.getCompHoldDecryptionProcess());
		cstb.getGrpEncryptDecrypt().setText("Decryption");
		cstb.getTxtInfoEncryptionDecryption().setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_decrypt"));

	}
	

}
