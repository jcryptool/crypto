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

public class DoubleRatchetAliceReceivingContent implements DoubleRatchetEntityContent {

    StyledText txt_aliceReceivingStep5;
    StyledText txt_aliceReceivingStep6;
    StyledText txt_aliceReceivingStep7;
    StyledText txt_aliceReceivingStep8;
    StyledText txt_aliceReceivingStep9;

    FlowChartNode txt_rootChainTop;
    FlowChartNode txt_rootChainConst;
    FlowChartNode txt_rootChainMid;
    FlowChartNode txt_rootChainBot;
    FlowChartNode txt_receivingChainTop;
    FlowChartNode txt_receivingChainConst;
    FlowChartNode txt_receivingChainMid;
    FlowChartNode txt_messageKeys;
    FlowChartNode txt_receivingChainBot;
    FlowChartNode txt_diffieHellmanTop;
    FlowChartNode txt_diffieHellmanMid;
    FlowChartNode txt_diffieHellmanBot;

    Text txt_plainText;
    Text txt_cipherText;

    private String step5 = Messages.DoubleRatchet_Step + " 5 " + Templating.forAlice(Messages.DoubleRatchet_Step5Receiving);
    private String step6 = Messages.DoubleRatchet_Step + " 6 " + Templating.forAlice(Messages.DoubleRatchet_Step6);
    private String step7 = Messages.DoubleRatchet_Step + " 7 " + Templating.forAlice(Messages.DoubleRatchet_Step7);
    private String step8 = Messages.DoubleRatchet_Step + " 8 " + Templating.forAlice(Messages.DoubleRatchet_Step8);
    private String step9 = Messages.DoubleRatchet_Step + " 9 " + Templating.forAlice(Messages.DoubleRatchet_Step9);

    private String DiffieHellmanLabelTop = Templating.forAlice(Messages.DoubleRatchet_DiffieHellmanLabelTop);
    private String DiffieHellmanLabelMid = Templating.forAlice(Messages.DoubleRatchet_DiffieHellmanLabelMid);
    private String DiffieHellmanLabelBot = Templating.forAlice(Messages.DoubleRatchet_DiffieHellmanLabelBot);
    private String RootChainLabelTop = Messages.DoubleRatchet_RootChainLabelTop;
    private String RootChainLabelMid = Messages.DoubleRatchet_RootChainLabelMid;
    private String RootChainLabelBot = Messages.DoubleRatchet_RootChainLabelBot;
    private String ReceivingChainLabelTop = Messages.DoubleRatchet_ReceivingChainLabelTop;
    private String ReceivingChainLabelMid = Messages.DoubleRatchet_ReceivingChainLabelMid;
    private String ReceivingChainLabelBot = Messages.DoubleRatchet_ReceivingChainLabelBot;
    private String ChainLabelConst = Messages.DoubleRatchet_ChainLabelConst;
    private String MessageKeyLabel = Messages.DoubleRatchet_MessageKeyLabel;



    private String MessageboxCipherText = "The Ciphertext";
    private String MessageboxPlainText = "Geben Sie hier Ihre Nachricht an Alice ein.";
    private String step = Messages.DoubleRatchet_Step;
    private String MessageBoxDescription = Messages.SignalEncryption_MessageboxDescription;
    private String DiffieHellmanGroupDescription = step + " 6" + Messages.SignalEncryption_DiffieHellmanGroupDescription;
    private String RootChainDescription = step + " 7" + Messages.SignalEncryption_RootChainDescription;
    private String ReceivingChainDescription = step + " 8" + Messages.SignalEncryption_ReceivingChainDescription;

    protected ArrowComponent arr_messagePublicKey;
    protected ArrowComponent arr_diffieHellman1;
    protected ArrowComponent arr_diffieHellman2;
    protected ArrowComponent arr_rootChain1;
    protected ArrowComponent arr_rootChain2;
    protected ArrowComponent arr_rootChain3;
    protected ArrowComponent arr_receivingChain1;
    protected ArrowComponent arr_receivingChain2;
    protected ArrowComponent arr_receivingChain3;
    protected ArrowComponent arr_space3;
    protected ArrowComponent arr_space1;
    protected ArrowComponent arr_space2;
    protected ImageComponent drw_outgoingMailIcon;

    Group grp_receivingChain;
    Group grp_rootChain;
    Group grp_diffieHellman;
    Group grp_messageBox;
    Group grp_decryptedMessage;

    private ComponentDrawComposite cmp_aliceReceivingAlgorithm;
    private List<StyledText> stepDescriptions;

    @Override
    public Composite buildStepsContent(Composite parent) {
        Composite cmp_aliceReceivingSteps = new Composite(parent, SWT.NONE);
        cmp_aliceReceivingSteps.setLayout(Layout.gl_stepsComposite());

        txt_aliceReceivingStep5 = new StyledText(cmp_aliceReceivingSteps, SWT.WRAP | SWT.READ_ONLY);
        txt_aliceReceivingStep5.setText(step5);
        txt_aliceReceivingStep5.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceReceivingStep6 = new StyledText(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep6.setText(step6);
        txt_aliceReceivingStep6.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceReceivingStep7 = new StyledText(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep7.setText(step7);
        txt_aliceReceivingStep7.setLayoutData(Layout.gd_longDescriptionTexts());
        txt_aliceReceivingStep8 = new StyledText(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep8.setText(step8);
        txt_aliceReceivingStep8.setLayoutData(Layout.gd_shortDescriptionTexts());
        txt_aliceReceivingStep9 = new StyledText(cmp_aliceReceivingSteps, SWT.READ_ONLY | SWT.WRAP);
        txt_aliceReceivingStep9.setText(step9);
        txt_aliceReceivingStep9.setLayoutData(Layout.gd_longDescriptionTexts());

        stepDescriptions = List.of(txt_aliceReceivingStep5, txt_aliceReceivingStep6, txt_aliceReceivingStep7,
                txt_aliceReceivingStep8, txt_aliceReceivingStep9);
        return cmp_aliceReceivingSteps;
    }

    @Override
    public Composite buildAlgorithmContent(Composite parent) {
        cmp_aliceReceivingAlgorithm = new ComponentDrawComposite(parent, SWT.NONE);
        cmp_aliceReceivingAlgorithm.setLayout(Layout.gl_algorithmGroup());
        cmp_aliceReceivingAlgorithm.setLayoutData(Layout.gd_algorithmGroup());

        grp_decryptedMessage = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_receivingChain = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_rootChain = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_diffieHellman = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);
        grp_messageBox = new Group(cmp_aliceReceivingAlgorithm, SWT.NONE);

        createReceivingChain();
        createRootChain();
        createDiffieHellmanRatchet();
        createEncryptedMessagebox();
        createDecryptedMessagebox();
        createArrowSpaces();
        return cmp_aliceReceivingAlgorithm;
    }

    private void createDiffieHellmanRatchet() {
        grp_diffieHellman.setText(DiffieHellmanGroupDescription);
        grp_diffieHellman.setLayout(Layout.gl_diffieHellmanComposite());
        grp_diffieHellman.setLayoutData(Layout.gd_diffieHellmanComposite());

        txt_diffieHellmanTop = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelTop)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeEcPublic, DUMMY)).valueNode();
        txt_diffieHellmanTop.setLayoutData(Layout.gd_algorithmNodes());

        arr_messagePublicKey = ArrowComponent.fromAnchors().fromAnchorX(grp_messageBox, Side.WEST)
                .fromAnchorY(txt_diffieHellmanTop, Side.EAST).outgoingDirection(Side.WEST)
                .toAnchorX(grp_diffieHellman, Side.EAST).toAnchorY(txt_diffieHellmanTop, Side.EAST)
                .incomingDirection(Side.EAST).on(cmp_aliceReceivingAlgorithm).create();

        txt_diffieHellmanMid = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelMid)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeSharedSecret, DUMMY)).operationNode();
        txt_diffieHellmanMid.setLayoutData(Layout.gd_algorithmNodes());

        txt_diffieHellmanBot = new FlowChartNode.Builder(grp_diffieHellman).title(DiffieHellmanLabelBot)
                .popupProvider(FlowChartNodePopup.create(Messages.DoubleRatchet_TypeEcPrivate, DUMMY)).valueNode();
        txt_diffieHellmanBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_diffieHellman1 = ArrowComponent.from(txt_diffieHellmanTop).south().to(txt_diffieHellmanMid).north()
                .on(cmp_aliceReceivingAlgorithm).create();

        arr_diffieHellman2 = ArrowComponent.from(txt_diffieHellmanBot).north().to(txt_diffieHellmanMid).south()
                .on(cmp_aliceReceivingAlgorithm).create();
    }

    public void setDiffieHellmanRatchetVisible(boolean visible) {
        grp_diffieHellman.setVisible(visible);
        arr_messagePublicKey.setVisible(visible);
        arr_diffieHellman1.setVisible(visible);
        arr_diffieHellman2.setVisible(visible);
    }

    private void createRootChain() {
        grp_rootChain.setText(RootChainDescription);
        grp_rootChain.setLayout(Layout.gl_rootChainComposite(SWT.RIGHT));
        grp_rootChain.setLayoutData(Layout.gd_rootChainComposite());

        txt_rootChainTop = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelTop)
                .popupProvider(FlowChartNodePopup.create("Root chain", "0")).valueNode();
        txt_rootChainTop.setLayoutData(Layout.gd_algorithmNodes());

        txt_rootChainConst = new FlowChartNode.Builder(grp_rootChain).title(ChainLabelConst)
                .popupProvider(FlowChartNodePopup.create("Konstante", "0")).valueNode();
        txt_rootChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_rootChainMid = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelMid)
                .popupProvider(FlowChartNodePopup.create(
                        Messages.DoubleRatchet_TypeRootChainKey, DUMMY,
                        Messages.DoubleRatchet_TypeNewRootChainKey, DUMMY))
                .operationNode();
        txt_rootChainMid.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_rootChain, 1);

        arr_rootChain1 = ArrowComponent.from(txt_rootChainTop).south().to(txt_rootChainMid).north()
                .on(cmp_aliceReceivingAlgorithm).create();

        arr_rootChain2 = ArrowComponent.from(txt_rootChainConst).south().to(txt_rootChainMid).east()
                .on(cmp_aliceReceivingAlgorithm).create();

        txt_rootChainBot = new FlowChartNode.Builder(grp_rootChain).title(RootChainLabelBot)
                .popupProvider(FlowChartNodePopup.create("Neuer Root key", "0")).valueNode();
        txt_rootChainBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_rootChain3 = ArrowComponent.from(txt_rootChainMid).south().to(txt_rootChainBot).north()
                .on(cmp_aliceReceivingAlgorithm).create();
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

        txt_receivingChainTop = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelTop)
                .popupProvider(FlowChartNodePopup.create("Receive Chain Key", "0")).valueNode();
        txt_receivingChainTop.setLayoutData(Layout.gd_algorithmNodes());

        txt_receivingChainConst = new FlowChartNode.Builder(grp_receivingChain).title(ChainLabelConst)
                .popupProvider(FlowChartNodePopup.create("Constant", "0")).valueNode();
        txt_receivingChainConst.setLayoutData(Layout.gd_algorithmNodesSlim());

        txt_receivingChainMid = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelMid)
                .popupProvider(FlowChartNodePopup.create(
                        MessageKeyLabel, DUMMY, Messages.DoubleRatchet_TypeNewChainKey, DUMMY))
                .operationNode();
        txt_receivingChainMid.setLayoutData(Layout.gd_algorithmNodes());

        UiUtils.insertSpacers(grp_receivingChain, 1);

        arr_receivingChain1 = ArrowComponent.from(txt_receivingChainTop).south().to(txt_receivingChainMid).north()
                .on(cmp_aliceReceivingAlgorithm).create();
        arr_receivingChain2 = ArrowComponent.from(txt_receivingChainConst).south().to(txt_receivingChainMid).east()
                .on(cmp_aliceReceivingAlgorithm).create();

        txt_receivingChainBot = new FlowChartNode.Builder(grp_receivingChain).title(ReceivingChainLabelBot)
                .popupProvider(FlowChartNodePopup.create("Neuer Receiving Chain Key", "0")).valueNode();
        txt_receivingChainBot.setLayoutData(Layout.gd_algorithmNodes());

        arr_receivingChain3 = ArrowComponent.from(txt_receivingChainMid).south().to(txt_receivingChainBot).north()
                .on(cmp_aliceReceivingAlgorithm).create();
    }

    public void setReceivingChainVisible(boolean visible) {
        arr_space2.setVisible(visible);
        grp_receivingChain.setVisible(visible);
        arr_receivingChain1.setVisible(visible);
        arr_receivingChain2.setVisible(visible);
        arr_receivingChain3.setVisible(visible);
    }

    private void createEncryptedMessagebox() {
        grp_messageBox.setLayout(Layout.gl_messageBoxGroup());
        grp_messageBox.setLayoutData(Layout.gd_messageBoxComposite());
        grp_messageBox.setText(MessageBoxDescription);

        txt_cipherText = new Text(grp_messageBox, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
        txt_cipherText.setText(MessageboxCipherText);
        txt_cipherText.setLayoutData(Layout.gd_Messagebox());
        txt_cipherText.setFont(FontService.getNormalMonospacedFont());

        drw_outgoingMailIcon = ImageComponent.on(cmp_aliceReceivingAlgorithm).relativeTo(txt_cipherText, Side.EAST)
                .offsetX(ViewConstants.MAIL_ICON_X_OFFSET).incomingMail();
        // This one is a special spacer: it doesn't have any content but ensures that
        // the image drawn
        // (which does NOT have a concept of layouting) has enough space to be drawn.
        // This is necessary because the icon is the last element of the view.
        var requiredWidth = drw_outgoingMailIcon.imageWidth() + ViewConstants.MAIL_ICON_X_OFFSET
                - ViewConstants.ARROW_CANVAS_WIDTH;
        UiUtils.insertSpacers(cmp_aliceReceivingAlgorithm, 1, requiredWidth - 5);

    }

    public void setEncryptedMessageBoxVisible(boolean visible) {
        drw_outgoingMailIcon.setVisible(visible);
        grp_messageBox.setVisible(visible);
    }

    private void createDecryptedMessagebox() {
        grp_decryptedMessage.setLayout(Layout.gl_messageBoxGroup());
        grp_decryptedMessage.setLayoutData(Layout.gd_messageBoxComposite());
        grp_decryptedMessage.setText("Nachricht entschlüsseln");

        txt_messageKeys = new FlowChartNode.Builder(grp_decryptedMessage).title(MessageKeyLabel)
                .popupProvider(FlowChartNodePopup.create("MessageKeys", "0")).valueNode();
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
        arr_space1 = ArrowComponent.from(grp_diffieHellman, txt_diffieHellmanMid).west()
                .to(txt_rootChainMid, txt_rootChainMid).east().on(cmp_aliceReceivingAlgorithm).withDefaults();

        arr_space2 = ArrowComponent.from(grp_rootChain, txt_rootChainMid).west()
                .to(txt_receivingChainMid, txt_receivingChainMid).east().on(cmp_aliceReceivingAlgorithm).breakBetween()
                .first(grp_rootChain, Side.EAST).second(grp_receivingChain, Side.WEST).at(ArrowComponent.BREAK_CENTER)
                .withDefaults();

        arr_space3 = ArrowComponent.from(txt_receivingChainMid).west().to(grp_decryptedMessage, txt_messageKeys).east()
                .on(cmp_aliceReceivingAlgorithm).create();
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
