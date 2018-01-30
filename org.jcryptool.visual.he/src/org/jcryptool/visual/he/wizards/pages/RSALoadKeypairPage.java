// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.he.wizards.pages;

import java.util.Enumeration;
import java.util.HashMap;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.backend.KeyStoreManager;
import org.jcryptool.visual.he.Messages;
import org.jcryptool.visual.he.rsa.RSAData;

import de.flexiprovider.core.rsa.RSAPrivateCrtKey;

/**
 * Page for loading a Keypair.
 * 
 * @author Michael Gaber
 */
public class RSALoadKeypairPage extends WizardPage {

    /** constant for the pagename. */
    private static final String PAGENAME = "Load Keypair Page"; //$NON-NLS-1$

    /** constant for the title */
    private static final String TITLE = Messages.RSALoadKeypairPage_select_keypair;

    /** Keystore manager for accessing the keystore. */
    private KeyStoreManager ksm;

    /** Hashmap for the Keystoreitems. */
    private final HashMap<String, KeyStoreAlias> keyStoreItems = new HashMap<String, KeyStoreAlias>();

    /** Combo box for storing the Aliases. */
    private Combo combo;

    /** Field for entering the password. */
    private Text passfield;

    /** the resulting private alias. */
    private KeyStoreAlias privateAlias;

    /** shared data object. */
    private final RSAData data;

    /** the resulting public alias. */
    private KeyStoreAlias publicAlias;

    /**
     * Constructor for a new page. sets the data object, description and inits the keystore connection
     * 
     * @param data the data object
     */
    public RSALoadKeypairPage(RSAData data) {
        super(PAGENAME, TITLE, null);
        this.setDescription(Messages.RSALoadKeypairPage_pleae_select_keypair);
        initKeystoreConnection();
        this.data = data;
        setPageComplete(false);
    }

    /**
     * initializes the keystore connection and adds all matching keys to the key selection list
     */
    private void initKeystoreConnection() {
        ksm = KeyStoreManager.getInstance();
        KeyStoreAlias alias;
        for (Enumeration<String> aliases = ksm.getAliases(); aliases.hasMoreElements();) {
            alias = new KeyStoreAlias(aliases.nextElement());
            if (alias.getClassName().equals(RSAPrivateCrtKey.class.getName())) {
                keyStoreItems.put(alias.getContactName() + " - " + alias.getKeyLength() + "Bit - " //$NON-NLS-1$ //$NON-NLS-2$
                        + alias.getClassName(), alias);
            }
        }
    }

    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        // do stuff like layout et al
        composite.setLayout(new GridLayout());
        new Label(composite, SWT.NONE).setText(Messages.RSALoadKeypairPage_select_keypair_from_list);
        combo = new Combo(composite, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        combo.setItems(keyStoreItems.keySet().toArray(new String[keyStoreItems.size()]));
        combo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                privateAlias = keyStoreItems.get(combo.getText());
                publicAlias = getPublicForPrivate();
                checkComplete();
            }
        });
        combo.select(0);
        combo.setFocus();

        new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));

        Text l = new Text(composite, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY);
        l.setText(Messages.RSALoadKeypairPage_enter_password);
        l.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        passfield = new Text(composite, SWT.BORDER | SWT.PASSWORD);
        passfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        passfield.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                checkComplete();
            }
        });
        setControl(composite);
    }

    /**
     * gets the matching public entry for a private one.
     * 
     * @return the {@link KeyStoreAlias} for the public key
     */
    private KeyStoreAlias getPublicForPrivate() {
        Enumeration<String> aliases = ksm.getAliases();
        KeyStoreAlias alias;
        while (aliases != null && aliases.hasMoreElements()) {
            alias = new KeyStoreAlias(aliases.nextElement());
            if (alias.getHashValue().equalsIgnoreCase(privateAlias.getHashValue()) && alias != privateAlias)
                return alias;
        }
        return null;
    }

    /**
     * checks whether this page is already complete.
     */
    private void checkComplete() {
        boolean complete = privateAlias != null && !passfield.getText().equals(""); //$NON-NLS-1$
        if (complete) {
            data.setPrivateAlias(privateAlias);
            data.setPublicAlias(publicAlias);
            data.setContactName(privateAlias.getContactName());
            data.setPassword(passfield.getText());
        }
        setPageComplete(complete);
    }

    /**
     * getter for the pagename.
     * 
     * @return the pagename
     */
    public static String getPagename() {
        return PAGENAME;
    }

    @Override
    public final IWizardPage getNextPage() {
        return null;
    }
}
