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
package org.jcryptool.crypto.ui.textblockloader;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.core.util.input.AbstractUIInput;

public class TANLNumberLoaderPage extends WizardPage {

	private int maxNumber;
	private CompositeNumberInput compNumberInput;

	/**
	 * Create the wizard.
	 */
	public TANLNumberLoaderPage(int maxNumber) {
		super(Messages.TANLNumberLoaderPage_wtitle);
		this.maxNumber = maxNumber;
		setTitle(Messages.TANLNumberLoaderPage_wtitle);
		setDescription(Messages.TANLNumberLoaderPage_wdescr);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		
		this.compNumberInput = new CompositeNumberInput(container, SWT.NONE, maxNumber);
		GridData compNumberInputLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		compNumberInputLayoutData.heightHint = 200;
		this.compNumberInput.setLayoutData(compNumberInputLayoutData);
		
		setControl(container);
		
		getNumberInput().addObserver(new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				getWizard().getContainer().updateButtons();
			}
		});
	}

	
	public AbstractUIInput<List<Integer>> getNumberInput() {
		return compNumberInput.getNumberInput();
	}
	
	public List<Integer> getNumbers() {
		return getNumberInput().getContent();
	}
}
