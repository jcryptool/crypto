package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponent;
import org.jcryptool.visual.signalencryption.graphics.ComponentDrawComposite;
import org.jcryptool.visual.signalencryption.graphics.ImageComponent;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.util.UiUtils;

public class DoubleRatchetBobSendingContent implements DoubleRatchetEntityContent {
    
    Text txt_bobSendingStep0;
    Text txt_bobSendingStep1;
    Text txt_bobSendingStep2;
    Text txt_bobSendingStep3;
    Text txt_bobSendingStep4;
    Text txt_bobSendingStep5;
 
    
    FlowChartNode txt_bobRootChain1;
    FlowChartNode txt_bobRootChain2;
    FlowChartNode txt_bobRootChain3;
	FlowChartNode txt_bobRootChain4;
    FlowChartNode txt_bobSendingChain1;
    FlowChartNode txt_bobSendingChain2;
    FlowChartNode txt_bobSendingChain3;
    FlowChartNode txt_bobSendingChain4;
    FlowChartNode txt_bobSendingChain5;
    FlowChartNode txt_bobDiffieHellman1;
    FlowChartNode txt_bobDiffieHellman2;
    FlowChartNode txt_bobDiffieHellman3;
    
    Text txt_bobPlainText;
    Text txt_bobCipherText;
    
    private String bobDiffieHellmanLabel1 = Messages.SignalEncryption_bobDiffieHellmanLabel1;
    private String bobDiffieHellmanLabel2 = Messages.SignalEncryption_bobDiffieHellmanLabel2;
    private String bobDiffieHellmanLabel3 = Messages.SignalEncryption_bobDiffieHellmanLabel3;
    private String bobRootChainLabel1 = Messages.SignalEncryption_bobRootChainLabel1;
    private String bobRootChainLabel2 = Messages.SignalEncryption_bobRootChainLabel2;
    private String bobRootChainLabel3 = Messages.SignalEncryption_bobRootChainLabel3;
    private String bobSendingChainLabel1 = Messages.SignalEncryption_bobSendingChainLabel1;
    private String bobSendingChainLabel2 = Messages.SignalEncryption_bobSendingChainLabel2;
    private String bobSendingChainLabel3 = Messages.SignalEncryption_bobSendingChainLabel3;
    private String bobSendingChainLabel4 = Messages.SignalEncryption_bobSendingChainLabel4;
    private String bobSendingChainLabel5 = Messages.SignalEncryption_bobSendingChainLabel5;

    protected Canvas arr_bobReceivingChainArrow1;
    protected Canvas arr_bobReceivingChainArrow2;
    protected Canvas arr_bobReceivingChainArrow3;
    protected Canvas arr_bobReceivingChainArrow4;
    protected ArrowComponent arr_bobSendingChainArrow1;
    protected ArrowComponent arr_bobSendingChainArrow2;
    protected ArrowComponent arr_bobSendingChainArrow3;
    protected ArrowComponent arr_bobSpace3;
    protected ArrowComponent arr_bobDiffieHellmanArrow1;
    protected ArrowComponent arr_bobDiffieHellmanArrow2;
    protected ArrowComponent arr_bobRootChainArrow1;
    protected ArrowComponent arr_bobRootChainArrow2;
	protected ArrowComponent arr_bobRootChainArrow3;
    protected ArrowComponent arr_bobSpace1;
    protected ArrowComponent arr_bobSpace2;
    protected ImageComponent drw_outgoingMailIcon;

    Group grp_bobSendingChain;
    Group grp_bobSpace2;
    Group grp_bobRootChain;
    Group grp_bobSpace1;
    Group grp_bobDiffieHellman;
    Composite cmp_bobMessagebox;
    

    private String MessageboxCipherText = "The Ciphertext";
    private String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;
    private String RootChainDescription = Messages.SignalEncryption_RootChainDescription;
    private String DiffieHellmanGroupDescription = Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String SendingChainDescription = Messages.SignalEncryption_SendingChainDescription;
	private ComponentDrawComposite cmp_bobSendingAlgorithm;
    

    @Override
    public Composite buildStepsContent(Composite parent, COMMUNICATION_STATE state) {
        Composite cmp_bobSendingSteps = new Composite(parent, SWT.NONE);
        cmp_bobSendingSteps.setLayout(Layout.gl_stepsComposite());
        
        txt_bobSendingStep0 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep0.setText("Bob sendet eine Nachricht an Alice.");
        txt_bobSendingStep0.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobSendingStep1 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep1.setText("Schritt 1 Diffie Hellman Ratchet: Nach jeder Nachricht wird ein neuer Diffie Hellman-Schlüssel erstellt. Bob erzeugt dazu Alices Public einen neuen Private Key.");
        txt_bobSendingStep1.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobSendingStep2 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep2.setText("Schritt 2 Root Chain: Der Diffie Hellman-Schlüssel und der Output der letzten KDF der Root Chain wird genutzt um einen neuen Root Chain Key zu erzeugen.");
        txt_bobSendingStep2.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobSendingStep3 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep3.setText("Schritt 3 Sending Chain: Der Root Chain-Schlüssel und der Output der letzten KDF in der Sending Chain wird genutzt um einen neuen Receiving Chain Key (Message Key) zu erzeugen.");
        txt_bobSendingStep3.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobSendingStep4 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep4.setText("Schritt 4 Nachricht verfassen: Bob kann seine Nachricht an Alice schreiben.");
        txt_bobSendingStep4.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobSendingStep5 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobSendingStep5.setText("Schritt 5 Nachricht verschlüsseln & senden: Bob verschlüsselt die Nachricht mit dem Message Key und sendet diese mit seinem Diffie-Hellman-Schlüssel an Alice.");
        txt_bobSendingStep5.setLayoutData(Layout.gd_shortDescriptionTexts());
        return cmp_bobSendingSteps;
    }

    @Override
    public Composite buildAlgorithmContent(Composite parent, COMMUNICATION_STATE state) {
        cmp_bobSendingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_bobSendingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        var layout = Layout.gd_algorithmGroup();
        layout.horizontalAlignment = SWT.RIGHT;
        cmp_bobSendingAlgorithm.setLayoutData(layout);
        
        UiUtils.insertExcessiveSpacers(cmp_bobSendingAlgorithm, 1);
        createOutgoingMailIcon();
        cmp_bobMessagebox = new Composite(cmp_bobSendingAlgorithm, SWT.NONE);
        grp_bobSendingChain = new Group(cmp_bobSendingAlgorithm, SWT.NONE);
        grp_bobRootChain = new Group(cmp_bobSendingAlgorithm, SWT.NONE);
        grp_bobDiffieHellman = new Group(cmp_bobSendingAlgorithm, SWT.NONE);
        
        createBobDiffieHellmanChain();
        createBobRootChain();
        createBobSendingChain();
        createBobMessagebox();
        createBobArrowSpaces();
        return cmp_bobSendingAlgorithm;
    }

    
    private void createBobSendingChain() {
        grp_bobSendingChain.setLayout(Layout.gl_sendingReceivingChainComposite(SWT.LEFT));
        grp_bobSendingChain.setLayoutData(Layout.gd_sendingReceivingChainComposite());
        grp_bobSendingChain.setText(SendingChainDescription);
        
        txt_bobSendingChain1 = new FlowChartNode.Builder(grp_bobSendingChain)
                .title(bobSendingChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Sending Chain key", ""))
                .buildValueNode();
        txt_bobSendingChain1.setLayoutData(Layout.gd_algorithmNodes());
        
        txt_bobSendingChain2 =  new FlowChartNode.Builder(grp_bobSendingChain)
                .title(bobSendingChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Konstante", "0"))
                .buildValueNode();
        txt_bobSendingChain2.setLayoutData(Layout.gd_algorithmNodesSlim());
        
        txt_bobSendingChain3 =  new FlowChartNode.Builder(grp_bobSendingChain)
                .title(bobSendingChainLabel2)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildOperationNode();
        txt_bobSendingChain3.setLayoutData(Layout.gd_algorithmNodesSlim());
        
        arr_bobSendingChainArrow1 = ArrowComponent
        		.from(txt_bobSendingChain1).south()
        		.to(txt_bobSendingChain3).north()
        		.on(cmp_bobSendingAlgorithm)
        		.create();

        arr_bobSendingChainArrow2 = ArrowComponent
        		.from(txt_bobSendingChain2).south()
        		.to(txt_bobSendingChain3).east()
        		.on(cmp_bobSendingAlgorithm)
        		.create();

                UiUtils.insertSpacers(grp_bobSendingChain, 1);

        txt_bobSendingChain5 =  new FlowChartNode.Builder(grp_bobSendingChain)
                .title(bobSendingChainLabel5)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildValueNode();
        txt_bobSendingChain5.setLayoutData(Layout.gd_algorithmNodes());

        arr_bobSendingChainArrow3 = ArrowComponent
        		.from(txt_bobSendingChain3).south()
        		.to(txt_bobSendingChain5).north()
        		.on(cmp_bobSendingAlgorithm)
        		.create();
    }
    
    public void setSendingChainVisible(boolean visible) {
    	grp_bobSendingChain.setVisible(visible);
    	arr_bobSpace2.setVisible(visible);
		grp_bobSendingChain.setVisible(visible);
		arr_bobSendingChainArrow1.setVisible(visible);
		arr_bobSendingChainArrow2.setVisible(visible);
		arr_bobSendingChainArrow3.setVisible(visible);
		arr_bobSpace3.setVisible(visible);
		// Part of what we consider the chain is already part in the
		// Messagebox composite
		cmp_bobMessagebox.setVisible(visible);
		txt_bobSendingChain4.setVisible(visible);
		txt_bobPlainText.setVisible(!visible);
		txt_bobCipherText.setVisible(!visible);
    }


    private void createBobRootChain() {
        grp_bobRootChain.setText(RootChainDescription);
        grp_bobRootChain.setLayout(Layout.gl_rootChainComposite(SWT.RIGHT));
        grp_bobRootChain.setLayoutData(Layout.gd_rootChainComposite());

        txt_bobRootChain1 =  new FlowChartNode.Builder(grp_bobRootChain)
                .title(bobRootChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Root Chain Key", "0"))
                .buildValueNode();
        txt_bobRootChain1.setLayoutData(Layout.gd_algorithmNodes());

        txt_bobRootChain2 =  new FlowChartNode.Builder(grp_bobRootChain)
                .title(bobSendingChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Constant", "0"))
                .buildValueNode();
        txt_bobRootChain2.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_bobRootChain3 =  new FlowChartNode.Builder(grp_bobRootChain)
                .title(bobRootChainLabel3)
                .popupProvider(FlowChartNodePopup.create("KDF", "0"))
                .buildOperationNode();
        txt_bobRootChain3.setLayoutData(Layout.gd_algorithmNodes());
        
	    UiUtils.insertSpacers(grp_bobRootChain, 1, ViewConstants.BOX_WIDTH_SLIM);
        
        txt_bobRootChain4 =  new FlowChartNode.Builder(grp_bobRootChain)
                .title(bobRootChainLabel3)
                .popupProvider(FlowChartNodePopup.create("KDF", "0"))
                .buildValueNode();
        txt_bobRootChain4.setLayoutData(Layout.gd_algorithmNodes());

        arr_bobRootChainArrow1 = ArrowComponent
        		.from(txt_bobRootChain1).south()
        		.to(txt_bobRootChain3).north()
        		.on(cmp_bobSendingAlgorithm)
        		.create();

        arr_bobRootChainArrow2 = ArrowComponent
        		.from(txt_bobRootChain2).south()
        		.to(txt_bobRootChain3).east()
        		.on(cmp_bobSendingAlgorithm)
        		.create();

        arr_bobRootChainArrow3 = ArrowComponent
        		.from(txt_bobRootChain3).south()
        		.to(txt_bobRootChain4).north()
        		.on(cmp_bobSendingAlgorithm)
        		.create();
    }

    public void setRootChainVisible(boolean visible) {
		grp_bobRootChain.setVisible(visible);
		arr_bobSpace1.setVisible(visible);
		arr_bobRootChainArrow1.setVisible(visible);
		arr_bobRootChainArrow2.setVisible(visible);
		arr_bobRootChainArrow3.setVisible(visible);
	}

	private void createBobDiffieHellmanChain() {
        grp_bobDiffieHellman.setText(DiffieHellmanGroupDescription);
        grp_bobDiffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
        grp_bobDiffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());

        txt_bobDiffieHellman1 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
                .title(bobDiffieHellmanLabel1)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildValueNode();
        txt_bobDiffieHellman1.setLayoutData(Layout.gd_algorithmNodes());

        txt_bobDiffieHellman2 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
                .title(bobDiffieHellmanLabel2)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildOperationNode();
        txt_bobDiffieHellman2.setLayoutData(Layout.gd_algorithmNodes());

        txt_bobDiffieHellman3 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
                .title(bobDiffieHellmanLabel3)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildValueNode();
        txt_bobDiffieHellman3.setLayoutData(Layout.gd_algorithmNodes());

        arr_bobDiffieHellmanArrow1 = ArrowComponent
        		.from(txt_bobDiffieHellman1).south()
        		.to(txt_bobDiffieHellman2).north()
        		.on(cmp_bobSendingAlgorithm)
        		.create();

        arr_bobDiffieHellmanArrow2 = ArrowComponent
        		.from(txt_bobDiffieHellman3).north()
        		.to(txt_bobDiffieHellman2).south()
        		.on(cmp_bobSendingAlgorithm)
        		.create();
    }
	
	public void setDiffieHellmanChainVisible(boolean visible) {
		grp_bobDiffieHellman.setVisible(visible);
		arr_bobDiffieHellmanArrow1.setVisible(visible);
		arr_bobDiffieHellmanArrow2.setVisible(visible);
	}
    
    private void createBobArrowSpaces() {
        arr_bobSpace1 = ArrowComponent
				.from(grp_bobDiffieHellman, txt_bobDiffieHellman2).west()
				.to(txt_bobRootChain3, txt_bobRootChain3).east()
				.on(cmp_bobSendingAlgorithm)
				.withDefaults();
		
		arr_bobSpace2 = ArrowComponent
				.from(txt_bobRootChain3).west()
				.to(txt_bobSendingChain3).east()
				.on(cmp_bobSendingAlgorithm)
	    	    .arrowId("cmp_aliceArrowSpace2")
				.withDefaults();
		
        arr_bobSpace3 = ArrowComponent
	    	.from(txt_bobSendingChain3).west()
	    	.to(cmp_bobMessagebox, txt_bobSendingChain3).east()
	    	.on(cmp_bobSendingAlgorithm)
	    	.create();
    }
    
    private void createOutgoingMailIcon() {
    	drw_outgoingMailIcon = ImageComponent.on(cmp_bobSendingAlgorithm)
    		.xOffsetFromEast() // so we don't have to subtract the width of the image ourself as we draw to the left.
    		.setAnchorLater() // defer setting the location until the object is created
	    	.offsetX(-ViewConstants.MAIL_ICON_X_OFFSET)  // minus because we want to draw to the left
	    	.outgoingMailLeft();
	    
	    // This one is a special spacer: it doesn't have any content but ensures that the image drawn
	    // (which does NOT have a concept of layouting) has enough space to be drawn.
	    // This is necessary because the icon is the last element of the view.
	    var requiredWidth =  drw_outgoingMailIcon.imageWidth() + ViewConstants.MAIL_ICON_X_OFFSET
	    		- ViewConstants.ARROW_CANVAS_WIDTH;
	    UiUtils.insertSpacers(cmp_bobSendingAlgorithm, 1, requiredWidth - 5);

    }

    private void createBobMessagebox() {
        cmp_bobMessagebox.setLayout(Layout.gl_messageboxGroup());
        cmp_bobMessagebox.setLayoutData(Layout.gd_messageboxComposite());

        txt_bobPlainText = new Text(cmp_bobMessagebox,
                SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_bobPlainText.setLayoutData(Layout.gd_Messagebox());
        txt_bobPlainText.setTextLimit(256);
        txt_bobPlainText.setEditable(true);
        
        txt_bobSendingChain4 =  new FlowChartNode.Builder(cmp_bobMessagebox)
                .title(bobSendingChainLabel4)
                .popupProvider(FlowChartNodePopup.create("MessageKeys", "0"))
                .buildValueNode();
        txt_bobSendingChain4.setLayoutData(Layout.gd_algorithmNodes());
        
        txt_bobCipherText = new Text(cmp_bobMessagebox,
                SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
	    txt_bobCipherText.setFont(FontService.getNormalMonospacedFont());
        txt_bobCipherText.setText(MessageboxCipherText);
        txt_bobCipherText.setLayoutData(Layout.gd_Messagebox());
        
        drw_outgoingMailIcon.setRelativeTo(txt_bobCipherText, Side.WEST);
    }
    
    public void setMessageBoxVisible(boolean visible) {
		txt_bobPlainText.setVisible(visible);
		txt_bobCipherText.setVisible(visible);
		drw_outgoingMailIcon.setVisible(visible);
	}
	
	public void showOnlyMessagePlaintext() {
		txt_bobPlainText.setVisible(true);
		txt_bobCipherText.setVisible(false);
		drw_outgoingMailIcon.setVisible(false);
	}

    public void setAllVisible(boolean visible) {
		setDiffieHellmanChainVisible(visible);
		setRootChainVisible(visible);
		setSendingChainVisible(visible);
		setMessageBoxVisible(visible);
	}
}
