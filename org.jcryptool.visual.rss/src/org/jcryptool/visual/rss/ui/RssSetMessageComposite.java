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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyPersistence;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.Information;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;

/**
 * The composite with button, text fields etc. with the function to set the message parts to be encrypted.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssSetMessageComposite extends RssRightSideComposite {
    private static int DEFAULT_NUMBER_PARTS = 2;
    private static int MAX_NUMBER_PARTS = 5;

    private final Composite inner;
    private final Composite addMessageComposite;
    private final List<Text> addMessageTextList;
    private final List<Label> addMessageLabelList;
    private final Button addMessageButton;
    private final Button confirmMessageButton;
  

    public RssSetMessageComposite(RssBodyComposite body, RssAlgorithmController rac, int numberMessageParts,
            List<String> oldMessages) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.SetMessages);
        prepareAboutComposite();

        inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

        addMessageButton = new Button(inner, SWT.PUSH);
        addMessageButton.setText(Descriptions.AddMessagePart);
        if (numberMessageParts >= MAX_NUMBER_PARTS) {
            addMessageButton.setEnabled(false);
        }

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

        confirmMessageButton = new Button(inner, SWT.PUSH);
        confirmMessageButton.setEnabled(false);
        confirmMessageButton.setText(Descriptions.ConfirmMessages);

        addMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.updateBodyComposite(
                            new RssSetMessageComposite(body, rac, numberMessageParts + 1, getMessages()));
                    body.setActiveRssComposite(ActiveRssBodyComposite.SET_MESSAGE);
                }
            }
        });

        confirmMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    addMessageButton.setEnabled(false);
                    confirmMessageButton.setEnabled(false);
                    disableMessageEditing();
                    rac.newMessage(getMessages());
                    body.lightPath();
                    body.setActiveRssComposite(ActiveRssBodyComposite.SIGN_MESSAGE);
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
