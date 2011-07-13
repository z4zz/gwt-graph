package graph.client;

import graph.client.domain.Point;
import graph.client.model.CanvasNode;

import java.util.LinkedList;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;

public class GraphComponent extends Composite {

	int gridsize = 30;

	final LinkedList<NodeProxy> proxys = new LinkedList<NodeProxy>();

	final LinkedList<NodeProxy> objectLayer = new LinkedList<NodeProxy>();
	final LinkedList<NodeProxy> movingLayer = new LinkedList<NodeProxy>();

	private NodeProxy moving = null; 
	
	private final CanvasContainer canvasContainer;
	int width = 1000;
	int height = 2000;

	public GraphComponent() {
		canvasContainer = new CanvasContainer(width, height);
		initWidget(new ScrollPanel(canvasContainer));

		initCanvas();
	}

	private void initCanvas() {
		canvasContainer.addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				Point point = new Point(event.getX(), event.getY());

				NodeProxy nodeAt = getFirstNodeAt(point);

				if (nodeAt != null) {
					objectLayer.remove(nodeAt);
					movingLayer.add(nodeAt);
					moving = nodeAt;
				} else {
					CanvasNode node = new CanvasNode();
					Point click = adjustToGrid(point);

					node.setX(click.getX());
					node.setY(click.getY());

					NodeProxy proxy = new NodeProxy(node);
					proxy.drawExample();

					proxys.add(proxy);
					objectLayer.add(proxy);
				}
				redrawAll();
			}
		});
		canvasContainer.addMouseMoveHandler(new MouseMoveHandler() {
			
			public void onMouseMove(MouseMoveEvent event) {
				if (moving != null) {
					Point p = getPointerPoint(event);
					
					p = adjustToGrid(p);
					if (!p.equals(moving.getModel().getPosition() ) ) {
						moving.getModel().setPosition(p);
						redrawMoving();
					}
				}
			}
		});
		
		canvasContainer.addMouseUpHandler(new MouseUpHandler() {
			
			public void onMouseUp(MouseUpEvent event) {
				if (moving != null) {
					movingLayer.remove(moving);
					objectLayer.add(moving);
					moving = null;
					redrawAll();
				}
			}
		});
	}

	private Point getPointerPoint(MouseEvent<?> event) {
		return new Point(event.getX(), event.getY());
	}
	
	protected Point adjustToGrid(Point p) {
		int newX = 0;
		int newY = 0;

		newX = p.getX() - (p.getX() % gridsize);
		newY = p.getY() - (p.getY() % gridsize);

		if (newX < 0)
			newX = 0;

		if (newX > width)
			newX = width;

		if (newY < 0)
			newY = 0;

		if (newY > height)
			newY = height;

		return new Point(newX, newY);
	}

	public void redrawAll() {
		redrawObject();
		redrawMoving();
	}
	
	public void redrawObject() {
		canvasContainer.getObjects().clearRect(0, 0, width, height);
		for (NodeProxy proxy : objectLayer) {
			canvasContainer.getObjects().drawImage(proxy.getImage().getCanvasElement(), proxy.getModel()
					.getX(), proxy.getModel().getY());
			
		}
	}
	
	public void redrawMoving() {
		canvasContainer.getMoving().clearRect(0, 0, width, height);
		for (NodeProxy proxy : movingLayer) {
			canvasContainer.getMoving().drawImage(proxy.getImage().getCanvasElement(), proxy.getModel()
					.getX(), proxy.getModel().getY());
		}
	}

	private NodeProxy getFirstNodeAt(Point p) {
		for (NodeProxy node : objectLayer) {
			if (node.hit(p)) {
				return node;
			}
		}
		return null;

	}

	public void drawBackground() {
		Context2d bg = canvasContainer.getBackground();
		bg.save();

		bg.beginPath();
		bg.setStrokeStyle("#dcdcdc");
		bg.setLineWidth(1);
		for (int i = 0; i < height; i = i + gridsize) {
			if (i % (5 * gridsize) == 0) {
				continue;
			}
			bg.moveTo(0, i);
			bg.lineTo(width, i);

		}

		for (int i = 0; i < width; i = i + gridsize) {
			if (i % (5 * gridsize) == 0) {
				continue;
			}

			bg.moveTo(i, 0);
			bg.lineTo(i, height);
		}

		bg.stroke();

		bg.beginPath();
		bg.setStrokeStyle("#808080");
		bg.setLineWidth(1);

		for (int i = 0; i < width; i = i + 5 * gridsize) {
			bg.moveTo(i, 0);
			bg.lineTo(i, height);
		}

		for (int i = 0; i < height; i = i + 5 * gridsize) {
			bg.moveTo(0, i);
			bg.lineTo(width, i);
		}

		bg.stroke();

		bg.restore();
	}

	public void clearBackground() {
		Context2d background = canvasContainer.getBackground();
		background.clearRect(0, 0, width, height);
	}

	public void clear() {
		canvasContainer.getObjects().clearRect(0, 0, width, height);

	}

}