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
import org.jcryptool.visual.rss.algorithm.MessagePart;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to verify the before signed and possible redacted message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssVerifyRedactedComposite extends RssRightSideComposite {
    private List<MessagePart> messages;
    private List<Text> textList;

    private final RssAlgorithmController rssAlgorithmController;
    private boolean isVerified;
    private Label checkImage;
    private Label checkLabel;

    private final Composite inner;
    
    private final Button saveMessageButton;

    public RssVerifyRedactedComposite(RssBodyComposite body, RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.VerifyRedacted);
        prepareAboutComposite();

        rssAlgorithmController = rac;
        isVerified = rac.verifyCurrentSignature();
        body.lightPath();

        messages = rac.getMessageParts();
        //originalSignedMessages = new ArrayList<String>(messages);

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        /*
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
        });*/

        if(messages != null) {
        	textList = new ArrayList<Text>();
            Composite addMessageComposite = new Composite(inner, SWT.NULL);
            addMessageComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
            addMessageComposite.setLayout(new GridLayout(1, false));
            //int numberRedacted = 0;
            for (int i = 0; i < messages.size(); i++) {
            //	if (redactedParts != null && redactedParts.get(i)) {
            //		numberRedacted++;
            //		continue;
            //    }
                Label desc = new Label(addMessageComposite, SWT.READ_ONLY);
                desc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            //  desc.setText(Descriptions.MessagePart + " " + (i + 1 - numberRedacted));
                Text msg = new Text(addMessageComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP);
                msg.setLayoutData(new GridData(300, 50));
                msg.setText(messages.get(i).getMessage());
                //msg.addModifyListener(getAllNotEmptyListener());
                textList.add(msg);
            }
        }
        

        if(isVerified) {
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
        Button nextButton = new Button(inner, SWT.PUSH);
        nextButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.resetStateOverview(DataType.REDACTED, false);
                }
            }
        });
        
        if(!isVerified) {
        	nextButton.setEnabled(false);
        }

        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSave);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));
        
        // Button to save the signature
        saveMessageButton = new Button(saveLoad, SWT.PUSH);
        saveMessageButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

            	// Open a dialog to get location for signature file storage.
				FileDialog storeDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				storeDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				storeDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				storeDialog.setFileName("redacted-message.xml");
				storeDialog.setOverwrite(true);
				String storePath = storeDialog.open();
            	
				// storePath might be null in case the dialog was closed
				if(storePath != null && !storePath.equals("")) {
					
					// Save the current signature 
					try {
						rac.saveCurrentSignature(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreSign, Descriptions.ErrorStoringSign);				
					}       
				}
            }
        });

    }

    /*private ModifyListener getAllNotEmptyListener() {
        ModifyListener listener = new ModifyListener() {
           
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
    */

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextVerifyRedacted);
    }

}
