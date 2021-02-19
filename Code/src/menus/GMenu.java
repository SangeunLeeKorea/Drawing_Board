package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.GMainFrame;
import frames.GPanelArea;
import main.GConstants;
import main.GConstants.EEditMenu;
import main.GConstants.EFlip;
import main.GConstants.EMoveTen;
import main.GConstants.EOrder;
import main.GConstants.EShapeMenu;
import main.GConstants.IMenu;
import main.GConstants.IPMenuItem;

public abstract class GMenu extends JMenu {
	private static final long serialVersionUID = GConstants.serialVersionUID;

	protected GMainFrame gMainFrame;
	protected GPanelArea gPanelArea;

	protected Vector<Object> menuItems;
	protected ActionHandler actionHandler;
	protected IMenu iMenu;

	public GMenu(String name) {
		super(name);
		this.menuItems = new Vector<Object>();
		this.actionHandler = new ActionHandler();
		for (IMenu menu : values()) {
			if (menu.getKeyStroke() != null) {
				JMenuItem menuItem = new JMenuItem(menu.getTitle());
				menuItem.addActionListener(actionHandler);
				menuItem.setActionCommand(menu.getActionCommand());
				menuItem.setAccelerator(menu.getKeyStroke());
				this.menuItems.add(menuItem);
				this.add(menuItem);
			} else {
				JMenu newMenu = new JMenu(menu.getTitle());
//				newMenu.addActionListener(actionHandler);
				newMenu.setActionCommand(menu.getActionCommand());
				setMenu(newMenu);
				this.menuItems.add(newMenu);
				this.add(newMenu);
			}
		}
	}

	private void setMenu(JMenu newMenu) {
		IPMenuItem[] item = null;
		if (newMenu.getActionCommand().equals(EEditMenu.eOrder.getActionCommand())) {
			item = EOrder.values();
		} else if (newMenu.getActionCommand().equals(EShapeMenu.eFlip.getActionCommand())){
			item = EFlip.values();
		} else if (newMenu.getActionCommand().equals(EShapeMenu.eMoveTen.getActionCommand())) {
			item = EMoveTen.values();
		}
		if (item != null) {
			for(IPMenuItem menu:item) {
				JMenuItem menuItem = new JMenuItem(menu.getTitle());
				menuItem.addActionListener(actionHandler);
				menuItem.setActionCommand(menu.getActionCommand());
				newMenu.add(menuItem);
				if(menu.getKeyStroke() != null){
					menuItem.setAccelerator(menu.getKeyStroke());
				}
			}
		}
	}

	public abstract void initialize();

	public abstract IMenu[] values();

	public void setAssociation(GPanelArea panelArea, GMainFrame gMainFrame) {
		this.gPanelArea = panelArea;
		this.gMainFrame = gMainFrame;
	}

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
