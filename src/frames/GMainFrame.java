package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import main.GConstants;
import menus.GMenu;

public class GMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components
	private GMenuBar menuBar;
	private GShapeToolBar toolBar;
	private GPanelArea panelArea;
	private GPanelList panelList;
	private JScrollPane scrollPane;

	public GMainFrame() {
		super();

		// initialize attributes
		this.setSize(GConstants.EMainFrame.eWidth.getValue(), GConstants.EMainFrame.eHeight.getValue());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(500, 500));

		// create,register components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		this.toolBar = new GShapeToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.panelArea = new GPanelArea();
		scrollPane = new JScrollPane(panelArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(this.scrollPane, BorderLayout.CENTER);

		this.panelList = new GPanelList();
		this.add(this.panelList, BorderLayout.WEST);
		
		this.addWindowListener(new WindowHandler());
	}

	public void initialize() {
		// set associations
		this.toolBar.setAssociation(this.panelArea);
		this.menuBar.setAssociation(this.panelArea, this);
		this.panelList.setAssociation(this.panelArea, menuBar.getMenus());
		this.panelArea.setAssociation(this.panelList);
		// initialize associative attributes

		// initialize components
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.panelArea.initialize();
		this.panelList.initialize();
	}

	class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			boolean exit = true;
			Vector<GMenu> menus = menuBar.getMenus();
			exit = panelArea.checkSaveAll(menus);
			if (exit) {
				System.exit(0);
			}
		}
	}
}
