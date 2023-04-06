package org.jcryptool.visual.signalencryption.ui;

import java.util.Map;
import java.util.HashMap;

import java.util.Collections;
import java.util.function.BiFunction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.StyledText;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.fonts.FontService;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;

/**
 * Provides a function which outputs an information Composite based on pre-filled information.
 * This is intended to be used in tandem with the {@link FlowChartNode} and its "lense" icon
 * which opens the popup
 */
public class FlowChartNodePopup implements BiFunction<Composite, Integer, Composite> {
	
	private Map<String, String> keyValuePairs;
	
	/** Creates a simple pop-up which displays exactly one key-value pair */
	public static FlowChartNodePopup create(String key, String value) {
		return new FlowChartNodePopup(Map.of(key, value));
	}
	
	/** Creates a simple pop-up which displays each item in the given key-value pairs (one per line) */
	public static FlowChartNodePopup create(Map<String, String> keyValuePairs) {
		return new FlowChartNodePopup(keyValuePairs);
	}
	
	
	/** Creates an empty pop-up */
	public static FlowChartNodePopup empty() {
		return new FlowChartNodePopup(Collections.emptyMap());
	}
	
	private FlowChartNodePopup(Map<String, String> keyValuePairs) {
		this.keyValuePairs = new HashMap<>(keyValuePairs); // wrapping in HashMap should ensure mutability
	}
	
	/**
	 * Updates the value when the next pop-up is created.
	 * This method is only applicable when initially created with one key-value pair
	 */
	public void setValue(String value) {
		assert keyValuePairs.size() == 1; 
		var optKey = keyValuePairs.keySet().stream().findFirst();
		if (optKey.isPresent()) {
			keyValuePairs.put(optKey.get(), value);
		} else {
			LogUtil.logError(String.format("Tried to set value '%s' but it failed%n", value));
		}
	}
	
	/** Sets all the given key-value pairs when the next pop-up is created. Overrides any previously set values */
	public void setValues(Map<String, String> newKeyValuePairs) {
		this.keyValuePairs = newKeyValuePairs;
	}

	@Override
	public Composite apply(Composite parent, Integer style) {
		var composite = new Composite(parent, style);

			for (var keyValuePair : keyValuePairs.entrySet()) {
				var label = new Label(composite, SWT.NONE);
				var txt = new StyledText(composite, SWT.BORDER);
				txt.setCaret(null);
				txt.setEditable(false);

				var layout = new GridLayout(2, false);

				composite.setLayout(layout);
				composite.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.FILL, true, true).get());
				label.setText(keyValuePair.getKey());
				label.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.TOP, false, false).get());
				txt.setLayoutData(GridDataBuilder.with(SWT.FILL, SWT.TOP, true, false).get());
				txt.setFont(FontService.getNormalMonospacedFont());
				txt.setText(keyValuePair.getValue());
			}
			return composite;
	}
}
