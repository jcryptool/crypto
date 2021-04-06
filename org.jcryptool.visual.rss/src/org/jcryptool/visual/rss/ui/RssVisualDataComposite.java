package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.jcryptool.visual.rss.Descriptions;
import org.eclipse.swt.widgets.Listener;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;

public class RssVisualDataComposite extends Composite {
    private final Font defaultFont;
    private final Font highlightedFont;
    private final DataType type;
    private final Button viewButton;
    private final Button redoButton;
    private final CLabel l;
    
    private boolean enabled;

    public RssVisualDataComposite(Composite parent, RssOverviewComposite overview, DataType type) {
        super(parent, SWT.BORDER);
        this.type = type;
        
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginWidth = RssOverviewComposite.STATE_BOX_X_PADDING;
        gridLayout.marginHeight = RssOverviewComposite.STATE_BOX_Y_PADDING * 3 / 2;
        gridLayout.verticalSpacing *= 3;
        setLayout(gridLayout);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

        l = new CLabel(this, SWT.CENTER);
        l.setText(type.getName());

        GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, true, 0, 0);
        gd.horizontalSpan = 2;
        gd.widthHint = l.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + RssOverviewComposite.LABEL_EXTRA_WIDTH;

        l.setLayoutData(gd);

        defaultFont = l.getFont();
        FontData fontData = defaultFont.getFontData()[0];
        highlightedFont = new Font(getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));

        GridData buttonGd = new GridData(SWT.CENTER, SWT.CENTER, true, true, 0, 0);

        viewButton = new Button(this, SWT.PUSH);
        viewButton.setLayoutData(buttonGd);
		viewButton.setImage(Activator.getImageDescriptor("icons/magnifying_glass.png").createImage());
        redoButton = new Button(this, SWT.PUSH);
        redoButton.setLayoutData(buttonGd);
		redoButton.setImage(Activator.getImageDescriptor("icons/reset.png").createImage());

        viewButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    overview.unfocusAll();
                    setActive();
                    overview.setActiveRssBodyComposite(type.getComposite());
                }
              }
            });

        redoButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    overview.resetState(type);
                }
              }
            });

        setDeactivated();
    }
    
    public void setFocused() {
        l.setFont(highlightedFont);
        setBackground(RssColors.LIGHT_BLUE);
        l.setBackground(RssColors.LIGHT_BLUE);
    }
    
    public void setUnfocused() {
        l.setFont(defaultFont);
        if (enabled) {
            setBackground(RssColors.WHITE);
            l.setBackground(RssColors.WHITE);
        } else {
            setBackground(RssColors.LIGHT_GRAY);
            l.setBackground(RssColors.LIGHT_GRAY);
        }
    }
    
    public void setActive() {
        enabled = true;
        setFocused();
        viewButton.setEnabled(true);
        redoButton.setEnabled(true);
    }

    public void setLit() {
        enabled = true;
        setUnfocused();
        viewButton.setEnabled(true);
        redoButton.setEnabled(true);
    }
    
    public void setDeactivated() {
        enabled = false;
        setUnfocused();
        viewButton.setEnabled(false);
        redoButton.setEnabled(false);
    }
    
    public enum DataType {
        KEY(ActiveRssBodyComposite.VIEW_KEY, Descriptions.DataBoxKey),
        MESSAGE(ActiveRssBodyComposite.VIEW_MESSAGE, Descriptions.DataBoxOrig),
        REDACTED(ActiveRssBodyComposite.VIEW_REDACTED, Descriptions.DataBoxRed);
        
        private final ActiveRssBodyComposite composite;
        private final String name;
        
        DataType(ActiveRssBodyComposite composite, String name) {
            this.composite = composite;
            this.name = name;
        }
        
        public ActiveRssBodyComposite getComposite() {
            return composite;
        }
        
        public String getName() {
            return name;
        }
    }
}
