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
package org.jcryptool.crypto.ui.textloader.ui.wizard.loadtext;

import org.eclipse.jface.wizard.Wizard;
import org.jcryptool.core.operations.algorithm.classic.textmodify.TransformData;
import org.jcryptool.crypto.ui.textloader.ui.ControlHatcher;
import org.jcryptool.crypto.ui.textsource.TextInputWithSource;

public class LoadTextWizard extends Wizard {

	private LoadTextWizardPage firstPage;
	private TranspTextModifyPage page2;
	private boolean finished = false;
	private TextInputWithSource textPageConfig;
	private TransformData transformData;

	public LoadTextWizard() {
		this(null, null);
	}

	public LoadTextWizard(ControlHatcher beforeWizardTextParasiteLabel, ControlHatcher afterWizardTextParasiteLabel) {
		setWindowTitle("Load text");
		firstPage = new LoadTextWizardPage(beforeWizardTextParasiteLabel, afterWizardTextParasiteLabel);
		page2 = new TranspTextModifyPage();
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public final void addPages() {
		addPage(firstPage);
		addPage(page2);
	}

	public TextInputWithSource getTextPageConfig() {
		if (!finished) return firstPage.getPageConfiguration();
		else return textPageConfig;
	}

	public TransformData getTransformData() {
		if (!finished) return page2.getSelectedData();
		else return transformData;
	}

	private void saveResults() {
		this.transformData = getTransformData();
		this.textPageConfig = getTextPageConfig();
	}

	public void setTextPageConfig(TextInputWithSource config) {
		firstPage.setPageConfiguration(config);
	}

	public void setTransformData(TransformData transformData) {
		page2.setSelectedData(transformData);
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public final boolean performFinish() {
		saveResults();
		finished = true;
		return true;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public final boolean performCancel() {
		saveResults();
		finished = true;
		return true;
	}

}
