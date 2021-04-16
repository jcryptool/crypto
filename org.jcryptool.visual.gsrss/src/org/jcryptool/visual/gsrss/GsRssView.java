package org.jcryptool.visual.gsrss;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.visual.rss.Activator;

/**
 * Main view for the GsRss algorithm.
 * Makes the visual scrollable, sets a headerAndTitle component and expands to maximal available size.
 * Also creates the mainContent under the headerAndTitle component.
 * 
 * @author Lukas Krodinger
 *
 */
public class GsRssView extends ViewPart {
	
	private Composite parent;
	private ScrolledComposite scrolledComposite;
	
	public GsRssView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		// Make the composite scrollable
		scrolledComposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite insideScrolledComposite = new Composite(scrolledComposite, SWT.NONE | SWT.BORDER);
		scrolledComposite.setContent(insideScrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		insideScrolledComposite.setLayout(new GridLayout());

		// Set title and description
 		TitleAndDescriptionComposite headerTextfield = new TitleAndDescriptionComposite(insideScrolledComposite);
 		headerTextfield.setTitleAndDescription(Messages.Title, Messages.Description);
 		headerTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));


 		// Content filling main part
        // final RssBaseComposite baseComposite = new RssBaseComposite(insideScrolledComposite);


        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, Activator.PLUGIN_ID + ".view");
               
	}

	@Override
	public void setFocus() {
		parent.setFocus();
	}

	/**
	 * Needed by the restart handler to reset the view when restarting the plugin.
	 */
	public void resetView() {
		Control[] children = parent.getChildren();
        for (Control control : children) {
            control.dispose();
        }
        createPartControl(parent);
        parent.layout();
	}

}
