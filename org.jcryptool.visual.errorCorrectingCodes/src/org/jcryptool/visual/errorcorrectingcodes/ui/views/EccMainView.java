// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.errorcorrectingcodes.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.errorcorrectingcodes.EccPlugin;
import org.jcryptool.visual.errorcorrectingcodes.ui.Messages;

public class EccMainView extends ViewPart {
	private static final int DEFAULT_TAB = 0;
//	private Composite parent;
	private ScrolledComposite sc;

	private CTabFolder tabFolder;
	private CTabItem tabGeneral;
	private CTabItem tabHamming;
	private CTabItem tabMcEliece;
	private GeneralEccView generalEcc;
	private HammingCryptoView hammingView;
	private McElieceView mcEliece;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent) {

		tabFolder = new CTabFolder(parent, SWT.NONE);
		tabFolder.setSelectionBackground(ColorService.LIGHTGRAY);

		// McEliece Algorithm view
		sc = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		mcEliece = new McElieceView(sc, SWT.NONE);
		tabMcEliece = new CTabItem(tabFolder, SWT.NONE);
		tabMcEliece.setText(Messages.EccMainView_ztabMcElieceText);
		sc.setContent(mcEliece);
		sc.setMinSize(mcEliece.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tabMcEliece.setControl(sc);

		// Hamming McEliece view
		sc = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		hammingView = new HammingCryptoView(sc, SWT.NONE);
		tabHamming = new CTabItem(tabFolder, SWT.NONE);
		tabHamming.setText(Messages.EccMainView_ztabHammingText);
		sc.setContent(hammingView);
		sc.setMinSize(hammingView.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tabHamming.setControl(sc);

		// Error correcting coe view
		sc = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		generalEcc = new GeneralEccView(sc, SWT.NONE);
		tabGeneral = new CTabItem(tabFolder, SWT.NONE);
		tabGeneral.setText(Messages.EccMainView_ztabGeneralText);
		sc.setContent(generalEcc);
		sc.setMinSize(generalEcc.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tabGeneral.setControl(sc);
		tabFolder.setSelection(DEFAULT_TAB);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				"org.jcryptool.visual.errorcorrectingcodes.mcElieceHelp"); //$NON-NLS-1$
	}

	@Override
	public void setFocus() {
		tabFolder.getSelection().getControl().setFocus();
	}

	/**
	 * Reset each tab to its inital state.
	 */
	public void resetView() {
		
		switch (tabFolder.getSelectionIndex()) {
		case 0:
			Control[] children = mcEliece.getChildren();
			for (Control control : children) {
				control.dispose();
			}
			mcEliece.dispose();
			mcEliece = new McElieceView(sc, SWT.NONE);
			sc.setContent(mcEliece);
			sc.setMinSize(mcEliece.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			tabMcEliece.setControl(sc);
			break;
		case 1:
			hammingView.initView();
			break;
		case 2:
			generalEcc.initView();
			break;

		default:
			LogUtil.logError(EccPlugin.PLUGIN_ID, "Something went wrong when resetting the McElice-Plugin");
			break;
		}
		

	}

}
