// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.elGamal.ui.wizards.wizardpages;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.elGamal.ElGamalData;
import org.jcryptool.visual.elGamal.Messages;
import org.jcryptool.visual.library.Constants;
import org.jcryptool.visual.library.Lib;

/**
 * page for entering a ciphertext to decrypt.
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class EnterCiphertextPage extends TextWizardPage {

	/** unique pagename to get this page from inside a wizard. */
	private static final String PAGENAME = "Enter Ciphertext Page"; //$NON-NLS-1$

	/** title of this page, displayed in the head of the wizard. */
	private static final String TITLE = Messages.EnterCiphertextPage_enter_ciphertext;

	/** shared data object to save the entries made on this page. */
	private final ElGamalData data;

	/**
	 * Constructor setting up the data object and description.
	 * @param data the data object
	 */
	public EnterCiphertextPage(final ElGamalData data) {
		super(PAGENAME, TITLE, null);
		this.data = data;
		this.setDescription(Messages.EnterCiphertextPage_ciphertext_text);
		this.setPageComplete(false);
	}

	/**
	 * sets up all the UI stuff.
	 * @param parent the parent composite
	 */
	public final void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		// do stuff like layout et al
		GridLayout gl_composite = new GridLayout();
		gl_composite.marginWidth = 50;
		composite.setLayout(gl_composite);
		final Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		label.setText(Messages.EnterCiphertextPage_textentry);
		text = new Text(composite, SWT.BORDER | SWT.WRAP);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		text.addModifyListener(new ModifyListener() {

			public void modifyText(final ModifyEvent e) {
				final String trimmed = ((Text) e.widget).getText().replaceAll(Lib.WHITESPACE, ""); //$NON-NLS-1$
				final boolean leer = trimmed.equals(""); //$NON-NLS-1$
				if (!leer) {
					for (final String s : text.getText().trim().split(" ")) { //$NON-NLS-1$
						if (new BigInteger(s, Constants.HEXBASE).compareTo(data.getModulus()) >= 0) {
							setErrorMessage(Messages.EnterCiphertextPage_error_param_gt_mod);
							setPageComplete(false);
							return;
						}
					}
					setErrorMessage(null);
				}
				setPageComplete(!leer);
			}
		});
		text.addVerifyListener(Lib.getVerifyListener(Lib.HEXDIGIT));

		// fill in old data
		text.setText(data.getCipherText());

		// finish
		setControl(composite);
	}

	/**
	 * getter for the pagename.
	 * @return the pagename
	 */
	public static String getPagename() {
		return PAGENAME;
	}
}
