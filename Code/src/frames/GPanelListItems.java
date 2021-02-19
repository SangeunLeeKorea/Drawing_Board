package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.GConstants;

public class GPanelListItems extends JPanel {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	JLabel label;
	Rectangle rect;
	boolean selected = false;
	boolean changeOrder = false;
	

	public GPanelListItems(String name) {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(200, 100));
		this.setBackground(Color.WHITE);
		
		this.label = new JLabel(name);
		rect = new Rectangle();
		rect.setFrame(0, 0, 200, 100);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(label, gbc);
		if (selected) {
			this.setBorder(new LineBorder(Color.BLACK,1));
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			this.setBorder(new LineBorder(Color.BLACK,1));
		} else {
			this.setBorder(null);
		}
	}
	
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}

	public void setText(String name) {
		this.label.setText(name);
		updateUI();
	}
	
	public String getText() {
		return label.getText();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setChangeOrder(boolean changeOrder) {
		this.changeOrder = changeOrder;
		if (changeOrder) {
			this.setBackground(Color.LIGHT_GRAY);
		} else {
			this.setBackground(Color.WHITE);
		}
	}

	public boolean getChangeOrder() {
		return changeOrder;
	}
}
