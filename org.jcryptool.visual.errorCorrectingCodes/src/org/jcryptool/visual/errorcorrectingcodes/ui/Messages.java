// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.errorcorrectingcodes.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.jcryptool.visual.errorcorrectingcodes.ui.messages"; //$NON-NLS-1$
    public static String EccMainView_ztabGeneralText;
	public static String EccMainView_ztabHammingText;
	public static String EccMainView_ztabMcElieceText;
	public static String GeneralEccView_btnNextStep;
    public static String GeneralEccView_btnPrev;
    public static String GeneralEccView_btnReset;
    public static String GeneralEccView_grpErrorCode;
    public static String GeneralEccView_grpReceiver;
    public static String GeneralEccView_grpSenderStep;
    public static String GeneralEccView_grpTextInfo;
    public static String GeneralEccView_lblHeader;
    public static String GeneralEccView_lblTextDecoded;
    public static String GeneralEccView_lblTextEncode;
    public static String GeneralEccView_lblTextOriginal;
    public static String GeneralEccView_textHeader;
    public static String GeneralEccView_textInfo_step1;
    public static String GeneralEccView_textInfo_step2;
    public static String GeneralEccView_textInfo_step3;
    public static String GeneralEccView_textInfo_step4;
    public static String GeneralEccView_textInfo_step5;
    public static String HammingCryptoView_lblHeader;
    public static String HammingCryptoView_textHeader;
    public static String HammingCryptoView_btnGeneratePrivateKey;
    public static String HammingCryptoView_grpInverseMatrices;
    public static String HammingCryptoView_grpEncryption;
    public static String HammingCryptoView_grpDecryption;
    public static String HammingCryptoView_grpPrivateKey;
    public static String HammingCryptoView_grpPublicKey;
    public static String HammingCryptoView_grpTextInfo;
    public static String HammingCryptoView_lblEncrypt;
    public static String HammingCryptoView_lblOutput;
    public static String HammingCryptoView_lblTextOriginal;
    public static String HammingCryptoView_step1;
    public static String HammingCryptoView_step2;
    public static String HammingCryptoView_step3;
    
    public static String McElieceView_errorParamsTitle;
    public static String McElieceView_errorParams;
    public static String McElieceView_errorCipher;
    
    public static String McElieceView_errorEncryptionFailedTitle;
    public static String McElieceView_errorEncryptionFailed;
    public static String McElieceView_errorDecryptionFailedTitle;
    public static String McElieceView_errorDecryptionFailed;
    public static String McElieceView_errorNoParametersSelectedTitle;
    public static String McElieceView_errorNoParametersSelected;
    
    public static String McElieceView_btnDecrypt;
    public static String McElieceView_btnEncrypt;
    public static String McElieceView_btnFillKey;
    public static String McElieceView_grpAlgorithmInfo;
    public static String McElieceView_grpInput;
    public static String McElieceView_grpKeyParams;
    public static String McElieceView_grpOutput;
    public static String McElieceView_lblHeader;
    public static String McElieceView_lblPublicKey;
    public static String McElieceView_textHeader;
    
    public static String McElieceView_filename;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
