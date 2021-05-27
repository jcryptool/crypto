// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.errorcorrectingcodes.ui.widget;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.jcryptool.visual.errorcorrectingcodes.data.Matrix2D;

/**
 * The Class InteractiveMatrix is a custom composite to represent a binary matrix that can be
 * modified by user input. It consists of a rows * columns grid of buttons that switch their value
 * from 1 to 0 on mouse click.
 * 
 * @author Daniel Hofmann
 */
public class InteractiveMatrix extends Composite {

    Matrix2D matrix;

    /** The button grid represented as a one-dimensional ArrayList. */
    private ArrayList<Button> buttonGrid;
    
    /** The modified flag, false by default */
    boolean modified;

    /** The permutation matrix flag, false by default */
    boolean permutation;

    private int rows, columns;

    private Composite compMatrixElements;

    private Composite compControlButtons;


    /**
     * Instantiates a new interactive matrix.
     *
     * @param parent the parent composite
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public InteractiveMatrix(Composite parent, int rows, int columns) {
        super(parent, SWT.NONE);
        this.matrix = new Matrix2D(rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.permutation = false;
        buttonGrid = new ArrayList<>();
        
        setLayout(new GridLayout());
       
        compMatrixElements = new Composite(this, SWT.NONE);
        GridLayout gl_compMatrixElements = new GridLayout(columns, false);
        gl_compMatrixElements.marginHeight = 0;
        gl_compMatrixElements.marginWidth = 0;
        compMatrixElements.setLayout(gl_compMatrixElements);
        compMatrixElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Button btn = new Button(compMatrixElements, SWT.NONE);
                btn.setText("0"); //$NON-NLS-1$
                btn.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
                btn.setData(new Point(i, j));

                btn.addListener(SWT.Selection, e -> {
                    setMatrixValues(e, (Point) btn.getData());
                    modified = true;
                });
                
                // Change the mouse icon to a hand when over a button
                btn.addMouseTrackListener(new MouseTrackListener() {
					
					@Override
					public void mouseHover(MouseEvent e) {
						
					}
					
					@Override
					public void mouseExit(MouseEvent e) {
						Display.getCurrent().getActiveShell().setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_ARROW));
					}
					
					@Override
					public void mouseEnter(MouseEvent e) {
						Display.getCurrent().getActiveShell().setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_HAND));
					}
				});

                buttonGrid.add(btn);
            }
        }
        
        compControlButtons = new Composite(this, SWT.NONE); 
        
        GridLayout gl_compControlButtons = new GridLayout();
        gl_compControlButtons.marginHeight = 0;
        gl_compControlButtons.marginWidth = 0;
        compControlButtons.setLayout(gl_compControlButtons);
      
      
    }

    private void setMatrixValues(Event e, Point p) {
        Button b = (Button) e.widget;
        if (b.getText().equals("0")) { //$NON-NLS-1$
            b.setText("1"); //$NON-NLS-1$
            matrix.set(p.x, p.y, 1);
            
            if (isPermutation()) {
                // value 1 sets every other value in its row and column to 0
                for (int row = 0; row < rows; row++) {
                    if (row != p.x) {
                        buttonGrid.get(p.y + (row * rows)).setText("0"); //$NON-NLS-1$
                        matrix.set(row, p.y, 0);
                    }
                }

                for (int col = 0; col < columns; col++) {
                    if (col != p.y) {
                        buttonGrid.get((p.x * rows) + col).setText("0"); //$NON-NLS-1$
                        matrix.set(p.x, col, 0);
                    }
                }
            }
        } else {
            b.setText("0"); //$NON-NLS-1$
            matrix.set(p.x, p.y, 0);
        }
    }

    /**
     * Match the button values to the given binary matrix and set the "modified" flag.
     *
     * @param m the new matrix
     */
    public void setMatrix(Matrix2D m) {
        for (int row = 0; row < m.getRowCount(); row++) {
            for (int col = 0; col < m.getColCount(); col++) {
                buttonGrid.get(col + (row * (m.getColCount()))).setText(String.valueOf(m.get(row, col)));

            }
        }
        this.matrix = m;
        modified = true;
    }

    /**
     * Return the represented matrix by parsing the button values.
     *
     * @return the matrix
     */
    public Matrix2D getMatrix() {
        return matrix;
    }

    /**
     * Checks if the modified flag is set.
     *
     * @return true, if is modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets the modified flag.
     *
     * @param modified the new modified
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }

    /**
     * Checks if the permutation flag is set.
     *
     * @return true, if is permutation
     */
    public boolean isPermutation() {
        return permutation;
    }

    /**
     * Sets the permutation flag.
     *
     * @param permutation the new permutation
     */
    public void setPermutation(boolean permutation) {
        this.permutation = permutation;
    }

    /**
     * Reset the buttons to 0.
     */
    public void reset() {
        for (int i = 0; i < rows * columns; i++) {
            buttonGrid.get(i).setText("0"); //$NON-NLS-1$
        }
    }
}
