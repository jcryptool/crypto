/**
 *
 */
package org.jcryptool.visual.elGamal.ui;

import java.math.BigInteger;
import java.util.HashMap;

import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.elGamal.Action;
import org.jcryptool.visual.elGamal.ElGamalData;
import org.jcryptool.visual.elGamal.Messages;
import org.jcryptool.visual.elGamal.ui.wizards.KeySelectionWizard;
import org.jcryptool.visual.elGamal.ui.wizards.TextEntryWizard;
import org.jcryptool.visual.elGamal.ui.wizards.UniqueKeyWizard;
import org.jcryptool.visual.library.Constants;
import org.jcryptool.visual.library.Lib;

/**
 * composite, display of everything this visual shows, that is not contained within wizards.
 *
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class ElGamalComposite extends Composite {

    /** buttons for running the wizards and finishing up. */
    private Button keysel, textEnter, runCalc;

    /** shared data object. */
    private ElGamalData data;

    /** field for the text entered in the wizard. */
    private Text textText;

    /** field for the signature or the text translated to numbers. */
    private Text numberText;

    /** field for displaying the result. */
    private Text resultText;

    /** button to copy the result to the clipboard. */
    private Button copyButton;

    /** array containing the split up numbertext. */
    private String[] numbers;

    /** current index for the stepping through the fast exponentiation. */
//    private int numberIndex;
    private int numberIndex = 0;

    /**
     * small field showing whether the signature is ok when we chose to verify a signature and entered plaintext.
     */
    private StyledText verifiedText;

    /** Textstyle constant for superscript. */
    private TextStyle superScript;

    /** Textstyle constant for double superscript. */
    private TextStyle superSuperScript;

    /** Textstyle constant for subscript. */
    private TextStyle subscript;

    /** field for displaying p */
    private Text pText;

    /** field for displaying g */
    private Text gText;

    /** field for displaying A */
    private Text bigAText;

    /** field for displaying a */
    private Text aText;

    /** field for displaying the result of the calculation step */
    private Text stepResult;

    /** button used for starting and stepping the stepwise calculation */
    private Button stepButton;

    /** place to display the steps of the stepwise calculation */
    private Composite fastExpTable;

    /** {@link TextLayout} to be able to display superscripts */
    private final TextLayout fastExpText = new TextLayout(this.getDisplay());

    /** Button for starting the unique key entering wizard */
    private Button uniqueKeyButton;

    /** map of all the data objects */
    private final HashMap<Action, ElGamalData> datas;

    /** whether dialogs are wanted TODO: default to true. */
    public boolean dialog = false;

    /** combo to list all pages to inherit data from another operation */
    private Combo inheritCombo;

    /** text that replaces stepLabel */
    private Text stepText;
    
    /** Group for the key arguments */
    private Group groupKey;
    
    /** Group for the Text */
    private Group groupText;
    
    /** Group for the calculations */
    private Group groupCalculations;
    
    /** Group for the results */
    private Group groupResult;
    
    /** to check if it is the first step of a decryption/encryption */
    private boolean firstRun = true;

    /**
     * updates the label that shows the current calculated step
     */
	private void updateLabel() {
		this.stepText.setText(
				NLS.bind(Messages.ElGamalComposite_step1, new Object[] { this.numberIndex + 1, this.numbers.length }));

	}

    /**
     * constructor calls super and saves a reference to the view.
     *
     * @param parent the parent composite
     * @param style style of the Widget to construct
     * @param datas the set of data objects for all the operations
     * @param action the action of this tab
     * @see Composite#Composite(Composite, int)
     */
    public ElGamalComposite(final Composite parent, final int style, final Action action,
            final HashMap<Action, ElGamalData> datas) {
        super(parent, style);
        this.data = new ElGamalData(action);
        datas.put(action, this.data);
        this.datas = datas;
        this.initialize();
    }

    /**
     * initializes the startup situation of this view.
     */
    private void initialize() {
        // basic layout is a
        this.setLayout(new GridLayout());
        this.createHead();
        this.createMainArea();
        this.createOptionsArea();
    }

	/**
	 * creates the description head of the window to display informations about the
	 * Algorithm itself.
	 */
	private void createHead() {
		final Composite head = new Composite(this, SWT.NONE);
		head.setBackground(ColorService.WHITE);
		head.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		head.setLayout(new GridLayout());

		final Label label = new Label(head, SWT.NONE);
		label.setFont(FontService.getHeaderFont());
		label.setBackground(ColorService.WHITE);
		label.setText(Messages.ElGamalComposite_title);

		final StyledText stDescription = new StyledText(head, SWT.READ_ONLY | SWT.MULTI);
		switch (this.data.getAction()) {
		case EncryptAction:
			stDescription.setText(
					Messages.ElGamalComposite_description_encrypt);
			break;

		case DecryptAction:
			stDescription.setText(
					Messages.ElGamalComposite_description_decrypt);
			break;

		case SignAction:
			stDescription.setText(
					Messages.ElGamalComposite_description_sign);
			break;
		case VerifyAction:
			stDescription.setText(
					Messages.ElGamalComposite_description_verify);
		default:
			break;
		}

		stDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

    /**
     * creates the main area where everything except head and options is contained.
     */
    private void createMainArea() {
        final Group groupAlgorithm = new Group(this, SWT.NONE);
        groupAlgorithm.setText(Messages.ElGamalComposite_algorithm);
        final GridLayout gl = new GridLayout(2, false);
        groupAlgorithm.setLayout(gl);
        groupAlgorithm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        this.createButtonArea(groupAlgorithm);
        this.createAlgoArea(groupAlgorithm);
    }

	/**
     * called to set the values of the key selection to their fields.
     */
    private void keySelected() {
        this.keysel.setBackground(ColorService.GREEN);
        this.textEnter.setEnabled(true);
        if (this.data.getModulus() != null) {
            this.pText.setText(this.data.getModulus().toString());
        }
        if (this.data.getGenerator() != null) {
            this.gText.setText(this.data.getGenerator().toString());
        }
        if (this.data.getPublicA() != null) {
            this.bigAText.setText(this.data.getPublicA().toString());
        }
        if (this.data.getA() != null) {
            this.aText.setText(this.data.getA().toString());
        }
    }

    /**
     * create the vertical area for the three main buttons.
     *
     * @param parent the parent composite
     */
    private void createButtonArea(final Composite parent) {
        // set up the canvas for the Buttons
    	final Composite compositeButtons = new Composite(parent, SWT.NONE);
        compositeButtons.setLayout(new GridLayout());
        GridData gd_btnComposite = new GridData(SWT.FILL, SWT.FILL, false, true);
        gd_btnComposite.verticalIndent = 10;
        gd_btnComposite.horizontalIndent = 80;
        compositeButtons.setLayoutData(gd_btnComposite);

        // Key selection Button
        this.keysel = new Button(compositeButtons, SWT.PUSH);
        this.keysel.setBackground(ColorService.RED);
        this.keysel.setEnabled(true);
        this.keysel.setText(Messages.ElGamalComposite_key_selection);
        GridData gd_keysel = new GridData(SWT.FILL, SWT.FILL, false, false);
        gd_keysel.heightHint = 60;
        this.keysel.setLayoutData(gd_keysel);
        this.keysel.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (ElGamalComposite.this.dialog) {
                    final MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()), SWT.ICON_INFORMATION
                            | SWT.OK);
                    messageBox.setText(Messages.ElGamalComposite_key_selection);
                    messageBox.setMessage(Messages.ElGamalComposite_key_selection_message_text);
                    messageBox.open();
                }
				WizardDialog keySelDialog = new WizardDialog(ElGamalComposite.this.getShell(), new KeySelectionWizard(
						ElGamalComposite.this.data.getAction(), ElGamalComposite.this.data, false));
				keySelDialog.setHelpAvailable(false);
				if (keySelDialog.open() == Window.OK) {
					ElGamalComposite.this.keySelected();
				}
            }
        });

        // Text enter Button
        this.textEnter = new Button(compositeButtons, SWT.PUSH);
        this.textEnter.setBackground(ColorService.RED);
        this.textEnter.setEnabled(false);
        this.textEnter.setText(Messages.ElGamalComposite_enter_text);
        GridData gd_textEnter = new GridData(SWT.FILL, SWT.FILL, false, false);
        gd_textEnter.verticalIndent = 10;
        gd_textEnter.heightHint = 60;
        this.textEnter.setLayoutData(gd_textEnter);
        this.textEnter.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(final SelectionEvent e) {
				if (ElGamalComposite.this.dialog) {
					final MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
							SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText(Messages.ElGamalComposite_textentry);
					messageBox.setMessage(Messages.ElGamalComposite_textentry_text);
					messageBox.open();
				}
				WizardDialog textEnterDialog = new WizardDialog(ElGamalComposite.this.getShell(),
						new TextEntryWizard(ElGamalComposite.this.data.getAction(), ElGamalComposite.this.data));
				textEnterDialog.setHelpAvailable(false);
				if (textEnterDialog.open() == Window.OK) {
					ElGamalComposite.this.textEntered();
				}
			}

        });

        // unique parameter button
        this.uniqueKeyButton = new Button(compositeButtons, SWT.PUSH);
        this.uniqueKeyButton.setBackground(ColorService.RED);
        this.uniqueKeyButton.setEnabled(false);
        this.uniqueKeyButton.setText(Messages.ElGamalComposite_enter_param);
        this.uniqueKeyButton.setToolTipText(Messages.ElGamalComposite_enter_param_text);
        GridData gd_uniqueKeyButton = new GridData(SWT.FILL, SWT.FILL, false, false);
        gd_uniqueKeyButton.verticalIndent = 50;
        gd_uniqueKeyButton.heightHint = 60;
        this.uniqueKeyButton.setLayoutData(gd_uniqueKeyButton);
        this.uniqueKeyButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
            	WizardDialog uniqueKeyButtonDialog = new WizardDialog(ElGamalComposite.this.getShell(), 
            			new UniqueKeyWizard(ElGamalComposite.this.data));
            	uniqueKeyButtonDialog.setHelpAvailable(false);
            	if (uniqueKeyButtonDialog.open() == Window.OK) {
                    ElGamalComposite.this.bEntered();
                }
            }
        });

        // Run Calculations Button
        this.runCalc = new Button(compositeButtons, SWT.PUSH);
        this.runCalc.setBackground(ColorService.RED);
        this.runCalc.setEnabled(false);
        this.runCalc.setText(Messages.ElGamalComposite_calculate);
        this.runCalc.setToolTipText(Messages.ElGamalComposite_calculate_popup);
        GridData gd_runCalc = new GridData(SWT.FILL, SWT.FILL, false, false);
        gd_runCalc.verticalIndent = 90;
        gd_runCalc.heightHint = 60;
        this.runCalc.setLayoutData(gd_runCalc);
        this.runCalc.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                ElGamalComposite.this.uniqueKeyButton.setEnabled(false);
                ElGamalComposite.this.textEnter.setEnabled(false);
                ElGamalComposite.this.runCalc.setEnabled(false);
                ElGamalComposite.this.runCalc.setBackground(ColorService.GREEN);
                // startButton.setEnabled(false);
                ElGamalComposite.this.stepButton.setEnabled(false);
                if (ElGamalComposite.this.dialog) {
                    final MessageBox message = new MessageBox(new Shell(Display.getCurrent()), SWT.ICON_INFORMATION
                            | SWT.OK);
                    message.setText(Messages.ElGamalComposite_finish_calculations);
                    message.setMessage(Messages.ElGamalComposite_finish_calculations_text);
                    message.open();
                }
                if (ElGamalComposite.this.data.getAction() == Action.SignAction) {
                    final String value = ElGamalComposite.this.data.getAction().run(ElGamalComposite.this.data,
                            ElGamalComposite.this.numberText.getText().split(" ")); //$NON-NLS-1$
                    ElGamalComposite.this.resultText
                            .setText("(" + ElGamalComposite.this.data.getR().toString(Constants.HEXBASE) + ", " + value + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                } else {
                    ElGamalComposite.this.resultText.setText(ElGamalComposite.this.data.getAction().run(
                            ElGamalComposite.this.data, ElGamalComposite.this.numberText.getText().split(" "))); //$NON-NLS-1$
                }
                ElGamalComposite.this.finish();
            }
        });
    }

    /**
     * create the main algorithm view.
     *
     * @param parent the parent
     */
    private void createAlgoArea(final Composite parent) {
        final Composite compositeMain = new Composite(parent, SWT.SHADOW_NONE);
        compositeMain.setLayout(new GridLayout());
        compositeMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        this.createKeyGroup(compositeMain);
        this.createTextGroup(compositeMain);
        this.createCalcGroup(compositeMain);
        this.createResultGroup(compositeMain);
    }

    /**
     * create the keygroup where the components of the key are displayed
     *
     * @param parent the parent
     */
    private void createKeyGroup(final Composite parent) {
        groupKey = new Group(parent, SWT.SHADOW_NONE);
        groupKey.setText(Messages.ElGamalComposite_key);
        groupKey.setLayout(new GridLayout(9, true));
        groupKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Label label = new Label(groupKey, SWT.NONE);
        label.setText("p = "); //$NON-NLS-1$
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        this.pText = new Text(groupKey, SWT.READ_ONLY | SWT.BORDER);
        this.pText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        label = new Label(groupKey, SWT.NONE);
        label.setText("g = "); //$NON-NLS-1$
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        this.gText = new Text(groupKey, SWT.READ_ONLY | SWT.BORDER);
        this.gText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        label = new Label(groupKey, SWT.NONE);
        label.setText("A = "); //$NON-NLS-1$
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        this.bigAText = new Text(groupKey, SWT.READ_ONLY | SWT.BORDER);
        this.bigAText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        label = new Label(groupKey, SWT.NONE);
        label.setText("a = "); //$NON-NLS-1$
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        this.aText = new Text(groupKey, SWT.READ_ONLY | SWT.BORDER);
        this.aText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        // Spacer
        label = new Label(groupKey, SWT.NONE);
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
    }

    /**
     * create the group where text and "translated" text are displayed.
     *
     * @param parent the parent
     */
    private void createTextGroup(final Composite parent) {
        groupText = new Group(parent, SWT.NONE);
        groupText.setLayout(new GridLayout());
        groupText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        new Label(groupText, SWT.NONE).setText(Messages.ElGamalComposite_text);
        
        this.textText = new Text(groupText, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        this.textText.setText("\n\n\n"); //$NON-NLS-1$
        this.textText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        this.textText.addModifyListener(new ModifyListener() {
        	
        	@Override
            public void modifyText(final ModifyEvent e) {
                if (ElGamalComposite.this.textText.getText().equals("")) { //$NON-NLS-1$
                    return;
                }
                if (ElGamalComposite.this.data.getAction() == Action.SignAction) {
                    ElGamalComposite.this.numberText.setText(Lib.hash(ElGamalComposite.this.textText.getText(),
                            ElGamalComposite.this.data.getSimpleHash(), ElGamalComposite.this.data.getModulus()));
                } else {
                    final StringBuffer sb = new StringBuffer();
                    final String s = ((Text) e.widget).getText();
                    for (int i = 0; i < s.length(); ++i) {
                        sb.append(Integer.toHexString(s.charAt(i)));
                        if (i != s.length() - 1) {
                            sb.append(' ');
                        }
                    }
                    ElGamalComposite.this.numberText.setText(sb.toString());
                }
            }
        });
        
        new Label(groupText, SWT.NONE).setText(Messages.ElGamalComposite_hextext);
        
        this.numberText = new Text(groupText, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        this.numberText.setText("\n\n\n"); //$NON-NLS-1$
        this.numberText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    }

    /**
     * create the calculations group where the fast exponentiation table and the step result are displayed.
     *
     * @param parent the parent
     */
    private void createCalcGroup(final Composite parent) {
        groupCalculations = new Group(parent, SWT.NONE);
        groupCalculations.setLayout(new GridLayout(3, false));
        groupCalculations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        groupCalculations.setText(Messages.ElGamalComposite_calculations);

        
        this.stepButton = new Button(groupCalculations, SWT.PUSH);
        this.stepButton.setText(Messages.ElGamalComposite_start);
        this.stepButton.setEnabled(false);
        this.stepButton.setToolTipText(Messages.ElGamalComposite_start_calc);
        this.stepButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        this.stepButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (ElGamalComposite.this.firstRun) {
					ElGamalComposite.this.uniqueKeyButton.setEnabled(false);
					ElGamalComposite.this.textEnter.setEnabled(false);
					ElGamalComposite.this.numbers = ElGamalComposite.this.numberText.getText().split(" "); //$NON-NLS-1$
					ElGamalComposite.this.numberIndex = 0;
					ElGamalComposite.this.stepButton
							.setEnabled(ElGamalComposite.this.numberIndex != ElGamalComposite.this.numbers.length - 1);
					ElGamalComposite.this.initTable();
					ElGamalComposite.this.updateTable();
					ElGamalComposite.this.updateLabel();
					if (ElGamalComposite.this.numberIndex == ElGamalComposite.this.numbers.length - 1) {
						ElGamalComposite.this.runCalc.setEnabled(false);
						ElGamalComposite.this.runCalc.setBackground(ColorService.GREEN);
						ElGamalComposite.this.finish();
					}
					ElGamalComposite.this.stepButton.setText(Messages.ElGamalComposite_step);
					ElGamalComposite.this.stepButton.pack();
					ElGamalComposite.this.firstRun = false;
				} else {
					++ElGamalComposite.this.numberIndex;
					ElGamalComposite.this.updateTable();
					ElGamalComposite.this.updateLabel();
					if (ElGamalComposite.this.numberIndex == ElGamalComposite.this.numbers.length - 1) {
						ElGamalComposite.this.stepButton.setEnabled(false);
						ElGamalComposite.this.runCalc.setEnabled(false);
						ElGamalComposite.this.runCalc.setBackground(ColorService.GREEN);
						ElGamalComposite.this.finish();
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

        this.stepText = new Text(groupCalculations, SWT.BORDER);
        this.stepText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        this.stepText.setEnabled(false);

        // set up a composite to draw final the fast exp shit on
        this.fastExpTable = new Composite(groupCalculations, SWT.NONE);
        GridData gd_fastExpTable = new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1);
        gd_fastExpTable.minimumHeight = 100;
        this.fastExpTable.setLayoutData(gd_fastExpTable);
        this.fastExpTable.setVisible(false);

        final Label l = new Label(groupCalculations, SWT.NONE);
        l.setText(Messages.ElGamalComposite_stepresult);
        
        this.stepResult = new Text(groupCalculations, SWT.BORDER | SWT.READ_ONLY);
        this.stepResult.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    }

    /**
     * initializes the fast exponentiation table.
     */
    private void initTable() {
        // get the graphics context
        final GC gc = new GC(this.fastExpTable);
        // get the standard font we're using everywhere
        final Font normalFont = this.getDisplay().getSystemFont();
        // get the associated fontData
        final FontData normalData = normalFont.getFontData()[0];
        // set the new font height to 12
        normalData.setHeight(12);
        // create small and very small data from the normal data and create
        // matching fonts with each 2pt final less height
        final FontData smallData = new FontData(normalData.getName(), normalData.getHeight() - 2, normalData.getStyle());
        final Font smallFont = new Font(this.getDisplay(), smallData);
        final FontData verySmallData = new FontData(smallData.getName(), smallData.getHeight() - 2,
                smallData.getStyle());
        final Font verySmallFont = new Font(this.getDisplay(), verySmallData);
        // some metrics, whatever they are
        final FontMetrics metrics = gc.getFontMetrics();
        // something to calculate the actual place of the superscripts
        final int baseline = metrics.getAscent() + metrics.getLeading();
        // set the font to standard
        this.fastExpText.setFont(normalFont);
        // small and very small textstyles for the super- and subscripts
        this.superScript = new TextStyle(smallFont, null, null);
        this.superSuperScript = new TextStyle(verySmallFont, null, null);
        this.subscript = new TextStyle(verySmallFont, null, null);
        // rises, values found by experiment
        this.superScript.rise = baseline / 2 - 1;
        this.superSuperScript.rise = baseline - 2;
        this.subscript.rise = -baseline / 2 + 2;
        this.fastExpTable.setVisible(true);
        // add a paint listener which paints the text everytime it's needed
        this.fastExpTable.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				ElGamalComposite.this.fastExpText.draw(e.gc, 0, 0);
			}
		});
    }

    /**
     * updates the fast exponentiation table i.e. displays the next step
     */
    private void updateTable() {
        // reset resultText if it contains \n (only first run)
        if (this.resultText.getText().contains("\n")) { //$NON-NLS-1$
            this.resultText.setText(""); //$NON-NLS-1$
        }
        switch (this.data.getAction()) {
            case EncryptAction:
                // TODO das hier in ne konstante bei initialisierung der tabelle
                this.updateEncrypt();
                break;
            case DecryptAction:
                this.updateDecrypt();
                break;
            case SignAction:
                this.updateSign();
                break;
            case VerifyAction:
                this.updateVerify();
                break;
        }
        this.fastExpTable.redraw();
    }

    /**
     * Sets up the table for verification
     */
    private void updateVerify() {
        final BigInteger modulus = this.data.getModulus();
        final StringBuilder sb = new StringBuilder();
        BigInteger value;
        int offset0;
        int offset1 = 0;
        int offset2;
        value = new BigInteger(this.data.getAction().run(this.data, (String) null), Constants.HEXBASE);
        sb.append("r = "); //$NON-NLS-1$
        sb.append(this.data.getR().toString(Constants.HEXBASE));
        sb.append("\ns = "); //$NON-NLS-1$
        final BigInteger s = new BigInteger(
                this.data.getSignature().split(",")[1].replace(')', ' ').trim(), Constants.HEXBASE); //$NON-NLS-1$
        sb.append(s.toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        offset0 = sb.length();
        sb.append("gH(m) = "); //$NON-NLS-1$
        BigInteger ghm = null, hash = null;
        if (this.data.getPlainText().length() > 0) {
            sb.append(this.data.getGenerator().toString(Constants.HEXBASE));
            hash = new BigInteger(
                    Lib.hash(this.data.getPlainText(), this.data.getSimpleHash(), this.data.getModulus()),
                    Constants.HEXBASE);
            offset1 = sb.length();
            sb.append(hash.toString(Constants.HEXBASE));
            sb.append(" mod "); //$NON-NLS-1$
            sb.append(modulus.toString(Constants.HEXBASE));
            sb.append(" = "); //$NON-NLS-1$
            sb.append((ghm = this.data.getGenerator().modPow(hash, modulus)).toString(Constants.HEXBASE));
        }
        sb.append("\n"); //$NON-NLS-1$
        offset2 = sb.length();
        sb.append("Arrs = "); //$NON-NLS-1$
        sb.append(this.data.getPublicA().toString(Constants.HEXBASE));
        sb.append(this.data.getR().toString(Constants.HEXBASE));
        sb.append(" ∙ "); //$NON-NLS-1$
        sb.append(this.data.getR().toString(Constants.HEXBASE));
        sb.append(s.toString(Constants.HEXBASE));
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(value.toString(Constants.HEXBASE));
        // set style
        this.fastExpText.setText(sb.toString());
        this.fastExpText.setStyle(this.superScript, offset0 + 1, offset0 + 3);
        if (this.data.getPlainText().length() > 0) {
            this.fastExpText.setStyle(this.superScript, offset1, offset1 + hash.toString(Constants.HEXBASE).length()
                    - 1);
        }
        this.fastExpText.setStyle(this.superScript, offset2 + 1, offset2 + 1);
        this.fastExpText.setStyle(this.superScript, offset2 + 3, offset2 + 3);
        offset2 = offset2 + 7 + this.data.getPublicA().toString(Constants.HEXBASE).length();
        this.fastExpText.setStyle(this.superScript, offset2, offset2
                + this.data.getR().toString(Constants.HEXBASE).length() - 1);
        offset2 = offset2 + this.data.getR().toString(Constants.HEXBASE).length() + 5;
        this.fastExpText.setStyle(this.superScript, offset2, offset2 + s.toString(Constants.HEXBASE).length() - 1);

        // set results
        this.stepResult.setText("A^r*r^s = " + value.toString(Constants.HEXBASE)); //$NON-NLS-1$
        this.verifiedText.setData(ghm.toString(Constants.HEXBASE));
        this.resultText.setText(value.toString(Constants.HEXBASE));
    }

    /**
     * Sets up the table for signing
     */
    private void updateSign() {
        final StringBuilder sb = new StringBuilder();
        final BigInteger modulus = this.data.getModulus();
        BigInteger value;
        int offset0;
        int offset1;
        value = new BigInteger(this.data.getAction().run(this.data, this.numbers[this.numberIndex]), Constants.HEXBASE);
        sb.append("k = "); //$NON-NLS-1$
        sb.append(this.data.getK().toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        offset0 = sb.length();
        sb.append("r = gk = "); //$NON-NLS-1$
        sb.append(this.data.getGenerator().toString(Constants.HEXBASE));
        sb.append(this.data.getK().toString(Constants.HEXBASE));
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(this.data.getR().toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        offset1 = sb.length();
        sb.append("s = (H(m) - ar)k-1 = ("); //$NON-NLS-1$
        sb.append(this.numbers[this.numberIndex]);
        sb.append(" - "); //$NON-NLS-1$
        sb.append(this.data.getA().toString(Constants.HEXBASE));
        sb.append(" ∙ "); //$NON-NLS-1$
        sb.append(this.data.getR().toString(Constants.HEXBASE));
        sb.append(") ∙ "); //$NON-NLS-1$
        sb.append(this.data.getK().modInverse(modulus.subtract(BigInteger.ONE)).toString(Constants.HEXBASE));
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.subtract(BigInteger.ONE).toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(value.toString(Constants.HEXBASE));
        // style it
        this.fastExpText.setText(sb.toString());
        this.fastExpText.setStyle(this.superScript, offset0 + 5, offset0 + 5);
        offset0 = offset0 + 9 + this.data.getGenerator().toString(Constants.HEXBASE).length();
        this.fastExpText.setStyle(this.superScript, offset0, offset0
                + this.data.getK().toString(Constants.HEXBASE).length() - 1);
        this.fastExpText.setStyle(this.superScript, offset1 + 16, offset1 + 17);
        // set result
        this.stepResult
                .setText("r = " + this.data.getR().toString(Constants.HEXBASE) + ", s = " + value.toString(Constants.HEXBASE)); //$NON-NLS-1$ //$NON-NLS-2$
        this.resultText
                .setText("(" + this.data.getR().toString(Constants.HEXBASE) + ", " + value.toString(Constants.HEXBASE) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Sets up the table for signing
     */
    private void updateDecrypt() {
        final StringBuilder sb = new StringBuilder();
        final BigInteger modulus = this.data.getModulus();
        BigInteger value;
        int offset0;
        int offset1;
        int offset2;
        BigInteger x;
        sb.append("x = p - a - 1 = "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" - "); //$NON-NLS-1$
        sb.append(this.data.getA().toString(Constants.HEXBASE));
        sb.append(" - 1 = "); //$NON-NLS-1$
        sb.append((x = modulus.subtract(this.data.getA()).subtract(BigInteger.ONE)).toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        offset0 = sb.length();
        sb.append("B = gb mod p = "); //$NON-NLS-1$
        sb.append(this.data.getGenerator().toString(Constants.HEXBASE));
        sb.append(this.data.getB().toString(Constants.HEXBASE));
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(this.data.getGPowB().toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        offset1 = sb.length();
        sb.append("m = Bxc = "); //$NON-NLS-1$
        sb.append(this.data.getGPowB().toString(Constants.HEXBASE));
        offset2 = sb.length();
        sb.append(x.toString(Constants.HEXBASE));
        sb.append(" ∙ "); //$NON-NLS-1$
        sb.append(this.numbers[this.numberIndex]);
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        value = new BigInteger(
                "" + (int) this.data.getAction().run(this.data, this.numbers[this.numberIndex]).charAt(0)); //$NON-NLS-1$
        sb.append(value.toString(Constants.HEXBASE));
        // set style
        this.fastExpText.setText(sb.toString());
        this.fastExpText.setStyle(this.superScript, offset0 + 5, offset0 + 5);
        offset0 = offset0 + 15 + this.data.getGenerator().toString(Constants.HEXBASE).length();
        this.fastExpText.setStyle(this.superScript, offset0, offset0
                + this.data.getB().toString(Constants.HEXBASE).length() - 1);
        this.fastExpText.setStyle(this.superScript, offset1 + 5, offset1 + 5);
        this.fastExpText.setStyle(this.superScript, offset2, offset2 + x.toString(Constants.HEXBASE).length() - 1);
        // set result
        this.stepResult.setText("m = " + (char) value.intValue()); //$NON-NLS-1$
        this.resultText.setText(this.resultText.getText() + (char) value.intValue());
    }

    /**
     * Sets up the table for signing
     */
    private void updateEncrypt() {
        final StringBuilder sb = new StringBuilder();
        final BigInteger modulus = this.data.getModulus();
        BigInteger value;
        int offset0;
        int offset1;
        int offset2;
        sb.append("b = "); //$NON-NLS-1$
        sb.append(this.data.getB().toString());
        sb.append("\n"); //$NON-NLS-1$
        offset0 = sb.length();
        sb.append("B = gb mod p = "); //$NON-NLS-1$
        sb.append(this.data.getGenerator().toString(Constants.HEXBASE));
        sb.append(this.data.getB().toString(Constants.HEXBASE));
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus.toString(Constants.HEXBASE));
        sb.append(" = "); //$NON-NLS-1$
        sb.append(this.data.getGPowB().toString(Constants.HEXBASE));
        sb.append("\n"); //$NON-NLS-1$
        // TODO bis hier
        offset1 = sb.length();
        sb.append("c = Abm mod p = "); //$NON-NLS-1$
        sb.append(this.data.getPublicA().toString(Constants.HEXBASE));
        offset2 = sb.length();
        sb.append(this.data.getB().toString(Constants.HEXBASE));
        sb.append(" ∙ "); //$NON-NLS-1$
        sb.append(this.numbers[this.numberIndex]);
        sb.append(" mod "); //$NON-NLS-1$
        sb.append(modulus);
        sb.append(" = "); //$NON-NLS-1$
        value = new BigInteger(this.data.getAction().run(this.data, this.numbers[this.numberIndex]).trim(),
                Constants.HEXBASE);
        sb.append(value.toString(Constants.HEXBASE));
        int tmp;
        // set style
        this.fastExpText.setText(sb.toString());
        this.fastExpText.setStyle(this.superScript, offset0 + 5, offset0 + 5);
        this.fastExpText.setStyle(this.superScript,
                tmp = offset0 + 15 + this.data.getGenerator().toString(Constants.HEXBASE).length(), tmp
                        + this.data.getB().toString(Constants.HEXBASE).length());
        this.fastExpText.setStyle(this.superScript, offset1 + 5, offset1 + 5);
        this.fastExpText.setStyle(this.superScript, offset2, offset2
                + this.data.getB().toString(Constants.HEXBASE).length() - 1);
        // set to stepresult
        this.stepResult.setText("c = " + value.toString(Constants.HEXBASE)); //$NON-NLS-1$
        if (this.resultText.getText().equals("")) { //$NON-NLS-1$
        	this.resultText.setText(value.toString(Constants.HEXBASE));
        } else {
        	this.resultText.setText(this.resultText.getText() + " " + value.toString(Constants.HEXBASE)); //$NON-NLS-1$
        }
    }

    /**
     * create the resultgroup where the result and the copy button are displayed.
     *
     * @param parent the parent
     */
    private void createResultGroup(final Composite parent) {
        groupResult = new Group(parent, SWT.NONE);
        groupResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        groupResult.setLayout(new GridLayout(3, false));
        groupResult.setText(Messages.ElGamalComposite_result);
        this.resultText = new Text(groupResult, SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER | SWT.MULTI | SWT.WRAP);
        this.resultText.setText("\n\n"); //$NON-NLS-1$
        this.resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        this.resultText.addModifyListener(new ModifyListener() {

        	@Override
        	public void modifyText(final ModifyEvent e) {
                ElGamalComposite.this.copyButton.setEnabled(true);
                if (ElGamalComposite.this.data.getAction() == Action.VerifyAction
                        && !ElGamalComposite.this.textText.getText().equals("")) { //$NON-NLS-1$
                    String text;
                    if (ElGamalComposite.this.verifiedText.getData() != null
                            && ElGamalComposite.this.resultText.getText().trim()
                                    .equals(ElGamalComposite.this.verifiedText.getData())) {
                        text = Messages.ElGamalComposite_valid;
                        ElGamalComposite.this.verifiedText.setForeground(ColorService.GREEN);
                    } else {
                        text = Messages.ElGamalComposite_invalid;
                        ElGamalComposite.this.verifiedText.setForeground(ColorService.RED);
                    }
                    ElGamalComposite.this.verifiedText.setText(text);
                }
            }
        });

        this.verifiedText = new StyledText(groupResult, SWT.READ_ONLY);
        this.verifiedText.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        this.verifiedText.setText("                "); //$NON-NLS-1$

        this.copyButton = new Button(groupResult, SWT.PUSH);
        this.copyButton.setEnabled(false);
        this.copyButton.setText(Messages.ElGamalComposite_copy);
        this.copyButton.setToolTipText(Messages.ElGamalComposite_copy_to_clipboard);
        this.copyButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        this.copyButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Clipboard cb = new Clipboard(Display.getCurrent());
                cb.setContents(new Object[] {ElGamalComposite.this.resultText.getText()},
                        new Transfer[] {TextTransfer.getInstance()});
            }
        });
    }

    /**
     * creates the bottom options area.
     */
    private void createOptionsArea() {
        // setup the main layout for this group
        final Group optionsGroup = new Group(this, SWT.NONE);
        optionsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        optionsGroup.setLayout(new GridLayout(5, false));
        optionsGroup.setText(Messages.ElGamalComposite_options);

        // temporary spacer
        final Button keyButton = new Button(optionsGroup, SWT.PUSH);
        keyButton.setText(Messages.ElGamalComposite_key_generation);
        keyButton.setToolTipText(Messages.ElGamalComposite_key_generation_popup);
        keyButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
        keyButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				WizardDialog keyButtonDialog = new WizardDialog(new Shell(Display.getDefault()),
						new KeySelectionWizard(null, null, true));
				keyButtonDialog.setHelpAvailable(false);
				keyButtonDialog.open();
			}
        });

        // initialize copy data selector
        final Label l = new Label(optionsGroup, SWT.NONE);
        l.setText(Messages.ElGamalComposite_inherit_from);
        this.inheritCombo = new Combo(optionsGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
        this.inheritCombo.add(""); //$NON-NLS-1$
        this.inheritCombo.add(Messages.ElGamalComposite_encrypt);
        this.inheritCombo.setData("1", Action.EncryptAction); //$NON-NLS-1$
        this.inheritCombo.add(Messages.ElGamalComposite_decrypt);
        this.inheritCombo.setData("2", Action.DecryptAction); //$NON-NLS-1$
        this.inheritCombo.add(Messages.ElGamalComposite_sign);
        this.inheritCombo.setData("3", Action.SignAction); //$NON-NLS-1$
        this.inheritCombo.add(Messages.ElGamalComposite_verify);
        this.inheritCombo.setData("4", Action.VerifyAction); //$NON-NLS-1$
        this.inheritCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                final Action newAction = (Action) ElGamalComposite.this.inheritCombo.getData("" //$NON-NLS-1$
                        + ((Combo) e.widget).getSelectionIndex());
                if (((Combo) e.widget).getSelectionIndex() == 0 || newAction == ElGamalComposite.this.data.getAction()) {
                    return;
                } else {
                    final MessageBox mb = new MessageBox(ElGamalComposite.this.getShell(), SWT.ICON_QUESTION | SWT.OK
                            | SWT.CANCEL);
                    mb.setText(Messages.ElGamalComposite_sure);
                    mb.setMessage(Messages.ElGamalComposite_data_loss);
                    if (mb.open() == SWT.OK) {
                        final ElGamalData oldData = ElGamalComposite.this.datas.get(newAction);
                        ElGamalComposite.this.reset();
                        ElGamalComposite.this.data.inherit(oldData);
                        // if we got any data at all insert the key parameters to
                        // their fields
                        if (ElGamalComposite.this.data.getModulus() != null) {
                            ElGamalComposite.this.keySelected();
                            // if we got plaintext/ciphertext/signature, set
                            // them up as well
                            if (ElGamalComposite.this.data.getPlainText().length() != 0
                                    || ElGamalComposite.this.data.getCipherText().length() != 0) {
                                ElGamalComposite.this.textEntered();
                                if (ElGamalComposite.this.data.getB() != null) {
                                    ElGamalComposite.this.bEntered();
                                }
                            }
                        }
                    }
                }
            }

        });

        // initialize dialog checkbox
        final Button dialogButton = new Button(optionsGroup, SWT.CHECK);
        dialogButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        dialogButton.setText(Messages.ElGamalComposite_show_dialogs);
        dialogButton.setSelection(this.dialog);
        dialogButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                ElGamalComposite.this.dialog = ((Button) e.widget).getSelection();
            }
        });

        // initialize reset button
        final Button reset = new Button(optionsGroup, SWT.PUSH);
        reset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
        reset.setText(Messages.ElGamalComposite_reset);
        reset.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(final SelectionEvent e) {
                ElGamalComposite.this.reset();
            }
        });
    }

    /**
     * reset the tab to its initial state
     */
    private void reset() {
        this.keysel.setEnabled(true);
        this.keysel.setBackground(ColorService.RED);
        this.textEnter.setEnabled(false);
        this.textEnter.setBackground(ColorService.RED);
        this.uniqueKeyButton.setEnabled(false);
        this.uniqueKeyButton.setBackground(ColorService.RED);
        this.runCalc.setEnabled(false);
        this.runCalc.setBackground(ColorService.RED);
        this.data = new ElGamalData(this.data.getAction());
        this.datas.put(this.data.getAction(), this.data);
        this.pText.setText(""); //$NON-NLS-1$
        this.gText.setText(""); //$NON-NLS-1$
        this.bigAText.setText(""); //$NON-NLS-1$
        this.aText.setText(""); //$NON-NLS-1$
        this.textText.setText(""); //$NON-NLS-1$
        this.numberText.setText(""); //$NON-NLS-1$
        this.fastExpTable.setVisible(false);
        this.stepResult.setText(""); //$NON-NLS-1$
        this.stepButton.setEnabled(false);
        
        this.firstRun = true;
        this.numberIndex = 0;

        this.stepButton.setText(Messages.ElGamalComposite_start);
        this.stepButton.setToolTipText(Messages.ElGamalComposite_start_calc);
        this.stepButton.pack();
        this.stepText.setText(""); //$NON-NLS-1$
        this.resultText.setText(""); //$NON-NLS-1$
        this.copyButton.setEnabled(false);
        this.verifiedText.setText(""); //$NON-NLS-1$
    }

    /**
     * called when the textentry has finished
     */
    private void textEntered() {
        this.keysel.setEnabled(false);
        this.textEnter.setBackground(ColorService.GREEN);
        this.uniqueKeyButton.setEnabled(true);
        switch (this.data.getAction()) {
            case EncryptAction:
            case SignAction:
                this.textText.setText(this.data.getPlainText());
                break;
            case DecryptAction:
                this.numberText.setText(this.data.getCipherText());
                break;
            case VerifyAction:
                this.textText.setText(this.data.getPlainText());
                this.numberText.setText(this.data.getSignature().split(",")[1].replace(')', ' ').trim()); //$NON-NLS-1$
                this.uniqueKeyButton.setEnabled(false);
                this.uniqueKeyButton.setBackground(ColorService.GREEN);
                this.runCalc.setEnabled(true);
                this.stepButton.setEnabled(true);
                break;
            default:
                break;
        }
    }

    /**
     * finished up after the operation by setting the plaintext ciphertext or signature to the data object
     */
    private void finish() {
        switch (this.data.getAction()) {
            case EncryptAction:
                this.data.setCipherText(this.resultText.getText());
                break;
            case DecryptAction:
                this.data.setPlainText(this.resultText.getText());
                break;
            case SignAction:
                this.data.setSignature(this.resultText.getText());
                break;
		default:
			break;
        }
    }

    /**
     * called when b has been entered via the wizard or inheritance
     */
    private void bEntered() {
        this.textEnter.setEnabled(false);
        this.uniqueKeyButton.setBackground(ColorService.GREEN);
        this.runCalc.setEnabled(true);
        this.stepButton.setEnabled(true);
    }

}
