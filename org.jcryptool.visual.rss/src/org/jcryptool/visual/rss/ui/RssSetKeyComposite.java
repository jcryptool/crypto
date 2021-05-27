package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyPersistence;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.AlgorithmType;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Information;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to set the keys for the encryption.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSetKeyComposite extends RssRightSideComposite {
    private final Combo keySizeCombo;
    private final Button generateKeyButton;
    private final Combo algorithmSelectionCombo;
    private final Composite inner;
    private final Button saveKeyButton;
    private final Button loadKeyButton;
    private final Button nextButton;

    public RssSetKeyComposite(RssBodyComposite body, final RssAlgorithmController rssController) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.AlgorithmSettings);
        prepareAboutComposite();
        
        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());
        
        // Select algorithm variant text
        Label algorithmVariantLabel = new Label(inner, SWT.READ_ONLY);
        algorithmVariantLabel.setText(Descriptions.SelectAlgorithmVariant);
        
        // Dropdown to select the algorithm variant        
        algorithmSelectionCombo = new Combo(inner, SWT.READ_ONLY);
        for(AlgorithmType signatureType: AlgorithmType.values()) {
        	algorithmSelectionCombo.add(signatureType.toString());
        }
        algorithmSelectionCombo.select(0);

        // Select key length text
        Label keyLengthLabel = new Label(inner, SWT.READ_ONLY);
        keyLengthLabel.setText(Descriptions.SelectKeySize);
        
        // Dropdown to select the key length
        keySizeCombo = new Combo(inner, SWT.READ_ONLY);
        for(KeyLength keyLength: KeyLength.values()) {
        	keySizeCombo.add(keyLength.toString());
        }
        keySizeCombo.select(0);
        
        // Button to generate a new key and set the algorithm controller for the selected size and variant
        generateKeyButton = new Button(inner, SWT.PUSH);
        generateKeyButton.setText(Descriptions.GenerateKey);
               
        // On click listener for the generate key button
        generateKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                	
                	// Disable controls
                    generateKeyButton.setEnabled(false);
                    keySizeCombo.setEnabled(false);
                    algorithmSelectionCombo.setEnabled(false);
                    
                    // Get selected length and algorithm type
                    KeyLength length = getKeyLength();
                    AlgorithmType type = getAlgorithmType();
                                        
                    // Generate a new keypair and set the chosen algorithm variant to the rssController
                    rssController.genKeyAndSignature(type, length);
                    
                    // Change the visual
                    body.lightPath();
                    body.lightDataBox(DataType.KEY);
                    
                    // Continue with the singing step
                    nextButton.setEnabled(true);
                    saveKeyButton.setEnabled(true);
                    
                    break;
                }
            }
        });
        
        // Button to save key
        saveKeyButton = new Button(inner, SWT.PUSH);
        saveKeyButton.setText(Descriptions.SaveKey);
        saveKeyButton.setEnabled(false);
        saveKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
            	
            	Information keyInformation = rssController.getInformation();
            	//byte[] publicKey = RssViewKeyComposite.getPublicKey(keyInformation.getKeyPair());
            	try {
                	System.out.print(keyInformation.getKeyPair());

					KeyPersistence.saveKeyPair(keyInformation.getKeyPair(), "F:/tmp/test.xml");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
               
                
            }
        });
        
        // Button to load the key
        loadKeyButton = new Button(inner, SWT.PUSH);
        loadKeyButton.setText(Descriptions.LoadKey);
        loadKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

            	KeyPair keyPair = null;
            	// Load the key
            	try {
					keyPair = KeyPersistence.loadKeyPair("F:/tmp/test.xml");
					//rssController.setKey(keyPair);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            	System.out.print(keyPair);
            	
            	nextButton.setEnabled(true);
            }
        });
        
        // Next button
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
            }
        });
        
        
    }

    private KeyLength getKeyLength() {
        String keyLength = keySizeCombo.getItem(keySizeCombo.getSelectionIndex());
        return KeyLength.getItem(keyLength);
    }
    
    private AlgorithmType getAlgorithmType() {
    	String keyText = algorithmSelectionCombo.getItem(algorithmSelectionCombo.getSelectionIndex());
    	return AlgorithmType.fromString(keyText);
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSetKey);
    }
}