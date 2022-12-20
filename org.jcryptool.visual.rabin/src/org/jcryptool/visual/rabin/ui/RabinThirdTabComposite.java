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

	public Group grpSetParam;
	
	public Group grpFactor;
	public Composite npqComp;
	public Combo cmbN;
	
	
	public Text txtNWarning;
	public Table factorTable;
	
	public Button btnGenKeysMan;
	public Text txtP1;
	public Text txtP2;
	public Text txtResultP;
	public Text txtQ1;
	public Text txtQ2;
	public Text txtResultQ;
	public Button btnFactorize;
	public Button btnGenKeysAlgo;
	
	
	public ScrolledComposite sc;
	public Composite rootComposite;
	public Composite compFermatAttack;
	public Composite compPollardRho;
	public Button btnFactorizePollard;
	
	public Text txtWarningNPollard;
	public Text txtWarningxPollard;
	public Text txtWarningyPollard;
	public Text txtWarninggxPollard;
	public Text txtgxPollard;
	public Combo cmbNPollard;
	public Text txtxPollard;
	public Text txtyPollard;
	public Table pollardFactorTable;
	
	public Button btnSelFermat;
	public Button btnSelPollard;
	
	public Text txtInfoStopComputation;
	public Button btnStopComputation;
	public Text txtInfoStopComputationPollard;
	public Button btnStopComputationPollard;
	
	public Group grpPollardSetParam;
	public Text txtpPollard;
	public Text txtqPollard;
	public Composite compSelFermatPollard;
	public Composite compSelParam;
	public Composite compSelMode;
	public Label lblSepFactorPollard;
	public Text txtInfoFactorPollard;
	public Label lblNPollard;
	public Label lblxPollard;
	public Label lblyPollard;
	public Group grpRndFunction;
	public Label lblC;
	public Group grpResult;
	public Label lblpPollard;
	public Label lblqPollard;
	public Button btnGenKeysAlgoPollard2;
	public Group grpPollardFactorization;
	public Composite compGenKeys;
	public Label lblSepFactor;
	public Text txtInfoFactor;
	public Label lblN;
	public Composite compPrimeP;
	public Label lblPrimeP;
	public Label lblPlusP;
	public Label lblResultP;
	public Composite compPrimeQ;
	public Label lblPrimeQ;
	public Label lblMinusQ;
	public Label lblResultQ;
	
	public TableColumn tc1;
	public TableColumn tc2;
	public TableColumn tc3;
	public TableColumn tc4;
	public TableColumn pollardtc1;
	public TableColumn pollardtc2;
	public TableColumn pollardtc3;
	public TableColumn pollardtc4;
	
	
	public Text txtLblNPollard;
	public Text txtLblxPollard;
	public Text txtLblyPollard;
	public Text txtLblC;
	public Text txtLblpPollard;
	public Text txtLblqPollard;
	public Text txtLblN;
	public Text txtLblPrimeP;
	public Text txtLblPlusP;
	public Text txtLblResultP;
	public Text txtLblPrimeQ;
	public Text txtLblMinusQ;
	public Text txtLblResultQ;
	public Group grpInfoPollard;
	public Group grpInfoFermat;
	
	
	
	
	public HandleThirdTab guiHandler;
	
	
	
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
	
	
	
	
	
	
	
	/**
	 * @return current instance of this class
	 */
	public RabinThirdTabComposite getCurrentInstance() {
		return this;
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
	 * create fermat and pollard content
	 * @param parent
	 */
	private void createFermatPollardContent(Composite parent) {		
		compSelFermatPollard = new Composite(parent, SWT.NONE);
		compSelFermatPollard.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		compSelFermatPollard.setLayout(new GridLayout(2, false));
		
		btnSelFermat = new Button(compSelFermatPollard, SWT.RADIO);
		btnSelFermat.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		btnSelFermat.setText(Messages.RabinThirdTabComposite_grpFermat);
		btnSelFermat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.hideControl(compPollardRho);
				guiHandler.showControl(compFermatAttack);
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
			}
		});
		
		
		
		compFermatAttack = new Composite(parent, SWT.NONE);
		compFermatAttack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//compFermatAttack.setLayout(new GridLayout(1, false));
		compFermatAttack.setLayout(new GridLayout(2, false));
			
		compPollardRho = new Composite(parent, SWT.NONE);
		compPollardRho.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//compPollardRho.setLayout(new GridLayout(1, false));
		compPollardRho.setLayout(new GridLayout(2, false));
		
		guiHandler.hideControl(compPollardRho);
		
		grpPollardSetParam = new Group(compPollardRho, SWT.NONE);
		grpPollardSetParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//grpPollardSetParam.setLayout(new GridLayout(4, false));
		grpPollardSetParam.setLayout(new GridLayout(2, false));
		grpPollardSetParam.setText(Messages.RabinThirdTabComposite_grpSetParam);
		
		
		
		compSelParam = new Composite(grpPollardSetParam, SWT.NONE);
		compSelParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compSelParam.setLayout(new GridLayout(2, false));
		
		compSelMode = new Composite(grpPollardSetParam, SWT.NONE);
		compSelMode.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		compSelMode.setLayout(new GridLayout(1, false));
		((GridData) compSelMode.getLayoutData()).verticalIndent = 110;
		
		
		//lblSepFactorPollard = new Label(grpPollardSetParam, SWT.SEPARATOR | SWT.VERTICAL);
		//lblSepFactorPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		grpInfoPollard = new Group(compPollardRho, SWT.NONE);
		grpInfoPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpInfoPollard.setLayout(new GridLayout(1, false));
		grpInfoPollard.setText(" "); //$NON-NLS-1$
		//guiHandler.setSizeControl(grpInfoPollard, 300, SWT.DEFAULT);
		
		
		//txtInfoFactorPollard = new Text(grpPollardSetParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		txtInfoFactorPollard = new Text(grpInfoPollard, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoFactorDataPollard = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoFactorPollard.setLayoutData(txtInfoFactorDataPollard);
		guiHandler.setSizeControl(txtInfoFactorPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoFactorPollard.setText(guiHandler.getMessageByControl("txtInfoFactor_pollard")); //$NON-NLS-1$
		
		txtInfoFactorPollard.setBackground(ColorService.LIGHTGRAY);
		txtInfoFactorPollard.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoFactorPollard, SWT.CURSOR_ARROW);
			}
		});
	
		
		
//		lblNPollard = new Label(compSelParam, SWT.NONE);
//		lblNPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblNPollard.setText("N = "); //$NON-NLS-1$
		
		txtLblNPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblNPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true));
		txtLblNPollard.setBackground(guiHandler.colorBGinfo);
		txtLblNPollard.setText("N ="); //$NON-NLS-1$
		
		
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
		txtWarningNPollard.setBackground(guiHandler.colorBackgroundWarning);
		txtWarningNPollard.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtWarningNPollard);
		
		

//		lblxPollard = new Label(compSelParam, SWT.NONE);
//		lblxPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblxPollard.setText("x = "); //$NON-NLS-1$
		
		txtLblxPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblxPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true));
		txtLblxPollard.setBackground(guiHandler.colorBGinfo);
		txtLblxPollard.setText("x ="); //$NON-NLS-1$
		
		txtxPollard = new Text(compSelParam, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtxPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtxPollard.addModifyListener(mlPollard);
		
		
		txtWarningxPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningxPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarningxPollard.getLayoutData()).horizontalIndent = 32;
		guiHandler.setSizeControlWarning(txtWarningxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningxPollard.setBackground(guiHandler.colorBackgroundWarning);
		txtWarningxPollard.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtWarningxPollard);
		
		
//		lblyPollard = new Label(compSelParam, SWT.NONE);
//		lblyPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblyPollard.setText("y = "); //$NON-NLS-1$
		
		txtLblyPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblyPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, true));
		txtLblyPollard.setBackground(guiHandler.colorBGinfo);
		txtLblyPollard.setText("y ="); //$NON-NLS-1$
		
		txtyPollard = new Text(compSelParam, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtyPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtyPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtyPollard.addModifyListener(mlPollard);
		
		
		
		
		
		txtWarningyPollard = new Text(compSelParam, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningyPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarningyPollard.getLayoutData()).horizontalIndent = 32;
		guiHandler.setSizeControlWarning(txtWarningyPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningyPollard.setBackground(guiHandler.colorBackgroundWarning);
		txtWarningyPollard.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtWarningyPollard);
		
		
		grpRndFunction = new Group(compSelParam, SWT.NONE);
		grpRndFunction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpRndFunction.setLayout(new GridLayout(2, false));
		grpRndFunction.setText("g(x) = x\u00b2 + c"); //$NON-NLS-1$
		
		
		
		
		
//		lblC = new Label(grpRndFunction, SWT.NONE);
//		lblC.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblC.setText("c = "); //$NON-NLS-1$
		
		txtLblC = new Text(grpRndFunction, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblC.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblC.setBackground(guiHandler.colorBGinfo);
		txtLblC.setText("c ="); //$NON-NLS-1$
		
		txtgxPollard = new Text(grpRndFunction, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtgxPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtgxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtgxPollard.addModifyListener(mlPollard);
		
		
		txtWarninggxPollard = new Text(grpRndFunction, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarninggxPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		((GridData) txtWarninggxPollard.getLayoutData()).horizontalIndent = 28;
		guiHandler.setSizeControlWarning(txtWarninggxPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtWarninggxPollard.setBackground(guiHandler.colorBackgroundWarning);
		txtWarninggxPollard.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtWarninggxPollard);
		
		
		
		grpResult = new Group(compSelParam, SWT.NONE);
		grpResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpResult.setLayout(new GridLayout(2, false));
		grpResult.setText(Messages.RabinThirdTabComposite_grpResult);
		
		
//		lblpPollard = new Label(grpResult, SWT.NONE);
//		lblpPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblpPollard.setText(Messages.RabinThirdTabComposite_lblpPollard);
		
		txtLblpPollard = new Text(grpResult, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblpPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblpPollard.setBackground(guiHandler.colorBGinfo);
		txtLblpPollard.setText(Messages.RabinThirdTabComposite_lblpPollard);
		
		txtpPollard = new Text(grpResult, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.READ_ONLY);
		txtpPollard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		guiHandler.setSizeControl(txtpPollard, SWT.DEFAULT, SWT.DEFAULT);
		txtpPollard.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				guiHandler.txtpPollardAction(getCurrentInstance());
			}
		});
		txtpPollard.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtpPollard, SWT.CURSOR_ARROW);
			}
		});
	
		
		
//		lblqPollard = new Label(grpResult, SWT.NONE);
//		lblqPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
//		lblqPollard.setText("q = N/p = "); //$NON-NLS-1$
		
		txtLblqPollard = new Text(grpResult, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblqPollard.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblqPollard.setBackground(guiHandler.colorBGinfo);
		txtLblqPollard.setText("q = N/p = "); //$NON-NLS-1$
		
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
		
			
		btnFactorizePollard = new Button(compSelMode, SWT.PUSH);
		btnFactorizePollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnFactorizePollard.setText(Messages.RabinThirdTabComposite_btnFactorize);
		btnFactorizePollard.setEnabled(false);
		
		
		btnFactorizePollard.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {			
				guiHandler.btnFactorizePollardAction(getCurrentInstance());
			}
		});
		
		
		btnStopComputationPollard = new Button(compSelMode, SWT.PUSH);
		btnStopComputationPollard.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnStopComputationPollard.setText(Messages.RabinThirdTabComposite_7);
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
		txtInfoStopComputationPollard.setText(Messages.RabinThirdTabComposite_8);
		guiHandler.setSizeControlWarning(txtInfoStopComputationPollard, SWT.DEFAULT, SWT.DEFAULT);
		((GridData) txtInfoStopComputationPollard.getLayoutData()).verticalIndent = 10;
		guiHandler.hideControl(txtInfoStopComputationPollard);
		
		
		
		grpPollardFactorization = new Group(compPollardRho, SWT.NONE);
		grpPollardFactorization.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
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

		
		
		
		
	
		grpSetParam = new Group(compFermatAttack, SWT.NONE);
		grpSetParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//grpSetParam.setLayout(new GridLayout(4, false));
		grpSetParam.setLayout(new GridLayout(2, false));
		grpSetParam.setText(Messages.RabinThirdTabComposite_grpSetParam);
		
		
		
		
		npqComp = new Composite(grpSetParam, SWT.NONE);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		npqComp.setLayout(new GridLayout(2, false));
			
		
		compGenKeys = new Composite(grpSetParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		((GridLayout) compGenKeys.getLayout()).marginTop = 75;
			
		
		
		btnGenKeysAlgo = new Button(compGenKeys, SWT.PUSH);
		btnGenKeysAlgo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnGenKeysAlgo.setText(Messages.RabinThirdTabComposite_9);
		btnGenKeysAlgo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnGenKeysAlgoAction(getCurrentInstance(), txtNWarning, cmbN);
			}
		});
		
		
		
		btnFactorize = new Button(compGenKeys, SWT.PUSH);
		btnFactorize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		btnFactorize.setText(Messages.RabinThirdTabComposite_btnFactorize);
		btnFactorize.setEnabled(false);
		
		
		btnFactorize.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				guiHandler.btnFactorizeFermatAction(getCurrentInstance());
			}
		});
		
		
		
		btnStopComputation = new Button(compGenKeys, SWT.PUSH);
		btnStopComputation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnStopComputation.setText(Messages.RabinThirdTabComposite_10);
		btnStopComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				guiHandler.btnStopComputation(getCurrentInstance());
			}
		});
		
	
		
		txtInfoStopComputation = new Text(compGenKeys, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtInfoStopComputation.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
		txtInfoStopComputation.setBackground(ColorService.LIGHTGRAY);
		txtInfoStopComputation.setForeground(ColorService.GREEN);
		txtInfoStopComputation.setText(Messages.RabinThirdTabComposite_11);
		guiHandler.setSizeControlWarning(txtInfoStopComputation, SWT.DEFAULT, SWT.DEFAULT);
		((GridData) txtInfoStopComputation.getLayoutData()).verticalIndent = 10;
		guiHandler.hideControl(txtInfoStopComputation);
		
		
		
		//lblSepFactor = new Label(grpSetParam, SWT.SEPARATOR | SWT.VERTICAL);
		//lblSepFactor.setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, false, true));
		
		grpInfoFermat = new Group(compFermatAttack, SWT.NONE);
		grpInfoFermat.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpInfoFermat.setLayout(new GridLayout(1, false));
		grpInfoFermat.setText(" "); //$NON-NLS-1$
		
		grpFactor = new Group(compFermatAttack, SWT.NONE);
		//grpFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpFactor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		grpFactor.setLayout(new GridLayout(1, false));
		grpFactor.setText(Messages.RabinThirdTabComposite_grpFactor);
		
		
		//txtInfoFactor = new Text(grpSetParam, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		txtInfoFactor = new Text(grpInfoFermat, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
		GridData txtInfoFactorData = new GridData(SWT.FILL, SWT.FILL, true, true);
		txtInfoFactor.setLayoutData(txtInfoFactorData);
		guiHandler.setSizeControl(txtInfoFactor, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoFactor.setText(guiHandler.getMessageByControl("txtInfoFactor_fermat")); //$NON-NLS-1$
		
		
		txtInfoFactor.setBackground(ColorService.LIGHTGRAY);
		txtInfoFactor.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoFactor, SWT.CURSOR_ARROW);
			}
		});
	
		
		
//		lblN = new Label(npqComp, SWT.NONE);
//		lblN.setText("N ="); //$NON-NLS-1$
		
		txtLblN = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblN.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblN.setBackground(guiHandler.colorBGinfo);
		txtLblN.setText("N ="); //$NON-NLS-1$
		
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
		txtNWarning.setBackground(guiHandler.colorBackgroundWarning);
		txtNWarning.setForeground(guiHandler.colorForegroundWarning);
		guiHandler.hideControl(txtNWarning);
		
		
		compPrimeP = new Composite(npqComp, SWT.NONE);
		compPrimeP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		compPrimeP.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPrimeP, 0, 5);
		((GridLayout) compPrimeP.getLayout()).marginLeft = 2;
		
//		lblPrimeP = new Label(compPrimeP, SWT.NONE);
//		lblPrimeP.setText("p ="); //$NON-NLS-1$
		
		txtLblPrimeP = new Text(compPrimeP, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblPrimeP.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblPrimeP.setBackground(guiHandler.colorBGinfo);
		txtLblPrimeP.setText("p ="); //$NON-NLS-1$
		
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
	
//		lblPlusP = new Label(compPrimeP, SWT.NONE);
//		lblPlusP.setText("+"); //$NON-NLS-1$
		
		txtLblPlusP = new Text(compPrimeP, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblPlusP.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		txtLblPlusP.setBackground(guiHandler.colorBGinfo);
		txtLblPlusP.setText("+"); //$NON-NLS-1$
		
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
	
//		lblResultP = new Label(npqComp, SWT.NONE);
//		lblResultP.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		lblResultP.setText("= "); //$NON-NLS-1$
		
		txtLblResultP = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblResultP.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		txtLblResultP.setBackground(guiHandler.colorBGinfo);
		txtLblResultP.setText("= "); //$NON-NLS-1$
		
		txtResultP = new Text(npqComp, SWT.BORDER | SWT.READ_ONLY);
		txtResultP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtResultP, SWT.DEFAULT, SWT.DEFAULT);
		txtResultP.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
					guiHandler.txtResultPAction(getCurrentInstance());
			}
		});
		txtResultP.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtResultP, SWT.CURSOR_ARROW);
			}
		});
		
		
		compPrimeQ = new Composite(npqComp, SWT.NONE);
		compPrimeQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		compPrimeQ.setLayout(new GridLayout(4, false));
		guiHandler.setControlMargin(compPrimeQ, 0, 5);
		((GridLayout) compPrimeQ.getLayout()).marginLeft = 2;
		
//		lblPrimeQ = new Label(compPrimeQ, SWT.NONE);
//		lblPrimeQ.setText("q ="); //$NON-NLS-1$
		
		txtLblPrimeQ = new Text(compPrimeQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblPrimeQ.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		txtLblPrimeQ.setBackground(guiHandler.colorBGinfo);
		txtLblPrimeQ.setText("q ="); //$NON-NLS-1$
		
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
	
//		lblMinusQ = new Label(compPrimeQ, SWT.NONE);
//		lblMinusQ.setText("\u2212"); //$NON-NLS-1$
		
		txtLblMinusQ = new Text(compPrimeQ, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblMinusQ.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		txtLblMinusQ.setBackground(guiHandler.colorBGinfo);
		txtLblMinusQ.setText("\u2212"); //$NON-NLS-1$
		
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
	
//		lblResultQ = new Label(npqComp, SWT.NONE);
//		lblResultQ.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
//		lblResultQ.setText("= "); //$NON-NLS-1$
		
		txtLblResultQ = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtLblResultQ.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		txtLblResultQ.setBackground(guiHandler.colorBGinfo);
		txtLblResultQ.setText("= "); //$NON-NLS-1$
		
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
		
		Color colorBG = GUIHandler.colorDarkModeBG;
		Color colorFG = GUIHandler.colorDarkModeFG;
		Color colorTxtWarningFG = GUIHandler.colorDarkModeWarningFG;
		Color colorButtonBG = GUIHandler.colorButtonsBG;
		Color colorButtonFG = GUIHandler.colorButtonsFG;
		Color colorTxtWhichYouCanEnterBG = GUIHandler.colorTxtWhichYouCanEnterBG;
		Color colorTxtWhichYouCanEnterFG = GUIHandler.colorTxtWhichYouCanEnterFG;

		// latest addition (5.11)
		txtLblNPollard.setBackground(colorBG);
		txtLblNPollard.setForeground(colorFG);
		txtLblxPollard.setBackground(colorBG);
		txtLblxPollard.setForeground(colorFG);
		txtLblyPollard.setBackground(colorBG);
		txtLblyPollard.setForeground(colorFG);
		txtLblC.setBackground(colorBG);
		txtLblC.setForeground(colorFG);
		txtLblpPollard.setBackground(colorBG);
		txtLblpPollard.setForeground(colorFG);
		txtLblqPollard.setBackground(colorBG);
		txtLblqPollard.setForeground(colorFG);
		txtLblN.setBackground(colorBG);
		txtLblN.setForeground(colorFG);
		txtLblPrimeP.setBackground(colorBG);
		txtLblPrimeP.setForeground(colorFG);
		txtLblPlusP.setBackground(colorBG);
		txtLblPlusP.setForeground(colorFG);
		txtLblResultP.setBackground(colorBG);
		txtLblResultP.setForeground(colorFG);
		txtLblPrimeQ.setBackground(colorBG);
		txtLblPrimeQ.setForeground(colorFG);
		txtLblMinusQ.setBackground(colorBG);
		txtLblMinusQ.setForeground(colorFG);
		txtLblResultQ.setBackground(colorBG);
		txtLblResultQ.setForeground(colorFG);
		grpInfoPollard.setBackground(colorBG);
		grpInfoPollard.setForeground(colorFG);
		grpInfoFermat.setBackground(colorBG);
		grpInfoFermat.setForeground(colorFG);
		
		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
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
		compFermatAttack.setBackground(colorBG);
		compFermatAttack.setForeground(colorFG);
		compPollardRho.setBackground(colorBG);
		compPollardRho.setForeground(colorFG);
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
//		lblSepFactorPollard.setBackground(colorBG);
//		lblSepFactorPollard.setForeground(colorFG);
		txtInfoFactorPollard.setBackground(colorBG);
		txtInfoFactorPollard.setForeground(colorFG);
//		lblNPollard.setBackground(colorBG);
//		lblNPollard.setForeground(colorFG);
//		lblxPollard.setBackground(colorBG);
//		lblxPollard.setForeground(colorFG);
//		lblyPollard.setBackground(colorBG);
//		lblyPollard.setForeground(colorFG);
		grpRndFunction.setBackground(colorBG);
		grpRndFunction.setForeground(colorFG);
//		lblC.setBackground(colorBG);
//		lblC.setForeground(colorFG);
		grpResult.setBackground(colorBG);
		grpResult.setForeground(colorFG);
//		lblpPollard.setBackground(colorBG);
//		lblpPollard.setForeground(colorFG);
//		lblqPollard.setBackground(colorBG);
//		lblqPollard.setForeground(colorFG);
		btnGenKeysAlgoPollard2.setBackground(colorButtonBG);
		btnGenKeysAlgoPollard2.setForeground(colorButtonFG);
		grpPollardFactorization.setBackground(colorBG);
		grpPollardFactorization.setForeground(colorFG);
		compGenKeys.setBackground(colorBG);
		compGenKeys.setForeground(colorFG);
//		lblSepFactor.setBackground(colorBG);
//		lblSepFactor.setForeground(colorFG);
		txtInfoFactor.setBackground(colorBG);
		txtInfoFactor.setForeground(colorFG);
//		lblN.setBackground(colorBG);
//		lblN.setForeground(colorFG);
		compPrimeP.setBackground(colorBG);
		compPrimeP.setForeground(colorFG);
//		lblPrimeP.setBackground(colorBG);
//		lblPrimeP.setForeground(colorFG);
//		lblPlusP.setBackground(colorBG);
//		lblPlusP.setForeground(colorFG);
//		lblResultP.setBackground(colorBG);
//		lblResultP.setForeground(colorFG);
		compPrimeQ.setBackground(colorBG);
		compPrimeQ.setForeground(colorFG);
//		lblPrimeQ.setBackground(colorBG);
//		lblPrimeQ.setForeground(colorFG);
//		lblMinusQ.setBackground(colorBG);
//		lblMinusQ.setForeground(colorFG);
//		lblResultQ.setBackground(colorBG);
//		lblResultQ.setForeground(colorFG);
		
	}
	
	
	
	/**
	 * initialize content of Attacks tab
	 */
	private void initializeContent() {
		String strInitN = String.valueOf(23 * 31);
		String[] items = {"80741", "67051078057", "354778132601", "100539149",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"121238213", "413967937", "111893951048684957", "567507024199975605209"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		cmbN.setItems(items);
		cmbNPollard.setItems(items);
		cmbN.setText(strInitN);
		cmbNPollard.setText(strInitN);
		
		txtxPollard.setText("1"); //$NON-NLS-1$
		txtyPollard.setText("1"); //$NON-NLS-1$
		txtgxPollard.setText("1"); //$NON-NLS-1$
		btnSelFermat.setSelection(true);
		
		
		txtP1.setBackground(GUIHandler.colorBGinfo);
		txtP2.setBackground(GUIHandler.colorBGinfo);
		txtQ1.setBackground(GUIHandler.colorBGinfo);
		txtQ2.setBackground(GUIHandler.colorBGinfo);
		txtResultP.setBackground(GUIHandler.colorBGinfo);
		txtResultQ.setBackground(GUIHandler.colorBGinfo);
		txtpPollard.setBackground(GUIHandler.colorBGinfo);
		txtqPollard.setBackground(GUIHandler.colorBGinfo);
	
	}
	
	
	
	
	/**
	 * create content of Attacks tab
	 */
	private void createContent() {
		this.setLayout(new GridLayout(1, false));
		
		guiHandler.setControlMargin(this, 0, 0);
		
		createFermatPollardContent(this);	 
				
		initializeContent();
		
		if(GUIHandler.isDarkmode)
			setColors();
				
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
//	public RabinThirdTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
//		super(parent, style);
//		this.guiHandler = new HandleThirdTab(rootScrolledComposite, rootComposite, rabinFirst, rabinSecond);
//		createContent();
//	}
	
	public RabinThirdTabComposite(Composite parent, int style, Rabin rabin, ScrolledComposite rootScrolledComposite, Composite rootComposite) {
		super(parent, style);
		this.guiHandler = new HandleThirdTab(rootScrolledComposite, rootComposite, rabin);
		createContent();
	}
	
	
	
	/**
	 * @param parent
	 * @param style
	 * @param rabinFirst
	 * @param rabinSecond
	 */
//	public RabinThirdTabComposite(Composite parent, int style, Rabin rabinFirst, Rabin rabinSecond) {
//		super(parent, style);
//		this.guiHandler = new HandleThirdTab(sc, rootComposite, rabinFirst, rabinSecond);
//		createContent();
//	}

}
