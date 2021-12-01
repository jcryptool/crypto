// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2014, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.sigVerification.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.visual.sigVerification.algorithm.Input;

/**
 * This class contains loads the composite for the signature wizard.
 * 
 * @author Wilfing
 */
public class SignatureWizardPage extends WizardPage {
    private SignatureComposite composite;
    private int method;
    Input input;

    public SignatureWizardPage(String pageName, int m, Input input) {
        super(Messages.SignatureWizard_header);
        this.input = input;
        this.method = m;
        setDescription(Messages.SignatureWizard_header);
        setTitle(Messages.SignatureWizard_WindowTitle);
    }

    @Override
	public void createControl(Composite parent) {
    	composite = new SignatureComposite(parent, NONE, method, this, input);
        setControl(composite);
        setPageComplete(true);
    }

    /**
     * @return the grpSignatures
     */
    public Group getGrpSignatures() {
        return composite.getgrpSignatures();
    }
    
    /**
     * @return the KeyStoreAlias
     */
    public KeyStoreAlias getAlias() {
        return composite.getAlias();
    }
}
