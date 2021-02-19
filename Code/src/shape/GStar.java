package shape;

import java.awt.Polygon;

import main.GConstants;

public class GStar extends GPolygon {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	int X1 = 0;
	int Y1 = 0;

	public GStar() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		this.shape = new Polygon();
		
	}

	@Override
	public void setOrigin(int x, int y) {
		Polygon star = (Polygon) this.shape;
		star.xpoints[0] = x;
		star.ypoints[0] = y;
		for (int i = 0; i < 10; i++) {
			star.addPoint(x, y);
		}
		X1 = x;
		Y1 = y;
	}

	@Override
	public void setPoint(int x, int y) {
		Polygon star = (Polygon) this.shape;
		//맨 위부터 시계반대방향
		star.xpoints[0] = (x-X1)/2+X1;
		star.xpoints[1] = (int) ((x-X1)/4*2.5+X1);
		star.xpoints[2] = x;
		star.xpoints[3] = (int) ((x-X1)/4*2.75+X1);
		star.xpoints[4] = (x-X1)/4*3+X1;
		star.xpoints[5] = star.xpoints[0];
		star.xpoints[6] = (x-X1)/4+X1;
		star.xpoints[7] = (int) ((x-X1)/4*1.25+X1);
		star.xpoints[9] = (int) ((x-X1)/4*1.5+X1);
		
		star.ypoints[1] = (y-Y1)/3+Y1;
		star.ypoints[2] = star.ypoints[1];
		star.ypoints[3] = (int) ((y-Y1)/9*5.15+Y1);
		star.ypoints[4] = y;
		star.ypoints[5] = (y-Y1)/9*7+Y1;
		star.ypoints[6] = y;
		star.ypoints[7] = star.ypoints[3];
		star.ypoints[8] = star.ypoints[1];
		star.ypoints[9] = star.ypoints[1];
	}

	@Override
	public void addPoint(int x, int y) {
	}
}
