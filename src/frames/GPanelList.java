package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.GConstants;
import menus.GListSelectPopup;
import menus.GMenu;

public class GPanelList extends JPanel {

	private static final long serialVersionUID = GConstants.serialVersionUID;

	private GPanelArea panelArea;
	private JScrollPane scrollPane;
	private JPanel scrollPanel;
	private JPanel buttonPanel;
	private GridBagConstraints gbc;
	private MouseHandler mouseHandler;
	private ActionHandler actionHandler;
	private JButton up;
	private JButton down;
	private GListSelectPopup popup;

	private Vector<GPanelListItems> labelList;

	public GPanelList() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 700));

		this.labelList = new Vector<GPanelListItems>();

		// 스크롤 부분
		this.scrollPanel = new JPanel();
		gbc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		this.scrollPanel.setLayout(gbl);
		scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		this.mouseHandler = new MouseHandler();
		scrollPanel.addMouseListener(mouseHandler);
		scrollPanel.addMouseMotionListener(mouseHandler);

		// button panel
		this.buttonPanel = new JPanel();
		this.actionHandler = new ActionHandler();
		up = new JButton("▲");
		up.setActionCommand("up");
		up.addActionListener(actionHandler);
		down = new JButton("▼");
		down.setActionCommand("down");
		down.addActionListener(actionHandler);
		buttonPanel.add(up);
		buttonPanel.add(down);

		this.buttonPanel.setBackground(Color.BLACK);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void setAssociation(GPanelArea panelArea, Vector<GMenu> menus) {
		this.panelArea = panelArea;
		popup = new GListSelectPopup(panelArea, menus);
		this.add(popup);
	}

	public void initialize() {
		initPanelList();
	}

	public void initPanelList() {
		int size = panelArea.getPanelsSize();
		gbc.fill = GridBagConstraints.BOTH;
		GPanelListItems item = new GPanelListItems(panelArea.getPanel(0).getName());
		item.setSelected(true);
		gbc.gridx = 0;
		gbc.gridy = 0;
		scrollPanel.add(item, gbc);
		labelList.add(item);

		gbc.gridx = 0;
		gbc.gridy = size;
		gbc.weighty = 0.1;
		scrollPanel.add(new JPanel(), gbc);
	}

	public void addPanel() {
		for (GPanelListItems item : labelList) {
			item.setSelected(false);
		}
		gbc.fill = GridBagConstraints.BOTH;
		int size = panelArea.getPanelsSize();
		scrollPanel.remove(size - 1);
		GPanelListItems item = new GPanelListItems(panelArea.getCurrentPanel().getName());
		item.setSelected(true);
		gbc.gridx = 0;
		gbc.gridy = size - 1;
		gbc.weighty = 0;
		scrollPanel.add(item, gbc);
		labelList.add(item);

		gbc.gridx = 0;
		gbc.gridy = size;
		gbc.weighty = 0.1;
		scrollPanel.add(new JPanel(), gbc);

		updateUI();
	}

	public void closePanel(int i) {
		scrollPanel.remove(i);
		labelList.remove(i);
		labelList.get(labelList.size() - 1).setSelected(true);
	}

	public void changeName(int i, String fileName) {
		labelList.get(i).setText(fileName);
	}

	class MouseHandler implements MouseMotionListener, MouseListener {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			for (int i = 0; i < labelList.size(); i++) {
				labelList.get(i).setSelected(false);
				int newY = y - i * 100;
				if (labelList.get(i).contains(x, newY)) {
					labelList.get(i).setSelected(true);
					labelList.get(i).setChangeOrder(true);
					panelArea.setCurrentPanel(i);
				}
			}
			updateUI();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			if (labelList.size() > 1) {
				for (int i = 0; i < labelList.size(); i++) {
					int newY = y - i * 100;
					if (labelList.get(i).isSelected() && !labelList.get(i).contains(x, newY)) {
						if (newY < 0) {
							changeOrder("up");
						} else if (newY > 100) {
							changeOrder("down");
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			for (int i = 0; i < labelList.size(); i++) {
				if (labelList.get(i).isSelected()) {
					labelList.get(i).setChangeOrder(false);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				for (int i = 0; i < labelList.size(); i++) {
					if (labelList.get(i).isSelected()) {
						popup.show(labelList.get(i), 0, 0);
						break;
					}
				}
			}
			if (e.getClickCount() == 2) {
				popup.changeName();
			}
			
		}

		// unused
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeOrder(e.getActionCommand());
		}
	}

	public void changeOrder(String action) {
		int index = -1;
		for (GPanelListItems item : labelList) {
			if (item.isSelected()) {
				index = labelList.indexOf(item);
			}
		}
		if (index != -1) {
			switch (action) {
			case "up":
				if (index != 0)
					swapData(index - 1, index);
				break;
			case "down":
				if (index != labelList.size() - 1)
					swapData(index, index + 1);
				break;
			}
			reList();
		}
	}

	private void reList() {
		scrollPanel.removeAll();
		GridBagLayout gbl = new GridBagLayout();
		scrollPanel.setLayout(gbl);
		scrollPanel.addMouseListener(mouseHandler);
		scrollPanel.addMouseMotionListener(mouseHandler);
		for (int i = 0; i < labelList.size(); i++) {
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weighty = 0;
			gbc.gridx = 0;
			gbc.gridy = i;
			scrollPanel.add(labelList.get(i), gbc);
		}
		gbc.gridx = 0;
		gbc.gridy = labelList.size();
		gbc.weighty = 0.1;
		scrollPanel.add(new JPanel(), gbc);
	}

	private void swapData(int a, int b) {
		GPanelListItems temp = labelList.get(a);
		labelList.set(a, labelList.get(b));
		labelList.set(b, temp);
		panelArea.swapData(a, b);
		updateUI();
	}
}
