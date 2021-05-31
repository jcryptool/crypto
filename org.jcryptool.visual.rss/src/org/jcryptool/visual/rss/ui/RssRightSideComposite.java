package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.rss.Descriptions;

/**
 * Handles the layout and the text for the right side of the component (buttons + text).
 * A class extending this class can set the text by calling prepareAboutComposite().
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public abstract class RssRightSideComposite extends Composite {
    public final static int R_MAX_SIZE = 250;

    Composite leftComposite;
    Composite rightComposite;
    Text description;

    public RssRightSideComposite(RssBodyComposite body, int style) {
        super(body, style);
    }
    
    public static String getSplittedString(String string, int charsToNextWrap) {
        String[] newlines = string.split(" ");
        for (int i = 0; i < newlines.length; i++) {
            String rdy = "", rest = newlines[i];
            while (rest.length() > charsToNextWrap) {
                rdy += rest.substring(0, charsToNextWrap) + "\n";
                rest = rest.substring(charsToNextWrap);
            }
            newlines[i] = rdy + rest;
        }
        String out = "";
        for (int i = 0; i < newlines.length; i++) {
            if (i == newlines.length - 1) {
                return out + newlines[i];
            }
            out += newlines[i] + " ";
        }
        return out;
    }

    abstract void prepareAboutComposite();

    protected void prepareGroupView(Composite body, String description) {
        body.setLayout(new RowLayout());

        Group overviewGroupL = new Group(body, SWT.NONE);
        overviewGroupL.setText(description);
        overviewGroupL.setLayout(new GridLayout());
        Composite contentCompositeL = new Composite(overviewGroupL, SWT.NONE);
        contentCompositeL.setLayout(new GridLayout(2, false));

        Group overviewGroupR = new Group(body, SWT.NONE);
        overviewGroupR.setText(Descriptions.About);
        overviewGroupR.setLayout(new GridLayout());
        overviewGroupR.setSize(R_MAX_SIZE, R_MAX_SIZE);
        Composite contentCompositeR = new Composite(overviewGroupR, SWT.NONE);
        contentCompositeR.setLayout(new GridLayout(2, false));

        leftComposite = new Composite(contentCompositeL, SWT.NONE);
        rightComposite = new Composite(contentCompositeR, SWT.NONE);

        GridLayout leftGridLayout = new GridLayout();
        leftGridLayout.horizontalSpacing = 0;
        leftGridLayout.verticalSpacing = 0;
        leftComposite.setLayout(leftGridLayout);

        GridLayout rightGridLayout = new GridLayout();
        rightGridLayout.horizontalSpacing = 0;
        rightGridLayout.verticalSpacing *= 10;
        rightComposite.setLayout(rightGridLayout);

        this.description = new Text(rightComposite, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.LEFT);
        this.description.setEnabled(false);
        GridData labelGridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
        labelGridData.widthHint = R_MAX_SIZE;
        this.description.setLayoutData(labelGridData);
    }
    
    /**
     * Opens a error dialog when the key failed to be loaded.
     * @param title The title of the error dialog.
     * @param message The message of the error dialog.
     */
	protected void showErrorDialog(String title, String message) {
		MessageBox dialog =
		    new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_ERROR | SWT.OK);
		dialog.setText(title);
		dialog.setMessage(message);
		dialog.open();		
	}

}
