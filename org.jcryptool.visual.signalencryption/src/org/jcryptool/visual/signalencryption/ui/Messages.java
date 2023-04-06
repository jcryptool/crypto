// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2020, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.osgi.util.NLS;

/**
 * This class defines variables, which are used in the GUI. The values of this variables are defined
 * in the messages.properties and messages_de.properties. This is an easy way to change text printed
 * in the GUI, without doing anything in the actual code.
 * 
 * @author Dominik Fuereder
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.jcryptool.visual.signalencryption.ui";

    
    /**
     * 
     */
    
    //Overview Strings - All text variables which can be found in the tab "Overview"
    
    //Top title

//    public static String SignalEncryption_TabTitle1;
//    public static String SignalEncryption_TabDesc1;

    
    //Table Titles
    public static String SignalEncryption_TblTitel_Key;
    public static String SignalEncryption_TblTitel_Description;
    public static String SignalEncryption_TblTitel_ValuesAlice;
    public static String SignalEncryption_TblTitel_ValuesBob;
    
    //Table Values - First Column, the names of the keys
    public static String SignalEncryption_KeyName_Diffie_Private;
    public static String SignalEncryption_KeyName_Diffie_Public;
    public static String SignalEncryption_KeyName_Shared;
    public static String SignalEncryption_KeyName_Root;
    public static String SignalEncryption_KeyName_Sending;
    public static String SignalEncryption_KeyName_Receiving;
    public static String SignalEncryption_KeyName_MsgKey;
    
    //Table Values - Second Column, description of the keys
    public static String SignalEncryption_DescText_Diffie_Private;
    public static String SignalEncryption_DescText_Diffie_Public;
    public static String SignalEncryption_DescText_Shared;
    public static String SignalEncryption_DescText_Root;
    public static String SignalEncryption_DescText_Sending;
    public static String SignalEncryption_DescText_Receiving;
    public static String SignalEncryption_DescText_MsgKey;
    
    
    //Button texts
    public static String SignalEncryption_btn_generateBoth;
    public static String SignalEncryption_btn_generateAlice;
    public static String SignalEncryption_btn_generateBob;
    
    //Dummy Text for not implemented Keys
    public static String SignalEncryption_Dummy;
    
    
    //Double Ratchet Strings - All text variables which can be found in the tab "Double Ratchet"
    public static String SignalEncryption_btnName_Alice;
    public static String SignalEncryption_btnName_Bob;

    public static String SignalEncryption_alice_AlgorithmGroupDescription;
    public static String SignalEncryption_bob_AlgorithmGroupDescription;
    public static String SignalEncryption_stepGroupDescription;
    public static String SignalEncryption_DiffieHellmanGroupDescription;
    public static String SignalEncryption_RootChainDescription;
    public static String SignalEncryption_SendingChainDescription;
    public static String SignalEncryption_ReceivingChainDescription;
    public static String SignalEncryption_MessageboxDescription;
    
    public static String SignalEncryption_aliceDiffieHellmanLabel1;
    public static String SignalEncryption_aliceDiffieHellmanLabel2;
    public static String SignalEncryption_aliceDiffieHellmanLabel3;
    public static String SignalEncryption_bobDiffieHellmanLabel1;
    public static String SignalEncryption_bobDiffieHellmanLabel2;
    public static String SignalEncryption_bobDiffieHellmanLabel3;
    
    public static String SignalEncryption_aliceRootChainLabel1;
    public static String SignalEncryption_aliceRootChainLabel2;
    public static String SignalEncryption_aliceRootChainLabel3;
    public static String SignalEncryption_bobRootChainLabel1;
    public static String SignalEncryption_bobRootChainLabel2;
    public static String SignalEncryption_bobRootChainLabel3;
    
    public static String SignalEncryption_aliceSendingChainLabel1;
    public static String SignalEncryption_aliceSendingChainLabel2;
    public static String SignalEncryption_aliceSendingChainLabel3;
    public static String SignalEncryption_aliceSendingChainLabel4;
    public static String SignalEncryption_aliceSendingChainLabel5;
    public static String SignalEncryption_bobSendingChainLabel1;
    public static String SignalEncryption_bobSendingChainLabel2;
    public static String SignalEncryption_bobSendingChainLabel3;
    public static String SignalEncryption_bobSendingChainLabel4;
    public static String SignalEncryption_bobSendingChainLabel5;
    
    public static String SignalEncryption_aliceDefaultMessage;
    public static String SignalEncryption_bobDefaultMessage;
    
    public static String SignalEncryption_aliceReceivingChainLabel1;
    public static String SignalEncryption_aliceReceivingChainLabel2;
    public static String SignalEncryption_aliceReceivingChainLabel3;
    public static String SignalEncryption_aliceReceivingChainLabel4;
    public static String SignalEncryption_aliceReceivingChainLabel5;
    public static String SignalEncryption_bobReceivingChainLabel1;
    public static String SignalEncryption_bobReceivingChainLabel2;
    public static String SignalEncryption_bobReceivingChainLabel3;
    public static String SignalEncryption_bobReceivingChainLabel4;
    public static String SignalEncryption_bobReceivingChainLabel5;

    public static String SignalEnrcyption_messageboxDescription;
    public static String SignalEncryption_aliceDescriptionText0;
    public static String SignalEncryption_bobDescriptionText0;
    public static String SignalEncryption_stepText1;
    public static String SignalEncryption_stepText2;
    public static String SignalEncryption_stepText3;
    public static String SignalEncryption_stepText4;
    public static String SignalEncryption_aliceStepText5;
    public static String SignalEncryption_bobStepText5;
    public static String SignalEncryption_stepText6;
    public static String SignalEncryption_stepText7;
    public static String SignalEncryption_stepText8;
    public static String SignalEncryption_stepText9;


    public static String SignalEncryption_TabTitle1;


    public static String SignalEncryption_TabDesc1;


    public static String SignalEncryption_TabTitle2;


    public static String SignalEncryption_TabDesc2;

    public static String SignalEncryption_btnName_Next;
    public static String SignalEncryption_btnName_Previous;







    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME + ".messages", Messages.class);
    }

    private Messages() {
    }
}
