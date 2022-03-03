//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2012, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.ui.textsource;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.jcryptool.crypto.ui.textsource.messages"; //$NON-NLS-1$
	public static String TextInputWithSource_editor;
	public static String TextInputWithSource_file;
	public static String TextInputWithSource_manualInput;
	public static String TextInputWithSource_manualInput_source;
	public static String TextInputWithSource_notopenedanymore;
	public static String TextInputWithSourceDisplayer_editorOpenHint;
	public static String TextInputWithSourceDisplayer_fileOpenHint;
	public static String TextInputWithSourceDisplayer_manualinput;
	public static String TextInputWithSourceDisplayer_originLabelBeginning;
	public static String TextInputWithSourceDisplayer_userinputOpenHint;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
