package org.jcryptool.visual.rss.algorithm;

import java.security.KeyPair;

import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.AlgorithmType;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;

public class KeyInformation {
	public AlgorithmType keyType;
	public KeyLength keyLength;
	public KeyPair keyPair;



	public KeyInformation(AlgorithmType keyType, KeyLength keyLength, KeyPair keyPair) {
		this.keyType = keyType;
		this.keyLength = keyLength;
		this.keyPair = keyPair;
	}

	public AlgorithmType getKeyType() {
		return keyType;
	}

	public KeyLength getKeyLength() {
		return keyLength;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	
}