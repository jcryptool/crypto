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
	
	private RabinMainView rmv;
	

	public Settings(Composite parent, int style) {
		super(parent, style);
		guiHandler = new GUIHandler();
		createContent(this);
		
		if(guiHandler.getDarkmode())
			setColors();
		// TODO Auto-generated constructor stub
	}
	
	public Settings(Composite parent, int style, RabinMainView rmv) {
		super(parent, style);
		this.rmv = rmv;
		guiHandler = new GUIHandler();
		createContent(this);
		
		if(guiHandler.getDarkmode())
			setColors();
	}
	
	
	
	private void setColors() {
		
		Color colorBG = guiHandler.getColorDarkModeBG();
		Color colorFG = guiHandler.getColorDarkModeFG();
		//Color colorFG = ColorService.YELLOW;
		Color colorTxtWarningFG = guiHandler.getColorDarkModeWarningFG();
		Color colorButtonBG = guiHandler.getColorButtonsBG();
		Color colorButtonFG = guiHandler.getColorButtonsFG();
		Color colorTxtWhichYouCanEnterBG = ColorService.WHITE;
		Color colorTxtWhichYouCanEnterFG = ColorService.BLACK;

		
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
		lblSeparateInfo.setBackground(colorBG);
		lblSeparateInfo.setForeground(colorFG);
		txtInfoMode.setBackground(colorBG);
		txtInfoMode.setForeground(colorFG);
	}
	
	
	
	private void restartPlugin() {
		rmv.reset();
	}
	
	
	
	private void createContent(Composite parent) {
		
		grpMode = new Group(parent, SWT.NONE);
		grpMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpMode.setLayout(new GridLayout(4, false));
		grpMode.setText("Mode");
		
		compSelectionMode = new Composite(grpMode, SWT.NONE);
		compSelectionMode.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		compSelectionMode.setLayout(new GridLayout(3, false));
		
		btnRadioClassicMode = new Button(compSelectionMode, SWT.RADIO);
		btnRadioClassicMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		btnRadioClassicMode.setText("Classic mode");
		btnRadioClassicMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		
		btnRadioDarkMode = new Button(compSelectionMode, SWT.RADIO);
		btnRadioDarkMode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		btnRadioDarkMode.setText("Dark mode");
		btnRadioDarkMode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		
		if(guiHandler.getDarkmode()) {
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
					
					if(!guiHandler.getDarkmode())
						return;
					
					guiHandler.setDarkmode(false);
					//guiHandler.setColorBGinfo(ColorService.LIGHTGRAY);
					//guiHandler.setColorFGinfo(ColorService.BLACK);
					restartPlugin();
				}
				
				else if(btnRadioDarkMode.getSelection()) {
					
					if(guiHandler.getDarkmode())
						return;
					
					guiHandler.setDarkmode(true);
					//guiHandler.setColorBGinfo(ColorService.GRAY);
					//guiHandler.setColorFGinfo(ColorService.WHITE);
					restartPlugin();
				}
			}
		});
		
		
		lblSeparateInfo = new Label(grpMode, SWT.SEPARATOR | SWT.VERTICAL);
		lblSeparateInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));	
		
		
		
		txtInfoMode = new Text(grpMode, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		txtInfoMode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		guiHandler.setSizeControl(txtInfoMode, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoMode.setBackground(guiHandler.getColorBGinfo());
		txtInfoMode.setText("Select \"Classic mode\" or \"Dark mode\" and click on \"Apply\" to "
				+ "either activate the classic mode or the dark mode. Be aware that the plugin will restart "
				+ "after clicking on \"Apply\"");
		txtInfoMode.addMouseTrackListener(new MouseTrackAdapter() {
			
			@Override
			public void mouseEnter(MouseEvent e) {
				// TODO Auto-generated method stub
				guiHandler.setCursorControl(txtInfoMode, SWT.CURSOR_ARROW);
			}
		});
	
		
		
		
	}

}
