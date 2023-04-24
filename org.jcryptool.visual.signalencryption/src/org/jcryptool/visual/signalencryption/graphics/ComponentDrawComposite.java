package org.jcryptool.visual.signalencryption.graphics;

import java.util.Set;
import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ComponentDrawComposite extends Canvas {

    private Set<Component> componentsToDraw = new HashSet<>();

    public ComponentDrawComposite(Composite parent, int style) {
        super(parent, style | SWT.DOUBLE_BUFFERED); // we want this always double buffered for smooth interaction
        this.addPaintListener(e -> paintControl(e));
    }

    private void paintControl(PaintEvent e) {
        // e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_DARK_RED));
        e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WIDGET_BORDER));

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

}
