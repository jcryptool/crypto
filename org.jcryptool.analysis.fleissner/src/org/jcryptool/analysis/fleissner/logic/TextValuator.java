// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.fleissner.logic;

import java.io.FileNotFoundException;

import org.jcryptool.crypto.classic.model.ngram.NGramFrequencies;

public class TextValuator {

	public static NGramFrequencies ngrams;
	private String alphabet;
	private int n, m;

	/**
	 * Sets Alphabet for parameter language and saves text statistics to give and
	 * size of statistics for text valuation
	 * 
	 * @param statistics
	 * @param n
	 * @throws FileNotFoundException
	 */
	public TextValuator(NGramFrequencies statistics, int n) throws FileNotFoundException {
		this.n = n;
		TextValuator.ngrams = statistics;

		alphabet = statistics.alphabet;
		m = alphabet.length();
	}

	/**
	 * Evaluates parameter text by weighing all nGrams of the text and calculating
	 * the overall weight of a text
	 * 
	 * @param text
	 * @return value of the text
	 */
	public double evaluate(String text) {
		double value = 0;
		int[] letterNumber = new int[n];
		String digits = "0123456789";
		String evaluationText = "";
		boolean digit;
		for (int j = 0; j < text.length(); j++) {
			digit = false;
			for (int i = 0; i < digits.length(); i++) {
				if (text.charAt(j) == digits.charAt(i))
					digit = true;
			}
			if (digit == false)
				evaluationText += text.charAt(j);
		}
//		System.out.println("EvaluationText: "+evaluationText);
		int textLength = evaluationText.length();
		for (int i = 0; i < textLength - n; i++) {
			String ngram = evaluationText.substring(i, i + n);
			value += ngrams.getFrequencyFor(ngram);
//			for (int k = 0; k < n; k++) {
//				for (int j = 0; j < alphabet.length(); j++) {
//					if ((ngram.charAt(k)) == (alphabet.charAt(j))) {
//						letterNumber[k] = j;
//					}
//				}
//			}
//			int index = 0;
//			for (int l = 0; l < letterNumber.length; l++) {
//				index += (letterNumber[l]) * ((int) Math.pow(m, (letterNumber.length - 1 - l)));
//			}
//			if (TextValuator.ngrams[index] != 0) {
//				value += TextValuator.ngrams[index];
//				value += ngrams.getFrequencyFor(ngram)
//			}
		}
		return -value;
	}
}