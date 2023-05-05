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

import static org.jcryptool.visual.signalencryption.util.UiUtils.onSelection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.auto.SmoothScroller;

/** [Entry-point] Creates the plug-in and all its UI components */
public class SignalEncryptionView extends ViewPart {

    public static final String ID = "org.jcryptool.visual.signalencryption";
    public static final int OVERVIEW_ID = 0;
    public static final int DOUBLE_RATCHET_ID = 1;

    private Composite parent;

    private TabFolder tabFolder;
    private TabItem tbtmOverview;
    private TabItem tbtmDoubleRatchet;

    /** plug-in's scroll-able container */
    private ScrolledComposite scrolledComposite;
    /** First tab's view (overview, general) */
    private OverviewView overviewView;
    /** Second tab's view (double-ratchet) */
    private DoubleRatchetView doubleRatchetView;

    /** [Entry-point] Creates the plug-in and all its UI components */
    @Override
    public void createPartControl(Composite parent) {
        this.parent = parent;
        scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);

        tabFolder = new TabFolder(scrolledComposite, SWT.NONE);

        tbtmOverview = new TabItem(tabFolder, SWT.NONE);
        tbtmOverview.setText(Messages.SignalEncryption_TabTitleOverView);
        tbtmDoubleRatchet = new TabItem(tabFolder, SWT.NONE);
        tbtmDoubleRatchet.setText(Messages.SignalEncryption_TabTitleRatchetView);

        tabFolder.addSelectionListener(onSelection((selectionEvent) -> {
            setTab(tabFolder.getSelectionIndex());
        }));

        overviewView = new OverviewView(tabFolder, SWT.NONE, this);
        doubleRatchetView = new DoubleRatchetView(tabFolder, SWT.NONE);
        tbtmOverview.setControl(overviewView);

        scrolledComposite.setContent(tabFolder);
        scrolledComposite.setMinSize(tabFolder.computeSize(ViewConstants.PLUGIN_WIDTH, ViewConstants.PLUGIN_HEIGTH));

        // This makes the ScrolledComposite scrolling, when the mouse
        // is on a Text with one or more of the following tags: SWT.READ_ONLY,
        // SWT.V_SCROLL or SWT.H_SCROLL.
        SmoothScroller.scrollSmooth(scrolledComposite);
    }

    public void setTab(int tabIndex) {
        setTab(Tab.values()[tabIndex]);
    }

    public void setTab(Tab tab) {
        switch (tab) {
        case OVERVIEW:
            overviewView.updateValues();
            tbtmOverview.setControl(overviewView);
            break;
        case DOUBLE_RATCHET:
            tbtmDoubleRatchet.setControl(doubleRatchetView);
        default:
            break;
        }

        tabFolder.setSelection(tab.getIndex());
        FontService.getHeaderFont();
    }

    @Override
    public void setFocus() {
        scrolledComposite.setFocus();
    }

    /** Resets the whole plug-in */
    public void resetView() {
        AlgorithmState.destroy();
        Control[] children = parent.getChildren();
        for (Control control : children) {
            control.dispose();
        }
        createPartControl(parent);
        parent.layout();

    }

    /** Resets the {@link DoubleRatchetView} tab */
    public void resetDoubleRatchetView() {
        doubleRatchetView.resetView();
    }

    public enum Tab {
        OVERVIEW {
            @Override
            int getIndex() {
                return OVERVIEW.ordinal();
            }
        },
        DOUBLE_RATCHET {
            @Override
            int getIndex() {
                return DOUBLE_RATCHET.ordinal();
            }
        };

        abstract int getIndex();
    }
}
