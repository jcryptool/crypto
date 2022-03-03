//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2011, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.visual.PairingBDII.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.PairingBDII.basics.UserData_BNP;
import org.jcryptool.visual.PairingBDII.basics.UserData_ECBDII;

public class DefinitionAndDetails {
    private final Group groupDefinitions;
    private final Group groupDetails;

    private Text label;
    private final Text labelAuthetification;
    private final Text labelPK;
    private final Text labelExplanation;
    private final Text labelDetails;

    private final Spinner spinnerUserIndex;
    private final Label labelUserIndex;

    public DefinitionAndDetails(Composite parent, View view) {

    	
        groupDefinitions = new Group(parent, SWT.NONE);
        groupDefinitions.setText(Messages.DefinitionAndDetails_0);
        groupDefinitions.setLayout(new GridLayout(1, false));
        groupDefinitions.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
        
    	ExpandBar bar_definitions = new ExpandBar(groupDefinitions, SWT.NONE);
    	GridData gd_bar_definitions =new GridData(SWT.FILL, SWT.FILL, true, false);
    	bar_definitions.setLayoutData(gd_bar_definitions);
    	
    	Composite comp_definitions = new Composite(bar_definitions, SWT.NONE);
    	comp_definitions.setLayout(new GridLayout(2, false));

        label = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        label.setText(Messages.DefinitionAndDetails_1);

        label = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3));
        label.setText(Messages.DefinitionAndDetails_2
                        + Messages.DefinitionAndDetails_3
                        + Messages.DefinitionAndDetails_4
                        + Messages.DefinitionAndDetails_5
                        + Messages.DefinitionAndDetails_6
                        + Messages.DefinitionAndDetails_7
                        + Messages.DefinitionAndDetails_8
                        + Messages.DefinitionAndDetails_9);

        label = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        label.setText(Messages.DefinitionAndDetails_10 
        		+ Messages.DefinitionAndDetails_11
                + Messages.DefinitionAndDetails_12);
        
        labelPK = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        labelPK.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        labelExplanation = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        labelExplanation.setText(Messages.DefinitionAndDetails_13 
        		+ Messages.DefinitionAndDetails_14
                + Messages.DefinitionAndDetails_15
                + Messages.DefinitionAndDetails_16);
        labelExplanation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        labelAuthetification = new Text(comp_definitions, SWT.WRAP | SWT.READ_ONLY);
        labelAuthetification.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        labelAuthetification.setText("\n\n\n");
        
        ExpandItem item_definitions = new ExpandItem(bar_definitions, SWT.NONE, 0);
        item_definitions.setHeight(comp_definitions.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
        item_definitions.setControl(comp_definitions);
        item_definitions.setExpanded(true);
        item_definitions.setText(Messages.DefinitionAndDetails_26);

        bar_definitions.addExpandListener(new ExpandListener() {
			
			@Override
			public void itemExpanded(ExpandEvent e) {
				changed(e, true);
			}
			
			@Override
			public void itemCollapsed(ExpandEvent e) {
				changed(e, false);
			}
			
		    private void changed(ExpandEvent e, boolean expand) {
		        if (e.item instanceof ExpandItem) {
		            ExpandItem expItem = (ExpandItem) e.item;
		            if (expand) {
		            	// The user expanded the tab
		            	expItem.setHeight(comp_definitions.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		            	gd_bar_definitions.heightHint = comp_definitions.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
		            			+ bar_definitions.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		            } else {
		            	// The user collapsed the item.
		            	expItem.setHeight(0);
		            	gd_bar_definitions.heightHint = bar_definitions.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		            }
		            // Layout and recalculate the size of the whole window.
		            // This is especially necessary for the correct
		            // behaviour of the scrollbars.
					view.layTheShitOut();
		        }
		    }
		});
        

        groupDetails = new Group(parent, SWT.NONE);
        groupDetails.setText(Messages.DefinitionAndDetails_17);
        groupDetails.setLayout(new GridLayout(1, false));
        groupDetails.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
        
    	ExpandBar bar_details = new ExpandBar(groupDetails, SWT.NONE);
    	GridData gd_bar_details =new GridData(SWT.FILL, SWT.FILL, true, false);
    	bar_details.setLayoutData(gd_bar_details);
    	
    	Composite comp_details = new Composite(bar_details, SWT.NONE);
    	comp_details.setLayout(new GridLayout(1, false));

        final Composite userSelection = new Composite(comp_details, SWT.NONE);
        userSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        userSelection.setLayout(new GridLayout(2, false));

        labelUserIndex = new Label(userSelection, SWT.NONE);
        labelUserIndex.setText(Messages.DefinitionAndDetails_18);
        
        spinnerUserIndex = new Spinner(userSelection, SWT.BORDER);
        spinnerUserIndex.setMinimum(1);
        spinnerUserIndex.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().infoUserIndex = spinnerUserIndex.getSelection()-1;

                if (!Model.getDefault().isOnBNCurve) {
                    displayUserDetails(Model.getDefault().usersData.get(Model.getDefault().infoUserIndex));
                } else {
                    displayUserDetails(Model.getDefault().bnpuData.get(Model.getDefault().infoUserIndex));
                }
            }
        });

        labelDetails = new Text(comp_details, SWT.MULTI | SWT.READ_ONLY | SWT.H_SCROLL);
        labelDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        labelDetails.setText("\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
        labelDetails.addMouseMoveListener(new MouseMoveListener() {
            // work around to ensure parent can be scrolled when entering this text field
            @Override
			public void mouseMove(MouseEvent e) {
                groupDetails.getParent().setFocus();
            }
        });
        
        
        ExpandItem item_details = new ExpandItem(bar_details, SWT.NONE, 0);
        item_details.setHeight(comp_details.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
        item_details.setControl(comp_details);
        item_details.setExpanded(true);
        item_details.setText(Messages.DefinitionAndDetails_28);

        bar_details.addExpandListener(new ExpandListener() {
			
			@Override
			public void itemExpanded(ExpandEvent e) {
				changed(e, true);
			}
			
			@Override
			public void itemCollapsed(ExpandEvent e) {
				changed(e, false);
			}
			
		    private void changed(ExpandEvent e, boolean expand) {
		        if (e.item instanceof ExpandItem) {
		            ExpandItem expItem = (ExpandItem) e.item;
		            if (expand) {
		            	// The user expanded the tab
		            	expItem.setHeight(comp_details.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		            	gd_bar_details.heightHint = comp_details.computeSize(SWT.DEFAULT, SWT.DEFAULT).y
		            			+ bar_details.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		            } else {
		            	// The user collapsed the item.
		            	expItem.setHeight(0);
		            	gd_bar_details.heightHint = bar_details.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		            }
		            // Layout and recalculate the size of the whole window.
		            // This is especially necessary for the correct
		            // behaviour of the scrollbars.
					view.layTheShitOut();
		        }
		    }
		});
        
        
        parent.getParent().layout();
    }

    public void changetoKis2(boolean change) {
        if (change) {
            labelPK.setText(Messages.DefinitionAndDetails_19);
            labelAuthetification
                    .setText(Messages.DefinitionAndDetails_20
                            + Messages.DefinitionAndDetails_21);
        } else {
            labelPK.setText(Messages.DefinitionAndDetails_22);
            labelAuthetification
                    .setText(Messages.DefinitionAndDetails_23
                            + Messages.DefinitionAndDetails_24);
        }
        groupDefinitions.layout();
    }

    public void displayUserDetails(UserData_BNP userData) {
        labelDetails.setText(userData.toString()); //$NON-NLS-1$
        labelDetails.setEnabled(true);
        
        groupDetails.getParent().layout(new Control[] {labelDetails});
    }

    public void displayUserDetails(UserData_ECBDII userData) {
        labelDetails.setText(userData.toString()); //$NON-NLS-1$
        labelDetails.setEnabled(true);
        
        groupDetails.getParent().layout(new Control[] {labelDetails});
    }

    public Composite getGroupDefinitions() {
        return groupDefinitions;
    }

    public Group getGroupDetails() {
        return groupDetails;
    }

    public void reset() {
        labelUserIndex.setEnabled(false);
        labelDetails.setEnabled(false);
        spinnerUserIndex.setEnabled(false);
        labelDetails.setText("\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
    }

    public void setMaximumNumberOfUsers(int numberOfUsers) {
        spinnerUserIndex.setMaximum(numberOfUsers);
        spinnerUserIndex.pack();
    }

    public void step1ExplVisible(boolean isvisible) {
        labelUserIndex.setEnabled(isvisible);
        spinnerUserIndex.setEnabled(isvisible);
        if (!isvisible) {
            labelDetails.setText("\n\n\n\n\n\n\n\n"); //$NON-NLS-1$
        }
    }

    public void step2ExplVisible(boolean isvisible) {
    	// The setText("") is a quich workaround for a 
    	// problem that overwrites (I mean really overwrites
    	// the text (two texts above each other))
    	labelExplanation.setText("");
    	
        labelExplanation.setText(Messages.DefinitionAndDetails_13
                + Messages.DefinitionAndDetails_30
                + Messages.DefinitionAndDetails_31
                + Messages.DefinitionAndDetails_32
                + Messages.DefinitionAndDetails_33);
        groupDefinitions.layout();
    }

    public void step3ExplVisible(boolean isvisible) {
        labelExplanation.setText(Messages.DefinitionAndDetails_13
                + Messages.DefinitionAndDetails_35);
        groupDefinitions.layout();
    }
}
