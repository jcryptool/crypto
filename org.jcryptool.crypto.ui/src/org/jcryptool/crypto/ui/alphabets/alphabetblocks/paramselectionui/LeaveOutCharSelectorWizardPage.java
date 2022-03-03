//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2012, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.ui.alphabets.alphabetblocks.paramselectionui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.crypto.ui.alphabets.alphabetblocks.BlockAlphabet;

public class LeaveOutCharSelectorWizardPage extends WizardPage {
	private Label lblCharacterToExclude;
	private Combo combo;
	private BlockAlphabet alpha;
	private Character selectedChar = null;

	/**
	 * Create the wizard.
	 */
	public LeaveOutCharSelectorWizardPage(BlockAlphabet alpha) {
		super("wizardPage");
		this.alpha = alpha;
		setTitle("Exclude one character from the alphabet block");
		setDescription(String.format("Please select the character to exclude from the %s alphabet block:", alpha.getBlockName()));
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(2, false));
		{
			lblCharacterToExclude = new Label(container, SWT.NONE);
			lblCharacterToExclude.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblCharacterToExclude.setText("Character to exclude: ");
		}
		{
			combo = new Combo(container, SWT.NONE);
			GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
			layoutData.minimumWidth = 50;
			combo.setLayoutData(layoutData);
			fillCombo(alpha);
			if(combo.getItemCount() > 0) {
				selectCharAt(0);
			}
			this.setPageComplete(true);
			combo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectCharAt(combo.getSelectionIndex());
				}
			});
		}
	}

	private void selectCharAt(int i) {
		combo.select(i);
		selectedChar = alpha.getCharacterSet()[combo.getSelectionIndex()];
	}

	private void fillCombo(BlockAlphabet alpha) {
		for(char c: alpha.getCharacterSet()) {
			String displayString = AbstractAlphabet.getPrintableCharRepresentation(c);
			combo.add(displayString);
		}
	}
	
	public char getSelectedChar() {
		return selectedChar;
	}

}
