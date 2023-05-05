package org.jcryptool.visual.signalencryption.ui;

import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;

/** A step in the visualization of the Double Ratchet algorithm containing necessary UI and algorithm updates. */
public interface DoubleRatchetStep {

    /** What's the index of steps switching to the receiving side */
    static int INDEX_OF_RECEIVING_STEPS = 4;

    /**
     * Advances one step, e.g. when the user clicks the 'next' button.
     * Updates all UI elements of {@code swtParent} accordingly and does necessary algorithm state changes.
     * The method can be overriden to hook additional roll-over logic.
     *
     * @return the next step
     */
    default DoubleRatchetStep next(DoubleRatchetView swtParent) {
        peekForward().switchState(swtParent);
        return peekForward();
    }

    /**
     * Goes back one step, e.g. when the user clicks the 'back' button.
     * Updates all UI elements of {@code swtParent} accordingly and does necessary algorithm state changes.
     * The method can be overriden to hook additional roll-over logic.
     *
     * @return the previous step
     */
    default DoubleRatchetStep back(DoubleRatchetView swtParent) {
        peekBackward().switchState(swtParent);
        return peekBackward();
    }

    /**
     * Switches itself to the active state and updates all UI elements accordingly.
     * May perform algorithmic "glue-logic" as well.
     */
    void switchState(DoubleRatchetView swtParent);

    /** Returns the next step without taking any action */
    DoubleRatchetStep peekForward();

    /** Returns the previous step without taking any action */
    DoubleRatchetStep peekBackward();

    /** Returns the communication entity (Alice/Bob) which should be visible so the user can see the step happening. */
    CommunicationEntity shouldShowEntity();

    /** Returns the step's logical index (some steps may be  different but share the same index). */
    int getStepIndex();

    /** Returns true if the step requires a view-change when going forward (e.g. switching from sender to receiver). */
    default boolean requiresViewSwitchForward() {
        return peekForward().shouldShowEntity() != this.shouldShowEntity();
    }

    /** If the step requires a view-change when going backward (e.g. switching from receiver to sender). */
    default boolean requiresViewSwitchBackward() {
        return peekBackward().shouldShowEntity() != this.shouldShowEntity();
    }
}
