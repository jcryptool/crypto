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

    public static String SignalEncryption_TabTitleOverView;
    public static String SignalEncryption_TabTitleRatchetView;
    
    public static String SignalEncryption_Title;
    public static String SignalEncryption_Description;
    
    public static String Name_Alice;
    public static String Name_Bob;
    /** Alice's name in genitive case */
    public static String Name_AliceGenitive;
    /** Bob's name in genitive case */
    public static String Name_BobGenitive;
    /** Alice's name followed by a space (for easy string concatenation) */
    public static String Name_Alice_Space;
    /** Bob's name followed by a space (for easy string concatenation) */
    public static String Name_Bob_Space;
    /** Alice's name in genitive case followed by a space (for easy string concatenation) */
    public static String Name_AliceGenitive_Space;
    /** Bob's name in genitive case followed by a space (for easy string concatenation) */
    public static String Name_BobGenitive_Space;
    
    public static String Overview_GroupTitleIdentities;
    public static String Overview_GroupTitleDoubleRatchet;
    public static String Overview_PreKeyBundle;
    public static String Overview_DiscardWarningTitle;
    public static String Overview_DiscardWarningMessage;
    public static String Overview_IdentityFingerprint;
    public static String Overview_generateIdentityBoth;
    public static String Overview_generateIdentityPerson;
    public static String Overview_IdentityPublicKey;
    public static String Overview_PreKey;
    public static String Overview_PreKeySignature;
	public static String Overview_DoubleRatchetOverview;
    public static String Overview_showDoubleRatchet;
    public static String Overview_QuestionX3DH;
    public static String Overview_AnswerX3DH;
    public static String Overview_QuestionPreKeyBundle;
    public static String Overview_AnswerPreKeyBundle;
    public static String Overview_QuestionDoubleRatchetSecurity;
    public static String Overview_AnswerDoubleRatchetSecurity;
    public static String Overview_QuestionDoubleRatchetInit;
    public static String Overview_AnswerDoubleRatchetInit;

    public static String DoubleRatchet_TopBarStatusSending;
    public static String DoubleRatchet_TopBarStatusReceiving;
    public static String DoubleRatchet_Step;
    public static String DoubleRatchet_sendingInitialMessage;
    public static String DoubleRatchet_sendingAnyMessage;
    public static String DoubleRatchet_waitingForInitialMessage;
    public static String DoubleRatchet_waitingForAnyMessage;

    //Table Values - Second Column, description of the keys
    public static String SignalEncryption_DescText_Diffie_Private;
    public static String SignalEncryption_DescText_Diffie_Public;
    public static String SignalEncryption_DescText_Shared;
    public static String SignalEncryption_DescText_Root;
    public static String SignalEncryption_DescText_Sending;
    public static String SignalEncryption_DescText_Receiving;
    public static String SignalEncryption_DescText_MsgKey;
    
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

    public static String SignalEncryption_btnName_Next;
    public static String SignalEncryption_btnName_Previous;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME + ".messages", Messages.class);
    }

    private Messages() {
    }
    
}
