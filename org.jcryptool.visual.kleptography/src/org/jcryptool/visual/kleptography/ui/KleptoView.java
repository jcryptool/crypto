// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.kleptography.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.auto.LayoutAdvisor;
import org.jcryptool.core.util.ui.auto.SmoothScroller;
import org.jcryptool.visual.kleptography.KleptographyPlugin;
import org.jcryptool.visual.kleptography.algorithm.Kleptography;

/**
 * Graphical driver of the Kleptography visualization.
 * 
 * @author Patrick Vacek
 */
public class KleptoView extends ViewPart {
    private ScrolledComposite scKeys;
    private ScrolledComposite scAttack;

    public Kleptography klepto;
    public RSAKeyView keyView;
    public RSAAttackView attackView;

    private TabFolder tabFolder;
    private TabItem tabKeys;
    private TabItem tabAttacks;

    /**
     * The possible prime generation settings. HONEST = honest (random) prime number generation FIXED = fixed P, honest
     * Q PRF = Pseudo-Random Function PRG = Pseudo-Random Generator SETUP = Secretly Embedded Trapdoor with Universal
     * Protection
     */
    public enum PrimeGenSetting {
        HONEST, FIXED, PRF, PRG, SETUP
    };

    /** The current prime generation setting. */
    public PrimeGenSetting currentSetting;

    /**
     * The current step in the sequence of the key generation/attack. 1. Default/intro. Honest/Fixed: explain PQ;
     * PRF/PRG/SETUP: explain ID/index/seed/attacker keys 2. PRF/PRG/SETUP only. ID/index/seed/attacker keys generated.
     * Explain PQ. 3. PQ generated. Explain N. 4. N calculated. Explain Phi/E. 5. Phi/E generated. Explain D. 6. D
     * calculated. Explain text entry/encryption. 7. Text entered. Only actually occurs after encrypting and then
     * changing the text. 8. Encrypted. Honest/PRF/PRG: explain decryption/attack; Fixed/SETUP: explain save. Only Fixed
     * and SETUP continue further. 9. Decrypted or saved. Fixed: explain second key/text generation; SETUP: explain
     * decrypting P. 10. Fixed: Second PQ generated; SETUP: P decrypted, explain calculating private keys. 11. Fixed:
     * Second N calculated; SETUP: private keys calculated, explain decrypting texts. 12. Fixed: Second Phi/E generated;
     * SETUP: texts decrypted. Only Fixed continues yet further. 13. Second D calculated. Explain second text
     * entry/encryption. 14. Second text entered. See step 7. 15. Second text encrypted. Explain second save. 16. Second
     * text saved. Explain GCD/factorization. 17. P factored. Explain calculating private keys. 18. Private keys
     * calculated. Explain decrypting texts. 19. Texts decrypted.
     */
    public Integer currentStep;
    private Composite parent;

    // Define all the colors at once.
    public static final Color WHITE = ColorService.WHITE;
    public static final Color BACKGROUND_GRAY = ColorService.LIGHTGRAY;
    public static final Color FOREGROUND_GRAY = ColorService.GRAY;
    public static final Color BLACK = ColorService.BLACK;
    public static final Color YELLOW = ColorService.YELLOW;

    @Override
    public void createPartControl(final Composite parent) {
        this.parent = parent;
        klepto = new Kleptography();
        setUpGUI(parent);
        
        /**
         * This is necessary, because this plugin uses the
         * <see>TitleAndDescriptionComposite</see> to display
         * the title and the description of the plugin. 
         * The problem with this way of displaying the title and
         * description is, that it adds a resize listener to 
         * the top scrolledcomposite. On resize it lays out ALL
         * widgets. If there is a widget with much content it 
         * will stretch horizontally. This is not intended.
         */
        LayoutAdvisor.addPreLayoutRootComposite(parent);

        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(parent.getShell(), KleptographyPlugin.PLUGIN_ID + ".kleptoview");
    }

    /**
     * Sets up the entire GUI, including the tab folder.
     * 
     * @param viewParent The parent of the GUI, received from the core application that calls this plugin.
     */
    private void setUpGUI(Composite viewParent) {
        // Start with a tabFolder to hold everything.
        tabFolder = new TabFolder(viewParent, SWT.TOP);

        // Start with a tab and a scrolledComposite and then put the
        // key generation and encryption composite within it.
        tabKeys = new TabItem(tabFolder, SWT.NONE);
        tabKeys.setText(Messages.KleptoView_Keygen);
        scKeys = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
        scKeys.setExpandHorizontal(true);
        scKeys.setExpandVertical(true);

        keyView = new RSAKeyView(scKeys, SWT.None, this);

        scKeys.setContent(keyView);
        scKeys.setMinSize(keyView.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        tabKeys.setControl(scKeys);
        
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(scKeys);

        // Start with a tab and a scrolledComposite and then put the
        // attack composite within it. Note that the tab itself is left
        // uninitialized until the user selects either Fixed P or SETUP.
        // tabAttacks = new TabItem(tabFolder, SWT.NONE);
        // tabAttacks.setText("Attacking Fixed P");
        scAttack = new ScrolledComposite(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
        scAttack.setExpandHorizontal(true);
        scAttack.setExpandVertical(true);

        attackView = new RSAAttackView(scAttack, SWT.None, this);

        scAttack.setContent(attackView);
        scAttack.setMinSize(attackView.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(scAttack);
    }

    @Override
    public void setFocus() {
        // parent.setFocus();
    }

    /**
     * Sets the selected tab. 0 is key generation and encryption; 1 is attack.
     * 
     * @param tab The tab to select.
     */
    public void setTabSelection(int tab) {
        tabFolder.setSelection(tab);
    }

    /**
     * Hides or shows the attack tab. This is no hide tab function, so we have to try to dispose it instead. To make it
     * visible again, it must be recreated anew.
     * 
     * @param isVisible Whether or not the attack tab should be visible.
     */
    public void setAttackTabVisibility(boolean isVisible) {
        try {
            tabAttacks.dispose();
        } catch (NullPointerException e) {
            //
        }
        if (isVisible) {
            tabAttacks = new TabItem(tabFolder, SWT.FILL);
            tabAttacks.setControl(scAttack);
            if (currentSetting == PrimeGenSetting.FIXED) {
                tabAttacks.setText(Messages.KleptoView_FixedP);
            } else if (currentSetting == PrimeGenSetting.SETUP) {
                tabAttacks.setText(Messages.KleptoView_SETUP);
            }
            attackView.updateVisibility();
        }
    }

    /**
     * Reset the plugin.</br>
     * Disposes all widgets and creates them new.
     */
    public void reset() {
        Control[] children = parent.getChildren();
        for (Control control : children) {
            control.dispose();
        }
        createPartControl(parent);
        parent.layout();
    }
}