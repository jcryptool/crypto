package org.jcryptool.visual.rss.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Draws the paths in the visual between the state boxes.
 * 
 * @author Leon Shell, Lukas Krodinger
 */
public class RssPathComposite extends Canvas {
    public static final int PATH_WIDTH = RssOverviewComposite.STATE_BOX_X_PADDING / 4 * 3;

    private final RssPathPaintListener listener;

    public RssPathComposite(Composite parent) {
        this(parent, PathType.P_PATH);
    }

    public RssPathComposite(Composite parent, PathType pathType) {
        super(parent, SWT.NONE);

        GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, false);
        gridData.heightHint = RssOverviewComposite.STATE_BOX_Y_PADDING * 4;
        setLayoutData(gridData);

        listener = new RssPathPaintListener(pathType);

        addPaintListener(listener);
    }

    public void setColor(PathColor pathColor) {
        listener.setPathColor(pathColor);
        redraw();
    }

    private class RssPathPaintListener implements PaintListener {

        private PathColor pathColor;
        private PathType pathType;

        public RssPathPaintListener(PathType pathType) {
            this.pathType = pathType;
            pathColor = PathColor.GREY;
        }

        @Override
        public void paintControl(PaintEvent e) {
            Color c;
            if (pathColor == PathColor.GREEN) {
                c = RssColors.GREEN;
            } else if (pathColor == PathColor.GREY) {
                c = RssColors.LIGHT_GRAY;
            } else {
                throw new IllegalStateException();
            }

            switch (pathType) {
            case L_PATH:
                drawLPath(e, c);
                break;
            case RL_PATH:
                drawRLPath(e, c);
                break;
            case FRL_PATH:
                drawFRLPath(e, c);
                break;
            case P_PATH:
                drawPPath(e, c);
                break;
            default:
                throw new IllegalStateException();
            }

        }

        private void drawPPath(PaintEvent e, Color c) {
            GC gc = e.gc;
            Path ab = new Path(Display.getCurrent());
            int width = e.width;
            int height = e.height;

            int x1 = width / 2 - PATH_WIDTH / 2;
            ab.moveTo(x1, 0);
            ab.lineTo(x1, height);
            ab.lineTo(x1 + PATH_WIDTH, height);
            ab.lineTo(x1 + PATH_WIDTH, 0);
            gc.setBackground(c);
            gc.fillPath(ab);
        }

        private void drawLPath(PaintEvent e, Color c) {
            GC gc = e.gc;
            Path ab = new Path(Display.getCurrent());
            int width = e.width;
            int height = e.height;

            int x1 = width / 2 - PATH_WIDTH / 2;
            int y1 = height / 2 + PATH_WIDTH / 2;
            ab.moveTo(x1, 0);
            ab.lineTo(x1, y1);
            ab.lineTo(width, y1);
            ab.lineTo(width, y1 - PATH_WIDTH);
            ab.lineTo(x1 + PATH_WIDTH, y1 - PATH_WIDTH);
            ab.lineTo(x1 + PATH_WIDTH, 0);
            gc.setBackground(c);
            gc.fillPath(ab);
        }

        private void drawRLPath(PaintEvent e, Color c) {
            GC gc = e.gc;
            Path ab = new Path(Display.getCurrent());
            int width = e.width;
            int height = e.height;

            int x1 = width / 2 - PATH_WIDTH / 2;
            int y1 = height / 2 + PATH_WIDTH / 2;
            ab.moveTo(0, y1 - PATH_WIDTH);
            ab.lineTo(0, y1);
            ab.lineTo(x1 + PATH_WIDTH, y1);
            ab.lineTo(x1 + PATH_WIDTH, 0);
            ab.lineTo(x1, 0);
            ab.lineTo(x1, y1 - PATH_WIDTH);
            gc.setBackground(c);
            gc.fillPath(ab);
        }

        private void drawFRLPath(PaintEvent e, Color c) {
            GC gc = e.gc;
            Path ab = new Path(Display.getCurrent());
            int width = e.width;
            int height = e.height;

            int x1 = width / 2 - PATH_WIDTH / 2;
            int y1 = height / 2 - PATH_WIDTH / 2;
            ab.moveTo(0, y1);
            ab.lineTo(0, y1 + PATH_WIDTH);
            ab.lineTo(x1, y1 + PATH_WIDTH);
            ab.lineTo(x1, height);
            ab.lineTo(x1 + PATH_WIDTH, height);
            ab.lineTo(x1 + PATH_WIDTH, y1);
            gc.setBackground(c);
            gc.fillPath(ab);
        }

        public void setPathType(PathType pathType) {
            this.pathType = pathType;
        }

        public void setPathColor(PathColor pathColor) {
            this.pathColor = pathColor;
        }

    }

    public enum PathType {
        P_PATH, L_PATH, RL_PATH, FRL_PATH
    }

    public enum PathColor {
        GREEN, GREY
    }

}
