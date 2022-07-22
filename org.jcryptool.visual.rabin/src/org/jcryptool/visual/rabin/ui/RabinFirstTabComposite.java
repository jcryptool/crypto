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

import java.awt.Font;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
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
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
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
import org.jcryptool.visual.rabin.Util;
import org.eclipse.swt.layout.GridData;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.auto.SmoothScroller; 

// App { 
//   var gui = GUI {
//     public btn = ???
//     public comp = ???
//     setLayout(..)
//   } 
// 	 var controller = new Controller(gui) {
//     ...
//     gui.btn.addSelectio(..)
//     
//   }
//   public void main {
//     controller.setListeners
//     gui.paint(isDarkMode)
//   }
// }

public class RabinFirstTabComposite extends Composite {
	public Text txtEnterText;
	public Combo cmbP;
	public Combo cmbQ;
	public Text txtModN;
	public Text txtLowLimP;
	public Text txtUpperLimP;
	public Text txtLowLimQ;
	public Text txtUpperLimQ;
	public Text txtChosenPlain;
	public Text txtEncDecStartWarning;
	public Text[][] plaintexts;
	public Text txtWarningNpq;
	public Text txtcompGenPandQWarning;
	public Text txtEnc;
	public Text txtChosenPlainInfo;
	
	public Group grpPlaintext;
	public Group grpParam;
	public Group grpEncDec;
	public Group grpMessages;
	
	public TextLoadController textSelector;
	
	public Label lblPrimeP;
	public Label lblPrimeQ;
	public Label lblModN;
	
	public Composite npqComp;
	public Group genPComp;
	public Group genQComp;
	public Composite paramMainComp;
	public Composite compGenKeys;
	public Composite compHoldEncDec;
	public Composite compPlaintexts;
	public Composite compHoldPlainAndText;
	public Composite rootComposite;

	
	public Button btnGenPrimes;
	public Button btnRadEnc;
	public Button btnRadDec;
	public Button btnEncDec;
	public Button btnGenKeysMan;
	public Button btnGenKeysAlgo;
	public Button btnEncDecStart;
	public Button btnStartGenKeys;
	public Button btnGenKeys;
	public Button btnUseKeysAlgo;
	
	public GridData grpMessagesData;
	
	
	
	
	
	
	
	public GridData txtWarningNpqData;
	
	public GridData txtcompGenPandQWarningData;
	public ScrolledComposite sc;

	public GridData txtChosenPlainData;
	
	public Text pWarning;
	public Text qWarning;
	public Text nWarning;
	
	
	public Text txtLowLimPQSingle;
	public Text txtUpperLimPQSingle;
	public Composite compSelectMultiPandQ;
	public Group grpSelectSinglePandQ;
	public Text txtSinglePQWarning;
	public Button btnSelectSingleLimit;
	public Button btnSelectMultiPandQ;
	
	
	public CryptosystemTextbookComposite cstb;
	public RabinSecondTabComposite rstc;
	public Button btnSelectCryptotb;
	public Button btnSelectCryptoSteps;
	
	public Text txtInfoSetParam;
	
	public Composite compHoldSelectionPrimesAndLimits;
	public Text txtInfoSelection;
	public Composite compSelectPrimeGen;
	public Label lblLowLimPQSingle;
	public Label lblUpperLimPQSingle;
	public Label lblSepInfoSetParam;
	public Label lblLowLimP;
	public Label lblUpperLim;
	public Label lblLowLimQ;
	public Label lblUpperLimQ;
	public Composite compSelectionCryptosystem;
	public Composite compSelection;
	public Label lblSeparateInfoTop;
	public Group grpInfoSelection;
	public Label lblSeparateInfoBottom;
	
	
	public Composite compTest;
	public StyledText txtInstructions;
	public Composite compHoldLayoutContent;
	public Label lblSepParamContentBottom;
	public Label lblSepSelectionCryptoBottom;
	public Composite compHoldSepAndInfoForCryptoSelection;
	public Label lblSepInfoForCryptoSelection;
	public Composite compHoldSepAndInfoForParam;

	
	
	
	

	
	public HandleFirstTab guiHandler;
	
	
	
	public RabinFirstTabComposite getCurrentInstance() {
		return this;
	}
	
	


	private VerifyListener vlNumbers = new VerifyListener() {
		
		/**
		 * is not used at the moment, maybe for future uses
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
	
	
	
	Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			guiHandler.btnStartGenKeysAction(getCurrentInstance(), rstc, 1000);
		}
	});
	
	
	
	
	public void setColors() {
		
		
		
		Color colorBG = guiHandler.getColorDarkModeBG();
		Color colorFG = guiHandler.getColorDarkModeFG();
		//Color colorFG = new Color(255, 0, 255);
		
		Color colorTxtWarningFG = guiHandler.getColorDarkModeWarningFG();
		Color colorButtonBG = guiHandler.getColorButtonsBG();
		Color colorButtonFG = guiHandler.getColorButtonsFG();
		Color colorTxtWhichYouCanEnterBG = ColorService.WHITE;
		Color colorTxtWhichYouCanEnterFG = ColorService.BLACK;
		
		//txtEnterText.setBackground(colorBG);
		//txtEnterText.setForeground(colorFG);
		cmbP.setBackground(colorTxtWhichYouCanEnterBG);
		cmbP.setForeground(colorTxtWhichYouCanEnterFG);
		cmbQ.setBackground(colorTxtWhichYouCanEnterBG);
		cmbQ.setForeground(colorTxtWhichYouCanEnterFG);
		txtModN.setBackground(colorBG);
		txtModN.setForeground(colorFG);
		txtLowLimP.setBackground(colorTxtWhichYouCanEnterBG);
		txtLowLimP.setForeground(colorTxtWhichYouCanEnterFG);
		txtUpperLimP.setBackground(colorTxtWhichYouCanEnterBG);
		txtUpperLimP.setForeground(colorTxtWhichYouCanEnterFG);
		txtLowLimQ.setBackground(colorTxtWhichYouCanEnterBG);
		txtLowLimQ.setForeground(colorTxtWhichYouCanEnterFG);
		txtUpperLimQ.setBackground(colorTxtWhichYouCanEnterBG);
		txtUpperLimQ.setForeground(colorTxtWhichYouCanEnterFG);
		txtcompGenPandQWarning.setBackground(colorBG);
		txtcompGenPandQWarning.setForeground(colorTxtWarningFG);
		grpParam.setBackground(colorBG);
		grpParam.setForeground(colorFG);
		lblPrimeP.setBackground(colorBG);
		lblPrimeP.setForeground(colorFG);
		lblPrimeQ.setBackground(colorBG);
		lblPrimeQ.setForeground(colorFG);
		lblModN.setBackground(colorBG);
		lblModN.setForeground(colorFG);
		npqComp.setBackground(colorBG);
		npqComp.setForeground(colorFG);
		genPComp.setBackground(colorBG);
		genPComp.setForeground(colorFG);
		genQComp.setBackground(colorBG);
		genQComp.setForeground(colorFG);
		compGenKeys.setBackground(colorBG);
		compGenKeys.setForeground(colorFG);
		btnGenKeysMan.setBackground(colorBG);
		btnGenKeysMan.setForeground(colorFG);
		btnStartGenKeys.setBackground(colorButtonBG);
		btnStartGenKeys.setForeground(colorButtonFG);
		
		btnGenKeys.setBackground(colorBG);
		btnGenKeys.setForeground(colorFG);
		pWarning.setBackground(colorBG);
		pWarning.setForeground(colorTxtWarningFG);
		qWarning.setBackground(colorBG);
		qWarning.setForeground(colorTxtWarningFG);
		nWarning.setBackground(colorBG);
		nWarning.setForeground(colorTxtWarningFG);
		txtLowLimPQSingle.setBackground(colorTxtWhichYouCanEnterBG);
		txtLowLimPQSingle.setForeground(colorTxtWhichYouCanEnterFG);
		txtUpperLimPQSingle.setBackground(colorTxtWhichYouCanEnterBG);
		txtUpperLimPQSingle.setForeground(colorTxtWhichYouCanEnterFG);
		compSelectMultiPandQ.setBackground(colorBG);
		compSelectMultiPandQ.setForeground(colorFG);
		grpSelectSinglePandQ.setBackground(colorBG);
		grpSelectSinglePandQ.setForeground(colorFG);
		txtSinglePQWarning.setBackground(colorBG);
		txtSinglePQWarning.setForeground(colorTxtWarningFG);
		btnSelectSingleLimit.setBackground(colorBG);
		btnSelectSingleLimit.setForeground(colorFG);
		btnSelectMultiPandQ.setBackground(colorBG);
		btnSelectMultiPandQ.setForeground(colorFG);
		btnSelectCryptotb.setBackground(colorBG);
		btnSelectCryptotb.setForeground(colorFG);
		btnSelectCryptoSteps.setBackground(colorBG);
		btnSelectCryptoSteps.setForeground(colorFG);
		txtInfoSetParam.setBackground(colorBG);
		txtInfoSetParam.setForeground(colorFG);
		compHoldSelectionPrimesAndLimits.setBackground(colorBG);
		compHoldSelectionPrimesAndLimits.setForeground(colorFG);
		txtInfoSelection.setBackground(colorBG);
		txtInfoSelection.setForeground(colorFG);
		compSelectPrimeGen.setBackground(colorBG);
		compSelectPrimeGen.setForeground(colorFG);
		lblLowLimPQSingle.setBackground(colorBG);
		lblLowLimPQSingle.setForeground(colorFG);
		lblUpperLimPQSingle.setBackground(colorBG);
		lblUpperLimPQSingle.setForeground(colorFG);
		lblSepInfoSetParam.setBackground(colorBG);
		lblSepInfoSetParam.setForeground(colorFG);
		lblLowLimP.setBackground(colorBG);
		lblLowLimP.setForeground(colorFG);
		lblUpperLim.setBackground(colorBG);
		lblUpperLim.setForeground(colorFG);
		lblLowLimQ.setBackground(colorBG);
		lblLowLimQ.setForeground(colorFG);
		lblUpperLimQ.setBackground(colorBG);
		lblUpperLimQ.setForeground(colorFG);
		compSelectionCryptosystem.setBackground(colorBG);
		compSelectionCryptosystem.setForeground(colorFG);
		compSelection.setBackground(colorBG);
		compSelection.setForeground(colorFG);		
		compHoldLayoutContent.setBackground(colorBG);
		compHoldLayoutContent.setForeground(colorFG);
		lblSepParamContentBottom.setBackground(colorBG);
		lblSepParamContentBottom.setForeground(colorFG);
		lblSepSelectionCryptoBottom.setBackground(colorBG);
		lblSepSelectionCryptoBottom.setForeground(colorFG);
		compHoldSepAndInfoForCryptoSelection.setBackground(colorBG);
		compHoldSepAndInfoForCryptoSelection.setForeground(colorFG);
		lblSepInfoForCryptoSelection.setBackground(colorBG);
		lblSepInfoForCryptoSelection.setForeground(colorFG);
		compHoldSepAndInfoForParam.setBackground(colorBG);
		compHoldSepAndInfoForParam.setForeground(colorFG);
		
	}
	
	
	
	
		
	
	/**
	 * initialize the content when starting the plugin
	 */
	private void initializeContent() {
		cmbP.setText("23"); //$NON-NLS-1$
		cmbQ.setText("31"); //$NON-NLS-1$
		txtLowLimP.setText("1"); //$NON-NLS-1$
		txtUpperLimP.setText("2^5"); //$NON-NLS-1$
		txtLowLimQ.setText("1"); //$NON-NLS-1$
		txtUpperLimQ.setText("2^10"); //$NON-NLS-1$
		txtLowLimPQSingle.setText("1"); //$NON-NLS-1$
		txtUpperLimPQSingle.setText("2^10"); //$NON-NLS-1$
		btnSelectSingleLimit.setSelection(true);
		btnGenKeysMan.setSelection(true);
		guiHandler.initializePrimes(20, getCurrentInstance());
		btnSelectCryptotb.setSelection(true);
		guiHandler.hideControl(compHoldSelectionPrimesAndLimits);
		guiHandler.hideControl(compSelectPrimeGen);
	}
	
	
	
	
	
	
	/**
	 * create group "Setting Paramters" and its content
	 * @param parent
	 */
	private void createSetParamContent(Composite parent) {
		
		// create group for setting parameters
		grpParam = new Group(parent, SWT.NONE);
		grpParam.setLayout(new GridLayout(2, false));
		grpParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpParam.setText(Messages.RabinFirstTabComposite_grpParam);
		//((GridData) grpParam.getLayoutData()).widthHint = 800;
		
		
		
		// create composite for setting p, q, N manually
		npqComp = new Composite(grpParam, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		npqComp.setLayout(new GridLayout(2, false));
		
			

		
		// create label for prime p in npq composite
		lblPrimeP = new Label(npqComp, SWT.NONE);
		lblPrimeP.setText("p ="); //$NON-NLS-1$
		
		// test creating composite for border color		
		
		cmbP = new Combo(npqComp, SWT.BORDER);
		//txtP = new Text(compBorderColorP, SWT.BORDER);
		GridData txtPData = new GridData(SWT.FILL, SWT.FILL, true, false);
		cmbP.setLayoutData(txtPData);
		guiHandler.setSizeControl(cmbP, SWT.DEFAULT, SWT.DEFAULT);
		cmbP.addModifyListener(mlpq);

		// LAMBDA!! :)
//		cmbP.addSelectionListener(Util.listenToClick((SelectionEvent e) -> { 
//			guiHandler.cmbSelectionAction(cmbP);
//		}));
		
		cmbP.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbSelectionAction(cmbP);
			}
			
		});
		
		
		pWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		pWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) pWarning.getLayoutData()).horizontalIndent = 28;
		pWarning.setText(Messages.RabinFirstTabComposite_pWarning);
		pWarning.setBackground(guiHandler.getColorBackgroundWarning());
		pWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(pWarning, SWT.DEFAULT, SWT.DEFAULT);
		
		// test setSizeControl instead of setSizeControlWarning to check whether
		// the space at the bottom of the plugin will be removed
		guiHandler.hideControl(pWarning);
		
		
		
		
		// create label for prime q in npq composite
		lblPrimeQ = new Label(npqComp, SWT.NONE);
		lblPrimeQ.setText("q ="); //$NON-NLS-1$
		cmbQ = new Combo(npqComp, SWT.BORDER);
		GridData txtQData = new GridData(SWT.FILL, SWT.FILL, true, false);
		cmbQ.setLayoutData(txtQData);
		guiHandler.setSizeControl(cmbQ, SWT.DEFAULT, SWT.DEFAULT);
		cmbQ.addModifyListener(mlpq);
		cmbQ.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbSelectionAction(cmbQ);
			}
			
		});
		
		
		qWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		qWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) qWarning.getLayoutData()).horizontalIndent = 28;
		qWarning.setText(Messages.RabinFirstTabComposite_qWarning);
		qWarning.setBackground(guiHandler.getColorBackgroundWarning());
		qWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(qWarning, SWT.DEFAULT, SWT.DEFAULT);
		
		// same as above with pWarning
		guiHandler.hideControl(qWarning);
		
		
		// create label for N in npq composite
		lblModN = new Label(npqComp, SWT.NONE);
		lblModN.setText("N ="); //$NON-NLS-1$
		txtModN = new Text(npqComp, SWT.READ_ONLY | SWT.BORDER);
		GridData txtModNData = new GridData(SWT.FILL, SWT.FILL, true, false);
		txtModN.setLayoutData(txtModNData);
		guiHandler.setSizeControl(txtModN, SWT.DEFAULT, SWT.DEFAULT);
				
		txtModN.setBackground(ColorService.LIGHTGRAY);
		txtModN.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtModN, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		nWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP);
		nWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) nWarning.getLayoutData()).horizontalIndent = 28;
		nWarning.setText(Messages.RabinFirstTabComposite_nWarning);
		nWarning.setBackground(guiHandler.getColorBackgroundWarning());
		nWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(nWarning, SWT.DEFAULT, SWT.DEFAULT);
		
		// same as above
		guiHandler.hideControl(nWarning);
		
		
		
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
				guiHandler.btnGenKeysManAction(getCurrentInstance(), e);
			}
		});
		
		btnGenKeys = new Button(compGenKeys, SWT.RADIO | SWT.WRAP | SWT.BORDER);
		btnGenKeys.setText(Messages.RabinFirstTabComposite_btnGenKeys);
		btnGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(btnGenKeys, SWT.DEFAULT, 35);
		
		btnGenKeys.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysAction(getCurrentInstance(), e);
			}
		});
		
			
		btnStartGenKeys = new Button(compGenKeys, SWT.PUSH);
		GridData btnStartGenData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
		btnStartGenData.horizontalIndent = 5;
		btnStartGenData.verticalIndent = 5;
		btnStartGenKeys.setLayoutData(btnStartGenData);
		guiHandler.setSizeControl(btnStartGenKeys, 100, 30);
		btnStartGenKeys.setText(Messages.RabinFirstTabComposite_btnStartGenKeys);
		
		btnStartGenKeys.addSelectionListener(new SelectionAdapter() {		
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnStartGenKeysAction(getCurrentInstance(), rstc, 1000);				
			}			
		});
			
		
		compHoldSelectionPrimesAndLimits = new Composite(grpParam, SWT.NONE);
		compHoldSelectionPrimesAndLimits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		compHoldSelectionPrimesAndLimits.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldSelectionPrimesAndLimits, 0, 0);
		
		
		compSelectPrimeGen = new Composite(grpParam, SWT.NONE);
		compSelectPrimeGen.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		
		
		
		// create text warning for npqComp
		
		grpSelectSinglePandQ = new Group(compHoldSelectionPrimesAndLimits, SWT.NONE);
		grpSelectSinglePandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpSelectSinglePandQ.setLayout(new GridLayout(2, false));
		grpSelectSinglePandQ.setText(Messages.RabinFirstTabComposite_grpSelectSinglePandQ);
		
		lblLowLimPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
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
		
		
		
		
		lblUpperLimPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
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
		
		
		
		txtSinglePQWarning = new Text(grpSelectSinglePandQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtSinglePQWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) txtSinglePQWarning.getLayoutData()).horizontalIndent = 80;
		txtSinglePQWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtSinglePQWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.setSizeControlWarning(txtSinglePQWarning, SWT.DEFAULT, SWT.DEFAULT);
		
		// same as above
		guiHandler.hideControl(txtSinglePQWarning);
		
		
		compSelectMultiPandQ = new Composite(compHoldSelectionPrimesAndLimits, SWT.NONE);
		compSelectMultiPandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelectMultiPandQ.setLayout(new GridLayout(2, false));
		guiHandler.setControlMargin(compSelectMultiPandQ, 0, 0);
		guiHandler.hideControl(compSelectMultiPandQ);
		
		
		
		
		// create composite for entering lower and upper limit for 
		// prime p
		genPComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genPComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genPComp.setLayout(new GridLayout(2, false));
		genPComp.setText(Messages.RabinFirstTabComposite_genPComp);
		
		// create composite for entering lower and upper limit for 
		// prime q
		genQComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genQComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genQComp.setLayout(new GridLayout(2, false));
		genQComp.setText(Messages.RabinFirstTabComposite_genQComp);
		
		
		txtcompGenPandQWarning = new Text(compSelectMultiPandQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtcompGenPandQWarningData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtcompGenPandQWarning.setLayoutData(txtcompGenPandQWarningData);
		guiHandler.setSizeControlWarning(txtcompGenPandQWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtcompGenPandQWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtcompGenPandQWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtcompGenPandQWarning);
		
		
		compHoldSepAndInfoForParam = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoForParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoForParam.setLayout(new GridLayout(2, false));
		((GridData) compHoldSepAndInfoForParam.getLayoutData()).widthHint = 400;
		guiHandler.setControlMargin(compHoldSepAndInfoForParam, 5, 0);
		
		
		
		lblSepInfoSetParam = new Label(compHoldSepAndInfoForParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoSetParam.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
			
		
		txtInfoSetParam = new Text(compHoldSepAndInfoForParam, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoSetParamData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoSetParam.setLayoutData(txtInfoSetParamData);
		guiHandler.setSizeControl(txtInfoSetParam, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSetParam.setText(guiHandler.getMessageByControl("btnGenKeysMan_selection"));
		txtInfoSetParam.setBackground(ColorService.LIGHTGRAY);
		txtInfoSetParam.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				guiHandler.setCursorControl(txtInfoSetParam, SWT.CURSOR_ARROW);
			}
		});
		
		
		lblLowLimP = new Label(genPComp, SWT.NONE);
		lblLowLimP.setText(strLowerLimit);
		txtLowLimP = new Text(genPComp, SWT.BORDER);
		txtLowLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtLowLimP, SWT.DEFAULT, SWT.DEFAULT);
		
		
		txtLowLimP.addModifyListener(mlLimit);
		
		
		
		lblUpperLim = new Label(genPComp, SWT.NONE);
		lblUpperLim.setText(strUpperLimit);
		txtUpperLimP = new Text(genPComp, SWT.BORDER);
		txtUpperLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimP, SWT.DEFAULT, SWT.DEFAULT);
		
		txtUpperLimP.addModifyListener(mlLimit);
		
		
		
		// create label for prime q
		
		lblLowLimQ = new Label(genQComp, SWT.NONE);
		lblLowLimQ.setText(strLowerLimit);
		txtLowLimQ = new Text(genQComp, SWT.BORDER);
		txtLowLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtLowLimQ, SWT.DEFAULT, SWT.DEFAULT);
		
		txtLowLimQ.addModifyListener(mlLimit);
		
		
		
		lblUpperLimQ = new Label(genQComp, SWT.NONE);
		lblUpperLimQ.setText(strUpperLimit);
		txtUpperLimQ = new Text(genQComp, SWT.BORDER);
		txtUpperLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimQ, SWT.DEFAULT, SWT.DEFAULT);
	
		
		txtUpperLimQ.addModifyListener(mlLimit);	
		
		
		guiHandler.setSizeControlWarning(grpParam, 840, SWT.DEFAULT);
	
	}
	
	
	
	
	
	
	private void createSelectionCryptosystemContent(Composite parent) {
		
		compSelectionCryptosystem = new Composite(parent, SWT.NONE);
		compSelectionCryptosystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelectionCryptosystem.setLayout(new GridLayout(1, false));
		
		
		compSelection = new Composite(compSelectionCryptosystem, SWT.NONE);
		compSelection.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		compSelection.setLayout(new GridLayout(2, false));
		
			
		
		btnSelectCryptotb = new Button(compSelection, SWT.RADIO);
		btnSelectCryptotb.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnSelectCryptotb.setText("Cryptosystem in textbook format");
		btnSelectCryptotb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.hideStepByStepPart(rstc);
				guiHandler.showTextbookPart(cstb);
	
				
				txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection_textbook"));
				guiHandler.showControl(txtInfoSelection);
				
			}
		});
		
		btnSelectCryptoSteps = new Button(compSelection, SWT.RADIO);
		btnSelectCryptoSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnSelectCryptoSteps.setText("Cryptosystem step by step");
		btnSelectCryptoSteps.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideTextbookPart(cstb);
				guiHandler.showStepByStepPart(rstc);
				txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection_steps"));
				guiHandler.showControl(txtInfoSelection);
			}
		});
		
		
		
		compHoldSepAndInfoForCryptoSelection = new Composite(parent, SWT.NONE);
		compHoldSepAndInfoForCryptoSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSepAndInfoForCryptoSelection.setLayout(new GridLayout(2, false));
		
		lblSepInfoForCryptoSelection = new Label(compHoldSepAndInfoForCryptoSelection, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoForCryptoSelection.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		
		txtInfoSelection = new Text(compHoldSepAndInfoForCryptoSelection, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtInfoSelection, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSelection.setBackground(guiHandler.getColorBGinfo());
		txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection_textbook"));
		txtInfoSelection.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				guiHandler.setCursorControl(txtInfoSelection, SWT.CURSOR_ARROW);
			}
		});			
	}
	
	
	
	private void createInfoForSelectionContent(Composite parent) {
		
		lblSeparateInfoTop = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparateInfoTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		grpInfoSelection = new Group(parent, SWT.NONE);
		grpInfoSelection.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		grpInfoSelection.setLayout(new GridLayout(1, false));
		grpInfoSelection.setText("Info");
		
		
		
		txtInfoSelection = new Text(grpInfoSelection, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControlWarning(txtInfoSelection, SWT.DEFAULT + 400, SWT.DEFAULT);
		txtInfoSelection.setBackground(guiHandler.getColorBGinfo());
		txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection_textbook"));
		txtInfoSelection.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				guiHandler.setCursorControl(txtInfoSelection, SWT.CURSOR_ARROW);
			}
		});
		
		
		lblSeparateInfoBottom = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparateInfoBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
	}
	

	
	
	/**
	 * create the content of the Cryptosystem tab
	 */
	private void createContent(ScrolledComposite sc, Composite rootComposite) {
		setLayout(new GridLayout(1, false));
		
		compHoldLayoutContent = new Composite(this, SWT.NONE);
		compHoldLayoutContent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
		compHoldLayoutContent.setLayout(new GridLayout(2, false));
		
		
		
		createSetParamContent(compHoldLayoutContent);
		
		
		lblSepParamContentBottom = new Label(compHoldLayoutContent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepParamContentBottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		createSelectionCryptosystemContent(compHoldLayoutContent);
		
			
		lblSepSelectionCryptoBottom = new Label(compHoldLayoutContent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepSelectionCryptoBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		
		cstb = new CryptosystemTextbookComposite(compHoldLayoutContent, SWT.NONE, this);
		cstb.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cstb.setLayout(new GridLayout(1, false));
		guiHandler.hideControl(cstb);
		
		
		
		
		
		rstc = new RabinSecondTabComposite(compHoldLayoutContent, SWT.NONE, guiHandler.getRabinFirst(), guiHandler.getRabinSecond(), sc, rootComposite);
		rstc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rstc.setLayout(new GridLayout(1, false));
		guiHandler.hideControl(rstc);
		
		guiHandler.hideStepByStepPart(rstc);
		
		
		
		if(guiHandler.getDarkmode())
			setColors();
		
		initializeContent();
		
		
		
		
	}
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RabinFirstTabComposite(Composite parent, int style) {
		super(parent, style);
		//createContent();
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
		createContent(rootScrolledComposite, rootComposite);
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
		//createContent();
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
