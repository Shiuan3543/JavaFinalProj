package da2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePanel extends JPanel implements ActionListener {

	private BufferedImage info;
	private BufferedImage info_panel;
	private BufferedImage home_bg;

	private JButton start_btn;

	// history panel
	private JLabel userName;
	private int infoPanel_X = 70;
	private int infoPanel_Y = 30;
	
	private int max_radius;
	private int max_score;
	
	private JLabel max_radius_text;
	private JLabel max_score_text;
	
	private JLabel max_radius_jl;
	private JLabel max_score_jl;

	// 儲存現在顯示畫面的狀態(為何panel)
	private final static int LOGIN = 1;
	private final static int GAME_PANEL = 2;
	private final static int HOME_PANEL = 3;
	private final static int SHOP_PANEL = 4;
	private final static int ACHIEVEMENT_PANEL = 5;
	private final static int RESULT_PANEL = 7;

	// 主畫面的區塊
	private JPanel historyPanel;
	private JPanel homeMainMenuPanel;
	private JPanel skillsPanel;
	private JPanel friendsPanel;

	// homeMainMenuPanel
	private BufferedImage userIcon;
	private String getUserName_forGameFrame;
	private BufferedImage panelInHome_bg;
	private String panelBgPath = "bin/da2/PanelInHome_bg.png";
	private JLabel enterName;
	private JTextField name_tf;
	private JButton name_send;

	// Friends Panel
	private BufferedImage friendsIcon;
	private JLabel friends_title;
	private JTextField search_tf;
	private Icon addFriends_icon = new ImageIcon(getClass().getResource("/icon/ic_person_add_black_24dp_1x.png"));
	private JButton addFriends_btn;

	// Skills Panel
	private BufferedImage skillIcon;
	private BufferedImage Q_skill;
	private BufferedImage W_skill;
	private BufferedImage E_skill;
	private BufferedImage R_skill;
	private JLabel skill_title;
	private JButton skill_edit;
	
	private JLabel Q_skill_info;
	private JLabel W_skill_info;
	private JLabel E_skill_info;
	private JLabel R_skill_info;

	public HomePanel() {
		max_radius = 0;
		max_score = 0;
		setLayout(null);
		setOpaque(false);

		// -- historyPanel --

		historyPanel = new JPanel();
		historyPanel.setOpaque(false);
		historyPanel.setLayout(null);

		max_radius_text = new JLabel("歷史最大半徑:");
		max_radius_text.setFont(new Font("Courier", Font.PLAIN, 20));
		max_radius_text.setBounds(28, 295, 300, 30);
		historyPanel.add(max_radius_text);
		
		max_radius = 10;
		max_radius_jl = new JLabel(Integer.toString(max_radius));
		max_radius_jl.setBounds(160, 295, 100, 30);
		historyPanel.add(max_radius_jl);
		
		max_score_text = new JLabel("歷史最高分數:");
		max_score_text.setFont(new Font("Courier", Font.PLAIN, 20));
		max_score_text.setBounds(28, 335, 300, 30);
		historyPanel.add(max_score_text);
		
		max_score = 20;
		max_score_jl = new JLabel(Integer.toString(max_score));
		max_score_jl.setBounds(160, 335, 100, 30);
		historyPanel.add(max_score_jl);
		
		userName = new JLabel("????");
		userName.setFont(new Font("Courier", Font.PLAIN, 30));
		userName.setBounds(20, 17, 200, 30);
		historyPanel.add(userName);

		historyPanel.setBounds(infoPanel_X, infoPanel_Y, 270, 429);
		add(historyPanel);

		// -- homeMainMenuPanel --
		homeMainMenuPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					panelInHome_bg = ImageIO.read(new File(panelBgPath));
					g.drawImage(panelInHome_bg, 0, 0, null);
					userIcon = ImageIO.read(new File("bin/icon/ic_person_black_24dp_1x.png"));
					g.drawImage(userIcon, 10, 7, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		enterName = new JLabel("輸入用戶名稱:");
		enterName.setFont(new Font("SansSerif", Font.BOLD, 18));
		enterName.setBounds(42, 10, 200, 20);
		homeMainMenuPanel.add(enterName);

		name_tf = new JTextField("", 100);
		name_tf.setBounds(15, 40, 200, 30);
		homeMainMenuPanel.add(name_tf);

		name_send = new JButton("確認");
		name_send.addActionListener(this);
		name_send.setBounds(225, 40, 65, 30);
		homeMainMenuPanel.add(name_send);

		start_btn = new JButton("開始遊戲");
		start_btn.addActionListener(this);
		start_btn.setBounds(20, 80, 100, 40);
		homeMainMenuPanel.add(start_btn);

		homeMainMenuPanel.setOpaque(false);
		homeMainMenuPanel.setBounds(350, 20, 300, 140);
		homeMainMenuPanel.setLayout(null);
		add(homeMainMenuPanel);

		// -- skillsPanel --

		skillsPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					panelInHome_bg = ImageIO.read(new File(panelBgPath));
					g.drawImage(panelInHome_bg, 0, 0, null);
					skillIcon = ImageIO.read(new File("bin/icon/ic_equalizer_black_24dp_1x.png"));
					g.drawImage(skillIcon, 10, 7, null);
					
					Q_skill = ImageIO.read(new File("bin/skill/Q_skill.png"));
					g.drawImage(Q_skill, 10, 40, null);
					
					W_skill = ImageIO.read(new File("bin/skill/W_skill.png"));
					g.drawImage(W_skill, 10, 120, null);
					
					E_skill = ImageIO.read(new File("bin/skill/E_skill.png"));
					g.drawImage(E_skill, 10, 200, null);
					
					R_skill = ImageIO.read(new File("bin/skill/R_skill.png"));
					g.drawImage(R_skill, 10, 280, null);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		Q_skill_info = new JLabel("<html>朝敵人開槍，造成當前生命百分比的傷害，冷卻時間:2.5秒</html>");
		Q_skill_info.setBounds(80, 15, 200, 100);
		skillsPanel.add(Q_skill_info);
		
		W_skill_info = new JLabel("<html>短時間內無敵，冷卻時間:4秒</html>");
		W_skill_info.setBounds(80, 100, 200, 100);
		skillsPanel.add(W_skill_info);
		
		E_skill_info = new JLabel("<html>進行一小段位移，冷卻時間:7秒</html>");
		E_skill_info.setBounds(80, 180, 200, 100);
		skillsPanel.add(E_skill_info);
		
		R_skill_info = new JLabel("<html>對地圖中心進行鏡射，延遲1秒後，閃現至該位置，冷卻時間:12秒</html>");
		R_skill_info.setBounds(80, 255, 200, 100);
		skillsPanel.add(R_skill_info);
		

		skill_title = new JLabel("技能");
		skill_title.setFont(new Font("SansSerif", Font.BOLD, 18));
		skill_title.setBounds(42, 10, 200, 20);
		skillsPanel.add(skill_title);

		skillsPanel.setOpaque(false);
		skillsPanel.setBounds(350, 180, 300, 390);
		skillsPanel.setLayout(null);
		add(skillsPanel);

		// -- friendsPanel --

		friendsPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					panelInHome_bg = ImageIO.read(new File(panelBgPath));
					g.drawImage(panelInHome_bg, 0, 0, null);
					friendsIcon = ImageIO.read(new File("bin/icon/ic_people_black_24dp_1x.png"));
					g.drawImage(friendsIcon, 10, 7, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		friends_title = new JLabel("好友列表");
		friends_title.setFont(new Font("SansSerif", Font.BOLD, 18));
		friends_title.setBounds(42, 10, 200, 20);
		friendsPanel.add(friends_title);

		search_tf = new JTextField("", 100);
		search_tf.setBounds(15, 40, 200, 30);
		friendsPanel.add(search_tf);

		addFriends_btn = new JButton("新增好友", addFriends_icon);
		addFriends_btn.setBounds(15, 80, 120, 40);
		friendsPanel.add(addFriends_btn);

		friendsPanel.setOpaque(false);
		friendsPanel.setBounds(670, 20, 330, 550);
		friendsPanel.setLayout(null);
		add(friendsPanel);

	}

	protected void paintComponent(Graphics g) {
		try {
			home_bg = ImageIO.read(new File("bin/sao/home_bg.jpg"));
			g.drawImage(home_bg, 0, 0, null);

			info_panel = ImageIO.read(new File("bin/sao/panel.png"));
			g.drawImage(info_panel, infoPanel_X, infoPanel_Y, null);
			info = ImageIO.read(new File("bin/sao/info.png"));
			g.drawImage(info, infoPanel_X + 25, infoPanel_Y + 63, null);

			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier", Font.PLAIN, 30));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 監聽器實作區 **/

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == start_btn) {
			Start.changeState(GAME_PANEL);
		} else if (e.getSource() == name_send) {
			getUserName_forGameFrame = name_tf.getText();
			userName.setText(name_tf.getText());
			name_tf.setText("");
		}

	}

	public String getName() {
		return getUserName_forGameFrame;
	}

	/** 監聽器分隔 **/

	/** 監聽器結束 **/

}
