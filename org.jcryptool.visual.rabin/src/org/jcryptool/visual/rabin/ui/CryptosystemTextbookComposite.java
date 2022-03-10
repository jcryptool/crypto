package org.jcryptool.visual.rabin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.HandleCryptosystemTextbook;
import org.jcryptool.visual.rabin.HandleFirstTab;
import org.jcryptool.visual.rabin.Messages;

public class CryptosystemTextbookComposite extends Composite {


	
	//private HandleFirstTab guiHandler;
	private GUIHandler guiHandler;
	private HandleCryptosystemTextbook hcstb;
	private RabinFirstTabComposite rftc;
	
	private Group grpEncryptDecrypt;
	private Composite compHoldEncryptDecryptContent;
	private Button btnRadioEncrypt;
	private Button btnRadioDecrypt;
	private Composite compHoldEncryptionProcess;
	private Composite compHoldDecryptionProcess;
	private TextLoadController textSelectorEncryption;
	private Composite compHoldCiphertext;
	private Label lblCiphertextDescryption;
	private Text txtCiphertext;
	private Composite compHoldBtnsForFeatures;
	private Button btnEncrypt;
	private Button btnDecryptInEncryptionMode;
	private Button btnWriteToJCTeditor;
	private TextLoadController textSelectorDecryption;
	private Composite compHoldPlaintext;
	private Composite compCiphertextSegmentsComponents;
	private Composite compChooseBlockComponents;
	private Combo cmbChooseBlock;
	private StyledText txtCiphertextSegments;
	private Composite compPrevNextComponents;
	private Button btnPrevBlock;
	private Button btnNextBlock;
	private Composite compPlaintexts;
	private Text[] plaintexts;
	private Composite compPreviewChosenPlaintexts;
	private Text txtChosenPlaintexts;
	private Composite compHoldBtnsForFeaturesDecryptionMode;
	private Button btnDecryption;
	private Button btnWriteToJCTeditorDecryptMode;
	private Text txtInfoEncryptionDecryption;
	private Text txtEncryptWarning;
	private TextLoadController textSelector;
	private Text txtEncryptionWarning;

		
	
	public CryptosystemTextbookComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	
	public CryptosystemTextbookComposite(Composite parent, int style, RabinFirstTabComposite rftc) {
		super(parent, style);
		this.hcstb = new HandleCryptosystemTextbook(rftc);
		this.guiHandler = rftc.getGUIHandler();
		createLoadTextContent(this);
		createEncryptionDecryptionContent(this);
	}
	
	
	
	public CryptosystemTextbookComposite getCurrentInstance() {
		return this;
	}
	
	
	
	
	public Combo getCmbChooseBlock() {
		return cmbChooseBlock;
	}
	
	
	/**
	 * @return the plaintexts
	 */
	public Text[] getPlaintexts() {
		return plaintexts;
	}


	/**
	 * @return the txtCiphertextSegments
	 */
	public StyledText getTxtCiphertextSegments() {
		return txtCiphertextSegments;
	}


	/**
	 * @return the btnEncrypt
	 */
	public Button getBtnEncrypt() {
		return btnEncrypt;
	}


	/**
	 * @return the textSelector
	 */
	public TextLoadController getTextSelector() {
		return textSelector;
	}


	/**
	 * @return the compHoldEncryptionProcess
	 */
	public Composite getCompHoldEncryptionProcess() {
		return compHoldEncryptionProcess;
	}


	/**
	 * @return the compHoldDecryptionProcess
	 */
	public Composite getCompHoldDecryptionProcess() {
		return compHoldDecryptionProcess;
	}
	
	
	private void createLoadTextContent(Composite parent) {
		Group grpLoadText = new Group(parent, SWT.NONE);
		grpLoadText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpLoadText.setLayout(new GridLayout(3, false));
		grpLoadText.setText("Load plaintext or ciphertext");
		
		textSelector = new TextLoadController(grpLoadText, parent, SWT.NONE, true, true);
		textSelector.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	
		
		
		Label lblInfoSeperator = new Label(grpLoadText, SWT.SEPARATOR | SWT.VERTICAL);
		lblInfoSeperator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
	
		
		Text txtInfoSelector = new Text(grpLoadText, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelector.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoSelector.setText("insert a text here");
		txtInfoSelector.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoSelector, SWT.DEFAULT, SWT.DEFAULT);
		((GridData) textSelector.getLayoutData()).horizontalIndent = 400;
	}
	
	


	private void createEncryptionDecryptionContent(Composite parent) {
		
		grpEncryptDecrypt = new Group(parent, SWT.NONE);
		grpEncryptDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpEncryptDecrypt.setLayout(new GridLayout(3, false));
		grpEncryptDecrypt.setText("Encryption/Decryption");
		
		compHoldEncryptDecryptContent = new Composite(grpEncryptDecrypt, SWT.NONE);
		compHoldEncryptDecryptContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptDecryptContent.setLayout(new GridLayout(1, false));
		
		
		Composite compHoldEncryptDecryptRadio = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		compHoldEncryptDecryptRadio.setLayout(new GridLayout(2, false));
		
		
		
		btnRadioEncrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioEncrypt.setText("Encryption");
		btnRadioEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compHoldDecryptionProcess);
				guiHandler.showControl(compHoldEncryptionProcess);
			}
		});
		
		
		btnRadioDecrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioDecrypt.setText("Decryption");
		btnRadioDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compHoldEncryptionProcess);
				guiHandler.showControl(compHoldDecryptionProcess);
			}
		});
		
		
		
		compHoldEncryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldEncryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptionProcess.setLayout(new GridLayout(2, false));
		guiHandler.hideControl(compHoldEncryptionProcess);
		
		
		compHoldDecryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldDecryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldDecryptionProcess.setLayout(new GridLayout(2, false));
		
		
		
		compHoldCiphertext = new Composite(compHoldEncryptionProcess, SWT.NONE);
		compHoldCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldCiphertext.setLayout(new GridLayout(1, false));
		
		lblCiphertextDescryption = new Label(compHoldCiphertext, SWT.NONE);
		lblCiphertextDescryption.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		lblCiphertextDescryption.setText("Ciphertext");
		
		txtCiphertext = new Text(compHoldCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtCiphertext.setText("this is a test");
		txtCiphertext.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtCiphertext, 600, 300);
		
		
		txtEncryptionWarning = new Text(compHoldCiphertext, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtEncryptionWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtEncryptionWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtEncryptionWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(txtEncryptionWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtEncryptionWarning);
		
		
		
		
		
		compHoldBtnsForFeatures = new Composite(compHoldEncryptionProcess, SWT.NONE);
		compHoldBtnsForFeatures.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compHoldBtnsForFeatures.setLayout(new GridLayout(1, false));
		
		btnEncrypt = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnEncrypt.setText("Encrypt");
		btnEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnEncryptAction(textSelector, txtCiphertext, txtEncryptionWarning);
			}
		});
		
		
		btnDecryptInEncryptionMode = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnDecryptInEncryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnDecryptInEncryptionMode.setText("Decrypt and switch to decryption mode");
		btnDecryptInEncryptionMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnDecryptInEncryptionModeAction(txtCiphertextSegments, cmbChooseBlock, btnPrevBlock, btnNextBlock, plaintexts, 
						compHoldDecryptionProcess, compHoldEncryptionProcess, btnRadioEncrypt, btnRadioDecrypt);
				
			}
		});
		
		
		
		btnWriteToJCTeditor = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnWriteToJCTeditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnWriteToJCTeditor.setText("Write to JCT editor");
		btnWriteToJCTeditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		
		/*txtEncryptWarning = new Text(compHoldBtnsForFeatures, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtEncryptWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControlWarning(txtEncryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtEncryptWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtEncryptWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtEncryptWarning);*/
		
		
		
		
		compHoldPlaintext = new Composite(compHoldDecryptionProcess, SWT.NONE);
		compHoldPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldPlaintext.setLayout(new GridLayout(1, false));
	
		
		Label lblCiphertextSegmentsDescription = new Label(compHoldPlaintext, SWT.NONE);
		lblCiphertextSegmentsDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		lblCiphertextSegmentsDescription.setText("Ciphertext separated into segments (\"||\" as separator)");
		
		
		compCiphertextSegmentsComponents = new Composite(compHoldPlaintext, SWT.NONE);
		compCiphertextSegmentsComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compCiphertextSegmentsComponents.setLayout(new GridLayout(3, false));
		
		
		compChooseBlockComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compChooseBlockComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		compChooseBlockComponents.setLayout(new GridLayout(1, false));
		
		Label lblChooseBlockDesc = new Label(compChooseBlockComponents, SWT.NONE);
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
		
		
		txtCiphertextSegments = new StyledText(compCiphertextSegmentsComponents, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtCiphertextSegments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtCiphertextSegments.setText("this is a test");
		txtCiphertextSegments.setBackground(ColorService.LIGHTGRAY);
		txtCiphertextSegments.setSelectionBackground(ColorService.LIGHT_AREA_BLUE);
		txtCiphertextSegments.setSelectionForeground(ColorService.BLACK);
		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
		txtCiphertextSegments.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.fixSelection(txtCiphertextSegments, cmbChooseBlock);
			}
		});
		
		
		compPrevNextComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compPrevNextComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		compPrevNextComponents.setLayout(new GridLayout(1, false));
		
		
		
		
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
		
		
		
		
		
		compPlaintexts = new Composite(compHoldPlaintext, SWT.NONE);
		compPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compPlaintexts.setLayout(new GridLayout(4, false));
		
		
		plaintexts = new Text[4];
		
		for(int i = 0; i < plaintexts.length; i++) {
			plaintexts[i] = new Text(compPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
			plaintexts[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			guiHandler.setSizeControl(plaintexts[i], 100, 100);
		}
		
		
		
		compPreviewChosenPlaintexts = new Composite(compHoldPlaintext, SWT.BORDER);
		compPreviewChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compPreviewChosenPlaintexts.setLayout(new GridLayout(1, false));
		
		Label lblPreviewChosenPlaintextDesc = new Label(compPreviewChosenPlaintexts, SWT.NONE);
		lblPreviewChosenPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblPreviewChosenPlaintextDesc.setText("Preview chosen plaintexts");
		
		
		Label lblSeparatePreviewDesc = new Label(compPreviewChosenPlaintexts, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparatePreviewDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtChosenPlaintexts = new Text(compPreviewChosenPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		txtChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtChosenPlaintexts.setText("insert something");
		txtChosenPlaintexts.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtChosenPlaintexts, SWT.DEFAULT, 200);
		
		
		
		
		
		compHoldBtnsForFeaturesDecryptionMode = new Composite(compHoldDecryptionProcess, SWT.NONE);
		compHoldBtnsForFeaturesDecryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compHoldBtnsForFeaturesDecryptionMode.setLayout(new GridLayout(1, false));
		
		
		btnDecryption = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
		btnDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnDecryption.setText("Decrypt");
		btnDecryption.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		
		btnWriteToJCTeditorDecryptMode = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
		btnWriteToJCTeditorDecryptMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnWriteToJCTeditorDecryptMode.setText("Write to JCT editor");
		btnWriteToJCTeditorDecryptMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		
		
		
		
	
		
	
		
		
		
		
		
		
		Label lblSeperateDescription = new Label(grpEncryptDecrypt, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeperateDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) lblSeperateDescription.getLayoutData()).horizontalIndent = 0;
		
		txtInfoEncryptionDecryption = new Text(grpEncryptDecrypt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtInfoEncryptionDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoEncryptionDecryption.setText("insert a text");
		txtInfoEncryptionDecryption.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtInfoEncryptionDecryption, SWT.DEFAULT, SWT.DEFAULT);
		
		
		
		
		
		
	}
	
	

}
