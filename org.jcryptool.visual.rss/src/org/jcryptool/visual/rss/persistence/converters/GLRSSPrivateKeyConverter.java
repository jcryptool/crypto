package org.jcryptool.visual.rss.persistence.converters;

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


public class GLRSSPrivateKeyConverter implements Converter {

	@Override
	public boolean canConvert(Class clazz) {
		return clazz.equals(GLRSSPrivateKey.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		GLRSSPrivateKey privateKey = (GLRSSPrivateKey) value;
		
		writer.startNode("GsrssKey");
		context.convertAnother(privateKey.getGsrssKey());
		writer.endNode();
		
		writer.startNode("AccumulatorKey");
		context.convertAnother(privateKey.getAccumulatorKey());
		writer.endNode();
		
		writer.startNode("Algorithm");
		writer.setValue(privateKey.getAlgorithm());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		
		reader.moveDown();
		PrivateKey gsrssKey = (GSRSSPrivateKey)context.convertAnother(context, GSRSSPrivateKey.class);
		reader.moveUp();
		
		reader.moveDown();
		PrivateKey accumulatorKey = (BPPrivateKey)context.convertAnother(context, BPPrivateKey.class);
		reader.moveUp();	
		
		reader.moveDown();
		String algorithm = reader.getValue();
		reader.moveUp();	
		
		PrivateKey privateKey = new GLRSSPrivateKey(algorithm, gsrssKey, accumulatorKey);
		
		return privateKey;
	}
	
	
	
	
	
}
