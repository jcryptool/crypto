package org.jcryptool.visual.rabin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Collectors;


public class Rabin {
	
	private BigInteger n;
	private BigInteger p;
	private BigInteger q;
	//private int blocklength;
	//private int bytesPerBlock;
	//private String padding = "20";
	private String padding;
	//private Charset charset = StandardCharsets.UTF_8;
	private Charset charset;
	private Random rng = new SecureRandom();
	private BigInteger TWO = BigInteger.valueOf(2);
	private BigInteger THREE = BigInteger.valueOf(3);
	private BigInteger FOUR = BigInteger.valueOf(4);
	
	
	
	
	/**
	 * 
	 * @param n
	 * @param p
	 * @param q
	 * @param padding
	 * @param charset
	 */
	public Rabin(BigInteger n, BigInteger p, BigInteger q, String padding, Charset charset) {
		this.setN(n);
		this.setP(p);
		this.setQ(q);
		this.setPadding(padding);
		this.setCharset(charset);
	}
	
	/**
	 * default constructor
	 */
	public Rabin() {
		
	}
	
	
	
	
	
	
	
	
	/**
	 * @return n
	 */
	public BigInteger getN() {
		return n;
	}





	/**
	 * @param n the n to set
	 */
	public void setN(BigInteger n) {
		this.n = n;
	}





	/**
	 * @return p
	 */
	public BigInteger getP() {
		return p;
	}





	/**
	 * @param p the p to set
	 */
	public void setP(BigInteger p) {
		this.p = p;
	}





	/**
	 * @return q
	 */
	public BigInteger getQ() {
		return q;
	}





	/**
	 * @param q the q to set
	 */
	public void setQ(BigInteger q) {
		this.q = q;
	}





	/**
	 * @return padding
	 */
	public String getPadding() {
		return padding;
	}





	/**
	 * @param padding the padding to set
	 */
	public void setPadding(String padding) {
		this.padding = padding;
	}





	/**
	 * @return charset
	 */
	public Charset getCharset() {
		return charset;
	}





	/**
	 * @param charset the charset to set
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}


	
	/**
	 * check whether passed a is a suitable prime, i.e. is prime plus a = 3 mod 4
	 * @param a
	 * @return true if a is suitable prime, else false
	 */
	public boolean isSuitablePrime(BigInteger a) {
		return a.isProbablePrime(1000) 
				&& a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3));		
	}
	
	
	/**
	 * check if a * b > 256 = N
	 * @param a
	 * @param b
	 * @return true if a * b > 256 = N, else false
	 */
	public boolean isCompositeSuitable(BigInteger a, BigInteger b) {
		return a.multiply(b).compareTo(BigInteger.valueOf(256)) > 0;
	}
	
	
	
	/**
	 * check if p is appropriate prime
	 * @param p
	 * @param lowLim
	 * @param upLim
	 * @return true if p >= lowlim && p <= uplim && p is suitable prime, else false
	 */
	public boolean isAppropriatePrime(BigInteger p, BigInteger lowLim, BigInteger upLim) {
		return p.compareTo(lowLim) >= 0 
				&& p.compareTo(upLim) <= 0 
				&& isSuitablePrime(p);				
	}



	/**
	 * for testing and debugging
	 * @param bitLength
	 * @return N, p, q
	 */
	public BigInteger[] generateKey(int bitLength) {
		BigInteger p = blumPrime(bitLength / 2);
		BigInteger q = blumPrime(bitLength / 2);
	    BigInteger N = p.multiply(q);
	    return new BigInteger[] {N, p, q};
	}
	
	
	/**
	 * for test and debugging
	 * @return n, p, q
	 */
	public static BigInteger[] generateKey() {
		BigInteger p = BigInteger.valueOf(7);
		BigInteger q = BigInteger.valueOf(11);
		BigInteger n = p.multiply(q);
		return new BigInteger[] {n, p, q};
	}
	
	
	/**
	 * encrypt a message m
	 * @param m
	 * @return ciphertext c = m^2 mod N
	 */
	public BigInteger encrypt(BigInteger m) {
		return m.modPow(TWO, n);
	}
	
	
	/**
	 * compute square root mod p of ciphertext c
	 * @param c
	 * @return square root mod p of ciphertext c
	 */
	public BigInteger getSquarerootP(BigInteger c) {
		BigInteger ret = c.modPow(p.add(BigInteger.ONE).divide(FOUR), p);
		return ret;
	}
	
	
	
	
	/**
	 * compute square root mod q of ciphertext c
	 * @param c
	 * @return cquare root mod q of ciphertext c
	 */
	public BigInteger getSquarerootQ(BigInteger c) {
		BigInteger ret = c.modPow(q.add(BigInteger.ONE).divide(FOUR), q	);
		return ret;
	}
	
	
	
	/**
	 * compute inverse elements of p and q
	 * @return inverse elements p^(-1) = y_p and q^(-1) = y_q
	 */
	public BigInteger[] getInverseElements() {
		BigInteger[] ret = Gcd(p, q);
		BigInteger y_p = ret[1];
		BigInteger y_q = ret[2];
		return new BigInteger[] {y_p, y_q};
	}
	
	
	
	/**
	 * compute v = y_p * p * m_q mod N
	 * @param y_p
	 * @param m_q
	 * @return v
	 */
	public BigInteger getV(BigInteger y_p, BigInteger m_q) {
		BigInteger ret = y_p.multiply(p).multiply(m_q).mod(n);
		return ret;
	}
	
	
	/**
	 * compute w = y_q * q * m_p mod N
	 * @param y_q
	 * @param m_p
	 * @return w
	 */
	public BigInteger getW(BigInteger y_q, BigInteger m_p) {
		BigInteger ret = y_q.multiply(q).multiply(m_p).mod(n);
		return ret;
	}
	
	
	
	/**
	 * compute possible plaintexts m1, m2, m3, m4 given v and w
	 * @param v
	 * @param w
	 * @return m1, m2, m3, m4
	 */
	public BigInteger[] getPlaintexts(BigInteger v, BigInteger w) {
		BigInteger m1 = v.add(w).mod(n);
		BigInteger m2 = v.subtract(w).mod(n);
		BigInteger m3 = w.subtract(v).mod(n);
		BigInteger m4 = v.add(w).negate().mod(n);
		
		return new BigInteger[] {m1, m2, m3, m4};
	}
	
	
	/**
	 * compute possible plaintexts for testing and debugging
	 * @param c
	 * @return possible plaintexts
	 */
	public BigInteger[] decrypt(BigInteger c) {
		BigInteger N = p.multiply(q);
        BigInteger p1 = c.modPow(p.add(BigInteger.ONE).divide(FOUR), p);
        BigInteger p2 = p.subtract(p1);
        BigInteger q1 = c.modPow(q.add(BigInteger.ONE).divide(FOUR), q);
        BigInteger q2 = q.subtract(q1);
  
        BigInteger[] ext = Gcd(p, q);
        BigInteger y_p = ext[1];
        BigInteger y_q = ext[2];
  
        BigInteger d1 = y_p.multiply(p).multiply(q1).add(y_q.multiply(q).multiply(p1)).mod(N);
        BigInteger d2 = y_p.multiply(p).multiply(q2).add(y_q.multiply(q).multiply(p1)).mod(N);
        BigInteger d3 = y_p.multiply(p).multiply(q1).add(y_q.multiply(q).multiply(p2)).mod(N);
        BigInteger d4 = y_p.multiply(p).multiply(q2).add(y_q.multiply(q).multiply(p2)).mod(N);
  
        return new BigInteger[] { d1, d2, d3, d4 };
	}
	
	
	
	/**
	 * compute gcd
	 * @param a
	 * @param b
	 * @return gcd
	 */
	public BigInteger[] Gcd(BigInteger a, BigInteger b) {
		BigInteger s = BigInteger.ZERO;
        BigInteger old_s = BigInteger.ONE;
        BigInteger t = BigInteger.ONE;
        BigInteger old_t = BigInteger.ZERO;
        BigInteger r = b;
        BigInteger old_r = a;
        while (!r.equals(BigInteger.ZERO)) {
            BigInteger q = old_r.divide(r);
            BigInteger tr = r;
            r = old_r.subtract(q.multiply(r));
            old_r = tr;
  
            BigInteger ts = s;
            s = old_s.subtract(q.multiply(s));
            old_s = ts;
  
            BigInteger tt = t;
            t = old_t.subtract(q.multiply(t));
            old_t = tt;
        }
        return new BigInteger[] { old_r, old_s, old_t };
	}
	
	
	
	
	
	/**
	 * compute a blum prime, for testing and debugging
	 * @param bitLength
	 * @return blum prime p
	 */
	public BigInteger blumPrime(int bitLength)
    {
        BigInteger p;
        do {
            p = BigInteger.probablePrime(bitLength, rng);
        } while (!p.mod(FOUR).equals(THREE));
        return p;
    }
	
	
	/**
	 * fix bytes array in case the first byte is the null byte
	 * @param b
	 * @return fixed byte array (remove the null byte)
	 */
	public byte[] fixBigIntegerBytes(byte[] b) {
		if(b[0] == 0) {
		    byte[] tmp = new byte[b.length - 1];
		    System.arraycopy(b, 1, tmp, 0, tmp.length);
		    b = tmp;
		}
		return b;
	}
	
	
	/**
	 * convert byte to String
	 * @param b
	 * @return String
	 */
	private String byteToString(byte b) {
		String hexString = Integer.toString(Byte.toUnsignedInt(b), 16);
		return hexString.length() == 1 ? "0" + hexString : hexString;
	}

	
	
	/**
	 * convert bytes array to String
	 * @param content
	 * @return collect the String presentation
	 */
	public String bytesToString(byte[] content) {
		LinkedList<String> result = new LinkedList<String>();
		for (byte b : content) {
			String byteToString = byteToString(b);
			result.add(byteToString);
		}
		String collect = result.stream().collect(Collectors.joining(""));
		return collect;
	}
	
	/**
	 * parses a string with the given blocklength
	 * @param toParse
	 * @param blocklength
	 * @return ArrayList of parsed strings
	 */
	public ArrayList<String> parseString(String toParse, int blocklength){
		ArrayList<String> parsedItems = new ArrayList<String>();
		
		for(int i = 0; i < toParse.length(); i += blocklength) {
			String substr = toParse.substring(i, i + blocklength);
			parsedItems.add(substr);
		}
		
		return parsedItems;
	}
	
	/**
	 * returns a String of the form BigInteger1 sep BigInteger2 sep etc
	 * @param a
	 * @param sep
	 * @return String with separator
	 */
	public String getStringWithSepForm(ArrayList<String> a, String sep) {
		String ret = "";
		
		for (int i = 0; i < a.size(); i++) {
			String item = a.get(i);
			ret += item;
			if(i != a.size() - 1) {
				ret += " ";
				ret += sep;
				ret += " ";
			}
		}
		
		return ret;
	}
	
	/**
	 * returns a String of the form BigInteger1(radix) sep BigInteger2(radix) sep etc
	 * @param a
	 * @param sep
	 * @return String with separator
	 */
	public String getStringWithSepRadixForm(ArrayList<String> a, String sep, int radix) {
		String ret = "";
		for(int i = 0; i < a.size(); i++) {
			String item = a.get(i);
			BigInteger itemNum = new BigInteger(item);
			ret += itemNum.toString(radix);
			if(i != a.size() - 1) {
				ret += " ";
				ret += sep;
				ret += " ";
			}
		}
		return ret;
	}
	
	/**
	 * returns a concatenation of Strings of an ArrayList
	 * @param input
	 * @return String of Strings of ArrayList
	 */
	public String getArrayListToString(ArrayList<String> input) {
		String ret = "";
		for(String item : input) {
			ret += item;
		}
		return ret;
	}
	
	/**
	 * get String in encoded form using the charset
	 * @param a
	 * @param sep
	 * @param radix
	 * @param charset
	 * @return String in encoded form
	 */
	public String getStringWithSepEncodedForm(ArrayList<String> a, String sep, int radix, Charset charset) {
		String ret = "";
		
		for (int i = 0; i < a.size(); i++) {
			String item = a.get(i);
			BigInteger itemToNum = new BigInteger(item, radix);
			byte[] itemAsBytes = itemToNum.toByteArray();
			String itemAsUtf8 = new String(fixBigIntegerBytes(itemAsBytes), charset);
			
			ret += itemAsUtf8;
			if(i != a.size() - 1) {
				ret += " ";
				ret += sep;
				ret += " ";
			}
		}
		
		return ret;
	}
	
	/**
	 * get encoded List
	 * @param a
	 * @param radix
	 * @param charset
	 * @return encoded List
	 */
	public ArrayList<String> getEncodedList(ArrayList<String> a, int radix, Charset charset){
		ArrayList<String> ret = new ArrayList<String>();
		
		for (int i = 0; i < a.size(); i++) {
			String item = a.get(i);
			BigInteger itemToNum = new BigInteger(item, radix);
			byte[] itemAsBytes = itemToNum.toByteArray();
			String itemAsUtf8 = new String(fixBigIntegerBytes(itemAsBytes), charset);
			ret.add(itemAsUtf8);
		}
		return ret;
	}
	
	/**
	 * get list of encrypted Strings
	 * @param a
	 * @param radix
	 * @return list of encrypted Strings
	 */
	public ArrayList<String> getEncryptedListOfStrings(ArrayList<String> a, int radix) {
		ArrayList<String> ret = new ArrayList<String>();
		for(String item : a) {
			BigInteger itemAsNum = new BigInteger(item, radix);
			BigInteger c = encrypt(itemAsNum);
			String cAsStr = c.toString(radix);
			ret.add(cAsStr);
		}
		return ret;
	}
	
	/**
	 * get padded plaintext using padding
	 * @param message
	 * @param blocklength
	 * @return padded plaintext
	 */
	public String getPaddedPlaintext(String message, int blocklength) {
		String paddedMessage = new String(message);
		while(paddedMessage.length() % blocklength != 0) {
			paddedMessage += padding;
		}
		return paddedMessage;
	}
	
	public String getPaddedPlaintext(String message, int blocklength, String paddingScheme) {
		String paddedMessage = new String(message);
		/*while(paddedMessage.length() % blocklength != 0) {
			paddedMessage += padding;
		}*/
		
		//int numOfPaddedBytes = (paddedMessage.length() - (paddedMessage.length() % blocklength)) / blocklength;
		int numOfPaddedBytes = BigInteger.valueOf(-(paddedMessage.length()/2)).mod(BigInteger.valueOf(blocklength/2)).intValue();
		if(numOfPaddedBytes == 0)
			return paddedMessage;
		
		if(paddingScheme.equals("ANSI X9.23")) {
			int numOfNullBytes = numOfPaddedBytes - 1;

			for(int i = 0; i < numOfNullBytes; i++) {
				paddedMessage += "00";
			}
			paddedMessage += byteToString((byte) numOfPaddedBytes);
		}
		else if(paddingScheme.equals("PKCS#7")) {
			for(int i = 0; i < numOfPaddedBytes; i++) {
				paddedMessage += byteToString((byte) numOfPaddedBytes);
			}
		}
		
		
		return paddedMessage;
	}
	
	/**
	 * 
	 * @param a
	 * @param blocklength
	 * @return padded ciphertextblocks as list
	 */
	public ArrayList<String> getPaddedCiphertextblocks(ArrayList<String> a, int blocklength) {
		ArrayList<String> paddedCiphertextblocks = new ArrayList<String>();
		for(String item : a) {
			while(item.length() % blocklength != 0) {
				item = "0" + item;
			}
			paddedCiphertextblocks.add(item);
		}
		return paddedCiphertextblocks;
	}
	
	
	public ArrayList<String> getPaddedPlaintextblocks(ArrayList<String> a, int blocklength) {
		ArrayList<String> paddedPlaintextblocks = new ArrayList<String>();
		for(String item : a) {
			while(item.length() % blocklength != 0) {
				item = "0" + item;
			}
			paddedPlaintextblocks.add(item);
		}
		return paddedPlaintextblocks;
	}
	
	/**
	 * method for debugging
	 * @param a
	 * @param p
	 * @param q
	 * @param radix
	 * @return
	 */
	public String getPossiblePlaintextsRadix(ArrayList<String> a, BigInteger p, BigInteger q, int radix) {
		String ret = "";
		
		for(String item : a) {
			String tmp = "[";
			BigInteger c = new BigInteger(item, radix);
			BigInteger[] pt = decrypt(c);
			for (int i = 0; i < pt.length; i++) {
				BigInteger plain = pt[i];
				tmp += plain.toString(16);
				if(i != pt.length - 1) {
					tmp += ", ";
				}
			}
			tmp += "]";
			ret += tmp;
			ret += "\n";
		}
		return ret;
	}
	
	/**
	 * method for testing and debugging
	 * @param a
	 * @param p
	 * @param q
	 * @param radix
	 * @param charset
	 * @return
	 */
	public String getPossiblePlaintextsEncoded(ArrayList<String> a, BigInteger p, BigInteger q, int radix, Charset charset) {
		String ret = "";
		
		for(String item : a) {
			String tmp = "[";
			BigInteger c = new BigInteger(item, radix);
			BigInteger[] pt = decrypt(c);
			for (int i = 0; i < pt.length; i++) {
				BigInteger plain = pt[i];
				byte[] plainInBytes = plain.toByteArray();
				String plainEncoded = new String(fixBigIntegerBytes(plainInBytes), charset);
				tmp += plainEncoded;
				if(i != pt.length - 1) {
					tmp += ", ";
				}
			}
			tmp += "]";
			ret += tmp;
			ret += "\n";
		}
		return ret;
	}
	
	/**
	 * get encoded plaintexts of a ciphertext c using charset
	 * @param c
	 * @return encoded plaintexts as list
	 */
	public ArrayList<String> getPossiblePlaintextsEncoded(BigInteger c) {
		ArrayList<String> possiblePlaintexts = new ArrayList<>();
		
		BigInteger[] plaintexts = decrypt(c);
		for(BigInteger pt : plaintexts) {
			byte[] ptInBytes = pt.toByteArray();
			String ptEncoded = new String(fixBigIntegerBytes(ptInBytes), charset);
			possiblePlaintexts.add(ptEncoded);
		}

		return possiblePlaintexts;
	}
	
	
	/*public ArrayList<ArrayList<String>> getAllPlaintextsFromListOfCiphertextblocks(ArrayList<String> ciphertextblocks, int radix){
		ArrayList<ArrayList<String>> allPlaintexts = new ArrayList<ArrayList<String>>();
		
		
		for(String ciphertextblock : ciphertextblocks) {
			BigInteger biCiphertextblock = new BigInteger(ciphertextblock, radix);
			ArrayList<String> possiblePlaintexts = getPossiblePlaintextsEncoded(biCiphertextblock);
			allPlaintexts.add(possiblePlaintexts);
		}
		
		return allPlaintexts;
	}*/
	
	
	public ArrayList<ArrayList<String>> getAllPlaintextsFromListOfCiphertextblocks(ArrayList<String> ciphertextblocks, int radix) {
		ArrayList<ArrayList<String>> allPlaintexts = new ArrayList<ArrayList<String>>();
		//long sum = 0;
		
		for (int i = 0; i < ciphertextblocks.size(); i++) {
			
			//long start = System.nanoTime();
			String ciphertextblock = ciphertextblocks.get(i);
			BigInteger biCiphertextblock = new BigInteger(ciphertextblock, radix);
			ArrayList<String> possiblePlaintexts = getPossiblePlaintextsEncoded(biCiphertextblock);
			allPlaintexts.add(possiblePlaintexts);
			//long end = System.nanoTime();
			//sum += (end - start);
			//System.out.println("time = " + (end - start));
		}
		
		//long average = sum / ciphertextblocks.size();
		//System.out.println("average time = " + average);
		
		return allPlaintexts;
	}
	
	
	
	
	/**
	 * method for testing and debugging
	 * @param a
	 * @param radix
	 * @param p
	 * @param q
	 * @param n
	 * @return
	 */
	public String getDecMessageSteps(ArrayList<String> a, int radix, BigInteger p, 
			BigInteger q, BigInteger n) {
		String ret = "";
		
		for(String item : a) {
			String tmp = "[";
			BigInteger c = new BigInteger(item, radix);
			BigInteger m_p = getSquarerootP(c);
			BigInteger m_q = getSquarerootQ(c);
			BigInteger[] y_pAndy_q = getInverseElements();
			BigInteger y_p = y_pAndy_q[0];
			BigInteger y_q = y_pAndy_q[1];
			BigInteger v = getV(y_p, m_q);
			BigInteger w = getW(y_q, m_p);
			BigInteger[] possibleMessages = getPlaintexts(v, w);
			for (int i = 0; i < possibleMessages.length; i++) {
				BigInteger pm = possibleMessages[i];
				String pmstr = pm.toString(radix);
				tmp += pmstr;
				if(i != possibleMessages.length - 1) {
					tmp += ", ";
				}
			}
			tmp += "]";
			ret += tmp;
			ret += ", ";
		}
		return ret;
	}
	
	/**
	 * 
	 * @param plaintext
	 * @return
	 */
	public ArrayList<String> getParsedPlaintextInBase10(String plaintext){
		ArrayList<String> ret = null;
		//String[] parsedPlaintext = plaintext.split(" || ");
		if(plaintext != null) {
			ret = new ArrayList<String>();
			if(plaintext.matches("\\d+"))
				ret.add(plaintext);
			else {
				String[] parsedPlaintext = plaintext.split(" \\|\\| ");
				for(String s : parsedPlaintext) {
					ret.add(s);
				}
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param a
	 * @param radix
	 * @return ArrayList of String in radix format
	 */
	public ArrayList<String> getListasRadix(ArrayList<String> a, int radix){
		ArrayList<String> ret = new ArrayList<String>();
		for(String item : a) {
			BigInteger b = new BigInteger(item);
			ret.add(b.toString(radix));
		}
		return ret;
	}
	
	/**
	 * 
	 * @param plaintextList
	 * @param radix
	 * @return
	 */
	public boolean isValidPlaintext(ArrayList<String> plaintextList, int radix) {
		for(String plaintextblock : plaintextList) {
			BigInteger biPlaintextblock = new BigInteger(plaintextblock, radix);
			if(biPlaintextblock.compareTo(n) == 1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param plaintext
	 * @param bytesPerBlock
	 * @param blocklength
	 * @param radix
	 * @return ciphertext String
	 */
	public String encryptPlaintext(String plaintext, int bytesPerBlock, int blocklength, int radix) {
		String ciphertext = null;
		String plaintextHex = bytesToString(plaintext.getBytes());
		String paddedPlaintext = getPaddedPlaintext(plaintextHex, bytesPerBlock);
		ArrayList<String> parsedPlaintext = parseString(paddedPlaintext, bytesPerBlock);
		ArrayList<String> encryptedPlaintextList = getEncryptedListOfStrings(parsedPlaintext, radix);
		ArrayList<String> paddedCiphertextList = getPaddedCiphertextblocks(encryptedPlaintextList, blocklength);
		ciphertext = getArrayListToString(paddedCiphertextList);
		//ciphertext = Rabin.getStringWithSepForm(paddedCiphertextList, "");
		return ciphertext;
	}
	
	
	
	
	
	public ArrayList<String> getCiphertextblocksAsList(String plaintext, int bytesPerBlock, int blocklength, int radix) {
		String ciphertext = null;
		String plaintextHex = bytesToString(plaintext.getBytes());
		String paddedPlaintext = getPaddedPlaintext(plaintextHex, bytesPerBlock);
		ArrayList<String> parsedPlaintext = parseString(paddedPlaintext, bytesPerBlock);
		ArrayList<String> encryptedPlaintextList = getEncryptedListOfStrings(parsedPlaintext, radix);
		ArrayList<String> paddedCiphertextList = getPaddedCiphertextblocks(encryptedPlaintextList, blocklength);
		return paddedCiphertextList;
	}
	
	
	public ArrayList<String> getCiphertextblocksAsList(String plaintext, String paddingScheme, int bytesPerBlock, int blocklength, int radix) {
		String ciphertext = null;
		String plaintextHex = bytesToString(plaintext.getBytes());
		String paddedPlaintext = getPaddedPlaintext(plaintextHex, bytesPerBlock, paddingScheme);
		ArrayList<String> parsedPlaintext = parseString(paddedPlaintext, bytesPerBlock);
		ArrayList<String> encryptedPlaintextList = getEncryptedListOfStrings(parsedPlaintext, radix);
		ArrayList<String> paddedCiphertextList = getPaddedCiphertextblocks(encryptedPlaintextList, blocklength);
		return paddedCiphertextList;
	}
	
	
	
	
	
	
	public boolean isInteger(BigDecimal bd) {
		BigInteger rounded = bd.toBigInteger();
		BigDecimal roundedTobd = new BigDecimal(rounded);
		if(roundedTobd.compareTo(bd) == 0)
			return true;
		
		return false;
	}
	
	
	/**
	 * fermat factorization for testing and debugging
	 * @param n
	 * @return factors as String[]
	 */
	public String[] fermatFactorize(String n) {
		
		String[] factors = null;
		BigInteger nAsNum = new BigInteger(n);
		
		BigInteger a = nAsNum.sqrt().add(BigInteger.ONE);
		BigInteger b = a.pow(2).subtract(nAsNum);
		BigDecimal bAsBigDecimal = new BigDecimal(b);
		BigDecimal squareB = bAsBigDecimal.sqrt(new MathContext(5, RoundingMode.FLOOR));
		
		while(!isInteger(squareB)) {
			a = a.add(BigInteger.ONE);
			b = a.pow(2).subtract(nAsNum);
			bAsBigDecimal = new BigDecimal(b);
			squareB = bAsBigDecimal.sqrt(new MathContext(5, RoundingMode.FLOOR));
		}
		
		BigInteger reta = a.add(squareB.toBigInteger());
		BigInteger retb = a.subtract(squareB.toBigInteger());
		
		boolean isValidPrime = reta.isProbablePrime(1000) && retb.isProbablePrime(1000);
		
		if(isValidPrime)
			factors = new String[] {reta.toString(), retb.toString()};
		
		return factors;
	}
	
	public static void main(String[] args) {
		
		Rabin rabin = new Rabin();
		
		BigDecimal bd = BigDecimal.valueOf(123.1);
		
		if(rabin.isInteger(bd))
			System.out.println("success");
		else
			System.out.println("fail");
		
		//String n = "69841";
		String n = "9";
		String [] factors = rabin.fermatFactorize(n);
		
		
		
		
		
		
	        /*BigInteger[] key = Rabin.generateKey(512);
	        BigInteger n = key[0];
	        BigInteger p = key[1];
	        BigInteger q = key[2];*/
		 	//System.out.println(HexEditorDebugLogic.bytesToString(new String("k").getBytes()));
		
			/*String num = "2^123";
			
			System.out.println("num = " + num);
			
			String[] parsedString = num.split("\\^");
			
			
			
			for(String elem : parsedString) {
				System.out.println(elem);
			}
			
		
		
			
		 	Charset charset = StandardCharsets.UTF_8;
		 	BigInteger p = BigInteger.valueOf(76231);//BigInteger.valueOf(19);
		 	BigInteger q = BigInteger.valueOf(5867);//BigInteger.valueOf(23);
		 	BigInteger n = p.multiply(q);
	        String finalMessage = null;
	        //int i = 1;
	        String s = "lwefwiem woehfwiefm lwiefhw, woeifw, wefkwnwekn\n"
	        		+ "owiejffjwbeb wehfwe \n"
	        		+ "iwejfiw";
	        String padding = "20";
	        
	        String sHex = Rabin.bytesToString(s.getBytes());
	        
	        int bytesPerBlock = (n.bitLength() / 8) * 2;
	        int blocklength = ((n.bitLength() / 8) + 1) * 2;
	        
	        String paddedS = Rabin.getPaddedPlaintext(sHex, padding, bytesPerBlock);
	        
	        ArrayList<String> messages = Rabin.parseString(paddedS, bytesPerBlock);
	        
	        String messagesWithSep = Rabin.getStringWithSepForm(messages, "||");
	        
	        String messagesEncoded = Rabin.getStringWithSepEncodedForm(messages, "||", 16, charset);
	        
	        ArrayList<String> encMessages = Rabin.getEncryptedListOfStrings(messages, 16, n);
	        
	        ArrayList<String> encMessagesPadded = Rabin.getPaddedCiphertextblocks(encMessages, blocklength);
	        
	        String encMessagesAsStr = Rabin.getStringWithSepForm(encMessages, "||");
	        
	        String encMessagedPaddedAsStr = Rabin.getStringWithSepForm(encMessagesPadded, "||");
	        
	        String possiblePlaintexts = Rabin.getPossiblePlaintextsRadix(encMessagesPadded, p, q, 16);
	        
	        String possiblePlaintextsEncoded = Rabin.getPossiblePlaintextsEncoded(encMessagesPadded, p, q, 16, charset);
	        
	        String possibleMessagesCmp = Rabin.getDecMessageSteps(encMessagesPadded, 16, p, q, n);
	        
	        System.out.println("message separated = " + messagesEncoded);
	        
	        System.out.println("message in hex = " + messagesWithSep);
	        
	        System.out.println("c[i] = " + encMessagesAsStr);
	        
	        System.out.println("c[i] padded = " + encMessagedPaddedAsStr);
	        
	        System.out.println("possible plaintexts = " + possiblePlaintexts);
	        
	        System.out.println("possible plaintexts cmp = " + possibleMessagesCmp);
	        
	        System.out.println("possible plaintexts encoded = " + possiblePlaintextsEncoded);
	        
	        String test = "2";
	        
	        ArrayList<String> a = Rabin.getParsedPlaintextInBase10(test);
	        */
		
		
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        /*String s_padded = Rabin.bytesToString(s.getBytes(charset));//new String(s);
	        String ciphertext = "";
	        
	        String s_with_sep = ""; 
	        String dec_m = "";
	        
	        String mHex = "";
	        
	        String c_with_sep = "";
	        
	        int bytesPerBlock = 2; //n.bitLength() / 8;
	        int blocklength = ((n.bitLength() / 8) + 1) * 2;//(bytesPerBlock + 1) * 2;
	        int padding = 0x20;
	        String padstr = new String(BigInteger.valueOf(padding).toByteArray(), charset);
	        // padding part
	       
	        if(s_padded.length() % (bytesPerBlock*2) != 0) {
	        	while(s_padded.length() % (bytesPerBlock*2) != 0) {
	        		s_padded += "20";
	        	}
	        }*/
	        
	        //encryption part
	        /*for(int i = 0; i < s_padded.length(); i+=blocklength*2) {
	        	String mblock = s_padded.substring(i, i+blocklength*2);
	        	s_with_sep += mblock + " || ";
	        	System.out.println(bytesToString(mblock.getBytes()));
	        	//BigInteger mblockasNum = new BigInteger(Rabin.bytesToString(mblock.getBytes()), 16);
	        	BigInteger mblockasNum = new BigInteger(mblock, 16);
	        	System.out.println("mblockasnum = "+ mblockasNum);
	        	BigInteger cblockasNum = Rabin.encrypt(mblockasNum, n);
	        	System.out.println("cblockasnum["+i+"]"+" = " + cblockasNum.toString(16));
	        	byte[] cbytes = cblockasNum.toByteArray();
	        	byte[] fixedC = Rabin.fixBigIntegerBytes(cbytes);
	        	//String cstr = new String(cblockasNum.toByteArray(), charset);
	        	String cstr = new String(fixedC, charset);
	        	String cstrb16 = Rabin.bytesToString(fixedC);
	        	BigInteger test = new BigInteger("03c5", 16);
	        	System.out.println(Rabin.bytesToString(cbytes));
	        	ciphertext += cstrb16;
	        	
	        	
	        }*/
	        
	        
	        // encryption
	        
	        /*for(int i = 0; i < s_padded.length(); i += bytesPerBlock * 2) {
	        	String mblock = s_padded.substring(i, i + bytesPerBlock * 2);
	        	BigInteger mblockNum = new BigInteger(mblock, 16);
	        	byte [] mBytes = Rabin.fixBigIntegerBytes(mblockNum.toByteArray());     
	        	String mblock_utf8 = new String(mBytes, charset);
	        	s_with_sep += mblock_utf8;
	        	mHex += mblock;
	        	
	        	if(i != s_padded.length() - bytesPerBlock * 2) {
	        		s_with_sep += " || ";
	        		mHex += " || ";
	        	}
	        	
	        	BigInteger cblockNum = Rabin.encrypt(mblockNum, n);
	        	String cblockstr = cblockNum.toString(16);
	        	System.out.println(cblockstr);
	        	String cblockstrPadded = new String(cblockstr);
	        	
	        	while(cblockstrPadded.length() % blocklength != 0) {
	        		cblockstrPadded = "0" + cblockstrPadded;
	        	}
	        	
	        	ciphertext += cblockstrPadded;
	        	c_with_sep += cblockstrPadded;
	        	
	        	if(i != s_padded.length() - bytesPerBlock * 2) {
	        		c_with_sep += " || ";
	        	}
	        	
	        }
	        
	        System.out.println("s_with_sep = " + s_with_sep);
	        System.out.println("m as hex = " + mHex);
	        System.out.println("c_with_sep = " + c_with_sep);
	        System.out.println("c = " + ciphertext);
	        
	        
	        // decryption
	        
	      
	        
	        for(int i = 0; i < ciphertext.length(); i += blocklength) {
	        	String cblock = ciphertext.substring(i, i + blocklength);
	        	BigInteger cAsNum = new BigInteger(cblock, 16);
	        	BigInteger[] cDec = Rabin.decrypt(cAsNum, p, q);
	        	
	        	String cDecGroup = "[";
	        	for(BigInteger b : cDec) {
	        		String butf8 = new String(Rabin.fixBigIntegerBytes(b.toByteArray()), charset);
	        		cDecGroup += butf8;
	        		if(b.compareTo(cDec[3]) != 0)
	        			cDecGroup += ", ";
	        	}
	        	cDecGroup += "]";
	        	
	        	System.out.println(cDecGroup);
	        }*/
	        
	        
	        
	        /*System.out.println();
	        System.out.println(ciphertext.length());
	        String dec = "";
	        //decryption part
	        for(int i = 0; i < ciphertext.length(); i+=blocklength*2) {
	        	String cblock = ciphertext.substring(i, i+blocklength*2);
	        	//String cmp_m = s.substring(i, i+blocklength);
	        	//BigInteger cblockasnum = new BigInteger(cblock.getBytes());
	        	BigInteger cblockasnum = new BigInteger(cblock, 16);   	
	        	byte[] casbytes = cblockasnum.toByteArray();
	        	//cblockasnum = new BigInteger(Rabin.fixBigIntegerBytes(cblockasnum.toByteArray()));
	        	BigInteger[] dec_c = Rabin.decrypt(cblockasnum, p, q);
	        	String fm = null;
	        	System.out.print("[");
	        	for(BigInteger m: dec_c) {
	        		//String dec = new String(s.substring(i, i+blocklength).getBytes(), charset);
	        		dec += new String(Rabin.fixBigIntegerBytes(m.toByteArray()), charset);//m.toByteArray(), charset);
	        		dec += ", ";
	        		//System.out.print(dec + ", ");
	        	}
	        	System.out.println(dec + "]");
	        	dec = "";
	        }
	        
	        System.out.println("message to encrypt = " + s);
	        System.out.println("message with separator = " + s_with_sep);
	        System.out.println("ciphertext c = " + ciphertext);
	        */
	        
	        
	        
	        
	        
	        /*String sModified = String.join("", HexEditorDebugLogic.bytesToString(s.getBytes()).split(" "));
	        System.out.println("sModified = " + sModified);
	        BigInteger u = new BigInteger(s.getBytes(StandardCharsets.UTF_8));
	        System.out.println("Message sent by sender : " + s);
	        System.out.println("message in hex: " + 
	        		HexEditorDebugLogic.bytesToString(s.getBytes()));
	        System.out.println("message in hex (biginteger): " +
	        		new BigInteger(Rabin.fixBigIntegerBytes(u.toByteArray())).toString(16));
	        
	        System.out.println("bitlength of N = " + n + " = " + 
	        		n.bitLength());
	        
	        int blocklength = n.bitLength() / 8;
	        
	        //int padding = 0x20;
	        String padding = "20";
	        BigInteger pad = new BigInteger(padding, 16);
	        System.out.println("padding = "+ pad.toString(16));
	        //String l = new String(BigInteger.valueOf(padding).toByteArray(), StandardCharsets.UTF_8);
	        //System.out.println(l);
	        
	        System.out.println("blocklength for encryption = " + blocklength);
	        
	        StringBuilder sb = new StringBuilder();
	        
	        for(int i = 0; i < s.length(); i+=blocklength) {
	        	String substr = s.substring(i, i+blocklength);
	        	BigInteger m = new BigInteger(substr.getBytes(StandardCharsets.UTF_8));
	        	BigInteger c = Rabin.encrypt(m, n);
	        	byte[] fixedC = Rabin.fixBigIntegerBytes(c.toByteArray());
	        	sb.append(new String(fixedC, StandardCharsets.UTF_8));
	        }
	        
	        System.out.println(sb);
	  
	        
	        BigInteger m = new BigInteger(s.getBytes(StandardCharsets.UTF_8));
	        BigInteger c = Rabin.encrypt(m, n);
	        byte[] cAsByte = c.toByteArray();
	        byte[] cFixed = Rabin.fixBigIntegerBytes(cAsByte);
	  
	        System.out.println("Encrypted Message : " + c);
	        System.out.println(HexEditorDebugLogic.bytesToString(cFixed));
	        System.out.println("Encrypted message as string: " +
	        		new String(cFixed, StandardCharsets.UTF_8));
	        
	        System.out.println("bitlength of c = " + c.bitLength());
	        
	        BigInteger[] m2 = Rabin.decrypt(c, p, q);
	        for (BigInteger b : m2) {
	            String dec = new String(
	                b.toByteArray(),
	                Charset.forName("ascii"));
	            if (dec.equals(s)) {
	                finalMessage = dec;
	            }
	            //i++;
	        }
	        System.out.println(
	            "Message received by Receiver : "
	            + finalMessage);*/
	    }
}
