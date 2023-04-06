package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponent;
import org.jcryptool.visual.signalencryption.graphics.ComponentDrawComposite;
import org.jcryptool.visual.signalencryption.graphics.ImageComponent;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.util.UiUtils;

import java.util.Map;

public class DoubleRatchetBobReceivingContent implements DoubleRatchetEntityContent {
    
    Text txt_bobReceivingStep0;
    Text txt_bobReceivingStep5;
    Text txt_bobReceivingStep6;
    Text txt_bobReceivingStep7;
    Text txt_bobReceivingStep8;
    Text txt_bobReceivingStep9;
    
	FlowChartNode txt_bobRootChain0;
    FlowChartNode txt_bobRootChain1;
    FlowChartNode txt_bobRootChain2;
    FlowChartNode txt_bobRootChain3;
    FlowChartNode txt_bobReceivingChain1;
    FlowChartNode txt_bobReceivingChain2;
    FlowChartNode txt_bobReceivingChain3;
    FlowChartNode txt_bobReceivingChain4;
    FlowChartNode txt_bobReceivingChain5;
    FlowChartNode txt_bobDiffieHellman1;
    FlowChartNode txt_bobDiffieHellman2;
    FlowChartNode txt_bobDiffieHellman3;
    
    Text txt_bobPlainText;
    Text txt_bobCipherText;
    
    private String bobStep0 = Messages.SignalEncryption_bobDescriptionText0;
    private String bobStep5 = Messages.SignalEncryption_bobStepText5;
    private String step6 = Messages.SignalEncryption_stepText6;
    private String step7 = Messages.SignalEncryption_stepText7;
    private String step8 = Messages.SignalEncryption_stepText8;
    private String step9 = Messages.SignalEncryption_stepText9;
    
    private String bobDiffieHellmanLabel1 = Messages.SignalEncryption_bobDiffieHellmanLabel1;
    private String bobDiffieHellmanLabel2 = Messages.SignalEncryption_bobDiffieHellmanLabel2;
    private String bobDiffieHellmanLabel3 = Messages.SignalEncryption_bobDiffieHellmanLabel3;
    private String bobRootChainLabel1 = Messages.SignalEncryption_bobRootChainLabel1;
    private String bobRootChainLabel2 = Messages.SignalEncryption_bobRootChainLabel2;
    private String bobRootChainLabel3 = Messages.SignalEncryption_bobRootChainLabel3;
    private String bobReceivingChainLabel1 = Messages.SignalEncryption_bobReceivingChainLabel1;
    private String bobReceivingChainLabel2 = Messages.SignalEncryption_bobReceivingChainLabel2;
    private String bobReceivingChainLabel3 = Messages.SignalEncryption_bobReceivingChainLabel3;
    private String bobReceivingChainLabel4 = Messages.SignalEncryption_bobReceivingChainLabel4;
    private String bobReceivingChainLabel5 = Messages.SignalEncryption_bobReceivingChainLabel5;

	protected ArrowComponent arr_bobMessagePublicKey;
    protected ArrowComponent arr_bobReceivingChainArrow1;
    protected ArrowComponent arr_bobReceivingChainArrow2;
    protected ArrowComponent arr_bobReceivingChainArrow3;
    protected ArrowComponent arr_bobSpace3;
    protected ArrowComponent arr_bobDiffieHellmanArrow1;
    protected ArrowComponent arr_bobDiffieHellmanArrow2;
	protected ArrowComponent arr_bobRootChainArrow0;
    protected ArrowComponent arr_bobRootChainArrow1;
    protected ArrowComponent arr_bobRootChainArrow2;

    Group grp_bobReceivingChain;
    Group grp_bobSpace2;
    Group grp_bobRootChain;
    Group grp_bobSpace1;
    Group grp_bobDiffieHellman;
    Group grp_bobMessagebox;
    Group grp_bobDecryptedMessage;
    
    ComponentDrawComposite cmp_bobReceivingAlgorithm;
    Composite cmp_bobDiffieHellman;
    Composite cmp_bobRootChain;
    ArrowComponent arr_bobSpace1;
    ArrowComponent arr_bobSpace2;
    ImageComponent drw_incomingMailIcon;

    private String MessageboxCipherText = "The Ciphertext";
    private String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;
    private String RootChainDescription = Messages.SignalEncryption_RootChainDescription;
    private String MessageboxPlainText = "Geben Sie hier Ihre Nachricht an Bob ein.";
    private String DiffieHellmanGroupDescription = Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String ReceivingChainDescription = Messages.SignalEncryption_ReceivingChainDescription;
    

    @Override
    public Composite buildStepsContent(Composite parent, COMMUNICATION_STATE state) {
        Composite cmp_bobSendingSteps = new Composite(parent, SWT.NONE);
        cmp_bobSendingSteps.setLayout(Layout.gl_stepsComposite());
        
        txt_bobReceivingStep0 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep0.setText(bobStep0);
        txt_bobReceivingStep0.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep5 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep5.setText(bobStep5);
        txt_bobReceivingStep5.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep6 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep6.setText(step6);
        txt_bobReceivingStep6.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep7 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep7.setText(step7);
        txt_bobReceivingStep7.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep8 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep8.setText(step8);
        txt_bobReceivingStep8.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep9 = new Text(cmp_bobSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep9.setText(step9);
        txt_bobReceivingStep9.setLayoutData(Layout.gd_shortDescriptionTexts());
        return cmp_bobSendingSteps;
    }

    @Override
    public Composite buildAlgorithmContent(Composite parent, COMMUNICATION_STATE state) {
    	cmp_bobReceivingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_bobReceivingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        cmp_bobReceivingAlgorithm.setLayoutData(Layout.gd_algorithmGroup());
        
        createMailIcon();
        grp_bobMessagebox = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_bobDiffieHellman = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_bobRootChain = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_bobReceivingChain = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_bobDecryptedMessage = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        
        createBobEncryptedMessagebox();
        createBobDiffieHellmanChain();
        createBobRootChain();
        createBobReceivingChain();
        createBobDecryptedMessagebox();
        createBobArrowSpaces();
        return cmp_bobReceivingAlgorithm;
    }
    
    private void createMailIcon() {
    	UiUtils.insertSpacers(cmp_bobReceivingAlgorithm, 0, 0);
    	drw_incomingMailIcon = ImageComponent.on(cmp_bobReceivingAlgorithm)
	    	.xOffsetFromEast() // so we don't have to subtract the width of the image ourself as we draw to the left.
	    	.offsetX(-ViewConstants.MAIL_ICON_X_OFFSET) // minus because we want to draw to the left
	    	.setAnchorLater() // defer setting the location until the object is created
	    	.incomingMail();
	    
	    // This one is a special spacer: it doesn't have any content but ensures that the image drawn
	    // (which does NOT have a concept of layouting) has enough space to be drawn.
	    // This is necessary because the icon is the last element of the view.
    	// The x offset is taken twice, so we have even distance left and right
	    var requiredWidth =  drw_incomingMailIcon.imageWidth()
	    		+ (ViewConstants.MAIL_ICON_X_OFFSET * 2) - ViewConstants.ARROW_CANVAS_WIDTH;
	    UiUtils.insertSpacers(cmp_bobReceivingAlgorithm, 1, requiredWidth - 5);

    }

    private void createBobEncryptedMessagebox() {
	    grp_bobMessagebox.setLayout(Layout.gl_messageboxGroup());
	    grp_bobMessagebox.setLayoutData(Layout.gd_messageboxComposite());
	    grp_bobMessagebox.setText(MessageboxDescription);
	
	    txt_bobCipherText = new Text(
	            grp_bobMessagebox,
	            SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY | SWT.CENTER
	    );
	    txt_bobCipherText.setText(MessageboxCipherText);
	    txt_bobCipherText.setLayoutData(Layout.gd_Messagebox());
	    txt_bobCipherText.setEditable(false);
	    txt_bobCipherText.setFont(FontService.getNormalMonospacedFont());
	    
	    // This has to live here, because the icon has to come into existance before the messagebox, but still needs
	    // a reference to the messagebox to infer it's location.
	    drw_incomingMailIcon.setRelativeTo(grp_bobMessagebox, Side.WEST);
	}
    
    public void setEncryptedMessageBoxVisible(boolean visible) {
    	grp_bobMessagebox.setVisible(visible);
    }

	private void createBobDiffieHellmanChain() {
	    grp_bobDiffieHellman.setText(DiffieHellmanGroupDescription);
	    grp_bobDiffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
	    grp_bobDiffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());
	
	    txt_bobDiffieHellman1 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
	            .title(bobDiffieHellmanLabel1)
	            .popupProvider(FlowChartNodePopup.create("EC Public Key", "0"))
	            .buildValueNode();
	    txt_bobDiffieHellman1.setLayoutData(Layout.gd_algorithmNodes());
	    
	    arr_bobMessagePublicKey = ArrowComponent
	    		.fromAnchors()
	    		.fromAnchorX(grp_bobMessagebox, Side.EAST)
	    		.fromAnchorY(txt_bobDiffieHellman1, Side.WEST)
	    		.outgoingDirection(Side.EAST)
	    		.toAnchorX(grp_bobDiffieHellman, Side.WEST)
	    		.toAnchorY(txt_bobDiffieHellman1, Side.WEST)
	    		.incomingDirection(Side.WEST)
	    		.on(cmp_bobReceivingAlgorithm)
	    		.create();
	    
	    txt_bobDiffieHellman2 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
	            .title(bobDiffieHellmanLabel2)
	            .popupProvider(FlowChartNodePopup.create("Shared Secret", "1"))
	            .buildOperationNode();
	    txt_bobDiffieHellman2.setLayoutData(Layout.gd_algorithmNodes());
	
	    txt_bobDiffieHellman3 =  new FlowChartNode.Builder(grp_bobDiffieHellman)
	            .title(bobDiffieHellmanLabel3)
	            .popupProvider(FlowChartNodePopup.create("EC Private Key", "2"))
	            .buildValueNode();
	    txt_bobDiffieHellman3.setLayoutData(Layout.gd_algorithmNodes());
	    
	    arr_bobDiffieHellmanArrow1 = ArrowComponent
	    	.from(txt_bobDiffieHellman1).south()
	    	.to(txt_bobDiffieHellman2).north()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	
	    arr_bobDiffieHellmanArrow2 = ArrowComponent
	    	.from(txt_bobDiffieHellman3).north()
	    	.to(txt_bobDiffieHellman2).south()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	
	}
	
    public void setDiffieHellmanChainVisible(boolean visible) {
    	grp_bobDiffieHellman.setVisible(visible);
    	arr_bobMessagePublicKey.setVisible(visible);
    	arr_bobDiffieHellmanArrow1.setVisible(visible);
    	arr_bobDiffieHellmanArrow2.setVisible(visible);
    }

	private void createBobRootChain() {
	    grp_bobRootChain.setText(RootChainDescription);
	    grp_bobRootChain.setLayout(Layout.gl_rootChainComposite(SWT.LEFT));
	    grp_bobRootChain.setLayoutData(Layout.gd_rootChainComposite());
	    
	    txt_bobRootChain0 = new FlowChartNode.Builder(grp_bobRootChain)
        		.title(bobReceivingChainLabel2)
        		.popupProvider(FlowChartNodePopup.create("Constant", "WhisperChain"))
        		.buildValueNode();
        txt_bobRootChain0.setLayoutData(Layout.gd_algorithmNodesSlim());

	    txt_bobRootChain1 =  new FlowChartNode.Builder(grp_bobRootChain)
	            .title(bobRootChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "4"))
	            .buildValueNode();
	    txt_bobRootChain1.setLayoutData(Layout.gd_algorithmNodes());
	    
	    UiUtils.insertSpacers(grp_bobRootChain, 1, ViewConstants.BOX_WIDTH_SLIM);
	
	    txt_bobRootChain2 =  new FlowChartNode.Builder(grp_bobRootChain)
	            .title(bobRootChainLabel2)
                .popupProvider(FlowChartNodePopup.create(Map.of("Chain key", "5", "New Root-Key", "6")))
	            .buildOperationNode();
	    txt_bobRootChain2.setLayoutData(Layout.gd_algorithmNodes());
	    
	    arr_bobRootChainArrow0 = ArrowComponent
	    		.from(txt_bobRootChain0).south()
	    		.to(txt_bobRootChain2).west()
	    		.on(cmp_bobReceivingAlgorithm)
	    		.create();
	
	    UiUtils.insertSpacers(grp_bobRootChain, 1);
	    
	    txt_bobRootChain3 =  new FlowChartNode.Builder(grp_bobRootChain)
	            .title(bobRootChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "5"))
	            .buildValueNode();
	    txt_bobRootChain3.setLayoutData(Layout.gd_algorithmNodes());
	    arr_bobRootChainArrow1 = ArrowComponent
	    	.from(txt_bobRootChain1).south()
	    	.to(txt_bobRootChain2).north()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	
	    arr_bobRootChainArrow2 = ArrowComponent
	    	.from(txt_bobRootChain2).south()
	    	.to(txt_bobRootChain3).north()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	
	}
	
	public void setRootChainVisible(boolean visible) {
		arr_bobSpace1.setVisible(visible);
		grp_bobRootChain.setVisible(visible);
		arr_bobRootChainArrow0.setVisible(visible);
		arr_bobRootChainArrow1.setVisible(visible);
		arr_bobRootChainArrow2.setVisible(visible);
	}

	private void createBobReceivingChain() {
        grp_bobReceivingChain.setLayout(Layout.gl_sendingReceivingChainComposite(SWT.RIGHT));
        grp_bobReceivingChain.setLayoutData(Layout.gd_sendingReceivingChainComposite());
        grp_bobReceivingChain.setText(ReceivingChainDescription);
        
        txt_bobReceivingChain1 = new FlowChartNode.Builder(grp_bobReceivingChain)
                .title(bobReceivingChainLabel3)
	            .popupProvider(FlowChartNodePopup.create("Constant", "WhisperMessage"))
                .buildValueNode();
        txt_bobReceivingChain1.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_bobReceivingChain2 =  new FlowChartNode.Builder(grp_bobReceivingChain)
                .title(bobReceivingChainLabel1)
	            .popupProvider(FlowChartNodePopup.create("Chain-Key", "7"))
                .buildValueNode();
        txt_bobReceivingChain2.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_bobReceivingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_bobReceivingChain3 =  new FlowChartNode.Builder(grp_bobReceivingChain)
                .title(bobReceivingChainLabel2)
	            .popupProvider(FlowChartNodePopup.create(Map.of("Message Key", "9", "New Chain Key", "10")))
                .buildOperationNode();
        txt_bobReceivingChain3.setLayoutData(Layout.gd_algorithmNodes());
        
	    arr_bobReceivingChainArrow1 = ArrowComponent
	    	.from(txt_bobReceivingChain1).south()
	    	.to(txt_bobReceivingChain3).west()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	    
	    arr_bobReceivingChainArrow2 = ArrowComponent
	    	.from(txt_bobReceivingChain2).south()
	    	.to(txt_bobReceivingChain3).north()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();

        UiUtils.insertSpacers(grp_bobReceivingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

                txt_bobReceivingChain5 =  new FlowChartNode.Builder(grp_bobReceivingChain)
                .title(bobReceivingChainLabel5)
	            .popupProvider(FlowChartNodePopup.create("Message-Key", "9"))
                .buildValueNode();
        txt_bobReceivingChain5.setLayoutData(Layout.gd_algorithmNodes());
        
        arr_bobReceivingChainArrow3 = ArrowComponent
	    	.from(txt_bobReceivingChain3).south()
	    	.to(txt_bobReceivingChain5).north()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
	        }

	public void setReceivingChainVisible(boolean visible) {
		arr_bobSpace2.setVisible(visible);
		grp_bobReceivingChain.setVisible(visible);
		arr_bobReceivingChainArrow1.setVisible(visible);
		arr_bobReceivingChainArrow2.setVisible(visible);
		arr_bobReceivingChainArrow3.setVisible(visible);
    }
	
    private void createBobDecryptedMessagebox() {
	    grp_bobDecryptedMessage.setLayout(Layout.gl_messageboxGroup());
	    grp_bobDecryptedMessage.setLayoutData(Layout.gd_messageboxComposite());
	    grp_bobDecryptedMessage.setText("Nachricht entschlüsseln");
	    
    	txt_bobReceivingChain4 =  new FlowChartNode.Builder(grp_bobDecryptedMessage)
                .title(bobReceivingChainLabel4)
	            .popupProvider(FlowChartNodePopup.create("Message Keys", "10"))
                .buildValueNode();
        txt_bobReceivingChain4.setLayoutData(Layout.gd_algorithmNodes());
        
	    txt_bobPlainText = new Text(
	            grp_bobDecryptedMessage,
	            SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY
	    );
	    txt_bobPlainText.setText(MessageboxPlainText);
	    txt_bobPlainText.setLayoutData(Layout.gd_Messagebox());
	    txt_bobPlainText.setEditable(false);
	}
    
    public void setDecryptedMessageboxVisible(boolean visible) {
		arr_bobSpace3.setVisible(visible);
    	grp_bobDecryptedMessage.setVisible(visible);
    }

	private void createBobArrowSpaces() {
		arr_bobSpace1 = ArrowComponent
				.from(grp_bobDiffieHellman, txt_bobDiffieHellman2).east()
				.to(txt_bobRootChain2, txt_bobRootChain2).west()
				.on(cmp_bobReceivingAlgorithm)
				.withDefaults();
		arr_bobSpace2 = ArrowComponent
				.from(grp_bobRootChain, txt_bobRootChain2).east()
				.to(txt_bobReceivingChain3, txt_bobReceivingChain3).west()
				.on(cmp_bobReceivingAlgorithm)
				.breakBetween()
	    	    	.first(grp_bobRootChain, Side.EAST)
	    	    	.second(grp_bobReceivingChain, Side.WEST)
	    	    	.at(ArrowComponent.BREAK_CENTER)
	    	    .arrowId("cmp_bobArrowSpace2")
				.withDefaults();
		
        arr_bobSpace3 = ArrowComponent
	    	.from(txt_bobReceivingChain3).east()
	    	.to(grp_bobDecryptedMessage, txt_bobReceivingChain4).west()
	    	.on(cmp_bobReceivingAlgorithm)
	    	.withDefaults();
    }
	
	public void setAllVisible(boolean visible) {
		setEncryptedMessageBoxVisible(visible);
		setDiffieHellmanChainVisible(visible);
		setRootChainVisible(visible);
		setReceivingChainVisible(visible);
		setDecryptedMessageboxVisible(visible);
	}

}
