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
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;

public class Illustration {

    private final Canvas canvas;

    private final Button GenAuthi;
    private final Button Broadcast2;
    private final Button Broadcast3;
    private final Button CompKey;
    private final Button Verify;

    private final Text VerifyLabel;

    private final Text Step1;
    private final Text Step2;
    private final Text Step3;
    private final Text Step4;

    public Illustration(Composite parent) {

        final Group group_Illustration = new Group(parent, SWT.NONE);
        group_Illustration.setLayout(new GridLayout(2, false));
        group_Illustration.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        group_Illustration.setText(Messages.Illustration_0);

        // This  is the illustration on the left side of the plugin.
        canvas = new Canvas(group_Illustration, SWT.NONE);
        canvas.setBackground(ColorService.WHITE);
        canvas.addPaintListener(new GraphPainter());
        GridData gridData_canvas = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 5);
        gridData_canvas.widthHint = 400;
        gridData_canvas.heightHint = 480;
        canvas.setLayoutData(gridData_canvas);
        canvas.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent e) {
				// Resize the illustration that it consumes the 
				// full height of the illustration group
		        gridData_canvas.heightHint = group_Illustration.getClientArea().height - 10;
		        gridData_canvas.widthHint = (int) ((float) gridData_canvas.heightHint * 300f / 360f) - 10;
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
				
			}
		});


        final Group groupStep1 = new Group(group_Illustration, SWT.NONE);
        groupStep1.setText(Messages.Illustration_1);
        groupStep1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
        groupStep1.setLayout(new GridLayout(1, false));
        
        Step1 = new Text(groupStep1, SWT.WRAP | SWT.READ_ONLY);
        Step1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        Step1.setText(Messages.Illustration_2);
        Step1.setEnabled(false);
        
        GenAuthi = new Button(groupStep1, SWT.PUSH);
        GenAuthi.setText(Messages.Illustration_3);
        GenAuthi.setEnabled(false);
        GenAuthi.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().setupStep2();
            }
        });

        final Group groupStep2 = new Group(group_Illustration, SWT.NONE);
        groupStep2.setText(Messages.Illustration_4);
        groupStep2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
        groupStep2.setLayout(new GridLayout(1, false));
        
        Step2 = new Text(groupStep2, SWT.WRAP | SWT.READ_ONLY);
        Step2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        Step2.setText(Messages.Illustration_5);
        Step2.setEnabled(false);
        
        Broadcast2 = new Button(groupStep2, SWT.PUSH);
        Broadcast2.setText(Messages.Illustration_6);
        Broadcast2.setEnabled(false);
        Broadcast2.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().setupStep3();
            }
        });

        final Group groupStep3 = new Group(group_Illustration, SWT.NONE);
        groupStep3.setText(Messages.Illustration_7);
        groupStep3.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
        groupStep3.setLayout(new GridLayout(1, false));
        
        Step3 = new Text(groupStep3, SWT.WRAP | SWT.READ_ONLY);
        Step3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        Step3.setText(Messages.Illustration_8
                        + Messages.Illustration_9
                        + Messages.Illustration_10
                        + Messages.Illustration_11
                        + Messages.Illustration_12);
        Step3.setEnabled(false);
        
        Broadcast3 = new Button(groupStep3, SWT.PUSH);
        Broadcast3.setText(Messages.Illustration_13);
        Broadcast3.setEnabled(false);
        Broadcast3.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().setupStep4();
            }
        });
        

        final Group groupStep4 = new Group(group_Illustration, SWT.NONE);
        groupStep4.setText(Messages.Illustration_14);
        groupStep4.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
        groupStep4.setLayout(new GridLayout(1, false));
        
        Step4 = new Text(groupStep4, SWT.WRAP | SWT.READ_ONLY);
        Step4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        Step4.setText(Messages.Illustration_15
                        + Messages.Illustration_16
                        + Messages.Illustration_17);
        Step4.setEnabled(false);
        
        CompKey = new Button(groupStep4, SWT.PUSH);
        CompKey.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().setupStep5();
            }
        });
        // CompKey.addListener(SWT.Selection, listener);
        CompKey.setText(Messages.Illustration_18);
        CompKey.setEnabled(false);

        final Group groupStep5 = new Group(group_Illustration, SWT.NONE);
        groupStep5.setText(Messages.Illustration_19);
        groupStep5.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
        groupStep5.setLayout(new GridLayout(1, false));
        
        VerifyLabel = new Text(groupStep5, SWT.READ_ONLY);
        VerifyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        VerifyLabel.setText(Messages.Illustration_20);
        VerifyLabel.setEnabled(false);
        
        Verify = new Button(groupStep5, SWT.NONE);
        Verify.setText(Messages.Illustration_21);
        Verify.setEnabled(false);
        Verify.addSelectionListener(new SelectionListener() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }

            @Override
			public void widgetSelected(SelectionEvent e) {
                Model.getDefault().setupStep6();
                Verify.setEnabled(false);
            }
        });
        
    }

    public void changeToKis2(boolean change) {
        if (change) {
            Step2.setText(Messages.Illustration_22);
            Step3.setText(Messages.Illustration_23
                            + Messages.Illustration_24);
            Step4.setText(Messages.Illustration_25);
        } else {
            Step2.setText(Messages.Illustration_26);
            Step3.setText(Messages.Illustration_27
                            + Messages.Illustration_28);
            Step4.setText(Messages.Illustration_29);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Button getStep0() {
        return GenAuthi;
    }

    public Button getStep1() {
        return Broadcast2;
    }

    public Button getStep2() {
        return Broadcast3;
    }

    public Button getStep3() {
        return CompKey;
    }

    public Button getVerify() {
        return Verify;
    }

    public Text getVerifyLabel() {
        return VerifyLabel;
    }

    // makes step 1 visible
    public void setStep1Enabled(boolean isvisible) {
        GenAuthi.setEnabled(isvisible);
        Step1.setEnabled(isvisible);

        Broadcast2.setEnabled(false);
        Step2.setEnabled(false);
        Broadcast3.setEnabled(false);
        Step3.setEnabled(false);
        CompKey.setEnabled(false);
        Step4.setEnabled(false);
        Verify.setEnabled(false);
        VerifyLabel.setEnabled(false);
    }

    // makes step 2 visible
    public void setStep2Enabled(boolean isvisible) {
        Broadcast2.setEnabled(isvisible);
        Step2.setEnabled(isvisible);
        GenAuthi.setEnabled(!isvisible);
    }

    // makes step 3 visible
    public void setStep3Enabled(boolean isvisible) {
        Broadcast3.setEnabled(isvisible);
        Step3.setEnabled(isvisible);
        Broadcast2.setEnabled(!isvisible);
    }

    // Makes step 4 visible
    public void setStep4Enabled(boolean isvisible) {
        CompKey.setEnabled(isvisible);
        Step4.setEnabled(isvisible);
        Broadcast3.setEnabled(!isvisible);
    }

    // makes the verification unit visible
    public void setVerifyEnabled(boolean isvisible) {
        Verify.setEnabled(isvisible);
        VerifyLabel.setEnabled(isvisible);
        CompKey.setEnabled(!isvisible);
    }
}
