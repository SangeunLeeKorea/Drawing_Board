package shape;

import java.awt.Shape;
import java.awt.geom.Path2D;

import main.GConstants;

public class GHeart extends GShape {

	private int X0;
	private int Y0;

	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GHeart() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		
		this.shape = new Path2D.Double();
		init();
	}

	@Override
	public void setShape(Shape shape) {
		Path2D heart = (Path2D) shape;
		this.shape = (Shape) heart.clone();
	}

	@Override
	public void setOrigin(int x, int y) {
		this.X0 = x;
		this.Y0 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Path2D path = new Path2D.Double();
		double w = x - X0;
		double h = y - Y0;
		path.moveTo(X0+w/2, Y0+h*1/3);
		path.curveTo(X0+w/2, Y0+h*1/3, X0+w*5/12, Y0+h*1/40, X0+w*1/4, Y0);
		path.curveTo(X0+w*1/4, Y0, X0+w*1/15, Y0+h*1/100, X0, Y0+h*2/5);
		path.curveTo(X0, Y0+h*2/5, X0+w/16, Y0+h*3/5, X0+w/2, y);
		path.curveTo(X0+w/2, y, X0+w*15/16, Y0+h*3/5, x, Y0+h*2/5);
		path.curveTo(x, Y0+h*2/5, X0+w*14/15, Y0+h*1/100, X0+w*3/4, Y0);
		path.curveTo(X0+w*3/4, Y0, X0+w*7/12, Y0+h*1/40, X0+w/2, Y0+h*1/3);
		this.shape = path;
	}

	@Override
	public void addPoint(int x, int y) {
	}
}
