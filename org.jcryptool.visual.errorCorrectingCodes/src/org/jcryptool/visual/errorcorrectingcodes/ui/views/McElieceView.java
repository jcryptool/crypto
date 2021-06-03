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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.operations.OperationsPlugin;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.units.DefaultByteFormatter;
import org.jcryptool.core.util.units.DefaultByteFormatter.BaseUnit;
import org.jcryptool.visual.errorcorrectingcodes.EccPlugin;
import org.jcryptool.visual.errorcorrectingcodes.algorithm.McElieceCrypto;
import org.jcryptool.visual.errorcorrectingcodes.ui.Messages;
import org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper;

public class McElieceView extends Composite {

    private McElieceCrypto mceCrypto;

    private Composite parent;
    private Composite mainComposite;

    private Group grpInput;
    private Group grpOutput;
    private Group grpAlgorithmInfo;

    private StyledText txtInput;
    private StyledText txtOutput;
    private Combo comboValueM;
    private Text txtValueT;
    private Text txtPublicKey;
    private Text txtValueK;
    private Text txtValueN;
    private Button btnEncrypt;
    private Button btnDecrypt;
    private Button btnFillKey;
    
    private DefaultByteFormatter dbf = new DefaultByteFormatter
    		.Builder()
    		.asBase(BaseUnit.BASE_2)
    		.abbreviateUnit(true)
    		.precision(2)
    		.build();

    
    public McElieceView(Composite parent, int style) {
        super(parent, style);
        this.parent = parent;
        mceCrypto = new McElieceCrypto();
        
        setLayout(new GridLayout());
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        TitleAndDescriptionComposite header = new TitleAndDescriptionComposite(this);
        header.setTitle(Messages.McElieceView_lblHeader);
        header.setDescription(Messages.McElieceView_textHeader);
        header.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        
        mainComposite = new Composite(this, SWT.NONE);
        GridLayout gl_mainComposite = new GridLayout();
        gl_mainComposite.marginWidth = 0;
        gl_mainComposite.marginHeight = 0;
        mainComposite.setLayout(gl_mainComposite);
        mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        grpAlgorithmInfo = new Group(mainComposite, SWT.NONE);
        grpAlgorithmInfo.setText(Messages.McElieceView_grpKeyParams);
        grpAlgorithmInfo.setLayout(new GridLayout(5, false));
        
        Label lblValueM = new Label(grpAlgorithmInfo, SWT.NONE);
        lblValueM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        lblValueM.setText("m = "); //$NON-NLS-1$
        
        comboValueM = new Combo(grpAlgorithmInfo, SWT.READ_ONLY);
        comboValueM.setItems(mceCrypto.getValidMValues());
        comboValueM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        comboValueM.setText("8");
        
        btnFillKey = new Button(grpAlgorithmInfo, SWT.PUSH | SWT.WRAP);
        btnFillKey.setText(Messages.McElieceView_btnFillKey);
        GridData gd_btnFillKey = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
        gd_btnFillKey.horizontalIndent = 60;
        btnFillKey.setLayoutData(gd_btnFillKey);
        btnFillKey.addListener(SWT.Selection, e -> generateKeys());
        
        Label lblValueK = new Label(grpAlgorithmInfo, SWT.NONE);
        GridData gd_lblValueK = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_lblValueK.horizontalIndent = 60;
        lblValueK.setLayoutData(gd_lblValueK);
        lblValueK.setText("k = ");
        lblValueK.setForeground(ColorService.GRAY);
        
        txtValueK = new Text(grpAlgorithmInfo, SWT.READ_ONLY);
        txtValueK.setText("0");
        GridData gd_txtValueK = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_txtValueK.widthHint = 120;
        txtValueK.setLayoutData(gd_txtValueK);
        txtValueK.setForeground(ColorService.GRAY);
        
        Label lblValueT = new Label(grpAlgorithmInfo, SWT.NONE);
        lblValueT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        lblValueT.setText("t   = "); //$NON-NLS-1$
        
        txtValueT = new Text(grpAlgorithmInfo, SWT.BORDER);
        txtValueT.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        txtValueT.setText("12");


        Label lblValueN = new Label(grpAlgorithmInfo, SWT.NONE);
        GridData gd_lblValueN = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_lblValueN.horizontalIndent = 60;
        lblValueN.setLayoutData(gd_lblValueN);
        lblValueN.setText("n = ");
        lblValueN.setForeground(ColorService.GRAY);
        
        txtValueN = new Text(grpAlgorithmInfo, SWT.READ_ONLY);
        txtValueN.setText("0");
        GridData gd_txtValueN = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_txtValueN.widthHint = 120;
        txtValueN.setLayoutData(gd_txtValueN);
        txtValueN.setForeground(ColorService.GRAY);
        
        
        // 3 Spacer 
        new Label(grpAlgorithmInfo, SWT.None);
        new Label(grpAlgorithmInfo, SWT.None);
        new Label(grpAlgorithmInfo, SWT.None);

        Label lblPublicKey = new Label(grpAlgorithmInfo, SWT.NONE);
        GridData gd_lblPublicKey = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_lblPublicKey.horizontalIndent = 60;
        lblPublicKey.setLayoutData(gd_lblPublicKey);
        lblPublicKey.setText(Messages.McElieceView_lblPublicKey);
        lblPublicKey.setForeground(ColorService.GRAY);
        
        
        txtPublicKey = new Text(grpAlgorithmInfo, SWT.READ_ONLY);
        txtPublicKey.setText(dbf.format(0));
        GridData gd_txtPublicKey = new GridData(SWT.FILL, SWT.CENTER, false, false);
        gd_txtPublicKey.widthHint = 120;
        txtPublicKey.setLayoutData(gd_txtPublicKey);
        txtPublicKey.setForeground(ColorService.GRAY);


        grpInput = new Group(mainComposite, SWT.NONE);
        grpInput.setText(Messages.McElieceView_grpInput + " (0 Byte)");
        grpInput.setLayout(new GridLayout());
        grpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        txtInput = UIHelper.mutltiLineText(grpInput, SWT.FILL, SWT.CENTER, 400, 5);
        txtInput.addListener(SWT.Modify, e -> updatePlaintextLength());
        txtInput.setText(getDefaultText());
        
        btnEncrypt = new Button(grpInput, SWT.PUSH);
        btnEncrypt.setText(Messages.McElieceView_btnEncrypt);
        btnEncrypt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));
        btnEncrypt.addListener(SWT.Selection, e -> performEncryption());

        grpOutput = new Group(mainComposite, SWT.NONE);
        grpOutput.setText(Messages.McElieceView_grpOutput + " (0 Byte)");
        grpOutput.setLayout(new GridLayout());
        grpOutput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        txtOutput = UIHelper.mutltiLineText(grpOutput, SWT.FILL, SWT.CENTER, 400, 5);
        txtOutput.addListener(SWT.Modify, e -> updateCiphertextLength());
        
        btnDecrypt = new Button(grpOutput, SWT.PUSH);
        btnDecrypt.setText(Messages.McElieceView_btnDecrypt);
        btnDecrypt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));
        btnDecrypt.addListener(SWT.Selection, e -> performDecryption());

    }
    
    private void updatePlaintextLength() {
    	int textLength = txtInput.getText().length();
    	grpInput.setText(Messages.McElieceView_grpInput + " (" + textLength + " Byte)");
    }
    
    private void updateCiphertextLength() {
    	// Length is divided by 2 because text is in hexadecimal format
    	int textLength = txtOutput.getText().length() / 2;
    	grpOutput.setText(Messages.McElieceView_grpOutput + " (" + textLength + " Byte)");
    }

    private void generateKeys() {
    	if (comboValueM.getText().isEmpty() || txtValueT.getText().isEmpty()) {
    		MessageBox keyErrorDialog = new MessageBox(parent.getShell(), SWT.ERROR);
    		keyErrorDialog.setText(Messages.McElieceView_errorNoParametersSelectedTitle);
    		keyErrorDialog.setMessage(Messages.McElieceView_errorNoParametersSelected);
    		keyErrorDialog.open();
    		return;
    	}
    	
        if (updateParams() != 0) {
            comboValueM.setText(String.valueOf(mceCrypto.getM()));
            txtValueT.setText(String.valueOf(mceCrypto.getT()));
            txtValueK.setText(String.valueOf(mceCrypto.getK()));
            txtValueN.setText(String.valueOf(mceCrypto.getCodeLength()));
            txtPublicKey.setText(dbf.format((long) mceCrypto.getPrivateKeySize()));
        }
    }

    private int updateParams() {

    	int m = Integer.valueOf(comboValueM.getText());
    	int t = Integer.valueOf(txtValueT.getText());
    	
        try {
            mceCrypto.setKeyParams(m, t);
        } catch (Exception ex) {
            LogUtil.logError(ex);
            MessageBox keyErrorDialog = new MessageBox(parent.getShell(), SWT.ERROR);
            keyErrorDialog.setText(Messages.McElieceView_errorParamsTitle);
            keyErrorDialog.setMessage(Messages.McElieceView_errorParams);
            keyErrorDialog.open();
        }

        txtValueK.setText(String.valueOf(mceCrypto.getK()));
        txtValueN.setText(String.valueOf(mceCrypto.getCodeLength()));
        txtPublicKey.setText(dbf.format((long) mceCrypto.getPrivateKeySize()));
        return mceCrypto.getCodeLength();

    }

    private void performDecryption() {
    	
    	if (comboValueM.getText().isEmpty() || txtValueT.getText().isEmpty()) {
    		MessageBox keyErrorDialog = new MessageBox(parent.getShell(), SWT.ERROR);
    		keyErrorDialog.setText(Messages.McElieceView_errorEncryptionFailedTitle);
    		keyErrorDialog.setMessage(Messages.McElieceView_errorEncryptionFailed);
    		keyErrorDialog.open();
    		return;
    	}

        if (Integer.valueOf(comboValueM.getText()) != mceCrypto.getM()
                || Integer.valueOf(txtValueT.getText()) != mceCrypto.getT()) {
            updateParams();
        }

        try {
            mceCrypto.decrypt(txtOutput.getText());
            txtInput.setText(mceCrypto.getClearText());
        } catch (Exception e) {
            LogUtil.logError(EccPlugin.PLUGIN_ID, Messages.McElieceView_errorCipher, e, true);
        }
    }

    private void performEncryption() {
    	if (comboValueM.getText().isEmpty() || txtValueT.getText().isEmpty()) {
    		MessageBox keyErrorDialog = new MessageBox(parent.getShell(), SWT.ERROR);
    		keyErrorDialog.setText(Messages.McElieceView_errorDecryptionFailedTitle);
    		keyErrorDialog.setMessage(Messages.McElieceView_errorDecryptionFailed);
    		keyErrorDialog.open();
    		return;
    	}
    	
        if (Integer.valueOf(comboValueM.getText()) != mceCrypto.getM()
                || Integer.valueOf(txtValueT.getText()) != mceCrypto.getT()) {
            updateParams();
        }

        mceCrypto.encrypt(txtInput.getText().getBytes());
        txtOutput.setText(mceCrypto.getEncryptedHex());
    }
    
    /**
     * Loads the JCT default editor text (This is the JCryptool sample file...)
     */
    private String getDefaultText() {
    	String defaultText = "Something went wrong loading the deafult text. Please report this issue.";
    	try {
    		URL url = OperationsPlugin.getDefault().getBundle().getEntry("/");
			File template = new File(FileLocator.toFileURL(url).getFile() + "templates" + File.separatorChar + Messages.McElieceView_filename);
			
			BufferedReader br = new BufferedReader(new FileReader(template));
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			
			br.close();
			
			defaultText = sb.toString();
			
    	} catch (IOException e) {
			LogUtil.logError(EccPlugin.PLUGIN_ID, e);
		} 
    	
    	return defaultText;
    	
    }
    
    public void resetView() {
		Control[] children = this.getChildren();
		for (Control control : children) {
			control.dispose();
		}
    }
}
