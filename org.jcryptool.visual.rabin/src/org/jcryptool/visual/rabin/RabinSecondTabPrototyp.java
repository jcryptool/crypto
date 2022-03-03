//package org.jcryptool.visual.rabin;
//
//import java.math.BigInteger;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Currency;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.CCombo;
//import org.eclipse.swt.custom.ScrolledComposite;
//import org.eclipse.swt.custom.StyledText;
//import org.eclipse.swt.events.FocusEvent;
//import org.eclipse.swt.events.FocusListener;
//import org.eclipse.swt.events.HelpEvent;
//import org.eclipse.swt.events.HelpListener;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.events.TypedEvent;
//import org.eclipse.swt.events.VerifyEvent;
//import org.eclipse.swt.events.VerifyListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.Point;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Combo;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Control;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Group;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Listener;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Text;
//import org.jcryptool.core.util.colors.ColorService;
//
//
//
//public class RabinSecondTabPrototyp extends Shell {
//	
//	private Group grpParam;
//	private Composite npqComp;
//	private Composite genPComp;
//	private Composite genQComp;
//	private Button btnGenPrimes;
//	private Text txtLowLimP;
//	private Text txtUpperLimP;
//	private Text txtLowLimQ;
//	private Text txtUpperLimQ;
//	private Text txtP;
//	private Text txtQ;
//	private Text txtModN;
//	private Group grpPlaintext;
//	private Text txtPlain;
//	private Button btnEnc;
//	private Group grpDec;
//	private Text txtCipherFirst;
//	private Group grpSqrRoot;
//	private Composite compMp;
//	private Text txtmp;
//	private Composite compMq;
//	private Text txtmq;
//	private Button btnSqrRoot;
//	private Group grpLinCon;
//	private Composite compMerge;
//	private Text txtyp;
//	private Text txtyq;
//	private Group grpPosPlain;
//	private Text txtm1;
//	private Text txtm2;
//	private Text txtm3;
//	private Text txtm4;
//	private Composite compAllPt;
//	private Button btnComputePt;
//	private Composite compMainContent;
//	private Button btnNextC;
//	private Button btnGenKeysMan;
//	private Button btnStartGenKeys;
//	private CCombo cmbP;
//	private CCombo cmbQ;
//	private BigInteger p;
//	private BigInteger q;
//	private BigInteger n;
//	private CCombo cmbBlockN;
//	private Button btnText;
//	private Button btnNum;
//	private int bytesPerBlock;
//	private Text txtMessage;
//	private StyledText txtMessageSep;
//	private StyledText txtMessageBase;
//	//private Text txtCipher;
//	private StyledText txtCipher;
//	private final Charset charset = StandardCharsets.UTF_8;
//	private GridData grpPlaintextData;
//	private GridData compEnterCiphertextData;
//	private Composite compEnterCiphertext;
//	private Composite compHoldAllSteps;
//	private Label lblMessage;
//	private Label lblMessageSep;
//	private GridData lblMessageSepData;
//	private GridData lblMessageData;
//	private GridData txtMessageData;
//	private GridData txtMessageSepData;
//	private String padding = "20";
//	private String separator = "||";
//	private int radix = 16;
//	private int blocklength;
//	private Composite encPartSteps;
//	private Button btnSelectionEnc;
//	private Button btnSelectionDec;
//	private Button btnApplyCiphertext;
//	private Composite rootComposite;
//	private CCombo cmbChooseCipher;
//	private ArrayList<String> plaintextsTextMode;
//	private ArrayList<String> ciphertextsTextMode;
//	private ArrayList<String> plaintextsNumMode;
//	private ArrayList<String> ciphertextsNumMode;
//	private ArrayList<String> ciphertextsDecHexMode;
//	private ArrayList<String> ciphertextsDecDecimalMode;
//	private ArrayList<String> currentCiphertextList;
//	private ArrayList<String> currentPlaintextList;
//	private Button btnComputeYpYq;
//	private Button btnComputevw;
//	private Text txtV;
//	private Text txtW;
//	private int currentIdx;
//	private Composite compEncStepsNum;
//	private GridData compEncStepsNumData;
//	private Composite compEncSteps;
//	private GridData compEncStepsData;
//	private int cmbChooseCipherIdxNumMode;
//	private int cmbChooseCipherIdxTextMode;
//	private int cmbChooseCipherIdxDecHexMode;
//	private int cmbChooseCipherIdxDecDecimalMode;
//	private ArrayList<String> plaintextEncodedList;
//	private Text txtMessageSepNum;
//	private StyledText txtMessageBaseNum;
//	private StyledText txtCipherNum;
//	private Composite compEnterCiphertextPart1;
//	private Composite compHoldDecimal;
//	private Button btnRadHex;
//	private Button btnRadDecimal;
//	private GridData compEnterCiphertextPart1Data;
//	private GridData compHoldDecimalData;
//	private Composite compEnterCiphertextSteps;
//	private Text txtEnterCiphertextDecimal;
//	private StyledText txtCiphertextSegmentsDecimal;
//	private Text txtEnterCiphertext;
//	private StyledText txtCiphertextSegments;
//	private Text txtWarningNpq;
//	private GridData txtWarningNpqData;
//	private Composite compBlockN;
//	private Text txtMessageSepNumWarning;
//	private GridData txtMessageSepNumWarningData;
//	private Text txtEnterCiphertextWarning;
//	private GridData txtEnterCiphertextWarningData;
//	private Text txtEnterCiphertextDecimalWarning;
//	private GridData txtEnterCiphertextDecimalWarningData;
//	private Text txtMessageWarning;
//	private GridData txtMessageWarningData;
//	private Button btnPrevElem;
//	private Button btnNextElem;
//	
//	//private String[] cmbChooseCipherStateText;
//	//private String[] cmbChooseCipherStateNum;
//	
//	
//	
//	
//	// saving states when switching between textmode and num mode
//	private ArrayList<String> stateText = new ArrayList<String>();
//	private ArrayList<String> stateNum = new ArrayList<String>();
//	private ArrayList<String> stateDecHex = new ArrayList<String>();
//	private ArrayList<String> stateDecDecimal = new ArrayList<String>();
//	
//	//private String[] stateText = new String[11];
//	//private String[] stateNum = new String[11];
//	
//	private void initStates() {
//		for(int i = 0; i < 11; i++) {
//			stateText.add("");
//			stateNum.add("");
//			stateDecHex.add("");
//			stateDecDecimal.add("");
//		}	
//	}
//	
//	/*private void initState() {
//		Arrays.fill(stateText, "");
//		Arrays.fill(stateNum, "");
//	}*/
//	
//	private String[] getDecState() {
//		String[] statesText = {txtCipherFirst.getText(),
//				txtmp.getText(), txtmq.getText(), txtyp.getText(),
//				txtyq.getText(), txtV.getText(), txtW.getText(), 
//				txtm1.getText(), txtm2.getText(), txtm3.getText(),
//				txtm4.getText() }; 
//		
//		return statesText;
//	}
//	
//	
//	
//	
//	private void saveState(ArrayList<String> state, String[] states) {
//		state.clear();
//		for(String s : states) {
//			state.add(s);
//		}
//	}
//	
//	/*private void saveState(String[] state, String[] data) {
//		Arrays.fill(state, "");
//		for(int i = 0; i < data.length; i++) {
//			state[i] = data[i];
//		}
//	}*/
//	
//	private void setState(ArrayList<String> state) {
//		txtCipherFirst.setText(state.get(0));
//		txtmp.setText(state.get(1));
//		txtmq.setText(state.get(2));
//		txtyp.setText(state.get(3));
//		txtyq.setText(state.get(4));
//		txtV.setText(state.get(5));
//		txtW.setText(state.get(6));
//		txtm1.setText(state.get(7));
//		txtm2.setText(state.get(8));
//		txtm3.setText(state.get(9));
//		txtm4.setText(state.get(10));
//	}
//	
//	private void saveChooseCipherIdxTextMode() {
//		cmbChooseCipherIdxTextMode = cmbChooseCipher.getSelectionIndex();
//	}
//	
//	private void saveChooseCipherIdxNumMode() {
//		cmbChooseCipherIdxNumMode = cmbChooseCipher.getSelectionIndex();
//	}
//	
//	private void saveChooseCipherIdxDecHexMode() {
//		cmbChooseCipherIdxDecHexMode = cmbChooseCipher.getSelectionIndex();
//	}
//	
//	private void saveChooseCipherIdxDecDecimalMode() {
//		cmbChooseCipherIdxDecDecimalMode = cmbChooseCipher.getSelectionIndex();
//	}
//	
//	private void setChooseCipheridxNumMode() {
//		if(cmbChooseCipherIdxNumMode != -1) 
//			cmbChooseCipher.select(cmbChooseCipherIdxNumMode);
//	}
//	
//	private void setChooseCipheridxTextMode() {
//		if(cmbChooseCipherIdxTextMode != -1) 
//			cmbChooseCipher.select(cmbChooseCipherIdxTextMode);
//	}
//	
//	private void setChooseCipheridxDecHexMode() {
//		if(cmbChooseCipherIdxDecHexMode != -1) 
//			cmbChooseCipher.select(cmbChooseCipherIdxDecHexMode);
//	}
//	
//	private void setChooseCipheridxDecDecimalMode() {
//		if(cmbChooseCipherIdxDecDecimalMode != -1) 
//			cmbChooseCipher.select(cmbChooseCipherIdxDecDecimalMode);
//	}
//	
//	private void setChooseCipher(ArrayList<String> a) {
//		cmbChooseCipher.removeAll();
//		if(a != null) {
//			for(int i = 1; i <= a.size(); i++) {
//				cmbChooseCipher.add(String.valueOf(i));
//			}
//		}
//	}
//	
//	private void saveTextState() {
//		saveChooseCipherIdxTextMode();
//		String[] decState = getDecState();
//		saveState(stateText, decState);
//	}
//	
//	private void setTextState() {
//		setChooseCipher(ciphertextsTextMode);
//		setChooseCipheridxTextMode();
//		setState(stateText);
//		currentPlaintextList = plaintextsTextMode;
//		currentCiphertextList = ciphertextsTextMode;
//	}
//	
//	private void saveNumState() {
//		saveChooseCipherIdxNumMode();
//		String[] decState = getDecState();
//		saveState(stateNum, decState);
//	}
//	
//	private void setNumState() {
//		setChooseCipher(ciphertextsNumMode);
//		setChooseCipheridxNumMode();
//		setState(stateNum);
//		currentPlaintextList = plaintextsNumMode;
//		currentCiphertextList = ciphertextsNumMode;
//	}
//	
//	private void saveDecHexState() {
//		saveChooseCipherIdxDecHexMode();
//		String[] decState = getDecState();
//		saveState(stateDecHex, decState);
//	}
//	
//	private void setDecHexState() {
//		setChooseCipher(ciphertextsDecHexMode);
//		setChooseCipheridxDecHexMode();
//		setState(stateDecHex);
//		currentCiphertextList = ciphertextsDecHexMode;
//	}
//	
//	private void saveDecDecimalState() {
//		saveChooseCipherIdxDecDecimalMode();
//		String[] decState = getDecState();
//		saveState(stateDecDecimal, decState);
//	}
//	
//	private void setDecDecimalState() {
//		setChooseCipher(ciphertextsDecDecimalMode);
//		setChooseCipheridxDecDecimalMode();
//		setState(stateDecDecimal);
//		currentCiphertextList = ciphertextsDecDecimalMode;
//	}
//	
//	
//	private boolean isSuitablePrime(BigInteger a) {
//		return a.isProbablePrime(1000) 
//				&& a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3));		
//	}
//	
//	private boolean isCompositeSuitable(BigInteger a, BigInteger b) {
//		return a.multiply(b).compareTo(BigInteger.valueOf(256)) > 0;
//	}
//	
//	private void selectionTextNumMode(int elem) {
//		int startIdxCipher = getStartIdx(elem, currentCiphertextList);
//		int endIdxCipher = getEndIdx(startIdxCipher, elem, currentCiphertextList);
//		
//		int startIdxPlain = getStartIdx(elem, currentPlaintextList);
//		int endIdxPlain = getEndIdx(startIdxPlain, elem, currentPlaintextList);
//		
//		if(btnText.getSelection()) {
//		
//			
//			txtCipher.setSelection(startIdxCipher, endIdxCipher);
//			txtMessageBase.setSelection(startIdxPlain, endIdxPlain);
//			
//			
//			int startIdxPlainEncoded = getStartIdx(elem, plaintextEncodedList);
//			int endIdxPlainEncoded = getEndIdx(startIdxPlainEncoded, elem, plaintextEncodedList);
//			txtMessageSep.setSelection(startIdxPlainEncoded, endIdxPlainEncoded);
//		}
//		
//		if(btnNum.getSelection()) {
//			txtMessageBaseNum.setSelection(startIdxPlain, endIdxPlain);
//			txtCipherNum.setSelection(startIdxCipher, endIdxCipher);
//		}
//	}
//	
//	private void selectionHexDecMode(int elem) {
//		int startIdxCipher = getStartIdx(elem, currentCiphertextList);
//		int endIdxCipher = getEndIdx(startIdxCipher, elem, currentCiphertextList);
//		
//		//int startIdxPlain = getStartIdx(elem, currentPlaintextList);
//		//int endIdxPlain = getEndIdx(startIdxPlain, elem, currentPlaintextList);
//		
//		if(btnRadHex.getSelection()) {
//			txtCiphertextSegments.setSelection(startIdxCipher, endIdxCipher);
//		}
//		if(btnRadDecimal.getSelection()) {
//			txtCiphertextSegmentsDecimal.setSelection(startIdxCipher, endIdxCipher);
//		}
//	}
//	
//	
//	private void nextElementTextNumMode() {
//		//int nextIdx = BigInteger.valueOf(currentIdx-1).mod(BigInteger.valueOf(currentCiphertextList.size())).intValue();
//		int nextIdx = (cmbChooseCipher.getSelectionIndex() + 1)
//				% currentCiphertextList.size();
//		int elem = nextIdx + 1;
//		String nextElem = currentCiphertextList.get(nextIdx);
//		txtCipherFirst.setText(nextElem);
//		
//		selectionTextNumMode(elem);
//		
//		cmbChooseCipher.select(nextIdx);
//	}
//	
//	private void prevElementTextNumMode() {
//		int nextIdx = BigInteger.valueOf(
//				cmbChooseCipher.getSelectionIndex()-1).mod(
//						BigInteger.valueOf(
//								currentCiphertextList.size())).intValue();
//		int elem = nextIdx + 1;
//		String nextElem = currentCiphertextList.get(nextIdx);
//		txtCipherFirst.setText(nextElem);
//		
//		selectionTextNumMode(elem);
//		
//		cmbChooseCipher.select(nextIdx);
//	}
//	
//	private void nextElementHexDecMode() {
//		int nextIdx = (cmbChooseCipher.getSelectionIndex() + 1)
//				% currentCiphertextList.size();
//		int elem = nextIdx + 1;
//		String nextElem = currentCiphertextList.get(nextIdx);
//		txtCipherFirst.setText(nextElem);
//		
//		selectionHexDecMode(elem);
//		
//		cmbChooseCipher.select(nextIdx);
//	}
//	
//	private void prevElementHexDecMode() {
//		int nextIdx = BigInteger.valueOf(
//				cmbChooseCipher.getSelectionIndex()-1).mod(
//						BigInteger.valueOf(
//								currentCiphertextList.size())).intValue();
//		int elem = nextIdx + 1;
//		String nextElem = currentCiphertextList.get(nextIdx);
//		txtCipherFirst.setText(nextElem);
//		
//		selectionHexDecMode(elem);
//		
//		cmbChooseCipher.select(nextIdx);
//	}
//	
//	
//	private VerifyListener vlNumbers = new VerifyListener() {
//		
//		@Override
//		public void verifyText(VerifyEvent e) {
//			// TODO Auto-generated method stub
//			
//			CCombo cmb = null;
//			
//			if(e.getSource() instanceof CCombo) {
//				cmb = (CCombo) e.getSource();
//			}
//			
//			boolean doit = true;
//			
//			if((cmb.getText().length() == 0 && e.text.compareTo("0") == 0)
//					|| !(e.text.matches("\\d"))
//					//|| cmb.getText().length() == 5
//					|| cmb == null)
//				doit = false;
//			
//			if(e.character == '\b')
//				doit = true;
//
//			e.doit = doit;
//			
//		}
//	};
//	
//private VerifyListener vlNumbersTest = new VerifyListener() {
//		
//		@Override
//		public void verifyText(VerifyEvent e) {
//			// TODO Auto-generated method stub
//			
//			Text txt = null;
//			
//			if(e.getSource() instanceof Text) {
//				txt = (Text) e.getSource();
//			}
//			
//			
//			boolean doit = true;
//			
//			if(!(e.text.matches("\\d| |\\|"))
//					|| txt == null)
//				doit = false;
//			
//			if(e.character == '\b')
//				doit = true;
//
//			e.doit = doit;
//			
//		}
//	};
//private Text txtInfoEnc;
//private Text txtInfoDec;
//
//
//
//	
//	
//	
//	private void hideTextWarning() {
//		txtWarningNpqData.exclude = true;
//		txtWarningNpq.setVisible(false);
//		npqComp.requestLayout();
//	}
//
//
//	
//	private void showTextWarning() {
//		txtWarningNpqData.exclude = false;
//		txtWarningNpq.setVisible(true);
//		npqComp.requestLayout();
//	}
//	
//	private void hideTextWarningNum(Text textToHide, GridData gd, Composite parent) {
//		gd.exclude = true;
//		textToHide.setVisible(false);
//		parent.requestLayout();
//	}
//	
//	private void showTextWarningNum(Text textToShow, GridData gd, Composite parent) {
//		gd.exclude = false;
//		textToShow.setVisible(true);
//		parent.requestLayout();
//	}
//	
//	
//	
//	
//	private void updateTextfields(TypedEvent e) {
//		
//		BigInteger ptmp = null;
//		BigInteger qtmp = null;
//		btnStartGenKeys.setEnabled(false);
//		String pAsStr = cmbP.getText();
//		String qAsStr = cmbQ.getText();
//		
//		if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
//			cmbP.setBackground(ColorService.RED);
//			cmbQ.setBackground(ColorService.RED);
//			txtWarningNpq.setText("Attention: p and q are missing");
//			showTextWarning();
//			
//			
//			
//		}
//		else if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
//			/*if(e.widget.equals(btnGenKeysMan)) {
//				cmbP.setBackground(ColorService.RED);
//				txtWarningNpq.setText("Attention: p is missing");
//			}
//			else*/
//			cmbP.setBackground(ColorService.RED);
//			qtmp = new BigInteger(qAsStr);
//			if(isSuitablePrime(qtmp)) {
//				//changeQ = qtmp;
//				cmbQ.setBackground(ColorService.GREEN);
//				txtWarningNpq.setText("Attention: p is missing");
//				showTextWarning();
//			}
//			else {
//				cmbQ.setBackground(ColorService.RED);
//				txtWarningNpq.setText("Attention:\n"
//						+ "1) p is missing\n"
//						+ "2) q is not a suitable prime");
//				showTextWarning();
//			}
//		}
//		else if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
//			cmbQ.setBackground(ColorService.RED);
//			ptmp = new BigInteger(pAsStr);
//			if(isSuitablePrime(ptmp)) {
//				//changeP = ptmp;
//				cmbP.setBackground(ColorService.GREEN);
//				txtWarningNpq.setText("Attention: q is missing");
//				showTextWarning();
//			}
//			else {
//				cmbP.setBackground(ColorService.RED);
//				txtWarningNpq.setText("Attention:\n"
//						+ "1) p is not a suitable prime\n"
//						+ "2) q is missing");
//				showTextWarning();
//				
//			}
//		}
//		else {
//			ptmp = new BigInteger(pAsStr);
//			qtmp = new BigInteger(qAsStr);
//			
//			if(isSuitablePrime(ptmp) && isSuitablePrime(qtmp)) {
//				
//				//changeP = ptmp;
//				//changeQ = qtmp;
//				//cmbP.setBackground(ColorService.GREEN);
//				//cmbQ.setBackground(ColorService.GREEN);
//				
//				//if(!changeP.equals(changeQ))
//				/*if(!ptmp.equals(qtmp) && isCompositeSuitable(ptmp, qtmp) 
//						&& btnGenKeysMan.getSelection()) {
//					btnStartGenKeys.setEnabled(true);
//					hideTextWarning();
//				}*/
//				if(!ptmp.equals(qtmp)) {
//					if(isCompositeSuitable(ptmp, qtmp)) {
//						cmbP.setBackground(ColorService.GREEN);
//						cmbQ.setBackground(ColorService.GREEN);
//						if(btnGenKeysMan.getSelection()) {
//							btnStartGenKeys.setEnabled(true);
//							hideTextWarning();
//						}
//						else {
//							txtWarningNpq.setText("Attention: select"
//									+ " \"Generate private and public key manually\"");
//							showTextWarning();
//						}
//					}
//					else {
//						cmbP.setBackground(ColorService.RED);
//						cmbQ.setBackground(ColorService.RED);
//						txtWarningNpq.setText("Attention: "
//								+ "N = p \u2219 q < 256");
//						showTextWarning();
//					}
//				}
//				else {
//					cmbP.setBackground(ColorService.RED);
//					cmbQ.setBackground(ColorService.RED);
//					txtWarningNpq.setText("Attention: p = q but the condition p \u2260 q must be satisfied");
//					showTextWarning();
//				}
//			}
//			else if(isSuitablePrime(ptmp) && !isSuitablePrime(qtmp)) {
//				//changeP = ptmp;
//				cmbP.setBackground(ColorService.GREEN);
//				cmbQ.setBackground(ColorService.RED);
//				txtWarningNpq.setText("Attention: q is not a suitable prime");
//				showTextWarning();
//			}
//			else if(!isSuitablePrime(ptmp) && isSuitablePrime(qtmp)) {
//				//changeQ = qtmp;
//				cmbP.setBackground(ColorService.RED);
//				cmbQ.setBackground(ColorService.GREEN);
//				txtWarningNpq.setText("Attention: p is not a suitable prime");
//				showTextWarning();
//			}
//			else if(!isSuitablePrime(ptmp) && !isSuitablePrime(qtmp)) {
//				cmbP.setBackground(ColorService.RED);
//				cmbQ.setBackground(ColorService.RED);
//				txtWarningNpq.setText("Attention: p and q are not suitable primes");
//				showTextWarning();
//			}
//		}
//		
//	}
//	
//	private void btnGenKeysManAction() {
//		
//		String pAsStr = cmbP.getText();
//		String qAsStr = cmbQ.getText();
//		p = new BigInteger(pAsStr);
//		q = new BigInteger(qAsStr);
//		n = p.multiply(q);
//		txtModN.setText(n.toString());
//		
//		/*q = changeQ;
//		n = p.multiply(q);
//		String nAsString = n.toString();
//		txtModN.setText(nAsString);*/
//	}
//	
//	
//	private void setSizeInfoText(Text txt, GridData gd, int minWidth, int minHeigth) {
//		gd.minimumWidth = minWidth;
//		gd.minimumHeight = minHeigth;
//		gd.widthHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//		gd.heightHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
//	}
//	
//	private void setSizeInfoText(StyledText txt, GridData gd, int minWidth, int minHeigth) {
//		gd.minimumWidth = minWidth;
//		gd.minimumHeight = minHeigth;
//		gd.widthHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//		gd.heightHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
//	}
//	
//	private void setSizeWarningText(Text txt, GridData gd, int minWidth, int minHeigth) {
//		gd.minimumWidth = minWidth;
//		gd.minimumHeight = minHeigth;
//		gd.widthHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//	}
//	
//	
//	private int getStartIdx(int elem, ArrayList<String> a) {
//		int startIdx = 0;
//		for(int i = 0; i < elem - 1; i++) {
//			startIdx += a.get(i).length();
//			startIdx += separator.length() + 2;
//		}
//		return startIdx;
//	}
//	
//	private int getEndIdx(int startIdx, int elem, ArrayList<String> a) {
//		int endIdx = startIdx + a.get(elem - 1).length();
//		return endIdx;
//	}
//	
//	
//	private void handleDecimalNumbersEncDecMode() {
//		String plaintext = txtMessageSepNum.getText();
//		
//		if(plaintext.isEmpty()) {
//			txtMessageSepNum.setBackground(ColorService.WHITE);
//			hideTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//			btnEnc.setEnabled(false);
//			return;
//		}
//		
//		if(n == null) {
//			txtMessageSepNum.setBackground(ColorService.RED);
//			txtMessageSepNumWarning.setText("Attention: "
//					+ "please generate a private and public key first");
//			showTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//			btnEnc.setEnabled(false);
//			return;
//		}
//		
//		boolean validPlaintext = plaintext.matches("^(\\d+( \\|\\| \\d+)*)$");
//		ArrayList<String> plaintextList = null;
//		if(validPlaintext) {
//			plaintextList = Rabin.getParsedPlaintextInBase10(plaintext);
//			boolean isPlaintextValidN = Rabin.isValidPlaintext(plaintextList, 10);
//			if(!isPlaintextValidN) {
//				txtMessageSepNum.setBackground(ColorService.RED);
//				txtMessageSepNumWarning.setText("Attention: "
//						+ "there exist one or more p[i] with "
//						+ "p[i] \u2265 N, but the condition "
//						+ "p[i] < N must be satisfied");
//				showTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//				btnEnc.setEnabled(false);
//			}
//			else {
//				txtMessageSepNum.setBackground(ColorService.GREEN);
//				hideTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//				btnEnc.setEnabled(true);
//			}
//		}
//		else {
//			txtMessageSepNum.setBackground(ColorService.RED);
//			txtMessageSepNumWarning.setText("Attention:\n"
//					+ "not a valid plaintext. The plaintext has to be "
//					+ "of the form \"decimal number\" or \"decimal number 1 || decimal number 2 || ...\"");
//			showTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//			btnEnc.setEnabled(false);
//		}
//	}
//	
//	private void handleHexNumDecMode() {
//		String ciphertextParsed = txtEnterCiphertext.getText();
//		String ciphertext = ciphertextParsed.replaceAll("\\s+", "");
//		
//		if(ciphertext.isEmpty()) {
//			txtEnterCiphertext.setBackground(ColorService.WHITE);
//			hideTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		if(n == null) {
//			txtEnterCiphertext.setBackground(ColorService.RED);
//			txtEnterCiphertextWarning.setText("Attention: "
//					+ "generate a private and public key first");
//			showTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		boolean matchRegex = ciphertext.matches("[0-9a-fA-F]+");
//		if(!matchRegex) {
//			txtEnterCiphertext.setBackground(ColorService.RED);
//			txtEnterCiphertextWarning.setText("Attention: only hexadecimal numbers "
//					+ "[0-f/F] are allowed");
//			showTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		if(ciphertext.length() % blocklength != 0) {
//			txtEnterCiphertext.setBackground(ColorService.RED);
//			txtEnterCiphertextWarning.setText("Attention: "
//					+ "the length of the entered ciphertext must be "
//					+ "a multiple of the blocklength = " + (blocklength / 2) + " " + "Byte");
//			showTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		ArrayList<String> ciphertextList = Rabin.parseString(ciphertext, blocklength);
//		boolean isValidCiphertext = Rabin.isValidPlaintext(ciphertextList, radix);
//		
//		if(!isValidCiphertext) {
//			txtEnterCiphertext.setBackground(ColorService.RED);
//			txtEnterCiphertextWarning.setText("Attention:\n"
//					+ "there is one or more ciphertextblock c[i] with c[i] \u2265 N.\n"
//					+ "The condition c[i] < N must be satisfied");
//			
//			showTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		txtEnterCiphertext.setBackground(ColorService.GREEN);
//		hideTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//		btnApplyCiphertext.setEnabled(true);
//	}
//	
//	
//	private void handleDecimalNumbersDecMode() {
//		String plaintext = txtEnterCiphertextDecimal.getText();
//		
//		if(plaintext.isEmpty()) {
//			txtEnterCiphertextDecimal.setBackground(ColorService.WHITE);
//			hideTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		if(n == null) {
//			txtEnterCiphertextDecimal.setBackground(ColorService.RED);
//			txtEnterCiphertextDecimalWarning.setText("Attention: "
//					+ "please generate a private and public key first");
//			showTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//			btnApplyCiphertext.setEnabled(false);
//			return;
//		}
//		
//		boolean validPlaintext = plaintext.matches("^(\\d+( \\|\\| \\d+)*)$");
//		ArrayList<String> plaintextList = null;
//		if(validPlaintext) {
//			plaintextList = Rabin.getParsedPlaintextInBase10(plaintext);
//			boolean isPlaintextValidN = Rabin.isValidPlaintext(plaintextList, 10);
//			if(!isPlaintextValidN) {
//				txtEnterCiphertextDecimal.setBackground(ColorService.RED);
//				txtEnterCiphertextDecimalWarning.setText("Attention: "
//						+ "there exist one or more p[i] with "
//						+ "p[i] \u2265 N, but the condition "
//						+ "p[i] < N must be satisfied");
//				showTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//				btnApplyCiphertext.setEnabled(false);
//			}
//			else {
//				txtEnterCiphertextDecimal.setBackground(ColorService.GREEN);
//				hideTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//				btnApplyCiphertext.setEnabled(true);
//			}
//		}
//		else {
//			txtEnterCiphertextDecimal.setBackground(ColorService.RED);
//			txtEnterCiphertextDecimalWarning.setText("Attention: "
//					+ "not a valid plaintext. The plaintext has to be "
//					+ "of the form \"number\" or \"number1 || number 2 || ...\"");
//			showTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//			btnApplyCiphertext.setEnabled(false);
//		}
//	}
//	
//	
//	private void chooseCipherAction() {
//		int idx = cmbChooseCipher.getSelectionIndex();
//		String item = cmbChooseCipher.getItem(idx);
//		int i = Integer.parseInt(item);
//		int chosenIdx = i - 1;
//		int elem = chosenIdx + 1;
//		String ciphertext = currentCiphertextList.get(chosenIdx);
//		txtCipherFirst.setText(ciphertext);
//		
//		
//		if(btnSelectionEnc.getSelection()) {
//			selectionTextNumMode(elem);
//		}
//		if(btnSelectionDec.getSelection()) {
//			selectionHexDecMode(elem);
//		}
//	}
//	
//	private void fixSelection(ArrayList<String> a, StyledText stxt) {
//		int idx = cmbChooseCipher.getSelectionIndex();
//		if(idx == -1)
//			return;
//		
//		int elem = idx + 1;
//		int startIdx = getStartIdx(elem, a);
//		int endIdx = getEndIdx(startIdx, elem, a);
//		stxt.setSelection(startIdx, endIdx);
//	}
//	
//	private void setFinalPlaintextColor() {
//		int idx = cmbChooseCipher.getSelectionIndex();
//		if(idx == -1)
//			return;
//		
//		String currentPlaintext = currentPlaintextList.get(idx);
//		
//		String m1 = txtm1.getText();
//		String m2 = txtm2.getText();
//		String m3 = txtm3.getText();
//		String m4 = txtm4.getText();
//		
//		if(m1.equals(currentPlaintext)) {
//			txtm1.setBackground(ColorService.GREEN);
//		}
//		if(m2.equals(currentPlaintext)) {
//			txtm2.setBackground(ColorService.GREEN);
//		}
//		if(m3.equals(currentPlaintext)) {
//			txtm3.setBackground(ColorService.GREEN);
//		}
//		if(m4.equals(currentPlaintext)) {
//			txtm4.setBackground(ColorService.GREEN);
//		}
//	}
//	
//	private void resetFinalPlaintextColor() {
//		txtm1.setBackground(ColorService.WHITE);
//		txtm2.setBackground(ColorService.WHITE);
//		txtm3.setBackground(ColorService.WHITE);
//		txtm4.setBackground(ColorService.WHITE);
//	}
//	
//	
//	private void handlePlaintextTextMode() {
//		if(txtMessage.getText().isEmpty()) {
//			hideTextWarningNum(txtMessageWarning, txtMessageWarningData, compEncSteps);
//			txtMessage.setBackground(ColorService.WHITE);
//			btnEnc.setEnabled(false);
//			return;
//		}
//		
//		
//		if(n == null) {
//			txtMessage.setBackground(ColorService.RED);
//			txtMessageWarning.setText("Attention: "
//					+ "generate a private and public key first");
//			showTextWarningNum(txtMessageWarning, txtMessageWarningData, compEncSteps);
//			btnEnc.setEnabled(false);
//			return;
//		}
//		
//		
//		if(cmbBlockN.getSelectionIndex() == -1) {
//			txtMessage.setBackground(ColorService.RED);
//			txtMessageWarning.setText("Attention: select \"Bytes per block\"");
//			showTextWarningNum(txtMessageWarning, txtMessageWarningData, compEncSteps);
//			btnEnc.setEnabled(false);
//			return;
//			
//		}
//		
//		txtMessage.setBackground(ColorService.WHITE);
//		hideTextWarningNum(txtMessageWarning, txtMessageWarningData, compEncSteps);
//		btnEnc.setEnabled(true);
//	}
//	
//	
//	private void resetDecComponents() {
//		txtmp.setText("");
//		txtmq.setText("");
//		txtyp.setText("");
//		txtyq.setText("");
//		txtV.setText("");
//		txtW.setText("");
//		txtm1.setText("");
//		txtm2.setText("");
//		txtm3.setText("");
//		txtm4.setText("");
//	}
//	
//	private void initializePrimes(int numOfPrimes) {
//		
//		for(int i = 0, count = 0; count < numOfPrimes; i++) {
//			BigInteger possiblePrime = BigInteger.valueOf(i);
//			if(this.isSuitablePrime(possiblePrime)) {
//				cmbP.add(possiblePrime.toString());
//				cmbQ.add(possiblePrime.toString());
//				count++;
//			}
//		}
//	}
//	
//	
//	private void initializeComponents() {
//		btnGenKeysMan.setSelection(true);
//		btnStartGenKeys.setEnabled(false);
//		btnSelectionEnc.setSelection(true);
//		btnText.setSelection(true);
//		btnRadHex.setSelection(true);
//		//cmbBlockN.setEnabled(false);
//		btnEnc.setEnabled(false);
//		//cmbChooseCipher.setEnabled(false);
//		btnPrevElem.setEnabled(false);
//		btnNextElem.setEnabled(false);
//		btnSqrRoot.setEnabled(false);
//		btnComputeYpYq.setEnabled(false);
//		btnComputevw.setEnabled(false);
//		btnComputePt.setEnabled(false);
//		btnApplyCiphertext.setEnabled(false);
//	}
//	
//	
//	
//	
//	
//	private void setInfoEncTextMode() {
//		txtInfoEnc.setText("To encrypt a plaintext in \"Text\" mode do the following:\n"
//				+ "1) Select \"Bytes per block\" to choose how many bytes you want "
//				+ "to encrypt at once\n"
//				+ "2) Enter a plaintext in the field \"Plaintext\"\n"
//				+ "3) Click on \"Encrypt\"");
//		
//	}
//	
//	private void setInfoEncDecimalMode() {
//		txtInfoEnc.setText("To encrypt a plaintext in \"Decimal numbers\" mode do the following:\n"
//				+ "1) Enter a plaintext in the field \"Plaintext seperated into segments (\"||\" as separator)\".\n"
//				+ "Pay attention to enter the plaintext in the format \"decimal number\" or "
//				+ "\"decimal number 1 || decimal number 2 || ...\"\n"
//				+ "3) Click on \"Encrypt\"");
//	}
//	
//	private void setInfoDecHexMode() {
//		txtInfoDec.setText("To decrypt a ciphertext c[i] in \"Hex\" mode do the following:\n"
//				+ "1) Enter a ciphertext in the field \"Ciphertext in base 16 format\"\n"
//				+ "2) Click on \"Apply\"\n"
//				+ "3) Either Use the drop-down list or the buttons \"c[i-1]\" "
//				+ "and \"c[i+1]\" to choose a ciphertext c[i]\n"
//				+ "4) Click on \"Compute square roots mod p and q\"\n"
//				+ "5) Click on \"Compute y_p and y_q\" to compute the inverse values of p and q"
//				+ " for the Chinese Remainder Theorem (CRT)\n"
//				+ "6) Click on \"Compute v and w\" to compute the intermediate values\n"
//				+ "7) Click on \"Compute all plaintexts\" to compute all plaintexts using the CRT\n");
//
//	}
//	
//	private void setInfoDecDecimalMode() {
//		txtInfoDec.setText("To decrypt a ciphertext c[i] in \"Decimal numbers\" mode do the following:\n"
//				+ "1) Enter a ciphertext in the field \"Ciphertext in base 10 format seperated into segments (\"||\" as separator)\"\n"
//				+ "Pay attention to enter the ciphertext in the format "
//				+ "\"decimal number\" or \"decimal number 1 || decimal number 2 || ...\"\n"
//				+ "2) Click on \"Apply\"\n"
//				+ "3) Either Use the drop-down list or the buttons \"c[i-1]\" "
//				+ "and \"c[i+1]\" to choose a ciphertext c[i]\n"
//				+ "4) Click on \"Compute square roots mod p and q\"\n"
//				+ "5) Click on \"Compute y_p and y_q\" to compute the inverse values of p and q"
//				+ " for the Chinese Remainder Theorem (CRT)\n"
//				+ "6) Click on \"Compute v and w\" to compute the intermediate values\n"
//				+ "7) Click on \"Compute all plaintexts\" to compute all plaintexts using the CRT\n");
//
//	}
//	
//	
//	private void createSetParamContent(Composite parent) {
//		
//		
//		// create main group for setting primes and modulus N
//		grpParam = new Group(parent, SWT.NONE);
//		grpParam.setLayout(new GridLayout(5, false));
//		grpParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		grpParam.setText("Step 1 - Setting parameters");
//		
//		// create composite for entering primes p,q manually
//		npqComp = new Composite(grpParam, SWT.BORDER);
//		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		npqComp.setLayout(new GridLayout(2, false));
//		
//		// create label and combo for prime p in npq composite
//		Label lblPrimeP = new Label(npqComp, SWT.NONE);
//		lblPrimeP.setText("p =");
//		cmbP = new CCombo(npqComp, SWT.SIMPLE);
//		cmbP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		cmbP.setVisibleItemCount(10);
//		//txtP = new Text(npqComp, SWT.BORDER);
//		//txtP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		
//		cmbP.addVerifyListener(vlNumbers);
//		
//		cmbP.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				// TODO Auto-generated method stub
//				updateTextfields(e);
//			}
//		});
//		
//		cmbP.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				cmbP.setBackground(ColorService.WHITE);
//				int idx = cmbP.getSelectionIndex();
//				String item = cmbP.getItem(idx);
//				
//				cmbP.removeVerifyListener(vlNumbers);
//				cmbP.setText(item);
//				cmbP.addVerifyListener(vlNumbers);
//				
//			}
//			
//		});
//		
//		// create label and combo for prime q in npq composite
//		Label lblPrimeQ = new Label(npqComp, SWT.NONE);
//		lblPrimeQ.setText("q =");
//		cmbQ = new CCombo(npqComp, SWT.SIMPLE);
//		cmbQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		cmbQ.setVisibleItemCount(10);
//		//txtQ = new Text(npqComp, SWT.BORDER);
//		//txtQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		
//		cmbQ.addVerifyListener(vlNumbers);
//		
//		cmbQ.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				// TODO Auto-generated method stub
//				
//				updateTextfields(e);
//			}
//		});
//		
//		
//		cmbQ.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				//cmbQ.setBackground(ColorService.WHITE);
//				int idx = cmbQ.getSelectionIndex();
//				String item = cmbQ.getItem(idx);
//				
//				cmbQ.removeVerifyListener(vlNumbers);
//				cmbQ.setText(item);
//				cmbQ.addVerifyListener(vlNumbers);
//				
//			}
//			
//		});
//		
//		// create label for N in npq composite
//		Label lblModN = new Label(npqComp, SWT.NONE);
//		lblModN.setText("N =");
//		txtModN = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
//		txtModN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//
//		txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
//		txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
//		setSizeWarningText(txtWarningNpq, txtWarningNpqData, SWT.DEFAULT, SWT.DEFAULT);
//		txtWarningNpq.setLayoutData(txtWarningNpqData);
//		txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
//		txtWarningNpq.setForeground(ColorService.RED);
//		hideTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
//		//txtWarningNpq.setText("aaaaaaaaaaaaa aaaaaaaaaaa, aaaaaaaaaa aaaaaaaaaaaaaaaaaaaa");
//		/*txtWarningNpqData.exclude = true;
//		txtWarningNpq.setVisible(false);
//		npqComp.requestLayout();*/
//		
//		 
//		
//		Composite compGenKeys = new Composite(grpParam, SWT.NONE);
//		compGenKeys.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		compGenKeys.setLayout(new GridLayout(1, false));
//		
//		// label separating group for gen keys and info
//		Label lblSepKeysInfo = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
//		lblSepKeysInfo.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
//		
//		// textInfo
//		Text txtInfoGenKeys = new Text(grpParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
//		GridData txtInfoKeysData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		setSizeInfoText(txtInfoGenKeys, txtInfoKeysData, SWT.DEFAULT, SWT.DEFAULT);
//		txtInfoGenKeys.setLayoutData(txtInfoKeysData);
//		String txtInfoGenKeysInfo = "You have three possibilities to choose primes"
//				+ " p and q:\n"
//				+ "1) Enter p and q manually in the corresponding fields.\n"
//				+ "1.1) Select \"Generate private and public key manually\" and click on \"Start\"\n"
//				+ "2) Enter p and q using the drop down list\n"
//				+ "2.1) Select \"Generate private and public key manually\" and click on \"Start\"\n" 
//				+ "3) Select \"Use private and public key generated in Cryptosystem tab\"\n"
//				+ "3.1) Click on \"Start\"";
//		txtInfoGenKeys.setText(txtInfoGenKeysInfo);
//		txtInfoGenKeys.setBackground(ColorService.LIGHTGRAY);
//		
//		//txtInfoKeysData.minimumHeight = 250;
//		/*txtInfoKeysData.widthHint = txtInfoGenKeys.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//		txtInfoKeysData.heightHint = txtInfoGenKeys.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
//		txtInfoGenKeys.setLayoutData(txtInfoKeysData);
//		txtInfoGenKeys.setText("You have three possibilities to choose primes"
//				+ " p and q:\n"
//				+ "1) Enter p and q manually in the corresponding fields.\n"
//				+ "1.1) Select \"Generate private and public key manually\" and click on \"Start\"\n"
//				+ "2) Enter p and q using the drop down list\n"
//				+ "2.1) Select \"Generate private and public key manually\" and click on \"Start\"\n" 
//				+ "3) Select \"Use private and public key generated in Cryptosystem tab\"\n"
//				+ "3.1) Click on \"Start\"");
//		txtInfoGenKeys.setBackground(grpParam.getBackground());*/
//		
//		//Composite compGenKeysCont = new Composite(compGenKeys, SWT.NONE);
//		//compGenKeysCont.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		//compGenKeysCont.setLayout(new GridLayout(2, false));
//		
//		btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
//		btnGenKeysMan.setText("Generate private and public key manually");
//		btnGenKeysMan.setSelection(true);
//		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//					updateTextfields(e);
//				}
//				
//				
//				
//				//cmbBlockN.setItems(blockItems);
//				
//			}
//			
//		});
//		//Label lblDescMan = new Label(compGenKeysCont, SWT.NONE);
//		//lblDescMan.setText("Generate private and public key manually");
//		
//		//Button btnGenKeys = new Button(compGenKeysCont, SWT.RADIO);
//		//Label lblDescGenKeys = new Label(compGenKeysCont, SWT.NONE);
//		//lblDescGenKeys.setText("Generate private and public key");
//		
//		Button btnGenKeysAlgo = new Button(compGenKeys, SWT.RADIO);
//		btnGenKeysAlgo.setText("Use private and public key generated in Cryptosystem tab");
//		//Label lblDescAlgo = new Label(compGenKeysCont, SWT.NONE);
//		//lblDescAlgo.setText("Use private and public key generated in Cryptosystem tab");
//		
//		btnStartGenKeys = new Button(compGenKeys, SWT.PUSH);
//		GridData btnStartGenData = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
//		btnStartGenData.horizontalIndent = 5;
//		btnStartGenKeys.setLayoutData(btnStartGenData);
//		btnStartGenKeys.setText("Start");		
//		
//		btnStartGenKeys.addSelectionListener(new SelectionAdapter() {
//		
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				if(btnGenKeysMan.getSelection()) {
//					btnGenKeysManAction();
//				}
//				
//				int bitlength = n.bitLength();
//				int maxbytesPerBlock = bitlength / 8;
//				int blocklengthtmp = ((bitlength / 8) + 1) * 2;
//				blocklength = blocklengthtmp;
//				bytesPerBlock = 2;
//				//String blockItems = "";
//				
//				cmbBlockN.removeAll();
//				
//				for(int i = 1; i <= maxbytesPerBlock; i++) {
//					cmbBlockN.add(String.valueOf(i));
//				}
//				
//				cmbBlockN.select(0);
//				
//				// for eliminate warnings
//				handlePlaintextTextMode();
//				handleDecimalNumbersEncDecMode();
//				handleHexNumDecMode();
//				handleDecimalNumbersDecMode();
//				
//				// for current mode
//				if(btnSelectionEnc.getSelection()) {
//					if(btnText.getSelection()) {
//						handlePlaintextTextMode();
//					}
//					if(btnNum.getSelection()) {
//						handleDecimalNumbersEncDecMode();
//					}
//				}
//				
//				if(btnSelectionDec.getSelection()) {
//					if(btnRadHex.getSelection()) {
//						handleHexNumDecMode();
//					}
//					if(btnRadDecimal.getSelection()) {
//						handleDecimalNumbersDecMode();
//					}
//				}
//				
//				// TODO: maybe resetting content of encryption/decryption
//				
//			}
//			
//			
//		});
//			
//	}
//	
//	private void createEncContent(Composite parent) {
//		// create plaintext part
//		grpPlaintext = new Group(parent, SWT.NONE);
//		grpPlaintextData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		grpPlaintext.setLayoutData(grpPlaintextData);
//		grpPlaintext.setLayout(new GridLayout(5, false));
//		grpPlaintext.setText("Step 2 - Encryption");
//		
//		compBlockN = new Composite(grpPlaintext, SWT.BORDER);
//		GridData compBlockNData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
//		compBlockN.setLayoutData(compBlockNData);
//		compBlockN.setLayout(new GridLayout(1, false));
//		
//		Label lblBlockN = new Label(compBlockN, SWT.NONE);
//		lblBlockN.setText("Bytes per block");
//		cmbBlockN = new CCombo(compBlockN, SWT.DROP_DOWN | SWT.READ_ONLY);
//		cmbBlockN.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		cmbBlockN.setVisibleItemCount(10);
//		//cmbBlockN.add("1");
//		cmbBlockN.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				int idx = cmbBlockN.getSelectionIndex();
//				String item = cmbBlockN.getItem(idx);
//				bytesPerBlock = Integer.parseInt(item) * 2;
//				
//				/*if(btnText.getSelection()
//						&& !txtMessage.getText().isEmpty()) {
//					btnEnc.setEnabled(true);
//				}
//				else
//					btnEnc.setEnabled(false);*/
//				
//				handlePlaintextTextMode();
//				
//				//System.out.println("cmbBlockN Text = " + cmbBlockN.getText());
//			}
//			
//			
//		});
//		
//		
//		
//		encPartSteps = new Composite(grpPlaintext, SWT.BORDER);
//		encPartSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		encPartSteps.setLayout(new GridLayout(1, false));
//		
//		// create composite for numbers and text selection
//		Composite compSelNumTxt = new Composite(encPartSteps, SWT.NONE);
//		compSelNumTxt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		compSelNumTxt.setLayout(new GridLayout(2, false));
//		
//		btnText = new Button(compSelNumTxt, SWT.RADIO);
//		btnText.setText("Text");
//		btnNum = new Button(compSelNumTxt, SWT.RADIO);
//		btnNum.setText("Decimal numbers");
//		
//		
//		btnNum.addSelectionListener(new SelectionAdapter() {
//		
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//		
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//					compEncStepsData.exclude = true;
//					compEncSteps.setVisible(false);
//					compEncStepsNumData.exclude = false;
//					compEncStepsNum.setVisible(true);
//					compBlockNData.exclude = true;
//					compBlockN.setVisible(false);
//					
//					encPartSteps.requestLayout();
//					
//					
//					// save and restore states
//					saveTextState();
//					setNumState();
//					
//					handleDecimalNumbersEncDecMode();
//					
//					resetFinalPlaintextColor();
//					
//					// set color of correct computed plaintext
//					setFinalPlaintextColor();
//					
//					// set info text
//					setInfoEncDecimalMode();
//					
//					
//					
//					
//				}
//				
//			}
//			
//			
//		});
//		
//		btnText.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//					compEncStepsNumData.exclude = true;
//					compEncStepsNum.setVisible(false);
//					compEncStepsData.exclude = false;
//					compEncSteps.setVisible(true);
//					compBlockNData.exclude = false;
//					compBlockN.setVisible(true);
//					
//					encPartSteps.requestLayout();
//					
//					// save and set state
//					saveNumState();
//					setTextState();
//					
//				
//					// verify input
//					handlePlaintextTextMode();
//					
//					// set color of correctly computed plaintext 
//					resetFinalPlaintextColor();
//					setFinalPlaintextColor();
//					
//					// set info text
//					setInfoEncTextMode();
//					
//					
//					
//				}
//			}
//			
//		});
//		
//		
//		compEncStepsNum = new Composite(encPartSteps, SWT.NONE);
//		compEncStepsNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		compEncStepsNum.setLayoutData(compEncStepsNumData);
//		compEncStepsNum.setLayout(new GridLayout(1, false));
//		
//		compEncStepsNumData.exclude = true;
//		compEncStepsNum.setVisible(false);
//		encPartSteps.requestLayout();
//		
//		
//		Label lblMessageSepNum = new Label(compEncStepsNum, SWT.NONE);
//		//lblMessageSep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		lblMessageSepNum.setText("Plaintext separated into segments (\"||\" as separator)");
//		//GridData lblMessageSepNumData = new GridData();
//		txtMessageSepNum = new Text(compEncStepsNum, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL);
//		GridData txtMessageSepNumData = new GridData(SWT.FILL, SWT.CENTER, true, true);
//		txtMessageSepNum.setLayoutData(txtMessageSepNumData);
//		setSizeInfoText(txtMessageSepNum, txtMessageSepNumData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtMessageSepNum.addModifyListener(new ModifyListener() {
//				
//			@Override
//			public void modifyText(ModifyEvent e) {
//				// TODO Auto-generated method stub
//				handleDecimalNumbersEncDecMode();
//			}
//		});
//		
//		txtMessageSepNumWarning = new Text(compEncStepsNum, SWT.MULTI | SWT.WRAP);
//		txtMessageSepNumWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtMessageSepNumWarning.setLayoutData(txtMessageSepNumWarningData);
//		setSizeWarningText(txtMessageSepNumWarning, txtMessageSepNumWarningData, SWT.DEFAULT, SWT.DEFAULT);
//		txtMessageSepNumWarning.setBackground(ColorService.LIGHTGRAY);
//		txtMessageSepNumWarning.setForeground(ColorService.RED);
//		//txtMessageSepNumWarning.setText("this is a test iuhiuhiuhuhihu iuiuguzfjvuv utfubuzv uzguzgiug iugzuizgi izguzg uzguzfigz uzguizgfi");
//		hideTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//		//showTextWarningNum(txtMessageSepNumWarning, txtMessageSepNumWarningData, compEncStepsNum);
//		
//		Label lblMessageBaseNum = new Label(compEncStepsNum, SWT.NONE);
//		lblMessageBaseNum.setText("Plaintext in base 16 format");
//		txtMessageBaseNum = new StyledText(compEncStepsNum, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtMessageBaseNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtMessageBaseNum.setLayoutData(txtMessageBaseNumData);
//		txtMessageBaseNum.setSelectionBackground(ColorService.GREEN);
//		txtMessageBaseNum.setSelectionForeground(ColorService.BLACK);
//		setSizeInfoText(txtMessageBaseNum, txtMessageBaseNumData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtMessageBaseNum.addSelectionListener(new SelectionAdapter() {
//		
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fixSelection(currentPlaintextList, txtMessageBaseNum);
//				
//			}
//			
//		});
//	
//		Label lblCipherNum = new Label(compEncStepsNum, SWT.NONE);
//		lblCipherNum.setText("Ciphertext c[i] = m[i]\u00b2 mod N");
//		txtCipherNum = new StyledText(compEncStepsNum, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtCipherNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtCipherNum.setLayoutData(txtCipherNumData);
//		txtCipherNum.setSelectionBackground(ColorService.GREEN);
//		txtCipherNum.setSelectionForeground(ColorService.BLACK);
//		setSizeInfoText(txtCipherNum, txtCipherNumData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtCipherNum.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fixSelection(currentCiphertextList, txtCipherNum);
//				
//			}
//			
//		});
//		
//		
//		
//		
//		
//		
//		compEncSteps = new Composite(encPartSteps, SWT.NONE);
//		compEncStepsData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		compEncSteps.setLayoutData(compEncStepsData);
//		compEncSteps.setLayout(new GridLayout(1, false));
//		
//		
//		lblMessage = new Label(compEncSteps, SWT.NONE);
//		lblMessageData = new GridData();
//		lblMessage.setText("Plaintext");
//		txtMessage = new Text(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
//		txtMessageData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		setSizeInfoText(txtMessage, txtMessageData, SWT.DEFAULT, SWT.DEFAULT);
//		txtMessage.setLayoutData(txtMessageData);
//		txtMessage.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				handlePlaintextTextMode();
//			}
//		});	
//		
//		
//		txtMessageWarning = new Text(compEncSteps, SWT.MULTI | SWT.WRAP);
//		txtMessageWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtMessageWarning.setLayoutData(txtMessageWarningData);
//		setSizeWarningText(txtMessageWarning, txtMessageWarningData, SWT.DEFAULT, SWT.DEFAULT);
//		txtMessageWarning.setBackground(ColorService.LIGHTGRAY);
//		txtMessageWarning.setForeground(ColorService.RED);
//		hideTextWarningNum(txtMessageWarning, txtMessageWarningData, compEncSteps);
//		
//		
//		
//		//txtMessageData.widthHint = txtMessage.getSize().x;
//		//txtMessageData.heightHint = 20;
//		
//		//Composite lblAndTxtMessage = new Composite(encPartSteps, SWT.BORDER);
//		//lblAndTxtMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		//lblAndTxtMessage.setLayout(new GridLayout(1, false));
//		
//		lblMessageSep = new Label(compEncSteps, SWT.NONE);
//		//lblMessageSep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		lblMessageSep.setText("Plaintext separated into segments (\"||\" as separator)");
//		//lblMessageSepData = new GridData();
//		txtMessageSep = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		txtMessageSepData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		setSizeInfoText(txtMessageSep, txtMessageSepData, SWT.DEFAULT, SWT.DEFAULT);
//		txtMessageSep.setLayoutData(txtMessageSepData);
//		txtMessageSep.setSelectionBackground(ColorService.GREEN);
//		txtMessageSep.setSelectionForeground(ColorService.BLACK);
//		txtMessageSep.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fixSelection(plaintextEncodedList, txtMessageSep);
//			}
//		});
//		
//		Label lblMessageBase = new Label(compEncSteps, SWT.NONE);
//		lblMessageBase.setText("Plaintext in base 16 format");
//		txtMessageBase = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtMessageBaseData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtMessageBase.setLayoutData(txtMessageBaseData);
//		setSizeInfoText(txtMessageBase, txtMessageBaseData, SWT.DEFAULT, SWT.DEFAULT);
//		txtMessageBase.setSelectionBackground(ColorService.GREEN);
//		txtMessageBase.setSelectionForeground(ColorService.BLACK);
//		txtMessageBase.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//				fixSelection(currentPlaintextList, txtMessageBase);
//				
//			}
//		});
//		
//	
//		Label lblCipher = new Label(compEncSteps, SWT.NONE);
//		lblCipher.setText("Ciphertext c[i] = m[i]\u00b2 mod N");
//		txtCipher = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtCipherData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtCipher.setLayoutData(txtCipherData);
//		setSizeInfoText(txtCipher, txtCipherData, SWT.DEFAULT, SWT.DEFAULT);
//		txtCipher.setSelectionBackground(ColorService.GREEN);
//		txtCipher.setSelectionForeground(ColorService.BLACK);
//		txtCipher.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//				fixSelection(currentCiphertextList, txtCipher);
//				
//			}
//		});
//		
//		/*txtCipher.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String ciphertext = txtCipher.getText();
//				if(ciphertext.isEmpty()) {
//					btnPrevElem.setEnabled(false);
//					btnNextElem.setEnabled(false);
//					return;
//				}
//				
//				btnPrevElem.setEnabled(true);
//				btnNextElem.setEnabled(true);
//				
//			}
//		});*/
//		
//		
//		/*
//		 * TEST
//		 */
//		
//		/*StyledText stTest = new StyledText(compEncSteps, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData stTestData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		stTest.setLayoutData(stTestData);
//		setSizeInfoText(stTest, stTestData, SWT.DEFAULT, SWT.DEFAULT);
//		stTest.setText("this || is || a || test || Im || super || excited "
//				+ "|| oeihwoeifhweifhw || owiehfwoiehfiwoeh || oiwhedfowiehfoi "
//				+ "|| woehfwoefhwoiefhweifowhef || oiehdowiehdoqiwehdqoiwdh");
//		
//		stTest.setSelectionBackground(ColorService.WHITE);
//		stTest.setSelectionForeground(ColorService.GREEN);
//		
//		
//		
//		ArrayList<String> testa = new ArrayList<String>();
//		testa.add("this");
//		testa.add("is");
//		testa.add("a");
//		testa.add("test");
//		testa.add("Im");
//		testa.add("super");
//		testa.add("excited");
//		testa.add("oeihwoeifhweifhw");
//		testa.add("owiehfwoiehfiwoeh");
//		testa.add("oiwhedfowiehfoi");
//		testa.add("woehfwoefhwoiefhweifowhef");
//		testa.add("oiehdowiehdoqiwehdqoiwdh");
//		
//		
//		
//		Button btnTest = new Button(compEncSteps, SWT.PUSH);
//		btnTest.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		btnTest.setText("test selection");
//		btnTest.addSelectionListener(new SelectionAdapter() {
//			int x = 1;
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//				int startIdx = 0;
//				int endIdx = 0;
//				
//				for(int i = 0; i < x-1; i++) {
//					startIdx += testa.get(i).length();
//					startIdx += 4;
//				}
//				
//				endIdx = startIdx + testa.get(x-1).length();
//				
//				//String testStr = "test";
//				stTest.setSelection(startIdx, endIdx);
//				
//				x += 1;
//				//stTest.setSelectionForeground(ColorService.WHITE);
//			}
//		});
//		
//		
//		
//		
//		
//		stTest.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});*/
//		
//		
//		
//		
//		
//		btnEnc = new Button(grpPlaintext, SWT.PUSH);
//		btnEnc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		btnEnc.setText("Encrypt");
//		btnEnc.setEnabled(false);
//		btnEnc.addSelectionListener(new SelectionAdapter() {
//			
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				
//				if(btnText.getSelection()) {
//				
//					String plaintext = txtMessage.getText();
//					String plaintextHex = Rabin.bytesToString(plaintext.getBytes());
//					String paddedPlaintextHex = Rabin.getPaddedPlaintext(plaintextHex, bytesPerBlock);
//					ArrayList<String> plaintextsHex = Rabin.parseString(paddedPlaintextHex, bytesPerBlock);
//					
//					plaintextEncodedList = Rabin.getEncodedList(plaintextsHex, radix, charset);
//					String plaintextEncodedWithSep = Rabin.getStringWithSepEncodedForm(plaintextsHex, separator, radix, charset);
//					
//					String plaintextInHex = Rabin.getStringWithSepForm(plaintextsHex, separator);
//					
//					ArrayList<String> ciphertextsHex = Rabin.getEncryptedListOfStrings(plaintextsHex, radix);
//					
//					ArrayList<String> paddedCiphertextsHex = Rabin.getPaddedCiphertextblocks(ciphertextsHex, blocklength);
//					
//					String ciphertextInHex = Rabin.getStringWithSepForm(paddedCiphertextsHex, separator);
//					
//					txtMessageSep.setText(plaintextEncodedWithSep);
//					txtMessageBase.setText(plaintextInHex);
//					txtCipher.setText(ciphertextInHex);
//					
//					
//					plaintextsTextMode = plaintextsHex;
//					ciphertextsTextMode = paddedCiphertextsHex;
//					currentPlaintextList = plaintextsTextMode;
//					currentCiphertextList = ciphertextsTextMode;
//					
//					setChooseCipher(currentCiphertextList);
//					
//					cmbChooseCipher.select(0);
//					
//					chooseCipherAction();
//					
//					/*cmbChooseCipher.removeAll();
//					for(int i = 1; i <= ciphertextsTextMode.size(); i++) {
//						cmbChooseCipher.add(String.valueOf(i));
//					}*/
//					
//					
//				}
//				
//				if(btnNum.getSelection()) {
//					String plaintext = txtMessageSepNum.getText();
//					ArrayList<String> plaintexts = Rabin.getParsedPlaintextInBase10(plaintext);
//					ArrayList<String> plaintextListBase16 = Rabin.getListasRadix(plaintexts, radix);
//					String plaintextBase16 = Rabin.getStringWithSepRadixForm(plaintexts, separator, radix);
//					ArrayList<String> ciphertexts = Rabin.getEncryptedListOfStrings(plaintexts, 10);
//					String ciphertextsWithSep = Rabin.getStringWithSepRadixForm(ciphertexts, separator, radix);
//					ArrayList<String> ciphertextListBase16 = Rabin.getListasRadix(ciphertexts, radix);
//					
//					txtMessageBaseNum.setText(plaintextBase16);
//					txtCipherNum.setText(ciphertextsWithSep);
//					
//					plaintextsNumMode = plaintextListBase16;
//					ciphertextsNumMode = ciphertextListBase16;
//					currentPlaintextList = plaintextsNumMode;
//					currentCiphertextList = ciphertextsNumMode;
//					
//					setChooseCipher(currentCiphertextList);
//					
//					cmbChooseCipher.select(0);
//					
//					chooseCipherAction();
//					
//					/*cmbChooseCipher.removeAll();
//					for(int i = 1; i <= ciphertextsNumMode.size(); i++) {
//						cmbChooseCipher.add(String.valueOf(i));
//					}*/
//				}
//				
//				resetDecComponents();
//				resetFinalPlaintextColor();
//				
//			}
//			
//			
//		});
//		
//		// label as separator for infotext
//		Label lblSepInfoEnc = new Label(grpPlaintext, SWT.SEPARATOR | SWT.VERTICAL);
//		GridData lblSepInfoEncData = new GridData(SWT.FILL, SWT.FILL, false, true);
//		//lblSepInfoEncData.horizontalIndent = 50;
//		lblSepInfoEnc.setLayoutData(lblSepInfoEncData);
//		
//		txtInfoEnc = new Text(grpPlaintext, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
//		GridData txtInfoEncData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		//txtInfoKeysData.minimumHeight = 250;
//		//txtInfoEncData.horizontalIndent = 50;
//		txtInfoEncData.widthHint = txtInfoEnc.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//		txtInfoEncData.heightHint = txtInfoEnc.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
//		txtInfoEnc.setLayoutData(txtInfoEncData);
//		txtInfoEnc.setText("To encrypt a plaintext in \"Text\" mode do the following:\n"
//				+ "1) Select \"Bytes per block\" to choose how many bytes you want "
//				+ "to encrypt at once\n"
//				+ "2) Enter a plaintext in the field \"Plaintext\"\n"
//				+ "3) Click on \"Encrypt\"");
//		txtInfoEnc.setBackground(ColorService.LIGHTGRAY);
//
//	}
//	
//	private void createDecContent(Composite parent) {
//		// init states
//		initStates();
//		
//		
//		grpDec = new Group(parent, SWT.NONE);
//		grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 50));
//		grpDec.setLayout(new GridLayout(2, false));
//		grpDec.setText("Step 3 - Decryption");
//		
//		compHoldAllSteps = new Composite(grpDec, SWT.NONE);
//		compHoldAllSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldAllSteps.setLayout(new GridLayout(1, false));
//		
//		
//		compEnterCiphertext = new Composite(compHoldAllSteps, SWT.BORDER);
//		compEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		compEnterCiphertext.setLayoutData(compEnterCiphertextData);
//		compEnterCiphertext.setLayout(new GridLayout(2, false));
//		
//		compEnterCiphertextData.exclude = true;
//		compEnterCiphertext.setVisible(false);
//		//compHoldAllSteps.layout(false);
//		compHoldAllSteps.requestLayout();
//		//compEnterCiphertextData.exclude = false;
//		
//		Composite compHoldRadHexDec = new Composite(compEnterCiphertext, SWT.BORDER);
//		compHoldRadHexDec.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
//		compHoldRadHexDec.setLayout(new GridLayout(2, false));
//		
//		btnRadHex = new Button(compHoldRadHexDec, SWT.RADIO);
//		btnRadHex.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		btnRadHex.setText("Hex");
//		btnRadHex.setSelection(true);
//		btnRadHex.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//					compHoldDecimalData.exclude = true;
//					compHoldDecimal.setVisible(false);
//					//compEnterCiphertext.requestLayout();
//					
//					compEnterCiphertextPart1Data.exclude = false;
//					compEnterCiphertextPart1.setVisible(true);
//					compEnterCiphertext.requestLayout();
//					
//					saveDecDecimalState();
//					setDecHexState();
//					
//					handleHexNumDecMode();
//					
//					
//					setInfoDecHexMode();
//				}
//			}
//			
//		});
//		//btnRadHex.setSelection(true);
//		
//		
//		btnRadDecimal = new Button(compHoldRadHexDec, SWT.RADIO);
//		btnRadDecimal.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		btnRadDecimal.setText("Decimal numbers");
//		btnRadDecimal.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//					compEnterCiphertextPart1Data.exclude = true;
//					compEnterCiphertextPart1.setVisible(false);
//					//compEnterCiphertext.requestLayout();
//					
//					compHoldDecimalData.exclude = false;
//					compHoldDecimal.setVisible(true);
//					compEnterCiphertext.requestLayout();
//					
//					saveDecHexState();
//					setDecDecimalState();
//					
//					handleDecimalNumbersDecMode();
//					
//					setInfoDecDecimalMode();
//					
//				}
//			}
//			
//		});
//		
//		compEnterCiphertextSteps = new Composite(compEnterCiphertext, SWT.BORDER);
//		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));
//		
//		
//		compEnterCiphertextPart1 = new Composite(compEnterCiphertextSteps, SWT.BORDER);
//		compEnterCiphertextPart1Data = new GridData(SWT.FILL, SWT.FILL, true, false);
//		compEnterCiphertextPart1.setLayoutData(compEnterCiphertextPart1Data);
//		compEnterCiphertextPart1.setLayout(new GridLayout(1, false));
//		
//		btnApplyCiphertext = new Button(compEnterCiphertext, SWT.PUSH);
//		btnApplyCiphertext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
//		btnApplyCiphertext.setText("Apply");
//		btnApplyCiphertext.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				if(btnRadHex.getSelection()) {
//					String ciphertextHexstring = txtEnterCiphertext.getText();
//					ciphertextHexstring = ciphertextHexstring.replaceAll("\\s+", "");
//					ArrayList<String> ciphertextList = Rabin.parseString(ciphertextHexstring, blocklength);
//					String ciphertextHexstringWithSep = Rabin.getStringWithSepForm(ciphertextList, separator);
//					txtCiphertextSegments.setText(ciphertextHexstringWithSep);
//					
//					// add global list
//					
//					currentCiphertextList = ciphertextList;
//					ciphertextsDecHexMode = ciphertextList;
//					
//					setChooseCipher(currentCiphertextList);
//					
//					cmbChooseCipher.select(0);
//					
//					chooseCipherAction();
//
//					
//					
//				}
//				
//				if(btnRadDecimal.getSelection()) {
//					String ciphertextDecimal = txtEnterCiphertextDecimal.getText();
//					ArrayList<String> ciphertextList = Rabin.getParsedPlaintextInBase10(ciphertextDecimal);
//					String ciphertextBase16 = Rabin.getStringWithSepRadixForm(ciphertextList, separator, radix);
//					ArrayList<String> ciphertextHexList = Rabin.getListasRadix(ciphertextList, radix);
//					txtCiphertextSegmentsDecimal.setText(ciphertextBase16);
//					
//					
//					ciphertextsDecDecimalMode = ciphertextHexList;
//					currentCiphertextList = ciphertextHexList;
//					//ciphertextsDecDecimalMode = ciphertextHexList;
//					
//					setChooseCipher(ciphertextHexList);
//					
//					cmbChooseCipher.select(0);
//					
//					chooseCipherAction();
//					
//				}
//				
//				resetDecComponents();
//				resetFinalPlaintextColor();
//			}
//			
//		});
//		
//		
//		compHoldDecimal = new Composite(compEnterCiphertextSteps, SWT.BORDER);
//		compHoldDecimalData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		compHoldDecimal.setLayoutData(compHoldDecimalData);
//		compHoldDecimal.setLayout(new GridLayout(1, false));
//		
//		compHoldDecimalData.exclude = true;
//		compHoldDecimal.setVisible(false);
//		compEnterCiphertextSteps.requestLayout();
//		
//		
//		
//		Label lblEnterCiphertextDecimal = new Label(compHoldDecimal, SWT.NONE);
//		lblEnterCiphertextDecimal.setText("Ciphertext in base 10 format separated into segments (\"||\" as separator)");
//		
//		txtEnterCiphertextDecimal = new Text(compHoldDecimal, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		GridData txtEnterCiphertextDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtEnterCiphertextDecimal.setLayoutData(txtEnterCiphertextDecimalData);
//		setSizeInfoText(txtEnterCiphertextDecimal, txtEnterCiphertextDecimalData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtEnterCiphertextDecimal.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				handleDecimalNumbersDecMode();
//			}
//		});
//		
//		txtEnterCiphertextDecimalWarning = new Text(compHoldDecimal, SWT.MULTI | SWT.WRAP);
//		txtEnterCiphertextDecimalWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtEnterCiphertextDecimalWarning.setLayoutData(txtEnterCiphertextDecimalWarningData);
//		setSizeWarningText(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, SWT.DEFAULT, SWT.DEFAULT);
//		txtEnterCiphertextDecimalWarning.setBackground(ColorService.LIGHTGRAY);
//		txtEnterCiphertextDecimalWarning.setForeground(ColorService.RED);
//		hideTextWarningNum(txtEnterCiphertextDecimalWarning, txtEnterCiphertextDecimalWarningData, compHoldDecimal);
//		
//		
//		Label lblCiphertextSegmentsDecimal = new Label(compHoldDecimal, SWT.NONE);
//		lblCiphertextSegmentsDecimal.setText("Ciphertext in base 16 format separated into segments (\"||\""
//				+ " as separator)");
//		
//		txtCiphertextSegmentsDecimal = new StyledText(compHoldDecimal, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
//		GridData txtCiphertextSegmentsDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtCiphertextSegmentsDecimal.setLayoutData(txtCiphertextSegmentsDecimalData);
//		txtCiphertextSegmentsDecimal.setSelectionBackground(ColorService.GREEN);
//		txtCiphertextSegmentsDecimal.setSelectionForeground(ColorService.BLACK);
//		setSizeInfoText(txtCiphertextSegmentsDecimal, txtCiphertextSegmentsDecimalData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtCiphertextSegmentsDecimal.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fixSelection(currentCiphertextList, txtCiphertextSegmentsDecimal);
//				
//			}
//			
//		});
//		
//		
//		
//		
//		Label lblEnterCiphertext = new Label(compEnterCiphertextPart1, SWT.NONE);
//		lblEnterCiphertext.setText("Ciphertext in base 16 format");
//		
//		txtEnterCiphertext = new Text(compEnterCiphertextPart1, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		GridData txtEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtEnterCiphertext.setLayoutData(txtEnterCiphertextData);
//		setSizeInfoText(txtEnterCiphertext, txtEnterCiphertextData, SWT.DEFAULT, SWT.DEFAULT);
//		txtEnterCiphertext.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				handleHexNumDecMode();
//				
//			}
//		});
//		
//		txtEnterCiphertextWarning = new Text(compEnterCiphertextPart1, SWT.MULTI | SWT.WRAP);
//		txtEnterCiphertextWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtEnterCiphertextWarning.setLayoutData(txtEnterCiphertextWarningData);
//		txtEnterCiphertextWarning.setForeground(ColorService.RED);
//		txtEnterCiphertextWarning.setBackground(ColorService.LIGHTGRAY);
//		setSizeWarningText(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, SWT.DEFAULT, SWT.DEFAULT);
//		hideTextWarningNum(txtEnterCiphertextWarning, txtEnterCiphertextWarningData, compEnterCiphertextPart1);
//		
//		
//		Label lblCiphertextSegments = new Label(compEnterCiphertextPart1, SWT.NONE);
//		lblCiphertextSegments.setText("Ciphertext separated into segments (\"||\""
//				+ " as separator)");
//		
//		txtCiphertextSegments = new StyledText(compEnterCiphertextPart1, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
//		GridData txtCiphertextSegmentsData = new GridData(SWT.FILL, SWT.CENTER, true, true);
//		txtCiphertextSegments.setLayoutData(txtCiphertextSegmentsData);
//		txtCiphertextSegments.setSelectionBackground(ColorService.GREEN);
//		txtCiphertextSegments.setSelectionForeground(ColorService.BLACK);
//		setSizeInfoText(txtCiphertextSegments, txtCiphertextSegmentsData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		
//		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fixSelection(currentCiphertextList, txtCiphertextSegments);
//				
//			}
//			
//		});
//		
//		
//		Composite compInfoDec = new Composite(grpDec, SWT.NONE);
//		GridData compInfoDecData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		compInfoDec.setLayoutData(compInfoDecData);
//		compInfoDec.setLayout(new GridLayout(2, false));
//		
//		
//		
//		Composite compTest = new Composite(compHoldAllSteps, SWT.NONE);
//		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		compTest.setLayout(new GridLayout(4, false));
//		
//		
//		
//		
//		// create combobox for selecting ciphertext c[i]
//		Composite compChooseCipher = new Composite(compTest, SWT.BORDER);
//		compChooseCipher.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		compChooseCipher.setLayout(new GridLayout(1, false));
//		
//		// place combo and label inside compChooseCipher
//		Label lblChoosCipher = new Label(compChooseCipher, SWT.NONE);
//		lblChoosCipher.setText("Block (c[i])");
//		cmbChooseCipher = new CCombo(compChooseCipher, SWT.READ_ONLY | SWT.DROP_DOWN);
//		cmbChooseCipher.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		cmbChooseCipher.setVisibleItemCount(10);
//		//cmbChooseCipher.add("1");
//		cmbChooseCipher.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				int idx = cmbChooseCipher.getSelectionIndex();
//				String item = cmbChooseCipher.getItem(idx);
//				int i = Integer.parseInt(item);
//				int chosenIdx = i - 1;
//				int elem = chosenIdx + 1;
//				String ciphertext = currentCiphertextList.get(chosenIdx);
//				txtCipherFirst.setText(ciphertext);
//				
//				
//				if(btnSelectionEnc.getSelection()) {
//					selectionTextNumMode(elem);
//				}
//				if(btnSelectionDec.getSelection()) {
//					selectionHexDecMode(elem);
//				}
//				
//				
//			}
//			
//		});
//		
//	
//		
//		//cmbChooseCipher.setVisibleItemCount(5);
//		
//		
//		
//		
//		//create label for first block of ciphertext
//		Label lblCipherFirst = new Label(compTest, SWT.NONE);
//		//lblCipherFirst.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
//		lblCipherFirst.setText("c[i] = ");
//				
//		
//		// create textbox for first block of the ciphertext
//		txtCipherFirst = new Text(compTest, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
//		GridData txtCipherFirstData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtCipherFirst.setLayoutData(txtCipherFirstData);
//		setSizeInfoText(txtCipherFirst, txtCipherFirstData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtCipherFirst.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String chooseCipher = txtCipherFirst.getText();
//				if(chooseCipher.isEmpty()) {
//					btnPrevElem.setEnabled(false);
//					btnNextElem.setEnabled(false);
//					btnSqrRoot.setEnabled(false);
//					return;
//				}
//				
//				btnPrevElem.setEnabled(true);
//				btnNextElem.setEnabled(true);
//				btnSqrRoot.setEnabled(true);
//			}
//		});
//		
//		Composite compNavElem = new Composite(compTest, SWT.NONE);
//		compNavElem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		compNavElem.setLayout(new GridLayout(2, false));
//		
//		btnPrevElem = new Button(compNavElem, SWT.PUSH);
//		btnPrevElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		btnPrevElem.setText("c[i-1]");
//		btnPrevElem.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				resetDecComponents();
//				
//				if(btnSelectionEnc.getSelection()) {
//					resetFinalPlaintextColor();
//					prevElementTextNumMode();
//				}
//				if(btnSelectionDec.getSelection()) {
//					prevElementHexDecMode();
//				}
//			}
//		});
//		
//	
//		
//		btnNextElem = new Button(compNavElem, SWT.PUSH);
//		btnNextElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		btnNextElem.setText("c[i+1]");
//		btnNextElem.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				resetDecComponents();
//				
//				if(btnSelectionEnc.getSelection()) {
//					resetFinalPlaintextColor();
//					nextElementTextNumMode();
//				}
//				if(btnSelectionDec.getSelection()) {
//					nextElementHexDecMode();
//				}
//			}
//		});
//		
//		Label lblSepNavElem = new Label(compNavElem, SWT.SEPARATOR | SWT.HORIZONTAL);
//		lblSepNavElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
//		
//		
//		// create button for computing squareroots
//		btnSqrRoot = new Button(compNavElem, SWT.PUSH);
//		btnSqrRoot.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
//		btnSqrRoot.setText("Compute square roots mod p and q");
//		
//		btnSqrRoot.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				String currentCiphertext = txtCipherFirst.getText();
//				BigInteger c = new BigInteger(currentCiphertext, radix);
//				BigInteger mp = Rabin.getSquarerootP(c);
//				BigInteger mq = Rabin.getSquarerootQ(c);
//				String mpStr = mp.toString(radix);
//				String mqStr = mq.toString(radix);
//				txtmp.setText(mpStr);
//				txtmq.setText(mqStr);
//				
//			}
//			
//		});
//		
//		// txtInfo test
//		//Text txtInfoDec = new Text(grpDec, SWT.BORDER | SWT.V_SCROLL);
//		//txtInfoDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
//		
//		/*Composite compSqrRootInfoHolder = new Composite(grpDec, SWT.NONE);
//		compSqrRootInfoHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compSqrRootInfoHolder.setLayout(new GridLayout(3, false));
//		
//		Composite compSqrRoot = new Composite(compSqrRootInfoHolder, SWT.NONE);
//		compSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compSqrRoot.setLayout(new GridLayout(1, false));*/
//		
//		// label for separating info txt
//		Label lblSepDecInfo = new Label(compInfoDec, SWT.SEPARATOR | SWT.VERTICAL);
//		lblSepDecInfo.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
//		
//		txtInfoDec = new Text(compInfoDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
//		GridData txtInfoDecData = new GridData(SWT.FILL, SWT.CENTER, true, true);
//		//txtInfoDecData.minimumWidth = 400;
//		txtInfoDecData.minimumHeight = 250;
//		//txtInfoEncData.horizontalIndent = 50;
//		txtInfoDecData.widthHint = txtInfoDec.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
//		txtInfoDecData.heightHint = txtInfoDec.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
//		txtInfoDec.setLayoutData(txtInfoDecData);
//		txtInfoDec.setText("To decrypt a ciphertext c[i] do the following:\n"
//				+ "1) Either Use the drop-down list or the buttons \"c[i-1]\" "
//				+ "and \"c[i+1]\" to choose a ciphertext c[i]\n"
//				+ "2) Click on \"Compute square roots mod p and q\"\n"
//				+ "3) Click on \"Compute y_p and y_q\" to compute the inverse values of p and q"
//				+ " for the Chinese Remainder Theorem (CRT)\n"
//				+ "4) Click on \"Compute v and w\" to compute the intermediate values\n"
//				+ "5) Click on \"Compute all plaintexts\" to compute all plaintexts using the CRT\n"
//				+ "The correct plaintext will be shown in the color green");
//		txtInfoDec.setBackground(ColorService.LIGHTGRAY);
//		
//		// create group for computing square roots
//		grpSqrRoot = new Group(compHoldAllSteps, SWT.NONE);
//		grpSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		grpSqrRoot.setLayout(new GridLayout(2, false));
//		grpSqrRoot.setText("Square roots of c modulo p and q");
//		
//		// info txt for decryption
//		//Text txtInfoDec = new Text(grpDec, SWT.BORDER | SWT.V_SCROLL);
//		//txtInfoDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
//
//		// create group for m_p
//		compMp = new Composite(grpSqrRoot, SWT.BORDER);
//		compMp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compMp.setLayout(new GridLayout(1, false));
//		
//		//create label for m_p
//		Label lblMp = new Label(compMp, SWT.NONE);
//		lblMp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		lblMp.setText("m\u209a = c^((p+1)/4) mod p");
//		
//		// create txtbox for m_p
//		txtmp = new Text(compMp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
//		GridData txtmpData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtmp.setLayoutData(txtmpData);
//		setSizeInfoText(txtmp, txtmpData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtmp.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String mp = txtmp.getText();
//				if(mp.isEmpty()) {
//					btnComputeYpYq.setEnabled(false);
//					return;
//				}
//				btnComputeYpYq.setEnabled(true);
//				
//			}
//		});
//		
//		// create group for m_q
//		compMq = new Composite(grpSqrRoot, SWT.BORDER);
//		compMq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compMq.setLayout(new GridLayout(1, false));
//		
//		//create label for m_q
//		Label lblMq = new Label(compMq, SWT.NONE);
//		lblMq.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		lblMq.setText("m_q = c^((q+1)/4) mod q");
//		
//		// create txtbox for m_p
//		txtmq = new Text(compMq, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
//		GridData txtmqData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtmq.setLayoutData(txtmqData);
//		setSizeInfoText(txtmq, txtmqData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtmq.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String mq = txtmq.getText();
//				if(mq.isEmpty()) {
//					btnComputeYpYq.setEnabled(false);
//					return;
//				}
//				btnComputeYpYq.setEnabled(true);
//				
//			}
//		});
//		
//		// create group for linear congruences
//		grpLinCon = new Group(compHoldAllSteps, SWT.NONE);
//		grpLinCon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		grpLinCon.setLayout(new GridLayout(2, false));
//		grpLinCon.setText("Compute y_p and y_q such that y_p * p + y_q * q = 1 "
//				+ "using the Euclidean algorithm");
//		
//		// create composite for first 2 congruences 
//		compMerge = new Composite(grpLinCon, SWT.BORDER);
//		compMerge.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compMerge.setLayout(new GridLayout(2, false));
//		
//		// create linear congruence
//		// m1 = mp mod p
//		// m1 = mq mod q
//		
//		
//		// label yp
//		Label lblyp = new Label(compMerge, SWT.NONE);
//		lblyp.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblyp.setText("y_p = ");
//		
//		// create y_p txtbox
//		txtyp = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtypData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtyp.setLayoutData(txtypData);
//		setSizeInfoText(txtyp, txtypData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtyp.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String yp = txtyp.getText();
//				if(yp.isEmpty()) {
//					btnComputevw.setEnabled(false);
//					return;
//				}
//				btnComputevw.setEnabled(true);
//			}
//		});
//		
//		//create label y_q
//		Label lblyq = new Label(compMerge, SWT.NONE);
//		lblyq.setText("y_q = ");
//		
//		// create txtbox for y_q
//		txtyq = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtyqData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtyq.setLayoutData(txtyqData);
//		setSizeInfoText(txtyq, txtyqData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtyq.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String yq = txtyq.getText();
//				if(yq.isEmpty()) {
//					btnComputevw.setEnabled(false);
//					return;
//				}
//				btnComputevw.setEnabled(true);
//			}
//		});
//		
//		btnComputeYpYq = new Button(grpLinCon, SWT.PUSH);
//		btnComputeYpYq.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		btnComputeYpYq.setText("Compute y_p and y_q");
//		btnComputeYpYq.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				BigInteger[] yp_yq = Rabin.getInverseElements();
//				String yp = yp_yq[0].toString(radix);
//				String yq = yp_yq[1].toString(radix);
//				txtyp.setText(yp);
//				txtyq.setText(yq);
//			}
//			
//		});
//		
//		// create group for computing v = y_p * p * m_q
//		// and w = y_q * q * m_p
//		Group grpComputeIs = new Group(compHoldAllSteps, SWT.NONE);
//		grpComputeIs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		grpComputeIs.setLayout(new GridLayout(2, false));
//		grpComputeIs.setText("Compute v = y_p \u22c5 p \u22c5 m_q and "
//				+ "w = y_q \u22c5 q \u22c5 m_p");
//		
//		Composite compHoldvw = new Composite(grpComputeIs, SWT.BORDER);
//		compHoldvw.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldvw.setLayout(new GridLayout(2, false));
//		
//		btnComputevw = new Button(grpComputeIs, SWT.PUSH);
//		btnComputevw.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		btnComputevw.setText("Compute v and w");
//		btnComputevw.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				BigInteger yp = new BigInteger(txtyp.getText(), radix);
//				BigInteger yq = new BigInteger(txtyq.getText(), radix);
//				BigInteger mp = new BigInteger(txtmp.getText(), radix);
//				BigInteger mq = new BigInteger(txtmq.getText(), radix);
//				BigInteger v = Rabin.getV(yp, mq);
//				BigInteger w = Rabin.getW(yq, mp);
//				txtV.setText(v.toString(radix));
//				txtW.setText(w.toString(radix));
//			}
//		});
//		
//		Label lblV = new Label(compHoldvw, SWT.NONE);
//		lblV.setText("v = ");
//		txtV = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtVData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtV.setLayoutData(txtVData);
//		setSizeInfoText(txtV, txtVData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtV.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String v = txtV.getText();
//				if(v.isEmpty()) {
//					btnComputePt.setEnabled(false);
//					return;
//				}
//				btnComputePt.setEnabled(true);
//			}
//		});	
//		
//		Label lblW = new Label(compHoldvw, SWT.NONE);
//		lblW.setText("w = ");
//		txtW = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtWData = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtW.setLayoutData(txtWData);
//		setSizeInfoText(txtW, txtWData, SWT.DEFAULT, SWT.DEFAULT);
//		
//		txtW.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				String w = txtW.getText();
//				if(w.isEmpty()) {
//					btnComputePt.setEnabled(false);
//					return;
//				}
//				btnComputePt.setEnabled(true);
//			}
//		});	
//		
//		// create group for computing plaintexts
//		grpPosPlain = new Group(compHoldAllSteps, SWT.NONE);
//		grpPosPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		grpPosPlain.setLayout(new GridLayout(2, false));
//		grpPosPlain.setText("Compute all plaintexts");
//		
//		// composite for packing all plaintexts for better structure
//		compAllPt = new Composite(grpPosPlain, SWT.BORDER);
//		compAllPt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compAllPt.setLayout(new GridLayout(2, false));
//		
//		// label for m1
//		Label lblm1 = new Label(compAllPt, SWT.NONE);
//		lblm1.setText("m\u2081 = v + w mod N = ");
//		
//		// txt for m1
//		txtm1 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtm1Data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtm1.setLayoutData(txtm1Data);
//		setSizeInfoText(txtm1, txtm1Data, SWT.DEFAULT, SWT.DEFAULT);
//		
//		// label for m2
//		Label lblm2 = new Label(compAllPt, SWT.NONE);
//		lblm2.setText("m\u2082 = v \u2212 w mod N = ");
//		
//		// txt for m2
//		txtm2 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtm2Data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtm2.setLayoutData(txtm2Data);
//		setSizeInfoText(txtm2, txtm2Data, SWT.DEFAULT, SWT.DEFAULT);
//		
//		// label for m3
//		Label lblm3 = new Label(compAllPt, SWT.NONE);
//		lblm3.setText("m\u2083 = w \u2212 v mod N = ");
//		
//		// txt for m3
//		txtm3 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtm3Data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtm3.setLayoutData(txtm3Data);
//		setSizeInfoText(txtm3, txtm3Data, SWT.DEFAULT, SWT.DEFAULT);
//		
//		// label for m4
//		Label lblm4 = new Label(compAllPt, SWT.NONE);
//		lblm4.setText("m\u2084 = -v \u2212 w mod N = ");
//		
//		// txt for m4
//		txtm4 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
//		GridData txtm4Data = new GridData(SWT.FILL, SWT.FILL, true, true);
//		txtm4.setLayoutData(txtm4Data);
//		setSizeInfoText(txtm4, txtm4Data, SWT.DEFAULT, SWT.DEFAULT);
//		
//		// create Composite holding "compute all plaintexts" and "continue with next block c[i]"
//		Composite compHoldNextC = new Composite(grpPosPlain, SWT.BORDER);
//		compHoldNextC.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
//		compHoldNextC.setLayout(new GridLayout(1, false));
//		
//		// create button for computing all plaintexts
//		btnComputePt = new Button(compHoldNextC, SWT.PUSH);
//		btnComputePt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
//		btnComputePt.setText("Compute all plaintexts");
//		btnComputePt.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				BigInteger v = new BigInteger(txtV.getText(), radix);
//				BigInteger w = new BigInteger(txtW.getText(), radix);
//				BigInteger[] possibleMessages = Rabin.getPlaintexts(v, w);
//				String m1 = possibleMessages[0].toString(radix);
//				String m2 = possibleMessages[1].toString(radix);
//				String m3 = possibleMessages[2].toString(radix);
//				String m4 = possibleMessages[3].toString(radix);
//				txtm1.setText(m1);
//				txtm2.setText(m2);
//				txtm3.setText(m3);
//				txtm4.setText(m4);
//				
//				if(btnSelectionEnc.getSelection())
//					setFinalPlaintextColor();
//				
//				
//			}
//		});
//		
//		
//		/*btnNextC = new Button(compHoldNextC, SWT.PUSH);
//		btnNextC.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
//		btnNextC.setText("Next block c[i+1]");
//		btnNextC.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				//int nextIdx = (currentIdx + 1) % currentCiphertextList.size();
//				
//				int idx = cmbChooseCipher.getSelectionIndex();
//				String item = cmbChooseCipher.getItem(idx);
//				int nextIdx = Integer.parseInt(item) % (currentCiphertextList.size() - 1);
//				
//				nextElementTextNumMode();
//				try {
//					nextElem = currentCiphertextList.get(nextIdx);
//				}
//				catch(IndexOutOfBoundsException exc) {
//					System.out.println("There is not any further element.");
//					return;
//				}
//				int nextIdx = BigInteger.valueOf(currentIdx-1).mod(BigInteger.valueOf(currentCiphertextList.size())).intValue();
//				int elem = nextIdx + 1;
//				nextElem = currentCiphertextList.get(nextIdx);
//				int startIdx = getStartIdx(elem, currentCiphertextList);
//				int endIdx = getEndIdx(startIdx, elem, currentCiphertextList);
//				txtCipherFirst.setText(nextElem);
//				txtCipher.setSelection(startIdx, endIdx);
//				currentIdx = nextIdx;
//			}
//		});*/
//
//	}
//	
//	
//	
//	
//	
//	
//	
//
//	/**
//	 * Launch the application.
//	 * @param args
//	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			RabinSecondTabPrototyp shell = new RabinSecondTabPrototyp(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Create the shell.
//	 * @param display
//	 */
//	public RabinSecondTabPrototyp(Display display) {
//		super(display, SWT.SHELL_TRIM);
//		setLayout(new GridLayout(1, false));
//		ScrolledComposite sc = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//		sc.setLayout(new GridLayout(1, false));
//		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		rootComposite = new Composite(sc, SWT.BORDER);
//		rootComposite.setLayout(new GridLayout(1, false));
//		rootComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		sc.setContent(rootComposite);
//		sc.setExpandHorizontal(true);
//		sc.setExpandVertical(true);
//		rootComposite.setLayout(new GridLayout(1, false));
//		//setLayout(new GridLayout(1, false));
//		//this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		createSetParamContent(rootComposite);
//		initializePrimes(100);
//		
//		// create radio buttons for selecting either encryption or decryption
//		Composite compSelectEncDec = new Composite(rootComposite, SWT.NONE);
//		compSelectEncDec.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		compSelectEncDec.setLayout(new GridLayout(4, false));
//		
//		btnSelectionEnc = new Button(compSelectEncDec, SWT.RADIO);
//		btnSelectionEnc.setText("Encryption and decryption");
//		btnSelectionEnc.setSelection(true);
//		
//		btnSelectionEnc.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//				
//					grpPlaintextData.exclude = false;
//					grpPlaintext.setVisible(true);
//					rootComposite.requestLayout();
//					
//					compEnterCiphertextData.exclude = true;
//					compEnterCiphertext.setVisible(false);
//					compHoldAllSteps.requestLayout();
//					
//					if(btnRadHex.getSelection()) {
//						saveDecHexState();
//					}
//					if(btnRadDecimal.getSelection()) {
//						saveDecDecimalState();
//					}
//					
//					if(btnText.getSelection()) {
//						setTextState();
//					}
//					if(btnNum.getSelection()) {
//						setNumState();
//					}
//					
//					resetFinalPlaintextColor();
//					
//					txtInfoDec.setText("To decrypt a ciphertext c[i] do the following:\n"
//							+ "1) Either Use the drop-down list or the buttons \"c[i-1]\" "
//							+ "and \"c[i+1]\" to choose a ciphertext c[i]\n"
//							+ "2) Click on \"Compute square roots mod p and q\"\n"
//							+ "3) Click on \"Compute y_p and y_q\" to compute the inverse values of p and q"
//							+ " for the Chinese Remainder Theorem (CRT)\n"
//							+ "4) Click on \"Compute v and w\" to compute the intermediate values\n"
//							+ "5) Click on \"Compute all plaintexts\" to compute all plaintexts using the CRT\n"
//							+ "The correct plaintext will be shown in the color green");
//				
//				}
//				
//			}
//		});
//		
//		btnSelectionDec = new Button(compSelectEncDec, SWT.RADIO);
//		btnSelectionDec.setText("Decryption");
//		
//		btnSelectionDec.addSelectionListener(new SelectionAdapter() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				Button src = (Button) e.getSource();
//				
//				if(src.getSelection()) {
//				
//				
//					grpPlaintextData.exclude = true;
//					grpPlaintext.setVisible(false);
//					rootComposite.requestLayout();
//					
//					compEnterCiphertextData.exclude = false;
//					compEnterCiphertext.setVisible(true);
//					compHoldAllSteps.requestLayout();
//					
//					
//					if(btnText.getSelection()) {
//						saveTextState();
//					}
//					if(btnNum.getSelection()) {
//						saveNumState();
//					}
//					
//					if(btnRadHex.getSelection()) {
//						setDecHexState();
//					}
//					if(btnRadDecimal.getSelection()) {
//						setDecDecimalState();
//					}
//					
//					resetFinalPlaintextColor();
//					
//					if(btnRadHex.getSelection()) {
//						setInfoDecHexMode();
//					}
//					if(btnRadDecimal.getSelection()) {
//						setInfoDecDecimalMode();
//					}
//					
//				
//				}
//			}
//			
//			
//		});
//		
//		createEncContent(rootComposite);
//		createDecContent(rootComposite);
//		initializeComponents();
//		sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		//rootComposite.setSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		//createContents();
//	}
//
//	/**
//	 * Create contents of the shell.
//	 */
//	protected void createContents() {
//		setText("SWT Application");
//		setSize(450, 300);
//
//	}
//
//	@Override
//	protected void checkSubclass() {
//		// Disable the check that prevents subclassing of SWT components
//	}
//
//}
