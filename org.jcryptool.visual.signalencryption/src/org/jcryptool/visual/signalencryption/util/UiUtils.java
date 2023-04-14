package org.jcryptool.visual.signalencryption.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;

public class UiUtils {
	
	
	private static enum Debug {
		YES, NO
	}

    private UiUtils() {
        // Private constructor to prevent instantiation of this class
    }

    /**
     * Insert invisible spacing widgets into the composite container.
     * 
     * Can be used for GridLayouts to fill into "empty" slots in the grid.
     * 
     * @param parent widget to attach spacers to
     * @param number number of spacers (grid cells) to fill
     */
    public static void insertSpacers(Composite parent, int number) {
        for (int i = 0; i < number; ++i) {
            var label = new Label(parent, SWT.NONE);
            label.setVisible(false);
        }
    }
    
    /**Insert invisible spacing widgets into the parent composite which have the minimum width specified */
    public static void insertExcessiveSpacers(Composite parent, int number) {
    	var layout = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
    	insert(parent, number, layout);
    }
    
    /**Insert invisible spacing widgets into the parent composite which have the minimum width specified */
    public static void insertSpacers(Composite parent, int number, int minimumWidth) {
    	var layout = GridDataBuilder.with(SWT.LEFT, SWT.FILL, true, false).minimumWidth(minimumWidth).get();
    	insert(parent, number, layout);
    }
    
    /**Insert invisible spacing widgets into the parent composite which have the minimum width and height specified */
    public static void insertSpacers(Composite parent, int number, int minimumWidth, int minimumHeight) {
    	var layout = GridDataBuilder.with(SWT.LEFT, SWT.TOP, true, true)
    			.minimumWidth(minimumWidth)
    			.minimumHeight(minimumHeight)
    			.get();
    	insert(parent, number, layout);
    }

    private static void insert(Composite parent, int number, GridData layout) {
    	insert(parent, number, layout, Debug.NO);
    }
    
    private static void insert(Composite parent, int number, GridData layout, Debug debug) {
    	for (int i = 0; i < number; ++i) {
            var label = new Label(parent, SWT.NONE);
            label.setLayoutData(layout);
            
            // In case you have layout problems, this flag can be toggled to actually show the spaces
            if (debug == Debug.YES) {
            	label.setBackground(ColorService.LIGHT_AREA_BLUE);
            } else {
            	label.setVisible(false);
            }
        }
    }
    
    public static SelectionAdapter onSelection(SelectionHandler handler) {
    	
    	return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handler.on(e);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
    		
		};
    }
    
    
    @FunctionalInterface
    public interface SelectionHandler {
    	
    	public void on(SelectionEvent e);
    }

}
