// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.gsrss;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.jcryptool.visual.gsrss.messages";
	
	public static String Title;
	public static String Description;
	
	public static String SignatureWizard_Title;
	public static String SignatureWizard_header;
	public static String SignatureWizard_WindowTitle;
	public static String SignatureWizard_grpSignatures;
	public static String GsRss;
	public static String SignatureWizard_Usage;
	public static String SignatureWizard_Usage2;
	public static String SignatureWizard_FurtherInfoInOnlineHelp;
	public static String SignatureWizard_labelKey;
	public static String SignatureWizard_noKeysHint;
	public static String SigComposite_btnSignature;


	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
