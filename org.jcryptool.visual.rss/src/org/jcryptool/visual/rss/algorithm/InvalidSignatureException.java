package org.jcryptool.visual.rss.algorithm;

/**
 * This exception is thrown, when the given signed message file is not supported by this visualisation.
 * 
 * @author Lukas Krodinger
 *
 */
public class InvalidSignatureException extends Exception {

	/**
	 * Random.
	 */
	private static final long serialVersionUID = -3913169932463206008L;
	
	public InvalidSignatureException(String cause) {
		super(cause);
	}
	
	public InvalidSignatureException() {
		
	}

}
