package org.jcryptool.visual.rabin.ui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.HandleThirdTab;
import org.jcryptool.visual.rabin.Messages;
import org.jcryptool.visual.rabin.Rabin;

public class RabinThirdTabComposite extends Composite {

	private Group grpFermat;
	private Group grpSetParam;
	
	private Group grpFactor;
	private Composite npqComp;
	private Combo cmbN;
	
	
	private Text txtNWarning;
	private Table factorTable;
	
	private Button btnStartGen;
	private Button btnGenKeysMan;
	private Text txtP1;
	private Text txtP2;
	private Text txtResultP;
	private Text txtQ1;
	private Text txtQ2;
	private Text txtResultQ;
	private Button btnFactorize;
	private Button btnGenKeysAlgo;
	
	
	private ScrolledComposite sc;
	private Composite rootComposite;
	private Composite compFermatAttack;
	private Composite compPollardRho;
	private Button btnGenKeysManPollard;
	private Button btnGenKeysAlgoPollard;
	private Button btnStartGenPollard;
	private Button btnFactorizePollard;
	
	private Text txtWarningNPollard;
	private Text txtWarningxPollard;
	private Text txtWarningyPollard;
	private Text txtWarninggxPollard;
	private Text txtgxPollard;
	private Combo cmbNPollard;
	private Text txtxPollard;
	private Text txtyPollard;
	private Table pollardFactorTable;
	
	private Button btnSelFermat;
	private Button btnSelPollard;
	
	private Text txtInfoStopComputation;
	private Button btnStopComputation;
	private Text txtInfoStopComputationPollard;
	private Button btnStopComputationPollard;
	
	private Group grpPollardSetParam;
	private Text txtpPollard;
	private Text txtqPollard;
	private Composite compSelFermatPollard;
	private Composite compSelParam;
	private Composite compSelMode;
	private Label lblSepFactorPollard;
	private Text txtInfoFactorPollard;
	private Label lblNPollard;
	private Label lblxPollard;
	private Label lblyPollard;
	private Group grpRndFunction;
	private Label lblC;
	private Group grpResult;
	private Label lblpPollard;
	private Label lblqPollard;
	private Button btnGenKeysAlgoPollard2;
	private Group grpPollardFactorization;
	private Composite compGenKeys;
	private Label lblSepFactor;
	private Text txtInfoFactor;
	private Label lblN;
	private Composite compPrimeP;
	private Label lblPrimeP;
	private Label lblPlusP;
	private Label lblResultP;
	private Composite compPrimeQ;
	private Label lblPrimeQ;
	private Label lblMinusQ;
	private Label lblResultQ;
	
	
	
	private HandleThirdTab guiHandler;
	
	private Thread t = null;
	
	
	/**
	 * modifylistener for pollard rho fields
	 */
	private ModifyListener mlPollard = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			// TODO Auto-generated method stub
			guiHandler.updateTextFieldsPollard(e, getCurrentInstance(), cmbNPollard, txtWarningNPollard, txtxPollard, txtWarningxPollard, txtyPollard, txtWarningyPollard, txtgxPollard, txtWarninggxPollard);
		}
	};
	
	
	
	public Text getTxtInfoStopComputationPollard() {
		return txtInfoStopComputationPollard;
	}
	
	
	public Button getBtnStopComputationPollard() {
		return btnStopComputationPollard;
	}
	
	
	
	/**
	 * @return current instance of this class
	 */
	public RabinThirdTabComposite getCurrentInstance() {
		return this;
	}
	
	
	public Button getBtnStopComputation() {
		return btnStopComputation;
	}
	
	
	
	public Text getTxtInfoStopComputation() {
		return txtInfoStopComputation;
	}
	
	
	
	/**
	 * @return pollardFactorTable
	 */
	public Table getPollardFactorTable() {
		return pollardFactorTable;
	}
	
	
	
	/**
	 * @return btnGenKeysManPollard
	 */
	public Button getBtnGenKeysManPollard() {
		return btnGenKeysManPollard;
	}
	
	
	
	/**
	 * @return btnGenKeysAlgoPollard
	 */
	public Button getBtnGenKeysAlgoPollard() {
		return btnGenKeysAlgoPollard;
	}
	
	
	
	
	/**
	 * @return btnStartGenPollard
	 */
	public Button getBtnStartGenPollard() {
		return btnStartGenPollard;
	}
	
	
	
	/**
	 * @return btnFactorizePollard
	 */
	public Button getBtnFactorizePollard() {
		return btnFactorizePollard;
	}
	
	
	
	/**
	 * @return btnGenKeysAlgo
	 */
	public Button getBtnGenKeysAlgo() {
		return btnGenKeysAlgo;
	}
	
	

	/**
	 * @return the txtN
	 */
	public Combo getCmbN() {
		return cmbN;
	}

	

	

	/**
	 * @return the txtNWarning
	 */
	public Text getTxtNWarning() {
		return txtNWarning;
	}

	/**
	 * @return the t
	 */
	public Table getFactorTable() {
		return factorTable;
	}

	

	/**
	 * @return the btnStartGen
	 */
	public Button getBtnStartGen() {
		return btnStartGen;
	}

	/**
	 * @return the btnGenKeysMan
	 */
	public Button getBtnGenKeysMan() {
		return btnGenKeysMan;
	}

	/**
	 * @return the txtP1
	 */
	public Text getTxtP1() {
		return txtP1;
	}

	/**
	 * @return the txtP2
	 */
	public Text getTxtP2() {
		return txtP2;
	}

	/**
	 * @return the txtResultP
	 */
	public Text getTxtResultP() {
		return txtResultP;
	}

	/**
	 * @return the txtQ1
	 */
	public Text getTxtQ1() {
		return txtQ1;
	}

	/**
	 * @return the txtQ2
	 */
	public Text getTxtQ2() {
		return txtQ2;
	}

	/**
	 * @return the txtResultQ
	 */
	public Text getTxtResultQ() {
		return txtResultQ;
	}

	/**
	 * @return the btnFactorize
	 */
	public Button getBtnFactorize() {
		return btnFactorize;
	}



		
	/**
	 * modifylistener for fermats factorization fields
	 */
	private ModifyListener ml = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			guiHandler.updateTextfieldN(e, getCurrentInstance());
		}
	};
	
	
	
	/**
	 * not used here, but can be used for future puposes
	 */
	private VerifyListener vlNumbers = new VerifyListener() {
		
		@Override
		public void verifyText(VerifyEvent e) {
			guiHandler.verifyControlFields(e);
		}
	};
	private TableColumn tc1;
	private TableColumn tc2;
	private TableColumn tc3;
	private TableColumn tc4;
	private TableColumn pollardtc1;
	private TableColumn pollardtc2;
	private TableColumn pollardtc3;
	private TableColumn pollardtc4;
	
	
	

	
	

	
	/**
	 * create fermat and pollard content
	 * @param parent
	 */
	private void createFermatPollardContent(Composite parent) {
		/*grpFermat = new Group(parent, SWT.NONE);
		grpFermat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grpFermat.setLayout(new GridLayout(1, false));
		grpFermat.setText(Messages.RabinThirdTabComposite_grpFermat);*/
		
		compSelFermatPollard = new Composite(parent, SWT.NONE);
		compSelFermatPollard.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		compSelFermatPollard.setLayout(new GridLayout(2, false));
		//((GridLayout) compSelFermatPollard.getLayout()).marginTop = 15;
		
		btnSelFermat = new Button(compSelFermatPollard, SWT.RADIO);
		btnSelFermat.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnSelFermat.setText(Messages.RabinThirdTabComposite_grpFermat);
		btnSelFermat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compPollardRho);
				guiHandler.showControl(compFermatAttack);
				
				// in case we need it at a later stage
				
				
				BigInteger n1 = guiHandler.getSavedRabin().getN();
				BigInteger n2 = guiHandler.getRabinFirst().getN();
				
				
				guiHandler.getSavedRabin().setN(n2);
				guiHandler.getRabinFirst().setN(n1);
			}
		});
		
		btnSelPollard = new Button(compSelFermatPollard, SWT.RADIO);
		btnSelPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnSelPollard.setText(Messages.RabinThirdTabComposite_btnSelPollard);
		btnSelPollard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compFermatAttack);
				guiHandler.showControl(compPollardRho);
				
				// in case we need it at a later stage
				
				BigInteger n1 = guiHandler.getSavedRabin().getN();
				BigInteger n2 = guiHandler.getRabinFirst().getN();
				guiHandler.getSavedRabin().setN(n2);
				guiHandler.getRabinFirst().setN(n1);
			}
		});
		
		
		
		compFermatAttack = new Composite(parent, SWT.NONE);
		compFermatAttack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compFermatAttack.setLayout(new GridLayout(1, false));
		
		//guiHandler.hideControl(compFermatAttack);
		
		compPollardRho = new Composite(parent, SWT.NONE);
		compPollardRho.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compPollardRho.setLayout(new GridLayout(1, false));
		
		guiHandler.hideControl(compPollardRho);
		
		grpPollardSetParam = new Group(compPollardRho, SWT.NONE);
		grpPollardSetParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpPollardSetParam.setLayout(new GridLayout(4, false));
		grpPollardSetParam.setText(Messages.RabinThirdTabComposite_grpSetParam);
		//grpPollardSetParam.setBackground(ColorService.GRAY);
		
		
		
		compSelParam = new Composite(grpPollardSetParam, SWT.NONE);
		compSelParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelParam.setLayout(new GridLayout(2, false));
		
		compSelMode = new Composite(grpPollardSetParam, SWT.NONE);
		compSelMode.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		compSelMode.setLayout(new GridLayout(1, false));
		((GridData) compSelMode.getLayoutData()).verticalIndent = 110;
		
		
		lblSepFactorPollard = new Label(grpPollardSetParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepFactorPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		txtInfoFactorPollard = new Text(grpPollardSetParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoFactorDataPollard = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoFactorPollard.setLayoutData(txtInfoFactorDataPollard);
		guiHandler.setSizeControl(txtInfoFactorPollard, SWT.DEFAULT, SWT.DEFAULT);
		//txtInfoFactorPollard.setText(Messages.RabinThirdTabComposite_txtInfoFactorPollard);
		txtInfoFactorPollard.setText(guiHandler.getMessageByControl("txtInfoFactor_pollard"));
		
		txtInfoFactorPollard.setBackground(ColorService.LIGHTGRAY);
		txtInfoFactorPollard.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoFactorPollard, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		lblNPollard = new Label(compSelParam, SWT.NONE);
		lblNPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblNPollard.setText("N = "); //$NON-NLS-1$
		
		//txtNPollard = new Text(compSelParam, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		cmbNPollard = new Combo(compSelParam, SWT.DROP_DOWN);
		cmbNPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(cmbNPollard, SWT.DEFAULT, SWT.DEFAULT);
		cmbNPollard.addModifyListener(mlPollard);
		cmbNPollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.cmbSelectionAction(cmbNPollard);
			}
			
		});
		
		
		txtWarningNPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningNPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarningNPollard.getLayoutData()).horizontalIndent = 32;
		guiHandler.setSizeControlWarning(txtWarningNPollard, SWT.DEFAULT, SWT.DEFAULT);
		//txtWarningNPollard.setText("this is a test");
		txtWarningNPollard.setBackground(guiHandler.getColorBackgroundWarning());
		txtWarningNPollard.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtWarningNPollard);
		
		

		lblxPollard = new Label(compSelParam, SWT.NONE);
		lblxPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblxPollard.setText("x = "); //$NON-NLS-1$
		
		txtxPollard = new Text(compSelParam, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtxPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtxPollard.addModifyListener(mlPollard);
		
		
		txtWarningxPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningxPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarningxPollard.getLayoutData()).horizontalIndent = 32;
		guiHandler.setSizeControlWarning(txtWarningxPollard, SWT.DEFAULT, SWT.DEFAULT);
		//txtWarningxPollard.setText("this is a test");
		txtWarningxPollard.setBackground(guiHandler.getColorBackgroundWarning());
		txtWarningxPollard.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtWarningxPollard);
		
		
		lblyPollard = new Label(compSelParam, SWT.NONE);
		lblyPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblyPollard.setText("y = "); //$NON-NLS-1$
		
		txtyPollard = new Text(compSelParam, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtyPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtyPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtyPollard.addModifyListener(mlPollard);
		
		
		
		
		
		txtWarningyPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningyPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarningyPollard.getLayoutData()).horizontalIndent = 32;
		guiHandler.setSizeControlWarning(txtWarningyPollard, SWT.DEFAULT, SWT.DEFAULT);
		//txtWarningyPollard.setText("this is a test");
		txtWarningyPollard.setBackground(guiHandler.getColorBackgroundWarning());
		txtWarningyPollard.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtWarningyPollard);
		
		
		grpRndFunction = new Group(compSelParam, SWT.NONE);
		grpRndFunction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpRndFunction.setLayout(new GridLayout(2, false));
		grpRndFunction.setText("g(x) = x\u00b2 + c"); //$NON-NLS-1$
		
		
		
		
		
		lblC = new Label(grpRndFunction, SWT.NONE);
		lblC.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblC.setText("c = "); //$NON-NLS-1$
		
		txtgxPollard = new Text(grpRndFunction, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtgxPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtgxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtgxPollard.addModifyListener(mlPollard);
		
		
		txtWarninggxPollard = new Text(grpRndFunction, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarninggxPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarninggxPollard.getLayoutData()).horizontalIndent = 28;
		guiHandler.setSizeControlWarning(txtWarninggxPollard, SWT.DEFAULT, SWT.DEFAULT);
		//txtWarninggxPollard.setText("this is a test");
		txtWarninggxPollard.setBackground(guiHandler.getColorBackgroundWarning());
		txtWarninggxPollard.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtWarninggxPollard);
		
		
		
		grpResult = new Group(compSelParam, SWT.NONE);
		grpResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpResult.setLayout(new GridLayout(2, false));
		grpResult.setText(Messages.RabinThirdTabComposite_grpResult);
		
		
		lblpPollard = new Label(grpResult, SWT.NONE);
		lblpPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblpPollard.setText(Messages.RabinThirdTabComposite_lblpPollard);
		
		txtpPollard = new Text(grpResult, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.READ_ONLY);
		txtpPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtpPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtpPollard.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtpPollard, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		lblqPollard = new Label(grpResult, SWT.NONE);
		lblqPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lblqPollard.setText("q = N/p = "); //$NON-NLS-1$
		
		txtqPollard = new Text(grpResult, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.READ_ONLY);
		txtqPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtqPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtqPollard.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtqPollard, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
		btnGenKeysAlgoPollard2 = new Button(compSelMode, SWT.PUSH);
		btnGenKeysAlgoPollard2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysAlgoPollard2.setText(Messages.RabinThirdTabComposite_btnGenKeysAlgo);
		btnGenKeysAlgoPollard2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysAlgoPollardAction(getCurrentInstance(), txtWarningNPollard, cmbNPollard);
			}
		});
		
		
		
		/*btnGenKeysManPollard = new Button(compSelMode, SWT.RADIO);
		btnGenKeysManPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysManPollard.setText(Messages.RabinThirdTabComposite_btnGenKeysMan);
		btnGenKeysManPollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//guiHandler.btnGenKeysManAction(e, getCurrentInstance());
				guiHandler.updateTextFieldsPollard(e, getCurrentInstance(), txtNPollard, txtWarningNPollard, txtxPollard, txtWarningxPollard, txtyPollard, txtWarningyPollard, txtgxPollard, txtWarninggxPollard);
			}
		});
		
		btnGenKeysManPollard.setSelection(true);
		
		btnGenKeysAlgoPollard = new Button(compSelMode, SWT.RADIO);
		btnGenKeysAlgoPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysAlgoPollard.setText(Messages.RabinThirdTabComposite_btnGenKeysAlgo);
		btnGenKeysAlgoPollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnStartGenPollard.setEnabled(true);
			}
		});*/
		
		
		
		/*Composite compHoldApplyAndFactorPollard = new Composite(compSelMode, SWT.NONE);
		compHoldApplyAndFactorPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		compHoldApplyAndFactorPollard.setLayout(new GridLayout(2, false));*/
		
			
		/*btnStartGenPollard = new Button(compHoldApplyAndFactorPollard, SWT.PUSH);
		btnStartGenPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		btnStartGenPollard.setText(Messages.RabinThirdTabComposite_btnStartGen);
		btnStartGenPollard.setEnabled(false);
		
		
		btnStartGenPollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//guiHandler.btnStartGenAction(getCurrentInstance());
					
			}
		});*/
		
		btnFactorizePollard = new Button(compSelMode, SWT.PUSH);
		btnFactorizePollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnFactorizePollard.setText(Messages.RabinThirdTabComposite_btnFactorize);
		btnFactorizePollard.setEnabled(false);
		
		
		btnFactorizePollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				//guiHandler.btnFactorizeAction(getCurrentInstance());	
				guiHandler.btnFactorizePollardAction(getCurrentInstance(), cmbNPollard, txtxPollard, txtyPollard, txtgxPollard, txtWarningNPollard, txtpPollard, txtqPollard);
			}
		});
		
		
		btnStopComputationPollard = new Button(compSelMode, SWT.PUSH);
		btnStopComputationPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnStopComputationPollard.setText("Stop computation");
		btnStopComputationPollard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnStopComputationPollardAction(getCurrentInstance());
			}
		});
		
		
		
		txtInfoStopComputationPollard = new Text(compSelMode, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtInfoStopComputationPollard.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		txtInfoStopComputationPollard.setBackground(ColorService.LIGHTGRAY);
		txtInfoStopComputationPollard.setForeground(ColorService.GREEN);
		txtInfoStopComputationPollard.setText("Stopped computation successfully");
		guiHandler.setSizeControlWarning(txtInfoStopComputationPollard, SWT.DEFAULT, SWT.DEFAULT);
		((GridData) txtInfoStopComputationPollard.getLayoutData()).verticalIndent = 10;
		guiHandler.hideControl(txtInfoStopComputationPollard);
		
		
		
		grpPollardFactorization = new Group(compPollardRho, SWT.NONE);
		grpPollardFactorization.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpPollardFactorization.setLayout(new GridLayout(1, false));
		grpPollardFactorization.setText(Messages.RabinThirdTabComposite_grpFactor);
		
		
		
		
		pollardFactorTable = new Table(grpPollardFactorization, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.WRAP);
		GridData pollardFactorTableData = new GridData(SWT.CENTER, SWT.TOP, true, true);
		pollardFactorTable.setLayoutData(pollardFactorTableData);
		pollardFactorTableData.minimumHeight = 300;
		pollardFactorTableData.heightHint = pollardFactorTable.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		
		pollardtc1 = new TableColumn(pollardFactorTable, SWT.CENTER);
	    pollardtc2 = new TableColumn(pollardFactorTable, SWT.CENTER);
	    pollardtc3 = new TableColumn(pollardFactorTable, SWT.CENTER);
	    pollardtc4 = new TableColumn(pollardFactorTable, SWT.CENTER);
	    pollardtc1.setText("i"); //$NON-NLS-1$
	    pollardtc2.setText("x = g(x)"); //$NON-NLS-1$
	    pollardtc3.setText("y = g(g(y))"); //$NON-NLS-1$
	    pollardtc4.setText("gcd(|x \u2212 y|, N)"); //$NON-NLS-1$
	    pollardtc1.setWidth(290);
	    pollardtc2.setWidth(290);
	    pollardtc3.setWidth(290);
	    pollardtc4.setWidth(290);
	    pollardFactorTable.setHeaderVisible(true);

		
		
		
		
	
		//grpSetParam = new Group(grpFermat, SWT.NONE);
		grpSetParam = new Group(compFermatAttack, SWT.NONE);
		grpSetParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpSetParam.setLayout(new GridLayout(4, false));
		grpSetParam.setText(Messages.RabinThirdTabComposite_grpSetParam);
		
		//grpFactor = new Group(grpFermat, SWT.NONE);
		grpFactor = new Group(compFermatAttack, SWT.NONE);
		grpFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpFactor.setLayout(new GridLayout(1, false));
		grpFactor.setText(Messages.RabinThirdTabComposite_grpFactor);
		
		
		npqComp = new Composite(grpSetParam, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		npqComp.setLayout(new GridLayout(2, false));
		
		
		/*Composite compTest = new Composite(grpSetParam, SWT.NONE);
		compTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		compTest.setLayout(new GridLayout(1, false));*/
		
		
		compGenKeys = new Composite(grpSetParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		((GridLayout) compGenKeys.getLayout()).marginTop = 75;
			
		
		
		btnGenKeysAlgo = new Button(compGenKeys, SWT.PUSH);
		btnGenKeysAlgo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysAlgo.setText("Use public key generated in Cryptosystem tab");
		btnGenKeysAlgo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysAlgoAction(getCurrentInstance(), txtNWarning, cmbN);
			}
		});
		
		
		
		/*btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysMan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysMan.setText(Messages.RabinThirdTabComposite_btnGenKeysMan);
		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnGenKeysManAction(e, getCurrentInstance());
			}
		});
		
		btnGenKeysMan.setSelection(true);
		
		btnGenKeysAlgo = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysAlgo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysAlgo.setText(Messages.RabinThirdTabComposite_btnGenKeysAlgo);
		btnGenKeysAlgo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnGenKeysAlgoAction(e, getCurrentInstance());
				
			}
		});*/
		
		
		/*Composite compHoldApplyAndFactor = new Composite(compGenKeys, SWT.NONE);
		compHoldApplyAndFactor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		compHoldApplyAndFactor.setLayout(new GridLayout(2, false));*/
		
			
		/*btnStartGen = new Button(compHoldApplyAndFactor, SWT.PUSH);
		btnStartGen.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		btnStartGen.setText(Messages.RabinThirdTabComposite_btnStartGen);
		btnStartGen.setEnabled(false);
		
		
		btnStartGen.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnStartGenAction(getCurrentInstance());
					
			}
		});*/
		
		btnFactorize = new Button(compGenKeys, SWT.PUSH);
		btnFactorize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnFactorize.setText(Messages.RabinThirdTabComposite_btnFactorize);
		btnFactorize.setEnabled(false);
		
		
		btnFactorize.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//if(t == null) {
					//t = guiHandler.btnFactorizeAction(getCurrentInstance());	
					//t.start();
				//}
				
				guiHandler.btnFactorizeFermatAction(getCurrentInstance());
				
				
				//btnFactorize.setEnabled(false);
			}
		});
		
		
		
		btnStopComputation = new Button(compGenKeys, SWT.PUSH);
		btnStopComputation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnStopComputation.setText("Stop computation");
		btnStopComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//guiHandler.setStopComputation(true);
				//btnFactorize.setEnabled(true);
				//t = null;
				guiHandler.btnStopComputation(getCurrentInstance());
			}
		});
		//btnStopComputation.setEnabled(false);
		
	
		
		txtInfoStopComputation = new Text(compGenKeys, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtInfoStopComputation.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		txtInfoStopComputation.setBackground(ColorService.LIGHTGRAY);
		txtInfoStopComputation.setForeground(ColorService.GREEN);
		txtInfoStopComputation.setText("Stopped computation successfully");
		guiHandler.setSizeControlWarning(txtInfoStopComputation, SWT.DEFAULT, SWT.DEFAULT);
		((GridData) txtInfoStopComputation.getLayoutData()).verticalIndent = 10;
		guiHandler.hideControl(txtInfoStopComputation);
		
		
		
		lblSepFactor = new Label(grpSetParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepFactor.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		
		txtInfoFactor = new Text(grpSetParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoFactorData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoFactor.setLayoutData(txtInfoFactorData);
		guiHandler.setSizeControl(txtInfoFactor, SWT.DEFAULT, SWT.DEFAULT);
		//txtInfoFactor.setText(Messages.RabinThirdTabComposite_txtInfoFactor);
		txtInfoFactor.setText(guiHandler.getMessageByControl("txtInfoFactor_fermat"));
		
		
		txtInfoFactor.setBackground(ColorService.LIGHTGRAY);
		txtInfoFactor.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoFactor, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		lblN = new Label(npqComp, SWT.NONE);
		lblN.setText("N ="); //$NON-NLS-1$
		//txtN = new Text(npqComp, SWT.BORDER);
		cmbN = new Combo(npqComp, SWT.DROP_DOWN);
		cmbN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(cmbN, SWT.DEFAULT, SWT.DEFAULT);
		cmbN.addModifyListener(ml);
		cmbN.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
					guiHandler.cmbSelectionAction(cmbN);
			}
			
		});
		
		
		
		txtNWarning = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtNWarning.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		((GridData) txtNWarning.getLayoutData()).horizontalIndent = 28;
		guiHandler.setSizeControlWarning(txtNWarning, SWT.DEFAULT, SWT.DEFAULT);
		txtNWarning.setBackground(guiHandler.getColorBackgroundWarning());
		txtNWarning.setForeground(guiHandler.getColorForegroundWarning());
		guiHandler.hideControl(txtNWarning);
		
		
		compPrimeP = new Composite(npqComp, SWT.NONE);
		compPrimeP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		compPrimeP.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPrimeP, 0, 5);
		((GridLayout) compPrimeP.getLayout()).marginLeft = 2;
		
		lblPrimeP = new Label(compPrimeP, SWT.NONE);
		lblPrimeP.setText("p ="); //$NON-NLS-1$
		txtP1 = new Text(compPrimeP, SWT.BORDER | SWT.READ_ONLY);
		txtP1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtP1, SWT.DEFAULT, SWT.DEFAULT);
		txtP1.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtP1, SWT.CURSOR_ARROW);
			}
		});
	
		lblPlusP = new Label(compPrimeP, SWT.NONE);
		lblPlusP.setText("+"); //$NON-NLS-1$
		txtP2 = new Text(compPrimeP, SWT.BORDER | SWT.READ_ONLY);
		txtP2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtP2, SWT.DEFAULT, SWT.DEFAULT);
		txtP2.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtP2, SWT.CURSOR_ARROW);
			}
		});
	
		lblResultP = new Label(npqComp, SWT.NONE);
		lblResultP.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblResultP.setText("= "); //$NON-NLS-1$
		txtResultP = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
		txtResultP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtResultP, SWT.DEFAULT, SWT.DEFAULT);
		txtResultP.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtResultP, SWT.CURSOR_ARROW);
			}
		});
	
		/*txtResultP.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub
				guiHandler.txtResultPQAction(getCurrentInstance());
			}
		});*/
		
		
		compPrimeQ = new Composite(npqComp, SWT.NONE);
		compPrimeQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		compPrimeQ.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPrimeQ, 0, 5);
		((GridLayout) compPrimeQ.getLayout()).marginLeft = 2;
		
		lblPrimeQ = new Label(compPrimeQ, SWT.NONE);
		lblPrimeQ.setText("q ="); //$NON-NLS-1$
		txtQ1 = new Text(compPrimeQ, SWT.BORDER | SWT.READ_ONLY);
		txtQ1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtQ1, SWT.DEFAULT, SWT.DEFAULT);
		txtQ1.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtQ1, SWT.CURSOR_ARROW);
			}
		});
	
		lblMinusQ = new Label(compPrimeQ, SWT.NONE);
		lblMinusQ.setText("\u2212"); //$NON-NLS-1$
		txtQ2 = new Text(compPrimeQ, SWT.BORDER | SWT.READ_ONLY);
		txtQ2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtQ2, SWT.DEFAULT, SWT.DEFAULT);
		txtQ2.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtQ2, SWT.CURSOR_ARROW);
			}
		});
	
		lblResultQ = new Label(npqComp, SWT.NONE);
		lblResultQ.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		lblResultQ.setText("= "); //$NON-NLS-1$
		txtResultQ = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
		txtResultQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtResultQ, SWT.DEFAULT, SWT.DEFAULT);
		txtResultQ.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtResultQ, SWT.CURSOR_ARROW);
			}
		});
	
		
		/*txtResultQ.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.txtResultPQAction(getCurrentInstance());
			}
		});*/
		
		
		
		
		factorTable = new Table(grpFactor, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridData tData = new GridData(SWT.CENTER, SWT.TOP, true, true);
		factorTable.setLayoutData(tData);
		tData.minimumHeight = 300;
		tData.heightHint = factorTable.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		
		tc1 = new TableColumn(factorTable, SWT.CENTER);
	    tc2 = new TableColumn(factorTable, SWT.CENTER);
	    tc3 = new TableColumn(factorTable, SWT.CENTER);
	    tc4 = new TableColumn(factorTable, SWT.CENTER);
	    tc1.setText("y\u2096"); //$NON-NLS-1$
	    tc2.setText("y\u2096\u00b2"); //$NON-NLS-1$
	    tc3.setText("y\u2096\u00b2 \u2212 N"); //$NON-NLS-1$
	    tc4.setText("\u221a(y\u2096\u00b2 \u2212 N)"); //$NON-NLS-1$
	    tc1.setWidth(290);
	    tc2.setWidth(290);
	    tc3.setWidth(290);
	    tc4.setWidth(290);
	    factorTable.setHeaderVisible(true);

	}
	
	
	
	public void setColors() {
		
		Color colorBG = guiHandler.getColorDarkModeBG();
		Color colorFG = guiHandler.getColorDarkModeFG();
		//Color colorFG = ColorService.YELLOW;
		Color colorTxtWarningFG = guiHandler.getColorDarkModeWarningFG();
		Color colorButtonBG = guiHandler.getColorButtonsBG();
		Color colorButtonFG = guiHandler.getColorButtonsFG();
		Color colorTxtWhichYouCanEnterBG = GUIHandler.colorTxtWhichYouCanEnterBG;
		Color colorTxtWhichYouCanEnterFG = GUIHandler.colorTxtWhichYouCanEnterFG;

		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
		//grpFermat.setBackground(colorBG);
		//grpFermat.setForeground(colorFG);
		grpSetParam.setBackground(colorBG);
		grpSetParam.setForeground(colorFG);
		grpFactor.setBackground(colorBG);
		grpFactor.setForeground(colorFG);
		npqComp.setBackground(colorBG);
		npqComp.setForeground(colorFG);
		cmbN.setBackground(colorTxtWhichYouCanEnterBG);
		cmbN.setForeground(colorTxtWhichYouCanEnterFG);
		txtNWarning.setBackground(colorBG);
		txtNWarning.setForeground(colorTxtWarningFG);
		//factorTable.setBackground(colorBG);
		//factorTable.setForeground(colorFG);
		//btnStartGen.setBackground(colorBG);
		//btnStartGen.setForeground(colorFG);
		//btnGenKeysMan.setBackground(colorBG);
		//btnGenKeysMan.setForeground(colorFG);
		txtP1.setBackground(colorBG);
		txtP1.setForeground(colorFG);
		txtP2.setBackground(colorBG);
		txtP2.setForeground(colorFG);
		txtResultP.setBackground(colorBG);
		txtResultP.setForeground(colorTxtWhichYouCanEnterFG);
		txtQ1.setBackground(colorBG);
		txtQ1.setForeground(colorFG);
		txtQ2.setBackground(colorBG);
		txtQ2.setForeground(colorFG);
		txtResultQ.setBackground(colorBG);
		txtResultQ.setForeground(colorTxtWhichYouCanEnterFG);
		btnFactorize.setBackground(colorButtonBG);
		btnFactorize.setForeground(colorButtonFG);
		btnGenKeysAlgo.setBackground(colorButtonBG);
		btnGenKeysAlgo.setForeground(colorButtonFG);
		//sc.setBackground(colorBG);
		//sc.setForeground(colorFG);
		//rootComposite.setBackground(colorBG);
		//rootComposite.setForeground(colorFG);
		compFermatAttack.setBackground(colorBG);
		compFermatAttack.setForeground(colorFG);
		compPollardRho.setBackground(colorBG);
		compPollardRho.setForeground(colorFG);
		//btnGenKeysManPollard.setBackground(colorBG);
		//btnGenKeysManPollard.setForeground(colorFG);
		//btnGenKeysAlgoPollard.setBackground(colorBG);
		//btnGenKeysAlgoPollard.setForeground(colorFG);
		//btnStartGenPollard.setBackground(colorBG);
		//btnStartGenPollard.setForeground(colorFG);
		btnFactorizePollard.setBackground(colorButtonBG);
		btnFactorizePollard.setForeground(colorButtonFG);
		txtWarningNPollard.setBackground(colorBG);
		txtWarningNPollard.setForeground(colorTxtWarningFG);
		txtWarningxPollard.setBackground(colorBG);
		txtWarningxPollard.setForeground(colorTxtWarningFG);
		txtWarningyPollard.setBackground(colorBG);
		txtWarningyPollard.setForeground(colorTxtWarningFG);
		txtWarninggxPollard.setBackground(colorBG);
		txtWarninggxPollard.setForeground(colorTxtWarningFG);
		txtgxPollard.setBackground(colorTxtWhichYouCanEnterBG);
		txtgxPollard.setForeground(colorTxtWhichYouCanEnterFG);
		cmbNPollard.setBackground(colorTxtWhichYouCanEnterBG);
		cmbNPollard.setForeground(colorTxtWhichYouCanEnterFG);
		txtxPollard.setBackground(colorTxtWhichYouCanEnterBG);
		txtxPollard.setForeground(colorTxtWhichYouCanEnterFG);
		txtyPollard.setBackground(colorTxtWhichYouCanEnterBG);
		txtyPollard.setForeground(colorTxtWhichYouCanEnterFG);
		//pollardFactorTable.setBackground(colorBG);
		//pollardFactorTable.setForeground(colorFG);
		btnSelFermat.setBackground(colorBG);
		btnSelFermat.setForeground(colorFG);
		btnSelPollard.setBackground(colorBG);
		btnSelPollard.setForeground(colorFG);
		txtInfoStopComputation.setBackground(colorBG);
		txtInfoStopComputation.setForeground(ColorService.LIGHT_AREA_GREEN);
		btnStopComputation.setBackground(colorButtonBG);
		btnStopComputation.setForeground(colorButtonFG);
		txtInfoStopComputationPollard.setBackground(colorBG);
		txtInfoStopComputationPollard.setForeground(ColorService.LIGHT_AREA_GREEN);
		btnStopComputationPollard.setBackground(colorButtonBG);
		btnStopComputationPollard.setForeground(colorButtonFG);
		grpPollardSetParam.setBackground(colorBG);
		grpPollardSetParam.setForeground(colorFG);
		txtpPollard.setBackground(colorBG);
		txtpPollard.setForeground(colorTxtWhichYouCanEnterFG);
		txtqPollard.setBackground(colorBG);
		txtqPollard.setForeground(colorTxtWhichYouCanEnterFG);
		compSelFermatPollard.setBackground(colorBG);
		compSelFermatPollard.setForeground(colorFG);
		compSelParam.setBackground(colorBG);
		compSelParam.setForeground(colorFG);
		compSelMode.setBackground(colorBG);
		compSelMode.setForeground(colorFG);
		lblSepFactorPollard.setBackground(colorBG);
		lblSepFactorPollard.setForeground(colorFG);
		txtInfoFactorPollard.setBackground(colorBG);
		txtInfoFactorPollard.setForeground(colorFG);
		lblNPollard.setBackground(colorBG);
		lblNPollard.setForeground(colorFG);
		lblxPollard.setBackground(colorBG);
		lblxPollard.setForeground(colorFG);
		lblyPollard.setBackground(colorBG);
		lblyPollard.setForeground(colorFG);
		grpRndFunction.setBackground(colorBG);
		grpRndFunction.setForeground(colorFG);
		lblC.setBackground(colorBG);
		lblC.setForeground(colorFG);
		grpResult.setBackground(colorBG);
		grpResult.setForeground(colorFG);
		lblpPollard.setBackground(colorBG);
		lblpPollard.setForeground(colorFG);
		lblqPollard.setBackground(colorBG);
		lblqPollard.setForeground(colorFG);
		btnGenKeysAlgoPollard2.setBackground(colorButtonBG);
		btnGenKeysAlgoPollard2.setForeground(colorButtonFG);
		grpPollardFactorization.setBackground(colorBG);
		grpPollardFactorization.setForeground(colorFG);
		compGenKeys.setBackground(colorBG);
		compGenKeys.setForeground(colorFG);
		lblSepFactor.setBackground(colorBG);
		lblSepFactor.setForeground(colorFG);
		txtInfoFactor.setBackground(colorBG);
		txtInfoFactor.setForeground(colorFG);
		lblN.setBackground(colorBG);
		lblN.setForeground(colorFG);
		compPrimeP.setBackground(colorBG);
		compPrimeP.setForeground(colorFG);
		lblPrimeP.setBackground(colorBG);
		lblPrimeP.setForeground(colorFG);
		lblPlusP.setBackground(colorBG);
		lblPlusP.setForeground(colorFG);
		lblResultP.setBackground(colorBG);
		lblResultP.setForeground(colorFG);
		compPrimeQ.setBackground(colorBG);
		compPrimeQ.setForeground(colorFG);
		lblPrimeQ.setBackground(colorBG);
		lblPrimeQ.setForeground(colorFG);
		lblMinusQ.setBackground(colorBG);
		lblMinusQ.setForeground(colorFG);
		lblResultQ.setBackground(colorBG);
		lblResultQ.setForeground(colorFG);
		
	}
	
	
	
	
	private void setColorsGroup(Group grp) {
		grp.setBackground(guiHandler.getColorGroupBG());
		grp.setForeground(guiHandler.getColorGroupFG());
		
		Control[] controls = grp.getChildren();
		
		for(Control c : controls) {
			
			if(c instanceof Composite) {
				c.setBackground(guiHandler.getColorCompositeBG());
			}
			
			if(c instanceof Text) {
				c.setBackground(ColorService.GRAY);
				c.setForeground(guiHandler.getColorBackgroundNeutral());
			}
			
			if(c instanceof Label) {
				c.setForeground(guiHandler.getColorBackgroundNeutral());
			}
		}
	}
	
	
	
	/**
	 * initialize content of Attacks tab
	 */
	private void initializeContent() {
		String strInitN = String.valueOf(23 * 31);
		String[] items = {"80741", "67051078057", "354778132601", "100539149", 
				"121238213", "413967937", "111893951048684957", "567507024199975605209"};
		
		cmbN.setItems(items);
		cmbNPollard.setItems(items);
		cmbN.setText(strInitN);
		cmbNPollard.setText(strInitN);
		
		txtxPollard.setText("1"); //$NON-NLS-1$
		txtyPollard.setText("1"); //$NON-NLS-1$
		txtgxPollard.setText("1"); //$NON-NLS-1$
		btnSelFermat.setSelection(true);
		
		
		//txtgxPollard.setBackground(guiHandler.getColorBGinfo());
		txtP1.setBackground(guiHandler.getColorBGinfo());
		txtP2.setBackground(guiHandler.getColorBGinfo());
		txtQ1.setBackground(guiHandler.getColorBGinfo());
		txtQ2.setBackground(guiHandler.getColorBGinfo());
		txtResultP.setBackground(guiHandler.getColorBGinfo());
		txtResultQ.setBackground(guiHandler.getColorBGinfo());
		txtpPollard.setBackground(guiHandler.getColorBGinfo());
		txtqPollard.setBackground(guiHandler.getColorBGinfo());
	
	}
	
	
	
	
	/**
	 * create content of Attacks tab
	 */
	private void createContent() {
		this.setLayout(new GridLayout(1, false));
		
		guiHandler.setControlMargin(this, 0, 0);
		
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
		
		//createFermatContent(rootComposite);	 
		createFermatPollardContent(this);	 
		
		
		
		
		initializeContent();
		
		if(guiHandler.getDarkmode())
			setColors();
		
		//setColorsGroup(grpPollardSetParam);
	    
		//sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}
	
	

	/**
	 * @param parent
	 * @param style
	 */
	public RabinThirdTabComposite(Composite parent, int style) {
		super(parent, style);
		createContent();	
	}
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 * @param rootScrolledComposite
	 * @param rootComposite
	 */
	public RabinThirdTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
		super(parent, style);
		this.guiHandler = new HandleThirdTab(rootScrolledComposite, rootComposite, rabinFirst, rabinSecond);
		createContent();
	}
	
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public RabinThirdTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond) {
		super(parent, style);
		this.guiHandler = new HandleThirdTab(sc, rootComposite, rabinFirst, rabinSecond);
		createContent();
	}

}
