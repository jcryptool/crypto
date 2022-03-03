package org.jcryptool.visual.rss.algorithm;

import java.security.KeyPair;

import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Scheme;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;

/**
 * Wrapper class containing the AlgorithmType, the KeyLength and the concrete KeyPair.
 * 
 * @author Lukas Krodinger
 */
public class KeyInformation {
	public Scheme scheme;
	public KeyLength keyLength;
	public KeyPair keyPair;

	public KeyInformation(Scheme scheme, KeyLength keyLength, KeyPair keyPair) {
		this.scheme = scheme;
		this.keyLength = keyLength;
		this.keyPair = keyPair;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public KeyLength getKeyLength() {
		return keyLength;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	
}