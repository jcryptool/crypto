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
package org.jcryptool.crypto.classic.vigenere.algorithm;

import org.jcryptool.core.operations.algorithm.classic.AbstractClassicAlgorithm;

/**
 * The VigenereAlgorithm extends the AbstractClassicAlgorithm.
 * @see org.jcryptool.core.operations.algorithm.classic.AbstractClassicAlgorithm.
 *
 * @author amro
 * @version 0.1
 */
public class VigenereAlgorithm extends AbstractClassicAlgorithm {

	public static final VigenereAlgorithmSpecification specification = new VigenereAlgorithmSpecification();
	
	/**
	 * Constructor
	 * The specific engine of the algorithm is assigned.
	 *
	 */
	public VigenereAlgorithm() {
		engine = new VigenereEngine();
	}

	/**
	 * This method takes the key data as a char array and
	 * generates from it the algorithm key as int array
	 * @param keyData the key data
	 * @return the generated key as int array
	 */
	@Override
	protected int[] generateKey(char[] keyData) {

		return alphaConv.charArrayToIntArray(keyData);
	}

    @Override
    public String getAlgorithmName() {
        return "Vigen\u00E8re"; //$NON-NLS-1$
    }

}
