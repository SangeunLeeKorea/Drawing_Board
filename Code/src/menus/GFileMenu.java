package menus;

import java.awt.FileDialog;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import file.GFile;
import main.GConstants;
import main.GConstants.EFileMenu;
import main.GConstants.IMenu;

public class GFileMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	private GFile gFile;

	public GFileMenu(String name) {
		super(name);
		gFile = new GFile();
	}

	public void initialize() {
	}

	public void nnew() {
		gPanelArea.addNewPanel();
	}

	@SuppressWarnings("deprecation")
	public void open() {
		FileDialog dialog = new FileDialog(gMainFrame, "열기", FileDialog.LOAD);
		dialog.setSize(300, 300);
		dialog.show();

		String path = dialog.getDirectory();
		String fileName = dialog.getFile();
		if (path != null) {
			Object shapes = gFile.readObject(new File(path + "\\" + fileName));
			gPanelArea.openPanel(shapes, fileName);
		}
	}

	public void save() {
		boolean result = gFile.saveObject(gPanelArea.getCurrentPanel().getShapes());
		if (!result) {
			saveAs();
		}
	}

	@SuppressWarnings("deprecation")
	public void saveAsJPG() {
		FileDialog dialog = new FileDialog(gMainFrame, "JPG로 저장", FileDialog.SAVE);
		dialog.setSize(300, 300);
		dialog.show();

		String path = dialog.getDirectory();
		String fileName = dialog.getFile();
		File file = new File(path + "\\" + fileName + ".jpg");
		if (path != null) {
			try {
				Image img = gPanelArea.getCurrentPanel().getFinishedImg();
				ImageIO.write((RenderedImage) img, "jpg", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void saveAs() {
		String name = gPanelArea.getCurrentPanel().getName();
		FileDialog dialog = new FileDialog(gMainFrame, "다른 이름으로 저장", FileDialog.SAVE);
		dialog.setSize(300, 300);
		dialog.setFile(name);
		dialog.show();

		String path = dialog.getDirectory();
		String fileName = dialog.getFile();
		File file = new File(path + "\\" + fileName);
		if (path != null) {
			gFile.saveAsObject(gPanelArea.getCurrentPanel().getShapes(), file);
			gPanelArea.changeName(fileName);
		}
	}

	public void close() {
		if (checkSave()) {
			gPanelArea.closePanel();
		}
	}

	public void quit() {
		boolean checked = true;
		for (int i=0;checked && i<gPanelArea.getPanelsSize();i++) {
			gPanelArea.setCurrentPanel(i);
			checked = checkSave();
		}
		if (checked) {
			System.exit(0);
		}
	}

	public boolean checkSave() {
		if (!gPanelArea.getCurrentPanel().isSaved()) {
			String name = gPanelArea.getCurrentPanel().getName();
			String message = name+" 파일의 변경내용을 저장하시겠습니까?";
			String title = "저장 확인";
			int option = JOptionPane.YES_NO_CANCEL_OPTION;
			int result = JOptionPane.showConfirmDialog(gPanelArea.getCurrentPanel(), message, title, option);
			if (result == JOptionPane.YES_OPTION) {
				save();
			} else if (result == JOptionPane.CANCEL_OPTION) {
				return false;
			} else if (result == JOptionPane.CLOSED_OPTION) {
				return false;
			}
		}
		return true;
	}

	@Override
	public IMenu[] values() {
		return EFileMenu.values();
	}
}