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

    public static GridLayout gl_arrowSpaceComposite() {
        var arrowSpaceLayout = new GridLayout(1, false);
        arrowSpaceLayout.marginWidth = 0;  // The arrow should "dock" onto the boxes
        arrowSpaceLayout.marginTop = calculateConnectingArrowMargin();
        arrowSpaceLayout.verticalSpacing = 0;
        return arrowSpaceLayout;
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
        // if (alignment == SWT.LEFT) {
        //     gl_sendingReceivingChainComposite.marginRight = ViewConstants.HORIZONTAL_SPACING;
        //     gl_sendingReceivingChainComposite.marginLeft = 0;
        // } else if (alignment == SWT.RIGHT) {
        //     gl_sendingReceivingChainComposite.marginRight = 0;
        //     gl_sendingReceivingChainComposite.marginLeft = ViewConstants.HORIZONTAL_SPACING;
        // } else {
        //     throw new NoSuchElementException(
        //             "PROGRAMMING ERROR: This method takes SWT.LEFT or SWT.RIGHT as argument, "
        //             + "nothing else.");
        // }
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

    public static int calculateConnectingArrowHeight(int borderWidth) {
    // The distance to calculate is explained by this chart:
    //
    //        +--------------+
    //        |      BOX     |      —
    //        +--------------+      |
    //         verticalSpacing      |
    //                |             |
    //       Arrow    |             |  target: total height
    //                |             |
    //                ▾             |
    //         verticalSpacing      |
    //        +--------------+      |
    //        |      BOX     |      —
    //        +--------------+
    // We can take one single box height as we need it two times halved to get the box centers.
    // As this has a 1px border, we add +1 for each border (in total 4)
    // The vertical spacing has to be taken twice (applied at arrow start and arrow end).
    // A major part is the vertical arrow height itself.
    // Finally, in order for the connecting arrow to be centered, half its thickness must be
    // at each end, so one arrow thickness is added.
    return ViewConstants.BOX_HEIGHT + borderWidth * 4 + gl_algorithmGroup().verticalSpacing * 2 +
        ViewConstants.UP_DOWN_ARROW_SIZE + ViewConstants.ARROW_THICKNESS;
    }

    private static int calculateConnectingArrowMargin() {
        // The distance to calculate is explained by this chart:
        //
        //      --Group Top Border-------   —
        //           marginTop              |
        //        verticalSpacing           |  target: total height
        //        +--------------+          |
        //        |      BOX     |          — 
        //        +--------------+ 
        //
        // TODO: The unknown group height is explained in more detail in its docstring.
        // Find out if there is a clean, platform independent solution to calculate the correct
        // offset.
        int unknownGroupHeight = ViewConstants.UNKNOWN_GROUP_HEIGHT_DEFAULT;
        var os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            unknownGroupHeight = ViewConstants.UNKNOWN_GROUP_HEIGHT_LINUX;
        } else if (os.contains("win")) {
            unknownGroupHeight = ViewConstants.UNKNOWN_GROUP_HEIGHT_WINDOWS;
        }

        var layout = Layout.gl_diffieHellmanComposite();
        return layout.marginTop + layout.verticalSpacing + (ViewConstants.BOX_HEIGHT / 2) -
            (ViewConstants.ARROW_HEAD_THICKNESS/ 2) + unknownGroupHeight;
    }
}
