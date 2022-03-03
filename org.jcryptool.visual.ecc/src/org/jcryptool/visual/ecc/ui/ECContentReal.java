// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.ecc.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.auto.SmoothScroller;
import org.jcryptool.visual.ecc.ECCPlugin;
import org.jcryptool.visual.ecc.Messages;
import org.jcryptool.visual.ecc.algorithm.EC;
import org.jcryptool.visual.ecc.algorithm.FpPoint;

public class ECContentReal extends Composite {

	private Button btnDeletePoints = null;
	private Button btnKP = null;
	private Button btnPQ = null;
	private Button btnClear = null;
	private Canvas canvasCurve = null;
	private Color black = ColorService.BLACK;
	private Color white = ColorService.WHITE;
	private Color lightBlue = new Color(this.getDisplay(), 0, 255, 255);
	private Color blue = new Color(this.getDisplay(), 0, 0, 255);
	private Color purple = new Color(this.getDisplay(), 255, 0, 255);
	private Color darkPurple = new Color(this.getDisplay(), 148, 3, 148);
	private Color red = new Color(this.getDisplay(), 203, 0, 0);
	private EC curve;
	private Group groupCalculations = null;
	private Group groupCurve = null;
	private Group groupCurveAttributes = null;
	private Group groupCurveType = null;
	private Group groupSettings = null;
	private Group groupSave = null;
	private Text lblCurve = null;
	private Text lblP = null;
	private Text lblQ = null;
	private Text lblR = null;
	private FpPoint pointP;
	private FpPoint pointQ;
	private FpPoint pointR;
	private FpPoint pointSelect;
	private FpPoint[] points;
	private Button rbtnFP = null;
	private Button rbtnFM = null;
	private Button rbtnReal = null;
	private Button rbtnLarge = null;
	private Slider sliderZoom = null;
	private Spinner spnrA = null;
	private Spinner spnrB = null;
	private Spinner spnrK = null;
	private ECView view;
	private Composite content;
	private Group groupSize;
	private Button rbtnSmall;

	public ECContentReal(Composite parent, int style, ECView view) {
		super(parent, style);
		this.view = view;

		this.setLayout(new FillLayout());

		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		content = new Composite(scrolledComposite, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);
		content.setLayout(gridLayout);

		createCompositeIntro();
		createGroupCurve();
		createGroupSettings();

		scrolledComposite.setContent(content);
		scrolledComposite.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		createGroupAttributesR();

		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					LogUtil.logError(ECCPlugin.PLUGIN_ID, e);
				}
				updateCurve(true);
			}
		});

		// This makes the ScrolledComposite scrolling, when the mouse
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(scrolledComposite);
	}

	@Override
	public void dispose() {
		blue.dispose();
		darkPurple.dispose();
		lightBlue.dispose();
		purple.dispose();
		red.dispose();
		super.dispose();
	}

	/**
	 * This method initializes groupCurve1
	 *
	 */
	private void createGroupCurve() {
		groupCurve = new Group(content, SWT.NONE);
		groupCurve.setLayout(new GridLayout(3, false));
		groupCurve.setText(Messages.ECContentReal_0); // $NON-NLS-1$
		groupCurve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		createCanvasCurve();

		lblCurve = new Text(groupCurve, SWT.READ_ONLY);
		lblCurve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		lblCurve.setText(""); //$NON-NLS-1$

		btnDeletePoints = new Button(groupCurve, SWT.NONE);
		btnDeletePoints.setToolTipText(Messages.ECContentReal_3); // $NON-NLS-1$
		btnDeletePoints.setText(Messages.ECView_RemoveSelection); // $NON-NLS-1$
		btnDeletePoints.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		btnDeletePoints.setEnabled(false);
		btnDeletePoints.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnPQ.setSelection(true);
				btnPQ.setEnabled(false);
				btnClear.setEnabled(false);
				btnKP.setSelection(false);
				btnKP.setEnabled(false);
				spnrK.setEnabled(false);
				btnDeletePoints.setEnabled(false);
				setPointP(null);
				setPointQ(null);
				setPointR(null);
				updateCurve(false);
			}
		});
		Label label = new Label(groupCurve, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		label.setText(Messages.ECView_ZoomGraph); // $NON-NLS-1$
		sliderZoom = new Slider(groupCurve, SWT.NONE);
		sliderZoom.setSelection(10);
		sliderZoom.setMaximum(57);
		sliderZoom.setMinimum(0);
		sliderZoom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		sliderZoom.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				curve.updateCurve(spnrA.getSelection(), spnrB.getSelection(), 50 - sliderZoom.getSelection(),
						canvasCurve.getSize());
				points = curve.getPoints();
				updateCurve(false);
			}
		});
	}

	/**
	 * This method initializes groupSize
	 *
	 */
	private void createGroupSize() {
		groupSize = new Group(groupSettings, SWT.NONE);
		groupSize.setText(Messages.ECView_SelectCurveSize); // $NON-NLS-1$
		GridData groupSizeLData = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupSizeLData.verticalIndent = ECView.customHeightIndent;
		groupSize.setLayoutData(groupSizeLData);
		groupSize.setLayout(new GridLayout(2, true));
		rbtnSmall = new Button(groupSize, SWT.RADIO);
		rbtnSmall.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnSmall.setText(Messages.ECView_Small); // $NON-NLS-1$
		rbtnSmall.setSelection(true);
		rbtnLarge = new Button(groupSize, SWT.RADIO);
		rbtnLarge.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnLarge.setText(Messages.ECView_Large); // $NON-NLS-1$
		rbtnLarge.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.showLarge();
			}
		});
	}

	/**
	 * This method initializes groupSettings
	 *
	 */
	private void createGroupSettings() {
		groupSettings = new Group(content, SWT.NONE);
		groupSettings.setText(Messages.ECContentReal_9); // $NON-NLS-1$
		groupSettings.setLayout(new GridLayout());
		groupSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		createGroupSize();
		createGroupCurveType();
		createGroupCurveAttributes();
		createGroupCalculations();
		createGroupSave();
	}

	/**
	 * This method initializes canvasCurve
	 *
	 */
	private void createCanvasCurve() {
		canvasCurve = new Canvas(groupCurve, SWT.DOUBLE_BUFFERED);
		canvasCurve.setBackground(white);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);
		canvasCurve.setLayoutData(gridData);
		canvasCurve.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				drawGraph(e);
			}
		});
		canvasCurve.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				Point size = canvasCurve.getSize();
				if (points != null) {
					int gridSize = 50 - sliderZoom.getSelection();
					double step = Math.pow(gridSize, -1);
					double x = ((e.x - size.x / 2) * (step * 100));
					double y = -((e.y - size.y / 2) * (step * 100));
					FpPoint nearestPoint = findNearestPoint(x, y);
					if (nearestPoint != null) {
						setPointSelect(nearestPoint);
					}
				}
			}
		});
		canvasCurve.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (pointSelect != null) {
					if (pointP == null) {
						setPointP(pointSelect);
					} else if (btnPQ.getSelection()) {
						setPointQ(pointSelect);
					}
				}
			}
		});
		canvasCurve.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseEnter(MouseEvent e) {
			}

			@Override
			public void mouseExit(MouseEvent e) {
				pointSelect = null;
				updateCurve(false);
				if (pointP == null)
					lblP.setText(""); //$NON-NLS-1$
				if (pointQ == null)
					lblQ.setText(""); //$NON-NLS-1$
			}

			@Override
			public void mouseHover(MouseEvent e) {
			}
		});
	}

	/**
	 * This method initializes groupSave
	 *
	 */
	private void createGroupSave() {
        groupSave = new Group(groupSettings, SWT.NONE);
        groupSave.setLayout(new GridLayout(1, false));
        GridData groupSaveLData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		groupSaveLData.verticalIndent = ECView.customHeightIndent;
        groupSave.setLayoutData(groupSaveLData);
        groupSave.setText(Messages.ECView_calculationHistory); 
        
        Button showHistoryBtn = new Button(groupSave, SWT.PUSH);
        showHistoryBtn.setText(Messages.ECView_showCalculationHistory);
        showHistoryBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.openLogFileInEditor(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
        
        
        Text logFileLocationLbl = new Text(groupSave, SWT.MULTI);
        logFileLocationLbl.setEditable(false);
        GridData gd_logFileLocationLbl = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd_logFileLocationLbl.widthHint = 200;
        logFileLocationLbl.setLayoutData(gd_logFileLocationLbl);
        logFileLocationLbl.setText(Messages.ECView_logLocation + ":\n" + view.getLogFileLocation()); //$NON-NLS-1$
	}

	/**
	 * This method initializes groupCurveType1
	 *
	 */
	private void createGroupCurveType() {
		groupCurveType = new Group(groupSettings, SWT.NONE);
		GridData groupCurveTypeLData = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupCurveTypeLData.verticalIndent = ECView.customHeightIndent;
		groupCurveType.setLayoutData(groupCurveTypeLData);
		groupCurveType.setLayout(new GridLayout(3, true));
		groupCurveType.setText(Messages.ECView_SelectCurveType); // $NON-NLS-1$

		rbtnReal = new Button(groupCurveType, SWT.RADIO);
		rbtnReal.setText(Messages.ECView_RealNumbers); // $NON-NLS-1$
		rbtnReal.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnReal.setSelection(true);
		rbtnReal.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.showReal();
			}

		});
		rbtnFP = new Button(groupCurveType, SWT.RADIO);
		rbtnFP.setText("F(p)"); //$NON-NLS-1$
		rbtnFP.setSelection(false);
		rbtnFP.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnFP.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.showFp();
			}
		});
		rbtnFM = new Button(groupCurveType, SWT.RADIO);
		rbtnFM.setText("F(2^m)"); //$NON-NLS-1$
		rbtnFM.setSelection(false);
		rbtnFM.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnFM.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.showFm();
			}
		});
	}

	/**
	 * This method initializes groupCurveAttributes
	 *
	 */
	private void createGroupCurveAttributes() {
		groupCurveAttributes = new Group(groupSettings, SWT.NONE);
		GridData groupCurveAttributesLData = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupCurveAttributesLData.verticalIndent = ECView.customHeightIndent;
		groupCurveAttributes.setLayoutData(groupCurveAttributesLData);
		groupCurveAttributes.setLayout(new GridLayout(2, false));
		groupCurveAttributes.setText(Messages.ECView_SelectCurveAttributes); // $NON-NLS-1$
	}

	private void createGroupAttributesR() {
		Control[] c = groupCurveAttributes.getChildren();
		for (int i = 0; i < c.length; i++)
			c[i].dispose();
		sliderZoom.setEnabled(true);

		GridData gridData16 = new GridData();
		gridData16.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		Label label = new Label(groupCurveAttributes, SWT.NONE);
		label.setText("a ="); //$NON-NLS-1$
		spnrA = new Spinner(groupCurveAttributes, SWT.BORDER);
		spnrA.setMaximum(100000);
		spnrA.setSelection(-10);
		spnrA.setMinimum(-100000);
		spnrA.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnPQ.setSelection(true);
				btnKP.setSelection(false);
				btnPQ.setEnabled(false);
				btnClear.setEnabled(false);
				btnKP.setEnabled(false);
				spnrK.setEnabled(false);
				spnrK.setSelection(1);
				setPointP(null);
				setPointQ(null);
				setPointR(null);
				updateCurve(true);
			}

		});
		Label label1 = new Label(groupCurveAttributes, SWT.NONE);
		label1.setText("b ="); //$NON-NLS-1$
		spnrB = new Spinner(groupCurveAttributes, SWT.BORDER);
		spnrB.setMaximum(100000);
		spnrB.setSelection(15);
		spnrB.setMinimum(-100000);
		spnrB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnPQ.setSelection(true);
				btnKP.setSelection(false);
				btnPQ.setEnabled(false);
				btnClear.setEnabled(false);
				btnKP.setEnabled(false);
				spnrK.setEnabled(false);
				spnrK.setSelection(1);
				setPointP(null);
				setPointQ(null);
				setPointR(null);
				updateCurve(true);
			}

		});
		groupSettings.layout();
	}

	/**
	 * This method creates the Area for the title and description.
	 *
	 */
	private void createCompositeIntro() {
		TitleAndDescriptionComposite titleAndDescription = new TitleAndDescriptionComposite(content);
		GridData gd_titleAndDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_titleAndDescription.widthHint = 800;
		titleAndDescription.setLayoutData(gd_titleAndDescription);
		titleAndDescription.setTitle(Messages.ECView_Title);
		titleAndDescription.setDescription(Messages.ECView_Description);
	}

	/**
	 * This method initializes groupCalculations
	 *
	 */
	private void createGroupCalculations() {
		groupCalculations = new Group(groupSettings, SWT.NONE);
		groupCalculations.setText(Messages.ECContentReal_35); // $NON-NLS-1$
		groupCalculations.setLayout(new GridLayout(3, false));
		GridData groupCalculationsLData = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupCalculationsLData.verticalIndent = ECView.customHeightIndent;
		groupCalculations.setLayoutData(groupCalculationsLData);

		Label label = new Label(groupCalculations, SWT.WRAP);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		label.setText(Messages.ECContentReal_36); // $NON-NLS-1$

		btnPQ = new Button(groupCalculations, SWT.RADIO);
		btnPQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		btnPQ.setText(Messages.ECContentReal_37); // $NON-NLS-1$
		btnPQ.setSelection(true);
		btnPQ.setEnabled(false);

		btnPQ.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setPointQ(null);
			}
		});

		btnClear = new Button(groupCalculations, SWT.PUSH);
		btnClear.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		btnClear.setText(Messages.ECView_ClearQ);
		btnClear.setEnabled(false);

		btnClear.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				setPointQ(null);
				btnClear.setEnabled(false);
			}
		});

		btnKP = new Button(groupCalculations, SWT.RADIO);
		btnKP.setText(Messages.ECContentReal_38); // $NON-NLS-1$
		btnKP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnKP.setEnabled(false);
		btnKP.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				btnClear.setEnabled(false);
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnClear.setEnabled(false);
				spnrK.setEnabled(btnKP.getSelection());
				FpPoint q = curve.multiplyPoint(pointP, spnrK.getSelection());
				setPointQ(q);
				updateCurve(false);
			}
		});
		spnrK = new Spinner(groupCalculations, SWT.BORDER);
		spnrK.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		spnrK.setMinimum(1);
		spnrK.setEnabled(false);
		spnrK.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FpPoint q = curve.multiplyPoint(pointP, spnrK.getSelection());
				setPointQ(q);
				updateCurve(false);
			}
		});

		label = new Label(groupCalculations, SWT.READ_ONLY);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		label.setText(Messages.ECContentReal_39); // $NON-NLS-1$

		label = new Label(groupCalculations, SWT.READ_ONLY);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		label.setText("P ="); //$NON-NLS-1$
		lblP = new Text(groupCalculations, SWT.READ_ONLY);
		lblP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		lblP.setText(""); //$NON-NLS-1$

		label = new Label(groupCalculations, SWT.READ_ONLY);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		label.setText("Q ="); //$NON-NLS-1$
		lblQ = new Text(groupCalculations, SWT.READ_ONLY);
		lblQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		lblQ.setText(""); //$NON-NLS-1$

		label = new Label(groupCalculations, SWT.READ_ONLY);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		label.setText("R = P + Q ="); //$NON-NLS-1$
		lblR = new Text(groupCalculations, SWT.READ_ONLY);
		lblR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		lblR.setText(""); //$NON-NLS-1$
	}

	public void setPointSelect(FpPoint p) {
		pointSelect = p;
		if (pointP == null)
			lblP.setText("(" + ((double) pointSelect.x / 100) + "|" + ((double) pointSelect.y / 100) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (pointP != null && pointQ == null)
			lblQ.setText("(" + ((double) pointSelect.x / 100) + "|" + ((double) pointSelect.y / 100) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		updateCurve(false);
	}

	public void setPointP(FpPoint p) {
		if (p == null) {
			pointP = null;
			setPointQ(null);
			lblP.setText(""); //$NON-NLS-1$
			btnDeletePoints.setEnabled(false);
			btnKP.setEnabled(false);
			btnPQ.setEnabled(false);
			btnPQ.setSelection(true);
			btnClear.setEnabled(false);
		} else {
			btnKP.setEnabled(true);
			btnKP.setEnabled(true);
			btnPQ.setEnabled(true);
			btnPQ.setSelection(true);
			btnDeletePoints.setEnabled(true);
			pointP = p;
			view.log("\n" + Messages.ECView_CurveAttributes + ": a = " + spnrA.getText() + ", b = " + spnrB.getText());
			view.log(Messages.ECView_Curve + ": " + lblCurve.getText()); //$NON-NLS-1$ //$NON-NLS-3
			view.log(Messages.ECView_Point + " P = " + "(" + ((double) pointP.x / 100) + "|" + ((double) pointP.y / 100) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ")"); //$NON-NLS-1$ 
			lblP.setText("(" + ((double) pointP.x / 100) + "|" + ((double) pointP.y / 100) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		updateCurve(false);
	}

	public void setPointQ(FpPoint q) {
		if (q == null) {
			pointQ = null;
			lblQ.setText(""); //$NON-NLS-1$
			setPointR(null);

			if (btnPQ.getSelection()) {
				btnClear.setEnabled(false);
			}
		} else {
			pointQ = q;
			lblQ.setText("(" + ((double) pointQ.x / 100) + "|" + ((double) pointQ.y / 100) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			btnDeletePoints.setEnabled(true);

			view.log(Messages.ECView_Point + " Q = " + "(" + ((double) pointQ.x / 100) + "|" + ((double) pointQ.y / 100) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ ")"); //$NON-NLS-1$ 
			setPointR(curve.addPoints(pointP, pointQ));

			if (btnPQ.getSelection()) {
				btnClear.setEnabled(true);
			}
		}
		updateCurve(false);
	}

	public void setPointR(FpPoint r) {
		if (r == null) {
			pointR = null;
			lblR.setText(""); //$NON-NLS-1$
		} else {
			if (r.isInfinite()) {
				pointR = r;
				lblR.setText("O"); //$NON-NLS-1$ 
				MessageDialog.openInformation(getShell(), Messages.ECView_InfinityPoint,
						Messages.ECView_InfinityPointExplanation);
			} else {
				pointR = r;
				lblR.setText("(" + ((double) pointR.x / 100) + "|" + ((double) pointR.y / 100) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				view.log(Messages.ECView_Point + " R = P + Q = " + "(" + ((double) pointR.x / 100) + "|" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ ((double) pointR.y / 100) + ")"); //$NON-NLS-1$ 
			}
		}
	}

	/**
	 * Sets the label beneath the canvas
	 *
	 */
	public void setCurveLabel() {
		String s;
		if (points == null) {
			s = Messages.ECView_NoCurve; // $NON-NLS-1$
		} else
			s = curve.toString();
		lblCurve.setText(s);
	}

	/**
	 * This method controls all the parameters that need to be updated
	 */
	public void updateCurve(boolean full) {
		if (full) {
			pointSelect = null;
			pointP = null;
			pointQ = null;
			pointR = null;
			setPointP(null);

			if (curve == null)
				curve = new EC();

			curve.updateCurve(spnrA.getSelection(), spnrB.getSelection(), 50 - sliderZoom.getSelection(),
					canvasCurve.getSize());

			points = curve.getPoints();
			setCurveLabel();
		}
		canvasCurve.redraw();
	}

	private void drawGraph(PaintEvent e) {
		GC gc = e.gc;
		Point size = canvasCurve.getSize();
		int gridSize = 50 - sliderZoom.getSelection();
		Color black = new Color(canvasCurve.getDisplay(), 0, 0, 0);
		Color grey = new Color(canvasCurve.getDisplay(), 235, 235, 235);

		// first, draw the grid
		gc.setForeground(grey);
		for (int i = 0; i < size.x / 2; i += gridSize) {
			gc.drawLine(size.x / 2 - i, 0, size.x / 2 - i, size.y);
			gc.drawLine(size.x / 2 + i, 0, size.x / 2 + i, size.y);
		}
		for (int i = 0; i < size.y / 2; i += gridSize) {
			gc.drawLine(0, size.y / 2 - i, size.x, size.y / 2 - i);
			gc.drawLine(0, size.y / 2 + i, size.x, size.y / 2 + i);
		}

		// Draw the axis
		gc.setForeground(black); // Black
		gc.drawLine(size.x / 2, 0, size.x / 2, size.y);
		gc.drawLine(0, size.y / 2, size.x, size.y / 2);
		int labeljumps = 5;
		int scale = (size.x / 2 / gridSize) - ((size.x / 2 / gridSize) % labeljumps);
		if (scale > 50)
			labeljumps = 10;
		for (int i = 0; i < size.x / 2; i += gridSize) {
			if ((i / gridSize) % labeljumps == 0) {
				gc.drawLine(size.x / 2 + i, size.y / 2 - 8, size.x / 2 + i, size.y / 2 + 8);
				gc.drawLine(size.x / 2 - i, size.y / 2 - 8, size.x / 2 - i, size.y / 2 + 8);
				gc.drawLine(size.x / 2 - 8, size.y / 2 + i, size.x / 2 + 8, size.y / 2 + i);
				gc.drawLine(size.x / 2 - 8, size.y / 2 - i, size.x / 2 + 8, size.y / 2 - i);

				int label = i / gridSize;
				if (label < 10) {
					if (label != 0) {
						gc.drawText(label + "", size.x / 2 + i - 2, size.y / 2 + 10, true); //$NON-NLS-1$
						gc.drawText(-label + "", size.x / 2 - i - 5, size.y / 2 + 10, true); //$NON-NLS-1$

						gc.drawText(label + "", size.x / 2 + 13, size.y / 2 - i - 7, true); //$NON-NLS-1$
						gc.drawText(-label + "", size.x / 2 + 13, size.y / 2 + i - 7, true); //$NON-NLS-1$
					} else {
						gc.drawText(label + "", size.x / 2 + i + 13, size.y / 2 + 10, true); //$NON-NLS-1$
					}
				} else {
					gc.drawText(label + "", size.x / 2 + i - 6, size.y / 2 + 10, true); //$NON-NLS-1$
					gc.drawText(-label + "", size.x / 2 - i - 10, size.y / 2 + 10, true); //$NON-NLS-1$

					gc.drawText(label + "", size.x / 2 + 13, size.y / 2 - i - 7, true); //$NON-NLS-1$
					gc.drawText(-label + "", size.x / 2 + 13, size.y / 2 + i - 7, true); //$NON-NLS-1$
				}

			} else {
				gc.drawLine(size.x / 2 + i, size.y / 2 - 2, size.x / 2 + i, size.y / 2 + 2);
				gc.drawLine(size.x / 2 - i, size.y / 2 - 2, size.x / 2 - i, size.y / 2 + 2);
				gc.drawLine(size.x / 2 - 2, size.y / 2 + i, size.x / 2 + 2, size.y / 2 + i);
				gc.drawLine(size.x / 2 - 2, size.y / 2 - i, size.x / 2 + 2, size.y / 2 - i);
			}
		}

		if (points != null) {
			gc.setForeground(blue);
			double step = Math.pow(gridSize, -1);
			double x1, y1, x2, y2;
			for (int i = 2; i < points.length; i++) {
				if (points[i - 2].y == 0) {
					if (points[i - 1] != null && points[i - 1].y != 0) {
						x1 = (double) points[i - 2].x / 100 / step + size.x / 2;
						y1 = -(double) points[i - 2].y / 100 / step + size.y / 2;
						x2 = (double) points[i - 1].x / 100 / step + size.x / 2;
						y2 = -(double) points[i - 1].y / 100 / step + size.y / 2;
						gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
						gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
						x1 = (double) points[i - 2].x / 100 / step + size.x / 2;
						y1 = -(double) points[i - 2].y / 100 / step + size.y / 2;
						x2 = (double) points[i].x / 100 / step + size.x / 2;
						y2 = -(double) points[i].y / 100 / step + size.y / 2;
						gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
						gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
					} else {
						x1 = (double) points[i - 2].x / 100 / step + size.x / 2;
						y1 = -(double) points[i - 2].y / 100 / step + size.y / 2;
						x2 = (double) points[i - 3].x / 100 / step + size.x / 2;
						y2 = -(double) points[i - 3].y / 100 / step + size.y / 2;
						gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
						gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
						x1 = (double) points[i - 2].x / 100 / step + size.x / 2;
						y1 = -(double) points[i - 2].y / 100 / step + size.y / 2;
						x2 = (double) points[i - 4].x / 100 / step + size.x / 2;
						y2 = -(double) points[i - 4].y / 100 / step + size.y / 2;
						gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
						gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
					}
				} else {
					x1 = (double) points[i - 2].x / 100 / step + size.x / 2;
					y1 = -(double) points[i - 2].y / 100 / step + size.y / 2;
					x2 = (double) points[i].x / 100 / step + size.x / 2;
					y2 = -(double) points[i].y / 100 / step + size.y / 2;
					if ((int) Math.signum(points[i - 2].y + 0.0) == (int) Math.signum(points[i].y + 0.0)) {
						gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
						gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
					}
				}
				if (points[points.length - 1].y == 0) {
					x1 = (double) points[points.length - 1].x / 100 / step + size.x / 2;
					y1 = -(double) points[points.length - 1].y / 100 / step + size.y / 2;
					x2 = (double) points[points.length - 2].x / 100 / step + size.x / 2;
					y2 = -(double) points[points.length - 2].y / 100 / step + size.y / 2;
					gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
					gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
					x1 = (double) points[points.length - 1].x / 100 / step + size.x / 2;
					y1 = -(double) points[points.length - 1].y / 100 / step + size.y / 2;
					x2 = (double) points[points.length - 3].x / 100 / step + size.x / 2;
					y2 = -(double) points[points.length - 3].y / 100 / step + size.y / 2;
					gc.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
					gc.drawLine((int) x1, (int) y1 + 1, (int) x2, (int) y2 + 1);
				}
			}

			if (pointR != null && !pointR.isInfinite()) {
				int rX;
				int rY;
				int lX;
				int lY;
				FpPoint p = pointP;
				FpPoint q = pointQ;
				FpPoint r = pointR;
				if (q != null) {
					if (p.x < q.x && p.x < r.x) { // if pointP is the most left point
						lX = (int) ((double) p.x / 100 / step + size.x / 2);
						lY = (int) ((double) -p.y / 100 / step + size.y / 2);
					} else if (q.x < r.x) { // if pointQ is the most left point
						lX = (int) ((double) q.x / 100 / step + size.x / 2);
						lY = (int) ((double) -q.y / 100 / step + size.y / 2);
					} else { // if pointR is the most left point
						lX = (int) ((double) r.x / 100 / step + size.x / 2);
						lY = (int) ((double) r.y / 100 / step + size.y / 2);
					}

					if (p.x > q.x && p.x > r.x) { // if pointP is the most right point
						rX = (int) ((double) p.x / 100 / step + size.x / 2);
						rY = (int) ((double) -p.y / 100 / step + size.y / 2);
					} else if (q.x > r.x) { // if pointQ is the most right point
						rX = (int) ((double) q.x / 100 / step + size.x / 2);
						rY = (int) ((double) -q.y / 100 / step + size.y / 2);
					} else { // if pointR is the most left point
						rX = (int) ((double) r.x / 100 / step + size.x / 2);
						rY = (int) ((double) r.y / 100 / step + size.y / 2);
					}
				} else {
					if (p.x < r.x) { // if pointP is the most left point
						lX = (int) ((double) p.x / 100 / step + size.x / 2);
						lY = (int) ((double) -p.y / 100 / step + size.y / 2);
						rX = (int) ((double) r.x / 100 / step + size.x / 2);
						rY = (int) ((double) r.y / 100 / step + size.y / 2);
					} else { // if pointR is the most left point
						lX = (int) ((double) r.x / 100 / step + size.x / 2);
						lY = (int) ((double) r.y / 100 / step + size.y / 2);
						rX = (int) ((double) p.x / 100 / step + size.x / 2);
						rY = (int) ((double) -p.y / 100 / step + size.y / 2);
					}
				}

				int startX;
				int endX;
				int startY;
				int endY;

				if ((rX > lX ? rX - lX : lX - rX) > (rY > lY ? rY - lY : lY - rY)) {
					double alfa = (double) (rY - lY) / (double) (rX - lX);
					startX = lX - 30;
					endX = rX + 30;
					startY = lY - (int) (alfa * 30);
					endY = rY + (int) (alfa * 30);
				} else {
					double alfa = (double) (rX - lX) / (double) (rY - lY);
					startX = lX + (lY > rY ? (int) (alfa * 30) : -(int) (alfa * 30));
					endX = rX + (lY > rY ? -(int) (alfa * 30) : (int) (alfa * 30));
					;
					startY = lY + (lY > rY ? 30 : -30);
					endY = rY + (lY > rY ? -30 : 30);
				}

				if (startY < 0) {
					double alfa = (double) (rX - lX) / (double) (rY - lY);
					startX += (-startY) * alfa;
					startY = 0;
				} else if (startY > size.y) {
					double alfa = (double) (rX - lX) / (double) (rY - lY);
					startX += -(double) (startY - size.y) * alfa;
					startY = size.y;
				}

				if (endY < 0) {
					double alfa = (double) (rX - lX) / (double) (rY - lY);
					endX += (-endY - 1) * alfa;
					endY = -1;
				} else if (endY > size.y) {
					double alfa = (double) (rX - lX) / (double) (rY - lY);
					endX -= (endY - size.y) * alfa + 0.5;
					endY = size.y;
				}

				gc.setForeground(darkPurple);
				gc.setLineWidth(2);
				gc.drawLine(startX, startY, endX, endY);

				startX = (int) ((double) pointR.x / 100 / step + size.x / 2);
				endX = startX;
				if (pointR.y > 0) {
					startY = (int) ((double) pointR.y / 100 / step + size.y / 2) + 30;
					endY = (int) ((double) -pointR.y / 100 / step + size.y / 2) - 30;
				} else {
					startY = (int) ((double) pointR.y / 100 / step + size.y / 2) - 30;
					endY = (int) ((double) -pointR.y / 100 / step + size.y / 2) + 30;
				}
				gc.setForeground(purple);
				gc.drawLine(startX, startY, endX, endY);
				gc.setLineWidth(1);

				double x = (double) pointR.x / 100 / step + size.x / 2;
				double y = (double) pointR.y / 100 / step + size.y / 2;
				gc.setForeground(black);
				gc.setBackground(darkPurple);
				gc.fillOval((int) x - 3, (int) y - 3, 7, 7);
				gc.drawOval((int) x - 3, (int) y - 3, 6, 6);
				gc.setForeground(darkPurple);
				gc.setBackground(white);
				if (y < size.x / 2) {
					gc.drawText("-R", (int) x + 8, (int) y + 4, true); //$NON-NLS-1$
				} else {
					gc.drawText("-R", (int) x + 8, (int) y - 10, true); //$NON-NLS-1$
				}
			} else if (pointP != null) {
				gc.setForeground(darkPurple);
				gc.setLineWidth(2);
				int x = (int) ((double) pointP.x / 100 / step + size.x / 2);
				gc.drawLine(x, 0, x, size.y);
				gc.setLineWidth(1);
			}

			if (pointSelect != null) {
				double x = (double) pointSelect.x / 100 / step + size.x / 2;
				double y = -(double) pointSelect.y / 100 / step + size.y / 2;
				String caption = "Q"; //$NON-NLS-1$
				if (pointP == null)
					caption = "P"; //$NON-NLS-1$
				markPoint(x, y, caption, black, lightBlue, gc);
			}

			if (pointP != null) {
				double x = (double) pointP.x / 100 / step + size.x / 2;
				double y = -(double) pointP.y / 100 / step + size.y / 2;
				String caption = "P"; //$NON-NLS-1$
				if (pointP.equals(pointQ))
					caption = "P=Q"; //$NON-NLS-1$
				if (pointP.equals(pointR))
					caption = "P=R"; //$NON-NLS-1$

				markPoint(x, y, caption, red, red, gc);
			}

			if (pointQ != null && !pointQ.equals(pointP)) {
				double x = (double) pointQ.x / 100 / step + size.x / 2;
				double y = -(double) pointQ.y / 100 / step + size.y / 2;
				if (!pointQ.equals(pointP))
					markPoint(x, y, "Q", red, red, gc); //$NON-NLS-1$
			}

			if (pointR != null && !pointR.isInfinite()) {
				double x = (double) pointR.x / 100 / step + size.x / 2;
				double y = -(double) pointR.y / 100 / step + size.y / 2;
				markPoint(x, y, "R", purple, purple, gc); //$NON-NLS-1$
			}
		}
	}

	private void markPoint(double x, double y, String label, Color fg, Color bg, GC gc) {
		gc.setForeground(black);
		gc.setBackground(bg);
		gc.fillOval((int) x - 3, (int) y - 3, 7, 7);
		gc.drawOval((int) x - 3, (int) y - 3, 6, 6);
		gc.setForeground(fg);
		gc.setBackground(white);
		if (y > 0) {
			gc.drawText(label, (int) x + 8, (int) y + 4, true);
		} else {
			gc.drawText(label, (int) x + 10, (int) y - 10, true);
		}
	}

	public int getLabelPositionX(int x, double grid, int gridSize) {
		return (int) (grid * x) + 25 + (x < gridSize / 2 ? 3 : -8);
	}

	public int getLabelPositionY(int y, Point size, double grid, int gridSize) {
		return (int) (size.y - 25 - grid * y + (y < gridSize / 2 ? -15 : 3));
	}

	private FpPoint findNearestPoint(Double x, Double y) {
		double minimum = Double.MAX_VALUE;
		FpPoint nearestPoint = null;
		for (int i = 0; i < points.length; i++) {
			if (points[i] != null) {
				double currentDistance = Math
						.sqrt((points[i].x - x) * (points[i].x - x) + (points[i].y - y) * (points[i].y - y));
				if (currentDistance < minimum) {
					minimum = currentDistance;
					nearestPoint = points[i];
				}
			}
		}
		return nearestPoint;
	}

	public void adjustButtons() {
		rbtnReal.setSelection(true);
		rbtnFP.setSelection(false);
		rbtnFM.setSelection(false);
		rbtnLarge.setSelection(false);
		rbtnSmall.setSelection(true);
	}
}
