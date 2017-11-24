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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.visual.elGamal.Messages;

/**
 * Page for choosing whether to create a public key or a private keypair.
 *
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class ChooseKeytypePage extends WizardPage {

    /** title of this page. */
    private static final String TITLE = Messages.ChooseKeytypePage_choose_keytype;

    /** name of this page. */
    private static final String PAGENAME = "Choose Keytype Page"; //$NON-NLS-1$

    /** the button for selecting to create a keypair. */
    private Button keypairButton;

    /**
     * the selection listener which updates the buttons when changing from keypair to pubkey and vice versa.
     */
    private final SelectionListener sl = new SelectionAdapter() {

        public void widgetSelected(final SelectionEvent e) {
            getContainer().updateButtons();
        }
    };

    /**
     * Constructor does nothing special.
     */
    public ChooseKeytypePage() {
        super(getPagename(), TITLE, null);
        setDescription(Messages.ChooseKeytypePage_choose_keytype_text);
    }

    /**
     * sets up all the UI stuff.
     *
     * @param parent the parent composite.
     */
    public final void createControl(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout gl_composite = new GridLayout();
        gl_composite.marginWidth = 50;
        composite.setLayout(gl_composite);

        keypairButton = new Button(composite, SWT.RADIO);
        keypairButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        keypairButton.setText(Messages.ChooseKeytypePage_new_keypair);
        keypairButton.setToolTipText(Messages.ChooseKeytypePage_new_keypair_popup);
        keypairButton.addSelectionListener(sl);

        final Button pubkeyButton = new Button(composite, SWT.RADIO);
        pubkeyButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
        pubkeyButton.setText(Messages.ChooseKeytypePage_new_pubkey);
        pubkeyButton.setToolTipText(Messages.ChooseKeytypePage_new_pubkey_popup);
        pubkeyButton.addSelectionListener(sl);

        setControl(composite);
    }

    @Override
    public final IWizardPage getNextPage() {
        if (keypairButton.getSelection()) {
            return getWizard().getPage(NewKeypairPage.getPagename());
        } else {
            return getWizard().getPage(NewPublicKeyPage.getPagename());
        }
    }

    /**
     * getter for the page name
     *
     * @return the pagename
     */
    public static String getPagename() {
        return PAGENAME;
    }

    /**
     * getter for the selection status of the keypair button.
     *
     * @return whether the user wants a keypair
     */
    public final boolean keypair() {
        return keypairButton.getSelection();
    }
}
