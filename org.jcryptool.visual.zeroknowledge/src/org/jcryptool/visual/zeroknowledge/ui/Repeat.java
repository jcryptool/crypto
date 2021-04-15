// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.zeroknowledge.ui;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.visual.zeroknowledge.algorithm.Funcs;
import org.jcryptool.visual.zeroknowledge.algorithm.feigefiatshamir.FFSFuncs;

/**
 * Diese Klasse ist ein Dialog, der ein Protokoll mehrmals durchgeführt werden
 * kann. Dazu muss das Interface Funcs implementiert sein
 *
 * @author Mareike Paul
 *
 */
public class Repeat extends Dialog {
	private Button aliceButton;
	private Scale amount;
	private Label amountAnzeige;
	private Button carolButton;
	private Label carolPercent;
	private Text ergebnis;
	private Funcs funcs;
	private Label info;
	private Button start;
	private Composite main;
	private HashMap<Integer, Double> attackChances;
	private String plugin;

	/**
	 * Konstruktor für die graphische Oberfläche
	 *
	 * @param parent Shell, zu dem dieser Dialog gehört
	 * @param funcs  Funcs-Implementation, die ein Protokoll durchführen soll
	 * @param string String, der ggf. dem String zum Finden des richtigen Strings
	 *               aus den MessagesBundle*.properties
	 */
	public Repeat(Shell parent, Funcs funcs, String string) {
		super(parent, SWT.NONE);
		this.funcs = funcs;
		// Sets Carols chance to guess right for the different functions
		// All except FiatFeigeShamir have a probability of 50%
		plugin = string;
		if (plugin == "FFS.") {
			attackChances = calculateAttackChances(((FFSFuncs) funcs).getVectorLength());
		} else {
			attackChances = calculateAttackChances(1);
		}

		parent.setText(Messages.Repeat_0);
		createGui(parent);
		parent.pack();
		parent.open();

		// Set Focus to the start button.
		start.setFocus();

	}

	private void createGui(Shell s) {

		main = new Composite(s, SWT.NONE);
		GridLayout gl_main = new GridLayout(2, false);
		gl_main.marginWidth = 50;
		gl_main.marginHeight = 20;
		main.setLayout(gl_main);

		aliceButton = new Button(main, SWT.RADIO);
		aliceButton.setText(Messages.Repeat_2);
		aliceButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * Wenn der RadioButton betätigt wurde, wird beim Funcs-Objekt gesetzt, das das
			 * Geheimnis bekannt ist. Zusätzlich wird der RadioButton für Carol auf "nicht
			 * ausgewählt" gesetzt
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				aliceButton.setSelection(true);
				carolButton.setSelection(false);
				carolPercent.setVisible(false);
				funcs.setSecretKnown(true);
			}
		});
		aliceButton.setSelection(true);
		aliceButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		carolButton = new Button(main, SWT.RADIO);
		// In FFS the chance is not k/2, it is k^0.5^entries in vektor
		if (plugin == "FFS.") {
			// plugin ist Feige Fiat Shamir
			carolButton.setText(Messages.Repeat_3_2);
		} else {
			// Plugin is fiat smair, magic door, or graph isomorphism
			carolButton.setText(Messages.Repeat_3_1);
		}
		carolButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * Wenn der RadioButton betätigt wurde, wird beim Funcs-Objekt gesetzt, das das
			 * Geheimnis nicht bekannt ist. Zusätzlich wird der RadioButton für Alice auf
			 * "nicht ausgewählt" gesetzt
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				aliceButton.setSelection(false);
				carolButton.setSelection(true);
				carolPercent.setVisible(true);
				funcs.setSecretKnown(false);
			}
		});
		GridData gd_carolButton = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		carolButton.setLayoutData(gd_carolButton);

		info = new Label(main, SWT.NONE);
		info.setText(Messages.Repeat_4);
		GridData gd_info = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_info.verticalIndent = 20;
		info.setLayoutData(gd_info);

		amountAnzeige = new Label(main, SWT.NONE);
		amountAnzeige.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		amount = new Scale(main, SWT.NONE);
		amount.setPageIncrement(2);
		amount.setMinimum(1);
		amount.setMaximum(20);
		amount.setSelection(10);
		amount.addSelectionListener(new SelectionAdapter() {
			/**
			 * Methode, die aufgerufen wird, wenn der Scale bewegt wurde. Aktualisiert den
			 * Wert im Label, das die Anzahl der Durchläufe angibt
			 */
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				var selectedRounds = amount.getSelection();
				amountAnzeige.setText(Integer.toString(selectedRounds)); // $NON-NLS-1$
				// If Carol:
				// Calculate how likely it is for the specified amount of iterations
				// to deceive Bob
				double chance = attackChances.get(selectedRounds);
				chance = chance * 100;
				
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
				nf.setMaximumFractionDigits(5);
				
				String chanceText = "";
				if (chance > 0.00001) {
					chanceText = nf.format(chance);
				} else {
					chanceText = "< " + nf.format((double) 0.00001);
				}
				
				carolPercent.setText(chanceText + Messages.Repeat_1);
				
				// Clear the result area to avoid an inconsisten GUI state
				ergebnis.setText("");
			}
		});
		amount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		// Create the labels which specify for Carol the chance to deceive Bob
		carolPercent = new Label(main, SWT.NONE);
		carolPercent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		carolPercent.setVisible(false);

		start = new Button(main, SWT.PUSH);
		start.setText(Messages.Repeat_9);
		GridData gd_start = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_start.verticalIndent = 20;
		start.setLayoutData(gd_start);
		start.addSelectionListener(new SelectionAdapter() {
			/**
			 * Methode, die aufgerufen wird, wenn der Start-Button betätigt wurde. Liest den
			 * Wert des Sliders aus, lässt das Protokoll dementsprechend oft laufen und gibt
			 * das Ergebnis aus.
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Versuche insgesamt
				int amount_int = amount.getSelection();
				// Erfolgreiche Versuche
				int result = funcs.protokoll(amount_int);
				
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
				nf.setMaximumFractionDigits(2);
				
				StringBuilder sb = new StringBuilder();
				Double percent = ((double) result * 100) / amount_int;

				sb.append(MessageFormat.format(Messages.Repeat_5,
						amount_int,
						result,
						nf.format(percent)));
				
				// Only when all iterations can be guessed right from Carol, Bob is deceived
				if (carolButton.getSelection()) {
					sb.append("\n");
					if (amount_int - result == 0) { //$NON-NLS-1$
						sb.append(Messages.Repeat_6);
					} else {
						sb.append(Messages.Repeat_7);
					}
				}
				
				ergebnis.setText(sb.toString());
				
			}
		});
		start.setToolTipText(Messages.Repeat_8);

		ergebnis = new Text(main, SWT.WRAP);
		ergebnis.setFont(FontService.getNormalBoldFont());
		ergebnis.setEditable(false);
		GridData gd_ergebnis = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_ergebnis.verticalIndent = 20;
		ergebnis.setLayoutData(gd_ergebnis);
		// Is used to allocate space in the gui
		ergebnis.setText("\n ");

		int defaultScalerSelect = amount.getSelection();
		amountAnzeige.setText(Integer.toString(defaultScalerSelect));

		// Set initial chance to deceive Bob when "Carol"
		double chance = attackChances.get(defaultScalerSelect);
		chance = chance * 100;

		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		nf.setMaximumFractionDigits(5);
		
		String chanceText = "";
		if (chance > 0.00001) {
			chanceText = nf.format(chance);
		} else {
			chanceText = "< " + nf.format((double) 0.00001);
		}
		
		carolPercent.setText(chanceText + Messages.Repeat_1);

		main.pack();
	}

	/**
	 * Calculate a map of (round: chance of successful attack) for 20 rounds.
	 * 
	 * The attack chance for Feige-Fiat-Shamir is 0.5^(k*t), where k is the used
	 * secret vector length and t is the number of rounds in the protocol.
	 * 
	 * @param vectorSize Length of secret vector used in the protocol variant.
	 * @return A HashMap with key=round, value=chance
	 * 
	 */
	private HashMap<Integer, Double> calculateAttackChances(int vectorSize) {
		var map = new HashMap<Integer, Double>();
		for (int i = 1; i <= 21; ++i) {
			map.put(i, Math.pow(0.5, i * vectorSize));
		}
		return map;
	}
}
