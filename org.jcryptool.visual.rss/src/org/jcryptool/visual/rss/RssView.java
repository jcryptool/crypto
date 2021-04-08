package org.jcryptool.visual.rss;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.visual.rss.ui.RssBaseComposite;

/**
 * Main view for the RSS algorithm visual.
 * Makes the visual scrollable and makes the main content expand to maximal available size.
 * 
 * @author Leon Sell, Lukas Krodinger
 */
public class RssView extends ViewPart {
	public RssView() {
	}
    private Composite parent;
    private Composite headComposite;
    private Composite headerAndContent;
    private ScrolledComposite sc;

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt. widgets.Composite)
     * This Method declares and sets the GUI-Control elements
     */
    @Override
    public void createPartControl(final Composite parent) {
        this.parent = parent;

        parent.setLayout(new GridLayout(1, false));
        
        // Make content scrollable
        sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc.setContent(headerAndContent);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
	
        headerAndContent = new Composite(sc, SWT.NONE);
		headerAndContent.setLayout(new GridLayout());
		headerAndContent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		
		sc.setContent(headerAndContent);
	               
        // Begin - Header
 		headComposite = new Composite(headerAndContent, SWT.NONE);
 		headComposite.setBackground(ColorService.WHITE);
 		headComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
 		headComposite.setLayout(new GridLayout());

 		TitleAndDescriptionComposite headerTextfield = new TitleAndDescriptionComposite(headComposite);
 		headerTextfield.setTitleAndDescription(Descriptions.Title, Descriptions.Description);
 		headerTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Scrolling		
//        final ScrolledComposite scrolledComposite = new ScrolledComposite(headerAndContent, SWT.H_SCROLL | SWT.V_SCROLL);
//        scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
 		// Content filling main part
        final RssBaseComposite baseComposite = new RssBaseComposite(headComposite);
        baseComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//        scrolledComposite.setContent(baseComposite);
//        scrolledComposite.setExpandHorizontal(true);
//        scrolledComposite.setExpandVertical(true);
//        baseComposite.updateScrollSize();
//        scrolledComposite.addListener(SWT.Resize, x -> computeMinSize(scrolledComposite, baseComposite));
//
//        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), "org.jcryptool.visual.rss.rssview");
        
        
    }

    /**
     * Adjust the minimum scroll size of the given scrolled composite with the size of the given content
     * @param scrolledComposite ScrolledComposite that gets it's minimum scroll size adjusted
     * @param content Content of the ScrolledComposite
     */
    public static void computeMinSize(ScrolledComposite scrolledComposite, Composite content) {
        int width = scrolledComposite.getClientArea().width;
        scrolledComposite.setMinSize(content.computeSize(width, SWT.DEFAULT));
    }

    /**
     * resets the view
     */
    public void resetView() {
        Control[] children = parent.getChildren();
        for (Control control : children) {
            control.dispose();
        }
        createPartControl(parent);
        parent.layout();
    }

    @Override
    public void setFocus() {
        parent.setFocus();
    }
}
