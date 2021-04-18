package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyType;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to set the keys for the encryption.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSetKeyComposite extends RssRightSideComposite {
    private final Combo keySizeCombo;
    private final Button genKeyButton;
    private final Button nextButton;
    
    private final Combo algorithmSelectionCombo;

    public RssSetKeyComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.SelectKeySize);
        prepareAboutComposite();

        keySizeCombo = new Combo(leftComposite, SWT.READ_ONLY);
        keySizeCombo.add("KL_512");
        keySizeCombo.add("KL_1024");
        keySizeCombo.add("KL_2048" + " " + Descriptions.TakesLongTime);
        keySizeCombo.select(0);

        genKeyButton = new Button(leftComposite, SWT.PUSH);
        genKeyButton.setText(Descriptions.GenerateKey);

        nextButton = new Button(leftComposite, SWT.PUSH);
        nextButton.setText(Descriptions.Next + ": " + Descriptions.NewMessage);
        nextButton.setEnabled(false);
        
        // Select the algorithm variant
        algorithmSelectionCombo = new Combo(leftComposite, SWT.READ_ONLY);
        for(KeyType signatureType: KeyType.values()) {
        	algorithmSelectionCombo.add(signatureType.getKt());
        }
        algorithmSelectionCombo.select(0);
        
        
        genKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    genKeyButton.setEnabled(false);
                    keySizeCombo.setEnabled(false);
                    KeyLength length = getLength();
                 
                    KeyType type = getType();
                    rac.genKeyAndSignature(type, length);
                    body.lightPath();
                    body.lightDataBox(DataType.KEY);
                    nextButton.setEnabled(true);
                    break;
                }
            }
        });
        
      

        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
                    break;
                }
            }
        });
    }

    private KeyLength getLength() {
        String keySize = keySizeCombo.getItem(keySizeCombo.getSelectionIndex());
        if (keySize.contains("512")) {
            return KeyLength.KL_512;
        }
        if (keySize.contains("1024")) {
            return KeyLength.KL_1024;
        }
        return KeyLength.KL_2048;
    }
    
    private KeyType getType() {
    	String keyText = algorithmSelectionCombo.getItem(algorithmSelectionCombo.getSelectionIndex());
    	System.out.println(keyText);
    	return KeyType.fromString(keyText);
    }

    public void resetKey() {
        keySizeCombo.setEnabled(true);
        genKeyButton.setEnabled(true);
        nextButton.setEnabled(true);
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSetKey);
    }
}