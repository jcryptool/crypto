package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public final class RssColors {
    public static final Color LIGHT_GREEN, LIGHT_BLUE, GREEN, BLACK, WHITE, LIGHT_GRAY, GRAY, YELLOW;

    static {
        LIGHT_GREEN = new Color(Display.getDefault(), 99, 208, 28);
        LIGHT_BLUE = new Color(Display.getDefault(), 143, 195, 249);
        GREEN = new Color(Display.getDefault(), 71, 152, 17);
        BLACK = new Color(Display.getDefault(), 0, 0, 0);
        WHITE = new Color(Display.getDefault(), 255, 255, 255);
        LIGHT_GRAY = new Color(Display.getDefault(), 140, 138, 140);
        GRAY = new Color(Display.getDefault(), 100, 98, 100);
        YELLOW = new Color(Display.getDefault(), 255, 255, 51);
    }

    private RssColors() {
    }
}
