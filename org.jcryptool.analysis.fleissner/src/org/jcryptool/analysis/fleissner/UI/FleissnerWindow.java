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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.analysis.fleissner.Activator;
import org.jcryptool.analysis.fleissner.key.Grille;
import org.jcryptool.analysis.fleissner.key.KeySchablone;
import org.jcryptool.analysis.fleissner.logic.MethodApplication;
import org.jcryptool.analysis.fleissner.logic.ParameterSettings;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.colors.ColorService;
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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class FleissnerWindow extends Composite {

	
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
	private Button analyze;
	private Button encrypt;
	private Button decrypt;
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
		analyze.notifyListeners(SWT.Selection, new Event());
	}

	private LinkedList<NgramStoreEntry> initializeStats() {
		LinkedList<NgramStoreEntry> result = new LinkedList<NgramStoreEntry>();
		result.add(NgramStore.en_5_space);
		result.add(NgramStore.de_5_space);
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
		createStartButtoArea();
		createAnalysisOutputGroup();
		
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
		methodGroup.setLayout(new GridLayout(3, true));
		methodGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		methodGroup.setText(Messages.FleissnerWindow_methods);

		analyze = new Button(methodGroup, SWT.RADIO);
		analyze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		analyze.setText(Messages.FleissnerWindow_label_analyze);
		analyze.setSelection(true);
		analyze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (analyze.getSelection()) {
					analyze();
				}
			}
		});

		encrypt = new Button(methodGroup, SWT.RADIO);
		encrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		encrypt.setText(Messages.FleissnerWindow_label_encrypt);
		encrypt.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (encrypt.getSelection()) {
					encrypt();
				}
			}
		});

		decrypt = new Button(methodGroup, SWT.RADIO);
		decrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		decrypt.setText(Messages.FleissnerWindow_label_decrypt);
		decrypt.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (decrypt.getSelection()) {
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
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 5);
		gridData.widthHint = 201;
		gridData.heightHint = 201;
		canvasKey.setLayoutData(gridData);
		canvasKey.setEnabled(false);

		Label spinner = new Label(keyGroup, SWT.NONE);
		spinner.setText(Messages.FleissnerWindow_label_keyLength);
		GridData gd_spinner = new GridData(SWT.FILL, SWT.TOP, false, true);
		gd_spinner.horizontalIndent = 20;
		spinner.setLayoutData(gd_spinner);

		keySize = new Spinner(keyGroup, SWT.NONE);
		keySize.setMinimum(2);
		keySize.setMaximum(20);
		keySize.setIncrement(1);
		keySize.setSelection(7);
		keySize.setEnabled(true);
		GridData gd_keySize = new GridData(SWT.LEFT, SWT.TOP, false, true);
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
					keySize.setSelection(7);
				}
				model.setKey(new KeySchablone(Integer.parseInt(keySize.getText())));
				canvasKey.redraw();
				updateKeyText();
				saveKey(model.getKey());
				canvasKey.removeMouseListener(keyListener);
				canvasKey.addMouseListener(keyListener);

				if (analyze.getSelection()) {
					if (keySize.getSelection() < 5) {
						restarts_Label.setEnabled(false);
						restarts_Spinner.setEnabled(false);
					} else {
						restarts_Label.setEnabled(true);
						restarts_Spinner.setEnabled(true);
					}
				}

			}
		});

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

		keyText = new Text(keyGroup, SWT.H_SCROLL);
		GridData gd_keyText = new GridData(SWT.FILL, SWT.BOTTOM, true, true, 2, 1);
		gd_keyText.horizontalIndent = 20;
		keyText.setText(Messages.FleissnerWindow_label_key + ": "); //$NON-NLS-1$
//        limits text field width and height so text will wrap at the end of composite
		gd_keyText.widthHint = keyGroup.getSize().y;
		keyText.setLayoutData(gd_keyText);
		keyText.setEditable(false);
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

		createPlaintext(inOutTextComposite);

		createCiphertext(inOutTextComposite);
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
		plaintextInvalidCharWarning.setImage(ImageService.ICON_WARNING);
		plaintextInvalidCharWarning.setText(Messages.InvalidCharsWarningPlaintext);

		plaintextText = new Text(plaintextGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//        limits text field width and height so text will wrap at the end of composite
		gridData.widthHint = plaintextGroup.getSize().y;
		gridData.heightHint = plaintextGroup.getSize().x;
		plaintextText.setLayoutData(gridData);
		plaintextText.setEditable(false);
		plaintextText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				plaintextGroup.setText(
						Messages.FleissnerWindow_label_plaintext + " (" + normalizeText(plaintextText.getText(), NgramStore.en_5_space.alphabet()).length() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				if (writeTextRadioButton.getSelection()) {
					oldManualTextInput = plaintextText.getText();
				}
				// Activate/Deactivate the Start Button
				checkOkButton();
			}	
		});

		plaintextGroup
				.setText(Messages.FleissnerWindow_label_plaintext + " (" + normalizeText(plaintextText.getText(), NgramStore.en_5_space.alphabet()).length() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
	

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
		ciphertextInvalidCharWarning.setImage(ImageService.ICON_WARNING);
		ciphertextInvalidCharWarning.setText(Messages.InvalidCharsWarningCiphertext);
		
		ciphertextText = new Text(ciphertextGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
//        limits text field width and height so text will wrap at the end of composite
		gridData.widthHint = ciphertextGroup.getSize().y;
		gridData.heightHint = ciphertextGroup.getSize().x;
		ciphertextText.setLayoutData(gridData);
		ciphertextText.setEnabled(true);
		ciphertextText.setEditable(false);
		ciphertextText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				ciphertextGroup.setText(
						Messages.FleissnerWindow_label_ciphertext + " (" + normalizeText(ciphertextText.getText()).length() + ")"); //$NON-NLS-1$//$NON-NLS-2$
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
		GridData gd_analysisSettingsGroup = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_analysisSettingsGroup.verticalIndent = 20;
		analysisSettingsGroup.setLayoutData(gd_analysisSettingsGroup);
		analysisSettingsGroup.setText(Messages.FleissnerWindow_label_analysisSettings);
		analysisSettingsGroup.setEnabled(true);

		restarts_Label = new Label(analysisSettingsGroup, SWT.NONE);
		restarts_Label.setText(Messages.FleissnerWindow_label_restarts);

		restarts_Spinner = new Spinner(analysisSettingsGroup, SWT.NONE);
		restarts_Spinner.setMinimum(1);
		restarts_Spinner.setMaximum(1400);
		restarts_Spinner.setIncrement(1);
		restarts_Spinner.setSelection(5);
		restarts_Spinner.setEnabled(true);
		GridData gd_restarts_Spinner = new GridData(SWT.LEFT, SWT.TOP, false, true, 3, 1);
		gd_restarts_Spinner.horizontalIndent = 20;
		restarts_Spinner.setLayoutData(gd_restarts_Spinner);
		restarts_Spinner.setToolTipText(Messages.FleissnerWindow_toolTipText_restarts);

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
		statisticsCombo.select(0);
//		statisticsCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//		statisticsCombo.add("ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß");
//
		if (Locale.getDefault().toString().equals("de")) { //$NON-NLS-1$
			statisticsCombo.select(1);
		} else {
			statisticsCombo.select(0);
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
		textSelectionGroup.setText(Messages.FleissnerWindow_label_textChoiceCipher);

		exampleTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		exampleTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		exampleTextRadioButton.setText(Messages.FleissnerWindow_label_exampleTextCipher);
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
		exampleTextCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		exampleTextCombo.setItems(items);

		if (Locale.getDefault().toString().equals("de")) { //$NON-NLS-1$
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

				if (analyze.getSelection()) {
					encryptWithRandomKey(lf.InputStreamToString(lf.openMyTestStream(filename)));
					ciphertextText.setEditable(false);
				} else if (decrypt.getSelection()) {
					encryptWithRandomKey(lf.InputStreamToString(lf.openMyTestStream(filename)));
					ciphertextText.setEditable(false);
				} else if (encrypt.getSelection()) {
					plaintextText.setText(lf.InputStreamToString(lf.openMyTestStream(filename)));
					plaintextText.setEditable(false);
				}
			}
		});

		writeTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		writeTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
		writeTextRadioButton.setText(Messages.FleissnerWindow_label_writeTextCipher);
		writeTextRadioButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (writeTextRadioButton.getSelection()) {

					exampleTextCombo.setEnabled(false);
					textloader.setEnabled(false);

					if (analyze.getSelection()) {
						ciphertextText.setText(oldManualTextInput);
						ciphertextText.setEditable(true);
					} else if (decrypt.getSelection()) {
						ciphertextText.setText(oldManualTextInput);
						ciphertextText.setEditable(true);
					} else if (encrypt.getSelection()) {
						plaintextText.setText(oldManualTextInput);
						plaintextText.setEditable(true);
					}
				}
			}
		});

		loadOwnTextRadioButton = new Button(textSelectionGroup, SWT.RADIO);
		loadOwnTextRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		loadOwnTextRadioButton.setText(Messages.FleissnerWindow_label_loadOwnTextCipher);
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
						if (analyze.getSelection()) {
							ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
							ciphertextText.setEditable(false);
						} else if (decrypt.getSelection()) {
							ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
							ciphertextText.setEditable(false);
						} else if (encrypt.getSelection()) {
							plaintextText.setText(lastSuccessfulLoadedTextSource.getText());
							plaintextText.setEditable(false);
						}
					} else {
						if (analyze.getSelection()) {
							ciphertextText.setText(""); //$NON-NLS-1$
							ciphertextText.setEditable(false);
						} else if (decrypt.getSelection()) {
							ciphertextText.setText(""); //$NON-NLS-1$
							ciphertextText.setEditable(false);
						} else if (encrypt.getSelection()) {
							plaintextText.setText(""); //$NON-NLS-1$
							plaintextText.setEditable(false);
						}
					}
				}
			}
		});

		textloader = new TextLoadController(textSelectionGroup, this, SWT.PUSH, true, false);
		textloader.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
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

					if (analyze.getSelection()) {
						ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
					} else if (decrypt.getSelection()) {
						ciphertextText.setText(lastSuccessfulLoadedTextSource.getText());
					} else if (encrypt.getSelection()) {
						plaintextText.setText(lastSuccessfulLoadedTextSource.getText());
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

		Shell shell = new Shell();

		detailsButton = new Button(analysisGroup, SWT.PUSH);
		detailsButton.setLayoutData(new GridData(SWT.FILL, SWT.UP, false, false));
		detailsButton.setText(Messages.FleissnerWindow_label_outputDetails);
		detailsButton.setEnabled(false);
		detailsButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				dialog = new OutputDialog(shell, extendedOutputFromJob);
				dialog.create(Messages.FleissnerWindow_label_dialogOutput,
						Messages.FleissnerWindow_label_dialogDescription);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		consoleText = new Text(analysisGroup, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData gridOut = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridOut.heightHint = 150;
		gridOut.widthHint = 800;
		consoleText.setLayoutData(gridOut);
		consoleText.setEditable(false);
		consoleText.setBackground(ColorService.WHITE);
		consoleText.setFont(FontService.getNormalMonospacedFont());
	}

	/**
	 * Creates the header and short description at the top of the plugin
	 * 
	 * @param parent
	 */
	private void createHeader() {
		TitleAndDescriptionComposite td = new TitleAndDescriptionComposite(mainComposite);
		td.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		td.setTitle(Messages.FleissnerWindow_title);
		td.setDescription(Messages.FleissnerWindow_subtitle);
	}

	/**
	 * installs settings for 'analyze' is chosen as the active method
	 */
	public void analyze() {

//      key settings
		canvasKey.setEnabled(false);
		randomKey.setEnabled(false);
		deleteHoles.setEnabled(false);
		// Eine Analyse benötigt keine Schablone, daher Löcher löschen
		deleteHoles();

//        text settings
		plaintextText.setEditable(false);
		plaintextText.setText(""); //$NON-NLS-1$
		ciphertextText.setEditable(false);
		plaintextInvalidCharWarning.setVisible(false);
		ciphertextInvalidCharWarning.setVisible(true);
		

		if (exampleTextRadioButton.getSelection()) {
			exampleTextCombo.notifyListeners(SWT.Selection, new Event());
		} else if (writeTextRadioButton.getSelection()) {
			writeTextRadioButton.notifyListeners(SWT.Selection, new Event());
		} else if (loadOwnTextRadioButton.getSelection()) {
			loadOwnTextRadioButton.notifyListeners(SWT.Selection, new Event());
		}

//      analysis settings
		if (keySize.getSelection() < 5) {
			restarts_Label.setEnabled(false);
			restarts_Spinner.setEnabled(false);
		} else {
			restarts_Label.setEnabled(true);
			restarts_Spinner.setEnabled(true);
		}

		alphabetLabel.setEnabled(true);
		statisticsCombo.setEnabled(true);

		// output settings
		consoleText.setText(Messages.FleissnerWindow_output_progress + consoleOutputFromJob);
		// Activate/Deactivate the Start Button
		checkOkButton();

	}

	/**
	 * installs settings for 'encrypt' is chosen as the active method
	 */
	public void encrypt() {

//      key settings
		canvasKey.setEnabled(true);
		randomKey.setEnabled(true);
		deleteHoles.setEnabled(true);

		if (oldKey != null) {
			model.setKey(oldKey);
			canvasKey.redraw();
			updateKeyText();
		}

//        text settings
		plaintextText.setEditable(false);
		ciphertextText.setEditable(false);
		ciphertextText.setText(""); //$NON-NLS-1$
		plaintextInvalidCharWarning.setVisible(true);
		ciphertextInvalidCharWarning.setVisible(false);
		

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

		alphabetLabel.setEnabled(false);
		statisticsCombo.setEnabled(false);

		textloader.setEnabled(false);

		// output settings
		detailsButton.setEnabled(false);
		consoleText.setText(Messages.FleissnerWindow_output_progress);

		// Activate/Deactivate the Start Button
		checkOkButton();
	}

	/**
	 * installs settings for 'decrypt' is chosen as the active method
	 */
	public void decrypt() {
//      key settings
		canvasKey.setEnabled(true);
		randomKey.setEnabled(true);
		deleteHoles.setEnabled(true);

		if (oldKey != null) {
			model.setKey(oldKey);
			canvasKey.redraw();
			updateKeyText();
		}

//      text settings
		plaintextText.setEditable(false);
		plaintextText.setText(""); //$NON-NLS-1$
		ciphertextText.setEditable(false);
		plaintextInvalidCharWarning.setVisible(false);
		ciphertextInvalidCharWarning.setVisible(true);
		

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

		alphabetLabel.setEnabled(false);
		statisticsCombo.setEnabled(false);

		textloader.setEnabled(false);

		// output settings
		detailsButton.setEnabled(false);
		consoleText.setText(Messages.FleissnerWindow_output_progress);

		// Activate/Deactivate the Start Button
		checkOkButton();
	}

	private String canAnalyze() {
		int grillesize = keySize.getSelection();
		int squared = grillesize * grillesize;
		int textsize = ciphertextText.getText().length();
		if (textsize % squared != 0) {
			return String.format(Messages.FleissnerWindow_5, grillesize, squared, textsize, squared);
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	

	private String normalizeText(String text, String alphabet) {
		String result = normalizeText(text);
		result = filterTextBy(text, alphabet);
		return result;
	}
	private String normalizeText(String text) {
		String result = text;
		result = text.replaceAll("\\R", ""); //$NON-NLS-1$ //$NON-NLS-2$
		result = result.toUpperCase();
		return result;
	}
	
	
	/**
	 * sets the arguments for chosen method and starts method 'startApplication'
	 * that execudes method
	 * 
	 * @throws IllegalArgumentException
	 */
	public void startMethod() throws IllegalArgumentException {

		FleissnerMethodJob job = null;

		if (analyze.getSelection()) {

			// Checks if the plaintext length is a multiple of the keylength
			// If not, it aborts the background job call.
			String message = canAnalyze();
			if (message.length() != 0) {
				MessageBox box = new MessageBox(getShell(), SWT.OK);
				box.setText("Grille"); //$NON-NLS-1$
				box.setMessage(message);
				box.open();
				return;
			}

			String keylength = keySize.getText();
			String text = ciphertextText.getText();
			text = normalizeText(text);

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
				MessageDialog.openError(getShell(), Messages.FleissnerWindow_1, String.format(Messages.FleissnerWindow_26, stats.alphabet));
				return;
			}
			job = new FleissnerMethodJob("analyze", keylength, null, text, nGramSizeAsInt+"", restarts, stats); //$NON-NLS-1$ //$NON-NLS-2$
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClick(); // Mechanism to not let the user start other things in the background

					// Do shit after job is finished
					detailsButton.setEnabled(true);
					plaintextText.setText(textFromJob);

					// Print the key to the GUI
					deleteHoles();
					for (int i = 0; i < keyFromJob.length / 2; i++) {
						model.getKey().set(keyFromJob[2 * i + 1], keyFromJob[2 * i]);
					}
					updateKeyText();

					consoleText.append(consoleOutputFromJob);
				});
			});
		} else if (encrypt.getSelection()) {
			String text = plaintextText.getText();
			String normalized = normalizeText(text, NgramStore.en_5_space.alphabet());
			String key = model.translateKeyToLogic();
			job = new FleissnerMethodJob("encrypt", null, key, normalized, null, null, null); //$NON-NLS-1$
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClick(); // Mechanism to not let the user start other things in the background

					// Do shit after job is finished
					ciphertextText.setText(textFromJob);
					consoleText.append(consoleOutputFromJob);
				});
			});
		} else if (decrypt.getSelection()) {
			String text = ciphertextText.getText();
			String key = model.translateKeyToLogic();
			job = new FleissnerMethodJob("decrypt", null, key, text, null, null, null); //$NON-NLS-1$
			job.finalizeListeners.add(status -> {
				getDisplay().syncExec(() -> {
					liftNoClick(); // Mechanism to not let the user start other things in the background
					// Do shit after job is finished
					plaintextText.setText(textFromJob);
					consoleText.append(consoleOutputFromJob);
				});
			});
		}

		imposeNoClick(); // Mechanism to not let the user start other things in the background

		// Start the calculation
		job.runInBackground();

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

	private void imposeNoClick() {
		getShell().setCursor(getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
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

		FleissnerMethodJob job = new FleissnerMethodJob("encrypt", keySize.getText(), //$NON-NLS-1$
				myTempGrille.translateKeyToLogic(), text, null, null, null);

		job.finalizeListeners.add(status -> {
			getDisplay().syncExec(() -> {
				liftNoClick(); // Mechanism to not let the user start other things in the background

				// Do something after execution
				ciphertextText.setText(textFromJob);
			});
		});

		imposeNoClick(); // Mechanism to not let the user start other things in the background

		// Start the calculation
		job.runInBackground();

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
		if (analyze.getSelection()) {
			if (!ciphertextText.getText().isEmpty()) {
				startButton.setEnabled(true);
			} else {
				startButton.setEnabled(false);
			}

		} else if (encrypt.getSelection()) {
			if (model.getKey().isValid() && !plaintextText.getText().isEmpty()) {
				startButton.setEnabled(true);
			} else {
				startButton.setEnabled(false);
			}
		} else if (decrypt.getSelection()) {
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

		/**
		 * 
		 * @return the most important parameters for starting analysis. Method will be
		 *         executed at the start of every analysis
		 */
		public String checkArgs() {

			String argsString = ""; //$NON-NLS-1$

			argsString += Messages.FleissnerWindow_parameter_enlistment_keyLength + keysize;
			argsString += Messages.FleissnerWindow_parameter_enlistment_restarts + restarts;
			argsString += Messages.FleissnerWindow_parameter_enlistment_language + "pseudo-lang-string"; //$NON-NLS-1$
			argsString += Messages.FleissnerWindow_parameter_enlistment_nGram + ngramsize;

			return argsString;
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
				this.ma = new MethodApplication(ps, statistics);

			} catch (IllegalArgumentException ex) {
				LogUtil.logError(Activator.PLUGIN_ID, Messages.FleissnerWindow_error_enterValidParameters, ex, true);
				return Status.CANCEL_STATUS;
			} catch (FileNotFoundException ex) {
				LogUtil.logError(Activator.PLUGIN_ID, Messages.FleissnerWindow_error_fileNotFound, ex, true); // $NON-NLS-1$
				return Status.CANCEL_STATUS;
			}

			switch (method) {
			case "analyze": //$NON-NLS-1$
				ma.analyze();
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
