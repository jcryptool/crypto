package org.jcryptool.visual.signalencryption.graphics;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/** Data and util classes for specifying locations and anchors */
public class Positioning {

    private Positioning() {
        // prevent instantiation of this util class
    }

    /**
     * Specifies a Point on the given control. Those can resolve to X or Y
     * coordinates independently.
     * 
     * <pre>
     * North | ˇ +-----------------------+ | | West -> | Control | <- East | |
     * +-----------------------+ ^ | South </pre
     */
    public static class Anchor {

        public static final Anchor UNEDFINED = new Anchor(null, null);

        Control control;
        Side side;

        public Anchor(Control control, Side side) {
            this.control = control;
            this.side = side;
        }

        /**
         * Resolve the absolute point of this anchor on the given canvas. In-between
         * parents are automatically traversed and added.
         */
        public Point resolve(ComponentDrawComposite canvas) {
            return resolve(canvas, 0, 0);
        }

        /**
         * Resolve the absolute point of this anchor plus the given offsets on the given
         * canvas In-between parents are automatically traversed and added.
         */
        public Point resolve(ComponentDrawComposite canvas, int xOffset, int yOffset) {
            return new Point(side.getX(control, canvas) + xOffset, side.getY(control, canvas) + yOffset);
        }
    }

    /** Side specifier, see {@link Anchor} */
    public static enum Side {
        NORTH {

            @Override
            boolean faces(Side other) {
                return other == SOUTH;
            }

            @Override
            int getX(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.x + (bounds.width / 2);
            }

            @Override
            int getY(Control control, Control anchor) {
                return correctedBounds(control, anchor).y + CORRECTION_FACTOR;
            }

        },
        EAST {

            @Override
            boolean faces(Side other) {
                return other == WEST;
            }

            @Override
            int getX(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.x + bounds.width;
            }

            @Override
            int getY(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.y + (bounds.height / 2) + CORRECTION_FACTOR;
            }

        },
        SOUTH {

            @Override
            boolean faces(Side other) {
                return other == NORTH;
            }

            @Override
            int getX(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.x + (bounds.width / 2);
            }

            @Override
            int getY(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.y + bounds.height + CORRECTION_FACTOR;
            }

        },
        WEST {

            @Override
            boolean faces(Side other) {
                return other == EAST;
            }

            @Override
            int getX(Control control, Control anchor) {
                return correctedBounds(control, anchor).x;
            }

            @Override
            int getY(Control control, Control anchor) {
                var bounds = correctedBounds(control, anchor);
                return bounds.y + (bounds.height / 2) + CORRECTION_FACTOR;
            }

        };

        /**
         * There is a problem with groups that they don't put their proper location into
         * getBounds(). This constants acknowledges that we are using exactly two
         * groups.
         * 
         * I don't exactly know if there was a cleaner way to do this.
         */
        static final int CORRECTION_FACTOR = 20;

        abstract boolean faces(Side other);

        abstract int getX(Control control, Control anchor);

        abstract int getY(Control control, Control anchor);

        /**
         * Traverse all parents until the drawing canvas to get a correct absolute
         * position of things.
         */
        public static Rectangle correctedBounds(Control control, Control drawingCanvas) {
            Rectangle innerBounds = control.getBounds();
            var offsetX = 0;
            var offsetY = 0;
            while (control.getParent() != null && control != drawingCanvas) {
                control = control.getParent();
                var location = control.getLocation();
                offsetX += location.x;
                offsetY += location.y;
            }
            return new Rectangle(innerBounds.x + offsetX, innerBounds.y + offsetY, innerBounds.width,
                    innerBounds.height);
        }
    }
}
