package org.jcryptool.visual.signalencryption.ui;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;

/**
 * Provides a function which outputs an information Composite based on
 * pre-filled information. This is intended to be used in tandem with the
 * {@link FlowChartNode} and its "lense" icon which opens the popup
 */
public class FlowChartNodePopup implements BiFunction<Composite, Integer, Composite> {

    private List<SimpleEntry<String, String>> keyValuePairs;

    /** Creates a simple pop-up which displays exactly one key-value pair */
    public static FlowChartNodePopup create(String key, String value) {
        return new FlowChartNodePopup(List.of(new SimpleEntry<>(key, value)));
    }

    /**
     * Creates a simple pop-up which displays each item in the given key-value pairs
     * (one per line)
     */
    public static FlowChartNodePopup create(String k1, String v1, String k2, String v2) {
        List<SimpleEntry<String, String>> keyValuePairs = List.of(
                new SimpleEntry<>(k1, v1),
                new SimpleEntry<>(k2, v2)
        );
        return new FlowChartNodePopup(keyValuePairs);
    }

    /** Creates an empty pop-up */
    public static FlowChartNodePopup empty() {
        return new FlowChartNodePopup(Collections.emptyList());
    }

    private FlowChartNodePopup(List<SimpleEntry<String, String>> keyValuePairs) {
        this.keyValuePairs = new ArrayList<>(keyValuePairs); // wrapping in ArrayList should ensure mutability
    }

    /**
     * Updates the value when the next pop-up is created. This method is only
     * applicable when initially created with one key-value pair
     */
    public void setValue(String value) {
        assert keyValuePairs.size() == 1;
        keyValuePairs.set(0, new SimpleEntry<>(keyValuePairs.get(0).getKey(), value));
    }

    /**
     * Sets all the given key-value pairs when the next pop-up is created. Overrides
     * any previously set values
     */
    public void setValues(List<SimpleEntry<String, String>> newKeyValuePairs) {
        this.keyValuePairs = newKeyValuePairs;
    }

    @Override
    public Composite apply(Composite parent, Integer style) {
        var composite = new Composite(parent, style);

        for (var keyValuePair : keyValuePairs) {
            var label = new Label(composite, SWT.NONE);
            var txt = new StyledText(composite, SWT.BORDER | SWT.WRAP);
            txt.setCaret(null);
            txt.setEditable(false);

            var layout = new GridLayout(2, false);

            composite.setLayout(layout);
            composite.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, true).get());
            label.setText(keyValuePair.getKey());
            label.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.CENTER, false, false).get());
            txt.setLayoutData(
                    GridDataBuilder.with(SWT.FILL, SWT.TOP, true, true).widthHint(ViewConstants.POPUP_TEXT_WIDTH).get()
            );
            txt.setFont(FontService.getNormalMonospacedFont());
            txt.setText(keyValuePair.getValue());
        }
        return composite;
    }
}
