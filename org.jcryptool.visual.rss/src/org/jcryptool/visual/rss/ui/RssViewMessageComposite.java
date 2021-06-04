package org.jcryptool.visual.rss.ui;

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

/**
 * The composite with button, text fields etc. with the function to show the (signed and possible redacted) message parts.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssViewMessageComposite extends RssRightSideComposite {

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
        des2.setText(Descriptions.ProofPart);
        
        List<MessagePart> messages = rac.getMessageParts();
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
            Text l3 = new Text(inner, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            String proofmsg = "proof.get(i)";
            proofmsg = getSplittedString(proofmsg, 50);
            l3.setText(proofmsg);
        }

        Button returnButton = new Button(leftComposite, SWT.PUSH);
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
