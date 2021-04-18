package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.AlgorithmType;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to set the keys for the encryption.
 * TODO: Remove dead code of next button
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSetKeyComposite extends RssRightSideComposite {
    private final Combo keySizeCombo;
    private final Button generateKeyButton;
    //private final Button nextButton;
    private final Combo algorithmSelectionCombo;

    public RssSetKeyComposite(RssBodyComposite body, final RssAlgorithmController rssController) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.SelectKeySize);
        prepareAboutComposite();
        
        // Dropdown to select the algorithm variant
        algorithmSelectionCombo = new Combo(leftComposite, SWT.READ_ONLY);
        for(AlgorithmType signatureType: AlgorithmType.values()) {
        	algorithmSelectionCombo.add(signatureType.getKt());
        }
        algorithmSelectionCombo.select(0);

        
        // Dropdown to select the key length
        keySizeCombo = new Combo(leftComposite, SWT.READ_ONLY);
        keySizeCombo.add("KL_512");
        keySizeCombo.add("KL_1024");
        keySizeCombo.add("KL_2048" + " " + Descriptions.TakesLongTime);
        keySizeCombo.select(0);
        
        // Button to generate a new key and set the algorithm controller for the selected size and variant
        generateKeyButton = new Button(leftComposite, SWT.PUSH);
        generateKeyButton.setText(Descriptions.GenerateKey);

        // Next button to continue with setting the message parts. Will be enabled when a key is generated.
//        nextButton = new Button(leftComposite, SWT.PUSH);
//        nextButton.setText(Descriptions.Next + ": " + Descriptions.NewMessage);
//        nextButton.setEnabled(false);
               
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
                    KeyLength length = getLength();
                    AlgorithmType type = getType();
                    
                    // Generate a new keypair and set the chosen algorithm variant to the rssController
                    rssController.genKeyAndSignature(type, length);
                    
                    // Change the visual
                    body.lightPath();
                    body.lightDataBox(DataType.KEY);
                    
                    // Continue with the singing step
                    //nextButton.setEnabled(true);
                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
                    
                    break;
                }
            }
        });
        
        // On click listener for the next button
//        nextButton.addListener(SWT.Selection, new Listener() {
//            public void handleEvent(Event e) {
//                switch (e.type) {
//                case SWT.Selection:
//                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
//                    break;
//                }
//            }
//        });
    }

    private KeyLength getLength() {
        String keyLength = keySizeCombo.getItem(keySizeCombo.getSelectionIndex());
       
        if (keyLength.contains("512")) {
            return KeyLength.KL_512;
        }
        if (keyLength.contains("1024")) {
            return KeyLength.KL_1024;
        }
        return KeyLength.KL_2048;
    }
    
    private AlgorithmType getType() {
    	String keyText = algorithmSelectionCombo.getItem(algorithmSelectionCombo.getSelectionIndex());
    	return AlgorithmType.fromString(keyText);
    }

//    public void resetKey() {
//        keySizeCombo.setEnabled(true);
//        generateKeyButton.setEnabled(true);
//        nextButton.setEnabled(true);
//    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSetKey);
    }
}