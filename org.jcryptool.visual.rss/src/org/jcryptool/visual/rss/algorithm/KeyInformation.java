package org.jcryptool.visual.rss.algorithm;

import java.security.KeyPair;

import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.AlgorithmType;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;

/**
 * Wrapper class containing the AlgorithmType, the KeyLength and the concrete KeyPair.
 * 
 * @author Lukas Krodinger
 */
public class KeyInformation {
	public AlgorithmType algorithmType;
	public KeyLength keyLength;
	public KeyPair keyPair;

	public KeyInformation(AlgorithmType algorithmType, KeyLength keyLength, KeyPair keyPair) {
		this.algorithmType = algorithmType;
		this.keyLength = keyLength;
		this.keyPair = keyPair;
	}

	public AlgorithmType getAlgorithmType() {
		return algorithmType;
	}

	public KeyLength getKeyLength() {
		return keyLength;
	}

	public KeyPair getKeyPair() {
		return keyPair;
	}
	
	
}