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
	private Text txtInfoDec;
	private ScrolledComposite sc;
	private Button btnGenKeysAlgo;
	private GridData compBlockNData;
	
	private Text nWarning;
	private Text qWarning;
	private Text pWarning;
	
	private Color selectionBGC = ColorService.LIGHT_AREA_BLUE;
	private Color selectionFGC = ColorService.BLACK;
	
	private String strPlaintextSeparatedIntoSegments = Messages.RabinSecondTabComposite_strPlaintextSeparatedIntoSegments;
	private String strPlainInBase16 = Messages.RabinSecondTabComposite_strPlainInBase16;
	
	
	private HandleSecondTab guiHandler;
	
	
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
	public Text getTxtInfoDec() {
		return txtInfoDec;
	}


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
	private void createSetParamContent(Composite parent) {
		
		
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
				guiHandler.cmbPSelectionAction(cmbP, vlNumbers);
			}
			
		});
		
		pWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		pWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) pWarning.getLayoutData()).horizontalIndent = 28;
		pWarning.setText(Messages.RabinSecondTabComposite_pWarning);
		pWarning.setBackground(ColorService.LIGHTGRAY);
		pWarning.setForeground(ColorService.RED);
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
		
		
		qWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		qWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) qWarning.getLayoutData()).horizontalIndent = 28;
		qWarning.setText(Messages.RabinSecondTabComposite_qWarning);
		qWarning.setBackground(ColorService.LIGHTGRAY);
		qWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(qWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(qWarning);
		
		
		
		// create label for N in npq composite
		Label lblModN = new Label(npqComp, SWT.NONE);
		lblModN.setText("N ="); //$NON-NLS-1$
		txtModN = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
		txtModN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtModN, SWT.DEFAULT, SWT.DEFAULT);
		

		/*txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtWarningNpq.setLayoutData(txtWarningNpqData);
		guiHandler.setSizeControlWarning(txtWarningNpq, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
		txtWarningNpq.setForeground(ColorService.RED);
		guiHandler.hideControl(txtWarningNpq);*/
		
		
		
		nWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		nWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) nWarning.getLayoutData()).horizontalIndent = 28;
		nWarning.setText(Messages.RabinSecondTabComposite_nWarning);
		nWarning.setBackground(ColorService.LIGHTGRAY);
		nWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(nWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(nWarning);
		
		
		
		 	
		Composite compGenKeys = new Composite(grpParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		
		// label separating group for gen keys and info
		Label lblSepKeysInfo = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepKeysInfo.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		// textInfo
		Text txtInfoGenKeys = new Text(grpParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
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
		
		Label lblBlockN = new Label(compBlockN, SWT.NONE);
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
		
		// create composite for numbers and text selection
		Composite compSelNumTxt = new Composite(encPartSteps, SWT.NONE);
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
	
			}
			
			
		});
		
		btnText.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnTextAction(getCurrentInstance(), e);
				
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
		
		
		Label lblMessageSepNum = new Label(compEncStepsNum, SWT.NONE);
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
		
		txtMessageSepNumWarning = new Text(compEncStepsNum, SWT.MULTI | SWT.WRAP);
		txtMessageSepNumWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageSepNumWarning.setLayoutData(txtMessageSepNumWarningData);
		guiHandler.setSizeControlWarning(txtMessageSepNumWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageSepNumWarning.setBackground(ColorService.LIGHTGRAY);
		txtMessageSepNumWarning.setForeground(ColorService.RED);
		guiHandler.hideControl(txtMessageSepNumWarning);
	
		
		Label lblMessageBaseNum = new Label(compEncStepsNum, SWT.NONE);
		lblMessageBaseNum.setText(MessageFormat.format(strPlainInBase16, guiHandler.getSeparator()));
		txtMessageBaseNum = new StyledText(compEncStepsNum, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtMessageBaseNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageBaseNum.setLayoutData(txtMessageBaseNumData);
		txtMessageBaseNum.setSelectionBackground(selectionBGC);
		txtMessageBaseNum.setSelectionForeground(selectionFGC);
		guiHandler.setSizeControl(txtMessageBaseNum, SWT.DEFAULT, SWT.DEFAULT);
		
		txtMessageBaseNum.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentPlaintextList(), txtMessageBaseNum, getCurrentInstance());
				
			}
			
		});
	
		Label lblCipherNum = new Label(compEncStepsNum, SWT.NONE);
		lblCipherNum.setText(Messages.RabinSecondTabComposite_lblCipherNum);
		txtCipherNum = new StyledText(compEncStepsNum, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCipherNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipherNum.setLayoutData(txtCipherNumData);
		txtCipherNum.setSelectionBackground(selectionBGC);
		txtCipherNum.setSelectionForeground(selectionFGC);
		guiHandler.setSizeControl(txtCipherNum, SWT.DEFAULT, SWT.DEFAULT);
		
		txtCipherNum.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCipherNum, getCurrentInstance());
				
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
		
		
		txtMessageWarning = new Text(compEncSteps, SWT.MULTI | SWT.WRAP);
		txtMessageWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageWarning.setLayoutData(txtMessageWarningData);
		guiHandler.setSizeControlWarning(txtMessageWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageWarning.setBackground(ColorService.LIGHTGRAY);
		txtMessageWarning.setForeground(ColorService.RED);
		guiHandler.hideControl(txtMessageWarning);
		
			
		lblMessageSep = new Label(compEncSteps, SWT.NONE);
		lblMessageSep.setText(MessageFormat.format(strPlaintextSeparatedIntoSegments, guiHandler.getSeparator()));
		txtMessageSep = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		txtMessageSepData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageSep.setLayoutData(txtMessageSepData);
		guiHandler.setSizeControl(txtMessageSep, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageSep.setSelectionBackground(selectionBGC);
		txtMessageSep.setSelectionForeground(selectionFGC);
		txtMessageSep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getPlaintextEncodedList(), txtMessageSep, getCurrentInstance());
			}
		});
		
		Label lblMessageBase = new Label(compEncSteps, SWT.NONE);
		lblMessageBase.setText(MessageFormat.format(strPlainInBase16, guiHandler.getSeparator()));
		txtMessageBase = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtMessageBaseData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageBase.setLayoutData(txtMessageBaseData);
		guiHandler.setSizeControl(txtMessageBase, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageBase.setSelectionBackground(selectionBGC);
		txtMessageBase.setSelectionForeground(selectionFGC);
		txtMessageBase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentPlaintextList(), txtMessageBase, getCurrentInstance());
				
			}
		});
		
	
		Label lblCipher = new Label(compEncSteps, SWT.NONE);
		lblCipher.setText(Messages.RabinSecondTabComposite_lblCipher);
		txtCipher = new StyledText(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtCipherData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipher.setLayoutData(txtCipherData);
		guiHandler.setSizeControl(txtCipher, SWT.DEFAULT, SWT.DEFAULT);
		txtCipher.setSelectionBackground(selectionBGC);
		txtCipher.setSelectionForeground(selectionFGC);
		
		txtCipher.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {			
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCipher, getCurrentInstance());
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
		
		// label as separator for infotext
		Label lblSepInfoEnc = new Label(grpPlaintext, SWT.SEPARATOR | SWT.VERTICAL);
		GridData lblSepInfoEncData = new GridData(SWT.FILL, SWT.FILL, false, true);
		lblSepInfoEnc.setLayoutData(lblSepInfoEncData);
		
		txtInfoEnc = new Text(grpPlaintext, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		GridData txtInfoEncData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoEnc.setLayoutData(txtInfoEncData);
		guiHandler.setSizeControl(txtInfoEnc, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoEnc.setText(Messages.RabinSecondTabComposite_txtInfoEnc);
		txtInfoEnc.setBackground(ColorService.LIGHTGRAY);

	}
	
	
	
	
	
	
	
	/**
	 * create group "Decryption" and its content
	 * @param parent
	 */
	private void createDecContent(Composite parent) {
		guiHandler.initStates();
		
		
		grpDec = new Group(parent, SWT.NONE);
		//grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 50));
		grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpDec.setLayout(new GridLayout(2, false));
		grpDec.setText(Messages.RabinSecondTabComposite_grpDec);
		
		compHoldAllSteps = new Composite(grpDec, SWT.NONE);
		compHoldAllSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldAllSteps.setLayout(new GridLayout(1, false));
		
		
		compEnterCiphertext = new Composite(compHoldAllSteps, SWT.NONE);
		compEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, false);
		compEnterCiphertext.setLayoutData(compEnterCiphertextData);
		compEnterCiphertext.setLayout(new GridLayout(2, false));
		
		compEnterCiphertextData.exclude = true;
		compEnterCiphertext.setVisible(false);
		compHoldAllSteps.requestLayout();
		
		Composite compHoldRadHexDec = new Composite(compEnterCiphertext, SWT.NONE);
		compHoldRadHexDec.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		compHoldRadHexDec.setLayout(new GridLayout(2, false));
		
		btnRadHex = new Button(compHoldRadHexDec, SWT.RADIO);
		btnRadHex.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnRadHex.setText(Messages.RabinSecondTabComposite_btnRadHex);
		btnRadHex.setSelection(true);
		btnRadHex.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				guiHandler.btnRadHexAction(getCurrentInstance(), e);
			}
			
		});
		
		
		btnRadDecimal = new Button(compHoldRadHexDec, SWT.RADIO);
		btnRadDecimal.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btnRadDecimal.setText(Messages.RabinSecondTabComposite_btnRadDecimal);
		btnRadDecimal.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnRadDecimalAction(getCurrentInstance(), e);
				
			
			}
			
		});
		
		compEnterCiphertextSteps = new Composite(compEnterCiphertext, SWT.NONE);
		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));
		
		
		compEnterCiphertextPart1 = new Composite(compEnterCiphertextSteps, SWT.NONE);
		compEnterCiphertextPart1Data = new GridData(SWT.FILL, SWT.FILL, true, false);
		compEnterCiphertextPart1.setLayoutData(compEnterCiphertextPart1Data);
		compEnterCiphertextPart1.setLayout(new GridLayout(1, false));
		
		btnApplyCiphertext = new Button(compEnterCiphertext, SWT.PUSH);
		btnApplyCiphertext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		btnApplyCiphertext.setText(Messages.RabinSecondTabComposite_btnApplyCiphertext);
		btnApplyCiphertext.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnApplyCiphertextAction(getCurrentInstance());
				
			}
			
		});
		
		
		compHoldDecimal = new Composite(compEnterCiphertextSteps, SWT.NONE);
		compHoldDecimalData = new GridData(SWT.FILL, SWT.FILL, true, false);
		compHoldDecimal.setLayoutData(compHoldDecimalData);
		compHoldDecimal.setLayout(new GridLayout(1, false));
		
		compHoldDecimalData.exclude = true;
		compHoldDecimal.setVisible(false);
		compEnterCiphertextSteps.requestLayout();
		
		
		
		Label lblEnterCiphertextDecimal = new Label(compHoldDecimal, SWT.NONE);
		String strCipherInBase10 = Messages.RabinSecondTabComposite_strCipherInBase10;
		lblEnterCiphertextDecimal.setText(MessageFormat.format(strCipherInBase10, guiHandler.getSeparator()));
		
		txtEnterCiphertextDecimal = new Text(compHoldDecimal, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData txtEnterCiphertextDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextDecimal.setLayoutData(txtEnterCiphertextDecimalData);
		guiHandler.setSizeControl(txtEnterCiphertextDecimal, SWT.DEFAULT, SWT.DEFAULT);
		
		txtEnterCiphertextDecimal.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.handleDecimalNumbersDecMode(getCurrentInstance());
			}
		});
		
		txtEnterCiphertextDecimalWarning = new Text(compHoldDecimal, SWT.MULTI | SWT.WRAP);
		txtEnterCiphertextDecimalWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextDecimalWarning.setLayoutData(txtEnterCiphertextDecimalWarningData);
		guiHandler.setSizeControlWarning(txtEnterCiphertextDecimalWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtEnterCiphertextDecimalWarning.setBackground(ColorService.LIGHTGRAY);
		txtEnterCiphertextDecimalWarning.setForeground(ColorService.RED);
		guiHandler.hideControl(txtEnterCiphertextDecimalWarning);
		
		
		Label lblCiphertextSegmentsDecimal = new Label(compHoldDecimal, SWT.NONE);
		String strCipherInBase16 = Messages.RabinSecondTabComposite_strCipherInBase16;
		lblCiphertextSegmentsDecimal.setText(MessageFormat.format(strCipherInBase16, guiHandler.getSeparator()));
		
		txtCiphertextSegmentsDecimal = new StyledText(compHoldDecimal, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegmentsDecimal.setLayoutData(txtCiphertextSegmentsDecimalData);
		txtCiphertextSegmentsDecimal.setSelectionBackground(selectionBGC);
		txtCiphertextSegmentsDecimal.setSelectionForeground(selectionFGC);
		guiHandler.setSizeControl(txtCiphertextSegmentsDecimal, SWT.DEFAULT, SWT.DEFAULT);
		
		txtCiphertextSegmentsDecimal.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCiphertextSegmentsDecimal, getCurrentInstance());
				
			}
			
		});
		
		
		
		
		Label lblEnterCiphertext = new Label(compEnterCiphertextPart1, SWT.NONE);
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
		});
		
		txtEnterCiphertextWarning = new Text(compEnterCiphertextPart1, SWT.MULTI | SWT.WRAP);
		txtEnterCiphertextWarningData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtEnterCiphertextWarning.setLayoutData(txtEnterCiphertextWarningData);
		txtEnterCiphertextWarning.setForeground(ColorService.RED);
		txtEnterCiphertextWarning.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControlWarning(txtEnterCiphertextWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEnterCiphertextWarning);
		
		
		Label lblCiphertextSegments = new Label(compEnterCiphertextPart1, SWT.NONE);
		String strCipherIntoSegments = Messages.RabinSecondTabComposite_strCipherIntoSegments;
		lblCiphertextSegments.setText(MessageFormat.format(strCipherIntoSegments, guiHandler.getSeparator()));
		
		txtCiphertextSegments = new StyledText(compEnterCiphertextPart1, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		txtCiphertextSegments.setLayoutData(txtCiphertextSegmentsData);
		txtCiphertextSegments.setSelectionBackground(selectionBGC);
		txtCiphertextSegments.setSelectionForeground(selectionFGC);
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
		
		
		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.fixSelection(guiHandler.getCurrentCiphertextList(), txtCiphertextSegments, getCurrentInstance());
			}
			
		});
		
		
		Composite compInfoDec = new Composite(grpDec, SWT.NONE);
		GridData compInfoDecData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compInfoDec.setLayoutData(compInfoDecData);
		compInfoDec.setLayout(new GridLayout(2, false));
		
		
		
		Composite compTest = new Composite(compHoldAllSteps, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compTest.setLayout(new GridLayout(4, false));
		
		
		
		
		// create combobox for selecting ciphertext c[i]
		Composite compChooseCipher = new Composite(compTest, SWT.NONE);
		compChooseCipher.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		compChooseCipher.setLayout(new GridLayout(1, false));
		
		// place combo and label inside compChooseCipher
		Label lblChoosCipher = new Label(compChooseCipher, SWT.NONE);
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
		
	
	
		//create label for first block of ciphertext
		Label lblCipherFirst = new Label(compTest, SWT.NONE);
		lblCipherFirst.setText("c[i] = "); //$NON-NLS-1$
				
		
		// create textbox for first block of the ciphertext
		txtCipherFirst = new Text(compTest, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtCipherFirstData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCipherFirst.setLayoutData(txtCipherFirstData);
		guiHandler.setSizeControl(txtCipherFirst, SWT.DEFAULT, SWT.DEFAULT);
		
		txtCipherFirst.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtCipherFirstModifyAction(getCurrentInstance());
				
			}
		});
		
		Composite compNavElem = new Composite(compTest, SWT.NONE);
		compNavElem.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		compNavElem.setLayout(new GridLayout(2, false));
		
		btnPrevElem = new Button(compNavElem, SWT.PUSH);
		btnPrevElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		btnPrevElem.setText("c[i-1]"); //$NON-NLS-1$
		btnPrevElem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnPrevElemAction(getCurrentInstance());
				
			}
		});
		
	
		
		btnNextElem = new Button(compNavElem, SWT.PUSH);
		btnNextElem.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		btnNextElem.setText("c[i+1]"); //$NON-NLS-1$
		btnNextElem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnNextElemAction(getCurrentInstance());
		
			}
		});
		
		Label lblSepNavElem = new Label(compNavElem, SWT.SEPARATOR | SWT.HORIZONTAL);
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
		Label lblSepDecInfo = new Label(compInfoDec, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepDecInfo.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		
		txtInfoDec = new Text(compInfoDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoDecData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		txtInfoDec.setLayoutData(txtInfoDecData);
		guiHandler.setSizeControl(txtInfoDec, SWT.DEFAULT, 250);
		txtInfoDec.setText(Messages.RabinSecondTabComposite_txtInfoDec);
		txtInfoDec.setBackground(ColorService.LIGHTGRAY);
		
		// create group for computing square roots
		grpSqrRoot = new Group(compHoldAllSteps, SWT.NONE);
		grpSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSqrRoot.setLayout(new GridLayout(2, false));
		grpSqrRoot.setText(Messages.RabinSecondTabComposite_grpSqrRoot);
		

		// create group for m_p
		compMp = new Composite(grpSqrRoot, SWT.NONE);
		compMp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMp.setLayout(new GridLayout(1, false));
		
		//create label for m_p
		Label lblMp = new Label(compMp, SWT.NONE);
		lblMp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblMp.setText(Messages.RabinSecondTabComposite_lblMp);
		
		// create txtbox for m_p
		txtmp = new Text(compMp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtmpData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtmp.setLayoutData(txtmpData);
		guiHandler.setSizeControl(txtmp, SWT.DEFAULT, SWT.DEFAULT);
		
		txtmp.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtMpAction(getCurrentInstance());
				
			}
		});
		
		// create group for m_q
		compMq = new Composite(grpSqrRoot, SWT.NONE);
		compMq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMq.setLayout(new GridLayout(1, false));
		
		//create label for m_q
		Label lblMq = new Label(compMq, SWT.NONE);
		lblMq.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblMq.setText(Messages.RabinSecondTabComposite_lblMq);
		
		// create txtbox for m_p
		txtmq = new Text(compMq, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtmqData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtmq.setLayoutData(txtmqData);
		guiHandler.setSizeControl(txtmq, SWT.DEFAULT, SWT.DEFAULT);
		
		txtmq.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtMqAction(getCurrentInstance());
				
			}
		});
		
		// create group for linear congruences
		grpLinCon = new Group(compHoldAllSteps, SWT.NONE);
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
		
		
		// label yp
		Label lblyp = new Label(compMerge, SWT.NONE);
		lblyp.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblyp.setText("y_p = "); //$NON-NLS-1$
		
		// create y_p txtbox
		txtyp = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtypData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtyp.setLayoutData(txtypData);
		guiHandler.setSizeControl(txtyp, SWT.DEFAULT, SWT.DEFAULT);
		
		txtyp.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtYpAction(getCurrentInstance());
			}
		});
		
		//create label y_q
		Label lblyq = new Label(compMerge, SWT.NONE);
		lblyq.setText("y_q = "); //$NON-NLS-1$
		
		// create txtbox for y_q
		txtyq = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtyqData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtyq.setLayoutData(txtyqData);
		guiHandler.setSizeControl(txtyq, SWT.DEFAULT, SWT.DEFAULT);
		
		txtyq.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtYqAction(getCurrentInstance());
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
		
		// create group for computing v = y_p * p * m_q
		// and w = y_q * q * m_p
		Group grpComputeIs = new Group(compHoldAllSteps, SWT.NONE);
		grpComputeIs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpComputeIs.setLayout(new GridLayout(2, false));
		grpComputeIs.setText(Messages.RabinSecondTabComposite_grpComputeIs);
		
		Composite compHoldvw = new Composite(grpComputeIs, SWT.NONE);
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
		
		Label lblV = new Label(compHoldvw, SWT.NONE);
		lblV.setText("v = "); //$NON-NLS-1$
		txtV = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtVData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtV.setLayoutData(txtVData);
		guiHandler.setSizeControl(txtV, SWT.DEFAULT, SWT.DEFAULT);
		
		txtV.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtVAction(getCurrentInstance());
				
			}
		});	
		
		Label lblW = new Label(compHoldvw, SWT.NONE);
		lblW.setText("w = "); //$NON-NLS-1$
		txtW = new Text(compHoldvw, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtWData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtW.setLayoutData(txtWData);
		guiHandler.setSizeControl(txtW, SWT.DEFAULT, SWT.DEFAULT);
		
		txtW.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.txtWAction(getCurrentInstance());
				
			}
		});	
		
		// create group for computing plaintexts
		grpPosPlain = new Group(compHoldAllSteps, SWT.NONE);
		grpPosPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpPosPlain.setLayout(new GridLayout(2, false));
		grpPosPlain.setText(Messages.RabinSecondTabComposite_grpPosPlain);
		
		// composite for packing all plaintexts for better structure
		compAllPt = new Composite(grpPosPlain, SWT.NONE);
		compAllPt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compAllPt.setLayout(new GridLayout(2, false));
		
		// label for m1
		Label lblm1 = new Label(compAllPt, SWT.NONE);
		lblm1.setText(Messages.RabinSecondTabComposite_lblm1);
		
		// txt for m1
		txtm1 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm1Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm1.setLayoutData(txtm1Data);
		guiHandler.setSizeControl(txtm1, SWT.DEFAULT, SWT.DEFAULT);
		
		// label for m2
		Label lblm2 = new Label(compAllPt, SWT.NONE);
		lblm2.setText(Messages.RabinSecondTabComposite_lblm2);
		
		// txt for m2
		txtm2 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm2Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm2.setLayoutData(txtm2Data);
		guiHandler.setSizeControl(txtm2, SWT.DEFAULT, SWT.DEFAULT);
		
		// label for m3
		Label lblm3 = new Label(compAllPt, SWT.NONE);
		lblm3.setText(Messages.RabinSecondTabComposite_lblm3);
		
		// txt for m3
		txtm3 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm3Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm3.setLayoutData(txtm3Data);
		guiHandler.setSizeControl(txtm3, SWT.DEFAULT, SWT.DEFAULT);
		
		// label for m4
		Label lblm4 = new Label(compAllPt, SWT.NONE);
		lblm4.setText(Messages.RabinSecondTabComposite_lblm4);
		
		// txt for m4
		txtm4 = new Text(compAllPt, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtm4Data = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtm4.setLayoutData(txtm4Data);
		guiHandler.setSizeControl(txtm4, SWT.DEFAULT, SWT.DEFAULT);
		
		// create Composite holding "compute all plaintexts" and "continue with next block c[i]"
		Composite compHoldNextC = new Composite(grpPosPlain, SWT.NONE);
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
			
	}
	
	
	
	
	
	/**
	 * create composite containing the encryption and decryption
	 * radio buttons for selecting one of them 
	 * @param parent
	 */
	private void createSelectEncDecContent(Composite parent) {
		// create radio buttons for selecting either encryption or decryption
		Composite compSelectEncDec = new Composite(parent, SWT.NONE);
		compSelectEncDec.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compSelectEncDec.setLayout(new GridLayout(4, false));
		
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
		createSetParamContent(this);
		
		guiHandler.initializePrimes(100, getCurrentInstance());
		
		/*createSelectEncDecContent(rootComposite);
		
		createEncContent(rootComposite);
		createDecContent(rootComposite);*/
		
		createSelectEncDecContent(this);
		
		createEncContent(this);
		createDecContent(this);
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
