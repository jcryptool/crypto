//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2018, 2020 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.auto.SmoothScroller;





public class SignalEncryptionView extends ViewPart {
	
	// Constants
	public static final String ID = "org.jcryptool.visual.signalencryption";
		
	// Main UI components
	private Composite parent;

	private TabFolder tabFolder;
	private TabItem tbtmOverview;
	private TabItem tbtmDoubleRatchet;
	private SignalEncryptionView mainView;
	private DoubleRatchetView doubleRatchetView;
	
	// Tab-Handling
    private int previousTab = 0;
	private SignalEncryptionViewOverview overViewTabComposite;
	private DoubleRatchetView doubleRatchetTabComposite;
	
	private EncryptionAlgorithm signalEncryptionAlgorithm;
	
	
	public SignalEncryptionView() {
	}
	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		this.mainView = this;
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		// Tab folder for the tabs
		tabFolder = new TabFolder(scrolledComposite, SWT.NONE);
		
		// first tab for the key overview
		tbtmOverview = new TabItem(tabFolder, SWT.NONE);
		tbtmOverview.setText("Allgemein");

		
		// second tab for the Double Ratchet algorithm
		tbtmDoubleRatchet = new TabItem(tabFolder, SWT.NONE);
		tbtmDoubleRatchet.setText("Double Ratchet");
		
		//tab selection listener
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = tabFolder.getSelectionIndex();
				
				tabFolder.setSelection(selection);
				setTab(selection);
			}
		});
		
		//init first tab
        doubleRatchetTabComposite = new DoubleRatchetView(tabFolder, SWT.NONE);
		overViewTabComposite = new SignalEncryptionViewOverview(tabFolder, SWT.NONE, doubleRatchetTabComposite);	

        tbtmOverview.setControl(overViewTabComposite);
		                     
		
        scrolledComposite.setContent(tabFolder);
        scrolledComposite.setMinSize(tabFolder.computeSize(ViewConstants.PLUGIN_WIDTH,ViewConstants.PLUGIN_HEIGTH));
		
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(scrolledComposite);
	}
	

	// sets the selected tab as current content of the view
	// 0 = overviewTab
	// 1 = doubleRatchetTab
	
	public void setTab(int tab) { 
        switch (tab) {
         case 0:

           //overViewTabComposite = new SignalEncryptionViewOverview(tabFolder, SWT.NONE, signalEncryptionState, doubleRatchetTabComposite);
           overViewTabComposite.setParameter();
           overViewTabComposite.textReset();
           tbtmOverview.setControl(overViewTabComposite);
           previousTab = 0;
           break; 
         case 1:
             //doubleRatchetTabComposite = new DoubleRatchetView(tabFolder, SWT.NONE, signalEncryptionState);
             tbtmDoubleRatchet.setControl(doubleRatchetTabComposite);
             previousTab = 1;
        default:
            break;
        }

        tabFolder.setSelection(tab);
        FontService.getHeaderFont();
    }
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	/**
     * resets the view, it's needed by JCrypTool
     */
    public void resetView() {
        AlgorithmState.destroy();
        Control[] children = parent.getChildren();
        for (Control control : children) {
            control.dispose();
        }
        createPartControl(parent);
        parent.layout();
        
    }
    
}
