package org.jcryptool.visual.rsa_elgamal.textbook;

import java.math.BigInteger;

public interface IPrivateKey {
	public BigInteger decrypt(BigInteger data);
	public BigInteger sign(BigInteger data);
}
