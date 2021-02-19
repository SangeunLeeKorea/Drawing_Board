package frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.GConstants;

public class ResizeFrame extends JFrame {
	
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	JLabel lWidth, lHeight;
	JTextField tWidth, tHeight;
	JButton bOkay, bCancel;
	GPanelArea source;
	String type;

	ActionHandler actionHandler;
	
	public ResizeFrame(Dimension dimension, GPanelArea source, String type, String labelF, String labelS){
		this.setVisible(true);
		this.setSize(600, 400);
		this.setLayout(new GridBagLayout());
		
		this.source = source;
		this.type = type;
		
		this.actionHandler = new ActionHandler();
		
		lWidth = new JLabel(labelF);
		lHeight = new JLabel(labelS);
		tWidth = new JTextField();
		tHeight = new JTextField();
		tWidth.setText(Integer.toString((int)dimension.getWidth()));
		tHeight.setText(Integer.toString((int)dimension.getHeight()));
		bOkay = new JButton("확인");
		bOkay.setActionCommand("okay");
		bOkay.addActionListener(actionHandler);
		bCancel = new JButton("취소");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(actionHandler);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(3, 3, 3, 3);
		
		gbc = layout(gbc, 0, 0, 1, 1);
		this.add(lWidth, gbc);
		gbc = layout(gbc, 1, 0, 2, 1);
		this.add(tWidth, gbc);
		gbc = layout(gbc, 0, 1, 1, 1);
		this.add(lHeight, gbc);
		gbc = layout(gbc, 1, 1, 2, 1);
		this.add(tHeight, gbc);
		gbc = layout(gbc, 1, 2, 1, 1);
		this.add(bOkay, gbc);
		gbc = layout(gbc, 2, 2, 1, 1);
		this.add(bCancel, gbc);
	}
	
	private GridBagConstraints layout(GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		return gbc;
	}
	
	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(bOkay)) {
				int newW = -1;
				int newH = -1;
				try {
					newW = Integer.parseInt(tWidth.getText());
					newH = Integer.parseInt(tHeight.getText());
					if (newW >= 0 && newH >= 0) {
						Dimension newD = new Dimension(newW, newH);
						switch (type) {
						case "resizePanel":
							source.setCurrentPanelSize(newD);
							break;
						 case "resizeShape":
							 source.setShapeSize(newD);
							 break;
						 case "changeLocation":
							 source.changeShapeLocation(newD);
							 break;
						}
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "음수가 입력되었습니다.", "형식 오류", JOptionPane.ERROR_MESSAGE);
					}
				} catch(NumberFormatException format) {
					JOptionPane.showMessageDialog(null, "정수가 아닌 값이 입력되었습니다.", "형식 오류", JOptionPane.ERROR_MESSAGE);
				} 
			} else if (e.getSource().equals(bCancel)) {
				dispose();
			}
		}
	}

}
