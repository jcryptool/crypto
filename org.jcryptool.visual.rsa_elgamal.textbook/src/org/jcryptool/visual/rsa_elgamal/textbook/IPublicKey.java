package org.jcryptool.visual.rsa_elgamal.textbook;

import java.math.BigInteger;

public interface IPublicKey {
	public BigInteger encrypt(BigInteger data);
	public BigInteger verify(BigInteger data);
}
