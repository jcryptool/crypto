package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.widgets.Composite;

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

}