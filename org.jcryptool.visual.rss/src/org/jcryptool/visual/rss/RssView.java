package org.jcryptool.visual.rss;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
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
       
		// make the composite scrollable
		sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite insideScrolledComposite = new Composite(sc, SWT.NONE | SWT.BORDER);
		sc.setContent(insideScrolledComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);

		insideScrolledComposite.setLayout(new GridLayout());

 		TitleAndDescriptionComposite headerTextfield = new TitleAndDescriptionComposite(insideScrolledComposite);
 		headerTextfield.setTitleAndDescription(Descriptions.Title, Descriptions.Description);
 		headerTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


 		// Content filling main part
        final RssBaseComposite baseComposite = new RssBaseComposite(insideScrolledComposite);


        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), "org.jcryptool.visual.rss.rssview");
               
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
