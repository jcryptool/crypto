// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.entropy.ui;

import java.text.DecimalFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jcryptool.analysis.entropy.calc.EntropyCalc;

public class EntropyUItable extends Composite {
	private Table tableEnt;
	private DecimalFormat df;
	private DecimalFormat dfp;

	public EntropyUItable(Composite parent, int style) {
		super(parent, style);
		df = new DecimalFormat(Messages.EntropyUItable_0);
		dfp = new DecimalFormat(Messages.EntropyUItable_1);
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridLayout());

		tableEnt = new Table(this, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		tableEnt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableEnt.setLinesVisible(true);
		tableEnt.setHeaderVisible(true);
		String[] titles = { Messages.EntropyUItable_2, Messages.EntropyUItable_3, Messages.EntropyUItable_4,
				Messages.EntropyUItable_5, Messages.EntropyUItable_6, Messages.EntropyUItable_7 };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(tableEnt, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++) {
			tableEnt.getColumn(i).pack();
		}

	}

	public void printEntropyMatrix(EntropyCalc eC) {
		tableEnt.removeAll();
		double[][] matrix = eC.getResultMatrix();
		for (int i = 0; i < eC.getActualN(); i++) {
			String num = ((Integer) (i + 1)).toString();
			TableItem item = new TableItem(tableEnt, SWT.NONE);
			item.setText(0, num); // n
			item.setText(1, df.format(matrix[i][1])); // Gn
			if (i != 0) {
				item.setText(2, dfp.format((matrix[i][9]))); // growth
			}
			item.setText(3, df.format(matrix[i][0])); // Fn
			item.setText(4, df.format(matrix[i][6])); // Gn / n
			item.setText(5, dfp.format(matrix[i][8])); // redundancy
		}

		for (int i = 0; i < 6; i++) {
			tableEnt.getColumn(i).pack();
			tableEnt.getColumn(i).setAlignment(SWT.CENTER);
		}
	}

}
