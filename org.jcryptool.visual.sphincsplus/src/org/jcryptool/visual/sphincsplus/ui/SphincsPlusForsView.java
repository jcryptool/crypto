// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2020, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.sphincsplus.ui;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.swt.custom.StyledText;

/**
 * 
 * @author Sebastian Ranftl
 *
 */
public class SphincsPlusForsView extends Composite {

    private Graph graph;

    private GraphViewer graphViewer;
    private GraphViewer graphViewerSub;

    private Composite zestComposite;
    private Composite expandComposite;
    private Composite sphincsPlusForsView;

    private Label descLabel;

    private boolean mouseDragging;
    private boolean mouseDraggingSub;

    private Point oldMouse;
    private Point newMouse;
    private Point oldMouseSub;
    private Point newMouseSub;
    private org.eclipse.draw2d.geometry.Point viewLocation;
    private org.eclipse.draw2d.geometry.Point viewLocationSub;

    private Display curDisplay;

    private ExpandBar descriptionExpander;

    private StyledText descText;

	private Graph graphSub;

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public SphincsPlusForsView(Composite parent, int style, SphincsPlusView sphincsPlusView) {
        super(parent, style);

        this.setLayout(new GridLayout());
        this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        curDisplay = getDisplay();
        sphincsPlusForsView = this;

        descLabel = new Label(this, SWT.NONE);
        descLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        descLabel.setText(Messages.SphincsPlusForsView_descLabel);

        descriptionExpander = new ExpandBar(this, SWT.NONE);
        descriptionExpander.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        ExpandItem collapsablePart = new ExpandItem(descriptionExpander, SWT.NONE, 0);

        expandComposite = new Composite(descriptionExpander, SWT.NONE);
        GridLayout expandLayout = new GridLayout(2, true);
        expandComposite.setLayout(expandLayout);

        descText = new StyledText(expandComposite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
        descText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        descText.setText(Messages.SphincsPlusForsView_descText_general);

        int preferredHeight = descText.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
        collapsablePart.setText(Messages.SphincsPlusForsView_collapsedPart_expanded);
        collapsablePart.setExpanded(true);
        collapsablePart.setControl(expandComposite);
        collapsablePart.setHeight(preferredHeight + 60);
        descriptionExpander.setBackground(curDisplay.getSystemColor(SWT.COLOR_WHITE));

        zestComposite = new Composite(this, SWT.DOUBLE_BUFFERED | SWT.BORDER);
        zestComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        zestComposite.setLayout(new GridLayout(3, false));
        zestComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
        zestComposite.setBackgroundMode(SWT.INHERIT_FORCE);
        zestComposite.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));

        // create GraphViewer and Graph
        graphViewer = new GraphViewer(zestComposite, SWT.V_SCROLL | SWT.H_SCROLL);
        graph = graphViewer.getGraphControl();
        graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
        graph.setScrollBarVisibility(FigureCanvas.NEVER);
        
        Label sep = new Label(zestComposite, SWT.SEPARATOR | SWT.VERTICAL);
        sep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

        graphViewerSub = new GraphViewer(zestComposite, SWT.V_SCROLL | SWT.H_SCROLL);
        graphSub = graphViewerSub.getGraphControl();
        graphSub.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
        graphSub.setScrollBarVisibility(FigureCanvas.NEVER);

        // Adding graph nodes and connect them
        GraphNode node_1 = new GraphNode(graph, SWT.NONE, "FORS PK");
        GraphNode container_1 = new GraphNode(graph, SWT.NONE, "ROOT-Node");
        GraphNode container_2 = new GraphNode(graph, SWT.NONE, "ROOT-Node");
        GraphNode container_3 = new GraphNode(graph, SWT.NONE, "ROOT-Node");

        node_1.setSize(120, 40);
        createForsSubTree(container_1);
//        createForsSubTree(container_2);
//        createForsSubTree(container_3);
        node_1.setBackgroundColor(ColorConstants.orange);
        container_1.setBackgroundColor(ColorConstants.lightGreen);
        container_2.setBackgroundColor(ColorConstants.lightGreen);
        container_3.setBackgroundColor(ColorConstants.lightGreen);

        SelectionAdapter selectionListenerLeft = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (e.item == node_1) {
                    descText.setText(Messages.SphincsPlusForsView_description_rootNode);
                } else {
                    descText.setText(Messages.SphincsPlusForsView_descText_general);
                }
            }
        };
		graph.addSelectionListener(selectionListenerLeft);

        new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node_1, container_1);
        new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node_1, container_2);
        new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, node_1, container_3);

        /*
         * This graphViewer consists of 2 components: the control and the graph (Figure) We want to
         * give the control a size by the layout and the graph a custom, bigger value. For the
         * control (graphViewer.getControl) I simply grab all available space
         */
        GridDataFactory.fillDefaults().grab(true, true).applyTo(graphViewer.getControl());
        GridDataFactory.fillDefaults().grab(true, true).applyTo(graphViewerSub.getControl());

        // For the graph we have to create a PaintListener.
        graphViewer.getControl().addPaintListener(new PaintListener() {

            @Override
            public void paintControl(PaintEvent e) {
                Point currentShellSize;
                currentShellSize = parent.getSize();
                double x, y;
                x = currentShellSize.x/2;
                y = currentShellSize.y / 1.5;
                graph.getViewport().setSize((int) x, (int) y);
            }
        });
        graphViewerSub.getControl().addPaintListener(new PaintListener() {

            @Override
            public void paintControl(PaintEvent e) {
                Point currentShellSize;
                currentShellSize = parent.getSize();
                double x, y;
                x = currentShellSize.x/2;
                y = currentShellSize.y / 1.5;
                graphSub.getViewport().setSize((int) x, (int) y);
            }
        });
        // The Graph now fills the shell/parent composite,
        // but the actual graph size can be set as we want in the paint //listener

        // Zooming with the class org.eclipse.gef.editparts.ZoomManager
        // As arguments we need a ScalableFigure which we receive by graph.getRootLayer and the
        // Viewport.
        ZoomManager zoomManager = new ZoomManager(graph.getRootLayer(), graph.getViewport());
        ZoomManager zoomManagerSub = new ZoomManager(graphSub.getRootLayer(), graphSub.getViewport());

        // we bind the zoom mechanic to a simple mouse wheel listener
        graph.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseScrolled(MouseEvent e) {
                if (e.count < 0) {
                    zoomManager.zoomOut();
                } else {
                    zoomManager.zoomIn();
                }
            }
        });
        graphSub.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseScrolled(MouseEvent e) {
                if (e.count < 0) {
                    zoomManagerSub.zoomOut();
                } else {
                    zoomManagerSub.zoomIn();
                }
            }
        });

        // Camera Movement
        MouseListener dragListener = new MouseListener() {

            @Override
            public void mouseUp(MouseEvent e) {
                mouseDragging = false;
                zestComposite.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_CROSS));
            }

            @Override
            public void mouseDown(MouseEvent e) {
                mouseDragging = true;
                oldMouse = Display.getCurrent().getCursorLocation();
                viewLocation = graph.getViewport().getViewLocation();

            }

            @Override
            public void mouseDoubleClick(MouseEvent e) {
                // do nothing
            }
        };
        // Camera Movement
        MouseListener dragListenerSub = new MouseListener() {

            @Override
            public void mouseUp(MouseEvent e) {
                mouseDraggingSub = false;
            }

            @Override
            public void mouseDown(MouseEvent e) {
                mouseDraggingSub = true;
                oldMouseSub = Display.getCurrent().getCursorLocation();
                viewLocationSub = graph.getViewport().getViewLocation();

            }

            @Override
            public void mouseDoubleClick(MouseEvent e) {
                // do nothing
            }
        };


        graphViewer.getGraphControl().addMouseListener(dragListener);
        graphViewerSub.getGraphControl().addMouseListener(dragListenerSub);
        MouseMoveListener moveListener = new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if (oldMouse == null) {
					oldMouse = Display.getCurrent().getCursorLocation();
				}
				updateViewLocation();
			}
		};
        MouseMoveListener moveListenerSub = new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent e) {
				if (oldMouseSub == null) {
					oldMouseSub = Display.getCurrent().getCursorLocation();
				}
				updateViewLocationSub();
			}
		};
		graphViewer.getGraphControl().addMouseMoveListener(moveListener);
		graphViewerSub.getGraphControl().addMouseMoveListener(moveListenerSub);
        graphViewer.getControl().addMouseListener(dragListener);
        graphViewerSub.getControl().addMouseListener(dragListener);

        // We give the focus to our graphViewer, so it receives the MouseWheel Events.
        graphViewer.getControl().forceFocus();

        // Prevents the nodes from being dragged around using the mouse.
        // by overriding the mouseMovedListener with empty event
        graph.getLightweightSystem().setEventDispatcher(new SWTEventDispatcher() {
            @Override
			public void dispatchMouseMoved(MouseEvent e) {
            }
        });
        graphSub.getLightweightSystem().setEventDispatcher(new SWTEventDispatcher() {
            @Override
			public void dispatchMouseMoved(MouseEvent e) {
            }
        });

        /**
         * Opening and closing the description expander. Expand to the height of the text.
         */
        descriptionExpander.addExpandListener(new ExpandListener() {
            @Override
            public void itemExpanded(ExpandEvent e) {
                curDisplay.asyncExec(() -> {
                    collapsablePart.setHeight(preferredHeight + 60);
                    descriptionExpander.pack();
                    sphincsPlusForsView.layout();
                    collapsablePart.setText(Messages.SphincsPlusForsView_collapsedPart_expanded);
                });
            }

            @Override
            public void itemCollapsed(ExpandEvent e) {
                curDisplay.asyncExec(() -> {
                    descriptionExpander.pack();
                    sphincsPlusForsView.layout();
                    collapsablePart.setText(Messages.SphincsPlusForsView_collapsedPart_collapsed);
                });
            }
        });
    }

    /**
     * Sets the current view location based on mouse movement.
     */
    private void updateViewLocation() {
    	int differenceMouseX;
    	int differenceMouseY;
    	newMouse = getDisplay().getCursorLocation();
    	if(oldMouse == null) {
    		oldMouse = newMouse;
    	}
    	differenceMouseX = newMouse.x - oldMouse.x;
    	differenceMouseY = newMouse.y - oldMouse.y;

    	if (differenceMouseX != 0 || differenceMouseY != 0) {
    		if (mouseDragging) {
    			graph.getViewport().setViewLocation(viewLocation.x -= differenceMouseX,
    					viewLocation.y -= differenceMouseY);
    			oldMouse = newMouse;
    		}
    	}
    }
    /**
     * Sets the current view location based on mouse movement.
     */
    private void updateViewLocationSub() {
    	int differenceMouseX;
    	int differenceMouseY;
    	newMouseSub = getDisplay().getCursorLocation();
    	if(oldMouseSub == null) {
    		oldMouseSub = newMouseSub;
    	}
    	differenceMouseX = newMouseSub.x - oldMouseSub.x;
    	differenceMouseY = newMouseSub.y - oldMouseSub.y;

    	if (differenceMouseX != 0 || differenceMouseY != 0) {
    		if (mouseDraggingSub) {
    			graphSub.getViewport().setViewLocation(viewLocationSub.x -= differenceMouseX,
    					viewLocationSub.y -= differenceMouseY);
    			oldMouseSub = newMouseSub;
    		}
    	}
    }

    /**
     * Creates a FORS Subtree with four bottom leafs.
     * 
     * @param container_1
     */
    private void createForsSubTree(GraphNode container_1) {
//     	root=new GraphNode
//         container_1.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), false);
        GraphNode root = new GraphNode(graphSub, SWT.NONE, container_1.getText());

        GraphNode node_1 = new GraphNode(graphSub, SWT.NONE, "Node");
        GraphNode node_2 = new GraphNode(graphSub, SWT.NONE, "Node");
        GraphNode node_3 = new GraphNode(graphSub, SWT.NONE, "Leaf");
        GraphNode node_4 = new GraphNode(graphSub, SWT.NONE, "Leaf");
        GraphNode node_5 = new GraphNode(graphSub, SWT.NONE, "Leaf");
        GraphNode node_6 = new GraphNode(graphSub, SWT.NONE, "Leaf");

        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, root, node_1);
        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, root, node_2);
        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, node_1, node_3);
        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, node_1, node_4);
        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, node_2, node_5);
        new GraphConnection(graphSub, ZestStyles.CONNECTIONS_DIRECTED, node_2, node_6);

        root.setBackgroundColor(ColorConstants.lightGreen);
//        container_1.open(true);
//        container_1.applyLayout();
    }

    @Override
    protected void checkSubclass() {
        // we are a composite subclass
    }

}
