package shape;

import java.awt.Polygon;

import main.GConstants;

public class GTriangle extends GPolygon {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GTriangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Polygon();
	}

	@Override
	public void setOrigin(int x, int y) {
		Polygon triangle = (Polygon) this.shape;
		triangle.npoints = 3;
		for (int i = 0; i < 3; i++) {
			triangle.xpoints[i] = x;
			triangle.ypoints[i] = y;
		}
	}

	@Override
	public void setPoint(int x, int y) {
		Polygon triangle = (Polygon) this.shape;
		int w = x - triangle.xpoints[1];
		triangle.xpoints[0] = (int) (triangle.xpoints[1] + w * 0.5);
		triangle.xpoints[2] = x;
		triangle.ypoints[1] = y;
		triangle.ypoints[2] = y;
	}

	@Override
	public void addPoint(int x, int y) {
	}
}
