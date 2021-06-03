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

import static org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper.createArrowCanvas;
import static org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper.markCode;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.errorcorrectingcodes.algorithm.EccController;
import org.jcryptool.visual.errorcorrectingcodes.data.EccData;
import org.jcryptool.visual.errorcorrectingcodes.ui.Messages;
import org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper;
import org.jcryptool.visual.errorcorrectingcodes.ui.widget.ArrowCanvas;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite; 

/**
 * The Class GeneralEccView represents the common process of detecting and correcting errors when
 * transmitting data over a noisy channel.
 * @author Daniel Hofmann
 */
public class GeneralEccView extends Composite {

    private static final int _WHINT = 400;
    private EccController ecc;
    private DataBindingContext dbc;

    private Composite mainComposite;
    private Composite compFoot;
    private Composite compInputStep;
    private Composite compEncodeStep;
    private Composite compArrowRight1;
    private Composite compArrowRight2;
    private Composite compArrowDown;
    private Composite compArrowUp;
    private Composite compOutputStep;   
    private Composite compDecodeStep;
    private Group grpSender;
    private Group grpErrorCode;
    private Group grpFootButtons;
    private Group grpTextInfo;
    private Group grpReceiver;

    private StyledText textAsBinary;
    private StyledText textInput;
    private StyledText textEncoded;
    private StyledText textInfo;
    private StyledText textOutput;
    private StyledText textCorrected;
    private StyledText textError;
    private Button btnReset;
    private Button btnNextStep;
    private Button btnPrev;
    private ArrowCanvas arrowDown;
    private ArrowCanvas arrowRight1;
    private ArrowCanvas arrowRight2;
    private ArrowCanvas arrowUp;

    private Label lblTextOriginal;
    private Label lblTextEncoded;
    private Label lblTextDecoded;


    /**
     * Instantiates a new general ecc view.
     */

    public GeneralEccView(Composite parent, int style) {
        super(parent, style);
        ecc = new EccController(new EccData());

        // common grid layout for all elements
        GridLayoutFactory glf = GridLayoutFactory.fillDefaults().margins(5, 5).equalWidth(true);
        GridDataFactory gdf = GridDataFactory.fillDefaults().grab(true, false);
        
        glf.applyTo(this);
        gdf.applyTo(this);

        TitleAndDescriptionComposite header = new TitleAndDescriptionComposite(this);
        header.setTitle(Messages.GeneralEccView_lblHeader);
        header.setDescription(Messages.GeneralEccView_textHeader);
        header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        
        mainComposite = new Composite(this, SWT.NONE);
        GridLayout gl_mainComposite = new GridLayout(5, false);
        gl_mainComposite.marginHeight = 0;
        gl_mainComposite.marginWidth = 0;
        mainComposite.setLayout(gl_mainComposite);
        mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        grpSender = new Group(mainComposite, SWT.NONE);
        grpSender.setBackground(ColorService.LIGHT_AREA_BLUE);
        grpSender.setLayout(new GridLayout());
        grpSender.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        grpSender.setText(Messages.GeneralEccView_grpSenderStep);
        
        compInputStep = new Composite(grpSender, SWT.NONE);
        GridLayout gl_compInputStep = new GridLayout();
        gl_compInputStep.marginHeight = 0;
        gl_compInputStep.marginWidth = 0;
        compInputStep.setLayout(gl_compInputStep);
        compInputStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        lblTextOriginal = new Label(compInputStep, SWT.NONE);
        lblTextOriginal.setText(Messages.GeneralEccView_lblTextOriginal);
        
        textInput = new StyledText(compInputStep, SWT.BORDER);
        GridData gd_textInput = new GridData(SWT.FILL, SWT.FILL, true, false);
        gd_textInput.widthHint = _WHINT;
        textInput.setLayoutData(gd_textInput);
        textInput.addListener(SWT.FocusOut, e -> ecc.textAsBinary());
        
        textAsBinary = UIHelper.mutltiLineText(compInputStep, SWT.FILL, SWT.TOP, _WHINT, 5, FontService.getNormalMonospacedFont(), true);
        
        compArrowDown = new Composite(grpSender, SWT.NONE);
        compArrowDown.setLayout(new GridLayout(2, true));
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.TOP).applyTo(compArrowDown);
        
        arrowDown = createArrowCanvas(compArrowDown, 10, 10, 10, 50, 3, 13.0);
        
        lblTextEncoded = new Label(compArrowDown, SWT.NONE);
        lblTextEncoded.setText(Messages.GeneralEccView_lblTextEncode);
        
        compEncodeStep = new Composite(grpSender, SWT.NONE);
        GridLayout gl_compEncodeStep = new GridLayout();
        gl_compEncodeStep.marginHeight = 0;
        gl_compEncodeStep.marginWidth = 0;
        compEncodeStep.setLayout(gl_compEncodeStep);
        GridDataFactory.fillDefaults().applyTo(compEncodeStep);
        
        textEncoded = UIHelper.mutltiLineText(compEncodeStep, SWT.FILL, SWT.BOTTOM, _WHINT, 5, FontService.getNormalMonospacedFont(), true);
        
        compArrowRight1 = new Composite(mainComposite, SWT.NONE);
        compArrowRight1.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(compArrowRight1);
        arrowRight1 = createArrowCanvas(compArrowRight1, 10, 10, 50, 10, 2, 10.0);
        arrowRight1.setLineStyle(SWT.LINE_DASH);

        grpErrorCode = new Group(mainComposite, SWT.NONE);
        grpErrorCode.setBackground(ColorService.LIGHT_AREA_GREEN);
        grpErrorCode.setLayout(new GridLayout());
        grpErrorCode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        grpErrorCode.setText(Messages.GeneralEccView_grpErrorCode);
        
        textError = UIHelper.mutltiLineText(grpErrorCode, SWT.FILL, SWT.CENTER, 150, 5, FontService.getNormalMonospacedFont(), true);

        compArrowRight2 = new Composite(mainComposite, SWT.NONE);
        compArrowRight2.setLayout(new GridLayout());
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(compArrowRight2);
        arrowRight2 = createArrowCanvas(compArrowRight2, 10, 10, 50, 10, 2, 10.0);
        arrowRight2.setLineStyle(SWT.LINE_DASH);

        grpReceiver = new Group(mainComposite, SWT.NONE);
        grpReceiver.setBackground(ColorService.LIGHT_AREA_RED);
        grpReceiver.setLayout(new GridLayout());
        gdf.applyTo(grpReceiver);
        grpReceiver.setText(Messages.GeneralEccView_grpReceiver);
        
        compOutputStep = new Composite(grpReceiver, SWT.NONE);
        GridLayout gl_compOutputStep = new GridLayout();
        gl_compOutputStep.marginHeight = 0;
        gl_compOutputStep.marginWidth = 0;
        compOutputStep.setLayout(gl_compEncodeStep);
        compOutputStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        textOutput = UIHelper.mutltiLineText(compOutputStep, SWT.FILL, SWT.FILL, _WHINT, 5, FontService.getNormalMonospacedFont(), true);
        
        compArrowUp = new Composite(grpReceiver, SWT.NONE);
        glf.numColumns(2).applyTo(compArrowUp);
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(compArrowUp);
        arrowUp = createArrowCanvas(compArrowUp, 10, 50, 10, 10, 3, 13.0);
        
        lblTextDecoded = new Label(compArrowUp, SWT.NONE);
        lblTextDecoded.setText(Messages.GeneralEccView_lblTextDecoded);
        
        compDecodeStep = new Composite(grpReceiver, SWT.NONE);
        GridLayout gl_compDecodeStep = new GridLayout();
        gl_compDecodeStep.marginHeight = 0;
        gl_compDecodeStep.marginWidth = 0;
        compDecodeStep.setLayout(gl_compDecodeStep);
        compDecodeStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        textCorrected = UIHelper.mutltiLineText(compDecodeStep, SWT.FILL, SWT.FILL, _WHINT, 5, FontService.getNormalMonospacedFont(), true);

        compFoot = new Composite(this, SWT.NONE);
        GridLayout gl_compFoot = new GridLayout(2, false);
        gl_compFoot.marginHeight = 0;
        gl_compFoot.marginWidth = 0;
        compFoot.setLayout(gl_compFoot);
        compFoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));


        grpFootButtons = new Group(compFoot, SWT.NONE);
        GridLayout gl_grpFootButtons = new GridLayout(3, true);
        gl_grpFootButtons.horizontalSpacing = 10;
        grpFootButtons.setLayout(gl_grpFootButtons);
        grpFootButtons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        
        btnPrev = new Button(grpFootButtons, SWT.NONE);
        btnPrev.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        btnPrev.setText(Messages.GeneralEccView_btnPrev);
        btnPrev.addListener(SWT.Selection, e -> prevStep());
        
        btnNextStep = new Button(grpFootButtons, SWT.NONE);
        btnNextStep.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        btnNextStep.setText(Messages.GeneralEccView_btnNextStep);
        btnNextStep.addListener(SWT.Selection, e -> nextStep());
        
        btnReset = new Button(grpFootButtons, SWT.NONE);
        btnReset.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        btnReset.setText(Messages.GeneralEccView_btnReset);
        btnReset.addListener(SWT.Selection, e -> initView());
        
        grpTextInfo = new Group(compFoot, SWT.NONE);
        grpTextInfo.setLayout(new GridLayout());
        grpTextInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        grpTextInfo.setText(Messages.GeneralEccView_grpTextInfo);
        textInfo = UIHelper.mutltiLineText(grpTextInfo, SWT.FILL, SWT.FILL, SWT.DEFAULT, 5, null, true);
        textInfo.setText(Messages.GeneralEccView_textInfo_step1);

        bindValues();
        initView();
    }

    /**
     * Display Next step by iterating the shown view elements.
     */
    private void nextStep() {
        if (!compEncodeStep.isVisible()) {
            ecc.encodeBits();
            btnPrev.setEnabled(true);
            compEncodeStep.setVisible(true);
            compArrowDown.setVisible(true);
            textInfo.setText(Messages.GeneralEccView_textInfo_step2);
        } else if (!compArrowRight1.isVisible()) {
            ecc.flipBits();
            markCode(textError, SWT.COLOR_RED, ecc.getBitErrors());
            compArrowRight1.setVisible(true);
            grpErrorCode.setVisible(true);
            textInfo.setText(Messages.GeneralEccView_textInfo_step3);
        } else if (!grpReceiver.isVisible()) {
            ecc.correctErrors();
            markCode(textCorrected, SWT.COLOR_CYAN, ecc.getBitErrors());
            grpReceiver.setVisible(true);
            compArrowRight2.setVisible(true);
            compDecodeStep.setVisible(true);
            textInfo.setText(Messages.GeneralEccView_textInfo_step4);
        } else if (!compOutputStep.isVisible()) {
            btnNextStep.setEnabled(false);
            compArrowUp.setVisible(true);
            compOutputStep.setVisible(true);
            textInfo.setText(Messages.GeneralEccView_textInfo_step5);

        }
    }

    /**
     * Display previous step by iterating the hidden view elements.
     */
    private void prevStep() {
        if (compOutputStep.isVisible()) {
            btnNextStep.setEnabled(true);
            compArrowUp.setVisible(false);
            compOutputStep.setVisible(false);
            textInfo.setText(Messages.GeneralEccView_textInfo_step4);
        } else if (grpReceiver.isVisible()) {
            grpReceiver.setVisible(false);
            compArrowRight2.setVisible(false);
            compDecodeStep.setVisible(false);
            textInfo.setText(Messages.GeneralEccView_textInfo_step3);
        } else if (compArrowRight1.isVisible()) {
            compArrowRight1.setVisible(false);
            grpErrorCode.setVisible(false);
            textInfo.setText(Messages.GeneralEccView_textInfo_step2);
        } else if (compEncodeStep.isVisible()) {
            btnPrev.setEnabled(false);
            compEncodeStep.setVisible(false);
            compArrowDown.setVisible(false);
            textInfo.setText(Messages.GeneralEccView_textInfo_step1);
        }
    }

    /**
     * Initializes the view by hiding later steps.
     */
    public void initView() {
        textInput.setText("hello"); //$NON-NLS-1$
        ecc.textAsBinary();
        btnPrev.setEnabled(false);
        btnNextStep.setEnabled(true);
        compEncodeStep.setVisible(false);
        grpErrorCode.setVisible(false);
        grpReceiver.setVisible(false);
        compDecodeStep.setVisible(false);
        compOutputStep.setVisible(false);
        compArrowDown.setVisible(false);
        compArrowRight1.setVisible(false);
        compArrowRight2.setVisible(false);
        compArrowUp.setVisible(false);
    }

    /**
     * Bind model values to view elements using the JFace framework.
     * 
     */
    private void bindValues() {
        dbc = new DataBindingContext();

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textInput),
                BeanProperties.value(EccData.class, "originalString", String.class).observe(ecc.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textOutput),
                BeanProperties.value(EccData.class, "binaryAsString", String.class).observe(ecc.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textAsBinary),
                BeanProperties.value(EccData.class, "binaryAsString", String.class).observe(ecc.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textEncoded),
                BeanProperties.value(EccData.class, "codeAsString", String.class).observe(ecc.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textError),
                BeanProperties.value(EccData.class, "codeStringWithErrors", String.class).observe(ecc.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textCorrected),
                BeanProperties.value(EccData.class, "correctedString", String.class).observe(ecc.getData())); //$NON-NLS-1$
    }
}
