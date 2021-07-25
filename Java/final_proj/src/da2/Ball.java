package da2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Ball {

	private boolean isPlayer;

	// 畫主角的球
	private BufferedImage skin;
	private int skin_type;
	private int skin_index;

	private final int PURE = 0;
	private final int GRADIENT = 1;
	private final int ANIMAL = 2;
	private final int HOTS = 3;
	private final int OTHERS = 4;
	
	public float x, y;
	public float r = 0;
	public int countr;
	public int rx, ry;
	public int tx, ty;
	public float speedX, speedY;
	public float speed;
	public boolean displayr;

	public boolean wskill = false;
	public boolean eskill = true;
	public boolean qskill = false;
	public boolean rskill = true;

	public float wtime;
	private boolean qcd, wcd, ecd, rcd;

	volatile boolean go;
	Thread runner, cdw, cde, cdr, display,cdww;

	public Ball(int x, int y, int r, int speed) {
		this.isPlayer = false;
		wcd = true;
		ecd = true;
		rcd = true;
		displayr = false;
		wtime = 500;
		this.x = x;
		this.y = y;
		this.countr = 0;
		addR(20);
		this.speed = (float) speed / 10;
		go = true;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (!isPlayer) {
			g.fillOval((int) (x - r), (int) (y - r), (int) (r + r), (int) (r + r));
		} else {
			g.drawImage(getSkin(), (int) (x - r), (int) (y - r), (int) (r + r), (int) (r + r), null);
		}

		if (displayr) {
			g.setColor(Color.blue);
			g.drawOval((int) (rx - r), (int) (ry - r), (int) (r + r), (int) (r + r));
		}

		if (wskill) {
			g.setColor(new Color(0, 0, 0));
			g2.setStroke(new BasicStroke(3));
			g.drawOval((int) (x - r), (int) (y - r), (int) (r + r), (int) (r + r));
		}
	}

	public BufferedImage getSkin(){

		switch(skin_type){
		case PURE:
			switch(skin_index){
			case 0:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_0.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					skin = ImageIO.read(new File("bin/skins/pure/pure_7.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case GRADIENT:
			switch(skin_index){
			case 0:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_0.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					skin = ImageIO.read(new File("bin/skins/gradient/gradient_7.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case ANIMAL:
			switch(skin_index){
			case 0:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_0.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					skin = ImageIO.read(new File("bin/skins/animal/animal_7.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case HOTS:
			switch(skin_index){
			case 0:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_0.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					skin = ImageIO.read(new File("bin/skins/hots/hots_7.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		case OTHERS:
			switch(skin_index){
			case 0:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_0.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					skin = ImageIO.read(new File("bin/skins/others/others_7.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		}
		
		return skin;
	}
	
	public void setSkin(int skin_type, int skin_index) {
		this.skin_type = skin_type;
		this.skin_index = skin_index;
	}	
	
	public void setIsPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public void setTxTy(int tx, int ty) {
		if (eskill) {
			this.tx = tx;
			this.ty = ty;
			this.speedX = (float) (speed * (tx - x) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
			this.speedY = (float) (speed * (ty - y) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
		}
	}

	public void eSkill(int tx, int ty) {
		if (eskill && ecd) {
			skill(3);
			eskill = false;
			this.tx = tx;
			this.ty = ty;
			this.speedX = (float) (5 * speed * (tx - x) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
			this.speedY = (float) (5 * speed * (ty - y) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
		}
	}

	public void rSkill(int maxx, int maxy) {
		if (rskill && rcd) {
			display = new Thread(new Runnable() {

				@Override
				public void run() {
					skill(4);
					rx = (int) (maxx - x);
					ry = (int) (maxy - y);
					displayr = true;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					x = rx;
					y = ry;
					tx = rx;
					ty = ry;
					displayr = false;

				}
			});
			display.start();
		}
	}
	
	public void addR(int r) {
		for (int i = 0; i < r; i++) {
			if (countr < 30) {
				countr++;
				this.r += 1.5;
			} else if (countr < 45) {
				countr++;
				this.r += 1;
			} else if (countr < 150) {
				countr++;
				this.r += 0.75;
			}
		}
	}

	public void behit(int n) {
		if (n == 1) {
			for (int i = 0; i < countr / 2; i++) {
				if (countr < 30) {
					countr--;
					this.r -= 1.5;
				} else if (countr < 45) {
					countr--;
					this.r -= 1;
				} else if (countr <= 150) {
					countr--;
					this.r -= 0.75;
				}
			}
		}
	}

	public void addSpeed(int speed) {
		if (this.speed < 1.5) {
			this.speed += (float) speed / 10;
			this.speedX = (float) (this.speed * (tx - x) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
			this.speedY = (float) (this.speed * (ty - y) / Math.sqrt((tx - x) * (tx - x) + (ty - y) * (ty - y)));
		}
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public int getR() {
		return (int) r;
	}

	public void walker() {
		runner = new Thread(new Runnable() {
			public void run() {
				while (go) {
					if ((int) Math.abs(x - tx) < 3 && (int) Math.abs(y - ty) < 3) {
						speedX = 0;
						speedY = 0;
						eskill = true;
					}
					x += speedX;
					y += speedY;
					try {
						Thread.sleep(1000 / 800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		runner.start();
	}

	public void skill(int n) {
		if (wcd && n == 2) {
			cdw = new Thread(new Runnable() {
				public void run() {
					wskill = true;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wskill = false;
				}
			});
			cdww = new Thread(new Runnable(){

				@Override
				public void run() {
					wcd = false;
					try {
						Thread.sleep(3500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wcd = true;
				}
				
			});
			cdww.start();
			cdw.start();
		} else if (n == 3) {
			cde = new Thread(new Runnable() {
				@Override
				public void run() {
					ecd = false;
					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ecd = true;
				}

			});
			cde.start();
		} else if (n == 4) {
			cdr = new Thread(new Runnable() {

				@Override
				public void run() {
					rskill = false;
					try {
						Thread.sleep(12000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rskill = true;

				}

			});
			cdr.start();
		}
	}

	public void displayrSkill() {
		
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTX(int tx) {
		this.tx = tx;
	}

	public void setTY(int ty) {
		this.ty = ty;
	}
}
