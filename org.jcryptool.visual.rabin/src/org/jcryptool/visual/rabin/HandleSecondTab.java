package org.jcryptool.visual.rabin;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;
import org.jcryptool.visual.rabin.Messages;


public class HandleSecondTab extends GUIHandler {

	private ArrayList<String> currentCiphertextList;
	private String separator = "||"; //$NON-NLS-1$
	private ArrayList<String> currentPlaintextList;
	private ArrayList<String> plaintextEncodedList;
	private DataTransfer dataTransfer;
	private ArrayList<String> plaintextsTextMode;
	private ArrayList<String> ciphertextsTextMode;
	private ArrayList<String> plaintextsNumMode;
	private ArrayList<String> ciphertextsNumMode;
	private ArrayList<String> ciphertextsDecHexMode;
	private ArrayList<String> ciphertextsDecDecimalMode;
	public int oldIdxCmbBlockN;
	public int oldIdxChoosePadding;
	public int oldChooseCipherIdxEncDecText;
	public int oldChooseCipherIdxEncDecDecimal;
	public int oldChooseCipherIdxDecHex;
	public int oldChooseCipherIdxDecDecimal;
	private String strGenKeyPairST = Messages.HandleSecondTab_strGenKeyPairST;
	private String strPlessN = Messages.HandleSecondTab_strPlessN;
	private String strCipherLessN = Messages.HandleSecondTab_strCipherLessN;
	private String strNotValidPlaintext = Messages.HandleSecondTab_strNotValidPlaintext;
	private String strNotValidCiphertext = Messages.HandleSecondTab_0;
	
	
	private enum State { TEXT, NUM, DECHEX, DECDECIMAL };

	
	
	/**
	 * @param scMain
	 * @param compMain
	 * @param rabin
	 */
	public HandleSecondTab(ScrolledComposite scMain, Composite compMain, Rabin rabin) {
		super(scMain, compMain, rabin);
		this.dataTransfer = new DataTransfer();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * @return separator
	 */
	public String getSeparator() {
		return separator;
	}
	
	
	
	/**
	 * @return the currentCiphertextList
	 */
	public ArrayList<String> getCurrentCiphertextList() {
		return currentCiphertextList;
	}









	/**
	 * @return the currentPlaintextList
	 */
	public ArrayList<String> getCurrentPlaintextList() {
		return currentPlaintextList;
	}









	/**
	 * @return the plaintextEncodedList
	 */
	public ArrayList<String> getPlaintextEncodedList() {
		return plaintextEncodedList;
	}









	/**
	 * @return the plaintextsTextMode
	 */
	public ArrayList<String> getPlaintextsTextMode() {
		return plaintextsTextMode;
	}









	/**
	 * @return the ciphertextsTextMode
	 */
	public ArrayList<String> getCiphertextsTextMode() {
		return ciphertextsTextMode;
	}









	/**
	 * @return the plaintextsNumMode
	 */
	public ArrayList<String> getPlaintextsNumMode() {
		return plaintextsNumMode;
	}









	/**
	 * @return the ciphertextsNumMode
	 */
	public ArrayList<String> getCiphertextsNumMode() {
		return ciphertextsNumMode;
	}









	/**
	 * @return the ciphertextsDecHexMode
	 */
	public ArrayList<String> getCiphertextsDecHexMode() {
		return ciphertextsDecHexMode;
	}









	/**
	 * @return the ciphertextsDecDecimalMode
	 */
	public ArrayList<String> getCiphertextsDecDecimalMode() {
		return ciphertextsDecDecimalMode;
	}








	/**
	 * save state
	 * @param state
	 * @param rstc
	 */
	public void saveState(State state, RabinSecondTabComposite rstc) {
		
		switch(state) {
			case TEXT:
				dataTransfer.saveTextState(rstc);
				break;
				
			case NUM:
				dataTransfer.saveNumState(rstc);
				break;
				
			case DECHEX:
				dataTransfer.saveDecHexState(rstc);
				break;
				
			case DECDECIMAL:
				dataTransfer.saveDecDecimalState(rstc);
				break;
				
			default:
				break;
		}
	}
	
	
	
	/**
	 * set state
	 * @param state
	 * @param rstc
	 */
	public void setState(State state, RabinSecondTabComposite rstc) {
		switch(state) {
			case TEXT:
				dataTransfer.setTextState(ciphertextsTextMode, rstc);
				currentPlaintextList = plaintextsTextMode;
				currentCiphertextList = ciphertextsTextMode;
				break;
				
			case NUM:
				dataTransfer.setNumState(ciphertextsNumMode, rstc);
				currentPlaintextList = plaintextsNumMode;
				currentCiphertextList = ciphertextsNumMode;
				break;
				
			case DECHEX:
				dataTransfer.setDecHexState(ciphertextsDecHexMode, rstc);
				currentCiphertextList = ciphertextsDecHexMode;
				break;
				
			case DECDECIMAL:
				dataTransfer.setDecDecimalState(ciphertextsDecDecimalMode, rstc);
				currentCiphertextList = ciphertextsDecDecimalMode;
				break;
				
			default:
				break;
		}
	}
	

	
	
	/**
	 * selection of plaintext ciphertext in text and num mode
	 * @param elem
	 * @param rstc
	 */
	public void selectionTextNumMode(int elem, RabinSecondTabComposite rstc) {
		
		int startIdxCipher = getStartIdx(elem, currentCiphertextList);
		int endIdxCipher = getEndIdx(startIdxCipher, elem, currentCiphertextList);
		
		int startIdxPlain = getStartIdx(elem, currentPlaintextList);
		int endIdxPlain = getEndIdx(startIdxPlain, elem, currentPlaintextList);
		
		if(rstc.btnText.getSelection()) {
		
			
			rstc.txtCipher.setSelection(startIdxCipher, endIdxCipher);
			rstc.txtMessageBase.setSelection(startIdxPlain, endIdxPlain);
			
			
			int startIdxPlainEncoded = getStartIdx(elem, plaintextEncodedList);
			int endIdxPlainEncoded = getEndIdx(startIdxPlainEncoded, elem, plaintextEncodedList);
			rstc.txtMessageSep.setSelection(startIdxPlainEncoded, endIdxPlainEncoded);
		}
		
		if(rstc.btnNum.getSelection()) {
			rstc.txtMessageBaseNum.setSelection(startIdxPlain, endIdxPlain);
			rstc.txtCipherNum.setSelection(startIdxCipher, endIdxCipher);
		}
	}
	
	
	
	
	
	
	/**
	 * selection of plaintext, ciphertext in hex and dec mode
	 * @param elem
	 * @param rstc
	 */
	public void selectionHexDecMode(int elem, RabinSecondTabComposite rstc) {
		
		int startIdxCipher = getStartIdx(elem, currentCiphertextList);
		int endIdxCipher = getEndIdx(startIdxCipher, elem, currentCiphertextList);
		
		
		if(rstc.btnRadHex.getSelection()) {
			rstc.txtCiphertextSegments.setSelection(startIdxCipher, endIdxCipher);
		}
		if(rstc.btnRadDecimal.getSelection()) {
			rstc.txtCiphertextSegmentsDecimal.setSelection(startIdxCipher, endIdxCipher);
		}
	}
	
	
	
	
	
	
	
	/**
	 * select next elem in text num mode
	 * @param rstc
	 */
	public void nextElementTextNumMode(RabinSecondTabComposite rstc) {
		
		//int nextIdx = BigInteger.valueOf(currentIdx-1).mod(BigInteger.valueOf(currentCiphertextList.size())).intValue();
		int nextIdx = (rstc.cmbChooseCipher.getSelectionIndex() + 1)
				% currentCiphertextList.size();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.txtCipherFirst.setText(nextElem);
		
		this.selectionTextNumMode(elem, rstc);
		
		rstc.cmbChooseCipher.select(nextIdx);
		
		
		if(rstc.btnText.getSelection()) {
			oldChooseCipherIdxEncDecText = rstc.cmbChooseCipher.getSelectionIndex();
		}
		if(rstc.btnNum.getSelection()) {
			oldChooseCipherIdxEncDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
		}
	}
	
	
	
	
	
	
	/**
	 * select prev elem in text num mode
	 * @param rstc
	 */
	public void prevElementTextNumMode(RabinSecondTabComposite rstc) {
		int nextIdx = BigInteger.valueOf(
				rstc.cmbChooseCipher.getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								currentCiphertextList.size())).intValue();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.txtCipherFirst.setText(nextElem);
		
		this.selectionTextNumMode(elem, rstc);
		
		rstc.cmbChooseCipher.select(nextIdx);
		
		if(rstc.btnText.getSelection()) {
			oldChooseCipherIdxEncDecText = rstc.cmbChooseCipher.getSelectionIndex();
		}
		if(rstc.btnNum.getSelection()) {
			oldChooseCipherIdxEncDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
		}
	}
	
	
	
	
	/**
	 * select next elem in hex dec mode
	 * @param rstc
	 */
	public void nextElementHexDecMode(RabinSecondTabComposite rstc) {
		int nextIdx = (rstc.cmbChooseCipher.getSelectionIndex() + 1)
				% currentCiphertextList.size();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.txtCipherFirst.setText(nextElem);
		
		this.selectionHexDecMode(elem, rstc);
		
		rstc.cmbChooseCipher.select(nextIdx);
		
		if(rstc.btnRadHex.getSelection()) {
			oldChooseCipherIdxDecHex = rstc.cmbChooseCipher.getSelectionIndex();
		}
		if(rstc.btnRadDecimal.getSelection()) {
			oldChooseCipherIdxDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
		}
	}
	
	/**
	 * select prev elem in hex dec mode
	 * @param rstc
	 */
	public void prevElementHexDecMode(RabinSecondTabComposite rstc) {
		int nextIdx = BigInteger.valueOf(
				rstc.cmbChooseCipher.getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								currentCiphertextList.size())).intValue();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.txtCipherFirst.setText(nextElem);
		
		this.selectionHexDecMode(elem, rstc);
		
		rstc.cmbChooseCipher.select(nextIdx);
		
		if(rstc.btnRadHex.getSelection()) {
			oldChooseCipherIdxDecHex = rstc.cmbChooseCipher.getSelectionIndex();
		}
		if(rstc.btnRadDecimal.getSelection()) {
			oldChooseCipherIdxDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
		}
	}
	
	
	
	/**
	 * handle decimal numbers in encdec mode
	 * @param rstc
	 */
	public void handleDecimalNumbersEncDecMode(RabinSecondTabComposite rstc) {
		String plaintext = rstc.txtMessageSepNum.getText();
		resetEncAndDecComponentsDecimalWhenModified(rstc);
		
		if(plaintext.isEmpty()) {
			//resetEncAndDecComponentsDecimalWhenModified(rstc);
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageSepNumWarning.setText(strGenKeyPairST);
			showControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		String pattern = "^(\\d+( \\|\\| \\d+)*)$"; //$NON-NLS-1$
		
		boolean validPlaintext = plaintext.matches(pattern);
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = rabinFirst.getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = rabinFirst.isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
				rstc.txtMessageSepNumWarning.setText(strPlessN);
				showControl(rstc.txtMessageSepNumWarning);
				rstc.btnEnc.setEnabled(false);
			}
			else {
				rstc.txtMessageSepNum.setBackground(this.colorBackgroundCorrect);
				hideControl(rstc.txtMessageSepNumWarning);
				rstc.btnEnc.setEnabled(true);
			}
		}
		else {
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageSepNumWarning.setText(strNotValidPlaintext);
			showControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
		}
		
		
		
		
	}	
	
	
	
	
	public void handleDecimalNumbersEncDecModeWithoutReset(RabinSecondTabComposite rstc) {
		String plaintext = rstc.txtMessageSepNum.getText();
		
		if(plaintext.isEmpty()) {
			//resetEncAndDecComponentsDecimalWhenModified(rstc);
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageSepNumWarning.setText(strGenKeyPairST);
			showControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		String pattern = "^(\\d+( \\|\\| \\d+)*)$"; //$NON-NLS-1$
		
		boolean validPlaintext = plaintext.matches(pattern);
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = rabinFirst.getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = rabinFirst.isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
				rstc.txtMessageSepNumWarning.setText(strPlessN);
				showControl(rstc.txtMessageSepNumWarning);
				rstc.btnEnc.setEnabled(false);
			}
			else {
				rstc.txtMessageSepNum.setBackground(this.colorBackgroundCorrect);
				hideControl(rstc.txtMessageSepNumWarning);
				rstc.btnEnc.setEnabled(true);
			}
		}
		else {
			rstc.txtMessageSepNum.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageSepNumWarning.setText(strNotValidPlaintext);
			showControl(rstc.txtMessageSepNumWarning);
			rstc.btnEnc.setEnabled(false);
		}
		
		
		//resetEncAndDecComponentsDecimalWhenModified(rstc);
		
	}	
	
	
	
	
	
	
	
	/**
	 * handle hex numbers in dec mode
	 * @param rstc
	 */
	public void handleHexNumDecMode(RabinSecondTabComposite rstc) {
		String ciphertextParsed = rstc.txtEnterCiphertext.getText();
		String ciphertext = ciphertextParsed.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
		resetDecComponentsHexWhenModified(rstc);
		
		if(ciphertext.isEmpty()) {
			//resetDecComponentsHexWhenModified(rstc);
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextWarning.setText(strGenKeyPairST);
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		boolean matchRegex = ciphertext.matches("[0-9a-fA-F]+"); //$NON-NLS-1$
		if(!matchRegex) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			String strOnlyHexNumbers = Messages.HandleSecondTab_strOnlyHexNumbers;
			rstc.txtEnterCiphertextWarning.setText(strOnlyHexNumbers);
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		if(ciphertext.length() % blocklength != 0) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			String strCipherMultipleBlocklength = Messages.HandleSecondTab_strCipherMultipleBlocklength;
			rstc.txtEnterCiphertextWarning.setText(MessageFormat.format(
					strCipherMultipleBlocklength,
					(blocklength / 2)));
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		ArrayList<String> ciphertextList = rabinFirst.parseString(ciphertext, blocklength);
		boolean isValidCiphertext = rabinFirst.isValidPlaintext(ciphertextList, radix);
		
		if(!isValidCiphertext) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextWarning.setText(strCipherLessN);
			
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		rstc.txtEnterCiphertext.setBackground(this.colorBackgroundCorrect);
		hideControl(rstc.txtEnterCiphertextWarning);
		rstc.btnApplyCiphertext.setEnabled(true);
		
		//resetDecComponentsHexWhenModified(rstc);
		
	}
	
	
	
	
	public void handleHexNumDecModeWithoutReset(RabinSecondTabComposite rstc) {
		String ciphertextParsed = rstc.txtEnterCiphertext.getText();
		String ciphertext = ciphertextParsed.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
		
		if(ciphertext.isEmpty()) {
			//resetDecComponentsHexWhenModified(rstc);
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextWarning.setText(strGenKeyPairST);
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		boolean matchRegex = ciphertext.matches("[0-9a-fA-F]+"); //$NON-NLS-1$
		if(!matchRegex) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			String strOnlyHexNumbers = Messages.HandleSecondTab_strOnlyHexNumbers;
			rstc.txtEnterCiphertextWarning.setText(strOnlyHexNumbers);
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		if(ciphertext.length() % blocklength != 0) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			String strCipherMultipleBlocklength = Messages.HandleSecondTab_strCipherMultipleBlocklength;
			rstc.txtEnterCiphertextWarning.setText(MessageFormat.format(
					strCipherMultipleBlocklength,
					(blocklength / 2)));
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		ArrayList<String> ciphertextList = rabinFirst.parseString(ciphertext, blocklength);
		boolean isValidCiphertext = rabinFirst.isValidPlaintext(ciphertextList, radix);
		
		if(!isValidCiphertext) {
			rstc.txtEnterCiphertext.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextWarning.setText(strCipherLessN);
			
			showControl(rstc.txtEnterCiphertextWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		rstc.txtEnterCiphertext.setBackground(this.colorBackgroundCorrect);
		hideControl(rstc.txtEnterCiphertextWarning);
		rstc.btnApplyCiphertext.setEnabled(true);
		
		//resetDecComponentsHexWhenModified(rstc);
		
	}
	
	
	
	
	
	
	
	/**
	 * handle decimal numbers in dec mode
	 * @param rstc
	 */
	public void handleDecimalNumbersDecMode(RabinSecondTabComposite rstc) {
		String plaintext = rstc.txtEnterCiphertextDecimal.getText();
		resetDecComponentsDecimalWhenModified(rstc);
		
		if(plaintext.isEmpty()) {
			//resetDecComponentsDecimalWhenModified(rstc);
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextDecimalWarning.setText(strGenKeyPairST);
			showControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		boolean validPlaintext = plaintext.matches("^(\\d+( \\|\\| \\d+)*)$"); //$NON-NLS-1$
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = rabinFirst.getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = rabinFirst.isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
				rstc.txtEnterCiphertextDecimalWarning.setText(strCipherLessN);
				showControl(rstc.txtEnterCiphertextDecimalWarning);
				rstc.btnApplyCiphertext.setEnabled(false);
			}
			else {
				rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundCorrect);
				hideControl(rstc.txtEnterCiphertextDecimalWarning);
				rstc.btnApplyCiphertext.setEnabled(true);
			}
		}
		else {
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextDecimalWarning.setText(strNotValidCiphertext);
			showControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
		}
		
		//resetDecComponentsDecimalWhenModified(rstc);
	}
	
	
	
	
	
	public void handleDecimalNumbersDecModeWithoutReset(RabinSecondTabComposite rstc) {
		String plaintext = rstc.txtEnterCiphertextDecimal.getText();
		
		if(plaintext.isEmpty()) {
			//resetDecComponentsDecimalWhenModified(rstc);
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundNeutral);
			hideControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		BigInteger n = this.rabinFirst.getN();
		
		if(n == null) {
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextDecimalWarning.setText(strGenKeyPairST);
			showControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
			return;
		}
		
		boolean validPlaintext = plaintext.matches("^(\\d+( \\|\\| \\d+)*)$"); //$NON-NLS-1$
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = rabinFirst.getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = rabinFirst.isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
				rstc.txtEnterCiphertextDecimalWarning.setText(strCipherLessN);
				showControl(rstc.txtEnterCiphertextDecimalWarning);
				rstc.btnApplyCiphertext.setEnabled(false);
			}
			else {
				rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundCorrect);
				hideControl(rstc.txtEnterCiphertextDecimalWarning);
				rstc.btnApplyCiphertext.setEnabled(true);
			}
		}
		else {
			rstc.txtEnterCiphertextDecimal.setBackground(this.colorBackgroundWrong);
			rstc.txtEnterCiphertextDecimalWarning.setText(strNotValidCiphertext);
			showControl(rstc.txtEnterCiphertextDecimalWarning);
			rstc.btnApplyCiphertext.setEnabled(false);
		}
		
		//resetDecComponentsDecimalWhenModified(rstc);
	}
	
	
	
	
	
	
	
	
	
	
	
	/** action for chooseCipher combo
	 * @param rstc
	 */
	private void chooseCipherAction(RabinSecondTabComposite rstc) {
		int idx = rstc.cmbChooseCipher.getSelectionIndex();
		String item = rstc.cmbChooseCipher.getItem(idx);
		int i = Integer.parseInt(item);
		int chosenIdx = i - 1;
		int elem = chosenIdx + 1;
		String ciphertext = currentCiphertextList.get(chosenIdx);
		rstc.txtCipherFirst.setText(ciphertext);
//		boolean resetEncDecText = false;
//		boolean resetEncDecDecimal = false;
//		
//		if(rstc.btnSelectionEnc.getSelection()) {
//			if(rstc.btnText.getSelection()) {
//				if(idx != oldChooseCipherIdxEncDecText) {
//					resetDecComponents(rstc);
//					resetEncDecText = true;
//				}
//			}
//			if(rstc.btnNum.getSelection()) {
//				if(idx != oldChooseCipherIdxEncDecDecimal) {
//					resetDecComponents(rstc);
//					resetEncDecDecimal = true;
//				}
//			}
//		}
//		else {
//			if(rstc.btnRadHex.getSelection()) {
//				if(idx != oldChooseCipherIdxDecHex) {
//					resetDecComponents(rstc);
//					
//				}
//			}
//			else {
//				if(idx != oldChooseCipherIdxDecDecimal) {
//					resetDecComponents(rstc);
//					
//				}
//			}
//		}
		
		
		
		
		if(rstc.btnSelectionEnc.getSelection()) {
//			if(resetEncDecText)
//				resetFinalPlaintextColor(rstc);
//			if(resetEncDecDecimal)
//				resetFinalPlaintextColor(rstc);
			
			selectionTextNumMode(elem, rstc);
		}
		if(rstc.btnSelectionDec.getSelection()) {
			selectionHexDecMode(elem, rstc);
		}
	}
	
	/**
	 * fix the selection in case you click on the textfield
	 * @param a
	 * @param stxt
	 * @param rstc
	 */
	public void fixSelection(ArrayList<String> a, StyledText stxt, RabinSecondTabComposite rstc) {
		int idx = rstc.cmbChooseCipher.getSelectionIndex();
		if(idx == -1)
			return;
		
		int elem = idx + 1;
		int startIdx = getStartIdx(elem, a);
		int endIdx = getEndIdx(startIdx, elem, a);
		stxt.setSelection(startIdx, endIdx);
	}
	
	/**
	 * set final plaintext color
	 * @param rstc
	 */
	private void setFinalPlaintextColor(RabinSecondTabComposite rstc) {
		int idx = rstc.cmbChooseCipher.getSelectionIndex();
		if(idx == -1)
			return;
		
		String currentPlaintext = currentPlaintextList.get(idx);
		
		if(rstc.btnText.getSelection()) {
			currentPlaintext = new BigInteger(currentPlaintext, radix).toString(radix);
		}
		
		String m1 = rstc.txtm1.getText();
		String m2 = rstc.txtm2.getText();
		String m3 = rstc.txtm3.getText();
		String m4 = rstc.txtm4.getText();
		
		if(m1.equals(currentPlaintext)) {
			rstc.txtm1.setBackground(this.colorBackgroundCorrect);
			rstc.txtm1.setForeground(GUIHandler.colorSelectControlFG);
		}
		if(m2.equals(currentPlaintext)) {
			rstc.txtm2.setBackground(this.colorBackgroundCorrect);
			rstc.txtm2.setForeground(GUIHandler.colorSelectControlFG);
		}
		if(m3.equals(currentPlaintext)) {
			rstc.txtm3.setBackground(this.colorBackgroundCorrect);
			rstc.txtm3.setForeground(GUIHandler.colorSelectControlFG);
		}
		if(m4.equals(currentPlaintext)) {
			rstc.txtm4.setBackground(this.colorBackgroundCorrect);
			rstc.txtm4.setForeground(GUIHandler.colorSelectControlFG);
		}
	}
	
	/**
	 * reset final plaintext color
	 * @param rstc
	 */
	public void resetFinalPlaintextColor(RabinSecondTabComposite rstc) {
		rstc.txtm1.setBackground(GUIHandler.colorResetFinalPlaintextBG);
		rstc.txtm1.setForeground(GUIHandler.colorResetFinalPlaintextFG);
		rstc.txtm2.setBackground(GUIHandler.colorResetFinalPlaintextBG);
		rstc.txtm2.setForeground(GUIHandler.colorResetFinalPlaintextFG);
		rstc.txtm3.setBackground(GUIHandler.colorResetFinalPlaintextBG);
		rstc.txtm3.setForeground(GUIHandler.colorResetFinalPlaintextFG);
		rstc.txtm4.setBackground(GUIHandler.colorResetFinalPlaintextBG);
		rstc.txtm4.setForeground(GUIHandler.colorResetFinalPlaintextFG);
	}
	
	
	/**
	 * handle plaintext text mode
	 * @param rstc
	 */
	public void handlePlaintextTextMode(RabinSecondTabComposite rstc) {
		Text txtWarning = rstc.txtMessageWarning;
		resetEncAndDecComponentsTextWhenModified(rstc);
		
		if(rstc.txtMessage.getText().isEmpty()) {
			//rstc.cmbBlockN.setEnabled(false);
			rstc.cmbBlockN.removeAll();
			//resetEncAndDecComponentsTextWhenModified(rstc);
			hideControl(txtWarning);
			rstc.txtMessage.setBackground(this.colorBackgroundNeutral);
			rstc.btnEnc.setEnabled(false);
			
			rstc.cmbBlockN.setEnabled(false);
			rstc.cmbChooseBlockPadding.setEnabled(false);
			return;
		}
		
		BigInteger n =this.rabinFirst.getN();
		
		if(n == null) {
			//rstc.cmbBlockN.setEnabled(false);
			rstc.cmbBlockN.removeAll();
			rstc.txtMessage.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageWarning.setText(Messages.HandleSecondTab_rstc_getTxtMessageWarning);
			showControl(txtWarning);
			rstc.btnEnc.setEnabled(false);
			rstc.cmbBlockN.setEnabled(false);
			rstc.cmbChooseBlockPadding.setEnabled(false);
			return;
		}
		
		
		/*if(rstc.cmbBlockN.getSelectionIndex() == -1) {
			rstc.txtMessage.setBackground(this.colorBackgroundWrong);
			String strSelectBytesPerBlock = Messages.HandleSecondTab_strSelectBytesPerBlock;
			rstc.txtMessageWarning.setText(strSelectBytesPerBlock);
			showControl(txtWarning);
			rstc.btnEnc.setEnabled(false);
			return;
			
		}*/
		
		rstc.cmbBlockN.setEnabled(true);
		rstc.cmbChooseBlockPadding.setEnabled(true);
		
		int bitlength = rabinFirst.getN().bitLength();
		int maxBytesPerBlock = bitlength / 8;
		int byteLen = rstc.txtMessage.getText().length();
		
		
		if(byteLen < maxBytesPerBlock)
			maxBytesPerBlock = byteLen;
		
		rstc.cmbBlockN.removeAll();
		
		for(int i = 1; i <= maxBytesPerBlock; i++) {
			rstc.cmbBlockN.add(String.valueOf(i));
		}
		
		rstc.cmbBlockN.select(0);
		
		oldIdxCmbBlockN = rstc.cmbBlockN.getSelectionIndex();
		
		rstc.txtMessage.setBackground(this.colorBackgroundNeutral);
		hideControl(txtWarning);
		rstc.btnEnc.setEnabled(true);
		
		//resetEncAndDecComponentsTextWhenModified(rstc);
	}
	
	
	public void handlePlaintextTextModeWithoutReset(RabinSecondTabComposite rstc) {
		Text txtWarning = rstc.txtMessageWarning;
		if(rstc.txtMessage.getText().isEmpty()) {
			//rstc.cmbBlockN.setEnabled(false);
			//rstc.cmbBlockN.removeAll();
			//resetEncAndDecComponentsTextWhenModified(rstc);
			hideControl(txtWarning);
			rstc.txtMessage.setBackground(this.colorBackgroundNeutral);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		BigInteger n =this.rabinFirst.getN();
		
		if(n == null) {
			//rstc.cmbBlockN.setEnabled(false);
			//rstc.cmbBlockN.removeAll();
			rstc.txtMessage.setBackground(this.colorBackgroundWrong);
			rstc.txtMessageWarning.setText(Messages.HandleSecondTab_rstc_getTxtMessageWarning);
			showControl(txtWarning);
			rstc.btnEnc.setEnabled(false);
			return;
		}
		
		
		/*if(rstc.cmbBlockN.getSelectionIndex() == -1) {
			rstc.txtMessage.setBackground(this.colorBackgroundWrong);
			String strSelectBytesPerBlock = Messages.HandleSecondTab_strSelectBytesPerBlock;
			rstc.txtMessageWarning.setText(strSelectBytesPerBlock);
			showControl(txtWarning);
			rstc.btnEnc.setEnabled(false);
			return;
			
		}*/
		
		
		/*int bitlength = rabinFirst.getN().bitLength();
		int maxBytesPerBlock = bitlength / 8;
		int byteLen = rstc.txtMessage.getText().length();
		
		
		if(byteLen < maxBytesPerBlock)
			maxBytesPerBlock = byteLen;
		
		rstc.cmbBlockN.removeAll();
		
		for(int i = 1; i <= maxBytesPerBlock; i++) {
			rstc.cmbBlockN.add(String.valueOf(i));
		}
		
		rstc.cmbBlockN.select(0);*/
		
		rstc.txtMessage.setBackground(this.colorBackgroundNeutral);
		hideControl(txtWarning);
		rstc.btnEnc.setEnabled(true);
		
		//resetEncAndDecComponentsTextWhenModified(rstc);
	}
	
	
	
	
	
	
	
	public void resetEncComponentsText(RabinSecondTabComposite rstc) {
		//rstc.txtMessage.setText("");
		rstc.txtMessageBase.setText(""); //$NON-NLS-1$
		rstc.txtMessageSep.setText(""); //$NON-NLS-1$
		rstc.txtMessageWithPadding.setText(""); //$NON-NLS-1$
		rstc.txtCipher.setText(""); //$NON-NLS-1$
		rstc.cmbBlockN.removeAll();
		rstc.cmbChooseCipher.removeAll();
		rstc.txtCipherFirst.setText(""); //$NON-NLS-1$
		rstc.cmbChooseCipher.removeAll();
		
		if(ciphertextsDecDecimalMode != null)
			ciphertextsDecDecimalMode.clear();
		if(ciphertextsDecHexMode != null)
			ciphertextsDecHexMode.clear();
		if(ciphertextsNumMode != null)
			ciphertextsNumMode.clear();
		if(ciphertextsTextMode != null)
			ciphertextsTextMode.clear();
	}
	
	
	
	
	
	
	
	
	public void resetEncAndDecComponentsTextWhenModified(RabinSecondTabComposite rstc) {
		rstc.txtMessageBase.setText(""); //$NON-NLS-1$
		rstc.txtMessageSep.setText(""); //$NON-NLS-1$
		rstc.txtMessageWithPadding.setText(""); //$NON-NLS-1$
		rstc.txtCipher.setText(""); //$NON-NLS-1$
		rstc.txtCipherFirst.setText(""); //$NON-NLS-1$
		rstc.cmbChooseCipher.removeAll();
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);
		if(ciphertextsTextMode != null)
			ciphertextsTextMode.clear();
	}
	
	
	public void resetEncAndDecComponentsDecimalWhenModified(RabinSecondTabComposite rstc) {
		rstc.txtMessageBaseNum.setText(""); //$NON-NLS-1$
		rstc.txtCipherNum.setText(""); //$NON-NLS-1$
		rstc.txtCipherFirst.setText(""); //$NON-NLS-1$
		rstc.cmbChooseCipher.removeAll();
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);
		if(ciphertextsNumMode != null)
			ciphertextsNumMode.clear();
	}
	
	
	public void resetDecComponentsHexWhenModified(RabinSecondTabComposite rstc) {
		rstc.txtCiphertextSegments.setText(""); //$NON-NLS-1$
		rstc.txtCipherFirst.setText(""); //$NON-NLS-1$
		rstc.cmbChooseCipher.removeAll();
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);
		if(ciphertextsDecHexMode != null)
			ciphertextsDecHexMode.clear();
	}
	
	
	public void resetDecComponentsDecimalWhenModified(RabinSecondTabComposite rstc) {
		rstc.txtCiphertextSegmentsDecimal.setText(""); //$NON-NLS-1$
		rstc.txtCipherFirst.setText(""); //$NON-NLS-1$
		rstc.cmbChooseCipher.removeAll();
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);
		if(ciphertextsDecDecimalMode != null)
			ciphertextsDecDecimalMode.clear();
	}
	
	
	
	public void resetEncComponentsDecimal(RabinSecondTabComposite rstc) {
		rstc.txtMessageBaseNum.setText(""); //$NON-NLS-1$
		//rstc.txtMessageSepNum.setText("");
		rstc.txtCipherNum.setText(""); //$NON-NLS-1$
	}
	
	public void resetDecComponentsHex(RabinSecondTabComposite rstc) {
		//rstc.txtEnterCiphertext.setText("");
		rstc.txtCiphertextSegments.setText(""); //$NON-NLS-1$
	}
	
	public void resetDecComponentsDecimal(RabinSecondTabComposite rstc) {
		//rstc.txtEnterCiphertextDecimal.setText("");
		rstc.txtCiphertextSegmentsDecimal.setText(""); //$NON-NLS-1$
	}
	
	public void resetAllStates() {
		dataTransfer.resetAllStates();
	}
	
	
	/**
	 * reset components
	 * @param rstc
	 */
	public void resetDecComponents(RabinSecondTabComposite rstc) {
		rstc.txtmp.setText(""); //$NON-NLS-1$
		rstc.txtmq.setText(""); //$NON-NLS-1$
		rstc.txtyp.setText(""); //$NON-NLS-1$
		rstc.txtyq.setText(""); //$NON-NLS-1$
		rstc.txtV.setText(""); //$NON-NLS-1$
		rstc.txtW.setText(""); //$NON-NLS-1$
		rstc.txtm1.setText(""); //$NON-NLS-1$
		rstc.txtm2.setText(""); //$NON-NLS-1$
		rstc.txtm3.setText(""); //$NON-NLS-1$
		rstc.txtm4.setText(""); //$NON-NLS-1$
	}
	
	/**
	 * initialize primes for comboboxes
	 * @param numOfPrimes
	 * @param rstc
	 */
	public void initializePrimes(int numOfPrimes, RabinSecondTabComposite rstc) {
		
		for(int i = 0, count = 0; count < numOfPrimes; i++) {
			BigInteger possiblePrime = BigInteger.valueOf(i);
			if(this.rabinFirst.isSuitablePrime(possiblePrime)) {
				rstc.cmbP.add(possiblePrime.toString());
				rstc.cmbQ.add(possiblePrime.toString());
				count++;
			}
		}
	}
	
	
	/**
	 * initialize components
	 * @param rstc
	 */
	public void initializeComponents(RabinSecondTabComposite rstc) {
		rstc.btnSelectionEnc.setSelection(true);
		rstc.btnText.setSelection(true);
		rstc.btnRadHex.setSelection(true);
		rstc.btnEnc.setEnabled(false);
		rstc.btnPrevElem.setEnabled(false);
		rstc.btnNextElem.setEnabled(false);
		rstc.btnSqrRoot.setEnabled(false);
		rstc.btnComputeYpYq.setEnabled(false);
		rstc.btnComputevw.setEnabled(false);
		rstc.btnComputePt.setEnabled(false);
		rstc.btnApplyCiphertext.setEnabled(false);
		rstc.cmbBlockN.setEnabled(false);
		rstc.cmbChooseBlockPadding.setEnabled(false);
		rstc.cmbChooseCipher.setEnabled(false);
		setInfoEncTextMode(rstc);
	}
	
	
	
	/**
	 * set info encryption text mode
	 * @param rstc
	 */
	private void setInfoEncTextMode(RabinSecondTabComposite rstc) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getMessageByControl("txtInfoSquareRoots_encryption_text")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoLC_encryption_text")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoPlaintexts_encryption_text")); //$NON-NLS-1$
		
		rstc.txtInfoDecryption.setText(sb.toString());
		rstc.txtInfoEnc.setText(this.getMessageByControl("txtInfoEnc_Text")); //$NON-NLS-1$
	}
	
	/**
	 * set info encryption decimal mode
	 * @param rstc
	 */
	private void setInfoEncDecimalMode(RabinSecondTabComposite rstc) {
		rstc.txtInfoEnc.setText(this.getMessageByControl("txtInfoEnc_Decimal")); //$NON-NLS-1$
		StringBuffer sb = new StringBuffer();
		sb.append(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		//sb.append(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal"));
		sb.append(this.getMessageByControl("txtInfoLC_encryption_decimal")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		//sb.append(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal"));
		sb.append(this.getMessageByControl("txtInfoPlaintexts_encryption_decimal")); //$NON-NLS-1$
		rstc.txtInfoDecryption.setText(sb.toString());
	}
	
	/**
	 * set info decryption hex mode
	 * @param rstc
	 */
	private void setInfoDecHexMode(RabinSecondTabComposite rstc) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getMessageByControl("txtInfoDecimalAndHex_decryption_hex")); //$NON-NLS-1$
		sb.append("\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal")); //$NON-NLS-1$
		//sb.append("\n\n");
		sb.append("\n\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal")); //$NON-NLS-1$
		rstc.txtInfoDecryption.setText(sb.toString());
	}
	
	/**
	 * set info decryption decimal mode
	 * @param rstc
	 */
	private void setInfoDecDecimalMode(RabinSecondTabComposite rstc) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getMessageByControl("txtInfoDecimalAndHex_decryption_decimal")); //$NON-NLS-1$
		sb.append("\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal")); //$NON-NLS-1$
		sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal")); //$NON-NLS-1$
		//sb.append("\n\n");
		sb.append("\n\n\n\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
		sb.append(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal")); //$NON-NLS-1$
		rstc.txtInfoDecryption.setText(sb.toString());
	}
	
	
	
	/**
	 * action for cmbBlockN
	 * @param rstc
	 */
	public void cmbBlockNAction(RabinSecondTabComposite rstc) {
		int idx = rstc.cmbBlockN.getSelectionIndex();
		String item = rstc.cmbBlockN.getItem(idx);
		this.bytesPerBlock = Integer.parseInt(item) * 2;
		
		
		if(idx != oldIdxCmbBlockN)
			resetEncAndDecComponentsTextWhenModified(rstc);
		
		oldIdxCmbBlockN = idx;
		//resetDecComponents(rstc);
		//resetFinalPlaintextColor(rstc);
		//handlePlaintextTextMode(rstc);
	}
	
	
	/**
	 * action for btnNum
	 * @param rstc
	 * @param e
	 */
	public void btnNumAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.hideControl(rstc.compEncSteps);
			this.showControl(rstc.compEncStepsNum);
			this.hideControl(rstc.compBlockN);
			
			
			// save and restore states
			this.saveState(State.TEXT, rstc);
			this.setState(State.NUM, rstc);
			
			//handleDecimalNumbersEncDecMode(rstc);
			handleDecimalNumbersEncDecModeWithoutReset(rstc);
			
			resetFinalPlaintextColor(rstc);
			
			// set color of correct computed plaintext
			setFinalPlaintextColor(rstc);
			
			// set info text
			setInfoEncDecimalMode(rstc);
		
		}
	}
	
	
	/**
	 * action for btnText
	 * @param rstc
	 * @param e
	 */
	public void btnTextAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			rstc.compEncStepsNumData.exclude = true;
			rstc.compEncStepsNum.setVisible(false);
			rstc.compEncStepsData.exclude = false;
			rstc.compEncSteps.setVisible(true);
			rstc.compBlockNData.exclude = false;
			rstc.compBlockN.setVisible(true);
			
			rstc.encPartSteps.requestLayout();
			
			// save and set state
			this.saveState(State.NUM, rstc);
			this.setState(State.TEXT, rstc);
			
		
			// verify input
			//handlePlaintextTextMode(rstc);
			handlePlaintextTextModeWithoutReset(rstc);
			
			// set color of correctly computed plaintext 
			resetFinalPlaintextColor(rstc);
			setFinalPlaintextColor(rstc);
			
			// set info text
			setInfoEncTextMode(rstc);
		
		}
	
	}
	
	
	public void cmbChooseBlockPaddingAction(RabinSecondTabComposite rstc) {
		int idx = rstc.cmbChooseBlockPadding.getSelectionIndex();
		String paddingScheme = rstc.cmbChooseBlockPadding.getItem(idx);
		String pattern = Messages.HandleSecondTab_48;
		String message = MessageFormat.format(pattern, paddingScheme);
		rstc.txtLblMessageWithPadding.setText(message);
		
		if(idx != oldIdxChoosePadding)
			resetEncAndDecComponentsTextWhenModified(rstc);
		
		oldIdxChoosePadding = idx;
	}
	
	
	
	/**
	 * action for btnEnc
	 * @param rstc
	 */
	public void btnEncAction(RabinSecondTabComposite rstc) {
		if(rstc.btnText.getSelection()) {
			
			int iblocklengthFinal = 0;
			if(this.blocklength > (this.bytesPerBlock * 2))
				iblocklengthFinal = this.bytesPerBlock * 2;
			else
				iblocklengthFinal = this.blocklength;
			
			String plaintext = rstc.txtMessage.getText();
			String plaintextHex = rabinFirst.bytesToString(plaintext.getBytes());
			
			String paddingScheme = rstc.cmbChooseBlockPadding.getItem(rstc.cmbChooseBlockPadding.getSelectionIndex());
			
			//String paddedPlaintextHex = rabinFirst.getPaddedPlaintext(plaintextHex, bytesPerBlock);
			
			String paddedPlaintextHex = rabinFirst.getPaddedPlaintext(plaintextHex, bytesPerBlock, paddingScheme);
			ArrayList<String> plaintextsHex = rabinFirst.parseString(paddedPlaintextHex, bytesPerBlock);
			
			// test plaintext with prefix padding
			ArrayList<String> prefixPaddedPlaintextblocks = rabinFirst.getPaddedPlaintextblocks(plaintextsHex, iblocklengthFinal);
			paddedPlaintextHex = rabinFirst.getArrayListToString(prefixPaddedPlaintextblocks);
			
			plaintextEncodedList = rabinFirst.getEncodedList(plaintextsHex, radix, rabinFirst.getCharset());
			String plaintextEncodedWithSep = rabinFirst.getStringWithSepEncodedForm(plaintextsHex, separator, radix, rabinFirst.getCharset());
			
			//String plaintextInHex = rabinFirst.getStringWithSepForm(plaintextsHex, separator);
			String plaintextInHex = rabinFirst.getStringWithSepForm(prefixPaddedPlaintextblocks, separator);
			
			ArrayList<String> ciphertextsHex = rabinFirst.getEncryptedListOfStrings(plaintextsHex, radix);
			
			// this is a test for reducing the prefixed bytes for a ciphertext (maybe delete it later again)
			/*int iblocklengthFinal = 0;
			if(this.blocklength > (this.bytesPerBlock * 2))
				iblocklengthFinal = this.bytesPerBlock * 2;
			else
				iblocklengthFinal = this.blocklength;*/
			
			//ArrayList<String> paddedCiphertextsHex = rabinFirst.getPaddedCiphertextblocks(ciphertextsHex, blocklength);
			ArrayList<String> paddedCiphertextsHex = rabinFirst.getPaddedCiphertextblocks(ciphertextsHex, iblocklengthFinal);
			
			String ciphertextInHex = rabinFirst.getStringWithSepForm(paddedCiphertextsHex, separator);
			
			
			rstc.txtMessageWithPadding.setText(paddedPlaintextHex);
			rstc.txtMessageSep.setText(plaintextEncodedWithSep);
			rstc.txtMessageBase.setText(plaintextInHex);
			rstc.txtCipher.setText(ciphertextInHex);
			
			
			//plaintextsTextMode = plaintextsHex;
			plaintextsTextMode = prefixPaddedPlaintextblocks;
			ciphertextsTextMode = paddedCiphertextsHex;
			currentPlaintextList = plaintextsTextMode;
			currentCiphertextList = ciphertextsTextMode;
		
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.cmbChooseCipher.select(0);
			
			
			if(rstc.btnText.getSelection()) {
				oldChooseCipherIdxEncDecText = rstc.cmbChooseCipher.getSelectionIndex();
			}
			if(rstc.btnNum.getSelection()) {
				oldChooseCipherIdxEncDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
			}
			
			
			this.chooseCipherAction(rstc);
			
			
		}
		
		if(rstc.btnNum.getSelection()) {
			String plaintext = rstc.txtMessageSepNum.getText();
			ArrayList<String> plaintexts = rabinFirst.getParsedPlaintextInBase10(plaintext);
			ArrayList<String> plaintextListBase16 = rabinFirst.getListasRadix(plaintexts, radix);
			String plaintextBase16 = rabinFirst.getStringWithSepRadixForm(plaintexts, separator, radix);
			ArrayList<String> ciphertexts = rabinFirst.getEncryptedListOfStrings(plaintexts, 10);
			String ciphertextsWithSep = rabinFirst.getStringWithSepRadixForm(ciphertexts, separator, radix);
			ArrayList<String> ciphertextListBase16 = rabinFirst.getListasRadix(ciphertexts, radix);
			
			rstc.txtMessageBaseNum.setText(plaintextBase16);
			rstc.txtCipherNum.setText(ciphertextsWithSep);
			
			plaintextsNumMode = plaintextListBase16;
			ciphertextsNumMode = ciphertextListBase16;
			currentPlaintextList = plaintextsNumMode;
			currentCiphertextList = ciphertextsNumMode;
			
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.cmbChooseCipher.select(0);
			
			this.chooseCipherAction(rstc);
			
		}
		
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);
	}
	
	
	/**
	 * initialize states
	 */
	public void initStates() {
		dataTransfer.initStates();
	}
	
	
	
	/**
	 * action for btnRadHex
	 * @param rstc
	 * @param e
	 */
	public void btnRadHexAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.hideControl(rstc.compHoldDecimal);
			this.showControl(rstc.compEnterCiphertextSteps);
			
			this.saveState(State.DECDECIMAL, rstc);
			this.setState(State.DECHEX, rstc);
			
			//handleHexNumDecMode(rstc);
			handleHexNumDecModeWithoutReset(rstc);
			
			setInfoDecHexMode(rstc);
		}
	}
	
	
	
	
	/**
	 * action for btnRadDecimal
	 * @param rstc
	 * @param e
	 */
	public void btnRadDecimalAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.hideControl(rstc.compEnterCiphertextSteps);
			this.showControl(rstc.compHoldDecimal);
			
			this.saveState(State.DECHEX, rstc);
			this.setState(State.DECDECIMAL, rstc);
			
			//handleDecimalNumbersDecMode(rstc);
			handleDecimalNumbersDecModeWithoutReset(rstc);
			
			setInfoDecDecimalMode(rstc);
			
		}
	}
	
	
	
	/**
	 * action for btnApplyCiphertext
	 * @param rstc
	 */
	public void btnApplyCiphertextAction(RabinSecondTabComposite rstc) {
		if(rstc.btnRadHex.getSelection()) {
			String ciphertextHexstring = rstc.txtEnterCiphertext.getText();
			ciphertextHexstring = ciphertextHexstring.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			ArrayList<String> ciphertextList = rabinFirst.parseString(ciphertextHexstring, blocklength);
			String ciphertextHexstringWithSep = rabinFirst.getStringWithSepForm(ciphertextList, separator);
			rstc.txtCiphertextSegments.setText(ciphertextHexstringWithSep);
			
			// add global list
			
			currentCiphertextList = ciphertextList;
			ciphertextsDecHexMode = ciphertextList;
			
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.cmbChooseCipher.select(0);
			
			oldChooseCipherIdxDecHex = rstc.cmbChooseCipher.getSelectionIndex();
			
			this.chooseCipherAction(rstc);
			
			
		}
		
		if(rstc.btnRadDecimal.getSelection()) {
			String ciphertextDecimal = rstc.txtEnterCiphertextDecimal.getText();
			ArrayList<String> ciphertextList = rabinFirst.getParsedPlaintextInBase10(ciphertextDecimal);
			String ciphertextBase16 = rabinFirst.getStringWithSepRadixForm(ciphertextList, separator, radix);
			ArrayList<String> ciphertextHexList = rabinFirst.getListasRadix(ciphertextList, radix);
			rstc.txtCiphertextSegmentsDecimal.setText(ciphertextBase16);
			
			
			ciphertextsDecDecimalMode = ciphertextHexList;
			currentCiphertextList = ciphertextHexList;
			dataTransfer.setChooseCipher(ciphertextHexList, rstc);
			
			rstc.cmbChooseCipher.select(0);
			
			oldChooseCipherIdxDecDecimal = rstc.cmbChooseCipher.getSelectionIndex();
			
			chooseCipherAction(rstc);
			
			
		}
		
		resetDecComponents(rstc);
		resetFinalPlaintextColor(rstc);

	}
	
	
	
	
	/**
	 * action for cmbChooseCipher
	 * @param rstc
	 */
	public void cmbChooseCipherAction(RabinSecondTabComposite rstc) {
		int idx = rstc.cmbChooseCipher.getSelectionIndex();
		String item = rstc.cmbChooseCipher.getItem(idx);
		int i = Integer.parseInt(item);
		int chosenIdx = i - 1;
		int elem = chosenIdx + 1;
		String ciphertext = currentCiphertextList.get(chosenIdx);
		rstc.txtCipherFirst.setText(ciphertext);
		
		boolean resetEncDecText = false;
		boolean resetEncDecDecimal = false;
		
		if(rstc.btnSelectionEnc.getSelection()) {
			if(rstc.btnText.getSelection()) {
				if(idx != oldChooseCipherIdxEncDecText) {
					resetDecComponents(rstc);
					resetEncDecText = true;
				}
			}
			if(rstc.btnNum.getSelection()) {
				if(idx != oldChooseCipherIdxEncDecDecimal) {
					resetDecComponents(rstc);
					resetEncDecDecimal = true;
				}
			}
		}
		else {
			if(rstc.btnRadHex.getSelection()) {
				if(idx != oldChooseCipherIdxDecHex) {
					resetDecComponents(rstc);
					
				}
			}
			else {
				if(idx != oldChooseCipherIdxDecDecimal) {
					resetDecComponents(rstc);
					
				}
			}
		}
		
		if(rstc.btnSelectionEnc.getSelection()) {
			if(resetEncDecText)
				resetFinalPlaintextColor(rstc);
			if(resetEncDecDecimal)
				resetFinalPlaintextColor(rstc);
			
			selectionTextNumMode(elem, rstc);
		}
		if(rstc.btnSelectionDec.getSelection()) {
			selectionHexDecMode(elem, rstc);
		}
		
		if(rstc.btnSelectionEnc.getSelection()) {
			if(rstc.btnText.getSelection()) {
				oldChooseCipherIdxEncDecText = idx;
			}
			else {
				oldChooseCipherIdxEncDecDecimal = idx;
			}
		}
		else {
			if(rstc.btnRadHex.getSelection()) {
				oldChooseCipherIdxDecHex = idx;
			}
			else {
				oldChooseCipherIdxDecDecimal = idx;
			}
		}

	}
	
	
	
	/**
	 * action for txtCipherFirstModify
	 * @param rstc
	 */
	public void txtCipherFirstModifyAction(RabinSecondTabComposite rstc) {
		String chooseCipher = rstc.txtCipherFirst.getText();
		if(chooseCipher.isEmpty()) {
			rstc.btnPrevElem.setEnabled(false);
			rstc.btnNextElem.setEnabled(false);
			rstc.btnSqrRoot.setEnabled(false);
			rstc.cmbChooseCipher.setEnabled(false);
			return;
		}
		
		rstc.btnPrevElem.setEnabled(true);
		rstc.btnNextElem.setEnabled(true);
		rstc.btnSqrRoot.setEnabled(true);
		rstc.cmbChooseCipher.setEnabled(true);
	}
	
	
	
	/**
	 * action for btnPrevElem
	 * @param rstc
	 */
	public void btnPrevElemAction(RabinSecondTabComposite rstc) {
		
		if(currentCiphertextList.size() > 1)
			resetDecComponents(rstc);
		
		if(rstc.btnSelectionEnc.getSelection()) {
			if(currentCiphertextList.size() > 1)
				resetFinalPlaintextColor(rstc);
			prevElementTextNumMode(rstc);
		}
		if(rstc.btnSelectionDec.getSelection()) {
			prevElementHexDecMode(rstc);
		}
	}
	
	
	
	/**
	 * action for btnNextElem
	 * @param rstc
	 */
	public void btnNextElemAction(RabinSecondTabComposite rstc) {
		
		if(currentCiphertextList.size() > 1)
			resetDecComponents(rstc);
		
		if(rstc.btnSelectionEnc.getSelection()) {
			if(currentCiphertextList.size() > 1)
				resetFinalPlaintextColor(rstc);
			nextElementTextNumMode(rstc);
		}
		if(rstc.btnSelectionDec.getSelection()) {
			nextElementHexDecMode(rstc);
		}
	}
	
	
	
	
	/**
	 * action for btnSquareRoot
	 * @param rstc
	 */
	public void btnSquareRootAction(RabinSecondTabComposite rstc) {
		String currentCiphertext = rstc.txtCipherFirst.getText();
		BigInteger c = new BigInteger(currentCiphertext, radix);
		BigInteger mp = rabinFirst.getSquarerootP(c);
		BigInteger mq = rabinFirst.getSquarerootQ(c);
		String mpStr = mp.toString(radix);
		String mqStr = mq.toString(radix);
		rstc.txtmp.setText(mpStr);
		rstc.txtmq.setText(mqStr);
	}
	
	
	
	/**
	 * actionf for txtMp
	 * @param rstc
	 */
	public void txtMpAction(RabinSecondTabComposite rstc) {
		String mp = rstc.txtmp.getText();
		if(mp.isEmpty()) {
			rstc.btnComputeYpYq.setEnabled(false);
			return;
		}
		rstc.btnComputeYpYq.setEnabled(true);
	}
	
	
	
	/**
	 * action for txtMq
	 * @param rstc
	 */
	public void txtMqAction(RabinSecondTabComposite rstc) {
		String mq = rstc.txtmq.getText();
		if(mq.isEmpty()) {
			rstc.btnComputeYpYq.setEnabled(false);
			return;
		}
		rstc.btnComputeYpYq.setEnabled(true);
	}
	
	
	/**
	 * action for txtYp
	 * @param rstc
	 */
	public void txtYpAction(RabinSecondTabComposite rstc) {
		String yp = rstc.txtyp.getText();
		if(yp.isEmpty()) {
			rstc.btnComputevw.setEnabled(false);
			return;
		}
		rstc.btnComputevw.setEnabled(true);
	}
	
	
	
	/**
	 * action for txtYq
	 * @param rstc
	 */
	public void txtYqAction(RabinSecondTabComposite rstc) {
		String yq = rstc.txtyq.getText();
		if(yq.isEmpty()) {
			rstc.btnComputevw.setEnabled(false);
			return;
		}
		rstc.btnComputevw.setEnabled(true);
	}
	
	
	/**
	 * action for btnComputeYpYq
	 * @param rstc
	 */
	public void btnComputeYpYqAction(RabinSecondTabComposite rstc) {
		BigInteger[] yp_yq = rabinFirst.getInverseElements();
		String yp = yp_yq[0].toString(radix);
		String yq = yp_yq[1].toString(radix);
		rstc.txtyp.setText(yp);
		rstc.txtyq.setText(yq);
	}
	
	
	
	/**
	 * action for btnComputevw
	 * @param rstc
	 */
	public void btnComputevwAction(RabinSecondTabComposite rstc) {
		BigInteger yp = new BigInteger(rstc.txtyp.getText(), radix);
		BigInteger yq = new BigInteger(rstc.txtyq.getText(), radix);
		BigInteger mp = new BigInteger(rstc.txtmp.getText(), radix);
		BigInteger mq = new BigInteger(rstc.txtmq.getText(), radix);
		BigInteger v = rabinFirst.getV(yp, mq);
		BigInteger w = rabinFirst.getW(yq, mp);
		rstc.txtV.setText(v.toString(radix));
		rstc.txtW.setText(w.toString(radix));
	}
	
	
	/**
	 * action for txtV
	 * @param rstc
	 */
	public void txtVAction(RabinSecondTabComposite rstc) {
		String v = rstc.txtV.getText();
		if(v.isEmpty()) {
			rstc.btnComputePt.setEnabled(false);
			return;
		}
		rstc.btnComputePt.setEnabled(true);
	}
	
	
	
	/**
	 * action for txtW
	 * @param rstc
	 */
	public void txtWAction(RabinSecondTabComposite rstc) {
		String w = rstc.txtW.getText();
		if(w.isEmpty()) {
			rstc.btnComputePt.setEnabled(false);
			return;
		}
		rstc.btnComputePt.setEnabled(true);
	}
	
	
	
	
	/**
	 * action for btnComputePt
	 * @param rstc
	 */
	public void btnComputePtAction(RabinSecondTabComposite rstc) {
		BigInteger v = new BigInteger(rstc.txtV.getText(), radix);
		BigInteger w = new BigInteger(rstc.txtW.getText(), radix);
		BigInteger[] possibleMessages = rabinFirst.getPlaintexts(v, w);
		String m1 = possibleMessages[0].toString(radix);
		String m2 = possibleMessages[1].toString(radix);
		String m3 = possibleMessages[2].toString(radix);
		String m4 = possibleMessages[3].toString(radix);
		rstc.txtm1.setText(m1);
		rstc.txtm2.setText(m2);
		rstc.txtm3.setText(m3);
		rstc.txtm4.setText(m4);
		
		
		if(rstc.btnSelectionEnc.getSelection())
			setFinalPlaintextColor(rstc);

	}
	
	
	
	/**
	 * action for btnSelectionEnc
	 * @param rstc
	 * @param e
	 */
	public void btnSelectionEncAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.hideControl(rstc.compEnterCiphertext);
			this.showControl(rstc.grpPlaintext);
			//this.showControl(rstc.compHoldSepAndInfoForEncryption);
			this.showControl(rstc.grpHoldSepAndInfoForEncryption);
			//this.showControl(rstc.lblSepEncryptionBottom);
			
			if(rstc.btnRadHex.getSelection()) {
				this.saveState(State.DECHEX, rstc);
			}
			if(rstc.btnRadDecimal.getSelection()) {
				this.saveState(State.DECDECIMAL, rstc);
			}
			
			if(rstc.btnText.getSelection()) {
				setInfoEncTextMode(rstc);
				this.setState(State.TEXT, rstc);
			}
			if(rstc.btnNum.getSelection()) {
				setInfoEncDecimalMode(rstc);
				this.setState(State.NUM, rstc);
			}
			
			resetFinalPlaintextColor(rstc);
			setFinalPlaintextColor(rstc);
			rstc.grpDec.setText(Messages.HandleSecondTab_49);
			
		}
	}
	
	
	
	/**
	 * action for btnSelectionDec
	 * @param rstc
	 * @param e
	 */
	public void btnSelectionDecAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.hideControl(rstc.grpPlaintext);
			//this.hideControl(rstc.compHoldSepAndInfoForEncryption);
			this.hideControl(rstc.grpHoldSepAndInfoForEncryption);
			//this.hideControl(rstc.lblSepEncryptionBottom);
			this.showControl(rstc.compEnterCiphertext);
			
			if(rstc.btnText.getSelection()) {
				this.saveState(State.TEXT, rstc);
			}
			if(rstc.btnNum.getSelection()) {
				this.saveState(State.NUM, rstc);
			}
			
			if(rstc.btnRadHex.getSelection()) {
				setInfoDecHexMode(rstc);
				this.setState(State.DECHEX, rstc);
			}
			if(rstc.btnRadDecimal.getSelection()) {
				setInfoDecDecimalMode(rstc);
				this.setState(State.DECDECIMAL, rstc);
			}
			
			resetFinalPlaintextColor(rstc);
			
			rstc.grpDec.setText(Messages.HandleSecondTab_50);
		}
	}
	
	
}
	
	
	
	
	


