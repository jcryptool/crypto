// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.elGamal.ui.wizards.wizardpages;

import java.util.Enumeration;
import java.util.HashMap;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.backend.KeyStoreManager;
import org.jcryptool.visual.elGamal.ElGamalData;
import org.jcryptool.visual.elGamal.Messages;

import de.flexiprovider.core.elgamal.ElGamalPrivateKey;

/**
 * Page for loading a Keypair.
 * 
 * @author Michael Gaber
 * @author Thorben Groos
 */
public class LoadKeypairPage extends WizardPage {

    /** constant for the pagename. */
    private static final String PAGENAME = "Load Keypair Page"; //$NON-NLS-1$

    /** constant for the title */
    private static final String TITLE = Messages.LoadKeypairPage_select_keypair;

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
    private final ElGamalData data;

    /** the resulting public alias. */
    private KeyStoreAlias publicAlias;
    
    /** hint for the wrong password */
    private Label pwHint;

    /**
     * Constructor setting pagename, description, data and completion status and initializes the keystore connection
     * 
     * @param data {@link #data}
     */
    public LoadKeypairPage(final ElGamalData data) {
        super(PAGENAME, TITLE, null);
        this.setDescription(Messages.LoadKeypairPage_select_keypair_text);
        initKeystoreConnection();
        this.data = data;
        setPageComplete(false);
    }

    /**
     * initializes the keystore connection and gets a list of all compatible keys
     */
    private void initKeystoreConnection() {
        ksm = KeyStoreManager.getInstance();
        KeyStoreAlias alias;
        for (final Enumeration<String> aliases = ksm.getAliases(); aliases.hasMoreElements();) {
            alias = new KeyStoreAlias(aliases.nextElement());
            if (alias.getClassName().equals(ElGamalPrivateKey.class.getName())) {
                keyStoreItems.put(alias.getContactName() + " - " //$NON-NLS-1$
                        + alias.getKeyLength() + "Bit - " //$NON-NLS-1$
                        + alias.getClassName(), alias);
            }
        }
    }

    public void createControl(final Composite parent) {
        final Composite composite = new Composite(parent, SWT.NONE);
        // do stuff like layout et al
        final GridLayout gl = new GridLayout();
        gl.marginWidth = 50;
        composite.setLayout(gl);
        new Label(composite, SWT.NONE).setText(Messages.LoadKeypairPage_select_keypair_from_list);
        
        combo = new Combo(composite, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        combo.setItems(keyStoreItems.keySet().toArray(new String[keyStoreItems.size()]));
        combo.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(final SelectionEvent e) {
                // will not be called
            }

            public void widgetSelected(final SelectionEvent e) {
                privateAlias = keyStoreItems.get(combo.getText());
                publicAlias = getPublicForPrivate();
                checkComplete();
            }
        });

        //Seperator
        GridData gd_seperator = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_seperator.verticalIndent = 30;
        new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(gd_seperator);

        final Text l = new Text(composite, SWT.WRAP | SWT.MULTI | SWT.READ_ONLY);
        l.setText(Messages.LoadKeypairPage_enter_password);
        GridData gd_l = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gd_l.verticalIndent = 30;
        l.setLayoutData(gd_l);
        
        passfield = new Text(composite, SWT.BORDER | SWT.PASSWORD);
        passfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        passfield.addModifyListener(new ModifyListener() {
            public void modifyText(final ModifyEvent e) {
                checkComplete();
            }
        });
        
        pwHint = new Label(composite, SWT.NONE);
        pwHint.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
        pwHint.setVisible(false);
        pwHint.setText(Messages.LoadKeypairPage_0);
        pwHint.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        //select a key in the combo box
        if (combo.getItemCount() > 0) {
        	combo.select(0);
        	combo.notifyListeners(SWT.Selection, new Event());
        }
        
        setControl(composite);
    }

    /**
     * Sets the visibility for the hint for the password
     * True if it should be displayed. False if not.
     * @param status true, if the passwort is right. false, if it is wrong
     */
    public final void setPasswordHint(boolean status) {
    	pwHint.setVisible(status);
    }
    
    /**
     * gets the matching public entry for a private one.
     * 
     * @return the {@link KeyStoreAlias} for the public key
     */
    protected KeyStoreAlias getPublicForPrivate() {
        Enumeration<String> aliases = ksm.getAliases();
        KeyStoreAlias alias;
        while (aliases != null && aliases.hasMoreElements()) {
            alias = new KeyStoreAlias(aliases.nextElement());
            if (alias.getHashValue().equalsIgnoreCase(privateAlias.getHashValue()) && alias != privateAlias) {
                return alias;
            }
        }
        return null;
    }

    /**
     * checks whether this page is already complete.
     */
    private void checkComplete() {
        final boolean complete = privateAlias != null && !passfield.getText().equals(""); //$NON-NLS-1$
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
