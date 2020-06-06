package org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa;

import java.math.BigInteger;

import org.jcryptool.visual.rsa_elgamal.textbook.IPublicKey;

public class RSAPublicKey implements IPublicKey {

	public BigInteger n;
	public BigInteger e;
	
    public RSAPublicKey(BigInteger n, BigInteger e) {
		this.n = n;
		this.e = e;
	}

    public BigInteger verify(BigInteger data) {
        return data.modPow(e, n);
    }

	@Override
	public BigInteger encrypt(BigInteger data) {
        return data.modPow(e, n);
	}

}
