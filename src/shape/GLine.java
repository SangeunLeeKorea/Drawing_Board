package shape;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import main.GConstants;

public class GLine extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GLine() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Line2D.Double();
		init();
	}

	@Override
	public boolean contains(int x, int y) {
		Path2D path = (Path2D) shape;
		Rectangle rect = path.getBounds();
		Point2D lastP = path.getCurrentPoint();
		Line2D line = new Line2D.Double();
		line.setLine(0, 0, lastP.getX(), lastP.getY());
		if (rect.getX() == lastP.getX()) {
			line.setLine(rect.x+rect.width, 0, line.getX2(), line.getY2());
		} else {
			line.setLine(rect.x, 0, line.getX2(), line.getY2());
		}
		if (rect.getY() == lastP.getY()) {
			line.setLine(line.getX1(), rect.y+rect.height, line.getX2(), line.getY2());
		} else {
			line.setLine(line.getX1(), rect.y, line.getX2(), line.getY2());
		}
		
		if (line.ptLineDist(x, y) < 7 && line.getBounds().contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setOrigin(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void setShape(Shape shape) {
		try {
			Line2D originLine = (Line2D) shape;
			this.shape = (Shape) originLine.clone();
		} catch (ClassCastException e) {
			Path2D originRectangle = (Path2D) shape;
			this.shape = (Shape) originRectangle.clone();
		}
	}

}
