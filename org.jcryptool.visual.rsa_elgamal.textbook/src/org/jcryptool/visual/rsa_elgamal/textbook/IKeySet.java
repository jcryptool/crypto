package org.jcryptool.visual.rsa_elgamal.textbook;

public interface IKeySet<Priv extends IPrivateKey, Pub extends IPublicKey> {
	
	public Pub getPublicKey();
	public Priv getPrivateKey();

}
