package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to verify the before signed and possible redacted message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssVerifyRedactedComposite extends RssRightSideComposite {
    private List<String> messages;
    private final List<String> originalSignedMessages;
    private List<Text> textList;

    private final RssAlgorithmController rssAlgorithmController;
    // TODO as long as the implementation on the RssAlgotithmController is weak, not "unsafe"
    private boolean isVerified = true;
    private Label checkImage;
    private Label checkLabel;

    private final Composite inner;
    
    private final Button saveMessageButton;

    public RssVerifyRedactedComposite(RssBodyComposite body, RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.VerifyRedacted);
        prepareAboutComposite();

        rssAlgorithmController = rac;
        rac.verifyRedactedMessage();
        body.lightPath();

        messages = new ArrayList<String>(rac.getMessageParts());
        originalSignedMessages = new ArrayList<String>(messages);

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        Button resetMessageButton = new Button(inner, SWT.PUSH);
        resetMessageButton.setText(Descriptions.Reset);
        resetMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                	List<Boolean> redactedParts = rac.getRedactedParts();
                	int ind = 0;
                	for (int i = 0; i < redactedParts.size(); i++) {
                	    if (!redactedParts.get(i)) {
                	        textList.get(ind).setText(originalSignedMessages.get(i));
                	        ++ind;
                	    }
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
        List<Boolean> redactedParts = rac.getRedactedParts();
        int numberRedacted = 0;
        for (int i = 0; i < messages.size(); i++) {
        	if (redactedParts != null && redactedParts.get(i)) {
        		numberRedacted++;
        		continue;
            }
            Label desc = new Label(addMessageComposite, SWT.READ_ONLY);
            desc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            desc.setText(Descriptions.MessagePart + " " + (i + 1 - numberRedacted));
            Text msg = new Text(addMessageComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP);
            msg.setLayoutData(new GridData(300, 50));
            msg.setText(messages.get(i));
            msg.addModifyListener(getAllNotEmptyListener());
            textList.add(msg);
        }

        checkLabel = new Label(inner, SWT.NONE);
        checkLabel.setText(Descriptions.IsVerified + ": " + Descriptions.Yes);
        checkImage = new Label(inner, SWT.NONE);
        checkImage.setImage(Activator.getImageDescriptor("icons/check.png").createImage(true));
        
        // Button to save the message
        saveMessageButton = new Button(inner, SWT.PUSH);
        saveMessageButton.setText(Descriptions.SaveSign);
        saveMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

            	// Open a dialog to get location for key file storage.
				FileDialog messageStoreDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				messageStoreDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				messageStoreDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				messageStoreDialog.setFileName("redacted-message.xml");
				messageStoreDialog.setOverwrite(true);
				String messageStorePath = messageStoreDialog.open();
            	
				// messageStorePath might be null in case the dialog was closed
				if(messageStorePath != null && !messageStorePath.equals("")) {
					
					// Save the key 
					try {
						rac.saveRedactedSignature(messageStorePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreSign, Descriptions.ErrorStoringSign);				
					}       
				}
            }
        });

        Button nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.ContinueWithRedacting);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.resetStateOverview(DataType.REDACTED, false);
                }
            }
        });


    }

    private ModifyListener getAllNotEmptyListener() {
        ModifyListener listener = new ModifyListener() {
            /** {@inheritDoc} */
            public void modifyText(ModifyEvent e) {
                messages = textList.stream().map(t -> t.getText()).collect(Collectors.toList());
                updateVerified();
            }
        };
        return listener;
    }

    private void updateVerified() {
        List<Boolean> redactedParts = rssAlgorithmController.getRedactedParts();
        List<String> redactedInstances = new LinkedList<>();
        for (int i = 0; i < redactedParts.size(); i++) {
            if (!redactedParts.get(i)) {
                redactedInstances.add(originalSignedMessages.get(i));
            }
        }
        boolean isVerified = redactedInstances.equals(messages);
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
        description.setText(Descriptions.TextVerifyRedacted);
    }

}
