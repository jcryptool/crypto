//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2011, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.classic.doppelkasten.algorithm;

import java.util.regex.Pattern;

import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.crypto.classic.model.algorithm.ClassicAlgorithmSpecification;

/**
 * 
 * 
 * @author Simon L
 */
public class DoppelkastenAlgorithmSpecification extends
		ClassicAlgorithmSpecification {
	
	public static final String KEY_SEPARATOR = "SLGFLBSDFGKSDFGSDFGLK";

	@Override
	public boolean isValidPlainTextAlphabet(AbstractAlphabet alpha) {
		char[] one = alpha.getCharacterSet();
		char[] two = "ABCDEFGHIKLMNOPQRSTUVWXYZ".toCharArray(); //$NON-NLS-1$
		if(one.length == two.length)
		{
			for(int k=0; k<one.length; k++)
			{
				if(one[k] != two[k]) return false;
			}
		}
		else return false;
		return true;
	}
	
	public static String glueKeys(String key1, String key2) {
		return (key1 + KEY_SEPARATOR + key2); 
	}
	
	public static String[] unglueKeys(String gluedKeys) {
		return gluedKeys.split(Pattern.quote(KEY_SEPARATOR));
	}
	
	
	public char[] keyInputStringToDataobjectFormat(String key1, String key2) {
	    return glueKeys(key1, key2).toCharArray();
	}
	
	@Override
	public boolean isAllowCustomAlphabetCreation() {
		return false;
	}
	
}
