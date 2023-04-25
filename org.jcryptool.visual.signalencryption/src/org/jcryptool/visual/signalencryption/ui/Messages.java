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
 * This class defines variables, which are used in the GUI. The values of this
 * variables are defined in the messages.properties and messages_de.properties.
 * This is an easy way to change text printed in the GUI, without doing anything
 * in the actual code.
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
    public static String Name_AlicePronoun;
    public static String Name_AlicePronounPossessive;
    public static String Name_BobPronoun;
    public static String Name_BobPronounPossessive;
    /**
     * Alice's name in genitive case followed by a space (for easy string
     * concatenation)
     */
    public static String Name_AliceGenitive_Space;
    /**
     * Bob's name in genitive case followed by a space (for easy string
     * concatenation)
     */
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

    public static String DoubleRatchet_buttonNext;
    public static String DoubleRatchet_buttonBack;

    public static String DoubleRatchet_Step1Initial;
    public static String DoubleRatchet_Step2Initial;
    public static String DoubleRatchet_Step3Initial;
    public static String DoubleRatchet_Step4Initial;
    public static String DoubleRatchet_Step5SendingInitial;
    public static String DoubleRatchet_Step5ReceivingInitial;
    public static String DoubleRatchet_Step6Initial;
    public static String DoubleRatchet_Step7Initial;
    public static String DoubleRatchet_Step8Initial;
    public static String DoubleRatchet_Step9Initial;

    public static String DoubleRatchet_Step1;
    public static String DoubleRatchet_Step2;
    public static String DoubleRatchet_Step3;
    public static String DoubleRatchet_Step4;
    public static String DoubleRatchet_Step5Sending;
    public static String DoubleRatchet_Step5Receiving;
    public static String DoubleRatchet_Step6;
    public static String DoubleRatchet_Step7;
    public static String DoubleRatchet_Step8;
    public static String DoubleRatchet_Step9;

    public static String DoubleRatchet_TopBarStatusSending;
    public static String DoubleRatchet_TopBarStatusReceiving;
    public static String DoubleRatchet_Step;
    public static String DoubleRatchet_sendingInitialMessage;
    public static String DoubleRatchet_sendingAnyMessage;
    public static String DoubleRatchet_waitingForInitialMessage;
    public static String DoubleRatchet_waitingForAnyMessage;

    public static String DoubleRatchet_DiffieHellmanLabelTop;
    public static String DoubleRatchet_DiffieHellmanLabelMid;
    public static String DoubleRatchet_DiffieHellmanLabelBot;
    public static String DoubleRatchet_RootChainLabelTop;
    public static String DoubleRatchet_RootChainLabelMid;
    public static String DoubleRatchet_RootChainLabelBot;
    public static String DoubleRatchet_SendingChainLabelTop;
    public static String DoubleRatchet_SendingChainLabelMid;
    public static String DoubleRatchet_SendingChainLabelBot;
    public static String DoubleRatchet_ReceivingChainLabelTop;
    public static String DoubleRatchet_ReceivingChainLabelMid;
    public static String DoubleRatchet_ReceivingChainLabelBot;
    public static String DoubleRatchet_ChainLabelConst;
    public static String DoubleRatchet_MessageKeyLabel;

    public static String DoubleRatchet_TypeEcPublic;
    public static String DoubleRatchet_TypeEcPrivate;
    public static String DoubleRatchet_TypeSharedSecret;
    public static String DoubleRatchet_TypeChainKey;
    public static String DoubleRatchet_TypeNewChainKey;
    public static String DoubleRatchet_TypeRootChainKey;
    public static String DoubleRatchet_TypeRootOutput;
    public static String DoubleRatchet_TypeNewRootChainKey;
    public static String DoubleRatchet_TypeBytes;
    public static String DoubleRatchet_TypeSymmAes;
    public static String DoubleRatchet_TypeSymmCount;
    public static String DoubleRatchet_TypeSymmIv;
    public static String DoubleRatchet_TypeSymmMac;

    public static String DoubleRatchet_stepGroupDescription;
    public static String DoubleRatchet_DiffieHellmanGroupDescription;
    public static String DoubleRatchet_RootChainDescription;
    public static String DoubleRatchet_SendingChainDescription;
    public static String DoubleRatchet_ReceivingChainDescription;
    public static String DoubleRatchet_MessageBoxDescription;
    public static String DoubleRatchet_MessageDecryptionDescription;
    public static String DoubleRatchet_DefaultPlainText;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME + ".messages", Messages.class);
    }

    private Messages() {
    }
}
