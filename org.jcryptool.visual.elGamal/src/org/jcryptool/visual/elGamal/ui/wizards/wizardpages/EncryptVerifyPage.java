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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.visual.elGamal.Messages;

/**
 * page to choose whether to use a new key or enter the parameters manually.
 *
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class EncryptVerifyPage extends WizardPage {

    /** unique pagename to get this page from inside a wizard. */
    private static final String PAGENAME = "Encrypt/Verify Page"; //$NON-NLS-1$

    /** title of this page, displayed in the head of the wizard. */
    private static final String TITLE = Messages.EncryptVerifyPage_choose_action;

    /** Button for selecting to create a new key. */
    private Button newPubkeyButton;

    /** Button for selecting to load an existing key. */
    private Button existingPubkeyButton;

    /**
     * Constructor, setting name, title and description.
     */
    public EncryptVerifyPage() {
        super(PAGENAME, TITLE, null);
        this.setDescription(Messages.EncryptVerifyPage_choose_action_text);
    }

    /**
     * sets up all the UI stuff.
     *
     * @param parent the parent composite
     */
    public final void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        // set layout
        GridLayout gl_composite = new GridLayout();
        gl_composite.marginWidth = 50;
        composite.setLayout(gl_composite);
        // add enter Pubkey button
        newPubkeyButton = new Button(composite, SWT.RADIO);
        newPubkeyButton.setText(Messages.EncryptVerifyPage_manual_entry);
        newPubkeyButton.setToolTipText(Messages.EncryptVerifyPage_manual_entry_popup);
        newPubkeyButton.setSelection(true);
        newPubkeyButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        // add existing Pubkey button
        existingPubkeyButton = new Button(composite, SWT.RADIO);
        existingPubkeyButton.setText(Messages.EncryptVerifyPage_load_key);
        existingPubkeyButton.setToolTipText(Messages.EncryptVerifyPage_load_key_popup);
        existingPubkeyButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        // finally set control something
        setControl(composite);
    }

    @Override
    public final IWizardPage getNextPage() {
        if (newPubkeyButton.getSelection()) {
            return this.getWizard().getPage(NewPublicKeyPage.getPagename());
        } else {
            return this.getWizard().getPage(LoadPublicKeyPage.getPagename());
        }
    }

    /**
     * getter for the pagename.
     *
     * @return the pagename
     */
    public static String getPagename() {
        return PAGENAME;
    }

    /**
     * convenience method for checking whether key creation is enabled or not.
     *
     * @return selection status of the corresponding checkbox
     */
    public final boolean wantNewKey() {
        return newPubkeyButton.getSelection();
    }
}
