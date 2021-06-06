package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
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
import org.jcryptool.visual.rss.persistence.InvalidSignatureException;
import org.jcryptool.visual.rss.persistence.MessageAndRedactable;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function select which message parts should be redactable later.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSignMessageComposite extends RssRightSideComposite {

    private final Composite inner;

    private ArrayList<String> messageList;
    private ArrayList<Button> buttonList;
    
    private final Button saveMessageButton;
    private final Button loadMessageButton;
    private final Button nextButton;
    private final Button signMessageButton;
    
    private final boolean onlyRedactablePartsAllowed;

    public RssSignMessageComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NULL);
        prepareGroupView(this, Descriptions.SelectFixedParts);
        prepareAboutComposite();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        List<MessagePart> messageParts = rac.getMessageParts();

        Composite c = new Composite(inner, SWT.NONE);
        c.setLayout(new GridLayout(3, false));
        Label l1 = new Label(c, SWT.READ_ONLY);
        l1.setText(Descriptions.Number);
        Label l2 = new Label(c, SWT.READ_ONLY);
        l2.setText(Descriptions.Message);
        Label l3 = new Label(c, SWT.READ_ONLY);
        l3.setText(Descriptions.Redactable + "?");
        
        // Check if redacting is allowed
        onlyRedactablePartsAllowed = rac.isOnlyRedactablePartsAllowed();
        
        setMessagePartsAndRedactable(c, messageParts, null);
        signMessageButton = new Button(inner, SWT.PUSH);
        signMessageButton.setText(Descriptions.SignMessage);

        signMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    signMessageButton.setEnabled(false);
                    for (int i = 0; i < buttonList.size(); i++) {
                    	messageParts.get(i).setRedactable(buttonList.get(i).getSelection());
                    }
                    rac.signMessage(messageParts);
                   
                    // Change active buttons
                    nextButton.setEnabled(true);
                    saveMessageButton.setEnabled(true);
                    
                    // Change visual
		           	body.lightPath();
		            body.lightDataBox(DataType.MESSAGE);
                    
                    break;
                }
            }
        });
        
        
        // Next button
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        nextButton.setEnabled(false);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                 body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_MESSAGE);
            }
        });
        
        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSave);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));
        
       
        // Button to load the message
        loadMessageButton = new Button(saveLoad, SWT.PUSH);
        loadMessageButton.setImage(Activator.getImageDescriptor("icons/outline_file_download_black_24dp.png").createImage(true));
        loadMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
        		boolean loadingSuccess = false;
            	
            	// Open a dialog to get the message store location.
				FileDialog fileOpenDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
				fileOpenDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				fileOpenDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				String messageStorePath = fileOpenDialog.open();
            	
				// Load the key in case a path was selected
				if(messageStorePath != null && !messageStorePath.equals("")) {
					
			
					try {
						loadingSuccess = rac.loadSignature(messageStorePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToLoadSign, Descriptions.ErrorLoadingSign);				
					} catch (InvalidSignatureException e1) {
						showErrorDialog(Descriptions.FailedToLoadSign, Descriptions.InvalidSign);	
					} 
				
					if(loadingSuccess) {
						
						// Change visual
			           	body.lightPath();
			            body.lightDataBox(DataType.MESSAGE);
			            
						body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_MESSAGE);
					
					}		
				}
        	}
        });
            
        // Button to save the signature
        saveMessageButton = new Button(saveLoad, SWT.PUSH);
        saveMessageButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveMessageButton.setEnabled(false);
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
					
					// Save the signature 
					try {
						rac.saveOriginalSignature(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreKey, Descriptions.ErrorStoringKey);
					}       
				}
            }
        });

    }

	private void setMessagePartsAndRedactable(Composite c, List<MessagePart> messages, List<Boolean> redactableParts) {
       messageList = new ArrayList<String>();
       buttonList = new ArrayList<Button>();
		
		for (int i = 0; i < messages.size(); i++) {
            Label la = new Label(c, SWT.READ_ONLY);
            la.setText("" + (i + 1));
            Text l = new Text(c, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
            labelGridData.widthHint = R_MAX_SIZE;
            l.setLayoutData(labelGridData);
            String msg = messages.get(i).getMessage();
            msg = getSplittedString(msg, 50);
            l.setText(msg);
            messageList.add(msg);
            Button redactingAllowedCheckbox = new Button(c, SWT.CHECK);
  
            if (onlyRedactablePartsAllowed) {
            	
            	// If all parts must be redactable, check all checkboxes and disable the control.
            	redactingAllowedCheckbox.setSelection(true);
            	redactingAllowedCheckbox.setEnabled(false);
            } else {
            	if(redactableParts != null) {
            		
            		// If the part got loaded and redactableParts are already defined, disable the controls and set the selection as defined.
            		boolean isRedactable = redactableParts.get(i);
            		redactingAllowedCheckbox.setEnabled(false);
            		redactingAllowedCheckbox.setSelection(isRedactable);
            	} else {
            		
                    // Otherwise uncheck them and enable the control.
            		redactingAllowedCheckbox.setSelection(false);
                	redactingAllowedCheckbox.setEnabled(true);
            	}
            }
            
            buttonList.add(redactingAllowedCheckbox);
        }
	}

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSignMessage);
    }

}
