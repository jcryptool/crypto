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
package org.jcryptool.crypto.classic.xor.algorithm;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.core.operations.keys.KeyVerificator;
import org.jcryptool.core.util.input.InputVerificationResult;
import org.jcryptool.crypto.classic.model.algorithm.ClassicAlgorithmSpecification;

/**
 * 
 * 
 * @author Simon L
 */
public class XorAlgorithmSpecification extends ClassicAlgorithmSpecification {

	public static int log2(int N)
    {
        return (int)(Math.log(N) / Math.log(2));
    }

	@Override
	public boolean isValidPlainTextAlphabet(AbstractAlphabet alpha) {
		int log2 = log2(alpha.getCharacterSet().length);
		int supposedLen = (int) Math.round(Math.pow(2, log2));
		if (supposedLen == alpha.getCharacterSet().length) {
			return true; // alphabet is a power of 2
		}
		return false;
	}
	
	
	public char[] keyInputStringToDataobjectFormat(String keyInput) {
		return keyInput == null ? "".toCharArray() : keyInput.toCharArray();
	}

	/**
	 * @return the verificators for the key input, where the key is a file name.
	 */
	public List<KeyVerificator> getKeyFileVerificators() {
		List<KeyVerificator> verificators = new LinkedList<KeyVerificator>();
		verificators.add(new KeyVerificator() {
			
			@Override
			protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
				File f = new File(key);
				return f.exists();
			}
			
			@Override
			protected InputVerificationResult getFailResult(String key,
					AbstractAlphabet alphabet) {
				return new InputVerificationResult() {
					@Override
					public boolean isValid() {
						return false;
					}
					@Override
					public boolean isStandaloneMessage() {
						return true;
					}
					@Override
					public MessageType getMessageType() {
						return InputVerificationResult.MessageType.ERROR;
					}
					@Override
					public String getMessage() {
						return "The key file does not exist.";
					}
				};
			}
		});
		
		return verificators;
	}
	
	@Override
	public boolean isAllowCustomAlphabetCreation() {
		return false;
	}
	
}
