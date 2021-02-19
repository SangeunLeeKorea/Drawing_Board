package menus;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

import frames.ResizeFrame;
import main.GConstants;
import main.GConstants.ELineDot;
import main.GConstants.ELineThick;
import main.GConstants.EShapeMenu;
import main.GConstants.IMenu;
import shape.GShape;

public class GShapeMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components

	public GShapeMenu(String name) {
		super(name);
	}

	public void initialize() {

	}

	// method
	public void fillC() {
		Color originColor = gPanelArea.getCurrentPanel().getFillColor();
		Color newColor = JColorChooser.showDialog(null, "Color Selection", originColor);
		if (newColor != null) {
			gPanelArea.getCurrentPanel().setFillColor(newColor);
		}
	}

	public void lineC() {
		Color originColor = gPanelArea.getCurrentPanel().getFillColor();
		Color newColor = JColorChooser.showDialog(null, "Color Selection", originColor);
		if (newColor != null) {
			gPanelArea.getCurrentPanel().setLineColor(newColor);
		}
	}

	public void lineThick() {
		Object[] list = new Object[ELineThick.values().length];
		for (int i = 0; i < list.length; i++) {
			list[i] = ELineThick.values()[i].getValue();
		}
		Object selected = JOptionPane.showInputDialog(null, "�׵θ��� �β��� �����ϼ���", "�� �β� ����", JOptionPane.QUESTION_MESSAGE,
				null, list, gPanelArea.getCurrentPanel().getLineThick());
		if (selected != null) {
			gPanelArea.getCurrentPanel().setLineThick((int) selected);
		}
	}

	public void lineDot() {
		Object[] list = new Object[ELineDot.values().length];
		for (int i = 0; i < list.length; i++) {
			list[i] = ELineDot.values()[i].getSecond();
		}
		Object selected = JOptionPane.showInputDialog(null, "���� ��Ÿ���� �����ϼ���", "���� ��Ÿ�� ����", JOptionPane.QUESTION_MESSAGE,
				null, list, gPanelArea.getCurrentPanel().getLineDot());
		if (selected != null) {
			gPanelArea.getCurrentPanel().setLineDot((float) selected);
		}
	}

	public void horizontalFlip() {
		gPanelArea.getCurrentPanel().setFlip(0, 1);
	}

	public void verticalFlip() {
		gPanelArea.getCurrentPanel().setFlip(1, 0);
	}

	public void resize() {
		GShape shapes = gPanelArea.getCurrentPanel().getSelectedShape();
		if (shapes != null) {
			Dimension d = new Dimension();
			d.setSize(shapes.getBounds().getWidth(), shapes.getBounds().getHeight());
			new ResizeFrame(d, gPanelArea, "resizeShape", "�ʺ�", "����");
		}
	}

	public void rotate() {
		if (gPanelArea.getCurrentPanel().getSelectedShape() != null) {
			String stringDegree = JOptionPane.showInputDialog("����(����)�� �Է��ϼ���(0~360, ���� ����)");
			try {
				int intDegree = Integer.parseInt(stringDegree);
				if (intDegree > 360 || intDegree < 0) {
					JOptionPane.showMessageDialog(null, "���� ���� ���� �ԷµǾ����ϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
				} else {
					gPanelArea.setShapeRotate(intDegree);
				}
			} catch (NumberFormatException format) {
				JOptionPane.showMessageDialog(null, "������ �ƴ� ���� �ԷµǾ����ϴ�.", "���� ����", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void move() {
		GShape shapes = gPanelArea.getCurrentPanel().getSelectedShape();
		if (shapes != null) {
			Dimension d = new Dimension();
			d.setSize(shapes.getBounds().getX(), shapes.getBounds().getY());
			new ResizeFrame(d, gPanelArea, "changeLocation", "X��ǥ", "Y��ǥ");
		}
	}
	
	public void shapeUp() {
		gPanelArea.getCurrentPanel().shapeUp();
	}
	
	public void shapeDown() {
		gPanelArea.getCurrentPanel().shapeDown();
	}
	
	public void shapeLeft() {
		gPanelArea.getCurrentPanel().shapeLeft();
	}
	
	public void shapeRight() {
		gPanelArea.getCurrentPanel().shapeRight();
	}

	@Override
	public IMenu[] values() {
		return EShapeMenu.values();
	}
}
