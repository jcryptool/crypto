package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ExpandAdapter;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.jcryptool.visual.signalencryption.ui.OverviewView.ScrollableSize;

/** Displays a question — if you click it, displays a text with the answer */
public class QuestionAnswerExpander {

    private static final int EXPANDED_MARGIN = 15;

    private final String title;
    private final String description;
    private ExpandBar explanationExpander;
    private ExpandItem collapsable;
    private Composite content;

    private StyledText descriptionText;
    private ScrollableSize callback;

    /** Displays a question — if you click it, displays a text with the answer */
    public QuestionAnswerExpander(Composite parent, int style, String title, String description) {
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
        content.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        descriptionText = new StyledText(content, SWT.WRAP | SWT.MULTI);
        descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        descriptionText.setText(description);
        descriptionText.setCaret(null);
        descriptionText.setEditable(false);

        collapsable.setControl(content);
        collapsable.setHeight(content.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
    }

    public QuestionAnswerExpander setLayoutData(GridData gridData) {
        explanationExpander.setLayoutData(gridData);
        return this;
    }

    /**
     * If the this class lives in a {@link ScrolledComposite} there are some
     * layouting issues. One can pass in a callback which adjusts the parents
     * ScrolledComposite on expand/collapse events.
     */
    public QuestionAnswerExpander setScrollerCalback(ScrollableSize callback) {
        this.callback = callback;
        return this;
    }

    ExpandAdapter expandAdapter(Composite parent) {
        return new ExpandAdapter() {

            @Override
            public void itemCollapsed(ExpandEvent e) {
                Display.getDefault().asyncExec(() -> {
                    collapsable.setHeight(0);
                    parent.layout();
                    scrollableSizeCallback();
                });
            }

            @Override
            public void itemExpanded(ExpandEvent e) {
                Display.getDefault().asyncExec(() -> {
                    // How to layout this thing?
                    // Initially, we don't know anything, so let's compute the estimated size
                    var parentSize = content.getParent().getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT);
                    var contentSize = content.computeSize(parentSize.x, parentSize.y);
                    var estimatedSize = descriptionText.computeSize(contentSize.x, SWT.DEFAULT);
                    // Good, set that estimated size and layout it.
                    collapsable.setHeight(estimatedSize.y);
                    parent.layout();
                    // Now since the text has space, it gets actually resized to its proper size
                    // (which is usually
                    // smaller than the estimatedSize). So let's get its actual size and set it as
                    // height.
                    var actualSize = descriptionText.getSize();
                    collapsable.setHeight(actualSize.y + EXPANDED_MARGIN);
                    // Now layout again and tell an any outer scrollable (if exists) to adjust its
                    // height as well.
                    parent.layout();
                    scrollableSizeCallback();
                });
            }
        };
    }

    private void scrollableSizeCallback() {
        if (callback != null) {
            callback.adjust();
        }
    }

}
