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
package org.jcryptool.crypto.ui.alphabets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.core.util.input.InputVerificationResult;
import org.jcryptool.core.util.input.TextfieldInput;
import org.jcryptool.crypto.ui.alphabets.composite.AtomAlphabet;

public class AlphabetManualInputField extends Composite {
	private Text text;
	private Label lblYouCanEnter;
	private TextfieldInput<AtomAlphabet> alphabetInput;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AlphabetManualInputField(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		{
			text = new Text(this, SWT.BORDER);
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			alphabetInput = new TextfieldInput<AtomAlphabet>() {
				@Override
				public Text getTextfield() {
					return text;
				}
				@Override
				protected InputVerificationResult verifyUserChange() {
					return InputVerificationResult.DEFAULT_RESULT_EVERYTHING_OK;
				}
				@Override
				public AtomAlphabet readContent() {
					char[] chars = AbstractAlphabet.parseAlphaContentFromString(getTextfield().getText());
					return new AtomAlphabet(chars);
				}
				@Override
				protected AtomAlphabet getDefaultContent() {
					return new AtomAlphabet(new char[]{});
				}
				@Override
				public String getName() {
					return "Alphabet input"; //$NON-NLS-1$
				}
				@Override
				public void writeContent(AtomAlphabet content) {
					setTextfieldTextExternal(AbstractAlphabet.alphabetContentAsString(content.getCharacterSet()));
				}
			};
		}
		{
			lblYouCanEnter = new Label(this, SWT.WRAP);
			lblYouCanEnter.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC)); //$NON-NLS-1$
			lblYouCanEnter.setText(Messages.getString("AlphabetManualInputField.2")); //$NON-NLS-1$
			GridData gd_lblYouCanEnter = new GridData(SWT.FILL, SWT.CENTER, true, false);
			lblYouCanEnter.setLayoutData(gd_lblYouCanEnter);
			gd_lblYouCanEnter.widthHint = 100;
		}
	}
	
	public AtomAlphabet getAlphabet() {
		return alphabetInput.getContent();
	}

	public TextfieldInput<AtomAlphabet> getAlphabetInput() {
		return alphabetInput;
	}
	
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void writeCharset(char[] characterSet) {
		if(text != null && !text.isDisposed()) {
			alphabetInput.setTextfieldTextExternal(AbstractAlphabet.alphabetContentAsString(characterSet));
			alphabetInput.synchronizeWithUserSide();
		}
	}

}
