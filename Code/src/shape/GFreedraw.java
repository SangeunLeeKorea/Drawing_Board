package shape;

import java.awt.Shape;
import java.awt.geom.Path2D;

import main.GConstants;

public class GFreedraw extends GShape {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GFreedraw() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		
		this.shape = new Path2D.Double();
		init();
	}

	@Override
	public void setShape(Shape shape) {
		Path2D drawing = (Path2D) shape;
		this.shape = (Shape) drawing.clone();
	}

	@Override
	public void setOrigin(int x, int y) {
		Path2D drawing = (Path2D)shape;
		drawing.moveTo(x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		Path2D drawing = (Path2D)shape;
		drawing.lineTo(x, y);
	}

	@Override
	public void addPoint(int x, int y) {
	}
}
