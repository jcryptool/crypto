package org.jcryptool.visual.signalencryption.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.jcryptool.core.util.ui.layout.GridDataBuilder;
import org.eclipse.swt.widgets.Canvas;

public class ArrowCanvas extends Canvas {

    private static final boolean DRAW_HEAD = true;
    private static final boolean DRAW_NO_HEAD = false;

    public ArrowCanvas(Composite parent, int style) {
        super(parent, style);
    }

    /**
     * Create a Layout for an arrow Canvas with a fixed width and auto-adjusted height.
     * 
     * @param horizonalAlignment
     * @param verticalAlignment
     * @param grabExcessHorizontal
     * @param grabExcessVertical
     * @param horizontalSpan
     * @param verticalSpan
     * @param width
     * @return
     */
    public static GridData canvasData(int horizonalAlignment, int verticalAlignment, boolean grabExcessHorizontal,
            boolean grabExcessVertical, int horizontalSpan, int verticalSpan, int width) {
        return canvasData(
                horizonalAlignment,
                verticalAlignment,
                grabExcessHorizontal,
                grabExcessVertical,
                horizontalSpan,
                verticalSpan,
                width,
                SWT.DEFAULT
        );
    }
    
    /**
     * Create a Layout for an arrow Canvas with both fixed width and height.
     * 
     * @param horizonalAlignment
     * @param verticalAlignment
     * @param grabExcessHorizontal
     * @param grabExcessVertical
     * @param horizontalSpan
     * @param verticalSpan
     * @param width
     * @param height
     * @return
     */
    public static GridData canvasData(int horizonalAlignment, int verticalAlignment, boolean grabExcessHorizontal,
            boolean grabExcessVertical, int horizontalSpan, int verticalSpan, int width, int height) {
        return GridDataBuilder.with(
                horizonalAlignment,
                verticalAlignment,
                grabExcessHorizontal,
                grabExcessVertical, horizontalSpan,
                verticalSpan)
                .widthHint(width)
                .heightHint(height)
                .get();
    }

    public static Path drawRightArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = width - (ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN);

        float lowerLineY = (height / 2) - (arrowWidth / 2);
        float upperLineY = (height / 2) + (arrowWidth / 2);

        float lowerArrowHeadY = (height / 2) - (arrowHeadWidth / 2);
        float upperArrowHeadY = (height / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(0, lowerLineY);
        resultPath.lineTo(0, upperLineY);
        resultPath.lineTo(baseLength, upperLineY);
        resultPath.lineTo(baseLength, upperArrowHeadY);
        resultPath.lineTo(baseLength + ViewConstants.ARROW_HEAD_SIZE, height / 2);
        resultPath.lineTo(baseLength, lowerArrowHeadY);
        resultPath.lineTo(baseLength, lowerLineY);
        resultPath.lineTo(0, lowerLineY);
        
        return resultPath;
    }

    public static Path drawLeftArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN;
        

        float lowerLineY = (height / 2) - (arrowWidth / 2);
        float upperLineY = (height / 2) + (arrowWidth / 2);

        float lowerArrowHeadY = (height / 2) - (arrowHeadWidth / 2);
        float upperArrowHeadY = (height / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(width, lowerLineY);
        resultPath.lineTo(width, upperLineY);
        resultPath.lineTo(baseLength, upperLineY);
        resultPath.lineTo(baseLength, upperArrowHeadY);
        resultPath.lineTo(baseLength - ViewConstants.ARROW_HEAD_SIZE, height / 2);
        resultPath.lineTo(baseLength, lowerArrowHeadY);
        resultPath.lineTo(baseLength, lowerLineY);
        resultPath.lineTo(width, lowerLineY);
        
        return resultPath;
    }
    
    public  static Path drawUpArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {

        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN;

        float leftLineY = (width / 2) - (arrowWidth / 2);
        float rightLineY = (width / 2) + (arrowWidth / 2);

        float leftArrowLineY = (width / 2) - (arrowHeadWidth / 2);
        float rightArrowLineY = (width / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(leftLineY, height);
        resultPath.lineTo(leftLineY, baseLength);
        resultPath.lineTo(leftArrowLineY, baseLength);
        resultPath.lineTo(width/2,baseLength - ViewConstants.ARROW_HEAD_SIZE);
        resultPath.lineTo(rightArrowLineY, baseLength);
        resultPath.lineTo(rightLineY, baseLength);
        resultPath.lineTo(rightLineY, height);
        resultPath.lineTo(leftLineY, height);
        
        return resultPath;
    }
    
    public static Path drawDownArrow (Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = height - (ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN);
        

        float leftLineY = (width / 2) - (arrowWidth / 2);
        float rightLineY = (width / 2) + (arrowWidth / 2);

        float leftArrowLineY = (width / 2) - (arrowHeadWidth / 2);
        float rightArrowLineY = (width / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(leftLineY, 0);
        resultPath.lineTo(leftLineY, baseLength);
        resultPath.lineTo(leftArrowLineY, baseLength);
        resultPath.lineTo(width/2, baseLength + ViewConstants.ARROW_HEAD_SIZE);
        resultPath.lineTo(rightArrowLineY, baseLength);
        resultPath.lineTo(rightLineY, baseLength);
        resultPath.lineTo(rightLineY, 0);
        resultPath.lineTo(leftLineY, 0);

        return resultPath;
    }
    
    public static Path drawDownRightArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = height - (ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN);
        float baseLengthSecond = width - (ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN);
        

        float leftLineY = (width / 2) - (arrowWidth / 2);
        float rightLineY = (width / 2) + (arrowWidth / 2);

        float leftArrowLineY = (width / 2) - (arrowHeadWidth / 2);
        float rightArrowLineY = (width / 2) + (arrowHeadWidth / 2);
        
        float lowerLineY = (height / 2) - (arrowWidth / 2);
        float upperLineY = (height / 2) + (arrowWidth / 2);

        float lowerArrowHeadY = (height / 2) - (arrowHeadWidth / 2);
        float upperArrowHeadY = (height / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(leftLineY, 0);
        resultPath.lineTo(leftLineY, baseLength);
        resultPath.lineTo(leftArrowLineY, baseLength);
        resultPath.lineTo(width/2, baseLength + ViewConstants.ARROW_HEAD_SIZE);
        resultPath.lineTo(rightArrowLineY, baseLength);
        resultPath.lineTo(rightLineY, baseLength);
        resultPath.lineTo(rightLineY, lowerLineY);
        resultPath.lineTo(baseLengthSecond, lowerLineY);
        resultPath.lineTo(baseLengthSecond, lowerArrowHeadY);
        resultPath.lineTo(baseLengthSecond + ViewConstants.ARROW_HEAD_SIZE, height / 2);
        resultPath.lineTo(baseLengthSecond, upperArrowHeadY);
        resultPath.lineTo(baseLengthSecond, upperLineY);
        resultPath.lineTo(rightLineY, upperLineY);
        resultPath.lineTo(rightLineY, 0);
        resultPath.lineTo(leftLineY, 0);

        return resultPath;
    }
    public static Path drawDownLeftArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float baseLength = height - (ViewConstants.ARROW_HEAD_SIZE - ViewConstants.TARGET_MARGIN);
        float baseLengthSecond = ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN;
        

        float leftLineY = (width / 2) - (arrowWidth / 2);
        float rightLineY = (width / 2) + (arrowWidth / 2);

        float leftArrowLineY = (width / 2) - (arrowHeadWidth / 2);
        float rightArrowLineY = (width / 2) + (arrowHeadWidth / 2);
        
        float lowerLineY = (height / 2) - (arrowWidth / 2);
        float upperLineY = (height / 2) + (arrowWidth / 2);

        float lowerArrowHeadY = (height / 2) - (arrowHeadWidth / 2);
        float upperArrowHeadY = (height / 2) + (arrowHeadWidth / 2);

        resultPath.moveTo(leftLineY, 0);
        
        resultPath.lineTo(leftLineY, upperLineY);
        resultPath.lineTo(baseLengthSecond, upperLineY);
        resultPath.lineTo(baseLengthSecond, upperArrowHeadY);
        resultPath.lineTo(baseLengthSecond - ViewConstants.ARROW_HEAD_SIZE, height / 2);
        resultPath.lineTo(baseLengthSecond, lowerArrowHeadY);
        resultPath.lineTo(baseLengthSecond, lowerLineY);
        resultPath.lineTo(leftLineY, lowerLineY);
        
        resultPath.lineTo(leftLineY, baseLength);
        resultPath.lineTo(leftArrowLineY, baseLength);
        resultPath.lineTo(width/2, baseLength + ViewConstants.ARROW_HEAD_SIZE);
        resultPath.lineTo(rightArrowLineY, baseLength);
        resultPath.lineTo(rightLineY, baseLength);
        resultPath.lineTo(rightLineY, 0);
        resultPath.lineTo(leftLineY, 0);

        return resultPath;
    }
    public  static Path drawVertikalLine(Canvas canvas, int arrowWidth, int arrowHeadWidth, int percentValue) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        
        float percent = (float)percentValue / 100;
        
        float lineWidthRight = width * percent+ arrowWidth/2 ;
        float lineWidthLeft = width * percent - arrowWidth/2;
        
        resultPath.moveTo(lineWidthRight, 0);
        resultPath.lineTo(lineWidthRight, height);
        resultPath.lineTo(lineWidthLeft, height);
        resultPath.lineTo(lineWidthLeft, 0);


        return resultPath;
    }
    public  static Path drawHorizontalLine(Canvas canvas, int arrowWidth, int arrowHeadWidth, int percentValue) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        
        float percent = (float)percentValue / 100;
        float lineWidthRight = height * percent + arrowWidth/2;
        float lineWidthLeft = height * percent - arrowWidth/2 ;
        
        resultPath.moveTo(0, lineWidthRight);
        resultPath.lineTo(width, lineWidthRight);
        resultPath.lineTo( width, lineWidthLeft);
        resultPath.lineTo(0, lineWidthLeft);

        return resultPath;
    }

    public static Path drawRightUpRightLine(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        // Note that arrowHeadWidth is still a parameter here, to ensure that the up-right-up line
        // is on the same height as the arrows' line. 
        return drawUpRightUp(canvas, DRAW_NO_HEAD, arrowWidth, arrowHeadWidth);
    }

    public static Path drawRightUpRightArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        return drawUpRightUp(canvas, DRAW_HEAD, arrowWidth, arrowHeadWidth);
    }

    public static Path drawLeftUpLeftLine(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        // Note that arrowHeadWidth is still a parameter here, to ensure that the up-left-up line
        // is on the same height as the arrows' line. 
        return drawLeftUpLeft(canvas, DRAW_NO_HEAD, arrowWidth, arrowHeadWidth);
    }

    public static Path drawLeftUpLeftArrow(Canvas canvas, int arrowWidth, int arrowHeadWidth) {
        return drawLeftUpLeft(canvas, DRAW_HEAD, arrowWidth, arrowHeadWidth);
    }
 
    private static Path drawUpRightUp(Canvas canvas, boolean drawHead, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float horizontalLength = width - (ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN);
        
        // left edge of the middle line.
        float middleLineXLeft = (width / 2) - (arrowWidth / 2);
        // right edge of the middle line.
        float middleLineXRight = (width / 2) + (arrowWidth / 2);  

        // upper edge of top line
        float upperLineYTop = (arrowHeadWidth / 2) - (arrowWidth / 2);
        // lower edge of top line
        float upperLineYBottom = (arrowHeadWidth / 2) + (arrowHeadWidth / 2) - (arrowWidth / 2);

        // corner of arrow space top
        float topArrowHeadY = 0;
        // corner of arrow space bottom 
        float bottomArrowHeadY = arrowHeadWidth;
        float arrowTipX = width - ViewConstants.TARGET_MARGIN;
        
        //                                                              X          Y
        resultPath.moveTo(0, height - arrowWidth);                 // left        bottom
        resultPath.lineTo(middleLineXLeft, height - arrowWidth);   // middle      bottom
        resultPath.lineTo(middleLineXLeft, upperLineYTop);         // middle       top 
        resultPath.lineTo(horizontalLength, upperLineYTop);        // right        top
        
        if (drawHead) {
            drawArrowHead(
                    resultPath,
                    horizontalLength,
                    topArrowHeadY,
                    arrowTipX,
                    bottomArrowHeadY
            );
        } else {
            // prolong rectangular section instead of a head
            resultPath.lineTo(width, upperLineYTop);
            resultPath.lineTo(width, upperLineYBottom);
            
        }
        
        //                                                              X          Y
        resultPath.lineTo(horizontalLength, upperLineYBottom);     // right      top
        resultPath.lineTo(middleLineXRight, upperLineYBottom);     // middle     top
        resultPath.lineTo(middleLineXRight, height);               // middle    bottom
        resultPath.lineTo(0, height);                              //  left     bottom
        resultPath.lineTo(0, height - arrowWidth);                 //  return to start

        return resultPath;
    }

    private static Path drawLeftUpLeft(Canvas canvas, boolean drawHead, int arrowWidth, int arrowHeadWidth) {
        Path resultPath = new Path(canvas.getDisplay());
        int width = canvas.getBounds().width;
        int height = canvas.getBounds().height;
        float horizontalLength = ViewConstants.ARROW_HEAD_SIZE + ViewConstants.TARGET_MARGIN;
        
        // left edge of the middle line.
        float middleLineXLeft = (width / 2) - (arrowWidth / 2);
        // right edge of the middle line.
        float middleLineXRight = (width / 2) + (arrowWidth / 2);  

        // upper edge of top line
        float upperLineYTop = (arrowHeadWidth / 2) - (arrowWidth / 2);
        // lower edge of top line
        float upperLineYBottom = (arrowHeadWidth / 2) + (arrowHeadWidth / 2) - (arrowWidth / 2);

        // corner of arrow space top
        float topArrowHeadY = 0;
        // corner of arrow space bottom 
        float bottomArrowHeadY = arrowHeadWidth;
        float arrowTipX = ViewConstants.TARGET_MARGIN;
        
        //                                                              X          Y
        resultPath.moveTo(width, height - arrowWidth);             // right      bottom
        resultPath.lineTo(middleLineXRight, height - arrowWidth);  // middle     bottom
        resultPath.lineTo(middleLineXRight, upperLineYTop);        // middle       top 
        resultPath.lineTo(horizontalLength, upperLineYTop);        // left         top
        
        if (drawHead) {
            drawArrowHead(
                    resultPath,
                    horizontalLength,
                    topArrowHeadY,
                    arrowTipX,
                    bottomArrowHeadY
            );
        } else {
            // prolong rectangular section instead of a head
            resultPath.lineTo(0, upperLineYTop);
            resultPath.lineTo(0, upperLineYBottom);
            
        }
        
        //                                                              X          Y
        resultPath.lineTo(horizontalLength, upperLineYBottom);     // left        top
        resultPath.lineTo(middleLineXLeft, upperLineYBottom);      // middle      top
        resultPath.lineTo(middleLineXLeft, height);                // middle    bottom
        resultPath.lineTo(width, height);                          // right     bottom
        resultPath.lineTo(width, height - arrowWidth);             //  return to start

        return resultPath;
    }

    private static Path drawArrowHead(Path target, float baseX, float upperBaseY, float tipX, float lowerBaseY) {
        target.lineTo(baseX, upperBaseY);
        target.lineTo(tipX, upperBaseY + (lowerBaseY - upperBaseY) / 2);
        target.lineTo(baseX, lowerBaseY);
        return target;
    }



}
