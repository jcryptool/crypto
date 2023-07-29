package org.jcryptool.visual.signalencryption.graphics;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.core.util.images.ImageService;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Anchor;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.ui.SignalEncryptionView;
import org.jcryptool.visual.signalencryption.util.UiUtils;

/**
 * Draws images on the canvas which can be easily hidden via
 * {@link #setVisible(boolean)}. Image position can be conveniently specified
 * via the {@link Positioning} utility.
 */
public class ImageComponent implements Component {

    private static final String OUTGOING_MAIL_BASE = "icons/mailOutgoing";
    private static final String INCOMING_MAIL_BASE = "icons/mailIncoming";
    private static final String LIGHT_ICON_PATH = "_light";
    private static final String DARK_ICON_PATH = "_dark";
    private static final String LEFT_ORIENTATION = "_left";
    private static final String RIGHT_ORIENTATION = "_right";
    private static final String FILE_ENDING = ".png";

    private boolean visible;
    private final LocationProps location;
    private final Image image;

    public static ImageComponentBuilder on(ComponentDrawComposite canvas) {
        return new ImageComponentBuilder(canvas);
    }

    public int imageWidth() {
        return image.getBounds().width;
    }

    public int imageHeight() {
        return image.getBounds().height;
    }

    private ImageComponent(String imagePath, Anchor location, Side offsetSide, Point offset,
            ComponentDrawComposite canvas) {
        this.location = new LocationProps(location, offsetSide, offset, canvas);
        this.visible = true;
        this.image = loadImage(imagePath);
    }

    @Override
    public void draw(GC gc) {
        checkAnchor();
        var drawLocation = this.location.resolve(image);
        gc.drawImage(image, drawLocation.x, drawLocation.y);
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void dispose() {
        image.dispose();
    }

    /**
     * Sets the position of where to draw the image relative to an existing Control.
     */
    public void setRelativeTo(Control to, Side side) {
        this.location.anchor = new Anchor(to, side);
    }

    private void checkAnchor() {
        if (location.anchor == null || location.anchor == Anchor.UNEDFINED) {
            throw new IllegalArgumentException("Trying to draw an " + this.getClass().getSimpleName() + " without "
                    + "having a location set. This can happen if you forgot to set an Anchor with the method"
                    + "relativeTo(), or you chose setAnchorLater() and forgot to do it then.");
        }
    }

    private Image loadImage(String path) {
        // Dispose if already loaded. (Should not happen, but I've seen some errors
        // related to it)
        if (image != null) {
            dispose();
        }
        var image = ImageService.getImage(SignalEncryptionView.ID, path);
        if (image == null) {
            LogUtil.logWarning(String.format("Could not load image '%s'. Displaying empty instead...%n", path));
            image = new Image(location.canvas.getDisplay(), 0, 0);
        }
        return image;
    }

    /** Dataclass which holds all details where to draw the image. */
    private class LocationProps {
        /**
         * Canvas anchor where the image shall be drawn (uses center y coordinate
         * instead of a corner)
         */
        private Anchor anchor;
        /**
         * Use WEST or EAST to indicate if the offset shall be added to the left or east
         * side of the image
         */
        final Side imageAnchor;
        /** An additional offset if you want it somewhere off of the anchor. */
        final Point offset;
        /** Target canvas to draw on */
        final ComponentDrawComposite canvas;

        private LocationProps(Anchor anchor, Side imageAnchor, Point offset, ComponentDrawComposite canvas) {
            this.anchor = anchor;
            this.imageAnchor = imageAnchor;
            this.offset = offset;
            this.canvas = canvas;

            if (imageAnchor != Side.WEST && imageAnchor != Side.EAST) {
                throw new IllegalArgumentException("Only WEST or EAST allowed to specify image left/right anchor");
            }
        }

        /**
         * Gets the current point where the image should be drawn. Note that this
         * resolving anchors at the image's west point, instead of north-west.
         */
        Point resolve(Image image) {
            var imageBounds = image.getBounds();
            int finalXOffset;
            if (imageAnchor == Side.WEST) {
                finalXOffset = offset.x;
            } else {
                // User wants to specify offset relative to the EAST side of the image
                finalXOffset = offset.x - imageBounds.width;
            }
            return anchor.resolve(canvas, finalXOffset, offset.y - imageBounds.height / 2);
        }
    }

    public static class ImageComponentBuilder {

        private ComponentDrawComposite canvas;
        private Anchor anchor;
        private Side xOffsetSide = Side.WEST;
        private int offsetX = 0;
        private int offsetY = 0;
        private final String colorScheme = UiUtils.isDarkTheme() ? LIGHT_ICON_PATH : DARK_ICON_PATH;

        public ImageComponentBuilder(ComponentDrawComposite canvas) {
            this.canvas = canvas;
        }

        public ImageComponentBuilder offsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public ImageComponentBuilder offsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public ImageComponentBuilder relativeTo(Control control, Positioning.Side side) {
            this.anchor = new Anchor(control, side);
            return this;
        }

        public ImageComponentBuilder setAnchorLater() {
            this.anchor = Anchor.UNEDFINED;
            return this;
        }

        /**
         * Use the image's right side to determine the offset (handy if you want
         * something drawn to the left). If you want to use the left (west) side, just
         * don't call this method, west is the default.
         */
        public ImageComponentBuilder xOffsetFromEast() {
            xOffsetSide = Side.EAST;
            return this;
        }

        public ImageComponent outgoingMailLeft() {
            return create(OUTGOING_MAIL_BASE + LEFT_ORIENTATION + colorScheme + FILE_ENDING);
        }

        public ImageComponent outgoingMailRight() {
            return create(OUTGOING_MAIL_BASE + RIGHT_ORIENTATION + colorScheme + FILE_ENDING);
        }

        public ImageComponent incomingMail() {
            return create(INCOMING_MAIL_BASE + colorScheme + FILE_ENDING);
        }

        private ImageComponent create(String path) {
            return canvas
                    .addComponent(new ImageComponent(path, anchor, xOffsetSide, new Point(offsetX, offsetY), canvas));
        }
    }
}
