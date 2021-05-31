package org.jcryptool.visual.rss.ui;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPublicKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPublicKey;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPrivateKey;

/**
 * The composite with button, text fields etc. with the function to show the set key.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssViewKeyComposite extends RssRightSideComposite {

    public RssViewKeyComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.ViewKey);
        prepareAboutComposite();

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout(2, false));

        KeyInformation info = rac.getInformation();
        if (info.getAlgorithmType() != null) {
            Label l = new Label(inner, SWT.NONE);
            l.setText(Descriptions.KeyType + ": ");
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(info.getAlgorithmType().toString());
        }
        if (info.getKeyLength() != null) {
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(Descriptions.KeyLength + ": ");
            Label l = new Label(inner, SWT.NONE);
            l.setText(info.getKeyLength().getKl() + "");
        }
        if (info.getKeyPair() != null) {
            Label l1 = new Label(leftComposite, SWT.READ_ONLY);
            l1.setText(Descriptions.PrivateKey);
            Text t1 = new Text(leftComposite, SWT.NONE | SWT.MULTI | SWT.WRAP);
            t1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            
            KeyPair keyPair = info.getKeyPair();
            byte[] encodedPrivateKey = getPrivateKey(keyPair);
            
            String text1 = new String(Base64.getEncoder().encode(encodedPrivateKey));
            t1.setText(getSplittedString(text1, 50));
            Label l2 = new Label(leftComposite, SWT.NONE);
            l2.setText(Descriptions.PublicKey);
            Text t2 = new Text(leftComposite, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
            t2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                       
            byte[] encodedPublicKey = getPublicKey(keyPair);
            
            String text2 = new String(Base64.getEncoder().encode(encodedPublicKey));
            t2.setText(getSplittedString(text2, 50));
        }

        Button returnButton = new Button(leftComposite, SWT.PUSH);
        returnButton.setText("Return");

        returnButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    body.returnToCurrentState();
                }
            }
        });
    }

	/**
	 * Gets the encoded publicKey.
	 * The object structure for the key is as follows:
	 * 1. The keyPair contains a publicKey
	 * 2. There are multiple options:
	 * - If the publicKey is a GLRSSPrivateKey it contains an GSRSSPublicKey.
	 * - If the publicKey is (now) a GSRSSPublicKey, it contains the dSignKey.
	 * - If the publicKey is a PSRSSPublicKey, it contains the key as BigInteger.
	 * 
	 * @param keyPair The keyPair to obtain the publicKey from.
	 * @return The encoded publicKey.
	 */
	public static byte[] getPublicKey(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		byte[] encodedPublicKey = null;
		
		if (publicKey instanceof GLRSSPublicKey) {
			GLRSSPublicKey glrssPublicKey = (GLRSSPublicKey) publicKey;
			publicKey = glrssPublicKey.getGsrssKey();
		} 
		
		if (publicKey instanceof GSRSSPublicKey) {
			GSRSSPublicKey gsrssPublicKey = (GSRSSPublicKey) publicKey;
			publicKey = gsrssPublicKey.getDSigKey();
			encodedPublicKey = publicKey.getEncoded();
		}
		
        /*if (publicKey instanceof PSRSSPublicKey) {
        	PSRSSPublicKey psrssPrivateKey = (PSRSSPublicKey) publicKey;
        	encodedPublicKey = psrssPrivateKey.getKey().toByteArray();
        }*/
        
        /*if (publicKey instanceof MersaPublicKey) {
        	MersaPublicKey mersaPrivateKey = (MersaPublicKey) publicKey;
        	encodedPublicKey = mersaPrivateKey.getEncoded();
        }*/
			   
        if (encodedPublicKey == null) {
        	encodedPublicKey = publicKey.getEncoded();
        }
        
        
        if (encodedPublicKey == null) {
        	throw new RuntimeException("Extracting public key not possible");
        }
        
	
		return encodedPublicKey;
	}

    /**
     * Gets the encoded privateKey.
     * The object structure for the key is (for whatever reason) as follows:
	 * - If the privateKey is a GLRSSPrivateKey it contains an GSRSSPrivateKey.
	 * - If the privateKey is (now) a GSRSSPrivateKey, it contains the dSignKey.
	 * - If the privateKey is a PSRSSPrivateKey, it contains the key as BigInteger.
	 * 
     * @param keyPair The keyPair to obtain the privateKey from.
     * @return The encoded privateKey.
     */
    public static byte[] getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encodedPrivateKey = null;
        
        if (privateKey instanceof GLRSSPrivateKey) {
        	GLRSSPrivateKey glrssPrivateKey = (GLRSSPrivateKey) privateKey;
        	privateKey = glrssPrivateKey.getGsrssKey();
        } 
        
        if (privateKey instanceof GSRSSPrivateKey) {
        	GSRSSPrivateKey gsrssPrivateKey = (GSRSSPrivateKey) privateKey;
        	privateKey = gsrssPrivateKey.getDSigKey();
        	encodedPrivateKey = privateKey.getEncoded();
        } 
        
        /*if (privateKey instanceof PSRSSPrivateKey) {
        	PSRSSPrivateKey psrssPrivateKey = (PSRSSPrivateKey) privateKey;
        	encodedPrivateKey = psrssPrivateKey.getKey().toByteArray();
        }  */ 
        
        if (encodedPrivateKey == null) {
        	encodedPrivateKey = privateKey.getEncoded();
        }
        
        /*if (privateKey instanceof MersaPrivateKey) {
        	MersaPrivateKey mersaPrivateKey = (MersaPrivateKey) privateKey;
        	encodedPrivateKey = mersaPrivateKey.getEncoded();
        }*/
        
        if (encodedPrivateKey == null) {
        	throw new RuntimeException("Extracting private key not possible");
        }
        
        return encodedPrivateKey;
	}

	@Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewKey);
    }

}
