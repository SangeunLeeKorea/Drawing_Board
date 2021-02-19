package main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import menus.GEditMenu;
import menus.GFileMenu;
import menus.GMenu;
import menus.GShapeMenu;
import shape.GArrow;
import shape.GFreedraw;
import shape.GHeart;
import shape.GImage;
import shape.GLine;
import shape.GNShape;
import shape.GOval;
import shape.GPolygon;
import shape.GRectangle;
import shape.GRoundRectangle;
import shape.GShape;
import shape.GStar;
import shape.GTriangle;

public class GConstants {

	public static final long serialVersionUID = 1L;

	public GConstants() {
	}

	public enum EMainFrame {
		eWidth(1600), eHeight(800);

		private int value;

		private EMainFrame(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum EDrawingPanel {
		eWidth(1000), eHeight(600);

		private int value;

		private EDrawingPanel(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum EMenubar {
		eFile(new GFileMenu("����")), eEdit1(new GEditMenu("����")), eColor(new GShapeMenu("���� �Ӽ�"));

		private GMenu menu;

		private EMenubar(GMenu menu) {
			this.menu = menu;
		}

		public GMenu getMenu() {
			return this.menu;
		}
	}

	public interface IMenu {
		public String getTitle();

		public String getActionCommand();

		public KeyStroke getKeyStroke();
	}

	@SuppressWarnings("deprecation")
	public enum EFileMenu implements IMenu {
		eNew("New", "nnew", KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK)),
		eOpen("����", "open", KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK)),
		eSave("����", "save", KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK)),
		eSaveAs("�ٸ��̸�����", "saveAs", KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eSaveAsImg("JPG�� ����", "saveAsJPG", KeyStroke.getKeyStroke('J', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eClose("�ݱ�", "close", KeyStroke.getKeyStroke('C', InputEvent.ALT_MASK)),
		eQuit("����", "quit", KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));

		private String title;
		private String actionCommand;
		private KeyStroke keyStroke;

		private EFileMenu(String title, String actionCommand, KeyStroke keyStroke) {
			this.title = title;
			this.actionCommand = actionCommand;
			this.keyStroke = keyStroke;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public String getActionCommand() {
			return this.actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return keyStroke;
		}
	}

	@SuppressWarnings("deprecation")
	public enum EEditMenu implements IMenu {
		eResizePanel("�̹��� ũ��", "resizePanel", KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK)),
		eBackground("��� �� ����", "changeBackground",
				KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eUndo("�ǵ�����", "undo", KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK)),
		eRedo("�ٽý���", "redo", KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK)),
		eCopy("����", "copy", KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK)),
		eCut("�ڸ���", "cut", KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK)),
		ePaste("�ٿ��ֱ�", "paste", KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK)),
		eDelete("�����", "delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)),
		eGroup("�׷�", "group", KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK)),
		eUnGroup("�׷� ����", "ungroup", KeyStroke.getKeyStroke('U', InputEvent.CTRL_MASK)),
		eOrder("���� �ٲٱ�", "setOrder", null),
		eClear("��� �����", "clearAll", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK));

		private String title;
		private String actionCommand;
		private KeyStroke keyStroke;

		private EEditMenu(String title, String actionCommand, KeyStroke keyStroke) {
			this.title = title;
			this.actionCommand = actionCommand;
			this.keyStroke = keyStroke;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public String getActionCommand() {
			return this.actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return this.keyStroke;
		}
	}

	@SuppressWarnings("deprecation")
	public enum EShapeMenu implements IMenu, IPMenuItem {
		eLineColor("���� ��", "lineC", KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK)),
		eFillColor("ä��� ��", "fillC", KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK)),
		eLineThick("�׵θ� �β�", "lineThick", KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK)),
		eLineDot("����", "lineDot", KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eFlip("����", "filp", null),
		eRotate("ȸ��", "rotate", KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eResize("ũ�� ����", "resize", KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eMove("��ġ ����", "move", KeyStroke.getKeyStroke('M', InputEvent.CTRL_MASK)),
		eMoveTen("��ġ ����(10�� �̵�)", "moveTen", null);

		private String title;
		private String actionCommand;
		private KeyStroke keyStroke;

		private EShapeMenu(String title, String actionCommand, KeyStroke keyStroke) {
			this.title = title;
			this.actionCommand = actionCommand;
			this.keyStroke = keyStroke;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public String getActionCommand() {
			return this.actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return keyStroke;
		}
	}

	public enum EFlip implements IPMenuItem {
		eHorizontal("�¿����", "horizontalFlip"), eVertical("���Ϲ���", "verticalFlip");

		String title;
		String actionCommand;

		private EFlip(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}
	}

	public enum EMoveTen implements IPMenuItem {
		eUp("��", "shapeUp", KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_MASK)),
		eDown("�Ʒ�", "shapeDown", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_MASK)),
		eLeft("����", "shapeLeft", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_MASK)),
		eRight("������", "shapeRight", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_MASK));

		String title;
		String actionCommand;
		KeyStroke keyStroke;

		private EMoveTen(String title, String actionCommand, KeyStroke keyStroke) {
			this.title = title;
			this.actionCommand = actionCommand;
			this.keyStroke = keyStroke;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return keyStroke;
		}
	}

	public enum EOrder implements IPMenuItem {
		eFirst("�� ������", "first"), eForward("������", "forward"), eLast("�� �ڷ�", "last"), eBackward("�ڷ�", "backward");

		private String title;
		private String actionCommand;

		private EOrder(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return this.title;
		}

		@Override
		public String getActionCommand() {
			return this.actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}
	}

	public enum EMenuState {
		eNone, eClicked;
	}

	public enum EShapeToolbar {
		eSelection("���� ����", null), eRectangle("�׸�", new GRectangle()), eRoundRect("�ձ� �׸�", new GRoundRectangle()),
		eOval("��", new GOval()), eLine("����", new GLine()), ePolygon("�ٰ���", new GPolygon()),
		eTriangle("�ﰢ��", new GTriangle()), eStar("��", new GStar()), eHeart("��Ʈ", new GHeart()),
		eArrow("ȭ��ǥ", new GArrow()), eNShape("���ٰ���", new GNShape()), eImage("�̹���", new GImage()),
		eFreeDraw("���� �׸���", new GFreedraw());

		private String title;
		private GShape tool;

		private EShapeToolbar(String title, GShape tool) {
			this.title = title;
			this.tool = tool;
		}

		public String getTitle() {
			return this.title;
		}

		public GShape getTool() {
			return this.tool;
		}
	}

	public final static int MAXPOINTS = 100;

	public enum ECursor {
		eDefault(new Cursor(Cursor.DEFAULT_CURSOR)), eMove(new Cursor(Cursor.MOVE_CURSOR)), eRotate(rotateCursor()),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)), eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)), eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)), eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)), eSW(new Cursor(Cursor.SW_RESIZE_CURSOR));

		private Cursor cursor;

		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}

		private static Cursor rotateCursor() {
			Cursor cursor;
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Image image = toolkit.getImage("data/rotate.png");
			cursor = toolkit.createCustomCursor(image, new Point(0, 0), "");
			return cursor;
		}

		public Cursor getCursor() {
			return this.cursor;
		}
	}

	public enum EAnchor {
		eRadius(7), eLineLength(15), eRotateSize(20);

		int value;

		private EAnchor(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public interface IPMenu {
		public String getTitle();

		public IPMenuItem[] getItems();
	}

	public interface IPMenuItem {
		public String getTitle();

		public String getActionCommand();

		public KeyStroke getKeyStroke();
	}

	public enum ESelectedPMenu implements IPMenu {
		eOrder("���� �ٲٱ�", EOrder.values()), eColor("����", EShapeMenu.values()), eGroup("�׷�/�׷� Ǯ��", EGroupMenu.values()),
		eEdit("����/�ٿ��ֱ�", ECopyPasteMenu.values());

		String title;
		IPMenuItem[] values;

		ESelectedPMenu(String title, IPMenuItem[] values) {
			this.title = title;
			this.values = values;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IPMenuItem[] getItems() {
			return values;
		}
	}

	public enum EGroupMenu implements IPMenuItem {
		eGroup("�׷�", "group"), eUnGroup("�׷� ����", "ungroup");

		private String title;
		private String actionCommand;

		private EGroupMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}

	}

	public enum ELineDot {
		e0f(1, 0f), e1f(1, 1f), e4f(1, 4f), e10f(1, 10f);

		float[] array;

		private ELineDot(float first, float second) {
			this.array = new float[2];
			array[0] = first;
			array[1] = second;
		}

		public float getFirst() {
			return array[0];
		}

		public float getSecond() {
			return array[1];
		}

		public float[] getArray() {
			return array;
		}
	}

	public enum ELineThick {
		e1(1), e2(2), e3(3), e4(4), e5(5), e6(6), e7(7);

		int value;

		private ELineThick(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum EPanelListMenu implements IPMenu {
		eAttribute("�̹��� �Ӽ�", EPanelATmenu.values()), eEdit("����/�ٿ��ֱ�", ECopyPasteMenu.values()),
		eFile("new/close", ENewCloseMenu.values());

		IPMenuItem[] items;
		String title;

		private EPanelListMenu(String title, IPMenuItem[] items) {
			this.title = title;
			this.items = items;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public IPMenuItem[] getItems() {
			return items;
		}

	}

	public enum EPanelATmenu implements IPMenuItem {
		eName("�̸� �ٲٱ�", "changeName"), eResizePanel("�̹��� ũ��", "resizePanel");

		String title;
		String actionCommand;

		private EPanelATmenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}
	}

	public enum ECopyPasteMenu implements IPMenuItem {
		eCopy("����", "copy"), ePaste("�ٿ��ֱ�", "paste"), eCut("�ڸ���", "cut");

		String title;
		String actionCommand;

		private ECopyPasteMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}
	}

	public enum ENewCloseMenu implements IPMenuItem {
		eNew("new", "nnew"), eClose("�ݱ�", "close");

		String title;
		String actionCommand;

		private ENewCloseMenu(String title, String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public String getActionCommand() {
			return actionCommand;
		}

		@Override
		public KeyStroke getKeyStroke() {
			return null;
		}
	}
}
