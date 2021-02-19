package shape;

import java.awt.Polygon;

public class GNShape extends GPolygon {
	
	int points = 0;

	public GNShape() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		this.shape = new Polygon();
		init();
	}
	
	public void setShapeType(int points) {
		this.points = points;
	}

	@Override
	public void setOrigin(int x, int y) {
		Polygon nShape = (Polygon)shape;
		this.originX = x;
		this.originY = y;
		while(nShape.npoints<points) {
			nShape.addPoint(x, y);
		}
	}

	@Override
	public void setPoint(int x, int y) {
		//각도 계산해서 하기
		Polygon nShape = (Polygon)shape;
		double degree = 360/points;
		int[] xPoints = nShape.xpoints;
		int[] yPoints = nShape.ypoints;
		int w = x-originX;
		int h = y-originY;
		if(w<h) {
			w = h;
		}
		xPoints[0] = originX +w/2;
		yPoints[0] = originY;
		for (int i=1;i<points;i++) {
			double radian = Math.toRadians(degree*(i));
			double cos = Math.cos(radian/2);
			double sin = Math.sin(radian/2);
			xPoints[i] = (int) (cos*sin*w) + xPoints[0];
			yPoints[i] = (int) (sin*sin*w) + yPoints[0];
		}
	}
	
	@Override
	public void addPoint(int x, int y) {
	}
	
	@Override
	public GShape clone() {
		GShape shape = super.clone();
		shape.setShapeType(points);
		return shape;
	}

}
