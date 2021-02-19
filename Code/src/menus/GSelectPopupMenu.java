package menus;

import java.awt.Color;

import javax.swing.JColorChooser;

import frames.GDrawingPanel;
import main.GConstants;
import main.GConstants.ESelectedPMenu;
import main.GConstants.IPMenu;

public class GSelectPopupMenu extends GPopupMenu {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;

	GDrawingPanel drawingPanel;

	public GSelectPopupMenu(GDrawingPanel drawingPanel) {
		super();
		this.drawingPanel = drawingPanel;
	}

	@Override
	public IPMenu[] values() {
		return ESelectedPMenu.values();
	}

	public void first() {
		drawingPanel.first();
	}

	public void forward() {
		drawingPanel.forward();
	}

	public void last() {
		drawingPanel.last();
	}

	public void backward() {
		drawingPanel.backward();
	}
	
	public void group() {
		drawingPanel.group();
	}
	
	public void ungroup() {
		drawingPanel.ungroup();
	}

	public void fill() {
		Color originColor = drawingPanel.getFillColor();
		Color newColor = JColorChooser.showDialog(null, "Color Selection", originColor);
		if (newColor != null) {
			drawingPanel.setFillColor(newColor);
		}
	}

	public void line() {
		Color originColor = drawingPanel.getFillColor();
		Color newColor = JColorChooser.showDialog(null, "Color Selection", originColor);
		if (newColor != null) {
			drawingPanel.setLineColor(newColor);
		}
	}
	
	public void copy() {
		drawingPanel.copy();
	}
	
	public void cut() {
		drawingPanel.cut();
	}
	
	public void paste() {
		drawingPanel.paste();
	}
}
