package menus;
import java.awt.Color;

import javax.swing.JColorChooser;

import frames.ResizeFrame;
import main.GConstants;
import main.GConstants.EEditMenu;
import main.GConstants.IMenu;

public class GEditMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	
	public GEditMenu(String name) {
		super(name);
	}
	
	public void initialize() {
	}
	
	//method
	public void undo() {
		gPanelArea.getCurrentPanel().undo();
	}
	
	public void redo() {
		gPanelArea.getCurrentPanel().redo();
	}
	
	public void copy() {
		gPanelArea.getCurrentPanel().copy();
	}
	
	public void cut() {
		gPanelArea.getCurrentPanel().cut();
	}
	
	public void paste() {
		gPanelArea.getCurrentPanel().paste();
	}
	
	public void delete() {
		gPanelArea.getCurrentPanel().delete();
		gPanelArea.getCurrentPanel().shapeSelectCancel();
		gPanelArea.getCurrentPanel().repaint();
	}
	
	public void group() {
		gPanelArea.getCurrentPanel().group();
	}
	
	public void ungroup() {
		gPanelArea.getCurrentPanel().ungroup();
	}
	
	public void first() {
		gPanelArea.getCurrentPanel().first();
	}
	
	public void forward() {
		gPanelArea.getCurrentPanel().forward();
	}
	
	public void last() {
		gPanelArea.getCurrentPanel().last();
	}
	
	public void backward() {
		gPanelArea.getCurrentPanel().backward();
	}
	
	public void resizePanel() {
		new ResizeFrame(gPanelArea.getDimension(), gPanelArea, "resizePanel", "너비", "높이");
	}
	
	public void changeBackground() {
		Color originColor = gPanelArea.getCurrentPanel().getFillColor();
		Color newColor = JColorChooser.showDialog(null, "Color Selection", originColor);
		if (newColor != null) {
			gPanelArea.getCurrentPanel().setBackgroundColor(newColor);
			gPanelArea.getCurrentPanel().repaint();
		}
	}
	
	public void clearAll() {
		gPanelArea.getCurrentPanel().clearAll();
	}
	
	@Override
	public IMenu[] values() {
		return EEditMenu.values();
	}
}
