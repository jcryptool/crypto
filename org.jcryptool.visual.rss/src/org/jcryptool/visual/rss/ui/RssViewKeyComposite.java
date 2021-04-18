package org.jcryptool.visual.rss.ui;

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
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Information;
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

        Information info = rac.getInformation();
        if (info.getKeyType() != null) {
            Label l = new Label(inner, SWT.NONE);
            l.setText(Descriptions.KeyType + ": ");
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(info.getKeyType().toString());
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
	 * The object structure for the key is (for whatever reason) as follows:
	 * 1. The keyPair contains a publicKey
	 * 2. Only if the publicKey is an instance of GLRSSPrivateKey it contains an GSRSSPublicKey
	 * 3. The GSRSSPublicKey contains the dSignKey
	 * @param keyPair The keyPair to obtain the publicKey from.
	 * @return The encoded publicKey.
	 */
	private byte[] getPublicKey(KeyPair keyPair) {

		PublicKey publicKey = keyPair.getPublic();
		
		if (publicKey instanceof GLRSSPublicKey) {
			GLRSSPublicKey glrssPublicKey = (GLRSSPublicKey) publicKey;
			publicKey = glrssPublicKey.getGsrssKey();
		} 
		
		PublicKey dPublicSigKey;
		if (publicKey instanceof GSRSSPublicKey) {
			GSRSSPublicKey gsrssPublicKey = (GSRSSPublicKey) publicKey;
			dPublicSigKey = gsrssPublicKey.getDSigKey();
		} else {
			throw new RuntimeException("Unsupported Key Structure");
		}
		
		byte[] encodedPublicKey = dPublicSigKey.getEncoded();
		return encodedPublicKey;
	}

    /**
     * Gets the encoded privateKey.
     * The object structure for the key is (for whatever reason) as follows:
     * 1. The keyPair contains a privateKey
     * 2. Only if the keyPair is an instance of GLRSSPrivateKey it contains an GSRSSPrivateKey
     * 3. The GSRSSPrivateKey contains the dSignKey
     * @param keyPair The keyPair to obtain the privateKey from.
     * @return The encoded privateKey.
     */
    private byte[] getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        
        if (privateKey instanceof GLRSSPrivateKey) {
        	GLRSSPrivateKey glrssPrivateKey = (GLRSSPrivateKey) privateKey;
        	privateKey = glrssPrivateKey.getGsrssKey();
        } 
        
        PrivateKey dPrivateSigKey;
        if (privateKey instanceof GSRSSPrivateKey) {
        	GSRSSPrivateKey gsrssPrivateKey = (GSRSSPrivateKey) privateKey;
        	dPrivateSigKey = gsrssPrivateKey.getDSigKey();
        } else {
        	throw new RuntimeException("Unsupported Key Structure");
        }
        
        byte[] encodedPrivateKey = dPrivateSigKey.getEncoded();
        return encodedPrivateKey;
	}

	@Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewKey);
    }

}
