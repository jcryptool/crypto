package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.KeyLength;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

public class RssSetKeyComposite extends RssRightSideComposite {
    private final Combo keySizeCombo;
    private final Button genKeyButton;
    private final Button nextButton;

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

        genKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    genKeyButton.setEnabled(false);
                    keySizeCombo.setEnabled(false);
                    KeyLength length = getLength();
                    rac.genKeyAndSignature(RssAlgorithmController.KeyType.GLRSS_WITH_RSA_AND_BPA, length);
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
        String keyText = keySizeCombo.getItem(keySizeCombo.getSelectionIndex());
        if (keyText.contains("512")) {
            return KeyLength.KL_512;
        }
        if (keyText.contains("1024")) {
            return KeyLength.KL_1024;
        }
        return KeyLength.KL_2048;
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