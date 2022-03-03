package org.jcryptool.visual.rss.ui;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.MessagePart;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to redact those parts of the message, where it is allowed.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssRedactComposite extends RssRightSideComposite {

	private List<MessagePart> messageParts;
    private List<Button> buttonList;
    private List<Text> textList;

    public RssRedactComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.RedactMessages);
        prepareAboutComposite();

        messageParts = rac.getMessageParts();

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());
        
        if(messageParts != null) {
        	 Composite c = new Composite(inner, SWT.NONE);
             c.setLayout(new GridLayout(3, false));
             Label l1 = new Label(c, SWT.READ_ONLY);
             l1.setText(Descriptions.Number);
             Label l2 = new Label(c, SWT.READ_ONLY);
             l2.setText(Descriptions.Message);
             Label l3 = new Label(c, SWT.READ_ONLY);
             l3.setText(Descriptions.Redact + "?");
             textList = new ArrayList<Text>();
             buttonList = new ArrayList<Button>();
    
             for (int i = 0; i < messageParts.size(); i++) {
             
                 Label la = new Label(c, SWT.READ_ONLY);
                 la.setText(Descriptions.MessagePart + " " + (i + 1));
                 Text l = new Text(c, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
                 GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
                 labelGridData.widthHint = R_MAX_SIZE;
                 l.setLayoutData(labelGridData);
                 String msg = messageParts.get(i).getMessage();
                 msg = getSplittedString(msg, 50);
                 l.setText(msg);
                 textList.add(l);
                 Composite ch = new Composite(c, SWT.NULL);
                 ch.setLayout(new GridLayout(2, false));
                 Button b = new Button(ch, SWT.CHECK);
                 b.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
                 b.setToolTipText(i + "");
                 b.setSelection(false);
                 Label fix = new Label(ch, SWT.NULL);
                 fix.setText(Descriptions.Fix);
                 fix.setVisible(false);
                 if (!messageParts.get(i).isRedactable()) {
                     b.setEnabled(false);
                     b.setVisible(true);
                     fix.setVisible(true);
                 }
                 buttonList.add(b);
             }

        }

       
        Button redactMessageButton = new Button(inner, SWT.PUSH);
        redactMessageButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_next_black_24dp.png").createImage(true));
        redactMessageButton.setText(Descriptions.RedactMessages);
        redactMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {

                redactMessageButton.setEnabled(false);
                List<MessagePart> toRedact = new ArrayList<>();
                for (int i = 0; i < buttonList.size(); i++) {
                    if (buttonList.get(i).getSelection()) {
                        toRedact.add(messageParts.get(i));
                    }
                    buttonList.get(i).setEnabled(false);
                }
                try {
					rac.redactMessage(toRedact);
					
	                // Change the visual
                    // Sets the color to "lit" (see visual state component)
                    //body.lightPath();
	                body.lightDataBox(DataType.REDACTED);
	                
	                body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_REDACTED);
				} catch (InvalidKeyException e1) {
					showErrorDialog("Invalid key", e1.getMessage());
				}

            }
        });
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextRedact);
    }
}
