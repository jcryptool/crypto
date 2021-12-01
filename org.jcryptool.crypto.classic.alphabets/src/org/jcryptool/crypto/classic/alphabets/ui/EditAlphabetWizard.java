//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2011, 2021 JCrypTool Team and Contributors
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.classic.alphabets.ui;

import org.eclipse.jface.wizard.Wizard;
import org.jcryptool.core.operations.alphabets.AbstractAlphabet;

/**
 * The wizard for editing a new alphabet.
 * 
 * @author t-kern
 *
 */
public class EditAlphabetWizard extends Wizard {

	/** The wizard page */
	private EditAlphabetWizardPage page;
	
	/** The alphabet that will be modified */
	private AbstractAlphabet alphabet;
	
	/**
	 * Creates a new instance of AddAlphabetWizard.
	 */
	public EditAlphabetWizard(AbstractAlphabet alphabet) {
		setWindowTitle(Messages.getString("EditAlphabetWizard.0")); //$NON-NLS-1$
		this.alphabet = alphabet;
	}
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new EditAlphabetWizardPage(alphabet);
		addPage(page);
	}
	
	/**
	 * Returns the name of the new alphabet.
	 * 
	 * @return	The name of the new alphabet
	 */
	public String getAlphabetName() {
		return page.getAlphabetName();
	}
	
	/**
	 * Returns the charset of the new alphabet.
	 * 
	 * @return	The charset of the new alphabet
	 */
	public String getAlphabetCharset() {
		return page.getAlphabetCharset();
	}
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

}
