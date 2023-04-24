package org.jcryptool.visual.signalencryption.graphics;

import java.util.function.BiFunction;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponent.ArrowProperties;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Anchor;
import org.jcryptool.visual.signalencryption.ui.ViewConstants;

class ArrowComponentBuilder {

    private final static BiFunction<Point, Point, Integer> CORNER_AT_MIDDLE = (start, end) -> {
        return (int) Math.round((end.x - start.x) * 0.5);
    };

    private ArrowComponentBuilder() {
        // prevent instantiation of this util class
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class FromBuilder {

        private final ArrowLocationContext location;

        public FromBuilder(ArrowLocationContext location) {
            this.location = location;
        }

        public FromBuilder fromAnchorX(Control control, Positioning.Side side) {
            location.fromX = new Anchor(control, side);
            return this;
        }

        public FromBuilder fromAnchorY(Control control, Positioning.Side side) {
            location.fromY = new Anchor(control, side);
            return this;
        }

        /**
         * Set the outgoing (at source) direction. Then continue with the destination
         * location. Make sure that both to anchors are set before calling this
         */
        public ToBuilder outgoingDirection(Positioning.Side outgoingDirection) {
            location.outgoingDirection = outgoingDirection;
            assert location.fromX != null;
            assert location.fromY != null;
            return new ToBuilder(location);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class ToBuilder {
        private final ArrowLocationContext location;

        private ToBuilder(ArrowLocationContext location) {
            this.location = location;
        }

        public ToBuilder toAnchorX(Control control, Positioning.Side side) {
            location.toX = new Anchor(control, side);
            return this;
        }

        public ToBuilder toAnchorY(Control control, Positioning.Side side) {
            location.toY = new Anchor(control, side);
            return this;
        }

        /**
         * Set the incoming (at destination) direction. Then continue with the target
         * canvas. Make sure that both to anchors are set before calling this
         */
        public To incomingDirection(Positioning.Side incomingDirection) {
            location.incomingDirection = incomingDirection;
            assert location.toX != null;
            assert location.toY != null;
            return new To(location);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class FromControl {
        private final ArrowLocationContext location;

        FromControl(ArrowLocationContext location) {
            this.location = location;
        }

        public From south() {
            return set(Positioning.Side.SOUTH);
        }

        public From east() {
            return set(Positioning.Side.EAST);
        }

        public From north() {
            return set(Positioning.Side.NORTH);
        }

        public From west() {
            return set(Positioning.Side.WEST);
        }

        private From set(Positioning.Side side) {
            location.fromX.side = side;
            location.fromY.side = side;
            location.outgoingDirection = side;
            return new From(location);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class From {
        private final ArrowLocationContext location;

        private From(ArrowLocationContext location) {
            this.location = location;
        }

        public ToControl to(Control to) {
            return init(to, to);
        }

        public ToControl to(Control toX, Control toY) {
            return init(toX, toY);
        }

        private ToControl init(Control toX, Control toY) {
            this.location.toX = new Anchor(toX, null);
            this.location.toY = new Anchor(toY, null);
            return new ToControl(location);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class ToControl {
        private final ArrowLocationContext location;

        private ToControl(ArrowLocationContext location) {
            this.location = location;
        }

        public To south() {
            return set(Positioning.Side.SOUTH);
        }

        public To east() {
            return set(Positioning.Side.EAST);
        }

        public To north() {
            return set(Positioning.Side.NORTH);
        }

        public To west() {
            return set(Positioning.Side.WEST);
        }

        private To set(Positioning.Side side) {
            location.toX.side = side;
            location.toY.side = side;
            location.incomingDirection = side;
            return new To(location);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class To {
        private final ArrowLocationContext location;

        private To(ArrowLocationContext location) {
            this.location = location;
        }

        public PropertiesBuilder on(ComponentDrawComposite canvas) {
            return new PropertiesBuilder(location, canvas);
        }
    }

    /**
     * Part of builder chain, don't use directly rather start with
     * {@link ArrowComponent#from(Control)}
     */
    public static class PropertiesBuilder {
        final Anchor fromX;
        final Anchor fromY;
        final Anchor toX;
        final Anchor toY;
        final Positioning.Side outgoingSide;
        final Positioning.Side incomingSide;
        final ComponentDrawComposite canvas;

        private int lineWidth = ViewConstants.ARROW_THICKNESS;
        private int headWidth = ViewConstants.ARROW_HEAD_THICKNESS;
        private int headSize = ViewConstants.ARROW_HEAD_SIZE;
        private String id = null;
        private BiFunction<Point, Point, Integer> cornerLocationSupplier = CORNER_AT_MIDDLE;

        private PropertiesBuilder(ArrowLocationContext location, ComponentDrawComposite canvas) {
            this.fromX = location.fromX;
            this.fromY = location.fromY;
            this.outgoingSide = location.outgoingDirection;
            this.toX = location.toX;
            this.toY = location.toY;
            this.incomingSide = location.incomingDirection;
            this.canvas = canvas;
        }

        public PropertiesBuilder lineWidth(int lineWidth) {
            this.lineWidth = lineWidth;
            return this;
        }

        public PropertiesBuilder headWidth(int headWidth) {
            this.headWidth = headWidth;
            return this;
        }

        public PropertiesBuilder headSize(int headSize) {
            this.headSize = headSize;
            return this;
        }

        public PropertiesBuilder arrowId(String id) {
            this.id = id;
            return this;
        }

        public CornerLocationBuilder breakBetween() {
            return new CornerLocationBuilder(this);
        }

        public ArrowComponent withDefaults() {
            // Override defaults and create
            lineWidth(ViewConstants.ARROW_THICKNESS);
            headWidth(ViewConstants.ARROW_HEAD_THICKNESS);
            headSize(ViewConstants.ARROW_HEAD_SIZE);
            arrowId(null);
            return create();
        }

        public ArrowComponent create() {
            var arrowProps = new ArrowComponent.ArrowProperties(fromX, fromY, outgoingSide, toX, toY, incomingSide,
                    lineWidth, headWidth, headSize, canvas, cornerLocationSupplier);
            return createAndRegister(arrowProps);
        }

        /** Create an arrow Object and register it for drawing */
        private ArrowComponent createAndRegister(ArrowComponent.ArrowProperties arrowProps) {
            var arrow = id == null ? new ArrowComponent(arrowProps, true) : new ArrowComponent(arrowProps, true, id);
            return arrowProps.canvas.addComponent(arrow);
        }
    }

    public static class CornerLocationBuilder {

        public static final double CENTER = 0.5;

        private final PropertiesBuilder parentBuilder;
        private Anchor from;
        private Anchor to;

        private CornerLocationBuilder(PropertiesBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public CornerLocationBuilder first(Control control, Positioning.Side side) {
            this.from = new Anchor(control, side);
            return this;
        }

        public CornerLocationBuilder second(Control control, Positioning.Side side) {
            this.to = new Anchor(control, side);
            return this;
        }

        public PropertiesBuilder at(double split) {
            parentBuilder.cornerLocationSupplier = new ArrowComponent.HorizontalBetweenProvider(from, to,
                    parentBuilder.canvas, split);
            return parentBuilder;
        }
    }

    /**
     * Small helper data class which only contains position related arrow properties
     * which are part of the bigger {@link ArrowProperties}
     */
    static class ArrowLocationContext {
        Anchor fromX;
        Anchor fromY;
        Positioning.Side outgoingDirection;
        Anchor toX;
        Anchor toY;
        Positioning.Side incomingDirection;
    }

}
