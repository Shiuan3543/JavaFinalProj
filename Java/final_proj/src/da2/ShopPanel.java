package da2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShopPanel extends JPanel implements ActionListener {

	private JPanel itemMenuPanel;
	private JPanel shopItemsPanel;

	// 繪製 8個skins的panel
	private int itemsPanel_x[] = { 0, 170, 340, 510, 0, 170, 340, 510 };
	private int itemsPanel_width;
	private int itemsPanel_height;
	private int skins_width;
	private JPanel itemsPanel[];
	private boolean isEnter[] = {false,false,false,false,false,false,false,false};
	private boolean isBuy[][] = {{false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false}};
	private BufferedImage itemsPanel_bg;
	private int skins_index;

	private BufferedImage shop_bg;

	// 儲存items的狀態
	private int items_state;
	private final int PURE = 0;
	private final int GRADIENT = 1;
	private final int ANIMAL = 2;
	private final int HOTS = 3;
	private final int OTHERS = 4;

	// itemMenuPanel的類別
	private JButton pureColor_btn;
	private JButton gradient_btn;
	private JButton animal_btn;
	private JButton hots_btn;
	private JButton others_btn;

	// skins 圖片
	private BufferedImage skins_pure[];
	private BufferedImage skins_gradient[];
	private BufferedImage skins_animal[];
	private BufferedImage skins_hots[];
	private BufferedImage skins_others[];

	// shopItemPanel
	private BufferedImage panelInHome_bg;
	private String panelBgPath = "bin/da2/PanelInHome_bg.png";
	private BufferedImage shopItemsMenu_bg;

	private JButton set_skins[];
	private JButton buy_skins[];

	private GridBagLayout gb;
	private GridBagConstraints gbc;
	private Box itemMenu_box;

	private final int NONE = 0;
	private final int HORIZONTAL = 1;
	private final int VERTICAL = 2;
	private final int BOTH = 3;

	public ShopPanel() {
		// init
		gb = new GridBagLayout();
		gbc = new GridBagConstraints();
		itemsPanel = new JPanel[8];
		items_state = 0;
		skins_index = 0;
		// this
		setLayout(null);
		setOpaque(false);
		// -- itemMenuPanel --
		itemMenuPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					shopItemsMenu_bg = ImageIO.read(new File("bin/da2/shopItemsMenu_bg.png"));
					g.drawImage(shopItemsMenu_bg, 0, 0, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		itemMenuPanel.setVisible(true);
		itemMenuPanel.setBounds(30, 25, 300, 540);
		itemMenuPanel.setOpaque(false);

		itemMenu_box = Box.createVerticalBox();

		itemMenu_box.add(Box.createVerticalStrut(70));

		itemMenu_box.setOpaque(false);

		pureColor_btn = new JButton("　　純色　　");
		pureColor_btn.addActionListener(this);
		itemMenu_box.add(pureColor_btn);

		itemMenu_box.add(Box.createVerticalStrut(5));

		gradient_btn = new JButton("　　漸層　　");
		gradient_btn.addActionListener(this);
		itemMenu_box.add(gradient_btn);

		itemMenu_box.add(Box.createVerticalStrut(5));

		animal_btn = new JButton("　　動物　　");
		animal_btn.addActionListener(this);
		itemMenu_box.add(animal_btn);

		itemMenu_box.add(Box.createVerticalStrut(5));

		hots_btn = new JButton("　　英霸　　");
		hots_btn.addActionListener(this);
		itemMenu_box.add(hots_btn);

		itemMenu_box.add(Box.createVerticalStrut(5));

		others_btn = new JButton("　　其他　　");
		others_btn.addActionListener(this);
		itemMenu_box.add(others_btn);

		itemMenu_box.add(Box.createVerticalStrut(5));

		itemMenu_box.add(Box.createVerticalStrut(5));
		itemMenu_box.add(Box.createVerticalStrut(5));
		itemMenu_box.add(Box.createVerticalStrut(5));

		itemMenuPanel.add(itemMenu_box);

		add(itemMenuPanel);

		// -- shopItemsPanel --
		setSkins();
		shopItemsPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					panelInHome_bg = ImageIO.read(new File(panelBgPath));
					g.drawImage(panelInHome_bg, 0, 0, null);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		shopItemsPanel.setLayout(null);
		shopItemsPanel.setOpaque(false);
		shopItemsPanel.setVisible(true);
		shopItemsPanel.setBounds(360, 25, 680, 540);

		// -- itemsPanel --

		itemsPanel_width = 150;
		itemsPanel_height = 255;

		set_skins = new JButton[8];
		buy_skins = new JButton[8];

		for (int i = 0; i < 8; i++) {
			set_skins[i] = new JButton("套用");
			buy_skins[i] = new JButton("購買");
			set_skins[i].addActionListener(this);
			buy_skins[i].addActionListener(this);
		}

		int temp_x = 37;
		int temp_y = 66;

		skins_width = 100;

		itemsPanel[0] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[0]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[0], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[0], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[0], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[0], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[0], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[1] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[1]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[1], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[1], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[1], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[1], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[1], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[2] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[2]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[2], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[2], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[2], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[2], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[2], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[3] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[3]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[3], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[3], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[3], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[3], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[3], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[4] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[4]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[4], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[4], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[4], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[4], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[4], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[5] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[5]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[5], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[5], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[5], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[5], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[5], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[6] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[6]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[6], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[6], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[6], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[6], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[6], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		itemsPanel[7] = new JPanel() {
			protected void paintComponent(Graphics g) {
				try {
					if (isEnter[7]) {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg_hover.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					} else {
						itemsPanel_bg = ImageIO.read(new File("bin/da2/itemsPanel_bg.png"));
						g.drawImage(itemsPanel_bg, 7, 7, null);
					}

					switch (items_state) {
					case PURE:
						g.drawImage(skins_pure[7], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case GRADIENT:
						g.drawImage(skins_gradient[7], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case ANIMAL:
						g.drawImage(skins_animal[7], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case HOTS:
						g.drawImage(skins_hots[7], temp_x, temp_y, skins_width, skins_width, null);
						break;
					case OTHERS:
						g.drawImage(skins_others[7], temp_x, temp_y, skins_width, skins_width, null);
						break;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		ItemsPanel_Listener itemsPanel_Listener = new ItemsPanel_Listener();
		ItemsMouseMotionListener itemsMouseMotionListener = new ItemsMouseMotionListener();

		for (int i = 0; i < 8; i++) {
			itemsPanel[i].setLayout(null);
			if (i < 4) {
				itemsPanel[i].setBounds(itemsPanel_x[i], 10, itemsPanel_width, itemsPanel_height);
			} else {
				itemsPanel[i].setBounds(itemsPanel_x[i], 285, itemsPanel_width, itemsPanel_height);
			}

			set_skins[i].setBounds(10, 180, 60, 30);
			buy_skins[i].setBounds(80, 180, 60, 30);
			itemsPanel[i].add(set_skins[i]);
			itemsPanel[i].add(buy_skins[i]);
			itemsPanel[i].setOpaque(false);

			itemsPanel[i].setBackground(Color.BLACK);
			shopItemsPanel.add(itemsPanel[i]);
		}

		shopItemsPanel.addMouseListener(itemsPanel_Listener);
		shopItemsPanel.addMouseMotionListener(itemsMouseMotionListener);
		add(shopItemsPanel);

	}

	class ItemsPanel_Listener implements MouseListener {

		public void mouseClicked(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		} // mouseEntered end

		public void mouseExited(MouseEvent e) {


		} // mouseExited end

	}

	class ItemsMouseMotionListener implements MouseMotionListener {

		public void mouseDragged(MouseEvent e) {
			
		} // mouseDragged end

		public void mouseMoved(MouseEvent e) {

			int temp_x = e.getX();
			int temp_y = e.getY();

			int temp_itemsPanel_y1 = 10;
			int temp_itemsPanel_y2 = 285;
			
			for (int i = 0; i < 8; i++) {
				// 0~3
				if (i < 4) {
					if (temp_x >= itemsPanel_x[i] && temp_x <= itemsPanel_x[i] + itemsPanel_width
							&& temp_y >= temp_itemsPanel_y1 && temp_y <= temp_itemsPanel_y1 + itemsPanel_height) {
						isEnter[i] = true;
						itemsPanel[i].repaint();
					}else{
						isEnter[i] = false;
						itemsPanel[i].repaint();
					}
					// 4~7
				} else {
					if (temp_x >= itemsPanel_x[i] && temp_x <= itemsPanel_x[i] + itemsPanel_width
							&& temp_y >= temp_itemsPanel_y2 && temp_y <= temp_itemsPanel_y2 + itemsPanel_height) {
						isEnter[i] = true;
						itemsPanel[i].repaint();
					}else{
						isEnter[i] = false;
						itemsPanel[i].repaint();
					}
				}
				
			} // for迴圈  end : 判斷isEnter為true或false
		} // mouseMoved end

	}

	protected void paintComponent(Graphics g) {
		try {
			shop_bg = ImageIO.read(new File("bin/sao/shop_bg.jpg"));
			g.drawImage(shop_bg, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 抓取並設定skins圖片
	public void setSkins() {

		skins_pure = new BufferedImage[8];
		skins_gradient = new BufferedImage[8];
		skins_animal = new BufferedImage[8];
		skins_hots = new BufferedImage[8];
		skins_others = new BufferedImage[8];

		for (int i = 0; i < 8; i++) {
			try {
				skins_pure[i] = ImageIO.read(new File("bin/skins/pure/pure_" + i + ".png"));
				skins_gradient[i] = ImageIO.read(new File("bin/skins/gradient/gradient_" + i + ".png"));
				skins_animal[i] = ImageIO.read(new File("bin/skins/animal/animal_" + i + ".png"));
				skins_hots[i] = ImageIO.read(new File("bin/skins/hots/hots_" + i + ".png"));
				skins_others[i] = ImageIO.read(new File("bin/skins/others/others_" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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

	public int getSkinType() {
		return items_state;
	}

	public int getSkinIndex() {
		return skins_index;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == pureColor_btn) {
			items_state = PURE;
			shopItemsPanel.repaint();
		} else if (e.getSource() == gradient_btn) {
			items_state = GRADIENT;
			shopItemsPanel.repaint();
		} else if (e.getSource() == animal_btn) {
			items_state = ANIMAL;
			shopItemsPanel.repaint();
		} else if (e.getSource() == hots_btn) {
			items_state = HOTS;
			shopItemsPanel.repaint();
		} else if (e.getSource() == others_btn) {
			items_state = OTHERS;
			shopItemsPanel.repaint();
		} else if (e.getSource() == set_skins[0]) {
			skins_index = 0;
		} else if (e.getSource() == set_skins[1]) {
			skins_index = 1;
		} else if (e.getSource() == set_skins[2]) {
			skins_index = 2;
		} else if (e.getSource() == set_skins[3]) {
			skins_index = 3;
		} else if (e.getSource() == set_skins[4]) {
			skins_index = 4;
		} else if (e.getSource() == set_skins[5]) {
			skins_index = 5;
		} else if (e.getSource() == set_skins[6]) {
			skins_index = 6;
		} else if (e.getSource() == set_skins[7]) {
			skins_index = 7;
		} else if (e.getSource() == buy_skins[0]) {
			
		} else if (e.getSource() == buy_skins[1]) {
		} else if (e.getSource() == buy_skins[2]) {
		} else if (e.getSource() == buy_skins[3]) {
		} else if (e.getSource() == buy_skins[4]) {
		} else if (e.getSource() == buy_skins[5]) {
		} else if (e.getSource() == buy_skins[6]) {
		} else if (e.getSource() == buy_skins[7]) {
		}

	}

}
