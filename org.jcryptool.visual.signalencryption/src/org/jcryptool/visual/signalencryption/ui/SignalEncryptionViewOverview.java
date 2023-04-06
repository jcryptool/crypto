package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;

public class SignalEncryptionViewOverview extends Composite {

	//

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
	private GridData gd_value;

	// TableLabels
	private Label label_column;
	private Label text_column;
	private Label value_column_alice;
	private Label value_column_bob;

	private Label label_b_private;
	private Label label_b_public;
	private Label label_d;

	// TableTexts

	private Text text_b_private;
	private Text text_b_public;
	private Text text_d;

	// TableValues
	private Text value_b_alice_private;
	private Text value_b_alice_public;

	// TableValues
	private Text value_b_bob_private;
	private Text value_b_bob_public;

	private Button btn_regenerate;

	// Variables for the used keys
	private String aliceRatchetPublicKey;
	private String aliceRatchetPrivateKey;

	private String bobRatchetPublicKey;
	private String bobRatchetPrivateKey;

	private DoubleRatchetView doubleRatchetTabComposite;

	/**
	 * 
	 **/
	public SignalEncryptionViewOverview(final Composite parent, final int style,
			DoubleRatchetView doubleRatchetTabComposite) {
		super(parent, style);
		this.doubleRatchetTabComposite = doubleRatchetTabComposite;
		setLayout(new GridLayout());

		setTitleAndDescription();
		setParameter();
		createOverViewComposite();

	}

	private void createDescriptionGroup() {
		// gd_descriptionGroup = new GridData(SWT.FILL, SWT.FILL, true, true);
	}

	private void createTable() {
		gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_value = gd_text;
		gd_text.widthHint = 300;

		// Button for generating all keys new
		btn_regenerate = new Button(keyTableGroup, SWT.PUSH);
		btn_regenerate.setText(Messages.SignalEncryption_btn_generateBoth);
		btn_regenerate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateBoth();
				}
			}

		});

		btn_regenerate.addSelectionListener(new SelectionAdapter() {
		});

		// Button for generating new keys for Alice
		btn_regenerate = new Button(keyTableGroup, SWT.PUSH);
		btn_regenerate.setText(Messages.SignalEncryption_btn_generateAlice);
		btn_regenerate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateAlice();
				}
			}
		});

		// Button for generation new keys for Bob
		btn_regenerate = new Button(keyTableGroup, SWT.PUSH);
		btn_regenerate.setText(Messages.SignalEncryption_btn_generateBob);
		btn_regenerate.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (discardIfNecessary()) {
					generateBob();
				}
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

	private void createTableGroup() {

		keyTableGroup = new Group(overViewComposite, SWT.NONE);
		gl_keyTableGroup = new GridLayout(7, false);
		gd_keyTableGroup = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_keyTableGroup.minimumWidth = 1000;

		keyTableGroup.setLayout(gl_keyTableGroup);
		keyTableGroup.setText(Messages.SignalEncryption_TblTitel_Key);
		keyTableGroup.setLayoutData(gd_keyTableGroup);

		createTable();

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
		createDescriptionGroup();

	}

	public void generateBoth() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBoth();
		doubleRatchetTabComposite.resetAll();
		setParameter();
		textReset();
	}

	public void generateAlice() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateAlice();
		doubleRatchetTabComposite.resetAll();
		setParameter();
		textReset();
	}

	public void generateBob() {
		AlgorithmState.get().getSignalEncryptionAlgorithm().generateBob();
		doubleRatchetTabComposite.resetAll();
		setParameter();
		textReset();
	}
	
	public void reinitializeDoubleRatchetTab() {
		doubleRatchetTabComposite.resetAll();
	}

	public void textReset() {
		value_b_alice_private.setText(aliceRatchetPrivateKey);
		value_b_alice_public.setText(aliceRatchetPublicKey);

		value_b_bob_private.setText(bobRatchetPrivateKey);
		value_b_bob_public.setText(bobRatchetPublicKey);
	}

	public void setParameter() {
		var signalEncryptionState = AlgorithmState.get().getCommunication().begin();
		aliceRatchetPrivateKey = signalEncryptionState.diffieHellmanSenderPrivateKey();
		aliceRatchetPublicKey = signalEncryptionState.diffieHellmanReceiverPublicKey();

		bobRatchetPrivateKey = signalEncryptionState.diffieHellmanReceiverPrivateKey();
		bobRatchetPublicKey = signalEncryptionState.diffieHellmanSenderPublicKey();
	}
}
