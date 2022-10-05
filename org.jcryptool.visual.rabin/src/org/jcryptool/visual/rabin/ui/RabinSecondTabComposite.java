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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
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
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.HandleSecondTab;
import org.jcryptool.visual.rabin.Messages;
import org.jcryptool.visual.rabin.Rabin;

/**
 * @author rico
 *
 */
public class RabinSecondTabComposite extends Composite {
	
	public Group grpPlaintext;
	public Group grpDec;
	public Group grpSqrRoot;
	public Group grpLinCon;
	public Text txtModN;
	public Button btnEnc;
	
	public Text txtCipherFirst;
	
	public Composite compMp;
	public Text txtmp;
	public Composite compMq;
	public Text txtmq;
	public Button btnSqrRoot;
	
	public Composite compMerge;
	public Text txtyp;
	public Text txtyq;
	public Group grpPosPlain;
	public Text txtm1;
	public Text txtm2;
	public Text txtm3;
	public Text txtm4;
	public Composite compAllPt;
	public Button btnComputePt;
	public Button btnGenKeysMan;
	public Button btnStartGenKeys;
	public Combo cmbP;
	public Combo cmbQ;
	public CCombo cmbBlockN;
	public Button btnText;
	public Button btnNum;
	public Text txtMessage;
	public StyledText txtMessageSep;
	public StyledText txtMessageBase;

	public StyledText txtCipher;
	public GridData grpPlaintextData;
	public GridData compEnterCiphertextData;
	public Composite compEnterCiphertext;
	public Composite compHoldAllSteps;
	public Label lblMessage;
	public Label lblMessageSep;

	public GridData lblMessageData;
	public GridData txtMessageData;
	public GridData txtMessageSepData;

	public Composite encPartSteps;
	public Button btnSelectionEnc;
	public Button btnSelectionDec;
	public Button btnApplyCiphertext;
	public Composite rootComposite;
	public CCombo cmbChooseCipher;

	public Button btnComputeYpYq;
	public Button btnComputevw;
	public Text txtV;
	public Text txtW;
	
	public Composite compEncStepsNum;
	public GridData compEncStepsNumData;
	public Composite compEncSteps;
	public GridData compEncStepsData;
	
	public Text txtMessageSepNum;
	public StyledText txtMessageBaseNum;
	public StyledText txtCipherNum;
	public Composite compHoldDecimal;
	public Button btnRadHex;
	public Button btnRadDecimal;
	public GridData compHoldDecimalData;
	public Composite compEnterCiphertextSteps;
	public Text txtEnterCiphertextDecimal;
	public StyledText txtCiphertextSegmentsDecimal;
	public Text txtEnterCiphertext;
	public StyledText txtCiphertextSegments;
	public Text txtWarningNpq;
	public Composite compBlockN;
	public Text txtMessageSepNumWarning;
	public GridData txtMessageSepNumWarningData;
	public Text txtEnterCiphertextWarning;
	public GridData txtEnterCiphertextWarningData;
	public Text txtEnterCiphertextDecimalWarning;
	public GridData txtEnterCiphertextDecimalWarningData;
	public Text txtMessageWarning;
	public GridData txtMessageWarningData;
	public Button btnPrevElem;
	public Button btnNextElem;
	public Text txtInfoEnc;
	public ScrolledComposite sc;
	public Button btnGenKeysAlgo;
	public GridData compBlockNData;
	
	public Text nWarning;
	
	public Text txtMessageWithPadding;
	public Label lblBlockN;
	public Label lblMessageSepNum;
	public Label lblMessageBaseNum;
	public Label lblCipherNum;
	public Label lblMessageWithPadding;
	public Label lblMessageBase;
	public Label lblCipher;
	public Label lblSepInfoEnc;
	public Label lblEnterCiphertextDecimal;
	public Label lblCiphertextSegmentsDecimal;
	public Label lblEnterCiphertext;
	public Label lblCiphertextSegments;
	public Composite compHoldTestAndSQR;
	public Composite compTest;
	public Composite compChooseCipher;
	public Label lblChoosCipher;
	public Label lblCipherFirst;
	public Composite compNavElem;
	public Label lblSepNavElem;
	public Label lblMp;
	public Label lblMq;
	public Composite compHoldLCandInverse;
	public Label lblyp;
	public Label lblyq;
	public Group grpComputeIs;
	public Composite compHoldvw;
	public Label lblV;
	public Label lblW;
	public Composite compHoldPlaintextsAndInfo;
	public Label lblm1;
	public Label lblm2;
	public Label lblm3;
	public Label lblm4;
	public Composite compSelectEncDec;
	
	public Composite compSelNumTxt;
	public Composite compHoldNextC;
	
	public Composite compHoldRadioHexAndDecAndSteps;
	public Composite compHoldRadHexDec2;
	
	
	public Label lblSepEncryptionBottom;
	public Composite compHoldSepAndInfoForEncryption;
	public Composite compHoldSepAndInfoForDecryption;
	public Label lblSepInfoForDecryption;
	public Text txtInfoDecryption;
	public Composite compHoldSepAndInfoEncDecSelection;
	public Label lblSepForInfoEncDecSelection;
	public Text txtInfoForEncDecSelection;
	public Label lblSepEncDecBottom;


	public Label lblChooseBlockPadding;
	public CCombo cmbChooseBlockPadding;
	
	public Group grpSelectEncDec;
	public Composite compSpaceBetweenCmbs;
	
	
	
	private String strPlaintextSeparatedIntoSegments = Messages.RabinSecondTabComposite_strPlaintextSeparatedIntoSegments;
	private String strPlainInBase16 = Messages.RabinSecondTabComposite_strPlainInBase16;
	
	
	public HandleSecondTab guiHandler;
	
	public RabinSecondTabComposite getCurrentInstance() {
		return this;
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
	
	
	
	
			
	
	
	public void setColors() {
		
		Color colorBG = GUIHandler.colorDarkModeBG;
		Color colorFG = GUIHandler.colorDarkModeFG;
		Color colorTxtWarningFG = GUIHandler.colorDarkModeWarningFG;
		Color colorButtonBG = GUIHandler.colorButtonsBG;
		Color colorButtonFG = GUIHandler.colorButtonsFG;
		Color colorTxtWhichYouCanEnterBG = GUIHandler.colorTxtWhichYouCanEnterBG;
		Color colorTxtWhichYouCanEnterFG = GUIHandler.colorTxtWhichYouCanEnterFG;

	
		/*lblChooseBlockPadding.setBackground(colorBG);
		lblChooseBlockPadding.setForeground(colorFG);
		cmbChooseBlockPadding.setBackground(colorButtonBG);
		cmbChooseBlockPadding.setForeground(colorButtonFG);
		grpSelectEncDec.setBackground(colorBG);
		grpSelectEncDec.setForeground(colorFG);
		compSpaceBetweenCmbs.setBackground(colorBG);
		compSpaceBetweenCmbs.setForeground(colorFG);*/
		
		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
		
		lblChooseBlockPadding.setBackground(colorBG);
		lblChooseBlockPadding.setForeground(colorFG);
		cmbChooseBlockPadding.setBackground(colorButtonBG);
		cmbChooseBlockPadding.setForeground(colorButtonFG);
		grpSelectEncDec.setBackground(colorBG);
		grpSelectEncDec.setForeground(colorFG);
		compSpaceBetweenCmbs.setBackground(colorBG);
		compSpaceBetweenCmbs.setForeground(colorFG);
		
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
		lblEnterCiphertextDecimal.setBackground(colorBG);
		lblEnterCiphertextDecimal.setForeground(colorFG);
		lblCiphertextSegmentsDecimal.setBackground(colorBG);
		lblCiphertextSegmentsDecimal.setForeground(colorFG);
		lblEnterCiphertext.setBackground(colorBG);
		lblEnterCiphertext.setForeground(colorFG);
		lblCiphertextSegments.setBackground(colorBG);
		lblCiphertextSegments.setForeground(colorFG);
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
		compSelectEncDec.setBackground(colorBG);
		compSelectEncDec.setForeground(colorFG);

		compHoldRadioHexAndDecAndSteps.setBackground(colorBG);
		compHoldRadioHexAndDecAndSteps.setForeground(colorFG);
		compHoldRadHexDec2.setBackground(colorBG);
		compHoldRadHexDec2.setForeground(colorFG);
		
		//lblSepEncryptionBottom.setBackground(colorBG);
		//lblSepEncryptionBottom.setForeground(colorFG);
		compHoldSepAndInfoForEncryption.setBackground(colorBG);
		compHoldSepAndInfoForEncryption.setForeground(colorFG);
		compHoldSepAndInfoForDecryption.setBackground(colorBG);
		compHoldSepAndInfoForDecryption.setForeground(colorFG);
		lblSepInfoForDecryption.setBackground(colorBG);
		lblSepInfoForDecryption.setForeground(colorFG);
		txtInfoDecryption.setBackground(colorBG);
		txtInfoDecryption.setForeground(colorFG);
		compHoldSepAndInfoEncDecSelection.setBackground(colorBG);
		compHoldSepAndInfoEncDecSelection.setForeground(colorFG);
		lblSepForInfoEncDecSelection.setBackground(colorBG);
		lblSepForInfoEncDecSelection.setForeground(colorFG);
		txtInfoForEncDecSelection.setBackground(colorBG);
		txtInfoForEncDecSelection.setForeground(colorFG);
		//lblSepEncDecBottom.setBackground(colorBG);
		//lblSepEncDecBottom.setForeground(colorFG);
		
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
		//grpPlaintext.setLayout(new GridLayout(5, false));
		grpPlaintext.setLayout(new GridLayout(3, false));
		//grpPlaintext.setText(Messages.RabinSecondTabComposite_grpPlaintext);
		grpPlaintext.setText("4 \u2212 Encryption");
		
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
		
		
		compSpaceBetweenCmbs = new Composite(compBlockN, SWT.NONE);
		compSpaceBetweenCmbs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		compSpaceBetweenCmbs.setLayout(new GridLayout(1, false));
		guiHandler.setSizeControl(compSpaceBetweenCmbs, SWT.DEFAULT, 20);
		
		
		lblChooseBlockPadding = new Label(compBlockN, SWT.NONE);
		lblChooseBlockPadding.setText("Block-Padding");
		cmbChooseBlockPadding = new CCombo(compBlockN, SWT.READ_ONLY);
		cmbChooseBlockPadding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//cmbChooseBlockPadding.setVisibleItemCount(10);
		cmbChooseBlockPadding.setItems(new String[]{"ANSI X9.23", "PKCS#7"});
		cmbChooseBlockPadding.select(0);
		cmbChooseBlockPadding.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.cmbChooseBlockPaddingAction(getCurrentInstance());
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
		txtMessageSepNumWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtMessageSepNumWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtMessageSepNumWarning);
	
		
		lblMessageBaseNum = new Label(compEncStepsNum, SWT.NONE);
		lblMessageBaseNum.setText(MessageFormat.format(strPlainInBase16, guiHandler.getSeparator()));
		txtMessageBaseNum = new StyledText(compEncStepsNum, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtMessageBaseNumData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageBaseNum.setLayoutData(txtMessageBaseNumData);
		txtMessageBaseNum.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtMessageBaseNum.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		guiHandler.setSizeControl(txtMessageBaseNum, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageBaseNum.setBackground(GUIHandler.colorBGinfo);
		
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
		txtCipherNum.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtCipherNum.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		guiHandler.setSizeControl(txtCipherNum, SWT.DEFAULT, SWT.DEFAULT);
		txtCipherNum.setBackground(GUIHandler.colorBGinfo);
		
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
		txtMessageWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtMessageWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtMessageWarning);
		
		
		
		lblMessageWithPadding = new Label(compEncSteps, SWT.NONE);
		lblMessageWithPadding.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		//lblMessageWithPadding.setText("Plaintext with padding in hex format (\"20\" as padding)");
		String paddingScheme = this.cmbChooseBlockPadding.getItem(this.cmbChooseBlockPadding.getSelectionIndex());
		lblMessageWithPadding.setText("Plaintext with possible padding (\""
				+ paddingScheme + "\" as paddig scheme, hex format)");
		txtMessageWithPadding = new Text(compEncSteps, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtMessageWithPaddingData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtMessageWithPadding.setLayoutData(txtMessageWithPaddingData);
		guiHandler.setSizeControl(txtMessageWithPadding, SWT.DEFAULT, SWT.DEFAULT);
		txtMessageWithPadding.setBackground(GUIHandler.colorBGinfo);
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
		txtMessageSep.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtMessageSep.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		txtMessageSep.setBackground(GUIHandler.colorBGinfo);
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
		txtMessageBase.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtMessageBase.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		txtMessageBase.setBackground(GUIHandler.colorBGinfo);
		
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
		txtCipher.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtCipher.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		txtCipher.setBackground(GUIHandler.colorBGinfo);
		
		
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
		
		
		compHoldSepAndInfoForEncryption = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoForEncryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoForEncryption.setLayout(new GridLayout(2, false));
		
		
		
		lblSepInfoEnc = new Label(compHoldSepAndInfoForEncryption, SWT.SEPARATOR | SWT.VERTICAL);
		GridData lblSepInfoEncData = new GridData(SWT.FILL, SWT.FILL, false, true);
		lblSepInfoEnc.setLayoutData(lblSepInfoEncData);
		
		txtInfoEnc = new Text(compHoldSepAndInfoForEncryption, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoEncData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoEnc.setLayoutData(txtInfoEncData);
		guiHandler.setSizeControl(txtInfoEnc, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoEnc.setText(guiHandler.getMessageByControl("txtInfoEnc_Text"));
		txtInfoEnc.setBackground(ColorService.LIGHTGRAY);
		txtInfoEnc.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoEnc, SWT.CURSOR_ARROW);
			}
		});
		
		
		//lblSepEncryptionBottom = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSepEncryptionBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
	}
	
	
	
	
	
	
	
	/**
	 * create group "Decryption" and its content
	 * @param parent
	 */
	private void createDecContent(Composite parent) {
		guiHandler.initStates();	
		
		grpDec = new Group(parent, SWT.NONE);
		grpDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpDec.setLayout(new GridLayout(1, false));
		//grpDec.setText(Messages.RabinSecondTabComposite_grpDec);
		grpDec.setText("5 \u2212 Decryption");	
		
		compHoldAllSteps = new Composite(grpDec, SWT.NONE);
		compHoldAllSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldAllSteps.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldAllSteps, 5, 0);
		
			
		compEnterCiphertext = new Composite(compHoldAllSteps, SWT.NONE);
		compEnterCiphertextData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compEnterCiphertext.setLayoutData(compEnterCiphertextData);
		compEnterCiphertext.setLayout(new GridLayout(2, false));
		
		guiHandler.hideControl(compEnterCiphertext);
			
		compHoldRadioHexAndDecAndSteps = new Composite(compEnterCiphertext, SWT.NONE);
		compHoldRadioHexAndDecAndSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldRadioHexAndDecAndSteps.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldRadioHexAndDecAndSteps, SWT.DEFAULT, 0);
				
		compHoldRadHexDec2 = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compHoldRadHexDec2.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 1));
		compHoldRadHexDec2.setLayout(new GridLayout(2, false));
			
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
				
		
		btnApplyCiphertext = new Button(compEnterCiphertext, SWT.PUSH);
		btnApplyCiphertext.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		btnApplyCiphertext.setText(Messages.RabinSecondTabComposite_btnApplyCiphertext);
		btnApplyCiphertext.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnApplyCiphertextAction(getCurrentInstance());
				
			}
			
		});
		
				
		compEnterCiphertextSteps = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compEnterCiphertextSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compEnterCiphertextSteps.setLayout(new GridLayout(1, false));
		
		
		
		compHoldDecimal = new Composite(compHoldRadioHexAndDecAndSteps, SWT.NONE);
		compHoldDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compHoldDecimal.setLayoutData(compHoldDecimalData);
		compHoldDecimal.setLayout(new GridLayout(1, false));
		
		
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
		txtEnterCiphertextDecimalWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtEnterCiphertextDecimalWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtEnterCiphertextDecimalWarning);
		
		
		lblCiphertextSegmentsDecimal = new Label(compHoldDecimal, SWT.NONE);
		String strCipherInBase16 = Messages.RabinSecondTabComposite_strCipherInBase16;
		lblCiphertextSegmentsDecimal.setText(MessageFormat.format(strCipherInBase16, guiHandler.getSeparator()));
		
		txtCiphertextSegmentsDecimal = new StyledText(compHoldDecimal, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsDecimalData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegmentsDecimal.setLayoutData(txtCiphertextSegmentsDecimalData);
		txtCiphertextSegmentsDecimal.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtCiphertextSegmentsDecimal.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		txtCiphertextSegmentsDecimal.setBackground(GUIHandler.colorBGinfo);
		
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
		txtEnterCiphertextWarning.setForeground(guiHandler.colorForegroundWarning);
		txtEnterCiphertextWarning.setBackground(guiHandler.colorBackgroundWarning);
		guiHandler.setSizeControlWarning(txtEnterCiphertextWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEnterCiphertextWarning);
		
		
		lblCiphertextSegments = new Label(compEnterCiphertextSteps, SWT.NONE);
		String strCipherIntoSegments = Messages.RabinSecondTabComposite_strCipherIntoSegments;
		lblCiphertextSegments.setText(MessageFormat.format(strCipherIntoSegments, guiHandler.getSeparator()));
		
		txtCiphertextSegments = new StyledText(compEnterCiphertextSteps, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		GridData txtCiphertextSegmentsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtCiphertextSegments.setLayoutData(txtCiphertextSegmentsData);
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
		txtCiphertextSegments.setSelectionBackground(guiHandler.colorSelectionStyledTextBG);
		txtCiphertextSegments.setSelectionForeground(guiHandler.colorSelectionStyledTextFG);
		txtCiphertextSegments.setBackground(GUIHandler.colorBGinfo);
		
		
		
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
		
		
		compHoldTestAndSQR = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldTestAndSQR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldTestAndSQR.setLayout(new GridLayout(1, false));
			
		
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
		txtCipherFirst.setBackground(GUIHandler.colorBGinfo);
		
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
		
			
		grpSqrRoot = new Group(compHoldTestAndSQR, SWT.NONE);
		grpSqrRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpSqrRoot.setLayout(new GridLayout(2, false));
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
		txtmp.setBackground(GUIHandler.colorBGinfo);
		
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
		txtmq.setBackground(GUIHandler.colorBGinfo);
		
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
	
			
		compHoldLCandInverse = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldLCandInverse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldLCandInverse.setLayout(new GridLayout(1, false));	
		
					
		grpLinCon = new Group(compHoldLCandInverse, SWT.NONE);
		grpLinCon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpLinCon.setLayout(new GridLayout(2, false));
		grpLinCon.setText(Messages.RabinSecondTabComposite_grpLinCon);
		
		
		// create composite for first 2 congruences 
		compMerge = new Composite(grpLinCon, SWT.NONE);
		compMerge.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compMerge.setLayout(new GridLayout(2, false));
				
		
		lblyp = new Label(compMerge, SWT.NONE);
		lblyp.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblyp.setText("y_p = "); //$NON-NLS-1$
		
		// create y_p txtbox
		txtyp = new Text(compMerge, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
		GridData txtypData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtyp.setLayoutData(txtypData);
		txtyp.setBackground(GUIHandler.colorBGinfo);
		
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
		txtyq.setBackground(GUIHandler.colorBGinfo);
		
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
		txtV.setBackground(GUIHandler.colorBGinfo);
		
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
		txtW.setBackground(GUIHandler.colorBGinfo);
		
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
	
			
		compHoldPlaintextsAndInfo = new Composite(compHoldAllSteps, SWT.NONE);
		compHoldPlaintextsAndInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldPlaintextsAndInfo.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldPlaintextsAndInfo, 5, 0);
		
			
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
		txtm1.setBackground(GUIHandler.colorBGinfo);
		
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
		txtm2.setBackground(GUIHandler.colorBGinfo);
		
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
		txtm3.setBackground(GUIHandler.colorBGinfo);
		
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
		txtm4.setBackground(GUIHandler.colorBGinfo);
		
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
			
		
		compHoldSepAndInfoForDecryption = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoForDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoForDecryption.setLayout(new GridLayout(2, false));
		
		lblSepInfoForDecryption = new Label(compHoldSepAndInfoForDecryption, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoForDecryption.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		
		txtInfoDecryption = new Text(compHoldSepAndInfoForDecryption, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoDecryption.setBackground(GUIHandler.colorBGinfo);
		guiHandler.setSizeControl(txtInfoDecryption, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoDecryption.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoDecryption, SWT.CURSOR_ARROW);
			}
		});
		
	
		
	}
	
	
	
	
	
	/**
	 * create composite containing the encryption and decryption
	 * radio buttons for selecting one of them 
	 * @param parent
	 */
	private void createSelectEncDecContent(Composite parent) {
		
		grpSelectEncDec = new Group(parent, SWT.NONE);
		grpSelectEncDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpSelectEncDec.setLayout(new GridLayout(1, false));
		grpSelectEncDec.setText("3 \u2212 Encrypt/Decrypt or only decrypt");
		
		
		//compSelectEncDec = new Composite(parent, SWT.NONE);
		compSelectEncDec = new Composite(grpSelectEncDec, SWT.NONE);
		compSelectEncDec.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compSelectEncDec.setLayout(new GridLayout(2, false));
		
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
		
		
		compHoldSepAndInfoEncDecSelection = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoEncDecSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoEncDecSelection.setLayout(new GridLayout(2, false));
		
		lblSepForInfoEncDecSelection = new Label(compHoldSepAndInfoEncDecSelection, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepForInfoEncDecSelection.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		txtInfoForEncDecSelection = new Text(compHoldSepAndInfoEncDecSelection, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoForEncDecSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoForEncDecSelection.setBackground(GUIHandler.colorBGinfo);
		guiHandler.setSizeControl(txtInfoForEncDecSelection, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoForEncDecSelection.setText(guiHandler.getMessageByControl("txtInfoForEncDecSelection"));
		txtInfoForEncDecSelection.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoForEncDecSelection, SWT.CURSOR_ARROW);
			}
		});
		
		
		//lblSepEncDecBottom = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSepEncDecBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		
	}
	
	
	
	
	
	
	/**
	 * create the content of the Algorithm tab
	 */
	private void createContent(Composite parent) {
		this.setLayout(new GridLayout(1, false));
		
		createSelectEncDecContent(parent);
		createEncContent(parent);
		createDecContent(parent);
		
		
		if(GUIHandler.isDarkmode)
			setColors();
		
		guiHandler.initializeComponents(getCurrentInstance());		
		
	}

	
	
	
	/**
	 * @param parent
	 * @param style
	 */
	public RabinSecondTabComposite(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new GridLayout(1, false));
		createContent(parent);
	}
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabin
	 * @param rabinFromFirstTab
	 * @param rootScrolledComposite
	 * @param rootComposite
	 */
//	public RabinSecondTabComposite(Composite parent, int style, Rabin rabin, Rabin rabinFromFirstTab, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
//		super(parent, style);	
//		this.guiHandler = new HandleSecondTab(rootScrolledComposite, rootComposite, rabin, rabinFromFirstTab);
//		createContent(parent);
//	}
	
	public RabinSecondTabComposite(Composite parent, int style, Rabin rabin, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
		super(parent, style);	
		this.guiHandler = new HandleSecondTab(rootScrolledComposite, rootComposite, rabin);
		createContent(parent);
	}
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 */
//	public RabinSecondTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond) {
//		super(parent, style);	
//		this.guiHandler = new HandleSecondTab(sc, rootComposite, rabinFirst, rabinSecond);
//		createContent(parent);
//	}

}
