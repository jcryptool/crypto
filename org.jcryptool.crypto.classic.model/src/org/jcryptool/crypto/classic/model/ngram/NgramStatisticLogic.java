package org.jcryptool.crypto.classic.model.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.crypto.classic.model.ClassicCryptoModelPlugin;

public class NgramStatisticLogic {
	
    
    // expects exactly m^n lines of floating-point representations of log-normalized frequencies
    public static double[] readTxtNgramFrequencies(BufferedReader reader, int n, int m) throws IOException {
    	String line;
    	LogUtil.logError("m: " + m + "; n: "+ n);
    	int p = pow(m, n);
    	LogUtil.logError("p: " + p);
    	double[] result = new double[p];
    	LogUtil.logError("result.length" + result.length);
    	int idx = 0;
    	while ((line = reader.readLine()) != null) {
    		result[idx] = Double.parseDouble(line.trim());
    		idx++;
    		if (idx % 1000000 == 0)
    			LogUtil.logError("Index: " + idx);
		}
    	NgramException.check(idx == result.length, "ngram statistics file is incomplete; idx: " + idx + "; result.length " + result.length);
    	checkDataConsistency(result.length, n, m);
    	return result;
    }
    
    public static String showNGramStatistics(double[] statistics, String alphabet, int n, Optional<String> contained) {
    	StringBuilder builder = new StringBuilder();
    	for (int statIdx = 0; statIdx < statistics.length; statIdx++) {
    		String ngram = generateNGramAt(alphabet, n, statIdx);
    		if (contained.isPresent() && ! ngram.contains(contained.get())) {
    			continue;
			}
			builder.append(String.format("%s: %s\n", ngram, statistics[statIdx]));
		}
    	return builder.toString();   	
    }
    
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
    	File file = new File("/home/snuc/Desktop/ngrams/de-4gram-nocs.bin");
    	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß";
    	int n = 4;
    	int m = alphabet.length();
    	System.out.println(getIndexFor("THE", NgramStore.en_3_nospace.alphabet()));
    	NGramFrequencies stats = NgramStore.getInstance().getBuiltinFrequenciesFor(NgramStore.en_3_nospace);
    	double freq1 = stats.getFrequencyFor("THE");
    	double freq2 = stats.getFrequencyFor("ORT");
    	double freq3 = stats.getFrequencyFor("ZZZ");
    	System.out.println(String.format("%s,%s,%s", freq1, freq2, freq3));
	}
    
    public static void writeStatistics(double[] statistics, File file) {
    	
    }
    
    public static String generateNGramAt(String alphabet, int n, int ngramIdx) {
    	char[] ngram = new char[n];
    	int alphasize = alphabet.length();
    	int[] alphaIdxs = new int[n];
    	int ngramCounter = ngramIdx;
    	for (int i = 0; i < n; i++) {
    		int restMagnitude = (int)(Math.round(Math.pow(alphasize, n-i-1)));
    		int rest = ngramIdx % restMagnitude;
    		int positionIdx = ((ngramCounter - rest) / restMagnitude);
    		ngramCounter = ngramCounter - (positionIdx * restMagnitude);
    		alphaIdxs[i] = positionIdx;
		}
		char[] result = new char[n];
    	for (int i = 0; i < alphaIdxs.length; i++) {
			result[i] = alphabet.charAt(alphaIdxs[i]);
		}
    	return String.valueOf(result);
    }

	public static int getIndexFor(int[] ngram, String alphabet) {
		int m = alphabet.length();
		int n = ngram.length;
		int result = 0;
		for (int i = 0; i < n; i++) {
			int indexOf = ngram[i];
			int weight = pow(m, (n-i-1));
			result += weight * indexOf;
		}
		return result;
	}
	public static int getIndexFor(String ngram, String alphabet) {
//		checkDataConsistency(ngram, frequencies, alphabet);
		int m = alphabet.length();
		int n = ngram.length();
		char[] nchars = ngram.toCharArray();
		int result = 0;
		for (int i = 0; i < n; i++) {
			char nc = nchars[i];
			int indexOf = alphabet.indexOf(nc);
			int weight = pow(m, (n-i-1));
			result += weight * indexOf;
		}
		return result;
	}

	private static void checkDataConsistency(int freqEntries, int n, int m) {
		NgramException.check(n>1, "n > 1");
		NgramException.check(m>0, "len(alphabet) > 0");
		NgramException.check(freqEntries == pow(m, n), "len(frequencies) == len(alphabet)^n");
	}

	private static void checkDataConsistency(String ngram, int freqEntries, String alphabet) {
		int n = ngram.length();
		int m = alphabet.length();
		NgramException.check(n>1, "n > 1");
		NgramException.check(m>0, "len(alphabet) > 0");
		NgramException.check(freqEntries == pow(alphabet.length(), n), "len(frequencies) == len(alphabet)^n");
		// check that every ngram character is in the alphabet
		checkNgramInAlphabet(ngram, alphabet);
	}

	private static void checkNgramInAlphabet(String ngram, String alphabet) {
		for(char nc: ngram.toCharArray()) {
			boolean contained = false;
			for(char ac: alphabet.toCharArray()) {
				if (nc == ac) { contained = true; break; }				
			}
			NgramException.check(contained, String.format("ngram character %s must be contained in alphabet %s", nc, alphabet));
		}
	}
	
	private static int pow(int base, int exp) {
		return (int) Math.round(Math.pow(base, exp));
	}

    
}