package org.jcryptool.visual.signalencryption.graphics;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponentBuilder.FromBuilder;
import org.jcryptool.visual.signalencryption.graphics.ArrowComponentBuilder.FromControl;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Anchor;
import org.jcryptool.visual.signalencryption.graphics.Positioning.Side;
import org.jcryptool.visual.signalencryption.ui.ViewConstants;

/**
 * Draws arrows between {@link Anchor}s which can be easily hidden via
 * {@link #setVisible(boolean)}
 * <p/>
 * Some limitations apply to this arrow "framework":
 * <ul>
 * <li>Only facing arrows allowed (east {@code <-->} west or north {@code <-->}
 * south)
 * <li>Straight arrows work without problems
 * <li>Arrows which require a kink/corner are only allowed east/west
 * </ul>
 *
 * @see ArrowComponent#from(Control) ArrowComponent.from(Control) - easy object
 *      creation from edge to edge
 * @see ArrowComponent#fromAnchors() ArrowComponent.fromAnchors(Control) -
 *      advanced object creation
 */
public class ArrowComponent implements Component {

    private static final Function<Integer, Integer> STRAIGHT_DIRECTION = i -> i;
    private static final Function<Integer, Integer> OPPOSITE_DIRECTION = i -> -i;
    private static final String DEFAULT_ID_TEMPLATE = "arrow-%s-%s%n";
    public static final double BREAK_CENTER = 0.5;

    private final ArrowProperties properties;
    private boolean visible;
    private final String id;

    /**
     * Draws arrows between {@link Anchor}s which can be easily hidden via
     * {@link #setVisible(boolean)}
     * 
     * @see ArrowComponent#from(Control) ArrowComponent.from(Control) - easy object
     *      creation from edge to edge
     * @see ArrowComponent#fromAnchors() ArrowComponent.fromAnchors(Control) -
     *      advanced object creation
     */
    ArrowComponent(ArrowProperties properties, boolean visible) {
        this(properties, visible, String.format(DEFAULT_ID_TEMPLATE, properties.startSide, properties.endSide));
    }

    /**
     * Draws arrows between {@link Anchor}s which can be easily hidden via
     * {@link #setVisible(boolean)}
     * 
     * @see ArrowComponent#from(Control) ArrowComponent.from(Control) - easy object
     *      creation from edge to edge
     * @see ArrowComponent#fromAnchors() ArrowComponent.fromAnchors(Control) -
     *      advanced object creation
     */
    ArrowComponent(ArrowProperties properties, boolean visible, String id) {
        this.properties = properties;
        this.visible = visible;
        this.id = id;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void dispose() {
        // nothing to dispose in this class.
    }

    public String getId() {
        return id;
    }

    @Override
    public void draw(GC gc) {
        var path = ArrowComponent.createPath(properties);
        gc.fillPath(path);
        path.dispose();
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArrowComponent other = (ArrowComponent) obj;
        return Objects.equals(properties, other.properties);
    }

    public static Path createPath(ArrowProperties props) {
        Point start = calculateStartPoint(props);
        Point end = calculateEndPoint(props);

        if (isStraight(start, end)) {
            return drawStraightArrow(props, start, end);
        } else if (isEastWestWithCorner(props.startSide, props.endSide)) {
            return drawEastWestKinkedArrow(props, start, end);
        } else {
            return drawCornerArrow(props, start, end);
        }
    }

    private static Path drawCornerArrow(ArrowProperties props, Point start, Point end) {
        var resultPath = new Path(props.canvas.getDisplay());
        var cornerPoint = calculateCornerPoint(props);

        int width;
        int height;

        width = getWidth(props, start, cornerPoint);
        height = getHeight(props, start, cornerPoint);
        resultPath.addRectangle(start.x, start.y, width, height);

        width = getWidth(props, cornerPoint, end);
        height = getHeight(props, cornerPoint, end);
        resultPath.addRectangle(cornerPoint.x, cornerPoint.y, width, height);
        return drawArrowHead(resultPath, props, centerEnd(props, end));
    }

    private static Point calculateCornerPoint(ArrowProperties props) {
        var startX = props.xStart.resolve(props.canvas);
        var startY = props.yStart.resolve(props.canvas);
        var endX = props.yEnd.resolve(props.canvas);
        var endY = props.yEnd.resolve(props.canvas);
        if (props.startSide == Side.WEST || props.startSide == Side.EAST) {
            return new Point(endX.x, startY.y);
        } else {
            return new Point(startX.x, endY.y);
        }
    }

    private static boolean isEastWestWithCorner(Side outgoingEnd, Side incomingEnd) {
        return outgoingEnd == Side.EAST && incomingEnd == Side.WEST
                || outgoingEnd == Side.WEST && incomingEnd == Side.EAST;
    }

    /**
     * Returns a path for a straight horizontal or vertical arrow. Straightness must
     * be ensured by the caller
     */
    static Path drawStraightArrow(ArrowProperties props, Point start, Point end) {
        Path resultPath = new Path(props.canvas.getDisplay());
        var width = getWidth(props, start, end);
        var height = getHeight(props, start, end);
        resultPath.addRectangle(start.x, start.y, width, height);
        return drawArrowHead(resultPath, props, centerEnd(props, end));
    }

    /**
     * Attaches an arrow head to the given path and returns it. The end point must
     * be the actual end of line rect
     */
    private static Path drawArrowHead(Path path, ArrowProperties props, Point end) {
        int x1, x2, x3;
        int y1, y2, y3;
        Function<Integer, Integer> direction = STRAIGHT_DIRECTION;

        int arrowHeadWidthHalf = props.headWidth / 2;

        switch (props.endSide) {
        case EAST:
            direction = OPPOSITE_DIRECTION; // In this case the arrow should point in the opposite direction
        case WEST:
            x1 = end.x;
            y1 = end.y + arrowHeadWidthHalf;
            x2 = end.x + direction.apply(props.headSize);
            y2 = end.y;
            x3 = end.x;
            y3 = end.y - arrowHeadWidthHalf;
            break;
        case NORTH:
            direction = OPPOSITE_DIRECTION; // In this case the arrow should point in the opposite direction
        case SOUTH:
            x1 = end.x + arrowHeadWidthHalf;
            y1 = end.y;
            x2 = end.x;
            y2 = end.y - direction.apply(props.headSize);
            x3 = end.x - arrowHeadWidthHalf;
            y3 = end.y;
            break;
        default:
            return path;
        }
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.lineTo(x1, y1);
        return path;
    }

    /**
     * Draw a (horizontal) line which has to make a corner to reach its target
     * <b>Note</> that this only allows for horizontal targets
     * 
     * The place for the kink can be configured via
     * {@link ArrowProperties#cornerPosition}
     */
    static Path drawEastWestKinkedArrow(ArrowProperties props, Point start, Point end) {

        if (!attachesHorizontally(props)) {
            throw new UnsupportedOperationException("This arrow-type only supports horizontally offset arrows");
        }
        Path path = new Path(props.canvas.getDisplay());

        int width = getWidth(props, start, end);
        int height = props.lineWidth;

        var widthA = props.cornerPosition.apply(start, end);
        var widthB = width - (widthA + props.lineWidth);

        var heightCorrection = (end.y - start.y);

        path.addRectangle(start.x, start.y, widthA, height);
        path.addRectangle(start.x + widthA, start.y + props.lineWidth, props.lineWidth,
                heightCorrection - props.lineWidth);
        path.addRectangle(start.x + widthA + props.lineWidth, start.y + heightCorrection, widthB, height);

        return drawArrowHead(path, props, centerEnd(props, end));
    }

    private static int getWidth(ArrowProperties props, Point start, Point end) {
        var width = end.x - start.x;
        return width == 0 ? props.lineWidth : width;
    }

    private static int getHeight(ArrowProperties props, Point start, Point end) {
        var height = end.y - start.y;
        return height == 0 ? props.lineWidth : height;
    }

    static boolean isStraight(Point start, Point end) {
        return isStraightHorizontal(start, end) || isStraightVertical(start, end);
    }

    private static boolean isStraightHorizontal(Point start, Point end) {
        return start.y == end.y;
    }

    private static boolean isStraightVertical(Point start, Point end) {
        return start.x == end.x;
    }

    private static boolean attachesVertically(ArrowProperties props) {
        return props.endSide == Positioning.Side.NORTH || props.endSide == Positioning.Side.SOUTH;
    }

    private static boolean attachesHorizontally(ArrowProperties props) {
        return !attachesVertically(props);
    }

    /** Get the centred end-point of a line */
    private static Point centerEnd(ArrowProperties props, Point end) {
        int arrowWidthHalf = props.lineWidth / 2;
        if (attachesVertically(props)) {
            return new Point(end.x + arrowWidthHalf, end.y);
        } else {
            return new Point(end.x, end.y + arrowWidthHalf);
        }
    }

    static Point calculateStartPoint(ArrowProperties props) {
        return new Point(props.xStart.side.getX(props.xStart.control, props.canvas),
                props.yStart.side.getY(props.yStart.control, props.canvas));
    }

    static Point calculateEndPoint(ArrowProperties props) {
        var x = props.xEnd.side.getX(props.xEnd.control, props.canvas);
        var y = props.yEnd.side.getY(props.yEnd.control, props.canvas);
        switch (props.endSide) {
        case EAST:
            x += margin(props);
            break;
        case NORTH:
            y -= margin(props);
            break;
        case SOUTH:
            y += margin(props);
            break;
        case WEST:
            x -= margin(props);
            break;
        }
        return new Point(x, y);
    }

    static int margin(ArrowProperties props) {
        return props.headSize + ViewConstants.TARGET_MARGIN;
    }

    /**
     * Data class with information (location, size, width, etc. ) to draw an arrow
     * on an SWT canvas
     */
    public static class ArrowProperties {
        final Anchor xStart;
        final Anchor yStart;
        final Positioning.Side startSide;

        final Anchor xEnd;
        final Anchor yEnd;
        final Positioning.Side endSide;

        final int lineWidth;
        final int headWidth;
        final int headSize;
        final ComponentDrawComposite canvas;
        final BiFunction<Point, Point, Integer> cornerPosition;

        /**
         * Data class with information (location, size, width, etc. ) to draw an arrow
         * on an SWT canvas
         */
        ArrowProperties(Anchor xStart, Anchor yStart, Side outgoingSide, Anchor xEnd, Anchor yEnd, Side incomingSide,
                int lineWidth, int headWidth, int headSize, ComponentDrawComposite canvas,
                BiFunction<Point, Point, Integer> cornerPosition) {
            this.xStart = xStart;
            this.yStart = yStart;
            this.startSide = outgoingSide;
            this.xEnd = xEnd;
            this.yEnd = yEnd;
            this.endSide = incomingSide;
            this.lineWidth = lineWidth;
            this.headWidth = headWidth;
            this.headSize = headSize;
            this.canvas = canvas;
            this.cornerPosition = cornerPosition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lineWidth, headWidth, xEnd, xStart, yEnd, yStart, startSide, endSide);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ArrowProperties other = (ArrowProperties) obj;
            return lineWidth == other.lineWidth && Objects.equals(xEnd, other.xEnd)
                    && Objects.equals(xStart, other.xStart) && Objects.equals(yEnd, other.yEnd)
                    && Objects.equals(yStart, other.yStart) && startSide == other.startSide && endSide == other.endSide
                    && headWidth == other.headWidth;
        }
    }

    /**
     * Creates a new arrow with this powerful builder There is an easier
     * alternative: {@link ArrowComponent#from(Control)}
     * <p/>
     * This builder allows to specify anchors for the x and y location
     * independently.
     * <p>
     * <b>Example</b> to draw an arrow from labelA north east to a point on labelB's
     * X location and labelC's Y western location
     * 
     * <pre>{@code
     * var arrow = ArrowComponent.fromAnchors().fromAnchorX(labelA, Side.NORTH).fromAnchorY(labelA, Side.EAST)
     *         .outgoingDirection(Side.EAST).toAnchorX(labelB, Side.WEST).toAnchorY(labelC, Side.WEST)
     *         .incomingDirection(Side.WEST).on(myCanvas).withDefaults();
     * }</pre>
     * 
     */
    public static FromBuilder fromAnchors() {
        return new FromBuilder(new ArrowComponentBuilder.ArrowLocationContext());
    }

    /**
     * Creates a new Arrow with this easy-to-use builder from edge to edge anchors
     * The more complex alternative {@link ArrowComponent#fromAnchors()}
     * 
     * <b>Example 1</b> create an arrow from upper to lower box which is expected to
     * be straight and set it's width, headsize and ID (the ID is mainly for
     * debugging)
     * 
     * <pre>{@code
     * var arrow = ArrowComponent.from(upperBox).south().to(lowerBox).north().on(myCanvas).headSize(50).lineWidth(10)
     *         .arrowId("arrow-from-upper-to-lower").create();
     * }</pre>
     * 
     * <b>Example 2</b> Arrow with a corner. It starts at labelA and goes to labelD
     * but the corner should be 0.33 the way between compositeB and compositeC. Use
     * default arrow width, headWidth and headSize.
     * 
     * <pre>{@code
     * var arrow = ArrowComponent.from(labelA).east().to(labelB).west().on(myCanvas).breakBetween()
     *         .first(compositeB, Side.EAST).second(CompositeC, Side.WEST).at(0.33).withDefaults();
     * }</pre>
     */
    public static FromControl from(Control from) {
        return init(from, from);
    }

    public static FromControl from(Control fromX, Control fromY) {
        return init(fromX, fromY);
    }

    private static FromControl init(Control fromX, Control fromY) {
        var location = new ArrowComponentBuilder.ArrowLocationContext();
        location.fromX = new Anchor(fromX, null);
        location.fromY = new Anchor(fromY, null);
        return new ArrowComponentBuilder.FromControl(location);
    }

    /**
     * Calculate the break-point location for a given arrow between two points
     * 
     * This sounds abstract here's a visualization.
     * 
     * <pre>
     * 	                                                                                   arrowAnchorTo
     *                                                          ---------------------------------->
     *                                                          |
     *                                                          |
     *          +-----------------------------------------------
     *    arrowAnchorFrom                 cornerAnchorFrom    split          cornerAnchorTo
     *          |                                               |
     *          |    return the width  from arrowAnchorFrom     |
     *          |             to the split point                |
     * </pre>
     * 
     * Since the positions of the Anchors are dynamic, they have to be re-calculated
     * on subsequent draw calls. The cornerAnchors can change dynamically in the
     * drawing environment, but the corner will still be put at the split point
     * between them.
     * 
     */
    static class HorizontalBetweenProvider implements BiFunction<Point, Point, Integer> {

        private final Anchor from;
        private final Anchor to;
        private final ComponentDrawComposite canvas;
        private final double split;

        /**
         * Calculates the width from the Component anchor to the split between (to -
         * from) * split See the class annotation of {@link HorizontalBetweenProvider}
         * 
         * @param from   source anchor of the split
         * @param to     destination point of the split
         * @param canvas canvas on which the drawing happens
         * @param split  ratio of where between 'from' and 'to' the width should be
         *               given. e.g. 0.5 means center
         */
        public HorizontalBetweenProvider(Anchor from, Anchor to, ComponentDrawComposite canvas, double split) {
            this.from = from;
            this.to = to;
            this.canvas = canvas;
            this.split = split;

            assert Double.compare(split, 0.0) >= 0 && Double.compare(split, 1.0) <= 0;
        }

        @Override
        public Integer apply(Point arrowAnchorFrom, Point arrowAnchorTo) {
            var cornerOffset = resolveCornerX();
            if (!isBetween(arrowAnchorFrom, cornerOffset, arrowAnchorTo)) {
                throw new IllegalArgumentException("The given corner location is outside the arrow location");
            }
            return cornerOffset;
        }

        private int resolveCornerX() {
            Point resolvedFrom = from.resolve(canvas);
            Point resolvedTo = to.resolve(canvas);
            return (int) Math.round((resolvedTo.x - resolvedFrom.x) * split);
        }

        private boolean isBetween(Point from, int offset, Point to) {
            int x = from.x + offset;
            return x >= from.x && x <= to.x;
        }
    }

}
