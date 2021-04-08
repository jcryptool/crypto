package org.jcryptool.visual.rss.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;
import org.jcryptool.visual.rss.ui.RssVisualDataComposite.DataType;

/**
 * Depending on the current state of the visual shows the correct Component, which extends RssRightSideComposite.
 * This shown component then contains the available buttons in the middle section and sets the help text on the right side.
 * 
 * @author Leon Shell, Lukas Krodinger
 */
public class RssBodyComposite extends Composite {
    private final RssBaseComposite base;
    private final RssAlgorithmController rac;
    private ActiveRssBodyComposite activeRssBodyComposite;
    private Composite activeBodyInstance;
    private static final ActiveRssBodyComposite persistentActiveBodyComposites[]
            = { ActiveRssBodyComposite.VIEW_KEY,
                ActiveRssBodyComposite.VIEW_MESSAGE,
                ActiveRssBodyComposite.VIEW_REDACTED };
    private final Map<ActiveRssBodyComposite, Composite> persistentInstances
            = new EnumMap<>(ActiveRssBodyComposite.class);
    private final StackLayout layout;

    public RssBodyComposite(RssBaseComposite base, RssAlgorithmController rac) {
        super(base, SWT.NONE);
        this.base = base;
        this.rac = rac;
        layout = new StackLayout();
        setLayout(layout);
        setActiveRssComposite(ActiveRssBodyComposite.SET_KEY);
    }

    public void resetStateOverview(DataType type) {
        resetStateOverview(type, true);
    }
    
    public void resetStateOverview(DataType type, boolean reset) {
        base.resetStateOverview(type, reset);
    }
    
    public void returnToCurrentState() {
        base.returnToCurrentState();
        layout.topControl = activeBodyInstance;
        layout();
        base.updateScrollSize();
    }
    
    public void resetState(DataType type) {
        List<DataType> reverseDataTypeList = Arrays.asList(DataType.values());
        Collections.reverse(reverseDataTypeList);
        for (DataType t : reverseDataTypeList) {
            persistentInstances.remove(t.getComposite());
            if (t == type) {
                break;
            }
        }
    }

    public void setActiveRssComposite(ActiveRssBodyComposite activeRssBodyComposite) {
        setActiveRssComposite(activeRssBodyComposite, false);
    }
    
    public void setActiveRssComposite(ActiveRssBodyComposite activeRssBodyComposite, boolean forceReload) {
        if (!Arrays.asList(persistentActiveBodyComposites).contains(activeRssBodyComposite)) {
            base.updateOverview();
        }
        if ((forceReload || this.activeRssBodyComposite != activeRssBodyComposite)
            && !persistentInstances.containsKey(activeRssBodyComposite)) {
            switch (activeRssBodyComposite) {
            case SET_KEY: activeBodyInstance = new RssSetKeyComposite(this, rac); break;
            case VIEW_KEY:
                persistentInstances.put(activeRssBodyComposite, new RssViewKeyComposite(this, rac));
                break;
            case SET_MESSAGE: activeBodyInstance = new RssSetMessageComposite(this, rac); break;
            case SIGN_MESSAGE: activeBodyInstance = new RssSignMessageComposite(this, rac); break;
            case VERIFY_MESSAGE: activeBodyInstance = new RssVerifyMessageComposite(this, rac); break;
            case VIEW_MESSAGE:
                persistentInstances.put(activeRssBodyComposite, new RssViewMessageComposite(this, rac));
                break;
            case REDACT: activeBodyInstance = new RssRedactComposite(this, rac); break;
            case VERIFY_REDACTED: activeBodyInstance = new RssVerifyRedactedComposite(this, rac); break;
            case VIEW_REDACTED:
                persistentInstances.put(activeRssBodyComposite, new RssViewRedactedComposite(this, rac));
                break;
            default:
                throw new UnsupportedOperationException("");
            }
        }
        if (Arrays.asList(persistentActiveBodyComposites).contains(activeRssBodyComposite)) {
            layout.topControl = persistentInstances.get(activeRssBodyComposite);
        } else {
            this.activeRssBodyComposite = activeRssBodyComposite;
            layout.topControl = activeBodyInstance;
        }
        layout();
        base.updateScrollSize();
    }
    
    public void lightPath() {
        base.lightPath();
    }
    
    public void lightDataBox(DataType type) {
        base.lightDataBox(type);
    }
    
    public void activateDataBox(DataType type) {
        base.activateDataBox(type);
    }
    
    public void updateBodyComposite(Composite composite) {
        activeBodyInstance = composite;
        layout.topControl = activeBodyInstance;
        layout();
        pack();
    }
    
    public enum ActiveRssBodyComposite {
        SET_KEY("Set Key"),
        VIEW_KEY("View Key Material"),
        SET_MESSAGE("New Message"),
        SIGN_MESSAGE("Sign Message"),
        VIEW_MESSAGE("View Signed Message"),
        VERIFY_MESSAGE("Verify Signed Message"),
        REDACT("Redact Signed Message"),
        VIEW_REDACTED("View Redacted Messge"),
        VERIFY_REDACTED("Verify Redacted Message");
        
        private final String name;
        
        ActiveRssBodyComposite(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
    }

}
