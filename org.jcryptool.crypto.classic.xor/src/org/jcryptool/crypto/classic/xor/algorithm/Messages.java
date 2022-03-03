// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.crypto.classic.xor.algorithm;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.jcryptool.crypto.classic.xor.algorithm.messages"; //$NON-NLS-1$
	public static String XorAlgorithmHandler_0;
	public static String XorAlgorithmHandler_1;
	public static String XorAlgorithmHandler_2;
	public static String XorCmd_keyDetailsFilepath;
	public static String XorCmd_keyDetailsString;
	public static String XorCmd_onlyOneKeyMsg;
	public static String XORConfiguration_0;
	public static String XORConfiguration_1;
	public static String XORConfiguration_2;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
