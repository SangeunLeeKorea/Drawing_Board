package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.GConstants;
import main.GConstants.ECursor;
import main.GConstants.EDrawingPanel;
import main.GConstants.ELineDot;
import main.GConstants.ELineThick;
import main.GConstants.EShapeToolbar;
import menus.GSelectPopupMenu;
import shape.GGroup;
import shape.GImage;
import shape.GRectangle;
import shape.GShape;
import shape.GShape.EDrawingStyle;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	private enum EState {
		eIdle, eDrawing, eMoving, eSelected, eSelectedMoving, eResize, eRotate, eSelectTool, eMultiSelecting
	}

	private EState eState;
	private ECursor eCursor;

	// components
	private MouseHandler mouseHandler;
	private Vector<GShape> shapes;
	private Vector<GShape> selectedShapes;
	private GShape currentShape;
	private Vector<GShape> copyShape;
	private PreviousWork[] previousWorks;
	private int previousWorkIndex = 0;
	private int currentWorkIndex = 0;
	private int undoCount = 0;
	private int redoCount = 10;
	private ELineThick eLineThick;
	private ELineDot eLineDot;
	private Color lineColor;
	private Color fillColor;
	private Color backgroundColor;
	private GSelectPopupMenu selectPopup;
	private boolean saved = true;
	private Dimension dimension;
	private String name;

	// association components
	private GShape currentTool;

	// constructors and initializers
	public GDrawingPanel(String name) {
		// attributes
		this.dimension = new Dimension(EDrawingPanel.eWidth.getValue(), EDrawingPanel.eHeight.getValue());
		this.setPreferredSize(dimension);
		this.eState = EState.eIdle;
		this.eCursor = ECursor.eDefault;
		this.setCursor(eCursor.getCursor());

		// components
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.selectPopup = new GSelectPopupMenu(this);
		this.add(selectPopup);

		this.shapes = new Vector<GShape>();
		this.currentShape = null;
		this.copyShape = null;
		this.selectedShapes = new Vector<GShape>();
		this.previousWorks = new PreviousWork[10];

		this.eLineThick = ELineThick.e1;
		this.eLineDot = ELineDot.e0f;
		this.fillColor = Color.WHITE;
		this.lineColor = Color.BLACK;
		this.backgroundColor = Color.WHITE;

		this.name = name;

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (eState == EState.eDrawing && currentShape.getEDrawingStyle() == EDrawingStyle.eNPoints) {
					repaint();
				}
			}
		});
	}

	public void setAssociation() {
	}

	// setters & getters
	public void setCurrentTool(EShapeToolbar eToolBar) {
		if (eState == EState.eSelected) {
			shapeSelectCancel();
		}
		if (eToolBar.getTool() == EShapeToolbar.eImage.getTool()) {
			JFileChooser fileChooser = new JFileChooser(new File("./image"));
			FileFilter jpgF = new FileNameExtensionFilter("JPG", "jpg");
			FileFilter jpegF = new FileNameExtensionFilter("JPEG", "jpeg");
			FileFilter pngF = new FileNameExtensionFilter("PNG", "png");
			fileChooser.addChoosableFileFilter(jpgF);
			fileChooser.addChoosableFileFilter(jpegF);
			fileChooser.addChoosableFileFilter(pngF);
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File imageFile = fileChooser.getSelectedFile();
				GImage gImage = (GImage) eToolBar.getTool();
				gImage.setImage(imageFile);
				currentShape = gImage;
				int x = (int)(gImage.getBounds().getX() + gImage.getBounds().getWidth());
				int y = (int)(gImage.getBounds().getY() + gImage.getBounds().getHeight());
				finishDrawing(x, y);
				this.shapes.add(gImage);
				this.saved = false;
				repaint();
			}
		} else {
			this.currentTool = eToolBar.getTool();
			this.eCursor = ECursor.eDefault;
			this.setCursor(eCursor.getCursor());
			if (currentTool == null) {
				eState = EState.eSelectTool;
			} else {
				eState = EState.eIdle;
				currentTool.setLineColor(lineColor);
				currentTool.setFillColor(fillColor);
				currentTool.setLineDot(eLineDot);
				currentTool.setLineThick(eLineThick);
				if (currentTool == EShapeToolbar.eNShape.getTool()) {
					String result = JOptionPane.showInputDialog("다각형의 꼭짓점 개수를 입력하세요. (5 이상, 정수)");
					try {
						int points = Integer.parseInt(result);
						if (points < 5) {
							JOptionPane.showMessageDialog(null, "5 이하의 값이 입력되었습니다.", "입력 오류",
									JOptionPane.ERROR_MESSAGE);
						} else {
							currentTool.setShapeType(points);
						}
					} catch (NumberFormatException format) {
						JOptionPane.showMessageDialog(null, "정수가 아닌 값이 입력되었습니다.", "형식 오류", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<GShape> getShapes() {
		return this.shapes;
	}

	public void setPreviousShape() {
		Vector<GShape> cloned = new Vector<GShape>();
		for (GShape shape : shapes) {
			cloned.add(shape.clone());
		}
		previousWorks[previousWorkIndex % 10] = new PreviousWork(cloned);
		previousWorkIndex++;
		currentWorkIndex = previousWorkIndex;
		undoCount = 0;
		redoCount = 10;
	}
	
	public void removePreviousShape() {
		previousWorkIndex--;
		previousWorks[previousWorkIndex % 10] = null;
		currentWorkIndex = previousWorkIndex;
		undoCount = 0;
		redoCount = 10;
	}

	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<GShape>) shapes;
		repaint();
		saved = true;
	}

	private void setCursorMode(int x, int y) {
		boolean changed = false;
		if (currentShape != null) {
			if (onShape(x, y) != null) {
				eCursor = ECursor.eMove;
				changed = true;
			}
			if (checkRotate(x, y)) {
				eCursor = ECursor.eRotate;
				changed = true;
			} else if (checkResize(x, y) != null) {
				eCursor = checkResize(x, y);
				changed = true;
			}
		}
		if (!changed) {
			eCursor = ECursor.eDefault;
		}
		setCursor(eCursor.getCursor());
	}

	public Image getFinishedImg() {
		shapeSelectCancel();
		BufferedImage result = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = result.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, result.getWidth(null), result.getHeight(null));
		graphics.setColor(Color.BLACK);
		this.paint(graphics);
		return result;
	}

	public void setFillColor(Color newColor) {
		if (eState == EState.eSelected) {
			setPreviousShape();
			saved = false;
			for (GShape shape : selectedShapes) {
				shape.setFillColor(newColor);
			}
			repaint();
		} else if (eState != EState.eSelectTool) {
			currentTool.setFillColor(newColor);
			this.fillColor = newColor;
		}
	}

	public Color getFillColor() {
		if (eState == EState.eSelected) {
			return selectedShapes.get(0).getFillColor();
		}
		return null;
	}

	public void setLineColor(Color newColor) {
		if (eState == EState.eSelected) {
			setPreviousShape();
			saved = false;
			for (GShape shape : selectedShapes) {
				shape.setLineColor(newColor);
			}
			repaint();
		} else if (eState != EState.eSelectTool) {
			currentTool.setLineColor(newColor);
			this.lineColor = newColor;
		}
	}

	public Color getLineColor() {
		if (eState == EState.eSelected) {
			return selectedShapes.get(0).getLineColor();
		}
		return null;
	}

	public boolean isSaved() {
		return saved;
	}

	public GShape getSelectedShape() {
		if (selectedShapes.size() > 0) {
			return selectedShapes.get(0);
		}
		return null;
	}

	public Vector<GShape> getSelectedShapes() {
		return selectedShapes;
	}

	// methods
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		this.setBackground(backgroundColor);
		// user defined drawing
		for (GShape shape : this.shapes) {
			shape.draw(graphics);
		}
		if (eState == EState.eSelected) {
			drawAnchors(graphics);
		}
	}

	private GShape onShape(int x, int y) {
		GShape result = null;
		for (GShape shape : shapes) {
			if (shape.contains(x, y)) {
				result = shape;
			}
		}
		return result;
	}

	private boolean checkRotate(int x, int y) {
		for (GShape shape : shapes) {
			Rectangle rotateBound = shape.getAnchors().getRotateBound();
			int p1x = rotateBound.x;
			int p1y = rotateBound.y;
			int p2x = p1x + rotateBound.width;
			int p2y = p1y + rotateBound.height;

			if ((p1x < x && p2x > x) && (p2y > y && p1y < y))
				return true;
		}
		return false;
	}

	private ECursor checkResize(int x, int y) {
		for (GShape shape : shapes) {
			Vector<Ellipse2D> resizeDots = shape.getAnchors().getResizeDots();
			if (resizeDots != null) {
				for (Ellipse2D dot : resizeDots) {
					if (dot.contains(x, y)) {
						return shape.getAnchors().getECursor(dot);
					}
				}
			}
		}
		return null;
	}

	public void removeShapes() {
		this.shapeSelectCancel();
		this.shapes.removeAllElements();
		this.repaint();
		saved = true;
	}

	public void undo() {
		shapeSelectCancel();
		if (undoCount == 0) {
			setPreviousShape();
			previousWorkIndex--;
		}
		if (undoCount < 10 && previousWorkIndex > 0) {
			previousWorkIndex--;
			shapes = previousWorks[previousWorkIndex % 10].getWorks();
			undoCount++;
			redoCount--;
			repaint();
		}
	}

	public void redo() {
		shapeSelectCancel();
		if (redoCount > 0 && previousWorkIndex < currentWorkIndex - 1) {
			previousWorkIndex++;
			shapes = previousWorks[previousWorkIndex % 10].getWorks();
			undoCount--;
			redoCount++;
			repaint();
		}
	}

	public void shapeSelect(int x, int y, boolean ctrl) {
		if (selectedShapes.size() > 0 && !ctrl) {
			shapeSelectCancel();
		}
		GShape shape = onShape(x, y);
		if (shape != null) {
			if (selectedShapes.indexOf(shape) == -1) {
				selectedShapes.add(shape);
			} else if (ctrl) {
				selectedShapes.remove(shape);
			}
			if (selectedShapes.size() > 0) {
				eState = EState.eSelected;
			} else if (currentTool == null) {
				eState = EState.eSelectTool;
			} else {
				eState = EState.eIdle;
			}
		}
		repaint();
	}

	public void shapeSelect(int i, boolean ctrl) {
		if (!ctrl) {
			shapeSelectCancel();
		}
		if (i < shapes.size()) {
			GShape shape = shapes.get(i);
			if (ctrl) {
				if (selectedShapes.indexOf(shape) == -1) {
					selectedShapes.add(shape);
				} else {
					selectedShapes.remove(shape);
				}
			} else {
				selectedShapes.add(shape);
			}
		}
	}

	public void shapeSelectCancel() {
		if (currentTool == null) {
			eState = EState.eSelectTool;
		} else {
			eState = EState.eIdle;
		}
		selectedShapes.removeAllElements();
		repaint();
	}

	public void drawAnchors(Graphics graphics) {
		for (GShape shape : selectedShapes) {
			shape.drawAnchors(graphics);
		}
	}

	// Edit Menu Methods
	public void copy() {
		if (eState == EState.eSelected) {
			this.copyShape = new Vector<GShape>();
			for (GShape shape : selectedShapes) {
				copyShape.add(shape.clone());
			}
		}
	}

	public void cut() {
		if (eState == EState.eSelected) {
			setPreviousShape();
			saved = false;
			copy();
			for (GShape shape : selectedShapes) {
				shapes.remove(shape);
			}
			shapeSelectCancel();
			repaint();
		}
	}

	public void paste() {
		shapeSelectCancel();
		if (this.copyShape != null) {
			setPreviousShape();
			saved = false;
			for (GShape shape : copyShape) {
				GShape clone = shape.clone();
				shapes.add(clone);
			}
			repaint();
		}
	}

	public void delete() {
		if (eState == EState.eSelected) {
			setPreviousShape();
			saved = false;
			for (GShape shape : selectedShapes) {
				shapes.remove(shape);
			}
			repaint();
		} else if (currentTool != null) {
			if (eCursor == ECursor.eDefault && currentTool.getEDrawingStyle() == EDrawingStyle.e2Points){
				removePreviousShape();
				shapes.remove(shapes.size()-1);
				repaint();
			}
		}
	}

	public void group() {
		if (selectedShapes.size() > 1) {
			GGroup group = new GGroup();
			ungroup();
			for (GShape shape : selectedShapes) {
				shapes.remove(shape);
				group.addGrouped(shape);
			}
			selectedShapes.removeAllElements();
			selectedShapes.add(group);
			shapes.add(group);
			repaint();
		}
	}

	public void ungroup() {
		GGroup group = null;
		for (GShape shape : selectedShapes) {
			if (shape.getShape() == null) {
				group = (GGroup) shape;
				shapes.remove(shape);
				break;
			}
		}
		if (group != null) {
			selectedShapes.remove(group);
			for (int i = 0; i < group.size(); i++) {
				GShape temp = group.getGrouped(i);
				shapes.add(temp);
				selectedShapes.add(temp);
			}
		}
		repaint();
	}

	// re-order
	public void first() {
		if (eState == EState.eSelected) {
			delete();
			for (GShape shape : selectedShapes) {
				shapes.add(shape);
			}
			repaint();
		}
	}

	public void forward() {
		if (eState == EState.eSelected) {
			boolean saved = false;
			for (GShape shape : selectedShapes) {
				if (shapes.indexOf(shape) != shapes.size() - 1) {
					if (!saved) {
						saved = true;
						setPreviousShape();
						this.saved = false;
					}
					int index = shapes.indexOf(shape);
					shapes.remove(shape);
					if (index + 1 >= shapes.size()) {
						shapes.add(shape);
					} else {
						shapes.insertElementAt(shape, index + 1);
					}
				}
			}
			repaint();
		}
	}

	public void last() {
		if (eState == EState.eSelected) {
			delete();
			for (GShape shape : selectedShapes) {
				shapes.insertElementAt(shape, 0);
			}
			repaint();
		}
	}

	public void backward() {
		if (eState == EState.eSelected) {
			boolean saved = false;
			for (GShape shape : selectedShapes) {
				int i = shapes.indexOf(shape);
				if (i >= 0) {
					if (!saved) {
						saved = true;
						setPreviousShape();
						this.saved = false;
					}
					shapes.remove(shape);
					shapes.insertElementAt(shape, i - 1);
				}
			}
			repaint();
		}
	}

	// Transforming
	private void initTransforming(int x, int y) {
		if (eState == EState.eIdle || eState == EState.eSelectTool) {
			shapeSelectCancel();
			shapeSelect(x, y, false);
			if (selectedShapes.size() != 0) {
				setPreviousShape();
				saved = false;
				for (GShape shape : selectedShapes) {
					shape.initTransforming(x, y);
				}
				eState = EState.eMoving;
			}
		} else if (eState == EState.eSelected) {
			setPreviousShape();
			saved = false;

			GShape onShape = onShape(x, y);
			boolean goOn = false;
			for (GShape shape : selectedShapes) {
				if (shape.equals(onShape)) {
					goOn = true;
				}
			}
			if (!goOn) {
				shapeSelectCancel();
			} else {
				for (GShape shape : selectedShapes) {
					shape.initTransforming(x, y);
				}
				eState = EState.eSelectedMoving;
			}
		}
	}

	private void keepTransforming(int x, int y) {
		if (eState == EState.eMoving || eState == EState.eSelectedMoving) {
			for (GShape shape : selectedShapes) {
				shape.keepTransforming(x, y);
			}
		}
	}

	private void finishTransforming(int x, int y) {
		if (eState == EState.eSelectedMoving || eState == EState.eMoving) {
			for (GShape shape : selectedShapes) {
				shape.finishTransforming(x, y);
			}
			if (eState == EState.eMoving) {
				if (currentTool == null) {
					this.eState = EState.eSelectTool;
				} else {
					this.eState = EState.eIdle;
				}
			} else if (eState == EState.eSelectedMoving) {
				eState = EState.eSelected;
			}
			repaint();
		}
	}

	// drawing
	void initDrawing(int x, int y) {
		setPreviousShape();
		saved = false;
		shapeSelectCancel();
		this.currentShape = this.currentTool.clone();
		this.currentShape.setOrigin(x, y);
		this.shapes.add(this.currentShape);
	}

	void keepDrawing(int x, int y) {
		this.currentShape.setPoint(x, y);
	}

	void addPoint(int x, int y) {
		this.currentShape.addPoint(x, y);
	}

	void finishDrawing(int x, int y) {
		this.currentShape.setPoint(x, y);
		this.currentShape.toPath();
		Rectangle bound = currentShape.getBounds();
		shapeSelect((int) bound.getCenterX(), (int) bound.getCenterY(), false);
	}

	// resize
	private void initResize(int x, int y) {
		if (selectedShapes.size() != 0) {
			setPreviousShape();
			saved = false;
			for (GShape shape : selectedShapes) {
				shape.initResize(x, y, eCursor);
			}
			eState = EState.eResize;
		}
	}

	private void keepResize(int x, int y) {
		for (GShape shape : selectedShapes) {
			shape.keepResize(x, y);
		}
	}

	private void finishResize(int x, int y) {
		for (GShape shape : selectedShapes) {
			shape.finishResize(x, y);
		}
		eState = EState.eSelected;
		repaint();
	}

	// rotate
	public void initRotate(int x, int y) {
		setPreviousShape();
		saved = false;
		for (GShape shape : selectedShapes) {
			shape.initRotate(x, y);
		}
		eState = EState.eRotate;
	}

	public void keepRotate(int x, int y) {
		for (GShape shape : selectedShapes) {
			shape.keepRotate(x, y);
		}
	}

	public void finishRotate(int x, int y) {
		for (GShape shape : selectedShapes) {
			shape.finishRotate(x, y);
		}
		eState = EState.eSelected;
		repaint();
	}

	// multiSelect
	public void initMultiSelect(int x, int y) {
		currentShape = new GRectangle();
		currentShape.setOrigin(x, y);
		currentShape.setFillColor(new Color(0, 0, 0, 0));
		currentShape.setLineDot(ELineDot.e4f);
		this.shapes.add(currentShape);
		eState = EState.eMultiSelecting;
	}

	public void keepMultiSelect(int x, int y) {
		currentShape.setPoint(x, y);
	}

	public void finishMultiSelect(int x, int y) {
		this.shapes.remove(currentShape);
		Rectangle areaBound = currentShape.getBounds();
		selectedShapes = new Vector<GShape>();
		for (GShape shape : shapes) {
			Rectangle shapeBound = shape.getBounds();
			if (areaBound.contains(shapeBound)) {
				selectedShapes.add(shape);
			}
		}
		if (selectedShapes.size() != 0) {
			eState = EState.eSelected;
		} else {
			eState = EState.eSelectTool;
		}
		repaint();
	}

	// inner class
	class MouseHandler implements MouseMotionListener, MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (event.getButton() == MouseEvent.BUTTON3) {
				if (eState == EState.eSelected) {
					selectPopup.show(GDrawingPanel.this, x, y);
				}
			} else if (event.getButton() == MouseEvent.BUTTON1) {
				if (event.isControlDown()) {
					shapeSelect(x, y, true);
				} else {
					if (eCursor == ECursor.eMove && eState == EState.eSelected) {
						shapeSelectCancel();
						shapeSelect(x, y, false);
					} else if (eCursor == ECursor.eDefault && eState == EState.eSelected) {
						shapeSelectCancel();
					} else if (eCursor == ECursor.eMove) {
						shapeSelect(x, y, false);
					} else if (eCursor == ECursor.eDefault) {
						if (event.getClickCount() == 1) {
							this.mouse1Clicked(event);
						} else if (event.getClickCount() == 2) {
							this.mouse2Clicked(event);
						}
					}
				}
			}
		}

		// n point drawing
		private void mouse1Clicked(MouseEvent event) {
			if (currentTool != null) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eState == EState.eIdle) {
					int x = event.getX();
					int y = event.getY();
					initDrawing(x, y);
					eState = EState.eDrawing;
				} else if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eState == EState.eDrawing) {
					int x = event.getX();
					int y = event.getY();
					addPoint(x, y);
				} else if (eCursor == ECursor.eDefault && currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					delete();
				}
			}
		}

		private void mouse2Clicked(MouseEvent event) {
			if (currentTool != null) {
				if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eState == EState.eDrawing) {
					int x = event.getX();
					int y = event.getY();
					finishDrawing(x, y);
					eState = EState.eIdle;
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (eState == EState.eIdle || eState == EState.eSelected || eState == EState.eSelectTool) {
				setCursorMode(x, y);
			} else if (eState == EState.eDrawing && currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints) {
				keepDrawing(x, y);
			}
		}

		// 2 point drawing
		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (!event.isControlDown()) {
				if (checkResize(x, y) != null && eState == EState.eSelected) {
					initResize(x, y);
				} else if (eCursor == ECursor.eMove
						&& (eState == EState.eSelectTool || eState == EState.eSelected || eState == EState.eIdle)) {
					initTransforming(x, y);
				} else if (eCursor == ECursor.eRotate) {
					initRotate(x, y);
				} else if (eCursor == ECursor.eDefault && currentTool == null
						&& (eState == EState.eSelectTool || eState == EState.eSelected)) {
					initMultiSelect(x, y);
				} else if (eCursor == ECursor.eDefault && currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					initDrawing(x, y);
					eState = EState.eDrawing;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (eState == EState.eMoving || eState == EState.eSelectedMoving) {
				keepTransforming(x, y);
			} else if (eState == EState.eResize) {
				keepResize(x, y);
			} else if (eState == EState.eRotate) {
				keepRotate(x, y);
			} else if (eState == EState.eMultiSelecting) {
				keepMultiSelect(x, y);
			} else if (eState == EState.eDrawing && currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
				keepDrawing(x, y);
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (eState == EState.eMoving || eState == EState.eSelectedMoving) {
				finishTransforming(x, y);
			} else if (eState == EState.eResize) {
				finishResize(x, y);
			} else if (eState == EState.eRotate) {
				finishRotate(x, y);
			} else if (eState == EState.eMultiSelecting) {
				finishMultiSelect(x, y);
			} else if (eState == EState.eDrawing && currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
				finishDrawing(x, y);
			}
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		}

		@Override
		public void mouseExited(MouseEvent event) {
		}
	}

	public void setLineThick(int selected) {
		for (ELineThick line : ELineThick.values()) {
			if (line.getValue() == selected) {
				if (eState == EState.eSelected) {
					setPreviousShape();
					saved = false;
					for (GShape shape : selectedShapes) {
						shape.setLineThick(line);
					}
					repaint();
				} else if (eState == EState.eSelectTool) {
					this.eLineThick = line;
				} else {
					currentTool.setLineThick(line);
					this.eLineThick = line;
				}
			}
		}
	}

	public Object getLineThick() {
		if (selectedShapes.size() > 0) {
			return selectedShapes.get(0).getLineThick();
		} else {
			return ELineThick.e1.getValue();
		}
	}

	public Object getLineDot() {
		if (selectedShapes.size() > 0) {
			return selectedShapes.get(0).getLineDot();
		} else {
			return ELineDot.e0f.getSecond();
		}
	}

	public void setLineDot(float selected) {
		for (ELineDot line : ELineDot.values()) {
			if (line.getSecond() == selected) {
				if (eState == EState.eSelected) {
					setPreviousShape();
					saved = false;
					for (GShape shape : selectedShapes) {
						shape.setLineDot(line);
					}
					repaint();
				} else if (eState == EState.eSelectTool) {
					this.eLineDot = line;
				} else {
					currentTool.setLineDot(line);
					this.eLineDot = line;
				}
			}
		}
	}

	public void updateSize(Dimension newD) {
		this.setSize(newD);
		dimension = newD;
	}

	public GShape getCurrentTool() {
		return this.currentTool;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setFlip(int x, int y) {
		if (selectedShapes.size() > 0) {
			for (GShape shape : selectedShapes) {
				shape.setFlip(x, y);
				repaint();
			}
		}
	}

	public void setShapeSize(Dimension newD) {
		if (selectedShapes.size() != 0) {
			int x = (int) newD.getWidth();
			int y = (int) newD.getHeight();
			setPreviousShape();
			saved = false;
			for (GShape shape : selectedShapes) {
				shape.initResize(0, 0, ECursor.eSE);
				shape.keepResize(x, y);
				shape.finishResize(x, y);
			}
		}
		repaint();
	}

	public void setShapeRotate(double degree) {
		if (selectedShapes.size() != 0) {
			for (GShape shape : selectedShapes) {
				Rectangle bound = shape.getBounds();
				shape.rotateByDegree(bound.getCenterX(), bound.getCenterY(), degree);
			}
		}
		repaint();
	}

	public void clearAll() {
		selectedShapes.removeAllElements();
		shapes.removeAllElements();
		if (currentTool == null) {
			eState = EState.eSelectTool;
		} else {
			eState = EState.eIdle;
		}
		repaint();
	}

	public void setShapeLocation(double x, double y) {
		if (selectedShapes.size() != 0) {
			for (GShape shape : selectedShapes) {
				Rectangle bound = shape.getBounds();
				int dx = (int) (x - bound.getX());
				int dy = (int) (y - bound.getY());
				shape.move(dx, dy);
			}
		}
		repaint();
	}
	
	public void shapeUp() {
		if (eState == EState.eSelected) {
			eState = EState.eSelectedMoving;
			for (GShape shape:selectedShapes) {
				shape.initTransforming(0, 0);
				shape.keepTransforming(0, -10);
				finishTransforming(0, 0);
			}
		}
	}
	
	public void shapeDown() {
		if (eState == EState.eSelected) {
			eState = EState.eSelectedMoving;
			for (GShape shape:selectedShapes) {
				shape.initTransforming(0, 0);
				shape.keepTransforming(0, 10);
				finishTransforming(0, 0);
			}
		}
	}
	
	public void shapeLeft() {
		if (eState == EState.eSelected) {
			eState = EState.eSelectedMoving;
			for (GShape shape:selectedShapes) {
				shape.initTransforming(0, 0);
				shape.keepTransforming(-10, 0);
				finishTransforming(0, 0);
			}
		}
	}
	
	public void shapeRight() {
		if (eState == EState.eSelected) {
			eState = EState.eSelectedMoving;
			for (GShape shape:selectedShapes) {
				shape.initTransforming(0, 0);
				shape.keepTransforming(10, 0);
				finishTransforming(0, 0);
			}
		}
	}
}
