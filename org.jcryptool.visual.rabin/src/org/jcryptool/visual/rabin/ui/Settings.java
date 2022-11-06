package org.jcryptool.visual.rabin.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.GUIHandler;

public class Settings extends Composite {
	
	
	private GUIHandler guiHandler;
	private Group grpMode;
	private Composite compSelectionMode;
	private Button btnRadioClassicMode;
	private Button btnRadioDarkMode;
	private Button btnApplyMode;
	private Label lblSeparateInfo;
	private Text txtInfoMode;
	
	private Color colorButtonBG;
	private Color colorButtonFG;
	
	private RabinMainView rmv;
	private RabinFirstTabComposite rftc;
	private RabinSecondTabComposite rstc;
	private CryptosystemTextbookComposite cstb;
	private RabinThirdTabComposite rttc;
	private Group grpInfoMode;
	private Composite compHoldContent;
	

	public Settings(Composite parent, int style) {
		super(parent, style);
		guiHandler = new GUIHandler();
		createContent(this);
		
		if(guiHandler.isDarkmode)
			setColors();
		// TODO Auto-generated constructor stub
	}
	
	public Settings(Composite parent, int style, RabinMainView rmv, RabinFirstTabComposite rftc, RabinThirdTabComposite rttc) {
		super(parent, style);
		this.rmv = rmv;
		this.rftc = rftc;
		this.cstb = rftc.cstb;
		this.rstc = rftc.rstc;
		this.rttc = rttc;
		guiHandler = new GUIHandler();
		createContent(this);
		
		colorButtonBG = rftc.btnStartGenKeys.getBackground();
		colorButtonFG = rftc.btnStartGenKeys.getForeground();
		
		if(guiHandler.isDarkmode)
			setColors();
	}
	
	
	
	private void setColors() {
		
		Color colorBG = GUIHandler.colorDarkModeBG;
		Color colorFG = GUIHandler.colorDarkModeFG;
		//Color colorFG = ColorService.YELLOW;
		Color colorButtonBG = GUIHandler.colorButtonsBG;
		Color colorButtonFG = GUIHandler.colorButtonsFG;
		
		grpInfoMode.setBackground(colorBG);
		grpInfoMode.setForeground(colorFG);
		compHoldContent.setBackground(colorBG);
		compHoldContent.setForeground(colorFG);
		
		
		this.setBackground(colorBG);
		this.setForeground(colorFG);
		grpMode.setBackground(colorBG);
		grpMode.setForeground(colorFG);
		compSelectionMode.setBackground(colorBG);
		compSelectionMode.setForeground(colorFG);
		btnRadioClassicMode.setBackground(colorBG);
		btnRadioClassicMode.setForeground(colorFG);
		btnRadioDarkMode.setBackground(colorBG);
		btnRadioDarkMode.setForeground(colorFG);
		btnApplyMode.setBackground(colorButtonBG);
		btnApplyMode.setForeground(colorButtonFG);
//		lblSeparateInfo.setBackground(colorBG);
//		lblSeparateInfo.setForeground(colorFG);
		txtInfoMode.setBackground(colorBG);
		txtInfoMode.setForeground(colorFG);
	}
	
	
	
	private void restartPlugin() {
		rmv.reset();
	}
	
	
	
	private void createContent(Composite parent) {
		setLayout(new GridLayout(1, false));
		//guiHandler.setControlMargin(this, 0, 0);
//		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		compHoldContent = new Composite(parent, SWT.NONE);
		compHoldContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compHoldContent.setLayout(new GridLayout(2, false));
		//guiHandler.setControlMargin(compHoldContent, 0, 0);
		
		
		//grpMode = new Group(parent, SWT.NONE);
		grpMode = new Group(compHoldContent, SWT.NONE);
		grpMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//grpMode.setLayout(new GridLayout(4, false));
		grpMode.setLayout(new GridLayout(1, false));
		grpMode.setText("Mode");
		
		compSelectionMode = new Composite(grpMode, SWT.NONE);
		compSelectionMode.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		compSelectionMode.setLayout(new GridLayout(3, false));
		
		btnRadioClassicMode = new Button(compSelectionMode, SWT.RADIO);
		btnRadioClassicMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		btnRadioClassicMode.setText("Classic mode");
		
		
		
		btnRadioDarkMode = new Button(compSelectionMode, SWT.RADIO);
		btnRadioDarkMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		btnRadioDarkMode.setText("Dark mode");
		
		
		if(guiHandler.isDarkmode) {
			btnRadioDarkMode.setSelection(true);
			btnRadioClassicMode.setSelection(false);
		}
		else {
			btnRadioClassicMode.setSelection(true);
			btnRadioDarkMode.setSelection(false);
		}
		
		
		btnApplyMode = new Button(compSelectionMode, SWT.PUSH);
		btnApplyMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		btnApplyMode.setText("Apply");
		btnApplyMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(btnRadioClassicMode.getSelection()) {
					
					if(!guiHandler.isDarkmode)
						return;
					
					GUIHandler.isDarkmode = false;
					//guiHandler.setColorBGinfo(ColorService.LIGHTGRAY);
					//guiHandler.setColorFGinfo(ColorService.BLACK);
					restartPlugin();
				}
				
				else if(btnRadioDarkMode.getSelection()) {
					
					if(guiHandler.isDarkmode)
						return;
					
					GUIHandler.colorDeselectControlBG = ColorService.GRAY;
					GUIHandler.colorDeselectControlFG = ColorService.WHITE;
					GUIHandler.colorResetFinalPlaintextBG = ColorService.GRAY;
					GUIHandler.colorResetFinalPlaintextFG = ColorService.WHITE;
					GUIHandler.colorSelectControlBG = ColorService.LIGHT_AREA_BLUE;
					GUIHandler.colorSelectControlFG = ColorService.BLACK;
					GUIHandler.colorTxtWhichYouCanEnterBG = ColorService.WHITE;
					GUIHandler.colorTxtWhichYouCanEnterFG = ColorService.BLACK;
					
					
					GUIHandler.isDarkmode = true;
					//guiHandler.setColorBGinfo(ColorService.GRAY);
					//guiHandler.setColorFGinfo(ColorService.WHITE);
					restartPlugin();
				}
			}
		});
		
		
//		lblSeparateInfo = new Label(grpMode, SWT.SEPARATOR | SWT.VERTICAL);
//		lblSeparateInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));	
		
		grpInfoMode = new Group(compHoldContent, SWT.NONE);
		//Group grpInfoMode = new Group(parent, SWT.NONE);
		grpInfoMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpInfoMode.setLayout(new GridLayout(1, false));
		grpInfoMode.setText(" ");
		
		
		
		//txtInfoMode = new Text(grpMode, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoMode = new Text(grpInfoMode, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtInfoMode, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoMode.setBackground(GUIHandler.colorBGinfo);
		/*txtInfoMode.setText("Select \"Classic mode\" or \"Dark mode\" and click on \"Apply\" to "
				+ "either activate the classic mode or the dark mode. Be aware that the plugin will restart "
				+ "after clicking on \"Apply\"");*/
		
		txtInfoMode.setText("After selecting another mode, click on \"Apply\". Note that a mode change restarts the plugin which may take a little time.");
		/*txtInfoMode.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoMode, SWT.CURSOR_ARROW);
			}
		});*/
	
		
		
		
	}

}
