package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class RssVisualStateComposite extends Composite {
    private final Font defaultFont;
    private final Font highlightedFont;

    private final CLabel l;

    public RssVisualStateComposite(Composite parent, String name) {
        super(parent, SWT.BORDER);
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = RssOverviewComposite.STATE_BOX_X_PADDING;
        setLayout(gridLayout);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        l = new CLabel(this, SWT.CENTER);
        l.setText(name);

        GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, true, 0, RssOverviewComposite.STATE_BOX_Y_PADDING);
        gd.widthHint = l.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + RssOverviewComposite.LABEL_EXTRA_WIDTH;

        l.setLayoutData(gd);
        defaultFont = l.getFont();
        FontData fontData = defaultFont.getFontData()[0];
        highlightedFont = new Font(getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));
        setDeactivated();
    }

    public void setFocused() {
        l.setFont(highlightedFont);
    }

    public void setUnfocused() {
        l.setFont(defaultFont);
    }

    public void setActive() {
        setFocused();
        setBackground(RssColors.LIGHT_GREEN);
        l.setBackground(RssColors.LIGHT_GREEN);
        setForeground(RssColors.BLACK);
    }

    public void setPassive() {
        setUnfocused();
        setBackground(RssColors.GREEN);
        l.setBackground(RssColors.GREEN);
        setForeground(RssColors.GRAY);
    }

    public void setLit() {
        setUnfocused();
        setBackground(RssColors.WHITE);
        l.setBackground(RssColors.WHITE);
        setForeground(RssColors.BLACK);
    }

    public void setDeactivated() {
        setUnfocused();
        setBackground(RssColors.GRAY);
        l.setBackground(RssColors.GRAY);
        setForeground(RssColors.LIGHT_GRAY);

    }

}
