package shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import main.GConstants;

public class GOval extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	int X1 = 0;
	int Y1 = 0;

	public GOval() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		this.shape = new Ellipse2D.Double();
		init();
	}

	@Override
	public void setOrigin(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x, y, 0, 0);
		X1 = x;
		Y1 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		int newX = Math.min(x, X1);
		int newY = Math.min(y, Y1);
		int newW = Math.abs(x - X1);
		int newH = Math.abs(y - Y1);
		ellipse.setFrame(newX, newY, newW, newH);
	}

	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void setShape(Shape shape) {
		try {
			Ellipse2D newEllipse = (Ellipse2D) this.shape;
			Ellipse2D originEllipse = (Ellipse2D) shape;
			newEllipse.setFrame(originEllipse.getX(), originEllipse.getY(), originEllipse.getWidth(),
					originEllipse.getHeight());
		} catch (ClassCastException e) {
			Path2D originRectangle = (Path2D) shape;
			this.shape = (Shape) originRectangle.clone();
		}
	}

}
