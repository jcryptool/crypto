package org.jcryptool.visual.rabin;

import java.util.ArrayList;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;

public class DataTransfer {
	//saving states when switching between textmode and num mode
	private ArrayList<String> stateText;
	private ArrayList<String> stateNum;
	private ArrayList<String> stateDecHex;
	private ArrayList<String> stateDecDecimal;
	private int cmbChooseCipherIdxTextMode;
	private int cmbChooseCipherIdxNumMode;
	private int cmbChooseCipherIdxDecHexMode;
	private int cmbChooseCipherIdxDecDecimalMode;
	
	
	
	/**
	 * initialize states
	 */
	public void initStates() {
		for(int i = 0; i < 11; i++) {
			stateText.add("");
			stateNum.add("");
			stateDecHex.add("");
			stateDecDecimal.add("");
		}	
	}
	
	
	
	
	
	

	/**
	 * constructor
	 */
	public DataTransfer() {
		super();
		this.stateText = new ArrayList<String>();
		this.stateNum = new ArrayList<String>();
		this.stateDecHex = new ArrayList<String>();
		this.stateDecDecimal = new ArrayList<String>();
	}






	/**
	 * @param rstc
	 * @return statesText the state of the decryption content
	 */
	private String[] getDecState(RabinSecondTabComposite rstc) {
		String[] statesText = {rstc.txtCipherFirst.getText(),
				rstc.txtmp.getText(), rstc.txtmq.getText(), 
				rstc.txtyp.getText(),rstc.txtyq.getText(), 
				rstc.txtV.getText(), rstc.txtW.getText(), 
				rstc.txtm1.getText(), rstc.txtm2.getText(), 
				rstc.txtm3.getText(), rstc.txtm4.getText() }; 
		
		return statesText;
	}
	
	

		
	
	
	/**
	 * save the states of the textfields 
	 * @param state
	 * @param states
	 */
	private void saveState(ArrayList<String> state, String[] states) {
		state.clear();
		for(String s : states) {
			state.add(s);
		}
	}
	

	
	
	
	
	
	
	
	/**
	 * set the textfields
	 * @param state
	 * @param rstc
	 */
	private void setState(ArrayList<String> state, RabinSecondTabComposite rstc) {
		rstc.txtCipherFirst.setText(state.get(0));
		rstc.txtmp.setText(state.get(1));
		rstc.txtmq.setText(state.get(2));
		rstc.txtyp.setText(state.get(3));
		rstc.txtyq.setText(state.get(4));
		rstc.txtV.setText(state.get(5));
		rstc.txtW.setText(state.get(6));
		rstc.txtm1.setText(state.get(7));
		rstc.txtm2.setText(state.get(8));
		rstc.txtm3.setText(state.get(9));
		rstc.txtm4.setText(state.get(10));
		
	}
	
	
	
	
	/**
	 * save chooseCipherIdx in text mode
	 * @param rstc
	 */
	private void saveChooseCipherIdxTextMode(RabinSecondTabComposite rstc) {
		cmbChooseCipherIdxTextMode = rstc.cmbChooseCipher.getSelectionIndex();
	}
	
	/**
	 * save chooseCipherIdx in num mode
	 * @param rstc
	 */
	private void saveChooseCipherIdxNumMode(RabinSecondTabComposite rstc) {
		cmbChooseCipherIdxNumMode = rstc.cmbChooseCipher.getSelectionIndex();
	}
	
	/**
	 * save chooseCipherIdx in decryption hex mode
	 * @param rstc
	 */
	private void saveChooseCipherIdxDecHexMode(RabinSecondTabComposite rstc) {
		cmbChooseCipherIdxDecHexMode = rstc.cmbChooseCipher.getSelectionIndex();
	}
	
	/**
	 * save chooseCipher in decryption decimal mode
	 * @param rstc
	 */
	private void saveChooseCipherIdxDecDecimalMode(RabinSecondTabComposite rstc) {
		cmbChooseCipherIdxDecDecimalMode = rstc.cmbChooseCipher.getSelectionIndex();
	}
	
	/**
	 * set chooseCipher num mode
	 * @param rstc
	 */
	private void setChooseCipherIdxNumMode(RabinSecondTabComposite rstc) {
		if(cmbChooseCipherIdxNumMode != -1) 
			rstc.cmbChooseCipher.select(cmbChooseCipherIdxNumMode);
	}
	
	/**
	 * set chooseCipher text mode
	 * @param rstc
	 */
	private void setChooseCipherIdxTextMode(RabinSecondTabComposite rstc) {
		if(cmbChooseCipherIdxTextMode != -1) 
			rstc.cmbChooseCipher.select(cmbChooseCipherIdxTextMode);
	}
	
	/**
	 * set chooseCipher decryption hex mode
	 * @param rstc
	 */
	private void setChooseCipherIdxDecHexMode(RabinSecondTabComposite rstc) {
		if(cmbChooseCipherIdxDecHexMode != -1) 
			rstc.cmbChooseCipher.select(cmbChooseCipherIdxDecHexMode);
	}
	
	/**
	 * set chooseCipher decryption decimal mode
	 * @param rstc
	 */
	private void setChooseCipherIdxDecDecimalMode(RabinSecondTabComposite rstc) {
		if(cmbChooseCipherIdxDecDecimalMode != -1) 
			rstc.cmbChooseCipher.select(cmbChooseCipherIdxDecDecimalMode);
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * set chooseCipher by filling combo and setting idx
	 * @param a
	 * @param rstc
	 */
	public void setChooseCipher(ArrayList<String> a, RabinSecondTabComposite rstc) {
		rstc.cmbChooseCipher.removeAll();
		if(a != null) {
			for(int i = 1; i <= a.size(); i++) {
				rstc.cmbChooseCipher.add(String.valueOf(i));
			}
		}
	}
	
	
	

	
	
	/**
	 * save num state
	 * @param rstc
	 */
	public void saveNumState(RabinSecondTabComposite rstc) {
		saveChooseCipherIdxNumMode(rstc);
		String[] decState = getDecState(rstc);
		saveState(stateNum, decState);
	}
	
	
	/**
	 * save text state
	 * @param rstc
	 */
	public void saveTextState(RabinSecondTabComposite rstc) {
		saveChooseCipherIdxTextMode(rstc);
		String[] decState = getDecState(rstc);
		saveState(stateText, decState);
	}
	
	
	/**
	 * save decryption hex state
	 * @param rstc
	 */
	public void saveDecHexState(RabinSecondTabComposite rstc) {
		saveChooseCipherIdxDecHexMode(rstc);
		String[] decState = getDecState(rstc);
		saveState(stateDecHex, decState);
	}
	
	
	/**
	 * save decryption decimal state
	 * @param rstc
	 */
	public void saveDecDecimalState(RabinSecondTabComposite rstc) {
		saveChooseCipherIdxDecDecimalMode(rstc);
		String[] decState = getDecState(rstc);
		saveState(stateDecDecimal, decState);
	}
	
	

	
	
	
	
	
	/**
	 * set text state
	 * @param ciphertextsTextMode
	 * @param rstc
	 */
	public void setTextState(ArrayList<String> ciphertextsTextMode, RabinSecondTabComposite rstc) {
		setChooseCipher(ciphertextsTextMode, rstc);
		setChooseCipherIdxTextMode(rstc);
		setState(stateText, rstc);
	}
	
	
	/**
	 * set num state
	 * @param ciphertextsNumMode
	 * @param rstc
	 */
	public void setNumState(ArrayList<String> ciphertextsNumMode, RabinSecondTabComposite rstc) {
		setChooseCipher(ciphertextsNumMode, rstc);
		setChooseCipherIdxNumMode(rstc);
		setState(stateNum, rstc);
	}
	
	
	
	/**
	 * set decryption hex state
	 * @param ciphertextsDecHexMode
	 * @param rstc
	 */
	public void setDecHexState(ArrayList<String> ciphertextsDecHexMode, RabinSecondTabComposite rstc) {
		setChooseCipher(ciphertextsDecHexMode, rstc);
		setChooseCipherIdxDecHexMode(rstc);
		setState(stateDecHex, rstc);
	}
	
	
	/**
	 * set decryption decimal state
	 * @param ciphertextsDecDecimalMode
	 * @param rstc
	 */
	public void setDecDecimalState(ArrayList<String> ciphertextsDecDecimalMode, RabinSecondTabComposite rstc) {
		setChooseCipher(ciphertextsDecDecimalMode, rstc);
		setChooseCipherIdxDecDecimalMode(rstc);
		setState(stateDecDecimal, rstc);
	}
	
	
	
		
}
