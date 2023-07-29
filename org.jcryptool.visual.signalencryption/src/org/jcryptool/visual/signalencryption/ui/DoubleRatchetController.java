package org.jcryptool.visual.signalencryption.ui;

import static org.jcryptool.visual.signalencryption.communication.CommunicationEntity.ALICE;
import static org.jcryptool.visual.signalencryption.ui.DoubleRatchetController.Direction.BACKWARD;
import static org.jcryptool.visual.signalencryption.ui.DoubleRatchetController.Direction.FORWARD;

import org.jcryptool.visual.signalencryption.communication.CommunicationEntity;
import org.jcryptool.visual.signalencryption.ui.DoubleRatchetAliceSendingLogic.AliceSendingStep;

/**
 * A state machine that manages the DoubleRatchet steps and provides the
 * interface to advance/go back. This class is a central component of
 * coordinating the UI and its interfaces to the algorithm.
 */
public class DoubleRatchetController {

    private DoubleRatchetStep currentStep;

    public DoubleRatchetController(DoubleRatchetView swtParent) {
        currentStep = AliceSendingStep.STEP_0;
        currentStep.switchState(swtParent);
    }

    /**
     * Advances to the next step. Automatically changes the entity if end of one
     * message encryption/decryption cycle is reached. Does a view change instead of
     * a step forward if the incorrect view is currently showing.
     */
    public void stepForward(DoubleRatchetView swtParent) {
        if (isShowingCorrectView(swtParent, FORWARD) || stateChangeIncludesViewChange(FORWARD)) {
            currentStep = currentStep.next(swtParent);
        } else {
            showCorrectView(swtParent, FORWARD);
        }
    }

    /**
     * Goes back a step. Automatically changes the entity if beginning of one
     * message encryption/decryption cycle is reached. Does a view change instead of
     * a step forward if the incorrect view is currently showing. Calling this if
     * already at the initial step 0 is safe and does nothing.
     */
    public void stepBack(DoubleRatchetView swtParent) {
        if (isShowingCorrectView(swtParent, BACKWARD) || stateChangeIncludesViewChange(BACKWARD)) {
            currentStep = currentStep.back(swtParent);
        } else {
            showCorrectView(swtParent, BACKWARD);
        }
    }

    /**
     * Sets back the DoubleRatchet viz to its initial state. This may not include
     * all algorithmic resets necessary
     */
    public void reset(DoubleRatchetView swtParent) {
        currentStep = AliceSendingStep.STEP_0.setInitialState(swtParent);
    }

    /** Returns the step of the current state */
    public DoubleRatchetStep getCurrentStep() {
        return currentStep;
    }

    /**
     * Check if the state change into direction causes a natural view change. This
     * more or less means: next-button becomes showAliceView/showBobView button
     */
    private boolean stateChangeIncludesViewChange(Direction direction) {
        if (direction == BACKWARD) {
            return currentStep.requiresViewSwitchBackward();
        } else {
            return currentStep.requiresViewSwitchForward();
        }
    }

    /**
     * Some states only differ if they show Alice or Bob - switch state if the user
     * manually switches
     * <p/>
     * Example:
     *
     * <pre>
     *    Reaching end of alice view (next-button would switch to bob's view)
     *    User manually switches to bob view (next-button logic switches to showing next step of bob)
     * </pre>
     */
    public void doStateChangeIfRequiredByViewChange(DoubleRatchetView swtParent, CommunicationEntity showingEntity) {
        if (currentStep.requiresViewSwitchForward() && currentStep.peekForward().shouldShowEntity() == showingEntity) {
            stepForward(swtParent);
        }
        if (currentStep.requiresViewSwitchBackward()
                && currentStep.peekBackward().shouldShowEntity() == showingEntity) {
            stepBack(swtParent);
        }
    }

    private boolean isShowingCorrectView(DoubleRatchetView view, Direction direction) {
        return direction.peek(currentStep).shouldShowEntity() == view.getCurrentlyShowingEntity();
    }

    private void showCorrectView(DoubleRatchetView swtParent, Direction direction) {
        switchViewTo(direction.peek(currentStep).shouldShowEntity(), swtParent);
    }

    public static enum Direction {
        FORWARD {
            @Override
            DoubleRatchetStep peek(DoubleRatchetStep current) {
                return current.peekForward();
            }
        },
        BACKWARD {
            @Override
            DoubleRatchetStep peek(DoubleRatchetStep current) {
                return current.peekBackward();
            }
        };

        abstract DoubleRatchetStep peek(DoubleRatchetStep current);
    }

    private void switchViewTo(CommunicationEntity entity, DoubleRatchetView view) {
        if (entity == ALICE) {
            view.showAliceView();
        } else {
            view.showBobView();
        }
    }

}
