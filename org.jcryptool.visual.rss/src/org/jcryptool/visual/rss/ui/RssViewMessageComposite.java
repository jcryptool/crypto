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
import org.jcryptool.visual.rss.persistence.InvalidSignatureException;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to show the (signed and possible redacted) message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssViewMessageComposite extends RssRightSideComposite {
	
	private final Button saveMessageButton;

    public RssViewMessageComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.ViewMessage);
        prepareAboutComposite();

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout(3, false));

        Label des0 = new Label(inner, SWT.NONE);
        des0.setText(Descriptions.Number);
        Label des1 = new Label(inner, SWT.NONE);
        des1.setText(Descriptions.MessagePart);
        Label des2 = new Label(inner, SWT.NONE);
        des2.setText(Descriptions.Redactable);
        
        List<MessagePart> messages = rac.getOriginalMessageParts();
        for (int i = 0; i < messages.size(); i++) {
            Label l = new Label(inner, SWT.NONE);
            l.setText(Descriptions.Message + " " + (i + 1));
            Text l2 = new Text(inner, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
            labelGridData.widthHint = R_MAX_SIZE;
            l2.setLayoutData(labelGridData);
            String msg = messages.get(i).getMessage();
            msg = getSplittedString(msg, 50);
            l2.setText(msg);
            Button redactingAllowedCheckbox = new Button(inner, SWT.CHECK);
            redactingAllowedCheckbox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
            redactingAllowedCheckbox.setEnabled(false);
            redactingAllowedCheckbox.setSelection(messages.get(i).isRedactable());
        }

        
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

        Label placeholder = new Label(leftComposite, SWT.SEPARATOR | SWT.HORIZONTAL); GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false); gd.minimumHeight = 5; gd.verticalIndent = 3; placeholder.setLayoutData(gd);
        Button returnButton = new Button(leftComposite, SWT.PUSH);
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
        
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewMessage);
    }

}
