package org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa;

import java.math.BigInteger;

public class RSAMath {

	public static BigInteger phi(BigInteger p, BigInteger q) {
		return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
	}

	public static BigInteger d(BigInteger phi, BigInteger e) {
		return e.modInverse(phi);
	}
	
	public static BigInteger n(BigInteger p, BigInteger q) {
		return p.multiply(q);
	}

}
