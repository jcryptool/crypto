package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.KeyPersistence;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to verify the before signed and not yet redacted message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssVerifyMessageComposite extends RssRightSideComposite {

    private int numberMessages;
    private ArrayList<String> messages;
    private final ArrayList<String> originalSignedMessages;
    private ArrayList<Text> textList;

    private final RssAlgorithmController rssAlgorithmController;
    // TODO as long as the implementation on the RssAlgotithmController is weak, not "unsafe"
    private boolean isVerified = true;
    private Label checkImage;
    private Label checkLabel;

    private final Composite inner;
    
    private final Button nextButton;

    public RssVerifyMessageComposite(RssBodyComposite body, RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.VerifyMessage);
        prepareAboutComposite();

        rssAlgorithmController = rac;
        rac.verifyOriginalMessage();
        body.lightPath();

        messages = new ArrayList<String>(rac.getMessageParts());
        originalSignedMessages = new ArrayList<String>(messages);
        numberMessages = messages.size();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        Button resetMessageButton = new Button(inner, SWT.PUSH);
        resetMessageButton.setText(Descriptions.Reset);
        resetMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    for (int i = 0; i < textList.size(); i++) {
                        textList.get(i).setText(originalSignedMessages.get(i));
                    }
                    updateVerified();
                    break;
                }
            }
        });

        textList = new ArrayList<Text>();
        Composite addMessageComposite = new Composite(inner, SWT.NULL);
        addMessageComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        addMessageComposite.setLayout(new GridLayout(1, false));
        for (int i = 0; i < numberMessages; i++) {
            Label desc = new Label(addMessageComposite, SWT.READ_ONLY);
            desc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            desc.setText(Descriptions.MessagePart + " " + (i + 1));
            Text msg = new Text(addMessageComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP);
            msg.setLayoutData(new GridData(300, 50));
            msg.setText(messages.get(i));
            msg.addModifyListener(getAllNotEmptyListener());
            textList.add(msg);
        }

        checkLabel = new Label(inner, SWT.NONE);
        checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.Yes);
        checkImage = new Label(inner, SWT.NONE);
        // checkLabel.setLayoutData(buttonGd);
        checkImage.setImage(Activator.getImageDescriptor("icons/check.png").createImage(true));

        Button confirmButton = new Button(inner, SWT.PUSH);
        confirmButton.setText(Descriptions.ContinueWithRedacting);
        confirmButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                	nextButton.setEnabled(true);
                }
            }
        });
               
        // Next button
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                body.setActiveRssComposite(ActiveRssBodyComposite.REDACT);
            }
        });
    }

    private ModifyListener getAllNotEmptyListener() {
        ModifyListener listener = new ModifyListener() {
            /** {@inheritDoc} */
            public void modifyText(ModifyEvent e) {
                List<String> newMsg = new ArrayList();
                for (int i = 0; i < textList.size(); i++) {
                    newMsg.add(textList.get(i).getText());
                }
                messages = new ArrayList(newMsg);
                // TODO rssAlgorithmController.newMessage(newMsg);
                updateVerified();
            }
        };
        return listener;
    }

    private void updateVerified() {
        boolean isVerified = originalSignedMessages.equals(messages);
        if (isVerified != this.isVerified) {
            if (isVerified) {
                checkImage.setImage(Activator.getImageDescriptor("icons/check.png").createImage());
                checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.Yes);
            } else {
                checkImage.setImage(Activator.getImageDescriptor("icons/notcheck.png").createImage());
                checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.No);
            }
        }
        this.isVerified = isVerified;
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextVerifyMessage);
    }

}
