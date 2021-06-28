package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

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
	
	private final Button saveKeyButton;
	

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
            Label l1 = new Label(inner, SWT.READ_ONLY);
            l1.setText(Descriptions.PrivateKey);
            Text t1 = new Text(inner, SWT.NONE | SWT.MULTI | SWT.WRAP);
            t1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            
            KeyPair keyPair = info.getKeyPair();
            byte[] encodedPrivateKey = getPrivateKey(keyPair);
            
            String text1 = new String(Base64.getEncoder().encode(encodedPrivateKey));
            t1.setText(getSplittedString(text1, 50));
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(Descriptions.PublicKey);
            Text t2 = new Text(inner, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
            t2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
                       
            byte[] encodedPublicKey = getPublicKey(keyPair);
            
            String text2 = new String(Base64.getEncoder().encode(encodedPublicKey));
            t2.setText(getSplittedString(text2, 50));
        }
        
        Button returnButton = new Button(inner, SWT.PUSH);
        returnButton.setText(Descriptions.ReturnButton);
        returnButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_before_black_24dp.png").createImage(true));
        returnButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    body.returnToCurrentState();
                }
            }
        });
        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSave);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));       
        
        // Button to save key
        saveKeyButton = new Button(saveLoad, SWT.PUSH);
        saveKeyButton.setText(Descriptions.Save);
        saveKeyButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
            	
            	// Open a dialog to get location for key file storage.
				FileDialog storeDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				storeDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				storeDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				storeDialog.setFileName("key.xml");
				storeDialog.setOverwrite(true);
				String storePath = storeDialog.open();
            	
				// storePath might be null in case the dialog was closed
				if(storePath != null && !storePath.equals("")) {
					
					// Save the key 
					try {
						rac.saveKey(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreKey, Descriptions.ErrorStoringKey);			
					}            
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
