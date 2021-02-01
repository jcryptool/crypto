// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.fleissner.UI;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.operations.util.PathEditorInput;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.jcryptool.analysis.fleissner.Activator;
import org.jcryptool.analysis.fleissner.key.Grille;
import org.jcryptool.analysis.fleissner.key.KeySchablone;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer;
import org.jcryptool.analysis.fleissner.logic.KopalAnalyzer.Rotation;
import org.jcryptool.analysis.fleissner.logic.MethodApplication;
import org.jcryptool.analysis.fleissner.logic.ParameterSettings;
import org.jcryptool.analysis.fleissner.views.FleissnerView;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.directories.DirectoryService;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.images.ImageService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.crypto.classic.model.ngram.NGramFrequencies;
import org.jcryptool.crypto.classic.model.ngram.NgramStatisticLogic;
import org.jcryptool.crypto.classic.model.ngram.NgramStore;
import org.jcryptool.crypto.classic.model.ngram.NgramStore.NgramStoreEntry;
import org.jcryptool.crypto.ui.background.BackgroundJob;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.crypto.ui.textsource.TextInputWithSource;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class FleissnerWindow extends Composite {

	
	private static final String THE_TEXT_IS_EMPTY = "The entered text is empty (after filtering by alphabet and spaces)."; //$NON-NLS-1$
	private Grille model;
	private Composite mainComposite;
	private Group keyGroup;
	private Composite inOutTextComposite;
	private Group plaintextGroup;
	private Group ciphertextGroup;
	private Group analysisGroup;
	private Group textSelectionGroup;
	private Group analysisSettingsGroup;
	private Group methodGroup;
	private Canvas canvasKey;
	private KeyListener keyListener;
	private Text plaintextText;
	private Text ciphertextText;
	/**
	 * Console Textfield at the bottom.
	 */
	private Text consoleText;
	private Text descriptionText;
	private Text keyText;
	private Spinner keySize;
	private Spinner restarts_Spinner;
	private Button startButton;
	private Button exampleTextRadioButton;
	private Button writeTextRadioButton;
	private Button loadOwnTextRadioButton;
	private Button deleteHoles;
	private Button randomKey;
	private Button detailsButton;
	private Combo exampleTextCombo;
	private Label restarts_Label;
	private Label alphabetLabel;
	private Combo statisticsCombo;
	private CLabel plaintextInvalidCharWarning;
	private CLabel ciphertextInvalidCharWarning;

	/**
	 * The job sets the found key to this value
	 */
	private int[] keyFromJob = null;
	private LoadFiles lf = new LoadFiles();
	private OutputDialog dialog;
	/**
	 * The background job saves the extended output of the analysis to this
	 * variable.
	 */
	private ArrayList<String> extendedOutputFromJob;
	/**
	 * "Text laden..." Button
	 */
	private TextLoadController textloader;
	private TextInputWithSource lastSuccessfulLoadedTextSource = null;

	private static final int fleissner_max_text_length = 1000000;

	/**
	 * This text contains the encrypted or decrypted text from the BackhroundJob
	 */
	private String textFromJob = ""; //$NON-NLS-1$

	/**
	 * saveKey
	 */
	private KeySchablone oldKey = null;

	/**
	 * Speichert den eingegebenen Klar oder Ciphertext zwischen
	 */
	private String oldManualTextInput = ""; //$NON-NLS-1$

	/**
	 * The background job saves the output to this variable
	 */
	private String consoleOutputFromJob = ""; //$NON-NLS-1$

//	here are the available statistics and their string representation
	private LinkedList<NgramStoreEntry> statsEntries = initializeStats();
	private SelectionListener onOwnTextSelectionChanged;
	private Label lblPlaceholderInOutOnTop;
	private Label lblPlaceholderInOutOnBottom;
	private Button analyzeRight;
	private Rotation rotation = Rotation.Left;
	private Button analyzeLeft;
	private Button encryptLeft;
	private Button decryptLeft;
	private Button encryptRight;
	private Button decryptRight;
	private String lastMethod = "<unset>"; //$NON-NLS-1$
	private Group cipherSettingsGroup;
	private Combo cipheralphaCombo;
	private Label cipheralphaLabel;
	private String ngramStatisticsString(NgramStoreEntry entry) {
		String langString;
		if (entry.language.equals("en")) { //$NON-NLS-1$
			langString = Messages.FleissnerWindow_4;
		} else {
			langString = Messages.FleissnerWindow_6;
		}

		return String.format(Messages.FleissnerWindow_7, langString, entry.n, entry.alphabet());
	}



	/**
	 * Constructor creates header and main, sets new default grille
	 * 
	 * @param parent
	 * @param style
	 */
	public FleissnerWindow(Composite parent, int style) {
		super(parent, style);

		model = new Grille();
		model.setKey(new KeySchablone(7));

		createMain();

		// Apply the default settings to the GUI.
		analyzeLeft.setSelection(true);
		encryptLeft.setSelection(false);
		decryptLeft.setSelection(false);
		analyzeRight.setSelection(false);
		encryptRight.setSelection(false);
		decryptRight.setSelection(false);
		analyze();
		updateSettingsVisibility();
		plaintextText.notifyListeners(SWT.Modify, new Event());
		ciphertextText.notifyListeners(SWT.Modify, new Event());
	}

	private LinkedList<NgramStoreEntry> initializeStats() {
		LinkedList<NgramStoreEntry> result = new LinkedList<NgramStoreEntry>();
		result.add(NgramStore.en_5_nospace);
		result.add(NgramStore.de_5_nospace);
		return result;
	}

	/**
	 * creates main composite that includes all information except of header
	 * 
	 * @param parent
	 */
	private void createMain() {

		mainComposite = new Composite(this, SWT.NONE);
		mainComposite.setLayout(new GridLayout(2, false));
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createHeader();
		createMethodGroup();
		createInOutTextComposite();
		createKeyGroup();
		createLoadtextGroup();
		createAnalysisSettingsGroup();
		createCipherSettingsGroup();
		createStartButtoArea();
		createAnalysisOutputGroup();

		// to permeate "own text input always is default"
		onOwnTextSelectionChanged.widgetSelected(null);
		setLoadtextStrings();
	}

	private void createStartButtoArea() {
		startButton = new Button(mainComposite, SWT.PUSH);
		GridData gd_startButton = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_startButton.verticalIndent = 20;
		startButton.setLayoutData(gd_startButton);
		startButton.setText(Messages.FleissnerWindow_start_button);
		startButton.setEnabled(false);
		startButton.setToolTipText(Messages.FleissnerWindow_toolTipText_start);
		startButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					// This is the only valid place to start the analysis.
					startMethod();
				} catch (IllegalArgumentException e1) {
					LogUtil.logError(Activator.PLUGIN_ID, Messages.FleissnerWindow_error_noMethod, e1, true);
				}
			}
		});
	}

	/**
	 * creates method composite where one of the three methods can be choosen,
	 * method can be started or example analysis can be run
	 * 
	 * @param parent
	 */
	private void createMethodGroup() {

		methodGroup = new Group(mainComposite, SWT.NONE);
		methodGroup.setLayout(new GridLayout(2, true));
		methodGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		methodGroup.setText(Messages.FleissnerWindow_methods);


		analyzeLeft = new Button(methodGroup, SWT.RADIO);
		analyzeLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		analyzeLeft.setText(Messages.FleissnerWindow_label_analyze + " " + Messages.FleissnerWindow_ZZ1); //$NON-NLS-1$
		analyzeLeft.setSelection(true);
		analyzeLeft.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (analyzeLeft.getSelection()) {
					setRotation(Rotation.Left);
					analyze();
				}
			}
		});
		analyzeRight = new Button(methodGroup, SWT.RADIO);
		analyzeRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		analyzeRight.setText(Messages.FleissnerWindow_label_analyze + " " + Messages.FleissnerWindow_ZZ2); //$NON-NLS-1$
		analyzeRight.setSelection(true);
		analyzeRight.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (analyzeRight.getSelection()) {
					setRotation(Rotation.Right);
					analyze();
				}
			}
		});

		encryptLeft = new Button(methodGroup, SWT.RADIO);
		encryptLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		encryptLeft.setText(Messages.FleissnerWindow_label_encrypt + " " + Messages.FleissnerWindow_ZZ1); //$NON-NLS-1$
		encryptLeft.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (encryptLeft.getSelection()) {
					setRotation(Rotation.Left);
					encrypt();
				}
			}
		});
		encryptRight = new Button(methodGroup, SWT.RADIO);
		encryptRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		encryptRight.setText(Messages.FleissnerWindow_label_encrypt + " " + Messages.FleissnerWindow_ZZ2); //$NON-NLS-1$
		encryptRight.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (encryptRight.getSelection()) {
					setRotation(Rotation.Right);
					encrypt();
				}
			}
		});

		decryptLeft = new Button(methodGroup, SWT.RADIO);
		decryptLeft.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		decryptLeft.setText(Messages.FleissnerWindow_label_decrypt + " " + Messages.FleissnerWindow_ZZ1); //$NON-NLS-1$
		decryptLeft.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (decryptLeft.getSelection()) {
					setRotation(Rotation.Left);
					decrypt();
				}
			}
		});

		decryptRight = new Button(methodGroup, SWT.RADIO);
		decryptRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		decryptRight.setText(Messages.FleissnerWindow_label_decrypt + " " + Messages.FleissnerWindow_ZZ2); //$NON-NLS-1$
		decryptRight.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (decryptRight.getSelection()) {
					setRotation(Rotation.Right);
					decrypt();
				}
			}
		});
	}




	/**
	 * creates key composite including key grille, grille length and buttons 'random
	 * key' and 'delete key' to respectively generate a random key or delete the
	 * chosen holes
	 * 
	 * @param parent
	 */
	private void createKeyGroup() {

		keyGroup = new Group(mainComposite, SWT.NONE);
		keyGroup.setLayout(new GridLayout(3, false));
		GridData gd_keyGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_keyGroup.verticalIndent = 20;
		keyGroup.setLayoutData(gd_keyGroup);
		keyGroup.setText(Messages.FleissnerWindow_label_key);

//        canvas for grille with fixed height and width
		canvasKey = new Canvas(keyGroup, SWT.DOUBLE_BUFFERED);
		canvasKey.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		canvasKey.addPaintListener(new KeyPainter(canvasKey, model));
		keyListener = new org.jcryptool.analysis.fleissner.UI.KeyListener(model, this);
		canvasKey.addMouseListener(keyListener);
		GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 5);
		gridData.widthHint = 201;
		gridData.heightHint = 201;
		canvasKey.setLayoutData(gridData);
		canvasKey.setEnabled(false);

		Label spinner = new Label(keyGroup, SWT.NONE);
		spinner.setText(Messages.FleissnerWindow_label_keyLength);
		GridData gd_spinner = new GridData(SWT.FILL, SWT.CENTER, false, true);
		gd_spinner.horizontalIndent = 20;
		spinner.setLayoutData(gd_spinner);

		keySize = new Spinner(keyGroup, SWT.NONE);
		keySize.setMinimum(2);
		keySize.setMaximum(20);
		keySize.setIncrement(2);
		keySize.setSelection(6);
		keySize.setEnabled(true);
		GridData gd_keySize = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		gd_keySize.horizontalIndent = 20;
		keySize.setLayoutData(gd_keySize);
		keySize.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (Integer.parseInt(keySize.getText()) > 20 || Integer.parseInt(keySize.getText()) < 2) {
					keySize.setSelection(6);
				}
				if (keySize.getSelection() % 2 == 1) {
					keySize.setSelection(keySize.getSelection()+1);
				}
				model.setKey(new KeySchablone(Integer.parseInt(keySize.getText())));
				canvasKey.redraw();
				updateKeyText();
				if (exampleTextRadioButton.getSelection()) {
					exampleTextCombo.notifyListeners(SWT.Selection, new Event());
				}
				saveKey(model.getKey());
				canvasKey.removeMouseListener(keyListener);
				canvasKey.addMouseListener(keyListener);

				if (isAnalyze()) {
					if (keySize.getSelection() < 5) {
						restarts_Label.setEnabled(false);
						restarts_Spinner.setEnabled(false);
						restartsHint.setEnabled(false);
					} else {
						restarts_Label.setEnabled(true);
						restarts_Spinner.setEnabled(true);
						restartsHint.setEnabled(true);
					}
				}

			}
		});
		
		Label lblKeyHint = new Label(keyGroup, SWT.WRAP);
		GridData lblKHLD = new GridData();
		lblKHLD.horizontalIndent = 20;
		lblKHLD.horizontalSpan = 2;
		lblKHLD.widthHint = 400;
		lblKHLD.horizontalAlignment = SWT.FILL;
		lblKHLD.grabExcessHorizontalSpace = true;
		lblKeyHint.setLayoutData(lblKHLD);

		lblKeyHint.setText(Messages.FleissnerWindow_X1);


		randomKey = new Button(keyGroup, SWT.PUSH);
		GridData gd_setHoles = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 2, 1);
		gd_setHoles.horizontalIndent = 20;
		randomKey.setLayoutData(gd_setHoles);
		randomKey.setEnabled(false);
		randomKey.setText(Messages.FleissnerWindow_label_randomKey);
		randomKey.setToolTipText(Messages.FleissnerWindow_toolTipText_randomKey);
		randomKey.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				generateRandomKey();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		deleteHoles = new Button(keyGroup, SWT.PUSH);
		GridData gd_deleteHoles = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_deleteHoles.horizontalIndent = 20;
		deleteHoles.setLayoutData(gd_deleteHoles);
		deleteHoles.setEnabled(false);
		deleteHoles.setText(Messages.FleissnerWindow_label_deleteKey);
		deleteHoles.setToolTipText(Messages.FleissnerWindow_toolTipText_deleteKey);
		deleteHoles.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteHoles();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		enterKey = new Button(keyGroup, SWT.PUSH);
		GridData gd_enterKey = new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1);
		gd_enterKey.horizontalIndent = 20;
		enterKey.setLayoutData(gd_deleteHoles);
		enterKey.setEnabled(false);
		enterKey.setText(Messages.FleissnerWindow_ZZ3);
		enterKey.setToolTipText(Messages.FleissnerWindow_toolTipText_deleteKey);
		enterKey.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enterKeyButtonPressed(enterKey);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		keyText = new Text(keyGroup, SWT.H_SCROLL);
		GridData gd_keyText = new GridData(SWT.FILL, SWT.BOTTOM, true, true, 3, 1);
		gd_keyText.horizontalIndent = 20;
		keyText.setText(Messages.FleissnerWindow_label_key + ": "); //$NON-NLS-1$
//        limits text field width and height so text will wrap at the end of composite
		gd_keyText.widthHint = 400;
		keyText.setLayoutData(gd_keyText);
		keyText.setEditable(false);
		keyText.setBackground(ColorService.LIGHTGRAY);

		lblKeyClickHint = new Label(keyGroup, SWT.WRAP);
		lblKeyClickHint.setText(Messages.FleissnerWindow_X6);
		lblKCHData = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		lblKeyClickHint.setLayoutData(lblKCHData);
		lblKCHData.widthHint = 500;
		lblKeyClickHint.setVisible(false);
//		Label lblKeyClickHintFiller = new Label(keyGroup, SWT.NONE);
//		lblKeyClickHintFiller.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
	}

	protected void enterKeyButtonPressed(Button btn) {

		KeyPasteDialog dialog = new KeyPasteDialog(getShell());
		int result = dialog.open();
		if (result == Dialog.OK) {
			keySize.setSelection(dialog.key.getSize());
			model.setKey(dialog.key);
			
			saveKey(dialog.key);
			canvasKey.redraw();
			updateKeyText();
			checkOkButton();
		}
	}



	/**
	 * creates composite for plaintext and ciphertext
	 * 
	 * @param parent
	 */
	private void createInOutTextComposite() {

		inOutTextComposite = new Composite(mainComposite, SWT.NONE);
		inOutTextComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 5));
		GridLayout gl_inOutText = new GridLayout();
		gl_inOutText.marginHeight = 0;
		gl_inOutText.marginWidth = 0;
		inOutTextComposite.setLayout(gl_inOutText);

		
		lblPlaceholderInOutOnTop = new Label(inOutTextComposite, SWT.NONE);
		lblPlaceholderInOutOnTop.setVisible(false);
		GridData onTopData = new GridData();
		onTopData.exclude = true;
		lblPlaceholderInOutOnTop.setLayoutData(onTopData);
		lblPlaceholderInOutOnTop.setText("OnTop"); //$NON-NLS-1$

		lblPlaceholderInOutOnBottom = new Label(inOutTextComposite, SWT.NONE);
		lblPlaceholderInOutOnBottom.setVisible(false);
		GridData onBottomData = new GridData();
		onBottomData.exclude = true;
		lblPlaceholderInOutOnBottom.setLayoutData(onBottomData);
		lblPlaceholderInOutOnBottom.setText("OnBottom"); //$NON-NLS-1$

		createPlaintext(inOutTextComposite);
		createCiphertext(inOutTextComposite);
		
		moveCiphertextToTop();
	}
	
	public void moveCiphertextToTop() {
		plaintextGroup.moveBelow(lblPlaceholderInOutOnBottom);
		ciphertextGroup.moveBelow(lblPlaceholderInOutOnTop);
		mainComposite.layout(new Control[] {plaintextGroup, ciphertextGroup});
	}
	public void movePlaintextToTop() {
		plaintextGroup.moveBelow(lblPlaceholderInOutOnTop);
		ciphertextGroup.moveBelow(lblPlaceholderInOutOnBottom);
		mainComposite.layout(new Control[] {plaintextGroup, ciphertextGroup});
	}

	/**
	 * creates and controls plaintext composite
	 * 
	 * @param parent
	 */
	private void createPlaintext(Composite parent) {

		plaintextGroup = new Group(parent, SWT.NONE);
		plaintextGroup.setLayout(new GridLayout());
		plaintextGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		plaintextInvalidCharWarning = new CLabel(plaintextGroup, SWT.NONE);
		plaintextInvalidCharWarning.setLayoutData(new GridData());
		plaintextInvalidCharWarning.setImage(ImageService.ICON_INFO);
		plaintextInvalidCharWarning.setText(Messages.InvalidCharsWarningPlaintext);

		plaintextText = new Text(plaintextGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		plaintextText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F8) {
					showAnalysisDebugDialog();
					
				}
			}
		});
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//        limits text field width and height so text will wrap at the end of composite
		gridData.widthHint = plaintextGroup.getSize().y;
		gridData.heightHint = plaintextGroup.getSize().x;
		plaintextText.setLayoutData(gridData);
		setTextEnabled(plaintextText, false);
		plaintextText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				int lengthEntered = normalizeTextJustNewlines(plaintextText.getText()).length();
				int lengthNormalized;
				if (isEncrypt()) {
					lengthNormalized = normalizeText(plaintextText.getText(), getEnDecAlphabet()).length();
				} else {
					lengthNormalized = normalizeTextJustNewlines(plaintextText.getText()).length();
				}
				plaintextGroup.setText(
						Messages.FleissnerWindow_label_plaintext + String.format(Messages.FleissnerWindow_rr2, lengthNormalized, lengthEntered));
				if (writeTextRadioButton.getSelection()) {
					oldManualTextInput = plaintextText.getText();
				}
				// Activate/Deactivate the Start Button
				checkOkButton();
			}	
		});

		plaintextGroup
				.setText(Messages.FleissnerWindow_label_plaintext + " (" + normalizeText(plaintextText.getText(), getEnDecAlphabet()).length() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
	

	}



	/**
	 * creates and controls ciphertext composite
	 * 
	 * @param parent
	 */
	private void createCiphertext(Composite parent) {

		ciphertextGroup = new Group(parent, SWT.NONE);
		ciphertextGroup.setLayout(new GridLayout());
		ciphertextGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		ciphertextGroup.setText(Messages.FleissnerWindow_label_ciphertext + " (0)"); //$NON-NLS-1$

		ciphertextInvalidCharWarning = new CLabel(ciphertextGroup, SWT.NONE);
		ciphertextInvalidCharWarning.setLayoutData(new GridData());
		ciphertextInvalidCharWarning.setImage(ImageService.ICON_INFO);
		ciphertextInvalidCharWarning.setText(Messages.InvalidCharsWarningCiphertext);
		
		ciphertextText = new Text(ciphertextGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//        limits text field width and height so text will wrap at the end of composite
		gridData.widthHint = ciphertextGroup.getSize().y;
		gridData.heightHint = ciphertextGroup.getSize().x;
		ciphertextText.setLayoutData(gridData);
		setTextEnabled(ciphertextText, true);
		setTextEnabled(ciphertextText, false);
		ciphertextText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				int lengthEntered = normalizeTextJustNewlines(ciphertextText.getText()).length();
//				int lengthNormalized = normalizeText(ciphertextText.getText()).length();
				int lengthNormalized;
				if (isEncrypt()) {
					lengthNormalized = normalizeTextJustNewlines(ciphertextText.getText()).length();
				} else {
					lengthNormalized = normalizeText(ciphertextText.getText()).length();
				}
				ciphertextGroup.setText(
						Messages.FleissnerWindow_label_ciphertext + String.format(Messages.FleissnerWindow_rr2, lengthNormalized, lengthEntered)); //$NON-NLS-1$
				if (isAnalyze()) {
					plaintextText.setText(""); //$NON-NLS-1$
					plaintextText.notifyListeners(SWT.Modify, new Event());
				}
				if (writeTextRadioButton.getSelection()) {
					oldManualTextInput = ciphertextText.getText();
				}
				// Activate/Deactivate the Start Button
				checkOkButton();

			}
		});

		// Set title Text for ciphertext group
		ciphertextGroup
				.setText(Messages.FleissnerWindow_label_ciphertext + " (" + normalizeText(ciphertextText.getText()).length() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void showAnalysisDebugDialog() {
		Shell shell = new Shell(getDisplay(), SWT.APPLICATION_MODAL | SWT.BORDER | SWT.CLOSE | SWT.RESIZE);
		shell.setLayout(new GridLayout());
		shell.setBounds(getDisplay().getCursorLocation().x, getDisplay().getCursorLocation().y, 600, 600);
		Composite shellMain = new Composite(shell, SWT.MULTI | SWT.V_SCROLL);
		shellMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		shellMain.setLayout(new GridLayout(2, false));
		Text analysisResultText = new Text(shellMain, SWT.MULTI | SWT.V_SCROLL);
		Text comparetoText = new Text(shellMain, SWT.MULTI | SWT.V_SCROLL);
		GridData layoutData1 = new GridData(SWT.FILL, SWT.FILL, true, false);
		analysisResultText.setLayoutData(layoutData1);
		GridData layoutData2 = new GridData(SWT.FILL, SWT.FILL, true, false);
		comparetoText.setLayoutData(layoutData2);
		layoutData1.heightHint = 150;
		layoutData2.heightHint = 150;
		
		Button compareBtn = new Button(shellMain, SWT.PUSH);
		compareBtn.setText("Compare nGram valuations for these texts"); //$NON-NLS-1$
		compareBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Text resultText = new Text(shellMain, SWT.MULTI | SWT.V_SCROLL);
		resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		final String currentPlaintext = plaintextText.getText();
		final NgramStoreEntry statEntry;
		int selectionIndex = statisticsCombo.getSelectionIndex();
		if (selectionIndex >= 0) {
			statEntry = statsEntries.get(statisticsCombo.getSelectionIndex());
		} else {
			statEntry = null;
		}
		NGramFrequencies stats = NgramStore.getInstance().getBuiltinFrequenciesFor(statEntry);

		analysisResultText.setText(currentPlaintext);
		compareBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text1 = analysisResultText.getText();
				String text2 = comparetoText.getText();
				String tr1 = normalizeText(text1, statEntry.alphabet());
				String tr2 = normalizeText(text2, statEntry.alphabet());
				List<NgramStoreEntry> entries = new LinkedList<>() {{
					add(NgramStore.de_2_nospace);
					add(NgramStore.de_3_nospace);
					add(NgramStore.de_4_nospace);
					add(NgramStore.de_5_nospace);
					add(NgramStore.en_2_nospace);
					add(NgramStore.en_3_nospace);
					add(NgramStore.en_4_nospace);
					add(NgramStore.en_5_nospace);
				}};
				List<NgramStoreEntry> entriesToTest = entries.stream().filter(entry -> entry.alphabet().equals(stats.alphabet)).collect(Collectors.toList());
				StringBuilder sb = new StringBuilder();
				for(NgramStoreEntry entryToTest: entriesToTest) {
					NGramFrequencies freqs = NgramStore.getInstance().getBuiltinFrequenciesFor(entryToTest);
					double cost1 = KopalAnalyzer.calculateCost(freqs, KopalAnalyzer.MapTextIntoNumberSpace(tr1, stats.alphabet));
					double cost2 = KopalAnalyzer.calculateCost(freqs, KopalAnalyzer.MapTextIntoNumberSpace(tr2, stats.alphabet));
					sb.append(String.format("For stats=(%s, %s), cost_analysis_out=%s, cost_comparison=%s\n", entryToTest.language, entryToTest.n, cost1, cost2)); //$NON-NLS-1$
				}
				resultText.setText(sb.toString());
			}
		});

		

		shell.pack();
		shell.open();
//		shell.setVisible(true);
//		shell.layout();
		
//		while (!shell.isDisposed()) {
//	        if (!getDisplay().readAndDispatch ()) getDisplay().sleep ();
//	    }
//	    shell.dispose();
		
	}

	private void createCipherSettingsGroup() {

		cipherSettingsGroup = new Group(mainComposite, SWT.NONE);
		cipherSettingsGroup.setLayout(new GridLayout(4, false));
		gd_cipherSettingsGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_cipherSettingsGroup.verticalIndent = 20;
		cipherSettingsGroup.setLayoutData(gd_cipherSettingsGroup);
		cipherSettingsGroup.setEnabled(true);

		cipheralphaLabel = new Label(cipherSettingsGroup, SWT.NONE);
		cipheralphaLabel.setText(Messages.FleissnerWindow_24);

		cipheralphaCombo = new Combo(cipherSettingsGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_cipheralphaCombo = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_cipheralphaCombo.horizontalIndent = 20;
		cipheralphaCombo.setLayoutData(gd_cipheralphaCombo);
		cipheralphaCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ"); //$NON-NLS-1$
		cipheralphaCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"); //$NON-NLS-1$
		if (Locale.getDefault().toString().contains("de")) { //$NON-NLS-1$
			cipheralphaCombo.select(1);
		} else {
			cipheralphaCombo.select(0);
		}
		cipheralphaCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (exampleTextRadioButton.getSelection()) {
					exampleTextCombo.notifyListeners(SWT.Selection, new Event());
				}
				if (isEncrypt()) {
					plaintextText.notifyListeners(SWT.Modify, new Event());
				}
			}
		});
	}


	/**
	 * creates composites for text selection and analysis settings. Text selection
	 * manages the text input. Analysis settings manages the used language, number
	 * of restarts and used language statistics as well as statistics input
	 * 
	 * @param parent
	 */
	private void createAnalysisSettingsGroup() {

		analysisSettingsGroup = new Group(mainComposite, SWT.NONE);
		analysisSettingsGroup.setLayout(new GridLayout(4, false));
		gd_analysisSettingsGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_analysisSettingsGroup.verticalIndent = 20;
		analysisSettingsGroup.setLayoutData(gd_analysisSettingsGroup);
		analysisSettingsGroup.setText(Messages.FleissnerWindow_label_analysisSettings);
		analysisSettingsGroup.setEnabled(true);

		restarts_Label = new Label(analysisSettingsGroup, SWT.NONE);
		restarts_Label.setText(Messages.FleissnerWindow_label_restarts);

		restarts_Spinner = new Spinner(analysisSettingsGroup, SWT.NONE);
		restarts_Spinner.setMinimum(1);
		restarts_Spinner.setMaximum(1400);
		restarts_Spinner.setIncrement(5);
		restarts_Spinner.setSelection(20);
		restarts_Spinner.setEnabled(true);
		GridData gd_restarts_Spinner = new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1);
		gd_restarts_Spinner.horizontalIndent = 20;
		restarts_Spinner.setLayoutData(gd_restarts_Spinner);
		restarts_Spinner.setToolTipText(Messages.FleissnerWindow_toolTipText_restarts);
		
		restartsHint = new Label(analysisSettingsGroup, SWT.WRAP);
		GridData restartsHindData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		restartsHindData.widthHint = 350;
		restartsHint.setLayoutData(restartsHindData);
		restartsHint.setText(Messages.FleissnerWindow_X2);
		restartsHint.setEnabled(true);

		alphabetLabel = new Label(analysisSettingsGroup, SWT.NONE);
		alphabetLabel.setText(Messages.FleissnerWindow_0);
		
		statisticsCombo = new Combo(analysisSettingsGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_alphabetCombo = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_alphabetCombo.horizontalIndent = 20;
		statisticsCombo.setLayoutData(gd_alphabetCombo);
		for(NgramStoreEntry entry: statsEntries) {
			String entryString = ngramStatisticsString(entry);
			statisticsCombo.add(entryString);
		}
//		statisticsCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//		statisticsCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß");
//
		if (Locale.getDefault().toString().contains("de")) { //$NON-NLS-1$
			statisticsCombo.select(1);
		} else {
			statisticsCombo.select(0);
		}
	}

	
	private void setLoadtextStrings() {
		if (isAnalyze()) {
			textSelectionGroup.setText(Messages.FleissnerWindow_label_textChoiceCipher);
			exampleTextRadioButton.setText(Messages.FleissnerWindow_label_exampleTextCipher);
			writeTextRadioButton.setText(Messages.FleissnerWindow_label_writeTextCipher);
			loadOwnTextRadioButton.setText(Messages.FleissnerWindow_label_loadOwnTextCipher);
			
		} else if (isEncrypt()) {
			textSelectionGroup.setText(Messages.FleissnerWindow_label_textChoicePlain);
			exampleTextRadioButton.setText(Messages.FleissnerWindow_label_exampleTextPlain);
			writeTextRadioButton.setText(Messages.FleissnerWindow_label_writeTextPlain);
			loadOwnTextRadioButton.setText(Messages.FleissnerWindow_label_loadOwnTextPlain);
			
		} else { // decryption
			textSelectionGroup.setText(Messages.FleissnerWindow_label_textChoiceCipher);
			exampleTextRadioButton.setText(Messages.FleissnerWindow_label_exampleTextCipher);
			writeTextRadioButton.setText(Messages.FleissnerWindow_label_writeTextCipher);
			loadOwnTextRadioButton.setText(Messages.FleissnerWindow_label_loadOwnTextCipher);
		}
	}
	
	/**
	 * creates buttons and controls in text selection composite
	 * 
	 * @param thirdGroup
	 */
	private void createLoadtextGroup() {
		String[] items = { Messages.FleissnerWindow_textSelect_dawkinsGer, Messages.FleissnerWindow_textSelect_wikiGer,
				Messages.FleissnerWindow_textSelect_dawkinsEng, Messages.FleissnerWindow_textSelect_wikiEng };

		textSelectionGroup = new Group(mainComposite, SWT.NONE);
		textSelectionGroup.setLayout(new GridLayout(4, false));
		GridData gd_textSelectionGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_textSelectionGroup.verticalIndent = 20;
		textSelectionGroup.setLayoutData(gd_textSelectionGroup);

		exampleTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		exampleTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		exampleTextRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (exampleTextRadioButton.getSelection()) {

					exampleTextCombo.notifyListeners(SWT.Selection, new Event());

					// Enable the example text combo box.
					// Disable the "Text laden..." button
					exampleTextCombo.setEnabled(true);
					textloader.setEnabled(false);

				}
			}
		});
		exampleTextRadioButton.setSelection(true);

		exampleTextCombo = new Combo(textSelectionGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		exampleTextCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		exampleTextCombo.setItems(items);

		if (Locale.getDefault().toString().contains("de")) { //$NON-NLS-1$
			exampleTextCombo.select(0);
		} else {
			exampleTextCombo.select(2);
		}

		exampleTextCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				String filename = lf.textFiles(exampleTextCombo.getSelectionIndex());

				if (isAnalyze()) {
					encryptWithRandomKey(lf.InputStreamToString(lf.openMyTestStream(filename)));
					setTextEnabled(ciphertextText, false);
				} else if (isDecrypt()) {
					encryptWithRandomKey(lf.InputStreamToString(lf.openMyTestStream(filename)));
					setTextEnabled(ciphertextText, false);
				} else if (isEncrypt()) {
					plaintextText.setText(lf.InputStreamToString(lf.openMyTestStream(filename)));
					plaintextText.notifyListeners(SWT.Modify, new Event());
					setTextEnabled(plaintextText, false);
				}
			}
		});

		writeTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		writeTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		writeTextRadioButton.setSelection(false);
		onOwnTextSelectionChanged = new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (writeTextRadioButton.getSelection()) {

					exampleTextCombo.setEnabled(false);
					textloader.setEnabled(false);

					if (isAnalyze()) {
						ciphertextText.setText(oldManualTextInput);
						ciphertextText.notifyListeners(SWT.Modify, new Event());
						setTextEnabled(ciphertextText, true);
					} else if (isDecrypt()) {
						ciphertextText.setText(oldManualTextInput);
						ciphertextText.notifyListeners(SWT.Modify, new Event());
						setTextEnabled(ciphertextText, true);
					} else if (isEncrypt()) {
						plaintextText.setText(oldManualTextInput);
						plaintextText.notifyListeners(SWT.Modify, new Event());
						setTextEnabled(plaintextText, true);
					}
				}
			}
		};
		writeTextRadioButton.addSelectionListener(onOwnTextSelectionChanged);

		loadOwnTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		loadOwnTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		loadOwnTextRadioButton.setSelection(false);
		loadOwnTextRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (loadOwnTextRadioButton.getSelection()) {

					exampleTextCombo.setEnabled(false);
					textloader.setEnabled(true);

					if (lastSuccessfulLoadedTextSource != null) {
						if (isAnalyze()) {
							ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
							ciphertextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(ciphertextText, false);
						} else if (isDecrypt()) {
							ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
							ciphertextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(ciphertextText, false);
						} else if (isEncrypt()) {
							plaintextText.setText(lastSuccessfulLoadedTextSource.getText());
							plaintextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(plaintextText, false);
						}
					} else {
						if (isAnalyze()) {
							ciphertextText.setText(""); //$NON-NLS-1$
							ciphertextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(ciphertextText, false);
						} else if (isDecrypt()) {
							ciphertextText.setText(""); //$NON-NLS-1$
							ciphertextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(ciphertextText, false);
						} else if (isEncrypt()) {
							plaintextText.setText(""); //$NON-NLS-1$
							plaintextText.notifyListeners(SWT.Modify, new Event());
							setTextEnabled(plaintextText, false);
						}
					}
				}
			}
		});

		textloader = new TextLoadController(textSelectionGroup, this, SWT.PUSH, true, false);
		textloader.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		textloader.setEnabled(false);

		textloader.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {

				if (textloader.getText() != null) {

					if (textloader.getText().getText().length() < fleissner_max_text_length) {
						lastSuccessfulLoadedTextSource = textloader.getText();
					} else {
						boolean result = MessageDialog.openQuestion(FleissnerWindow.this.getShell(),
								Messages.FleissnerWindow_warning, Messages.FleissnerWindow_warning_text);
						if (result) {
							lastSuccessfulLoadedTextSource = textloader.getText();
						}
					}

					if (isAnalyze()) {
						ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
						ciphertextText.notifyListeners(SWT.Modify, new Event());
					} else if (isDecrypt()) {
						ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
						ciphertextText.notifyListeners(SWT.Modify, new Event());
					} else if (isEncrypt()) {
						plaintextText.setText(lastSuccessfulLoadedTextSource.getText());
						plaintextText.notifyListeners(SWT.Modify, new Event());
					}
				}
			}
		});

		
		
	}

	/**
	 * creates composite for analysis output where all results if analysis will be
	 * displayed
	 * 
	 * @param parent
	 */
	private void createAnalysisOutputGroup() {

		analysisGroup = new Group(mainComposite, SWT.NONE);
		analysisGroup.setLayout(new GridLayout(2, false));
		GridData grid = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		analysisGroup.setLayoutData(grid);
		analysisGroup.setText(Messages.FleissnerWindow_label_output);

//		Shell shell = new Shell(); // TODO: this must be avoided
//		detailsButton = new Button(analysisGroup, SWT.PUSH);
//		detailsButton.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false));
//		detailsButton.setText(Messages.FleissnerWindow_label_outputDetails);
//		detailsButton.setEnabled(false);
//		detailsButton.addSelectionListener(new SelectionListener() {
//
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//
//				dialog = new OutputDialog(shell, extendedOutputFromJob);
//				dialog.create(Messages.FleissnerWindow_label_dialogOutput,
//						Messages.FleissnerWindow_label_dialogDescription);
//				dialog.open();
//			}
//
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//			}
//		});

		consoleText = new Text(analysisGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData gridOut = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gridOut.heightHint = 150;
		gridOut.widthHint = 800;
		consoleText.setLayoutData(gridOut);
		consoleText.setEditable(false);
//		consoleText.setBackground(ColorService.WHITE);
		consoleText.setFont(FontService.getNormalMonospacedFont());
		
		consoleLogBtn = new Button(analysisGroup, SWT.PUSH);
		consoleLogBtn.setText(Messages.FleissnerWindow_ZZ7);
		consoleLogBtn.setEnabled(false);
		consoleLogBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));
		consoleLogBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (consoleLogCombo.getSelectionIndex() > -1) {
					consoleTextOpen();
				}
			}
		});
		consoleLogCombo = new Combo(analysisGroup, SWT.DROP_DOWN);
		GridData cLCData = new GridData(SWT.BEGINNING, SWT.FILL, false, false);
		cLCData.widthHint = 600;
		consoleLogCombo.setLayoutData(cLCData);
		consoleLogCombo.setEnabled(false);
		
	}
	
	private void consoleTextAppend(String text) {
		if (savedItems.size() == 0) {
			consoleTextSet(text);
		}
//		consoleText.append(text);
		SavedItem toAppendItem = savedItems.get(savedItems.size()-1);
		String oldText = toAppendItem.text;
		toAppendItem.setText(oldText + text);
//		toAppendItem.write(oldText + text);
	}
	private void consoleTextSet(String text) {
		// new item -- so clear log!
		consoleText.setText(""); //$NON-NLS-1$
//		consoleText.setText(text);
		String mode = isAnalyze()?"analyze":(isEncrypt()?"encrypt":"decrypt"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		String date = now();
		SavedItem item = new SavedItem(date, mode, rotation);
//		item.write(text);
		item.setText(text);
		savedItems.add(item);
	}
	private void consoleTextFinalize() {
		if (savedItems.size() == 0) {
			return;
		}
		SavedItem savedItem = savedItems.get(savedItems.size()-1);
		savedItem.date = now();
		savedItem.write(savedItem.text);
		if (savedItems.size() >= 1) { // only add nearest-to-last-item (not the current...)
			consoleLogCombo.setEnabled(true);
			consoleLogBtn.setEnabled(true);
			consoleLogCombo.add(savedItem.getFileName());
			consoleLogCombo.select(consoleLogCombo.getItemCount()-1);
			consoleText.setText(savedItem.text);
		}
	}
	private void consoleTextOpen() {
		String currentFilename = consoleLogCombo.getText();
		if (currentFilename.length() != 0) {
			try {
				String string = SavedItem.getAbsPath(currentFilename).toString();
				PathEditorInput input = new PathEditorInput(
						string);
				EditorsManager.getInstance().openNewTextEditor(
						input);
			} catch (PartInitException e) {
				LogUtil.logError(FleissnerView.ID, e);
			}
		}
	}
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd_HH-mm-ss"; //$NON-NLS-1$

	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	private static class SavedItem {
		public String date;
		public String operation;
		public Rotation rotation;
		public String text;
		public SavedItem(String date, String operation, Rotation rotation2) {
			super();
			this.date = date;
			this.operation = operation;
			this.rotation = rotation2;
			if (this.getFile().exists()) {
				this.text = read();
			}
		}
		public void setText(String string) {
			this.text = string;
		}
		public static Path getAbsPath(String currentFilename) {
			return Path.of(SavedItem.directory().getPath() + "/" + currentFilename); //$NON-NLS-1$
		}
		private static File directory() {
			String ws = DirectoryService.getWorkspaceDir();
			File grilleDir = new File(ws + "/grille"); //$NON-NLS-1$
			if (! grilleDir.exists()) {
				grilleDir.mkdirs();
			}
			return grilleDir;
		}
		public void write(String payload) {

			File file = getFile();
			try {
				Files.write(Path.of(file.getPath()), payload.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public String read() {
			try {
				return Files.readString(Path.of(getFile().getPath()), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
				return ""; //$NON-NLS-1$
			}
		}
		private File getFile() {
			return new File(directory().getPath() + "/" + getFileName()); //$NON-NLS-1$
		}
		private String getFileName() {
			return date.replaceAll("[^A-Za-z0-9_-]", "_") + "_operation_" + operation + Messages.FleissnerWindow_rr1 + KopalAnalyzer.RotationToStringShort(rotation) + ".txt"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

	}
	private List<SavedItem> savedItems = new LinkedList<SavedItem>();

	/**
	 * Creates the header and short description at the top of the plugin
	 * 
	 * @param parent
	 */
	private void createHeader() {
		TitleAndDescriptionComposite td = new TitleAndDescriptionComposite(mainComposite);
		td.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		td.setTitle(Messages.FleissnerWindow_title);
		td.setDescription(Messages.FleissnerWindow_subtitle); //$NON-NLS-1$
//		td.setDescription(Messages.FleissnerWindow_subtitle + "\n\n" + Messages.FleissnerWindow_Z1); //$NON-NLS-1$
	}

	protected boolean isEncrypt() {
		return encryptRight.getSelection() || encryptLeft.getSelection();
	}
	protected boolean isDecrypt() {
		return decryptRight.getSelection() || decryptLeft.getSelection();
	}
	protected boolean isAnalyze() {
		return analyzeRight.getSelection() || analyzeLeft.getSelection();
	}

	protected void setRotation(Rotation rot) {
		this.rotation = rot;
	}

	private void updateSettingsVisibility() {
		cipherSettingsGroup.setVisible(! isAnalyze());
		gd_cipherSettingsGroup.exclude = isAnalyze();
//		cipherSettingsGroup.requestLayout();

		analysisSettingsGroup.setVisible(isAnalyze());
		gd_analysisSettingsGroup.exclude = ! isAnalyze();
//		analysisSettingsGroup.requestLayout();
		
		String enc = Messages.FleissnerWindow_ZZ4;
		String dec = Messages.FleissnerWindow_ZZ5;
		String op = isEncrypt() ? enc : dec;
		String grouptext = String.format(Messages.FleissnerWindow_ZZ6, op);
		cipherSettingsGroup.setText(grouptext);
		mainComposite.layout(new Control[] {analysisSettingsGroup, cipherSettingsGroup});
	}
	
	/**
	 * installs settings for 'analyze' is chosen as the active method
	 */
	public void analyze() {
		if (exampleTextRadioButton.getSelection()) {
			exampleTextCombo.notifyListeners(SWT.Selection, new Event());
		}
		if (this.lastMethod.equals("analyze")) { //$NON-NLS-1$
			return;
		}
		makeNewAnalyzeConsole();
		this.lastMethod = "analyze"; //$NON-NLS-1$
		updateSettingsVisibility();

		setLoadtextStrings();
		moveCiphertextToTop();
		
		lblKCHData.exclude = true;
		lblKeyClickHint.setVisible(false);
		lblKeyClickHint.requestLayout();
		
//      key settings
		canvasKey.setEnabled(false);
		randomKey.setEnabled(false);
		deleteHoles.setEnabled(false);
		enterKey.setEnabled(false);
		// Eine Analyse benötigt keine Schablone, daher Löcher löschen
		deleteHoles();

//        text settings
		setTextEnabled(plaintextText, false);
		plaintextText.setText(""); //$NON-NLS-1$
		plaintextText.notifyListeners(SWT.Modify, new Event());
		setTextEnabled(ciphertextText, false);
		plaintextInvalidCharWarning_setVisible(false);
		ciphertextInvalidCharWarning_setVisible(true);
		

		if (writeTextRadioButton.getSelection()) {
			writeTextRadioButton.notifyListeners(SWT.Selection, new Event());
		} else if (loadOwnTextRadioButton.getSelection()) {
			loadOwnTextRadioButton.notifyListeners(SWT.Selection, new Event());
		}

//      analysis settings
		if (keySize.getSelection() < 5) {
			restarts_Label.setEnabled(false);
			restarts_Spinner.setEnabled(false);
			restartsHint.setEnabled(true);
		} else {
			restarts_Label.setEnabled(true);
			restarts_Spinner.setEnabled(true);
			restartsHint.setEnabled(true);
		}

		alphabetLabel.setEnabled(true);
		statisticsCombo.setEnabled(true);

		// output settings
//		consoleTextSet(Messages.FleissnerWindow_output_progress);
//		consoleTextSet(Messages.FleissnerWindow_output_progress + consoleOutputFromJob);
		// Activate/Deactivate the Start Button
		checkOkButton();

		mainComposite.layout();
	}



	private void makeNewAnalyzeConsole() {
		consoleTextSet(Messages.FleissnerWindow_output_progress_analyze + String.format(" (%s)\n", KopalAnalyzer.RotationToString(rotation))); //$NON-NLS-1$
	}


	private void ciphertextInvalidCharWarning_setVisible(boolean b) {
		if (ciphertextInvalidCharWarning == null) {
			return;
		}
		ciphertextInvalidCharWarning.setVisible(b);
		GridData gd = (GridData) ciphertextInvalidCharWarning.getLayoutData();
		gd.exclude = ! b;
		ciphertextInvalidCharWarning.requestLayout();
		mainComposite.layout();
	}



	private void plaintextInvalidCharWarning_setVisible(boolean b) {
		if (plaintextInvalidCharWarning == null) {
			return;
		}
		plaintextInvalidCharWarning.setVisible(b);
		GridData gd = (GridData) plaintextInvalidCharWarning.getLayoutData();
		gd.exclude = ! b;
		plaintextInvalidCharWarning.requestLayout();
		mainComposite.layout();
		
	}



	/**
	 * installs settings for 'encrypt' is chosen as the active method
	 */
	public void encrypt() {
		if (this.lastMethod.equals("encrypt")) { //$NON-NLS-1$
			return;
		}
		makeNewEncryptConsole();
		this.lastMethod = "encrypt"; //$NON-NLS-1$
		updateSettingsVisibility();

		setLoadtextStrings();
		movePlaintextToTop();

		lblKCHData.exclude = false;
		lblKeyClickHint.setVisible(true);
		lblKeyClickHint.requestLayout();

//      key settings
		canvasKey.setEnabled(true);
		randomKey.setEnabled(true);
		deleteHoles.setEnabled(true);
		enterKey.setEnabled(true);

		if (oldKey != null) {
			model.setKey(oldKey);
			canvasKey.redraw();
			updateKeyText();
		}

//        text settings
		setTextEnabled(plaintextText, false);
		setTextEnabled(ciphertextText, false);
		ciphertextText.setText(""); //$NON-NLS-1$
		ciphertextText.notifyListeners(SWT.Modify, new Event());
		plaintextInvalidCharWarning_setVisible(true);
		ciphertextInvalidCharWarning_setVisible(false);
		

		if (exampleTextRadioButton.getSelection()) {
			exampleTextCombo.notifyListeners(SWT.Selection, new Event());
		} else if (writeTextRadioButton.getSelection()) {
			writeTextRadioButton.notifyListeners(SWT.Selection, new Event());
		} else if (loadOwnTextRadioButton.getSelection()) {
			loadOwnTextRadioButton.notifyListeners(SWT.Selection, new Event());
		}


//		analysis settings
		restarts_Label.setEnabled(false);
		restarts_Spinner.setEnabled(false);
		restartsHint.setEnabled(false);

		alphabetLabel.setEnabled(false);
		statisticsCombo.setEnabled(false);

		textloader.setEnabled(false);

		// output settings
//		detailsButton.setEnabled(false);

		// Activate/Deactivate the Start Button
		checkOkButton();
		mainComposite.layout();
	}



	private void makeNewEncryptConsole() {
		consoleTextSet(Messages.FleissnerWindow_output_progress_encrypt + String.format(" (%s)\n", KopalAnalyzer.RotationToString(rotation))); //$NON-NLS-1$
	}

	/**
	 * installs settings for 'decrypt' is chosen as the active method
	 */
	public void decrypt() {
		if (this.lastMethod.equals("decrypt")) { //$NON-NLS-1$
			return;
		}
		makeNewDecryptConsole();
		this.lastMethod = "decrypt"; //$NON-NLS-1$
		updateSettingsVisibility();

		setLoadtextStrings();
		moveCiphertextToTop();

		lblKCHData.exclude = false;
		lblKeyClickHint.setVisible(true);
		lblKeyClickHint.requestLayout();
		
//      key settings
		canvasKey.setEnabled(true);
		randomKey.setEnabled(true);
		deleteHoles.setEnabled(true);
		enterKey.setEnabled(true);

		if (oldKey != null) {
			model.setKey(oldKey);
			canvasKey.redraw();
			updateKeyText();
		}

//      text settings
		setTextEnabled(plaintextText, false);
		plaintextText.setText(""); //$NON-NLS-1$
		plaintextText.notifyListeners(SWT.Modify, new Event());
		setTextEnabled(ciphertextText, false);
		plaintextInvalidCharWarning_setVisible(false);
		ciphertextInvalidCharWarning_setVisible(true);
		

		if (exampleTextRadioButton.getSelection()) {
			exampleTextCombo.notifyListeners(SWT.Selection, new Event());
		} else if (writeTextRadioButton.getSelection()) {
			writeTextRadioButton.notifyListeners(SWT.Selection, new Event());
		} else if (loadOwnTextRadioButton.getSelection()) {
			loadOwnTextRadioButton.notifyListeners(SWT.Selection, new Event());
		}

//		analysis setttings
		restarts_Label.setEnabled(false);
		restarts_Spinner.setEnabled(false);
		restartsHint.setEnabled(false);

		alphabetLabel.setEnabled(false);
		statisticsCombo.setEnabled(false);

		textloader.setEnabled(false);

		// output settings
//		detailsButton.setEnabled(false);

		// Activate/Deactivate the Start Button
		checkOkButton();
		mainComposite.layout();
	}



	private void makeNewDecryptConsole() {
		consoleTextSet(Messages.FleissnerWindow_output_progress_decrypt + String.format(" (%s)\n", KopalAnalyzer.RotationToString(rotation))); //$NON-NLS-1$
	}

	private void setTextEnabled(Text textfield, boolean b) {
		Color color;
		if (b) {
			color = ColorService.WHITE;
		} else {
			color = ColorService.LIGHTGRAY;
		}
		textfield.setEditable(b);
		textfield.setBackground(color);
	}



	private String canAnalyze(String textNormalized, int grillesize) {
		int textsize = textNormalized.length();
		int squared = grillesize * grillesize;
		if (textsize % squared != 0) {
			StringBuilder kHint = new StringBuilder();
			List<Integer> possibleKs = new LinkedList<>();
			for(int k=2; k<=20; k+=2) {
				int squaredK = k * k;
				if (textsize % squaredK == 0) {
					possibleKs.add(k);
				}
			}
			if (possibleKs.size() > 0) {
				kHint.append(String.format(Messages.FleissnerWindow_X3, possibleKs.stream().map(i -> i.toString()).collect(Collectors.joining(", ")))); //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$
			}
			return String.format(Messages.FleissnerWindow_5 + kHint.toString(), grillesize, squared, textsize, squared);
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	

	private String normalizeText(String text, String alphabet) {
		String result = normalizeText(text);
		result = filterTextBy(result, alphabet);
		return result;
	}
	private String normalizeTextJustNewlines(String text) {
		String result = text;
		result = text.replaceAll("[\r\n]", ""); //$NON-NLS-1$ //$NON-NLS-2$
		return result;
	}
	private String normalizeText(String text) {
		String result = text;
		result = text.replaceAll("[\r\n\t ]", ""); //$NON-NLS-1$ //$NON-NLS-2$
		String upperTransformFrom = "abcdefghijklmnopqrstuvwxyzäöüß"; //$NON-NLS-1$
		String upperTransformTo = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"; //$NON-NLS-1$
		char[] charArrayFrom = upperTransformFrom.toCharArray();
		char[] charArrayTo = upperTransformTo.toCharArray();
		for (int i = 0; i < charArrayFrom.length; i++) {
			char from = charArrayFrom[i];
			char to = charArrayTo[i];
			result = result.replace(from, to);
		}
		return result;
	}
	
	
	/**
	 * sets the arguments for chosen method and starts method 'startApplication'
	 * that execudes method
	 * 
	 * @throws IllegalArgumentException
	 */
	public void startMethod() throws IllegalArgumentException {
		if (isAnalyze()) {
			makeNewAnalyzeConsole();
		}
		if (isEncrypt()) {
			makeNewEncryptConsole();
		}
		if (isDecrypt()) {
			makeNewDecryptConsole();
		}

		FleissnerMethodJob job = null;

		if (isAnalyze()) {

			String keylength = keySize.getText();
			String text = ciphertextText.getText();
			text = normalizeText(text);
			if (text.length() == 0) {
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(THE_TEXT_IS_EMPTY));
				return;
			}
			String canAnalyzeMessage = canAnalyze(text, Integer.valueOf(keylength));
			if (canAnalyzeMessage.length() != 0) {
				MessageBox box = new MessageBox(getShell(), SWT.OK);
				box.setText("Grille"); //$NON-NLS-1$
				box.setMessage(canAnalyzeMessage);
				box.open();
				return;
			}

			String restarts = restarts_Spinner.getText();

			NgramStoreEntry statEntry = null;
			int selectionIndex = statisticsCombo.getSelectionIndex();
			if (selectionIndex >= 0) {
				statEntry = statsEntries.get(statisticsCombo.getSelectionIndex());
			}
			NGramFrequencies stats = NgramStore.getInstance().getBuiltinFrequenciesFor(statEntry);
			int nGramSizeAsInt = stats.n;
			String language = statEntry.language.equals("en") ? "english" : "german"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			NgramStoreEntry entry = NgramStore.en_5_nospace;

			if (! filterTextBy(text, stats.alphabet).equals(text)) {
				Set<Character> superfluousChars = getSuperfluousChars(text, stats.alphabet);
				String superfluousCharsMsgPart = superfluousChars.stream().map(c->c.toString()).collect(Collectors.joining(",")); //$NON-NLS-1$
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(Messages.FleissnerWindow_26, stats.alphabet, superfluousCharsMsgPart));
				return;
			}
			final FleissnerMethodJob analysisJob = new FleissnerMethodJob("analyze", keylength, null, text, nGramSizeAsInt+"", restarts, stats); //$NON-NLS-1$ //$NON-NLS-2$
			job = analysisJob;
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClickWithButton(); // Mechanism to not let the user start other things in the background

					// Do shit after job is finished
//					detailsButton.setEnabled(true);
					LinkedList<String> plaintextTextLines = new LinkedList<String>();
					int displayOffset = 0;
					int usedKeysize = Integer.parseInt(analysisJob.keysize);
					int partLength = (usedKeysize * usedKeysize)/4;
					while(textFromJob.length() > displayOffset) {
						plaintextTextLines.add(textFromJob.substring(displayOffset, displayOffset+partLength));
						displayOffset = displayOffset + partLength;
					}
					plaintextText.setText(plaintextTextLines.stream().collect(Collectors.joining("\n"))); //$NON-NLS-1$
					plaintextText.notifyListeners(SWT.Modify, new Event());

					// Print the key to the GUI
					deleteHoles();
					for (int i = 0; i < keyFromJob.length / 2; i++) {
						model.getKey().set(keyFromJob[2 * i + 1], keyFromJob[2 * i]);
					}
					updateKeyText();

					consoleTextAppend(consoleOutputFromJob);
					if (isAnalyze() || isDecrypt()) {
						if (exampleTextRadioButton.getSelection()) {
							String keyRepr = MethodApplication.threeKeyFormats(lastRandomKey, Messages.FleissnerWindow_XY8);
							String logMask = Messages.FleissnerWindow_XY9;
							String logMaskApplied = String.format(logMask, lastExamplePlaintext, keyRepr);
							consoleTextAppend("\n\n" + logMaskApplied); //$NON-NLS-1$
						}
					}
					consoleTextFinalize();
				});
			});
		} else if (isEncrypt()) {
			String text = plaintextText.getText();
			String normalized = normalizeText(text, getEnDecAlphabet());
			if (normalized.length() == 0) {
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(THE_TEXT_IS_EMPTY));
				return;
			}
			String padded = padText(normalized, keySize.getSelection(), getEnDecAlphabet());
			String key = model.translateKeyToLogic();
			job = new FleissnerMethodJob("encrypt", null, key, padded, null, null, null); //$NON-NLS-1$
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClickWithButton(); // Mechanism to not let the user start other things in the background

					// Do shit after job is finished
					ciphertextText.setText(textFromJob);
					ciphertextText.notifyListeners(SWT.Modify, new Event());
					consoleTextAppend(consoleOutputFromJob);
					consoleTextFinalize();
				});
			});
		} else if (isDecrypt()) {
			String text = ciphertextText.getText();
			String normalized1 = normalizeText(text);
			String normalized2 = normalizeText(normalized1, getEnDecAlphabet());
			if (! normalized1.equals(normalized2)) {
				Set<Character> superfluousChars = getSuperfluousChars(normalized1, getEnDecAlphabet());
				String superfluousCharsMsgPart = superfluousChars.stream().map(c->c.toString()).collect(Collectors.joining(",")); //$NON-NLS-1$
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(Messages.FleissnerWindow_26, getEnDecAlphabet(), superfluousCharsMsgPart));
				return;
			}
		if (normalized1.length() == 0) {
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(THE_TEXT_IS_EMPTY));
				return;
			}
			String canDecryptMessage = canAnalyze(normalized1, keySize.getSelection());
			if (canDecryptMessage.length() != 0) {
				MessageBox box = new MessageBox(getShell(), SWT.OK);
				box.setText("Grille"); //$NON-NLS-1$
				box.setMessage(canDecryptMessage);
				box.open();
				return;
			}

			String key = model.translateKeyToLogic();
			job = new FleissnerMethodJob("decrypt", null, key, normalized1, null, null, null); //$NON-NLS-1$
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClickWithButton(); // Mechanism to not let the user start other things in the background
					// Do shit after job is finished
					plaintextText.setText(textFromJob);
					plaintextText.notifyListeners(SWT.Modify, new Event());
					consoleTextAppend(consoleOutputFromJob);
					consoleTextFinalize();
				});
			});
		}

		imposeNoClickWithButton(startButton, Messages.FleissnerWindow_X4); // Mechanism to not let the user start other things in the background

		job.setRotation(this.rotation);
		// Start the calculation
		job.runInBackground();

	}






	private Set<Character> getSuperfluousChars(String text, String alphabet) {
		HashSet<Character> result = new HashSet<Character>();
		for(char c: text.toCharArray()) {
			if (! alphabet.contains(c+"")) { //$NON-NLS-1$
				result.add(c);
			}
		}
		return result;
	}



	private String filterTextBy(String text, String alphabet) {
		StringBuilder filtered = new StringBuilder();
		for(char c: text.toCharArray()) {
			if (alphabet.contains(""+c)) { //$NON-NLS-1$
				filtered.append(c);
			}
		}
		return filtered.toString();
	}



	private void liftNoClick() {
		getShell().setCursor(getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
	}
	private void liftNoClickWithButton() {
		liftNoClick();
		if (lastNoClickButton != null && ! lastNoClickButton.isDisposed()) {
			lastNoClickButton.setEnabled(true);
			lastNoClickButton.setText(lastNoClickButtonSavedState);
		}
	}

	Button lastNoClickButton = null;
	String lastNoClickButtonSavedState = ""; //$NON-NLS-1$
	private Label restartsHint;
	private Label lblKeyClickHint;
	private GridData lblKCHData;
	private String alphabet_endec = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"; //$NON-NLS-1$
	private Button enterKey;
	private GridData gd_cipherSettingsGroup;
	private GridData gd_analysisSettingsGroup;
	private Combo consoleLogCombo;
	private Button consoleLogBtn;
	private int[] lastRandomKey;
	private String lastExamplePlaintext;
	private String lastExampleCiphertext;
	private void imposeNoClick() {
		getShell().setCursor(getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
	}
	private void imposeNoClickWithButton(Button startButton, String text) {
		imposeNoClick();
		lastNoClickButton = startButton;
		lastNoClickButton.setEnabled(false);
		lastNoClickButtonSavedState = startButton.getText();
		startButton.setText(text);
	}

	/**
	 * generates a random key with current key length and displays key in grille
	 */
	public void generateRandomKey() {

		model.setKey(new KeySchablone(Integer.parseInt(keySize.getText())));

		int size = model.getKey().getSize();
		int x, y;

		do {
			do {
				x = ThreadLocalRandom.current().nextInt(0, size);
				y = ThreadLocalRandom.current().nextInt(0, size);
			} while (model.getKey().get(x, y) != '0');
			model.getKey().set(x, y);
		} while (!model.getKey().isValid());

		canvasKey.redraw();
		updateKeyText();
		saveKey(model.getKey());
		
		// Activate/Deactivate the Start Button
		checkOkButton();
	}

	/**
	 * executes random encryption with random key for example texts that are needed
	 * for analysis or decryption. If 'analyze' is the chosen method the key
	 * displayed on the grille will be deleted afterwards
	 */
	public void encryptWithRandomKey(String text) {

		Grille myTempGrille = new Grille();
		myTempGrille.setKey(new KeySchablone(Integer.parseInt(keySize.getText())));

		int size = myTempGrille.getKey().getSize();
		int x, y;
		do {
			do {
				x = ThreadLocalRandom.current().nextInt(0, size);
				y = ThreadLocalRandom.current().nextInt(0, size);
			} while (myTempGrille.getKey().get(x, y) != '0');
			myTempGrille.getKey().set(x, y);
		} while (!myTempGrille.getKey().isValid());

		String exampleinput = text;
		exampleinput = normalizeText(exampleinput, "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"); //$NON-NLS-1$
		exampleinput = padText(exampleinput, size, "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"); //$NON-NLS-1$
		FleissnerMethodJob job = new FleissnerMethodJob("encrypt", ""+size, //$NON-NLS-1$ //$NON-NLS-2$
				myTempGrille.translateKeyToLogic(), exampleinput, null, null, null);
		job.setRotation(this.rotation);

		job.finalizeListeners.add(status -> {
			getDisplay().syncExec(() -> {
				liftNoClickWithButton(); // Mechanism to not let the user start other things in the background

				// Do something after execution
				ciphertextText.setText(textFromJob);
				ciphertextText.notifyListeners(SWT.Modify, new Event());
				FleissnerWindow.this.lastExampleCiphertext = textFromJob;
			});
		});
		
		
		this.lastRandomKey = myTempGrille.translateKeyToArray();
		this.lastExamplePlaintext = exampleinput;

		imposeNoClick(); // Mechanism to not let the user start other things in the background

		// Start the calculation
		job.runInBackground();

	}

	private String padText(String exampleinput, int size, String alphabet) {
		int len1 = Math.floorDiv(exampleinput.length(), size*size) * (size*size);
		String result = exampleinput;
		if (exampleinput.length() != len1) {
			StringBuilder padding = new StringBuilder();
			while (exampleinput.length() + padding.length() < len1+size*size) {
				padding.append(alphabet.charAt(23));
			}
			result = exampleinput + padding.toString();
		}
		return result;
	}



	private String getEnDecAlphabet() {
		if (this.cipheralphaCombo != null) {
			return this.cipheralphaCombo.getText();
		}
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß"; //$NON-NLS-1$
	}



	/**
	 * deletes all chosen holes on the grille
	 */
	public void deleteHoles() {
		model.setKey(new KeySchablone(Integer.parseInt(keySize.getText())));
		canvasKey.redraw();
		updateKeyText();
		canvasKey.removeMouseListener(keyListener);
		canvasKey.addMouseListener(keyListener);

		// Activate/Deactivate the Start Button
		checkOkButton();
	}

	/**
	 * checks if all parameters for chosen method are correct and enables 'start'
	 * button if so
	 */
	public void checkOkButton() {
		if (isAnalyze()) {
			if (!ciphertextText.getText().isEmpty()) {
				startButton.setEnabled(true);
			} else {
				startButton.setEnabled(false);
			}

		} else if (isEncrypt()) {
			if (model.getKey().isValid() && !plaintextText.getText().isEmpty()) {
				startButton.setEnabled(true);
			} else {
				startButton.setEnabled(false);
			}
		} else if (isDecrypt()) {
			if (model.getKey().isValid() && !ciphertextText.getText().isEmpty()) {
				startButton.setEnabled(true);
			} else {
				startButton.setEnabled(false);
			}
		}
	}

	/**
	 * writes key in textform underneath the key template
	 */
	public void updateKeyText() {
		int counter = 1;
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < model.getKey().getSize(); j++) {
			for (int k = 0; k < model.getKey().getSize(); k++) {
				if (model.getKey().get(j, k) == '1') {
					sb.append(counter);
					sb.append(" "); //$NON-NLS-1$
				}
				counter++;
			}
		}

		keyText.setText(Messages.FleissnerWindow_label_key + ": " + sb.toString().trim()); //$NON-NLS-1$
	}

	/**
	 * 
	 * @return
	 */
	public Canvas getCanvasKey() {
		return canvasKey;
	}

	/**
	 * 
	 * @param canvasKey
	 */
	public void setCanvasKey(Canvas canvasKey) {
		this.canvasKey = canvasKey;
	}

	/**
	 * 
	 * @return
	 */
	public Grille getModel() {
		return model;
	}

	/**
	 * 
	 * @param model
	 */
	public void setModel(Grille model) {
		this.model = model;
	}

	/**
	 * 
	 * @return
	 */
	public Spinner getKeySize() {
		return keySize;
	}

	/**
	 * 
	 * @param keySize
	 */
	public void setKeySize(Spinner keySize) {
		this.keySize = keySize;
	}

	/**
	 * @return the descriptionText
	 */
	public Text getDescriptionText() {
		return descriptionText;
	}

	/**
	 * @return the dialog
	 */
	public OutputDialog getDialog() {
		return dialog;
	}

	public void saveKey(KeySchablone key) {
		oldKey = key;
	}

	public class FleissnerMethodJob extends BackgroundJob {

		private ParameterSettings ps;
		private MethodApplication ma;

		private String[] args;

		private String method;
		private String keysize;
		private String key;
		private String text;
		private String ngramsize;
		private String restarts;
		private NGramFrequencies statistics;
		private Rotation rotation = KopalAnalyzer.Rotation.Right;

		public FleissnerMethodJob(String method, String keysize, String key, String text, String ngramsize,
				String restarts, NGramFrequencies stats) {

			this.method = method;
			this.keysize = keysize;
			this.key = key;
			this.text = text;
			this.ngramsize = ngramsize;
			this.restarts = restarts;
			this.statistics = stats;
		}

		public void setRotation(Rotation rotation) {
			this.rotation = rotation;
		}

		@Override
		public String name() {
			return Messages.FleissnerWindow_X5;
		}
		
		/**
		 * 
		 * @return the most important parameters for starting analysis. Method will be
		 *         executed at the start of every analysis
		 */
		public String checkArgs() {

			StringBuilder argsStringBuilder = new StringBuilder(); //$NON-NLS-1$

			getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				String language = Messages.FleissnerWindow_Z2;
				NgramStoreEntry statEntry = null;
				int selectionIndex = statisticsCombo.getSelectionIndex();
				if (selectionIndex >= 0) {
					statEntry = statsEntries.get(statisticsCombo.getSelectionIndex());
					language = statEntry.language.equals("de") ? Messages.FleissnerWindow_Z3 : Messages.FleissnerWindow_Z2; //$NON-NLS-1$
				}
				argsStringBuilder.append(Messages.FleissnerWindow_parameter_enlistment_keyLength + keysize);
				argsStringBuilder.append(Messages.FleissnerWindow_parameter_enlistment_restarts + restarts);
				argsStringBuilder.append(Messages.FleissnerWindow_parameter_enlistment_language + language);
				argsStringBuilder.append(Messages.FleissnerWindow_parameter_enlistment_nGram + ngramsize);
					
				}
			});
			

			return argsStringBuilder.toString();
		}

		@Override
		public IStatus computation(IProgressMonitor monitor) {

			switch (method) {
			case "analyze": //$NON-NLS-1$
				args = new String[12];
				args[0] = Messages.FleissnerWindow_input_method;
				args[1] = method;
				args[2] = Messages.FleissnerWindow_input_keyLength;
				args[3] = keysize;
				args[4] = Messages.FleissnerWindow_input_cryptedText;
				args[5] = text;
				args[6] = Messages.FleissnerWindow_input_nGramSize;
				args[7] = ngramsize;
				args[8] = Messages.FleissnerWindow_input_language;
				args[9] = "pseudo-language-string"; //$NON-NLS-1$
				args[10] = Messages.FleissnerWindow_input_restarts;
				args[11] = restarts;
				break;
			case "encrypt": //$NON-NLS-1$
				args = new String[6];
				args[0] = Messages.FleissnerWindow_input_method;
				args[1] = method;
				args[2] = Messages.FleissnerWindow_input_key;
				args[3] = key;
				args[4] = Messages.FleissnerWindow_input_plaintext;
				args[5] = text;
				break;
			case "decrypt": //$NON-NLS-1$
				args = new String[6];
				args[0] = Messages.FleissnerWindow_input_method;
				args[1] = method;
				args[2] = Messages.FleissnerWindow_input_key;
				args[3] = key;
				args[4] = Messages.FleissnerWindow_input_cryptedText;
				args[5] = text;
				break;
			}

			return this.run(monitor);
		}

		private IStatus run(IProgressMonitor monitor) {
			this.ps = null;
			this.ma = null;

			try {
				// Configuration of given parameters and selecting and applying one of the three
				// methods

				this.ps = new ParameterSettings(args);
				this.ma = new MethodApplication(ps, statistics, this.rotation);

			} catch (IllegalArgumentException ex) {
				LogUtil.logError(Activator.PLUGIN_ID, Messages.FleissnerWindow_error_enterValidParameters, ex, true);
				return Status.CANCEL_STATUS;
			} catch (FileNotFoundException ex) {
				LogUtil.logError(Activator.PLUGIN_ID, Messages.FleissnerWindow_error_fileNotFound, ex, true); // $NON-NLS-1$
				return Status.CANCEL_STATUS;
			}

			switch (method) {
			case "analyze": //$NON-NLS-1$
				ma.analyze(monitor);
				extendedOutputFromJob = ma.getAnalysisOut();
				extendedOutputFromJob.add(0,
						new String(Messages.FleissnerWindow_parameter_enlistment_dialog + checkArgs()));

				extendedOutputFromJob.add(new String(ma.toString()));

				consoleOutputFromJob = ma.toString();
				keyFromJob = ma.getBestTemplate();
				textFromJob = ma.getBestDecryptedText();
				break;
			case "encrypt": //$NON-NLS-1$
				ma.encrypt();
				textFromJob = ma.getEncryptedText();
				consoleOutputFromJob = ma.toString();
				break;
			case "decrypt": //$NON-NLS-1$
				ma.decrypt();
				textFromJob = ma.getDecryptedText();
				consoleOutputFromJob = ma.toString();
				break;
			}
			monitor.worked(100);
			monitor.done();
			return Status.OK_STATUS;
		}
	}

}

