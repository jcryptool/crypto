package org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa;

import java.util.Optional;

import org.jcryptool.visual.rsa_elgamal.textbook.IKeySet;
import org.jcryptool.visual.rsa_elgamal.textbook.IPrivateKey;
import org.jcryptool.visual.rsa_elgamal.textbook.IPublicKey;

public class RSAKeys implements IKeySet<RSAPrivateKey, RSAPublicKey> {
	
	public RSAPrivateKey privateKey;
	public RSAPublicKey publicKey;
	public RSAKeys(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
		super();
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}
	@Override
	public RSAPublicKey getPublicKey() {
		return this.publicKey;
	}
	@Override
	public RSAPrivateKey getPrivateKey() {
		return this.privateKey;
	}
}
