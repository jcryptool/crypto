package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.MessagePart;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.persistence.XMLPersistence;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to verify the before signed and not yet redacted message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssVerifyMessageComposite extends RssRightSideComposite {

    private int numberMessages;
    private List<MessagePart> messages;
    private List<Text> textList;
    private final Button saveMessageButton;

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

        boolean isValid = false;
		try {
			isValid = rac.verifyOriginalSignature();
		} catch (InvalidKeyException e2) {
			isValid = false;
		}
 
        body.lightPath();

        messages = rac.getMessageParts();
        numberMessages = messages.size();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        /*Button resetMessageButton = new Button(inner, SWT.PUSH);
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
        });*/

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
            msg.setText(messages.get(i).getMessage());
            //msg.addModifyListener(getAllNotEmptyListener());
            textList.add(msg);
        }

       if(isValid) {
           checkLabel = new Label(inner, SWT.NONE);
           checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.Yes);
           checkImage = new Label(inner, SWT.NONE);
           checkImage.setImage(Activator.getImageDescriptor("icons/check.png").createImage(true));
        } else {
            checkLabel = new Label(inner, SWT.NONE);
            checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.No);
            checkImage = new Label(inner, SWT.NONE);
            checkImage.setImage(Activator.getImageDescriptor("icons/notcheck.png").createImage(true));
        }

        // Next button
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next);
        nextButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                body.setActiveRssComposite(ActiveRssBodyComposite.REDACT);
            }
        });
        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSaveSignedMessage);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));
                   
        // Button to save the signature
        saveMessageButton = new Button(saveLoad, SWT.PUSH);
        saveMessageButton.setText(Descriptions.Save);
        saveMessageButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

            	// Open a dialog to get location for signature file storage.
				FileDialog storeDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				storeDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				storeDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				storeDialog.setFileName("signed-message.xml");
				storeDialog.setOverwrite(true);
				String storePath = storeDialog.open();
            	
				// storePath might be null in case the dialog was closed
				if(storePath != null && !storePath.equals("")) {
					
					// Save the original signature 
					try {
						rac.saveOriginalSignature(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreKey, Descriptions.ErrorStoringKey);
					}       
				}
            }
        });
        
        if(!isValid) {
        	nextButton.setEnabled(false);
        }
    }

   /* private ModifyListener getAllNotEmptyListener() {
        ModifyListener listener = new ModifyListener() {
          
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
    }*/

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextVerifyMessage);
    }

}
