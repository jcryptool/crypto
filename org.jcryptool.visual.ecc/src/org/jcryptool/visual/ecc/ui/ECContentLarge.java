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

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.auto.SmoothScroller;
import org.jcryptool.visual.ecc.Messages;
import org.jcryptool.visual.ecc.algorithm.LargeCurves;

import de.flexiprovider.common.math.FlexiBigInt;
import de.flexiprovider.common.math.ellipticcurves.EllipticCurve;
import de.flexiprovider.common.math.ellipticcurves.EllipticCurveGF2n;
import de.flexiprovider.common.math.ellipticcurves.EllipticCurveGFP;
import de.flexiprovider.common.math.ellipticcurves.Point;
import de.flexiprovider.common.math.ellipticcurves.PointGF2n;
import de.flexiprovider.common.math.ellipticcurves.PointGFP;
import de.flexiprovider.common.math.finitefields.GF2nPolynomialElement;
import de.flexiprovider.common.math.finitefields.GF2nPolynomialField;
import de.flexiprovider.common.math.finitefields.GFPElement;

public class ECContentLarge extends Composite {
	private ECView view;
	private Group groupSize = null;
	private Button rbtnSmall = null;
	private Button rbtnLarge = null;
	private Group groupSettings = null;
	private Group groupType = null;
	private Button rbtnFP = null;
	private Button rbtnFM = null;
	private Group groupSelectAttributes = null;
	private Combo cStandard = null;
	private Combo cCurve = null;
	private Group groupCalculations = null;
	private Button rbtnPQ = null;
	private Button rbtnKP = null;
	private Spinner spnrK = null;
	private Group groupCurve = null;
	private Group groupAttributes = null;
	private Group groupSave = null;
	private Text txtA = null;
	private Text txtB = null;
	private Text txtP = null;
	private Text txtGx = null;
	private Text txtGy = null;
	private Text txtOrderG = null;

	private int radix = 10;

	private EllipticCurve curve;  //  @jve:decl-index=0:
	private Point pointP;  //  @jve:decl-index=0:
	private Point pointQ;  //  @jve:decl-index=0:
	private Point pointR;  //  @jve:decl-index=0:
	private Point pointG;  //  @jve:decl-index=0:
	private FlexiBigInt fbiA;
	private FlexiBigInt fbiB;
	private FlexiBigInt fbiP;  //  @jve:decl-index=0:
	private FlexiBigInt fbiOrderG;

	private Group groupRadix = null;
	private Button rbtnRadix2 = null;
	private Button rbtnRadix8 = null;
	private Button rbtnRadix10 = null;
	private Button rbtnRadix16 = null;
	private Group groupP = null;
	private Button btnPGenerate = null;
	private Button btnPGenerator = null;
	private Text txtPX = null;
	private Text txtPY = null;
	private Group groupQ = null;
	private Group groupR = null;
	private Button btnQGenerate = null;
	private Button btnQGenerator = null;
	private Text txtQX = null;
	private Text txtQY = null;
	private Label lblR = null;
	private Text txtRX = null;
	private Text txtRY = null;
	private Button btnClearPoints = null;
	private Label lblP = null;
	private Composite content;

	public ECContentLarge(Composite parent, int style, ECView v) {
		super(parent, style);
		view = v;

		setLayout(new FillLayout());

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

		fillCSelection();
		updateScreen();
		
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(scrolledComposite);
	}

	/**
	 * This method initializes groupSize
	 *
	 */
	private void createGroupSize() {
		groupSize = new Group(groupSettings, SWT.NONE);
		groupSize.setText(Messages.ECView_SelectCurveSize); 
		GridData groupSizeLData = new GridData(SWT.FILL, SWT.FILL, true, false);
		groupSizeLData.verticalIndent = ECView.customHeightIndent;
		groupSize.setLayoutData(groupSizeLData);
		groupSize.setLayout(new GridLayout(2, true));
		rbtnSmall = new Button(groupSize, SWT.RADIO);
		rbtnSmall.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnSmall.setText(Messages.ECView_Small); 
		rbtnSmall.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnSmall.getSelection()) {
					if(rbtnFP.getSelection())
						view.showFp();
					else
						view.showFm();
				}
			}

		});
		rbtnLarge = new Button(groupSize, SWT.RADIO);
		rbtnLarge.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnLarge.setText(Messages.ECView_Large); 
		rbtnLarge.setSelection(true);
	}



	/**
	 * This method creates the area for the title and description of the plugin
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
	 * This method initializes groupSettings
	 *
	 */
	private void createGroupSettings() {
		groupSettings = new Group(content, SWT.NONE);
        groupSettings.setText(Messages.ECView_Settings); 
        groupSettings.setLayout(new GridLayout());
        groupSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
        
		createGroupSize();
		createGroupType();
		createGroupSelectAttributes();
		createGroupRadix();
		createGroupCalculations();
		createGroupSave();
	}
	
	

	/**
	 * This method initializes groupType
	 *
	 */
	private void createGroupType() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 3;
		gridLayout1.makeColumnsEqualWidth = true;
		groupType = new Group(groupSettings, SWT.NONE);
		groupType.setText(Messages.ECView_SelectCurveType); 
		GridData groupTypeLData = gridData3;
		groupTypeLData.verticalIndent = ECView.customHeightIndent;
		groupType.setLayoutData(groupTypeLData);
		groupType.setLayout(gridLayout1);
		Button button = new Button(groupType, SWT.RADIO);
		button.setText(Messages.ECView_RealNumbers); 
		button.setEnabled(false);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnFP = new Button(groupType, SWT.RADIO);
		rbtnFP.setText("F(p)"); //$NON-NLS-1$
		rbtnFP.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnFP.setSelection(true);
		rbtnFP.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnFP.getSelection())
					fillCSelection();
			}
		});
		rbtnFM = new Button(groupType, SWT.RADIO);
		rbtnFM.setText("F(2^m)"); //$NON-NLS-1$
		rbtnFM.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
		rbtnFM.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnFM.getSelection())
					fillCSelection();
			}
		});
	}

	/**
	 * This method initializes groupSelectAttributes
	 *
	 */
	private void createGroupSelectAttributes() {
		GridData gridData48 = new GridData();
		gridData48.grabExcessHorizontalSpace = true;
		gridData48.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 2;
		groupSelectAttributes = new Group(groupSettings, SWT.NONE);
		groupSelectAttributes.setText(Messages.ECContentLarge_12); 
		GridData groupSelectAttributesLData = gridData48;
		groupSelectAttributesLData.verticalIndent = ECView.customHeightIndent;
		groupSelectAttributes.setLayoutData(groupSelectAttributesLData);
		groupSelectAttributes.setLayout(gridLayout2);
		Label label = new Label(groupSelectAttributes, SWT.NONE);
		label.setText(Messages.ECView_Standard); 
		createCStandard();
		label = new Label(groupSelectAttributes, SWT.NONE);
		label.setText(Messages.ECView_Curve); 
		createCCurve();
	}

	/**
	 * This method initializes cStandard
	 *
	 */
	private void createCStandard() {
		GridData gridData9 = new GridData();
		gridData9.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData9.grabExcessHorizontalSpace = true;
		cStandard = new Combo(groupSelectAttributes, SWT.NONE);
		cStandard.setLayoutData(gridData9);
		cStandard.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillCCurve();
			}

		});
	}

	/**
	 * This method initializes cCurve
	 *
	 */
	private void createCCurve() {
		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData10.grabExcessHorizontalSpace = true;
		cCurve = new Combo(groupSelectAttributes, SWT.NONE);
		cCurve.setLayoutData(gridData10);
		cCurve.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				setCurve();
			}
		});
	}

	/**
	 * This method initializes groupCalculations
	 *
	 */
	private void createGroupCalculations() {
				
		groupCalculations = new Group(groupSettings, SWT.NONE);
		GridData groupCalculationsLData = new GridData(SWT.FILL, SWT.FILL, false, false);
		groupCalculationsLData.verticalIndent = ECView.customHeightIndent;
		groupCalculations.setLayoutData(groupCalculationsLData);
		groupCalculations.setLayout(new GridLayout(2, false));
		groupCalculations.setText(Messages.ECView_Calculations); 
		
		btnClearPoints = new Button(groupCalculations, SWT.NONE);
		btnClearPoints.setText(Messages.ECView_ClearPoints); 
		btnClearPoints.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		btnClearPoints.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				pointP = null;
				pointQ = null;
				pointR = null;
				updateScreen();
			}
		});
		
		rbtnPQ = new Button(groupCalculations, SWT.RADIO);
		rbtnPQ.setText(Messages.ECContentLarge_17); 
		rbtnPQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		rbtnPQ.setSelection(true);
		rbtnPQ.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pointP != null && pointQ != null) {
					pointR = pointP.add(pointQ);
					
					lblR.setText("R = P + Q"); //$NON-NLS-1$
					view.log("R = P + Q"); //$NON-NLS-1$
					view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 

				} else {
					pointR = null;
				}

				txtQX.setEnabled(true);
				txtQY.setEnabled(true);
				updateScreen();
			}
		});
		
		rbtnKP = new Button(groupCalculations, SWT.RADIO);
		rbtnKP.setText(Messages.ECContentLarge_18); 
		rbtnKP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		rbtnKP.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnKP.getSelection()) {
					if (pointP != null) {
						//pointQ = null;
						int i = spnrK.getSelection();
						String s = "R = P + " + (i > 2 ? i - 1 : "") + "P"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						if(i > 3) {
							s += " = "; //$NON-NLS-1$
							if(i % 2 == 1)
								s += "P + "; //$NON-NLS-1$
							s += (i / 2) + "P + " + (i / 2) + "P"; //$NON-NLS-1$ //$NON-NLS-2$
						}
						pointR = multiplyPoint(pointP, i);
						
						lblR.setText(s);
						view.log("\n" + s); //$NON-NLS-1$
						view.log("\n" + Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ //$NON-NLS-2$
					}
					
					updateScreen();
					txtQX.setEnabled(false);
					txtQY.setEnabled(false);
				}
			}
		});
		
		Label label = new Label(groupCalculations, SWT.NONE);
		label.setText("k ="); //$NON-NLS-1$
		
		spnrK = new Spinner(groupCalculations, SWT.NONE);
		spnrK.setMinimum(2);
		spnrK.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		spnrK.setMaximum(Integer.MAX_VALUE);
		spnrK.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pointP != null) {
					int i = spnrK.getSelection();
					String s = "R = P + " + (i > 2 ? i - 1 : "") + "P"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					if(i > 3) {
						s += " = "; //$NON-NLS-1$
						if(i % 2 == 1)
							s += "P + "; //$NON-NLS-1$
						s += (i / 2) + "P + " + (i / 2) + "P"; //$NON-NLS-1$ //$NON-NLS-2$
					}
					pointR = multiplyPoint(pointP, i);
					lblR.setText(s);
					view.log("\n" + s); //$NON-NLS-1$
					view.log("\n" + Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ //$NON-NLS-2$
					
				}
				updateScreen();
			}
		});
	}
	

	/**
	 * This method initializes groupCurve
	 *
	 */
	private void createGroupCurve() {
		groupCurve = new Group(content, SWT.NONE);
		groupCurve.setLayout(new GridLayout());
		GridData gd_groupCurve = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd_groupCurve.minimumWidth = 600;
		gd_groupCurve.widthHint = 600;
		groupCurve.setLayoutData(gd_groupCurve);
		groupCurve.setText(Messages.ECView_EllipticCurve); 
		
		createGroupAttributes();
		createGroupP();
		createGroupQ();
		createGroupR();
	}

	/**
	 * This method initializes groupAttributes
	 *
	 */
	private void createGroupAttributes() {
		
		groupAttributes = new Group(groupCurve, SWT.NONE);
		groupAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupAttributes.setLayout(new GridLayout(2, false));
		groupAttributes.setText(Messages.ECView_CurveAttributes); 
		
		Label label = new Label(groupAttributes, SWT.RIGHT);
		label.setText("a ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtA = new Text(groupAttributes, SWT.BORDER | SWT.READ_ONLY);
		txtA.setEditable(false);
		txtA.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(groupAttributes, SWT.RIGHT);
		label.setText("b ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtB = new Text(groupAttributes, SWT.BORDER | SWT.READ_ONLY);
		txtB.setEditable(false);
		txtB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		lblP = new Label(groupAttributes, SWT.RIGHT);
		lblP.setText("p ="); //$NON-NLS-1$
		lblP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtP = new Text(groupAttributes, SWT.BORDER);
		txtP.setEditable(false);
		txtP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(groupAttributes, SWT.RIGHT);
		label.setText(Messages.ECView_Basepoint + " G:"); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		new Label(groupAttributes, SWT.NONE);
		
		label = new Label(groupAttributes, SWT.RIGHT);
		label.setText("x ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtGx = new Text(groupAttributes, SWT.BORDER);
		txtGx.setEditable(false);
		txtGx.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(groupAttributes, SWT.RIGHT);
		label.setText("y ="); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtGy = new Text(groupAttributes, SWT.BORDER);
		txtGy.setEditable(false);
		txtGy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(groupAttributes, SWT.RIGHT);
		label.setText(Messages.ECView_OrderOf + " G ="); //$NON-NLS-1$ 
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		txtOrderG = new Text(groupAttributes, SWT.BORDER);
		txtOrderG.setEditable(false);
		txtOrderG.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	private void fillCSelection() {

		cStandard.removeAll();
		String[] s = null;
		if(rbtnFP.getSelection()) {
			s = LargeCurves.standardFp;
		} else {
			s = LargeCurves.standardFm;
		}
		cStandard.setItems(s);
		cStandard.select(0);
		fillCCurve();
	}

	private void fillCCurve() {
		String[] s = null;
		if(rbtnFP.getSelection()) {
			s = LargeCurves.getNamesFp(cStandard.getSelectionIndex());
		} else {
			s = LargeCurves.getNamesFm(cStandard.getSelectionIndex());
		}
		cCurve.setItems(s);
		cCurve.select(0);
		setCurve();
	}

	private void setCurve() {
		if(rbtnFP.getSelection()) {
			FlexiBigInt[] fbi = LargeCurves.getCurveFp(cStandard.getSelectionIndex(), cCurve.getSelectionIndex());
			curve = new EllipticCurveGFP(new GFPElement(fbi[0], fbi[2]), new GFPElement(fbi[1], fbi[2]), fbi[2]);
			fbiA = fbi[0];
			fbiB = fbi[1];
			fbiP = fbi[2];
			lblP.setText("p ="); //$NON-NLS-1$
			fbiOrderG = fbi[4];
			pointG = new PointGFP(fbi[3].toByteArray(), (EllipticCurveGFP) curve);
		} else {
			FlexiBigInt[] fbi = LargeCurves.getCurveFm(cStandard.getSelectionIndex(), cCurve.getSelectionIndex());
			GF2nPolynomialField field = new GF2nPolynomialField(fbi[2].intValue());
			curve = new EllipticCurveGF2n(new GF2nPolynomialElement(field, fbi[0].toByteArray()), new GF2nPolynomialElement(field, fbi[1].toByteArray()), fbi[2].intValue());
			fbiA = fbi[0];
			fbiB = fbi[1];
			fbiP = fbi[2];
			lblP.setText("m ="); //$NON-NLS-1$
			fbiOrderG = fbi[4];
			pointG = new PointGF2n(fbi[3].toByteArray(), (EllipticCurveGF2n) curve);
		}
		pointP = null;
		pointQ = null;
		pointR = null;
		updateScreen();
	}

	private Point multiplyPoint(Point p, int m) {
		if(m == 0)
			return null;
		if(m == 1)
			return p;
		if(m % 2 == 0)
			return multiplyPoint(p, m / 2).multiplyBy2();
		else
			return p.add(multiplyPoint(p, m - 1));
	}

	private void updateScreen() {
		txtA.setText(fbiA.toString(radix));
		txtB.setText(fbiB.toString(radix));
		if(rbtnFP.getSelection()) {
			txtP.setText(fbiP.toString(radix));
		} else {
			txtP.setText(fbiP.toString(10));
		}
		txtGx.setText(new FlexiBigInt(pointG.getXAffin().toString(16).replaceAll("\\s", ""), 16).toString(radix));
		txtGy.setText(new FlexiBigInt(pointG.getYAffin().toString(16).replaceAll("\\s", ""), 16).toString(radix));
		txtOrderG.setText(fbiOrderG.toString(radix));

		if(pointP != null) {
			if(pointP.isZero()) {
				txtPX.setText("O"); //$NON-NLS-1$
				txtPY.setText("O"); //$NON-NLS-1$
			} else {
				txtPX.setText(pointP.getXAffin().toString(radix));
				txtPY.setText(pointP.getYAffin().toString(radix));
			}
		} else {
			txtPX.setText(""); //$NON-NLS-1$
			txtPY.setText(""); //$NON-NLS-1$
		}

		if(pointQ != null) {
			if(pointQ.isZero()) {
				txtQX.setText("O"); //$NON-NLS-1$
				txtQY.setText("O"); //$NON-NLS-1$
			} else {
				txtQX.setText(pointQ.getXAffin().toString(radix));
				txtQY.setText(pointQ.getYAffin().toString(radix));
			}
		} else {
			txtQX.setText(""); //$NON-NLS-1$
			txtQY.setText(""); //$NON-NLS-1$
		}

		if(pointR != null) {
			if(pointR.isZero()) {
				txtRX.setText("O"); //$NON-NLS-1$
				txtRY.setText("O"); //$NON-NLS-1$
			} else {
				txtRX.setText(pointR.getXAffin().toString(radix));
				txtRY.setText(pointR.getYAffin().toString(radix));
			}
		} else {
			txtRX.setText(""); //$NON-NLS-1$
			txtRY.setText(""); //$NON-NLS-1$
		}

		spnrK.setEnabled(rbtnKP.getSelection());
		btnPGenerate.setEnabled(curve != null);
		btnPGenerator.setEnabled(curve != null);
		btnQGenerate.setEnabled(pointP != null && rbtnPQ.getSelection());
		btnQGenerator.setEnabled(pointP != null && rbtnPQ.getSelection());
		btnClearPoints.setEnabled(pointP != null || pointQ != null || pointR != null);
		if(pointR == null)
			lblR.setText(""); //$NON-NLS-1$
	}

	/**
	 * This method initializes groupRadix
	 *
	 */
	private void createGroupRadix() {
		GridData gridData30 = new GridData();
		gridData30.grabExcessHorizontalSpace = true;
		gridData30.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData30.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		groupRadix = new Group(groupSettings, SWT.NONE);
		groupRadix.setLayout(new GridLayout());
		GridData groupRadixLData = gridData30;
		groupRadixLData.verticalIndent = ECView.customHeightIndent;
		groupRadix.setLayoutData(groupRadixLData);
		groupRadix.setText(Messages.ECView_Radix); 
		rbtnRadix2 = new Button(groupRadix, SWT.RADIO);
		rbtnRadix2.setText(Messages.ECView_Binary); 
		rbtnRadix2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnRadix2.getSelection()) {
					radix = 2;
					updateScreen();
				}
			}
		});
		rbtnRadix8 = new Button(groupRadix, SWT.RADIO);
		rbtnRadix8.setText(Messages.ECView_Octal); 
		rbtnRadix8.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnRadix8.getSelection()) {
					radix = 8;
					updateScreen();
				}
			}
		});
		rbtnRadix10 = new Button(groupRadix, SWT.RADIO);
		rbtnRadix10.setText(Messages.ECView_Decimal); 
		rbtnRadix10.setSelection(true);
		rbtnRadix10.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnRadix10.getSelection()) {
					radix = 10;
					updateScreen();
				}
			}
		});
		rbtnRadix16 = new Button(groupRadix, SWT.RADIO);
		rbtnRadix16.setText(Messages.ECView_Hexadecimal); 
		rbtnRadix16.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(rbtnRadix16.getSelection()) {
					radix = 16;
					updateScreen();
				}
			}
		});
	}

	/**
	 * This method initializes groupP
	 *
	 */
	private void createGroupP() {
		groupP = new Group(groupCurve, SWT.NONE);
		groupP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupP.setLayout(new GridLayout(4, false));
		groupP.setText(Messages.ECView_Point + " P"); //$NON-NLS-1$ 
		
		btnPGenerate = new Button(groupP, SWT.NONE);
		btnPGenerate.setText(Messages.ECView_GenerateRandomPoint); 
		btnPGenerate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPGenerate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(curve != null) {
					if(pointP == null) {
						view.log("\n" + Messages.ECView_CurveAttributes + ": " + Messages.ECView_Standard + ": " + cStandard.getText() + 
								",  " + Messages.ECView_Curve + ": "+ cCurve.getText());
						// The curve.toString() method returns a string containing
						// ... <sup>2<sup> ... . The "<sup>2<sup> is replaced by "²".
						String curveAsText = curve.toString().
								replaceAll("(<sup>2</sup>)", "²").
								replaceAll("(^2)", "²").
								replaceAll("(<sup>3</sup>)", "³").
								replaceAll("(^3)", "³");
						view.log(Messages.ECView_Curve + ": " + curveAsText + "\n" + (rbtnFP.getSelection() ? "p = " : "m = ") + txtP.getText()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
					if(rbtnFP.getSelection())
						pointP = new PointGFP((EllipticCurveGFP) curve, new Random());
					else
						pointP = new PointGF2n((EllipticCurveGF2n) curve, new Random());
					view.log("\n" + Messages.ECView_Point + " P = " + pointP.toString()); //$NON-NLS-1$ //$NON-NLS-2$ 

					if(rbtnKP.getSelection()) {
						int i = spnrK.getSelection();
						String s = "R = P + " + (i > 2 ? i - 1 : "") + "P"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						if(i > 3) {
							s += " = "; //$NON-NLS-1$
							if(i % 2 == 1)
								s += "P + "; //$NON-NLS-1$
							s += (i / 2) + "P + " + (i / 2) + "P"; //$NON-NLS-1$ //$NON-NLS-2$
						}
						pointR = multiplyPoint(pointP, i);
						lblR.setText(s);
						view.log("\n" + s); //$NON-NLS-1$
						view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
						updateScreen();
					} else if (pointQ != null) {
						pointR = pointP.add(pointQ);
						lblR.setText("R = P + Q"); //$NON-NLS-1$
						view.log("R = P + Q"); //$NON-NLS-1$
						view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
					}
					updateScreen();
				}
			}
		});
		
		btnPGenerator = new Button(groupP, SWT.NONE);
		btnPGenerator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPGenerator.setText(Messages.ECView_UseGeneratorG); 
		btnPGenerator.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(curve != null) {
					if(pointP == null) {
						view.log("\n" + Messages.ECView_CurveAttributes + ": " + Messages.ECView_Standard + ": " + cStandard.getText() + 
								",  " + Messages.ECView_Curve + ": " + cCurve.getText());
						// The curve.toString() method returns a string containing
						// ... <sup>2<sup> ... . The "<sup>2</sup> is replaced by "²".
						String curveAsText = curve.toString().
								replaceAll("(<sup>2</sup>)", "²").
								replaceAll("(^2)", "²").
								replaceAll("(<sup>3</sup>)", "³").
								replaceAll("(^3)", "³");
						view.log(Messages.ECView_Curve + ": " + curveAsText); //$NON-NLS-1$
					}
					pointP = pointG;
					view.log("\n" + Messages.ECView_Point + " P = " + pointP.toString() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

					if(rbtnKP.getSelection()) {
						int i = spnrK.getSelection();
						String s = "R = P + " + (i > 2 ? i - 1 : "") + "P"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						if(i > 3) {
							s += " = "; //$NON-NLS-1$
							if(i % 2 == 1)
								s += "P + "; //$NON-NLS-1$
							s += (i / 2) + "P + " + (i / 2) + "P"; //$NON-NLS-1$ //$NON-NLS-2$
						}
						pointR = multiplyPoint(pointP, i);
						lblR.setText(s);
						view.log("\n" + s); //$NON-NLS-1$
						view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
						updateScreen();
					} else if (pointQ != null) {
						pointR = pointP.add(pointQ);
						lblR.setText("R = P + Q"); //$NON-NLS-1$
						view.log("R = P + Q"); //$NON-NLS-1$
						view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
					}
					updateScreen();
				}
			}
		});
				
		Label label = new Label(groupP, SWT.NONE);
		label.setText("x ="); //$NON-NLS-1$
		
		txtPX = new Text(groupP, SWT.BORDER | SWT.READ_ONLY);
		txtPX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(groupP, SWT.NONE);
		label.setText("y ="); //$NON-NLS-1$
		
		txtPY = new Text(groupP, SWT.BORDER | SWT.READ_ONLY);
		txtPY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	}

	/**
	 * This method initializes groupQ
	 *
	 */
	private void createGroupQ() {
		groupQ = new Group(groupCurve, SWT.NONE);
		groupQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupQ.setLayout(new GridLayout(4, false));
		groupQ.setText(Messages.ECView_Point + " Q"); //$NON-NLS-1$ 
		
		btnQGenerate = new Button(groupQ, SWT.NONE);
		btnQGenerate.setText(Messages.ECView_GenerateRandomPoint); 
		btnQGenerate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnQGenerate.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { }
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(curve != null) {
					if(rbtnFP.getSelection())
						pointQ = new PointGFP((EllipticCurveGFP) curve, new Random());
					else
						pointQ = new PointGF2n((EllipticCurveGF2n) curve, new Random());
					view.log("\n" + Messages.ECView_Point + " Q = " + pointQ.toString() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

					if(rbtnPQ.getSelection()) {
						pointR = pointP.add(pointQ);
						lblR.setText("R = P + Q"); //$NON-NLS-1$
						view.log("R = P + Q"); //$NON-NLS-1$
						view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
					}
					updateScreen();
				}
			}
		});
		btnQGenerator = new Button(groupQ, SWT.NONE);
		btnQGenerator.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 2, 1));
		btnQGenerator.setText(Messages.ECView_UseGeneratorG); 
		btnQGenerator.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			@Override
			public void widgetSelected(SelectionEvent e) {
				pointQ = pointG;
				view.log("\n" + Messages.ECView_Point + " Q = " + pointQ.toString() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 

				if(rbtnPQ.getSelection()) {
					pointR = pointP.add(pointQ);
					lblR.setText("R = P + Q"); //$NON-NLS-1$
					view.log("R = P + Q"); //$NON-NLS-1$
					view.log(Messages.ECView_Point + " R = " + pointR.toString()); //$NON-NLS-1$ 
				}

				updateScreen();
			}
		});
		Label label = new Label(groupQ, SWT.NONE);
		label.setText("x ="); //$NON-NLS-1$
		txtQX = new Text(groupQ, SWT.BORDER | SWT.READ_ONLY);
		txtQX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		label = new Label(groupQ, SWT.NONE);
		label.setText("y ="); //$NON-NLS-1$
		txtQY = new Text(groupQ, SWT.BORDER | SWT.READ_ONLY);
		txtQY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	}

	/**
	 * This method initializes groupR
	 *
	 */
	private void createGroupR() {	
		groupR = new Group(groupCurve, SWT.NONE);
		groupR.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		groupR.setLayout(new GridLayout(2, false));
		groupR.setText(Messages.ECView_Point + " R"); //$NON-NLS-1$ 
		
		lblR = new Label(groupR, SWT.NONE);
		lblR.setText(""); //$NON-NLS-1$
		lblR.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label label = new Label(groupR, SWT.NONE);
		label.setText("x ="); //$NON-NLS-1$
		
		txtRX = new Text(groupR, SWT.BORDER | SWT.READ_ONLY);
		txtRX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		label = new Label(groupR, SWT.NONE);
		label.setText("y ="); //$NON-NLS-1$
		
		txtRY = new Text(groupR, SWT.BORDER | SWT.READ_ONLY);
		txtRY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
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

	public void adjustButtons() {
		rbtnSmall.setSelection(false);
		rbtnLarge.setSelection(true);
	}
}
