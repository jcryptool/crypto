package org.jcryptool.visual.signalencryption.ui;

import java.util.NoSuchElementException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

public class Layout {

	private Layout() {
        // Private constructor to prevent instantiation of this class with only static methods.
    }

    public static GridLayout gl_algorithmGroup() {
        var gl_algorithmGroup = new GridLayout(8, false);
        gl_algorithmGroup.horizontalSpacing = ViewConstants.ARROW_CANVAS_WIDTH;
        return gl_algorithmGroup;
    }

    public static GridLayout gl_stepsComposite() {
        return new GridLayout(1, false);
    }

    public static GridLayout gl_sendingReceivingChainComposite(int alignment) {
    	return gl_genericChainComposite(alignment, 2);
    }
    
    public static GridLayout gl_rootChainComposite(int alignment) {
    	return gl_genericChainComposite(alignment, 2);
    }

    public static GridLayout gl_genericChainComposite(int alignment, int numColumns) {
        var gl_sendingReceivingChainComposite = new GridLayout(numColumns, false);
        gl_sendingReceivingChainComposite.horizontalSpacing = ViewConstants.CHAIN_HORIZONTAL_SPACE;
        gl_sendingReceivingChainComposite.verticalSpacing = ViewConstants.BOX_HEIGHT;
        return gl_sendingReceivingChainComposite;
    }

    public static GridLayout gl_diffieHellmanComposite() {
    	var layout = new GridLayout(1, true);
    	layout.verticalSpacing = ViewConstants.BOX_HEIGHT;
    	return layout;
    }

    public static GridLayout gl_messageBoxGroup() {
        return new GridLayout(1, false);
    }

    public static GridData gd_stepsComposite() {
        return new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
    }

    public static GridData gd_algorithmGroup() {
        var gd_algorithmGroup = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        gd_algorithmGroup.heightHint = ViewConstants.ALGORITHM_HEIGHT; 
        return gd_algorithmGroup;
    }

    public static GridData gd_arrowSpaceComposite() {
        var gd_arrowSpaceComposite = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
        gd_arrowSpaceComposite.minimumHeight = ViewConstants.ALGORITHM_HEIGHT;
        return gd_arrowSpaceComposite;
    }

    public static GridData gd_Messagebox() {
        var gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gridData.widthHint = 250;
        gridData.heightHint = ViewConstants.BOX_HEIGHT * 2;
        return gridData;
    }

    public static GridData gd_shortDescriptionTexts() {
        var gd_shortDescriptionTexts = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_shortDescriptionTexts.minimumHeight = 20;
        gd_shortDescriptionTexts.widthHint = 800;
        return gd_shortDescriptionTexts;

    }
    
    // style data for the labels within the algorithm
    public static GridData gd_algorithmNodesSlim() {
    	return gd_algorithmNodes(ViewConstants.BOX_WIDTH_SLIM);
    }
    
    public static GridData gd_algorithmNodes() {
    	return gd_algorithmNodes(ViewConstants.BOX_WIDTH);
    }

    // style data for the labels within the algorithm
    private static GridData gd_algorithmNodes(int boxWidth) {
        var gd_algorithmLabels = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
        gd_algorithmLabels.widthHint = boxWidth;
        return gd_algorithmLabels;
    }

    public static GridData gd_sendingReceivingChainComposite() {
        return new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
    }

    public static GridData gd_rootChainComposite() {
        return new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
    }

    public static GridData gd_diffieHellmanComposite() {
        return new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
    }

    public static GridData gd_messageBoxComposite() {
        return new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
    }

    // style data for the description texts
    public static GridData gd_longDescriptionTexts() {
        var gd_longDescriptionTexts = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        gd_longDescriptionTexts.minimumHeight = 30;
        gd_longDescriptionTexts.widthHint = 800;
        return gd_longDescriptionTexts;
    }

}
