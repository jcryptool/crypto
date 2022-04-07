package org.jcryptool.visual.rabin.ui;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.HandleSecondTab;
import org.jcryptool.visual.rabin.Messages;
import org.jcryptool.visual.rabin.Rabin;

/**
 * @author rico
 *
 */
public class RabinSecondTabComposite extends Composite {
	
	private Group grpParam;
	private Group grpPlaintext;
	private Group grpDec;
	private Group grpSqrRoot;
	private Group grpLinCon;
	private Composite npqComp;
	private Text txtModN;
	private Text txtPlain;
	private Button btnEnc;
	
	private Text txtCipherFirst;
	
	private Composite compMp;
	private Text txtmp;
	private Composite compMq;
	private Text txtmq;
	private Button btnSqrRoot;
	
	private Composite compMerge;
	private Text txtyp;
	private Text txtyq;
	private Group grpPosPlain;
	private Text txtm1;
	private Text txtm2;
	private Text txtm3;
	private Text txtm4;
	private Composite compAllPt;
	private Button btnComputePt;
	private Button btnNextC;
	private Button btnGenKeysMan;
	private Button btnStartGenKeys;
	private Combo cmbP;
	private Combo cmbQ;
	private CCombo cmbBlockN;
	private Button btnText;
	private Button btnNum;
	private Text txtMessage;
	private StyledText txtMessageSep;
	private StyledText txtMessageBase;

	private StyledText txtCipher;
	private GridData grpPlaintextData;
	private GridData compEnterCiphertextData;
	private Composite compEnterCiphertext;
	private Composite compHoldAllSteps;
	private Label lblMessage;
	private Label lblMessageSep;

	private GridData lblMessageData;
	private GridData txtMessageData;
	private GridData txtMessageSepData;

	private Composite encPartSteps;
	private Button btnSelectionEnc;
	private Button btnSelectionDec;
	private Button btnApplyCiphertext;
	private Composite rootComposite;
	private CCombo cmbChooseCipher;

	private Button btnComputeYpYq;
	private Button btnComputevw;
	private Text txtV;
	private Text txtW;
	
	private Composite compEncStepsNum;
	private GridData compEncStepsNumData;
	private Composite compEncSteps;
	private GridData compEncStepsData;
	
	private Text txtMessageSepNum;
	private StyledText txtMessageBaseNum;
	private StyledText txtCipherNum;
	private Composite compEnterCiphertextPart1;
	private Composite compHoldDecimal;
	private Button btnRadHex;
	private Button btnRadDecimal;
	private GridData compEnterCiphertextPart1Data;
	private GridData compHoldDecimalData;
	private Composite compEnterCiphertextSteps;
	private Text txtEnterCiphertextDecimal;
	private StyledText txtCiphertextSegmentsDecimal;
	private Text txtEnterCiphertext;
	private StyledText txtCiphertextSegments;
	private Text txtWarningNpq;
	private GridData txtWarningNpqData;
	private Composite compBlockN;
	private Text txtMessageSepNumWarning;
	private GridData txtMessageSepNumWarningData;
	private Text txtEnterCiphertextWarning;
	private GridData txtEnterCiphertextWarningData;
	private Text txtEnterCiphertextDecimalWarning;
	private GridData txtEnterCiphertextDecimalWarningData;
	private Text txtMessageWarning;
	private GridData txtMessageWarningData;
	private Button btnPrevElem;
	private Button btnNextElem;
	private Text txtInfoEnc;
	//private Text txtInfoDec;
	private ScrolledComposite sc;
	private Button btnGenKeysAlgo;
	private GridData compBlockNData;
	
	private Text nWarning;
	private Text qWarning;
	private Text pWarning;
	
	private Text txtInfoDecimalAndHex;
	private Text txtInfoSquareRoots;
	private Text txtInfoLC;
	private Text txtInfoPlaintexts;
	private Text txtMessageWithPadding;
	private Label lblBlockN;
	private Label lblMessageSepNum;
	private Label lblMessageBaseNum;
	private Label lblCipherNum;
	private Label lblMessageWithPadding;
	private Label lblMessageBase;
	private Label lblCipher;
	private Label lblSepInfoEnc;
	private Composite compHoldRadHexDec;
	private Label lblInfoDecimalAndHex;
	private Label lblEnterCiphertextDecimal;
	private Label lblCiphertextSegmentsDecimal;
	private Label lblEnterCiphertext;
	private Label lblCiphertextSegments;
	private Composite compInfoDec;
	private Composite compHoldTestAndSQR;
	private Composite compTest;
	private Composite compChooseCipher;
	private Label lblChoosCipher;
	private Label lblCipherFirst;
	private Composite compNavElem;
	private Label lblSepNavElem;
	private Label lblSeparateInfoSquareRoots;
	private Label lblMp;
	private Label lblMq;
	private Composite compHoldLCandInverse;
	private Label lblyp;
	private Label lblyq;
	private Label lblSeparateInfoLC;
	private Group grpComputeIs;
	private Composite compHoldvw;
	private Label lblV;
	private Label lblW;
	private Composite compHoldPlaintextsAndInfo;
	private Label lblm1;
	private Label lblm2;
	private Label lblm3;
	private Label lblm4;
	private Label lblSeparateInfoPlaintexts;
	private Composite compSelectEncDec;
	
	private Composite compSelNumTxt;
	private Composite compHoldNextC;
	
	private Composite compHoldRadioHexAndDecAndSteps;
	private Composite compHoldRadHexDec2;


	
	
	private String strPlaintextSeparatedIntoSegments = Messages.RabinSecondTabComposite_strPlaintextSeparatedIntoSegments;
	private String strPlainInBase16 = Messages.RabinSecondTabComposite_strPlainInBase16;
	
	
	private HandleSecondTab guiHandler;
	
	
	
	
	public Text getTxtMessageWithPadding() {
		return txtMessageWithPadding;
	}
	
	
	
	/**
	 * @return the txtInfoDecimalAndHex
	 */
	public Text getTxtInfoDecimalAndHex() {
		return txtInfoDecimalAndHex;
	}


	/**
	 * @return the txtInfoSquareRoots
	 */
	public Text getTxtInfoSquareRoots() {
		return txtInfoSquareRoots;
	}


	/**
	 * @return the txtInfoLC
	 */
	public Text getTxtInfoLC() {
		return txtInfoLC;
	}


	/**
	 * @return the txtInfoPlaintexts
	 */
	public Text getTxtInfoPlaintexts() {
		return txtInfoPlaintexts;
	}


	/**
	 * @return nWarning
	 */
	public Text getNWarning() {
		return nWarning;
	}
	
	
	/**
	 * @return pWarning
	 */
	public Text getPWarning() {
		return pWarning;
	}
	
	
	
	/**
	 * @return qWarning
	 */
	public Text getQWarning() {
		return qWarning;
	}
	
	
	/**
	 * @return current instance of this class
	 */
	public RabinSecondTabComposite getCurrentInstance() {
		return this;
	}
	
	
	
	
	
	/**
	 * @return the guiHandler
	 */
	public HandleSecondTab getGuiHandler() {
		return guiHandler;
	}





	/**
	 * @return the compBlockNData
	 */
	public GridData getCompBlockNData() {
		return compBlockNData;
	}




	/**
	 * @return the txtCipherFirst
	 */
	public Text getTxtCipherFirst() {
		return txtCipherFirst;
	}


	/**
	 * @return the sc
	 */
	public ScrolledComposite getSC() {
		return sc;
	}


	

	

	/**
	 * @return the txtModN
	 */
	public Text getTxtModN() {
		return txtModN;
	}

	/**
	 * @return the grpPlaintext
	 */
	public Group getGrpPlaintext() {
		return grpPlaintext;
	}

	/**
	 * @return the txtPlain
	 */
	public Text getTxtPlain() {
		return txtPlain;
	}

	/**
	 * @return the btnEnc
	 */
	public Button getBtnEnc() {
		return btnEnc;
	}

	



	/**
	 * @return the btnSqrRoot
	 */
	public Button getBtnSqrRoot() {
		return btnSqrRoot;
	}





	

	

	/**
	 * @return the btnComputePt
	 */
	public Button getBtnComputePt() {
		return btnComputePt;
	}

	

	/**
	 * @return the btnNextC
	 */
	public Button getBtnNextC() {
		return btnNextC;
	}

	/**
	 * @return the btnGenKeysMan
	 */
	public Button getBtnGenKeysMan() {
		return btnGenKeysMan;
	}

	/**
	 * @return the btnStartGenKeys
	 */
	public Button getBtnStartGenKeys() {
		return btnStartGenKeys;
	}

	/**
	 * @return the cmbP
	 */
	public Combo getCmbP() {
		return cmbP;
	}

	/**
	 * @return the cmbQ
	 */
	public Combo getCmbQ() {
		return cmbQ;
	}

	/**
	 * @return the cmbBlockN
	 */
	public CCombo getCmbBlockN() {
		return cmbBlockN;
	}

	/**
	 * @return the btnText
	 */
	public Button getBtnText() {
		return btnText;
	}

	/**
	 * @return the btnNum
	 */
	public Button getBtnNum() {
		return btnNum;
	}

	
	public Composite getCompEnterCiphertextSteps() {
		return compEnterCiphertextSteps;
	}
	

	/**
	 * @return the txtMessage
	 */
	public Text getTxtMessage() {
		return txtMessage;
	}

	/**
	 * @return the txtMessageSep
	 */
	public StyledText getTxtMessageSep() {
		return txtMessageSep;
	}

	/**
	 * @return the txtMessageBase
	 */
	public StyledText getTxtMessageBase() {
		return txtMessageBase;
	}

	/**
	 * @return the txtCipher
	 */
	public StyledText getTxtCipher() {
		return txtCipher;
	}

	

	/**
	 * @return the grpPlaintextData
	 */
	public GridData getGrpPlaintextData() {
		return grpPlaintextData;
	}

	/**
	 * @return the compEnterCiphertextData
	 */
	public GridData getCompEnterCiphertextData() {
		return compEnterCiphertextData;
	}

	/**
	 * @return the compEnterCiphertext
	 */
	public Composite getCompEnterCiphertext() {
		return compEnterCiphertext;
	}

	/**
	 * @return the compHoldAllSteps
	 */
	public Composite getCompHoldAllSteps() {
		return compHoldAllSteps;
	}


	/**
	 * @return the encPartSteps
	 */
	public Composite getEncPartSteps() {
		return encPartSteps;
	}

	/**
	 * @return the btnSelectionEnc
	 */
	public Button getBtnSelectionEnc() {
		return btnSelectionEnc;
	}

	/**
	 * @return the btnSelectionDec
	 */
	public Button getBtnSelectionDec() {
		return btnSelectionDec;
	}

	/**
	 * @return the btnApplyCiphertext
	 */
	public Button getBtnApplyCiphertext() {
		return btnApplyCiphertext;
	}

	/**
	 * @return the rootComposite
	 */
	public Composite getRootComposite() {
		return rootComposite;
	}

	

	/**
	 * @return the btnComputeYpYq
	 */
	public Button getBtnComputeYpYq() {
		return btnComputeYpYq;
	}

	/**
	 * @return the btnComputevw
	 */
	public Button getBtnComputevw() {
		return btnComputevw;
	}

	

	/**
	 * @return the compEncStepsNum
	 */
	public Composite getCompEncStepsNum() {
		return compEncStepsNum;
	}

	/**
	 * @return the compEncStepsNumData
	 */
	public GridData getCompEncStepsNumData() {
		return compEncStepsNumData;
	}

	/**
	 * @return the compEncSteps
	 */
	public Composite getCompEncSteps() {
		return compEncSteps;
	}

	/**
	 * @return the compEncStepsData
	 */
	public GridData getCompEncStepsData() {
		return compEncStepsData;
	}

	

	

	
	
	/**
	 * @return the txtMessageSepNum
	 */
	public Text getTxtMessageSepNum() {
		return txtMessageSepNum;
	}

	/**
	 * @return the txtMessageBaseNum
	 */
	public StyledText getTxtMessageBaseNum() {
		return txtMessageBaseNum;
	}

	/**
	 * @return the txtCipherNum
	 */
	public StyledText getTxtCipherNum() {
		return txtCipherNum;
	}

	/**
	 * @return the compEnterCiphertextPart1
	 */
	public Composite getCompEnterCiphertextPart1() {
		return compEnterCiphertextPart1;
	}

	/**
	 * @return the compHoldDecimal
	 */
	public Composite getCompHoldDecimal() {
		return compHoldDecimal;
	}

	/**
	 * @return the btnRadHex
	 */
	public Button getBtnRadHex() {
		return btnRadHex;
	}

	/**
	 * @return the btnRadDecimal
	 */
	public Button getBtnRadDecimal() {
		return btnRadDecimal;
	}

	/**
	 * @return the compEnterCiphertextPart1Data
	 */
	public GridData getCompEnterCiphertextPart1Data() {
		return compEnterCiphertextPart1Data;
	}

	/**
	 * @return the compHoldDecimalData
	 */
	public GridData getCompHoldDecimalData() {
		return compHoldDecimalData;
	}

	

	/**
	 * @return the txtEnterCiphertextDecimal
	 */
	public Text getTxtEnterCiphertextDecimal() {
		return txtEnterCiphertextDecimal;
	}

	/**
	 * @return the txtCiphertextSegmentsDecimal
	 */
	public StyledText getTxtCiphertextSegmentsDecimal() {
		return txtCiphertextSegmentsDecimal;
	}

	/**
	 * @return the txtEnterCiphertext
	 */
	public Text getTxtEnterCiphertext() {
		return txtEnterCiphertext;
	}

	/**
	 * @return the txtCiphertextSegments
	 */
	public StyledText getTxtCiphertextSegments() {
		return txtCiphertextSegments;
	}

	/**
	 * @return the txtWarningNpq
	 */
	public Text getTxtWarningNpq() {
		return txtWarningNpq;
	}

	

	/**
	 * @return the compBlockN
	 */
	public Composite getCompBlockN() {
		return compBlockN;
	}

	/**
	 * @return the txtMessageSepNumWarning
	 */
	public Text getTxtMessageSepNumWarning() {
		return txtMessageSepNumWarning;
	}

	

	/**
	 * @return the txtEnterCiphertextWarning
	 */
	public Text getTxtEnterCiphertextWarning() {
		return txtEnterCiphertextWarning;
	}

	

	/**
	 * @return the txtEnterCiphertextDecimalWarning
	 */
	public Text getTxtEnterCiphertextDecimalWarning() {
		return txtEnterCiphertextDecimalWarning;
	}

	

	/**
	 * @return the txtMessageWarning
	 */
	public Text getTxtMessageWarning() {
		return txtMessageWarning;
	}

	

	/**
	 * @return the btnPrevElem
	 */
	public Button getBtnPrevElem() {
		return btnPrevElem;
	}

	/**
	 * @return the btnNextElem
	 */
	public Button getBtnNextElem() {
		return btnNextElem;
	}

	

	/**
	 * @return the vlNumbers
	 */
	public VerifyListener getVlNumbers() {
		return vlNumbers;
	}

	/**
	 * @return the txtInfoEnc
	 */
	public Text getTxtInfoEnc() {
		return txtInfoEnc;
	}

	/**
	 * @return the txtInfoDec
	 */
	/*public Text getTxtInfoDec() {
		return txtInfoDec;
	}*/


	/**
	 * @return the btnGenKeysAlgo
	 */
	public Button getBtnGenKeysAlgo() {
		return btnGenKeysAlgo;
	}

	
	/**
	 * @return the txtmp
	 */
	public Text getTxtmp() {
		return txtmp;
	}

	/**
	 * @return the txtmq
	 */
	public Text getTxtmq() {
		return txtmq;
	}


	/**
	 * @return the txtyp
	 */
	public Text getTxtyp() {
		return txtyp;
	}

	
	/**
	 * @return the txtyq
	 */
	public Text getTxtyq() {
		return txtyq;
	}

	
	/**
	 * @return the txtm1
	 */
	public Text getTxtm1() {
		return txtm1;
	}


	/**
	 * @return the txtm2
	 */
	public Text getTxtm2() {
		return txtm2;
	}


	
	
	/**
	 * @return the txtm3
	 */
	public Text getTxtm3() {
		return txtm3;
	}


	
	/**
	 * @return the txtm4
	 */
	public Text getTxtm4() {
		return txtm4;
	}


	/**
	 * @return the txtV
	 */
	public Text getTxtV() {
		return txtV;
	}


	
	/**
	 * @return the txtW
	 */
	public Text getTxtW() {
		return txtW;
	}

	
		
	
	/**
	 * @return the cmbChooseCipher
	 */
	public CCombo getCmbChooseCipher() {
		return cmbChooseCipher;
	}

	
	
	
		
	
	
	/**
	 * is not used here but it can still be used in the future
	 */
	private VerifyListener vlNumbers = new VerifyListener() {
		
		@Override
		public void verifyText(VerifyEvent e) {
			
			guiHandler.verifyControlFields(e);			
		}
	};
	
		
	


		
	/**
	 * create group "Setting parameters" and its content
	 * @param parent
	 */
	/*private void createSetParamContent(Composite parent) {
		
		
		// create main group for setting primes and modulus N
		grpParam = new Group(parent, SWT.NONE);
		grpParam.setLayout(new GridLayout(5, false));
		grpParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpParam.setText(Messages.RabinSecondTabComposite_grpParam);
		
		// create composite for entering primes p,q manually
		npqComp = new Composite(grpParam, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		npqComp.setLayout(new GridLayout(2, false));
		
		// create label and combo for prime p in npq composite
		Label lblPrimeP = new Label(npqComp, SWT.NONE);
		lblPrimeP.setText("p ="); //$NON-NLS-1$
		cmbP = new Combo(npqComp, SWT.FLAT);
		cmbP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(cmbP, SWT.DEFAULT, SWT.DEFAULT);
		cmbP.setVisibleItemCount(10);
		
		cmbP.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				//guiHandler.updateTextfields(cmbP, cmbQ, btnGenKeysMan, btnStartGenKeys, txtWarningNpq);
				guiHandler.updateTextfields(getCurrentInstance());
			}
		});
		
		cmbP.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbPSelectionAction(cmbP);
			}
			
		});
		
		pWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		pWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) pWarning.getLayoutData()).horizontalIndent = 28;
		pWarning.setText(Messages.RabinSecondTabComposite_pWarning);
		pWarning.setBackground(guiHandler.getColorBackgroundWarning());
		pWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(pWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(pWarning);
		
		// create label and combo for prime q in npq composite
		Label lblPrimeQ = new Label(npqComp, SWT.NONE);
		lblPrimeQ.setText("q ="); //$NON-NLS-1$
		cmbQ = new Combo(npqComp, SWT.FLAT);
		cmbQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(cmbQ, SWT.DEFAULT, SWT.DEFAULT);
		cmbQ.setVisibleItemCount(10);
		
		cmbQ.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.updateTextfields(getCurrentInstance());
			}
		});
		
		
		cmbQ.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbQSelectionAction(cmbQ, vlNumbers);
			}
			
		});
		
		
		qWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		qWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) qWarning.getLayoutData()).horizontalIndent = 28;
		qWarning.setText(Messages.RabinSecondTabComposite_qWarning);
		qWarning.setBackground(guiHandler.getColorBackgroundWarning());
		qWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(qWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(qWarning);
		
		
		
		// create label for N in npq composite
		Label lblModN = new Label(npqComp, SWT.NONE);
		lblModN.setText("N ="); //$NON-NLS-1$
		txtModN = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
		txtModN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtModN, SWT.DEFAULT, SWT.DEFAULT);
		

		//txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		//txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		//txtWarningNpq.setLayoutData(txtWarningNpqData);
		//guiHandler.setSizeControlWarning(txtWarningNpq, SWT.DEFAULT, SWT.DEFAULT);
		//txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
		//txtWarningNpq.setForeground(ColorService.RED);
		//guiHandler.hideControl(txtWarningNpq);
		
		
		
		nWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		nWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) nWarning.getLayoutData()).horizontalIndent = 28;
		nWarning.setText(Messages.RabinSecondTabComposite_nWarning);
		nWarning.setBackground(guiHandler.getColorBackgroundWarning());
		nWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(nWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(nWarning);
		
		
		
		 	
		Composite compGenKeys = new Composite(grpParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		
		// label separating group for gen keys and info
		Label lblSepKeysInfo = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepKeysInfo.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		// textInfo
		Text txtInfoGenKeys = new Text(grpParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoKeysData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoGenKeys.setLayoutData(txtInfoKeysData);
		guiHandler.setSizeControl(txtInfoGenKeys, SWT.DEFAULT, SWT.DEFAULT);
		String txtInfoGenKeysInfo = Messages.RabinSecondTabComposite_txtInfoGenKeysInfo;
		txtInfoGenKeys.setText(txtInfoGenKeysInfo);
		txtInfoGenKeys.setBackground(ColorService.LIGHTGRAY);
		
		
		btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysMan.setText(Messages.RabinSecondTabComposite_btnGenKeysMan);
		btnGenKeysMan.setSelection(true);
		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//guiHandler.btnGenKeysManAction(getCurrentInstance(), e);
				guiHandler.updateTextfields(getCurrentInstance());
			}
			
		});
	
		btnGenKeysAlgo = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysAlgo.setText(Messages.RabinSecondTabComposite_btnGenKeysAlgo);
		btnGenKeysAlgo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnGenKeysAlgoAction(getCurrentInstance(), e);
			}
		});
		
		btnStartGenKeys = new Button(compGenKeys, SWT.PUSH);
		GridData btnStartGenData = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		btnStartGenData.horizontalIndent = 5;
		btnStartGenKeys.setLayoutData(btnStartGenData);
		btnStartGenKeys.setText(Messages.RabinSecondTabComposite_btnStartGenKeys);		
		
		btnStartGenKeys.addSelectionListener(new SelectionAdapter() {
		
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				guiHandler.btnStartGenKeysAction(getCurrentInstance());	
			}
			
			
		});
			
	}*/
	
	
	
	private void setColors() {
		
		Color colorBG = guiHandler.getColorDarkModeBG();
		Color colorFG = guiHandler.getColorDarkModeFG();
		//Color colorFG = ColorService.YELLOW;
		Color colorTxtWarningFG = guiHandler.getColorDarkModeWarningFG();
		Color colorButtonBG = guiHandler.getColorButtonsBG();
		Color colorButtonFG = guiHandler.getColorButtonsFG();
		Color colorTxtWhichYouCanEnterBG = ColorService.WHITE;
		Color colorTxtWhichYouCanEnterFG = ColorService.BLACK;

	
		
		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
		
		
		compSelNumTxt.setBackground(colorBG);
		compSelNumTxt.setForeground(colorFG);
		compHoldNextC.setBackground(colorBG);
		compHoldNextC.setForeground(colorFG);

		
		grpPlaintext.setBackground(colorBG);
		grpPlaintext.setForeground(colorFG);
		grpDec.setBackground(colorBG);
		grpDec.setForeground(colorFG);
		grpSqrRoot.setBackground(colorBG);
		grpSqrRoot.setForeground(colorFG);
		grpLinCon.setBackground(colorBG);
		grpLinCon.setForeground(colorFG);
		btnEnc.setBackground(colorButtonBG);
		btnEnc.setForeground(colorButtonFG);
		txtCipherFirst.setBackground(colorBG);
		txtCipherFirst.setForeground(colorFG);
		compMp.setBackground(colorBG);
		compMp.setForeground(colorFG);
		txtmp.setBackground(colorBG);
		txtmp.setForeground(colorFG);
		compMq.setBackground(colorBG);
		compMq.setForeground(colorFG);
		txtmq.setBackground(colorBG);
		txtmq.setForeground(colorFG);
		btnSqrRoot.setBackground(colorButtonBG);
		btnSqrRoot.setForeground(colorButtonFG);
		compMerge.setBackground(colorBG);
		compMerge.setForeground(colorFG);
		txtyp.setBackground(colorBG);
		txtyp.setForeground(colorFG);
		txtyq.setBackground(colorBG);
		txtyq.setForeground(colorFG);
		grpPosPlain.setBackground(colorBG);
		grpPosPlain.setForeground(colorFG);
		txtm1.setBackground(colorBG);
		txtm1.setForeground(colorFG);
		txtm2.setBackground(colorBG);
		txtm2.setForeground(colorFG);
		txtm3.setBackground(colorBG);
		txtm3.setForeground(colorFG);
		txtm4.setBackground(colorBG);
		txtm4.setForeground(colorFG);
		compAllPt.setBackground(colorBG);
		compAllPt.setForeground(colorFG);
		btnComputePt.setBackground(colorButtonBG);
		btnComputePt.setForeground(colorButtonFG);
		cmbBlockN.setBackground(colorButtonBG);
		cmbBlockN.setForeground(colorButtonFG);
		btnText.setBackground(colorBG);
		btnText.setForeground(colorFG);
		btnNum.setBackground(colorBG);
		btnNum.setForeground(colorFG);
		txtMessage.setBackground(colorTxtWhichYouCanEnterBG);
		txtMessage.setForeground(colorTxtWhichYouCanEnterFG);
		txtMessageSep.setBackground(colorBG);
		txtMessageSep.setForeground(colorFG);
		txtMessageBase.setBackground(colorBG);
		txtMessageBase.setForeground(colorFG);
		txtCipher.setBackground(colorBG);
		txtCipher.setForeground(colorFG);
		compEnterCiphertext.setBackground(colorBG);
		compEnterCiphertext.setForeground(colorFG);
		compHoldAllSteps.setBackground(colorBG);
		compHoldAllSteps.setForeground(colorFG);
		lblMessage.setBackground(colorBG);
		lblMessage.setForeground(colorFG);
		lblMessageSep.setBackground(colorBG);
		lblMessageSep.setForeground(colorFG);
		encPartSteps.setBackground(colorBG);
		encPartSteps.setForeground(colorFG);
		btnSelectionEnc.setBackground(colorBG);
		btnSelectionEnc.setForeground(colorFG);
		btnSelectionDec.setBackground(colorBG);
		btnSelectionDec.setForeground(colorFG);
		btnApplyCiphertext.setBackground(colorButtonBG);
		btnApplyCiphertext.setForeground(colorButtonFG);
		cmbChooseCipher.setBackground(colorButtonBG);
		cmbChooseCipher.setForeground(colorButtonFG);
		btnComputeYpYq.setBackground(colorButtonBG);
		btnComputeYpYq.setForeground(colorButtonFG);
		btnComputevw.setBackground(colorButtonBG);
		btnComputevw.setForeground(colorButtonFG);
		txtV.setBackground(colorBG);
		txtV.setForeground(colorFG);
		txtW.setBackground(colorBG);
		txtW.setForeground(colorFG);
		compEncStepsNum.setBackground(colorBG);
		compEncStepsNum.setForeground(colorFG);
		compEncSteps.setBackground(colorBG);
		compEncSteps.setForeground(colorFG);
		txtMessageSepNum.setBackground(colorTxtWhichYouCanEnterBG);
		txtMessageSepNum.setForeground(colorTxtWhichYouCanEnterFG);
		txtMessageBaseNum.setBackground(colorBG);
		txtMessageBaseNum.setForeground(colorFG);
		txtCipherNum.setBackground(colorBG);
		txtCipherNum.setForeground(colorFG);
		//compEnterCiphertextPart1.setBackground(colorBG);
		//compEnterCiphertextPart1.setForeground(colorFG);
		compHoldDecimal.setBackground(colorBG);
		compHoldDecimal.setForeground(colorFG);
		btnRadHex.setBackground(colorBG);
		btnRadHex.setForeground(colorFG);
		btnRadDecimal.setBackground(colorBG);
		btnRadDecimal.setForeground(colorFG);
		compEnterCiphertextSteps.setBackground(colorBG);
		compEnterCiphertextSteps.setForeground(colorFG);
		txtEnterCiphertextDecimal.setBackground(colorTxtWhichYouCanEnterBG);
		txtEnterCiphertextDecimal.setForeground(colorTxtWhichYouCanEnterFG);
		txtCiphertextSegmentsDecimal.setBackground(colorBG);
		txtCiphertextSegmentsDecimal.setForeground(colorFG);
		txtEnterCiphertext.setBackground(colorTxtWhichYouCanEnterBG);
		txtEnterCiphertext.setForeground(colorTxtWhichYouCanEnterFG);
		txtCiphertextSegments.setBackground(colorBG);
		txtCiphertextSegments.setForeground(colorFG);
		compBlockN.setBackground(colorBG);
		compBlockN.setForeground(colorFG);
		txtMessageSepNumWarning.setBackground(colorBG);
		txtMessageSepNumWarning.setForeground(colorTxtWarningFG);
		txtEnterCiphertextWarning.setBackground(colorBG);
		txtEnterCiphertextWarning.setForeground(colorTxtWarningFG);
		txtEnterCiphertextDecimalWarning.setBackground(colorBG);
		txtEnterCiphertextDecimalWarning.setForeground(colorTxtWarningFG);
		txtMessageWarning.setBackground(colorBG);
		txtMessageWarning.setForeground(colorTxtWarningFG);
		btnPrevElem.setBackground(colorButtonBG);
		btnPrevElem.setForeground(colorButtonFG);
		btnNextElem.setBackground(colorButtonBG);
		btnNextElem.setForeground(colorButtonFG);
		txtInfoEnc.setBackground(colorBG);
		txtInfoEnc.setForeground(colorFG);
		txtInfoDecimalAndHex.setBackground(colorBG);
		txtInfoDecimalAndHex.setForeground(colorFG);
		txtInfoSquareRoots.setBackground(colorBG);
		txtInfoSquareRoots.setForeground(colorFG);
		txtInfoLC.setBackground(colorBG);
		txtInfoLC.setForeground(colorFG);
		txtInfoPlaintexts.setBackground(colorBG);
		txtInfoPlaintexts.setForeground(colorFG);
		txtMessageWithPadding.setBackground(colorBG);
		txtMessageWithPadding.setForeground(colorFG);
		lblBlockN.setBackground(colorBG);
		lblBlockN.setForeground(colorFG);
		lblMessageSepNum.setBackground(colorBG);
		lblMessageSepNum.setForeground(colorFG);
		lblMessageBaseNum.setBackground(colorBG);
		lblMessageBaseNum.setForeground(colorFG);
		lblCipherNum.setBackground(colorBG);
		lblCipherNum.setForeground(colorFG);
		lblMessageWithPadding.setBackground(colorBG);
		lblMessageWithPadding.setForeground(colorFG);
		lblMessageBase.setBackground(colorBG);
		lblMessageBase.setForeground(colorFG);
		lblCipher.setBackground(colorBG);
		lblCipher.setForeground(colorFG);
		lblSepInfoEnc.setBackground(colorBG);
		lblSepInfoEnc.setForeground(colorFG);
		//compHoldRadHexDec.setBackground(colorBG);
		//compHoldRadHexDec.setForeground(colorFG);
		lblInfoDecimalAndHex.setBackground(colorBG);
		lblInfoDecimalAndHex.setForeground(colorFG);
		lblEnterCiphertextDecimal.setBackground(colorBG);
		lblEnterCiphertextDecimal.setForeground(colorFG);
		lblCiphertextSegmentsDecimal.setBackground(colorBG);
		lblCiphertextSegmentsDecimal.setForeground(colorFG);
		lblEnterCiphertext.setBackground(colorBG);
		lblEnterCiphertext.setForeground(colorFG);
		lblCiphertextSegments.setBackground(colorBG);
		lblCiphertextSegments.setForeground(colorFG);
		//compInfoDec.setBackground(colorBG);
		//compInfoDec.setForeground(colorFG);
		compHoldTestAndSQR.setBackground(colorBG);
		compHoldTestAndSQR.setForeground(colorFG);
		compTest.setBackground(colorBG);
		compTest.setForeground(colorFG);
		compChooseCipher.setBackground(colorBG);
		compChooseCipher.setForeground(colorFG);
		lblChoosCipher.setBackground(colorBG);
		lblChoosCipher.setForeground(colorFG);
		lblCipherFirst.setBackground(colorBG);
		lblCipherFirst.setForeground(colorFG);
		compNavElem.setBackground(colorBG);
		compNavElem.setForeground(colorFG);
		lblSepNavElem.setBackground(colorBG);
		lblSepNavElem.setForeground(colorFG);
		lblSeparateInfoSquareRoots.setBackground(colorBG);
		lblSeparateInfoSquareRoots.setForeground(colorFG);
		lblMp.setBackground(colorBG);
		lblMp.setForeground(colorFG);
		lblMq.setBackground(colorBG);
		lblMq.setForeground(colorFG);
		compHoldLCandInverse.setBackground(colorBG);
		compHoldLCandInverse.setForeground(colorFG);
		lblyp.setBackground(colorBG);
		lblyp.setForeground(colorFG);
		lblyq.setBackground(colorBG);
		lblyq.setForeground(colorFG);
		lblSeparateInfoLC.setBackground(colorBG);
		lblSeparateInfoLC.setForeground(colorFG);
		grpComputeIs.setBackground(colorBG);
		grpComputeIs.setForeground(colorFG);
		compHoldvw.setBackground(colorBG);
		compHoldvw.setForeground(colorFG);
		lblV.setBackground(colorBG);
		lblV.setForeground(colorFG);
		lblW.setBackground(colorBG);
		lblW.setForeground(colorFG);
		compHoldPlaintextsAndInfo.setBackground(colorBG);
		compHoldPlaintextsAndInfo.setForeground(colorFG);
		lblm1.setBackground(colorBG);
		lblm1.setForeground(colorFG);
		lblm2.setBackground(colorBG);
		lblm2.setForeground(colorFG);
		lblm3.setBackground(colorBG);
		lblm3.setForeground(colorFG);
		lblm4.setBackground(colorBG);
		lblm4.setForeground(colorFG);
		lblSeparateInfoPlaintexts.setBackground(colorBG);
		lblSeparateInfoPlaintexts.setForeground(colorFG);
		compSelectEncDec.setBackground(colorBG);
		compSelectEncDec.setForeground(colorFG);

		compHoldRadioHexAndDecAndSteps.setBackground(colorBG);
		compHoldRadioHexAndDecAndSteps.setForeground(colorFG);
		compHoldRadHexDec2.setBackground(colorBG);
		compHoldRadHexDec2.setForeground(colorFG);
		
	}
	
	
	
	
	/**
	 * create group "Encryption"
	 * @param parent
	 */
	private void createEncContent(Composite parent) {
		// create plaintext part
		grpPlaintext = new Group(parent, SWT.NONE);
		grpPlaintextData = new GridData(SWT.FILL, SWT.FILL, true, false);
		grpPlaintext.setLayoutData(grpPlaintextData);
		grpPlaintext.setLayout(new GridLayout(5, false));
		grpPlaintext.setText(Messages.RabinSecondTabComposite_grpPlaintext);
		
		compBlockN = new Composite(grpPlaintext, SWT.NONE);
		compBlockNData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		compBlockN.setLayoutData(compBlockNData);
		compBlockN.setLayout(new GridLayout(1, false));
		
		lblBlockN = new Label(compBlockN, SWT.NONE);
		lblBlockN.setText(Messages.RabinSecondTabComposite_lblBlockN);
		cmbBlockN = new CCombo(compBlockN, SWT.READ_ONLY);
		cmbBlockN.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cmbBlockN.setVisibleItemCount(10);
		cmbBlockN.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.cmbBlockNAction(getCurrentInstance());
			}
			
			
		});
		
		
		
		encPartSteps = new Composite(grpPlaintext, SWT.NONE);
		encPartSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		encPartSteps.setLayout(new GridLayout(1, false));
		
		compSelNumTxt = new Composite(encPartSteps, SWT.NONE);
		compSelNumTxt.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compSelNumTxt.setLayout(new GridLayout(2, false));
		
		btnText = new Button(compSelNumTxt, SWT.RADIO);
		btnText.setText(Messages.RabinSecondTabComposite_btnText);
		btnNum = new Button(compSelNumTxt, SWT.RADIO);
		btnNum.setText(Messages.RabinSecondTabComposite_btnNum);
		
		
		btnNum.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnNumAction(getCurrentInstance(), e);
				//txtInfoEnc.setText(guiHandler.getMessageByControl("txtInfoEnc_Decimal"));
			}
			
			
		});
		
		btnText.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnTextAction(getCurrentInstance(), e);
				//txtInfoEnc.setText(guiHandler.getMessageByControl("txtInfoEnc_Text"));
			}
		});
		
		
		compEncStepsNum = new Composite(encPartSteps, SWT.NONE);
		compEncStepsNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compEncStepsNum.setLayoutData(compEncStepsNumData);
		compEncStepsNum.setLayout(new GridLayout(1, false));
		guiHandler.hideControl(compEncStepsNum);
		
		
		/*compEncStepsNumData.exclude = true;
		compEncStepsNum.setVisible(false);
		encPartSteps.requestLayout();*/
		
		
		lblMessageSepNum = new Label(compEncStepsNum, SWT.NONE);
		lblMessageSepNum.setText(MessageFormat.format(strPlaintextSeparatedIntoSegments, guiHandler.getSeparator()));
		txtMessageSepNum = new Text(compEncStepsNum, SWT.BORDER | SWT.SINGLE);
		GridData txtMessageSepNumData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtMessageSepNum.setLayoutData(txtMessageSepNumData);
		guiHandler.setSizeControl(txtMessageSepNum, SWT.DEFAULT, SWT.DEFAULT);
		
		txtMessageSepNum.addModifyListener(new ModifyListener() {
				
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handleDecimalNumbersEncDecMode(getCurrentInstance());
			}
		});
		
		txtMessageSepNumWarning = new Text(compEncStepsNum, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtMessageSepNumWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageSepNumWarning.setLayoutData(txtMessageSepNumWarningData);
		guiHandler.setSizeControlWarning(txtMessageSepNumWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageSepNumWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtMessageSepNumWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtMessageSepNumWarning);
	
		
		lblMessageBaseNum = new Label(compEncStepsNum, SWT.NONE);
		lblMessageBaseNum.setText(MessageFormat.format(strPlainInBase16, guiHandler.getSeparator()));
		txtMessageBaseNum = new StyledText(compEncStepsNum, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtMessageBaseNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageBaseNum.setLayoutData(txtMessageBaseNumData);
		txtMessageBaseNum.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtMessageBaseNum.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		guiHandler.setSizeControl(txtMessageBaseNum, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageBaseNum.setBackground(guiHandler.getColorBGinfo());
		
		txtMessageBaseNum.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentPlaintextList(), txtMessageBaseNum, getCurrentInstance());
				
			}
			
		});
		txtMessageBaseNum.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtMessageBaseNum, SWT.CURSOR_ARROW);
			}
		});
	
	
		lblCipherNum = new Label(compEncStepsNum, SWT.NONE);
		lblCipherNum.setText(Messages.RabinSecondTabComposite_lblCipherNum);
		txtCipherNum = new StyledText(compEncStepsNum, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCipherNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipherNum.setLayoutData(txtCipherNumData);
		txtCipherNum.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtCipherNum.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		guiHandler.setSizeControl(txtCipherNum, SWT.DEFAULT, SWT.DEFAULT);
		txtCipherNum.setBackground(guiHandler.getColorBGinfo());
		
		txtCipherNum.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCipherNum, getCurrentInstance());
				
			}
			
		});
		txtCipherNum.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCipherNum, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		
		
		
		compEncSteps = new Composite(encPartSteps, SWT.NONE);
		compEncStepsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compEncSteps.setLayoutData(compEncStepsData);
		compEncSteps.setLayout(new GridLayout(1, false));
		
		
		lblMessage = new Label(compEncSteps, SWT.NONE);
		lblMessageData = new GridData();
		lblMessage.setText(Messages.RabinSecondTabComposite_lblMessage);
		txtMessage = new Text(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		txtMessageData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessage.setLayoutData(txtMessageData);
		guiHandler.setSizeControl(txtMessage, SWT.DEFAULT, SWT.DEFAULT);
		txtMessage.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handlePlaintextTextMode(getCurrentInstance());
			}
		});	
		
		
		txtMessageWarning = new Text(compEncSteps, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtMessageWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageWarning.setLayoutData(txtMessageWarningData);
		guiHandler.setSizeControlWarning(txtMessageWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtMessageWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtMessageWarning);
		
		
		
		lblMessageWithPadding = new Label(compEncSteps, SWT.NONE);
		lblMessageWithPadding.setText("Plaintext with padding in hex format (\"20\" as padding)");
		txtMessageWithPadding = new Text(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtMessageWithPaddingData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageWithPadding.setLayoutData(txtMessageWithPaddingData);
		guiHandler.setSizeControl(txtMessageWithPadding, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageWithPadding.setBackground(guiHandler.getColorBGinfo());
		txtMessageWithPadding.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtMessageWithPadding, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
			
		lblMessageSep = new Label(compEncSteps, SWT.NONE);
		lblMessageSep.setText(MessageFormat.format(strPlaintextSeparatedIntoSegments, guiHandler.getSeparator()));
		txtMessageSep = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		txtMessageSepData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageSep.setLayoutData(txtMessageSepData);
		guiHandler.setSizeControl(txtMessageSep, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageSep.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtMessageSep.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtMessageSep.setBackground(guiHandler.getColorBGinfo());
		txtMessageSep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getPlaintextEncodedList(), txtMessageSep, getCurrentInstance());
			}
		});
		txtMessageSep.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtMessageSep, SWT.CURSOR_ARROW);
			}
		});
	
		
				
		
		
		
		lblMessageBase = new Label(compEncSteps, SWT.NONE);
		lblMessageBase.setText(MessageFormat.format(strPlainInBase16, guiHandler.getSeparator()));
		txtMessageBase = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtMessageBaseData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageBase.setLayoutData(txtMessageBaseData);
		guiHandler.setSizeControl(txtMessageBase, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageBase.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtMessageBase.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtMessageBase.setBackground(guiHandler.getColorBGinfo());
		
		txtMessageBase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentPlaintextList(), txtMessageBase, getCurrentInstance());
				
			}
		});
		txtMessageBase.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtMessageBase, SWT.CURSOR_ARROW);
			}
		});
	
		
	
		lblCipher = new Label(compEncSteps, SWT.NONE);
		lblCipher.setText(Messages.RabinSecondTabComposite_lblCipher);
		txtCipher = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtCipherData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipher.setLayoutData(txtCipherData);
		guiHandler.setSizeControl(txtCipher, SWT.DEFAULT, SWT.DEFAULT);
		txtCipher.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtCipher.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtCipher.setBackground(guiHandler.getColorBGinfo());
		
		
		txtCipher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCipher, getCurrentInstance());
			}
		});
		txtCipher.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCipher, SWT.CURSOR_ARROW);
			}
		});
	
		
				
		btnEnc = new Button(grpPlaintext, SWT.PUSH);
		btnEnc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnEnc.setText(Messages.RabinSecondTabComposite_btnEnc);
		btnEnc.setEnabled(false);
		btnEnc.addSelectionListener(new SelectionAdapter() {
			

			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnEncAction(getCurrentInstance());		
			}
			
		});
		
		lblSepInfoEnc = new Label(grpPlaintext, SWT.SEPARATOR | SWT.VERTICAL);
		GridData lblSepInfoEncData = new GridData(SWT.FILL, SWT.FILL, false, true);
		lblSepInfoEnc.setLayoutData(lblSepInfoEncData);
		
		txtInfoEnc = new Text(grpPlaintext, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoEncData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoEnc.setLayoutData(txtInfoEncData);
		guiHandler.setSizeControl(txtInfoEnc, SWT.DEFAULT, SWT.DEFAULT);
		//txtInfoEnc.setText(Messages.RabinSecondTabComposite_txtInfoEnc);
		txtInfoEnc.setText(guiHandler.getMessageByControl("txtInfoEnc_Text"));
		txtInfoEnc.setBackground(ColorService.LIGHTGRAY);
		txtInfoEnc.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoEnc, SWT.CURSOR_ARROW);
			}
		});
	

	}
	
	
	
	
	
	
	
	/**
	 * create group "Decryption" and its content
	 * @param parent
	 */
	private void createDecContent(Composite parent) {
		guiHandler.initStates();
		
		
		/*grpDec = new Group(parent, SWT.NONE);
		//grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 50));
		grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpDec.setLayout(new GridLayout(2, false));
		grpDec.setText(Messages.RabinSecondTabComposite_grpDec);*/
		
		
		grpDec = new Group(parent, SWT.NONE);
		//grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 50));
		grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpDec.setLayout(new GridLayout(1, false));
		grpDec.setText(Messages.RabinSecondTabComposite_grpDec);
		
		
		
		compHoldAllSteps = new Composite(grpDec, SWT.NONE);
		compHoldAllSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldAllSteps.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldAllSteps, 5, 0);
		
		
		/*compEnterCiphertext = new Composite(compHoldAllSteps, SWT.NONE);
		compEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, false);
		compEnterCiphertext.setLayoutData(compEnterCiphertextData);
		compEnterCiphertext.setLayout(new GridLayout(2, false));*/
		
		
		compEnterCiphertext = new Composite(compHoldAllSteps, SWT.NONE);
		compEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compEnterCiphertext.setLayoutData(compEnterCiphertextData);
		compEnterCiphertext.setLayout(new GridLayout(4, false));
		//guiHandler.setControlMargin(compEnterCiphertext, SWT.DEFAULT, 0);
		//((GridLayout) compEnterCiphertext.getLayout()).marginHeight = 0;
		
		guiHandler.hideControl(compEnterCiphertext);
		
		/*compEnterCiphertextData.exclude = true;
		compEnterCiphertext.setVisible(false);
		compHoldAllSteps.requestLayout();*/
		
		
		compHoldRadioHexAndDecAndSteps = new Composite(compEnterCiphertext, SWT.NONE);
		compHoldRadioHexAndDecAndSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldRadioHexAndDecAndSteps.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldRadioHexAndDecAndSteps, SWT.DEFAULT, 0);
		
		
		
		
		/*Composite compHoldRadHexDec = new Composite(compEnterCiphertext, SWT.NONE);
		compHoldRadHexDec.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		compHoldRadHexDec.setLayout(new GridLayout(2, false));*/
		
		
		compHoldRadHexDec2 = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compHoldRadHexDec2.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1));
		compHoldRadHexDec2.setLayout(new GridLayout(2, false));
		
		
		/*compHoldRadHexDec = new Composite(compEnterCiphertext, SWT.NONE);
		compHoldRadHexDec.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1));
		compHoldRadHexDec.setLayout(new GridLayout(2, false));*/
		
		
		btnRadHex = new Button(compHoldRadHexDec2, SWT.RADIO);
		btnRadHex.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnRadHex.setText(Messages.RabinSecondTabComposite_btnRadHex);
		btnRadHex.setSelection(true);
		btnRadHex.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				guiHandler.btnRadHexAction(getCurrentInstance(), e);
			}
			
		});
		
		
		btnRadDecimal = new Button(compHoldRadHexDec2, SWT.RADIO);
		btnRadDecimal.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnRadDecimal.setText(Messages.RabinSecondTabComposite_btnRadDecimal);
		btnRadDecimal.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnRadDecimalAction(getCurrentInstance(), e);
				
			
			}
			
		});
		
		/*compEnterCiphertextSteps = new Composite(compEnterCiphertext, SWT.NONE);
		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));*/
		
		
		btnApplyCiphertext = new Button(compEnterCiphertext, SWT.PUSH);
		btnApplyCiphertext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		btnApplyCiphertext.setText(Messages.RabinSecondTabComposite_btnApplyCiphertext);
		btnApplyCiphertext.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnApplyCiphertextAction(getCurrentInstance());
				
			}
			
		});
		
		
		lblInfoDecimalAndHex = new Label(compEnterCiphertext, SWT.SEPARATOR | SWT.VERTICAL);
		lblInfoDecimalAndHex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		//((GridData) lblInfoDecimalAndHex.getLayoutData()).horizontalIndent = 30;
		
		
		txtInfoDecimalAndHex = new Text(compEnterCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoDecimalAndHex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoDecimalAndHex.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoDecimalAndHex, SWT.DEFAULT, SWT.DEFAULT);
		//txtInfoDecimalAndHex.setText("test test test");
		txtInfoDecimalAndHex.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoDecimalAndHex, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		
		compEnterCiphertextSteps = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));
		
		
		/*compEnterCiphertextSteps = new Composite(compEnterCiphertext, SWT.BORDER);
		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));*/
		//guiHandler.setControlMargin(compEnterCiphertext, SWT.DEFAULT, 0);
		
		
		
		/*compEnterCiphertextPart1 = new Composite(compEnterCiphertextSteps, SWT.BORDER);
		compEnterCiphertextPart1Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		compEnterCiphertextPart1.setLayoutData(compEnterCiphertextPart1Data);
		compEnterCiphertextPart1.setLayout(new GridLayout(1, false));*/
		//guiHandler.setControlMargin(compEnterCiphertextPart1, SWT.DEFAULT, 0);
		
		/*btnApplyCiphertext = new Button(compEnterCiphertext, SWT.PUSH);
		btnApplyCiphertext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		btnApplyCiphertext.setText(Messages.RabinSecondTabComposite_btnApplyCiphertext);
		btnApplyCiphertext.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnApplyCiphertextAction(getCurrentInstance());
				
			}
			
		});*/
		
		
		/*compHoldDecimal = new Composite(compEnterCiphertextSteps, SWT.BORDER);
		compHoldDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compHoldDecimal.setLayoutData(compHoldDecimalData);
		compHoldDecimal.setLayout(new GridLayout(1, false));*/
		
		compHoldDecimal = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compHoldDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compHoldDecimal.setLayoutData(compHoldDecimalData);
		compHoldDecimal.setLayout(new GridLayout(1, false));
		
		//guiHandler.setControlMargin(compHoldDecimal, SWT.DEFAULT, 0);
		
		/*compHoldDecimalData.exclude = true;
		compHoldDecimal.setVisible(false);
		compEnterCiphertextSteps.requestLayout();*/
		
		guiHandler.hideControl(compHoldDecimal);
		
		
		
		lblEnterCiphertextDecimal = new Label(compHoldDecimal, SWT.NONE);
		String strCipherInBase10 = Messages.RabinSecondTabComposite_strCipherInBase10;
		lblEnterCiphertextDecimal.setText(MessageFormat.format(strCipherInBase10, guiHandler.getSeparator()));
		
		txtEnterCiphertextDecimal = new Text(compHoldDecimal, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData txtEnterCiphertextDecimalData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtEnterCiphertextDecimal.setLayoutData(txtEnterCiphertextDecimalData);
		guiHandler.setSizeControl(txtEnterCiphertextDecimal, SWT.DEFAULT, SWT.DEFAULT);
		
		txtEnterCiphertextDecimal.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handleDecimalNumbersDecMode(getCurrentInstance());
			}
		});
		txtEnterCiphertextDecimal.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtEnterCiphertextDecimal, SWT.CURSOR_ARROW);
			}
		});
	
		
		txtEnterCiphertextDecimalWarning = new Text(compHoldDecimal, SWT.MULTI | SWT.WRAP);
		txtEnterCiphertextDecimalWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextDecimalWarning.setLayoutData(txtEnterCiphertextDecimalWarningData);
		guiHandler.setSizeControlWarning(txtEnterCiphertextDecimalWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtEnterCiphertextDecimalWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtEnterCiphertextDecimalWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtEnterCiphertextDecimalWarning);
		
		
		lblCiphertextSegmentsDecimal = new Label(compHoldDecimal, SWT.NONE);
		String strCipherInBase16 = Messages.RabinSecondTabComposite_strCipherInBase16;
		lblCiphertextSegmentsDecimal.setText(MessageFormat.format(strCipherInBase16, guiHandler.getSeparator()));
		
		txtCiphertextSegmentsDecimal = new StyledText(compHoldDecimal, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegmentsDecimal.setLayoutData(txtCiphertextSegmentsDecimalData);
		txtCiphertextSegmentsDecimal.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtCiphertextSegmentsDecimal.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtCiphertextSegmentsDecimal.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtCiphertextSegmentsDecimal, SWT.DEFAULT, SWT.DEFAULT);
		
		txtCiphertextSegmentsDecimal.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCiphertextSegmentsDecimal, getCurrentInstance());
				
			}
			
		});
		txtCiphertextSegmentsDecimal.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCiphertextSegmentsDecimal, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		
		/*lblEnterCiphertext = new Label(compEnterCiphertextPart1, SWT.NONE);
		lblEnterCiphertext.setText(Messages.RabinSecondTabComposite_lblEnterCiphertext);
		
		txtEnterCiphertext = new Text(compEnterCiphertextPart1, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData txtEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertext.setLayoutData(txtEnterCiphertextData);
		guiHandler.setSizeControl(txtEnterCiphertext, SWT.DEFAULT, SWT.DEFAULT);
		txtEnterCiphertext.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handleHexNumDecMode(getCurrentInstance());
			}
		});*/
		
		
		lblEnterCiphertext = new Label(compEnterCiphertextSteps, SWT.NONE);
		lblEnterCiphertext.setText(Messages.RabinSecondTabComposite_lblEnterCiphertext);
		
		txtEnterCiphertext = new Text(compEnterCiphertextSteps, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData txtEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertext.setLayoutData(txtEnterCiphertextData);
		guiHandler.setSizeControl(txtEnterCiphertext, SWT.DEFAULT, SWT.DEFAULT);
		txtEnterCiphertext.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handleHexNumDecMode(getCurrentInstance());
			}
		});
		
		
		
		txtEnterCiphertextWarning = new Text(compEnterCiphertextSteps, SWT.MULTI | SWT.WRAP);
		txtEnterCiphertextWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextWarning.setLayoutData(txtEnterCiphertextWarningData);
		txtEnterCiphertextWarning.setForeground(guiHandler.getColorForegroundWarning());
		txtEnterCiphertextWarning.setBackground(guiHandler.getColorBackgroundWarning());
		guiHandler.setSizeControlWarning(txtEnterCiphertextWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEnterCiphertextWarning);
		
		
		lblCiphertextSegments = new Label(compEnterCiphertextSteps, SWT.NONE);
		String strCipherIntoSegments = Messages.RabinSecondTabComposite_strCipherIntoSegments;
		lblCiphertextSegments.setText(MessageFormat.format(strCipherIntoSegments, guiHandler.getSeparator()));
		
		txtCiphertextSegments = new StyledText(compEnterCiphertextSteps, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegments.setLayoutData(txtCiphertextSegmentsData);
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
		txtCiphertextSegments.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtCiphertextSegments.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtCiphertextSegments.setBackground(guiHandler.getColorBGinfo());
		
		
		
		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCiphertextSegments, getCurrentInstance());
			}
			
		});
		txtCiphertextSegments.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCiphertextSegments, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		/*txtEnterCiphertextWarning = new Text(compEnterCiphertextPart1, SWT.MULTI | SWT.WRAP);
		txtEnterCiphertextWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextWarning.setLayoutData(txtEnterCiphertextWarningData);
		txtEnterCiphertextWarning.setForeground(guiHandler.getColorForegroundWarning());
		txtEnterCiphertextWarning.setBackground(guiHandler.getColorBackgroundWarning());
		guiHandler.setSizeControlWarning(txtEnterCiphertextWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEnterCiphertextWarning);
		
		
		lblCiphertextSegments = new Label(compEnterCiphertextPart1, SWT.NONE);
		String strCipherIntoSegments = Messages.RabinSecondTabComposites_strCipherIntoSegments;
		lblCiphertextSegments.setText(MessageFormat.format(strCipherIntoSegments, guiHandler.getSeparator()));
		
		txtCiphertextSegments = new StyledText(compEnterCiphertextPart1, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegments.setLayoutData(txtCiphertextSegmentsData);
		txtCiphertextSegments.setSelectionBackground(guiHandler.getColorSelectionStyledTextBG());
		txtCiphertextSegments.setSelectionForeground(guiHandler.getColorSelectionStyledTextFG());
		txtCiphertextSegments.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
		
		
		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCiphertextSegments, getCurrentInstance());
			}
			
		});*/
		
		
		/*Label lblInfoDecimalAndHex = new Label(compEnterCiphertext, SWT.SEPARATOR | SWT.VERTICAL);
		lblInfoDecimalAndHex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		
		Text txtInfoDecimalAndHex = new Text(compEnterCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoDecimalAndHex.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoDecimalAndHex.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoDecimalAndHex, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoDecimalAndHex.setText("test test test");*/
		
		
		
		/*compInfoDec = new Composite(grpDec, SWT.NONE);
		GridData compInfoDecData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compInfoDec.setLayoutData(compInfoDecData);
		compInfoDec.setLayout(new GridLayout(2, false));*/
		
		
		
		/*Composite compHoldTestAndInfo = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldTestAndInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldTestAndInfo.setLayout(new GridLayout(3, false));*/
		
		
		
		/*Composite compHoldTestAndSQR = new Composite(compHoldTestAndInfo, SWT.NONE);
		compHoldTestAndSQR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldTestAndSQR.setLayout(new GridLayout(1, false));*/
		
		compHoldTestAndSQR = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldTestAndSQR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldTestAndSQR.setLayout(new GridLayout(3, false));
		//guiHandler.setSizeControl(compHoldTestAndSQR, 300, 400);
		
		
		
		
		
		/*Composite compTest = new Composite(compHoldAllSteps, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compTest.setLayout(new GridLayout(4, false));*/
		
		
		compTest = new Composite(compHoldTestAndSQR, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compTest.setLayout(new GridLayout(4, false));
		
		
		
		compChooseCipher = new Composite(compTest, SWT.NONE);
		compChooseCipher.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		compChooseCipher.setLayout(new GridLayout(1, false));
		
		lblChoosCipher = new Label(compChooseCipher, SWT.NONE);
		lblChoosCipher.setText(Messages.RabinSecondTabComposite_lblChoosCipher);
		cmbChooseCipher = new CCombo(compChooseCipher, SWT.READ_ONLY | SWT.DROP_DOWN);
		cmbChooseCipher.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		cmbChooseCipher.setVisibleItemCount(10);
		cmbChooseCipher.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbChooseCipherAction(getCurrentInstance());
				
			}
			
		});
		
	
	
		lblCipherFirst = new Label(compTest, SWT.NONE);
		lblCipherFirst.setText("c[i] = "); //$NON-NLS-1$
				
		
		// create textbox for first block of the ciphertext
		txtCipherFirst = new Text(compTest, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtCipherFirstData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipherFirst.setLayoutData(txtCipherFirstData);
		txtCipherFirst.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtCipherFirst, SWT.DEFAULT, SWT.DEFAULT);
		
		txtCipherFirst.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtCipherFirstModifyAction(getCurrentInstance());
				
			}
		});
		txtCipherFirst.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCipherFirst, SWT.CURSOR_ARROW);
			}
		});
	
		
		compNavElem = new Composite(compTest, SWT.NONE);
		compNavElem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		compNavElem.setLayout(new GridLayout(2, false));
		
		btnPrevElem = new Button(compNavElem, SWT.PUSH);
		btnPrevElem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnPrevElem.setText("Previous block"); //$NON-NLS-1$
		btnPrevElem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnPrevElemAction(getCurrentInstance());
				
			}
		});
		
	
		
		btnNextElem = new Button(compNavElem, SWT.PUSH);
		btnNextElem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//btnNextElem.pack(false);
		//btnNextElem.setSize(btnPrevElem.getSize());
		//((GridData) btnNextElem.getLayoutData()).
		guiHandler.setSizeControl(btnNextElem, btnPrevElem.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, SWT.DEFAULT);
		btnNextElem.setText("Next block"); //$NON-NLS-1$
		btnNextElem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnNextElemAction(getCurrentInstance());
		
			}
		});
		
		lblSepNavElem = new Label(compNavElem, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepNavElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		// create button for computing squareroots
		btnSqrRoot = new Button(compNavElem, SWT.PUSH);
		btnSqrRoot.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnSqrRoot.setText(Messages.RabinSecondTabComposite_btnSqrRoot);
		
		btnSqrRoot.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnSquareRootAction(getCurrentInstance());
					
			}
			
		});
		
		
		// label for separating info txt
		/*Label lblSepDecInfo = new Label(compInfoDec, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepDecInfo.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		
		txtInfoDec = new Text(compInfoDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoDecData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		txtInfoDec.setLayoutData(txtInfoDecData);
		guiHandler.setSizeControl(txtInfoDec, SWT.DEFAULT, 250);
		txtInfoDec.setText(Messages.RabinSecondTabComposite_txtInfoDec);
		txtInfoDec.setBackground(ColorService.LIGHTGRAY);*/
		
		
		
		lblSeparateInfoSquareRoots = new Label(compHoldTestAndSQR, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoSquareRoots.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
	
		
		txtInfoSquareRoots = new Text(compHoldTestAndSQR, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSquareRoots.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		txtInfoSquareRoots.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoSquareRoots, SWT.DEFAULT, SWT.DEFAULT);
		//txtInfoSquareRoots.setText("test test test");
		txtInfoSquareRoots.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoSquareRoots, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		// create group for computing square roots
		/*grpSqrRoot = new Group(compHoldAllSteps, SWT.NONE);
		grpSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSqrRoot.setLayout(new GridLayout(2, false));
		grpSqrRoot.setText(Messages.RabinSecondTabComposite_grpSqrRoot);*/
		
		grpSqrRoot = new Group(compHoldTestAndSQR, SWT.NONE);
		grpSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSqrRoot.setLayout(new GridLayout(2, false));
		//guiHandler.setSizeControl(grpSqrRoot, 700, 150);
		grpSqrRoot.setText(Messages.RabinSecondTabComposite_grpSqrRoot);
		

		// create group for m_p
		compMp = new Composite(grpSqrRoot, SWT.NONE);
		compMp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMp.setLayout(new GridLayout(1, false));
		
		lblMp = new Label(compMp, SWT.NONE);
		lblMp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblMp.setText(Messages.RabinSecondTabComposite_lblMp);
		
		// create txtbox for m_p
		txtmp = new Text(compMp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtmpData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtmp.setLayoutData(txtmpData);
		txtmp.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtmp, SWT.DEFAULT, SWT.DEFAULT);
		
		txtmp.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtMpAction(getCurrentInstance());
				
			}
		});
		txtmp.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtmp, SWT.CURSOR_ARROW);
			}
		});
	
		
		// create group for m_q
		compMq = new Composite(grpSqrRoot, SWT.NONE);
		compMq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMq.setLayout(new GridLayout(1, false));
		
		lblMq = new Label(compMq, SWT.NONE);
		lblMq.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblMq.setText(Messages.RabinSecondTabComposite_lblMq);
		
		// create txtbox for m_p
		txtmq = new Text(compMq, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtmqData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtmq.setLayoutData(txtmqData);
		txtmq.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtmq, SWT.DEFAULT, SWT.DEFAULT);
		
		txtmq.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtMqAction(getCurrentInstance());
				
			}
		});
		txtmq.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtmq, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		/*Label lblSeparateInfoSquareRoots = new Label(compHoldTestAndInfo, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoSquareRoots.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
	
		
		Text txtInfoSquareRoots = new Text(compHoldTestAndInfo, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtInfoSquareRoots.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoSquareRoots.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoSquareRoots, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSquareRoots.setText("test test test");*/
		
		
		/*Composite compHoldLinearCongruencesAndInverseAndInfo = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldLinearCongruencesAndInverseAndInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldLinearCongruencesAndInverseAndInfo.setLayout(new GridLayout(3, false));*/
		
		
		/*Composite compHoldLCandInverse = new Composite(compHoldLinearCongruencesAndInverseAndInfo, SWT.NONE);
		compHoldLCandInverse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldLCandInverse.setLayout(new GridLayout(1, false));*/
		
		compHoldLCandInverse = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldLCandInverse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldLCandInverse.setLayout(new GridLayout(3, false));
		//guiHandler.setSizeControl(compHoldLCandInverse, SWT.DEFAULT + 20, SWT.DEFAULT);
		//guiHandler.setControlMargin(compHoldLCandInverse, SWT.DEFAULT, 0);
		
		
		
		
		// create group for linear congruences
		/*grpLinCon = new Group(compHoldAllSteps, SWT.NONE);
		grpLinCon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpLinCon.setLayout(new GridLayout(2, false));
		grpLinCon.setText(Messages.RabinSecondTabComposite_grpLinCon);*/
		
		grpLinCon = new Group(compHoldLCandInverse, SWT.NONE);
		grpLinCon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpLinCon.setLayout(new GridLayout(2, false));
		grpLinCon.setText(Messages.RabinSecondTabComposite_grpLinCon);
		
		
		// create composite for first 2 congruences 
		compMerge = new Composite(grpLinCon, SWT.NONE);
		compMerge.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMerge.setLayout(new GridLayout(2, false));
		
		
		// create linear congruence
		// m1 = mp mod p
		// m1 = mq mod q
		
		
		lblyp = new Label(compMerge, SWT.NONE);
		lblyp.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblyp.setText("y_p = "); //$NON-NLS-1$
		
		// create y_p txtbox
		txtyp = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtypData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtyp.setLayoutData(txtypData);
		txtyp.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtyp, SWT.DEFAULT, SWT.DEFAULT);
		
		txtyp.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtYpAction(getCurrentInstance());
			}
		});
		txtyp.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtyp, SWT.CURSOR_ARROW);
			}
		});
	
		
		lblyq = new Label(compMerge, SWT.NONE);
		lblyq.setText("y_q = "); //$NON-NLS-1$
		
		// create txtbox for y_q
		txtyq = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtyqData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtyq.setLayoutData(txtyqData);
		txtyq.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtyq, SWT.DEFAULT, SWT.DEFAULT);
		
		txtyq.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtYqAction(getCurrentInstance());
			}
		});
		txtyq.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtyq, SWT.CURSOR_ARROW);
			}
		});
	
		
		btnComputeYpYq = new Button(grpLinCon, SWT.PUSH);
		btnComputeYpYq.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnComputeYpYq.setText(Messages.RabinSecondTabComposite_btnComputeYpYq);
		btnComputeYpYq.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnComputeYpYqAction(getCurrentInstance());
				
			}
			
		});
		
		lblSeparateInfoLC = new Label(compHoldLCandInverse, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoLC.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));
		
		txtInfoLC = new Text(compHoldLCandInverse, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoLC.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 1, 2));
		txtInfoLC.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoLC, SWT.DEFAULT, 289);
		txtInfoLC.setText("test test test");
		txtInfoLC.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoLC, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		
		// create group for computing v = y_p * p * m_q
		// and w = y_q * q * m_p
		/*Group grpComputeIs = new Group(compHoldAllSteps, SWT.NONE);
		grpComputeIs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpComputeIs.setLayout(new GridLayout(2, false));
		grpComputeIs.setText(Messages.RabinSecondTabComposite_grpComputeIs);*/
		
		grpComputeIs = new Group(compHoldLCandInverse, SWT.NONE);
		grpComputeIs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpComputeIs.setLayout(new GridLayout(2, false));
		grpComputeIs.setText(Messages.RabinSecondTabComposite_grpComputeIs);
		
		compHoldvw = new Composite(grpComputeIs, SWT.NONE);
		compHoldvw.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldvw.setLayout(new GridLayout(2, false));
		
		btnComputevw = new Button(grpComputeIs, SWT.PUSH);
		btnComputevw.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnComputevw.setText(Messages.RabinSecondTabComposite_btnComputevw);
		btnComputevw.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnComputevwAction(getCurrentInstance());
					
			}
		});
		
		lblV = new Label(compHoldvw, SWT.NONE);
		lblV.setText("v = "); //$NON-NLS-1$
		txtV = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtVData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtV.setLayoutData(txtVData);
		txtV.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtV, SWT.DEFAULT, SWT.DEFAULT);
		
		txtV.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtVAction(getCurrentInstance());
				
			}
		});	
		txtV.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtV, SWT.CURSOR_ARROW);
			}
		});
	
		
		lblW = new Label(compHoldvw, SWT.NONE);
		lblW.setText("w = "); //$NON-NLS-1$
		txtW = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtWData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtW.setLayoutData(txtWData);
		txtW.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtW, SWT.DEFAULT, SWT.DEFAULT);
		
		txtW.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtWAction(getCurrentInstance());
				
			}
		});	
		txtW.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtW, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		/*Label lblSeparateInfoLC = new Label(compHoldLinearCongruencesAndInverseAndInfo, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoLC.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		Text txtInfoLC = new Text(compHoldLinearCongruencesAndInverseAndInfo, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtInfoLC.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoLC.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoLC, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoLC.setText("test test test");*/
		
		compHoldPlaintextsAndInfo = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldPlaintextsAndInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldPlaintextsAndInfo.setLayout(new GridLayout(3, false));
		guiHandler.setControlMargin(compHoldPlaintextsAndInfo, 5, 0);
		
		
		// create group for computing plaintexts
		/*grpPosPlain = new Group(compHoldAllSteps, SWT.NONE);
		grpPosPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpPosPlain.setLayout(new GridLayout(2, false));
		grpPosPlain.setText(Messages.RabinSecondTabComposite_grpPosPlain);*/
		
		
		grpPosPlain = new Group(compHoldPlaintextsAndInfo, SWT.NONE);
		grpPosPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpPosPlain.setLayout(new GridLayout(2, false));
		grpPosPlain.setText(Messages.RabinSecondTabComposite_grpPosPlain);
		
		
		// composite for packing all plaintexts for better structure
		compAllPt = new Composite(grpPosPlain, SWT.NONE);
		compAllPt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compAllPt.setLayout(new GridLayout(2, false));
		
		lblm1 = new Label(compAllPt, SWT.NONE);
		lblm1.setText(Messages.RabinSecondTabComposite_lblm1);
		
		// txt for m1
		txtm1 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm1Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm1.setLayoutData(txtm1Data);
		txtm1.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtm1, SWT.DEFAULT, SWT.DEFAULT);
		txtm1.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtm1, SWT.CURSOR_ARROW);
			}
		});
	
		
		lblm2 = new Label(compAllPt, SWT.NONE);
		lblm2.setText(Messages.RabinSecondTabComposite_lblm2);
		
		// txt for m2
		txtm2 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm2Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm2.setLayoutData(txtm2Data);
		txtm2.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtm2, SWT.DEFAULT, SWT.DEFAULT);
		txtm2.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtm2, SWT.CURSOR_ARROW);
			}
		});
	
		
		lblm3 = new Label(compAllPt, SWT.NONE);
		lblm3.setText(Messages.RabinSecondTabComposite_lblm3);
		
		// txt for m3
		txtm3 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm3Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm3.setLayoutData(txtm3Data);
		txtm3.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtm3, SWT.DEFAULT, SWT.DEFAULT);
		txtm3.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtm3, SWT.CURSOR_ARROW);
			}
		});
	
		
		lblm4 = new Label(compAllPt, SWT.NONE);
		lblm4.setText(Messages.RabinSecondTabComposite_lblm4);
		
		// txt for m4
		txtm4 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm4Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm4.setLayoutData(txtm4Data);
		txtm4.setBackground(guiHandler.getColorBGinfo());
		
		guiHandler.setSizeControl(txtm4, SWT.DEFAULT, SWT.DEFAULT);
		txtm4.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtm4, SWT.CURSOR_ARROW);
			}
		});
	
		
		compHoldNextC = new Composite(grpPosPlain, SWT.NONE);
		compHoldNextC.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		compHoldNextC.setLayout(new GridLayout(1, false));
		
		// create button for computing all plaintexts
		btnComputePt = new Button(compHoldNextC, SWT.PUSH);
		btnComputePt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		btnComputePt.setText(Messages.RabinSecondTabComposite_btnComputePt);
		btnComputePt.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnComputePtAction(getCurrentInstance());
							
			}
		});
		
		
		lblSeparateInfoPlaintexts = new Label(compHoldPlaintextsAndInfo, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		
		txtInfoPlaintexts = new Text(compHoldPlaintextsAndInfo, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true));
		//((GridData) txtInfoPlaintexts.getLayoutData()).heightHint = 20;
		txtInfoPlaintexts.setBackground(guiHandler.getColorBGinfo());
		//guiHandler.setSizeControl(txtInfoPlaintexts, 100, 10);
		guiHandler.setSizeControl(txtInfoPlaintexts, SWT.DEFAULT, grpPosPlain.computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 10);
		//txtInfoPlaintexts.setText("test test test");
		txtInfoPlaintexts.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoPlaintexts, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		//guiHandler.setSizeControl(grpPosPlain, SWT.DEFAULT + 300, SWT.DEFAULT);
		guiHandler.setSizeControl(grpPosPlain, SWT.DEFAULT + 536, SWT.DEFAULT);
		guiHandler.setSizeControl(compTest, SWT.DEFAULT + 535, SWT.DEFAULT);
		guiHandler.setSizeControl(compHoldTestAndSQR, SWT.DEFAULT + 400, SWT.DEFAULT);
		//guiHandler.setSizeControl(compEnterCiphertextSteps, SWT.DEFAULT + 478, SWT.DEFAULT);	
		guiHandler.setSizeControl(txtEnterCiphertext, 464, SWT.DEFAULT);
	}
	
	
	
	
	
	/**
	 * create composite containing the encryption and decryption
	 * radio buttons for selecting one of them 
	 * @param parent
	 */
	private void createSelectEncDecContent(Composite parent) {
		compSelectEncDec = new Composite(parent, SWT.NONE);
		compSelectEncDec.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compSelectEncDec.setLayout(new GridLayout(4, false));
		((GridData) compSelectEncDec.getLayoutData()).verticalIndent = 40;
		((GridLayout) compSelectEncDec.getLayout()).marginBottom = 15;	
		
		btnSelectionEnc = new Button(compSelectEncDec, SWT.RADIO);
		btnSelectionEnc.setText(Messages.RabinSecondTabComposite_btnSelectionEnc);
		btnSelectionEnc.setSelection(true);
		
		btnSelectionEnc.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnSelectionEncAction(getCurrentInstance(), e);
				
			}
		});
		
		btnSelectionDec = new Button(compSelectEncDec, SWT.RADIO);
		btnSelectionDec.setText(Messages.RabinSecondTabComposite_btnSelectionDec);
		
		btnSelectionDec.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnSelectionDecAction(getCurrentInstance(), e);
				
			}
			
		});
	}
	
	
	
	
	
	
	/**
	 * create the content of the Algorithm tab
	 */
	private void createContent() {
		this.setLayout(new GridLayout(1, false));
		
		//sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		/*sc = new ScrolledComposite(this, SWT.NONE);
		sc.setLayout(new GridLayout(1, false));
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootComposite = new Composite(sc, SWT.BORDER);
		rootComposite.setLayout(new GridLayout(1, false));
		rootComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sc.setContent(rootComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);*/
		//createSetParamContent(rootComposite);
		//createSetParamContent(this);
		
		//guiHandler.initializePrimes(100, getCurrentInstance());
		
		/*createSelectEncDecContent(rootComposite);
		
		createEncContent(rootComposite);
		createDecContent(rootComposite);*/
		
		createSelectEncDecContent(this);
		
		createEncContent(this);
		createDecContent(this);
		
		
		if(guiHandler.getDarkmode())
			setColors();
		
		guiHandler.initializeComponents(getCurrentInstance());
		
		
		
		//sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	
	
	
	/**
	 * @param parent
	 * @param style
	 */
	public RabinSecondTabComposite(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout(1, false));
		createContent();
	}
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabin
	 * @param rabinFromFirstTab
	 * @param rootScrolledComposite
	 * @param rootComposite
	 */
	public RabinSecondTabComposite(Composite parent, int style, Rabin rabin, Rabin rabinFromFirstTab, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
		super(parent, style);	
		this.guiHandler = new HandleSecondTab(rootScrolledComposite, rootComposite, rabin, rabinFromFirstTab);
		createContent();
	}
	
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public RabinSecondTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond) {
		super(parent, style);	
		this.guiHandler = new HandleSecondTab(sc, rootComposite, rabinFirst, rabinSecond);
		createContent();
	}

}
