// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2014 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.ECDH.ui.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.sound.midi.Patch;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.operations.util.PathEditorInput;
import org.jcryptool.core.util.constants.IConstants;
import org.jcryptool.core.util.directories.DirectoryService;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.ECDH.ECDHPlugin;
import org.jcryptool.visual.ECDH.Messages;
import org.jcryptool.visual.ECDH.algorithm.EC;
import org.jcryptool.visual.ECDH.algorithm.ECFm;
import org.jcryptool.visual.ECDH.algorithm.ECPoint;
import org.jcryptool.visual.ECDH.ui.wizards.PublicParametersWizard;
import org.jcryptool.visual.ECDH.ui.wizards.SecretKeyWizard;

import de.flexiprovider.common.math.FlexiBigInt;
import de.flexiprovider.common.math.ellipticcurves.EllipticCurve;
import de.flexiprovider.common.math.ellipticcurves.Point;

public class ECDHComposite extends Composite implements PaintListener {

	private Group groupMain = null;
	private Group groupParameters = null;
	private Canvas canvasMain = null;

	// private Composite compositeBtn = null;
	private Composite compositeMain = null;
	private Canvas canvasBtn = null;
	private Canvas canvasExchange = null;

	private Button btnSetPublicParameters = null;
	private Button btnChooseSecrets = null;
	private Button btnCreateSharedKeys = null;
	private Button btnExchangeKeys = null;
	private Button btnGenerateKey = null;
	private Group groupAlice = null;
	private Group groupBob = null;
	private Text textCurve = null;
	private Text textGenerator = null;
	private Button btnSecretA = null;
	private Color cRed = new Color(Display.getCurrent(), 247, 56, 51);
	private Color cGreen = new Color(Display.getCurrent(), 0, 255, 64); // @jve:decl-index=0:
	private Button btnCalculateSharedA = null;
	private Text textSecretA = null;
	private Text textSharedA = null;
	private Button btnCalculateKeyA = null;
	private Text textCommonKeyA = null;
	private Button btnSecretB = null;
	private Text textSecretB = null;
	private Button btnCalculateSharedB = null;
	private Text textSharedB = null;
	private Button btnCalculateKeyB = null;
	private Text textCommonKeyB = null;
	private EC curve; // @jve:decl-index=0:
	private int[] elements;
	private int secretA = -1;
	private int secretB = -1;
	private FlexiBigInt secretLargeA = null;
	private FlexiBigInt secretLargeB = null;
	private ECPoint shareA;
	private ECPoint shareB;
	private Point shareLargeA;
	private Point shareLargeB;
	private ECPoint keyA;
	private ECPoint keyB;
	private Point keyLargeA;
	private Point keyLargeB;
	private ECPoint generator;
	private int valueN;
	private ECDHView view;
	private File logFile;
	private boolean large;
	private EllipticCurve largeCurve;
	private Point pointG;
	private FlexiBigInt largeOrder;
	private boolean showAnimation = true;
	private boolean showInformationDialogs = true;
	private final String saveToEditorCommandId = "org.jcryptool.visual.ecdh.commands.saveToEditor";
	private AbstractHandler saveToEditorHandler;
	private final String saveToFileCommandId = "org.jcryptool.visual.ecdh.commands.saveToFile";
	private AbstractHandler saveToFileHandler;
	private IServiceLocator serviceLocator;
	private Color grey = new Color(Display.getCurrent(), 140, 138, 140);
	private Color lightGrey = new Color(Display.getCurrent(), 180, 177, 180);

	public ECDHComposite(Composite parent, int style, ECDHView view) {
		super(parent, style);
		this.view = view;
		// setLayout(new GridLayout(2, false));
		setLayout(new GridLayout());
		createCompositeIntro();
		// createCanvasBtn();
		createGroupMain();

		serviceLocator = PlatformUI.getWorkbench();
		IMenuManager dropDownMenu = view.getViewSite().getActionBars().getMenuManager();
		final String showAnimationCommandId = "org.jcryptool.visual.ecdh.commands.showAnimation";
		AbstractHandler showAnimationHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event) {
				toggleAnimation();
				return null;
			}
		};
		defineCommand(showAnimationCommandId, Messages.getString("ECDHComposite.0"), showAnimationHandler);
		addContributionItem(dropDownMenu, showAnimationCommandId, null, null, SWT.CHECK);
		final String showInfoDialogsCommandId = "org.jcryptool.visual.ecdh.commands.showInfoDialogs";
		AbstractHandler showInfoDialogsHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event) {
				toggleInformationDialogs();
				return null;
			}
		};
		defineCommand(showInfoDialogsCommandId, Messages.getString("ECDHComposite.1"), showInfoDialogsHandler);
		addContributionItem(dropDownMenu, showInfoDialogsCommandId, null, null, SWT.CHECK);
		dropDownMenu.add(new Separator());
		saveToEditorHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event) {
				saveToEditor();
				return null;
			}
		};
		defineCommand(saveToEditorCommandId, Messages.getString("ECDHComposite.2"), null); // don't enable the command
																							// until we have results to
																							// save
		addContributionItem(dropDownMenu, saveToEditorCommandId, null, null, SWT.PUSH);
		saveToFileHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event) {
				saveToFile();
				return null;
			}
		};
		defineCommand(saveToFileCommandId, Messages.getString("ECDHComposite.3"), null); // don't enable the command
																							// until we have results to
																							// save
		addContributionItem(dropDownMenu, saveToFileCommandId, null, null, SWT.PUSH);

		IToolBarManager toolBarMenu = view.getViewSite().getActionBars().getToolBarManager();
		final String resetCommandId = "org.jcryptool.visual.ecdh.commands.reset";
		AbstractHandler resetHandler = new AbstractHandler() {
			public Object execute(ExecutionEvent event) {
				reset(0);
				return null;
			}
		};
		defineCommand(resetCommandId, Messages.getString("ECDHComposite.4"), resetHandler); // $NON_NLS-1$
		addContributionItem(toolBarMenu, resetCommandId, ECDHPlugin.getImageDescriptor("icons/reset.gif"), null, //$NON-NLS-1$
				SWT.PUSH);
	}

	private void createCanvasBtn(Group parent) {
		canvasBtn = new Canvas(parent, SWT.NO_REDRAW_RESIZE);
		canvasBtn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		canvasBtn.setLayout(new GridLayout());

		// All the Buttons and Buttonlistener are here
		btnSetPublicParameters = new Button(canvasBtn, SWT.NONE);
		GridData gd_btnSetPublicParameters = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnSetPublicParameters.heightHint = 60;
		btnSetPublicParameters.setLayoutData(gd_btnSetPublicParameters);
		btnSetPublicParameters.setBackground(cRed);
		btnSetPublicParameters.setText(Messages.getString("ECDHView.setPublicParameters")); //$NON-NLS-1$
		btnSetPublicParameters.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText(Messages.getString("ECDHView.setPublicParameters")); //$NON-NLS-1$
						messageBox.setMessage(Messages.getString("ECDHView.Step1")); //$NON-NLS-1$
						messageBox.open();
					}

					PublicParametersWizard wiz = new PublicParametersWizard(curve, generator);
					WizardDialog dialog = new WizardDialog(new Shell(Display.getCurrent()), wiz);
					if (dialog.open() == Window.OK) {
						reset(1);
						large = wiz.isLarge();
						if (large) {
							largeCurve = wiz.getLargeCurve();
							pointG = wiz.getLargeGenerator();
							largeOrder = wiz.getLargeOrder();
							textCurve.setText(largeCurve.toString());
							textGenerator.setText("" + pointG.getXAffin() + ", " + pointG.getYAffin() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						} else {
							curve = wiz.getCurve();
							if (curve != null && curve.getType() == ECFm.ECFm)
								elements = ((ECFm) curve).getElements();
							textCurve.setText(curve.toString());
							generator = wiz.getGenerator();
							valueN = wiz.getOrder();
							textGenerator.setText(generator.toString());
						}

						btnChooseSecrets.setEnabled(true);
						btnSetPublicParameters.setBackground(cGreen);
						// canvasMain.redraw();
						groupMain.redraw();
					}
				} catch (Exception ex) {
					LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
				}
			}
		});

		btnChooseSecrets = new Button(canvasBtn, SWT.NONE);
		GridData gd_btnChooseSecrets = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnChooseSecrets.verticalIndent = 60;
		gd_btnChooseSecrets.heightHint = 60;
		btnChooseSecrets.setLayoutData(gd_btnChooseSecrets);
		btnChooseSecrets.setEnabled(false);
		btnChooseSecrets.setBackground(cRed);
		btnChooseSecrets.setText(Messages.getString("ECDHView.chooseSecrets")); //$NON-NLS-1$
		btnChooseSecrets.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText(Messages.getString("ECDHView.chooseSecrets")); //$NON-NLS-1$
						messageBox.setMessage(Messages.getString("ECDHView.Step2")); //$NON-NLS-1$
						messageBox.open();
					}
					btnSecretA.setEnabled(true);
					btnSecretB.setEnabled(true);
					btnChooseSecrets.setBackground(cGreen);
					// war vorher nicht da
					groupMain.redraw();
				} catch (Exception ex) {
					LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
				}
			}
		});

		btnCreateSharedKeys = new Button(canvasBtn, SWT.NONE);
		GridData gd_btnCreateSharedKeys = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnCreateSharedKeys.verticalIndent = 60;
		gd_btnCreateSharedKeys.heightHint = 60;
		btnCreateSharedKeys.setLayoutData(gd_btnCreateSharedKeys);
		btnCreateSharedKeys.setEnabled(false);
		btnCreateSharedKeys.setBackground(cRed);
		btnCreateSharedKeys.setText(Messages.getString("ECDHView.createSharedKeys")); //$NON-NLS-1$
		btnCreateSharedKeys.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText(Messages.getString("ECDHView.createSharedKeys")); //$NON-NLS-1$
						messageBox.setMessage(Messages.getString("ECDHView.Step3")); //$NON-NLS-1$
						messageBox.open();
					}
					btnCalculateSharedA.setEnabled(true);
					btnCalculateSharedB.setEnabled(true);
					btnCreateSharedKeys.setBackground(cGreen);
					// War vorher nicht da
					groupMain.redraw();
				} catch (Exception ex) {
					LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
				}
			}
		});

		btnExchangeKeys = new Button(canvasBtn, SWT.NONE);
		GridData gd_btnExchangeKeys = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnExchangeKeys.verticalIndent = 60;
		gd_btnExchangeKeys.heightHint = 60;
		btnExchangeKeys.setLayoutData(gd_btnExchangeKeys);
		btnExchangeKeys.setEnabled(false);
		btnExchangeKeys.setBackground(cRed);
		btnExchangeKeys.setText(Messages.getString("ECDHView.exchangeSharedKeys")); //$NON-NLS-1$
		btnExchangeKeys.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText(Messages.getString("ECDHView.exchangeSharedKeys")); //$NON-NLS-1$
						messageBox.setMessage(Messages.getString("ECDHView.Step4")); //$NON-NLS-1$
						messageBox.open();
					}
					new Animate().run();
					btnGenerateKey.setEnabled(true);
					btnExchangeKeys.setBackground(cGreen);
//					canvasMain.redraw();
					groupMain.redraw();
				} catch (Exception ex) {
					LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
				}
			}
		});

		btnGenerateKey = new Button(canvasBtn, SWT.NONE);
		GridData gd_btnGenerateKey = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_btnGenerateKey.verticalIndent = 60;
		gd_btnGenerateKey.heightHint = 60;
		btnGenerateKey.setLayoutData(gd_btnGenerateKey);
		btnGenerateKey.setEnabled(false);
		btnGenerateKey.setBackground(cRed);
		btnGenerateKey.setText(Messages.getString("ECDHView.generateCommonKey")); //$NON-NLS-1$
		btnGenerateKey.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText(Messages.getString("ECDHView.generateCommonKey")); //$NON-NLS-1$
						messageBox.setMessage(Messages.getString("ECDHView.Step5")); //$NON-NLS-1$
						messageBox.open();
					}
					btnCalculateKeyA.setEnabled(true);
					btnCalculateKeyB.setEnabled(true);
					btnGenerateKey.setBackground(cGreen);
					// War vorher nicht da 
					groupMain.redraw();
				} catch (Exception ex) {
					LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
				}
			}
		});

		canvasBtn.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				GC gc = e.gc;
				// der Strich soll bei 1/3 links der Buttonbreite verlaufen
				int x = btnSetPublicParameters.getBounds().x + (btnSetPublicParameters.getBounds().width/3);
				int y = btnSetPublicParameters.getBounds().y;
				int width = 10;
				// länge des Strichs. y btn unten + y btn oben +60(Bereich, wo der Strich einen knick nach rechts macht
				int height = btnGenerateKey.getBounds().y - btnSetPublicParameters.getBounds().y + 60;
				gc.setBackground(grey);
				gc.fillRectangle(x, y, width, height);
			}
		});

	}

	private void defineCommand(final String commandId, final String name, AbstractHandler handler) {
		ICommandService commandService = (ICommandService) serviceLocator.getService(ICommandService.class);
		Command command = commandService.getCommand(commandId);
		command.define(name, null, commandService.getCategory(CommandManager.AUTOGENERATED_CATEGORY_ID));
		command.setHandler(handler);
	}

	private void addContributionItem(IContributionManager manager, final String commandId, final ImageDescriptor icon,
			final String tooltip, int Style) {
		CommandContributionItemParameter param = new CommandContributionItemParameter(serviceLocator, null, commandId,
				Style);
		if (icon != null)
			param.icon = icon;
		if (tooltip != null && !tooltip.equals(""))
			param.tooltip = tooltip;
		CommandContributionItem item = new CommandContributionItem(param);
		manager.add(item);
	}

	@Override
	public void dispose() {
		cRed.dispose();
		cGreen.dispose();
		super.dispose();
	}

	/**
	 * This method initializes compositeIntro
	 *
	 */
	private void createCompositeIntro() {
		Composite compositeIntro = new Composite(this, SWT.NONE);
		compositeIntro.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		// compositeIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2,
		// 1));
		compositeIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compositeIntro.setLayout(new GridLayout(1, false));

		Label label = new Label(compositeIntro, SWT.NONE);
		label.setFont(FontService.getHeaderFont());
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		label.setText(Messages.getString("ECDHView.title")); //$NON-NLS-1$

		StyledText stDescription = new StyledText(compositeIntro, SWT.READ_ONLY);
		stDescription.setText(Messages.getString("ECDHView.description")); //$NON-NLS-1$
		stDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	protected void toggleInformationDialogs() {
		showInformationDialogs = !showInformationDialogs;
	}

	protected void toggleAnimation() {
		showAnimation = !showAnimation;
	}

	/**
	 * This method initializes groupMain
	 *
	 */
	private void createGroupMain() {
		groupMain = new Group(this, SWT.NONE);
		groupMain.setLayout(new GridLayout(4, false));
		groupMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		createCanvasBtn(groupMain);
		createGroupParameters(groupMain);
		createGroupAlice(groupMain);
		createCanvasExchange(groupMain);
		createGroupBob(groupMain);

		groupMain.setText(Messages.getString("ECDHView.groupMain")); //$NON-NLS-1$
	}

	private void createCanvasExchange(Group parent) {
		canvasExchange = new Canvas(parent, SWT.NO_REDRAW_RESIZE);
		GridData gd_canvasExchange = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_canvasExchange.widthHint = 100;
		canvasExchange.setLayoutData(gd_canvasExchange);
		// TODO here should the visual key exchange happen
		canvasExchange.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
//				//FIXME the path is relative to the top left corner and not to the the 
//				//full display like the getBounds() points
//				
				// Pfad von a nach b 
				GC gc = e.gc;
				Path ab = new Path(Display.getCurrent());
//				
//				int x1 = groupAlice.getBounds().x + groupAlice.getBounds().width;
//				// Strich beginnt mitte dem Textfeld für shared A - 5(weil der Strich 10 dick ist.
//				int y1 = textSharedA.getBounds().y + (textSharedA.getBounds().height/2) - 5;
//				ab.moveTo(0, y1);
//				
//				System.out.print("Punkt 1: ");
//				System.out.print(x1);
//				System.out.print(", ");
//				System.out.println(y1);
//				
//				// wagerecht
//				// x2 ist ein drittel horizontal +5 des canvasExchange
//				// y2 ist das selbe wie y1
//				int x2 = x1 + (canvasExchange.getBounds().width/3) + 5;
//				ab.lineTo(x2, y1);
//				
//				System.out.print("Punkt 2: ");
//				System.out.print(x2);
//				System.out.print(", ");
//				System.out.println(y1);
//				
//				// x3 ist gleich wie x2
//				// y3 ist die hölfte des Abstand zwischen textSharedA und textCommonKeyA
//				// FIXME y koordinate ist falsch
//				int y3 = y1 + textCommonKeyB.getBounds().y + (textCommonKeyB.getBounds().height/2) +5;
//				ab.lineTo(x2, y3);
//				
//				System.out.print("Punkt 3: ");
//				System.out.print(x2);
//				System.out.print(", ");
//				System.out.println(y3);
//				
//				//x4 ist der Rand von groupBob
//				//y4 ist die höhe der mitte von textCommonKeyB -5
//				int x4 = groupBob.getBounds().x;
//				int y4 = textCommonKeyB.getBounds().y + (textCommonKeyB.getBounds().height/2) - 5;
//				ab.lineTo(x4, y4);
//				
//				System.out.print("Punkt 4: ");
//				System.out.print(x4);
//				System.out.print(", ");
//				System.out.println(y4);
//				
//				//x5 ist gleich mit x4
//				//y5 ist y4 +10(dicke des Strichs
//				int y5 = y4 + 10;
//				ab.lineTo(x4, y5);
//				
//				System.out.print("Punkt 5: ");
//				System.out.print(x4);
//				System.out.print(", ");
//				System.out.println(y5);
//				
//				//x6 ist x2/x3 -10
//				//y6 ist y3
//				int x6 = x2 - 10;
//				ab.lineTo(x6, y3);
//				
//				System.out.print("Punkt 6: ");
//				System.out.print(x6);
//				System.out.print(", ");
//				System.out.println(y3);
//				
//				//x7 ist x2 -10
//				//y7 ist y2 +10
//				int x7 = x2 - 10;
//				int y7 = y1 + 10;
//				ab.lineTo(x7, y7);
//				
//				System.out.print("Punkt 7: ");
//				System.out.print(x7);
//				System.out.print(", ");
//				System.out.println(y7);
//				
//				//x8 das selbe wie x1
//				//y8 y1 +10
//				int y8 = y1+10;
//				ab.lineTo(x1, y8);
//				
//				System.out.print("Punkt 8: ");
//				System.out.print(x1);
//				System.out.print(", ");
//				System.out.println(y8);
//				
//				//zurück zum startpunkt
//				ab.lineTo(0, y1);
//				
//				System.out.print("Punkt 9: ");
//				System.out.print(x1);
//				System.out.print(", ");
//				System.out.println(y1);
				
				int canvasWidth = canvasExchange.getBounds().width;
				int canvasHeight = canvasExchange.getBounds().height;
				
				// zweite Idee
				//linker rand des canvas
				int x1 = 0;
				// mitte von textSharedA -5
				int y1 = textSharedA.getBounds().y + (textSharedA.getBounds().height/2) -5;
				ab.moveTo(x1, y1);
				
				//linkes drittel des canvas
				int x2 = (canvasWidth/3) + 5;
				//selbe höhe wie Punkt 1
				ab.lineTo(x2, y1);
				
				//selbe breite wie punkt2
				//schwierig zu beschreiben - hoffentlich funktionierts. -2 dabei, damit das schräge ausgelichen wird
				int y3 = textCommonKeyB.getBounds().y + (textCommonKeyB.getBounds().height/2) - (canvasWidth*2/3) - 2;
				ab.lineTo(x2, y3);
				
				//rechter rand des canvas
				int x4 = canvasWidth;
				//mitte von textCommonKeyB -5 (eigentlich -7.5 weil der Strich 10px dick sein soll
				int y4 = textCommonKeyB.getBounds().y + (textCommonKeyB.getBounds().height/2) - 7;
				ab.lineTo(x4, y4);
				
				//x bleibt gleich. rechter rand des canvas
				//y +14
				int y5 = y4+14;
				ab.lineTo(x4, y5);
				
				//FIXME hier sind die Variablen nicht richtig durchnummeriert.
				// breite wie 2 nur mit -10
				int x5 = x2-10;
				//höhe gleich mit Punkt 3
				ab.lineTo(x5, y3 + 4);
				
				//breite wie punkt 5
				//höhe wie Punkt 1 nur +10
				int y6 = y1 +10;
				ab.lineTo(x5, y6);
				
				//linker rand des canvas -> 0=x1
				//höhe wie Punkt 6
				ab.lineTo(x1, y6);
				
				//und zurück zum anfang
				ab.lineTo(x1, y1);
				
				gc.setBackground(grey);
				gc.fillPath(ab);
				
				
				Path ba = new Path(Display.getCurrent());
				// right border of the composite canvasExchange
				int abX1 = canvasWidth;
				// height of the textSharedB -5
				int baY1 = textSharedB.getBounds().y + (textSharedB.getBounds().height/2) - 5;
				ba.moveTo(abX1, baY1);
				
				
				
				//rechtes drittel des canvasExchange
//				-5 damit die mitte getroffen wird
				int baX2 = canvasWidth*2/3 -5;
				//höhe ist die selbe wie beim startpunkt
				ba.lineTo(baX2, baY1);
				
				
				// So wie beim Punkt 3 von Path ab - schwierig zu beschreiben - X BLEIBT GLEICH
				// y ändert sich auf die entfernung von 2/3 des canvasExchange zum linken Rand  
			// als die höhe bis der der Strich gezogen wird(ungenauigkeit der Breite der des 45° Grad
				// strich nicht einbezigen (ergibt sich übrigens aus sqrt(2)*gewünschte breite

				int baY3 = textCommonKeyA.getBounds().y + (textCommonKeyA.getBounds().height/2) - (canvasWidth*2/3) - 2;
				ba.lineTo(baX2, baY3);
				
				
				//x ist 0 <=> linker rand des cE
				int baY4 = textCommonKeyA.getBounds().y + (textCommonKeyA.getBounds().height/2) - 7;
				ba.lineTo(0, baY4);
				
				// x bleib gleich 
				//y +14 
				int baY5 = baY4 + 14;
				ba.lineTo(0, baY5);
				
				//breite wie baX2 nur +10
				int baX6 = baX2 + 10;
				//höhe wie Punkt 3 + 4 um die 14 px linie auszugleichen
				int baY6 = baY3 + 4;
				ba.lineTo(baX6, baY6);
				
				//breite wie baX6
				//höhe wie baY1 +10
				int baY7 = baY1 + 10;
				ba.lineTo(baX6, baY7);
				
				//nach rechts an den rand
				ba.lineTo(canvasWidth, baY7);
				
				//und zurück zum Anfang
				ba.lineTo(canvasWidth, baY1);
				
				gc.fillPath(ba);
				
			}
		});

	}

	/**
	 * This method initializes groupParameters
	 *
	 */
	private void createGroupParameters(Group parent) {
		groupParameters = new Group(parent, SWT.NONE);
		groupParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		groupParameters.setText(Messages.getString("ECDHView.groupParameters")); //$NON-NLS-1$
		GridLayout gridLayout = new GridLayout(2, false);
		groupParameters.setLayout(gridLayout);
		Label label = new Label(groupParameters, SWT.NONE);
		label.setText(Messages.getString("ECDHView.labelCurve")); //$NON-NLS-1$
		textCurve = new Text(groupParameters, SWT.BORDER | SWT.READ_ONLY);
		textCurve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		label = new Label(groupParameters, SWT.NONE);
		label.setText(Messages.getString("ECDHView.labelGenerator")); //$NON-NLS-1$
		textGenerator = new Text(groupParameters, SWT.BORDER | SWT.READ_ONLY);
		textGenerator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	/**
	 * This method initializes groupAlice
	 *
	 */
	private void createGroupAlice(Group parent) {
		groupAlice = new Group(parent, SWT.NONE);
		groupAlice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		groupAlice.setText("Alice"); //$NON-NLS-1$
		groupAlice.setLayout(new GridLayout(2, false));

		btnSecretA = new Button(groupAlice, SWT.NONE);
		btnSecretA.setText(Messages.getString("ECDHView.secret")); //$NON-NLS-1$
		btnSecretA.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));
		btnSecretA.setBackground(cRed);
		btnSecretA.setEnabled(false);
		btnSecretA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SecretKeyWizard wiz;
				if (large)
					wiz = new SecretKeyWizard("Alice", secretLargeA, largeOrder); //$NON-NLS-1$
				else
					wiz = new SecretKeyWizard("Alice", secretA, valueN); //$NON-NLS-1$
				WizardDialog dialog = new WizardDialog(new Shell(Display.getCurrent()), wiz);
				dialog.setPageSize(600, 80);
				if (dialog.open() == Window.OK) {
					reset(2);
					if (large) {
						secretLargeA = wiz.getLargeSecret();
						if (secretLargeA != null && secretLargeB != null) {
							btnCreateSharedKeys.setEnabled(true);
							canvasMain.redraw();
						}
					} else {
						secretA = wiz.getSecret();
						if (secretA > 0 && secretB > 0) {
							btnCreateSharedKeys.setEnabled(true);
							canvasMain.redraw();
						}
					}
					textSecretA.setText("xxxxxxxxxxxxxxxxxxxxxx"); //$NON-NLS-1$
					btnSecretA.setBackground(cGreen);
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText("Alice " + Messages.getString("ECDHView.messageSecretKeyTitle")); //$NON-NLS-1$ //$NON-NLS-2$
						messageBox.setMessage("Alice " + Messages.getString("ECDHView.messageSecretKey") + " Alice" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								+ Messages.getString("ECDHView.messageSecretKey2"));
						messageBox.open();
					}
				}
			}
		});
		Label label = new Label(groupAlice, SWT.NONE);
		label.setText("a ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		textSecretA = new Text(groupAlice, SWT.BORDER | SWT.PASSWORD | SWT.READ_ONLY);
		textSecretA.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, true));

		btnCalculateSharedA = new Button(groupAlice, SWT.NONE);
		btnCalculateSharedA.setText(Messages.getString("ECDHView.calculate")); //$NON-NLS-1$
		btnCalculateSharedA.setEnabled(false);
		btnCalculateSharedA.setBackground(cRed);

		btnCalculateSharedA.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnCalculateSharedA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (large) {
					shareLargeA = multiplyLargePoint(pointG, secretLargeA);
					textSharedA.setText("(" + shareLargeA.getXAffin() + ", " + shareLargeA.getYAffin() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				} else {
					shareA = curve.multiplyPoint(generator, secretA);
					textSharedA.setText(shareA.toString());
				}
				btnCalculateSharedA.setBackground(cGreen);
				if (showInformationDialogs) {
					MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
							SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText(Messages.getString("ECDHView.messageSharedKeyTitle")); //$NON-NLS-1$
					messageBox.setMessage("Alice " + Messages.getString("ECDHView.messageSharedKeyHer")); //$NON-NLS-1$ //$NON-NLS-2$
					messageBox.open();
				}
				if ((large && shareLargeA != null && shareLargeB != null)
						|| (!large && shareA != null && shareB != null)) {
					btnExchangeKeys.setEnabled(true);
					canvasMain.redraw();
				}
			}
		});
		label = new Label(groupAlice, SWT.NONE);
		label.setText("A ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		textSharedA = new Text(groupAlice, SWT.BORDER | SWT.READ_ONLY);
		textSharedA.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		btnCalculateKeyA = new Button(groupAlice, SWT.NONE);
		btnCalculateKeyA.setText(Messages.getString("ECDHView.calculate")); //$NON-NLS-1$
		btnCalculateKeyA.setEnabled(false);
		btnCalculateKeyA.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, true, 2, 1));
		btnCalculateKeyA.setBackground(cRed);
		btnCalculateKeyA.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (large) {
					keyLargeA = multiplyLargePoint(shareLargeB, secretLargeA);
					textCommonKeyA.setText(keyLargeA.getXAffin().toString());
				} else {
					keyA = curve.multiplyPoint(shareB, secretA);
					if (keyA == null)
						keyA = generator;
					textCommonKeyA.setText(keyA.toString());
				}
				btnCalculateKeyA.setBackground(cGreen);
				if (showInformationDialogs) {
					MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
							SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText(Messages.getString("ECDHView.messageCommonKeyTitle")); //$NON-NLS-1$
					messageBox.setMessage("Alice " + Messages.getString("ECDHView.messageCommonKey")); //$NON-NLS-1$ //$NON-NLS-2$
					messageBox.open();
				}
				Boolean b;
				if (large)
					b = keyLargeA != null && keyLargeB != null;
				else
					b = keyA != null && keyB != null;
				if (b) {
					ICommandService commandService = (ICommandService) serviceLocator.getService(ICommandService.class);
					Command command = commandService.getCommand(saveToEditorCommandId);
					command.setHandler(saveToEditorHandler);
					command = commandService.getCommand(saveToFileCommandId);
					command.setHandler(saveToFileHandler);
					canvasMain.redraw();
					if (large)
						b = keyLargeA.getXAffin().equals(keyLargeB.getXAffin());
					else
						b = keyA.equals(keyB);
					if (b) {
						if (showInformationDialogs) {
							MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
									SWT.ICON_INFORMATION | SWT.OK);
							messageBox.setText(Messages.getString("ECDHView.messageSuccesTitle")); //$NON-NLS-1$
							messageBox.setMessage(Messages.getString("ECDHView.messageSucces")); //$NON-NLS-1$
							messageBox.open();
						}
					} else {
						if (showInformationDialogs) {
							MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
									SWT.ICON_ERROR | SWT.OK);
							messageBox.setText(Messages.getString("ECDHView.messageFailTitle")); //$NON-NLS-1$
							messageBox.setMessage(Messages.getString("ECDHView.messageFail")); //$NON-NLS-1$
							messageBox.open();
						}
					}
				}
			}

		});

		label = new Label(groupAlice, SWT.NONE);
		label.setText("S ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false));

		textCommonKeyA = new Text(groupAlice, SWT.BORDER | SWT.READ_ONLY);
		textCommonKeyA.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false));
	}

	/**
	 * This method initializes groupBob
	 *
	 */
	private void createGroupBob(Group parent) {
		groupBob = new Group(parent, SWT.NONE);
		// groupBob.setLayoutData(new GridData(SWT.FILL | SWT.RIGHT, SWT.FILL | SWT.TOP,
		// false, true));
		groupBob.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		groupBob.setText("Bob"); //$NON-NLS-1$
		groupBob.setLayout(new GridLayout(2, false));

		btnSecretB = new Button(groupBob, SWT.NONE);
		btnSecretB.setText(Messages.getString("ECDHView.secret")); //$NON-NLS-1$
		btnSecretB.setEnabled(false);
		// btnSecretB.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2,
		// 1));
		btnSecretB.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 2, 1));
		btnSecretB.setBackground(cRed);
		btnSecretB.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SecretKeyWizard wiz;
				if (large)
					wiz = new SecretKeyWizard("Bob", secretLargeB, largeOrder); //$NON-NLS-1$
				else
					wiz = new SecretKeyWizard("Bob", secretB, valueN); //$NON-NLS-1$

				WizardDialog dialog = new WizardDialog(new Shell(Display.getCurrent()), wiz);
				dialog.setPageSize(600, 80);
				if (dialog.open() == Window.OK) {
					reset(2);
					if (large) {
						secretLargeB = wiz.getLargeSecret();
						if (secretLargeA != null && secretLargeB != null) {
							btnCreateSharedKeys.setEnabled(true);
							canvasMain.redraw();
						}
					} else {
						secretB = wiz.getSecret();
						if (secretA > 0 && secretB > 0) {
							btnCreateSharedKeys.setEnabled(true);
//							canvasMain.redraw();
							groupMain.redraw();
						}
					}
					textSecretB.setText("xxxxxxxxxxxxxxxxxxxxxx"); //$NON-NLS-1$
					btnSecretB.setBackground(cGreen);
					if (showInformationDialogs) {
						MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
								SWT.ICON_INFORMATION | SWT.OK);
						messageBox.setText("Bob " + Messages.getString("ECDHView.messageSecretKeyTitle")); //$NON-NLS-1$ //$NON-NLS-2$
						messageBox.setMessage("Bob " + Messages.getString("ECDHView.messageSecretKey") + " Bob" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								+ Messages.getString("ECDHView.messageSecretKey2"));
						messageBox.open();
					}
				}
			}

		});
		Label label = new Label(groupBob, SWT.NONE);
		label.setText("b ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		textSecretB = new Text(groupBob, SWT.BORDER | SWT.PASSWORD | SWT.READ_ONLY);
		// textSecretB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		textSecretB.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, true));

		btnCalculateSharedB = new Button(groupBob, SWT.NONE);
		btnCalculateSharedB.setText(Messages.getString("ECDHView.calculate")); //$NON-NLS-1$
		btnCalculateSharedB.setEnabled(false);
		// GridData gridData = new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1);
		// gridData.verticalIndent = 40;
		// btnCalculateSharedB.setLayoutData(gridData);
		btnCalculateSharedB.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnCalculateSharedB.setBackground(cRed);
		btnCalculateSharedB.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (large) {
					shareLargeB = multiplyLargePoint(pointG, secretLargeB);
					textSharedB.setText("(" + shareLargeB.getXAffin() + ", " + shareLargeB.getYAffin() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				} else {
					shareB = curve.multiplyPoint(generator, secretB);
					textSharedB.setText(shareB.toString());
				}
				btnCalculateSharedB.setBackground(cGreen);
				if (showInformationDialogs) {
					MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
							SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText(Messages.getString("ECDHView.messageSharedKeyTitle")); //$NON-NLS-1$
					messageBox.setMessage("Bob " + Messages.getString("ECDHView.messageSharedKeyHis")); //$NON-NLS-1$ //$NON-NLS-2$
					messageBox.open();
				}
				if ((large && shareLargeA != null && shareLargeB != null)
						|| (!large && shareA != null && shareB != null)) {
					btnExchangeKeys.setEnabled(true);
//					canvasMain.redraw();
					groupMain.redraw();
				}
			}

		});
		label = new Label(groupBob, SWT.NONE);
		label.setText("B ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		textSharedB = new Text(groupBob, SWT.BORDER | SWT.READ_ONLY);
		// textSharedB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		textSharedB.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		btnCalculateKeyB = new Button(groupBob, SWT.NONE);
		btnCalculateKeyB.setText(Messages.getString("ECDHView.calculate")); //$NON-NLS-1$
		btnCalculateKeyB.setEnabled(false);
		// gridData = new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1);
		// gridData.verticalIndent = 130;
		// btnCalculateKeyB.setLayoutData(gridData);
		btnCalculateKeyB.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, true, 2, 1));
		btnCalculateKeyB.setBackground(cRed);
		btnCalculateKeyB.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (large) {
					keyLargeB = multiplyLargePoint(shareLargeA, secretLargeB);
					textCommonKeyB.setText(keyLargeB.getXAffin().toString());
				} else {
					keyB = curve.multiplyPoint(shareA, secretB);
					if (keyB == null)
						keyB = generator;
					textCommonKeyB.setText(keyB.toString());
				}
				btnCalculateKeyB.setBackground(cGreen);
				if (showInformationDialogs) {
					MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
							SWT.ICON_INFORMATION | SWT.OK);
					messageBox.setText(Messages.getString("ECDHView.messageCommonKeyTitle")); //$NON-NLS-1$
					messageBox.setMessage("Bob " + Messages.getString("ECDHView.messageCommonKey")); //$NON-NLS-1$ //$NON-NLS-2$
					messageBox.open();
				}
				Boolean b;
				if (large)
					b = keyLargeA != null && keyLargeB != null;
				else
					b = keyA != null && keyB != null;
				if (b) {
					ICommandService commandService = (ICommandService) serviceLocator.getService(ICommandService.class);
					Command command = commandService.getCommand(saveToEditorCommandId);
					command.setHandler(saveToEditorHandler);
					command = commandService.getCommand(saveToFileCommandId);
					command.setHandler(saveToFileHandler);
					canvasMain.redraw();
					if (large)
						b = keyLargeA.getXAffin().equals(keyLargeB.getXAffin());
					else
						b = keyA.equals(keyB);
					if (b) {
						if (showInformationDialogs) {
							MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
									SWT.ICON_INFORMATION | SWT.OK);
							messageBox.setText(Messages.getString("ECDHView.messageSuccesTitle")); //$NON-NLS-1$
							messageBox.setMessage(Messages.getString("ECDHView.messageSucces")); //$NON-NLS-1$
							messageBox.open();
						}
					} else {
						if (showInformationDialogs) {
							MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()),
									SWT.ICON_ERROR | SWT.OK);
							messageBox.setText(Messages.getString("ECDHView.messageFailTitle")); //$NON-NLS-1$
							messageBox.setMessage(Messages.getString("ECDHView.messageFail")); //$NON-NLS-1$
							messageBox.open();
						}
					}
				}
			}
		});
		label = new Label(groupBob, SWT.NONE);
		label.setText("S ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false));

		textCommonKeyB = new Text(groupBob, SWT.BORDER | SWT.READ_ONLY);
		// textCommonKeyB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		textCommonKeyB.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false));
	}

	public void paintControl(PaintEvent e) {
		GC gc = e.gc;
		Color grey = new Color(Display.getCurrent(), 140, 138, 140);
		Color lightGrey = new Color(Display.getCurrent(), 180, 177, 180);
		gc.setBackground(grey);
		// Strich der die Buttons verbindet
		int x = 100;
		int y = 70;
		int width = 10;
		int height = 0;
		int totalheight = 430;
		boolean b;
		if (large)
			b = keyLargeA == null || keyLargeB == null || !keyLargeA.getXAffin().equals(keyLargeB.getXAffin());
		else
			b = keyA == null || keyB == null || !keyA.equals(keyB);
		if (btnChooseSecrets == null || !btnChooseSecrets.getEnabled()) {
			height = 0;
		} else if (!btnCreateSharedKeys.getEnabled()) {
			height = 50;
		} else if (!btnExchangeKeys.getEnabled()) {
			height = 150;
		} else if (!btnGenerateKey.getEnabled()) {
			height = 250;
		} else if (b) {
			height = 350;
		} else
			height = totalheight;
		gc.fillRectangle(x, y, width, height);
		if (b) {
			gc.setBackground(lightGrey);
		} else {
			ImageDescriptor id = ECDHPlugin.getImageDescriptor("icons/key.png"); //$NON-NLS-1$
			ImageData imD = id.getImageData();
			imD.transparentPixel = 16777215; // white
			Image img = new Image(Display.getCurrent(), imD);
			gc.drawImage(img, 400, 480);
		}

		gc.fillRectangle(x, y + height, width, totalheight - height);
		// wagerechter Strich unten unter Gernerate Common key
		gc.fillRectangle(x, y + totalheight, 60, width);
		// Pfeilspitze
		Path p = new Path(Display.getCurrent());
		p.moveTo(160, 495);
		p.lineTo(160, 515);
		p.lineTo(190, 505);
		p.lineTo(160, 495);
		gc.fillPath(p);

		if (btnGenerateKey == null || !btnGenerateKey.getEnabled())
			gc.setBackground(lightGrey);
		else
			gc.setBackground(grey);
		// Pfad von a nach B
		Path ab = new Path(Display.getCurrent());
		x = 422;
		y = 257;
		ab.moveTo(x, y);
		ab.lineTo(x + 35, y);
		ab.lineTo(x + 90, y + 65);
		ab.lineTo(x + 90, y + 180);
		ab.lineTo(x + 100, y + 180);
		ab.lineTo(x + 100, y + 175);
		ab.lineTo(x + 120, y + 185);
		ab.lineTo(x + 100, y + 195);
		ab.lineTo(x + 100, y + 190);
		ab.lineTo(x + 80, y + 190);
		ab.lineTo(x + 80, y + 70);
		ab.lineTo(x + 30, y + 10);
		ab.lineTo(x, y + 10);
		ab.lineTo(x, y);
		gc.fillPath(ab);
		ab.dispose();

		// Pfad von b nach A
		Path ba = new Path(Display.getCurrent());
		x = 543;
		y = 257;
		ba.moveTo(x, y);
		ba.lineTo(x - 35, y);
		ba.lineTo(x - 90, y + 65);
		ba.lineTo(x - 90, y + 180);
		ba.lineTo(x - 100, y + 180);
		ba.lineTo(x - 100, y + 175);
		ba.lineTo(x - 120, y + 185);
		ba.lineTo(x - 100, y + 195);
		ba.lineTo(x - 100, y + 190);
		ba.lineTo(x - 80, y + 190);
		ba.lineTo(x - 80, y + 70);
		ba.lineTo(x - 30, y + 10);
		ba.lineTo(x, y + 10);
		ba.lineTo(x, y);
		gc.fillPath(ba);
		ba.dispose();
		grey.dispose();
		lightGrey.dispose();
	}

	private String intToBitString(int i, int length) {
		String s = ""; //$NON-NLS-1$
		int j = i;
		for (int k = 0; k < length; k++) {
			s = (j % 2) + s;
			j /= 2;
		}
		return s;
	}

	protected void saveToEditor() {
		saveToEditor(getLogString());
	}

	protected void saveToFile() {
		saveToFile(getLogString());
	}

	private String getLogString() {
		String s;
		if (large) {
			s = Messages.getString("ECDHView.logHeader") + "\n\n" + Messages.getString("ECDHView.curve") + ": " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ largeCurve + "\n\n"; //$NON-NLS-1$

			s += Messages.getString("ECDHView.AliceParameters") + ":\n"; //$NON-NLS-1$ //$NON-NLS-2$
			s += Messages.getString("ECDHView.secretKey") + " = " + secretLargeA + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.sharedKey") + " = " + shareLargeA.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.commonKey") + " = " + secretLargeA + " * " + shareLargeB + " = " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ keyLargeA + "\n\n"; //$NON-NLS-1$

			s += Messages.getString("ECDHView.BobParameters") + ":\n"; //$NON-NLS-1$ //$NON-NLS-2$
			s += Messages.getString("ECDHView.secretKey") + " = " + secretLargeB + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.sharedKey") + " = " + shareLargeB + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.commonKey") + " = " + secretLargeB + " * " + shareLargeA + " = " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ keyLargeB + "\n\n"; //$NON-NLS-1$
		} else {
			s = Messages.getString("ECDHView.logHeader") + "\n\n" + Messages.getString("ECDHView.curve") + ": " + curve //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ "\n\n"; //$NON-NLS-1$

			s += Messages.getString("ECDHView.AliceParameters") + ":\n"; //$NON-NLS-1$ //$NON-NLS-2$
			s += Messages.getString("ECDHView.secretKey") + " = " + secretA + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.sharedKey") + " = " + shareA.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.commonKey") + " = " + secretA + " * " + shareB + " = " + keyA + "\n\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

			s += Messages.getString("ECDHView.BobParameters") + ":\n"; //$NON-NLS-1$ //$NON-NLS-2$
			s += Messages.getString("ECDHView.secretKey") + " = " + secretB + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.sharedKey") + " = " + shareB + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			s += Messages.getString("ECDHView.commonKey") + " = " + secretB + " * " + shareA + " = " + keyB + "\n\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		return s;
	}

	private void saveToEditor(String s) {
		if (logFile == null) {
			logFile = new File(DirectoryService.getTempDir() + "ECDH results.txt"); //$NON-NLS-1$ //$NON-NLS-2$
			logFile.deleteOnExit();
		}

		saveToFile(s);

		IWorkbenchPage editorPage = view.getSite().getPage();

		IEditorReference[] er = editorPage.getEditorReferences();
		for (int i = 0; i < er.length; i++) {
			if (er[i].getName().equals("ECDH results.txt")) { //$NON-NLS-1$
				er[i].getEditor(false).getSite().getPage().closeEditor(er[i].getEditor(false), false);
			}
		}

		try {
			IPath location = new org.eclipse.core.runtime.Path(logFile.getAbsolutePath());
			editorPage.openEditor(new PathEditorInput(location), "org.jcryptool.editor.text.editor.JCTTextEditor"); //$NON-NLS-1$
		} catch (PartInitException e) {
			LogUtil.logError(ECDHPlugin.PLUGIN_ID, e);
		}
	}

	private void saveToFile(String s) {
		selectFileLocation();
		if (logFile != null) {
			try {
				String[] sa = s.split("\n"); //$NON-NLS-1$
				if (sa.length > 1 || !sa[0].equals("")) { //$NON-NLS-1$
					if (!logFile.exists())
						logFile.createNewFile();
					FileWriter fw = new FileWriter(logFile, true);
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < sa.length; i++) {
						if (i < sa.length - 1 || (i == sa.length - 1 && !sa[i].equals(""))) { //$NON-NLS-1$
							bw.write(sa[i]);
							bw.newLine();
						}
					}
					bw.close();
					fw.close();
				}
			} catch (Exception e) {
				MessageBox messageBox = new MessageBox(new Shell(Display.getCurrent()));
				messageBox.setText(Messages.getString("ECDHComposite.160")); //$NON-NLS-1$
				messageBox.setMessage(Messages.getString("ECDHComposite.161") + e.getMessage()); //$NON-NLS-1$
				messageBox.open();
			}
		}
	}

	private void selectFileLocation() {
		FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		dialog.setFilterNames(new String[] { IConstants.TXT_FILTER_NAME, IConstants.ALL_FILTER_NAME });
		dialog.setFilterExtensions(new String[] { IConstants.TXT_FILTER_EXTENSION, IConstants.ALL_FILTER_EXTENSION });
		dialog.setFilterPath(DirectoryService.getUserHomeDir());
		dialog.setFileName("ECDH.txt"); //$NON-NLS-1$
		dialog.setOverwrite(true);
		String filename = dialog.open();
		if (filename == null) {
			logFile = null;
			return;
		} else
			logFile = new File(filename);
	}

	private void reset(int i) {
		switch (i) {
		case 0: // complete reset
			curve = null;
			valueN = 0;
			generator = null;
			elements = null;

			textCurve.setText(""); //$NON-NLS-1$
			textGenerator.setText(""); //$NON-NLS-1$
			btnSetPublicParameters.setBackground(cRed);
		case 1:// reset from Set public parameters button
			secretA = -1;
			secretB = -1;
			secretLargeA = null;
			secretLargeB = null;

			btnChooseSecrets.setEnabled(false);
			btnChooseSecrets.setBackground(cRed);
			btnSecretA.setEnabled(false);
			btnSecretA.setBackground(cRed);
			textSecretA.setText(""); //$NON-NLS-1$
			btnSecretB.setEnabled(false);
			btnSecretB.setBackground(cRed);
			textSecretB.setText(""); //$NON-NLS-1$
			btnCreateSharedKeys.setEnabled(false);
		default:
			shareA = null;
			shareB = null;
			keyA = null;
			keyB = null;
			shareLargeA = null;
			shareLargeB = null;
			keyLargeA = null;
			keyLargeB = null;

			btnCreateSharedKeys.setBackground(cRed);
			btnCalculateSharedA.setEnabled(false);
			btnCalculateSharedA.setBackground(cRed);
			textSharedA.setText(""); //$NON-NLS-1$
			btnCalculateSharedB.setEnabled(false);
			btnCalculateSharedB.setBackground(cRed);
			textSharedB.setText(""); //$NON-NLS-1$
			btnExchangeKeys.setEnabled(false);
			btnExchangeKeys.setBackground(cRed);
			btnGenerateKey.setEnabled(false);
			btnGenerateKey.setBackground(cRed);
			btnCalculateKeyA.setEnabled(false);
			btnCalculateKeyA.setBackground(cRed);
			textCommonKeyA.setText(""); //$NON-NLS-1$
			btnCalculateKeyB.setEnabled(false);
			btnCalculateKeyB.setBackground(cRed);
			textCommonKeyB.setText(""); //$NON-NLS-1$
			ICommandService commandService = (ICommandService) serviceLocator.getService(ICommandService.class);
			Command command = commandService.getCommand(saveToEditorCommandId);
			command.setHandler(null);
			command = commandService.getCommand(saveToFileCommandId);
			command.setHandler(null);
		}
		// canvasMain.redraw();
		groupMain.redraw();
		layout();
	}

	private Point multiplyLargePoint(Point p, FlexiBigInt m) {
		if (m.doubleValue() == 0)
			return null;
		if (m.doubleValue() == 1)
			return p;
		if (m.mod(new FlexiBigInt("2")).doubleValue() == 0) //$NON-NLS-1$
			return multiplyLargePoint(p, m.divide(new FlexiBigInt("2"))).multiplyBy2(); //$NON-NLS-1$
		else
			return p.add(multiplyLargePoint(p, m.subtract(new FlexiBigInt("1")))); //$NON-NLS-1$
	}

	class Animate extends Thread {
		public void run() {
			if (showAnimation) {
				// TODO this seems to be an Animation obviously. Has to be changed. simpelest solution would be if it takes
				// c instead of canvasMain
//				GC gc = new GC(canvasMain);
				GC gc = new GC(canvasExchange);
//				Image original = new Image(canvasMain.getDisplay(), 150, 210);
				Image original = new Image(canvasExchange.getDisplay(), canvasExchange.getBounds().width, canvasExchange.getBounds().height);
//				gc.copyArea(original, 400, 250);
				gc.copyArea(original, canvasExchange.getBounds().x, canvasExchange.getBounds().y);
				// keine idee wofür x und y sind
				double x = -50;
				double y = 0;
				String msg;
				if (large) {
					msg = shareLargeA.getXAffin().toString(2).substring(0, 4) + " " //$NON-NLS-1$
							+ shareLargeA.getYAffin().toString(2).substring(0, 4);
				} else {
					if (curve.getType() == ECFm.ECFm)
						msg = intToBitString(shareA.x == elements.length ? 0 : elements[shareA.x], 5) + " " //$NON-NLS-1$
								+ intToBitString(shareA.y == elements.length ? 0 : elements[shareA.y], 5);
					else
						msg = intToBitString(shareA.x, 5) + " " + intToBitString(shareA.y, 5); //$NON-NLS-1$
				}
				for (int i = 0; i < 140; i++) {
//					Image im = new Image(canvasMain.getDisplay(), original, SWT.IMAGE_COPY);
					Image im = new Image(canvasExchange.getDisplay(), original, SWT.IMAGE_COPY);
					GC gcI = new GC(im);
					gcI.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
					gcI.setFont(FontService.getHeaderFont());
					gcI.drawText(msg, (int) x, (int) y, true);

//					gc.drawImage(im, 400, 250);
					gc.drawImage(im, canvasExchange.getBounds().x, canvasExchange.getBounds().y);
					if (i < 12) {
						x += 5;
					} else if (i < 23) {
						x += 5 * 11 / 12;
						y += 5;
					} else if (i < 48) {
						y += 5;
					} else if (i < 68) {
						x += 5;
					} else if (i == 68) {
						y = 0;
						x = 120;
						if (large) {
							msg = shareLargeB.getXAffin().toString(2).substring(0, 4) + " " //$NON-NLS-1$
									+ shareLargeB.getYAffin().toString(2).substring(0, 4);
						} else {
							if (curve.getType() == ECFm.ECFm)
								msg = intToBitString(shareB.x == elements.length ? 0 : elements[shareB.x], 5) + " " //$NON-NLS-1$
										+ intToBitString(shareB.y == elements.length ? 0 : elements[shareB.y], 5);
							else
								msg = intToBitString(shareB.x, 5) + " " + intToBitString(shareB.y, 5); //$NON-NLS-1$
						}
					} else if (i < 83) {
						x -= 5;
					} else if (i < 94) {
						x -= 5 * 11 / 12;
						y += 4;
					} else if (i < 121) {
						y += 5;
					} else {
						x -= 5;
					}

					try {
						sleep(50);
					} catch (InterruptedException ex) {
						LogUtil.logError(ECDHPlugin.PLUGIN_ID, ex);
					}
				}
			}
		}
	}
}
