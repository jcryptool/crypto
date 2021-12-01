// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.games.numbershark.strategies;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.jcryptool.games.numbershark.NumberSharkPlugin;

/**
 * Settings dialog for the calculation of the optimal strategies
 * 
 * @author Johannes Spaeth
 * @version 0.9.5
 */
public class HeuristicStrategyDialog extends AbstractStrategyDialog {

    public HeuristicStrategyDialog(Shell shell) {
        super(shell);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        setTitle(Messages.HeuStratDialog_0);
        setMessage(Messages.HeuStratDialog_5);

        Composite area = (Composite) super.createDialogArea(parent);
        area.setLayout(new GridLayout());

        Composite composite = new Composite(area, SWT.NONE);
        GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, false);
        composite.setLayoutData(gd_composite);
        GridLayout gl_composite = new GridLayout();
        gl_composite.marginTop = 15;
        gl_composite.marginLeft = 15;
        gl_composite.marginBottom = 15;
        gl_composite.marginRight = 15;
        composite.setLayout(gl_composite);      

        Group strategyGroup = new Group(composite, SWT.NONE);
        strategyGroup.setText(Messages.HeuStratDialog_8);
        strategyGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout gl_strategyGroup = new GridLayout(1, true);
        strategyGroup.setLayout(gl_strategyGroup);
        
        final Button[] radioButton = new Button[4];
        radioButton[0] = new Button(strategyGroup, SWT.RADIO);
        radioButton[0].setText(Messages.HeuStratDialog_1);

        radioButton[1] = new Button(strategyGroup, SWT.RADIO);
        radioButton[1].setText(Messages.HeuStratDialog_2);

        radioButton[2] = new Button(strategyGroup, SWT.RADIO);
        radioButton[2].setText(Messages.HeuStratDialog_3);

        SelectionAdapter radioButtonListener = new SelectionAdapter() {
        	
            @Override
			public void widgetSelected(SelectionEvent e) {
                updateRadioButton(radioButton, e.getSource());
            }
        };

        radioButton[0].setSelection(true);
        radioButton[1].setSelection(false);
        radioButton[2].setSelection(false);
        
        updateRadioButton(radioButton, radioButton[0]);
        
        radioButton[0].addSelectionListener(radioButtonListener);
        radioButton[1].addSelectionListener(radioButtonListener);
        radioButton[2].addSelectionListener(radioButtonListener);

        createSliders(composite, false, 2000, 40);
        
        Label separartor = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
        separartor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, NumberSharkPlugin.PLUGIN_ID + ".heuStratDialog"); //$NON-NLS-1$
        
        return area;
    }

    private void updateRadioButton(final Button[] radioButton, Object source) {
		if (source == radioButton[0]) {
	        selectedStrategy = 2;
	    } else if (source == radioButton[1]) {
	        selectedStrategy = 3;
	    } else if (source == radioButton[2]) {
	        selectedStrategy = 4;
	    } else if (source == radioButton[3]) {
	        selectedStrategy = 5;
	    }
	}

	@Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.HeuStratDialog_7);
    }
}
