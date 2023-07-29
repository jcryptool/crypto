package org.jcryptool.visual.signalencryption.graphics;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.signalencryption.util.UiUtils;

/** Draws {@link Component}s on the canvas. */
public class ComponentDrawComposite extends Canvas {

    private static final Color DRAW_COLOR = buildDrawColor();
    private Set<Component> componentsToDraw = new HashSet<>();

    public ComponentDrawComposite(Composite parent, int style) {
        super(parent, style | SWT.DOUBLE_BUFFERED); // we want this always double buffered for smooth interaction
        this.addPaintListener(e -> paintControl(e));
    }

    private void paintControl(PaintEvent e) {
        e.gc.setBackground(DRAW_COLOR);

        for (var component : componentsToDraw) {
            if (component.isVisible()) {
                component.draw(e.gc);
            }
        }
    }

    public <C extends Component> C addComponent(C component) {
        componentsToDraw.add(component);
        return component;
    }

    public void removeComponent(Component component) {
        componentsToDraw.remove(component);
    }

    /**
     * Build a draw color. Uses {@link SWT#COLOR_WIDGET_FOREGROUND} which is
     * slightly adjusted towards the middle.
     */
    private static Color buildDrawColor() {
        var baseColor = ColorService.getColor(SWT.COLOR_WIDGET_FOREGROUND);
        var rgb = baseColor.getRGB();
        var factor = UiUtils.isDarkTheme() ? 0.7 : 1.2; // Make slightly darker for dark themes and vice versa.
        rgb.red *= factor;
        rgb.green *= factor;
        rgb.blue *= factor;
        return new Color(Display.getDefault(), safeRgb(rgb));
    }

    private static RGB safeRgb(RGB rgb) {
        rgb.red = Math.max(0, Math.min(255, rgb.red));
        rgb.green = Math.max(0, Math.min(255, rgb.green));
        rgb.blue = Math.max(0, Math.min(255, rgb.blue));
        return rgb;
    }

    @Override
    public void dispose() {
        super.dispose();
        // We must dispose of the non-SWT children (Components) as well, because they
        // partly use SWT resources.
        for (var component : componentsToDraw) {
            component.dispose();
        }
    }
}
