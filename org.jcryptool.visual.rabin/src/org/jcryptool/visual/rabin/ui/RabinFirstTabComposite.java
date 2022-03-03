package org.jcryptool.visual.rabin.ui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.HandleFirstTab;
import org.jcryptool.visual.rabin.Messages;
import org.jcryptool.visual.rabin.Rabin;
import org.eclipse.swt.layout.GridData;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.auto.SmoothScroller; 

public class RabinFirstTabComposite extends Composite {
	private Text txtEnterText;
	private Text txtP;
	private Text txtQ;
	private Text txtModN;
	private Text txtLowLimP;
	private Text txtUpperLimP;
	private Text txtLowLimQ;
	private Text txtUpperLimQ;
	private Text txtChosenPlain;
	private Text txtEncDecStartWarning;
	private Text[][] plaintexts;
	private Text txtWarningNpq;
	private Text txtcompGenPandQWarning;
	private Text txtEnc;
	private Text txtChosenPlainInfo;
	
	private Group grpPlaintext;
	private Group grpParam;
	private Group grpEncDec;
	private Group grpMessages;
	
	private TextLoadController textSelector;
	
	private Label lblPrimeP;
	private Label lblPrimeQ;
	private Label lblModN;
	
	private Composite npqComp;
	private Group genPComp;
	private Group genQComp;
	private Composite paramMainComp;
	private Composite compGenKeys;
	private Composite compHoldEncDec;
	private Composite compPlaintexts;
	private Composite compHoldPlainAndText;
	private Composite rootComposite;

	
	private Button btnGenPrimes;
	private Button btnRadEnc;
	private Button btnRadDec;
	private Button btnEncDec;
	private Button btnGenKeysMan;
	private Button btnGenKeysAlgo;
	private Button btnEncDecStart;
	private Button btnStartGenKeys;
	private Button btnGenKeys;
	private Button btnUseKeysAlgo;
	
	private GridData grpMessagesData;
	
	
	
	
	
	
	
	private GridData txtWarningNpqData;
	
	private GridData txtcompGenPandQWarningData;
	private ScrolledComposite sc;

	private GridData txtChosenPlainData;
	
	private Text pWarning;
	private Text qWarning;
	private Text nWarning;
	
	
	private Text txtLowLimPQSingle;
	private Text txtUpperLimPQSingle;
	private Composite compSelectMultiPandQ;
	private Group grpSelectSinglePandQ;
	private Text txtSinglePQWarning;
	private Button btnSelectSingleLimit;
	private Button btnSelectMultiPandQ;
	
	

	
	private HandleFirstTab guiHandler;
	
	
	
	
	
	
	
	/**
	 * @return the txtLowLimPQSingle
	 */
	public Text getTxtLowLimPQSingle() {
		return txtLowLimPQSingle;
	}
	
	
	
	
	/**
	 * @return the txtUpperLimPQSingle
	 */
	public Text getTxtUpperLimPQSingle() {
		return txtUpperLimPQSingle;
	}
	
	
	/**
	 * @return txtSinglePQWarning
	 */
	public Text getTxtSinglePQWarning() {
		return txtSinglePQWarning;
	}
	
	
	/**
	 * @return btnSelectSingleLimit
	 */
	public Button getBtnSelectSingleLimit() {
		return btnSelectSingleLimit;
	}
	
	
	
	/**
	 * @return btnSelectMultiPandQ
	 */
	public Button getBtnSelectMultiPandQ() {
		return btnSelectMultiPandQ;
	}

	
	
	/**
	 * @return the compSelectMultiPandQ
	 */
	public Composite getCompSelectMultiPandQ() {
		return compSelectMultiPandQ;
	}


	/**
	 * @return the grpSelectSinglePandQ
	 */
	public Group getGrpSelectSinglePandQ() {
		return grpSelectSinglePandQ;
	}


	/**
	 * @return the pWarning
	 */
	public Text getPWarning() {
		return pWarning;
	}


	/**
	 * @return the qWarning
	 */
	public Text getQWarning() {
		return qWarning;
	}


	/**
	 * @return the nWarning
	 */
	public Text getNWarning() {
		return nWarning;
	}


	/**
	 * @return current instance of this class
	 */
	public RabinFirstTabComposite getCurrentInstance() {
		return this;
	}
	
	
	/**
	 * @return the txtEnterText
	 */
	public Text getTxtEnterText() {
		return txtEnterText;
	}






	/**
	 * @return the textSelector
	 */
	public TextLoadController getTextSelector() {
		return textSelector;
	}















	/**
	 * @return the txtP
	 */
	public Text getTxtP() {
		return txtP;
	}



	/**
	 * @return the txtQ
	 */
	public Text getTxtQ() {
		return txtQ;
	}



	/**
	 * @return the txtModN
	 */
	public Text getTxtModN() {
		return txtModN;
	}









	/**
	 * @return the btnGenPrimes
	 */
	public Button getBtnGenPrimes() {
		return btnGenPrimes;
	}






	/**
	 * @return the txtLowLimP
	 */
	public Text getTxtLowLimP() {
		return txtLowLimP;
	}



	/**
	 * @return the txtUpperLimP
	 */
	public Text getTxtUpperLimP() {
		return txtUpperLimP;
	}



	/**
	 * @return the txtLowLimQ
	 */
	public Text getTxtLowLimQ() {
		return txtLowLimQ;
	}



	/**
	 * @return the txtUpperLimQ
	 */
	public Text getTxtUpperLimQ() {
		return txtUpperLimQ;
	}



	/**
	 * @return the btnRadEnc
	 */
	public Button getBtnRadEnc() {
		return btnRadEnc;
	}



	/**
	 * @return the btnRadDec
	 */
	public Button getBtnRadDec() {
		return btnRadDec;
	}



	/**
	 * @return the btnEncDec
	 */
	public Button getBtnEncDec() {
		return btnEncDec;
	}









	/**
	 * @return the btnGenKeysMan
	 */
	public Button getBtnGenKeysMan() {
		return btnGenKeysMan;
	}



	/**
	 * @return the btnGenKeysAlgo
	 */
	public Button getBtnGenKeysAlgo() {
		return btnGenKeysAlgo;
	}






	/**
	 * @return the btnEncDecStart
	 */
	public Button getBtnEncDecStart() {
		return btnEncDecStart;
	}







	/**
	 * @return the btnStartGenKeys
	 */
	public Button getBtnStartGenKeys() {
		return btnStartGenKeys;
	}



	/**
	 * @return the btnGenKeys
	 */
	public Button getBtnGenKeys() {
		return btnGenKeys;
	}



	/**
	 * @return the btnUseKeysAlgo
	 */
	public Button getBtnUseKeysAlgo() {
		return btnUseKeysAlgo;
	}



	/**
	 * @return the txtChosenPlain
	 */
	public Text getTxtChosenPlain() {
		return txtChosenPlain;
	}



	/**
	 * @return the txtEncDecStartWarning
	 */
	public Text getTxtEncDecStartWarning() {
		return txtEncDecStartWarning;
	}



	/**
	 * @return the plaintexts
	 */
	public Text[][] getPlaintexts() {
		return plaintexts;
	}



	/**
	 * @return the guiHandler
	 */
	public GUIHandler getGuiHandler() {
		return guiHandler;
	}







	/**
	 * @return the txtWarningNpq
	 */
	public Text getTxtWarningNpq() {
		return txtWarningNpq;
	}






	/**
	 * @return the txtcompGenPandQWarning
	 */
	public Text getTxtcompGenPandQWarning() {
		return txtcompGenPandQWarning;
	}







	/**
	 * @return the txtEnc
	 */
	public Text getTxtEnc() {
		return txtEnc;
	}



	/**
	 * @return the txtChosenPlainInfo
	 */
	public Text getTxtChosenPlainInfo() {
		return txtChosenPlainInfo;
	}


	private VerifyListener vlNumbers = new VerifyListener() {
		
		/**
		 * is not used at the moment, but can be used
		 */
		@Override
		public void verifyText(VerifyEvent e) {
			// TODO Auto-generated method stub
			
			Text txt = null;
			
			if(e.getSource() instanceof Text) {
				txt = (Text) e.getSource();
			}
			
			boolean doit = true;
			
			if((txt.getText().length() == 0 && e.text.compareTo("0") == 0) //$NON-NLS-1$
					|| !(e.text.matches("\\d")) //$NON-NLS-1$
					//	|| txt.getText().length() == 10
					|| txt == null)
				doit = false;
			
			if(e.character == '\b')
				doit = true;

			e.doit = doit;
			
		}
	};
	
	
	
		
	
	private ModifyListener mlLimit = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {

			guiHandler.updateLimitFields(txtLowLimP, txtUpperLimP, txtLowLimQ, txtUpperLimQ, txtcompGenPandQWarning, btnGenKeys, btnStartGenKeys);
		}
	};
	
	
		
	private ModifyListener mlpq = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			guiHandler.updateTextfields(getCurrentInstance());
		}
	};
	
	
	
	
	
	
	
	
	
		
	
	/**
	 * initialize the content when starting the plugin
	 */
	private void initializeContent() {
		txtP.setText("23"); //$NON-NLS-1$
		txtQ.setText("31"); //$NON-NLS-1$
		txtLowLimP.setText("1"); //$NON-NLS-1$
		txtUpperLimP.setText("2^5"); //$NON-NLS-1$
		txtLowLimQ.setText("1"); //$NON-NLS-1$
		txtUpperLimQ.setText("2^10"); //$NON-NLS-1$
		txtLowLimPQSingle.setText("1"); //$NON-NLS-1$
		txtUpperLimPQSingle.setText("2^10"); //$NON-NLS-1$
		btnSelectSingleLimit.setSelection(true);
		btnGenKeysMan.setSelection(true);
		btnRadEnc.setSelection(true);
		btnEncDecStart.setEnabled(false);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * create group "Plaintext" with its content
	 * @param parent
	 */
	private void createPlainCipherContent(Composite parent) {
		// create group for selecting a plaintext/ciphertext
		grpPlaintext = new Group(parent, SWT.NONE);
		grpPlaintext.setLayout(new GridLayout(3, false));
		grpPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpPlaintext.setText(Messages.RabinFirstTabComposite_grpPlaintext);
		
		// create textselector for selecting plaintext/ciphertext
		textSelector = new TextLoadController(grpPlaintext, parent, SWT.NONE, true, true);
		textSelector.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		
		
		// create label for separating description
		Label lblSepInfoChoosePlain = new Label(grpPlaintext, SWT.SEPARATOR | SWT.VERTICAL);
		GridData lblSepInfoChoosePlainData = new GridData(SWT.CENTER, SWT.FILL, false, true);
		lblSepInfoChoosePlainData.horizontalIndent = 10;
		lblSepInfoChoosePlain.setLayoutData(lblSepInfoChoosePlainData);
		
		
		// create label for description of the chosen text
		/*Label lblDescChooseText = new Label(grpPlaintext, SWT.NONE);
		GridData descChooseTextData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		lblDescChooseText.setLayoutData(descChooseTextData);
		lblDescChooseText.setText(Messages.RabinFirstTabComposite_lblDescChooseText);*/
		
		Text txtInfoChoosePlain = new Text(grpPlaintext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoChoosePlain.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		txtInfoChoosePlain.setText(Messages.RabinFirstTabComposite_lblDescChooseText);
		guiHandler.setSizeControl(txtInfoChoosePlain, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoChoosePlain.setBackground(ColorService.LIGHTGRAY);
	}
	
	
	
	
	/**
	 * create group "Setting Paramters" and its content
	 * @param parent
	 */
	private void createSetParamContent(Composite parent) {
		
		// create group for setting parameters
		grpParam = new Group(parent, SWT.NONE);
		grpParam.setLayout(new GridLayout(4, false));
		grpParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpParam.setText(Messages.RabinFirstTabComposite_grpParam);
		
		
		// create main composite for entering parameter settings
		paramMainComp = new Composite(grpParam, SWT.NONE);
		paramMainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		paramMainComp.setLayout(new GridLayout(2, false));
		
		// create composite for setting p, q, N manually
		npqComp = new Composite(paramMainComp, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		npqComp.setLayout(new GridLayout(2, false));
		
		
		Composite compSelectPrimeGen = new Composite(paramMainComp, SWT.NONE);
		compSelectPrimeGen.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		compSelectPrimeGen.setLayout(new GridLayout(1, false));
		
		btnSelectSingleLimit = new Button(compSelectPrimeGen, SWT.RADIO);
		btnSelectSingleLimit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnSelectSingleLimit.setText(Messages.RabinFirstTabComposite_btnSelectSingleLimit);
		btnSelectSingleLimit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compSelectMultiPandQ);
				guiHandler.showControl(grpSelectSinglePandQ);
				guiHandler.updateLimitFieldsSingle(getCurrentInstance());
			}
		});
		
		
		btnSelectMultiPandQ = new Button(compSelectPrimeGen, SWT.RADIO);
		btnSelectMultiPandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnSelectMultiPandQ.setText(Messages.RabinFirstTabComposite_btnSelectMultiPandQ);
		btnSelectMultiPandQ.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(grpSelectSinglePandQ);
				guiHandler.showControl(compSelectMultiPandQ);
				guiHandler.updateLimitFields(txtLowLimP, txtUpperLimP, txtLowLimQ, txtUpperLimQ, txtcompGenPandQWarning, btnGenKeys, btnStartGenKeys);
			}
		});
		
		
		

		
		// create label for prime p in npq composite
		lblPrimeP = new Label(npqComp, SWT.NONE);
		lblPrimeP.setText("p ="); //$NON-NLS-1$
		txtP = new Text(npqComp, SWT.BORDER);
		GridData txtPData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtP.setLayoutData(txtPData);
		guiHandler.setSizeControl(txtP, SWT.DEFAULT, SWT.DEFAULT);
		txtP.addModifyListener(mlpq);
		
		
		pWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		pWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) pWarning.getLayoutData()).horizontalIndent = 28;
		pWarning.setText(Messages.RabinFirstTabComposite_pWarning);
		pWarning.setBackground(ColorService.LIGHTGRAY);
		pWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(pWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(pWarning);
		
		
		
		
		// create label for prime q in npq composite
		lblPrimeQ = new Label(npqComp, SWT.NONE);
		lblPrimeQ.setText("q ="); //$NON-NLS-1$
		txtQ = new Text(npqComp, SWT.BORDER);
		GridData txtQData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtQ.setLayoutData(txtQData);
		guiHandler.setSizeControl(txtQ, SWT.DEFAULT, SWT.DEFAULT);
		txtQ.addModifyListener(mlpq);
		
		
		qWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		qWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) qWarning.getLayoutData()).horizontalIndent = 28;
		qWarning.setText(Messages.RabinFirstTabComposite_qWarning);
		qWarning.setBackground(ColorService.LIGHTGRAY);
		qWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(qWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(qWarning);
		
		
		// create label for N in npq composite
		lblModN = new Label(npqComp, SWT.NONE);
		lblModN.setText("N ="); //$NON-NLS-1$
		txtModN = new Text(npqComp, SWT.READ_ONLY | SWT.BORDER);
		GridData txtModNData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtModN.setLayoutData(txtModNData);
		guiHandler.setSizeControl(txtModN, SWT.DEFAULT, SWT.DEFAULT);
		
		txtModN.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.txtModNActionFirstTab(txtModN, btnEncDecStart);
			}
		});
		
		
		
		nWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		nWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) nWarning.getLayoutData()).horizontalIndent = 28;
		nWarning.setText(Messages.RabinFirstTabComposite_nWarning);
		nWarning.setBackground(ColorService.LIGHTGRAY);
		nWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(nWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(nWarning);
		
		
		
		// create text warning for npqComp
		
		/*txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtWarningNpq.setLayoutData(txtWarningNpqData);
		guiHandler.setSizeControlWarning(txtWarningNpq, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
		txtWarningNpq.setForeground(ColorService.RED);
		guiHandler.hideControl(txtWarningNpq);*/
		
		grpSelectSinglePandQ = new Group(paramMainComp, SWT.NONE);
		grpSelectSinglePandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpSelectSinglePandQ.setLayout(new GridLayout(2, false));
		grpSelectSinglePandQ.setText(Messages.RabinFirstTabComposite_grpSelectSinglePandQ);
		//guiHandler.hideControl(grpSelectSinglePandQ);
		
		/*Label lblGenPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
		lblGenPQSingle.setText("Prime number p and q");
		GridData GenPQSingledata = new GridData();
		GenPQSingledata.horizontalSpan = 2;
		lblGenPQSingle.setLayoutData(GenPQSingledata);*/
		
		// create label and textbox for lower limit prime p
		Label lblLowLimPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
		String strLowerLimit = Messages.RabinFirstTabComposite_strLowerLimit;
		lblLowLimPQSingle.setText(strLowerLimit);
		txtLowLimPQSingle = new Text(grpSelectSinglePandQ, SWT.BORDER);
		txtLowLimPQSingle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtLowLimPQSingle, SWT.DEFAULT, SWT.DEFAULT);
		
		txtLowLimPQSingle.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.updateLimitFieldsSingle(getCurrentInstance());
				
			}
		});
		
		
		
		
		// create label and textbox for upper limit prime p
		Label lblUpperLimPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
		String strUpperLimit = Messages.RabinFirstTabComposite_strUpperLimit;
		lblUpperLimPQSingle.setText(strUpperLimit);
		txtUpperLimPQSingle = new Text(grpSelectSinglePandQ, SWT.BORDER);
		txtUpperLimPQSingle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimPQSingle, SWT.DEFAULT, SWT.DEFAULT);
		
		txtUpperLimPQSingle.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				guiHandler.updateLimitFieldsSingle(getCurrentInstance());
			}
		});
		
		
		
		txtSinglePQWarning = new Text(grpSelectSinglePandQ, SWT.MULTI | SWT.WRAP);
		txtSinglePQWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) txtSinglePQWarning.getLayoutData()).horizontalIndent = 80;
		txtSinglePQWarning.setBackground(ColorService.LIGHTGRAY);
		txtSinglePQWarning.setForeground(ColorService.RED);
		guiHandler.setSizeControlWarning(txtSinglePQWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtSinglePQWarning);
		
		
		
		

		compSelectMultiPandQ = new Composite(paramMainComp, SWT.NONE);
		compSelectMultiPandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelectMultiPandQ.setLayout(new GridLayout(2, false));
		guiHandler.hideControl(compSelectMultiPandQ);
		
		
		
		// create composite for entering lower and upper limit for 
		// prime p
		//genPComp = new Composite(paramMainComp, SWT.NONE);
		genPComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genPComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genPComp.setLayout(new GridLayout(2, false));
		genPComp.setText(Messages.RabinFirstTabComposite_genPComp);
		
		// create composite for entering lower and upper limit for 
		// prime q
		//genQComp = new Composite(paramMainComp, SWT.NONE);
		genQComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genQComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genQComp.setLayout(new GridLayout(2, false));
		genQComp.setText(Messages.RabinFirstTabComposite_genQComp);
		
		
		txtcompGenPandQWarning = new Text(compSelectMultiPandQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtcompGenPandQWarningData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtcompGenPandQWarning.setLayoutData(txtcompGenPandQWarningData);
		guiHandler.setSizeControlWarning(txtcompGenPandQWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtcompGenPandQWarning.setBackground(ColorService.LIGHTGRAY);
		txtcompGenPandQWarning.setForeground(ColorService.RED);
		guiHandler.hideControl(txtcompGenPandQWarning);
		
		
		// create composite for how to generate the keys
		compGenKeys = new Composite(grpParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		
		// create radio button for generating keys manually 
		btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysMan.setText(Messages.RabinFirstTabComposite_btnGenKeysMan);
		
		
		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//guiHandler.btnGenKeysManAction(getCurrentInstance(), e);
				guiHandler.updateTextfields(getCurrentInstance());
			}
		});
		
		btnGenKeys = new Button(compGenKeys, SWT.RADIO);
		btnGenKeys.setText(Messages.RabinFirstTabComposite_btnGenKeys);
		
		btnGenKeys.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysAction(getCurrentInstance(), e);
			}
		});
		
		
		btnUseKeysAlgo = new Button(compGenKeys, SWT.RADIO);
		btnUseKeysAlgo.setText(Messages.RabinFirstTabComposite_btnUseKeysAlgo);
		btnUseKeysAlgo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnUseKeysAlgoAction(getCurrentInstance(), e);
			}
		});
		
		
		btnStartGenKeys = new Button(compGenKeys, SWT.PUSH);
		GridData btnStartGenData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		btnStartGenData.horizontalIndent = 5;
		btnStartGenKeys.setLayoutData(btnStartGenData);
		btnStartGenKeys.setText(Messages.RabinFirstTabComposite_btnStartGenKeys);
		
		btnStartGenKeys.addSelectionListener(new SelectionAdapter() {		
			

			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnStartGenKeysAction(getCurrentInstance(), 1000);
			}
			
		});
		
		
		// create label for separating info
		Label lblSepInfoSetParam = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoSetParam.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		
		// create info for setting parameters
		Text txtInfoSetParam = new Text(grpParam, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoSetParamData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoSetParam.setLayoutData(txtInfoSetParamData);
		guiHandler.setSizeControl(txtInfoSetParam, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSetParam.setText(Messages.RabinFirstTabComposite_txtInfoSetParam);
		txtInfoSetParam.setBackground(ColorService.LIGHTGRAY);
		
		
		
		// create label for prime number p
		/*Label lblGenP = new Label(genPComp, SWT.NONE);
		lblGenP.setText("Prime number p");
		GridData GenPdata = new GridData();
		GenPdata.horizontalSpan = 2;
		lblGenP.setLayoutData(GenPdata);*/
		
		// create label and textbox for lower limit prime p
		Label lblLowLimP = new Label(genPComp, SWT.NONE);
		lblLowLimP.setText(strLowerLimit);
		txtLowLimP = new Text(genPComp, SWT.BORDER);
		txtLowLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtLowLimP, SWT.DEFAULT, SWT.DEFAULT);
		
		txtLowLimP.addModifyListener(mlLimit);
		
		
		
		// create label and textbox for upper limit prime p
		Label lblUpperLim = new Label(genPComp, SWT.NONE);
		lblUpperLim.setText(strUpperLimit);
		txtUpperLimP = new Text(genPComp, SWT.BORDER);
		txtUpperLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimP, SWT.DEFAULT, SWT.DEFAULT);
		
		txtUpperLimP.addModifyListener(mlLimit);
		
		
		
		// create label for prime q
		/*Label lblGenQ = new Label(genQComp, SWT.NONE);
		lblGenQ.setText("Prime number q");
		GridData GenQdata = new GridData();
		GenQdata.horizontalSpan = 2;
		lblGenQ.setLayoutData(GenPdata);*/
		
		// create label and textbox for lower limit prime q
		Label lblLowLimQ = new Label(genQComp, SWT.NONE);
		lblLowLimQ.setText(strLowerLimit);
		txtLowLimQ = new Text(genQComp, SWT.BORDER);
		txtLowLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtLowLimQ, SWT.DEFAULT, SWT.DEFAULT);
		
		txtLowLimQ.addModifyListener(mlLimit);
		
		
		
		// create label and textbox for upper limit prime q
		Label lblUpperLimQ = new Label(genQComp, SWT.NONE);
		lblUpperLimQ.setText(strUpperLimit);
		txtUpperLimQ = new Text(genQComp, SWT.BORDER);
		txtUpperLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimQ, SWT.DEFAULT, SWT.DEFAULT);
	
		
		txtUpperLimQ.addModifyListener(mlLimit);	

	}

	
	
	
	
	/**
	 * create the table which is used in "enc/dec" group
	 */
	private void createTable() {
		
		for(int i = 0; i < plaintexts.length; i++) {
			for(int j = 0; j < plaintexts[i].length; j++) {
				plaintexts[i][j] = new Text(compPlaintexts, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.READ_ONLY);
				plaintexts[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				guiHandler.setSizeControl(plaintexts[i][j], SWT.DEFAULT, SWT.DEFAULT);
			
				plaintexts[i][j].addMouseListener(new MouseAdapter() {
			
					@Override
					public void mouseDown(MouseEvent e) {
						
						guiHandler.tableMouseDownAction(e, txtChosenPlain, txtChosenPlainInfo, plaintexts);
						
					}
					
				});
				
				plaintexts[i][j].addMouseTrackListener(new MouseTrackAdapter() {
					
					@Override
					public void mouseEnter(MouseEvent e) {
						guiHandler.tableMouseEnterAction(e);
					}
				});
			}
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Create group for encryption/decryption and its content
	 * @param parent
	 */
	private void createEncDecContent(Composite parent) {
		
		// create Group for encryption/decryption
		grpEncDec = new Group(parent, SWT.NONE);
		grpEncDec.setLayout(new GridLayout(1, false));
		GridData grpEncDecData = new GridData(SWT.FILL, SWT.FILL, true, true);
		grpEncDecData.minimumHeight = 400;
		grpEncDec.setLayoutData(grpEncDecData);
		grpEncDec.setText(Messages.RabinFirstTabComposite_grpEncDec);
		
		
		// create composite for holding radio buttons, start button and info
		compHoldEncDec = new Composite(grpEncDec, SWT.NONE);
		compHoldEncDec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		compHoldEncDec.setLayout(new GridLayout(3, false));
		
		// create composite containing the radion buttons for enc/dec and start
		Composite compEncDecStart = new Composite(compHoldEncDec, SWT.NONE);
		compEncDecStart.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		compEncDecStart.setLayout(new GridLayout(1, false));
		
		// create enc/dec radio buttons
		btnRadEnc = new Button(compEncDecStart, SWT.RADIO);
		btnRadEnc.setText(Messages.RabinFirstTabComposite_btnRadEnc);
		btnRadEnc.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				guiHandler.hideControl(compHoldPlainAndText);
				guiHandler.showControl(txtEnc);
				
			}
			
		});
		
		btnRadDec = new Button(compEncDecStart, SWT.RADIO);
		btnRadDec.setText(Messages.RabinFirstTabComposite_btnRadDec);
		btnRadDec.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				
				guiHandler.hideControl(txtEnc);
				guiHandler.showControl(compHoldPlainAndText);
				
				
			}
		
		});
		
		btnEncDecStart = new Button(compEncDecStart, SWT.PUSH);
		GridData btnEncDecStartData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
		btnEncDecStartData.horizontalIndent = 5;
		btnEncDecStart.setLayoutData(btnEncDecStartData);
		
		btnEncDecStart.setText(Messages.RabinFirstTabComposite_btnEncDecStart);
		btnEncDecStart.setEnabled(false);
		
		btnEncDecStart.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnEncDecStartAction(textSelector, txtEnc, txtEncDecStartWarning, txtChosenPlain, plaintexts, btnRadEnc, btnRadDec);
								
			}
			
		});
		
			
		
		// create label for separating infoEncDec
		Label lblSepEncDec = new Label(compHoldEncDec, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepEncDec.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		
		// create txtbox for displaying info of encdec
		Text txtInfoEncDec = new Text(compHoldEncDec, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoEncDecData = new GridData(SWT.BEGINNING, SWT.FILL, true, true);
		txtInfoEncDec.setLayoutData(txtInfoEncDecData);
		guiHandler.setSizeControl(txtInfoEncDec, 600, SWT.DEFAULT);
		txtInfoEncDec.setText(Messages.RabinFirstTabComposite_txtInfoEncDec);
		
		txtInfoEncDec.setBackground(ColorService.LIGHTGRAY);
		
		
		txtEncDecStartWarning = new Text(compHoldEncDec, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtEncDecStartWarningData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		txtEncDecStartWarning.setLayoutData(txtEncDecStartWarningData);
		guiHandler.setSizeControlWarning(txtEncDecStartWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtEncDecStartWarning.setBackground(ColorService.LIGHTGRAY);
		txtEncDecStartWarning.setForeground(ColorService.RED);
		guiHandler.hideControl(txtEncDecStartWarning);
		
		
		
		
		
		
		
		grpMessages = new Group(grpEncDec, SWT.NONE);
		grpMessagesData = new GridData(SWT.FILL, SWT.FILL, true, false);
		grpMessages.setLayoutData(grpMessagesData);
		grpMessages.setLayout(new GridLayout(3, false));
		grpMessages.setText(Messages.RabinFirstTabComposite_grpMessages);
		
		
		
		
		
		
		txtEnc = new Text(grpMessages, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
		txtEnc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtEnc, 600, 400);
		
		compHoldPlainAndText = new Composite(grpMessages, SWT.NONE);
		compHoldPlainAndText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldPlainAndText.setLayout(new GridLayout(2, false));
		guiHandler.hideControl(compHoldPlainAndText);
		

		compPlaintexts = new Composite(compHoldPlainAndText, SWT.NONE);
		GridData compPlaintextsData = new GridData(SWT.FILL, SWT.FILL, true, true);

		
		compPlaintexts.setLayoutData(compPlaintextsData);
		guiHandler.setSizeControl(compPlaintexts, 500, 400);
		compPlaintexts.setLayout(new GridLayout(5, false));
		
		
		// labels c[i]
		for(int i = 1; i <= 5; i++) {
			Label lblCipherColumn = new Label(compPlaintexts, SWT.NONE);
			lblCipherColumn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
			lblCipherColumn.setText("c[" + i + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		
		plaintexts = new Text[4][5];
		
		
		createTable();
		
		
	
		Composite compChosenPlain = new Composite(compHoldPlainAndText, SWT.NONE);
		compChosenPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compChosenPlain.setLayout(new GridLayout(1, false));
		//guiHandler.setSizeControl(compChosenPlain, SWT.DEFAULT, SWT.DEFAULT);
		
		txtChosenPlainInfo = new Text(compChosenPlain, SWT.MULTI | SWT.READ_ONLY);
		txtChosenPlainInfo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		guiHandler.setSizeControlWarning(txtChosenPlainInfo, SWT.DEFAULT, SWT.DEFAULT);
		txtChosenPlainInfo.setText(""); //$NON-NLS-1$
		txtChosenPlainInfo.setBackground(ColorService.LIGHTGRAY);
		
		
		
		txtChosenPlain = new Text(compChosenPlain, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtChosenPlainData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtChosenPlain.setLayoutData(txtChosenPlainData);
		guiHandler.setSizeControl(txtChosenPlain, SWT.DEFAULT, SWT.DEFAULT);
		
		
		Button btnWriteToEditor = new Button(grpMessages, SWT.PUSH);
		btnWriteToEditor.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true));
		btnWriteToEditor.setText(Messages.RabinFirstTabComposite_btnWriteToEditor);
		
		btnWriteToEditor.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					guiHandler.btnWriteToEditorAction(btnRadEnc, btnRadDec, txtEnc, txtChosenPlain);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
					
			}
		});

			
	}
	
	
	/**
	 * create the content of the Cryptosystem tab
	 */
	private void createContent() {
		setLayout(new GridLayout(1, false));
		
		//sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		/*sc = new ScrolledComposite(this, SWT.NONE);
		sc.setLayout(new GridLayout(1, false));
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootComposite = new Composite(sc, SWT.BORDER);
		rootComposite.setLayout(new GridLayout(1, false));
		GridData rootCompositeData = new GridData(SWT.FILL, SWT.FILL, true, true);
		rootComposite.setLayoutData(rootCompositeData);
		
		sc.setContent(rootComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);*/

		
		/*// create plaintext/ciphertext content
		createPlainCipherContent(rootComposite);
		
		// create setting parameters group
		createSetParamContent(rootComposite);
		
		// create encryption/decryption group
		createEncDecContent(rootComposite);*/
		
		
		// create setting parameters group
		createSetParamContent(this);
		
		
		// create plaintext/ciphertext content
		createPlainCipherContent(this);
		
		// create encryption/decryption group
		createEncDecContent(this);

		initializeContent();
		
		//sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RabinFirstTabComposite(Composite parent, int style) {
		super(parent, style);
		createContent();
	}
	
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 * @param rootScrolledComposite
	 * @param rootComposite
	 */
	public RabinFirstTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
		super(parent, style);
		HandleFirstTab guiHandler = new HandleFirstTab(rootScrolledComposite, rootComposite, rabinFirst, rabinSecond);
		this.guiHandler = guiHandler;
		createContent();
	}
	
	
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public RabinFirstTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond) {
		super(parent, style);
		HandleFirstTab guiHandler = new HandleFirstTab(sc, rootComposite, rabinFirst, rabinSecond);
		this.guiHandler = guiHandler;
		createContent();
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
