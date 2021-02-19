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
		eFile(new GFileMenu("파일")), eEdit1(new GEditMenu("편집")), eColor(new GShapeMenu("도형 속성"));

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
		eOpen("열기", "open", KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK)),
		eSave("저장", "save", KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK)),
		eSaveAs("다른이름으로", "saveAs", KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eSaveAsImg("JPG로 저장", "saveAsJPG", KeyStroke.getKeyStroke('J', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eClose("닫기", "close", KeyStroke.getKeyStroke('C', InputEvent.ALT_MASK)),
		eQuit("종료", "quit", KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));

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
		eResizePanel("이미지 크기", "resizePanel", KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK)),
		eBackground("배경 색 변경", "changeBackground",
				KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eUndo("되돌리기", "undo", KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK)),
		eRedo("다시실행", "redo", KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK)),
		eCopy("복사", "copy", KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK)),
		eCut("자르기", "cut", KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK)),
		ePaste("붙여넣기", "paste", KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK)),
		eDelete("지우기", "delete", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)),
		eGroup("그룹", "group", KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK)),
		eUnGroup("그룹 해제", "ungroup", KeyStroke.getKeyStroke('U', InputEvent.CTRL_MASK)),
		eOrder("순서 바꾸기", "setOrder", null),
		eClear("모두 지우기", "clearAll", KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK));

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
		eLineColor("라인 색", "lineC", KeyStroke.getKeyStroke('L', InputEvent.CTRL_MASK)),
		eFillColor("채우기 색", "fillC", KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK)),
		eLineThick("테두리 두께", "lineThick", KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK)),
		eLineDot("점선", "lineDot", KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eFlip("반전", "filp", null),
		eRotate("회전", "rotate", KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eResize("크기 조절", "resize", KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK ^ InputEvent.SHIFT_MASK)),
		eMove("위치 변경", "move", KeyStroke.getKeyStroke('M', InputEvent.CTRL_MASK)),
		eMoveTen("위치 변경(10씩 이동)", "moveTen", null);

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
		eHorizontal("좌우반전", "horizontalFlip"), eVertical("상하반전", "verticalFlip");

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
		eUp("위", "shapeUp", KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_MASK)),
		eDown("아래", "shapeDown", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_MASK)),
		eLeft("왼쪽", "shapeLeft", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_MASK)),
		eRight("오른쪽", "shapeRight", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_MASK));

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
		eFirst("맨 앞으로", "first"), eForward("앞으로", "forward"), eLast("맨 뒤로", "last"), eBackward("뒤로", "backward");

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
		eSelection("선택 도구", null), eRectangle("네모", new GRectangle()), eRoundRect("둥근 네모", new GRoundRectangle()),
		eOval("원", new GOval()), eLine("라인", new GLine()), ePolygon("다각형", new GPolygon()),
		eTriangle("삼각형", new GTriangle()), eStar("별", new GStar()), eHeart("하트", new GHeart()),
		eArrow("화살표", new GArrow()), eNShape("정다각형", new GNShape()), eImage("이미지", new GImage()),
		eFreeDraw("자유 그리기", new GFreedraw());

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
		eOrder("순서 바꾸기", EOrder.values()), eColor("색상", EShapeMenu.values()), eGroup("그룹/그룹 풀기", EGroupMenu.values()),
		eEdit("복사/붙여넣기", ECopyPasteMenu.values());

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
		eGroup("그룹", "group"), eUnGroup("그룹 해제", "ungroup");

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
		eAttribute("이미지 속성", EPanelATmenu.values()), eEdit("복사/붙여넣기", ECopyPasteMenu.values()),
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
		eName("이름 바꾸기", "changeName"), eResizePanel("이미지 크기", "resizePanel");

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
		eCopy("복사", "copy"), ePaste("붙여넣기", "paste"), eCut("자르기", "cut");

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
		eNew("new", "nnew"), eClose("닫기", "close");

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
