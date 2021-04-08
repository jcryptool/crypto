// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.rss;

import org.eclipse.osgi.util.NLS;

/**
 * Class containing all internationalized Strings used by the Plugin
 * 
 */
public class Descriptions extends NLS {
    private static final String BUNDLE_NAME = "org.jcryptool.visual.rss";

    // Overview
    public static String Overview;
    public static String StateKey;
    public static String StateNewMsg;
    public static String StateSignMsg;
    public static String StateVerOMsg;
    public static String StateRedMsg;
    public static String StateVerRMsg;
    public static String DataBoxKey;
    public static String DataBoxOrig;
    public static String DataBoxRed;

    public static String Yes;
    public static String No;
    public static String Number;
    public static String Redact;
    public static String Redactable;
    public static String Redacted;
    public static String MessagePart;
    // SET_KEY
    public static String SelectKeySize;
    public static String TakesLongTime;
    public static String GenerateKey;
    public static String Next;
    public static String NewMessage;
    // SET_MESSAGE
    public static String SetMessages;
    public static String AddMessagePart;
    public static String PasteYour;
    public static String PartHere;
    public static String ConfirmMessages;
    public static String AddMessageButtonLabel;
    // SIGN_MESSAGE
    public static String SelectFixedParts;
    public static String SelectPartsInfoText;
    public static String SignMessage;
    // VERIFY_MESSAGE
    public static String VerifyMessage;
    public static String VerifyMessageInfoText;
    public static String Message;
    public static String Reset;
    public static String ReturnButton;
    public static String RedactMessages;
    public static String IsVerified;
    // REDACT
    public static String VerifyRedacted;
    public static String Fix;
    // About
    public static String Help;
    public static String About;
    public static String TextSetKey;
    public static String TextSetMessage;
    public static String TextSignMessage;
    public static String TextVerifyMessage;
    public static String TextRedact;
    public static String TextVerifyRedacted;
    public static String TextViewKey;
    public static String TextViewMessage;
    public static String TextViewRedacted;
    // View
    public static String ViewKey;
    public static String ViewMessage;
    public static String ViewRedacted;
    // Key
    public static String KeyLength;
    public static String KeyType;
    public static String KeyPair;
    public static String PrivateKey;
    public static String PublicKey;
    public static String ProofPart;
    
    //Head
    public static String Title;
    public static String Description;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME + ".descriptions", Descriptions.class);
    }

    private Descriptions() {
    }

}
