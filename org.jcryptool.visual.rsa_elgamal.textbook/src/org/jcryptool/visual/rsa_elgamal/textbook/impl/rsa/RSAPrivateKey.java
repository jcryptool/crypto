package org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa;

import java.math.BigInteger;

import org.jcryptool.visual.rsa_elgamal.textbook.IPrivateKey;

public class RSAPrivateKey implements IPrivateKey {

	public BigInteger n;
	public BigInteger d;

	public RSAPrivateKey(BigInteger n, BigInteger d) {
		this.n = n;
		this.d = d;
	}

	@Override
	public BigInteger decrypt(BigInteger data) {
		return data.modPow(d, n);
	}

	@Override
	public BigInteger sign(BigInteger data) {
		return data.modPow(d, n);
	}

}
