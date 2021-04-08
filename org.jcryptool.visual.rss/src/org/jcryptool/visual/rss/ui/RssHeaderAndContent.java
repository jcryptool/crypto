package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.visual.rss.Descriptions;

public class RssHeaderAndContent extends Composite {

	private final Composite parent;
	private Composite headComposite;
	//private final RssBaseComposite rssBaseComposite;
	
	public RssHeaderAndContent(Composite parent) {
		super(parent, SWT.NONE);
		
		this.parent = parent;
		
		setLayout(new GridLayout());
		
		// Header
 		headComposite = new Composite(parent, SWT.NONE);
 		headComposite.setBackground(ColorService.WHITE);
 		headComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
 		headComposite.setLayout(new GridLayout());

 		TitleAndDescriptionComposite headerTextfield = new TitleAndDescriptionComposite(headComposite);
 		headerTextfield.setTitleAndDescription(Descriptions.Title, Descriptions.Description);
 		headerTextfield.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
 		
 		// BaseComposite - Main part
 		//rssBaseComposite = new RssBaseComposite(parent);
		
	}

}
