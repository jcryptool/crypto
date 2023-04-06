package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Provide DoubleRatchet UI elements for a Signal communication entity (e.g. Alice or Bob).
 * 
 * Since entities have to show different content when sending than when receiving, this
 * interface allows a unified approach to get the co
 *
 */
public interface DoubleRatchetEntityContent {
    
    Composite buildStepsContent(Composite parent, COMMUNICATION_STATE state);
    Composite buildAlgorithmContent(Composite parent, COMMUNICATION_STATE state);

}

enum COMMUNICATION_STATE {
    INITIALISING,
    NORMAL;
}
