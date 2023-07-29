package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.images.ImageService;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;
import org.jcryptool.visual.signalencryption.util.UiUtils;

/**
 * The main component of the flowchart. A node with a title and a button, which
 * shows a pop-up when clicked. This class requires two async execution hooks
 * for the smooth pop-up opening and closing.
 */
public class FlowChartNode extends Composite {

    private static final int POPUP_STYLE = SWT.MODELESS | SWT.SHADOW_ETCHED_OUT | SWT.BORDER;
    private static final Image BUTTON_IMAGE = ImageService.getImage(SignalEncryptionView.ID, "icons/searchIcon.png");
    private static final String GEAR_ICON_NAME = UiUtils.isDarkTheme() ? "icons/gear_light.png" : "icons/gear_dark.png";
    private static final Image OPERATION_IMAGE = ImageService.getImage(SignalEncryptionView.ID, GEAR_ICON_NAME);
    /** The spacing between the gear symbol and the lense icon */
    private static final int HORIZONTAL_SPACING = 17;
    /**
     * How long to keep the lense-button disabled when the window is closing
     * (prevents immediate re-opening)
     */
    private static final long BUTTON_DISABLE_TIME_IN_MS = 80;
    /**
     * How long to block closing of the pop-up after gaining the focus.
     * <p>
     * Compared to {@link #DISPOSE_AFTER_MS} this value should be enough long that
     * both focusGained and focusLost events can fire, but not too long to make it
     * unresponsive.
     */
    private static final long PREVENT_CLOSING_FOR_MS = 50;
    private static final long DISPOSE_AFTER_MS = 15;

    static {
        // Make definitely sure that the pop-up closes before enable the open-button
        // again
        assert BUTTON_DISABLE_TIME_IN_MS > DISPOSE_AFTER_MS;
    }

    private final Shell parentShell;
    private final String title;
    private Text txt_title;
    private Button btn_showPopup;
    private boolean showing;
    private Shell popup;
    private FlowChartNodePopup popupProvider;

    private static Object focusSwitchLock = new Object();
    private static Object reopenButtonLock = new Object();
    private boolean allowOpen = true;
    private boolean allowClose = true;

    private FlowChartNode(
            Composite parent, int style,
            String title,
            String popupTooltip,
            FlowChartNodePopup popupProvider,
            Type type) {
        super(parent, style);
        this.popupProvider = popupProvider;
        this.parentShell = getShell();
        this.setBackground(ColorService.getColor(SWT.COLOR_LIST_BACKGROUND));
        this.title = title;

        if (type == Type.VALUE) {
            createValueBody();
        } else {
            createOperationBody();
        }

        if (popupTooltip != null && !popupTooltip.isEmpty()) {
            btn_showPopup.setToolTipText(popupTooltip);
        }

        btn_showPopup.addSelectionListener(showPopupListener());
    }

    private void createValueBody() {
        setLayout(new GridLayout(1, true));
        txt_title = new Text(this, SWT.NONE);
        txt_title.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
        txt_title.setText(title);

        btn_showPopup = new Button(this, SWT.TOGGLE);
        btn_showPopup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
        btn_showPopup.setImage(BUTTON_IMAGE);
    }

    private void createOperationBody() {
        var layout = new GridLayout(2, true);
        layout.horizontalSpacing = HORIZONTAL_SPACING;
        setLayout(layout);
        txt_title = new Text(this, SWT.NONE);
        txt_title.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
        txt_title.setText(title);

        var lbl_gears = new Label(this, SWT.NONE);
        lbl_gears.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
        lbl_gears.setImage(OPERATION_IMAGE);

        btn_showPopup = new Button(this, SWT.TOGGLE);
        btn_showPopup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        btn_showPopup.setImage(BUTTON_IMAGE);
    }

    public FlowChartNodePopup getPopupProvider() {
        return this.popupProvider;
    }

    public String getTitle() {
        return title;
    }

    /**
     * When focus is lost from component, close. Do nothing if focus stays in
     * component (can e.g. switch to other child)
     */
    public FocusListener closeOnFocusLose() {
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Prevent immediate re-opening of window if focus is lost to the
                // show-pop-up-button
                // (in that case the window would close and immediately re-open, which is not
                // what the user expects)
                allowOpening(false);
                Display.getDefault().asyncExec(() -> {
                    sleep(BUTTON_DISABLE_TIME_IN_MS);
                    allowOpening(true);
                });
                // Wait for some time and then attempt to close.
                // If in the meantime we realize that we actually kept the focus
                // (that means another child received focusGained), disposePopup() will do
                // nothing.
                Display.getDefault().asyncExec(() -> {
                    sleep(DISPOSE_AFTER_MS);
                    disposePopup();
                });
            }

            @Override
            public void focusGained(FocusEvent e) {
                // After we gain focus, prevent that the window will be closed by the focusLost
                // event for some time.
                // This indicates that the focus only „switched“ within the pop-up and did not
                // go outside.
                // This time allows the close-handler to figure out if it should actually
                // close (focusLost) or not (focusSwitched)
                allowClosing(false);
                Display.getDefault().asyncExec(() -> {
                    sleep(PREVENT_CLOSING_FOR_MS);
                    allowClosing(true);
                });
            }

        };
    }

    private SelectionAdapter showPopupListener() {
        return new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                synchronized (reopenButtonLock) {
                    // If the user clicks the button while it's still internally disabled, just set
                    // the button
                    // manually to not-pressed state and continue on. The internal disable happens,
                    // when the
                    // pop-up just closed due to a focus loss.
                    if (!allowOpen) {
                        btn_showPopup.setSelection(false);
                    } else if (!showing && allowOpen) {
                        showPopup();
                        btn_showPopup.setSelection(true);
                    }
                }
                // We do not need to cover the close event here, since that will happen on any
                // focus-lost event.
            }
        };
    }

    private void showPopup() {
        popup = new Shell(parentShell, POPUP_STYLE);
        popup.addFocusListener(closeOnFocusLose());

        var layout = new GridLayout();
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;

        popup.setLayout(layout);
        popup.setText(title);
        var baseComposite = new Composite(popup, SWT.NONE);
        baseComposite.setLayout(new GridLayout(1, true));
        baseComposite.setLayoutData(GridDataBuilder
                .with(SWT.FILL, SWT.FILL, true, true)
                .minimumHeight(ViewConstants.POPUP_MIN_HEIGHT)
                .minimumWidth(ViewConstants.POPUP_MIN_WIDTH)
                .get());
        popupProvider.apply(baseComposite, SWT.NONE);
        popup.forceActive();
        popup.forceFocus();
        popup.pack();
        setCenterLocation(popup);
        popup.open();
        showing = true;
        recursivelyAddFocusListener(popup);

        popup.addDisposeListener((e) -> {
            showing = false;
            btn_showPopup.setSelection(false);
        });
    }

    /** Adds a close-on-lose-focus listener to each child of the composite. */
    private void recursivelyAddFocusListener(Composite x) {
        var children = x.getChildren();
        for (var child : children) {
            if (child instanceof Composite) {
                recursivelyAddFocusListener((Composite) child);
            }
            child.addFocusListener(closeOnFocusLose());
        }
    }

    private void disposePopup() {
        synchronized (focusSwitchLock) {
            if (allowClose) {
                popup.dispose();
            }
        }
    }

    private void setCenterLocation(Shell shell) {
        var parentLocation = parentShell.getLocation();
        var parentSize = parentShell.getSize();
        var shellSize = shell.getSize();
        var x = parentLocation.x + (parentSize.x / 2) - (shellSize.x / 2);
        var y = parentLocation.y + (parentSize.y / 2) - (shellSize.y / 2);
        shell.setLocation(x, y);
    }

    private void allowOpening(boolean allow) {
        synchronized (reopenButtonLock) {
            allowOpen = allow;
        }
    }

    private void allowClosing(boolean allow) {
        synchronized (focusSwitchLock) {
            allowClose = allow;
        }
    }

    /** Sleeps „unsafely“, but we don't care about being interrupted */
    private void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ignored) {
        }
    }

    public static class Builder {

        public Builder(Composite parent) {
            this.parent = parent;
        }

        private Composite parent;
        private int style = SWT.BORDER;
        private String title = "NO TITLE";
        private String actionName = "";
        private FlowChartNodePopup popupProvider = FlowChartNodePopup.empty();

        public Builder style(int style) {
            this.style = style;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder actionName(String actionName) {
            this.actionName = actionName;
            return this;
        }

        public Builder popupProvider(FlowChartNodePopup popupProvider) {
            this.popupProvider = popupProvider;
            return this;
        }

        public FlowChartNode operationNode() {
            return new FlowChartNode(parent, style, title, actionName, popupProvider, Type.OPERATION);
        }

        public FlowChartNode valueNode() {
            return new FlowChartNode(parent, style, title, actionName, popupProvider, Type.VALUE);
        }

    }

    public static enum Type {
        OPERATION, VALUE
    }

}
