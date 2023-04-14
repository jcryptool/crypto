package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.util.ToHex.toHex;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;
import org.jcryptool.visual.signalencryption.ui.SignalEncryptionView.Tab;
import org.whispersystems.libsignal.state.PreKeyBundle;

public class SignalEncryptionViewOverview extends Composite {

	private static final int KEY_SIZE = 32;
	private Composite overViewComposite;

	// Title and description
	private TitleAndDescriptionComposite titleAndDescription;

	// Groups
	private Group keyTableGroup;

	// GridLayouts
	private GridLayout gl_overViewComposite;
	private GridLayout gl_keyTableGroup;

	// GridData
	private GridData gd_overViewComposite;
	private GridData gd_keyTableGroup;
	private GridData gd_text;
	

	// TableTexts

	private Button btn_newKeysBoth;

	private final DoubleRatchetView doubleRatchetTabComposite;
	private final SignalEncryptionView parentView;

	private Text txt_aliceIdentity;
	private Text txt_bobIdentity;

	private FlowChartNode node_aliceKeyBundle;
	private FlowChartNode node_bobKeyBundle;

	private Group grp_doubleRatchetInfo;

	private Button btn_switchToDoubleRatchetView;

	private Button btn_newKeysAlice;

	private Button btn_newKeysBob;

	/**
	 * 
	 **/
	public SignalEncryptionViewOverview(
			Composite parent,
			int style,
			DoubleRatchetView doubleRatchetTabComposite,
			SignalEncryptionView parentView
	) {
		super(parent, style);
		this.doubleRatchetTabComposite = doubleRatchetTabComposite;
		this.parentView = parentView;
		setLayout(new GridLayout());

		setTitleAndDescription();
		createOverViewComposite();
		updateValues();
	}

	private void setTitleAndDescription() {
	
		titleAndDescription = new TitleAndDescriptionComposite(this);
		titleAndDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		titleAndDescription.setTitle(Messages.SignalEncryption_TabTitle1);
		titleAndDescription.setDescription(Messages.SignalEncryption_TabDesc1);
	}

	private void createOverViewComposite() {
		overViewComposite = new Composite(this, SWT.NONE);
		gl_overViewComposite = new GridLayout(2, false);
		gl_overViewComposite.marginWidth = 0;
		gd_overViewComposite = new GridData(SWT.FILL, SWT.FILL, false, true);
		overViewComposite.setLayout(gl_overViewComposite);
		overViewComposite.setLayoutData(gd_overViewComposite);
	
		createTableGroup();
	
	}

	private void createKeyInformation() {
		gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_text.widthHint = 300;
		
		node_aliceKeyBundle = new FlowChartNode.Builder(keyTableGroup)
				.title("Alice Pre-Key-Bundle")
				.buildValueNode();
		node_aliceKeyBundle.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, false).get());
		node_bobKeyBundle = new FlowChartNode.Builder(keyTableGroup)
				.title("Bob Pre-Key-Bundle")
				.buildValueNode();
		node_bobKeyBundle.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, false).get());
		
		txt_aliceIdentity = createIdentityText(keyTableGroup);
		txt_bobIdentity = createIdentityText(keyTableGroup);
		
		// Button for generating new keys for Alice
		btn_newKeysAlice = new Button(keyTableGroup, SWT.PUSH);
		btn_newKeysAlice.setText(Messages.SignalEncryption_btn_generateAlice);
		btn_newKeysAlice.setLayoutData(gd_createKeyButton());
		btn_newKeysAlice.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateAlice();
				}
			}
		});

		// Button for generation new keys for Bob
		btn_newKeysBob = new Button(keyTableGroup, SWT.PUSH);
		btn_newKeysBob.setText(Messages.SignalEncryption_btn_generateBob);
		btn_newKeysBob.setLayoutData(gd_createKeyButton());
		btn_newKeysBob.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateBob();
				}
			}
		});
		
		btn_newKeysBoth = new Button(keyTableGroup, SWT.PUSH);
		btn_newKeysBoth.setText(Messages.SignalEncryption_btn_generateBoth);
		btn_newKeysBoth.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btn_newKeysBoth.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateBoth();
				}
			}
		});


	}

	private void createTableGroup() {
	
		keyTableGroup = new Group(overViewComposite, SWT.NONE);
		gl_keyTableGroup = new GridLayout(2, false);
		gd_keyTableGroup = new GridData(SWT.FILL, SWT.FILL, true, true);
	
		keyTableGroup.setLayout(gl_keyTableGroup);
		keyTableGroup.setText(Messages.SignalEncryption_TblTitel_Key);
		keyTableGroup.setLayoutData(gd_keyTableGroup);
		
		grp_doubleRatchetInfo = new Group(overViewComposite, SWT.NONE);
		grp_doubleRatchetInfo.setText("Double Ratchet");
		grp_doubleRatchetInfo.setLayoutData(gd_keyTableGroup);
		grp_doubleRatchetInfo.setLayout(new GridLayout(1, true));
	
		createKeyInformation();
		createDoubleRatchetInformation();
	
	}

	private void createDoubleRatchetInformation() {
		btn_switchToDoubleRatchetView = new Button(grp_doubleRatchetInfo, SWT.PUSH);
		btn_switchToDoubleRatchetView.setLayoutData(GridDataBuilder.with(SWT.CENTER, SWT.TOP, true, true).widthHint(200).heightHint(100).get());
		btn_switchToDoubleRatchetView.setText("Show Double-Ratchet");
		
		btn_switchToDoubleRatchetView.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parentView.setTab(Tab.DOUBLE_RATCHET);
			}
		});
	}

	private boolean discardIfNecessary() {
		if (AlgorithmState.get().getCommunication().inProgress()) {
			var messageBox = new MessageBox(getShell(), SWT.OK | SWT.CANCEL);
			messageBox.setMessage("Warning");
			messageBox.setMessage("Generating a new identity resets all the progress. Do you want to continue?");
			return messageBox.open() == SWT.OK;
		}
		return true;
	}

	public void generateBoth() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBoth();
		doubleRatchetTabComposite.resetAll();
		updateValues();
	}

	public void generateAlice() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateAlice();
		doubleRatchetTabComposite.resetAll();
		updateValues();
	}

	public void generateBob() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBob();
		doubleRatchetTabComposite.resetAll();
		updateValues();
	}
	
	public void reinitializeDoubleRatchetTab() {
		doubleRatchetTabComposite.resetAll();
	}

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
		
		map.put("Identity Public Key", toHex(bundle.getIdentityKey().getPublicKey().serialize(), 1, KEY_SIZE));
		map.put("Pre Key", toHex(bundle.getPreKey().serialize(), 1, KEY_SIZE));
		map.put("Pre Key-Signatur", toHex(bundle.getSignedPreKeySignature()));
		
		return map;
	}
	
	private Text createIdentityText(Composite parent) {
		
		var grp_fingerprint = new Group(parent, SWT.NONE);
		grp_fingerprint.setLayout(new GridLayout());
		grp_fingerprint.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.TOP, false, false).widthHint(80).get());
		grp_fingerprint.setText("Identity fingerprint");
		
		var text = new Text(grp_fingerprint, SWT.WRAP);
		text.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.TOP, true, false).widthHint(80).get());
		text.setEditable(false);
		text.setFont(FontService.getNormalMonospacedFont());
		return text;
	}
	
	private GridData gd_createKeyButton() {
		return GridDataBuilder.with(SWT.CENTER, SWT.CENTER, false, false).get();
	}
}
