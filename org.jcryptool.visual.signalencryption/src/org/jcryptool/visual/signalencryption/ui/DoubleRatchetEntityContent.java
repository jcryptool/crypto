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
    
    Composite buildStepsContent(Composite parent);
    Composite buildAlgorithmContent(Composite parent);
    void showStep(DoubleRatchetStep step);
    default void showStep(DoubleRatchetStep step, List<StyledText> stepDescriptions, int highlightLength) {
		int lastVisibleStep = step.getStepIndex();
		for (int i = 0; i < stepDescriptions.size(); ++i) {
			StyledText description = stepDescriptions.get(i);
			if (i < lastVisibleStep) {
				description.setVisible(true);
			} else if (i == lastVisibleStep) {
				System.out.println("Applying style to idx " + i);
				description.setStyleRange(new StyleRange(0, lastVisibleStep, ColorService.getColor(SWT.FOREGROUND), ColorService.getColor(SWT.BACKGROUND), SWT.BOLD));
			} else {
				description.setVisible(false);
			}
		}
	}

}