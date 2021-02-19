package shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Vector;

import main.GConstants;

public class GGroup extends GShape {
	
	//anchor 만들기, group anchor 그려진 상태에서 한 번 더 클릭되면 그 도형 선택되도록 하기
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	private Vector<GShape> grouped;
	
	public GGroup() {
		this.grouped = new Vector<GShape>();
	}
	
	public void addGrouped(GShape shape) {
		this.grouped.add(shape);
		this.anchors = new GAnchors(this.getBounds());
	}
	
	public GShape getGrouped(int i) {
		return grouped.get(i);
	}
	
	public int size() {
		return this.grouped.size();
	}
	
	@Override
	public boolean contains(int x, int y) {
		boolean check = false;
		for (GShape shape:grouped) {
			if (shape.contains(x, y)) {
				check = true;
			}
		}
		return check;
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle bound = grouped.get(0).getBounds();
		for (int i=1;i<grouped.size();i++) {
			Rectangle temp = grouped.get(i).getBounds();
			int x1 = Math.min(bound.x, temp.x);
			int y1 = Math.min(bound.y, temp.y);
			int x2 = Math.max(bound.x+bound.width, temp.x+temp.width);
			int y2 = Math.max(bound.y+bound.height, temp.y+temp.height);
			bound.x = x1;
			bound.y = y1;
			bound.width = x2 - x1;
			bound.height = y2 - y1;
		}
		return bound;
	}
	
	@Override
	public void draw(Graphics graphics) {
		for (GShape shape:grouped) {
			shape.draw(graphics);
		}
	}
	
	@Override
	public void drawAnchors(Graphics graphics) {
		anchors.setAnchors(this.getBounds());
		anchors.drawAnchors(graphics);
		
	}
	
	public void drawSepetrateAnchors(Graphics graphics) {
		for (GShape shape:grouped) {
			shape.drawAnchors(graphics);
		}
	}
	
	@Override
	public void move(int dx, int dy) {
		for (GShape shape:grouped) {
			shape.move(dx, dy);
		}
	}

	@Override
	public void resize(int dx, int dy, String x, String y) {
		for (GShape shape:grouped) {
			shape.resize(dx, dy, x, y);
		}
	}
	
	@Override
	public void initRotate(int x, int y) {
		originX = x;
		originY = y;
		centerX = getBounds().getCenterX();
		centerY = getBounds().getCenterY();
	}
	
	@Override
	public void keepRotate(int x, int y) {
		double degree = getDegree(centerX, centerY, originX, originY, x, y);
		for (GShape shape:grouped) {
			shape.rotateByDegree(centerX, centerY, degree);
		}
		originX = x;
		originY = y;
	}
	
	@Override
	public void setFillColor(Color color) {
		for (GShape shape:grouped) {
			shape.setFillColor(color);
		}
	}

	@Override
	public void setLineColor(Color color) {
		for (GShape shape:grouped) {
			shape.setLineColor(color);
		}
	}
	
	@Override
	public void rotateByDegree(double centerX, double centerY, double degree) {
		for (GShape shape:grouped) {
			shape.rotateByDegree(centerX, centerY, degree);
		}
	}
	
	//unused method
	@Override
	public void setShape(Shape shape) {}
	@Override
	public void setOrigin(int x, int y) {}
	@Override
	public void setPoint(int x, int y) {}
	@Override
	public void addPoint(int x, int y) {}

}
