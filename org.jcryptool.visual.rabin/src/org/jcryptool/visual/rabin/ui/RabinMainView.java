package org.jcryptool.visual.rabin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.TitleAndDescriptionComposite;
import org.jcryptool.core.util.ui.auto.SmoothScroller;
import org.jcryptool.visual.rabin.GUIHandler;
import org.jcryptool.visual.rabin.Messages;
import org.jcryptool.visual.rabin.Rabin;

public class RabinMainView extends ViewPart {

	private Composite parent;
	private Composite rootComposite;
	private RabinFirstTabComposite compCryptosystem;
	private TitleAndDescriptionComposite compTad;
	private TabFolder tfRabin;
	private TabItem tabFirstItem;
	private TabItem tabThirdItem;
	private RabinThirdTabComposite compAttacks;
	private Composite compHoldAttacks;
	private Composite compHoldCryptosystem;
	private Composite compHoldSettings;
	
	private GUIHandler guiHandler;
	

	public RabinMainView() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void setColors() {
		Color colorBG = guiHandler.colorDarkModeBG;
		Color colorFG = guiHandler.colorDarkModeFG;
		
		
		//parent.setBackground(colorBG);
		//parent.setForeground(colorFG);
		//rootComposite.setBackground(colorBG);
		//rootComposite.setForeground(colorFG);
		compCryptosystem.setBackground(colorBG);
		compCryptosystem.setForeground(colorFG);
		//compTad.setBackground(colorBG);
		//compTad.setForeground(colorFG);
		//tfRabin.setBackground(colorBG);
		//tfRabin.setForeground(colorFG);
		compAttacks.setBackground(colorBG);
		compAttacks.setForeground(colorFG);
		compHoldAttacks.setBackground(colorBG);
		compHoldAttacks.setForeground(colorFG);
		compHoldCryptosystem.setBackground(colorBG);
		compHoldCryptosystem.setForeground(colorFG);
	}
	
	

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		guiHandler = new GUIHandler();
		//sc = new ScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		//sc = new ScrolledComposite(parent, SWT.NONE);
		//rootComposite = new Composite(sc, SWT.NONE);
		rootComposite = new Composite(parent, SWT.NONE);
		//sc.setContent(rootComposite);
		//sc.setExpandHorizontal(true);
		//sc.setExpandVertical(true);
		rootComposite.setLayout(new GridLayout(1, false));
		rootComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 100));
		
		compTad = new TitleAndDescriptionComposite(rootComposite);
		compTad.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compTad.setTitle(Messages.RabinMainView_compTadTitle);
		compTad.setDescription(Messages.RabinMainView_0);
		
		
		// TODO: insert TitleAndDescriptionComposite here
		// use <Alt>-a + f to search in workspace for classes
		// use plugin.xml / bottom tab "Dependencies" to add plugin 
		//   dependencies (where the TADComposite is located)
		
		// TODO: uncomment -> fix errors (call correctly, look how other plugins do it)
		//Composite head = new Composite(rootComposite, SWT.NONE);
		//head.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//head.setLayout(new GridLayout());
		/*compTad = new TitleAndDescriptionComposite(rootComposite);
		compTad.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compTad.setTitle("Rabin Cryptosystem");
		compTad.setDescription("The Rabin cryptosystem is an asymmetric cryptosystem"
				+ " sharing similarities with the RSA cryptosystem."
				+ " Its security is based on the difficulty of computing squareroots"
				+ " modulo a composite integer n and the integer factorization problem." 
				+ " The plugin demonstrates the Rabin encryption scheme with a chosen"
				+ " private key pair (p,q), where p and q are distinct prime numbers"
				+ " with p = q \u2261 3 mod 4,"
				+ " and the public key N = p \u00B7 q, which is used as the modulus.");*/
		
		Rabin rabinCryptosystemTab = new Rabin();
		//Rabin rabinAlgorithmTab = new Rabin();
		//Rabin rabinAttacksTab = new Rabin();
		
		
		tfRabin = new TabFolder(rootComposite, SWT.NONE);
		tfRabin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		/*Composite compSpaceForTabs = new Composite(tfRabin, SWT.BORDER);
		compSpaceForTabs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		((GridData) compSpaceForTabs.getLayoutData()).heightHint = 50;
		compSpaceForTabs.setLayout(new GridLayout(1, false));
		compSpaceForTabs.setBackground(ColorService.WHITE);*/
		
		
		//tfRabin.setBackground(ColorService.GRAY);
		
		
	
		tabFirstItem = new TabItem(tfRabin, SWT.NONE);
		tabFirstItem.setText(Messages.RabinMainView_tabFirstItem);
		
		Composite compSpaceFirstTab = new Composite(tfRabin, SWT.NONE);
		compSpaceFirstTab.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		compSpaceFirstTab.setLayout(new GridLayout(1, false));
		((GridLayout) compSpaceFirstTab.getLayout()).marginHeight = 0;
		((GridLayout) compSpaceFirstTab.getLayout()).marginWidth = 0;
		((GridLayout) compSpaceFirstTab.getLayout()).marginTop = 10;
		compSpaceFirstTab.setBackground(ColorService.WHITE);
		
		
		//ScrolledComposite scCryptosystem = new ScrolledComposite(tfRabin, SWT.H_SCROLL | SWT.V_SCROLL);
		ScrolledComposite scCryptosystem = new ScrolledComposite(compSpaceFirstTab, SWT.H_SCROLL | SWT.V_SCROLL);
		scCryptosystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scCryptosystem.setExpandVertical(true);
		scCryptosystem.setExpandHorizontal(true);
		
		
		//scCryptosystem.setBackground(ColorService.GRAY);
		
		compHoldCryptosystem = new Composite(scCryptosystem, SWT.NONE);
		compHoldCryptosystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldCryptosystem.setLayout(new GridLayout(1, false));
		
		//compHoldCryptosystem.setBackground(ColorService.GRAY);
		
		//GUIHandler.setSizeControlStatic(compHoldCryptosystem, SWT.DEFAULT, SWT.DEFAULT);
		
		// test and debugging
		/*compHoldCryptosystem.addControlListener(new ControlAdapter() {
			
			@Override
			public void controlResized(ControlEvent e) {
				// TODO Auto-generated method stub
				//int width = compHoldCryptosystem.getBounds().width;
				//int height = compHoldCryptosystem.getBounds().height;
				int width = compHoldCryptosystem.getClientArea().width;
				scCryptosystem.setMinSize(compHoldCryptosystem.computeSize(width, SWT.DEFAULT));
				
			}
		});*/
		
		scCryptosystem.setContent(compHoldCryptosystem);
		
		// test and debugging
		scCryptosystem.addControlListener(new ControlAdapter() {
			
			@Override
			public void controlResized(ControlEvent e) {
				//Rectangle r = scCryptosystem.getClientArea();
				Rectangle r = scCryptosystem.getClientArea();			
				int width = r.width;
				//int height = r.height;
				//System.out.println("width = " + width);
				//System.out.println("height = " + height);
				
				//if(width < 1000)
					//return;
				
				scCryptosystem.setMinSize(compHoldCryptosystem.computeSize(width, SWT.DEFAULT));
				//scCryptosystem.setMinSize(scCryptosystem.computeSize(width, SWT.DEFAULT));
			}
			
		});
		
		
		
		
		//compCryptosystem = new RabinFirstTabComposite(compHoldCryptosystem, SWT.NONE, rabinCryptosystemTab, rabinAlgorithmTab, scCryptosystem, compHoldCryptosystem);
		compCryptosystem = new RabinFirstTabComposite(compHoldCryptosystem, SWT.NONE, rabinCryptosystemTab, scCryptosystem, compHoldCryptosystem);
		compCryptosystem.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compCryptosystem.setLayout(new GridLayout(1, false));
		
		//compCryptosystem.setBackground(ColorService.GRAY);
		
		
		scCryptosystem.setMinSize(compHoldCryptosystem.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		
		
		//tabFirstItem.setControl(scCryptosystem);
		tabFirstItem.setControl(compSpaceFirstTab);
		
		/*tabSecondItem = new TabItem(tfRabin, SWT.NONE);
		tabSecondItem.setText(Messages.RabinMainView_tabSecondItem);
		
		ScrolledComposite scAlgorithm = new ScrolledComposite(tfRabin, SWT.H_SCROLL | SWT.V_SCROLL);
		scAlgorithm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scAlgorithm.setExpandVertical(true);
		scAlgorithm.setExpandHorizontal(true);
		
		scAlgorithm.addControlListener(new ControlAdapter() {
			
			@Override
			public void controlResized(ControlEvent e) {
				int width = scAlgorithm.getClientArea().width;
				scAlgorithm.setMinSize(compHoldAlgorithm.computeSize(width, SWT.DEFAULT));
			}
		
		});
		
		compHoldAlgorithm = new Composite(scAlgorithm, SWT.NONE);
		compHoldAlgorithm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldAlgorithm.setLayout(new GridLayout(1, false));
		
		

		scAlgorithm.setContent(compHoldAlgorithm);
		
		
		compAlgorithm = new RabinSecondTabComposite(compHoldAlgorithm, SWT.NONE, rabinAlgorithmTab, rabinCryptosystemTab, scAlgorithm, compHoldAlgorithm);
		compAlgorithm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compAlgorithm.setLayout(new GridLayout(1, false));
		
		
		scAlgorithm.setMinSize(compHoldAlgorithm.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tabSecondItem.setControl(scAlgorithm);*/
		
		
		tabThirdItem = new TabItem(tfRabin, SWT.NONE);
		tabThirdItem.setText(Messages.RabinMainView_tabThirdItem);
		
		Composite compSpaceSecondTab = new Composite(tfRabin, SWT.NONE);
		compSpaceSecondTab.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		compSpaceSecondTab.setLayout(new GridLayout(1, false));
		((GridLayout) compSpaceSecondTab.getLayout()).marginHeight = 0;
		((GridLayout) compSpaceSecondTab.getLayout()).marginWidth = 0;
		((GridLayout) compSpaceSecondTab.getLayout()).marginTop = 10;
		compSpaceSecondTab.setBackground(ColorService.WHITE);
		
		//ScrolledComposite scAttacks = new ScrolledComposite(tfRabin, SWT.H_SCROLL | SWT.V_SCROLL);
		ScrolledComposite scAttacks = new ScrolledComposite(compSpaceSecondTab, SWT.H_SCROLL | SWT.V_SCROLL);
		scAttacks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scAttacks.setExpandVertical(true);
		scAttacks.setExpandHorizontal(true);
		
		scAttacks.addControlListener(new ControlAdapter() {
			
			@Override
			public void controlResized(ControlEvent e) {
				int width = scAttacks.getClientArea().width;
				scAttacks.setMinSize(compHoldAttacks.computeSize(width, SWT.DEFAULT));
			}
		
		});
		
		compHoldAttacks = new Composite(scAttacks, SWT.NONE);
		compHoldAttacks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldAttacks.setLayout(new GridLayout(1, false));
		
		//compHoldAttacks.setBackground(ColorService.GRAY);
		

		scAttacks.setContent(compHoldAttacks);
		
		
		//compAttacks = new RabinThirdTabComposite(compHoldAttacks, SWT.NONE, rabinAttacksTab, rabinCryptosystemTab, scAttacks, compHoldAttacks);
		compAttacks = new RabinThirdTabComposite(compHoldAttacks, SWT.NONE, rabinCryptosystemTab, scAttacks, compHoldAttacks);
		compAttacks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compAttacks.setLayout(new GridLayout(1, false));
		
		scAttacks.setMinSize(compHoldAttacks.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		//tabThirdItem.setControl(scAttacks);
		tabThirdItem.setControl(compSpaceSecondTab);
		
		
		TabItem tabFourthItem = new TabItem(tfRabin, SWT.NONE);
		tabFourthItem.setText(Messages.RabinMainView_1);
		
		Composite compSpaceThirdTab = new Composite(tfRabin, SWT.NONE);
		compSpaceThirdTab.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		compSpaceThirdTab.setLayout(new GridLayout(1, false));
		((GridLayout) compSpaceThirdTab.getLayout()).marginHeight = 0;
		((GridLayout) compSpaceThirdTab.getLayout()).marginWidth = 0;
		((GridLayout) compSpaceThirdTab.getLayout()).marginTop = 10;
		compSpaceThirdTab.setBackground(ColorService.WHITE);
		
		//ScrolledComposite scSettings = new ScrolledComposite(tfRabin, SWT.H_SCROLL | SWT.V_SCROLL);
		ScrolledComposite scSettings = new ScrolledComposite(compSpaceThirdTab, SWT.H_SCROLL | SWT.V_SCROLL);
		scSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scSettings.setExpandVertical(true);
		scSettings.setExpandHorizontal(true);
		
		
		scSettings.addControlListener(new ControlAdapter() {
			
			@Override
			public void controlResized(ControlEvent e) {
				int width = scSettings.getClientArea().width;
				scSettings.setMinSize(compHoldSettings.computeSize(width, SWT.DEFAULT));
			}
		
		});
		
		
		compHoldSettings = new Composite(scSettings, SWT.NONE);
		compHoldSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		compHoldSettings.setLayout(new GridLayout(1, false));
		guiHandler.setControlMargin(compHoldSettings, 0, 0);
	
	
		scSettings.setContent(compHoldSettings);
		
		
		Settings settings = new Settings(compHoldSettings, SWT.NONE, this, compCryptosystem, compAttacks);
		settings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		settings.setLayout(new GridLayout(1, false));
		
		
		scSettings.setMinSize(compHoldSettings.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		//tabFourthItem.setControl(scSettings);
		tabFourthItem.setControl(compSpaceThirdTab);
		
		if(GUIHandler.isDarkmode)
			setColors();
		
		SmoothScroller.scrollSmooth(scCryptosystem);
		SmoothScroller.scrollSmooth(scSettings);
		SmoothScroller.scrollSmooth(scAttacks);
		
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "org.jcryptool.visual.rabin.view");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "org.jcryptool.visual.rabin.view"); //$NON-NLS-1$
		//setBackgroundComponents(compCryptosystem, ColorService.GRAY);
		//setBackgroundComponents(compHoldCryptosystem, ColorService.GRAY);
		

	}
	
	
	/**
	 * reset the plugin
	 */
	public void reset() {
		Control[] children = parent.getChildren();
		for (Control control : children) {
		    control.dispose();
		}
		createPartControl(parent);
		parent.requestLayout();
	}
	

	@Override
	public void setFocus() {
		//compCryptosystem.setFocus();
		rootComposite.setFocus();
	}

}
