package frames;

import java.util.Vector;

import javax.swing.JMenuBar;

import main.GConstants;
import main.GConstants.EMenuState;
import menus.GMenu;

public class GMenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private Vector<GMenu> menus;
	EMenuState eMenuState;
//	ActionHandler actionHandler;
	
	public GMenuBar() {
		super();
		// set attributes
		
		// create components
		this.menus = new Vector<GMenu>();
		this.eMenuState = EMenuState.eNone;
//		actionHandler = new ActionHandler();
		
		for (GConstants.EMenubar eMenu: GConstants.EMenubar.values()) {
			GMenu menu = eMenu.getMenu();
			this.menus.add(menu);
			this.add(menu);
		}
		
	}

	public void initialize() {
		// set associations
		
		// set associative attributes
		
		// initialize components
		for (GMenu menu: this.menus) {
			menu.initialize();
		}
	}

	public void setAssociation(GPanelArea panelArea, GMainFrame mainFrame) {
		for (GMenu menu:menus) {
			menu.setAssociation(panelArea, mainFrame);
		}
	}
	
	public Vector<GMenu> getMenus(){
		return this.menus;
	}
}
