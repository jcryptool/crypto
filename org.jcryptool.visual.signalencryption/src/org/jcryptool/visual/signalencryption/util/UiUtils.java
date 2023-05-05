package org.jcryptool.visual.signalencryption.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;

public class UiUtils {

    private static enum Debug {
        YES, NO
    }

    private UiUtils() {
        // Private constructor to prevent instantiation of this class
    }

    /**
     * Insert invisible spacing widgets into the composite container.
     *
     * Can be used for GridLayouts to fill into "empty" slots in the grid.
     *
     * @param parent widget to attach spacers to
     * @param number number of spacers (grid cells) to fill
     */
    public static void insertSpacers(Composite parent, int number) {
        for (int i = 0; i < number; ++i) {
            var label = new Label(parent, SWT.NONE);
            label.setVisible(false);
        }
    }

    /**
     * Insert invisible spacing widgets into the parent composite which use the given widthHint.
     * As this is no minimumWidth, the widget could actually become smaller, has it has no actual size.
     * However, on the upside this definitely does not take more space than the widthHint.
     */
    public static void insertNonGreedySpacer(Composite parent, int number, int widthHint) {
        var layout = GridDataBuilder.with(SWT.FILL, SWT.FILL, false, false, 1, 1).widthHint(widthHint).get();
        insert(parent, number, layout);
    }

    /** Insert invisible spacing widgets into the parent composite which try to grab excessive space greedily  */
    public static void insertGreedySpacers(Composite parent, int number) {
        var layout = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        insert(parent, number, layout);
    }

    /**
     * Insert invisible spacing widgets into the parent composite with the given minimumWidth specified.
     * The spacer will be at least as large, but maybe actually bigger as it grabs excessive space.
     */
    public static void insertSpacers(Composite parent, int number, int minimumWidth) {
        var layout = GridDataBuilder.with(SWT.LEFT, SWT.FILL, true, false).minimumWidth(minimumWidth).get();
        insert(parent, number, layout, Debug.NO);
    }

    /**
     * Insert invisible spacing widgets into the parent composite with the given minimumWidth and Height specified.
     * The spacer will be at least as large, but maybe actually bigger as it grabs excessive space.
     */
    public static void insertSpacers(Composite parent, int number, int minimumWidth, int minimumHeight) {
        var layout = GridDataBuilder.with(SWT.LEFT, SWT.TOP, true, true).minimumWidth(minimumWidth)
                .minimumHeight(minimumHeight).get();
        insert(parent, number, layout);
    }

    private static void insert(Composite parent, int number, GridData layout) {
        insert(parent, number, layout, Debug.NO);
    }

    private static void insert(Composite parent, int number, GridData layout, Debug debug) {
        for (int i = 0; i < number; ++i) {
            var label = new Label(parent, SWT.NONE);
            label.setLayoutData(layout);

            // In case you have layout problems, this flag can be toggled to actually show the label
            if (debug == Debug.YES) {
                label.setBackground(ColorService.LIGHT_AREA_BLUE);
            } else {
                label.setVisible(false);
            }
        }
    }

    /**
     * Allows to register a widgetSelected listener with a lambda function:
     * {@code primeButton.addSelectionListener(onSelection(e -> calculatePrim());}
     */
    public static SelectionAdapter onSelection(SelectionHandler handler) {

        return new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                handler.on(e);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing as this is almost never required. If needed use a full-fledged SelectionAdapter
            }

        };
    }

    /** Used in {@link UiUtils#onSelection(SelectionHandler)} */
    @FunctionalInterface
    public interface SelectionHandler {

        public void on(SelectionEvent e);
    }

}
