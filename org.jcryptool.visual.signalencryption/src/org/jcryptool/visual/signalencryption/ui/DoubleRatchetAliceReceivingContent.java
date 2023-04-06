package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponent;
import org.jcryptool.visual.signalencryption.graphics.ComponentDrawComposite;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.util.UiUtils;

public class DoubleRatchetAliceReceivingContent implements DoubleRatchetEntityContent {
    
    private String aliceReceivingStep0 = Messages.SignalEncryption_aliceDescriptionText0;
    private String aliceReceivingStep5 = Messages.SignalEncryption_stepText1;
    private String aliceReceivingStep6 = Messages.SignalEncryption_stepText2;
    private String aliceReceivingStep7 = Messages.SignalEncryption_stepText3;
    private String aliceReceivingStep8 = Messages.SignalEncryption_stepText4;
    private String aliceReceivingStep9 = Messages.SignalEncryption_aliceStepText5;
   
    Text txt_aliceReceivingStep0;
    Text txt_aliceReceivingStep5;
    Text txt_aliceReceivingStep6;
    Text txt_aliceReceivingStep7;
    Text txt_aliceReceivingStep8;
    Text txt_aliceReceivingStep9;
    
    FlowChartNode txt_aliceRootChain1;
    FlowChartNode txt_aliceRootChain2;
    FlowChartNode txt_aliceRootChain3;
	FlowChartNode txt_aliceRootChain4;
    FlowChartNode txt_aliceReceivingChain1;
    FlowChartNode txt_aliceReceivingChain2;
    FlowChartNode txt_aliceReceivingChain3;
    FlowChartNode txt_aliceReceivingChain4;
    FlowChartNode txt_aliceReceivingChain5;
    FlowChartNode txt_aliceDiffieHellman1;
    FlowChartNode txt_aliceDiffieHellman2;
    FlowChartNode txt_aliceDiffieHellman3;
    
    Text txt_alicePlainText;
    Text txt_aliceCipherText;
    
    private String aliceDiffieHellmanLabel1 = Messages.SignalEncryption_aliceDiffieHellmanLabel1;
    private String aliceDiffieHellmanLabel2 = Messages.SignalEncryption_aliceDiffieHellmanLabel2;
    private String aliceDiffieHellmanLabel3 = Messages.SignalEncryption_aliceDiffieHellmanLabel3;
    private String aliceRootChainLabel1 = Messages.SignalEncryption_aliceRootChainLabel1;
    private String aliceRootChainLabel2 = Messages.SignalEncryption_aliceRootChainLabel2;
    private String aliceRootChainLabel3 = Messages.SignalEncryption_aliceRootChainLabel3;
    private String aliceReceivingChainLabel1 = Messages.SignalEncryption_aliceReceivingChainLabel1;
    private String aliceReceivingChainLabel2 = Messages.SignalEncryption_aliceReceivingChainLabel2;
    private String aliceReceivingChainLabel3 = Messages.SignalEncryption_aliceReceivingChainLabel3;
    private String aliceReceivingChainLabel4 = Messages.SignalEncryption_aliceReceivingChainLabel4;
    private String aliceReceivingChainLabel5 = Messages.SignalEncryption_aliceReceivingChainLabel5;

	protected ArrowComponent arr_aliceMessagePublicKey;
    protected ArrowComponent arr_aliceDiffieHellmanArrow1;
    protected ArrowComponent arr_aliceDiffieHellmanArrow2;
    protected ArrowComponent arr_aliceRootChainArrow1;
    protected ArrowComponent arr_aliceRootChainArrow2;
	protected ArrowComponent arr_aliceRootChainArrow3;
    protected ArrowComponent arr_aliceReceivingChainArrow1;
    protected ArrowComponent arr_aliceReceivingChainArrow2;
    protected ArrowComponent arr_aliceReceivingChainArrow3;
    protected ArrowComponent arr_aliceReceivingChainArrow4;
    protected ArrowComponent arr_aliceSpace1;
    protected ArrowComponent arr_aliceSpace2;

    Group grp_aliceReceivingChain;
    Group grp_aliceSpace2;
    Group grp_aliceRootChain;
    Group grp_aliceSpace1;
    Group grp_aliceDiffieHellman;
    Group grp_aliceMessagebox;
    Group grp_aliceDecryptedMessage;
    
    Composite cmp_aliceDiffieHellman;
    Composite cmp_aliceRootChain;
    Composite cmp_aliceArrowSpace1;
    Composite cmp_aliceArrowSpace2;

    private String MessageboxCipherText = "The Ciphertext";
    private String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;
    private String RootChainDescription = Messages.SignalEncryption_RootChainDescription;
    private String MessageboxPlainText = "Geben Sie hier Ihre Nachricht an Alice ein.";
    private String DiffieHellmanGroupDescription = Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String ReceivingChainDescription = Messages.SignalEncryption_ReceivingChainDescription;
	private ComponentDrawComposite cmp_aliceReceivingAlgorithm;
    

    @Override
    public Composite buildStepsContent(Composite parent, COMMUNICATION_STATE state) {
        Composite cmp_aliceReceivingSteps = new Composite(parent, SWT.NONE);
        cmp_aliceReceivingSteps.setLayout(Layout.gl_stepsComposite());
        
        txt_aliceReceivingStep0 = new Text(cmp_aliceReceivingSteps, SWT.WRAP | SWT.READ_ONLY);
        txt_aliceReceivingStep0.setText("Alice wartet auf eine Nachricht von Bob");
        txt_aliceReceivingStep0.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceReceivingStep5 = new Text(cmp_aliceReceivingSteps, SWT.WRAP | SWT.READ_ONLY);
        txt_aliceReceivingStep5.setText("Nachricht von Bob empfangen");
        txt_aliceReceivingStep5.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceReceivingStep6 = new Text(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep6.setText("Schritt 6 Diffie Hellman Ratchet: Alice führt mit dem in der Nachricht enthaltenen Schlüsselinformationen den nächsten Diffie Hellman-Schritt durch.");
        txt_aliceReceivingStep6.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceReceivingStep7 = new Text(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep7.setText("Schritt 7 Root Chain: Der Diffie Hellman-Schlüssel und der Output der letzten KDF der Root Chain wird genutzt um einen neuen Root Chain Key zu erzeugen.");
        txt_aliceReceivingStep7.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceReceivingStep8 = new Text(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep8.setText("Schritt 8 Receiving Chain: Der Root Chain-Schlüssel und der Output der letzten KDF in der Receiving Chain wird genutzt um einen neuen Receiving Chain Key (Message Key) zu erzeugen.");
        txt_aliceReceivingStep8.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceReceivingStep9 = new Text(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep9.setText("Schritt 9 Nachricht entschlüsseln: Alice nutzt den Message Key um die Nachricht zu entschlüssln.");
        txt_aliceReceivingStep9.setLayoutData(Layout.gd_longDescriptionTexts());
        return cmp_aliceReceivingSteps;
    }

    
    @Override
    public Composite buildAlgorithmContent(Composite parent, COMMUNICATION_STATE state) {
        cmp_aliceReceivingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_aliceReceivingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        cmp_aliceReceivingAlgorithm.setLayoutData(Layout.gd_algorithmGroup());
        
        UiUtils.insertExcessiveSpacers(cmp_aliceReceivingAlgorithm, 1);
        grp_aliceDecryptedMessage = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_aliceReceivingChain = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_aliceRootChain = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_aliceDiffieHellman = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_aliceMessagebox = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        
        createAliceReceivingChain();
        createAliceRootChain();
        createAliceDiffieHellmanChain();
        createAliceEncryptedMessagebox();
        createAliceDecryptedMessagebox();
        createAliceArrowSpaces();
        return cmp_aliceReceivingAlgorithm;
    }

    private void createAliceReceivingChain() {
        grp_aliceReceivingChain.setLayout(Layout.gl_sendingReceivingChainComposite(SWT.RIGHT));
        grp_aliceReceivingChain.setLayoutData(Layout.gd_sendingReceivingChainComposite());
        grp_aliceReceivingChain.setText(ReceivingChainDescription);
        
        txt_aliceReceivingChain1 = new FlowChartNode.Builder(grp_aliceReceivingChain)
                .title(aliceReceivingChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Receive Chain Key", "0"))
                .buildValueNode();
        txt_aliceReceivingChain1.setLayoutData(Layout.gd_algorithmNodes());

        txt_aliceReceivingChain2 = new FlowChartNode.Builder(grp_aliceReceivingChain)
                .title(aliceReceivingChainLabel2)
                .popupProvider(FlowChartNodePopup.create("Constant", "0"))
                .buildValueNode();
        txt_aliceReceivingChain2.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_aliceReceivingChain3 = new FlowChartNode.Builder(grp_aliceReceivingChain)
                .title(aliceReceivingChainLabel3)
                .popupProvider(FlowChartNodePopup.create("KDF", "0"))
                .buildOperationNode();
        txt_aliceReceivingChain3.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_aliceReceivingChain, 1);
        
        arr_aliceReceivingChainArrow1 = ArrowComponent
        		.from(txt_aliceReceivingChain1).south()
        		.to(txt_aliceReceivingChain3).north()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();
        arr_aliceReceivingChainArrow2 = ArrowComponent
        		.from(txt_aliceReceivingChain2).south()
        		.to(txt_aliceReceivingChain3).east()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();

        txt_aliceReceivingChain5 = new FlowChartNode.Builder(grp_aliceReceivingChain)
                .title(aliceReceivingChainLabel5)
                .popupProvider(FlowChartNodePopup.create("Neuer Receiving Chain Key", "0"))
                .buildValueNode();
        txt_aliceReceivingChain5.setLayoutData(Layout.gd_algorithmNodes());

        arr_aliceReceivingChainArrow3 = ArrowComponent
        		.from(txt_aliceReceivingChain3).south()
        		.to(txt_aliceReceivingChain5).north()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();
    }
    
    public void setReceivingChainVisible(boolean visible) {
		arr_aliceSpace2.setVisible(visible);
    	grp_aliceReceivingChain.setVisible(visible);
		arr_aliceReceivingChainArrow1.setVisible(visible);
		arr_aliceReceivingChainArrow2.setVisible(visible);
		arr_aliceReceivingChainArrow3.setVisible(visible);
    }

    private void createAliceRootChain() {
        grp_aliceRootChain.setText(RootChainDescription);
        grp_aliceRootChain.setLayout(Layout.gl_rootChainComposite(SWT.RIGHT));
        grp_aliceRootChain.setLayoutData(Layout.gd_rootChainComposite());

        txt_aliceRootChain1 = new FlowChartNode.Builder(grp_aliceRootChain)
                .title(aliceRootChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Root chain", "0"))
                .buildValueNode();
        txt_aliceRootChain1.setLayoutData(Layout.gd_algorithmNodes());

        txt_aliceRootChain2 = new FlowChartNode.Builder(grp_aliceRootChain)
                .title(aliceReceivingChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Konstante", "0"))
                .buildValueNode();
        txt_aliceRootChain2.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_aliceRootChain3 = new FlowChartNode.Builder(grp_aliceRootChain)
                .title(aliceRootChainLabel2)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildOperationNode();
        txt_aliceRootChain3.setLayoutData(Layout.gd_algorithmNodes());
        
        UiUtils.insertSpacers(grp_aliceRootChain, 1);

        arr_aliceRootChainArrow1 = ArrowComponent
        		.from(txt_aliceRootChain1).south()
        		.to(txt_aliceRootChain3).north()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();

        arr_aliceRootChainArrow2 = ArrowComponent
        		.from(txt_aliceRootChain2).south()
        		.to(txt_aliceRootChain3).east()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();

        txt_aliceRootChain4 = new FlowChartNode.Builder(grp_aliceRootChain)
                .title(aliceRootChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Neuer Root key", "0"))
                .buildValueNode();
        txt_aliceRootChain4.setLayoutData(Layout.gd_algorithmNodes());

        arr_aliceRootChainArrow3 = ArrowComponent
        		.from(txt_aliceRootChain3).south()
        		.to(txt_aliceRootChain4).north()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();
    }
    
    public void setRootChainVisible(boolean visible) {
		arr_aliceSpace1.setVisible(visible);
    	grp_aliceRootChain.setVisible(visible);
		arr_aliceRootChainArrow1.setVisible(visible);
		arr_aliceRootChainArrow2.setVisible(visible);
		arr_aliceRootChainArrow3.setVisible(visible);
    }

    private void createAliceDiffieHellmanChain() {
        grp_aliceDiffieHellman.setText(DiffieHellmanGroupDescription);
        grp_aliceDiffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
        grp_aliceDiffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());

        txt_aliceDiffieHellman1 = new FlowChartNode.Builder(grp_aliceDiffieHellman)
                .title(aliceDiffieHellmanLabel1)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildValueNode();
        txt_aliceDiffieHellman1.setLayoutData(Layout.gd_algorithmNodes());
        
        arr_aliceMessagePublicKey = ArrowComponent
	    		.fromAnchors()
	    		.fromAnchorX(grp_aliceMessagebox, Side.WEST)
	    		.fromAnchorY(txt_aliceDiffieHellman1, Side.EAST)
	    		.outgoingDirection(Side.WEST)
	    		.toAnchorX(grp_aliceDiffieHellman, Side.EAST)
	    		.toAnchorY(txt_aliceDiffieHellman1, Side.EAST)
	    		.incomingDirection(Side.EAST)
	    		.on(cmp_aliceReceivingAlgorithm)
	    		.create();


        txt_aliceDiffieHellman2 = new FlowChartNode.Builder(grp_aliceDiffieHellman)
                .title(aliceDiffieHellmanLabel2)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildOperationNode();
        txt_aliceDiffieHellman2.setLayoutData(Layout.gd_algorithmNodes());

        txt_aliceDiffieHellman3 = new FlowChartNode.Builder(grp_aliceDiffieHellman)
                .title(aliceDiffieHellmanLabel3)
                .popupProvider(FlowChartNodePopup.create("DH key calculation", "0"))
                .buildValueNode();
        txt_aliceDiffieHellman3.setLayoutData(Layout.gd_algorithmNodes());

        arr_aliceDiffieHellmanArrow1 = ArrowComponent
        		.from(txt_aliceDiffieHellman1).south()
        		.to(txt_aliceDiffieHellman2).north()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();

        arr_aliceDiffieHellmanArrow2 = ArrowComponent
        		.from(txt_aliceDiffieHellman3).north()
        		.to(txt_aliceDiffieHellman2).south()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();

    }
    
    public void setDiffieHellmanChainVisible(boolean visible) {
    	grp_aliceDiffieHellman.setVisible(visible);
    	arr_aliceMessagePublicKey.setVisible(visible);
    	arr_aliceDiffieHellmanArrow1.setVisible(visible);
    	arr_aliceDiffieHellmanArrow2.setVisible(visible);
    }
    
    private void createAliceArrowSpaces() {
        arr_aliceSpace1 = ArrowComponent
				.from(grp_aliceDiffieHellman, txt_aliceDiffieHellman2).west()
				.to(txt_aliceRootChain3, txt_aliceRootChain3).east()
				.on(cmp_aliceReceivingAlgorithm)
				.withDefaults();

        arr_aliceSpace2 = ArrowComponent
				.from(grp_aliceRootChain, txt_aliceRootChain3).west()
				.to(txt_aliceReceivingChain3, txt_aliceReceivingChain3).east()
				.on(cmp_aliceReceivingAlgorithm)
				.breakBetween()
	    	    	.first(grp_aliceRootChain, Side.EAST)
	    	    	.second(grp_aliceReceivingChain, Side.WEST)
	    	    	.at(ArrowComponent.BREAK_CENTER)
	    	    .arrowId("cmp_aliceArrowSpace2")
				.withDefaults();
 
    }

    private void createAliceEncryptedMessagebox() {
        grp_aliceMessagebox.setLayout(Layout.gl_messageboxGroup());
        grp_aliceMessagebox.setLayoutData(Layout.gd_messageboxComposite());
        grp_aliceMessagebox.setText(MessageboxDescription);

        txt_aliceCipherText = new Text(grp_aliceMessagebox,
                SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_aliceCipherText.setText(MessageboxCipherText);
        txt_aliceCipherText.setLayoutData(Layout.gd_Messagebox());
    }
    
    public void setEncryptedMessageBoxVisible(boolean visible) {
    	grp_aliceMessagebox.setVisible(visible);
    }


    private void createAliceDecryptedMessagebox() {
        grp_aliceDecryptedMessage.setLayout(Layout.gl_messageboxGroup());
        grp_aliceDecryptedMessage.setLayoutData(Layout.gd_messageboxComposite());
        grp_aliceDecryptedMessage.setText("Nachricht entschlüsseln");
        
        txt_aliceReceivingChain4 = new FlowChartNode.Builder(grp_aliceDecryptedMessage)
                .title(aliceReceivingChainLabel4)
                .popupProvider(FlowChartNodePopup.create("MessageKeys", "0"))
                .buildValueNode();
        txt_aliceReceivingChain4.setLayoutData(Layout.gd_algorithmNodes());
        
        arr_aliceReceivingChainArrow4 = ArrowComponent
        		.from(txt_aliceReceivingChain3).west()
        		.to(grp_aliceDecryptedMessage, txt_aliceReceivingChain4).east()
        		.on(cmp_aliceReceivingAlgorithm)
        		.create();
        //arr_aliceReceivingChainArrow4 = ArrowComponent.fromAnchors()
	    //	.fromAnchorX(txt_aliceReceivingChain3, Side.WEST)
	    //	.fromAnchorY(txt_aliceReceivingChain3, Side.WEST)
	    //	.outgoingDirection(Side.WEST)
	    //	.toAnchorX(grp_aliceDecryptedMessage, Side.EAST)
	    //	.toAnchorY(txt_aliceReceivingChain4, Side.EAST)
	    //	.incomingDirection(Side.EAST)
	    //	.on(cmp_aliceReceivingAlgorithm)
	    //	.withDefaults();

        txt_alicePlainText = new Text(grp_aliceDecryptedMessage,
                SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_alicePlainText.setText(MessageboxPlainText);
        txt_alicePlainText.setLayoutData(Layout.gd_Messagebox());
	    txt_alicePlainText.setEditable(false);
    }
    
    public void setDecryptedMessageboxVisible(boolean visible) {
    	arr_aliceReceivingChainArrow4.setVisible(visible);
    	grp_aliceDecryptedMessage.setVisible(visible);
    }
    
    public void setAllVisible(boolean visible) {
		setEncryptedMessageBoxVisible(visible);
		setDiffieHellmanChainVisible(visible);
		setRootChainVisible(visible);
		setReceivingChainVisible(visible);
		setDecryptedMessageboxVisible(visible);
	}
}
