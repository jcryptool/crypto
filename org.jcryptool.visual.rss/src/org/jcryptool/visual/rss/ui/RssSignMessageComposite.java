package org.jcryptool.visual.rss.ui;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

public class RssSignMessageComposite extends RssRightSideComposite {

    private final Composite inner;

    private final ArrayList<String> messageList;
    private final ArrayList<Button> buttonList;

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
            Button b = new Button(c, SWT.CHECK);
            b.setSelection(false);
            buttonList.add(b);
        }
        Button signMessageButton = new Button(inner, SWT.PUSH);
        signMessageButton.setText(Descriptions.SignMessage);

        Button nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next + ": " + Descriptions.VerifyMessage);
        nextButton.setEnabled(false);

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
                    body.lightPath();
                    body.lightDataBox(DataType.MESSAGE);
                    nextButton.setEnabled(true);
                    break;
                }
            }
        });

        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_MESSAGE);
                }
            }
        });
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextSignMessage);
    }

}
