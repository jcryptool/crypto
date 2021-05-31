package org.jcryptool.visual.rss.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.List;
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


public class MessageAndRedactable  {

	private List<String> messageParts;
	private List<Boolean> redactableParts;
	
	public MessageAndRedactable(List<String> messageParts, List<Boolean> redactableParts) {
		this.messageParts = messageParts;
		this.redactableParts = redactableParts;
	}
	
	public List<String> getMessageParts() {
		return messageParts;
	}
	public void setMessageParts(List<String> messageParts) {
		this.messageParts = messageParts;
	}
	public List<Boolean> getRedactableParts() {
		return redactableParts;
	}
	public void setRedactableParts(List<Boolean> redactableParts) {
		this.redactableParts = redactableParts;
	}

	
}
