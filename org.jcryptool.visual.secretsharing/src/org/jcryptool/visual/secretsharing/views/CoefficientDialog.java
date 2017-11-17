// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.secretsharing.views;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

/**
 * @author Oryal Inel
 * @version 1.0
 */
public class CoefficientDialog extends TitleAreaDialog implements Constants {

	private Group compositeArea;
	private Composite coefficientsGroup;
	private ScrolledComposite scrolledComposite;
	private int t;
	private BigInteger modul;
	private BigInteger secret;
	private BigInteger[] coefficient;
	private Spinner[] spinnerSet;
	private Button generateCoefficientsButton;

	/**
	 * Create the dialog
	 * @param parentShell
	 * @param secret
	 * @param modul
	 */
	public CoefficientDialog(Shell parentShell, int t, BigInteger secret, BigInteger[] coefficient, BigInteger modul) {
		super(parentShell);
		this.t = t;
		this.modul = modul;
		this.secret = secret;
		this.coefficient = coefficient;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		compositeArea = new Group(area, SWT.NONE);
		compositeArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		compositeArea.setLocation(0, 0);
		compositeArea.setText(MESSAGE_COEFFICIENTS_GROUP_NAME);
		final GridLayout gd_Layout = new GridLayout();
		compositeArea.setLayout(gd_Layout);

		scrolledComposite = new ScrolledComposite(compositeArea, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		coefficientsGroup = new Composite(scrolledComposite, SWT.NONE);
		coefficientsGroup.setLocation(0, 0);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		coefficientsGroup.setLayout(gridLayout);
		scrolledComposite.setContent(coefficientsGroup);

		generateCoefficientsButton = new Button(area, SWT.NONE);
		generateCoefficientsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*
				 * create random numbers for the coefficients
				 */
				SecureRandom sr = new SecureRandom();
				for (int i = 0; i < coefficient.length - 1; i++) {
					int randomValue = Math.abs(sr.nextInt()) % modul.intValue() + 1;
					spinnerSet[i].setSelection(randomValue);
				}
			}
		});
		generateCoefficientsButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		generateCoefficientsButton.setText(MESSAGE_COEFFICIENTS_GENERATE);
		
		setTitle(MESSAGE_COEFFICIENTS_GROUP_NAME);
		setMessage(MESSAGE_COEFFICIENTS_DIALOG);

		createGroupCoefficient();

//		size = area.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		
		return area;
	}

	/**
	 * method to create the coefficients lines
	 */
	private void createGroupCoefficient() {
		Label tmpLabel;
		Text tmpText;
		Spinner tmpSpinner;
		spinnerSet = new Spinner[t - 1];
		for (int i = 0; i < t; i++) {
			if (i == 0) {
				tmpLabel = new Label(coefficientsGroup, SWT.NONE);
				tmpLabel.setText("a" + convertToSubset(i));

				tmpLabel = new Label(coefficientsGroup, SWT.NONE);
				tmpLabel.setText(":= s");

				tmpText = new Text(coefficientsGroup, SWT.READ_ONLY | SWT.BORDER);
				tmpText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
				tmpText.setText(secret.toString());

			} else {
				tmpLabel = new Label(coefficientsGroup, SWT.NONE);
				tmpLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
				tmpLabel.setText("a" + convertToSubset(i));

				tmpSpinner = new Spinner(coefficientsGroup, SWT.BORDER);
				GridData gd_aSpinner = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
				tmpSpinner.setLayoutData(gd_aSpinner);
				tmpSpinner.setMaximum(modul.intValue() - 1);
				tmpSpinner.setMinimum(0);
				tmpSpinner.addDisposeListener(new DisposeListener() {
					public void widgetDisposed(final DisposeEvent e) {
						if (coefficient[0] == null) {
							for (int j = 0; j < coefficient.length; j++) {
								if (j == 0) {
									coefficient[j] = new BigInteger(secret.toString());
								} else {
									coefficient[j] = new BigInteger(String.valueOf(spinnerSet[j - 1].getSelection()));
								}
							}
						}
					}
				});
				spinnerSet[i - 1] = tmpSpinner;

			}

		}
		coefficientsGroup.pack();
		if (t > 6) {
			scrolledComposite.setExpandVertical(false);
		}
	}

	/**
	 * Converts the id value into subscript
	 * @param id
	 * @return a string converted to subscript
	 */
	private String convertToSubset(int id) {
		char[] data = String.valueOf(id).toCharArray();
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < data.length; i++) {
			if (data[i] == '0')
				result.append(uZero);
			else if (data[i] == '1')
				result.append(uOne);
			else if (data[i] == '2')
				result.append(uTwo);
			else if (data[i] == '3')
				result.append(uThree);
			else if (data[i] == '4')
				result.append(uFour);
			else if (data[i] == '5')
				result.append(uFive);
			else if (data[i] == '6')
				result.append(uSix);
			else if (data[i] == '7')
				result.append(uSeven);
			else if (data[i] == '8')
				result.append(uEight);
			else if (data[i] == '9')
				result.append(uNine);
		}

		return result.toString();
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(380, 395);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(MESSAGE_COEFFICIENTS_TITLE);
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

}
