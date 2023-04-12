package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.ALICE;
import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.BOB;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.Direction;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DoubleRatchetView extends Composite {

    Composite cmp_body;
    Composite cmp_alice;
    Composite cmp_bob;

    Button btn_alice;
    Button btn_bob;
    Button btn_next;
    Button btn_previous;

    Group grp_aliceSteps;
    Group grp_aliceAlgorithm;
    Group grp_bobSteps;
    Group grp_bobAlgorithm;
    
    private DoubleRatchetAliceSendingContent aliceSendingContent;
    private DoubleRatchetAliceReceivingContent aliceReceivingContent;
    DoubleRatchetBobSendingContent bobSendingContent;
    DoubleRatchetBobReceivingContent bobReceivingContent;
    
    Composite cmp_aliceSendingSteps;
    Composite cmp_aliceReceivingSteps;
    Composite cmp_bobSendingSteps;
    Composite cmp_bobReceivingSteps;
    
    Composite cmp_aliceSendingAlgorithm;
    Composite cmp_aliceReceivingAlgorithm;
    Composite cmp_bobSendingAlgorithm;
    Composite cmp_bobReceivingAlgorithm;

    String aliceAlgorithmGroupDescription = Messages.SignalEncryption_alice_AlgorithmGroupDescription;
    String bobAlgorithmGroupDescription = Messages.SignalEncryption_bob_AlgorithmGroupDescription;
    String stepGroupDescription = Messages.SignalEncryption_stepGroupDescription;
    String DiffieHellmanGroupDescription = Messages.SignalEncryption_DiffieHellmanGroupDescription;
    String RootChainDescription = Messages.SignalEncryption_RootChainDescription;
    String SendingChainDescription = Messages.SignalEncryption_SendingChainDescription;
    String ReceivingChainDescription = Messages.SignalEncryption_ReceivingChainDescription;
    String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;
    String btn_NextDescription = Messages.SignalEncryption_btnName_Next;
    String btn_PreviousDescription = Messages.SignalEncryption_btnName_Previous;
    
    StackLayout sl_aliceSteps;
    StackLayout sl_bobSteps;
    
    StackLayout sl_aliceAlgorithm;
    StackLayout sl_bobAlgorithm;

    private DoubleRatchetView instance;
    private DoubleRatchetAliceSendingLogic signalEncryptionUiState;

    private Composite cmp_buttons;
    private Composite cmp_header;

    DoubleRatchetView(Composite parent, int style) {
        super(parent, style);

        this.instance = this;
        this.setLayout(new GridLayout(1, false));

        createHeaderComposite();
        createBodyComposite();
        createAliceComposite();
        createBobComposite();
        showAliceView();

        signalEncryptionUiState = new DoubleRatchetAliceSendingLogic(this);

    }

    private void createHeaderComposite() {

        cmp_header = new Composite(this, SWT.NONE);
        cmp_header.setLayout(new GridLayout(1, false));
        cmp_header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 14, 1));

        createButtons();
    }

    private void createButtons() {
        cmp_buttons = new Composite(cmp_header, SWT.NONE);
        cmp_buttons.setLayout(new GridLayout(4, false));
        cmp_buttons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));

        createAliceButton();
        createPreviousButton();
        createNextButton();
        createBobButton();
    }

    private void createBodyComposite() {

        cmp_body = new Composite(this, SWT.NONE);
        cmp_body.setLayout(new StackLayout());
        cmp_body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    }

    private void createAliceButton() {

        btn_alice = new Button(cmp_buttons, SWT.TOGGLE);
        btn_alice.setAlignment(SWT.CENTER);

        GridData gd_btnAlice = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_btnAlice.widthHint = 150;
        btn_alice.setLayoutData(gd_btnAlice);
        btn_alice.setText(Messages.SignalEncryption_btnName_Alice);

        btn_alice.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                showAliceView();
            	signalEncryptionUiState.checkIfViewChangeRequiresStateChange(instance, ALICE);
            }
        });
    }

    private void createBobButton() {
        btn_bob = new Button(cmp_buttons, SWT.TOGGLE);
        btn_bob.setAlignment(SWT.CENTER);
        GridData gd_btnBob = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
        gd_btnBob.widthHint = 150;
        btn_bob.setLayoutData(gd_btnBob);
        btn_bob.setText(Messages.SignalEncryption_btnName_Bob);

        btn_bob.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                showBobView();
            	signalEncryptionUiState.checkIfViewChangeRequiresStateChange(instance, BOB);
            }
        });
    }

    private void createPreviousButton() {
        btn_previous = new Button(cmp_buttons, SWT.PUSH);
        btn_previous.setAlignment(SWT.CENTER);
        GridData gd_btnPrev = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
        gd_btnPrev.widthHint = 150;
        btn_previous.setLayoutData(gd_btnPrev);
        btn_previous.setText(btn_PreviousDescription);
        btn_previous.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(SelectionEvent e) {
					signalEncryptionUiState.stepBack(instance);
					System.out.println(signalEncryptionUiState.getCurrentStep().toString());
	           }
        });
    }

    private void createNextButton() {
        btn_next = new Button(cmp_buttons, SWT.PUSH);
        btn_next.setAlignment(SWT.CENTER);
        GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
        gd_btnNext.widthHint = 150;
        btn_next.setLayoutData(gd_btnNext);
        btn_next.setText(btn_NextDescription);
        btn_next.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
				signalEncryptionUiState.stepForward(instance);
				System.out.println(signalEncryptionUiState.getCurrentStep().toString());
            }
        });

    }

    private void createBobComposite() {
        // Init of the layouts and data needed
        cmp_bob = new Composite(cmp_body, SWT.NONE);
        cmp_bob.setLayout(new GridLayout(1, true));

        // Parent groups for algorithm and steps composites
        grp_bobSteps = new Group(cmp_bob, SWT.NONE);
        grp_bobAlgorithm = new Group(cmp_bob, SWT.NONE);

        grp_bobSteps.setText(stepGroupDescription);
        sl_bobSteps = new StackLayout();
        grp_bobSteps.setLayout(sl_bobSteps);
        grp_bobSteps.setLayoutData(Layout.gd_stepsComposite());

        sl_bobAlgorithm = new StackLayout();
        grp_bobAlgorithm.setText(bobAlgorithmGroupDescription);
        grp_bobAlgorithm.setLayout(sl_bobAlgorithm);
        grp_bobAlgorithm.setLayoutData(Layout.gd_algorithmGroup());
        
        bobSendingContent = new DoubleRatchetBobSendingContent();
        cmp_bobSendingSteps = getBobSendingContent().buildStepsContent(grp_bobSteps, COMMUNICATION_STATE.INITIALISING);
        cmp_bobSendingAlgorithm =  getBobSendingContent().buildAlgorithmContent(grp_bobAlgorithm, COMMUNICATION_STATE.INITIALISING);
        
        bobReceivingContent = new DoubleRatchetBobReceivingContent();
        cmp_bobReceivingSteps = getBobReceivingContent().buildStepsContent(grp_bobSteps, COMMUNICATION_STATE.INITIALISING);
        cmp_bobReceivingAlgorithm =  getBobReceivingContent().buildAlgorithmContent(grp_bobAlgorithm, COMMUNICATION_STATE.INITIALISING);
        
        showBobReceiving();
    }

    private void createAliceComposite() {
        // Init of the widgets, layouts and data needed
        cmp_alice = new Composite(cmp_body, SWT.NONE);
        cmp_alice.setLayout(new GridLayout(1, false));

        // Parent groups for algorithm and steps composites
        grp_aliceSteps = new Group(cmp_alice, SWT.NONE);
        grp_aliceAlgorithm = new Group(cmp_alice, SWT.NONE);

        grp_aliceAlgorithm.setText(aliceAlgorithmGroupDescription);
        grp_aliceAlgorithm.setLayout(Layout.gl_algorithmGroup());
        grp_aliceAlgorithm.setLayoutData(Layout.gd_algorithmGroup());
        
        var gd_stepsGroup = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
        grp_aliceSteps.setText(stepGroupDescription);
        sl_aliceSteps = new StackLayout();
        grp_aliceSteps.setLayout(sl_aliceSteps);
        grp_aliceSteps.setLayoutData(gd_stepsGroup);
        
        sl_aliceAlgorithm = new StackLayout();
        grp_aliceAlgorithm.setLayout(sl_aliceAlgorithm);
        
        aliceSendingContent  = new DoubleRatchetAliceSendingContent();
        cmp_aliceSendingSteps = getAliceSendingContent().buildStepsContent(grp_aliceSteps, COMMUNICATION_STATE.INITIALISING);
        cmp_aliceSendingAlgorithm =  getAliceSendingContent().buildAlgorithmContent(grp_aliceAlgorithm, COMMUNICATION_STATE.INITIALISING);
        
        aliceReceivingContent  = new DoubleRatchetAliceReceivingContent();
        cmp_aliceReceivingSteps = getAliceReceivingContent().buildStepsContent(grp_aliceSteps, COMMUNICATION_STATE.INITIALISING);
        cmp_aliceReceivingAlgorithm =  getAliceReceivingContent().buildAlgorithmContent(grp_aliceAlgorithm, COMMUNICATION_STATE.INITIALISING);

        showAliceSending();
    }
    

    void showBobView() {
        btn_alice.setSelection(false);
        btn_bob.setSelection(true);
        getCurrentLayout().topControl = this.cmp_bob;
        cmp_body.layout();
    }

    void showAliceView() {
        btn_alice.setSelection(true);
        btn_bob.setSelection(false);
        getCurrentLayout().topControl = this.cmp_alice;
        cmp_body.layout();
        
    }

    void showAliceReceiving() {
        sl_aliceSteps.topControl = cmp_aliceReceivingSteps;
        sl_aliceAlgorithm.topControl = cmp_aliceReceivingAlgorithm;
        grp_aliceSteps.layout();
        grp_aliceAlgorithm.layout();
        
    }

    void showAliceSending() {
        sl_aliceSteps.topControl = cmp_aliceSendingSteps;
        sl_aliceAlgorithm.topControl = cmp_aliceSendingAlgorithm;
        grp_aliceSteps.layout();
        grp_aliceAlgorithm.layout();
    }

    void showBobSending() {
        sl_bobSteps.topControl = cmp_bobSendingSteps;
        sl_bobAlgorithm.topControl = cmp_bobSendingAlgorithm;
        grp_bobSteps.layout();
        grp_bobAlgorithm.layout();
    }

    void showBobReceiving() {
        sl_bobSteps.topControl = cmp_bobReceivingSteps;
        sl_bobAlgorithm.topControl = cmp_bobReceivingAlgorithm;
        grp_bobSteps.layout();
        grp_bobAlgorithm.layout();
    }
    
    void switchSenderReceiver() {
        if (isAliceSendingShowing()) {
            showAliceReceiving();
        } else {
            showAliceSending();
        }
        if (isBobSendingShowing()) {
            showBobReceiving();
        } else {
            showBobSending();
        }
    }
    
    public boolean isShowingAlice() {
    	return getCurrentLayout().topControl == cmp_alice;
    }

    public boolean isShowingBob() {
    	return getCurrentLayout().topControl == cmp_bob;
    }
    
    public CommunicationEntity getCurrentlyShowingEntity() {
    	return isShowingAlice() ? ALICE : BOB;
    }
    
    private boolean shouldShowAlice(DoubleRatchetStep step) {
    	return step.shouldShowEntity() == ALICE;
    }
    
    private boolean shouldShowBob(DoubleRatchetStep step) {
    	return !shouldShowAlice(step);
    }
    
    private StackLayout getCurrentLayout() {
    	return (StackLayout) this.cmp_body.getLayout();
    }

    public void resetAll() {
    	showAliceSending();
    	AlgorithmState.get().resetCommunication();
        signalEncryptionUiState.reset(this);
    }
    
    private boolean isAliceSendingShowing() {
        return sl_aliceAlgorithm.topControl == cmp_aliceSendingAlgorithm;
    }
    
    private boolean isBobSendingShowing() {
        return sl_bobAlgorithm.topControl == cmp_bobSendingAlgorithm;
    }

    public DoubleRatchetBobSendingContent getBobSendingContent() {
        return bobSendingContent;
    }

    public DoubleRatchetBobReceivingContent getBobReceivingContent() {
        return bobReceivingContent;
    }

    public DoubleRatchetAliceSendingContent getAliceSendingContent() {
        return aliceSendingContent;
    }

    public DoubleRatchetAliceReceivingContent getAliceReceivingContent() {
        return aliceReceivingContent;
    }
}
