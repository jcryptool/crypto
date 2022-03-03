//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2008, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.classic.playfair.algorithm;


import java.io.InputStream;
import java.util.Observer;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.operations.algorithm.AbstractAlgorithm;
import org.jcryptool.core.operations.algorithm.AbstractAlgorithmHandler;
import org.jcryptool.core.operations.algorithm.classic.AbstractClassicAlgorithm;
import org.jcryptool.core.operations.alphabets.AbstractAlphabet;
import org.jcryptool.core.operations.dataobject.IDataObject;
import org.jcryptool.core.operations.dataobject.classic.ClassicDataObject;
import org.jcryptool.crypto.classic.model.algorithm.ClassicAlgorithmConfiguration;
import org.jcryptool.crypto.classic.model.algorithm.ClassicAlgorithmConfigurationWithKey;
import org.jcryptool.crypto.classic.model.ui.wizard.ClassicWizardDialog;
import org.jcryptool.crypto.classic.playfair.PlayfairPlugin;
import org.jcryptool.crypto.classic.playfair.ui.PlayfairWizard;

/**
 * The PlayfairAlgorithmHandler class is a specific
 * implementation of AbstractClassicAlgorithmAction.
 * @see org.jcryptool.core.operations.algorithm.classic.AbstractClassicAlgorithmAction
 *
 * @author SLeischnig
 * @author Holger Friedrich (support of Commands, based on PlayfairAlgorithmAction)
 * @version 0.2
 *
 */
public class PlayfairAlgorithmHandler extends AbstractAlgorithmHandler{

	/**
	 * Constructor
	 *
	 */
	public PlayfairAlgorithmHandler() {
		super();
	}

	/**
	 * This methods performs the action
	 */
	@Override
	public Object execute(ExecutionEvent event) {
		final PlayfairWizard wizard = new PlayfairWizard();
		final WizardDialog dialog = new ClassicWizardDialog(getActiveWorkbenchWindow().getShell(), wizard);
		dialog.setHelpAvailable(true);

		if (dialog.open() == Window.OK) {
            Job job = new Job(Messages.PlayfairAlgorithmHandler_0) {
                @Override
				public IStatus run(final IProgressMonitor monitor) {
                    try {
                        String jobTitle = Messages.PlayfairAlgorithmHandler_1;

                        if (!wizard.encrypt()) {
                            jobTitle = Messages.PlayfairAlgorithmHandler_2;
                        }

                        monitor.beginTask(jobTitle, 4);

                        if (monitor.isCanceled()) {
                            return Status.CANCEL_STATUS;
                        }

						InputStream editorContent = getActiveEditorInputStream();
						char[] key = wizard.getKey().toCharArray();
						
						monitor.worked(1);
						
						AbstractAlphabet alphabet = wizard.getSelectedAlphabet();
						AbstractClassicAlgorithm algorithm = new PlayfairAlgorithm();
						
						monitor.worked(2);
									
						if (wizard.encrypt()) {
							// explicit encrypt
							algorithm.init(AbstractAlgorithm.ENCRYPT_MODE,
									editorContent,
									alphabet,
									key,
									'X', wizard.getTransformData());
						} else {
							// implicit decrypt
							algorithm.init(AbstractAlgorithm.DECRYPT_MODE,
									editorContent,
									alphabet,
									key,
									'X', wizard.getTransformData());
						}
						
						monitor.worked(3);
						
						if(!wizard.isNonAlphaFilter()) {
							algorithm.setFilter(false);
						}
						
						monitor.worked(4);
						
			            ClassicAlgorithmConfigurationWithKey config = new ClassicAlgorithmConfigurationWithKey(
			            		wizard.encrypt(),
			            		"Playfair",
			            		wizard.getSelectedAlphabet(), 
			            		wizard.isNonAlphaFilter(), 
			            		wizard.getTransformData(), 
			            		wizard.getKey()
			            		);
			            Observer editorOpenObserver = ClassicAlgorithmConfiguration.createEditorOpenHandler(algorithm, config);

						PlayfairAlgorithmHandler.super.finalizeRun(algorithm, editorOpenObserver);
                    } catch (final Exception ex) {
                        LogUtil.logError(PlayfairPlugin.PLUGIN_ID, ex);
                    } finally {
                        monitor.done();
                    }

                    return Status.OK_STATUS;
                }
            };
            job.setUser(true);
            job.schedule();
		}
		return(null);
	}

	@Override
	public void run(IDataObject dataobject) {
		AbstractClassicAlgorithm algorithm = new PlayfairAlgorithm();

		ClassicDataObject d = (ClassicDataObject)dataobject;
		algorithm.init(d);

		super.finalizeRun(algorithm);
	}

}
