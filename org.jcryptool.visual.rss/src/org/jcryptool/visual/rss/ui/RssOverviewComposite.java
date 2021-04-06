package org.jcryptool.visual.rss.ui;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController.State;
import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;
import org.jcryptool.visual.rss.ui.RssPathComposite.PathColor;
import org.jcryptool.visual.rss.ui.RssPathComposite.PathType;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

public class RssOverviewComposite extends Composite {
    public static final int STATE_BOX_X_PADDING = 20;
    public static final int STATE_BOX_Y_PADDING = 10;

    // So label wont cut off last character when setting font style to bold.
    public static final int LABEL_EXTRA_WIDTH = 20;

    private final RssBaseComposite base;
    private final RssAlgorithmController rac;
    private State activeState;
    
    private final Map<State, RssVisualStateComposite> visualStates = new EnumMap<>(State.class);
    private final Map<State, RssPathComposite> pathToStates = new EnumMap<>(State.class);
    private final List<State> visualizeableStates = Arrays.asList(State.values()).subList(0, State.values().length - 1);
    private final List<RssPathComposite> redactAgainPaths = new LinkedList<>();
    
    private final RssVisualDataComposite keyData;
    private final RssVisualDataComposite messageData;
    private final RssVisualDataComposite redactedData;
    
    /**
     * Create the composite. Including Descriptions, Seed (,Bitmask) and Key
     * 
     * @param base
     *        Parent Composite
     * @param masterView
     *        Plugin-Main-Composite
     */
    public RssOverviewComposite(Composite parent, RssBaseComposite base, final RssAlgorithmController rac) {
        super(parent, SWT.DOUBLE_BUFFERED);

        this.base = base;
        this.rac = rac;
        
        setLayout(new GridLayout(2, false));

        Composite leftSide = new Composite(this, SWT.NONE);
        Composite rightSide = new Composite(this, SWT.NONE);
        
        GridLayout leftGridLayout = new GridLayout(2, false);
        leftGridLayout.horizontalSpacing = 0;
        leftGridLayout.verticalSpacing = 0;
        leftSide.setLayout(leftGridLayout);

        GridLayout rightGridLayout = new GridLayout();
        rightGridLayout.horizontalSpacing = 0;
        rightGridLayout.verticalSpacing *= 10;
        rightSide.setLayout(rightGridLayout);
        
        pathToStates.put(State.START, new RssPathComposite(leftSide));
        Composite spacer = new Composite(leftSide, SWT.NONE);
        GridData spacerGd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 9);
        spacer.setLayoutData(spacerGd);
        visualStates.put(State.START, new RssVisualStateComposite(leftSide, Descriptions.StateKey));

        pathToStates.put(State.KEY_SET, new RssPathComposite(leftSide));
        visualStates.put(State.KEY_SET, new RssVisualStateComposite(leftSide, Descriptions.StateNewMsg));

        pathToStates.put(State.MESSAGE_SET, new RssPathComposite(leftSide));
        visualStates.put(State.MESSAGE_SET, new RssVisualStateComposite(leftSide, Descriptions.StateSignMsg));
        
        pathToStates.put(State.MESSAGE_SIGNED, new RssPathComposite(leftSide));
        visualStates.put(State.MESSAGE_SIGNED, new RssVisualStateComposite(leftSide, Descriptions.StateVerOMsg));

        pathToStates.put(State.MESSAGE_VERIFIED, new RssPathComposite(leftSide));
        visualStates.put(State.MESSAGE_VERIFIED, new RssVisualStateComposite(leftSide, Descriptions.StateRedMsg));
        redactAgainPaths.add(new RssPathComposite(leftSide, PathType.FRL_PATH));

        pathToStates.put(State.PARTS_REDACTED, new RssPathComposite(leftSide));
        redactAgainPaths.add(new RssPathComposite(leftSide));
        visualStates.put(State.PARTS_REDACTED, new RssVisualStateComposite(leftSide, Descriptions.StateVerRMsg));
        redactAgainPaths.add(new RssPathComposite(leftSide));
        
        redactAgainPaths.add(new RssPathComposite(leftSide, PathType.L_PATH));
        redactAgainPaths.add(new RssPathComposite(leftSide, PathType.RL_PATH));

        keyData = new RssVisualDataComposite(rightSide, this, DataType.KEY);
        messageData = new RssVisualDataComposite(rightSide, this, DataType.MESSAGE);
        redactedData = new RssVisualDataComposite(rightSide, this, DataType.REDACTED);
        
        updateOverview();
    }

    public void lightDataBox(DataType type) {
        switch (type) {
        case KEY:
            keyData.setLit();
            break;
        case MESSAGE:
            messageData.setLit();
            break;
        case REDACTED:
            redactedData.setLit();
            break;
        default:
            throw new IllegalArgumentException();
        }
        
    }
    
    public void activateDataBox(DataType type) {
        switch (type) {
        case KEY:
            keyData.setActive();
            break;
        case MESSAGE:
            messageData.setActive();
            break;
        case REDACTED:
            redactedData.setActive();
            break;
        default:
            throw new IllegalArgumentException();
        }
        
    }

    public void updateOverview() {
        activeState = rac.getCurrentState();
        
        for (State state : visualizeableStates) {
            pathToStates.get(state).setColor(PathColor.GREEN);
            visualStates.get(state).setPassive();
            if (state == activeState) {
                visualStates.get(state).setActive();
                break;
            }
        }
    }
    
    public void unfocusAll() {
        keyData.setUnfocused();
        messageData.setUnfocused();
        redactedData.setUnfocused();
        unfocusActiveState();
    }
    
    private void deactivateAll() {
        keyData.setDeactivated();
        messageData.setDeactivated();
        redactedData.setDeactivated();

        for (State state : visualizeableStates) {
            pathToStates.get(state).setColor(PathColor.GREY);
            visualStates.get(state).setDeactivated();
        }
        
        for (RssPathComposite path : redactAgainPaths) {
            path.setColor(PathColor.GREY);
        }
    }
    
    public void focusActiveState() {
        visualStates.get(activeState).setFocused();
    }
    
    public void unfocusActiveState() {
        visualStates.get(activeState).setUnfocused();
    }
    
    public void lightPath() {
        State currentState = rac.getCurrentState();
        if (visualizeableStates.contains(currentState)) {
            pathToStates.get(currentState).setColor(PathColor.GREEN);
            visualStates.get(currentState).setLit();
        } else {
            for (RssPathComposite path : redactAgainPaths) {
                path.setColor(PathColor.GREEN);
            }
        }
    }

    public void setActiveRssBodyComposite(ActiveRssBodyComposite activeRssBodyComposite) {
        base.setActiveRssBodyComposite(activeRssBodyComposite);
    }
    
    public void setActiveRssBodyComposite(ActiveRssBodyComposite activeRssBodyComposite, boolean forceReload) {
        base.setActiveRssBodyComposite(activeRssBodyComposite, forceReload);
    }
    
    public void returnToCurrentState() {
        unfocusAll();
        visualStates.get(activeState).setFocused();
    }

    public void resetState(DataType type) {
        resetState(type, true);
    }
    
    public void resetState(DataType type, boolean reset) {
        base.resetState(type);
        switch (type) {
        case KEY:
            rac.setKeyAgain();
            setActiveRssBodyComposite(ActiveRssBodyComposite.SET_KEY, true);
            deactivateAll();
            updateOverview();
            break;
        case MESSAGE:
            rac.newMessageAgain();
            setActiveRssBodyComposite(ActiveRssBodyComposite.SET_MESSAGE, true);
            deactivateAll();
            updateOverview();
            keyData.setLit();
            break;
        case REDACTED:
            rac.redactAgain(reset);
            setActiveRssBodyComposite(ActiveRssBodyComposite.REDACT, true);
            deactivateAll();
            updateOverview();
            keyData.setLit();
            messageData.setLit();
            break;
        default:
            throw new IllegalStateException();
        }
    }
}
