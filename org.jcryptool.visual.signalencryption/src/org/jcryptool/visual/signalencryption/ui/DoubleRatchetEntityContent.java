package org.jcryptool.visual.signalencryption.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.core.util.colors.ColorService;

/**
 * Provide DoubleRatchet UI elements for a Signal communication entity (e.g. Alice or Bob).
 * 
 * Since entities have to show different content when sending than when receiving, this
 * interface allows a unified approach to get the co
 *
 */
public interface DoubleRatchetEntityContent {
	
	static final int RECEIVING_STEP_OFFSET = 5;
	static final int highlightLength = (Messages.DoubleRatchet_Step + " X").length();
	static final StyleRange stepBold = new StyleRange(0, highlightLength, null, null, SWT.BOLD);
	static final StyleRange stepNormal = new StyleRange(0, highlightLength, null, null, SWT.NORMAL);
    
    Composite buildStepsContent(Composite parent);
    
    Composite buildAlgorithmContent(Composite parent);
    
    void showStep(DoubleRatchetStep step);
    
    default void showStep(DoubleRatchetStep step, List<StyledText> stepDescriptions, int offset) {
    	// Last visible step if offset by one, as STEP_0 does not show a step description
		int lastVisibleStep = step.getStepIndex() - 1;
		for (int i = offset; i < stepDescriptions.size() + offset; ++i) {
			StyledText description = stepDescriptions.get(i - offset);
			if (i < lastVisibleStep) {
				description.setVisible(true);
				description.setStyleRange(stepNormal);
			} else if (i == lastVisibleStep) {
				description.setVisible(true);
				description.setStyleRange(stepBold);
			} else {
				description.setVisible(false);
			}
		}
	}

}