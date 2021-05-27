package org.jcryptool.visual.rss.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
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
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Information;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPrivateKey;

/**
 * Persists a key.
 * 
 * @author Lukas Krodinger
 */
public class KeyPersistence {
	
	
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
	
	public static void saveKeyPair(KeyPair keyPair, String path) throws FileNotFoundException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());
		//xstream.registerConverter(new GLRSSPrivateKeyConverter());

		
		
				
		xstream.toXML(keyPair, osw);
		
	}
	
	public static KeyPair loadKeyPair(String path) throws FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());

		//xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		//xstream.setClassLoader(GLRSSPrivateKey.class.getClassLoader());
		//xstream.registerConverter(new GLRSSPrivateKeyConverter());


		//xstream.alias("MersaPrivateKey", de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class);
		//xstream.setClassLoader(de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class.getClassLoader());
		
		KeyPair vector = (KeyPair)xstream.fromXML(isr);
		return vector;
	}
	
	public static void savePrivateKey(PrivateKey keyPair, String path) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
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

	public static void saveMessage(SignatureOutput signOut, String path) throws FileNotFoundException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());
		
	
		xstream.toXML(signOut, osw);
		
	}

	public static SignatureOutput loadMessage(String path) throws FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());
		
		SignatureOutput vector = (SignatureOutput)xstream.fromXML(isr);
		return vector;
	}
	
	
}
