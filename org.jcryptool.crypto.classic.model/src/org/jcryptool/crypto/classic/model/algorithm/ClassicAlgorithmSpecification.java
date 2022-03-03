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
package org.jcryptool.crypto.classic.model.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.core.operations.alphabets.AlphabetsManager;
import org.jcryptool.core.operations.keys.KeyVerificator;
import org.jcryptool.core.util.input.InputVerificationResult;
import org.jcryptool.crypto.ui.util.WidgetBubbleUIInputHandler;

/**
 * Specifies aspects of classic algorithms like plain text and cipher text combinations. Implementations
 * for specific algorithms may include key conversion (from String and to String) and other central functions
 * that are important for the algorithm and useful to have in a central, public place.
 * 
 * @author Simon L
 */
public class ClassicAlgorithmSpecification {

	
	public static final String RESULT_TYPE_NOKEY = "NOKEY";


	/**
	 * Validates, that the key is not blank. although, the key is not rejected, if blank, just a warning is put.
	 * This verificator returns a "fail" message when the key is zero length, which contains instructions about the key.
	 * These instructions can be set as argument.
	 */
	public static final KeyVerificator NOKEY(final String instructions){
		return new KeyVerificator() {
			@Override
			protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
				return key.length() > 0;
			}
			@Override
			protected InputVerificationResult getFailResult(String key, AbstractAlphabet alphabet) {
				return new InputVerificationResult() {
					@Override
					public boolean isStandaloneMessage() {
						return true;
					}
					@Override
					public MessageType getMessageType() {
						return InputVerificationResult.MessageType.INFORMATION;
					}
					@Override
					public String getMessage() {
						//key instructions to be inserted. Format: whole sentences.
						String mask = Messages.AbstractClassicCryptoPage_keyBalloonTipWithInstruction;
						return String.format(mask, instructions);
					}
					@Override
					public boolean isValid() {
						return true;
					}
					@Override
					public String toString() {return "NOKEY_FAIL";} //$NON-NLS-1$
					
					@Override
					public Object getResultType() {
						return RESULT_TYPE_NOKEY;
					}
				};
			}
		};
	}

	/**
	 * Validates, that the key is contained in the selected currentAlphabet.
	 */
	public static final KeyVerificator KEY_IN_ALPHABET = new KeyVerificator() {
		@Override
		protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
			for(int i=0; i<key.length(); i++) {
				if(! alphabet.contains(key.charAt(i))) {
					return false;
				}
			}
			return true;
		}

		@Override
		protected InputVerificationResult getFailResult(String key, AbstractAlphabet alphabet) {
			Character character = null;
			int i;
			for(i=0; i<key.length(); i++) {
				if(! alphabet.contains(key.charAt(i))) {
					character = key.charAt(i);
					break;
				}
			}
			return new InputVerificationResultKeyNotInAlphabet(character, alphabet, i);
		}
	};
	public static KeyVerificator NO_DOUBLETS = new KeyVerificator() {
		@Override
		protected boolean verifyKeyInput(String key, AbstractAlphabet alphabet) {
			Set<Character> occurence = new HashSet<Character>();
			for(int i=0; i<key.length(); i++) {
				if(occurence.contains(key.charAt(i))) return false; else {
					occurence.add(key.charAt(i));
				}
			}
			return true;
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
					return false;
				}
				@Override
				public int getMessagePersistenceCategory() {
					return (int)Math.round(WidgetBubbleUIInputHandler.STANDARD_TIP_SHOWTIME*0.6);
				}
				@Override
				public MessageType getMessageType() {
					return InputVerificationResult.MessageType.WARNING;
				}
				@Override
				public String getMessage() {
					return Messages.AdfgvxWizardPage_onlyoccuronce;
				}
				@Override
				public String getResultType() {
					return RESULT_TYPE_DOUBLET_IN_KEY; 
				}
			};
		}
	};
	public static final String RESULT_TYPE_DOUBLET_IN_KEY = "DOUBLET";
	
	
	/**
	 * @return the default cipher text currentAlphabet for this algorithm 
	 * (application in e. g. command line, wizards)
	 */
	public AbstractAlphabet getDefaultCipherTextAlphabet() {
		return getDefaultPlainTextAlphabet();
	}
	
	/**
	 * @return the default plain text currentAlphabet for this algorithm 
	 * (application in e. g. command line, wizards)
	 */
	public AbstractAlphabet getDefaultPlainTextAlphabet() {
		List<AbstractAlphabet> alphabets = getAvailablePlainTextAlphabets();
		if(alphabets.contains(AlphabetsManager.getInstance().getDefaultAlphabet())) {
			return AlphabetsManager.getInstance().getDefaultAlphabet();
		}
		return alphabets.size() > 0 ? alphabets.get(0) : AlphabetsManager.getInstance().getDefaultAlphabet();
	}
	
	/**
	 * This method specifies if the given combination of two alphabets is 
	 * suitable as input and output alphabets.
	 * 
	 * @param plainTextAlphabet
	 * @param cipherTextAlphabet
	 */
	public boolean isValidAlphabetCombination(AbstractAlphabet plainTextAlphabet, AbstractAlphabet cipherTextAlphabet) {
		return isValidPlainTextAlphabet(plainTextAlphabet) && isValidCipherTextAlphabet(cipherTextAlphabet) &&
		plainTextAlphabet.equals(cipherTextAlphabet);
	}

	/**
	 * @return whether a specified currentAlphabet can be accepted as plain text currentAlphabet. The currentAlphabet
	 * isn't necessarily required to be part of the list that is returned by {@link #getAvailablePlainTextAlphabets()}.
	 */
	public boolean isValidPlainTextAlphabet(AbstractAlphabet alpha) {
		return true;
	}
	
	/**
	 * @return whether a specified currentAlphabet can be accepted as cipher text currentAlphabet. The currentAlphabet
	 * isn't necessarily required to be part of the list that is returned by {@link #getAvailableCipherTextAlphabets()}.
	 */
	public boolean isValidCipherTextAlphabet(AbstractAlphabet alpha) {
		return isValidPlainTextAlphabet(alpha);
	}
	
	/**
	 * @return a list of alphabets that is a default set of choices for the cipher text currentAlphabet
	 */
	public List<AbstractAlphabet> getAvailableCipherTextAlphabets() {
		return getAvailablePlainTextAlphabets();
	}

	/**
	 * @return a list of alphabets that is a default set of choices for the plain text currentAlphabet
	 */
	public List<AbstractAlphabet> getAvailablePlainTextAlphabets() {
		ArrayList<AbstractAlphabet> result = new ArrayList<AbstractAlphabet>();
		for(AbstractAlphabet a: AlphabetsManager.getInstance().getAlphabets()) {
			if(isValidPlainTextAlphabet(a)) result.add(a);
		}

		return result;
	}

	/**
	 * Provides verificators for a String input for a key.
	 * This method is meant for usage when nothing else but key verificators have to be
	 * changed in the key input mechanism apart from the standard implementation.
	 *
	 * @return a list of key verificators
	 */
	public List<KeyVerificator> getKeyVerificators() {
		List<KeyVerificator> verificators = new LinkedList<KeyVerificator>();
		verificators.add(KEY_IN_ALPHABET);
		verificators.add(NOKEY(Messages.AbstractClassicCryptoPage_keyToolTip));
		return verificators;
	}
	
	/**
	 * @return if this algorithm should be allowed to use custom alphabets
	 */
	public boolean isAllowCustomAlphabetCreation() {
		return true;
	}
	
	/**
	 * @return whether this algorithm can be executed using the JCrypTool command line
	 */
	public boolean hasConsoleRepresentation() {
		//TODO: calculate dynamically by checking if the console extension point was used
		return true;
	}
	
}
