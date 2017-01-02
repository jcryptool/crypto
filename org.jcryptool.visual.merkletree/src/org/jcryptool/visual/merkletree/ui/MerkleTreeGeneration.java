package org.jcryptool.visual.merkletree.ui;

import java.security.SecureRandom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.visual.merkletree.Descriptions;
import org.jcryptool.visual.merkletree.MerkleTreeView;
import org.jcryptool.visual.merkletree.algorithm.ISimpleMerkle;
import org.jcryptool.visual.merkletree.algorithm.SimpleMerkleTree;
import org.jcryptool.visual.merkletree.algorithm.XMSSTree;
import org.jcryptool.visual.merkletree.files.Converter;
import org.jcryptool.visual.merkletree.ui.MerkleConst.SUIT;

/**
 * Class for the Composite with the Seed in Tabpage 1
 * 
 * @author Fabian Mayer
 * @author <i>revised by</i>
 * @author Maximilian Lindpointner
 * 
 *
 */
public class MerkleTreeGeneration extends Composite {
	public byte[] seedarray;
	public byte[] bitmaskSeedarray;
	private Button buttonCreateSeed;
	private Label randomgenerator;
	private Text textSeed;
	private int wParameter = 16;
	private SUIT mode;
	private ViewPart masterView;

	int spinnerValue;
	int treeValue;

	// w Parameter Box
	Button buttonSet4;
	Button buttonSet16;
	Label titleLabel;
	StyledText wParamDescription;

	// Generate Keys Box
	Button buttonCreateKeys;
	Label createLabel;
	StyledText createdKey;
	StyledText descText;

	MessageBox successBox;

	/**
	 * Create the composite. Including Seed content and KeyPairComposite
	 * 
	 * @param parent
	 * @param style
	 */
	public MerkleTreeGeneration(Composite parent, int style, SUIT mode, ViewPart masterView) {
		super(parent, style);
		this.setLayout(new GridLayout(MerkleConst.H_SPAN_MAIN, true));
		this.mode = mode;
		this.masterView = masterView;

		/**
		 * (non-javadoc)
		 * 
		 * Seed Box
		 * 
		 */

		Group group = new Group(this, SWT.NONE);
		group.setText(Descriptions.Tab0_Head1);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		group.setLayout(new GridLayout(MerkleConst.H_SPAN_MAIN, true));

		/*
		 * Seed Label
		 */
		randomgenerator = new Label(group, SWT.NONE);
		randomgenerator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		// randomgenerator.setText(Descriptions.Tab0_Head1);

		/*
		 * Textbox for seed initiates textbox with a seed
		 */
		textSeed = new Text(group, SWT.BORDER | SWT.CENTER);
		textSeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		seedarray = generateNewSeed();
		textSeed.setText(Converter._byteToHex(seedarray));

		/*
		 * Button generate new Seed
		 */
		buttonCreateSeed = new Button(group, SWT.NONE);
		buttonCreateSeed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
		buttonCreateSeed.setText(Descriptions.Tab0_Button1);

		buttonCreateSeed.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				seedarray = generateNewSeed();
				textSeed.setText(Converter._byteToHex(seedarray));
				((MerkleTreeView) masterView).updateElement();
			}
		});

		/*
		 * if XMSS or XMSS_MT is selected, also the Bitmask is requiered
		 * therefore the Bitmask Box is injected
		 */

		/**
		 * (non-javadoc)
		 * 
		 * Bitmask Seed
		 * 
		 */

		Button bitmaskButton;
		Label bitmaskLabel;
		StyledText bitmaskDescText;
		Text bitmaskText;
		Label randomgenerator;

		if (mode == SUIT.XMSS || mode == SUIT.XMSS_MT) {
			Group bitmaskGroup = new Group(this, SWT.NONE);
			bitmaskGroup.setText(Descriptions.Tab0_Head5);
			bitmaskGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
			bitmaskGroup.setLayout(new GridLayout(MerkleConst.H_SPAN_MAIN, true));
			bitmaskGroup.setText(Descriptions.Tab0_Head3);

			bitmaskLabel = new Label(bitmaskGroup, SWT.NONE);
			bitmaskLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, MerkleConst.H_SPAN_MAIN, 1));

			bitmaskDescText = new StyledText(bitmaskGroup, SWT.WRAP | SWT.BORDER);
			bitmaskDescText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, MerkleConst.H_SPAN_MAIN, 2));
			bitmaskDescText.setText(Descriptions.Tab0_Txt3);
			bitmaskDescText.setEditable(false);
			bitmaskDescText.setCaret(null);

			/*
			 * Bitmask Seed Label
			 */
			randomgenerator = new Label(bitmaskGroup, SWT.NONE);
			randomgenerator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
			randomgenerator.setText(Descriptions.Tab0_Head4);

			/*
			 * Textbox for seed
			 */
			bitmaskText = new Text(bitmaskGroup, SWT.BORDER | SWT.CENTER);
			bitmaskText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));

			/*
			 * Button generate new Seed
			 */
			bitmaskButton = new Button(bitmaskGroup, SWT.NONE);
			bitmaskButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 2, 1));
			bitmaskButton.setText(Descriptions.Tab0_Button3);

			bitmaskSeedarray = generateNewSeed();
			bitmaskText.setText(Converter._byteToHex(bitmaskSeedarray));

			bitmaskButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					bitmaskSeedarray = generateNewSeed();
					bitmaskText.setText(Converter._byteToHex(bitmaskSeedarray));
					((MerkleTreeView) masterView).updateElement();
				}
			});
		}

		/**
		 * (non-javadoc)
		 * 
		 * Winternitz Parameter Box
		 * 
		 */

		Group wParameterGroup = new Group(this, SWT.NONE);
		wParameterGroup.setText(Descriptions.Tab0_Head5);
		wParameterGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, MerkleConst.H_SPAN_MAIN, 1));
		wParameterGroup.setLayout(new GridLayout(MerkleConst.H_SPAN_MAIN, true));

		// headline
		titleLabel = new Label(wParameterGroup, SWT.NONE);
		titleLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));

		// text box with Description
		wParamDescription = new StyledText(wParameterGroup, SWT.WRAP | SWT.BORDER | SWT.READ_ONLY);
		wParamDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, MerkleConst.H_SPAN_MAIN, 2));
		wParamDescription.setText(Descriptions.Tab0_Txt2);

		// Radio Buttons 4/16
		buttonSet4 = new Button(wParameterGroup, SWT.RADIO);
		buttonSet4.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		buttonSet4.setText("4");

		buttonSet16 = new Button(wParameterGroup, SWT.RADIO);
		buttonSet16.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		buttonSet16.setText("16");
		buttonSet16.setSelection(true);

		buttonSet4.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wParameter = 4;
				((MerkleTreeView) masterView).updateElement();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

		buttonSet16.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				wParameter = 16;
				((MerkleTreeView) masterView).updateElement();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

		/**
		 * (non-javadoc)
		 * 
		 * Key Generation Box
		 * 
		 */

		treeValue = 0;
		this.setLayout(new GridLayout(MerkleConst.H_SPAN_MAIN, true));

		Group generateKeyGroup = new Group(this, SWT.NONE);
		generateKeyGroup.setText(Descriptions.Tab0_Head2);
		generateKeyGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		generateKeyGroup.setLayout(new GridLayout(1, true));

		// headline
		createLabel = new Label(generateKeyGroup, SWT.NONE);
		createLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, MerkleConst.H_SPAN_MAIN, 1));

		// text
		descText = new StyledText(generateKeyGroup, SWT.WRAP);
		descText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, MerkleConst.H_SPAN_MAIN, 2));
		descText.setCaret(null);

		// text - for the spinner
		Label keysum = new Label(generateKeyGroup, SWT.NONE);
		keysum.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));

		// spinner for the key-ammount
		Spinner spinnerkeysum = new Spinner(generateKeyGroup, SWT.BORDER);
		spinnerkeysum.setMaximum(1024);
		spinnerkeysum.setMinimum(2);
		spinnerkeysum.setSelection(0);
		spinnerkeysum.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		spinnerValue = 2;

		// set the spinner-value only to values of the power of 2
		spinnerkeysum.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.getSource() instanceof Spinner) {
					Spinner spinner = (Spinner) e.getSource();
					int selection = spinner.getSelection();
					//
					if (selection < spinnerValue) {

						spinner.setSelection(spinnerValue / 2);
					} else {
						spinner.setSelection(spinnerValue * 2);
					}
					spinnerValue = spinner.getSelection();
				}
				((MerkleTreeView) masterView).updateElement();
			}
		});

		// 'create button'
		buttonCreateKeys = new Button(generateKeyGroup, SWT.NONE);
		buttonCreateKeys.setEnabled(true);
		buttonCreateKeys.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 3, 1));

		// Text box with generated key info
		createdKey = new StyledText(generateKeyGroup, SWT.WRAP | SWT.BORDER);
		createdKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, MerkleConst.H_SPAN_MAIN, 2));
		createdKey.setText(Descriptions.MerkleTreeKey_1);

		// if the Mode is MultiTree there is an extra spinner for the amount of
		// Trees (Tree-Layers)
		if (mode == SUIT.XMSS_MT) {

			Label trees = new Label(this, SWT.NONE);
			trees.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
			trees.setText(Descriptions.XMSS_MT.Tab0_Lable2);

			Spinner treespinner = new Spinner(this, SWT.BORDER);
			treespinner.setMaximum(64);
			treespinner.setMinimum(2);
			treespinner.setSelection(0);
			treespinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
			treespinner.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					((MerkleTreeView) masterView).updateElement();
					treeValue = treespinner.getSelection();
				}
			});
		}

		// setting the text's depending on the actual suite
		keysum.setText(Descriptions.Tab0_Lable1);
		// createLabel.setText(Descriptions.Tab0_Head2);
		buttonCreateKeys.setText(Descriptions.Tab0_Button2);
		switch (mode) {
		case XMSS:
			descText.setText(Descriptions.XMSS.Tab0_Txt2);
			break;
		case XMSS_MT:
			descText.setText(Descriptions.XMSS_MT.Tab0_Txt2);
			break;
		case MSS:
		default:
			descText.setText(Descriptions.MSS.Tab0_Txt2);
			break;
		}
		descText.setEditable(false);

		// MessageBox when successfully creating a Key
		successBox = new MessageBox(new Shell(), SWT.ICON_WORKING | SWT.OK);
		successBox.setText(Descriptions.MerkleTreeKey_4);
		successBox.setMessage(Descriptions.MerkleTreeKey_5);

		/**
		 * Event Listener for the generate keys button if this button is pressed
		 * a new merkle tree is generated
		 */
		buttonCreateKeys.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.
			 * eclipse.swt.events. SelectionEvent) generates the MerkleTree and
			 * the KeyPairs
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				generateMerkleTree();
				successBox.open();
			}

		});
	}

	ISimpleMerkle merkle;

	public ISimpleMerkle generateMerkleTree() {
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {

			@Override
			public void run() {

				/*
				 * select the type of suite
				 */
				switch (mode) {
				case XMSS:
					merkle = new XMSSTree();
					break;
				case XMSS_MT:
					// new XMSS_MT_TREE
					// break;
				case MSS:
				default:
					merkle = new SimpleMerkleTree();
					break;
				}

				/*
				 * create the merkle tree with the chosen values
				 */
				// if the generated Tree is a XMSSTree -> the
				// bitmaskseed is
				// also needed
				if (merkle instanceof XMSSTree) {
					((XMSSTree) merkle).setBitmaskSeed(bitmaskSeedarray);
				}
				merkle.setSeed(seedarray);
				merkle.setLeafCount(spinnerValue);
				merkle.setWinternitzParameter(wParameter);
				merkle.selectOneTimeSignatureAlgorithm("SHA-256", "WOTSPlus");
				merkle.generateKeyPairsAndLeaves();
				merkle.generateMerkleTree();
				((MerkleTreeView) masterView).setAlgorithm(merkle, mode);

				// set or update the key information
				createdKey.setText(Descriptions.MerkleTreeKey_2 + " " + merkle.getKeyLength() + " " + Descriptions.MerkleTreeKey_3);

			}
		});
		return merkle;
	}

	/**
	 * generates a new random seed
	 * 
	 * @return random seed
	 */
	private byte[] generateNewSeed() {
		SecureRandom secureRandomGenerator = new SecureRandom();
		byte[] randomBytes = new byte[128];
		secureRandomGenerator.nextBytes(randomBytes);
		// set the seed length
		int seedByteCount = 16;
		byte[] seed = secureRandomGenerator.generateSeed(seedByteCount);
		return seed;
	}

}