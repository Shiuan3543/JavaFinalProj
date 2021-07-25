package da2;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel implements ActionListener, ComponentListener {

	private JButton login;
	private JButton sign_up;

	private JPanel btnPanel = new JPanel();

	// 儲存現在顯示畫面的狀態(為何panel)
	private final static int LOGIN = 1;
	private final static int GAME_PANEL = 2;
	private final static int HOME_PANEL = 3;
	private final static int SHOP_PANEL = 4;
	private final static int ACHIEVEMENT_PANEL = 5;

	private JLabel accLabel = new JLabel("Account: ");;
	private JLabel passLabel;
	private JLabel noneLabel;

	private JTextField account;
	private JPasswordField passwd;

	private GridBagLayout gb = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();

	private ShowTitle show_title_img = new ShowTitle();

	private BufferedImage title_img;

	private final int NONE = 0;
	private final int HORIZONTAL = 1;
	private final int VERTICAL = 2;
	private final int BOTH = 3;

	private int old_width;
	private int new_width;
	private int old_height;
	private int new_height;

	private Insets insets;

	public LoginPanel() {

		setLayout(new BorderLayout());
		addComponentListener(this);

		try {
			title_img = ImageIO.read(new File("bin/da2/title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		add(show_title_img);

		btnPanel.setLayout(gb);
		insets = new Insets(5, 5, 5, 5);
		gbc.insets = insets;

		account = new JTextField(10);
		passLabel = new JLabel("Password: ");
		passwd = new JPasswordField(10);

		setGridBag(0, 0, 2, 1, BOTH);
		btnPanel.add(accLabel, gbc);
		setGridBag(1, 0, 2, 1, BOTH);
		btnPanel.add(account, gbc);
		setGridBag(2, 0, 2, 1, BOTH);
		btnPanel.add(passLabel, gbc);
		setGridBag(3, 0, 2, 1, BOTH);
		btnPanel.add(passwd, gbc);

		login = new JButton("Login");
		login.addActionListener(this);
		sign_up = new JButton("Sing Up");

		insets.top = 10;
		insets.right = 10;
		gbc.insets = insets;
		setGridBag(4, 0, 1, 1, BOTH);
		btnPanel.add(login, gbc);

		insets.top = 10;
		insets.right = 5;
		gbc.insets = insets;
		setGridBag(4, 1, 1, 1, BOTH);
		btnPanel.add(sign_up, gbc);

		setGridBag(5, 0, 2, 1, BOTH);
		noneLabel = new JLabel("    ");
		btnPanel.add(noneLabel, gbc);

		add(btnPanel, BorderLayout.SOUTH);

	}

	public void setGridBag(int row, int col, int width, int height, int fill) {
		gbc.gridx = col;
		gbc.gridy = row;
		gbc.gridwidth = width;
		gbc.gridheight = height;

		switch (fill) {
		case BOTH:
			gbc.fill = GridBagConstraints.BOTH;
			break;
		case HORIZONTAL:
			gbc.fill = GridBagConstraints.HORIZONTAL;
			break;
		case VERTICAL:
			gbc.fill = GridBagConstraints.VERTICAL;
			break;
		case NONE:
			gbc.fill = GridBagConstraints.NONE;
			break;

		}

	}

	class ShowTitle extends JPanel {
		public void paint(Graphics g) {
//			g.drawImage(title_img, (new_width / 2) - old_width / 2 + 100, (new_height / 2) - old_height / 2 + 30, null);
			g.drawImage(title_img, (new_width / 2) - old_width / 2 + 100, (new_height / 2) - old_height / 2 + 30, null);
			

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	public void setDefaultHeightAndWidth(int width, int height) {
		this.new_height = height;
		this.old_height = height;
		this.new_width = width;
		this.old_width = width;
	}

	/** 監聽器實作區 **/

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == login) {
			Start.changeState(HOME_PANEL);
		}

	}

	/** 監聽器分隔 **/

	public void componentResized(ComponentEvent e) {
		new_width = this.getWidth();
		new_height = this.getHeight();

		remove(show_title_img);
		add(show_title_img);

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}

	/** 監聽器結束 **/

}
