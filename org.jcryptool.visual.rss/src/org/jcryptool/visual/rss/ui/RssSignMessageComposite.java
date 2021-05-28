package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
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
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function select which message parts should be redactable later.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSignMessageComposite extends RssRightSideComposite {

    private final Composite inner;

    private final ArrayList<String> messageList;
    private final ArrayList<Button> buttonList;
    
    private final Button saveMessageButton;
    private final Button loadMessageButton;
    private final Button nextButton;
    private final Button signMessageButton;

    public RssSignMessageComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NULL);
        prepareGroupView(this, Descriptions.SelectFixedParts);
        prepareAboutComposite();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        messageList = new ArrayList<String>();
        buttonList = new ArrayList<Button>();
        List<String> messages = rac.getMessageParts();

        Composite c = new Composite(inner, SWT.NONE);
        c.setLayout(new GridLayout(3, false));
        Label l1 = new Label(c, SWT.READ_ONLY);
        l1.setText(Descriptions.Number);
        Label l2 = new Label(c, SWT.READ_ONLY);
        l2.setText(Descriptions.Message);
        Label l3 = new Label(c, SWT.READ_ONLY);
        l3.setText(Descriptions.Redactable + "?");
        
        // Check if redacting is allowed
        boolean onlyRedactablePartsAllowed = rac.isOnlyRedactablePartsAllowed();
        
        for (int i = 0; i < messages.size(); i++) {
            Label la = new Label(c, SWT.READ_ONLY);
            la.setText("" + (i + 1));
            Text l = new Text(c, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
            labelGridData.widthHint = R_MAX_SIZE;
            l.setLayoutData(labelGridData);
            String msg = messages.get(i);
            msg = getSplittedString(msg, 50);
            l.setText(msg);
            messageList.add(msg);
            Button redactingAllowedCheckbox = new Button(c, SWT.CHECK);
            
            /*
             * If all parts must be redactable, check all checkboxes and disable the control.
             * Otherwise uncheck them and enable the control.
             */
            if (onlyRedactablePartsAllowed) {
            	redactingAllowedCheckbox.setSelection(true);
            	redactingAllowedCheckbox.setEnabled(false);
            } else {
            	redactingAllowedCheckbox.setSelection(false);
            	redactingAllowedCheckbox.setEnabled(true);
            }
            
            buttonList.add(redactingAllowedCheckbox);
        }
        signMessageButton = new Button(inner, SWT.PUSH);
        signMessageButton.setText(Descriptions.SignMessage);

        signMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    signMessageButton.setEnabled(false);
                    List<Boolean> redactableParts = new LinkedList<>();
                    for (int i = 0; i < buttonList.size(); i++) {
                        redactableParts.add(buttonList.get(i).getSelection());
                        buttonList.get(i).setEnabled(false);
                    }
                    rac.signMessage(redactableParts);
                   
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
        
        nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next);
        nextButton.setEnabled(false);
        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                 body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_MESSAGE);
            }
        });
            
        
        // Button to save the message
        saveMessageButton = new Button(inner, SWT.PUSH);
        saveMessageButton.setText(Descriptions.SaveMessage);
        saveMessageButton.setEnabled(false);
        saveMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

            	// Open a dialog to get location for key file storage.
				FileDialog messageStoreDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				messageStoreDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				messageStoreDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				messageStoreDialog.setFileName("signed-message.xml");
				messageStoreDialog.setOverwrite(true);
				String messageStorePath = messageStoreDialog.open();
            	
				// messageStorePath might be null in case the dialog was closed
				if(messageStorePath != null && !messageStorePath.equals("")) {
					
					// Save the key 
					try {
						rac.saveMessage(messageStorePath);
					} catch (FileNotFoundException e1) {
						
						// Open a error message dialog
						MessageBox dialog =
						    new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
						dialog.setText("Failed to store key!");
						dialog.setMessage("There was an error while trying to store the message. Please try again.");
						dialog.open();				
					}       
				}
            }
        });
        
        // Button to load the message
        loadMessageButton = new Button(inner, SWT.PUSH);
        loadMessageButton.setText(Descriptions.LoadMessage);
        loadMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
            	boolean loadingSuccessfully = false;
            	
            	// Open a dialog to get the message store location.
				FileDialog fileOpenDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
				fileOpenDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				fileOpenDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				String messageStorePath = fileOpenDialog.open();
            	
				// Load the key in case a path was selected
				if(messageStorePath != null && !messageStorePath.equals("")) {
					try {
						loadingSuccessfully = rac.loadMessage(messageStorePath);
					} catch (FileNotFoundException e1) {
						
						// Open a error message dialog
						MessageBox dialog =
						    new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
						dialog.setText("Failed to load key!");
						dialog.setMessage("There was an error while trying to load the key. Please try again.");
						dialog.open();				
					} catch (NoSuchAlgorithmException e1) {
						
						// Open a error message dialog
						MessageBox dialog =
						    new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
						dialog.setText("Failed to load key!");
						dialog.setMessage("The given input is no valid key or is not supported by this visualisation.");
						dialog.open();	
					} 
				
					if(loadingSuccessfully) {
						
						// Change active buttons
						nextButton.setEnabled(true);
	                	saveMessageButton.setEnabled(true);
	                	signMessageButton.setEnabled(false);
	                	
	                    // Change visual
			           	body.lightPath();
			            body.lightDataBox(DataType.MESSAGE);
					}		
				}
            }
        });

    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSignMessage);
    }

}
