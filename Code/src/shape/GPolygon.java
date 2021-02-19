package shape;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Arrays;

import main.GConstants;

public class GPolygon extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	Rectangle bound;

	boolean twoPoint = true;

	public final static int nMaxPoints = GConstants.MAXPOINTS;

	public GPolygon() {
		this.eDrawingStyle = EDrawingStyle.eNPoints;

		this.shape = new Line2D.Double();
		init();
	}

	@Override
	public void setOrigin(int x, int y) {
		Line2D polygon = (Line2D) this.shape;
		polygon.setLine(x, y, x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		if (twoPoint) {
			Line2D polygon = (Line2D) this.shape;
			polygon.setLine(polygon.getX1(), polygon.getY1(), x, y);
		} else {
			Polygon polygon = (Polygon) this.shape;
			int nPoints = polygon.npoints;
			polygon.xpoints[nPoints - 1] = x;
			polygon.ypoints[nPoints - 1] = y;
		}
	}

	@Override
	public void addPoint(int x, int y) {
		Polygon polygon;
		if (twoPoint) {
			Line2D line = (Line2D) this.shape;
			polygon = new Polygon();
			polygon.addPoint((int) line.getX1(), (int) line.getY1());
			polygon.addPoint((int) line.getX2(), (int) line.getY2());
			this.shape = polygon;
			twoPoint = false;
		}
		polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
	}

	@Override
	public void setShape(Shape shape) {
		try {
			Line2D originPolygon = (Line2D) shape;
			this.shape = (Shape) originPolygon.clone();
		} catch (ClassCastException e1) {
			try {
				Polygon originPolygon = (Polygon) shape;
				Polygon newPolygon = new Polygon();
				this.shape = newPolygon;
				newPolygon.xpoints = originPolygon.xpoints.clone();
				newPolygon.ypoints = originPolygon.ypoints.clone();
				newPolygon.npoints = originPolygon.npoints;
			} catch (ClassCastException e2) {
				Path2D originRectangle = (Path2D) shape;
				this.shape = (Shape) originRectangle.clone();
			}
		}
	}
}
