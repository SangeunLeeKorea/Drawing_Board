package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import javax.imageio.ImageIO;

import main.GConstants;
import main.GConstants.EAnchor;
import main.GConstants.ECursor;

public class GAnchors implements Serializable {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	public enum EAnchors {
		NW, WW, SW, NN, SS, NE, EE, SE;
	}

	private Vector<Ellipse2D> dots;
	private Rectangle bound, rotateBound;

	public GAnchors(Rectangle bound) {
		setAnchors(bound);
		rotateBound = new Rectangle();
	}

	public void setAnchors(Rectangle bound) {
		this.bound = bound;
		this.dots = new Vector<Ellipse2D>();
		int wHalf = bound.width / 2;
		int hHalf = bound.height / 2;
		int radius = EAnchor.eRadius.getValue();
		int i = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++, i++) {
				if (!(x == 1 && y == 1)) {
					EAnchors eAnchor = EAnchors.values()[i];
					ResizeDot anchor = new ResizeDot();
					Point point = new Point(bound.x + (x * wHalf) - radius, bound.y + (y * hHalf) - radius);
					anchor.setFrame(point.x, point.y, radius * 2, radius * 2);
					anchor.setEnum(eAnchor);
					this.dots.add(anchor);
				} else
					i--;
			}
		}
	}

	public void drawAnchors(Graphics graphics) {
		int wHalf = bound.width / 2;
		int lineLength = EAnchor.eLineLength.getValue();
		int rotateSize = EAnchor.eRotateSize.getValue();
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setColor(Color.GRAY);
		float array[] = { 1, 0f };
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, array, 0));
		g2d.draw(bound);
		g2d.drawLine(bound.x + wHalf, bound.y, bound.x + wHalf, bound.y - lineLength);
		for (Ellipse2D shape : dots) {
			g2d.fill(shape);
		}
		try {
			g2d.drawImage(ImageIO.read(new File("data/rotate.png")), bound.x + wHalf - (rotateSize / 2),
					bound.y - rotateSize - lineLength, rotateSize, rotateSize, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		rotateBound.setFrame(bound.x + wHalf - (rotateSize / 2), bound.y - rotateSize - lineLength, rotateSize,
				rotateSize);
	}

	public Rectangle getRotateBound() {
		return rotateBound;
	}

	@SuppressWarnings("serial")
	class ResizeDot extends Ellipse2D.Double {
		EAnchors eAnchor;
		ECursor eCursor;

		@SuppressWarnings("static-access")
		public void setEnum(EAnchors eAnchor) {
			this.eAnchor = eAnchor;
			this.eCursor = eCursor.valueOf("e" + eAnchor.toString());
		}

		public ECursor getECursor() {
			return eCursor;
		}
	}

	public Vector<Ellipse2D> getResizeDots() {
		return dots;
	}

	public ECursor getECursor(Ellipse2D dot) {
		ResizeDot resizedot = (ResizeDot) dot;
		return resizedot.getECursor();
	}
}
