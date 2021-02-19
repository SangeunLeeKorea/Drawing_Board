package shape;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import main.GConstants;

public class GRoundRectangle extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	int X1 = 0;
	int Y1 = 0;
	int arcWidth = 20;
	int arcHeight = 20;

	public GRoundRectangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		this.shape = new RoundRectangle2D.Double();
		init();
	}

	@Override
	public void setOrigin(int x, int y) {
		RoundRectangle2D roundR = (RoundRectangle2D) this.shape;
		roundR.setRoundRect(x, y, 0, 0, arcWidth, arcHeight);
		X1 = x;
		Y1 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		RoundRectangle2D roundR = (RoundRectangle2D) this.shape;
		int newX = Math.min(x, X1);
		int newY = Math.min(y, Y1);
		int newW = Math.abs(x - X1);
		int newH = Math.abs(y - Y1);
		roundR.setFrame(newX, newY, newW, newH);
	}

	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void setShape(Shape shape) {
		try {
			RoundRectangle2D originRoundR = (RoundRectangle2D) shape;
			this.shape = (Shape) originRoundR.clone();
		} catch (ClassCastException e) {
			Path2D originRectangle = (Path2D) shape;
			this.shape = (Shape) originRectangle.clone();
		}
	}
}
