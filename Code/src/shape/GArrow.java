package shape;

import java.awt.Polygon;

import main.GConstants;

public class GArrow extends GPolygon {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	int X1;
	int Y1;

	public GArrow() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Polygon();
	}
	
	@Override
	public void setOrigin(int x, int y) {
		Polygon arrow = (Polygon) this.shape;
		arrow.xpoints[0] = x;
		arrow.ypoints[0] = y;
		for (int i = 0; i < 7; i++) {
			arrow.addPoint(x, y);
		}
		X1 = x;
		Y1 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		int w = x-X1;
		int h = y-Y1;
		Polygon arrow = (Polygon) this.shape;
		//맨 위부터 시계반대방향
		arrow.xpoints[0] = X1+w*2/3;
		arrow.xpoints[1] = X1+w;
		arrow.xpoints[2] = arrow.xpoints[0];
		arrow.xpoints[3] = arrow.xpoints[0];
		arrow.xpoints[6] = arrow.xpoints[0];
		
		arrow.ypoints[1] = Y1+h/2;
		arrow.ypoints[2] = Y1+h;
		arrow.ypoints[3] = Y1+h*5/6;
		arrow.ypoints[4] = arrow.ypoints[3];
		arrow.ypoints[5] = Y1+h/6;
		arrow.ypoints[6] = arrow.ypoints[5];
	}

	@Override
	public void addPoint(int x, int y) {
	}

}
