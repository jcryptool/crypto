// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.crypto.classic.model;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author SLeischnig
 */
public class ClassicCryptoModelPlugin extends AbstractUIPlugin {
    /** The plug-in ID. */
    public static final String PLUGIN_ID = "org.jcryptool.crypto.classic.model";
    
    public static File getBundleFile(String path) {
    	Bundle bundle = Platform.getBundle(PLUGIN_ID);
    	URL fileURL = bundle.getEntry(path);
    	File file = null;
    	try {
    	   URL resolvedFileURL = FileLocator.toFileURL(fileURL);
    	   URI resolvedURI = new URI(resolvedFileURL.getProtocol(), resolvedFileURL.getPath(), null);
    	   file = new File(resolvedURI);
    	} catch (URISyntaxException e1) {
    	    e1.printStackTrace();
    	} catch (IOException e1) {
    	    e1.printStackTrace();
    	}	
    	return file;
    }

}