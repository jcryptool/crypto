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
package org.jcryptool.crypto.classic.transposition.algorithm;

import java.util.Collections;
import java.util.Comparator;
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
public class TranspositionAlgorithmSpecification extends
		ClassicAlgorithmSpecification {

	public static final String RESULT_TYPE_KEY_NOEFFECT = "NOTCHANGING";
	public static KeyVerificator NOTCHANGING = new KeyVerificator() {
		@Override
		protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
			if(key.length() < 1) return true;
			if(NOFITTINGKEYFORM.verify(key, alphabet).isValid()) {
				TranspositionKey tKey = new TranspositionKey(key, alphabet.getCharacterSet());
				return tKey.isReallyEncrypting();
			} else return false;
			
		}
		@Override
		protected InputVerificationResult getFailResult(final String key, final AbstractAlphabet alphabet) {
			return new InputVerificationResult() {
				@Override
				public boolean isStandaloneMessage() {return true;}
				@Override
				public MessageType getMessageType() {return InputVerificationResult.MessageType.INFORMATION;}
				@Override
				public boolean isValid() {return true;}
				public int getMessagePersistenceCategory() {
					return InputVerificationResult.PERSISTENCE_FOREVER;
				};
				@Override
				public String getMessage() {
					String mask = org.jcryptool.crypto.classic.transposition.ui.Messages.TranspositionWizardPage_firstnotalter;
					return String.format(mask, new TranspositionKey(key, alphabet.getCharacterSet()).toStringOneRelative());
				}
				@Override
				public Object getResultType() {
					return RESULT_TYPE_KEY_NOEFFECT; 
				}
			};
		}
	};
	
	public static final String RESULT_TYPE_KEY_NOFITTINGKEYFORM = "NOFITTINGKEYFORM";
	public static KeyVerificator NOFITTINGKEYFORM = new KeyVerificator() {
		@Override
		protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
			if(key.length() < 1) return true;

			if(!TranspositionKey.generateKeyFromStringMode(key, alphabet.getCharacterSet()).mode.isSuccessful()) {
				return false;
			}
			return true;
		}
		@Override
		protected InputVerificationResult getFailResult(final String key, final AbstractAlphabet alphabet) {
			return new InputVerificationResult() {
				@Override
				public boolean isStandaloneMessage() {return false;}
				@Override
				public MessageType getMessageType() {return InputVerificationResult.MessageType.ERROR;}
				@Override
				public boolean isValid() {return false;}
				@Override
				public String getMessage() {
					String mask = org.jcryptool.crypto.classic.transposition.ui.Messages.TranspositionWizardPage_nogoodform;
					Character missingChar = TranspositionKey.generateKeyFromStringMode(key, alphabet.getCharacterSet()).notinalphaChar;
					return String.format(mask, missingChar==null?"null":String.valueOf(missingChar));
				}
				@Override
				public Object getResultType() {
					return RESULT_TYPE_KEY_NOFITTINGKEYFORM; 
				}
			};
		}
	};
	
	@Override
	public AbstractAlphabet getDefaultPlainTextAlphabet() {
		List<AbstractAlphabet> availableAlphabets = getAvailablePlainTextAlphabets();
        Collections.sort(availableAlphabets, new Comparator<AbstractAlphabet>() {
            @Override
			public int compare(AbstractAlphabet o1, AbstractAlphabet o2) {
                return Integer.valueOf(o1.getCharacterSet().length).compareTo(
                        Integer.valueOf(o2.getCharacterSet().length));
            }
        });

        return availableAlphabets.size() == 0 ? null : availableAlphabets.get(availableAlphabets.size() - 1);
	}

	@Override
	public List<KeyVerificator> getKeyVerificators() {
		List<KeyVerificator> verificators = new LinkedList<KeyVerificator>();
		verificators.add(NOFITTINGKEYFORM);
		verificators.add(NOKEY(org.jcryptool.crypto.classic.transposition.ui.Messages.TranspositionKeyInputComposite_key_instructions));
		verificators.add(NOTCHANGING);
		return verificators;
	}
	

	public char[] keyInputStringToDataobjectFormat(String keyInput, AbstractAlphabet alphabet, boolean inOrder, boolean outOrder) {
		TranspositionKey transpKey = new TranspositionKey(keyInput, alphabet.getCharacterSet());
		String keyOutString = transpKey.toUnformattedChars(alphabet);
		
		//format the key
		//first 2 chars -- read in/out order (1st char of currentAlphabet: true, all others: false)
		char[] key = keyOutString.toCharArray();
		char[] formattedKey = new char[key.length+2];
		
		formattedKey[0] = inOrder ? alphabet.getCharacterSet()[0] : alphabet.getCharacterSet()[1];
		formattedKey[1] = outOrder ? alphabet.getCharacterSet()[0] : alphabet.getCharacterSet()[1];
		System.arraycopy(key, 0, formattedKey, 2, key.length);
		
		return formattedKey;
	}
	
}
