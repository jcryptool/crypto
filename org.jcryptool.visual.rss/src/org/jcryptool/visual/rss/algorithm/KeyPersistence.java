package org.jcryptool.visual.rss.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Vector;

import org.jcryptool.core.util.constants.IConstants;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Information;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;

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
	
	public static void saveKey(PrivateKey keyPair, String path) throws FileNotFoundException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path),
		        Charset.forName(IConstants.UTF8_ENCODING));
		
		XStream xstream = new XStream(new DomDriver());
		
		
				
		xstream.toXML(keyPair.getEncoded(), osw);
		
	}
	
	public static PrivateKey loadKey (String path) throws FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), Charset.forName(IConstants.UTF8_ENCODING));

		XStream xstream = new XStream(new DomDriver());

		xstream.alias("GLRSSPrivateKey", GLRSSPrivateKey.class);
		xstream.setClassLoader(GLRSSPrivateKey.class.getClassLoader());

		//xstream.alias("MersaPrivateKey", de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class);
		//xstream.setClassLoader(de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey.class.getClassLoader());
		
		PrivateKey vector = (PrivateKey)xstream.fromXML(isr);
		return vector;
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
