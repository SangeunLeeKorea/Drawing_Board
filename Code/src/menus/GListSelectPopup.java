package menus;

import java.util.Vector;

import javax.swing.JOptionPane;

import frames.GPanelArea;
import frames.ResizeFrame;
import main.GConstants;
import main.GConstants.EPanelListMenu;
import main.GConstants.IPMenu;

public class GListSelectPopup extends GPopupMenu {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;

	GPanelArea panelArea;
	Vector<GMenu> menus;

	public GListSelectPopup(GPanelArea panelArea, Vector<GMenu> menus) {
		super();
		this.panelArea = panelArea;
		this.menus = menus;
	}

	@Override
	public IPMenu[] values() {
		return EPanelListMenu.values();
	}
	
	public void changeName() {
		String name = JOptionPane.showInputDialog(null, "�ٲ� �̸��� �Է��ϼ���", panelArea.getCurrentPanel().getName());
		panelArea.changeName(name);
	}
	
	public void resizePanel() {
		new ResizeFrame(panelArea.getDimension(), panelArea, "resizePanel", "�ʺ�", "����");
	}
	
	public void copy() {
		panelArea.copy();
	}
	
	public void paste() {
		if (panelArea.isCopyied()) {
			panelArea.paste();
		}
	}
	
	public void cut() {
		if (panelArea.getPanelsSize() != 1) {
			panelArea.copy();
			panelArea.closePanel();
		}
	}
	
	public void nnew() {
		panelArea.addNewPanel();
	}
	
	public void close() {
		if (panelArea.checkSave(menus)) {
			panelArea.closePanel();
		}
	}
}
