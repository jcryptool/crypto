package org.jcryptool.visual.errorcorrectingcodes.ui.views;

import static org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper.codeText;
import static org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper.matrixText;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.visual.errorcorrectingcodes.algorithm.HammingCrypto;
import org.jcryptool.visual.errorcorrectingcodes.data.HammingData;
import org.jcryptool.visual.errorcorrectingcodes.data.Matrix2D;
import org.jcryptool.visual.errorcorrectingcodes.data.MatrixException;
import org.jcryptool.visual.errorcorrectingcodes.ui.Messages;
import org.jcryptool.visual.errorcorrectingcodes.ui.UIHelper;
import org.jcryptool.visual.errorcorrectingcodes.ui.binding.InteractiveMatrixProperty;
import org.jcryptool.visual.errorcorrectingcodes.ui.widget.InteractiveMatrix;

/**
 * CryptoView represents the McEliece cryptographic system with Hamming code and small matrices S and P.
 * Usually, the encryption requires larger linear code, such as Goppa, and accordingly big permutation and scrambling matrices. Because the resulting large key and data sizes are not easily represented, we decided to simplify the original proposal. Therefore this is not a secure implementation of the system.
 * 
 * @author dhofmann
 *
 */
public class HammingCryptoView extends Composite {

    private HammingData data;
    private HammingCrypto mce;
    private DataBindingContext dbc;

    private Composite parent;
    private Composite mainComposite;
    private Composite compInverseMatrices;
    private Group grpDecryption;
    private Group grpPrivateKey;
    private Group grpEncryption;
    private Group grpInputStep;
    private Group grpOutput;
    private Group grpPublicKey;
    private Group grpControlButtons;
    private Group grpTextInfo;
    private Group grpCiphertext;

    private Text textOutput;
    private Text textInput;
    private StyledText textAsBinary;
    private StyledText textEncrypted;
    private StyledText textInfo;
    private StyledText textMatrixG;
    private StyledText textMatrixSInverse;
    private StyledText textMatrixPInverse;
    private StyledText textMatrixGSP;
    private StyledText textDecoded;
    private InteractiveMatrix compMatrixP;
    private InteractiveMatrix compMatrixS;

    private Button btnReset;
    private Button btnNextStep;
    private Button btnPrev;
    private Button btnGeneratePrivateKey;
    private Label lblMatrixG;
    private Label lblMatrixS;
    private Label lblMatrixP;
    private Label lblMatrixGSP;
    private Label lblMatrixSInverse;
    private Label lblMatrixPInverse;

    public HammingCryptoView(Composite parent, int style) {
        super(parent, style);
        data = new HammingData();
        mce = new HammingCrypto(data);
        this.parent = parent;
        setLayout(new GridLayout());

        TitleAndDescriptionComposite header = new TitleAndDescriptionComposite(this);
        header.setTitle(Messages.HammingCryptoView_lblHeader);
        header.setDescription(Messages.HammingCryptoView_textHeader);
        header.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        
        mainComposite = new Composite(this, SWT.NONE);
        GridLayout gl_mainComposite = new GridLayout(2, true);
        mainComposite.setLayout(gl_mainComposite);
        mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        grpDecryption = new Group(mainComposite, SWT.NONE);
        grpDecryption.setText(Messages.HammingCryptoView_grpDecryption);
        grpDecryption.setLayout(new GridLayout(1, false));
        grpDecryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        

        grpPrivateKey = new Group(grpDecryption, SWT.NONE);
        grpPrivateKey.setText(Messages.HammingCryptoView_grpPrivateKey);
        GridLayout gl_grpPrivateKey = new GridLayout(6, false);
        gl_grpPrivateKey.horizontalSpacing = 20;
        grpPrivateKey.setLayout(gl_grpPrivateKey);
        grpPrivateKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        // The "Generate" Button uses the top 6 rows.
        btnGeneratePrivateKey = new Button(grpPrivateKey, SWT.NONE);
        btnGeneratePrivateKey.setText(Messages.HammingCryptoView_btnGeneratePrivateKey);
        btnGeneratePrivateKey.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 6, 1));
        btnGeneratePrivateKey.addListener(SWT.Selection, e -> generateKey(true));

        lblMatrixG = new Label(grpPrivateKey, SWT.NONE);
        lblMatrixG.setText("G = "); //$NON-NLS-1$
        lblMatrixG.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true));
        
        textMatrixG = matrixText(grpPrivateKey, SWT.LEFT, SWT.CENTER, 7, 4);
        // overwrite the layout data set in matrixText. It causes the styledText
        // to push the interactive matrixes to the right border of grpPrivateKey
        textMatrixG.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true));
        
        lblMatrixS = new Label(grpPrivateKey, SWT.NONE);
        lblMatrixS.setText("S = "); //$NON-NLS-1$
        lblMatrixS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
        
        compMatrixS = new InteractiveMatrix(grpPrivateKey, 4, 4);

        lblMatrixP = new Label(grpPrivateKey, SWT.NONE);
        lblMatrixP.setText("P = "); //$NON-NLS-1$
        lblMatrixP.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true));
        
        compMatrixP = new InteractiveMatrix(grpPrivateKey, 7, 7);
        compMatrixP.setPermutation(true);

        compInverseMatrices = new Composite(grpDecryption, SWT.NONE);
        GridLayout gl_compInverseMatrices = new GridLayout(4, false);
        // 20 pixel horizontal spacing between components in group.
        gl_compInverseMatrices.horizontalSpacing = 20;
        compInverseMatrices.setLayout(gl_compInverseMatrices);
        compInverseMatrices.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        
        lblMatrixSInverse = new Label(compInverseMatrices, SWT.NONE);
        lblMatrixSInverse.setText("S' = "); //$NON-NLS-1$
        
        textMatrixSInverse = matrixText(compInverseMatrices, SWT.LEFT, SWT.CENTER, 4, 4);
        
        lblMatrixPInverse = new Label(compInverseMatrices, SWT.NONE);
        lblMatrixPInverse.setText("P' = "); //$NON-NLS-1$
        lblMatrixPInverse.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true));
        
        textMatrixPInverse = matrixText(compInverseMatrices, SWT.LEFT, SWT.CENTER, 7, 7);

        grpOutput = new Group(grpDecryption, SWT.NONE);
        grpOutput.setLayout(new GridLayout());
        grpOutput.setText(Messages.HammingCryptoView_lblOutput);
        grpOutput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        textDecoded = UIHelper.mutltiLineText(grpOutput, SWT.FILL, SWT.TOP, 400, 1, FontService.getNormalMonospacedFont(), true);
        
        textOutput = new Text(grpOutput, SWT.BORDER);
        textOutput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        grpEncryption = new Group(mainComposite, SWT.NONE);
        grpEncryption.setText(Messages.HammingCryptoView_grpEncryption);
        grpEncryption.setLayout(new GridLayout());
        grpEncryption.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        grpInputStep = new Group(grpEncryption, SWT.NONE);
        grpInputStep.setText(Messages.HammingCryptoView_lblTextOriginal);
        grpInputStep.setLayout(new GridLayout());
        grpInputStep.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        textInput = new Text(grpInputStep, SWT.BORDER);
        textInput.addListener(SWT.FocusOut, e -> updateVector());
        textInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        
        textAsBinary = codeText(grpInputStep, SWT.FILL, SWT.TOP);
        GridData gd_textAsBinary = new GridData(SWT.FILL, SWT.FILL, true, false);
        gd_textAsBinary.widthHint = 400;
        textAsBinary.setLayoutData(gd_textAsBinary);

        grpPublicKey = new Group(grpEncryption, SWT.NONE);
        grpPublicKey.setText(Messages.HammingCryptoView_grpPublicKey);
        grpPublicKey.setLayout(new GridLayout(2, false));
        
        lblMatrixGSP = new Label(grpPublicKey, SWT.NONE);
        lblMatrixGSP.setText("G' = SGP = "); //$NON-NLS-1$
        
        textMatrixGSP = matrixText(grpPublicKey, SWT.LEFT, SWT.CENTER, 7, 4);

        grpCiphertext = new Group(grpEncryption, SWT.NONE);
        grpCiphertext.setLayout(new GridLayout());
        grpCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        grpCiphertext.setText(Messages.HammingCryptoView_lblEncrypt);
        
        
        textEncrypted = UIHelper.mutltiLineText(grpCiphertext, SWT.FILL, SWT.FILL, 400, 5, FontService.getNormalMonospacedFont(), true);

        grpControlButtons = new Group(grpEncryption, SWT.NONE);
        grpControlButtons.setLayout(new GridLayout(3, false));
        
        
        btnPrev = new Button(grpControlButtons, SWT.NONE);
        btnPrev.setText(Messages.GeneralEccView_btnPrev);
        btnPrev.addListener(SWT.Selection, e -> prevStep());
        
        btnNextStep = new Button(grpControlButtons, SWT.NONE);
        btnNextStep.setText(Messages.GeneralEccView_btnNextStep);
        btnNextStep.addListener(SWT.Selection, e -> nextStep());
        
        btnReset = new Button(grpControlButtons, SWT.NONE);
        btnReset.setText(Messages.GeneralEccView_btnReset);
        btnReset.addListener(SWT.Selection, e -> initView());
        
        
        grpTextInfo = new Group(grpEncryption, SWT.NONE);
        grpTextInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        grpTextInfo.setLayout(new GridLayout());
        grpTextInfo.setText(Messages.HammingCryptoView_grpTextInfo);
        
        textInfo = UIHelper.mutltiLineText(grpTextInfo, SWT.FILL, SWT.FILL, 400, 6, null, true);

        bindValues();
        initView();
                
    }

    private void updateVector() {
        mce.stringToArray();
        textAsBinary.setText(data.getBinary().toString());
    }

    /**
     * Initializes the view, resetting elements.
     */
    private void initView() {
        resetKeys();
        textInput.setText("Hallo"); //$NON-NLS-1$
        textInfo.setText(Messages.HammingCryptoView_step1);
        updateVector();
        btnPrev.setEnabled(false);
        btnGeneratePrivateKey.setEnabled(true);
        btnNextStep.setEnabled(true);
        grpPublicKey.setVisible(false);
        grpCiphertext.setVisible(false);
        compInverseMatrices.setVisible(false);
        grpOutput.setVisible(false);
        textMatrixG.setText(data.getMatrixG().toString());
    }

    /**
     * Display Next step by iterating the shown view elements.
     */
    private void nextStep() {
        if (!grpPublicKey.isVisible()) {
            try {
                generateKey(false);
                mce.computePublicKey();
                mce.encrypt();
                textMatrixGSP.setText(data.getMatrixSGP().toString());
                textEncrypted.setText(data.getEncrypted().toString());
                textInfo.setText(Messages.HammingCryptoView_step2);
                grpPublicKey.setVisible(true);
                grpCiphertext.setVisible(true);
                btnPrev.setEnabled(true);
                btnGeneratePrivateKey.setEnabled(false);
            } catch (MatrixException e) {
                MessageBox dialog = new MessageBox(parent.getShell(), SWT.ICON_ERROR | SWT.OK);
                dialog.setText("Input Error");
                dialog.setMessage(e.getMessage());
                // open dialog and await user selection
                dialog.open();
            }

        } else if (!grpOutput.isVisible()) {
            mce.decrypt();
            // UIHelper.markCode(textCorrected, SWT.COLOR_CYAN, ecc.getBitErrors());
            textMatrixPInverse.setText(data.getMatrixPInv().toString());
            textMatrixSInverse.setText(data.getMatrixSInv().toString());
            textInfo.setText(Messages.HammingCryptoView_step3);
            compInverseMatrices.setVisible(true);
            grpOutput.setVisible(true);
            btnNextStep.setEnabled(false);
        }
    }

    /**
     * Display previous step by iterating the hidden view elements.
     */
    private void prevStep() {
        if (grpOutput.isVisible()) {
            textInfo.setText(Messages.HammingCryptoView_step2);
            compInverseMatrices.setVisible(false);
            grpOutput.setVisible(false);
            btnNextStep.setEnabled(true);
        } else if (grpPublicKey.isVisible()) {
            textInfo.setText(Messages.HammingCryptoView_step1);
            grpCiphertext.setVisible(false);
            grpPublicKey.setVisible(false);
            btnPrev.setEnabled(false);
            btnGeneratePrivateKey.setEnabled(true);

        }
    }

    private void resetKeys() {
        compMatrixS.reset();
        compMatrixP.reset();
        compMatrixS.setModified(false);
        compMatrixP.setModified(false);
        data.setMatrixSInv(null);
        data.setMatrixPInv(null);
    }
    
    /**
     * Generate the private and public key matrices as well as their inverses.
     * @param reset if true, discard any user input by resetting S and P to all 0
     */

    private void generateKey(boolean reset) {
        if (reset) {
           resetKeys();
        }

        Matrix2D p, s, invS, invP;
        
        //when matrices have not been set yet generate them automatically
        if (!compMatrixP.isModified()) {
            mce.randomPermutationMatrix(7);
            //compMatrixP.setMatrix(data.getMatrixP());
        } else {
            invP = compMatrixP.getMatrix().invert();
            if (invP == null)
                throw new MatrixException("Matrix P is singular, no inverse could be found!");
            if (compMatrixP.getMatrix().equals(invP))
                throw new MatrixException("The inverse of Matrix P is equal, please choose other parameters.");
            //data.setMatrixP(compMatrixP.getMatrix());
            data.setMatrixPInv(invP);
        }

        if (!compMatrixS.isModified()) {
            mce.randomMatrix(4, 4);
            //compMatrixS.setMatrix(s);
        } else {
            invS = compMatrixS.getMatrix().invert();
            if (invS == null)
                throw new MatrixException("Matrix S is singular, no inverse could be found!");
            if (compMatrixS.getMatrix().equals(invS))
                throw new MatrixException("The inverse of Matrix S is equal, please choose other parameters.");
            //data.setMatrixS(s);
            data.setMatrixSInv(invS);
        }

        textMatrixSInverse.setText(data.getMatrixSInv().toString());
        try {
            textMatrixPInverse.setText(data.getMatrixPInv().toString());
        } catch (NullPointerException e) {
            LogUtil.logError(e);
        }
    }

    /**
     * Bind model values to view elements using the JFace framework.
     * 
     */
    private void bindValues() {
        dbc = new DataBindingContext();

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textInput),
                BeanProperties.value(HammingData.class, "originalString", String.class).observe(mce.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textAsBinary),
                BeanProperties.value(HammingData.class, "binaryAsString", String.class).observe(mce.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textEncrypted),
                BeanProperties.value(HammingData.class, "codeAsString", String.class).observe(mce.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textDecoded),
                BeanProperties.value(HammingData.class, "binaryDecoded", String.class).observe(mce.getData())); //$NON-NLS-1$

        dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(textOutput),
                BeanProperties.value(HammingData.class, "decodedString", String.class).observe(mce.getData())); //$NON-NLS-1$
               
        dbc.bindValue(new InteractiveMatrixProperty().observe(compMatrixP),
                BeanProperties.value(HammingData.class, "matrixP", Matrix2D.class).observe(mce.getData())); //$NON-NLS-1$

        dbc.bindValue(new InteractiveMatrixProperty().observe(compMatrixS),
                BeanProperties.value(HammingData.class, "matrixS", Matrix2D.class).observe(mce.getData())); //$NON-NLS-1$
        
        
    }

}
