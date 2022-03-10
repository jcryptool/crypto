package org.jcryptool.visual.rabin;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.ui.CryptosystemTextbookComposite;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;

public class HandleCryptosystemTextbook {
	
	
	ArrayList<String> ciphertextList;
	ArrayList<ArrayList<String>> plaintexts;
	ArrayList<ArrayList<Boolean>> clickedPlaintexts; 
	private RabinFirstTabComposite rftc;
	
	//private GUIHandler guiHandler;
	private HandleFirstTab guiHandler;
	
	public HandleCryptosystemTextbook(RabinFirstTabComposite rftc) {
		this.rftc = rftc;
		this.guiHandler = rftc.getGUIHandler();
	}
	
	public void showControl(Control c) {
		guiHandler.showControl(c);
	}
	
	public void hideControl(Control c) {
		guiHandler.hideControl(c);
	}
	
	
	
	public void btnEncryptAction(TextLoadController textSelector, Text txtToEncrypt, Text txtEncryptWarning) {
		if(textSelector.getText() == null || textSelector.getText().getText().isEmpty() || guiHandler.getRabinFirst().getN() == null) {
			String strLoadTextWarning = "Attention: make sure to generate a public and private key and click on \"Load text...\" to load a plaintext you want to encrypt";
			txtEncryptWarning.setText(strLoadTextWarning);
			showControl(txtEncryptWarning);
			return;
		}

		hideControl(txtEncryptWarning);
		
		String plaintext = textSelector.getText().getText();
		//String ciphertext = guiHandler.getRabinFirst().encryptPlaintext(plaintext, guiHandler.getBytesPerBlock(), guiHandler.getBlocklength(), guiHandler.getRadix());
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
	
	
	private void setPossiblePlaintexts(int idx, Text[] possiblePlaintexts) {
		ArrayList<String> allPossiblePlaintextsForIdx = plaintexts.get(idx);
		for(int i = 0; i < possiblePlaintexts.length; i++) {
			String possiblePlaintext = allPossiblePlaintextsForIdx.get(i);
			possiblePlaintexts[i].setText(possiblePlaintext);
		}
	}
	
	
	
	public void btnDecryptInEncryptionModeAction(StyledText txtCiphertextSegments, Combo cmbBlock, Button btnPrevBlock, 
			Button btnNextBlock, Text[] possiblePlaintexts, Composite compHoldDecryptionProcess, 
			Composite compHoldEncryptionProcess, Button btnRadioEncrypt, Button btnRadioDecrypt){
		
		btnRadioEncrypt.setSelection(false);
		btnRadioDecrypt.setSelection(true);
		
		plaintexts = guiHandler.getRabinFirst().getAllPlaintextsFromListOfCiphertextblocks(ciphertextList, guiHandler.getRadix());
		initializeClickedPlaintexts(plaintexts.size(), possiblePlaintexts.length);
		String ciphertextblocksWithSeparator = guiHandler.getRabinFirst().getStringWithSepForm(ciphertextList, guiHandler.getSeparator());
		txtCiphertextSegments.setText(ciphertextblocksWithSeparator);
		
		setChooseBlock(cmbBlock, plaintexts.size());
		cmbBlock.select(0);
		
		
		int idx = cmbBlock.getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(txtCiphertextSegments, elem, ciphertextList);
		/*int startIdx = guiHandler.getStartIdx(elem, ciphertextList);
		int endIdx = guiHandler.getEndIdx(startIdx, elem, ciphertextList);
		txtCiphertextSegments.setSelection(startIdx, endIdx);*/
		
		
		setPossiblePlaintexts(idx, possiblePlaintexts);
		
		
		hideControl(compHoldEncryptionProcess);
		showControl(compHoldDecryptionProcess);
	
	}
	
	
	public void btnNextBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = (cstb.getCmbChooseBlock().getSelectionIndex() + 1)
				% ciphertextList.size();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		
		setPossiblePlaintexts(nextIdx, cstb.getPlaintexts());
		
		cstb.getCmbChooseBlock().select(nextIdx);
	}
	
	
	
	public void btnPrevBlockAction(CryptosystemTextbookComposite cstb) {
		int nextIdx = BigInteger.valueOf(
				cstb.getCmbChooseBlock().getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								ciphertextList.size())).intValue();
		int elem = nextIdx + 1;
		
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		
		setPossiblePlaintexts(nextIdx, cstb.getPlaintexts());
		
		cstb.getCmbChooseBlock().select(nextIdx);
	}
	
	
	public void cmbChooseBlockAction(CryptosystemTextbookComposite cstb) {
		int idx = cstb.getCmbChooseBlock().getSelectionIndex();
		int elem = idx + 1;
		markCiphertext(cstb.getTxtCiphertextSegments(), elem, ciphertextList);
		setPossiblePlaintexts(idx, cstb.getPlaintexts());
	}
	
	
	
	public void fixSelection(StyledText stxt, Combo cmb) {
		int idx = cmb.getSelectionIndex();
		if(idx == -1)
			return;
		
		int elem = idx + 1;
		markCiphertext(stxt, elem, ciphertextList);
	}
	
	
	

}
