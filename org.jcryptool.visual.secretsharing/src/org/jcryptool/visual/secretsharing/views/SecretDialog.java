// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.secretsharing.views;

import java.math.BigInteger;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Oryal Inel
 * @version
 */
public class SecretDialog extends TitleAreaDialog implements Constants {

	private StyledText secretText;
	private Text modulText;
	private String[] result;
	private StyleRange style;
	private Button okButton;

	/**
	 * Create the dialog
	 * @param parentShell
	 * @param result
	 * @param secret
	 */
	public SecretDialog(Shell parentShell, BigInteger secret, String[] result) {
		super(parentShell);
		this.result = result;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label modulLabel = new Label(container, SWT.NONE);
		modulLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		modulLabel.setText(Messages.SSSConstants_Dialog_Modul_Info);

		modulText = new Text(container, SWT.BORDER);
		final GridData gd_modulText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		modulText.setLayoutData(gd_modulText);
		modulText.setText(result[0]);
		modulText.setEnabled(false);

		final Label secretLabel = new Label(container, SWT.NONE);
		secretLabel.setText(Messages.SSSConstants_Dialog_Secret_Info);

		secretText = new StyledText(container, SWT.BORDER);
		secretText.addVerifyKeyListener(new VerifyKeyListener() {
			public void verifyKey(VerifyEvent e) {
				/*
                 * keyCode == 8 is BACKSPACE and keyCode == 48 is ZERO and keyCode == 127 is DEL
                 */
                if (Character.toString(e.character).matches("[0-9]") || e.keyCode == 8 || e.keyCode == 127) {
                    if (secretText.getText().length() == 0 && Character.toString(e.character).compareTo("0") == 0) {
                        e.doit = false;
                    } else if (secretText.getSelection().x == 0 && e.keyCode == 48) {
                        e.doit = false;
                    } else {
                        e.doit = true;
                    }
                } else {
                    e.doit = false;
                }
			}	
		});
		secretText.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(final ExtendedModifyEvent event) {
				style = new StyleRange();
				style.start = 0;
				style.length = secretText.getText().length();
				style.foreground = BLACK;
				style.fontStyle = SWT.BOLD;
				secretText.setStyleRange(style);

				if (okButton != null) {
					okButton.setEnabled(false);
				}
				verify();
			}
		});
		secretText.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(final DisposeEvent e) {
				result[1] = secretText.getText();
			}
		});

		final GridData gd_secretText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		secretText.setLayoutData(gd_secretText);
		secretText.setText(result[1]);

		style = new StyleRange();
		style.start = 0;
		style.length = secretText.getText().length();
		style.foreground = RED;
		style.fontStyle = SWT.BOLD;
		secretText.setStyleRange(style);

//		final Button checkInputButton = new Button(container, SWT.NONE);
//		checkInputButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(final SelectionEvent e) {
//				verify();
//			}
//		});
//		final GridData gd_checkInputButton = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
//		checkInputButton.setLayoutData(gd_checkInputButton);
//		checkInputButton.setText(Messages.SSSConstants_Dialog_Verify_Input);

		setMessage(Messages.SSSConstants_Dialog_Secret_Message);
		setTitle(Messages.SSSConstants_Dialog_Secret_Header);
		
		return area;
	}

	protected void verify() {
		// TODO Auto-generated method stub
		String text = secretText.getText();
		style = new StyleRange();
		style.start = 0;
		style.length = secretText.getText().length();

		if (text.matches("[0-9]*")) {

			if (new BigInteger(secretText.getText()).compareTo(new BigInteger(modulText.getText())) <= 0) {
				style = new StyleRange();
				style.start = 0;
				style.length = secretText.getText().length();
				style.foreground = GREEN;
				style.fontStyle = SWT.BOLD;
				secretText.setStyleRange(style);
				okButton.setEnabled(true);
			} else {
				style = new StyleRange();
				style.start = 0;
				style.length = secretText.getText().length();
				style.foreground = RED;
				style.fontStyle = SWT.BOLD;
				secretText.setStyleRange(style);
				okButton.setEnabled(false);
			}
		} else {
			style.foreground = RED;
			style.fontStyle = SWT.BOLD;
			secretText.setStyleRange(style);
		}
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.SSSConstants_Dialog_Secret);
	}

}
