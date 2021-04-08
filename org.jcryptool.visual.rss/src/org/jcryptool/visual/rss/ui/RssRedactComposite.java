package org.jcryptool.visual.rss.ui;

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
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * The composite with button, text fields etc. with the function to redact those parts of the message, where it is allowed.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssRedactComposite extends RssRightSideComposite {
    private List<String> messages;
    private List<Boolean> redactableParts;

    private List<Button> buttonList;
    private List<Text> textList;

    public RssRedactComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.RedactMessages);
        prepareAboutComposite();

        messages = new ArrayList<String>(rac.getMessageParts());
        redactableParts = new ArrayList<Boolean>(rac.getRedactableParts());

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());

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
        int numberRedacted = 0;
        for (int i = 0; i < messages.size(); i++) {
            if (rac.getRedactedParts() != null && rac.getRedactedParts().get(i)) {
                numberRedacted++;
                continue;
            }
            Label la = new Label(c, SWT.READ_ONLY);
            la.setText("" + (i + 1 - numberRedacted));
            Text l = new Text(c, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
            GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
            labelGridData.widthHint = R_MAX_SIZE;
            l.setLayoutData(labelGridData);
            String msg = messages.get(i);
            msg = getSplittedString(msg, 50);
            l.setText(msg);
            textList.add(l);
            Composite ch = new Composite(c, SWT.NULL);
            ch.setLayout(new GridLayout(2, false));
            Button b = new Button(ch, SWT.CHECK);
            b.setToolTipText(i + "");
            b.setSelection(false);
            Label fix = new Label(ch, SWT.NULL);
            fix.setText(Descriptions.Fix);
            fix.setVisible(false);
            if (!redactableParts.get(i)) {
                b.setEnabled(false);
                b.setVisible(true);
                fix.setVisible(true);
            }
            buttonList.add(b);
        }

        Button redactMessageButton = new Button(inner, SWT.PUSH);
        redactMessageButton.setText(Descriptions.RedactMessages);

        Button nextButton = new Button(inner, SWT.PUSH);
        nextButton.setText(Descriptions.Next + ": " + Descriptions.VerifyRedacted);
        nextButton.setEnabled(false);

        redactMessageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    redactMessageButton.setEnabled(false);
                    List<Integer> indices = new ArrayList<>();
                    for (int i = 0; i < buttonList.size(); i++) {
                        if (buttonList.get(i).getSelection()) {
                            int index = Integer.parseInt(buttonList.get(i).getToolTipText());
                            indices.add(index);
                        }
                        buttonList.get(i).setEnabled(false);
                    }
                    rac.redactMessage(indices);
                    body.lightPath();
                    body.lightDataBox(DataType.REDACTED);
                    nextButton.setEnabled(true);
                    break;
                }
            }
        });

        nextButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                if (e.type == SWT.Selection) {
                    body.setActiveRssComposite(ActiveRssBodyComposite.VERIFY_REDACTED);
                }
            }
        });
    }

    @Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextRedact);
    }
}
