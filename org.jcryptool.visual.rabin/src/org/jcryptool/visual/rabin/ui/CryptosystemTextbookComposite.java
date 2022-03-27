package org.jcryptool.visual.rabin.ui;

import java.util.Observable;
import java.util.Observer;

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
import org.eclipse.ui.PartInitException;
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
	private Text txtDecryptWarning;
	
	private Text txtFirstPlaintext;
	private Text txtSecondPlaintext;
	private Text txtThirdPlaintext;
	private Text txtFourthPlaintext;
	
	private int possiblePlaintextsSize = 4;
	private Button btnResetChosenPlaintexts;

		
	
	public CryptosystemTextbookComposite(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	
	public CryptosystemTextbookComposite(Composite parent, int style, RabinFirstTabComposite rftc) {
		super(parent, style);
		this.hcstb = new HandleCryptosystemTextbook(rftc);
		this.guiHandler = rftc.getGUIHandler();
		createLoadTextContent(this);
		createEncryptionDecryptionRadioBtns(this);
		createEncryptionDecryptionContent(this);
		initializeContent();
	}
	
	
	
	public CryptosystemTextbookComposite getCurrentInstance() {
		return this;
	}
	
	
	public Group getGrpEncryptDecrypt() {
		return grpEncryptDecrypt;
	}
	
	
	public Text getTxtInfoEncryptionDecryption() {
		return txtInfoEncryptionDecryption;
	}
	
	
	
	public Button getBtnWriteToJCTeditor() {
		return btnWriteToJCTeditor;
	}
	
	
	public Button getBtnWriteToJCTeditorDecryptMode() {
		return btnWriteToJCTeditorDecryptMode;
	}
	
	
	public Button getBtnResetChosenPlaintexts() {
		return btnResetChosenPlaintexts;
	}
	
	
	
	public Button getBtnNextBlock() {
		return btnNextBlock;
	}
	
	
	public Button getBtnPrevBlock() {
		return btnPrevBlock;
	}
	
	
	public Button getBtnDecryptInEncryptionMode() {
		return btnDecryptInEncryptionMode;
	}
	
	
	
	public Text getTxtCiphertext() {
		return txtCiphertext;
	}
	
	
	public Button getBtnRadioEncrypt() {
		return btnRadioEncrypt;
	}
	
	
	public Button getBtnRadioDecrypt() {
		return btnRadioDecrypt;
	}
	
	
	
	public Text getTxtFirstPlaintext() {
		return txtFirstPlaintext;
	}
	
	
	public Text getTxtSecondPlaintext() {
		return txtSecondPlaintext;
	}

	
	public Text getTxtThirdPlaintext() {
		return txtThirdPlaintext;
	}
	
	
	public Text getTxtFourthPlaintext() {
		return txtFourthPlaintext;
	}
	
	
	public Text getTxtChosenPlaintexts() {
		return txtChosenPlaintexts;
	}
	
	
	
	public Text getDecryptWarning() {
		return txtDecryptWarning;
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
		
		//Composite compForVerticalSpacing = new Composite(parent, SWT.NONE);
		//compForVerticalSpacing.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//compForVerticalSpacing.setLayout(new GridLayout(1, false));
		//((GridData) compForVerticalSpacing.getLayoutData()).
		
		
		Group grpLoadText = new Group(parent, SWT.NONE);
		grpLoadText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpLoadText.setLayout(new GridLayout(3, false));
		grpLoadText.setText("Load plaintext or ciphertext");
		((GridData) grpLoadText.getLayoutData()).verticalIndent = 40;
		
		
		textSelector = new TextLoadController(grpLoadText, parent, SWT.NONE, true, true);
		textSelector.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		//textSelector.setBackground(guiHandler.getColorSelectControl());
		//textSelector.setForeground(guiHandler.getColorSelectControl());
		//((GridData) textSelector.getLayoutData()).horizontalIndent = 400;
		
		Label lblInfoSeperator = new Label(grpLoadText, SWT.SEPARATOR | SWT.VERTICAL);
		lblInfoSeperator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		//((GridData) lblInfoSeperator.getLayoutData()).horizontalIndent = 20;
		
		
		Text txtInfoSelector = new Text(grpLoadText, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelector.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoSelector.setBackground(guiHandler.getColorBGinfo());
		guiHandler.setSizeControl(txtInfoSelector, SWT.DEFAULT, 70);
		txtInfoSelector.setText(guiHandler.getMessageByControl("txtInfoSelector"));
		
		//((GridData) textSelector.getLayoutData()).horizontalIndent = 400;
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
		
		Composite compHoldEncryptDecryptRadio = new Composite(parent, SWT.NONE);
		compHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		compHoldEncryptDecryptRadio.setLayout(new GridLayout(2, false));
		guiHandler.setControlMargin(compHoldEncryptDecryptRadio, SWT.DEFAULT, 15);

		
		btnRadioEncrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioEncrypt.setText("Encryption");
		btnRadioEncrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*guiHandler.hideControl(compHoldDecryptionProcess);
				guiHandler.showControl(compHoldEncryptionProcess);
				grpEncryptDecrypt.setText("Encryption");
				txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_encrypt"));
				*/
				
				hcstb.btnRadioEncryptAction(getCurrentInstance());
			}
		});
		
		
		btnRadioDecrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
		btnRadioDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnRadioDecrypt.setText("Decryption");
		btnRadioDecrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*guiHandler.hideControl(compHoldEncryptionProcess);
				guiHandler.showControl(compHoldDecryptionProcess);
				grpEncryptDecrypt.setText("Decryption");
				txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_decrypt"));
				*/
				
				hcstb.btnRadioDecryptAction(getCurrentInstance());
			}
		});
	}
	
	


	private void createEncryptionDecryptionContent(Composite parent) {
		
		grpEncryptDecrypt = new Group(parent, SWT.NONE);
		grpEncryptDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpEncryptDecrypt.setLayout(new GridLayout(3, false));
		grpEncryptDecrypt.setText("Encryption");
		
		compHoldEncryptDecryptContent = new Composite(grpEncryptDecrypt, SWT.NONE);
		compHoldEncryptDecryptContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptDecryptContent.setLayout(new GridLayout(1, false));
		
		
		/*Composite compHoldEncryptDecryptRadio = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
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
				grpEncryptDecrypt.setText("Encryption");
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
				grpEncryptDecrypt.setText("Decryption");
			}
		});*/
		
		
		
		compHoldEncryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
		compHoldEncryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldEncryptionProcess.setLayout(new GridLayout(2, false));
		//guiHandler.hideControl(compHoldEncryptionProcess);
		
		
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
		btnEncrypt.setBackground(ColorService.LIGHT_AREA_BLUE);
		
		
		btnDecryptInEncryptionMode = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnDecryptInEncryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnDecryptInEncryptionMode.setText("Decrypt and switch to decryption mode");
		btnDecryptInEncryptionMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hcstb.btnDecryptInEncryptionModeAction(getCurrentInstance(), possiblePlaintextsSize);
				
			}
		});
		
		
		
		/*Button btnStopComputation = new Button(compHoldBtnsForFeatures, SWT.PUSH);
		btnStopComputation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnStopComputation.setText("Stop computation");
		btnStopComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});*/
		
		
		
		
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
		
		
		/*txtEncryptWarning = new Text(compHoldBtnsForFeatures, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtEncryptWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControlWarning(txtEncryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtEncryptWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtEncryptWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtEncryptWarning);*/
		
		
		
		
		compHoldPlaintext = new Composite(compHoldDecryptionProcess, SWT.NONE);
		compHoldPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldPlaintext.setLayout(new GridLayout(1, false));
	
		
		//Label lblCiphertextSegmentsDescription = new Label(compHoldPlaintext, SWT.NONE);
		//lblCiphertextSegmentsDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		//lblCiphertextSegmentsDescription.setText("Ciphertext separated into segments (\"||\" as separator)");
		
		
		compCiphertextSegmentsComponents = new Composite(compHoldPlaintext, SWT.NONE);
		compCiphertextSegmentsComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compCiphertextSegmentsComponents.setLayout(new GridLayout(3, false));
		guiHandler.setControlMargin(compCiphertextSegmentsComponents, 0, 5);
		
		
		
		
		compChooseBlockComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compChooseBlockComponents.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		compChooseBlockComponents.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compChooseBlockComponents, 0, 5);
		//((GridLayout) compChooseBlockComponents.getLayout()).marginLeft = 0;
		
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
		
		
		Composite compHoldCiphertextSegments = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
		compHoldCiphertextSegments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldCiphertextSegments.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldCiphertextSegments, 0, 0);
		
		
		Label lblCiphertextSegmentsDescription = new Label(compHoldCiphertextSegments, SWT.NONE);
		lblCiphertextSegmentsDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		lblCiphertextSegmentsDescription.setText("Ciphertext separated into blocks (\"||\" as separator)");
		
		
		
		/*Composite compTest = new Composite(compHoldCiphertextSegments, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		compTest.setLayout(new GridLayout(1, false));
		guiHandler.setSizeControl(compTest, SWT.DEFAULT, SWT.DEFAULT);*/
		
		
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
		
		
		
		/*txtDecryptWarning = new Text(compHoldCiphertextSegments, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtDecryptWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtDecryptWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtDecryptWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(txtDecryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtDecryptWarning);*/
		
		
		
		
		
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
		txtDecryptWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtDecryptWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(txtDecryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtDecryptWarning);
		((GridData) txtDecryptWarning.getLayoutData()).horizontalIndent = 90;
		
		
		
		compPlaintexts = new Composite(compHoldPlaintext, SWT.NONE);
		compPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compPlaintexts.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPlaintexts, 0, 5);
		
		
		Composite compFirstPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compFirstPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compFirstPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compFirstPlaintext, 0, 5);
		
		
		
		Label lblFirstPlaintextDesc = new Label(compFirstPlaintext, SWT.NONE);
		lblFirstPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblFirstPlaintextDesc.setText("Plaintext 1");
		
		
		
		txtFirstPlaintext = new Text(compFirstPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtFirstPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtFirstPlaintext, 100, 100);
		txtFirstPlaintext.setBackground(guiHandler.getColorBGinfo());
		txtFirstPlaintext.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtFirstPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		
		
		
		Composite compSecondPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compSecondPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSecondPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compSecondPlaintext, 0, 5);
		
		
		
		Label lblSecondPlaintextDesc = new Label(compSecondPlaintext, SWT.NONE);
		lblSecondPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblSecondPlaintextDesc.setText("Plaintext 2");
	
		
		
		
		txtSecondPlaintext = new Text(compSecondPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtSecondPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtSecondPlaintext, 100, 100);
		txtSecondPlaintext.setBackground(guiHandler.getColorBGinfo());
		txtSecondPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtSecondPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		
		
		
		Composite compThirdPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compThirdPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compThirdPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compThirdPlaintext, 0, 5);
		
		Label lblThirdPlaintextDesc = new Label(compThirdPlaintext, SWT.NONE);
		lblThirdPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblThirdPlaintextDesc.setText("Plaintext 3");
	
		
		
		
		
		txtThirdPlaintext = new Text(compThirdPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtThirdPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtThirdPlaintext, 100, 100);
		txtThirdPlaintext.setBackground(guiHandler.getColorBGinfo());
		txtThirdPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtThirdPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		
		
		
		Composite compFourthPlaintext = new Composite(compPlaintexts, SWT.NONE);
		compFourthPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compFourthPlaintext.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compFourthPlaintext, 0, 5);
		//((GridLayout) compFourthPlaintext.getLayout()).marginRight = 5;
		
		
		Label lblFourthPlaintextDesc = new Label(compFourthPlaintext, SWT.NONE);
		lblFourthPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblFourthPlaintextDesc.setText("Plaintext 4");
		
	
		
		
		
		
		txtFourthPlaintext = new Text(compFourthPlaintext, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtFourthPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtFourthPlaintext, 100, 100);
		txtFourthPlaintext.setBackground(guiHandler.getColorBGinfo());
		txtFourthPlaintext.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				hcstb.txtFourthPlaintextMouseDownAction(getCurrentInstance());
			}
			
		});
		
		
		//plaintexts = new Text[4];
		
		/*for(int i = 0; i < plaintexts.length; i++) {
			plaintexts[i] = new Text(compPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
			plaintexts[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			guiHandler.setSizeControl(plaintexts[i], 100, 100);
			plaintexts[i].addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseDown(MouseEvent e) {
					Text src = (Text) e.getSource();
					if(i == 0) {
						countClicksForPlaintexts[0]++;
					}
				}
				
			});
		}*/
		
		
		
		compPreviewChosenPlaintexts = new Composite(compHoldPlaintext, SWT.BORDER);
		compPreviewChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compPreviewChosenPlaintexts.setLayout(new GridLayout(1, false));
		
		Label lblPreviewChosenPlaintextDesc = new Label(compPreviewChosenPlaintexts, SWT.NONE);
		lblPreviewChosenPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		lblPreviewChosenPlaintextDesc.setText("Preview chosen plaintexts");
		
		
		Label lblSeparatePreviewDesc = new Label(compPreviewChosenPlaintexts, SWT.SEPARATOR | SWT.HORIZONTAL);
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
		
		
		/*txtDecryptWarning = new Text(compHoldPlaintext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtDecryptWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtDecryptWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtDecryptWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(txtDecryptWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtDecryptWarning);*/
		
		
		
		
		
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
		
		
		
		
		
		
	
		
	
		
		
		
		
		
		
		Label lblSeperateDescription = new Label(grpEncryptDecrypt, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeperateDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) lblSeperateDescription.getLayoutData()).horizontalIndent = 0;
		
		txtInfoEncryptionDecryption = new Text(grpEncryptDecrypt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoEncryptionDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtInfoEncryptionDecryption.setBackground(ColorService.LIGHTGRAY);
		guiHandler.setSizeControl(txtInfoEncryptionDecryption, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoEncryptionDecryption.setText(guiHandler.getMessageByControl("txtInfoEncryptionDecryption_encrypt"));
		
		
		
		
		
		
	}
	
	

}
