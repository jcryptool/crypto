package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
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
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.MessagePart;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;

/**
 * The composite with button, text fields etc. with the function to show the (signed and possible redacted) message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssViewRedactedComposite extends RssRightSideComposite {
	
	private final Button saveMessageButton;

    public RssViewRedactedComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.ViewRedacted);
        prepareAboutComposite();

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout(3, false));

        Label des0 = new Label(inner, SWT.NONE);
        des0.setText(Descriptions.Number);
        Label des1 = new Label(inner, SWT.NONE);
        des1.setText(Descriptions.MessagePart);
        Label des2 = new Label(inner, SWT.NONE);
        des2.setText(Descriptions.Redactable);
        
        List<MessagePart> currentMessage = rac.getMessageParts();
        for (int i = 0; i < currentMessage.size(); i++) {
            Label l = new Label(inner, SWT.NONE);
            l.setText(Descriptions.Message + " " + (i + 1));
            Text l2 = new Text(inner, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
            labelGridData.widthHint = R_MAX_SIZE;
            l2.setLayoutData(labelGridData);
            String msg = currentMessage.get(i).getMessage();
            msg = getSplittedString(msg, 50);
            l2.setText(msg);
            Button redactingAllowedCheckbox = new Button(inner, SWT.CHECK);
            redactingAllowedCheckbox.setEnabled(false);
            redactingAllowedCheckbox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
            redactingAllowedCheckbox.setSelection(currentMessage.get(i).isRedactable());
        }
        
        Button returnButton = new Button(inner, SWT.PUSH);
        returnButton.setText(Descriptions.ReturnButton);
        returnButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_before_black_24dp.png").createImage(true));
        returnButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    body.returnToCurrentState();
                }
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

            	// Open a dialog to get location for key file storage.
				FileDialog storeDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				storeDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				storeDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				storeDialog.setFileName("redacted-message.xml");
				storeDialog.setOverwrite(true);
				String storePath = storeDialog.open();
            	
				// messageStorePath might be null in case the dialog was closed
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

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewRedacted);
    }

}
