package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.util.ToHex.toHex;
import static org.jcryptool.visual.signalencryption.util.UiUtils.onSelection;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;
import org.jcryptool.visual.signalencryption.ui.SignalEncryptionView.Tab;
import org.jcryptool.visual.signalencryption.util.UiUtils;
import org.whispersystems.libsignal.state.PreKeyBundle;

public class OverviewView extends Composite {

	private static final int KEY_SIZE = 32;

	private final SignalEncryptionView parentView;

	private Composite overViewComposite;
	private TitleAndDescriptionComposite titleAndDescription;
	private Group grp_identitiesInfo;
	private Group grp_doubleRatchetInfo;
	private Text txt_aliceIdentity;
	private Text txt_bobIdentity;
	private FlowChartNode node_aliceKeyBundle;
	private FlowChartNode node_bobKeyBundle;
	private Button btn_switchToDoubleRatchetView;
	private Button btn_newKeysBoth;
	private Button btn_newKeysAlice;
	private Button btn_newKeysBob;

	private StyledText txt_doubleRatchetExplanation;

	/** First tab (overview tab) content **/
	public OverviewView(
			Composite parent,
			int style,
			SignalEncryptionView parentView
	) {
		super(parent, style);
		this.parentView = parentView;
		setLayout(new GridLayout());

		createTitleAndDescription();
		createBody();
		updateValues();
	}

	private void createTitleAndDescription() {
		titleAndDescription = new TitleAndDescriptionComposite(this);
		titleAndDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		titleAndDescription.setTitle(Messages.SignalEncryption_Title);
		titleAndDescription.setDescription(Messages.SignalEncryption_Description);
	}

	private void createBody() {
		overViewComposite = new Composite(this, SWT.NONE);
		overViewComposite.setLayout(new GridLayout(2, false));
		overViewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		grp_identitiesInfo = new Group(overViewComposite, SWT.NONE);
		grp_identitiesInfo.setLayout(new GridLayout(3, false));
		grp_identitiesInfo.setText(Messages.Overview_GroupTitleIdentities);
		grp_identitiesInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		grp_doubleRatchetInfo = new Group(overViewComposite, SWT.NONE);
		grp_doubleRatchetInfo.setText(Messages.Overview_GroupTitleDoubleRatchet);
		grp_doubleRatchetInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		grp_doubleRatchetInfo.setLayout(new GridLayout(1, true));
	
		createIdentitiesGroup();
		createDoubleRatchetInformation();
	}

	private void createIdentitiesGroup() {
		node_aliceKeyBundle = new FlowChartNode.Builder(grp_identitiesInfo)
				.title(Messages.Name_AliceGenitive_Space + Messages.Overview_PreKeyBundle)
				.buildValueNode();
		node_aliceKeyBundle.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, false).get());
		
		txt_aliceIdentity = createIdentityText(grp_identitiesInfo);
		// Button for generating new keys for Alice
		btn_newKeysAlice = new Button(grp_identitiesInfo, SWT.PUSH);
		btn_newKeysAlice.setText(Messages.Overview_generateIdentityPerson);
		btn_newKeysAlice.setLayoutData(gd_createKeyButton());
		btn_newKeysAlice.addSelectionListener(onSelection(selectionEvent -> {
				if (discardIfNecessary()) {
					generateAlice();
				}
		}));

		node_bobKeyBundle = new FlowChartNode.Builder(grp_identitiesInfo)
				.title(Messages.Name_BobGenitive_Space + Messages.Overview_PreKeyBundle)
				.buildValueNode();
		node_bobKeyBundle.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, false).get());
		
		txt_bobIdentity = createIdentityText(grp_identitiesInfo);
		
		//// Button for generation new keys for Bob
		btn_newKeysBob = new Button(grp_identitiesInfo, SWT.PUSH);
		btn_newKeysBob.setText(Messages.Overview_generateIdentityPerson);
		btn_newKeysBob.setLayoutData(gd_createKeyButton());
		btn_newKeysBob.addSelectionListener(onSelection(selectionEvent -> {
				if (discardIfNecessary()) {
					generateBob();
				}
		}));
		
		// Button for generation new keys for both parties
		UiUtils.insertSpacers(grp_identitiesInfo, 2);
		btn_newKeysBoth = new Button(grp_identitiesInfo, SWT.PUSH);
		btn_newKeysBoth.setText(Messages.Overview_generateIdentityBoth);
		btn_newKeysBoth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btn_newKeysBoth.addSelectionListener(onSelection(selectionEvent -> {
			if (discardIfNecessary()) {
				generateBoth();
			}
		}));
		insertHorizontalSeparator(grp_identitiesInfo);
		
		new Explainer(grp_identitiesInfo, SWT.NONE, Messages.Overview_QuestionPreKeyBundle, Messages.Overview_AnswerPreKeyBundle)
		.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		new Explainer(grp_identitiesInfo, SWT.NONE, Messages.Overview_QuestionX3DH, Messages.Overview_AnswerX3DH)
		.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
	}

	private void createDoubleRatchetInformation() {
		
		txt_doubleRatchetExplanation = new StyledText(grp_doubleRatchetInfo, SWT.BORDER);
		txt_doubleRatchetExplanation.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		txt_doubleRatchetExplanation.setText(Messages.Overview_DoubleRatchetOverview);
		
		btn_switchToDoubleRatchetView = new Button(grp_doubleRatchetInfo, SWT.PUSH);
		btn_switchToDoubleRatchetView.setFont(FontService.getNormalBoldFont());
		btn_switchToDoubleRatchetView.setText(Messages.Overview_showDoubleRatchet);
		btn_switchToDoubleRatchetView.setLayoutData(
				GridDataBuilder.with(SWT.CENTER, SWT.TOP, true, false).heightHint(40).get()
		);
		btn_switchToDoubleRatchetView.addSelectionListener(
				onSelection(selectionEvent -> parentView.setTab(Tab.DOUBLE_RATCHET))
		);
		insertHorizontalSeparator(grp_doubleRatchetInfo);
		new Explainer(grp_doubleRatchetInfo, SWT.NONE, Messages.Overview_QuestionDoubleRatchetSecurity, Messages.Overview_AnswerDoubleRatchetSecurity)
		.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		new Explainer(grp_doubleRatchetInfo, SWT.NONE, Messages.Overview_QuestionDoubleRatchetInit, Messages.Overview_AnswerDoubleRatchetInit)
		.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}

	private boolean discardIfNecessary() {
		if (AlgorithmState.get().getCommunication().inProgress()) {
			var messageBox = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
			messageBox.setMessage(Messages.Overview_DiscardWarningTitle);
			messageBox.setMessage(Messages.Overview_DiscardWarningMessage);
			return messageBox.open() == SWT.OK;
		}
		return true;
	}

	public void generateBoth() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBoth();
		parentView.resetDoubleRatchetView();
		updateValues();
	}

	public void generateAlice() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateAlice();
		parentView.resetDoubleRatchetView();
		updateValues();
	}

	public void generateBob() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBob();
		parentView.resetDoubleRatchetView();
		updateValues();
	}

	/** Updates all UI elements with the actual values (keys, finger-prints, etc.) */
	public void updateValues() {
		var aliceBundle = AlgorithmState.get().getSignalEncryptionAlgorithm().getAlicePreKeyBundle();
		var bobBundle = AlgorithmState.get().getSignalEncryptionAlgorithm().getBobPreKeyBundle();
		
		var aliceKeyInformation = unpackBundle(aliceBundle);
		var bobKeyInformation = unpackBundle(bobBundle);
		
		txt_aliceIdentity.setText(aliceKeyInformation.get("Identity Public Key"));
		txt_bobIdentity.setText(bobKeyInformation.get("Identity Public Key"));
		
		node_aliceKeyBundle.getPopupProvider().setValues(aliceKeyInformation);
		node_bobKeyBundle.getPopupProvider().setValues(bobKeyInformation);
	}
	
	private Map<String, String> unpackBundle(PreKeyBundle bundle) {
		var map = new TreeMap<String, String>();
		map.put(
				Messages.Overview_IdentityPublicKey,
				toHex(bundle.getIdentityKey().getPublicKey().serialize(), 1, KEY_SIZE)
		);
		map.put(
				Messages.Overview_PreKey,
				toHex(bundle.getPreKey().serialize(), 1, KEY_SIZE)
		);
		map.put(
				Messages.Overview_PreKeySignature,
				toHex(bundle.getSignedPreKeySignature())
		);
		return map;
	}
	
	private Text createIdentityText(Composite parent) {
		var grp_fingerprint = new Group(parent, SWT.NONE);
		grp_fingerprint.setLayout(new GridLayout());
		grp_fingerprint.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		grp_fingerprint.setText(Messages.Overview_IdentityFingerprint);
		
		var text = new Text(grp_fingerprint, SWT.WRAP);
		text.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.TOP, true, false).widthHint(80).get());
		text.setEditable(false);
		text.setFont(FontService.getNormalMonospacedFont());
		text.setBackground(ColorService.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		return text;
	}
	
	private GridData gd_createKeyButton() {
		return GridDataBuilder.with(SWT.FILL, SWT.CENTER, false, false).get();
	}
	
	private void insertHorizontalSeparator(Composite parent) {
		var numColumns = ((GridLayout) parent.getLayout()).numColumns;
		var separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		var gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = numColumns;
		separator.setLayoutData(gridData);
	}
}
