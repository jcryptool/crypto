package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyPairGeneratorType;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.MaxMessageParts;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Scheme;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.UnderlayingSignatureScheme;
import org.jcryptool.visual.rss.persistence.XMLPersistence;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Accumulator;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.AlgorithmType;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.HashMethod;
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
    private final Label hashMethodLabel;
    private final Combo hashMethodCombo;
    private final Label maxMessagePartsLabel;
    private final Combo maxMessagePartsCombo;
    private final Label accumulatorLabel;
    private final Combo accumulatorCombo;
    private final Label underlayingSignatureSchemeLabel;
    private final Combo underlayingSignatureSchemeCombo;
    
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
        
        hashMethodLabel = new Label(inner, SWT.READ_ONLY);
        hashMethodLabel.setText(Descriptions.SelectHashMethod);
        hashMethodCombo = new Combo(inner, SWT.READ_ONLY);
        for(HashMethod hashMethod: HashMethod.values()) {
        	hashMethodCombo.add(hashMethod.toString());
        }
        hashMethodCombo.select(0);
        
        // Begin of optional selection boxes
        accumulatorLabel = new Label(inner, SWT.READ_ONLY);
        accumulatorLabel.setText(Descriptions.SelectAccumulator);
        accumulatorCombo = new Combo(inner, SWT.READ_ONLY);
        for(Accumulator accumulator: Accumulator.values()) {
        	accumulatorCombo.add(accumulator.toString());
        }
        accumulatorCombo.select(0);
        
        underlayingSignatureSchemeLabel = new Label(inner, SWT.READ_ONLY);
        underlayingSignatureSchemeLabel.setText(Descriptions.SelectUnderlayingSignatureScheme);
        underlayingSignatureSchemeCombo = new Combo(inner, SWT.READ_ONLY);
        for(UnderlayingSignatureScheme underlayingSignatureScheme: UnderlayingSignatureScheme.values()) {
        	underlayingSignatureSchemeCombo.add(underlayingSignatureScheme.toString());
        }
        underlayingSignatureSchemeCombo.select(0);
        
        maxMessagePartsLabel = new Label(inner, SWT.READ_ONLY);
        maxMessagePartsLabel.setText(Descriptions.SelectMaxMessageParts);
        maxMessagePartsCombo = new Combo(inner, SWT.READ_ONLY);
        for(MaxMessageParts maxMessageParts: MaxMessageParts.values()) {
        	maxMessagePartsCombo.add(maxMessageParts.toString());
        }
        maxMessagePartsCombo.select(0);
        // End of optional selection boxes
        
        // Show/Hide optional selection boxes
        algorithmSelectionCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
        		AlgorithmType selected = AlgorithmType.valueOf(algorithmSelectionCombo.getText());
        		showHideCombos(selected);
            }
        });
        showHideCombos(AlgorithmType.GLRSS);
        
        // Disable SHA_512 with KeySize 512
        hashMethodCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	HashMethod hashMethod = HashMethod.valueOf(hashMethodCombo.getText());
        		KeyLength keySize = KeyLength.getItem(keySizeCombo.getText());

        		if(hashMethod == HashMethod.SHA_512 && keySize == KeyLength.KL_512) {
        			showWarning(Descriptions.ConfigurationNotAllowed, Descriptions.InvalidConfiguration);
        			hashMethodCombo.select(0);
        		}
            }
        });
        
        keySizeCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
            	HashMethod hashMethod = HashMethod.valueOf(hashMethodCombo.getText());
        		KeyLength keySize = KeyLength.getItem(keySizeCombo.getText());

        		if(hashMethod == HashMethod.SHA_512 && keySize == KeyLength.KL_512) {
        			showWarning(Descriptions.ConfigurationNotAllowed, Descriptions.InvalidConfiguration);
        			keySizeCombo.select(1);
        		}
            }
        });
   
        
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
                    
                    // Get selected params
                    KeyLength length = getSelectedKeyLength();
                    AlgorithmType algorithmType = getSelectedAlgorithmType();
                    HashMethod hashMethod = getSelectedHashMethod();
                    MaxMessageParts maxMessageParts = getSelectedMaxMessageParts();
                    Accumulator accumulator = getSelectedAccumulator();
                    UnderlayingSignatureScheme underlayingSignatureScheme = getSelectedUnderlayingSignatureScheme();
                    
                    // Get scheme for selected params
                    Scheme scheme = RssAlgorithmController.getScheme(algorithmType, hashMethod, accumulator, underlayingSignatureScheme);    
                    KeyPairGeneratorType keyGenType = RssAlgorithmController.getKeyGenType(algorithmType, maxMessageParts);
                                        
                    // Generate a new keypair and set the chosen algorithm variant to the rssController
                    rssController.generateKey(scheme, keyGenType, length);
                    
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
        

        // Next button
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next);
        nextButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        nextButton.setEnabled(false);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
            }
        });
        
        
        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSave);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));
        
        // Button to load the key
        loadKeyButton = new Button(saveLoad, SWT.PUSH);
        loadKeyButton.setText(Descriptions.Load);
        loadKeyButton.setImage(Activator.getImageDescriptor("icons/outline_file_download_black_24dp.png").createImage(true));
        loadKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
            	KeyInformation loadedKeyInformation = null;;
            	
            	// Open a dialog to get the key store location.
				FileDialog fileOpenDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
				fileOpenDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				fileOpenDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				String keyStorePath = fileOpenDialog.open();
            	
				// Load the key in case a path was selected
				if(keyStorePath != null && !keyStorePath.equals("")) {
					try {
						loadedKeyInformation = rssController.loadKey(keyStorePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToLoadKey, Descriptions.ErrorLoadingKey);
					} catch (NoSuchAlgorithmException | InvalidKeyException e1) {
						showErrorDialog(Descriptions.FailedToLoadKey, Descriptions.InvalidKey);
					}
				
					// It is null in case the loading was not successful
					if(loadedKeyInformation != null) {
						keySizeCombo.setText(getSelectedKeyLength().toString());
						algorithmSelectionCombo.setText(loadedKeyInformation.getAlgorithmType().toString());
						
						// Disable controls
		                generateKeyButton.setEnabled(false);
		                keySizeCombo.setEnabled(false);
		                algorithmSelectionCombo.setEnabled(false);
						
		                // Change the visual
		                body.lightPath();
		                body.lightDataBox(DataType.KEY);
		                        	
		                // Enable next button
		            	nextButton.setEnabled(true);
					}
				}
            }
        });
        
        
        // Button to save key
        saveKeyButton = new Button(saveLoad, SWT.PUSH);
        saveKeyButton.setText(Descriptions.Save);
        saveKeyButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveKeyButton.setEnabled(false);
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
						rssController.saveKey(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreKey, Descriptions.ErrorStoringKey);			
					}            
				}
            }
        });
        
        
    }
        
	private void showHideCombos(AlgorithmType selected) {
		switch(selected) {
			case GLRSS:
				maxMessagePartsCombo.setVisible(false);
				maxMessagePartsLabel.setVisible(false);
				
				accumulatorLabel.setVisible(true);
				accumulatorCombo.setVisible(true);
				
				underlayingSignatureSchemeLabel.setVisible(false);
				underlayingSignatureSchemeCombo.setVisible(false);
			break;
		case GSRSS:
				maxMessagePartsCombo.setVisible(false);
				maxMessagePartsLabel.setVisible(false);
				
				accumulatorLabel.setVisible(true);
				accumulatorCombo.setVisible(true);
				
				underlayingSignatureSchemeLabel.setVisible(false);
				underlayingSignatureSchemeCombo.setVisible(false);
			break;
		case GC:
				maxMessagePartsCombo.setVisible(false);
				maxMessagePartsLabel.setVisible(false);
				
				accumulatorLabel.setVisible(false);
				accumulatorCombo.setVisible(false);
				
				underlayingSignatureSchemeLabel.setVisible(true);
				underlayingSignatureSchemeCombo.setVisible(true);
			break;
		case MERSA:
				maxMessagePartsCombo.setVisible(true);
				maxMessagePartsLabel.setVisible(true);
				
				accumulatorLabel.setVisible(false);
				accumulatorCombo.setVisible(false);
				
				underlayingSignatureSchemeLabel.setVisible(false);
				underlayingSignatureSchemeCombo.setVisible(false);
			break;
		default:
			break;
		}
	}
	
	private void showWarning(String title, String message) {
		MessageBox dialog =
		    new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK);
		dialog.setText(title);
		dialog.setMessage(message);
		dialog.open();		
	}

    private KeyLength getSelectedKeyLength() {
        String keyLength = keySizeCombo.getItem(keySizeCombo.getSelectionIndex());
        return KeyLength.getItem(keyLength);
    }
    
    private AlgorithmType getSelectedAlgorithmType() {
    	String keyText = algorithmSelectionCombo.getItem(algorithmSelectionCombo.getSelectionIndex());
    	return AlgorithmType.valueOf(keyText);
    }
    
    private UnderlayingSignatureScheme getSelectedUnderlayingSignatureScheme() {
    	String text = underlayingSignatureSchemeCombo.getItem(underlayingSignatureSchemeCombo.getSelectionIndex());
    	return UnderlayingSignatureScheme.valueOf(text);
	}

	private Accumulator getSelectedAccumulator() {
    	String text = accumulatorCombo.getItem(accumulatorCombo.getSelectionIndex());
    	return Accumulator.valueOf(text);
	}

	private MaxMessageParts getSelectedMaxMessageParts() {
    	String text = maxMessagePartsCombo.getItem(maxMessagePartsCombo.getSelectionIndex());
    	return MaxMessageParts.getItem(text);
	}

	private HashMethod getSelectedHashMethod() {
    	String text = hashMethodCombo.getItem(hashMethodCombo.getSelectionIndex());
    	return HashMethod.valueOf(text);
	}

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSetKey);
    }
}