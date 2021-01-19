package org.jcryptool.crypto.classic.model.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

import org.jcryptool.core.logging.utils.LogUtil;

public class NGramFrequencies {
	
	public final double[] frequencies;
	public final String alphabet;
	public final int n;
	
	public NGramFrequencies(double[] frequencies, String alphabet, int n) {
		super();
		this.frequencies = frequencies;
		this.alphabet = alphabet;
		this.n = n;
	}
	
	public double getFrequencyFor(String ngram) {
 		int index = NgramStatisticLogic.getIndexFor(ngram, alphabet);
 		if(ngram.length() != n) {
 			throw new NgramException(String.format("queried ngram frequency for %s on stats with different n = %s", ngram, n));
		}
 		NgramException.check(index < this.frequencies.length && index >= 0, String.format("N-gram %s is not in the alphabet %s", ngram, alphabet));
 		return this.frequencies[index];
	}
	public double getFrequencyFor(int[] ngram) {
 		if(ngram.length != n) {
 			throw new NgramException(String.format("queried ngram frequency for %s on stats with different n = %s", ngram, n));
		}
 		int index = NgramStatisticLogic.getIndexFor(ngram, alphabet);
 		NgramException.check(index < this.frequencies.length && index >= 0, String.format("N-gram %s is not in the alphabet %s", NgramStatisticLogic.generateNGramAt(alphabet, n, index), alphabet));
 		return this.frequencies[index];
	}
	
	public static NGramFrequencies parseGzipStream(InputStream is, int n) {
		try {
			GZIPInputStream unzipped = new GZIPInputStream(is);
			BufferedReader reader = new BufferedReader(new InputStreamReader(unzipped, "UTF-8"));
			String alphabet = reader.readLine();
			for (char x : alphabet.toCharArray()) {
				LogUtil.logError("Char: " + x);
			}
			double[] stats = NgramStatisticLogic.readTxtNgramFrequencies(reader, n, alphabet.length());
			return new NGramFrequencies(stats, alphabet, n);
		} catch (IOException e) {
			throw new NgramException(e);
		}
	}
	public static NGramFrequencies parseGzipFile(File file, int n) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			return parseGzipStream(is, n);
		} catch (IOException e) {
			throw new NgramException(e);
		} finally {
			if(is != null) { try { is.close(); } catch (IOException e) { e.printStackTrace(); } }
		}
	}

	public static NGramFrequencies parseGzipFile(URL resource, int n2) {
		InputStream is = null;
		try {
			is = resource.openStream();
			return parseGzipStream(is, n2);
		} catch (IOException e) {
			throw new NgramException(e);
		} finally {
			if(is != null) { try { is.close(); } catch (IOException e) { e.printStackTrace(); } }
		}
	}

}
