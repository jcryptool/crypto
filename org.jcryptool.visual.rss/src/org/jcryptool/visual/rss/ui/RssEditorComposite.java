package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;

public class RssEditorComposite extends Composite {

    public RssEditorComposite(Composite parent) {
        super(parent, SWT.NONE);

        setLayout(new RowLayout());

        Group overviewGroup = new Group(this, SWT.NONE);
        overviewGroup.setText(Descriptions.Message);
        overviewGroup.setLayout(new GridLayout());

    }

}
