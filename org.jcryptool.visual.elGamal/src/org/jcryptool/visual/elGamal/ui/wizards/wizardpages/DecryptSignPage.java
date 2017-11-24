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
 * page to choose whether to use a new key or enter the parameters manually.
 *
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class DecryptSignPage extends WizardPage {

    /** unique pagename to get this page from inside a wizard. */
    private static final String PAGENAME = "Decrypt/Sign Page"; //$NON-NLS-1$

    /** title of this page, displayed in the head of the wizard. */
    private static final String TITLE = Messages.DecryptSignPage_choose_action;

    /** Button for selecting to create a new key. */
    private Button newKeypairButton;

    /** Button for selecting to load an existing key. */
    private Button existingKeypairButton;

    /** selection listener that updates the buttons. */
    private final SelectionListener sl = new SelectionAdapter() {

        public void widgetSelected(SelectionEvent e) {
            getContainer().updateButtons();
        }
    };

    /**
     * Constructor, setting name, title and description.
     */
    public DecryptSignPage() {
        super(PAGENAME, TITLE, null);
        this.setDescription(Messages.DecryptSignPage_choose_action_text);
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
        // add new Keypair button
        newKeypairButton = new Button(composite, SWT.RADIO);
        newKeypairButton.setText(Messages.DecryptSignPage_new_keypair);
        newKeypairButton.setToolTipText(Messages.DecryptSignPage_new_keypair_popup);
        newKeypairButton.setSelection(true);
        newKeypairButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        newKeypairButton.addSelectionListener(sl);
        // add existing Keypair button
        existingKeypairButton = new Button(composite, SWT.RADIO);
        existingKeypairButton.setText(Messages.DecryptSignPage_existing_keypair);
        existingKeypairButton.setToolTipText(Messages.DecryptSignPage_existing_keypair_popup);
        existingKeypairButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        existingKeypairButton.addSelectionListener(sl);
        // finally set control something
        setControl(composite);
    }

    @Override
    public final IWizardPage getNextPage() {
        if (newKeypairButton.getSelection()) {
            return getWizard().getPage(NewKeypairPage.getPagename());
        } else {
            return getWizard().getPage(LoadKeypairPage.getPagename());
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
        return newKeypairButton.getSelection();
    }
}
