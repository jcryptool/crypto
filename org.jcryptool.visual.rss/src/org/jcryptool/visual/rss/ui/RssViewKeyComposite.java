package org.jcryptool.visual.rss.ui;

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
            l2.setText(info.getKeyType().getKt());
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
            byte[] s1 = ((GSRSSPrivateKey) ((GLRSSPrivateKey) info.getKeyPair().getPrivate()).getGsrssKey()).getDSigKey().getEncoded();
            String text1 = new String(Base64.getEncoder().encode(s1));
            t1.setText(getSplittedString(text1, 50));
            Label l2 = new Label(leftComposite, SWT.NONE);
            l2.setText(Descriptions.PublicKey);
            Text t2 = new Text(leftComposite, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
            t2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            byte[] s2 = ((GSRSSPublicKey) ((GLRSSPublicKey) info.getKeyPair().getPublic()).getGsrssKey()).getDSigKey().getEncoded();
            String text2 = new String(Base64.getEncoder().encode(s2));
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

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewKey);
    }

}
