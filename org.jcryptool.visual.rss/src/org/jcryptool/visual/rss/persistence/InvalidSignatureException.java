package org.jcryptool.visual.rss.persistence;

/**
 * This exception is thrown, when the given signed message file is not supported by this visualisation.
 * 
 * @author Lukas Krodinger
 *
 */
public class InvalidSignatureException extends Exception {

	/**
	 * Random serialVersionUID.
	 */
	private static final long serialVersionUID = -3913169932463206008L;
	
	/**
	 * {@inheritDoc}
	 */
	public InvalidSignatureException(String cause) {
		super(cause);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public InvalidSignatureException() {
		
	}

}
