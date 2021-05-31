package org.jcryptool.visual.rss.algorithm;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.KeyPair;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;

/**
 * Defines methods to persist and load KeyInformation and SignatureOutputs.
 * 
 * @author Lukas Krodinger
 */
public interface Persistence {

	/**
	*  Saves key information to a given file.
	*  
	 * @param keyInformation The keyInformation to save.
	 * @param path The path to save the information to.
	 * @throws FileNotFoundException In case the chosen path is invalid.
	 */
	public void saveInformation(KeyInformation keyInformation, String path) throws FileNotFoundException;
	
	/**
	 * Loads key information from a given file.
	 * 
	 * @param path The path of the file to load from.
	 * @return The loaded key information.
	 * @return The loaded keyInformation class.
	 * @throws FileNotFoundException In case the file is not found or available.
	 * @throws InvalidKeyException In case the given file does not contain a valid key.
	 */
	public KeyInformation loadInformation(String path) throws FileNotFoundException, InvalidKeyException;
	
	/**
	 * Saves a signed message to a given file.
	 * 
	 * @param signOut The SignatureOutput to persist.
	 * @param path The path to the file and it's name.
	 * @throws FileNotFoundException In case the file is not found or available.
	 */
	public void saveSignatureOutput(SignatureOutput signOut, String path) throws FileNotFoundException;
	
	/**
	 * Loads a signed message from a given file and casts it to a SignatureOutput.
	 * 
	 * @param path The path to the file and it's name.
	 * @return The loaded SignatureOutput class.
	 * @throws FileNotFoundException In case the file is not found or available.
	 * @throws InvalidSignatureException In case the given file does not contain a valid SignatureOutput.
	 */
	public SignatureOutput loadSignatureOutput(String path) throws FileNotFoundException, InvalidSignatureException;

}
