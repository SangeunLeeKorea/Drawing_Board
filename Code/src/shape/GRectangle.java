package shape;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;

import main.GConstants;

public class GRectangle extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	int X1 = 0;
	int Y1 = 0;

	public GRectangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		this.shape = new Rectangle();
		init();
	}

	@Override
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
		X1 = x;
		Y1 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.x = Math.min(x, X1);
		rectangle.y = Math.min(y, Y1);
		int newW = Math.abs(x - X1);
		int newH = Math.abs(y - Y1);
		rectangle.setSize(newW, newH);
	}

	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void setShape(Shape shape) {
		try {
			Rectangle originRectangle = (Rectangle) shape;
			this.shape = (Shape) originRectangle.clone();
		} catch (ClassCastException e) {
			Path2D originRectangle = (Path2D) shape;
			this.shape = (Shape) originRectangle.clone();
		}
	}
}
