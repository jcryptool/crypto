package org.jcryptool.analysis.fleissner.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

import org.jcryptool.analysis.fleissner.Activator;
import org.jcryptool.core.logging.utils.LogUtil;

public class FleissnerStatisticsTool {
	
//    public static double[] writeBinNgramFrequencies_binary(OutputStream file, int m, int nGramSize, double[] frequencies) throws FileNotFoundException, IllegalArgumentException
//    {
//        double size=0, fileSize =0;
//        try {
//            size = Math.pow(m, nGramSize)*8;
//            fileSize = file.available();
//        } catch (IOException e2) {
//            LogUtil.logError(Activator.PLUGIN_ID, e2);
//        }
//        if (fileSize != size)
//            throw new IllegalArgumentException("error 1");
//        double ngrams[] = new double[(int) Math.pow(m, nGramSize)];
//        ByteBuffer myByteBuffer = ByteBuffer.allocate(((int) size) * 8);
//        myByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
//        DoubleBuffer doubleBuffer = myByteBuffer.asDoubleBuffer();
//        try {            
//            ReadableByteChannel rbc = Channels.newChannel(file);
//            rbc.read(myByteBuffer);
//            rbc.close();
//            } catch (IOException e) {
//                LogUtil.logError(Activator.PLUGIN_ID, e);
//                throw new FileNotFoundException("error 2");
//            }   
//        doubleBuffer.get(ngrams);
//        return ngrams;
//    }
//    public static double[] loadBinNgramFrequencies_binary(InputStream file, int m, int nGramSize) throws FileNotFoundException, IllegalArgumentException
//    {
//        double size=0, fileSize =0;
//        try {
//            size = Math.pow(m, nGramSize)*8;
//            fileSize = file.available();
//        } catch (IOException e2) {
//            LogUtil.logError(Activator.PLUGIN_ID, e2);
//        }
//        if (fileSize != size)
//            throw new IllegalArgumentException("error 1");
//        double ngrams[] = new double[(int) Math.pow(m, nGramSize)];
//        ByteBuffer myByteBuffer = ByteBuffer.allocate(((int) size) * 8);
//        myByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
//        DoubleBuffer doubleBuffer = myByteBuffer.asDoubleBuffer();
//        try {            
//            ReadableByteChannel rbc = Channels.newChannel(file);
//            rbc.read(myByteBuffer);
//            rbc.close();
//            } catch (IOException e) {
//                LogUtil.logError(Activator.PLUGIN_ID, e);
//                throw new FileNotFoundException("error 2");
//            }   
//        doubleBuffer.get(ngrams);
//        return ngrams;
//    }
//    
//    public static String showNGramStatistics(double[] statistics, String alphabet, int n, Optional<String> contained) {
//    	StringBuilder builder = new StringBuilder();
//    	for (int statIdx = 0; statIdx < statistics.length; statIdx++) {
//    		String ngram = generateNGramAt(alphabet, n, statIdx);
//    		if (contained.isPresent() && ! ngram.contains(contained.get())) {
//    			continue;
//			}
//			builder.append(String.format("%s: %s\n", ngram, statistics[statIdx]));
//		}
//    	return builder.toString();   	
//    }
//    
//    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
//    	File file = new File("/home/snuc/Desktop/ngrams/de-4gram-nocs.bin");
//    	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜß";
//    	int n = 4;
//    	int m = alphabet.length();
//    	double[] data = loadBinNgramFrequencies_binary(new FileInputStream(file), m, n);
//    	System.out.println("n-grams which contain DE:");
//    	System.out.println(showNGramStatistics(data, alphabet, n, Optional.of("DE"))); // ngrams which contain DE for testing if everything checks out
//    	System.out.println("Smoothed would be, for samplesize=Int.Max_VALUE: log(1/samplesize) = log(1)-log(samplesize) ~ never seen): " + (Math.log(1)-Math.log(Integer.MAX_VALUE)));
//    	
//	}
//    
//    public static void writeStatistics(double[] statistics, File file) {
//    	
//    }
//    
//    public static String generateNGramAt(String alphabet, int n, int ngramIdx) {
//    	char[] ngram = new char[n];
//    	int alphasize = alphabet.length();
//    	int[] alphaIdxs = new int[n];
//    	int ngramCounter = ngramIdx;
//    	for (int i = 0; i < n; i++) {
//    		int restMagnitude = (int)(Math.round(Math.pow(alphasize, n-i-1)));
//    		int rest = ngramIdx % restMagnitude;
//    		int positionIdx = ((ngramCounter - rest) / restMagnitude);
//    		ngramCounter = ngramCounter - (positionIdx * restMagnitude);
//    		alphaIdxs[i] = positionIdx;
//		}
//		char[] result = new char[n];
//    	for (int i = 0; i < alphaIdxs.length; i++) {
//			result[i] = alphabet.charAt(alphaIdxs[i]);
//		}
//    	return String.valueOf(result);
//    }
//    
//	public static double getNgramValue(double[] statistics, String ngram, String alphabet)
//	{	
//		double value = 0;
//		int n = ngram.length();
//		int[] letterNumber = new int[n];
//
//		String digits = "0123456789";
//		String evaluationText = "";
//		boolean digit;
//
//		for (int k=0; k<n; k++) {	
//			for (int j=0;j<alphabet.length();j++) {
//				if ((ngram.charAt(k))==(alphabet.charAt(j))) {
//					letterNumber[k] = j;
//				}
//			}
//		}
//		int index = 0;
//		for (int l=0;l<letterNumber.length;l++) {
//			index += (letterNumber[l])*((int)  Math.pow(m, (letterNumber.length-1-l)));
//		}
//		if (TextValuator.ngrams[index]!=0) {
//			value += TextValuator.ngrams[index];
//		}
//
//		return -value;
//	}
    
}
