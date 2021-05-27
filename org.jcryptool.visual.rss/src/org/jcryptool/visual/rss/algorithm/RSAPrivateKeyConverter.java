package org.jcryptool.visual.rss.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Vector;

import org.jcryptool.core.util.constants.IConstants;
import org.jcryptool.visual.rss.Activator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.BPPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey;


public class RSAPrivateKeyConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(RSAPrivateCrtKey.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) value;
		

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		

		
		PrivateKey privateKey = (PrivateKey) new RSAPrivateCrtKeySpec(null, null, null, null, null, null, null, null);
		
		return privateKey;
	}
	
	
	
	
	
}
