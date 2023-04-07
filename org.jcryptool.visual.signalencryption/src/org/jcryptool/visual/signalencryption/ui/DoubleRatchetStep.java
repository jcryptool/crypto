package org.jcryptool.visual.signalencryption.ui;

import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;

/**
 * A step in the UI logic of the Double Ratchet visualization.
 */
public interface DoubleRatchetStep {
    /**
     * Advance one step, e.g. when the user clicks the 'next' button.
     * 
     * @param swtParent The UI description where the visual changes for this step should be applied
     * @return the next step
     */
    DoubleRatchetStep next(DoubleRatchetView swtParent);
    /**
     * Go back one step, e.g. when the user clicks the 'back' button
     * 
     * @param swtParent The UI description where the visual changes for this step should be applied
     * @return the previous step
     */
    DoubleRatchetStep back(DoubleRatchetView swtParent);
    
    /** Returns the next step without taking any action */
    DoubleRatchetStep peekForward();

    /** Returns the previous step without taking any action */
    DoubleRatchetStep peekBackward();
    
    /** For the given step, which entity (Alice or Bob) should be shown on screen? */
    CommunicationEntity shouldShowThisEntity();
}
