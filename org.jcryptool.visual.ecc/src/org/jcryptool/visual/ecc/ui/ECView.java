// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.ecc.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.operations.util.PathEditorInput;
import org.jcryptool.core.util.directories.DirectoryService;
import org.jcryptool.visual.ecc.ECCPlugin;
import org.jcryptool.visual.ecc.Messages;

public class ECView extends ViewPart {
	
	public static int customHeightIndent = 10; 
	
    private Composite parent;
    private ECContentReal contentReal;
    private ECContentFp contentFp;
    private StackLayout layout;
    private ECContentFm contentFm;
    private ECContentLarge contentLarge;

    private String log = ""; //$NON-NLS-1$
//    private String saveLocation;
//    private String saveFileName = ""; //$NON-NLS-1$
//    public boolean autoSave = false;
//    public int saveTo = 0;
    private File logFile;
    private IWorkbenchPage editorPage;

    /**
     * The constructor.
     */
    public ECView() {
    	
    }

//    public String getFileName() {
//        return saveFileName;
//    }

    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    @Override
	public void createPartControl(Composite parent) {
        this.parent = parent;

        layout = new StackLayout();
        parent.setLayout(layout);

        contentReal = new ECContentReal(parent, SWT.NONE, this);
        contentFp = new ECContentFp(parent, SWT.NONE, this);
        contentFm = new ECContentFm(parent, SWT.NONE, this);
        contentLarge = new ECContentLarge(parent, SWT.NONE, this);

        layout.topControl = contentReal;

        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
                "org.jcryptool.visual.ecc.view"); //$NON-NLS-1$

        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        log(Messages.ECView_LogHeader1 + c.get(Calendar.DATE) + "-" + (c.get(Calendar.MONTH) + 1) + "-" 
        		+ c.get(Calendar.YEAR) + " " + Messages.ECView_LogHeader2 
        		+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ 
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    @Override
    public void setFocus() {
        parent.setFocus();
    }

    public void showReal() {
        if (!layout.topControl.equals(contentReal)) {
            contentReal.adjustButtons();
            layout.topControl = contentReal;
            parent.layout();
        }
    }

    public void showFp() {
        if (!layout.topControl.equals(contentFp)) {
            contentFp.adjustButtons();
            layout.topControl = contentFp;
            parent.layout();
        }
    }

    public void showFm() {
        if (!layout.topControl.equals(contentFm)) {
            contentFm.adjustButtons();
            layout.topControl = contentFm;
            parent.layout();
        }
    }

    public void showLarge() {
        if (!layout.topControl.equals(contentLarge)) {
            contentLarge.adjustButtons();
            layout.topControl = contentLarge;
            parent.layout();
        }
    }

    /**
     * Save the 
     * @param s
     */
	public void log(String s) {
		
		// Save the new log content to the log file
		log += s + "\n"; //$NON-NLS-1$
		saveToFile();
		log = ""; //$NON-NLS-1$
		
		// Check if the log is shown in an editor and udpate the file.
		updateEditorContent();
		
	}
	
	private void updateEditorContent() {
		if (logFile == null) {
			createLogFile();
		}
		
		if (editorPage == null) {
			editorPage = getSite().getPage();
		}
		
		// Close a propably already opened editor with the same name.
		IEditorReference[] er = editorPage.getEditorReferences();
		for (int i = 0; i < er.length; i++) {
			// Only reopen (update) the editor content, if an editor with the same name 
			// as the logfile is opened
			if (er[i].getName().equals(getLogFileName())) { 
				// Close the editor
				er[i].getEditor(false).getSite().getPage().closeEditor(er[i].getEditor(false), false);
				
				// Open the editor
				try {
					IPath location = new Path(getLogFileLocation());
					editorPage.openEditor(new PathEditorInput(location), "org.jcryptool.editor.text.editor.JCTTextEditor", false); //$NON-NLS-1$
				} catch (PartInitException e) {
					LogUtil.logError(ECCPlugin.PLUGIN_ID, e);
				}
			}
		}
		
		
	}
	
	public void openLogFileInEditor() {
		if (logFile == null) {
			createLogFile();
		}
		
		if (editorPage == null) {
			editorPage = getSite().getPage();
		}
		
		// Close a propably already opened editor with the same name.
		IEditorReference[] er = editorPage.getEditorReferences();
		for (int i = 0; i < er.length; i++) {
			if (er[i].getName().equals(getLogFileName())) { 
				er[i].getEditor(false).getSite().getPage().closeEditor(er[i].getEditor(false), false);
			}
		}
		
		// Open the log in the editor.
		try {
			IPath location = new Path(getLogFileLocation());
			editorPage.openEditor(new PathEditorInput(location), "org.jcryptool.editor.text.editor.JCTTextEditor"); //$NON-NLS-1$

		} catch (PartInitException e) {
			LogUtil.logError(ECCPlugin.PLUGIN_ID, e);
		}
	}



//    private void saveToEditor() {
//        if (logFile == null) {
//            logFile = new File(new File(DirectoryService.getTempDir()), "calculations.txt"); //$NON-NLS-1$ 
//            logFile.deleteOnExit();
//        }
//
//        saveToFile();
//
//        if (editorPage == null)
//            editorPage = getSite().getPage();
//
//        IEditorReference[] er = editorPage.getEditorReferences();
//        for (int i = 0; i < er.length; i++) {
//            if (er[i].getName().equals("calculations.txt")) { //$NON-NLS-1$
//                er[i].getEditor(false).getSite().getPage().closeEditor(er[i].getEditor(false),
//                        false);
//            }
//        }
//
//        try {
//            IPath location = new Path(logFile.getAbsolutePath());
//            editorPage.openEditor(new PathEditorInput(location),
//                    "org.jcryptool.editor.text.editor.JCTTextEditor"); //$NON-NLS-1$
//        } catch (PartInitException e) {
//            LogUtil.logError(ECCPlugin.PLUGIN_ID, e);
//        }
//    }
	
	/**
	 * Creates the log file if it does not exist already
	 */
	private void createLogFile() {
    	if (logFile == null) {
    		
    		// Create ECC directory in workspace
    		File logFileDirectory = new File(DirectoryService.getWorkspaceDir() + "/ECC/");
    		logFileDirectory.mkdir();
    		
    		// Create a timestamp and add it to the filename.
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-'at'-HH-mm");
    		Date date = new Date(System.currentTimeMillis());
    		logFile = new File(DirectoryService.getWorkspaceDir() + "/ECC/ECC-history-from-" + formatter.format(date) + ".txt");
    		try {
				logFile.createNewFile();
			} catch (IOException e) {
				LogUtil.logError(ECCPlugin.PLUGIN_ID, e);
			}
    	}
	}
	
	public String getLogFileLocation() {
		if (logFile == null) {
			createLogFile();
		}
		return logFile.getAbsolutePath();
	}
	
	public String getLogFileName() {
		if (logFile == null) {
			createLogFile();
		}
		return logFile.getName();
	}

	/**
	 * Save the log to the logifle.
	 */
	private void saveToFile() {

		// If the log file does not exist, delete it.
		if (logFile == null) {
			createLogFile();
		}

		try {
			String[] sa = log.split("\n"); //$NON-NLS-1$
			if (sa.length > 1 || !sa[0].equals("")) { //$NON-NLS-1$
				FileWriter fw = new FileWriter(logFile, true);
				BufferedWriter bw = new BufferedWriter(fw);
				for (int i = 0; i < sa.length; i++) {
					if (i < sa.length - 1 || (i == sa.length - 1 && !sa[i].equals(""))) { //$NON-NLS-1$
						bw.write(sa[i]);
						bw.newLine();
					}
				}
				bw.close();
				fw.close();
			}
		} catch (Exception ex) {
			LogUtil.logError(ECCPlugin.PLUGIN_ID, ex);
		}
	}


	public void reset() {
		Control[] children = parent.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		createPartControl(parent);
		parent.layout();
	}
    
    
    
}