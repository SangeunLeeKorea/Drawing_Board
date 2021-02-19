package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JToolBar;

import main.GConstants;
import main.GConstants.EShapeToolbar;

public class GShapeToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components
	private ActionHadler actionHadler;

	private Vector<JButton> buttons;

	// associations
	GPanelArea panelArea;

	public GShapeToolBar() {
		super();
		// create components
		this.actionHadler = new ActionHadler();
		this.setBackground(Color.WHITE);

		this.buttons = new Vector<JButton>();
		for (GConstants.EShapeToolbar eTool : GConstants.EShapeToolbar.values()) {
			JButton button = new JButton(eTool.getTitle());
			button.setActionCommand(eTool.toString());
			button.addActionListener(this.actionHadler);
			button.setForeground(new Color(69, 0, 79));
			button.setBackground(Color.WHITE);
			this.buttons.add(button);
			this.add(button);
		}
	}

	public void setAssociation(GPanelArea panelArea) {
		this.panelArea = panelArea;
	}

	public void initialize() {
		// set associations

		// set associative attributes
		this.buttons.get(0).doClick();

		// initialize components
		for (JButton button : buttons) {
			if (button.getText().equals(EShapeToolbar.eNShape.getTitle())) {
				button.setToolTipText("입력 창에 다각형의 꼭짓점 개수 입력 후 사용");
			}
		}
	}

	class ActionHadler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			panelArea.getCurrentPanel().setCurrentTool(GConstants.EShapeToolbar.valueOf(event.getActionCommand()));
		}
	}

}
