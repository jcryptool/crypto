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
	private Text txtEnterText;
	private Combo cmbP;
	private Combo cmbQ;
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
	
	
	private CryptosystemTextbookComposite cstb;
	private RabinSecondTabComposite rstc;
	private Button btnSelectCryptotb;
	private Button btnSelectCryptoSteps;
	
	private Text txtInfoSetParam;
	
	private Composite compHoldSelectionPrimesAndLimits;
	private Text txtInfoSelection;
	private Composite compSelectPrimeGen;
	private Label lblLowLimPQSingle;
	private Label lblUpperLimPQSingle;
	private Label lblSepInfoSetParam;
	private Label lblLowLimP;
	private Label lblUpperLim;
	private Label lblLowLimQ;
	private Label lblUpperLimQ;
	private Composite compSelectionCryptosystem;
	private Composite compSelection;
	private Label lblSeparateInfoTop;
	private Group grpInfoSelection;
	private Label lblSeparateInfoBottom;
	
	
	
	

	
	private HandleFirstTab guiHandler;
	
	
	
	public RabinSecondTabComposite getRabinSecondTabComposite() {
		return rstc;
	}
	
	
	public CryptosystemTextbookComposite getCryptosystemTextbookComposite() {
		return cstb;
	}
	
	
	public HandleFirstTab getGUIHandler() {
		return guiHandler;
	}
	
	
	
	public Composite getCompHoldSelectionPrimesAndLimits() {
		return compHoldSelectionPrimesAndLimits;
	}
	
	
	
	
	public Text getTxtInfoSetParam() {
		return txtInfoSetParam;
	}
	
	
	
	
	
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
	public Combo getCmbP() {
		return cmbP;
	}



	/**
	 * @return the txtQ
	 */
	public Combo getCmbQ() {
		return cmbQ;
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
	private Composite compTest;
	private StyledText txtInstructions;
	
	
	
	
	private void setColors() {
		
		
		
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
		//txtChosenPlain.setBackground(colorBG);
		//txtChosenPlain.setForeground(colorFG);
		//txtEncDecStartWarning.setBackground(colorBG);
		//txtEncDecStartWarning.setForeground(colorFG);
		//txtWarningNpq.setBackground(colorBG);
		//txtWarningNpq.setForeground(colorFG);
		txtcompGenPandQWarning.setBackground(colorBG);
		txtcompGenPandQWarning.setForeground(colorTxtWarningFG);
		//txtEnc.setBackground(colorBG);
		//txtEnc.setForeground(colorFG);
		//txtChosenPlainInfo.setBackground(colorBG);
		//txtChosenPlainInfo.setForeground(colorFG);
		//grpPlaintext.setBackground(colorBG);
		//grpPlaintext.setForeground(colorFG);
		grpParam.setBackground(colorBG);
		grpParam.setForeground(colorFG);
		//grpEncDec.setBackground(colorBG);
		//grpEncDec.setForeground(colorFG);
		//grpMessages.setBackground(colorBG);
		//grpMessages.setForeground(colorFG);
		//textSelector.setBackground(colorBG);
		//textSelector.setForeground(colorFG);
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
		paramMainComp.setBackground(colorBG);
		paramMainComp.setForeground(colorFG);
		compGenKeys.setBackground(colorBG);
		compGenKeys.setForeground(colorFG);
		//compHoldEncDec.setBackground(colorBG);
		//compHoldEncDec.setForeground(colorFG);
		//compPlaintexts.setBackground(colorBG);
		//compPlaintexts.setForeground(colorFG);
		//compHoldPlainAndText.setBackground(colorBG);
		//compHoldPlainAndText.setForeground(colorFG);
		//rootComposite.setBackground(colorBG);
		//rootComposite.setForeground(colorFG);
		//btnGenPrimes.setBackground(colorBG);
		//btnGenPrimes.setForeground(colorFG);
		//btnRadEnc.setBackground(colorBG);
		//btnRadEnc.setForeground(colorFG);
		//btnRadDec.setBackground(colorBG);
		//btnRadDec.setForeground(colorFG);
		//btnEncDec.setBackground(colorBG);
		//btnEncDec.setForeground(colorFG);
		btnGenKeysMan.setBackground(colorBG);
		btnGenKeysMan.setForeground(colorFG);
		//btnGenKeysAlgo.setBackground(colorBG);
		//btnGenKeysAlgo.setForeground(colorFG);
		//btnEncDecStart.setBackground(colorBG);
		//btnEncDecStart.setForeground(colorFG);
		btnStartGenKeys.setBackground(colorButtonBG);
		//btnStartGenKeys.setForeground(colorBG);
		btnStartGenKeys.setForeground(colorButtonFG);
		
		btnGenKeys.setBackground(colorBG);
		btnGenKeys.setForeground(colorFG);
		//btnUseKeysAlgo.setBackground(colorBG);
		//btnUseKeysAlgo.setForeground(colorFG);
		//sc.setBackground(colorBG);
		//sc.setForeground(colorFG);
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
		//cstb.setBackground(colorBG);
		//cstb.setForeground(colorFG);
		//rstc.setBackground(colorBG);
		//rstc.setForeground(colorFG);
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
		lblSeparateInfoTop.setBackground(colorBG);
		lblSeparateInfoTop.setForeground(colorFG);
		grpInfoSelection.setBackground(colorBG);
		grpInfoSelection.setForeground(colorFG);
		lblSeparateInfoBottom.setBackground(colorBG);
		lblSeparateInfoBottom.setForeground(colorFG);
		/*compTest.setBackground(colorBG);
		compTest.setForeground(colorFG);
		txtInstructions.setBackground(colorBG);
		txtInstructions.setForeground(colorFG);*/
		
	}
	
	
	
	
		
	
	/**
	 * initialize the content when starting the plugin
	 */
	private void initializeContent() {
		cmbP.setText("23"); //$NON-NLS-1$
		cmbQ.setText("31"); //$NON-NLS-1$
		//txtP.setBackground(ColorService.LIGHT_AREA_RED);
		//txtQ.setBackground(ColorService.LIGHT_AREA_GREEN);
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
		//btnRadEnc.setSelection(true);
		//btnEncDecStart.setEnabled(false);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * create group "Plaintext" with its content
	 * @param parent
	 */
	/*private void createPlainCipherContent(Composite parent) {
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
		//Label lblDescChooseText = new Label(grpPlaintext, SWT.NONE);
		//GridData descChooseTextData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//lblDescChooseText.setLayoutData(descChooseTextData);
		//lblDescChooseText.setText(Messages.RabinFirstTabComposite_lblDescChooseText);
		
		Text txtInfoChoosePlain = new Text(grpPlaintext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoChoosePlain.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		txtInfoChoosePlain.setText(Messages.RabinFirstTabComposite_lblDescChooseText);
		guiHandler.setSizeControl(txtInfoChoosePlain, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoChoosePlain.setBackground(ColorService.LIGHTGRAY);
	}*/
	
	
	
	
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
		//paramMainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		paramMainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		paramMainComp.setLayout(new GridLayout(2, false));
		
		// create composite for setting p, q, N manually
		npqComp = new Composite(paramMainComp, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		npqComp.setLayout(new GridLayout(2, false));
		//guiHandler.setSizeControl(npqComp, npqComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 100);
		
		
		compHoldSelectionPrimesAndLimits = new Composite(paramMainComp, SWT.NONE);
		//compHoldSelectionPrimesAndLimits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		compHoldSelectionPrimesAndLimits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		//guiHandler.setSizeControl(compHoldSelectionPrimesAndLimits, 800, 400);
		compHoldSelectionPrimesAndLimits.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldSelectionPrimesAndLimits, 0, 0);
		//guiHandler.setSizeControl(compHoldSelectionPrimesAndLimits, npqComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 1000);
		//guiHandler.hideControl(compHoldSelectionPrimesAndLimits);
		
		
		compSelectPrimeGen = new Composite(compHoldSelectionPrimesAndLimits, SWT.NONE);
		//compSelectPrimeGen.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1));
		compSelectPrimeGen.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
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
		
		// test creating composite for border color
		/*Composite compBorderColorP = new Composite(npqComp, SWT.BORDER);
		compBorderColorP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compBorderColorP.setLayout(new GridLayout(1, false));
		compBorderColorP.setBackground(ColorService.LIGHT_AREA_BLUE);
		((GridLayout) compBorderColorP.getLayout()).marginHeight = 0;
		((GridLayout) compBorderColorP.getLayout()).marginWidth = 0;*/
		
		
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
		//guiHandler.setSizeControl(pWarning, SWT.DEFAULT, SWT.DEFAULT);
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
		//guiHandler.setSizeControl(qWarning, SWT.DEFAULT, SWT.DEFAULT);
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
				//guiHandler.txtModNActionFirstTab(txtModN, btnEncDecStart);
				//guiHandler.updateEncryptButton(txtModN, cstb.getBtnEncrypt(), cstb.getTextSelector());
			}
		});
		
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
		//guiHandler.setSizeControl(nWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(nWarning);
		
		
		
		// create text warning for npqComp
		
		/*txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtWarningNpq.setLayoutData(txtWarningNpqData);
		guiHandler.setSizeControlWarning(txtWarningNpq, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
		txtWarningNpq.setForeground(ColorService.RED);
		guiHandler.hideControl(txtWarningNpq);*/
		
		//grpSelectSinglePandQ = new Group(paramMainComp, SWT.NONE);
		grpSelectSinglePandQ = new Group(compHoldSelectionPrimesAndLimits, SWT.NONE);
		grpSelectSinglePandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpSelectSinglePandQ.setLayout(new GridLayout(2, false));
		grpSelectSinglePandQ.setText(Messages.RabinFirstTabComposite_grpSelectSinglePandQ);
		//guiHandler.hideControl(grpSelectSinglePandQ);
		
		/*Label lblGenPQSingle = new Label(grpSelectSinglePandQ, SWT.NONE);
		lblGenPQSingle.setText("Prime number p and q");
		GridData GenPQSingledata = new GridData();
		GenPQSingledata.horizontalSpan = 2;
		lblGenPQSingle.setLayoutData(GenPQSingledata);*/
		
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
		//guiHandler.setSizeControlTest(txtSinglePQWarning, SWT.DEFAULT, SWT.DEFAULT);
		guiHandler.hideControl(txtSinglePQWarning);
		
		
		
		

		//compSelectMultiPandQ = new Composite(paramMainComp, SWT.NONE);
		compSelectMultiPandQ = new Composite(compHoldSelectionPrimesAndLimits, SWT.NONE);
		compSelectMultiPandQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelectMultiPandQ.setLayout(new GridLayout(2, false));
		guiHandler.hideControl(compSelectMultiPandQ);
		
		
		
		
		// create composite for entering lower and upper limit for 
		// prime p
		//genPComp = new Composite(paramMainComp, SWT.NONE);
		genPComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genPComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//genPComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		genPComp.setLayout(new GridLayout(2, false));
		genPComp.setText(Messages.RabinFirstTabComposite_genPComp);
		
		// create composite for entering lower and upper limit for 
		// prime q
		//genQComp = new Composite(paramMainComp, SWT.NONE);
		genQComp = new Group(compSelectMultiPandQ, SWT.NONE);
		genQComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//genQComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		genQComp.setLayout(new GridLayout(2, false));
		genQComp.setText(Messages.RabinFirstTabComposite_genQComp);
		
		
		txtcompGenPandQWarning = new Text(compSelectMultiPandQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtcompGenPandQWarningData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtcompGenPandQWarning.setLayoutData(txtcompGenPandQWarningData);
		guiHandler.setSizeControlWarning(txtcompGenPandQWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtcompGenPandQWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtcompGenPandQWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtcompGenPandQWarning);
		
		//guiHandler.setSizeControl(npqComp, compHoldSelectionPrimesAndLimits.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, SWT.DEFAULT);
		//guiHandler.setSizeControl(compHoldSelectionPrimesAndLimits, npqComp.getSize().x, SWT.DEFAULT);

		
		
		// create composite for how to generate the keys
		compGenKeys = new Composite(grpParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		((GridLayout) compGenKeys.getLayout()).marginTop = 30;
		
		// create radio button for generating keys manually 
		btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysMan.setText(Messages.RabinFirstTabComposite_btnGenKeysMan);
		//btnGenKeysMan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//guiHandler.setSizeControl(btnGenKeysMan, SWT.DEFAULT, SWT.DEFAULT);
		
		
		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysManAction(getCurrentInstance(), e);
				//guiHandler.hideControl(compHoldSelectionPrimesAndLimits);
				//guiHandler.updateTextfields(getCurrentInstance());
			}
		});
		
		btnGenKeys = new Button(compGenKeys, SWT.RADIO | SWT.WRAP | SWT.BORDER);
		btnGenKeys.setText(Messages.RabinFirstTabComposite_btnGenKeys);
		btnGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(btnGenKeys, SWT.DEFAULT, 35);
		
		btnGenKeys.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//guiHandler.showControl(compHoldSelectionPrimesAndLimits);
				guiHandler.btnGenKeysAction(getCurrentInstance(), e);
			}
		});
		
		
		
		/**
		 * btnUseKeysAlgo is eventually not used in this version
		 */
		
		/*btnUseKeysAlgo = new Button(compGenKeys, SWT.RADIO);
		btnUseKeysAlgo.setText(Messages.RabinFirstTabComposite_btnUseKeysAlgo);
		btnUseKeysAlgo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compHoldSelectionPrimesAndLimits);
				guiHandler.btnUseKeysAlgoAction(getCurrentInstance(), e);
			}
		});*/
		
		
		/*Composite compForStartAndStopBtn = new Composite(compGenKeys, SWT.NONE);
		compForStartAndStopBtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		compForStartAndStopBtn.setLayout(new GridLayout(2, false));
		guiHandler.setControlMargin(compForStartAndStopBtn, 0, SWT.DEFAULT);
		*/
		
		
		
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
				
			
				// TODO Auto-generated method stub
				guiHandler.btnStartGenKeysAction(getCurrentInstance(), rstc, 1000);
						
				
				//guiHandler.btnStartGenKeysAction(getCurrentInstance(), rstc, 1000);
			}
			
		});
		
		
		
		/*Button btnStopComputation = new Button(compForStartAndStopBtn, SWT.PUSH);
		btnStopComputation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnStopComputation.setText("Stop computation");
		btnStopComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				//guiHandler.setStopComputation(true);
				
			}
		});*/
		
		
		
		
		lblSepInfoSetParam = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoSetParam.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		
		/*Group grpTest = new Group(grpParam, SWT.NONE);
		grpTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpTest.setLayout(new GridLayout(1, false));
		grpTest.setText("Instructions");*/
		
		
		/*compTest = new Composite(grpParam, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compTest.setLayout(new GridLayout(1, false));*/
		//compTest.setBackground(guiHandler.getColorDarkModeBG());
		//compTest.setForeground(guiHandler.getColorDarkModeFG());
		
		
		/*Label lblInstructions = new Label(compTest, SWT.NONE);
		lblInstructions.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblInstructions.setText("Instructions");
		lblInstructions.setFont(new org.eclipse.swt.graphics.Font(getDisplay(), "test", 10, SWT.BOLD | SWT.UNDERLINE_DOUBLE));
		lblInstructions.setBackground(guiHandler.getColorDarkModeBG());
		lblInstructions.setForeground(guiHandler.getColorDarkModeFG());*/
		
		/*txtInstructions = new StyledText(compTest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInstructions.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtInstructions.setText("Instructions");
		txtInstructions.setFont(new org.eclipse.swt.graphics.Font(getDisplay(), "test", 10, SWT.BOLD));
		StyleRange styleInstructions = new StyleRange();
		styleInstructions.start = 0;
		styleInstructions.length = txtInstructions.getText().length();
		styleInstructions.underline = true;
		txtInstructions.setStyleRange(styleInstructions);
		
		txtInstructions.setBackground(guiHandler.getColorBGinfo());*/
		//txtInstructions.setForeground(guiHandler.getColorDarkModeFG());
		
		
		
		
		txtInfoSetParam = new Text(grpParam, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoSetParamData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoSetParam.setLayoutData(txtInfoSetParamData);
		guiHandler.setSizeControl(txtInfoSetParam, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSetParam.setText(guiHandler.getMessageByControl("btnGenKeysMan_selection"));
		txtInfoSetParam.setBackground(ColorService.LIGHTGRAY);
		txtInfoSetParam.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoSetParam, SWT.CURSOR_ARROW);
			}
		});
		
		
		
		/*txtInfoSetParam = new Text(grpTest, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridData txtInfoSetParamData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoSetParam.setLayoutData(txtInfoSetParamData);
		guiHandler.setSizeControl(txtInfoSetParam, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSetParam.setText(guiHandler.getMessageByControl("btnGenKeysMan_selection"));
		txtInfoSetParam.setBackground(ColorService.LIGHTGRAY);*/
		
		
		
		// create label for prime number p
		/*Label lblGenP = new Label(genPComp, SWT.NONE);
		lblGenP.setText("Prime number p");
		GridData GenPdata = new GridData();
		GenPdata.horizontalSpan = 2;
		lblGenP.setLayoutData(GenPdata);*/
		
		lblLowLimP = new Label(genPComp, SWT.NONE);
		lblLowLimP.setText(strLowerLimit);
		txtLowLimP = new Text(genPComp, SWT.BORDER);
		txtLowLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//txtLowLimP.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));
		guiHandler.setSizeControl(txtLowLimP, SWT.DEFAULT, SWT.DEFAULT);
		//guiHandler.setSizeControl(txtLowLimP, 20, SWT.DEFAULT);
		
		
		txtLowLimP.addModifyListener(mlLimit);
		
		
		
		lblUpperLim = new Label(genPComp, SWT.NONE);
		lblUpperLim.setText(strUpperLimit);
		txtUpperLimP = new Text(genPComp, SWT.BORDER);
		//txtUpperLimP.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, false));
		//guiHandler.setSizeControl(txtUpperLimP, 20, SWT.DEFAULT);
		txtUpperLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		guiHandler.setSizeControl(txtUpperLimP, SWT.DEFAULT, SWT.DEFAULT);
		
		txtUpperLimP.addModifyListener(mlLimit);
		
		
		
		// create label for prime q
		/*Label lblGenQ = new Label(genQComp, SWT.NONE);
		lblGenQ.setText("Prime number q");
		GridData GenQdata = new GridData();
		GenQdata.horizontalSpan = 2;
		lblGenQ.setLayoutData(GenPdata);*/
		
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
		
		
		//guiHandler.setSizeControl(compHoldSelectionPrimesAndLimits, SWT.DEFAULT + 400, SWT.DEFAULT);
		//guiHandler.setSizeControl(compHoldSelectionPrimesAndLimits, 500, SWT.DEFAULT);
		//guiHandler.setSizeControl(paramMainComp, 30, SWT.DEFAULT);
		guiHandler.setSizeControlWarning(paramMainComp, SWT.DEFAULT, SWT.DEFAULT);

	}
	
	
	
	
	
	
	private void createSelectionCryptosystemContent(Composite parent) {
		
		compSelectionCryptosystem = new Composite(parent, SWT.NONE);
		compSelectionCryptosystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compSelectionCryptosystem.setLayout(new GridLayout(2, false));
		((GridLayout) compSelectionCryptosystem.getLayout()).marginHeight = 40;
		
		//Label lblSeparateInfoTop = new Label(compSelectionCryptosystem, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSeparateInfoTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		
		compSelection = new Composite(compSelectionCryptosystem, SWT.NONE);
		compSelection.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		compSelection.setLayout(new GridLayout(2, false));
		guiHandler.setControlMargin(compSelection, SWT.DEFAULT, SWT.DEFAULT);
		//int indent = (parent.getSize().x / 2) - (compSelection.getSize().x / 2);
		//((GridData) compSelection.getLayoutData()).horizontalIndent = 415;
		//System.out.println("indent = " + indent);
		//((GridData) compSelection.getLayoutData()).horizontalIndent = indent;
		
		
		/*Label lblSeparateRight = new Label(compSelectionCryptosystem, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateRight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) lblSeparateRight.getLayoutData()).horizontalIndent = 15;
		*/
		
		/*Text txtInfoSelection = new Text(compSelectionCryptosystem, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtInfoSelection, SWT.DEFAULT, 100);
		txtInfoSelection.setBackground(guiHandler.getColorBGinfo());
		txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection"));
		*/
		
		//Label lblSeparateInfoBottom = new Label(compSelectionCryptosystem, SWT.SEPARATOR | SWT.HORIZONTAL);
		//lblSeparateInfoBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		
		btnSelectCryptotb = new Button(compSelection, SWT.RADIO);
		btnSelectCryptotb.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnSelectCryptotb.setText("Cryptosystem in textbook format");
		btnSelectCryptotb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(rstc);
				guiHandler.showControl(cstb);
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
				guiHandler.hideControl(cstb);
				guiHandler.showControl(rstc);
				txtInfoSelection.setText(guiHandler.getMessageByControl("txtInfoSelection_steps"));
				guiHandler.showControl(txtInfoSelection);
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
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoSelection, SWT.CURSOR_ARROW);
			}
		});
		
		
		lblSeparateInfoBottom = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparateInfoBottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
	}
	
	
	
//	private void createEncryptionDecryptionContent(Composite parent) {
//		
//		Group grpEncryptDecrypt = new Group(parent, SWT.NONE);
//		grpEncryptDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		grpEncryptDecrypt.setLayout(new GridLayout(3, false));
//		grpEncryptDecrypt.setText("Encryption/Decryption");
//		
//		Composite compHoldEncryptDecryptContent = new Composite(grpEncryptDecrypt, SWT.NONE);
//		compHoldEncryptDecryptContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldEncryptDecryptContent.setLayout(new GridLayout(1, false));
//		
//		
//		Composite compHoldEncryptDecryptRadio = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
//		compHoldEncryptDecryptRadio.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
//		compHoldEncryptDecryptRadio.setLayout(new GridLayout(2, false));
//		
//		
//		
//		Button btnRadioEncrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
//		btnRadioEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnRadioEncrypt.setText("Encryption");
//		btnRadioEncrypt.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		Button btnRadioDecrypt = new Button(compHoldEncryptDecryptRadio, SWT.RADIO);
//		btnRadioDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnRadioDecrypt.setText("Decryption");
//		btnRadioDecrypt.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		
//		Composite compHoldEncryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
//		compHoldEncryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldEncryptionProcess.setLayout(new GridLayout(3, false));
//		guiHandler.hideControl(compHoldEncryptionProcess);
//		
//		
//		Composite compHoldDecryptionProcess = new Composite(compHoldEncryptDecryptContent, SWT.NONE);
//		compHoldDecryptionProcess.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldDecryptionProcess.setLayout(new GridLayout(4, false));
//		
//		
//		
//		
//		TextLoadController textSelectorEncryption = new TextLoadController(compHoldEncryptionProcess, parent, SWT.NONE, true, true);
//		textSelectorEncryption.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
//		
//		Composite compHoldCiphertext = new Composite(compHoldEncryptionProcess, SWT.NONE);
//		compHoldCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldCiphertext.setLayout(new GridLayout(1, false));
//		
//		Label lblCiphertextDescryption = new Label(compHoldCiphertext, SWT.NONE);
//		lblCiphertextDescryption.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		lblCiphertextDescryption.setText("Ciphertext");
//		
//		Text txtCiphertext = new Text(compHoldCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
//		txtCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		txtCiphertext.setText("this is a test");
//		txtCiphertext.setBackground(ColorService.LIGHTGRAY);
//		guiHandler.setSizeControl(txtCiphertext, 600, 300);
//		
//		Composite compHoldBtnsForFeatures = new Composite(compHoldEncryptionProcess, SWT.NONE);
//		compHoldBtnsForFeatures.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
//		compHoldBtnsForFeatures.setLayout(new GridLayout(1, false));
//		
//		Button btnEncrypt = new Button(compHoldBtnsForFeatures, SWT.PUSH);
//		btnEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnEncrypt.setText("Encrypt");
//		btnEncrypt.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		Button btnDecryptInEncryptionMode = new Button(compHoldBtnsForFeatures, SWT.PUSH);
//		btnDecryptInEncryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnDecryptInEncryptionMode.setText("Switch to decryption mode");
//		btnDecryptInEncryptionMode.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		
//		Button btnWriteToJCTeditor = new Button(compHoldBtnsForFeatures, SWT.PUSH);
//		btnWriteToJCTeditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnWriteToJCTeditor.setText("Write to JCT editor");
//		btnWriteToJCTeditor.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		
//		
//		TextLoadController textSelectorDecryption = new TextLoadController(compHoldDecryptionProcess, parent, SWT.NONE, true, true);
//		textSelectorDecryption.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
//		
//		
//		Composite compHoldPlaintext = new Composite(compHoldDecryptionProcess, SWT.NONE);
//		compHoldPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compHoldPlaintext.setLayout(new GridLayout(1, false));
//	
//		
//		Label lblCiphertextSegmentsDescription = new Label(compHoldPlaintext, SWT.NONE);
//		lblCiphertextSegmentsDescription.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		lblCiphertextSegmentsDescription.setText("Ciphertext separated into segments (\"||\" as separator)");
//		
//		
//		Composite compCiphertextSegmentsComponents = new Composite(compHoldPlaintext, SWT.NONE);
//		compCiphertextSegmentsComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compCiphertextSegmentsComponents.setLayout(new GridLayout(3, false));
//		
//		
//		Composite compChooseBlockComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
//		compChooseBlockComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		compChooseBlockComponents.setLayout(new GridLayout(1, false));
//		
//		Label lblChooseBlockDesc = new Label(compChooseBlockComponents, SWT.NONE);
//		lblChooseBlockDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		lblChooseBlockDesc.setText("Block");
//		
//		Combo cmbChooseBlock = new Combo(compChooseBlockComponents, SWT.DROP_DOWN | SWT.READ_ONLY);
//		cmbChooseBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		
//		
//		
//		
//		Text txtCiphertextSegments = new Text(compCiphertextSegmentsComponents, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.READ_ONLY);
//		txtCiphertextSegments.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		txtCiphertextSegments.setText("this is a test");
//		txtCiphertextSegments.setBackground(ColorService.LIGHTGRAY);
//		guiHandler.setSizeControl(txtCiphertextSegments, SWT.DEFAULT, SWT.DEFAULT);
//		
//		
//		
//		Composite compPrevNextComponents = new Composite(compCiphertextSegmentsComponents, SWT.NONE);
//		compPrevNextComponents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		compPrevNextComponents.setLayout(new GridLayout(1, false));
//		
//		
//		
//		
//		Button btnPrevBlock = new Button(compPrevNextComponents, SWT.PUSH);
//		btnPrevBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnPrevBlock.setText("Previous block");
//		btnPrevBlock.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		Button btnNextBlock = new Button(compPrevNextComponents, SWT.PUSH);
//		btnNextBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnNextBlock.setText("Next block");
//		btnNextBlock.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		
//		
//		
//		Composite compPlaintexts = new Composite(compHoldPlaintext, SWT.NONE);
//		compPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compPlaintexts.setLayout(new GridLayout(4, false));
//		
//		
//		Text[] plaintext = new Text[4];
//		
//		for(int i = 0; i < plaintext.length; i++) {
//			plaintext[i] = new Text(compPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY);
//			plaintext[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//			guiHandler.setSizeControl(plaintext[i], 100, 100);
//		}
//		
//		
//		
//		Composite compPreviewChosenPlaintexts = new Composite(compHoldPlaintext, SWT.BORDER);
//		compPreviewChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		compPreviewChosenPlaintexts.setLayout(new GridLayout(1, false));
//		
//		Label lblPreviewChosenPlaintextDesc = new Label(compPreviewChosenPlaintexts, SWT.NONE);
//		lblPreviewChosenPlaintextDesc.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
//		lblPreviewChosenPlaintextDesc.setText("Preview chosen plaintexts");
//		
//		
//		Label lblSeparatePreviewDesc = new Label(compPreviewChosenPlaintexts, SWT.SEPARATOR | SWT.HORIZONTAL);
//		lblSeparatePreviewDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		
//		
//		Text txtChosenPlaintexts = new Text(compPreviewChosenPlaintexts, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
//		txtChosenPlaintexts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		txtChosenPlaintexts.setText("insert something");
//		txtChosenPlaintexts.setBackground(ColorService.LIGHTGRAY);
//		guiHandler.setSizeControl(txtChosenPlaintexts, SWT.DEFAULT, 200);
//		
//		
//		
//		
//		
//		Composite compHoldBtnsForFeaturesDecryptionMode = new Composite(compHoldDecryptionProcess, SWT.NONE);
//		compHoldBtnsForFeaturesDecryptionMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
//		compHoldBtnsForFeaturesDecryptionMode.setLayout(new GridLayout(1, false));
//		
//		
//		Button btnDecryption = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
//		btnDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnDecryption.setText("Decrypt");
//		btnDecryption.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		Button btnWriteToJCTeditorDecryptMode = new Button(compHoldBtnsForFeaturesDecryptionMode, SWT.PUSH);
//		btnWriteToJCTeditorDecryptMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
//		btnWriteToJCTeditorDecryptMode.setText("Write to JCT editor");
//		btnWriteToJCTeditorDecryptMode.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				
//			}
//		});
//		
//		
//		
//		
//		
//	
//		
//	
//		
//		
//		
//		
//		
//		
//		Label lblSeperateDescription = new Label(grpEncryptDecrypt, SWT.SEPARATOR | SWT.VERTICAL);
//		lblSeperateDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
//
//		
//		Text txtInfoEncryptionDecryption = new Text(grpEncryptDecrypt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
//		txtInfoEncryptionDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		txtInfoEncryptionDecryption.setText("insert a text");
//		txtInfoEncryptionDecryption.setBackground(ColorService.LIGHTGRAY);
//		guiHandler.setSizeControl(txtInfoEncryptionDecryption, SWT.DEFAULT, SWT.DEFAULT);
//		
//		
//	}
	
	

	
	
	
	
	/**
	 * create the table which is used in "enc/dec" group
	 */
	/*private void createTable() {
		
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
	}*/
	
	
	
	
	
	
	
	
	
	/**
	 * Create group for encryption/decryption and its content
	 * @param parent
	 */
	/*private void createEncDecContent(Composite parent) {
		
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
		txtEncDecStartWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtEncDecStartWarning.setForeground(guiHandler.getColorForegroundWarning());
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

			
	}*/
	
	
	/**
	 * create the content of the Cryptosystem tab
	 */
	private void createContent(ScrolledComposite sc, Composite rootComposite) {
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
		sc.setExpandVertical(true);

		
		// create plaintext/ciphertext content
		createPlainCipherContent(rootComposite);
		
		// create setting parameters group
		createSetParamContent(rootComposite);
		
		// create encryption/decryption group
		createEncDecContent(rootComposite);*/
		
		
		// create setting parameters group
		createSetParamContent(this);
		
		createSelectionCryptosystemContent(this);
		
		createInfoForSelectionContent(this);
		
		//createEncryptionDecryptionContent(this);
		
		cstb = new CryptosystemTextbookComposite(this, SWT.NONE, this);
		cstb.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		cstb.setLayout(new GridLayout(1, false));
		//guiHandler.hideControl(cstb);
		
		rstc = new RabinSecondTabComposite(this, SWT.NONE, guiHandler.getRabinFirst(), guiHandler.getRabinSecond(), sc, rootComposite);
		rstc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rstc.setLayout(new GridLayout(1, false));
		guiHandler.hideControl(rstc);
		
		//guiHandler.setControlMargin(this, 0, 0);
		
		// create plaintext/ciphertext content
		//createPlainCipherContent(this);
		
		// create encryption/decryption group
		//createEncDecContent(this);
	
		if(guiHandler.getDarkmode())
			setColors();
		
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
