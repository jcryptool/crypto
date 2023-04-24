package org.jcryptool.visual.signalencryption.ui;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponent;
import org.jcryptool.visual.signalencryption.graphics.ComponentDrawComposite;
import org.jcryptool.visual.signalencryption.graphics.ImageComponent;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.util.Templating;
import org.jcryptool.visual.signalencryption.util.UiUtils;

public class DoubleRatchetAliceSendingContent implements DoubleRatchetEntityContent {

    StyledText txt_aliceSendingStep1;
    StyledText txt_aliceSendingStep2;
    StyledText txt_aliceSendingStep3;
    StyledText txt_aliceSendingStep4;
    StyledText txt_aliceSendingStep5;

    FlowChartNode txt_diffieHellmanTop;
    FlowChartNode txt_diffieHellmanMid;
    FlowChartNode txt_diffieHellmanBot;
    FlowChartNode txt_rootChainConst;
    FlowChartNode txt_rootChainTop;
    FlowChartNode txt_rootChainMid;
    FlowChartNode txt_rootChainBot;
    FlowChartNode txt_sendingChainTop;
    FlowChartNode txt_sendingChainConst;
    FlowChartNode txt_sendingChainMid;
    FlowChartNode txt_messageKeys;
    FlowChartNode txt_sendingChainBot;

    Text txt_plainText;
    Text txt_cipherText;

    ComponentDrawComposite cmp_aliceSendingAlgorithm;
    Composite cmp_aliceDiffieHellman;
    Composite cmp_aliceRootChain;
    Composite cmp_aliceSteps;
    Composite cmp_messageBox;

    Group grp_diffieHellman;
    Group grp_rootChain;
    Group grp_sendingChain;

    private String step1Initial = Messages.DoubleRatchet_Step + " 1 " + Messages.DoubleRatchet_Step1Initial;
    private String step2Initial = Messages.DoubleRatchet_Step + " 2 " + Messages.DoubleRatchet_Step2Initial;
    private String step3Initial = Messages.DoubleRatchet_Step + " 3 " + Messages.DoubleRatchet_Step3Initial;
    private String step4Initial = Messages.DoubleRatchet_Step + " 4 " + Messages.DoubleRatchet_Step4Initial;
    private String step5Initial = Messages.DoubleRatchet_Step + " 5 " + Messages.DoubleRatchet_Step5SendingInitial;

    private String step1 = Messages.DoubleRatchet_Step + " 1 " + Templating.forAlice(Messages.DoubleRatchet_Step1);
    private String step2 = Messages.DoubleRatchet_Step + " 2 " + Templating.forAlice(Messages.DoubleRatchet_Step2);
    private String step3 = Messages.DoubleRatchet_Step + " 3 " + Templating.forAlice(Messages.DoubleRatchet_Step3);
    private String step4 = Messages.DoubleRatchet_Step + " 4 " + Templating.forAlice(Messages.DoubleRatchet_Step4);
    private String step5 = Messages.DoubleRatchet_Step + " 5 " + Templating.forAlice(Messages.DoubleRatchet_Step5Sending);

    private String aliceDiffieHellmanLabel1 = Messages.SignalEncryption_aliceDiffieHellmanLabel1;
    private String aliceDiffieHellmanLabel2 = Messages.SignalEncryption_aliceDiffieHellmanLabel2;
    private String aliceDiffieHellmanLabel3 = Messages.SignalEncryption_aliceDiffieHellmanLabel3;
    private String aliceRootChainLabel1 = Messages.SignalEncryption_aliceRootChainLabel1;
    private String aliceRootChainLabel2 = Messages.SignalEncryption_aliceRootChainLabel2;
    private String aliceRootChainLabel3 = Messages.SignalEncryption_aliceRootChainLabel3;
    private String aliceSendingChainLabel1 = Messages.SignalEncryption_aliceSendingChainLabel1;
    private String aliceSendingChainLabel2 = Messages.SignalEncryption_aliceSendingChainLabel2;
    private String aliceSendingChainLabel3 = Messages.SignalEncryption_aliceSendingChainLabel3;
    private String aliceSendingChainLabel4 = Messages.SignalEncryption_aliceSendingChainLabel4;
    private String aliceSendingChainLabel5 = Messages.SignalEncryption_aliceSendingChainLabel5;


    private String step = Messages.DoubleRatchet_Step;
    private String DiffieHellmanGroupDescription = step + " 1" + Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String RootChainDescription = step + " 2" + Messages.SignalEncryption_RootChainDescription;
    private String SendingChainDescription = step + " 3" + Messages.SignalEncryption_SendingChainDescription;
    private String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;

    protected ArrowComponent arr_diffieHellman1;
    protected ArrowComponent arr_diffieHellman2;
    protected ArrowComponent arr_rootChain1;
    protected ArrowComponent arr_rootChain2;
    protected ArrowComponent arr_rootChain3;
    protected ArrowComponent arr_sendingChain1;
    protected ArrowComponent arr_sendingChain2;
    protected ArrowComponent arr_sendingChain3;
    protected ArrowComponent arr_aliceReceivingChainArrow1;
    protected ArrowComponent arr_aliceReceivingChainArrow2;
    protected ArrowComponent arr_aliceReceivingChainArrow3;
    protected ArrowComponent arr_aliceReceivingChainArrow4;
    protected ArrowComponent arr_space1;
    protected ArrowComponent arr_space2;
    protected ArrowComponent arr_space3;
    protected ImageComponent drw_outgoingMailIcon;
    private List<StyledText> stepDescriptions;

    @Override
    public Composite buildStepsContent(Composite parent) {
        var cmp_aliceSendingSteps = new Composite(parent, SWT.NONE);
        cmp_aliceSendingSteps.setLayout(Layout.gl_stepsComposite());

        txt_aliceSendingStep1 = new StyledText(cmp_aliceSendingSteps, SWT.WRAP | SWT.READ_ONLY);
        txt_aliceSendingStep1.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceSendingStep2 = new StyledText(cmp_aliceSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceSendingStep2.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceSendingStep3 = new StyledText(cmp_aliceSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceSendingStep3.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceSendingStep4 = new StyledText(cmp_aliceSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceSendingStep4.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceSendingStep5 = new StyledText(cmp_aliceSendingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceSendingStep5.setLayoutData(Layout.gd_longDescriptionTexts());

        stepDescriptions = List.of(txt_aliceSendingStep1, txt_aliceSendingStep2, txt_aliceSendingStep3,
                txt_aliceSendingStep4, txt_aliceSendingStep5);
        setInitialStepDescriptions();
        return cmp_aliceSendingSteps;
    }

    public void setInitialStepDescriptions() {
        txt_aliceSendingStep1.setText(step1Initial);
        txt_aliceSendingStep2.setText(step2Initial);
        txt_aliceSendingStep3.setText(step3Initial);
        txt_aliceSendingStep4.setText(step4Initial);
        txt_aliceSendingStep5.setText(step5Initial);
    }

    public void setNormalStepDescriptions() {
        txt_aliceSendingStep1.setText(step1);
        txt_aliceSendingStep2.setText(step2);
        txt_aliceSendingStep3.setText(step3);
        txt_aliceSendingStep4.setText(step4);
        txt_aliceSendingStep5.setText(step5);
    }

    @Override
    public ComponentDrawComposite buildAlgorithmContent(Composite parent) {
        cmp_aliceSendingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_aliceSendingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        cmp_aliceSendingAlgorithm.setLayoutData(Layout.gd_algorithmGroup());

        grp_diffieHellman = new Group(cmp_aliceSendingAlgorithm, SWT.NONE);
        grp_rootChain = new Group(cmp_aliceSendingAlgorithm, SWT.NONE);
        grp_sendingChain = new Group(cmp_aliceSendingAlgorithm, SWT.NONE);
        cmp_messageBox = new Composite(cmp_aliceSendingAlgorithm, SWT.NONE);

        createDiffieHellmanRatchet();
        createRootChain();
        createSendingChain();
        createMessageBox();
        createArrowSpaces();

        return cmp_aliceSendingAlgorithm;
    }

    private void createDiffieHellmanRatchet() {
        grp_diffieHellman.setText(DiffieHellmanGroupDescription);
        grp_diffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
        grp_diffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());

        txt_diffieHellmanTop = new FlowChartNode.Builder(grp_diffieHellman).title(aliceDiffieHellmanLabel1)
                .popupProvider(FlowChartNodePopup.create("EC Public Key", "1")).buildValueNode();

        txt_diffieHellmanTop.setLayoutData(Layout.gd_algorithmNodes());

        txt_diffieHellmanMid = new FlowChartNode.Builder(grp_diffieHellman).title(aliceDiffieHellmanLabel2)
                .popupProvider(FlowChartNodePopup.create("Output", "2")).buildOperationNode();
        txt_diffieHellmanMid.setLayoutData(Layout.gd_algorithmNodes());

        txt_diffieHellmanBot = new FlowChartNode.Builder(grp_diffieHellman).title(aliceDiffieHellmanLabel3)
                .popupProvider(FlowChartNodePopup.create("EC Private key", "3")).buildValueNode();
        txt_diffieHellmanBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_diffieHellman1 = ArrowComponent.from(txt_diffieHellmanTop).south().to(txt_diffieHellmanMid).north()
                .on(cmp_aliceSendingAlgorithm).withDefaults();

        arr_diffieHellman2 = ArrowComponent.from(txt_diffieHellmanBot).north().to(txt_diffieHellmanMid).south()
                .on(cmp_aliceSendingAlgorithm).withDefaults();
    }

    public void setDiffieHellmanRatchetVisible(boolean visible) {
        grp_diffieHellman.setVisible(visible);
        arr_diffieHellman1.setVisible(visible);
        arr_diffieHellman2.setVisible(visible);
    }

    private void createRootChain() {
        grp_rootChain.setText(RootChainDescription);
        grp_rootChain.setLayout(Layout.gl_rootChainComposite(SWT.LEFT));
        grp_rootChain.setLayoutData(Layout.gd_rootChainComposite());

        txt_rootChainConst = new FlowChartNode.Builder(grp_rootChain).title(aliceSendingChainLabel2)
                .popupProvider(FlowChartNodePopup.create("Constant", "WhisperChain")).buildValueNode();
        txt_rootChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_rootChainTop = new FlowChartNode.Builder(grp_rootChain).title(aliceRootChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "4")).buildValueNode();
        txt_rootChainTop.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_rootChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_rootChainMid = new FlowChartNode.Builder(grp_rootChain).title(aliceRootChainLabel2)
                .popupProvider(FlowChartNodePopup.create(Map.of("Chain key", "5", "New Root-Key", "6")))
                .buildOperationNode();
        txt_rootChainMid.setLayoutData(Layout.gd_algorithmNodes());

        arr_rootChain1 = ArrowComponent.from(txt_rootChainConst).south().to(txt_rootChainMid).west()
                .on(cmp_aliceSendingAlgorithm).create();

        UiUtils.insertSpacers(grp_rootChain, 1);

        txt_rootChainBot = new FlowChartNode.Builder(grp_rootChain).title(aliceRootChainLabel3)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "5")).buildValueNode();
        txt_rootChainBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_rootChain2 = ArrowComponent.from(txt_rootChainTop).south().to(txt_rootChainMid).north()
                .on(cmp_aliceSendingAlgorithm).withDefaults();

        arr_rootChain3 = ArrowComponent.from(txt_rootChainMid).south().to(txt_rootChainBot).north()
                .on(cmp_aliceSendingAlgorithm).withDefaults();
    }

    public void setRootChainVisible(boolean visible) {
        arr_space1.setVisible(visible);
        grp_rootChain.setVisible(visible);
        arr_rootChain1.setVisible(visible);
        arr_rootChain2.setVisible(visible);
        arr_rootChain3.setVisible(visible);
    }

    private void createSendingChain() {
        grp_sendingChain.setLayout(Layout.gl_sendingReceivingChainComposite(SWT.LEFT));
        grp_sendingChain.setLayoutData(Layout.gd_sendingReceivingChainComposite());
        grp_sendingChain.setText(SendingChainDescription);

        txt_sendingChainConst = new FlowChartNode.Builder(grp_sendingChain).title(aliceSendingChainLabel2)
                .popupProvider(FlowChartNodePopup.create("Constant", "WhisperMessage")).buildValueNode();
        txt_sendingChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_sendingChainTop = new FlowChartNode.Builder(grp_sendingChain).title(aliceSendingChainLabel1)
                .popupProvider(FlowChartNodePopup.create("Chain-Key", "7")).buildValueNode();
        txt_sendingChainTop.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_sendingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_sendingChainMid = new FlowChartNode.Builder(grp_sendingChain).title(aliceSendingChainLabel3)
                .popupProvider(FlowChartNodePopup.create(Map.of("Message Key", "9", "New Chain Key", "10")))
                .buildOperationNode();
        txt_sendingChainMid.setLayoutData(Layout.gd_algorithmNodes());

        arr_sendingChain1 = ArrowComponent.from(txt_sendingChainConst).south().to(txt_sendingChainMid).west()
                .on(cmp_aliceSendingAlgorithm).withDefaults();

        arr_sendingChain2 = ArrowComponent.from(txt_sendingChainTop).south().to(txt_sendingChainMid).north()
                .on(cmp_aliceSendingAlgorithm).withDefaults();

        UiUtils.insertSpacers(grp_sendingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_sendingChainBot = new FlowChartNode.Builder(grp_sendingChain).title(aliceSendingChainLabel5)
                .popupProvider(FlowChartNodePopup.create("Message-Key", "9")).buildValueNode();
        txt_sendingChainBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_sendingChain3 = ArrowComponent.from(txt_sendingChainMid).south().to(txt_sendingChainBot).north()
                .on(cmp_aliceSendingAlgorithm).withDefaults();
    }

    public void setSendingChainVisible(boolean visible) {
        arr_space2.setVisible(visible);
        grp_sendingChain.setVisible(visible);
        arr_sendingChain1.setVisible(visible);
        arr_sendingChain2.setVisible(visible);
        arr_sendingChain3.setVisible(visible);
        arr_space3.setVisible(visible);
        // Part of what we consider the chain is already part in the MessageBox
        // composite
        cmp_messageBox.setVisible(visible);
        txt_messageKeys.setVisible(visible);
        txt_plainText.setVisible(!visible);
        txt_cipherText.setVisible(!visible);
    }

    private void createMessageBox() {
        cmp_messageBox.setLayout(Layout.gl_messageBoxGroup());
        cmp_messageBox.setLayoutData(Layout.gd_messageBoxComposite());

        txt_plainText = new Text(cmp_messageBox, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        txt_plainText.setLayoutData(Layout.gd_Messagebox());

        txt_messageKeys = new FlowChartNode.Builder(cmp_messageBox).title(aliceSendingChainLabel4)
                .popupProvider(FlowChartNodePopup.create("Chain Key", "10")).buildValueNode();
        txt_messageKeys.setLayoutData(Layout.gd_algorithmNodes());

        txt_cipherText = new Text(cmp_messageBox, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_cipherText.setLayoutData(Layout.gd_Messagebox());

        txt_cipherText.setFont(FontService.getNormalMonospacedFont());
        txt_cipherText.setText("Encrypted Message");
        txt_plainText.setTextLimit(256);
        txt_plainText.setEditable(true);

        drw_outgoingMailIcon = ImageComponent.on(cmp_aliceSendingAlgorithm).relativeTo(txt_cipherText, Side.EAST)
                .offsetX(ViewConstants.MAIL_ICON_X_OFFSET).outgoingMailRight();

        // This one is a special spacer: it doesn't have any content but ensures that
        // the image drawn
        // (which does NOT have a concept of layouting) has enough space to be drawn.
        // This is necessary because the icon is the last element of the view.
        var requiredWidth = drw_outgoingMailIcon.imageWidth() + ViewConstants.MAIL_ICON_X_OFFSET
                - ViewConstants.ARROW_CANVAS_WIDTH;
        UiUtils.insertSpacers(cmp_aliceSendingAlgorithm, 1, requiredWidth - 5);
    }

    public void setMessageBoxVisible(boolean visible) {
        txt_plainText.setVisible(visible);
        txt_cipherText.setVisible(visible);
        drw_outgoingMailIcon.setVisible(visible);
    }

    public void showOnlyMessagePlaintext() {
        txt_plainText.setVisible(true);
        txt_cipherText.setVisible(false);
        drw_outgoingMailIcon.setVisible(false);
    }

    private void createArrowSpaces() {
        arr_space1 = ArrowComponent.from(grp_diffieHellman, txt_diffieHellmanMid).east()
                .to(txt_rootChainMid, txt_rootChainMid).west().on(cmp_aliceSendingAlgorithm).withDefaults();

        arr_space2 = ArrowComponent.from(grp_rootChain, txt_rootChainMid).east()
                .to(txt_sendingChainMid, txt_sendingChainMid).west().on(cmp_aliceSendingAlgorithm)
                .arrowId("cmp_aliceArrowSpace2").withDefaults();

        arr_space3 = ArrowComponent.from(txt_sendingChainMid).east().to(cmp_messageBox, txt_sendingChainMid).west()
                .on(cmp_aliceSendingAlgorithm).withDefaults();
    }

    public void setAllVisible(boolean visible) {
        setDiffieHellmanRatchetVisible(visible);
        setRootChainVisible(visible);
        setSendingChainVisible(visible);
        setMessageBoxVisible(visible);
    }

    @Override
    public void showStep(DoubleRatchetStep step) {
        showStep(step, stepDescriptions, 0);
    }
}
