package org.jcryptool.visual.signalencryption.ui;

import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;

/**
 * A step in the UI logic of the Double Ratchet visualization.
 */
public interface DoubleRatchetStep {

    /** What's the index of steps switching to the receiving side */
    static int INDEX_OF_RECEIVING_STEPS = 4;

    /**
     * Advance one step, e.g. when the user clicks the 'next' button.
     * 
     * @param swtParent The UI description where the visual changes for this step
     *                  should be applied
     * @return the next step
     */
    default DoubleRatchetStep next(DoubleRatchetView swtParent) {
        peekForward().switchState(swtParent);
        return peekForward();
    }

    /**
     * Go back one step, e.g. when the user clicks the 'back' button
     * 
     * @param swtParent The UI description where the visual changes for this step
     *                  should be applied
     * @return the previous step
     */
    default DoubleRatchetStep back(DoubleRatchetView swtParent) {
        peekBackward().switchState(swtParent);
        return peekBackward();
    }

    void switchState(DoubleRatchetView swtParent);

    /** Returns the next step without taking any action */
    DoubleRatchetStep peekForward();

    /** Returns the previous step without taking any action */
    DoubleRatchetStep peekBackward();

    /**
     * For the given step, which entity (Alice or Bob) should be shown on screen?
     */
    CommunicationEntity shouldShowEntity();

    int getStepIndex();

    /** If the step requires a view-change when going forward */
    default boolean requiresViewSwitchForward() {
        return peekForward().shouldShowEntity() != this.shouldShowEntity();
    }

    /** If the step requires a view-change when going forward */
    default boolean requiresViewSwitchBackward() {
        return peekBackward().shouldShowEntity() != this.shouldShowEntity();
    }
}
