// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2012, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.crypto.ui.textmodify.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.jcryptool.core.operations.algorithm.classic.textmodify.TransformData;
import org.jcryptool.crypto.ui.alphabets.AlphabetSelectorComposite.Mode;

/**
 * The wizard for the transformation settings
 *
 * @author SLeischnig
 *
 */
public class ModifyWizard extends Wizard {
    /** The wizard page. */
    private ModifyWizardPage page;
    private TransformData wizardDataToSet = new TransformData();
    
    private Mode alphabetSelectionMode = Mode.SINGLE_COMBO_BOX_WITH_CUSTOM_ALPHABETS;

    public void setAlphabetSelectionMode(Mode alphabetSelectionMode) {
		this.alphabetSelectionMode = alphabetSelectionMode;
	}

	/**
     * Creates a new instance of CaesarWizard.
     *
     * @param alphabets the alphabets to be displayed in the alphabet box
     * @param defaultAlphabet the name of the default alphabet (the selected entry in the alphabet
     *        combo box) - if the alphabet is not found, the first Alphabet is used
     */
    public ModifyWizard() {
        setWindowTitle(Messages.ModifyWizard_textmodifyoptions);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
	public final void addPages() {
        page = new ModifyWizardPage();
        page.setAlphabetSelectionMode(alphabetSelectionMode);
        addPage(page);
        page.setPredefinedData(wizardDataToSet);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public final boolean performFinish() {
        return true;
    }

    public final TransformData getWizardData() {
        return page.getSelectedData();
    }

    public final void setPredefinedData(final TransformData data) {
        wizardDataToSet = data;
    }

}
