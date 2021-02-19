package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import main.GConstants;
import main.GConstants.ECursor;
import main.GConstants.ELineDot;
import main.GConstants.ELineThick;

public abstract class GShape implements Serializable, Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	public enum EDrawingStyle {
		e2Points, eNPoints, eNoDrawing, eText
	}

	protected EDrawingStyle eDrawingStyle;
	private ELineDot eLineDot;
	private ELineThick eLineThick;

	protected int originX;
	protected int originY;
	protected double centerX;
	protected double centerY;
	protected double totalDx;
	protected double totalDy;
	protected double originW;
	protected double originH;
	private String resizeX, resizeY;
	private double scale;
	protected Shape shape;
	protected Color fillColor;
	protected Color lineColor;
	protected GAnchors anchors;

	public GShape() {
		this.fillColor = Color.WHITE;
		this.lineColor = Color.BLACK;
		this.eLineDot = ELineDot.e0f;
		this.eLineThick = ELineThick.e1;
		this.scale = 0;
	}

	public void init() {
		anchors = new GAnchors(shape.getBounds());
	}

	public EDrawingStyle getEDrawingStyle() {
		return this.eDrawingStyle;
	}

	public void draw(Graphics graphics) {
		Graphics2D g2d = (Graphics2D) graphics;
		float[] array = eLineDot.getArray();
		g2d.setColor(fillColor);
		g2d.fill(shape);
		g2d.setColor(lineColor);
		g2d.setStroke(
				new BasicStroke(eLineThick.getValue(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, array, 0));
		g2d.draw(shape);
	}

	public GShape clone() {
		GShape shape = null;
		try {
			shape = this.getClass().getDeclaredConstructor().newInstance();
			shape.setFillColor(this.fillColor);
			shape.setLineColor(this.lineColor);
			shape.setShape(this.getShape());
			shape.eLineThick = this.eLineThick;
			shape.eLineDot = this.eLineDot;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return shape;
	}

	public abstract void setShape(Shape shape);

	public abstract void setOrigin(int x, int y);

	public abstract void setPoint(int x, int y);

	public abstract void addPoint(int x, int y);

	public void move(int dx, int dy) {
		Path2D tempShape = (Path2D) this.shape;
		AffineTransform at = new AffineTransform();
		at.translate(dx, dy);
		tempShape.transform(at);
	}

	public void resize(int dx, int dy, String x, String y) {
		if (scale == 0) {
			scale = shape.getBounds().getHeight() / shape.getBounds().getWidth();
		}
		totalDx += dx;
		totalDy += dy;
		Path2D tempShape = (Path2D) this.shape;
		AffineTransform at = new AffineTransform();
		if (x != null & y != null) {
			transformXY(dx, dy, x, y, at);
		} else if (x != null) {
			transformX(dx, x, at);
		} else if (y != null) {
			transformY(dy, y, at);
		}
		tempShape.transform(at);
		double width = shape.getBounds().getWidth();
		double height = shape.getBounds().getHeight();
		scale = height / width;
	}

	private void transformXY(double dx, double dy, String x, String y, AffineTransform at) {
		double width = shape.getBounds().getWidth();
		double height = shape.getBounds().getHeight();
		double xPoint = shape.getBounds().getX();
		double yPoint = shape.getBounds().getY();
		double newW = getNewLength(totalDx, originW, x);
		double newH = getNewLength(totalDy, originH, y);
		if (scale > newH / newW) {
			newH = scale * newW;
		} else {
			newW = newH / scale;
		}
		dx = newW-width;
		dy = newH-height;
		double scale = newH/height;
		boolean addDx = x.equals("W");
		boolean addDy = y.equals("N");
		if (addDx && addDy) {
			at.translate(xPoint * (1 - scale) - dx, yPoint * (1 - scale) - dy);
		} else if (addDx && !addDy) {
			at.translate(xPoint * (1 - scale) - dx, yPoint * (1 - scale));
		} else if (!addDx && addDy) {
			at.translate(xPoint * (1 - scale), yPoint * (1 - scale) - dy);
		} else {
			at.translate(xPoint * (1 - scale), yPoint * (1 - scale));
		}
		at.scale(scale, scale);
	}

	private double getNewLength(double dx, double origin, String type) {
		switch (type) {
		case "W":
		case "N":
			return origin - dx;
		case "E":
		case "S":
			return origin + dx;
		}
		return 0;
	}

	private void transformX(int dx, String x, AffineTransform at) {
		Rectangle bound = this.shape.getBounds();
		double width = bound.getWidth();
		if (x.equals("W")) {
			double scaleX = (width - dx) / width;
			at.translate(bound.getX() * (1 - scaleX) + dx, 0);
			at.scale(scaleX, 1);
		} else if (x.equals("E")) {
			double scaleX = (width + dx) / width;
			at.translate(bound.getX() * (1 - scaleX), 0);
			at.scale(scaleX, 1);
		}
	}

	private void transformY(int dy, String y, AffineTransform at) {
		Rectangle bound = this.shape.getBounds();
		double height = bound.getHeight();
		if (y.equals("N")) {
			double scaleY = (height - dy) / height;
			at.translate(0, bound.getY() * (1 - scaleY) + dy);
			at.scale(1, scaleY);
		} else if (y.equals("S")) {
			double scaleY = (height + dy) / height;
			at.translate(0, bound.getY() * (1 - scaleY));
			at.scale(1, scaleY);
		}
	}

	public void toPath() {
		AffineTransform at = new AffineTransform();
		this.shape = at.createTransformedShape(this.shape);
	}

	public void initTransforming(int x, int y) {
		originX = x;
		originY = y;
	}

	public void keepTransforming(int x, int y) {
		this.move(x - this.originX, y - this.originY);
		originX = x;
		originY = y;
	}

	public void finishTransforming(int x, int y) {
		originX = x;
		originY = y;
		toPath();
	}

	public void initResize(int x, int y, ECursor eCursor) {
		originX = x;
		originY = y;
		resizeX = null;
		resizeY = null;
		totalDx = 0;
		totalDy = 0;
		originW = shape.getBounds().getWidth();
		originH = shape.getBounds().getHeight();

		if (eCursor == ECursor.eEE || eCursor == ECursor.eNE || eCursor == ECursor.eSE) {
			resizeX = "E";
		} else if (eCursor == ECursor.eWW || eCursor == ECursor.eNW || eCursor == ECursor.eSW) {
			resizeX = "W";
		}

		if (eCursor == ECursor.eNN || eCursor == ECursor.eNE || eCursor == ECursor.eNW) {
			resizeY = "N";
		} else if (eCursor == ECursor.eSS || eCursor == ECursor.eSE || eCursor == ECursor.eSW) {
			resizeY = "S";
		}
	}

	public void keepResize(int x, int y) {
		this.resize(x - originX, y - originY, resizeX, resizeY);
		originX = x;
		originY = y;
	}

	public void finishResize(int x, int y) {
		
	}

	public void initRotate(int x, int y) {
		originX = x;
		originY = y;
		centerX = shape.getBounds().getCenterX();
		centerY = shape.getBounds().getCenterY();
	}

	public void keepRotate(int x, int y) {
		this.rotate(centerX, centerY, originX, originY, x, y);
		originX = x;
		originY = y;
	}

	public void finishRotate(int x, int y) {

	}

	protected double rotate(double centerX, double centerY, int originX, int originY, int x, int y) {
		double tempDegree = getDegree(centerX, centerY, originX, originY, x, y);
		AffineTransform at = new AffineTransform();
		if (tempDegree < -3) {
			tempDegree += Math.PI;
		}
		if (tempDegree < 0) {
			tempDegree += Math.PI * 2;
		}
		at.rotate(tempDegree, centerX, centerY);
		this.shape = at.createTransformedShape(this.shape);
		return tempDegree;
	}

	public void rotateByDegree(double centerX, double centerY, double degree) {
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(degree), centerX, centerY);
		this.shape = at.createTransformedShape(this.shape);
	}

	public double getDegree(double x0, double y0, double x1, double y1, double x2, double y2) {
		double d1 = Math.atan((x2 - x0) / (y2 - y0));
		double d2 = Math.atan((x1 - x0) / (y1 - y0));
		return d2 - d1;
	}

	public GAnchors getAnchors() {
		return anchors;
	}

	public void drawAnchors(Graphics graphics) {
		setAnchors();
		anchors.drawAnchors(graphics);
	}

	public void setAnchors() {
		anchors.setAnchors(this.shape.getBounds());
	}

	public Color getFillColor() {
		return fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public void setLineColor(Color color) {
		this.lineColor = color;
	}

	public Shape getShape() {
		return shape;
	}

	public Rectangle getBounds() {
		return shape.getBounds();
	}

	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}

	public boolean contains(Rectangle compare) {
		Rectangle original = shape.getBounds();
		boolean x1 = original.x < compare.x;
		boolean y1 = original.y < compare.y;
		boolean x2 = original.x + original.width > compare.x + compare.width;
		boolean y2 = original.y + original.height > compare.y + compare.height;
		return x1 && y1 && x2 && y2;
	}

	public void setLineDot(ELineDot eLineDot) {
		this.eLineDot = eLineDot;
	}

	public void setLineThick(ELineThick eLineThick) {
		this.eLineThick = eLineThick;
	}

	public Object getLineThick() {
		return this.eLineThick.getValue();
	}

	public Object getLineDot() {
		return this.eLineDot.getSecond();
	}

	public void setFlip(int x, int y) {
		AffineTransform at = new AffineTransform();
		Rectangle bound = shape.getBounds();
		System.out.println(bound.getX());
		System.out.println(bound.getWidth());
		if (x > y) {
			at.translate(0, bound.getY() * 2 + bound.getHeight());
			at.scale(1, -1);
		} else {
			at.translate(bound.getX() * 2 + bound.getWidth(), 0);
			at.scale(-1, 1);
		}
		this.shape = at.createTransformedShape(this.shape);
		System.out.println(shape.getBounds().getX());
		System.out.println(shape.getBounds().getWidth());
	}

	public void setShapeType(int points) {}
}
