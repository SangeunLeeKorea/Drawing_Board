package shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.GConstants;

public class GImage extends GShape {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	private double degree = 0;
	private File imageFile;

	public GImage() {
		this.eDrawingStyle = EDrawingStyle.eNoDrawing;
		this.shape = new Rectangle();
		this.setOrigin(0, 0);
		this.setPoint(0, 0);
		this.init();
	}

	public void setImage(File imageFile) {
		try {
			this.imageFile = imageFile;
			BufferedImage img = ImageIO.read(imageFile);
			this.setPoint(img.getWidth(), img.getHeight());
			anchors.setAnchors((Rectangle) shape);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "파일이 더이상 존재하지 않습니다.\n새로운 파일 경로를 선택하세요.", "파일 오류", JOptionPane.ERROR_MESSAGE);
			setNewImage();
		}
	}
	
	public void setNewImage() {
		JFileChooser fileChooser = new JFileChooser(new File("./image"));
		FileFilter jpgF = new FileNameExtensionFilter("JPG", "jpg");
		FileFilter jpegF = new FileNameExtensionFilter("JPEG", "jpeg");
		FileFilter pngF = new FileNameExtensionFilter("PNG", "png");
		fileChooser.addChoosableFileFilter(jpgF);
		fileChooser.addChoosableFileFilter(jpegF);
		fileChooser.addChoosableFileFilter(pngF);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File imageFile = fileChooser.getSelectedFile();
			shape = new Rectangle();
			this.setImage(imageFile);
			toPath();
		}
	}

	@Override
	public void draw(Graphics graphics) {
		Rectangle bound = this.shape.getBounds();
		BufferedImage img = null;
		try {
			img = ImageIO.read(imageFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "파일이 더이상 존재하지 않습니다.\n새로운 파일 경로를 선택하세요.", "파일 오류", JOptionPane.ERROR_MESSAGE);
			setNewImage();
			try {
				img = ImageIO.read(imageFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		img = getImage(img);
		graphics.drawImage(img, bound.x, bound.y, bound.width, bound.height, null);
	}

	private BufferedImage getImage(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		
		Rectangle rect = new Rectangle();
		rect.setSize(w, h);
		AffineTransform at = new AffineTransform();
		at.rotate(degree, rect.getCenterX(), rect.getCenterY());
		Shape rectB = at.createTransformedShape(rect);
		Rectangle originB = rectB.getBounds();
		
		BufferedImage temp = new BufferedImage(originB.width, originB.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) temp.getGraphics();
		g2d.rotate(degree, temp.getWidth()/2, temp.getHeight()/2);
		g2d.drawImage(img, null, (int)(temp.getWidth()/2-img.getWidth()/2), (int)(temp.getHeight()/2-img.getHeight()/2));
		
		return temp;
	}

	@Override
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
	}

	@Override
	public void setPoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);
	}

	@Override
	public void addPoint(int x, int y) {
	}

	@Override
	public void setShape(Shape shape) {
		try {
			this.shape = shape;
		} catch (ClassCastException e1) {
			try {
				Path2D originRectangle = (Path2D) shape;
				this.shape = (Shape) originRectangle.clone();
			} catch(ClassCastException e2) {
				Rectangle originRectangle = (Rectangle) shape;
				this.shape = (Shape) originRectangle.clone();
			}
		}
	}
	
	@Override
	protected double rotate(double centerX, double centerY, int originX, int originY, int x, int y) {
		double tempDegree = super.rotate(centerX, centerY, originX, originY, x, y);
		degree += tempDegree;
		return 0;
	}
}