package menus;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import main.GConstants;
import main.GConstants.IPMenu;
import main.GConstants.IPMenuItem;

public abstract class GPopupMenu extends PopupMenu {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	protected Vector<Object> menuItems;
	protected ActionHandler actionHandler;
	
	public GPopupMenu(){
		this.menuItems = new Vector<Object>();
		this.actionHandler = new ActionHandler();
		for(IPMenu menu:values()) {
			Menu pMenu = new Menu(menu.getTitle());
			for (IPMenuItem menuItem:menu.getItems()) {
				MenuItem item = new MenuItem(menuItem.getTitle());
				item.setActionCommand(menuItem.getActionCommand());
				item.addActionListener(actionHandler);
				pMenu.add(item);
			}
			this.add(pMenu);
		}
	}
	
	public abstract IPMenu[] values();
	
	protected void invokeMethod(String methodName) {
		try {
			this.getClass().getMethod(methodName).invoke(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	protected class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String methodName = e.getActionCommand();
			invokeMethod(methodName);
		}
	}
}
