package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import main.GConstants;
import main.GConstants.EShapeToolbar;
import menus.GFileMenu;
import menus.GMenu;
import shape.GShape;

public class GPanelArea extends JPanel {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	private GPanelList panelList;
	private GDrawingPanel currentPanel;
	private Vector<GShape> copyPanel;
	private String copyName;
	private Vector<GDrawingPanel> panels;
	private int count = 0;
	
	private JMenuItem up;
	private JMenuItem down;
	private JMenuItem right;
	private JMenuItem left;

	public GPanelArea() {
		this.setBackground(Color.GRAY);
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		this.panels = new Vector<GDrawingPanel>();
		count++;
		this.currentPanel = new GDrawingPanel("이미지 " + count);
		panels.add(currentPanel);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		this.add(currentPanel, gbc);

		this.copyPanel = null;
		this.copyName = null;
	}
	
	public void setAssociation(GPanelList panelList) {
		this.panelList = panelList;
		this.currentPanel.setAssociation();
	}
	
	public void initialize() {
	}

	public boolean checkSaveAll(Vector<GMenu> menus) {
		GFileMenu fileMenu = null;
		for (GMenu menu : menus) {
			if (menu.getClass().toString().equals(GFileMenu.class.toString())) {
				fileMenu = (GFileMenu) menu;
			}
		}
		for (GDrawingPanel panel : panels) {
			if (!panel.isSaved()) {
				currentPanel = panel;
				if (!fileMenu.checkSave()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkSave(Vector<GMenu> menus) {
		GFileMenu fileMenu = null;
		for (GMenu menu : menus) {
			if (menu.getClass().toString().equals(GFileMenu.class.toString())) {
				fileMenu = (GFileMenu) menu;
			}
		}
		if (!currentPanel.isSaved()) {
			if (!fileMenu.checkSave()) {
				return false;
			}
		}
		return true;
	}

	public GDrawingPanel getCurrentPanel() {
		return currentPanel;
	}

	public void setCurrentPanel(int i) {
		if (panels.size() > i) {
			GShape currentTool = currentPanel.getCurrentTool();
			currentPanel = panels.get(i);
			if (currentTool != null) {
				for (EShapeToolbar tool : EShapeToolbar.values()) {
					if (tool.getTool() != null && tool.getTool().equals(currentTool)) {
						currentPanel.setCurrentTool(tool);
					}
				}
			} else if (currentTool == null) {
				currentPanel.setCurrentTool(EShapeToolbar.eSelection);
			}
			repaintPanel();
		}
	}

	public int getPanelsSize() {
		return panels.size();
	}

	public GDrawingPanel getPanel(int i) {
		return panels.get(i);
	}

	public Dimension getDimension() {
		return currentPanel.getSize();
	}

	public void setCurrentPanelSize(Dimension newD) {
		currentPanel.updateSize(newD);
	}

	public void repaintPanel() {
		this.remove(0);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		this.add(currentPanel, gbc);
		updateUI();
	}

	public void addNewPanel() {
		count++;
		GDrawingPanel newPanel = new GDrawingPanel("이미지 " + count);
		panels.add(newPanel);
		setCurrentPanel(panels.indexOf(newPanel));
		panelList.addPanel();
		repaintPanel();
	}

	public void openPanel(Object shapes, String fileName) {
		GDrawingPanel newPanel = new GDrawingPanel(fileName);
		panels.add(newPanel);
		setCurrentPanel(panels.indexOf(newPanel));
		panelList.addPanel();
		currentPanel.setShapes(shapes);
		repaintPanel();
	}

	public void changeName(String fileName) {
		currentPanel.setName(fileName);
		panelList.changeName(panels.indexOf(currentPanel), fileName);
	}

	public void closePanel() {
		int i = panels.indexOf(currentPanel);
		panels.remove(i);
		currentPanel = panels.get(panels.size() - 1);
		repaintPanel();
		panelList.closePanel(i);
	}

	public void swapData(int a, int b) {
		GDrawingPanel temp = panels.get(a);
		panels.set(a, panels.get(b));
		panels.set(b, temp);
	}

	public void copy() {
		copyName = currentPanel.getName();
		Vector<GShape> shapes = (Vector<GShape>) currentPanel.getShapes();
		copyPanel = clone(shapes);
	}

	public void paste() {
		this.openPanel(clone(copyPanel), copyName);
	}

	public boolean isCopyied() {
		return copyName != null;
	}

	private Vector<GShape> clone(Vector<GShape> shapes) {
		Vector<GShape> result = new Vector<GShape>();
		for (GShape shape : shapes) {
			result.add(shape.clone());
		}
		return result;
	}

	public void setShapeSize(Dimension newD) {
		currentPanel.setShapeSize(newD);
	}

	public void setShapeRotate(double degree) {
		currentPanel.setShapeRotate(degree);
	}

	public void changeShapeLocation(Dimension newD) {
		currentPanel.setShapeLocation(newD.getWidth(), newD.getHeight());
	}

	public int getShapeListLength() {
		return currentPanel.getShapes().size();
	}

	public void setSelectedShape(int i, boolean b) {
		currentPanel.shapeSelect(i, b);
	}

	public void swapShape(int i) {
		currentPanel.shapeSelect(i, false);
	}
}
