package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ExpandAdapter;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public class Explainer {

    private final String title;
    private final String description;
    private ExpandBar explanationExpander;
    private ExpandItem collapsable;
    private Composite content;

    private StyledText descriptionText;

    public Explainer(Composite parent, int style, String title, String description) {
        this.title = title;
        this.description = description;
        createExpandItem(parent, style);
        createParts();
    }

    private void createExpandItem(Composite parent, int style) {
        explanationExpander = new ExpandBar(parent, style);
        explanationExpander.addExpandListener(expandAdapter(parent));
    }

    private void createParts() {
        collapsable = new ExpandItem(explanationExpander, SWT.NONE);
        collapsable.setText(title);

        content = new Composite(explanationExpander, SWT.NONE);
        content.setLayout(new GridLayout());
        content.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descriptionText = new StyledText(content, SWT.WRAP | SWT.MULTI);
        descriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        descriptionText.setText(description);
        descriptionText.setCaret(null);
        descriptionText.setEditable(false);

        collapsable.setControl(content);
        collapsable.setHeight(content.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
    }

    public Explainer setLayoutData(GridData gridData) {
        explanationExpander.setLayoutData(gridData);
        return this;
    }

    ExpandAdapter expandAdapter(Composite parent) {
        return new ExpandAdapter() {

            @Override
            public void itemCollapsed(ExpandEvent e) {
                Display.getDefault().asyncExec(() -> {
                    collapsable.setHeight(0);
                    parent.layout();
                });
            }

            @Override
            public void itemExpanded(ExpandEvent e) {
                Display.getDefault().asyncExec(() -> {
                    var contentSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
                    var textSize = descriptionText.computeSize(contentSize.x, SWT.DEFAULT);
                    collapsable.setHeight(textSize.y + contentSize.y);
                    parent.layout();
                });
            }
        };
    }

}
