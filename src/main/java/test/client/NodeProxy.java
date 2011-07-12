package test.client;

import test.client.model.CanvasNode;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.touch.client.Point;

public class NodeProxy {

	private Canvas image;
	
	private final Context2d ctx;

	private final CanvasNode model;
	
	public NodeProxy(CanvasNode model) {
		this.model = model;
		image = Canvas.createIfSupported();
		ctx = image.getContext2d();
		
		setSizeFromModel();
	}

	private void setSizeFromModel() {
		image.setPixelSize(model.getWidth(), model.getHeight());
		image.setCoordinateSpaceHeight(model.getHeight());
		image.setCoordinateSpaceWidth(model.getWidth());
	}
	
	public Context2d getContext() {
		return ctx;
	}

	public Canvas getImage() {
		return image;
	}

	public CanvasNode getModel() {
		return model;
	}
	
	public boolean hit(Point p) {
		
	}

	public void drawExample() {
		model.setWidth(104);
		model.setHeight(74);
		setSizeFromModel();

		Point o = new Point(2,2);
		
		ctx.setFillStyle("yellow");
		ctx.moveTo(90+o.getX(), o.getY());
		ctx.arcTo(100+o.getX(), o.getY(), 100+o.getX(), 10+o.getY(), 10);
		ctx.lineTo(100+o.getX(), 60+o.getY());
		ctx.arcTo(100+o.getX(), 70+o.getY(), 90+o.getX(), 70+o.getY(), 10);
		ctx.lineTo(10+o.getX(), 70+o.getY());
		ctx.arcTo(o.getX(), 70+o.getY(), o.getY(), 60+o.getX(), 10);
		ctx.lineTo(o.getX(), 10+o.getY());
		ctx.arcTo(o.getX(), o.getY(), 10+o.getX(), o.getY(), 10);
		ctx.lineTo(90+o.getX(), o.getY());
		ctx.fill();
		
		ctx.setFillStyle("black");
		ctx.setLineWidth(2);
		ctx.moveTo(90+o.getX(), o.getY());
		ctx.arcTo(100+o.getX(), o.getY(), 100+o.getX(), 10+o.getY(), 10);
		ctx.lineTo(100+o.getX(), 60+o.getY());
		ctx.arcTo(100+o.getX(), 70+o.getY(), 90+o.getX(), 70+o.getY(), 10);
		ctx.lineTo(10+o.getX(), 70+o.getY());
		ctx.arcTo(o.getX(), 70+o.getY(), o.getY(), 60+o.getX(), 10);
		ctx.lineTo(o.getX(), 10+o.getY());
		ctx.arcTo(o.getX(), o.getY(), 10+o.getX(), o.getY(), 10);
		ctx.lineTo(90+o.getX(), o.getY());
		ctx.stroke();
		
	}
}