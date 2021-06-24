// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.entropy.ui;

import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.analysis.entropy.calc.EntropyCalc;
import org.jcryptool.core.util.colors.ColorService;

public class EntropyUIresults extends Composite {

	private DecimalFormat twoD;
	private Text textNoAnalysisStarted;
	private CLabel textName;
	private CLabel cLdiffchars;
	private CLabel labelCond;
	private CLabel labelRedN;
	private CLabel labelEntN;
	private CLabel labelRed1;
	private CLabel labelEnt1;
	private CLabel labelMaxent;
	private CLabel conditionalEntropy;
	private CLabel tupelNRedundancy;
	private CLabel tupelNEntropy;
	private CLabel labelFilename;
	private CLabel singleCharEntropy;
	private CLabel singleCharRedundancy;
	private CLabel maxEntropy;
	private CLabel labelAllchars;
	private CLabel labelDiffchars;
	private Group groupAnalysis;
	private CLabel cLallchars;
	private Group groupFilter;

	public EntropyUIresults(Composite parent, int style) {
		super(parent, style);
		initGUI();
		twoD = new DecimalFormat(Messages.EntropyUIresults_0);
	}

	private void initGUI() {
		GridLayout gl_this = new GridLayout();
		gl_this.verticalSpacing = 20;
		setLayout(gl_this);

		textNoAnalysisStarted = new Text(this, SWT.READ_ONLY);
		textNoAnalysisStarted.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		textNoAnalysisStarted.setText(Messages.EntropyUIresults_16);
		textNoAnalysisStarted.setBackground(ColorService.LIGHTGRAY);
		textNoAnalysisStarted.setForeground(ColorService.getColor(SWT.COLOR_BLUE));
		
		groupFilter = new Group(this, SWT.NONE);
		GridLayout gl_groupFilter = new GridLayout(2, false);
		gl_groupFilter.horizontalSpacing = 20;
		groupFilter.setLayout(gl_groupFilter);
		groupFilter.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		groupFilter.setText(Messages.EntropyUIresults_17);
		
		textName = new CLabel(groupFilter, SWT.NONE);
		textName.setText(Messages.EntropyUIresults_23);
		
		labelFilename = new CLabel(groupFilter, SWT.NONE);
		labelFilename.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelFilename.setText("..."); //$NON-NLS-1$
		labelFilename.setForeground(ColorService.getColor(SWT.COLOR_BLUE));
		
		cLdiffchars = new CLabel(groupFilter, SWT.NONE);
		cLdiffchars.setText(Messages.EntropyUIresults_22);

		labelAllchars = new CLabel(groupFilter, SWT.NONE);
		labelAllchars.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelAllchars.setText("0"); //$NON-NLS-1$
		labelAllchars.setForeground(ColorService.getColor(SWT.COLOR_BLUE));
		
		cLallchars = new CLabel(groupFilter, SWT.NONE);
		cLallchars.setText(Messages.EntropyUIresults_21);

		labelDiffchars = new CLabel(groupFilter, SWT.NONE);
		labelDiffchars.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelDiffchars.setText("0"); //$NON-NLS-1$
		labelDiffchars.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		groupAnalysis = new Group(this, SWT.NONE);
		GridLayout gl_groupAnalysis = new GridLayout(2, false);
		gl_groupAnalysis.horizontalSpacing = 20;
		groupAnalysis.setLayout(gl_groupAnalysis);
		groupAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		groupAnalysis.setText(Messages.EntropyUIresults_3);

		maxEntropy = new CLabel(groupAnalysis, SWT.NONE);
		maxEntropy.setText(Messages.EntropyUIresults_15);

		labelMaxent = new CLabel(groupAnalysis, SWT.NONE);
		labelMaxent.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelMaxent.setText("0"); //$NON-NLS-1$
		labelMaxent.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		singleCharEntropy = new CLabel(groupAnalysis, SWT.NONE);
		singleCharEntropy.setText(Messages.EntropyUIresults_14);

		labelEnt1 = new CLabel(groupAnalysis, SWT.NONE);
		labelEnt1.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelEnt1.setText("G(1) = 0"); //$NON-NLS-1$
		labelEnt1.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		singleCharRedundancy = new CLabel(groupAnalysis, SWT.NONE);
		GridData gd_singleCharRedundancy = new GridData();
		gd_singleCharRedundancy.horizontalIndent = 30;
		singleCharRedundancy.setLayoutData(gd_singleCharRedundancy);
		singleCharRedundancy.setText(Messages.EntropyUIresults_13);

		labelRed1 = new CLabel(groupAnalysis, SWT.NONE);
		labelRed1.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelRed1.setText("0 %"); //$NON-NLS-1$
		labelRed1.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		tupelNEntropy = new CLabel(groupAnalysis, SWT.NONE);
		tupelNEntropy.setText(Messages.EntropyUIresults_12);

		labelEntN = new CLabel(groupAnalysis, SWT.NONE);
		labelEntN.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelEntN.setText("G(n) = 0"); //$NON-NLS-1$
		labelEntN.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		tupelNRedundancy = new CLabel(groupAnalysis, SWT.NONE);
		GridData gd_tupelNRedundancy = new GridData();
		gd_tupelNRedundancy.horizontalIndent = 30;
		tupelNRedundancy.setLayoutData(gd_tupelNRedundancy);
		tupelNRedundancy.setText(Messages.EntropyUIresults_11);

		labelRedN = new CLabel(groupAnalysis, SWT.NONE);
		labelRedN.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelRedN.setText("0 %"); //$NON-NLS-1$
		labelRedN.setForeground(ColorService.getColor(SWT.COLOR_BLUE));

		conditionalEntropy = new CLabel(groupAnalysis, SWT.NONE);
		conditionalEntropy.setText(Messages.EntropyUIresults_10);

		labelCond = new CLabel(groupAnalysis, SWT.NONE);
		labelCond.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		labelCond.setText("F(n) = 0"); //$NON-NLS-1$
		labelCond.setForeground(ColorService.getColor(SWT.COLOR_BLUE));
	}

	public void printSummary(EntropyCalc eC) {
		Integer actualN = Integer.valueOf(eC.getActualN());
		Integer n = Integer.valueOf(eC.getN());
		Integer diffChars = Integer.valueOf(eC.getMyData().getLengthAlphabet());
		Integer totalChars = Integer.valueOf(eC.getMyData().getLengthFilteredText());
		double signiveau = eC.getSigniveau();
		double[][] entMatrix = eC.getResultMatrix();

		if (actualN.equals(n)) {
			textNoAnalysisStarted.setText(Messages.EntropyUIresults_24 + (actualN + 1) + Messages.EntropyUIresults_25);
		} else {
			textNoAnalysisStarted.setText(
					Messages.EntropyUIresults_26 + twoD.format(signiveau * 100) + Messages.EntropyUIresults_27);
		}

		labelFilename.setText(eC.getEditorname());
		labelDiffchars.setText(diffChars.toString());
		labelAllchars.setText(totalChars.toString());
		labelMaxent.setText(twoD.format(entMatrix[0][4]));
		labelEnt1.setText("G(1) = " + twoD.format(entMatrix[0][6])); //$NON-NLS-1$
		labelRed1.setText(twoD.format(entMatrix[0][8] * 100) + " %"); //$NON-NLS-1$
		labelEntN.setText("G(" + (actualN) + ") = " + twoD.format(entMatrix[actualN - 1][6])); //$NON-NLS-1$ //$NON-NLS-2$
		labelRedN.setText(twoD.format(entMatrix[actualN - 1][8] * 100) + " %"); //$NON-NLS-1$
		labelCond.setText("F(" + actualN + ") = " + twoD.format(entMatrix[actualN - 1][0])); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
