package org.jcryptool.visual.errorcorrectingcodes.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.logging.utils.LogUtil;
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
//        grpAlgorithmInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        
        Label lblValueM = new Label(grpAlgorithmInfo, SWT.NONE);
        lblValueM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        lblValueM.setText("m = "); //$NON-NLS-1$
        
        comboValueM = new Combo(grpAlgorithmInfo, SWT.READ_ONLY);
        comboValueM.setItems(mceCrypto.getValidMValues());
        comboValueM.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        
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
        grpInput.setText(Messages.McElieceView_grpInput);
        grpInput.setLayout(new GridLayout());
        grpInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        txtInput = UIHelper.mutltiLineText(grpInput, SWT.FILL, SWT.CENTER, 400, 5);
        
        btnEncrypt = new Button(grpInput, SWT.PUSH);
        btnEncrypt.setText(Messages.McElieceView_btnEncrypt);
        btnEncrypt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));
        btnEncrypt.addListener(SWT.Selection, e -> performEncryption());

        grpOutput = new Group(mainComposite, SWT.NONE);
        grpOutput.setText(Messages.McElieceView_grpOutput);
        grpOutput.setLayout(new GridLayout());
        grpOutput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        txtOutput = UIHelper.mutltiLineText(grpOutput, SWT.FILL, SWT.CENTER, 400, 5);
        
        btnDecrypt = new Button(grpOutput, SWT.PUSH);
        btnDecrypt.setText(Messages.McElieceView_btnDecrypt);
        btnDecrypt.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));
        btnDecrypt.addListener(SWT.Selection, e -> performDecryption());

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
}
