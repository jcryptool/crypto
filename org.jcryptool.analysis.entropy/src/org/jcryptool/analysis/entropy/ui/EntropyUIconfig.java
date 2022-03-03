// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.entropy.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jcryptool.analysis.entropy.EntropyPlugin;
import org.jcryptool.analysis.entropy.calc.EntropyCalc;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.operations.algorithm.classic.textmodify.TransformData;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.constants.IConstants;
import org.jcryptool.crypto.ui.textmodify.wizard.ModifyWizard;

public class EntropyUIconfig extends Composite {

	private Combo cComboSignificance;
	private Button buttonFilter;
	private Button buttonStart;
	private Button buttonDeepAnalysis;
	private Combo cComboTupelLength;
	private Button buttonStandardAnalysis;
	private Group groupAnalysisConfig;
	private EntropyUI entropyUIpointer;

	private String editorname;

	private TransformData myModifySettings;

	public EntropyUIconfig(Composite parent, int style) {
		super(parent, style);
		initGUI();
		myModifySettings = new TransformData();
		myModifySettings.setUnmodified();
	}

	private void initGUI() {
		GridLayout gl_thisLayout = new GridLayout();
		gl_thisLayout.verticalSpacing = 20;
		this.setLayout(gl_thisLayout);

		groupAnalysisConfig = new Group(this, SWT.NONE);
		groupAnalysisConfig.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		GridLayout groupAnalysisConfigLayout = new GridLayout();
		groupAnalysisConfig.setLayout(groupAnalysisConfigLayout);
		groupAnalysisConfig.setText(Messages.EntropyUIconfig_2);

		buttonFilter = new Button(groupAnalysisConfig, SWT.PUSH | SWT.CENTER);
		buttonFilter.setText(Messages.EntropyUIconfig_3);
		buttonFilter.setToolTipText(Messages.EntropyUIconfig_24);
		buttonFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				myModifySettings = getWizardSettings(myModifySettings);
			}
		});

		buttonStandardAnalysis = new Button(groupAnalysisConfig, SWT.RADIO | SWT.LEFT);
		GridData buttonStandardAnalysisData = new GridData();
		buttonStandardAnalysisData.verticalAlignment = SWT.BOTTOM;
		buttonStandardAnalysisData.verticalIndent = 25;
		buttonStandardAnalysis.setLayoutData(buttonStandardAnalysisData);
		buttonStandardAnalysis.setText(Messages.EntropyUIconfig_4);
		buttonStandardAnalysis.setSelection(true);
		buttonStandardAnalysis.setToolTipText(Messages.EntropyUIconfig_26);
		buttonStandardAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				cComboSignificance.setEnabled(false);
				cComboTupelLength.setEnabled(true);
			}
		});

		cComboTupelLength = new Combo(groupAnalysisConfig, SWT.BORDER | SWT.READ_ONLY);
		for (int i = 1; i <= 30; i++) {
			cComboTupelLength.add(Messages.EntropyUIconfig_5 + i);
		}
		cComboTupelLength.select(4);

		buttonDeepAnalysis = new Button(groupAnalysisConfig, SWT.RADIO | SWT.LEFT);
		GridData buttonDeepAnalysisData = new GridData();
		buttonDeepAnalysisData.verticalAlignment = SWT.BOTTOM;
		buttonDeepAnalysisData.verticalIndent = 25;
		buttonDeepAnalysis.setLayoutData(buttonDeepAnalysisData);
		buttonDeepAnalysis.setText(Messages.EntropyUIconfig_6);
		buttonDeepAnalysis.setToolTipText(Messages.EntropyUIconfig_27);
		buttonDeepAnalysis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				cComboTupelLength.setEnabled(false);
				cComboSignificance.setEnabled(true);
			}
		});

		cComboSignificance = new Combo(groupAnalysisConfig, SWT.BORDER | SWT.READ_ONLY);
		cComboSignificance.add(Messages.EntropyUIconfig_9);
		cComboSignificance.add(Messages.EntropyUIconfig_10);
		cComboSignificance.add(Messages.EntropyUIconfig_11);
		cComboSignificance.add(Messages.EntropyUIconfig_12);
		cComboSignificance.add(Messages.EntropyUIconfig_13);
		cComboSignificance.select(1);
		cComboSignificance.setEnabled(false);

		buttonStart = new Button(this, SWT.PUSH | SWT.CENTER);
		buttonStart.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		buttonStart.setText(Messages.EntropyUIconfig_22);
		buttonStart.setToolTipText(Messages.EntropyUIconfig_23);
		buttonStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				final int n = buttonDeepAnalysis.getSelection() ? 0 : cComboTupelLength.getSelectionIndex() + 1;
				final double sig = getSignificance();

				if (checkEditor()) {
					String input = getEditorText();
					editorname = EditorsManager.getInstance().getActiveEditorTitle();
					if (input.length() >= n) {

						Job job = new Job(Messages.EntropyUIconfig_14) {
							@Override
							public IStatus run(final IProgressMonitor monitor) {
								monitor.beginTask(Messages.EntropyUIconfig_19, 2);

								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										blockStartButton();
									}
								});

								if (monitor.isCanceled()) {
									return Status.CANCEL_STATUS;
								}

								final EntropyCalc calc = new EntropyCalc(getEditorText(), n, sig, myModifySettings,
										entropyUIpointer, editorname);

								monitor.worked(1);

								calc.startCalculator();

								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										entropyUIpointer.getCompositeResults().printSummary(calc);
										entropyUIpointer.getCompositeTable().printEntropyMatrix(calc);
										entropyUIpointer.getCMainTabFolder().setSelection(1);
									}
								});

								monitor.worked(2);

								monitor.done();

								Display.getDefault().asyncExec(new Runnable() {
									@Override
									public void run() {
										freeStartButton();
									}
								});

								return Status.OK_STATUS;
							}
						};
						job.setUser(true);
						job.schedule();
					} else {
						MessageDialog.openInformation(entropyUIpointer.getShell(), Messages.EntropyUIconfig_15,
								Messages.EntropyUIconfig_16);
					}
				} else {
					MessageDialog dialog = new MessageDialog(entropyUIpointer.getShell(), Messages.EntropyUIconfig_17,
							null, Messages.EntropyUIconfig_18, MessageDialog.INFORMATION,
							new String[] { Messages.EntropyUIconfig_25, Messages.EntropyUIconfig_20 }, 1);
					int result = dialog.open();
					if (result == 0) {
						try {
							String s = ""; //$NON-NLS-1$
							InputStream is = new ByteArrayInputStream(s.getBytes(IConstants.UTF8_ENCODING));
							IEditorInput input = AbstractEditorService.createOutputFile(is);
							EditorsManager.getInstance().openNewTextEditor(input);
						} catch (UnsupportedEncodingException ueex) {
							MessageBox box = new MessageBox(getShell(), SWT.ICON_INFORMATION);
							box.setText(Messages.EntropyUIconfig_29);
							box.setMessage(Messages.EntropyUIconfig_30);
							box.open();
						} catch (PartInitException piex) {
							MessageBox box = new MessageBox(getShell(), SWT.ICON_INFORMATION);
							box.setText(Messages.EntropyUIconfig_29);
							box.setMessage(Messages.EntropyUIconfig_30);
							box.open();
						}
					}
				}
			}
		});

	}

	/**
	 * reads the current value from an input stream
	 * 
	 * @param in the input stream
	 */
	private String InputStreamToString(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, IConstants.UTF8_ENCODING));
		} catch (UnsupportedEncodingException e1) {
			LogUtil.logError(EntropyPlugin.PLUGIN_ID, e1);
		}

		StringBuffer myStrBuf = new StringBuffer();
		int charOut = 0;
		String output = ""; //$NON-NLS-1$
		try {
			while ((charOut = reader.read()) != -1) {
				myStrBuf.append(String.valueOf((char) charOut));
			}
		} catch (IOException e) {
			LogUtil.logError(EntropyPlugin.PLUGIN_ID, e);
		}
		output = myStrBuf.toString();
		return output;
	}

	private TransformData getWizardSettings(TransformData predefined) {

		ModifyWizard wizard = new ModifyWizard();
		wizard.setPredefinedData(predefined);
		WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		int result = dialog.open();

		if (result == 0)
			return wizard.getWizardData();
		else
			return predefined;

	}

	private boolean checkEditor() {
		return EditorsManager.getInstance().isEditorOpen();
	}

	private String getEditorText() {
		return InputStreamToString(EditorsManager.getInstance().getActiveEditorContentInputStream());
	}

	private void blockStartButton() {
		buttonStart.setText(Messages.EntropyUIconfig_21);
		buttonStart.setEnabled(false);
	}

	private void freeStartButton() {
		buttonStart.setText(Messages.EntropyUIconfig_22);
		buttonStart.setEnabled(true);
	}

	protected void setEntropyUIpointer(EntropyUI pointer) {
		this.entropyUIpointer = pointer;
	}

	private double getSignificance() {
		double result = 0;
		switch (cComboSignificance.getSelectionIndex()) {
		case 0:
			return result;
		case 1:
			result = 0.001;
			return result;
		case 2:
			result = 0.0025;
			return result;
		case 3:
			result = 0.005;
			return result;
		case 4:
			result = 0.01;
			return result;
		default:
			return result;
		}
	}
}
