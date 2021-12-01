// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.viterbi.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.analysis.viterbi.ViterbiPlugin;
import org.jcryptool.analysis.viterbi.algorithm.Combination;
import org.jcryptool.core.util.ui.auto.SmoothScroller;

/**
 * This class provides the basic structure for the gui. It creates the tabs.
 * 
 * @author Georg Chalupar, Niederwieser Martin, Scheuchenpflug Simon
 */
public class ViterbiView extends ViewPart {
	public ViterbiView() {
	}
	private XORComposite xorComposite;
	private ViterbiComposite viterbiComposite;
	public TabFolder tf;
	private Composite parent;
	public DetailsComposite detailsComposite;

	/**
	 * creates the two tabs
	 */
	@Override
	public final void createPartControl(final Composite parent) {
		this.parent = parent;

		tf = new TabFolder(parent, SWT.TOP);

		// XOR Tab
		TabItem ti = new TabItem(tf, SWT.NONE);
		ti.setText(Messages.XORComposite_tab_title);
		ScrolledComposite sc = new ScrolledComposite(tf, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		xorComposite = new XORComposite(sc, SWT.NONE, this);
		sc.setContent(xorComposite);
		ti.setControl(sc);
		
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT-H_SCROLL.
		SmoothScroller.scrollSmooth(sc);

		
		// Viterbi Tab
		ti = new TabItem(tf, SWT.NONE);
		ti.setText(Messages.ViterbiComposite_tab_title);
		sc = new ScrolledComposite(tf, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		viterbiComposite = new ViterbiComposite(sc, SWT.NONE, this);
		sc.setContent(viterbiComposite);
		ti.setControl(sc);
		
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT-H_SCROLL.
		SmoothScroller.scrollSmooth(sc);

		
		// Viterbi Details Tab
		ti = new TabItem(tf, SWT.NONE);
		ti.setText(Messages.ViterbiView_0);
		sc = new ScrolledComposite(tf, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		detailsComposite = new DetailsComposite(sc, SWT.NONE);
		sc.setContent(detailsComposite);
		ti.setControl(sc);
		
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT-H_SCROLL.
		SmoothScroller.scrollSmooth(sc);

		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(parent, ViterbiPlugin.PLUGIN_ID + ".view"); //$NON-NLS-1$
	}

	@Override
	public void setFocus() {
		parent.setFocus();
	}

	/**
	 * This class changes the active Tab from the XOR Tab to the Viterbi Tab. It
	 * does not work the other way. Informs the Viterbi-Tab about the ciphertext
	 * and the combination used to create the cipher text
	 * 
	 * @param ciphertext
	 *            the ciphertext that has been entered, and should be displayed
	 *            in the other tab
	 * @param combi
	 *            the type of combination that has been chosen by the user
	 * 
	 */
	public void changeTab(String ciphertext, Combination combi) {
		viterbiComposite.setCipherText(ciphertext);
		tf.setSelection(1);
		viterbiComposite.setCombination(combi);
	}

	public void reset() {
		Control[] children = parent.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		createPartControl(parent);
		parent.layout();
	}
}
