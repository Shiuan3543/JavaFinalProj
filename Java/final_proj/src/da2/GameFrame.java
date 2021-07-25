package da2;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.plaf.LayerUI;

import com.sun.xml.internal.ws.api.server.Container;


public class GameFrame extends JFrame implements ActionListener, KeyListener, ComponentListener {

	// 儲存數值
	private int coin_value;
	private int diamond_value;
	private String player_name;

	private String temp_string;

	private final static int COIN = 1;
	private final static int DIAMOND = 2;
	
	// 儲存現在顯示畫面的狀態(為何panel)
	private final static int LOGIN = 1;
	private final static int GAME_PANEL = 2;
	private final static int HOME_PANEL = 3;
	private final static int SHOP_PANEL = 4;
	private final static int ACHIEVEMENT_PANEL = 5;
	private final static int RESULT_PANEL = 7;

	// frame大小
	private int old_width;
	private int old_height;
	
	private int maxai;
	
	// gameBar上的button
	private Icon home_icon = new ImageIcon(getClass().getResource("/icon/ic_home_black_24dp_1x.png"));
	private JButton home_btn = new JButton("主頁", home_icon); // 呼叫選單
	private Icon shop_icon = new ImageIcon(getClass().getResource("/icon/ic_add_shopping_cart_black_24dp_1x.png"));
	private JButton shop_btn = new JButton("商店", shop_icon); // 呼叫選單
	private Icon achievement_icon = new ImageIcon(getClass().getResource("/icon/ic_grade_black_24dp_1x.png"));
	private JButton achievement_btn = new JButton("成就", achievement_icon); // 呼叫選單
	private Icon menu_icon = new ImageIcon(getClass().getResource("/icon/ic_reorder_black_24dp_1x.png"));
	private JButton menu_btn = new JButton("選單", menu_icon); // 呼叫選單

	// menuPanel
	private JPanel menuPanel = new JPanel();
	// FPS -> setFps(int fps)
	// AI數量 0~5個(傳進gamePanel的值要+1) -> setBallMax(int BallMax)
	// AI顏色  -> setAiColor(Color color)
	// 設定玩家子彈顏色  -> setSkillColor(Color color)

	// 選單上的button
	private JButton conti = new JButton("繼續遊戲"); // 繼續
	private JButton options = new JButton("遊戲選項");
	private JButton help = new JButton("遊戲幫助");
	private JButton backHome = new JButton("返回主頁"); // 返回主畫面
	private JButton exitGame = new JButton("離開遊戲");
	private JPanel menu = new JPanel(); // 選單本身
	private JPanel gameBar; // 遊戲內的工具列
	private JPanel playerBar; // 主畫面上的角色基本名稱

	private BufferedImage gameBar_bg;

	// playerBar上的元件
	private JPanel playerBar_west;
	private JPanel playerBar_east;
	private JLabel rank;
	private JLabel rank_lv;
	private JProgressBar rank_prog;
	private JLabel home_top_icon_jl;
	private ImageIcon home_top_icon;
	private JLabel coin_icon_jl;
	private ImageIcon coin_icon;
	private JLabel coin;
	private JLabel diamond_icon_jl;
	private ImageIcon diamond_icon;
	private JLabel diamond;
	private BufferedImage playerBar_bg;
	//
	private JPanel config;
	private JLabel fpslable, ailable;
	private JTextField fpsField, maxaiField;
	private int meunx, meuny, started;
	private JButton fpsButton, aiButton;
	private JButton colorBut;
	private Color color;
	//
	private int jl_width;
	private int jl_height;
	private int fps;
	private FlowLayout fl;
	private FlowLayout fl2;
	

	// 大類別的panel
	private static LoginPanel login;
	private static GamePanel gamePanel;
	private static HomePanel homePanel = new HomePanel();
	private static ShopPanel shopPanel = new ShopPanel();
	private static AchievementPanel achievementPanel = new AchievementPanel();
	private static ResultPanel resultPanel = new ResultPanel();

	public GameFrame(int width, int height) {
		
		super();
		// 2017/5/17
		fps = 60;
		meuny=100;
		meunx=100;
		maxai=1;
		//
		setResizable(false);
		this.old_width = width;
		this.old_height = height;
		gamePanel = null;
		gamePanel = new GamePanel(width, height,fps,maxai,color);
		
		setSize(width, height);
		setTitle("Game");
		setIconImage(getMyIconImage());

		addKeyListener(this);
		addComponentListener(this);

		init();

		login = new LoginPanel();
		login.setDefaultHeightAndWidth(old_width, old_height);
		add(login);
		setFocusable(true);
	}

	public void init() {

		// --- GameBar --- //
		gameBar = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					gameBar_bg = ImageIO.read(new File("bin/da2/gameBar.png"));
					// 繪製一張圖片作為背景
					g.drawImage(gameBar_bg, 0, 0, null);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		};
		gameBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

		home_btn.addActionListener(this);
		gameBar.add(home_btn);
		shop_btn.addActionListener(this);
		gameBar.add(shop_btn);
		achievement_btn.addActionListener(this);
		gameBar.add(achievement_btn);
		menu_btn.addActionListener(this);
		gameBar.add(menu_btn);

		// ---MenuPanel --- //
		
		menu.setOpaque(false);
		
		conti.addActionListener(this);
		options.addActionListener(this);
		help.addActionListener(this);
		backHome.addActionListener(this);
		exitGame.addActionListener(this);

		conti.setEnabled(false);

		menu.add(conti);
		menu.add(options);
		menu.add(help);
		menu.add(backHome);
		menu.add(exitGame);

		// --- PlayerBar --- //

		playerBar = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					playerBar_bg = ImageIO.read(new File("bin/da2/playerBar.png"));
					// 繪製一張圖片作為背景
					g.drawImage(playerBar_bg, 0, 0, null);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		};
		playerBar.setLayout(new BorderLayout());

		fl = new FlowLayout(1, 20, 15);
		fl2 = new FlowLayout();

		playerBar_west = new JPanel();
		playerBar_west.setLayout(fl);
		playerBar_west.setOpaque(false);

		playerBar_east = new JPanel();
		playerBar_east.setLayout(fl2);
		playerBar_east.setOpaque(false);

		rank = new JLabel("Lv:");
		rank_lv = new JLabel("99");
		rank_prog = new JProgressBar();

		home_top_icon = new ImageIcon("bin/sao/home_top_icon.png");
		coin_icon = new ImageIcon("bin/sao/coin.png");
		diamond_icon = new ImageIcon("bin/sao/diamond.png");

		home_top_icon.setImage(home_top_icon.getImage().getScaledInstance(home_top_icon.getIconWidth(),
				home_top_icon.getIconHeight(), Image.SCALE_DEFAULT));
		home_top_icon_jl = new JLabel(home_top_icon);

		jl_width = 35;
		jl_height = 35;

		coin_icon.setImage(coin_icon.getImage().getScaledInstance(jl_width, jl_height, Image.SCALE_DEFAULT));
		coin_icon_jl = new JLabel(coin_icon);
		coin = new JLabel("9999");

		diamond_icon.setImage(diamond_icon.getImage().getScaledInstance(jl_width, jl_height, Image.SCALE_DEFAULT));
		diamond_icon_jl = new JLabel(diamond_icon);
		diamond = new JLabel("9999");

		playerBar_west.add(rank);
		playerBar_west.add(rank_lv);
		playerBar_west.add(rank_prog);

		playerBar.add(home_top_icon_jl, BorderLayout.CENTER);

		fl2.setHgap(20);
		fl2.setVgap(5);
		playerBar_east.add(coin_icon_jl);
		playerBar_east.add(coin);
		playerBar_east.add(diamond_icon_jl);
		playerBar_east.add(diamond);

		playerBar.add(playerBar_west, BorderLayout.WEST);
		playerBar.add(playerBar_east, BorderLayout.EAST);
		
		// -- config --
		
		fpslable = new JLabel("Fps");
		maxaiField = new JTextField("1");
		ailable = new JLabel("AI的數量");
		fpsButton = new JButton("套用");
		fpsButton.addActionListener(this);
		aiButton = new JButton("套用");
		aiButton.addActionListener(this);

		fpsField = new JTextField("60");

		config = new JPanel();
		config.setLayout(null);
		ailable.setBounds(200 + meunx, 100 + meuny, 200, 25);
		maxaiField.setBounds(300 + meunx, 100 + meuny, 200, 25);
		aiButton.setBounds(500 + meunx, 100 + meuny, 100, 25);

		fpslable.setBounds(200 + meunx, 200 + meuny, 200, 25);
		fpsField.setBounds(300 + meunx, 200 + meuny, 200, 25);
		fpsButton.setBounds(500 + meunx, 200 + meuny, 100, 25);
		colorBut = new JButton("AI的顏色");
		colorBut.addActionListener(this);

		colorBut.setBounds(200 + meunx, 300 + meuny, 100, 100);

		config.add(fpsButton);
		config.add(ailable);
		config.add(maxaiField);
		config.add(fpsField);
		config.add(fpslable);
		config.add(aiButton);
		config.add(colorBut);
		
		menu.add(config);
		

	}

	public void setValue(int type, int value) {
		switch (type) {
		case COIN:
			this.coin_value += value;
			temp_string = Integer.toString(coin_value);
			coin.setText(temp_string);
			break;
		case DIAMOND:
			this.diamond_value += value;
			temp_string = Integer.toString(diamond_value);
			diamond.setText(temp_string);
			break;
		}
	}

	private Image getMyIconImage() {
		try {
			return ImageIO.read(GameFrame.class.getResource("icon.png"));
		} catch (Exception ex) {
			return null;
		}
	}

	public void showPlayerBar() {
		playerBar.setVisible(true);
		add(playerBar, BorderLayout.NORTH);
	}

	public void createGamePanel() {
		gamePanel = null;
		gamePanel = new GamePanel(old_width, old_height,fps,maxai,color);
		gamePanel.setVisible(true);
		if (homePanel.getName() != null) {
			gamePanel.setName(homePanel.getName());
		} else {
			gamePanel.setName("defalut");
		}
		gamePanel.addKeyListener(this);
		gamePanel.setSkin(0, 0);
		add(gamePanel);
	}

	public void showGameBar() {
		gameBar.setVisible(true);
		add(gameBar, BorderLayout.SOUTH);
	}

	public void showMenuPanel() {
		add(menu, BorderLayout.NORTH);
		add(config);
		gamePanel.setVisible(false);
		config.setVisible(true);
		menu.setVisible(true);
	}

	public void changeState(int state) {
		allClosePanel();
		if (state == LOGIN) {
			add(login);
			login.setVisible(true);
			gamePanel.stopGame();
		} else if (state == GAME_PANEL) {
			gamePanel = null;
			createGamePanel();
			showGameBar();
			conti.setEnabled(true);
			gamePanel.setSkin(shopPanel.getSkinType(), shopPanel.getSkinIndex());
			System.out.println("game Frame:" + shopPanel.getSkinType() + "," + shopPanel.getSkinIndex());
			gamePanel.startGame();

		} else if (state == HOME_PANEL) {
			add(homePanel);
			showGameBar();
			showPlayerBar();
			homePanel.setVisible(true);
			gamePanel.stopGame();
		} else if (state == SHOP_PANEL) {
			showGameBar();
			showPlayerBar();
			add(shopPanel);
			shopPanel.setVisible(true);
			gamePanel.stopGame();
		} else if (state == ACHIEVEMENT_PANEL) {
			showGameBar();
			showPlayerBar();
			add(achievementPanel);
			achievementPanel.setVisible(true);
			gamePanel.stopGame();
		}
	}

	public void allClosePanel() {
		login.setVisible(false);
		gamePanel.setVisible(false);
		homePanel.setVisible(false);
		shopPanel.setVisible(false);
		achievementPanel.setVisible(false);
		resultPanel.setVisible(false);
		menu.setVisible(false);
		config.setVisible(false);
		playerBar.setVisible(false);
		gameBar.setVisible(false);

	}

	/** 監聽器實作區 **/

	public void actionPerformed(ActionEvent e) {

		/** gameBar **/
		if (e.getSource() == home_btn) {
			allClosePanel();
			conti.setEnabled(false);
			changeState(HOME_PANEL);
		} else if (e.getSource() == shop_btn) {
			allClosePanel();
			conti.setEnabled(false);
			changeState(SHOP_PANEL);
		} else if (e.getSource() == achievement_btn) {
			allClosePanel();
			conti.setEnabled(false);
			changeState(ACHIEVEMENT_PANEL);
		} else if (e.getSource() == menu_btn) {
			gamePanel.stopGame();
			allClosePanel();
			showGameBar();
			showMenuPanel();
			/** menu **/
		} else if (e.getSource() == conti) {
			allClosePanel();
			gamePanel.setVisible(true);
			gameBar.setVisible(true);
			add(gameBar, BorderLayout.SOUTH);
			gamePanel.updateUI();
			gamePanel.startGame();
//			conti.setEnabled(false);
//			changeState(GAME_PANEL);
//			remove(menu);
//			gamePanel.setVisible(true);
//			gamePanel.startGame();
//			changeState(GAME_PANEL);
			
		} else if (e.getSource() == options) {
			
		} else if (e.getSource() == help) {
			JOptionPane.showConfirmDialog(this,
					"*** 關於本程式 ***\n大一下高等程式語言期末專題\n組員:陳允達、曾漢文、吳偉傑\n\n*** 操作方法 ***\n\n右鍵操縱小球\nQ、W、E、R、:施放技能", "遊戲幫助",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == backHome) {
			conti.setEnabled(false);
			changeState(HOME_PANEL);
		} else if (e.getSource() == exitGame) {
			System.exit(0);
			/// options 
		} else if (e.getSource() == aiButton) {
			maxai = Integer.parseInt(maxaiField.getText());
		} else if (e.getSource() == fpsButton) {
			fps = Integer.parseInt(fpsField.getText());
		} else if (e.getSource() == colorBut) {
			color = JColorChooser.showDialog(this, "Choose a color", color);
			colorBut.setBackground(color);
		}
		
		
//		color = JColorChooser.showDialog(PainterPanel.this, "Choose a color", color);
//		drawPanel.setColor(color);
//		colorBut.setBackground(color);

	}

	
	
	/** 監聽器分隔 **/

	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		gamePanel.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keywork(){
		addKeyListener(this);
		this.setFocusable(true);
	}

	public void componentResized(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}
	


	

	/** 監聽器結束 **/

}
