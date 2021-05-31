package org.jcryptool.visual.rss.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Vector;

import org.jcryptool.core.util.constants.IConstants;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.algorithm.KeyInformation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPrivateKey;

/**
 * Persists and loads KeyInformation and SignatureOutput to/from XML.
 * 
 * @author Lukas Krodinger
 */
public class XMLPersistence implements Persistence {
	
	
	/*public static void saveKey(KeyPair keyPair, String path) throws FileNotFoundException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());
		
		xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		
		xstream.toXML(keyPair, osw);
		
	}
	
	public static KeyPair loadKey (String path) throws FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());

		xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		
		KeyPair vector = (KeyPair)xstream.fromXML(isr);
		return vector;
	}*/
	
	// TODO: Remove
	public static void saveKeyPair(KeyPair keyPair, String path) throws FileNotFoundException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());	
		
		xstream.toXML(keyPair, osw);
		
	}
	
	// TODO: Remove
	public static KeyPair loadKeyPair(String path) throws FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());
		
		KeyPair vector = (KeyPair)xstream.fromXML(isr);
		return vector;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void saveInformation(KeyInformation keyInformation, String path) throws FileNotFoundException {
				
		// Creates a output stream writer to write to the file
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		// Creates a XML serializer
		XStream xstream = new XStream(new DomDriver());	
		
		// Serialize and save the SignatureOutput
		xstream.toXML(keyInformation, osw);
		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public KeyInformation loadInformation(String path) throws FileNotFoundException, InvalidKeyException {
		
		// Create a input stream reader to load the file
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		// Create a XML parser
		XStream xstream = new XStream(new DomDriver());
		
		// Parse the object from XML
		Object parsedObject = xstream.fromXML(isr);
		
		// Check if the file is valid
		if(parsedObject instanceof KeyInformation) {
			KeyInformation keyInformation = (KeyInformation) parsedObject;
			return keyInformation;
		} else {
			throw new InvalidKeyException("The given file does not contain a valid key.");
		}
	}
	
	// TODO: Remove
	public void savePrivateKey(PrivateKey keyPair, String path) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		xstream.alias("GSRSSPrivateKey", GSRSSPrivateKey.class);
		xstream.alias("KeyRep", KeyRep.class);

		//xstream.setClassLoader(KeyRep.class.getClassLoader());

		//xstream.registerConverter(new GLRSSPrivateKeyConverter());
		//xstream.registerConverter(new GSRSSPrivateKeyConverter());

        KeyFactory kf = KeyFactory.getInstance("RSA");

		
		RSAPrivateKey rsaKey = (RSAPrivateKey) keyPair;
        RSAPrivateKeySpec ksPrivate = kf.getKeySpec(keyPair, RSAPrivateKeySpec.class);
		xstream.toXML(ksPrivate, osw);
		
	}
	
	// TODO: Remove
	public static PrivateKey loadPrivateKey(String path) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());

		//xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		//xstream.setClassLoader(GLRSSPrivateKey.class.getClassLoader());
		xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		xstream.alias("GSRSSPrivateKey", GSRSSPrivateKey.class);
		xstream.alias("KeyRep", KeyRep.class);
		xstream.alias("RSAPrivateKeySpec", RSAPrivateKeySpec.class);
		
		
		



		//xstream.registerConverter(new GLRSSPrivateKeyConverter());
		//xstream.registerConverter(new RSAPrivateKeySpecConverter());



		//xstream.alias("MersaPrivateKey", de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class);
		//xstream.setClassLoader(de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class.getClassLoader());
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		RSAPrivateKeySpec privateKeySpec = (RSAPrivateKeySpec)xstream.fromXML(isr);
		PrivateKey key = keyFactory.generatePrivate(privateKeySpec);
  
		
		return key;
	}

	/**
	 *  {@inheritDoc}
	 */
	public void saveSignatureOutput(SignatureOutput signOut, String path) throws FileNotFoundException {
		
		// Creates a output stream writer to write to the file
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		// Creates a XML serializer
		XStream xstream = new XStream(new DomDriver());
		
		// Serialize and save the SignatureOutput
		xstream.toXML(signOut, osw);
	}

	/**
	 *  {@inheritDoc}
	 */
	public SignatureOutput loadSignatureOutput(String path) throws FileNotFoundException, InvalidSignatureException {
		
		// Create a input stream reader to load the file
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));
		
		// Create a XML parser
		XStream xstream = new XStream(new DomDriver());
		
		// Load and parse the file
		Object parsedObject = xstream.fromXML(isr);
		
		// Check if the file is valid
		if(parsedObject instanceof SignatureOutput) {
			SignatureOutput signOut = (SignatureOutput) parsedObject;
			return signOut;
		} else {
			throw new InvalidSignatureException("The given file is not supported by this visualisation.");
		}
	}
	
	
}
