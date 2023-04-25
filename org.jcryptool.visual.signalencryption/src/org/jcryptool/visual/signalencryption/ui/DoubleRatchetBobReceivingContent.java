package org.jcryptool.visual.signalencryption.ui;

import java.util.List;

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

public class DoubleRatchetBobReceivingContent implements DoubleRatchetEntityContent {

    StyledText txt_bobReceivingStep5;
    StyledText txt_bobReceivingStep6;
    StyledText txt_bobReceivingStep7;
    StyledText txt_bobReceivingStep8;
    StyledText txt_bobReceivingStep9;

    FlowChartNode txt_rootChainConst;
    FlowChartNode txt_rootChainTop;
    FlowChartNode txt_rootChainMid;
    FlowChartNode txt_rootChainBot;
    FlowChartNode txt_receivingChainConst;
    FlowChartNode txt_receivingChainTop;
    FlowChartNode txt_receivingChainMid;
    FlowChartNode txt_messageKeys;
    FlowChartNode txt_receivingChainBot;
    FlowChartNode txt_diffieHellmanTop;
    FlowChartNode txt_diffieHellmanMid;
    FlowChartNode txt_diffieHellmanBot;

    Text txt_plainText;
    Text txt_cipherText;

    private String step5Initial = Messages.DoubleRatchet_Step + " 5 " + Messages.DoubleRatchet_Step5ReceivingInitial;
    private String step6Initial = Messages.DoubleRatchet_Step + " 6 " + Messages.DoubleRatchet_Step6Initial;
    private String step7Initial = Messages.DoubleRatchet_Step + " 7 " + Messages.DoubleRatchet_Step7Initial;
    private String step8Initial = Messages.DoubleRatchet_Step + " 8 " + Messages.DoubleRatchet_Step8Initial;
    private String step9Initial = Messages.DoubleRatchet_Step + " 9 " + Messages.DoubleRatchet_Step9Initial;

    private String step5 = Messages.DoubleRatchet_Step + " 5 " + Templating.forBob(Messages.DoubleRatchet_Step5Receiving);
    private String step6 = Messages.DoubleRatchet_Step + " 6 " + Templating.forBob(Messages.DoubleRatchet_Step6);
    private String step7 = Messages.DoubleRatchet_Step + " 7 " + Templating.forBob(Messages.DoubleRatchet_Step7);
    private String step8 = Messages.DoubleRatchet_Step + " 8 " + Templating.forBob(Messages.DoubleRatchet_Step8);
    private String step9 = Messages.DoubleRatchet_Step + " 9 " + Templating.forBob(Messages.DoubleRatchet_Step9);

    private String DiffieHellmanLabelTop = Templating.forBob(Messages.DoubleRatchet_DiffieHellmanLabelTop);
    private String DiffieHellmanLabelMid = Templating.forBob(Messages.DoubleRatchet_DiffieHellmanLabelMid);
    private String DiffieHellmanLabelBot = Templating.forBob(Messages.DoubleRatchet_DiffieHellmanLabelBot);
    private String RootChainLabelTop = Messages.DoubleRatchet_RootChainLabelTop;
    private String RootChainLabelMid = Messages.DoubleRatchet_RootChainLabelMid;
    private String RootChainLabelBot = Messages.DoubleRatchet_RootChainLabelBot;
    private String ReceivingChainLabelTop = Messages.DoubleRatchet_ReceivingChainLabelTop;
    private String ReceivingChainLabelMid = Messages.DoubleRatchet_ReceivingChainLabelMid;
    private String ReceivingChainLabelBot = Messages.DoubleRatchet_ReceivingChainLabelBot;
    private String ChainLabelConst = Messages.DoubleRatchet_ChainLabelConst;
    private String MessageKeyLabel = Messages.DoubleRatchet_MessageKeyLabel;

    private String step = Messages.DoubleRatchet_Step;
    private String DiffieHellmanGroupDescription = step + " 6" + Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String RootChainDescription = step + " 7" + Messages.SignalEncryption_RootChainDescription;
    private String ReceivingChainDescription = step + " 8" + Messages.SignalEncryption_ReceivingChainDescription;

    private String MessageboxCipherText = "The Ciphertext";
    private String MessageboxPlainText = "Geben Sie hier Ihre Nachricht an Bob ein.";
    private String MessageboxDescription = Messages.SignalEncryption_MessageboxDescription;

    protected ArrowComponent arr_bobMessagePublicKey;
    protected ArrowComponent arr_receivingChain1;
    protected ArrowComponent arr_receivingChain2;
    protected ArrowComponent arr_receivingChain3;
    protected ArrowComponent arr_space3;
    protected ArrowComponent arr_diffieHellman1;
    protected ArrowComponent arr_diffieHellman2;
    protected ArrowComponent arr_rootChain1;
    protected ArrowComponent arr_rootChain2;
    protected ArrowComponent arr_rootChain3;

    Group grp_receivingChain;
    Group grp_rootChain;
    Group grp_diffieHellman;
    Group grp_messageBox;
    Group grp_decryptedMessage;

    ComponentDrawComposite cmp_bobReceivingAlgorithm;
    ArrowComponent arr_space1;
    ArrowComponent arr_space2;
    ImageComponent drw_incomingMailIcon;

    private List<StyledText> stepDescriptions;

    @Override
    public Composite buildStepsContent(Composite parent) {
        Composite cmp_bobReceivingSteps = new Composite(parent, SWT.NONE);
        cmp_bobReceivingSteps.setLayout(Layout.gl_stepsComposite());

        txt_bobReceivingStep5 = new StyledText(cmp_bobReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep5.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep6 = new StyledText(cmp_bobReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep6.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep7 = new StyledText(cmp_bobReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep7.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep8 = new StyledText(cmp_bobReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep8.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_bobReceivingStep9 = new StyledText(cmp_bobReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_bobReceivingStep9.setLayoutData(Layout.gd_shortDescriptionTexts());

        stepDescriptions = List.of(txt_bobReceivingStep5, txt_bobReceivingStep6, txt_bobReceivingStep7,
                txt_bobReceivingStep8, txt_bobReceivingStep9);
        setInitialStepDescriptions();
        return cmp_bobReceivingSteps;
    }

    public void setInitialStepDescriptions() {
        txt_bobReceivingStep5.setText(step5Initial);
        txt_bobReceivingStep6.setText(step6Initial);
        txt_bobReceivingStep7.setText(step7Initial);
        txt_bobReceivingStep8.setText(step8Initial);
        txt_bobReceivingStep9.setText(step9Initial);
    }

    public void setNormalStepDescriptions() {
        txt_bobReceivingStep5.setText(step5);
        txt_bobReceivingStep6.setText(step6);
        txt_bobReceivingStep7.setText(step7);
        txt_bobReceivingStep8.setText(step8);
        txt_bobReceivingStep9.setText(step9);
    }

    @Override
    public Composite buildAlgorithmContent(Composite parent) {
        cmp_bobReceivingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_bobReceivingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        cmp_bobReceivingAlgorithm.setLayoutData(Layout.gd_algorithmGroup());

        createMailIcon();
        grp_messageBox = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_diffieHellman = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_rootChain = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_receivingChain = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);
        grp_decryptedMessage = new Group(cmp_bobReceivingAlgorithm, SWT.NONE);

        createEncryptedMessageBox();
        createDiffieHellmanRatchet();
        createRootChain();
        createReceivingChain();
        createDecryptedMessagebox();
        createArrowSpaces();
        return cmp_bobReceivingAlgorithm;
    }

    private void createMailIcon() {
        UiUtils.insertSpacers(cmp_bobReceivingAlgorithm, 0, 0);
        drw_incomingMailIcon = ImageComponent.on(cmp_bobReceivingAlgorithm)
                .xOffsetFromEast() // so we don't have to subtract the width of the img ourself (we draw to the left)
                .offsetX(-ViewConstants.MAIL_ICON_X_OFFSET) // minus because we want to draw to the left
                .setAnchorLater() // defer setting the location until the object is created
                .incomingMail();

        // This one is a special spacer: it doesn't have any content but ensures that
        // the image drawn
        // (which does NOT have a concept of layouting) has enough space to be drawn.
        // This is necessary because the icon is the last element of the view.
        // The x offset is taken twice, so we have even distance left and right
        var requiredWidth = drw_incomingMailIcon.imageWidth() + (ViewConstants.MAIL_ICON_X_OFFSET * 2)
                - ViewConstants.ARROW_CANVAS_WIDTH;
        UiUtils.insertSpacers(cmp_bobReceivingAlgorithm, 1, requiredWidth - 5);

    }

    private void createEncryptedMessageBox() {
        grp_messageBox.setLayout(Layout.gl_messageBoxGroup());
        grp_messageBox.setLayoutData(Layout.gd_messageBoxComposite());
        grp_messageBox.setText(MessageboxDescription);

        txt_cipherText = new Text(grp_messageBox, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_cipherText.setText(MessageboxCipherText);
        txt_cipherText.setLayoutData(Layout.gd_Messagebox());
        txt_cipherText.setEditable(false);
        txt_cipherText.setFont(FontService.getNormalMonospacedFont());

        // This has to live here, because the icon has to come into existance before the
        // messagebox, but still needs
        // a reference to the messagebox to infer it's location.
        drw_incomingMailIcon.setRelativeTo(grp_messageBox, Side.WEST);
    }

    public void setEncryptedMessageBoxVisible(boolean visible) {
        grp_messageBox.setVisible(visible);
    }

    private void createDiffieHellmanRatchet() {
        grp_diffieHellman.setText(DiffieHellmanGroupDescription);
        grp_diffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
        grp_diffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());

        txt_diffieHellmanTop = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelTop)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeEcPublic, DUMMY)).valueNode();
        txt_diffieHellmanTop.setLayoutData(Layout.gd_algorithmNodes());

        arr_bobMessagePublicKey = ArrowComponent.fromAnchors().fromAnchorX(grp_messageBox, Side.EAST)
                .fromAnchorY(txt_diffieHellmanTop, Side.WEST).outgoingDirection(Side.EAST)
                .toAnchorX(grp_diffieHellman, Side.WEST).toAnchorY(txt_diffieHellmanTop, Side.WEST)
                .incomingDirection(Side.WEST).on(cmp_bobReceivingAlgorithm).create();

        txt_diffieHellmanMid = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelMid)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeSharedSecret, DUMMY)).operationNode();
        txt_diffieHellmanMid.setLayoutData(Layout.gd_algorithmNodes());

        txt_diffieHellmanBot = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelBot)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeEcPrivate, DUMMY)).valueNode();
        txt_diffieHellmanBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_diffieHellman1 = ArrowComponent.from(txt_diffieHellmanTop).south().to(txt_diffieHellmanMid).north()
                .on(cmp_bobReceivingAlgorithm).withDefaults();

        arr_diffieHellman2 = ArrowComponent.from(txt_diffieHellmanBot).north().to(txt_diffieHellmanMid).south()
                .on(cmp_bobReceivingAlgorithm).withDefaults();
    }

    public void setDiffieHellmanRatchetVisible(boolean visible) {
        grp_diffieHellman.setVisible(visible);
        arr_bobMessagePublicKey.setVisible(visible);
        arr_diffieHellman1.setVisible(visible);
        arr_diffieHellman2.setVisible(visible);
    }

    private void createRootChain() {
        grp_rootChain.setText(RootChainDescription);
        grp_rootChain.setLayout(Layout.gl_rootChainComposite(SWT.LEFT));
        grp_rootChain.setLayoutData(Layout.gd_rootChainComposite());

        txt_rootChainConst = new FlowChartNode.Builder(grp_rootChain).title(ChainLabelConst)
                .popupProvider(FlowChartNodePopup.create("Constant", "WhisperChain")).valueNode();
        txt_rootChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_rootChainTop = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelTop)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "4")).valueNode();
        txt_rootChainTop.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_rootChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_rootChainMid = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelMid)
                .popupProvider(FlowChartNodePopup.create(
                        Messages.DoubleRatchet_TypeRootChainKey, DUMMY,
                        Messages.DoubleRatchet_TypeNewRootChainKey, DUMMY))
                .operationNode();
        txt_rootChainMid.setLayoutData(Layout.gd_algorithmNodes());

        arr_rootChain1 = ArrowComponent.from(txt_rootChainConst).south().to(txt_rootChainMid).west()
                .on(cmp_bobReceivingAlgorithm).create();

        UiUtils.insertSpacers(grp_rootChain, 1);

        txt_rootChainBot = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelBot)
                .popupProvider(FlowChartNodePopup.create("Root-Key", "5")).valueNode();
        txt_rootChainBot.setLayoutData(Layout.gd_algorithmNodes());
        arr_rootChain2 = ArrowComponent.from(txt_rootChainTop).south().to(txt_rootChainMid).north()
                .on(cmp_bobReceivingAlgorithm).withDefaults();

        arr_rootChain3 = ArrowComponent.from(txt_rootChainMid).south().to(txt_rootChainBot).north()
                .on(cmp_bobReceivingAlgorithm).withDefaults();
    }

    public void setRootChainVisible(boolean visible) {
        arr_space1.setVisible(visible);
        grp_rootChain.setVisible(visible);
        arr_rootChain1.setVisible(visible);
        arr_rootChain2.setVisible(visible);
        arr_rootChain3.setVisible(visible);
    }

    private void createReceivingChain() {
        grp_receivingChain.setLayout(Layout.gl_sendingReceivingChainComposite(SWT.RIGHT));
        grp_receivingChain.setLayoutData(Layout.gd_sendingReceivingChainComposite());
        grp_receivingChain.setText(ReceivingChainDescription);

        txt_receivingChainConst = new FlowChartNode.Builder(grp_receivingChain).title(ChainLabelConst)
                .popupProvider(FlowChartNodePopup.create("Constant", "WhisperMessage")).valueNode();
        txt_receivingChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_receivingChainTop = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelTop)
                .popupProvider(FlowChartNodePopup.create("Chain-Key", "7")).valueNode();
        txt_receivingChainTop.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_receivingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_receivingChainMid = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelMid)
                .popupProvider(FlowChartNodePopup.create(
                        MessageKeyLabel, DUMMY,
                        Messages.DoubleRatchet_TypeNewChainKey, DUMMY))
                .operationNode();
        txt_receivingChainMid.setLayoutData(Layout.gd_algorithmNodes());

        arr_receivingChain1 = ArrowComponent.from(txt_receivingChainConst).south().to(txt_receivingChainMid).west()
                .on(cmp_bobReceivingAlgorithm).withDefaults();

        arr_receivingChain2 = ArrowComponent.from(txt_receivingChainTop).south().to(txt_receivingChainMid).north()
                .on(cmp_bobReceivingAlgorithm).withDefaults();

        UiUtils.insertSpacers(grp_receivingChain, 1, ViewConstants.BOX_WIDTH_SLIM);

        txt_receivingChainBot = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelBot)
                .popupProvider(FlowChartNodePopup.create("Message-Key", "9")).valueNode();
        txt_receivingChainBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_receivingChain3 = ArrowComponent.from(txt_receivingChainMid).south().to(txt_receivingChainBot).north()
                .on(cmp_bobReceivingAlgorithm).withDefaults();
    }

    public void setReceivingChainVisible(boolean visible) {
        arr_space2.setVisible(visible);
        grp_receivingChain.setVisible(visible);
        arr_receivingChain1.setVisible(visible);
        arr_receivingChain2.setVisible(visible);
        arr_receivingChain3.setVisible(visible);
    }

    private void createDecryptedMessagebox() {
        grp_decryptedMessage.setLayout(Layout.gl_messageBoxGroup());
        grp_decryptedMessage.setLayoutData(Layout.gd_messageBoxComposite());
        grp_decryptedMessage.setText("Nachricht entschlüsseln");

        txt_messageKeys = new FlowChartNode.Builder(grp_decryptedMessage).title(MessageKeyLabel)
                .popupProvider(FlowChartNodePopup.create("Message Keys", "10")).valueNode();
        txt_messageKeys.setLayoutData(Layout.gd_algorithmNodes());

        txt_plainText = new Text(grp_decryptedMessage,
                SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_plainText.setText(MessageboxPlainText);
        txt_plainText.setLayoutData(Layout.gd_Messagebox());
        txt_plainText.setEditable(false);
    }

    public void setDecryptedMessageboxVisible(boolean visible) {
        arr_space3.setVisible(visible);
        grp_decryptedMessage.setVisible(visible);
    }

    private void createArrowSpaces() {
        arr_space1 = ArrowComponent.from(grp_diffieHellman, txt_diffieHellmanMid).east()
                .to(txt_rootChainMid, txt_rootChainMid).west().on(cmp_bobReceivingAlgorithm).withDefaults();
        arr_space2 = ArrowComponent.from(grp_rootChain, txt_rootChainMid).east()
                .to(txt_receivingChainMid, txt_receivingChainMid).west().on(cmp_bobReceivingAlgorithm).breakBetween()
                .first(grp_rootChain, Side.EAST).second(grp_receivingChain, Side.WEST).at(ArrowComponent.BREAK_CENTER)
                .arrowId("cmp_bobArrowSpace2").withDefaults();

        arr_space3 = ArrowComponent.from(txt_receivingChainMid).east().to(grp_decryptedMessage, txt_messageKeys).west()
                .on(cmp_bobReceivingAlgorithm).withDefaults();
    }

    public void setAllVisible(boolean visible) {
        setEncryptedMessageBoxVisible(visible);
        setDiffieHellmanRatchetVisible(visible);
        setRootChainVisible(visible);
        setReceivingChainVisible(visible);
        setDecryptedMessageboxVisible(visible);
    }

    @Override
    public void showStep(DoubleRatchetStep step) {
        showStep(step, stepDescriptions, DoubleRatchetStep.INDEX_OF_RECEIVING_STEPS);
    }
}
