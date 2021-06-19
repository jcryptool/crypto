//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2013, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.visual.jctca;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.images.ImageService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.auto.SmoothScroller;
import org.jcryptool.visual.jctca.listeners.TabItemListener;
import org.jcryptool.visual.jctca.tabs.CertificationTab;
import org.jcryptool.visual.jctca.tabs.RegistrationTab;
import org.jcryptool.visual.jctca.tabs.SecondUserTab;
import org.jcryptool.visual.jctca.tabs.UserTab;


/**
 * 
 * This class implements the Certificate Authority visual for the JCrypTool.
 * 
 * @author Marco Macala, Kerstin Reisinger
 * 
 */

public class JCTCA_Visual extends ViewPart {

	private TitleAndDescriptionComposite titleAndDescription;
    private Composite composite;
    private Composite comp_image;
    private GridLayout gl_comp_image;
    private Composite comp_center;
    private GridLayout gl_comp_center;
    private TabFolder tabFolder;
    private ScrolledComposite root;
    private Label lbl_img;
    private Image currentImage;
	private StyledText stl_explain;

    @Override
    public void createPartControl(Composite parent) {
        root = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        composite = new Composite(root, SWT.NONE);
        root.setContent(composite);
        root.setExpandHorizontal(true);
        root.setExpandVertical(true);

        composite.setLayout(new GridLayout(1, false));

        titleAndDescription = new TitleAndDescriptionComposite(composite);
        titleAndDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        titleAndDescription.setTitle(Messages.JCTCA_Visual_Plugin_Headline);
        titleAndDescription.setDescription(Messages.JCTCA_Visual_archpic_create_explain);

        showArchitecture();
        
		// This makes the ScrolledComposite scrolling, when the mouse 
		// is on a Text with one or more of the following tags: SWT.READ_ONLY,
		// SWT.V_SCROLL or SWT.H_SCROLL.
		SmoothScroller.scrollSmooth(root);

    }

    /**
     * 
     * Displays the architecture pictures of jct-ca</br>
     * The start page of the plugin.
     * 
     */
    public void showArchitecture() {
        comp_center = new Composite(composite, SWT.NONE);
        gl_comp_center = new GridLayout(1, false);
        gl_comp_center.marginHeight = 0;
        gl_comp_center.marginWidth = 0;
        comp_center.setLayout(gl_comp_center);
        comp_center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Composite comp_buttons = new Composite(comp_center, SWT.NONE);
        GridLayout gl_comp_buttons = new GridLayout(4, false);
        gl_comp_buttons.marginHeight = 0;
        gl_comp_buttons.marginWidth = 0;
        comp_buttons.setLayout(gl_comp_buttons);
        comp_buttons.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
        
        
        Button btn_showCreate = new Button(comp_buttons, SWT.PUSH);
        btn_showCreate.setText(Messages.JCTCA_Visual_btn_show_archpic_create);
        btn_showCreate.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				titleAndDescription.setDescription(Messages.JCTCA_Visual_archpic_create_explain);
				currentImage = ImageService.getImage(Activator.PLUGIN_ID, Messages.JCTCA_Visual_path_to_create_img);
				lbl_img.setImage(currentImage);
				ResizeHelper.resize_image(lbl_img, comp_image);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
        
        Button btn_showRevoke = new Button(comp_buttons, SWT.PUSH);
        btn_showRevoke.setText(Messages.JCTCA_Visual_btn_show_archpic_revoke);
        btn_showRevoke.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				titleAndDescription.setDescription(Messages.JCTCA_Visual_archpic_revoke_explain);
				currentImage = ImageService.getImage(Activator.PLUGIN_ID, Messages.JCTCA_Visual_path_to_revoke_img);
				lbl_img.setImage(currentImage);
				ResizeHelper.resize_image(lbl_img, comp_image);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
        
        Button btn_showCheck = new Button(comp_buttons, SWT.PUSH);
        btn_showCheck.setText(Messages.JCTCA_Visual_btn_show_archpic_check);
        btn_showCheck.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				titleAndDescription.setDescription(Messages.JCTCA_Visual_archpic_check_explain);
				currentImage = ImageService.getImage(Activator.PLUGIN_ID, Messages.JCTCA_Visual_path_to_check_img);
				lbl_img.setImage(currentImage);
				ResizeHelper.resize_image(lbl_img, comp_image);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
        
        
        Button btn_continue = new Button(comp_buttons, SWT.PUSH);
        btn_continue.setText(Messages.JCTCA_Visual_btn_continue_to_plugin);
        btn_continue.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				disposeCompCenter();
				titleAndDescription.setDescription(Messages.JCTCA_Visual_visual_intro_text);
				showCenter();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
        
        
        
        comp_image = new Composite(comp_center, SWT.FILL);
        gl_comp_image = new GridLayout(1, false);
        gl_comp_image.marginHeight = 0;
        gl_comp_image.marginWidth = 0;
        comp_image.setLayout(gl_comp_image);
        comp_image.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        
        lbl_img = new Label(comp_image, SWT.WRAP | SWT.RESIZE);
        lbl_img.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        currentImage = ImageService.getImage(Activator.PLUGIN_ID, Messages.JCTCA_Visual_path_to_create_img);
        lbl_img.setImage(currentImage);
        lbl_img.addControlListener(new ControlListener() {
        	
        	private boolean resized = false;
			
			@Override
			public void controlResized(ControlEvent e) {
				// Changing the size of the control changes the 
				// available space for the image. Resizing is required.
				lbl_img.setImage(currentImage);
				
				// FIXME: Resizing the image does not work while resizing the whole plugin.
				// After fixing the resize problem the "resized" flag can be removed
				// and resizing can be done on every controlResized event.
				if (!resized) {
					ResizeHelper.resize_image(lbl_img, comp_image);
					resized = true;
				}
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				// Moving the whole control does not change the 
				// size of the image. No resizing is required.
				
			}
		});

        root.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    
    /**
     * This creates the GUI after clicking "continue with plugin"
     */
    public void showCenter() {
    	
        comp_center = new Composite(composite, SWT.NONE);
        gl_comp_center = new GridLayout(2, false);
        gl_comp_center.marginHeight = 0;
        gl_comp_center.marginWidth = 0;
        comp_center.setLayout(gl_comp_center);
        comp_center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        tabFolder = new TabFolder(comp_center, SWT.NONE);
        tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Group grp_explain = new Group(comp_center, SWT.NONE);
        grp_explain.setLayout(new GridLayout(1, true));
        GridData gd_explain = new GridData(SWT.FILL, SWT.FILL, false, true);
        gd_explain.widthHint = 500;
        grp_explain.setLayoutData(gd_explain);
        grp_explain.setText(Messages.JCTCA_Visual_grp_explain_headline);

        // label for showing explanation texts
        stl_explain = new StyledText(grp_explain, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        GridData gd_txt_explain = new GridData(SWT.FILL, SWT.FILL, true, true);
        stl_explain.setLayoutData(gd_txt_explain);

        TabItemListener tabItemListener = new TabItemListener(tabFolder, grp_explain);
        tabFolder.addSelectionListener(tabItemListener);

        new UserTab(tabFolder, grp_explain, SWT.NONE);

        new RegistrationTab(tabFolder, grp_explain, SWT.NONE);
        
        new CertificationTab(tabFolder, grp_explain, SWT.NONE);

        new SecondUserTab(tabFolder, grp_explain, SWT.NONE);

        tabFolder.setSelection(0);
        composite.layout(true);
        
        root.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
    }

    public void disposeCompCenter() {
        this.comp_center.dispose();
    }

    @Override
    public void setFocus() {
    	root.setFocus();
    }
}
