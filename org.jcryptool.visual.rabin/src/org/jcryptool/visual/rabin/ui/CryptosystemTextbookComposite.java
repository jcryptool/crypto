package org.jcryptool.visual.rabin.ui;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.HandleCryptosystemTextbook;
import org.jcryptool.visual.rabin.HandleFirstTab;
import org.jcryptool.visual.rabin.Messages;

public class CryptosystemTextbookComposite extends Composite {

	public GUIHandler guiHandler;
	public HandleCryptosystemTextbook hcstb;
	
	public Group grpEncryptDecrypt;
	public Composite compHoldEncryptDecryptContent;
	public Button btnRadioEncrypt;
	public Button btnRadioDecrypt;
	public Composite compHoldEncryptionProcess;
	public Composite compHoldDecryptionProcess;
	public Composite compHoldCiphertext;
	public Label lblCiphertextDescryption;
	public Text txtCiphertext;
	public Composite compHoldBtnsForFeatures;
	public Button btnEncrypt;
	public Button btnDecryptInEncryptionMode;
	public Button btnWriteToJCTeditor;
	public Composite compHoldPlaintext;
	public Composite compCiphertextSegmentsComponents;
	public Composite compChooseBlockComponents;
	public Combo cmbChooseBlock;
	public StyledText txtCiphertextSegments;
	public Composite compPrevNextComponents;
	public Button btnPrevBlock;
	public Button btnNextBlock;
	public Composite compPlaintexts;
	public Composite compPreviewChosenPlaintexts;
	public Text txtChosenPlaintexts;
	public Composite compHoldBtnsForFeaturesDecryptionMode;
	public Button btnDecryption;
	public Button btnWriteToJCTeditorDecryptMode;
	public Text txtInfoEncryptionDecryption;
	public TextLoadController textSelector;
	public Text txtEncryptionWarning;
	public Text txtDecryptWarning;
	
	public Text txtFirstPlaintext;
	public Text txtSecondPlaintext;
	public Text txtThirdPlaintext;
	public Text txtFourthPlaintext;
	
	public int possiblePlaintextsSize = 4;
	public Button btnResetChosenPlaintexts;
	public Group grpLoadText;
	public Label lblInfoSeperator;
	public Text txtInfoSelector;
	public Composite compHoldEncryptDecryptRadio;
	public Label lblChooseBlockDesc;
	public Composite compHoldCiphertextSegments;
	public Label lblCiphertextSegmentsDescription;
	public Composite compFirstPlaintext;
	public Label lblFirstPlaintextDesc;
	public Composite compSecondPlaintext;
	public Label lblSecondPlaintextDesc;
	public Composite compThirdPlaintext;
	public Label lblThirdPlaintextDesc;
	public Composite compFourthPlaintext;
	public Label lblFourthPlaintextDesc;
	public Label lblPreviewChosenPlaintextDesc;
	public Label lblSeparatePreviewDesc;
	public Label lblSeperateDescription;
	public Composite compHoldSepAndInfoForSelector;
	public Label lblSeparateEncDecWithLoadTextTop;
	public Composite compHoldInfoForEncDec;
	public Label lblSeparateInfoForEncDec;
	public Text txtInfoForEncDecRadio;
	public Composite compHoldSepAndInfoEncDec;
	public Label lblSeparateEncDecWithLoadTextBottom;
	
	
	public Label lblChooseBlockPadding;
	public CCombo cmbChooseBlockPadding;
	public Group grpHoldEncryptDecryptRadio;
	private Composite compChooseBlockPadding;
	private Composite compSpaceBetweenCmbs;
		
	
	public CryptosystemTextbookComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	
	public CryptosystemTextbookComposite(Composite parent, int style, RabinFirstTabComposite rftc) {
		super(parent, style);
		this.hcstb = new HandleCryptosystemTextbook(rftc);
		this.guiHandler = rftc.guiHandler;
		createLoadTextContent(parent);
		createEncryptionDecryptionRadioBtns(parent);
		createEncryptionDecryptionContent(parent);
	
		if(GUIHandler.isDarkmode)
			setColors();
		
		initializeContent();
	}
	
	

	public CryptosystemTextbookComposite getCurrentInstance() {
		return this;
	}
		
	
	
	public void setColors() {
		
		Color colorBG = GUIHandler.colorDarkModeBG;
		Color colorFG = GUIHandler.colorDarkModeFG;
		//Color colorFG = ColorService.YELLOW;
		Color colorTxtWarningFG = GUIHandler.colorDarkModeWarningFG;
		Color colorButtonBG = GUIHandler.colorButtonsBG;
		Color colorButtonFG = GUIHandler.colorButtonsFG;
		
		lblChooseBlockPadding.setBackground(colorBG);
		lblChooseBlockPadding.setForeground(colorFG);
		cmbChooseBlockPadding.setBackground(colorButtonBG);
		cmbChooseBlockPadding.setForeground(colorButtonFG);
		grpHoldEncryptDecryptRadio.setBackground(colorBG);
		grpHoldEncryptDecryptRadio.setForeground(colorFG);
		compChooseBlockPadding.setBackground(colorBG);
		compChooseBlockPadding.setForeground(colorFG);
		compSpaceBetweenCmbs.setBackground(colorBG);
		compSpaceBetweenCmbs.setForeground(colorFG);
		
		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
		grpEncryptDecrypt.setBackground(colorBG);
		grpEncryptDecrypt.setForeground(colorFG);
		compHoldEncryptDecryptContent.setBackground(colorBG);
		compHoldEncryptDecryptContent.setForeground(colorFG);
		btnRadioEncrypt.setBackground(colorBG);
		btnRadioEncrypt.setForeground(colorFG);
		btnRadioDecrypt.setBackground(colorBG);
		btnRadioDecrypt.setForeground(colorFG);
		compHoldEncryptionProcess.setBackground(colorBG);
		compHoldEncryptionProcess.setForeground(colorFG);
		compHoldDecryptionProcess.setBackground(colorBG);
		compHoldDecryptionProcess.setForeground(colorFG);
		//textSelectorEncryption.setBackground(colorBG);
		//textSelectorEncryption.setForeground(colorFG);
		compHoldCiphertext.setBackground(colorBG);
		compHoldCiphertext.setForeground(colorFG);
		lblCiphertextDescryption.setBackground(colorBG);
		lblCiphertextDescryption.setForeground(colorFG);
		txtCiphertext.setBackground(colorBG);
		txtCiphertext.setForeground(colorFG);
		compHoldBtnsForFeatures.setBackground(colorBG);
		compHoldBtnsForFeatures.setForeground(colorFG);
		btnEncrypt.setBackground(colorButtonBG);
		btnEncrypt.setForeground(colorButtonFG);
		btnDecryptInEncryptionMode.setBackground(colorButtonBG);
		btnDecryptInEncryptionMode.setForeground(colorButtonFG);
		btnDecryptInEncryptionMode.setEnabled(false);
		btnWriteToJCTeditor.setBackground(colorButtonBG);
		btnWriteToJCTeditor.setForeground(colorButtonFG);
		//textSelectorDecryption.setBackground(colorBG);
		//textSelectorDecryption.setForeground(colorFG);
		compHoldPlaintext.setBackground(colorBG);
		compHoldPlaintext.setForeground(colorFG);
		compCiphertextSegmentsComponents.setBackground(colorBG);
		compCiphertextSegmentsComponents.setForeground(colorFG);
		compChooseBlockComponents.setBackground(colorBG);
		compChooseBlockComponents.setForeground(colorFG);
		cmbChooseBlock.setBackground(colorButtonBG);
		cmbChooseBlock.setForeground(colorButtonFG);
		txtCiphertextSegments.setBackground(colorBG);
		txtCiphertextSegments.setForeground(colorFG);
		compPrevNextComponents.setBackground(colorBG);
		compPrevNextComponents.setForeground(colorFG);
		btnPrevBlock.setBackground(colorButtonBG);
		btnPrevBlock.setForeground(colorButtonFG);
		btnNextBlock.setBackground(colorButtonBG);
		btnNextBlock.setForeground(colorButtonFG);
		compPlaintexts.setBackground(colorBG);
		compPlaintexts.setForeground(colorFG);
		compPreviewChosenPlaintexts.setBackground(colorBG);
		compPreviewChosenPlaintexts.setForeground(colorFG);
		txtChosenPlaintexts.setBackground(colorBG);
		txtChosenPlaintexts.setForeground(colorFG);
		compHoldBtnsForFeaturesDecryptionMode.setBackground(colorBG);
		compHoldBtnsForFeaturesDecryptionMode.setForeground(colorFG);
		btnDecryption.setBackground(colorButtonBG);
		btnDecryption.setForeground(colorButtonFG);
		btnWriteToJCTeditorDecryptMode.setBackground(colorButtonBG);
		btnWriteToJCTeditorDecryptMode.setForeground(colorButtonFG);
		txtInfoEncryptionDecryption.setBackground(colorBG);
		txtInfoEncryptionDecryption.setForeground(colorFG);
		//txtEncryptWarning.setBackground(colorBG);
		//txtEncryptWarning.setForeground(colorFG);
		textSelector.setBackground(colorButtonBG);
		textSelector.setForeground(colorButtonFG);
		txtEncryptionWarning.setBackground(colorBG);
		txtEncryptionWarning.setForeground(colorTxtWarningFG);
		txtDecryptWarning.setBackground(colorBG);
		txtDecryptWarning.setForeground(colorTxtWarningFG);
		txtFirstPlaintext.setBackground(colorBG);
		txtFirstPlaintext.setForeground(colorFG);
		txtSecondPlaintext.setBackground(colorBG);
		txtSecondPlaintext.setForeground(colorFG);
		txtThirdPlaintext.setBackground(colorBG);
		txtThirdPlaintext.setForeground(colorFG);
		txtFourthPlaintext.setBackground(colorBG);
		txtFourthPlaintext.setForeground(colorFG);
		btnResetChosenPlaintexts.setBackground(colorButtonBG);
		btnResetChosenPlaintexts.setForeground(colorButtonFG);
		grpLoadText.setBackground(colorBG);
		grpLoadText.setForeground(colorFG);
		lblInfoSeperator.setBackground(colorBG);
		lblInfoSeperator.setForeground(colorFG);
		txtInfoSelector.setBackground(colorBG);
		txtInfoSelector.setForeground(colorFG);
		compHoldEncryptDecryptRadio.setBackground(colorBG);
		compHoldEncryptDecryptRadio.setForeground(colorFG);
		lblChooseBlockDesc.setBackground(colorBG);
		lblChooseBlockDesc.setForeground(colorFG);
		compHoldCiphertextSegments.setBackground(colorBG);
		compHoldCiphertextSegments.setForeground(colorFG);
		lblCiphertextSegmentsDescription.setBackground(colorBG);
		lblCiphertextSegmentsDescription.setForeground(colorFG);
		compFirstPlaintext.setBackground(colorBG);
		compFirstPlaintext.setForeground(colorFG);
		lblFirstPlaintextDesc.setBackground(colorBG);
		lblFirstPlaintextDesc.setForeground(colorFG);
		compSecondPlaintext.setBackground(colorBG);
		compSecondPlaintext.setForeground(colorFG);
		lblSecondPlaintextDesc.setBackground(colorBG);
		lblSecondPlaintextDesc.setForeground(colorFG);
		compThirdPlaintext.setBackground(colorBG);
		compThirdPlaintext.setForeground(colorFG);
		lblThirdPlaintextDesc.setBackground(colorBG);
		lblThirdPlaintextDesc.setForeground(colorFG);
		compFourthPlaintext.setBackground(colorBG);
		compFourthPlaintext.setForeground(colorFG);
		lblFourthPlaintextDesc.setBackground(colorBG);
		lblFourthPlaintextDesc.setForeground(colorFG);
		lblPreviewChosenPlaintextDesc.setBackground(colorBG);
		lblPreviewChosenPlaintextDesc.setForeground(colorFG);
		lblSeparatePreviewDesc.setBackground(colorBG);
		lblSeparatePreviewDesc.setForeground(colorFG);
		lblSeperateDescription.setBackground(colorBG);
		lblSeperateDescription.setForeground(colorFG);
		
		compHoldSepAndInfoForSelector.setBackground(colorBG);
		compHoldSepAndInfoForSelector.setForeground(colorFG);
		//lblSeparateEncDecWithLoadTextTop.setBackground(colorBG);
		//lblSeparateEncDecWithLoadTextTop.setForeground(colorFG);
		compHoldInfoForEncDec.setBackground(colorBG);
		compHoldInfoForEncDec.setForeground(colorFG);
		lblSeparateInfoForEncDec.setBackground(colorBG);
		lblSeparateInfoForEncDec.setForeground(colorFG);
		txtInfoForEncDecRadio.setBackground(colorBG);
		txtInfoForEncDecRadio.setForeground(colorFG);
		compHoldSepAndInfoEncDec.setBackground(colorBG);
		compHoldSepAndInfoEncDec.setForeground(colorFG);
		//lblSeparateEncDecWithLoadTextBottom.setBackground(colorBG);
		//lblSeparateEncDecWithLoadTextBottom.setForeground(colorFG);
	}
	
	
	
	private void createLoadTextContent(Composite parent) {
		grpLoadText = new Group(parent, SWT.NONE);
		grpLoadText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpLoadText.setLayout(new GridLayout(1, false));
		grpLoadText.setText("3 \u2212 Load plaintext or ciphertext");
	
		textSelector = new TextLoadController(grpLoadText, parent, SWT.NONE, true, true);
		textSelector.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		compHoldSepAndInfoForSelector = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoForSelector.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoForSelector.setLayout(new GridLayout(2, false));
		
		
		
		lblInfoSeperator = new Label(compHoldSepAndInfoForSelector, SWT.SEPARATOR | SWT.VERTICAL);
		lblInfoSeperator.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		txtInfoSelector = new Text(compHoldSepAndInfoForSelector, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelector.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoSelector.setBackground(GUIHandler.colorBGinfo);
		guiHandler.setSizeControl(txtInfoSelector, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSelector.setText(guiHandler.getMessageByControl("txtInfoSelector"));
		txtInfoSelector.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoSelector, SWT.CURSOR_ARROW);
			}
		});
		
	}
	
	
	
	
	private void initializeContent() {
		btnRadioEncrypt.setSelection(true);
		btnDecryptInEncryptionMode.setEnabled(false);
		btnNextBlock.setEnabled(false);
		btnPrevBlock.setEnabled(false);
		btnResetChosenPlaintexts.setEnabled(false);
		btnWriteToJCTeditor.setEnabled(false);
		btnWriteToJCTeditorDecryptMode.setEnabled(false);
	}
	
	
	
	private void createEncryptionDecryptionRadioBtns(Composite parent) {
		
		
		//lblSeparateEncDecWithLoadTextTop = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSeparateEncDecWithLoadTextTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		grpHoldEncryptDecryptRadio = new Group(parent, SWT.NONE);
		grpHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpHoldEncryptDecryptRadio.setLayout(new GridLayout(1, false));
		grpHoldEncryptDecryptRadio.setText("4 \u2212 Encrypt/Decrypt");
		
		/*compHoldEncryptDecryptRadio = new Composite(parent, SWT.NONE);
		compHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compHoldEncryptDecryptRadio.setLayout(new GridLayout(2, false));*/
		
		compHoldEncryptDecryptRadio = new Composite(grpHoldEncryptDecryptRadio, SWT.NONE);
		compHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compHoldEncryptDecryptRadio.setLayout(new GridLayout(2, false));
		
		
		
		
		btnRadioEncrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioEncrypt.setText("Encryption");
		btnRadioEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {		
				hcstb.btnRadioEncryptAction(getCurrentInstance());
			}
		});
		
		
		btnRadioDecrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioDecrypt.setText("Decryption");
		btnRadioDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnRadioDecryptAction(getCurrentInstance());
			}
		});
		
		
		compHoldInfoForEncDec = new Composite(parent, SWT.NONE);
		compHoldInfoForEncDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldInfoForEncDec.setLayout(new GridLayout(2, false));
		
		
		lblSeparateInfoForEncDec = new Label(compHoldInfoForEncDec, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfoForEncDec.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		txtInfoForEncDecRadio = new Text(compHoldInfoForEncDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoForEncDecRadio.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoForEncDecRadio.setBackground(GUIHandler.colorBGinfo);
		guiHandler.setSizeControl(txtInfoForEncDecRadio, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoForEncDecRadio.setText("Select \"Encryption\" to switch to encryption mode.\n"
				+ "Select \"Decryption\" to switch to decryption mode");
		txtInfoForEncDecRadio.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoForEncDecRadio, SWT.CURSOR_ARROW);
			}
		});
		
		//lblSeparateEncDecWithLoadTextBottom = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSeparateEncDecWithLoadTextBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	
	}
	
	


	private void createEncryptionDecryptionContent(Composite parent) {
		grpEncryptDecrypt = new Group(parent, SWT.NONE);
		grpEncryptDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpEncryptDecrypt.setLayout(new GridLayout(1, false));
		grpEncryptDecrypt.setText("5 \u2212 Encryption");
		
		compHoldEncryptDecryptContent = new Composite(grpEncryptDecrypt, SWT.NONE);
		compHoldEncryptDecryptContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptDecryptContent.setLayout(new GridLayout(1, false));
		
			
		compHoldEncryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldEncryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptionProcess.setLayout(new GridLayout(2, false));
		
		compHoldDecryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldDecryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldDecryptionProcess.setLayout(new GridLayout(2, false));
		guiHandler.hideControl(compHoldDecryptionProcess);
		
		
		compHoldCiphertext = new Composite(compHoldEncryptionProcess, SWT.NONE);
		compHoldCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldCiphertext.setLayout(new GridLayout(1, false));
		
		lblCiphertextDescryption = new Label(compHoldCiphertext, SWT.NONE);
		lblCiphertextDescryption.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		lblCiphertextDescryption.setText("Ciphertext");
		
		txtCiphertext = new Text(compHoldCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtCiphertext.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtCiphertext, 500, 200);
		txtCiphertext.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				hcstb.txtCiphertextModifyAction(getCurrentInstance());
			}
		});
		/*txtCiphertext.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtCiphertext, SWT.CURSOR_ARROW);
			}
		});*/
	
		
		
		txtEncryptionWarning = new Text(compHoldCiphertext, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtEncryptionWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtEncryptionWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtEncryptionWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.setSizeControlWarning(txtEncryptionWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEncryptionWarning);
		
		
		
		
		
		compHoldBtnsForFeatures = new Composite(compHoldEncryptionProcess, SWT.NONE);
		compHoldBtnsForFeatures.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compHoldBtnsForFeatures.setLayout(new GridLayout(1, false));
		
		compChooseBlockPadding = new Composite(compHoldBtnsForFeatures, SWT.NONE);
		compChooseBlockPadding.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		compChooseBlockPadding.setLayout(new GridLayout(1, false));
		
		
		lblChooseBlockPadding = new Label(compChooseBlockPadding, SWT.NONE);
		lblChooseBlockPadding.setText("Block-Padding");
		
		cmbChooseBlockPadding = new CCombo(compChooseBlockPadding, SWT.READ_ONLY);
		cmbChooseBlockPadding.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//cmbChooseBlockPadding.setVisibleItemCount(10);
		cmbChooseBlockPadding.setItems(new String[]{"ANSI X9.23", "PKCS#7"});
		cmbChooseBlockPadding.select(0);
		
		
		compSpaceBetweenCmbs = new Composite(compHoldBtnsForFeatures, SWT.NONE);
		compSpaceBetweenCmbs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		compSpaceBetweenCmbs.setLayout(new GridLayout(1, false));
		guiHandler.setSizeControl(compSpaceBetweenCmbs, SWT.DEFAULT, 20);
		
		
		btnEncrypt = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnEncrypt.setText("Encrypt");
		btnEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnEncryptAction(textSelector, txtCiphertext, txtEncryptionWarning, cmbChooseBlockPadding);
			}
		});
		
		btnDecryptInEncryptionMode = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnDecryptInEncryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnDecryptInEncryptionMode.setText("Decrypt and switch to decryption mode");
		btnDecryptInEncryptionMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
						hcstb.btnDecryptInEncryptionModeAction(getCurrentInstance(), possiblePlaintextsSize);
			}
		});
		
			
		
		btnWriteToJCTeditor = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnWriteToJCTeditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnWriteToJCTeditor.setText("Write to JCT editor");
		btnWriteToJCTeditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					hcstb.btnWriteToJCTeditor(getCurrentInstance());
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
			
		compHoldPlaintext = new Composite(compHoldDecryptionProcess, SWT.NONE);
		compHoldPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldPlaintext.setLayout(new GridLayout(1, false));
		
		
		compCiphertextSegmentsComponents = new Composite(compHoldPlaintext, SWT.NONE);
		compCiphertextSegmentsComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compCiphertextSegmentsComponents.setLayout(new GridLayout(3, false));
		guiHandler.setControlMargin(compCiphertextSegmentsComponents, 0, 5);
		
		
		
		
		compChooseBlockComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compChooseBlockComponents.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compChooseBlockComponents.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compChooseBlockComponents, 0, 5);
		
		lblChooseBlockDesc = new Label(compChooseBlockComponents, SWT.NONE);
		lblChooseBlockDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblChooseBlockDesc.setText("Block");
		
		cmbChooseBlock = new Combo(compChooseBlockComponents, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbChooseBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(cmbChooseBlock, SWT.DEFAULT + 70, SWT.DEFAULT);
		cmbChooseBlock.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.cmbChooseBlockAction(getCurrentInstance());
			}
			
		});
		
		
		compHoldCiphertextSegments = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compHoldCiphertextSegments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldCiphertextSegments.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldCiphertextSegments, 0, 0);
		
		
		lblCiphertextSegmentsDescription = new Label(compHoldCiphertextSegments, SWT.NONE);
		lblCiphertextSegmentsDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		lblCiphertextSegmentsDescription.setText("Ciphertext separated into blocks (\"||\" as separator)");
		
				
		txtCiphertextSegments = new StyledText(compHoldCiphertextSegments, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtCiphertextSegments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtCiphertextSegments.setBackground(ColorService.LIGHTGRAY);
		txtCiphertextSegments.setSelectionBackground(ColorService.LIGHT_AREA_BLUE);
		txtCiphertextSegments.setSelectionForeground(ColorService.BLACK);
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT + 75);
		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.fixSelection(txtCiphertextSegments, cmbChooseBlock);
			}
		});
		
		txtCiphertextSegments.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				hcstb.txtCiphertextSegmentsModifyAction(getCurrentInstance());
			}
		});
		
		
		compPrevNextComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compPrevNextComponents.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compPrevNextComponents.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compPrevNextComponents, 0, 5);
		((GridLayout) compPrevNextComponents.getLayout()).marginTop = 20;
		
		
		
		btnPrevBlock = new Button(compPrevNextComponents, SWT.PUSH);
		btnPrevBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnPrevBlock.setText("Previous block");
		btnPrevBlock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnPrevBlockAction(getCurrentInstance());
			}
		});
		
		
		btnNextBlock = new Button(compPrevNextComponents, SWT.PUSH);
		btnNextBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnNextBlock.setText("Next block");
		btnNextBlock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnNextBlockAction(getCurrentInstance());
			}
		});
		
		
		txtDecryptWarning = new Text(compHoldPlaintext, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtDecryptWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtDecryptWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtDecryptWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.setSizeControlWarning(txtDecryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtDecryptWarning);
		((GridData) txtDecryptWarning.getLayoutData()).horizontalIndent = 90;
		txtDecryptWarning.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtDecryptWarning, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		compPlaintexts = new Composite(compHoldPlaintext, SWT.NONE);
		compPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compPlaintexts.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPlaintexts, 0, 5);
		
		
		compFirstPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compFirstPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compFirstPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compFirstPlaintext, 0, 5);
		
		
		
		lblFirstPlaintextDesc = new Label(compFirstPlaintext, SWT.NONE);
		lblFirstPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblFirstPlaintextDesc.setText("Plaintext 1");
		
		
		
		txtFirstPlaintext = new Text(compFirstPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtFirstPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		guiHandler.setSizeControl(txtFirstPlaintext, 100, 50);
		txtFirstPlaintext.setBackground(GUIHandler.colorBGinfo);
		txtFirstPlaintext.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtFirstPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		txtFirstPlaintext.addMouseTrackListener(new MouseTrackAdapter() {
				
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtFirstPlaintext, SWT.CURSOR_ARROW);
			}
		});
		
		
		
		compSecondPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compSecondPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSecondPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compSecondPlaintext, 0, 5);
		
		
		
		lblSecondPlaintextDesc = new Label(compSecondPlaintext, SWT.NONE);
		lblSecondPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblSecondPlaintextDesc.setText("Plaintext 2");
	
		
		
		
		txtSecondPlaintext = new Text(compSecondPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtSecondPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		guiHandler.setSizeControl(txtSecondPlaintext, 100, 50);
		txtSecondPlaintext.setBackground(GUIHandler.colorBGinfo);
		txtSecondPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtSecondPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		txtSecondPlaintext.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtSecondPlaintext, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		compThirdPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compThirdPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compThirdPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compThirdPlaintext, 0, 5);
		
		lblThirdPlaintextDesc = new Label(compThirdPlaintext, SWT.NONE);
		lblThirdPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblThirdPlaintextDesc.setText("Plaintext 3");
	
		
		
		
		
		txtThirdPlaintext = new Text(compThirdPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtThirdPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		guiHandler.setSizeControl(txtThirdPlaintext, 100, 50);
		txtThirdPlaintext.setBackground(GUIHandler.colorBGinfo);
		txtThirdPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtThirdPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		txtThirdPlaintext.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtThirdPlaintext, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		compFourthPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compFourthPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compFourthPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compFourthPlaintext, 0, 5);
		
		lblFourthPlaintextDesc = new Label(compFourthPlaintext, SWT.NONE);
		lblFourthPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblFourthPlaintextDesc.setText("Plaintext 4");
		
	
		
		
		
		
		txtFourthPlaintext = new Text(compFourthPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtFourthPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		guiHandler.setSizeControl(txtFourthPlaintext, 100, 50);
		txtFourthPlaintext.setBackground(GUIHandler.colorBGinfo);
		txtFourthPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtFourthPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		txtFourthPlaintext.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtFourthPlaintext, SWT.CURSOR_ARROW);
			}
		});
	
				
		
		compPreviewChosenPlaintexts = new Composite(compHoldPlaintext, SWT.BORDER);
		compPreviewChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compPreviewChosenPlaintexts.setLayout(new GridLayout(1, false));
		
		lblPreviewChosenPlaintextDesc = new Label(compPreviewChosenPlaintexts, SWT.NONE);
		lblPreviewChosenPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblPreviewChosenPlaintextDesc.setText("Chosen plaintexts (preview)");
		
		
		lblSeparatePreviewDesc = new Label(compPreviewChosenPlaintexts, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparatePreviewDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtChosenPlaintexts = new Text(compPreviewChosenPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		txtChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtChosenPlaintexts.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtChosenPlaintexts, SWT.DEFAULT, 200);
		txtChosenPlaintexts.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				hcstb.txtChosenPlaintextsModifyAction(getCurrentInstance());
			}
		});
		/*txtChosenPlaintexts.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtChosenPlaintexts, SWT.CURSOR_ARROW);
			}
		});*/
	
			
		
		compHoldBtnsForFeaturesDecryptionMode = new Composite(compHoldDecryptionProcess, SWT.NONE);
		compHoldBtnsForFeaturesDecryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compHoldBtnsForFeaturesDecryptionMode.setLayout(new GridLayout(1, false));
		
		
		btnDecryption = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
		btnDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnDecryption.setText("Decrypt");
		btnDecryption.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnDecryptionAction(getCurrentInstance(), possiblePlaintextsSize);
			}
		});
		
		
		
		btnWriteToJCTeditorDecryptMode = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
		btnWriteToJCTeditorDecryptMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnWriteToJCTeditorDecryptMode.setText("Write to JCT editor");
		btnWriteToJCTeditorDecryptMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					hcstb.btnWriteToJCTeditorDecryptMode(getCurrentInstance());
				} catch (PartInitException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		btnResetChosenPlaintexts = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
		btnResetChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnResetChosenPlaintexts.setText("Reset chosen plaintexts");
		btnResetChosenPlaintexts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnResetChosenPlaintextsAction(getCurrentInstance(), possiblePlaintextsSize);
			}
		});
		
		
		
		
		compHoldSepAndInfoEncDec = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoEncDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoEncDec.setLayout(new GridLayout(2, false));
		
		
		
		
	
		lblSeperateDescription = new Label(compHoldSepAndInfoEncDec, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeperateDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) lblSeperateDescription.getLayoutData()).horizontalIndent = 0;
		
		txtInfoEncryptionDecryption = new Text(compHoldSepAndInfoEncDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoEncryptionDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoEncryptionDecryption.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtInfoEncryptionDecryption, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_encrypt"));
		txtInfoEncryptionDecryption.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoEncryptionDecryption, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		guiHandler.setSizeControlWarning(grpEncryptDecrypt, SWT.DEFAULT, SWT.DEFAULT);	
		
	}
}
