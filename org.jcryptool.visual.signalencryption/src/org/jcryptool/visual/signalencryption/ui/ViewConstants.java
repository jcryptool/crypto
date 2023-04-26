package org.jcryptool.visual.signalencryption.ui;

public class ViewConstants {

    // height of the plug-in as a whole (this is important for the scrolling of
    // the plug-in)
    public static final int PLUGIN_HEIGTH = 900;

    // width of the plug-in as a whole (this is important for the scrolling of
    // the plug-in)
    public static final int PLUGIN_WIDTH = 1300;

    /**
     * Estimated height of the algorithm composite.
     *
     * <p>
     * <b>Note:</b> this is not used as minimum height, but as hint. A minimum
     * height can only be used when grabbing excess space, which we do not do. So in
     * edge cases it may actually be larger.
     */
    public static final int ALGORITHM_HEIGHT = 400;

    /**
     * The width of the Alice and Bob selection view buttons
     */
    public static final int ENTITY_BUTTON_WIDTH = 150;

    /**
     * I don't know why, but this offset seems to work best when to compute the height of the explainers textbox
     * */
    public static final int EXPLAINER_HEIGHT_OFFSET = 50;
    /**
     * This is the width hint of the algorithm boxes, a major part of the plugin's
     * flow elements.
     */
    public static final int BOX_WIDTH = 180;

    /**
     * This is the width hint of the (slim) algorithm boxes, a major part of the
     * plugin's flow elements.
     */
    public static final int BOX_WIDTH_SLIM = 85;

    /**
     * Width of the encrypted/decrypted messagebox
     */
    public static final int MESSAGE_BOX_WIDTH = 250;

    /**
     * Any chain's horizontal spacing
     */
    public static final int CHAIN_HORIZONTAL_SPACE = 14;

    /**
     * This is the height hint of the algorithm boxes, a major part of the plugin's
     * flow elements.
     */
    public static final int BOX_HEIGHT = 60;

    /**
     * Default value of the SWT horizontal spacing.
     *
     * We sometimes have to specify this manually, because we set the default to 0,
     * so that some arrows connect nicely to their neighbours.
     */
    public static final int HORIZONTAL_SPACING = 5;

    /**
     * The horizontal spacing between canvas elements
     * (e.g. between Root and Sending Chain or Sending Chain and MessageBox)
     */
    public static final int CANVAS_SPACING = 38;

    /**
     * Horizontal distance between message-box and the mail icon edge
     */
    public static final int MAIL_ICON_X_OFFSET = Math.round(CANVAS_SPACING * 0.75f);

    /**
     * Arrow line thickness
     */
    public static final int ARROW_THICKNESS = 6;

    /**
     * The thickness or the "broadness" of the arrow tip
     */
    public static final int ARROW_HEAD_THICKNESS = 12;

    /**
     * Arrow head size from base to the tip.
     */
    public static final int ARROW_HEAD_SIZE = 12;

    /**
     * Distance between tip of the arrow head and the border of the canvas
     */
    public static final int TARGET_MARGIN = 2;

}