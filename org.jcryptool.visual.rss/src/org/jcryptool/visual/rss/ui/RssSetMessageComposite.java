package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.persistence.InvalidSignatureException;
import org.jcryptool.visual.rss.persistence.MessageAndRedactable;
import org.jcryptool.visual.rss.persistence.XMLPersistence;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to set the message parts to be encrypted.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSetMessageComposite extends RssRightSideComposite {
    private static int DEFAULT_NUMBER_PARTS = 3;
    private static int MAX_NUMBER_PARTS = 5;
    private static int MIN_NUMBER_PARTS = 2;

    private final Composite inner;
    private final Composite addMessageComposite;
    private final List<Text> addMessageTextList;
    private final List<Label> addMessageLabelList;
    private final Button addMessagePartButton;
    private final Button removeMessagePartButton;
    private final Button confirmMessageButton;
  
    private final Button loadMessageButton;

    public RssSetMessageComposite(RssBodyComposite body, RssAlgorithmController rac, int numberMessageParts,
            List<String> oldMessages) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.SetMessages);
        prepareAboutComposite();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        addMessageComposite = new Composite(inner, SWT.NULL);
        addMessageComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        addMessageComposite.setLayout(new GridLayout(1, false));
        addMessageLabelList = new ArrayList<Label>();
        addMessageTextList = new ArrayList<Text>();
        for (int i = 0; i < numberMessageParts; i++) {
            Label desc = new Label(addMessageComposite, SWT.READ_ONLY);
            desc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            desc.setText(Descriptions.PasteYour + " " + (i + 1) + " " + Descriptions.PartHere);
            addMessageLabelList.add(desc);
            Text msg = new Text(addMessageComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP);
            msg.setLayoutData(new GridData(300, 50));
            msg.setToolTipText(Descriptions.PasteYour + " " + (i + 1) + " " + Descriptions.PartHere);
            if (i < oldMessages.size()) {
                msg.setText(oldMessages.get(i));
            }
            msg.addModifyListener(getAllNotEmptyListener());
            addMessageTextList.add(msg);
        }

        addMessagePartButton = new Button(inner, SWT.PUSH);
        addMessagePartButton.setImage(Activator.getImageDescriptor("icons/outline_add_black_24dp.png").createImage(true));
        addMessagePartButton.setText(Descriptions.AddMessagePart);
        if (numberMessageParts >= MAX_NUMBER_PARTS) {
            addMessagePartButton.setEnabled(false);
        }
        
        addMessagePartButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.updateBodyComposite(
                            new RssSetMessageComposite(body, rac, numberMessageParts + 1, getMessages()));
                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
                }
            }
        });
        
        removeMessagePartButton = new Button(inner, SWT.PUSH);
        removeMessagePartButton.setImage(Activator.getImageDescriptor("icons/outline_remove_black_24dp.png").createImage(true));
        removeMessagePartButton.setText(Descriptions.RemoveMessagePart);
        if (numberMessageParts <= MIN_NUMBER_PARTS) {
        	removeMessagePartButton.setEnabled(false);
        }
        
        removeMessagePartButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.updateBodyComposite(
                            new RssSetMessageComposite(body, rac, numberMessageParts - 1, getMessages()));
                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
                }
            }
        });

        confirmMessageButton = new Button(inner, SWT.PUSH);
        confirmMessageButton.setEnabled(addMessageTextList.stream().allMatch(l -> !l.getText().isEmpty()));
        confirmMessageButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        confirmMessageButton.setText(Descriptions.ConfirmMessages);
        
        confirmMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    addMessagePartButton.setEnabled(false);
                    confirmMessageButton.setEnabled(false);
                    disableMessageEditing();
                    rac.newMessage(getMessages());
                    
	                // Change the visual
                    // Sets the color to "lit" (see visual state component)
                    //body.lightPath();
                    body.setActiveRssComposite(ActiveRssBodyComposite.SIGN_MESSAGE);
                }
            }
        });
        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSaveSignedMessage);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));
        
       
        // Button to load the message
        loadMessageButton = new Button(saveLoad, SWT.PUSH);
        loadMessageButton.setText(Descriptions.Load);
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
			           	//body.lightPath();
			            body.lightDataBox(DataType.MESSAGE);
			            
						body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_MESSAGE);
					}		
				}
        	}
        });
      
    }

    public RssSetMessageComposite(RssBodyComposite body, RssAlgorithmController rac) {
        this(body, rac, DEFAULT_NUMBER_PARTS, new LinkedList<>());
    }

    private void disableMessageEditing() {
        addMessageTextList.stream().forEach(t -> t.setEditable(false));
    }

    private List<String> getMessages() {
        return addMessageTextList.stream().map(t -> t.getText()).collect(Collectors.toList());
    }

    private ModifyListener getAllNotEmptyListener() {
        ModifyListener listener = new ModifyListener() {
            /** {@inheritDoc} */
            public void modifyText(ModifyEvent e) {
                confirmMessageButton.setEnabled(addMessageTextList.stream().allMatch(l -> !l.getText().isEmpty()));
            }
        };
        return listener;
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSetMessage);
    }
}
