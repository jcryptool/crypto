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
	private String strGenKeyPairST = Messages.HandleSecondTab_strGenKeyPairST;
	private String strPlessN = Messages.HandleSecondTab_strPlessN;
	private String strCipherLessN = Messages.HandleSecondTab_strCipherLessN;
	private String strNotValidPlaintext = Messages.HandleSecondTab_strNotValidPlaintext;
	
	
	
	private enum State { TEXT, NUM, DECHEX, DECDECIMAL };

	
	/**
	 * @param scMain
	 * @param compMain
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public HandleSecondTab(ScrolledComposite scMain, Composite compMain, Rabin rabinFirst, Rabin rabinSecond) {
		super(scMain, compMain, rabinFirst, rabinSecond);
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
	 * @param elem
	 * @param a
	 * @return startIdx the startIdx of elem in a
	 */
	/*private int getStartIdx(int elem, ArrayList<String> a) {
		int startIdx = 0;
		for(int i = 0; i < elem - 1; i++) {
			startIdx += a.get(i).length();
			startIdx += separator.length() + 2;
		}
		return startIdx;
	}*/
	
	/**
	 * @param startIdx
	 * @param elem
	 * @param a
	 * @return endIdx the endIdx of elem in a
	 */
	/*private int getEndIdx(int startIdx, int elem, ArrayList<String> a) {
		int endIdx = startIdx + a.get(elem - 1).length();
		return endIdx;
	}*/
	
	
	
	
	
	
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
		
		if(rstc.getBtnText().getSelection()) {
		
			
			rstc.getTxtCipher().setSelection(startIdxCipher, endIdxCipher);
			rstc.getTxtMessageBase().setSelection(startIdxPlain, endIdxPlain);
			
			
			int startIdxPlainEncoded = getStartIdx(elem, plaintextEncodedList);
			int endIdxPlainEncoded = getEndIdx(startIdxPlainEncoded, elem, plaintextEncodedList);
			rstc.getTxtMessageSep().setSelection(startIdxPlainEncoded, endIdxPlainEncoded);
		}
		
		if(rstc.getBtnNum().getSelection()) {
			rstc.getTxtMessageBaseNum().setSelection(startIdxPlain, endIdxPlain);
			rstc.getTxtCipherNum().setSelection(startIdxCipher, endIdxCipher);
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
		
		
		if(rstc.getBtnRadHex().getSelection()) {
			rstc.getTxtCiphertextSegments().setSelection(startIdxCipher, endIdxCipher);
		}
		if(rstc.getBtnRadDecimal().getSelection()) {
			rstc.getTxtCiphertextSegmentsDecimal().setSelection(startIdxCipher, endIdxCipher);
		}
	}
	
	
	
	
	
	
	
	/**
	 * select next elem in text num mode
	 * @param rstc
	 */
	public void nextElementTextNumMode(RabinSecondTabComposite rstc) {
		
		//int nextIdx = BigInteger.valueOf(currentIdx-1).mod(BigInteger.valueOf(currentCiphertextList.size())).intValue();
		int nextIdx = (rstc.getCmbChooseCipher().getSelectionIndex() + 1)
				% currentCiphertextList.size();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.getTxtCipherFirst().setText(nextElem);
		
		this.selectionTextNumMode(elem, rstc);
		
		rstc.getCmbChooseCipher().select(nextIdx);
	}
	
	
	
	
	
	
	/**
	 * select prev elem in text num mode
	 * @param rstc
	 */
	public void prevElementTextNumMode(RabinSecondTabComposite rstc) {
		int nextIdx = BigInteger.valueOf(
				rstc.getCmbChooseCipher().getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								currentCiphertextList.size())).intValue();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.getTxtCipherFirst().setText(nextElem);
		
		this.selectionTextNumMode(elem, rstc);
		
		rstc.getCmbChooseCipher().select(nextIdx);
	}
	
	
	
	
	/**
	 * select next elem in hex dec mode
	 * @param rstc
	 */
	public void nextElementHexDecMode(RabinSecondTabComposite rstc) {
		int nextIdx = (rstc.getCmbChooseCipher().getSelectionIndex() + 1)
				% currentCiphertextList.size();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.getTxtCipherFirst().setText(nextElem);
		
		this.selectionHexDecMode(elem, rstc);
		
		rstc.getCmbChooseCipher().select(nextIdx);
	}
	
	/**
	 * select prev elem in hex dec mode
	 * @param rstc
	 */
	public void prevElementHexDecMode(RabinSecondTabComposite rstc) {
		int nextIdx = BigInteger.valueOf(
				rstc.getCmbChooseCipher().getSelectionIndex()-1).mod(
						BigInteger.valueOf(
								currentCiphertextList.size())).intValue();
		int elem = nextIdx + 1;
		String nextElem = currentCiphertextList.get(nextIdx);
		rstc.getTxtCipherFirst().setText(nextElem);
		
		this.selectionHexDecMode(elem, rstc);
		
		rstc.getCmbChooseCipher().select(nextIdx);
	}
	
	
	
	/**
	 * handle decimal numbers in encdec mode
	 * @param rstc
	 */
	public void handleDecimalNumbersEncDecMode(RabinSecondTabComposite rstc) {
		String plaintext = rstc.getTxtMessageSepNum().getText();
		
		if(plaintext.isEmpty()) {
			rstc.getTxtMessageSepNum().setBackground(this.getColorBackgroundNeutral());
			hideControl(rstc.getTxtMessageSepNumWarning());
			rstc.getBtnEnc().setEnabled(false);
			return;
		}
		
		BigInteger n = this.getRabinFirst().getN();
		
		if(n == null) {
			rstc.getTxtMessageSepNum().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtMessageSepNumWarning().setText(strGenKeyPairST);
			showControl(rstc.getTxtMessageSepNumWarning());
			rstc.getBtnEnc().setEnabled(false);
			return;
		}
		
		String pattern = "^(\\d+( \\|\\| \\d+)*)$"; //$NON-NLS-1$
		
		boolean validPlaintext = plaintext.matches(pattern);
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = getRabinFirst().getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = getRabinFirst().isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.getTxtMessageSepNum().setBackground(this.getColorBackgroundWrong());
				rstc.getTxtMessageSepNumWarning().setText(strPlessN);
				showControl(rstc.getTxtMessageSepNumWarning());
				rstc.getBtnEnc().setEnabled(false);
			}
			else {
				rstc.getTxtMessageSepNum().setBackground(this.getColorBackgroundCorrect());
				hideControl(rstc.getTxtMessageSepNumWarning());
				rstc.getBtnEnc().setEnabled(true);
			}
		}
		else {
			rstc.getTxtMessageSepNum().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtMessageSepNumWarning().setText(strNotValidPlaintext);
			showControl(rstc.getTxtMessageSepNumWarning());
			rstc.getBtnEnc().setEnabled(false);
		}
	}	
	
	
	
	
	/**
	 * handle hex numbers in dec mode
	 * @param rstc
	 */
	public void handleHexNumDecMode(RabinSecondTabComposite rstc) {
		String ciphertextParsed = rstc.getTxtEnterCiphertext().getText();
		String ciphertext = ciphertextParsed.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
		
		if(ciphertext.isEmpty()) {
			rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundNeutral());
			hideControl(rstc.getTxtEnterCiphertextWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		BigInteger n = this.getRabinFirst().getN();
		
		if(n == null) {
			rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtEnterCiphertextWarning().setText(strGenKeyPairST);
			showControl(rstc.getTxtEnterCiphertextWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		boolean matchRegex = ciphertext.matches("[0-9a-fA-F]+"); //$NON-NLS-1$
		if(!matchRegex) {
			rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundWrong());
			String strOnlyHexNumbers = Messages.HandleSecondTab_strOnlyHexNumbers;
			rstc.getTxtEnterCiphertextWarning().setText(strOnlyHexNumbers);
			showControl(rstc.getTxtEnterCiphertextWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		if(ciphertext.length() % getBlocklength() != 0) {
			rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundWrong());
			String strCipherMultipleBlocklength = Messages.HandleSecondTab_strCipherMultipleBlocklength;
			rstc.getTxtEnterCiphertextWarning().setText(MessageFormat.format(
					strCipherMultipleBlocklength,
					(getBlocklength() / 2)));
			showControl(rstc.getTxtEnterCiphertextWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		ArrayList<String> ciphertextList = getRabinFirst().parseString(ciphertext, getBlocklength());
		boolean isValidCiphertext = getRabinFirst().isValidPlaintext(ciphertextList, getRadix());
		
		if(!isValidCiphertext) {
			rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtEnterCiphertextWarning().setText(strCipherLessN);
			
			showControl(rstc.getTxtEnterCiphertextWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		rstc.getTxtEnterCiphertext().setBackground(this.getColorBackgroundCorrect());
		hideControl(rstc.getTxtEnterCiphertextWarning());
		rstc.getBtnApplyCiphertext().setEnabled(true);
	}
	
	
	
	
	/**
	 * handle decimal numbers in dec mode
	 * @param rstc
	 */
	public void handleDecimalNumbersDecMode(RabinSecondTabComposite rstc) {
		String plaintext = rstc.getTxtEnterCiphertextDecimal().getText();
		
		if(plaintext.isEmpty()) {
			rstc.getTxtEnterCiphertextDecimal().setBackground(this.getColorBackgroundNeutral());
			hideControl(rstc.getTxtEnterCiphertextDecimalWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		BigInteger n = this.getRabinFirst().getN();
		
		if(n == null) {
			rstc.getTxtEnterCiphertextDecimal().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtEnterCiphertextDecimalWarning().setText(strGenKeyPairST);
			showControl(rstc.getTxtEnterCiphertextDecimalWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
			return;
		}
		
		boolean validPlaintext = plaintext.matches("^(\\d+( \\|\\| \\d+)*)$"); //$NON-NLS-1$
		ArrayList<String> plaintextList = null;
		if(validPlaintext) {
			plaintextList = getRabinFirst().getParsedPlaintextInBase10(plaintext);
			boolean isPlaintextValidN = getRabinFirst().isValidPlaintext(plaintextList, 10);
			if(!isPlaintextValidN) {
				rstc.getTxtEnterCiphertextDecimal().setBackground(this.getColorBackgroundWrong());
				rstc.getTxtEnterCiphertextDecimalWarning().setText(strPlessN);
				showControl(rstc.getTxtEnterCiphertextDecimalWarning());
				rstc.getBtnApplyCiphertext().setEnabled(false);
			}
			else {
				rstc.getTxtEnterCiphertextDecimal().setBackground(this.getColorBackgroundCorrect());
				hideControl(rstc.getTxtEnterCiphertextDecimalWarning());
				rstc.getBtnApplyCiphertext().setEnabled(true);
			}
		}
		else {
			rstc.getTxtEnterCiphertextDecimal().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtEnterCiphertextDecimalWarning().setText(strNotValidPlaintext);
			showControl(rstc.getTxtEnterCiphertextDecimalWarning());
			rstc.getBtnApplyCiphertext().setEnabled(false);
		}
	}
	
	
	
	
	
	
	
	/** action for chooseCipher combo
	 * @param rstc
	 */
	private void chooseCipherAction(RabinSecondTabComposite rstc) {
		int idx = rstc.getCmbChooseCipher().getSelectionIndex();
		String item = rstc.getCmbChooseCipher().getItem(idx);
		int i = Integer.parseInt(item);
		int chosenIdx = i - 1;
		int elem = chosenIdx + 1;
		String ciphertext = currentCiphertextList.get(chosenIdx);
		rstc.getTxtCipherFirst().setText(ciphertext);
		
		
		if(rstc.getBtnSelectionEnc().getSelection()) {
			selectionTextNumMode(elem, rstc);
		}
		if(rstc.getBtnSelectionDec().getSelection()) {
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
		int idx = rstc.getCmbChooseCipher().getSelectionIndex();
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
		int idx = rstc.getCmbChooseCipher().getSelectionIndex();
		if(idx == -1)
			return;
		
		String currentPlaintext = currentPlaintextList.get(idx);
		
		String m1 = rstc.getTxtm1().getText();
		String m2 = rstc.getTxtm2().getText();
		String m3 = rstc.getTxtm3().getText();
		String m4 = rstc.getTxtm4().getText();
		
		if(m1.equals(currentPlaintext)) {
			rstc.getTxtm1().setBackground(this.getColorBackgroundCorrect());
		}
		if(m2.equals(currentPlaintext)) {
			rstc.getTxtm2().setBackground(this.getColorBackgroundCorrect());
		}
		if(m3.equals(currentPlaintext)) {
			rstc.getTxtm3().setBackground(this.getColorBackgroundCorrect());
		}
		if(m4.equals(currentPlaintext)) {
			rstc.getTxtm4().setBackground(this.getColorBackgroundCorrect());
		}
	}
	
	/**
	 * reset final plaintext color
	 * @param rstc
	 */
	private void resetFinalPlaintextColor(RabinSecondTabComposite rstc) {
		rstc.getTxtm1().setBackground(this.getColorResetFinalPlaintextBG());
		rstc.getTxtm2().setBackground(this.getColorResetFinalPlaintextBG());
		rstc.getTxtm3().setBackground(this.getColorResetFinalPlaintextBG());
		rstc.getTxtm4().setBackground(this.getColorResetFinalPlaintextBG());
	}
	
	
	/**
	 * handle plaintext text mode
	 * @param rstc
	 */
	public void handlePlaintextTextMode(RabinSecondTabComposite rstc) {
		Text txtWarning = rstc.getTxtMessageWarning();
		if(rstc.getTxtMessage().getText().isEmpty()) {
			hideControl(txtWarning);
			rstc.getTxtMessage().setBackground(this.getColorBackgroundNeutral());
			rstc.getBtnEnc().setEnabled(false);
			return;
		}
		
		BigInteger n =this.getRabinFirst().getN();
		
		if(n == null) {
			rstc.getTxtMessage().setBackground(this.getColorBackgroundWrong());
			rstc.getTxtMessageWarning().setText(Messages.HandleSecondTab_rstc_getTxtMessageWarning);
			showControl(txtWarning);
			rstc.getBtnEnc().setEnabled(false);
			return;
		}
		
		
		if(rstc.getCmbBlockN().getSelectionIndex() == -1) {
			rstc.getTxtMessage().setBackground(this.getColorBackgroundWrong());
			String strSelectBytesPerBlock = Messages.HandleSecondTab_strSelectBytesPerBlock;
			rstc.getTxtMessageWarning().setText(strSelectBytesPerBlock);
			showControl(txtWarning);
			rstc.getBtnEnc().setEnabled(false);
			return;
			
		}
		
		rstc.getTxtMessage().setBackground(this.getColorBackgroundNeutral());
		hideControl(txtWarning);
		rstc.getBtnEnc().setEnabled(true);
	}
	
	
	/**
	 * reset components
	 * @param rstc
	 */
	private void resetDecComponents(RabinSecondTabComposite rstc) {
		rstc.getTxtmp().setText(""); //$NON-NLS-1$
		rstc.getTxtmq().setText(""); //$NON-NLS-1$
		rstc.getTxtyp().setText(""); //$NON-NLS-1$
		rstc.getTxtyq().setText(""); //$NON-NLS-1$
		rstc.getTxtV().setText(""); //$NON-NLS-1$
		rstc.getTxtW().setText(""); //$NON-NLS-1$
		rstc.getTxtm1().setText(""); //$NON-NLS-1$
		rstc.getTxtm2().setText(""); //$NON-NLS-1$
		rstc.getTxtm3().setText(""); //$NON-NLS-1$
		rstc.getTxtm4().setText(""); //$NON-NLS-1$
	}
	
	/**
	 * initialize primes for comboboxes
	 * @param numOfPrimes
	 * @param rstc
	 */
	public void initializePrimes(int numOfPrimes, RabinSecondTabComposite rstc) {
		
		for(int i = 0, count = 0; count < numOfPrimes; i++) {
			BigInteger possiblePrime = BigInteger.valueOf(i);
			if(this.getRabinFirst().isSuitablePrime(possiblePrime)) {
				rstc.getCmbP().add(possiblePrime.toString());
				rstc.getCmbQ().add(possiblePrime.toString());
				count++;
			}
		}
	}
	
	
	/**
	 * initialize components
	 * @param rstc
	 */
	public void initializeComponents(RabinSecondTabComposite rstc) {
		//rstc.getCmbP().setText("23"); //$NON-NLS-1$
		//rstc.getCmbQ().setText("31"); //$NON-NLS-1$
		//rstc.getBtnGenKeysMan().setSelection(true);
		//rstc.getBtnStartGenKeys().setEnabled(false);
		rstc.getBtnSelectionEnc().setSelection(true);
		rstc.getBtnText().setSelection(true);
		rstc.getBtnRadHex().setSelection(true);
		//cmbBlockN.setEnabled(false);
		rstc.getBtnEnc().setEnabled(false);
		//cmbChooseCipher.setEnabled(false);
		rstc.getBtnPrevElem().setEnabled(false);
		rstc.getBtnNextElem().setEnabled(false);
		rstc.getBtnSqrRoot().setEnabled(false);
		rstc.getBtnComputeYpYq().setEnabled(false);
		rstc.getBtnComputevw().setEnabled(false);
		rstc.getBtnComputePt().setEnabled(false);
		rstc.getBtnApplyCiphertext().setEnabled(false);
		setInfoEncTextMode(rstc);
	}
	
	
	
	/**
	 * set info encryption text mode
	 * @param rstc
	 */
	private void setInfoEncTextMode(RabinSecondTabComposite rstc) {
		rstc.getTxtInfoEnc().setText(this.getMessageByControl("txtInfoEnc_Text"));
		rstc.getTxtInfoSquareRoots().setText(this.getMessageByControl("txtInfoSquareRoots_encryption_text"));
		rstc.getTxtInfoLC().setText(this.getMessageByControl("txtInfoLC_encryption_text"));
		rstc.getTxtInfoPlaintexts().setText(this.getMessageByControl("txtInfoPlaintexts_encryption_text"));
	}
	
	/**
	 * set info encryption decimal mode
	 * @param rstc
	 */
	private void setInfoEncDecimalMode(RabinSecondTabComposite rstc) {
		rstc.getTxtInfoEnc().setText(this.getMessageByControl("txtInfoEnc_Decimal"));
		rstc.getTxtInfoSquareRoots().setText(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal"));
		rstc.getTxtInfoLC().setText(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal"));
		rstc.getTxtInfoPlaintexts().setText(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal"));
	}
	
	/**
	 * set info decryption hex mode
	 * @param rstc
	 */
	private void setInfoDecHexMode(RabinSecondTabComposite rstc) {
		rstc.getTxtInfoDecimalAndHex().setText(this.getMessageByControl("txtInfoDecimalAndHex_decryption_hex"));
		rstc.getTxtInfoSquareRoots().setText(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal"));
		rstc.getTxtInfoLC().setText(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal"));
		rstc.getTxtInfoPlaintexts().setText(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal"));
		
		//String strInfoDecHexMode = Messages.HandleSecondTab_strInfoDecHexMode;
		//rstc.getTxtInfoDec().setText(strInfoDecHexMode);

	}
	
	/**
	 * set info decryption decimal mode
	 * @param rstc
	 */
	private void setInfoDecDecimalMode(RabinSecondTabComposite rstc) {
		rstc.getTxtInfoDecimalAndHex().setText(this.getMessageByControl("txtInfoDecimalAndHex_decryption_decimal"));
		rstc.getTxtInfoSquareRoots().setText(this.getMessageByControl("txtInfoSquareRoots_decryption_hex_and_decimal"));
		rstc.getTxtInfoLC().setText(this.getMessageByControl("txtInfoLC_decryption_hex_and_decimal"));
		rstc.getTxtInfoPlaintexts().setText(this.getMessageByControl("txtInfoPlaintexts_decryption_hex_and_decimal"));
		
		
		
		//String strInfoDecDecimalMode = Messages.HandleSecondTab_strInfoDecDecimalMode;
		//rstc.getTxtInfoDec().setText(strInfoDecDecimalMode);

	}
	
	
	
	/**
	 * action for cmbPSelection
	 * @param cmbP
	 * @param vlNumbers
	 */
	public void cmbPSelectionAction(Combo cmbP) {
		int idx = cmbP.getSelectionIndex();
		String item = cmbP.getItem(idx);
		
		//cmbP.removeVerifyListener(vlNumbers);
		cmbP.setText(item);
		//cmbP.addVerifyListener(vlNumbers);
	}
	
	
	/**
	 * action cmbQSelection
	 * @param cmbQ
	 * @param vlNumbers
	 */
	public void cmbQSelectionAction(Combo cmbQ, VerifyListener vlNumbers) {
		int idx = cmbQ.getSelectionIndex();
		String item = cmbQ.getItem(idx);
		
		cmbQ.removeVerifyListener(vlNumbers);
		cmbQ.setText(item);
		cmbQ.addVerifyListener(vlNumbers);
	}
	
	
	
	/**
	 * action for btnGenKeysMan
	 * @param rstc
	 * @param e
	 */
	public void btnGenKeysManAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			this.updateTextfields(rstc.getCmbP(), rstc.getCmbQ(), rstc.getBtnGenKeysMan(), rstc.getBtnStartGenKeys(), rstc.getTxtWarningNpq());
		}
	}
	
	
	
	/**
	 * action for btnGenKeysAlgo
	 * @param rstc
	 * @param e
	 */
	public void btnGenKeysAlgoAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection())
			rstc.getBtnStartGenKeys().setEnabled(true);
	}
	
	
	
	/**
	 * action for btnGenKeysAlgo
	 * @param rstc
	 */
	public void btnStartGenKeysAction(RabinSecondTabComposite rstc) {
		
		if(rstc.getBtnGenKeysMan().getSelection()) {
			this.btnGenKeysManAction(rstc.getCmbP(), rstc.getCmbQ(), rstc.getTxtModN());
		}
		
		if(rstc.getBtnGenKeysAlgo().getSelection()) {
			BigInteger n = getRabinSecond().getN();
			if(n == null) {
				String strGenKeyPairInCryptosystemWarning = Messages.HandleSecondTab_strGenKeyPairInCryptosystemWarning;
				//rstc.getTxtWarningNpq().setText(strGenKeyPairInCryptosystemWarning);
				rstc.getNWarning().setText(strGenKeyPairInCryptosystemWarning);
				//showControl(rstc.getTxtWarningNpq());
				showControl(rstc.getNWarning());
				return;
				
			}
			BigInteger p = getRabinSecond().getP();
			BigInteger q = getRabinSecond().getQ();
			getRabinFirst().setP(p);
			getRabinFirst().setQ(q);
			getRabinFirst().setN(n);
			
			rstc.getCmbP().setText(p.toString());
			rstc.getCmbQ().setText(q.toString());
			rstc.getTxtModN().setText(n.toString());
			
			// uncomment this for alternative solution
			//hideControl(rstc.getTxtWarningNpq());
			hideControl(rstc.getNWarning());
			

		}
		
		int bitlength = getRabinFirst().getN().bitLength();
		int maxbytesPerBlock = bitlength / 8;
		int blocklengthtmp = ((bitlength / 8) + 1) * 2;
		//blocklength = blocklengthtmp;
		this.setBlocklength(blocklengthtmp);
		//bytesPerBlock = 2;
		this.setBytesPerBlock(2);
		//String blockItems = "";
		
		rstc.getCmbBlockN().removeAll();
		
		for(int i = 1; i <= maxbytesPerBlock; i++) {
			rstc.getCmbBlockN().add(String.valueOf(i));
		}
		
		rstc.getCmbBlockN().select(0);
		
		// to eliminate warnings
		handlePlaintextTextMode(rstc);
		handleDecimalNumbersEncDecMode(rstc);
		handleHexNumDecMode(rstc);
		handleDecimalNumbersDecMode(rstc);
		
		// for current mode
		if(rstc.getBtnSelectionEnc().getSelection()) {
			if(rstc.getBtnText().getSelection()) {
				handlePlaintextTextMode(rstc);
			}
			if(rstc.getBtnNum().getSelection()) {
				handleDecimalNumbersEncDecMode(rstc);
			}
		}
		
		if(rstc.getBtnSelectionDec().getSelection()) {
			if(rstc.getBtnRadHex().getSelection()) {
				handleHexNumDecMode(rstc);
			}
			if(rstc.getBtnRadDecimal().getSelection()) {
				handleDecimalNumbersDecMode(rstc);
			}
		}
	}
	
	
	
	/**
	 * action for cmbBlockN
	 * @param rstc
	 */
	public void cmbBlockNAction(RabinSecondTabComposite rstc) {
		int idx = rstc.getCmbBlockN().getSelectionIndex();
		String item = rstc.getCmbBlockN().getItem(idx);
		//bytesPerBlock = Integer.parseInt(item) * 2;
		this.setBytesPerBlock(Integer.parseInt(item) * 2);
		
		handlePlaintextTextMode(rstc);
	}
	
	
	/**
	 * action for btnNum
	 * @param rstc
	 * @param e
	 */
	public void btnNumAction(RabinSecondTabComposite rstc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			/*rstc.getCompEncStepsData().exclude = true;
			rstc.getCompEncSteps().setVisible(false);
			rstc.getCompEncStepsNumData().exclude = false;
			rstc.getCompEncStepsNum().setVisible(true);
			rstc.getCompBlockNData().exclude = true;
			rstc.getCompBlockN().setVisible(false);
			
			
			rstc.getEncPartSteps().requestLayout();*/
			
			this.hideControl(rstc.getCompEncSteps());
			this.showControl(rstc.getCompEncStepsNum());
			this.hideControl(rstc.getCompBlockN());
			
			
			// save and restore states
			//saveTextState();
			this.saveState(State.TEXT, rstc);
			//setNumState();
			this.setState(State.NUM, rstc);
			
			handleDecimalNumbersEncDecMode(rstc);
			
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
			rstc.getCompEncStepsNumData().exclude = true;
			rstc.getCompEncStepsNum().setVisible(false);
			rstc.getCompEncStepsData().exclude = false;
			rstc.getCompEncSteps().setVisible(true);
			rstc.getCompBlockNData().exclude = false;
			rstc.getCompBlockN().setVisible(true);
			
			rstc.getEncPartSteps().requestLayout();
			
			// save and set state
			//saveNumState();
			this.saveState(State.NUM, rstc);
			//setTextState();
			this.setState(State.TEXT, rstc);
			
		
			// verify input
			handlePlaintextTextMode(rstc);
			
			// set color of correctly computed plaintext 
			resetFinalPlaintextColor(rstc);
			setFinalPlaintextColor(rstc);
			
			// set info text
			setInfoEncTextMode(rstc);
		
		}
	
	}
	
	
	
	/**
	 * action for btnEnc
	 * @param rstc
	 */
	public void btnEncAction(RabinSecondTabComposite rstc) {
		if(rstc.getBtnText().getSelection()) {
			
			String plaintext = rstc.getTxtMessage().getText();
			String plaintextHex = getRabinFirst().bytesToString(plaintext.getBytes());
			String paddedPlaintextHex = getRabinFirst().getPaddedPlaintext(plaintextHex, getBytesPerBlock());
			ArrayList<String> plaintextsHex = getRabinFirst().parseString(paddedPlaintextHex, getBytesPerBlock());
			
			plaintextEncodedList = getRabinFirst().getEncodedList(plaintextsHex, getRadix(), getRabinFirst().getCharset());
			String plaintextEncodedWithSep = getRabinFirst().getStringWithSepEncodedForm(plaintextsHex, separator, getRadix(), getRabinFirst().getCharset());
			
			String plaintextInHex = getRabinFirst().getStringWithSepForm(plaintextsHex, separator);
			
			ArrayList<String> ciphertextsHex = getRabinFirst().getEncryptedListOfStrings(plaintextsHex, getRadix());
			
			ArrayList<String> paddedCiphertextsHex = getRabinFirst().getPaddedCiphertextblocks(ciphertextsHex, getBlocklength());
			
			String ciphertextInHex = getRabinFirst().getStringWithSepForm(paddedCiphertextsHex, separator);
			
			
			rstc.getTxtMessageWithPadding().setText(paddedPlaintextHex);
			rstc.getTxtMessageSep().setText(plaintextEncodedWithSep);
			rstc.getTxtMessageBase().setText(plaintextInHex);
			rstc.getTxtCipher().setText(ciphertextInHex);
			
			
			plaintextsTextMode = plaintextsHex;
			ciphertextsTextMode = paddedCiphertextsHex;
			currentPlaintextList = plaintextsTextMode;
			currentCiphertextList = ciphertextsTextMode;
		
			//setChooseCipher(currentCiphertextList);
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.getCmbChooseCipher().select(0);
			
			//chooseCipherAction();
			this.chooseCipherAction(rstc);
			
			
		}
		
		if(rstc.getBtnNum().getSelection()) {
			String plaintext = rstc.getTxtMessageSepNum().getText();
			ArrayList<String> plaintexts = getRabinFirst().getParsedPlaintextInBase10(plaintext);
			ArrayList<String> plaintextListBase16 = getRabinFirst().getListasRadix(plaintexts, getRadix());
			String plaintextBase16 = getRabinFirst().getStringWithSepRadixForm(plaintexts, separator, getRadix());
			ArrayList<String> ciphertexts = getRabinFirst().getEncryptedListOfStrings(plaintexts, 10);
			String ciphertextsWithSep = getRabinFirst().getStringWithSepRadixForm(ciphertexts, separator, getRadix());
			ArrayList<String> ciphertextListBase16 = getRabinFirst().getListasRadix(ciphertexts, getRadix());
			
			rstc.getTxtMessageBaseNum().setText(plaintextBase16);
			rstc.getTxtCipherNum().setText(ciphertextsWithSep);
			
			plaintextsNumMode = plaintextListBase16;
			ciphertextsNumMode = ciphertextListBase16;
			currentPlaintextList = plaintextsNumMode;
			currentCiphertextList = ciphertextsNumMode;
			
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.getCmbChooseCipher().select(0);
			
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
			//rstc.getCompHoldDecimalData().exclude = true;
			//rstc.getCompHoldDecimal().setVisible(false);
			//compEnterCiphertext.requestLayout();
			
			//rstc.getCompEnterCiphertextPart1Data().exclude = false;
			//rstc.getCompEnterCiphertextPart1().setVisible(true);
			//rstc.getCompEnterCiphertext().requestLayout();
			
			this.hideControl(rstc.getCompHoldDecimal());
			this.showControl(rstc.getCompEnterCiphertextSteps());
			
			//saveDecDecimalState();
			this.saveState(State.DECDECIMAL, rstc);
			//setDecHexState();
			this.setState(State.DECHEX, rstc);
			
			handleHexNumDecMode(rstc);
			
			
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
			//rstc.getCompEnterCiphertextPart1Data().exclude = true;
			//rstc.getCompEnterCiphertextPart1().setVisible(false);
			//compEnterCiphertext.requestLayout();
			
			//rstc.getCompHoldDecimalData().exclude = false;
			//rstc.getCompHoldDecimal().setVisible(true);
			//rstc.getCompEnterCiphertext().requestLayout();
			
			this.hideControl(rstc.getCompEnterCiphertextSteps());
			this.showControl(rstc.getCompHoldDecimal());
			
			//saveDecHexState();
			this.saveState(State.DECHEX, rstc);
			//setDecDecimalState();
			this.setState(State.DECDECIMAL, rstc);
			
			handleDecimalNumbersDecMode(rstc);
			
			setInfoDecDecimalMode(rstc);
			
		}
	}
	
	
	
	/**
	 * action for btnApplyCiphertext
	 * @param rstc
	 */
	public void btnApplyCiphertextAction(RabinSecondTabComposite rstc) {
		if(rstc.getBtnRadHex().getSelection()) {
			String ciphertextHexstring = rstc.getTxtEnterCiphertext().getText();
			ciphertextHexstring = ciphertextHexstring.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			ArrayList<String> ciphertextList = getRabinFirst().parseString(ciphertextHexstring, getBlocklength());
			String ciphertextHexstringWithSep = getRabinFirst().getStringWithSepForm(ciphertextList, separator);
			rstc.getTxtCiphertextSegments().setText(ciphertextHexstringWithSep);
			
			// add global list
			
			currentCiphertextList = ciphertextList;
			ciphertextsDecHexMode = ciphertextList;
			
			//setChooseCipher(currentCiphertextList);
			dataTransfer.setChooseCipher(currentCiphertextList, rstc);
			
			rstc.getCmbChooseCipher().select(0);
			
			//chooseCipherAction();
			this.chooseCipherAction(rstc);
			
			
		}
		
		if(rstc.getBtnRadDecimal().getSelection()) {
			String ciphertextDecimal = rstc.getTxtEnterCiphertextDecimal().getText();
			ArrayList<String> ciphertextList = getRabinFirst().getParsedPlaintextInBase10(ciphertextDecimal);
			String ciphertextBase16 = getRabinFirst().getStringWithSepRadixForm(ciphertextList, separator, getRadix());
			ArrayList<String> ciphertextHexList = getRabinFirst().getListasRadix(ciphertextList, getRadix());
			rstc.getTxtCiphertextSegmentsDecimal().setText(ciphertextBase16);
			
			
			ciphertextsDecDecimalMode = ciphertextHexList;
			currentCiphertextList = ciphertextHexList;
			//ciphertextsDecDecimalMode = ciphertextHexList;
			
			//setChooseCipher(ciphertextHexList);
			dataTransfer.setChooseCipher(ciphertextHexList, rstc);
			
			rstc.getCmbChooseCipher().select(0);
			
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
		int idx = rstc.getCmbChooseCipher().getSelectionIndex();
		String item = rstc.getCmbChooseCipher().getItem(idx);
		int i = Integer.parseInt(item);
		int chosenIdx = i - 1;
		int elem = chosenIdx + 1;
		String ciphertext = currentCiphertextList.get(chosenIdx);
		rstc.getTxtCipherFirst().setText(ciphertext);
		
		
		if(rstc.getBtnSelectionEnc().getSelection()) {
			selectionTextNumMode(elem, rstc);
		}
		if(rstc.getBtnSelectionDec().getSelection()) {
			selectionHexDecMode(elem, rstc);
		}

	}
	
	
	
	/**
	 * action for txtCipherFirstModify
	 * @param rstc
	 */
	public void txtCipherFirstModifyAction(RabinSecondTabComposite rstc) {
		String chooseCipher = rstc.getTxtCipherFirst().getText();
		if(chooseCipher.isEmpty()) {
			rstc.getBtnPrevElem().setEnabled(false);
			rstc.getBtnNextElem().setEnabled(false);
			rstc.getBtnSqrRoot().setEnabled(false);
			return;
		}
		
		rstc.getBtnPrevElem().setEnabled(true);
		rstc.getBtnNextElem().setEnabled(true);
		rstc.getBtnSqrRoot().setEnabled(true);
	}
	
	
	
	/**
	 * action for btnPrevElem
	 * @param rstc
	 */
	public void btnPrevElemAction(RabinSecondTabComposite rstc) {
		
		if(currentCiphertextList.size() > 1)
			resetDecComponents(rstc);
		
		if(rstc.getBtnSelectionEnc().getSelection()) {
			if(currentCiphertextList.size() > 1)
				resetFinalPlaintextColor(rstc);
			prevElementTextNumMode(rstc);
		}
		if(rstc.getBtnSelectionDec().getSelection()) {
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
		
		if(rstc.getBtnSelectionEnc().getSelection()) {
			if(currentCiphertextList.size() > 1)
				resetFinalPlaintextColor(rstc);
			nextElementTextNumMode(rstc);
		}
		if(rstc.getBtnSelectionDec().getSelection()) {
			nextElementHexDecMode(rstc);
		}
	}
	
	
	
	
	/**
	 * action for btnSquareRoot
	 * @param rstc
	 */
	public void btnSquareRootAction(RabinSecondTabComposite rstc) {
		String currentCiphertext = rstc.getTxtCipherFirst().getText();
		BigInteger c = new BigInteger(currentCiphertext, getRadix());
		BigInteger mp = getRabinFirst().getSquarerootP(c);
		BigInteger mq = getRabinFirst().getSquarerootQ(c);
		String mpStr = mp.toString(getRadix());
		String mqStr = mq.toString(getRadix());
		rstc.getTxtmp().setText(mpStr);
		rstc.getTxtmq().setText(mqStr);
	}
	
	
	
	/**
	 * actionf for txtMp
	 * @param rstc
	 */
	public void txtMpAction(RabinSecondTabComposite rstc) {
		String mp = rstc.getTxtmp().getText();
		if(mp.isEmpty()) {
			rstc.getBtnComputeYpYq().setEnabled(false);
			return;
		}
		rstc.getBtnComputeYpYq().setEnabled(true);
	}
	
	
	
	/**
	 * action for txtMq
	 * @param rstc
	 */
	public void txtMqAction(RabinSecondTabComposite rstc) {
		String mq = rstc.getTxtmq().getText();
		if(mq.isEmpty()) {
			rstc.getBtnComputeYpYq().setEnabled(false);
			return;
		}
		rstc.getBtnComputeYpYq().setEnabled(true);
	}
	
	
	/**
	 * action for txtYp
	 * @param rstc
	 */
	public void txtYpAction(RabinSecondTabComposite rstc) {
		String yp = rstc.getTxtyp().getText();
		if(yp.isEmpty()) {
			rstc.getBtnComputevw().setEnabled(false);
			return;
		}
		rstc.getBtnComputevw().setEnabled(true);
	}
	
	
	
	/**
	 * action for txtYq
	 * @param rstc
	 */
	public void txtYqAction(RabinSecondTabComposite rstc) {
		String yq = rstc.getTxtyq().getText();
		if(yq.isEmpty()) {
			rstc.getBtnComputevw().setEnabled(false);
			return;
		}
		rstc.getBtnComputevw().setEnabled(true);
	}
	
	
	/**
	 * action for btnComputeYpYq
	 * @param rstc
	 */
	public void btnComputeYpYqAction(RabinSecondTabComposite rstc) {
		BigInteger[] yp_yq = getRabinFirst().getInverseElements();
		String yp = yp_yq[0].toString(getRadix());
		String yq = yp_yq[1].toString(getRadix());
		rstc.getTxtyp().setText(yp);
		rstc.getTxtyq().setText(yq);
	}
	
	
	
	/**
	 * action for btnComputevw
	 * @param rstc
	 */
	public void btnComputevwAction(RabinSecondTabComposite rstc) {
		BigInteger yp = new BigInteger(rstc.getTxtyp().getText(), getRadix());
		BigInteger yq = new BigInteger(rstc.getTxtyq().getText(), getRadix());
		BigInteger mp = new BigInteger(rstc.getTxtmp().getText(), getRadix());
		BigInteger mq = new BigInteger(rstc.getTxtmq().getText(), getRadix());
		BigInteger v = getRabinFirst().getV(yp, mq);
		BigInteger w = getRabinFirst().getW(yq, mp);
		rstc.getTxtV().setText(v.toString(getRadix()));
		rstc.getTxtW().setText(w.toString(getRadix()));
	}
	
	
	/**
	 * action for txtV
	 * @param rstc
	 */
	public void txtVAction(RabinSecondTabComposite rstc) {
		String v = rstc.getTxtV().getText();
		if(v.isEmpty()) {
			rstc.getBtnComputePt().setEnabled(false);
			return;
		}
		rstc.getBtnComputePt().setEnabled(true);
	}
	
	
	
	/**
	 * action for txtW
	 * @param rstc
	 */
	public void txtWAction(RabinSecondTabComposite rstc) {
		String w = rstc.getTxtW().getText();
		if(w.isEmpty()) {
			rstc.getBtnComputePt().setEnabled(false);
			return;
		}
		rstc.getBtnComputePt().setEnabled(true);
	}
	
	
	
	
	/**
	 * action for btnComputePt
	 * @param rstc
	 */
	public void btnComputePtAction(RabinSecondTabComposite rstc) {
		BigInteger v = new BigInteger(rstc.getTxtV().getText(), getRadix());
		BigInteger w = new BigInteger(rstc.getTxtW().getText(), getRadix());
		BigInteger[] possibleMessages = getRabinFirst().getPlaintexts(v, w);
		String m1 = possibleMessages[0].toString(getRadix());
		String m2 = possibleMessages[1].toString(getRadix());
		String m3 = possibleMessages[2].toString(getRadix());
		String m4 = possibleMessages[3].toString(getRadix());
		rstc.getTxtm1().setText(m1);
		rstc.getTxtm2().setText(m2);
		rstc.getTxtm3().setText(m3);
		rstc.getTxtm4().setText(m4);
		
		if(rstc.getBtnSelectionEnc().getSelection())
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
		
			/*rstc.getGrpPlaintextData().exclude = false;
			rstc.getGrpPlaintext().setVisible(true);
			rstc.getRootComposite().requestLayout();
			
			rstc.getCompEnterCiphertextData().exclude = true;
			rstc.getCompEnterCiphertext().setVisible(false);
			rstc.getCompHoldAllSteps().requestLayout();*/
			
			this.showControl(rstc.getGrpPlaintext());
			this.hideControl(rstc.getCompEnterCiphertext());
			
			if(rstc.getBtnRadHex().getSelection()) {
				//saveDecHexState();
				//setInfoDecHexMode(rstc);
				this.saveState(State.DECHEX, rstc);
			}
			if(rstc.getBtnRadDecimal().getSelection()) {
				//saveDecDecimalState();
				this.saveState(State.DECDECIMAL, rstc);
			}
			
			if(rstc.getBtnText().getSelection()) {
				//setTextState();
				setInfoEncTextMode(rstc);
				this.setState(State.TEXT, rstc);
			}
			if(rstc.getBtnNum().getSelection()) {
				//setNumState();
				setInfoEncDecimalMode(rstc);
				this.setState(State.NUM, rstc);
			}
			
			resetFinalPlaintextColor(rstc);
			setFinalPlaintextColor(rstc);
			
			//String strInfoSelectionEncryption = Messages.HandleSecondTab_strInfoSelectionEncryption;
			//rstc.getTxtInfoDec().setText(strInfoSelectionEncryption);
			
			
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
		
		
			/*rstc.getGrpPlaintextData().exclude = true;
			rstc.getGrpPlaintext().setVisible(false);
			rstc.getRootComposite().requestLayout();
			
			rstc.getCompEnterCiphertextData().exclude = false;
			rstc.getCompEnterCiphertext().setVisible(true);
			rstc.getCompHoldAllSteps().requestLayout();*/
			
			this.hideControl(rstc.getGrpPlaintext());
			this.showControl(rstc.getCompEnterCiphertext());
			//rstc.getSC().setMinSize(rstc.getRootComposite().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			//rstc.getCurrentInstance().requestLayout();
			
			if(rstc.getBtnText().getSelection()) {
				//saveTextState();
				this.saveState(State.TEXT, rstc);
			}
			if(rstc.getBtnNum().getSelection()) {
				//saveNumState();
				this.saveState(State.NUM, rstc);
			}
			
			if(rstc.getBtnRadHex().getSelection()) {
				//setDecHexState();
				setInfoDecHexMode(rstc);
				this.setState(State.DECHEX, rstc);
			}
			if(rstc.getBtnRadDecimal().getSelection()) {
				//setDecDecimalState();
				setInfoDecDecimalMode(rstc);
				this.setState(State.DECDECIMAL, rstc);
			}
			
			resetFinalPlaintextColor(rstc);
			
			/*if(rstc.getBtnRadHex().getSelection()) {
				setInfoDecHexMode(rstc);
			}
			if(rstc.getBtnRadDecimal().getSelection()) {
				setInfoDecDecimalMode(rstc);
			}*/
			
		
		}
	}
	
	
}
	
	
	
	
	


