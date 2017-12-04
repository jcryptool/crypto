// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.extendedrsa;

import static java.math.BigInteger.ONE;
import static org.jcryptool.visual.library.Lib.LOW_PRIMES;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.ui.views.nodes.Contact;
import org.jcryptool.visual.library.Constants;
import org.jcryptool.visual.library.Lib;

import de.flexiprovider.core.rsa.RSAPrivateCrtKey;

/**
 * This class represents an identity in the visual.
 * 
 * @author Christoph Schnepf, Patrick Zillner
 * 
 */
public class Identity extends TabItem {

	private String identityName;
	private String forename;
	private String surname;
	private String organisation;
	private String region;
	private Group generalGroup;
	private Composite composite;
	private Label label;
	private Button enc_and_send;
	private Group actionGroup_1;
	private Group actionGroup_2;
	private Group actionGroup_3;
	private Group actionGroup_4;
	private Label initActions;
	private Label initActions2;
	private Text subjectInput;
	private Text clearMessage;
	private Text encryptedMessage;
	private Text decryptedMessage;
	private Text encryptedMessage_Tab2;
	private Combo messageRecipient;
	private Button sendMessage;
	private Button encryptMessage;
	private Button decryptMessage;
	private Combo recipientKeys;
	private Button receive_and_decrypt;
	private Button attackPublicKey;
	private Button keymanagement;
	private GridData gd_actionGroup_1;
	private GridData gd_actionGroup_2;
	private GridData gd_actionGroup_3;
	private GridData gd_actionGroup_4;
	private int forerunner;
	private int id;
	private Combo selectMessage;
	private Combo decryptionKeys;
	private Button deleteMessage;
	private Text pwPrivKey;
	private ExtendedTabFolder extTF;
	private TabFolder tf_keyMgmt;
	private TabItem keyMgmt_1;
	private TabItem keyMgmt_2;
	private TabItem keyMgmt_3;
	private Button radio_RSA;
	private Button radio_ExtRSA;
	private Button radio_RSA_tab2;
	private Button radio_ExtRSA_tab2;
	private Composite tab1;
	private Composite tab2;
	private Composite tab3;
	private Combo combo_rsaP;
	private Combo combo_rsaQ;
	private Combo combo_rsaE;
	private BigInteger bi_rsaP;
	private BigInteger bi_rsaQ;
	private BigInteger bi_rsaE;
	private BigInteger bi_rsaD; // D berechnen
	private BigInteger bi_rsaN;
	private BigInteger bi_rsaPhi;
	private BigInteger bi_ExtrsaP;
	private BigInteger bi_ExtrsaQ;
	private BigInteger bi_ExtrsaR;
	private BigInteger bi_ExtrsaS;
	private BigInteger bi_ExtrsaT;
	private BigInteger bi_ExtrsaE;
	private BigInteger bi_ExtrsaN;
	private BigInteger bi_ExtrsaPhi;
	private BigInteger bi_ExtrsaD;
	private Button pickRandomE;
	private Button pickRandomExtE;
	private Vector<String> possibleEs;
	private Label errorLabel_1;
	private boolean pIsPrime;
	private boolean qIsPrime;
	private boolean rIsPrime;
	private boolean sIsPrime;
	private boolean tIsPrime;
	private boolean eIsValid;
	private Composite rsaComposite;
	private Composite rsaExMainComposite;
	private Composite rsaExComposite1;
	private Composite rsaExComposite2;
	private Composite rsaExComposite3;
	private Combo numberOfPrimesExRSA;
	private Combo combo_ExrsaP;
	private Combo combo_ExrsaQ;
	private Combo combo_ExrsaE;
	private Combo combo_ExrsaR;
	private Combo combo_ExrsaS;
	private Combo combo_ExrsaT;
	private Label rsa_ex_S;
	private Label rsa_ex_T;
	private Text password1;
	private Text password2;
	private Text ext_password1;
	private Text ext_password2;
	private Combo rsa_length;
	private Combo extRsa_length;
	private Combo extRsa_numberPrimes_tab2;
	private Button createKey;
	private Button createKey_Tab2;
	private Combo selectedKey_Keydata;
	private Text password_keydata;
	private Button showKeydata;
	private Table keyData;
	private TableColumn column_parameter;
	private TableColumn column_value;
	private Text txtExplain;
	private Label init_tab1;
	private String pw1;
	private String pw2;
	private String pw1_Ext;
	private String pw2_Ext;
	private int validCount;
	private IdentityManager iMgr;
	private HashMap<String, KeyStoreAlias> rec;
	private Vector<BigInteger> pubKeyParameters;
	private Label lbl_notification_tab2;
	private KeyStoreAlias privateAlias;
	private Label lbl_pwWrong;
	private RsaImplementation rsa_impl;
	private Label infolabel_tab2;
	private boolean isPubKey;
	private TreeMap<String, KeyStoreAlias> allKeys_keydata;
	private Label wrongPW_keydata;
	private Label lbl_enterPW;
	private boolean stopUpdateE;
	private Button attackKey;
	private Combo keyToAttack;
	private Label lbl_noKeyToAttack;
	HashMap<String, KeyStoreAlias> attackableKeys;
	private Table keyData_attacked;
	private TableColumn column_parameter_attacked;
	private Label attack_hint;
	private Vector<BigInteger> primesE;
	private Text keydataN;
	private Label attack_success;
	private Button reconstructKey;
	private Label clipboardtext;
	private ScrolledComposite sc_identity;
	private Group actionGroup_5;
	private GridData gd_actionGroup_5;

	private final String EXPLAIN_INIT = Messages.Identity_0;
	private final String EXPLAIN_ENCRYPT = Messages.Identity_1;
	private final String EXPLAIN_DECRYPT = Messages.Identity_2;
	private final String EXPLAIN_SENDED = Messages.Identity_3;
	private final String EXPLAIN_DELETED = Messages.Identity_4;
	private final String PW_WRONG = Messages.Identity_5;
	private final String EXPLAIN_KEYMGMT_TAB1 = Messages.Identity_6;
	private final String EXPLAIN_KEYMGMT_TAB2 = Messages.Identity_7;
	private final String EXPLAIN_KEYMGMT_TAB3 = Messages.Identity_8;
	private final String EXPLAIN_ATTACK_PUBKEY = Messages.Identity_9;
	private final String ENTER_TWO_PRIMES = Messages.Identity_10;
	private final String ENTER_THREE_PRIMES = Messages.Identity_11;
	private final String ENTER_FOUR_PRIMES = Messages.Identity_12;
	private final String ENTER_FIVE_PRIMES = Messages.Identity_13;
	private final String TAB2_INIT = Messages.Identity_14;
	private final String TAB3_INIT = Messages.Identity_15;
	private final String NO_ENCRYPTED_MESSAGES = Messages.Identity_16;
	private final String NO_PRIME_P = Messages.Identity_17;
	private final String NO_PRIME_Q = Messages.Identity_18;
	private final String NO_PRIME_R = Messages.Identity_19;
	private final String NO_PRIME_S = Messages.Identity_20;
	private final String NO_PRIME_T = Messages.Identity_21;
	private final String NO_VALID_E = Messages.Identity_22;
	private final String PRIMES_EQUAL = Messages.Identity_23;
	private final String VALUE_TOO_SMALL = Messages.Identity_24;
	private final String NO_KEY_TO_ATTACK = Messages.Identity_25;
	private final String NOTHING = Messages.Identity_26;
	private final String HYPHEN = Messages.Identity_27;
	private final String FROM = Messages.Identity_174;
	private final String TO = Messages.Identity_175;
	private final String NO_VALID_GcdE = Messages.Identity_222;
	private final String CLIPBOARDTEXT_TEXT = Messages.Identity_179;
	private final String ALICE = "Alice Whitehat";
	private final String BOB = "BOB Whitehat";

	/**
	 * a {@link VerifyListener} instance that makes sure only digits are entered.
	 */
	private static final VerifyListener VL = Lib.getVerifyListener(Lib.DIGIT);

	/**
	 * a {@link VerifyListener} instance that makes sure only HEX-digits are
	 * entered.
	 */
	private static final VerifyListener VL_HEX = Lib.getVerifyListener(Lib.HEXDIGIT);

	/**
	 * a {@link ModifyListener} instance that calls {@link #calcParams()} whenever a
	 * value is changed.
	 */
	private final ModifyListener ml = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			try {
				bi_rsaP = new BigInteger(combo_rsaP.getText());
				bi_rsaQ = new BigInteger(combo_rsaQ.getText());
				checkParameter();
			} catch (NumberFormatException nfe) {
				bi_rsaN = Constants.MINUS_ONE;
				bi_rsaPhi = Constants.MINUS_ONE;
			}
		}
	};

	public Identity(ExtendedTabFolder parent, int style, Contact contact, Text explain) {
		super(parent, style);
		this.extTF = parent;
		this.identityName = contact.getName();
		this.forename = contact.getFirstname();
		this.surname = contact.getLastname();
		this.organisation = contact.getOrganization();
		this.region = contact.getRegion();
		this.id = parent.getItemCount();
		this.txtExplain = explain;

		iMgr = IdentityManager.getInstance();
		rsa_impl = new RsaImplementation();
		stopUpdateE = false;

		primesE = getEPrimes();

		// set the text of the TabItem
		this.setText(identityName);
		forerunner = 0;

		sc_identity = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		sc_identity.setExpandHorizontal(true);
		sc_identity.setExpandVertical(true);
		sc_identity.setLayout(new GridLayout());
		sc_identity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		this.setControl(sc_identity);

		// define the layout for the whole TabItem now
		generalGroup = new Group(sc_identity, SWT.NONE);
		generalGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		// 2 columns (actions and the actionswindow)
		generalGroup.setLayout(new GridLayout(2, false));

		sc_identity.setContent(generalGroup);

		// Grid-Layout for all the buttons on the left side
		composite = new Composite(generalGroup, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		label = new Label(composite, SWT.CENTER);
		label.setFont(FontService.getNormalBoldFont());
		label.setText(Messages.Identity_28);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		enc_and_send = new Button(composite, SWT.PUSH);
		enc_and_send.setText(Messages.Identity_36);
		enc_and_send.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		enc_and_send.addSelectionListener(new SelectionAdapter() {
			@Override
			// Button 1
			public void widgetSelected(final SelectionEvent e) {
				if (forerunner != 1) {
					actionGroup_1.dispose();
					actionGroup_2.dispose();
					actionGroup_3.dispose();
					actionGroup_4.dispose();
					actionGroup_5.dispose();
					
					createActionGroup1();
					actionGroup_1.setText(Messages.Identity_36);
					
					txtExplain.setText(EXPLAIN_ENCRYPT);

					Label lbl_subj = new Label(actionGroup_1, SWT.NONE);
					lbl_subj.setText(Messages.Identity_29);
					lbl_subj.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

					subjectInput = new Text(actionGroup_1, SWT.BORDER);
					subjectInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
					subjectInput.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							txtExplain.setText(EXPLAIN_ENCRYPT);
						}
					});

					label = new Label(actionGroup_1, SWT.NONE);
					label.setText(Messages.Identity_30);
					GridData gd_label = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
					gd_label.verticalIndent = 20;
					label.setLayoutData(gd_label);

					label = new Label(actionGroup_1, SWT.WRAP);
					label.setText(Messages.Identity_31);
					label.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));

					clearMessage = new Text(actionGroup_1, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
					GridData gd_clearMessage = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
					gd_clearMessage.minimumHeight = 100;
					clearMessage.setLayoutData(gd_clearMessage);
					clearMessage.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							changeButtonVisibility();
							if (messageRecipient.getItemCount() == 0) {
								addRecipientsToCombo();
							}
							txtExplain.setText(EXPLAIN_ENCRYPT);
						}

					});
					encryptedMessage = new Text(actionGroup_1,
							SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
					GridData gd_encryptedMessage = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
					gd_encryptedMessage.minimumHeight = 100;
					encryptedMessage.setLayoutData(gd_encryptedMessage);
					encryptedMessage.setFont(new Font(getDisplay(), "Courier", 10, SWT.NONE));

					label = new Label(actionGroup_1, SWT.NONE);
					label.setText(Messages.Identity_32);
					GridData gd_label2 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
					gd_label2.verticalIndent = 20;
					label.setLayoutData(gd_label2);

					messageRecipient = new Combo(actionGroup_1, SWT.READ_ONLY);
					messageRecipient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					messageRecipient.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeButtonVisibility();
							fillRecipientKeys();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					new Label(actionGroup_1, SWT.NONE);

					label = new Label(actionGroup_1, SWT.NONE);
					label.setText(Messages.Identity_34);
					GridData gd_label_3 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
					gd_label_3.verticalIndent = 20;
					label.setLayoutData(gd_label_3);

					recipientKeys = new Combo(actionGroup_1, SWT.READ_ONLY);
					recipientKeys.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					recipientKeys.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeButtonVisibility();
							pubKeyParameters = iMgr.getPublicKeyParameters(rec.get(recipientKeys.getText()));
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					new Label(actionGroup_1, SWT.NONE);

					encryptMessage = new Button(actionGroup_1, SWT.PUSH);
					GridData gd_encryptMessage = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
					gd_encryptMessage.verticalIndent = 20;
					encryptMessage.setLayoutData(gd_encryptMessage);
					encryptMessage.setText(Messages.Identity_35);
					encryptMessage.setEnabled(false);
					encryptMessage.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							encryptedMessage.setText(rsa_impl.encrypt(clearMessage.getText(), pubKeyParameters.get(1),
									pubKeyParameters.get(0)));
							sendMessage.setEnabled(true);
						}
					});

					sendMessage = new Button(actionGroup_1, SWT.PUSH);
					GridData gd_sendMessage = new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 2, 1);
					gd_sendMessage.verticalIndent = 20;
					sendMessage.setLayoutData(gd_sendMessage);
					sendMessage.setText(Messages.Identity_33);
					sendMessage.setEnabled(false);
					sendMessage.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							int keyID = Integer
									.parseInt(recipientKeys.getItem(recipientKeys.getSelectionIndex()).substring(
											recipientKeys.getItem(recipientKeys.getSelectionIndex()).indexOf(':') + 1));

							extTF.addMessageToQueue(
									new SecureMessage(encryptedMessage.getText(), keyID, Identity.this.identityName,
											rec.get(recipientKeys.getText()), subjectInput.getText()));
							encryptedMessage.setText(NOTHING);
							subjectInput.setText(NOTHING);
							clearMessage.setText(NOTHING);

							txtExplain.setText(EXPLAIN_SENDED);

							recipientKeys.removeAll();
							messageRecipient.removeAll();
							encryptMessage.setEnabled(false);
							sendMessage.setEnabled(false);
						}
					});
					generalGroup.layout();
					generalGroup.redraw();
					sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

					forerunner = 1;
				}
			}
		});

		receive_and_decrypt = new Button(composite, SWT.PUSH);
		receive_and_decrypt.addSelectionListener(new SelectionAdapter() {
			@Override
			// Button 2
			public void widgetSelected(SelectionEvent e) {
				if (forerunner != 2) {
					txtExplain.setText(EXPLAIN_DECRYPT);
					
					actionGroup_1.dispose();
					actionGroup_2.dispose();
					actionGroup_3.dispose();
					actionGroup_4.dispose();
					actionGroup_5.dispose();

					createActionGroup2();
					actionGroup_2.setText(Messages.Identity_44);

					initActions2 = new Label(actionGroup_2, SWT.NONE);
					initActions2.setText(Messages.Identity_37);
					initActions2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					infolabel_tab2 = new Label(actionGroup_2, SWT.WRAP);
					infolabel_tab2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					infolabel_tab2.setText(NOTHING);
					infolabel_tab2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));

					selectMessage = new Combo(actionGroup_2, SWT.READ_ONLY);
					selectMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					selectMessage.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeButtonVisibilityTab2();

							SecureMessage currentMsg = extTF.getMessageWithID(Integer.parseInt(
									selectMessage.getText().substring(selectMessage.getText().lastIndexOf(' ') + 1)));

							// display the message in hex
							encryptedMessage_Tab2.setText(currentMsg.getEncryptedMessage());

							HashMap<String, KeyStoreAlias> privKeys = iMgr.getPrivateKeys(Identity.this.identityName);

							decryptionKeys.setEnabled(true);
							decryptionKeys.setItems(privKeys.keySet().toArray(new String[privKeys.size()]));

							// select the "correct" key
							String hashCurrent = currentMsg.getRecipient().getHashValue();
							int count = 0;
							for (KeyStoreAlias ksa : privKeys.values()) {
								if (!ksa.getHashValue().equals(hashCurrent)) {
									count++;
								} else {
									decryptionKeys.select(count);
									privateAlias = privKeys.get(decryptionKeys.getText());
								}
							}

							if (identityName.equals(ALICE) || identityName.equals(BOB) || identityName.contains(":")) {
								lbl_pwWrong.setText(Messages.Identity_180);
							}

							pwPrivKey.setEnabled(true);
							pwPrivKey.setText(NOTHING);
							decryptMessage.setEnabled(false);
							deleteMessage.setEnabled(false);
							decryptedMessage.setText(NOTHING);
							txtExplain.setText(EXPLAIN_DECRYPT);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					label = new Label(actionGroup_2, SWT.WRAP);
					label.setText(Messages.Identity_38);
					label.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));

					label = new Label(actionGroup_2, SWT.NONE);
					label.setText(Messages.Identity_39);
					GridData gd_label2 = new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1);
					gd_label2.verticalIndent = 20;
					label.setLayoutData(gd_label2);

					encryptedMessage_Tab2 = new Text(actionGroup_2, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
					GridData gd_encryptedMessage_Tab2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
					gd_encryptedMessage_Tab2.minimumHeight = 100;
					encryptedMessage_Tab2.setLayoutData(gd_encryptedMessage_Tab2);
					encryptedMessage_Tab2.setFont(new Font(getDisplay(), "Courier", 10, SWT.NONE));
					encryptedMessage_Tab2.addVerifyListener(VL_HEX);
					encryptedMessage_Tab2.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							changeButtonVisibilityTab2();
						}
					});

					decryptedMessage = new Text(actionGroup_2,
							SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
					GridData gd_decryptedMessage = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
					gd_decryptedMessage.minimumHeight = 100;
					decryptedMessage.setLayoutData(gd_decryptedMessage);

					label = new Label(actionGroup_2, SWT.NONE);
					label.setText(Messages.Identity_40);
					GridData gd_label3 = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
					gd_label3.verticalIndent = 20;
					label.setLayoutData(gd_label3);

					decryptionKeys = new Combo(actionGroup_2, SWT.READ_ONLY);
					decryptionKeys.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					decryptionKeys.setEnabled(false);
					decryptionKeys.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeButtonVisibilityTab2();
							pwPrivKey.setText(NOTHING);

							HashMap<String, KeyStoreAlias> privKeys = iMgr.getPrivateKeys(Identity.this.identityName);
							privateAlias = privKeys.get(decryptionKeys.getText());
							pwPrivKey.setText(NOTHING);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					new Label(actionGroup_2, SWT.NONE);

					label = new Label(actionGroup_2, SWT.NONE);
					label.setText(Messages.Identity_42);
					GridData gd_label4 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
					gd_label4.verticalIndent = 20;
					label.setLayoutData(gd_label4);

					lbl_pwWrong = new Label(actionGroup_2, SWT.WRAP);
					lbl_pwWrong.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
					lbl_pwWrong.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3));

					pwPrivKey = new Text(actionGroup_2, SWT.BORDER | SWT.PASSWORD);
					pwPrivKey.setEnabled(false);
					pwPrivKey.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							changeButtonVisibilityTab2();
						}
					});
					pwPrivKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

					decryptMessage = new Button(actionGroup_2, SWT.PUSH);
					GridData gd_decryptMessage = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
					gd_decryptMessage.verticalIndent = 20;
					decryptMessage.setLayoutData(gd_decryptMessage);
					decryptMessage.setText(Messages.Identity_43);
					decryptMessage.setEnabled(false);
					decryptMessage.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							SecureMessage currentMsg = extTF.getMessageWithID(Integer.parseInt(
									selectMessage.getText().substring(selectMessage.getText().lastIndexOf(' ') + 1)));

							if (currentMsg.getRecipient().getContactName().equals(Identity.this.identityName)) {
								deleteMessage.setEnabled(true);
							}

							RSAPrivateCrtKey privkey = iMgr.getPrivateKey(privateAlias, pwPrivKey.getText());
							if (privkey == null) {
								// can't catch the 'java.security.UnrecoverableKeyException'
								lbl_pwWrong.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
								lbl_pwWrong.setText(PW_WRONG);
							} else {
								lbl_pwWrong.setText(NOTHING);

								Vector<BigInteger> privKeyValues = iMgr.getPrivateKeyParametersRSA(privkey);

								// we need d (position 0) and N (position 1)
								decryptedMessage.setText(rsa_impl.decrypt(encryptedMessage_Tab2.getText(),
										privKeyValues.get(1), privKeyValues.get(0)));
							}

						}
					});

					deleteMessage = new Button(actionGroup_2, SWT.PUSH);
					deleteMessage.setText(Messages.Identity_41);
					deleteMessage.setEnabled(false);
					deleteMessage.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(final SelectionEvent e) {
							extTF.deleteMessageWithID(Integer.parseInt(
									selectMessage.getText().substring(selectMessage.getText().lastIndexOf(' ') + 1)));

							txtExplain.setText(EXPLAIN_DELETED);

							selectMessage.removeAll();
							fillSelectMessage();

							encryptedMessage_Tab2.setText(NOTHING);
							decryptionKeys.removeAll();
							pwPrivKey.setText(NOTHING);
							pwPrivKey.setEnabled(false);
							decryptedMessage.setText(NOTHING);
							decryptMessage.setEnabled(false);
							deleteMessage.setEnabled(false);
						}
					});
					GridData gd_deleteMessagem = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1);
					gd_deleteMessagem.verticalIndent = 20;
					deleteMessage.setLayoutData(gd_deleteMessagem);

					fillSelectMessage();

					generalGroup.layout();
					generalGroup.redraw();
					sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

					forerunner = 2;
				}
			}
		});
		receive_and_decrypt.setText(Messages.Identity_44);
		receive_and_decrypt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		keymanagement = new Button(composite, SWT.PUSH);
		keymanagement.addSelectionListener(new SelectionAdapter() {
			@Override
			// Button 3
			public void widgetSelected(SelectionEvent e) {
				if (forerunner != 3) {
					txtExplain.setText(EXPLAIN_KEYMGMT_TAB1);

					actionGroup_1.dispose();
					actionGroup_2.dispose();
					actionGroup_3.dispose();
					actionGroup_4.dispose();
					actionGroup_5.dispose();
					
					createActionGroup3();
					actionGroup_3.setText(Messages.Identity_129);

					bi_rsaE = null;
					bi_rsaP = null;
					bi_rsaQ = null;

					tf_keyMgmt = new TabFolder(actionGroup_3, SWT.NONE);
					tf_keyMgmt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					tf_keyMgmt.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							resetRSAValues();

							if (tf_keyMgmt.getSelectionIndex() == 0) {
								txtExplain.setText(EXPLAIN_KEYMGMT_TAB1);
								eIsValid = false;
								if (password1 != null && !password1.isDisposed()) {
									password1.setEnabled(false);
									password1.setText(NOTHING);
								}
								if (password2 != null && !password2.isDisposed()) {
									password2.setEnabled(false);
									password2.setText(NOTHING);
								}
								if (pickRandomE != null && !pickRandomE.isDisposed()) {
									pickRandomE.setEnabled(false);
								}
								if (combo_rsaE != null && !combo_rsaE.isDisposed()) {
									combo_rsaE.setEnabled(false);
								}
								if (pickRandomExtE != null && combo_ExrsaE != null && !pickRandomExtE.isDisposed()
										&& !combo_ExrsaE.isDisposed()) {
									pickRandomExtE.setEnabled(false);
									combo_ExrsaE.setEnabled(false);
								}
								if (errorLabel_1 != null && !errorLabel_1.isDisposed()) {
									errorLabel_1.setText(NOTHING);
								}
							}
							if (tf_keyMgmt.getSelectionIndex() == 1) {
								txtExplain.setText(EXPLAIN_KEYMGMT_TAB2);
								ext_password1.setText(NOTHING);
								ext_password2.setText(NOTHING);
								lbl_notification_tab2.setText(NOTHING);
							}
							if (tf_keyMgmt.getSelectionIndex() == 2) {
								txtExplain.setText(EXPLAIN_KEYMGMT_TAB3);

								allKeys_keydata = iMgr
										.loadAllKeysForIdentityAndOtherPublics(Identity.this.identityName);
								selectedKey_Keydata
										.setItems(allKeys_keydata.keySet().toArray(new String[allKeys_keydata.size()]));

								if (selectedKey_Keydata.getItemCount() == 0) {
									wrongPW_keydata.setText(Messages.Identity_126);
									selectedKey_Keydata.setEnabled(false);
								}

								keyData.removeAll();
								lbl_enterPW.setVisible(false);
								password_keydata.setVisible(false);
							}
							actionGroup_3.layout();
							actionGroup_3.redraw();
							sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					// Tab "New Key"
					keyMgmt_1 = new TabItem(tf_keyMgmt, SWT.NONE);
					keyMgmt_1.setText(Messages.Identity_45);
					tab1 = new Composite(tf_keyMgmt, SWT.NONE);
					tab1.setLayout(new GridLayout(1, false));
					keyMgmt_1.setControl(tab1);

					init_tab1 = new Label(tab1, SWT.WRAP);
					init_tab1.setText(ENTER_TWO_PRIMES);
					init_tab1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					errorLabel_1 = new Label(tab1, SWT.WRAP);
					errorLabel_1.setFont(FontService.getNormalBoldFont());
					errorLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					errorLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					radio_RSA = new Button(tab1, SWT.RADIO);
					radio_RSA.setText(Messages.Identity_46);
					radio_RSA.setSelection(true);
					radio_RSA.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
					radio_RSA.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeRSAVisibility();
							init_tab1.setText(ENTER_TWO_PRIMES);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					// composite for the labels and dropdows
					rsaComposite = new Composite(tab1, SWT.NONE);
					rsaComposite.setLayout(new GridLayout(4, false));
					rsaComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					Label rsaP = new Label(rsaComposite, SWT.NONE);
					rsaP.setText(Messages.Identity_47);
					rsaP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

					combo_rsaP = new Combo(rsaComposite, SWT.NONE);
					fillPrimesTo(combo_rsaP);
					combo_rsaP.addModifyListener(ml);
					combo_rsaP.addVerifyListener(VL);
					combo_rsaP.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// check if p and q are primes and e is valid
							bi_rsaP = new BigInteger(combo_rsaP.getItem(combo_rsaP.getSelectionIndex()));
							combo_rsaE.setText(NOTHING);
							bi_rsaE = null;
							stopUpdateE = false;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_rsaP.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_rsaP.getText().length() > 0) {
								bi_rsaP = new BigInteger(combo_rsaP.getText());
								bi_rsaE = null;
								combo_rsaE.setText(NOTHING);
								stopUpdateE = false;
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					combo_rsaP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

					Label rsaQ = new Label(rsaComposite, SWT.NONE);
					rsaQ.setText(Messages.Identity_48);
					GridData gd_rsaQ = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
					gd_rsaQ.horizontalIndent = 20;
					rsaQ.setLayoutData(gd_rsaQ);

					combo_rsaQ = new Combo(rsaComposite, SWT.NONE);
					fillPrimesTo(combo_rsaQ);
					combo_rsaQ.addModifyListener(ml);
					combo_rsaQ.addVerifyListener(VL);
					combo_rsaQ.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_rsaQ.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// check if p and q are primes and e is valid
							bi_rsaQ = new BigInteger(combo_rsaQ.getItem(combo_rsaQ.getSelectionIndex()));
							combo_rsaE.setText(NOTHING);
							bi_rsaE = null;
							stopUpdateE = false;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_rsaQ.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_rsaQ.getText().length() > 0) {
								bi_rsaQ = new BigInteger(combo_rsaQ.getText());
								combo_rsaE.setText(NOTHING);
								bi_rsaE = null;
								stopUpdateE = false;
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					Label rsaE = new Label(rsaComposite, SWT.NONE);
					rsaE.setText(Messages.Identity_49);
					rsaE.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

					combo_rsaE = new Combo(rsaComposite, SWT.NONE);
					combo_rsaE.setEnabled(false);
					combo_rsaE.addVerifyListener(VL);
					combo_rsaE.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_rsaE.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_rsaE.getText().length() > 0) {
								bi_rsaE = new BigInteger(combo_rsaE.getText());
								stopUpdateE = true;
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					combo_rsaE.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_rsaE = new BigInteger(combo_rsaE.getItem(combo_rsaE.getSelectionIndex()));
							stopUpdateE = true;
							eIsValid = true;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					pickRandomE = new Button(rsaComposite, SWT.PUSH);
					pickRandomE.setText(Messages.Identity_50);
					pickRandomE.setEnabled(false);
					pickRandomE.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
					pickRandomE.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_rsaE = new BigInteger(possibleEs.get((int) (Math.random() * possibleEs.size())));
							combo_rsaE.setText(bi_rsaE.toString());
							combo_rsaE.setText(bi_rsaE.toString());
							eIsValid = true;
							errorLabel_1.setText(NOTHING);
							stopUpdateE = true;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					radio_ExtRSA = new Button(tab1, SWT.RADIO);
					radio_ExtRSA.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeRSAVisibility();
							init_tab1.setText(ENTER_THREE_PRIMES);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					GridData gd_radio_ExtRSA = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gd_radio_ExtRSA.verticalIndent = 20;
					radio_ExtRSA.setLayoutData(gd_radio_ExtRSA);
					radio_ExtRSA.setText(Messages.Identity_51);

					// composite for the labels and dropdows
					rsaExMainComposite = new Composite(tab1, SWT.NONE);
					rsaExMainComposite.setLayout(new GridLayout(1, false));
					rsaExMainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					rsaExComposite1 = new Composite(rsaExMainComposite, SWT.NONE);
					rsaExComposite1.setLayout(new GridLayout(2, false));
					rsaExComposite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					Label rsa_ex_Anz = new Label(rsaExComposite1, SWT.NONE);
					rsa_ex_Anz.setText(Messages.Identity_52);
					rsa_ex_Anz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					numberOfPrimesExRSA = new Combo(rsaExComposite1, SWT.READ_ONLY);
					numberOfPrimesExRSA.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
					numberOfPrimesExRSA.setEnabled(false);
					numberOfPrimesExRSA.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							int value = Integer
									.parseInt(numberOfPrimesExRSA.getItem(numberOfPrimesExRSA.getSelectionIndex()));
							switch (value) {
							case 3:
								combo_ExrsaS.setVisible(false);
								combo_ExrsaT.setVisible(false);
								rsa_ex_S.setVisible(false);
								rsa_ex_T.setVisible(false);
								init_tab1.setText(ENTER_THREE_PRIMES);
								additionalChanges();
								break;

							case 4:
								combo_ExrsaS.setVisible(true);
								combo_ExrsaT.setVisible(false);
								rsa_ex_S.setVisible(true);
								rsa_ex_T.setVisible(false);
								init_tab1.setText(ENTER_FOUR_PRIMES);
								additionalChanges();
								break;

							case 5:
								combo_ExrsaS.setVisible(true);
								combo_ExrsaT.setVisible(true);
								rsa_ex_S.setVisible(true);
								rsa_ex_T.setVisible(true);
								init_tab1.setText(ENTER_FIVE_PRIMES);
								additionalChanges();
								break;
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					for (int i = 3; i < 6; i++) {
						numberOfPrimesExRSA.add(NOTHING + i);
					}
					numberOfPrimesExRSA.select(0);

					rsaExComposite2 = new Composite(rsaExMainComposite, SWT.NONE);
					rsaExComposite2.setLayout(new GridLayout(10, false));
					rsaExComposite2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					// P
					Label rsa_ex_P = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_P.setText(Messages.Identity_53);
					rsa_ex_P.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					combo_ExrsaP = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_ExrsaP.addVerifyListener(VL);
					combo_ExrsaP.setEnabled(false);
					fillPrimesTo(combo_ExrsaP);
					combo_ExrsaP.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaP = new BigInteger(combo_ExrsaP.getItem(combo_ExrsaP.getSelectionIndex()));
							resetMPInputs();
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_ExrsaP.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaP.getText().length() > 0) {
								bi_ExtrsaP = new BigInteger(combo_ExrsaP.getText());
								resetMPInputs();
								checkParameter();
							} else {
								bi_ExtrsaE = null;
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					// Q
					Label rsa_ex_Q = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_Q.setText(Messages.Identity_54);
					GridData gd_rsa_ex_Q = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
					gd_rsa_ex_Q.horizontalIndent = 20;
					rsa_ex_Q.setLayoutData(gd_rsa_ex_Q);

					combo_ExrsaQ = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaQ.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_ExrsaQ.addVerifyListener(VL);
					combo_ExrsaQ.setEnabled(false);
					fillPrimesTo(combo_ExrsaQ);
					combo_ExrsaQ.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaQ = new BigInteger(combo_ExrsaQ.getItem(combo_ExrsaQ.getSelectionIndex()));
							resetMPInputs();
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_ExrsaQ.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaQ.getText().length() > 0) {
								bi_ExtrsaQ = new BigInteger(combo_ExrsaQ.getText());
								resetMPInputs();
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					// R
					Label rsa_ex_R = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_R.setText(Messages.Identity_55);
					GridData gd_rsa_ex_R = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
					gd_rsa_ex_R.horizontalIndent = 20;
					rsa_ex_R.setLayoutData(gd_rsa_ex_R);

					combo_ExrsaR = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaR.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_ExrsaR.addVerifyListener(VL);
					combo_ExrsaR.setEnabled(false);
					fillPrimesTo(combo_ExrsaR);
					combo_ExrsaR.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaR = new BigInteger(combo_ExrsaR.getItem(combo_ExrsaR.getSelectionIndex()));
							resetMPInputs();
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_ExrsaR.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaR.getText().length() > 0) {
								bi_ExtrsaR = new BigInteger(combo_ExrsaR.getText());
								resetMPInputs();
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					// S
					rsa_ex_S = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_S.setText(Messages.Identity_56);
					GridData gd_rsa_ex_S = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
					gd_rsa_ex_S.horizontalIndent = 20;
					rsa_ex_S.setLayoutData(gd_rsa_ex_S);
					rsa_ex_S.setVisible(false);

					combo_ExrsaS = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaS.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_ExrsaS.addVerifyListener(VL);
					combo_ExrsaS.setEnabled(false);
					combo_ExrsaS.setVisible(false);
					fillPrimesTo(combo_ExrsaS);
					combo_ExrsaS.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaS = new BigInteger(combo_ExrsaS.getItem(combo_ExrsaS.getSelectionIndex()));
							resetMPInputs();
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_ExrsaS.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaS.getText().length() > 0) {
								bi_ExtrsaS = new BigInteger(combo_ExrsaS.getText());
								resetMPInputs();
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					// T
					rsa_ex_T = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_T.setText(Messages.Identity_57);
					GridData gd_rsa_ex_T = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
					gd_rsa_ex_T.horizontalIndent = 20;
					rsa_ex_T.setLayoutData(gd_rsa_ex_T);
					rsa_ex_T.setVisible(false);

					combo_ExrsaT = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaT.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
					combo_ExrsaT.addVerifyListener(VL);
					combo_ExrsaT.setEnabled(false);
					combo_ExrsaT.setVisible(false);
					fillPrimesTo(combo_ExrsaT);
					combo_ExrsaT.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaT = new BigInteger(combo_ExrsaT.getItem(combo_ExrsaT.getSelectionIndex()));
							resetMPInputs();
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					combo_ExrsaT.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaT.getText().length() > 0) {
								bi_ExtrsaT = new BigInteger(combo_ExrsaT.getText());
								resetMPInputs();
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					// E
					Label rsa_ex_E = new Label(rsaExComposite2, SWT.NONE);
					rsa_ex_E.setText(Messages.Identity_58);
					rsa_ex_E.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					combo_ExrsaE = new Combo(rsaExComposite2, SWT.NONE);
					combo_ExrsaE.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					combo_ExrsaE.addVerifyListener(VL);
					combo_ExrsaE.setEnabled(false);
					combo_ExrsaE.addKeyListener(new KeyListener() {
						@Override
						public void keyReleased(KeyEvent e) {
							if (combo_ExrsaE.getText().length() > 0) {
								bi_ExtrsaE = new BigInteger(combo_ExrsaE.getText());
								stopUpdateE = true;
								checkParameter();
							}
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});
					combo_ExrsaE.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaE = new BigInteger(combo_ExrsaE.getItem(combo_ExrsaE.getSelectionIndex()));
							stopUpdateE = true;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					pickRandomExtE = new Button(rsaExComposite2, SWT.PUSH);
					pickRandomExtE.setText(Messages.Identity_59);
					pickRandomExtE.setEnabled(false);
					pickRandomExtE.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 8, 1));
					pickRandomExtE.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							bi_ExtrsaE = new BigInteger(possibleEs.get((int) (Math.random() * possibleEs.size())));
							combo_ExrsaE.setText(bi_ExtrsaE.toString());
							eIsValid = true;
							errorLabel_1.setText(NOTHING);
							stopUpdateE = true;
							checkParameter();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					new Label(rsaExMainComposite, SWT.SEPARATOR | SWT.HORIZONTAL)
							.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 10, 1));

					rsaExComposite3 = new Composite(rsaExMainComposite, SWT.NONE);
					rsaExComposite3.setLayout(new GridLayout(2, true));
					rsaExComposite3.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));

					// enter password
					Label rsa_password = new Label(rsaExComposite3, SWT.NONE);
					rsa_password.setText(Messages.Identity_60);
					rsa_password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					password1 = new Text(rsaExComposite3, SWT.PASSWORD | SWT.BORDER);
					password1.setEnabled(false);
					password1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					password1.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							pw1 = password1.getText();
							checkPasswords();
						}
					});

					// enter password again
					Label rsa_password2 = new Label(rsaExComposite3, SWT.NONE);
					rsa_password2.setText(Messages.Identity_61);
					rsa_password2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					password2 = new Text(rsaExComposite3, SWT.PASSWORD | SWT.BORDER);
					password2.setEnabled(false);
					password2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					password2.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							pw2 = password2.getText();
							checkPasswords();
						}
					});

					createKey = new Button(rsaExComposite3, SWT.PUSH);
					createKey.setText(Messages.Identity_62);
					createKey.setEnabled(false);
					createKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
					createKey.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							if (radio_RSA.getSelection()) {
								bi_rsaD = bi_rsaE.modInverse(bi_rsaPhi);

								errorLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								if (bi_rsaD.toString().length() < 40) {
									errorLabel_1.setText(Messages.Identity_63 + bi_rsaD + Messages.Identity_64);
									tab1.layout();
								} else {
									errorLabel_1.setText(Messages.Identity_65);
									tab1.layout();
								}

								iMgr.saveRSAKeyToKeystore(Identity.this.identityName, password1.getText(), null,
										bi_rsaN, bi_rsaP, bi_rsaQ, bi_rsaE, bi_rsaD);

								fillPrimesTo(combo_rsaP);
								fillPrimesTo(combo_rsaQ);
								combo_rsaE.removeAll();
								combo_rsaE.setEnabled(false);
								pickRandomE.setEnabled(false);
							} else {
								bi_ExtrsaD = bi_ExtrsaE.modInverse(bi_ExtrsaPhi);
								errorLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								if (bi_ExtrsaD.toString().length() < 40) {
									errorLabel_1.setText(Messages.Identity_169 + bi_ExtrsaD + Messages.Identity_64);
									tab1.layout();
								} else {
									errorLabel_1.setText(Messages.Identity_170);
									tab1.layout();
								}

								iMgr.saveMpRSAKeyToKeystore(Identity.this.identityName, password1.getText(), null,
										validCount, bi_ExtrsaN, bi_ExtrsaP, bi_ExtrsaQ, bi_ExtrsaR, bi_ExtrsaS,
										bi_ExtrsaT, bi_ExtrsaE, bi_ExtrsaD);

								fillPrimesTo(combo_ExrsaP);
								fillPrimesTo(combo_ExrsaQ);
								fillPrimesTo(combo_ExrsaR);
								fillPrimesTo(combo_ExrsaS);
								fillPrimesTo(combo_ExrsaT);
								combo_ExrsaE.removeAll();
								combo_ExrsaE.setEnabled(false);
								pickRandomExtE.setEnabled(false);
							}

							password1.setText(NOTHING);
							password1.setEnabled(false);
							password2.setText(NOTHING);
							password2.setEnabled(false);
							eIsValid = false;
							resetRSAValues();
							createKey.setEnabled(false);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
});

					// Tab "New Key (extended)"
					keyMgmt_2 = new TabItem(tf_keyMgmt, SWT.NONE);
					keyMgmt_2.setText(Messages.Identity_66);
					tab2 = new Composite(tf_keyMgmt, SWT.NONE);
					tab2.setLayout(new GridLayout(1, false));
					keyMgmt_2.setControl(tab2);

					Label lbl_init_tab2 = new Label(tab2, SWT.WRAP);
					lbl_init_tab2.setText(TAB2_INIT);
					lbl_init_tab2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					lbl_notification_tab2 = new Label(tab2, SWT.WRAP);
					lbl_notification_tab2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					radio_RSA_tab2 = new Button(tab2, SWT.RADIO);
					GridData gd_radio_RSA_tab2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gd_radio_RSA_tab2.verticalIndent = 20;
					radio_RSA_tab2.setLayoutData(gd_radio_RSA_tab2);
					radio_RSA_tab2.setText(Messages.Identity_67);
					radio_RSA_tab2.setSelection(true);
					radio_RSA_tab2.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeRSAVisibility_Tab2();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					Composite rsaComp = new Composite(tab2, SWT.NONE);
					rsaComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
					rsaComp.setLayout(new GridLayout(2, false));

					Label rsa_label = new Label(rsaComp, SWT.NONE);
					rsa_label.setText(Messages.Identity_68);
					rsa_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					rsa_length = new Combo(rsaComp, SWT.READ_ONLY);
					rsa_length.add(Messages.Identity_69);
					rsa_length.add(Messages.Identity_70);
					rsa_length.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							ext_password1.setText(NOTHING);
							ext_password2.setText(NOTHING);
							lbl_notification_tab2.setText(NOTHING);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					rsa_length.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					rsa_length.select(0);

					radio_ExtRSA_tab2 = new Button(tab2, SWT.RADIO);
					GridData gd_radio_ExtRSA_tab2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
					gd_radio_ExtRSA_tab2.verticalIndent = 20;
					radio_ExtRSA_tab2.setLayoutData(gd_radio_ExtRSA_tab2);
					radio_ExtRSA_tab2.setText(Messages.Identity_71);
					radio_ExtRSA_tab2.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							changeRSAVisibility_Tab2();
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					Composite rsaExtComp = new Composite(tab2, SWT.NONE);
					rsaExtComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
					rsaExtComp.setLayout(new GridLayout(2, false));

					Label rsa_extLabel = new Label(rsaExtComp, SWT.NONE);
					rsa_extLabel.setText(Messages.Identity_72);

					extRsa_numberPrimes_tab2 = new Combo(rsaExtComp, SWT.READ_ONLY);
					extRsa_numberPrimes_tab2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					extRsa_numberPrimes_tab2.setEnabled(false);
					for (int i = 3; i < 6; i++) {
						extRsa_numberPrimes_tab2.add(NOTHING + i);
					}
					extRsa_numberPrimes_tab2.select(0);
					extRsa_numberPrimes_tab2.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							ext_password1.setText(NOTHING);
							ext_password2.setText(NOTHING);
							lbl_notification_tab2.setText(NOTHING);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					Label rsa_extlength = new Label(rsaExtComp, SWT.NONE);
					rsa_extlength.setText(Messages.Identity_73);
					rsa_extlength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					extRsa_length = new Combo(rsaExtComp, SWT.READ_ONLY);
					extRsa_length.add(Messages.Identity_74);
					extRsa_length.add(Messages.Identity_75);
					extRsa_length.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							ext_password1.setText(NOTHING);
							ext_password2.setText(NOTHING);
							lbl_notification_tab2.setText(NOTHING);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
					extRsa_length.select(0);
					extRsa_length.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
					extRsa_length.setEnabled(false);

					new Label(tab2, SWT.SEPARATOR | SWT.HORIZONTAL)
							.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					rsaExComposite3 = new Composite(tab2, SWT.NONE);
					rsaExComposite3.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1));
					rsaExComposite3.setLayout(new GridLayout(2, true));

					// enter password
					Label rsa_password_2 = new Label(rsaExComposite3, SWT.NONE);
					rsa_password_2.setText(Messages.Identity_76);
					rsa_password2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					ext_password1 = new Text(rsaExComposite3, SWT.PASSWORD | SWT.BORDER);
					ext_password1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					ext_password1.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							pw1_Ext = ext_password1.getText();
							checkPasswords();
						}
					});

					// enter password again
					Label rsa_password2_2 = new Label(rsaExComposite3, SWT.NONE);
					rsa_password2_2.setText(Messages.Identity_77);
					rsa_password2_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					ext_password2 = new Text(rsaExComposite3, SWT.PASSWORD | SWT.BORDER);
					ext_password2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					ext_password2.addModifyListener(new ModifyListener() {

						@Override
						public void modifyText(ModifyEvent e) {
							pw2_Ext = ext_password2.getText();
							checkPasswords();
						}
					});

					createKey_Tab2 = new Button(rsaExComposite3, SWT.PUSH);
					createKey_Tab2.setText(Messages.Identity_78);
					createKey_Tab2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
					createKey_Tab2.setEnabled(false);
					createKey_Tab2.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							lbl_notification_tab2.setFont(FontService.getNormalBoldFont());

							if (radio_ExtRSA_tab2.getSelection()) {
								iMgr.createMpRSAIdentity(Identity.this.identityName, ext_password1.getText(),
										Integer.parseInt(extRsa_length.getItem(extRsa_length.getSelectionIndex())),
										Integer.parseInt(extRsa_numberPrimes_tab2
												.getItem(extRsa_numberPrimes_tab2.getSelectionIndex())));
								lbl_notification_tab2.setText(Messages.Identity_170);
								tab2.layout();
							} else {
								iMgr.createIdentity(Identity.this.identityName, Messages.Identity_79,
										ext_password1.getText(),
										Integer.parseInt(rsa_length.getItem(rsa_length.getSelectionIndex())));
								lbl_notification_tab2.setText(Messages.Identity_80);
								tab2.layout();
							}
							createKey_Tab2.setEnabled(false);
							ext_password1.setText(NOTHING);
							ext_password2.setText(NOTHING);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					// Tab "My Keys"
					keyMgmt_3 = new TabItem(tf_keyMgmt, SWT.NONE);
					keyMgmt_3.setText(Messages.Identity_81);
					tab3 = new Composite(tf_keyMgmt, SWT.NONE);
					tab3.setLayout(new GridLayout(1, false));
					keyMgmt_3.setControl(tab3);

					Label lbl_init_tab3 = new Label(tab3, SWT.WRAP);
					lbl_init_tab3.setText(TAB3_INIT);
					lbl_init_tab3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					Composite myKeyData = new Composite(tab3, SWT.NONE);
					myKeyData.setLayout(new GridLayout(3, false));
					myKeyData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

					// combo "select key"
					Label lbl_selectKey = new Label(myKeyData, SWT.WRAP);
					lbl_selectKey.setText(Messages.Identity_82);
					lbl_selectKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					selectedKey_Keydata = new Combo(myKeyData, SWT.READ_ONLY);
					selectedKey_Keydata.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

					selectedKey_Keydata.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							keyData.removeAll();
							clipboardtext.setVisible(false);

							if (selectedKey_Keydata.getItem(selectedKey_Keydata.getSelectionIndex())
									.contains(Messages.Identity_83)) {
								showKeydata.setEnabled(true);
								password_keydata.setVisible(false);
								lbl_enterPW.setVisible(false);
								password_keydata.setText(NOTHING);
								wrongPW_keydata.setText(NOTHING);
								isPubKey = true;
							} else {

								showKeydata.setEnabled(false);
								password_keydata.setVisible(true);
								lbl_enterPW.setVisible(true);
								password_keydata.setText(NOTHING);
								isPubKey = false;
								// if key belongs to alice or bob, or is cracked - show password hint
								String comboText = selectedKey_Keydata.getText();
								if (comboText.contains(":") || comboText.contains(ALICE) || comboText.contains(BOB)) {
									wrongPW_keydata.setFont(FontService.getNormalFont());
									wrongPW_keydata.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
									wrongPW_keydata.setText(Messages.Identity_180);

									actionGroup_3.layout();
									actionGroup_3.redraw();
									sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

								} else {
									wrongPW_keydata.setText(NOTHING);
								}
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					// button "show keydata"
					showKeydata = new Button(myKeyData, SWT.PUSH);
					showKeydata.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
					showKeydata.setText(Messages.Identity_84);
					showKeydata.setEnabled(false);
					showKeydata.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							clipboardtext.setVisible(true);
							keyData.removeAll();
							Vector<String> descriptions = null;
							Vector<String> values = null;
							Vector<String> rawValues = null;
							if (isPubKey) {
								descriptions = new Vector<String>(Arrays.asList(Messages.Identity_85,
										Messages.Identity_86, Messages.Identity_87, Messages.Identity_88));
								values = iMgr
										.getAllRSAPubKeyParameters(allKeys_keydata.get(selectedKey_Keydata.getText()));
							} else {
								String keyAlgorithm;

								if (selectedKey_Keydata.getText().contains(":")) {
									String part1 = selectedKey_Keydata.getText()
											.substring(selectedKey_Keydata.getText().indexOf("Bit - ") + 6);
									keyAlgorithm = part1.substring(0, part1.indexOf(' ')).trim();
								} else {
									keyAlgorithm = selectedKey_Keydata.getText()
											.substring(selectedKey_Keydata.getText().lastIndexOf('-') + 1).trim();
								}

								if (keyAlgorithm.startsWith(Messages.Identity_89)) {
									descriptions = new Vector<String>(Arrays.asList(Messages.Identity_90,
											Messages.Identity_91, Messages.Identity_92, Messages.Identity_93,
											Messages.Identity_94, Messages.Identity_95, Messages.Identity_96));
									values = iMgr.getAllRSAPrivKeyParameters(
											allKeys_keydata.get(selectedKey_Keydata.getText()),
											password_keydata.getText());
								} else {
									// mp-rsa key
									rawValues = iMgr.getAllMpRSAPrivKeyParameters(
											allKeys_keydata.get(selectedKey_Keydata.getText()),
											password_keydata.getText());
									String str_otherPrimeinfo = rawValues.get(rawValues.size() - 4);
									String[] split_opi = str_otherPrimeinfo.split(" ");

									values = new Vector<String>();
									for (int i = 0; i < 4; i++) {
										values.add(rawValues.get(i));
									}

									switch (split_opi.length) {
									case 1: {
										descriptions = new Vector<String>(Arrays.asList(Messages.Identity_98,
												Messages.Identity_99, Messages.Identity_100, Messages.Identity_101,
												Messages.Identity_102, Messages.Identity_103, Messages.Identity_104,
												Messages.Identity_105));
										values.add(split_opi[0]);
										break;
									}

									case 2: {
										descriptions = new Vector<String>(Arrays.asList(Messages.Identity_106,
												Messages.Identity_107, Messages.Identity_108, Messages.Identity_109,
												Messages.Identity_110, Messages.Identity_111, Messages.Identity_112,
												Messages.Identity_113, Messages.Identity_114));
										values.add(split_opi[0]);
										values.add(split_opi[1]);
										break;
									}

									case 3: {
										descriptions = new Vector<String>(Arrays.asList(Messages.Identity_115,
												Messages.Identity_116, Messages.Identity_117, Messages.Identity_118,
												Messages.Identity_119, Messages.Identity_120, Messages.Identity_121,
												Messages.Identity_122, Messages.Identity_123, Messages.Identity_124));
										values.add(split_opi[0]);
										values.add(split_opi[1]);
										values.add(split_opi[2]);
										break;
									}
									}

									for (int i = rawValues.size() - 3; i < rawValues.size(); i++) {
										values.add(rawValues.get(i));
									}
								}
							}
							if (values.size() == 0) {
								wrongPW_keydata.setFont(FontService.getNormalBoldFont());
								wrongPW_keydata.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
								wrongPW_keydata.setText(PW_WRONG);
							} else {
								wrongPW_keydata.setText(NOTHING);
								for (int i = 0; i < descriptions.size(); i++) {
									TableItem ti = new TableItem(keyData, SWT.NONE);
									ti.setText(new String[] { descriptions.get(i), values.get(i) });

								}
								column_value.setWidth((values.get(values.size() - 1).length() * 8) + 100);
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					// textfield "enter password"
					lbl_enterPW = new Label(myKeyData, SWT.NONE);
					lbl_enterPW.setText(Messages.Identity_125);
					lbl_enterPW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
					lbl_enterPW.setVisible(false);

					password_keydata = new Text(myKeyData, SWT.PASSWORD | SWT.BORDER);
					password_keydata.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
					password_keydata.setVisible(false);
					password_keydata.addKeyListener(new KeyListener() {

						@Override
						public void keyReleased(KeyEvent e) {
							showKeydata.setEnabled(true);
						}

						@Override
						public void keyPressed(KeyEvent e) {
						}
					});

					new Label(myKeyData, SWT.NONE)
							.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

					wrongPW_keydata = new Label(myKeyData, SWT.WRAP);
					wrongPW_keydata.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
					wrongPW_keydata.setFont(FontService.getNormalBoldFont());
					wrongPW_keydata.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

					new Label(tab3, SWT.SEPARATOR | SWT.HORIZONTAL)
							.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

					clipboardtext = new Label(tab3, SWT.WRAP);
					clipboardtext.setText(CLIPBOARDTEXT_TEXT);
					clipboardtext.setVisible(false);
					clipboardtext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

					keyData = new Table(tab3, SWT.BORDER | SWT.FULL_SELECTION);
					GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
					gd_table.heightHint = 200;
					keyData.setLayoutData(gd_table);
					keyData.setHeaderVisible(true);
					keyData.setLinesVisible(true);
					keyData.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							// copy the selected value to the clipboard
							String selectedValue = keyData.getItem(keyData.getSelectionIndex()).getText(1);
							final Clipboard cb = new Clipboard(extTF.getDisplay());
							TextTransfer textTransfer = TextTransfer.getInstance();
							cb.setContents(new Object[] { selectedValue }, new Transfer[] { textTransfer });
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					column_parameter = new TableColumn(keyData, SWT.NONE);
					column_parameter.setWidth(150);
					column_parameter.setText(Messages.Identity_127);

					column_value = new TableColumn(keyData, SWT.NONE);
					column_value.setWidth(300);
					column_value.setText(Messages.Identity_128);

					generalGroup.layout();
					generalGroup.redraw();
					sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

					forerunner = 3;
				}
			}
		});

		keymanagement.setText(Messages.Identity_129);
		keymanagement.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		attackPublicKey = new Button(composite, SWT.PUSH);
		attackPublicKey.addSelectionListener(new SelectionAdapter() {
			@Override
			// Button 4
			public void widgetSelected(SelectionEvent e) {
				if (forerunner != 4) {
					txtExplain.setText(EXPLAIN_ATTACK_PUBKEY);

					actionGroup_1.dispose();
					actionGroup_2.dispose();
					actionGroup_3.dispose();
					actionGroup_4.dispose();
					actionGroup_5.dispose();
					
					createActionGroup4();
					actionGroup_4.setText(Messages.Identity_137);

					lbl_noKeyToAttack = new Label(actionGroup_4, SWT.WRAP);
					lbl_noKeyToAttack.setFont(FontService.getNormalBoldFont());
					lbl_noKeyToAttack.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					lbl_noKeyToAttack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

					Label l = new Label(actionGroup_4, SWT.NONE);
					l.setText(Messages.Identity_130);
					l.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

					keyToAttack = new Combo(actionGroup_4, SWT.READ_ONLY);
					keyToAttack.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
					attackableKeys = iMgr.getAttackablePublicKeys(Identity.this.identityName);
					if (attackableKeys.size() == 0) {
						keyToAttack.setEnabled(false);
						lbl_noKeyToAttack.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
						lbl_noKeyToAttack.setText(NO_KEY_TO_ATTACK);
						actionGroup_4.layout();
					}
					keyToAttack.setItems(attackableKeys.keySet().toArray(new String[attackableKeys.size()]));
					keyToAttack.addSelectionListener(new SelectionListener() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (keyToAttack.getSelectionIndex() != -1 && attackableKeys.size() > 0) {
								attackKey.setEnabled(true);
								Vector<BigInteger> actualKey = iMgr
										.getPublicKeyParameters(attackableKeys.get(keyToAttack.getText()));

								String name = keyToAttack.getText().substring(0,
										keyToAttack.getText().indexOf('-') - 1);
								attack_hint.setText(Messages.Identity_131 + name + " " + Messages.Identity_132 + " "
										+ actualKey.get(0).bitLength() + " " + Messages.Identity_133);
								keydataN.setVisible(true);
								keydataN.setText(Messages.Identity_173 + actualKey.get(0));
								attack_success.setText(NOTHING);
								keyData_attacked.setVisible(false);

								reconstructKey.setEnabled(false);
								reconstructKey.setVisible(false);
								lbl_noKeyToAttack.setText(NOTHING);

								actionGroup_4.layout();
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					attackKey = new Button(actionGroup_4, SWT.PUSH);
					attackKey.setText(Messages.Identity_134);
					attackKey.setEnabled(false);
					attackKey.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
					attackKey.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							BigInteger publicN = null;
							BigInteger publicE = null;
							Vector<String> pubKeyParameter = iMgr
									.getAllRSAPubKeyParameters(attackableKeys.get(keyToAttack.getText()));
							if (pubKeyParameter.size() > 0) {
								publicN = new BigInteger(pubKeyParameter.get(pubKeyParameter.size() - 1));
								publicE = new BigInteger(pubKeyParameter.get(pubKeyParameter.size() - 2));
								factorizePubKey(publicN, publicE);

							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					reconstructKey = new Button(actionGroup_4, SWT.PUSH);
					reconstructKey.setText(Messages.Identity_176);
					reconstructKey.setEnabled(false);
					reconstructKey.setVisible(false);
					reconstructKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
					reconstructKey.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							TableItem[] allTableItems = keyData_attacked.getItems();
							lbl_noKeyToAttack.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

							if (allTableItems.length < 5) {
								// create RSA-key
								BigInteger rec_p = new BigInteger(allTableItems[0].getText(1));
								BigInteger rec_q = new BigInteger(allTableItems[1].getText(1));
								BigInteger rec_N = rec_p.multiply(rec_q);
								BigInteger rec_e = new BigInteger(allTableItems[2].getText(1));
								BigInteger rec_d = new BigInteger(allTableItems[3].getText(1));
								iMgr.saveRSAKeyToKeystore(
										keyToAttack.getText().substring(0, keyToAttack.getText().indexOf('-') - 1),
										"1234", identityName, rec_N, rec_p, rec_q, rec_e, rec_d);
								lbl_noKeyToAttack.setText(Messages.Identity_177);
							} else {
								// create MpRSA key
								BigInteger rec_p = new BigInteger(allTableItems[0].getText(1));
								BigInteger rec_q = new BigInteger(allTableItems[1].getText(1));
								BigInteger rec_r = new BigInteger(allTableItems[2].getText(1));

								switch (allTableItems.length) {
								case 5: {
									BigInteger rec_N = rec_p.multiply(rec_q).multiply(rec_r);
									BigInteger rec_e = new BigInteger(allTableItems[3].getText(1));
									BigInteger rec_d = new BigInteger(allTableItems[4].getText(1));
									iMgr.saveMpRSAKeyToKeystore(
											keyToAttack.getText().substring(0, keyToAttack.getText().indexOf('-') - 1),
											"1234", identityName, 3, rec_N, rec_p, rec_q, rec_r, BigInteger.ZERO,
											BigInteger.ZERO, rec_e, rec_d);
									break;
								}
								case 6: {
									BigInteger rec_s = new BigInteger(allTableItems[3].getText(1));
									BigInteger rec_N = rec_p.multiply(rec_q).multiply(rec_r).multiply(rec_s);
									BigInteger rec_e = new BigInteger(allTableItems[4].getText(1));
									BigInteger rec_d = new BigInteger(allTableItems[5].getText(1));
									iMgr.saveMpRSAKeyToKeystore(
											keyToAttack.getText().substring(0, keyToAttack.getText().indexOf('-') - 1),
											"1234", identityName, 4, rec_N, rec_p, rec_q, rec_r, rec_s, BigInteger.ZERO,
											rec_e, rec_d);
									break;
								}
								case 7: {
									BigInteger rec_s = new BigInteger(allTableItems[3].getText(1));
									BigInteger rec_t = new BigInteger(allTableItems[4].getText(1));
									BigInteger rec_N = rec_p.multiply(rec_q).multiply(rec_r).multiply(rec_s)
											.multiply(rec_t);
									BigInteger rec_e = new BigInteger(allTableItems[5].getText(1));
									BigInteger rec_d = new BigInteger(allTableItems[6].getText(1));
									iMgr.saveMpRSAKeyToKeystore(
											keyToAttack.getText().substring(0, keyToAttack.getText().indexOf('-') - 1),
											"1234", identityName, 5, rec_N, rec_p, rec_q, rec_r, rec_s, rec_t, rec_e,
											rec_d);
									break;
								}
								}
								lbl_noKeyToAttack.setText(Messages.Identity_178);
								actionGroup_4.layout();

							}

							keyData_attacked.setVisible(false);
							attackKey.setEnabled(false);
							attack_hint.setText(NOTHING);
							reconstructKey.setVisible(false);
							attack_success.setVisible(false);
							keydataN.setText(NOTHING);
							keydataN.setVisible(false);
							keyToAttack.removeAll();
							attackableKeys = iMgr.getAttackablePublicKeys(Identity.this.identityName);
							if (attackableKeys.size() == 0) {
								keyToAttack.setEnabled(false);
								lbl_noKeyToAttack.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
								lbl_noKeyToAttack.setText(NO_KEY_TO_ATTACK);
								actionGroup_4.layout();
							}
							keyToAttack.setItems(attackableKeys.keySet().toArray(new String[attackableKeys.size()]));

						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});

					attack_hint = new Label(actionGroup_4, SWT.WRAP);
					attack_hint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

					keydataN = new Text(actionGroup_4, SWT.V_SCROLL | SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
					keydataN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
					keydataN.setVisible(false);

					attack_success = new Label(actionGroup_4, SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
					attack_success.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

					keyData_attacked = new Table(actionGroup_4, SWT.BORDER | SWT.FULL_SELECTION);
					GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
					gd_table.heightHint = 180;
					keyData_attacked.setLayoutData(gd_table);
					keyData_attacked.setHeaderVisible(true);
					keyData_attacked.setLinesVisible(true);
					keyData_attacked.setVisible(false);

					column_parameter_attacked = new TableColumn(keyData_attacked, SWT.NONE);
					column_parameter_attacked.setWidth(100);
					column_parameter_attacked.setText(Messages.Identity_135);

					column_parameter_attacked = new TableColumn(keyData_attacked, SWT.NONE);
					column_parameter_attacked.setWidth(500);
					column_parameter_attacked.setText(Messages.Identity_136);

					generalGroup.layout();
					generalGroup.redraw();
					sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

					forerunner = 4;
				}
			}
		});
		attackPublicKey.setText(Messages.Identity_137);
		attackPublicKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		createActionGroup1();
		actionGroup_1.dispose();

		createActionGroup2();
		actionGroup_2.dispose();

		createActionGroup3();
		actionGroup_3.dispose();

		createActionGroup4();
		actionGroup_4.dispose();

		//This is the page that is displayed at the beginning.
		createActionGroup5();
		
		generalGroup.layout();
		generalGroup.redraw();
		sc_identity.setMinSize(generalGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * method to factorize a public key
	 * 
	 * @param n
	 *            the modulus
	 * @param e
	 *            the public exponent
	 */
	private void factorizePubKey(final BigInteger n, final BigInteger e) {
		new Thread() {

			private BigDecimal getNextPrime(final BigDecimal start) {
				BigDecimal prime = start.add(BigDecimal.ONE);
				boolean isPrime = false;
				while (!isPrime) {
					if (!Lib.isPrime(prime.toBigInteger())) {
						prime = prime.add(BigDecimal.ONE);
					} else {
						isPrime = true;
					}
				}
				return prime;
			}

			@Override
			public void run() {
				boolean finish = false;
				final double start = System.currentTimeMillis();
				final Vector<BigInteger> divisors = new Vector<BigInteger>();
				BigDecimal divisor = new BigDecimal(3);
				BigDecimal decN = new BigDecimal(n);
				BigInteger result = BigInteger.ONE;
				boolean check = false;

				while (!finish) {
					BigDecimal[] erg = decN.divideAndRemainder(divisor);
					if (erg[1].intValue() == 0) {
						divisors.add(divisor.toBigInteger());
						divisor = getNextPrime(divisor);
						check = true;
					} else {
						divisor = getNextPrime(divisor);
						check = false;
					}

					if (divisors.size() > 1 && check) {
						result = BigInteger.ONE;
						for (int i = 0; i < divisors.size(); i++) {
							result = result.multiply(divisors.get(i));
						}
						if (result.equals(decN.toBigInteger())) {
							finish = true;
						}
						check = false;
					}

					if (finish) {
						new UIJob(Messages.Identity_138) {
							@Override
							public IStatus runInUIThread(IProgressMonitor monitor) {
								keyData_attacked.setVisible(true);
								keyData_attacked.removeAll();
								double timeNeeded = (System.currentTimeMillis() - start) / 1000;
								attack_success
										.setText(Messages.Identity_139 + timeNeeded + " " + Messages.Identity_172);
								keyData_attacked.setVisible(true);
								reconstructKey.setEnabled(true);
								reconstructKey.setVisible(true);
								actionGroup_4.layout();
								
								TableItem ti_p;
								TableItem ti_q;
								TableItem ti_r;
								TableItem ti_s;
								TableItem ti_t;
								BigInteger phi = BigInteger.ONE;

								switch (divisors.size()) {
								case 2:
									ti_p = new TableItem(keyData_attacked, SWT.NONE);
									ti_p.setText(new String[] { Messages.Identity_140, NOTHING + divisors.get(0) });
									ti_q = new TableItem(keyData_attacked, SWT.NONE);
									ti_q.setText(new String[] { Messages.Identity_141, NOTHING + divisors.get(1) });
									phi = divisors.get(0).subtract(BigInteger.ONE)
											.multiply(divisors.get(1).subtract(BigInteger.ONE));
									break;

								case 3:
									ti_p = new TableItem(keyData_attacked, SWT.NONE);
									ti_p.setText(new String[] { Messages.Identity_142, NOTHING + divisors.get(0) });
									ti_q = new TableItem(keyData_attacked, SWT.NONE);
									ti_q.setText(new String[] { Messages.Identity_143, NOTHING + divisors.get(1) });
									ti_r = new TableItem(keyData_attacked, SWT.NONE);
									ti_r.setText(new String[] { Messages.Identity_144, NOTHING + divisors.get(2) });
									phi = divisors.get(0).subtract(BigInteger.ONE)
											.multiply(divisors.get(1).subtract(BigInteger.ONE))
											.multiply(divisors.get(2).subtract(BigInteger.ONE));
									break;

								case 4:
									ti_p = new TableItem(keyData_attacked, SWT.NONE);
									ti_p.setText(new String[] { Messages.Identity_145, NOTHING + divisors.get(0) });
									ti_q = new TableItem(keyData_attacked, SWT.NONE);
									ti_q.setText(new String[] { Messages.Identity_146, NOTHING + divisors.get(1) });
									ti_r = new TableItem(keyData_attacked, SWT.NONE);
									ti_r.setText(new String[] { Messages.Identity_147, NOTHING + divisors.get(2) });
									ti_s = new TableItem(keyData_attacked, SWT.NONE);
									ti_s.setText(new String[] { Messages.Identity_148, NOTHING + divisors.get(3) });
									phi = divisors.get(0).subtract(BigInteger.ONE)
											.multiply(divisors.get(1).subtract(BigInteger.ONE))
											.multiply(divisors.get(2).subtract(BigInteger.ONE))
											.multiply(divisors.get(3).subtract(BigInteger.ONE));
									break;

								case 5:
									ti_p = new TableItem(keyData_attacked, SWT.NONE);
									ti_p.setText(new String[] { Messages.Identity_149, NOTHING + divisors.get(0) });
									ti_q = new TableItem(keyData_attacked, SWT.NONE);
									ti_q.setText(new String[] { Messages.Identity_150, NOTHING + divisors.get(1) });
									ti_r = new TableItem(keyData_attacked, SWT.NONE);
									ti_r.setText(new String[] { Messages.Identity_151, NOTHING + divisors.get(2) });
									ti_s = new TableItem(keyData_attacked, SWT.NONE);
									ti_s.setText(new String[] { Messages.Identity_152, NOTHING + divisors.get(3) });
									ti_t = new TableItem(keyData_attacked, SWT.NONE);
									ti_t.setText(new String[] { Messages.Identity_153, NOTHING + divisors.get(4) });
									phi = divisors.get(0).subtract(BigInteger.ONE)
											.multiply(divisors.get(1).subtract(BigInteger.ONE))
											.multiply(divisors.get(2).subtract(BigInteger.ONE))
											.multiply(divisors.get(3).subtract(BigInteger.ONE))
											.multiply(divisors.get(4).subtract(BigInteger.ONE));
									break;
								}
								TableItem ti_e = new TableItem(keyData_attacked, SWT.NONE);
								ti_e.setText(new String[] { Messages.Identity_154, NOTHING + e });

								TableItem ti_d = new TableItem(keyData_attacked, SWT.NONE);
								ti_d.setText(new String[] { Messages.Identity_155, NOTHING + e.modInverse(phi) });
								return Status.OK_STATUS;
							}
						}.schedule();

					}
				}
			}
		}.start();
	}

	private void additionalChanges() {
		password1.setText(NOTHING);
		password2.setText(NOTHING);
		password1.setEnabled(false);
		password2.setEnabled(false);
		createKey.setEnabled(false);
		combo_ExrsaE.removeAll();
		pickRandomExtE.setEnabled(false);
		combo_ExrsaE.setEnabled(false);
		errorLabel_1.setText(NOTHING);
		resetRSAValues();
	}

	private void fillRecipientKeys() {
		if (!messageRecipient.getText().equals(NOTHING)) {
			rec = iMgr.getPublicKeys(messageRecipient.getText());
			recipientKeys.setItems(rec.keySet().toArray(new String[rec.size()]));
			recipientKeys.select(0);
			pubKeyParameters = iMgr.getPublicKeyParameters(rec.get(recipientKeys.getText()));
		}
	}

	private void fillSelectMessage() {
		// show all messages
		for (SecureMessage sec : extTF.getMessageQueue()) {
			// if (sec.getRecipient().getContactName().equals(identityName)){
			String subject = NOTHING;
			if (sec.getSubject().isEmpty()) {
				subject = Messages.Identity_159;
			} else {
				subject = sec.getSubject();
			}
			String message = NOTHING + subject + HYPHEN + FROM + sec.getSender() + HYPHEN + TO
					+ sec.getRecipient().getContactName() + HYPHEN + sec.getMessageID();
			selectMessage.add(message);
			// }
		}
		if (selectMessage.getItemCount() == 0) {
			infolabel_tab2.setText(NO_ENCRYPTED_MESSAGES);
			selectMessage.setEnabled(false);
			pwPrivKey.setEnabled(false);
			decryptionKeys.setEnabled(false);
			encryptedMessage_Tab2.setEnabled(false);
			decryptedMessage.setEnabled(false);
		}
	}

	private void changeButtonVisibilityTab2() {
		if ((selectMessage.getSelectionIndex() != -1) && (encryptedMessage_Tab2.getText().length() > 1)
				&& (decryptionKeys.getSelectionIndex() != -1) && (pwPrivKey.getText().length() > 0)) {
			decryptMessage.setEnabled(true);
		}
	}

	private void changeButtonVisibility() {
		if ((clearMessage.getText().length() > 0) && (messageRecipient.getSelectionIndex() != -1)) {
			encryptMessage.setEnabled(true);
		}
	}

	private void checkPasswords() {
		if (pw1 != null && pw2 != null) {
			if (pw1.equals(pw2) && eIsValid) {
				createKey.setEnabled(true);
			} else {
				createKey.setEnabled(false);
			}
		}
		if (pw1_Ext != null && pw2_Ext != null) {
			if (pw1_Ext.equals(pw2_Ext) && (pw1_Ext.length() > 0 && pw2_Ext.length() > 0)) {
				createKey_Tab2.setEnabled(true);
			} else {
				createKey_Tab2.setEnabled(false);
			}
		}

	}

	private void changeRSAVisibility_Tab2() {
		if (radio_RSA_tab2.getSelection()) {
			// Radiobutton "RSA" is activated
			rsa_length.setEnabled(true);
			extRsa_length.setEnabled(false);
			extRsa_numberPrimes_tab2.setEnabled(false);

		} else {
			// Radiobutton "Multi-prime RSA" is activated
			rsa_length.setEnabled(false);
			extRsa_length.setEnabled(true);
			extRsa_numberPrimes_tab2.setEnabled(true);
		}
		lbl_notification_tab2.setText(NOTHING);
		createKey_Tab2.setEnabled(false);
		ext_password1.setText(NOTHING);
		ext_password2.setText(NOTHING);
	}

	private void deactivateSave() {
		password1.setText(NOTHING);
		password2.setText(NOTHING);
		password1.setEnabled(false);
		password2.setEnabled(false);
		createKey.setEnabled(false);
		pickRandomE.setEnabled(false);
		combo_rsaE.setEnabled(false);
		combo_rsaE.setText(NOTHING);
		eIsValid = false;
		bi_rsaE = null;
	}

	private void changeRSAVisibility() {
		if (radio_RSA.getSelection()) {
			// Radiobutton "RSA" is activated

			pickRandomExtE.setEnabled(false);
			combo_ExrsaE.setEnabled(false);
			eIsValid = false;

			combo_rsaP.setEnabled(true);
			combo_rsaQ.setEnabled(true);
			combo_ExrsaP.setEnabled(false);
			combo_ExrsaQ.setEnabled(false);
			combo_ExrsaR.setEnabled(false);
			combo_ExrsaS.setEnabled(false);
			combo_ExrsaT.setEnabled(false);

			combo_ExrsaS.setVisible(false);
			combo_ExrsaT.setVisible(false);
			rsa_ex_S.setVisible(false);
			rsa_ex_T.setVisible(false);

			numberOfPrimesExRSA.select(0);
			numberOfPrimesExRSA.setEnabled(false);

			combo_rsaE.removeAll();

		} else {
			// Radiobutton "Multi-prime RSA" is activated
			pickRandomE.setEnabled(false);
			combo_rsaE.setEnabled(false);
			eIsValid = false;

			numberOfPrimesExRSA.setEnabled(true);
			numberOfPrimesExRSA.select(0);
			combo_rsaE.setEnabled(false);
			combo_rsaP.setEnabled(false);
			combo_rsaQ.setEnabled(false);
			combo_ExrsaP.setEnabled(true);
			combo_ExrsaQ.setEnabled(true);
			combo_ExrsaR.setEnabled(true);
			combo_ExrsaS.setEnabled(true);
			combo_ExrsaT.setEnabled(true);

			combo_ExrsaS.setVisible(false);
			combo_ExrsaT.setVisible(false);
			rsa_ex_S.setVisible(false);
			rsa_ex_T.setVisible(false);
			combo_ExrsaE.removeAll();
		}
		password1.setText(NOTHING);
		password2.setText(NOTHING);
		password1.setEnabled(false);
		password2.setEnabled(false);
		createKey.setEnabled(false);

		errorLabel_1.setText(NOTHING);
		txtExplain.setText(EXPLAIN_KEYMGMT_TAB1);
		resetRSAValues();
	}

	private void resetMPInputs() {
		bi_ExtrsaE = null;
		combo_ExrsaE.setText(NOTHING);
		password1.setEnabled(false);
		password2.setEnabled(false);
		password1.setText(NOTHING);
		password2.setText(NOTHING);
		pickRandomExtE.setEnabled(false);
		combo_ExrsaE.setEnabled(false);
		stopUpdateE = false;
	}

	private void resetRSAValues() {
		bi_rsaE = null;
		bi_rsaP = null;
		bi_rsaQ = null;
		bi_rsaN = null;
		bi_rsaPhi = null;
		bi_ExtrsaP = null;
		bi_ExtrsaQ = null;
		bi_ExtrsaR = null;
		bi_ExtrsaS = null;
		bi_ExtrsaT = null;
		bi_ExtrsaE = null;
		bi_ExtrsaN = null;
		bi_ExtrsaPhi = null;

		if (combo_rsaE != null && !combo_rsaE.isDisposed()) {
			combo_rsaE.setText(NOTHING);
		}
		if (combo_rsaP != null && !combo_rsaP.isDisposed()) {
			combo_rsaP.setText(NOTHING);
		}
		if (combo_rsaQ != null && !combo_rsaQ.isDisposed()) {
			combo_rsaQ.setText(NOTHING);
		}
		if (combo_ExrsaP != null && !combo_ExrsaP.isDisposed()) {
			combo_ExrsaP.setText(NOTHING);
		}
		if (combo_ExrsaQ != null && !combo_ExrsaQ.isDisposed()) {
			combo_ExrsaQ.setText(NOTHING);
		}
		if (combo_ExrsaR != null && !combo_ExrsaR.isDisposed()) {
			combo_ExrsaR.setText(NOTHING);
		}
		if (combo_ExrsaS != null && !combo_ExrsaS.isDisposed()) {
			combo_ExrsaS.setText(NOTHING);
		}
		if (combo_ExrsaT != null && !combo_ExrsaT.isDisposed()) {
			combo_ExrsaT.setText(NOTHING);
		}
		if (combo_ExrsaE != null && !combo_ExrsaT.isDisposed()) {
			combo_ExrsaE.setText(NOTHING);
		}
	}

	/**
	 * get all primes for a possible 'e'.
	 * 
	 * @return 6542 prime numbers from 3 to 65537
	 */
	private Vector<BigInteger> getEPrimes() {
		BigInteger start = new BigInteger(Messages.Identity_160);
		BigInteger end = new BigInteger(Messages.Identity_161);
		Vector<BigInteger> ePrimes = new Vector<BigInteger>();
		while (start.compareTo(end) <= 0) {
			if (!Lib.isPrime(start)) {
				start = start.add(BigInteger.ONE);
			} else {
				ePrimes.add(start);
				start = start.add(BigInteger.ONE);
			}
		}
		return ePrimes;
	}

	private void addRecipientsToCombo() {
		Vector<String> recipients = new Vector<String>();
		if (messageRecipient != null) {
			messageRecipient.removeAll();
		}

		for (String s : iMgr.getContacts()) {
			// if (!s.equals(this.identityName) && !recipients.contains(s)){
			if (!recipients.contains(s)) {
				if (IdentityManager.getInstance().countOwnKeys(s) > 0) {
					recipients.add(s);
				}
			}
		}
		messageRecipient.setItems(recipients.toArray(new String[recipients.size()]));
	}

	/**
	 * fills all primes into the given combo item.
	 * 
	 * @param combo
	 *            the list from which a prime can be selected
	 */
	private void fillPrimesTo(final Combo combo) {
		combo.removeAll();
		for (Integer i : LOW_PRIMES) {
			combo.add(i.toString());
		}
	}

	private void createActionGroup1() {
		actionGroup_1 = new Group(generalGroup, SWT.NONE);
		actionGroup_1.setText(Messages.Identity_162);
		actionGroup_1.setLayout(new GridLayout(2, true));
		gd_actionGroup_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_actionGroup_1.widthHint = 700;
		actionGroup_1.setLayoutData(gd_actionGroup_1);
	}

	private void createActionGroup2() {
		actionGroup_2 = new Group(generalGroup, SWT.NONE);
		actionGroup_2.setLayout(new GridLayout(2, true));
		gd_actionGroup_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_actionGroup_2.widthHint = 700;
		actionGroup_2.setLayoutData(gd_actionGroup_2);
	}

	private void createActionGroup3() {
		actionGroup_3 = new Group(generalGroup, SWT.NONE);
		actionGroup_3.setLayout(new GridLayout());
		gd_actionGroup_3 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_actionGroup_3.widthHint = 700;
		actionGroup_3.setLayoutData(gd_actionGroup_3);
	}

	private void createActionGroup4() {
		actionGroup_4 = new Group(generalGroup, SWT.NONE);
		actionGroup_4.setLayout(new GridLayout(2, false));
		gd_actionGroup_4 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_actionGroup_4.widthHint = 700;
		actionGroup_4.setLayoutData(gd_actionGroup_4);
	}
	
	private void createActionGroup5() {
		actionGroup_5 = new Group(generalGroup, SWT.NONE);
		actionGroup_5.setText(Messages.Identity_162);
		actionGroup_5.setLayout(new GridLayout());
		gd_actionGroup_5 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_actionGroup_5.widthHint = 700;
		actionGroup_5.setLayoutData(gd_actionGroup_5);

		initActions = new Label(actionGroup_5, SWT.WRAP);
		initActions.setText(Messages.Identity_163 + identityName + Messages.Identity_164);
		initActions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		txtExplain.setText(EXPLAIN_INIT);
	}

	public final String getIdentityName() {
		return identityName;
	}

	public final void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public final String getForename() {
		return forename;
	}

	public final void setForename(final String forename) {
		this.forename = forename;
	}

	public final String getSurname() {
		return surname;
	}

	public final void setSurname(final String surname) {
		this.surname = surname;
	}

	public final String getOrganisation() {
		return organisation;
	}

	public final void setOrganisation(final String organisation) {
		this.organisation = organisation;
	}

	public final String getRegion() {
		return region;
	}

	public final void setRegion(final String region) {
		this.region = region;
	}

	public int getId() {
		return id;
	}

	@Override
	protected void checkSubclass() {
	}

	/**
	 * main check-method for the entered parameters
	 */
	private void checkParameter() {
		pIsPrime = false;
		qIsPrime = false;
		rIsPrime = false;
		sIsPrime = false;
		tIsPrime = false;
		eIsValid = false;
		BigInteger minimum = new BigInteger(Messages.Identity_168);

		errorLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		if (radio_RSA.getSelection()) {
			if (bi_rsaP != null) {
				if (!bi_rsaP.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_rsaP)) {
					errorLabel_1.setText(NO_PRIME_P);
					combo_rsaE.removeAll();
					pIsPrime = false;
					deactivateSave();
				} else if (bi_rsaP.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					pIsPrime = true;
				}
			}
			if (bi_rsaQ != null) {
				if (!bi_rsaQ.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_rsaQ)) {
					errorLabel_1.setText(NO_PRIME_Q);
					combo_rsaE.removeAll();
					qIsPrime = false;
					deactivateSave();
				} else if (bi_rsaQ.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					qIsPrime = true;
				}
			}
			// show only the error-message again, because the message will be overwritten in
			// the code above
			if (qIsPrime && !pIsPrime && bi_rsaP != null) {
				errorLabel_1.setText(NO_PRIME_P);
			}
			if (!pIsPrime && !qIsPrime) {
				combo_rsaE.setText(NOTHING);
			}
			if (pIsPrime && qIsPrime) {
				if (!bi_rsaP.equals(bi_rsaQ)) {
					bi_rsaN = bi_rsaP.multiply(bi_rsaQ);
					bi_rsaPhi = Lib.calcPhi(bi_rsaP, bi_rsaQ);
					pickRandomE.setEnabled(true);

					if (!stopUpdateE) {
						combo_rsaE.setEnabled(true);
						combo_rsaE.removeAll();
						fillE();
					}

					if (bi_rsaE != null) {
						BigInteger gcd = bi_rsaE.gcd(bi_rsaPhi);
						if (!possibleEs.contains(bi_rsaE.toString())) {
							errorLabel_1.setText(NO_VALID_E);
							password1.setText(NOTHING);
							password2.setText(NOTHING);
							createKey.setEnabled(false);
							eIsValid = false;
						} else if (gcd.intValue() != 1) {
							errorLabel_1.setText(NO_VALID_GcdE);
							password1.setText(NOTHING);
							password2.setText(NOTHING);
							createKey.setEnabled(false);
							eIsValid = false;
						} else {
							errorLabel_1.setText(NOTHING);
							eIsValid = true;
						}
					}

				} else {
					bi_rsaN = Constants.MINUS_ONE;
					bi_rsaPhi = Constants.MINUS_ONE;
					combo_rsaE.removeAll();
					errorLabel_1.setText(PRIMES_EQUAL);
					pickRandomE.setEnabled(false);
					combo_rsaE.setEnabled(false);
				}

				password1.setEnabled(eIsValid);
				password2.setEnabled(eIsValid);
			}
		} else {
			// check the multi-prime RSA parameters
			bi_ExtrsaN = Constants.MINUS_ONE;
			bi_ExtrsaPhi = Constants.MINUS_ONE;

			if (bi_ExtrsaP != null) {
				if (!bi_ExtrsaP.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaP)) {
					errorLabel_1.setText(NO_PRIME_P);
					pIsPrime = false;
					disableSomeExRSAParams();
				} else if (bi_ExtrsaP.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					pIsPrime = true;
				}
			}
			if (bi_ExtrsaQ != null) {
				if (!bi_ExtrsaQ.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaQ)) {
					errorLabel_1.setText(NO_PRIME_Q);
					qIsPrime = false;
					disableSomeExRSAParams();
				} else if (bi_ExtrsaQ.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					qIsPrime = true;
				}
			}
			if (bi_ExtrsaR != null) {
				if (!bi_ExtrsaR.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaR)) {
					errorLabel_1.setText(NO_PRIME_R);
					rIsPrime = false;
					disableSomeExRSAParams();
				} else if (bi_ExtrsaR.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					rIsPrime = true;
				}
			}
			if (bi_ExtrsaS != null) {
				if (!bi_ExtrsaS.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaS)) {
					errorLabel_1.setText(NO_PRIME_S);
					sIsPrime = false;
					disableSomeExRSAParams();
				} else if (bi_ExtrsaS.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					sIsPrime = true;
				}
			}
			if (bi_ExtrsaT != null) {
				if (!bi_ExtrsaT.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaT)) {
					errorLabel_1.setText(NO_PRIME_T);
					tIsPrime = false;
					disableSomeExRSAParams();
				} else if (bi_ExtrsaT.compareTo(minimum) < 0) {
					errorLabel_1.setText(VALUE_TOO_SMALL);
				} else {
					errorLabel_1.setText(NOTHING);
					tIsPrime = true;
				}
			}

			// check belated changes and show only the message.. the rest is done in the
			// methods above
			if (!bi_ExtrsaP.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaP)
					&& combo_ExrsaP.getText().length() > 0) {
				errorLabel_1.setText(NO_PRIME_P);
			} else if (bi_ExtrsaP != null && bi_ExtrsaP.compareTo(minimum) < 0) {
				errorLabel_1.setText(VALUE_TOO_SMALL);
			}

			if (bi_ExtrsaQ != null && !bi_ExtrsaQ.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaQ)
					&& combo_ExrsaQ.getText().length() > 0) {
				errorLabel_1.setText(NO_PRIME_Q);
			} else if (bi_ExtrsaQ != null && bi_ExtrsaQ.compareTo(minimum) < 0) {
				errorLabel_1.setText(VALUE_TOO_SMALL);
			}

			if (bi_ExtrsaR != null && !bi_ExtrsaR.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaR)
					&& combo_ExrsaR.getText().length() > 0) {
				errorLabel_1.setText(NO_PRIME_R);
			} else if (bi_ExtrsaR != null && bi_ExtrsaR.compareTo(minimum) < 0) {
				errorLabel_1.setText(VALUE_TOO_SMALL);
			}

			if (bi_ExtrsaS != null && !bi_ExtrsaS.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaS)
					&& combo_ExrsaS.getText().length() > 0) {
				errorLabel_1.setText(NO_PRIME_S);
			} else if (bi_ExtrsaS != null && bi_ExtrsaS.compareTo(minimum) < 0) {
				errorLabel_1.setText(VALUE_TOO_SMALL);
			}

			if (bi_ExtrsaT != null && !bi_ExtrsaT.equals(Constants.MINUS_ONE) && !Lib.isPrime(bi_ExtrsaT)
					&& combo_ExrsaT.getText().length() > 0) {
				errorLabel_1.setText(NO_PRIME_T);
			} else if (bi_ExtrsaT != null && bi_ExtrsaT.compareTo(minimum) < 0) {
				errorLabel_1.setText(VALUE_TOO_SMALL);
			}

			// check, if different primes are chosen
			validCount = 0;
			TreeSet<BigInteger> checkPrimes = new TreeSet<BigInteger>();
			if (bi_ExtrsaP != null && pIsPrime) {
				checkPrimes.add(bi_ExtrsaP);
				validCount++;
			} else {
				combo_ExrsaE.removeAll();
			}

			if (bi_ExtrsaQ != null && qIsPrime) {
				if (checkPrimes.contains(bi_ExtrsaQ)) {
					errorLabel_1.setText(PRIMES_EQUAL);
					resetMPInputs();
					combo_ExrsaE.removeAll();
				} else {
					checkPrimes.add(bi_ExtrsaQ);
					validCount++;
				}
			}

			if (bi_ExtrsaR != null && rIsPrime) {
				if (checkPrimes.contains(bi_ExtrsaR)) {
					errorLabel_1.setText(PRIMES_EQUAL);
					resetMPInputs();
					combo_ExrsaE.removeAll();
				} else {
					checkPrimes.add(bi_ExtrsaR);
					validCount++;
				}
			}

			if (bi_ExtrsaS != null && sIsPrime) {
				if (checkPrimes.contains(bi_ExtrsaS)) {
					errorLabel_1.setText(PRIMES_EQUAL);
					resetMPInputs();
					combo_ExrsaE.removeAll();
				} else {
					checkPrimes.add(bi_ExtrsaS);
					validCount++;
				}
			}

			if (bi_ExtrsaT != null && tIsPrime) {
				if (checkPrimes.contains(bi_ExtrsaT)) {
					errorLabel_1.setText(PRIMES_EQUAL);
					resetMPInputs();
					combo_ExrsaE.removeAll();
				} else {
					checkPrimes.add(bi_ExtrsaT);
					validCount++;
				}
			}

			int selectednumberOfPrimes = Integer
					.parseInt(numberOfPrimesExRSA.getItem(numberOfPrimesExRSA.getSelectionIndex()));

			if (validCount == selectednumberOfPrimes) {
				switch (selectednumberOfPrimes) {
				case 3:
					bi_ExtrsaN = bi_ExtrsaP.multiply(bi_ExtrsaQ).multiply(bi_ExtrsaR);
					bi_ExtrsaPhi = bi_ExtrsaP.subtract(ONE).multiply(bi_ExtrsaQ.subtract(ONE))
							.multiply(bi_ExtrsaR.subtract(ONE));
					break;

				case 4:
					bi_ExtrsaN = bi_ExtrsaP.multiply(bi_ExtrsaQ).multiply(bi_ExtrsaR).multiply(bi_ExtrsaS);
					bi_ExtrsaPhi = (bi_ExtrsaP.subtract(ONE)).multiply(bi_ExtrsaQ.subtract(ONE))
							.multiply(bi_ExtrsaR.subtract(ONE)).multiply(bi_ExtrsaS.subtract(ONE));
					break;

				case 5:
					bi_ExtrsaN = bi_ExtrsaP.multiply(bi_ExtrsaQ).multiply(bi_ExtrsaR).multiply(bi_ExtrsaS)
							.multiply(bi_ExtrsaT);
					bi_ExtrsaPhi = bi_ExtrsaP.subtract(ONE).multiply(bi_ExtrsaQ.subtract(ONE))
							.multiply(bi_ExtrsaR.subtract(ONE)).multiply(bi_ExtrsaS.subtract(ONE))
							.multiply(bi_ExtrsaT.subtract(ONE));
					break;
				}
				if (!stopUpdateE) {
					combo_ExrsaE.removeAll();
					fillE();
				}
				pickRandomExtE.setEnabled(true);
				combo_ExrsaE.setEnabled(true);

				if (bi_ExtrsaE != null) {
					BigInteger gcd = bi_ExtrsaE.gcd(bi_ExtrsaPhi);
					if (!possibleEs.contains(bi_ExtrsaE.toString())) {
						errorLabel_1.setText(NO_VALID_E);
						eIsValid = false;
					} else if (gcd.intValue() != 1) {
						errorLabel_1.setText(NO_VALID_GcdE);
						password1.setText(NOTHING);
						password2.setText(NOTHING);
						createKey.setEnabled(false);
						eIsValid = false;
					} else {
						errorLabel_1.setText(NOTHING);
						eIsValid = true;

					}
					password1.setEnabled(eIsValid);
					password2.setEnabled(eIsValid);
				}
			}
		}

	}

	private void disableSomeExRSAParams() {
		eIsValid = false;
		pickRandomExtE.setEnabled(false);
		combo_ExrsaE.setEnabled(false);
		combo_ExrsaE.removeAll();
		createKey.setEnabled(false);
	}

	private void fillE() {
		eIsValid = false;
		password1.setText(NOTHING);
		password2.setText(NOTHING);
		createKey.setEnabled(false);
		possibleEs = new Vector<String>();

		if (radio_RSA.getSelection()) {
			combo_rsaE.removeAll();
			if (!bi_rsaP.equals(bi_rsaQ)) {
				for (int i = 0; i < primesE.size(); i++) {
					if (primesE.get(i).compareTo(bi_rsaN) < 0) {
						possibleEs.add(primesE.get(i).toString());
					}
				}
				combo_rsaE.setItems(possibleEs.toArray(new String[possibleEs.size()]));
			}
		} else {
			combo_ExrsaE.removeAll();
			for (int i = 0; i < primesE.size(); i++) {
				if (primesE.get(i).compareTo(bi_ExtrsaN) < 0) {
					possibleEs.add(primesE.get(i).toString());
				}
			}
			combo_ExrsaE.setItems(possibleEs.toArray(new String[possibleEs.size()]));
		}
	}
}