package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.RssView;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * Defines the layout of visual on the left side (1 column) and buttons and text on the right side (1 column).
 * 
 * @author Leon Shell, Lukas Krodinger
 */
public class RssBaseComposite extends Composite {
    private final RssOverviewComposite overview;
    private final RssBodyComposite body;
    private final RssAlgorithmController rac;
    private final Composite parent;
    
    public RssBaseComposite(Composite parent) {
        super(parent, SWT.NONE);

        this.parent = parent;

        rac = new RssAlgorithmController();
        
 		// Layout left visual and right buttons + text
        setLayout(new GridLayout(2, false));
                
        // Left: Visual
        // Visual
        Group overviewGroup = new Group(this, SWT.NONE);
        overviewGroup.setText(Descriptions.Overview);
        overviewGroup.setLayout(new GridLayout());
        overviewGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        overview = new RssOverviewComposite(overviewGroup, this, rac);

        // Right: Buttons + text
        body = new RssBodyComposite(this, rac);
        GridData gd = new GridData(SWT.LEFT, SWT.TOP, true, true);
        body.setLayoutData(gd);
    }

    public RssBaseComposite(ScrolledComposite parent) {
        super(parent, SWT.NONE);

        this.parent = parent;

        rac = new RssAlgorithmController();
        
 		// Layout left visual and right buttons + text
        setLayout(new GridLayout(2, false));
        
        // Left: Visual
        // Visual
        Group overviewGroup = new Group(this, SWT.NONE);
        overviewGroup.setText(Descriptions.Overview);
        overviewGroup.setLayout(new GridLayout());
        overviewGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        overview = new RssOverviewComposite(overviewGroup, this, rac);

        // Right: Buttons + text
        body = new RssBodyComposite(this, rac);
        GridData gd = new GridData(SWT.LEFT, SWT.TOP, true, true);
        body.setLayoutData(gd);
    }
    
    public void updateScrollSize() {
		if(parent instanceof ScrolledComposite) {
			RssView.computeMinSize((ScrolledComposite)parent, this);
		}
        layout();
    }

    public void resetStateOverview(DataType type) {
        resetStateOverview(type, true);
    }

    public void resetStateOverview(DataType type, boolean reset) {
        overview.resetState(type, reset);
    }

    public void resetState(DataType type) {
        body.resetState(type);
    }

    public void returnToCurrentState() {
        overview.returnToCurrentState();
    }

    public void setActiveRssBodyComposite(ActiveRssBodyComposite activeRssBodyComposite) {
        body.setActiveRssComposite(activeRssBodyComposite);
    }

    public void setActiveRssBodyComposite(ActiveRssBodyComposite activeRssBodyComposite, boolean forceReload) {
        body.setActiveRssComposite(activeRssBodyComposite, forceReload);
    }

    public void lightPath() {
        overview.lightPath();
    }

    public void lightDataBox(DataType type) {
        overview.lightDataBox(type);
    }

    public void activateDataBox(DataType type) {
        overview.activateDataBox(type);
    }

    public void updateOverview() {
        overview.updateOverview();
    }

}
