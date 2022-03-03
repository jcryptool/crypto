// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.entropy.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;

public class EntropyUI extends Composite {
	private CTabFolder cMainTabFolder;
	private CTabItem cTabConfig;
	private CTabItem cTabResult;
	private CTabItem cTabDetails;
	private EntropyUIresults compositeResults;
	private EntropyUIconfig compositeConfig;
	private EntropyUItable compositeTable;


	public CTabFolder getCMainTabFolder() {
		return cMainTabFolder;
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		EntropyUI inst = new EntropyUI(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public EntropyUI(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	/**
	 * Creates the GUI.
	 */
	private void initGUI() {
		Composite mainComp = this;
		mainComp.setLayout(new GridLayout(1, false));
		mainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		TitleAndDescriptionComposite titleAndDescription = new TitleAndDescriptionComposite(mainComp);
		titleAndDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		titleAndDescription.setTitle(Messages.EntropyUI_title);
		titleAndDescription.setDescription(Messages.EntropyUI_desc);

		cMainTabFolder = new CTabFolder(mainComp, SWT.BORDER);
		cMainTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		cTabConfig = new CTabItem(cMainTabFolder, SWT.NONE);
		cTabConfig.setText(Messages.EntropyUI_0);

		compositeConfig = new EntropyUIconfig(cMainTabFolder, SWT.NONE);
		cTabConfig.setControl(compositeConfig);

		cTabResult = new CTabItem(cMainTabFolder, SWT.NONE);
		cTabResult.setText(Messages.EntropyUI_1);

		compositeResults = new EntropyUIresults(cMainTabFolder, SWT.NONE);
		cTabResult.setControl(compositeResults);

		cTabDetails = new CTabItem(cMainTabFolder, SWT.NONE);
		cTabDetails.setText(Messages.EntropyUI_2);

		compositeTable = new EntropyUItable(cMainTabFolder, SWT.NONE);
		cTabDetails.setControl(compositeTable);

		cMainTabFolder.setSelection(0);

		this.layout();
		pack();
		compositeConfig.setEntropyUIpointer(this);
	}

	public EntropyUI getThis() {
		return this;
	}

	public EntropyUIresults getCompositeResults() {
		return compositeResults;
	}

	public EntropyUIconfig getCompositeConfig() {
		return compositeConfig;
	}

	public EntropyUItable getCompositeTable() {
		return compositeTable;
	}

}
