package da2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	Ball ball[];
	Bullet bullet[];
	
	private int skin_type;
	private int skin_index;

	private JPanel test= new JPanel();
	private Color maincolor;
	private Color skillcolor;
	private Color aiColor;
	private Color color[];
	//          被打 , 打人      ,   擋Q ,    吃AI,  射的次數
	public int behit,hitAI,wtoBlock,ateAI,shoottime;
	
	private int feedx[];
	private int feedy[];
	private int x,y,r,aiBallCount,fps=60;
	private int feedCount,maxX;
	private int maxY;
	private int nearest;
	private int thenearest;
	private int aix,aiy;
	private int testv;
	private int mosX,mosY;
	private int skill;
	private int nameLong;
	private int lcd;
	private float aX,aY,eX,eY;;
	private String myName="name";
	private int pointx,pointy;
	public boolean ballex[];
	private boolean aiDie[];
	private boolean stop=false;
	private boolean checkfeed=true;
	private boolean qcd,wcd,ecd,rcd;
	private final int MAX = 50;
	private int BALLMAX = 1;
	private boolean aigo;
	public int qimg,wimg,eimg,rimg;
	public int skillQnumber,skillWnumber,skillEnumber,skillRnumber;
	public BufferedImage q_skill,w_skill,e_skill,r_skill;
	
	volatile boolean running=true;
	Thread runner,cdr,cdq,cdw,cde;
	Thread checkEat,aiON,skillArea,walker;
	private Random rand;
	
	public GamePanel(int xx,int yy,int fps,int maxai,Color color){
		this.BALLMAX =Math.abs( maxai+1);
		this.fps = Math.abs(fps);
		try {
			q_skill = ImageIO.read(new File("bin/skill/Q_skill.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			w_skill = ImageIO.read(new File("bin/skill/W_skill.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			e_skill = ImageIO.read(new File("bin/skill/E_skill.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r_skill = ImageIO.read(new File("bin/skill/R_skill.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		skillQnumber=1;
		skillWnumber=2;
		skillEnumber=3;
		skillRnumber=4;
		behit=0;
		hitAI=0;
		wtoBlock=0;
		ateAI=0;
		shoottime=0;
		rand = new Random();
		maxX =xx;
		maxY =yy;
		nameLong=myName.length();
		aiColor = new Color(255,0,144);
		this.aiColor = color;
		this.setBackground(new Color(255,255,255));
		lcd = 1;
		pointx=-10;
		pointy=-10;
		qcd= true;
		wcd= true;
		ecd= true;
		rcd= true;
		aigo=true;
		aiDie = new boolean[BALLMAX];
		for(int i=0;i<BALLMAX;i++){
			aiDie[i]=true;
		}
		ball = new Ball[BALLMAX];
		bullet = new Bullet[BALLMAX];
		ballex = new boolean[BALLMAX];
		for(int i=0;i<BALLMAX;i++){
			ballex[i] = true;
			ball[i] = new Ball(rand.nextInt(maxX-1)+1,rand.nextInt(maxY-1)+1,20,1);
			bullet[i] = new Bullet();
		}
		ball[0].setIsPlayer(true);
		skillcolor = new Color(0, 166, 255);
		// eskill debug
		float px =(float)(bullet[0].speed * (mosX-ball[0].x) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
		float py =(float)(bullet[0].speed * (mosY-ball[0].y) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
		eX=ball[0].x;
		eY=ball[0].y;
			for(int i=0;i<600;i++){
				eX+=px;
				eY+=py;
			}
		
		//
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		feedCount=100;
		feedx = new int[feedCount];
		feedy = new int[feedCount];
		setfeedColor(feedCount);
		walker(BALLMAX);
		feedball();
		goahead();
		aiGo();
	}
	
	public void setSkin(int skin_type, int skin_index){
		System.out.println("gamePanel: "+skin_type + "," +skin_index);
		this.skin_type = skin_type;
		this.skin_index = skin_index;
		ball[0].setSkin(skin_type, skin_index);
	}
	
	public void setSkillColor (Color color){
		this.skillcolor = color;
	}
	
	public void setBallMax(int BallMax){
		this.BALLMAX = BallMax;
	}
	
	
	
	public void setfeedColor(int n){
		color = new Color[n];
		for(int i=0;i<n;i++){
				if(i%5==0){
					color[i]= new Color(0 ,0,205);
				}else if(i%5==1){
					color[i]= new Color(255  ,255 ,0);
				}else if(i%5==2){
					color[i]= new Color(255  ,20,147);
				}else if(i%5==3){
					color[i]= new Color(131  ,111 ,255);
				}else if(i%5==4){
					color[i]= new Color(0  ,255  ,0);
				}
		}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2=(Graphics2D)g;
		super.paintComponent(g);
		//display feed
		for (int i =0 ;i<feedCount;i++){
			g.setColor(color[i]);
			g.fillOval((feedx[i]-5), (feedy[i]-5),(int) (10),(int) (10));
		}
		//display aiBall
		for (int i=1;i<BALLMAX;i++){
			g.setColor(aiColor);
			ball[i].draw(g);
			g.setColor(Color.BLACK);
			bullet[i].draw(g);
		}
		maincolor=new Color(176 ,224 ,230);
		g.setColor(skillcolor);
		if(skill!=0){
			g.setColor(skillcolor);
			if(qcd && skill==1){
				float px =(float)(bullet[0].speed * (mosX-ball[0].x) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
				float py =(float)(bullet[0].speed * (mosY-ball[0].y) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
				aX=ball[0].x;
				aY=ball[0].y;
					for(int i=0;i<bullet[0].thelong-10;i++){
							aX+=px;
							aY+=py;
					}
				g2.setStroke(new BasicStroke(15));	
				g.drawLine((int)ball[0].x, (int)ball[0].y,(int) aX,(int) aY);
				g2.setStroke(new BasicStroke(12));	
				g.setColor(Color.white);
				g.drawLine((int)ball[0].x, (int)ball[0].y,(int) aX,(int) aY);
				}else if(wcd&&skill==4){
					g.drawRect((int)(mosX-ball[0].r), (int)(mosY-ball[0].r),(int)(maxX+ball[0].r),(int)( maxY+ball[0].r));
				}else if(ecd&&skill==3){
					g.drawOval((int)ball[0].x-300, (int)ball[0].y-300, 600, 600);
					if(Math.sqrt((ball[0].x-mosX)*(ball[0].x-mosX)+(ball[0].y-mosY)*(ball[0].y-mosY))<300){
						g2.setStroke(new BasicStroke(3));	
						g.drawOval((int)(mosX-ball[0].r), (int)(mosY-ball[0].r), (int)(ball[0].r+ball[0].r), (int)(ball[0].r+ball[0].r));
					}else{
						float px =(float)(bullet[0].speed * (mosX-ball[0].x) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
						float py =(float)(bullet[0].speed * (mosY-ball[0].y) /Math.sqrt((mosX-ball[0].x)*(mosX-ball[0].x)+(mosY-ball[0].y)*(mosY-ball[0].y)));
						eX=ball[0].x;
						eY=ball[0].y;
							for(int i=0;i<600;i++){
								eX+=px;
								eY+=py;
							}
							g2.setStroke(new BasicStroke(3));	
							g.drawOval((int)(eX-ball[0].r), (int)(eY-ball[0].r), (int)(ball[0].r+ball[0].r), (int)(ball[0].r+ball[0].r));
					}
				}
			}
		
		//display bullet
		bullet[0].draw(g);
		
		g.setColor(Color.red);
		g2.setStroke(new BasicStroke(1));
		g.fillOval(pointx-3, pointy-3,6,6);
		g.drawLine(pointx-6, pointy, pointx+6,pointy);
		g.drawLine(pointx, pointy-6, pointx,pointy+6);
		
		// display MAIN ball
		g.setColor(maincolor);
		ball[0].draw(g);
		
		g.setColor(new Color(0,0,0));
		g.setFont(new  Font("Helvetica",Font.PLAIN,20));
		g.drawString(myName,(int) (ball[0].x-nameLong*5),(int) ball[0].y+6);
		if(stop){
			g.setColor(new Color(255,0,0));
			g.setFont(new  Font("Helvetica",Font.PLAIN,50));
			g.drawString("STOP", maxX/2-50, maxY/2-20);
		}
		g.setColor(Color.BLACK);
		g.fillRect(maxX/2-92, maxY-132, 54,54);
		g.fillRect(maxX/2-32, maxY-132, 54,54);
		g.fillRect(maxX/2+28, maxY-132, 54,54);
		g.fillRect(maxX/2+88, maxY-132, 54,54);
		g.setColor(Color.WHITE);
		g.drawImage(q_skill, maxX/2-90, maxY-130, 50, 50,null);
		g.fillRect(maxX/2-90, maxY-130, 50,(int)(qimg));
		g.drawImage(w_skill, maxX/2-30, maxY-130, 50, 50,null);
		g.fillRect(maxX/2-30, maxY-130, 50,(int)(wimg));
		g.drawImage(e_skill,maxX/2+30, maxY-130, 50, 50,null);
		g.fillRect(maxX/2+30, maxY-130, 50,(int)(eimg));
		g.drawImage(r_skill, maxX/2+90, maxY-130, 50, 50,null);
		g.fillRect(maxX/2+90, maxY-130, 50,(int)(rimg));
	}
	
	public void aiGo(){    //AIGO
		aiON = new Thread(new Runnable(){
			@Override
			public void run() {
				while(running){
					for(int i=1;i<BALLMAX;i++){
						if(aigo&&aiDie[i]){
							thenearest = 1000000;
							for(int j=0;j<feedCount;j++){//find the nearest feed to eat
								int nowfar =  (int) Math.sqrt((ball[i].x-feedx[j])*(ball[i].x-feedx[j])+(ball[i].y-feedy[j])*(ball[i].y-feedy[j]));
								if(nowfar < thenearest){
									thenearest = nowfar;
									nearest = j;
								}
							}
							if(ball[i].countr>ball[0].countr*2+1||ball[i].countr==150)
								ball[i].setTxTy((int)ball[0].x,(int)ball[0].y);
							else{
								ball[i].setTxTy(feedx[nearest], feedy[nearest]);
							}
							//be carefull
							if(Math.sqrt((ball[i].x-bullet[0].x)*(ball[i].x-bullet[0].x)+(ball[i].y-bullet[0].y)*(ball[i].y-bullet[0].y)) < ball[i].r+20){
								ball[i].skill(2);
							}
							if(rand.nextInt(5)==0)
								if(bullet[i].work && Math.sqrt((ball[0].x-ball[i].x)*(ball[0].x-ball[i].x)+(ball[0].y-ball[i].y)*(ball[0].y-ball[i].y)) < bullet[i].thelong/2+ball[0].r){
									bullet[i].shoot(ball[i].x, ball[i].y,(int) ball[0].x,(int) ball[0].y);
								}
							if(ball[i].r>ball[0].r*2&&Math.sqrt((ball[0].x-ball[i].x)*(ball[0].x-ball[i].x)+(ball[0].y-ball[i].y)*(ball[0].y-ball[i].y))<300){
								ball[i].eSkill((int)ball[0].x,(int)ball[0].y);
							}
							//ball[i].r>ball[].r*2&&
							if(ball[i].r>ball[0].r*2&&Math.sqrt(((int)(maxX-ball[i].x-ball[0].x)*(int)(maxX-ball[i].x-ball[0].x)+(int)(maxY-ball[i].y-ball[0].y)*(int)(maxY-ball[i].y-ball[0].y)))<50){
								ball[i].rSkill(maxX, maxY);
							}
							if(ball[i].r*2<ball[0].r&&Math.sqrt((ball[0].x-ball[i].x)*(ball[0].x-ball[i].x)+(ball[0].y-ball[i].y)*(ball[0].y-ball[i].y))>300){
								ball[i].rSkill(maxX, maxY);
							}
						}
						
					}
				}
			}
		});
		aiON.start();
	}
	
	public void feedball(){
		checkEat = new Thread(new Runnable(){
			public void run() {
				rand = new Random();
				while(running){
				for(int j=0;j<BALLMAX;j++)
					if(ballex[j]){
						x=(int)ball[j].x;
						y=(int)ball[j].y;
						r=(int)ball[j].r;
						for(int i=0;i<feedCount;i++){
							if(feedx[i]==0){
								do{
								feedx[i]=rand.nextInt(maxX)+1;
								feedy[i]=rand.nextInt(maxY)+1;
								}while(Math.sqrt((x-feedx[i])*(x-feedx[i])+(y-feedy[i])*(y-feedy[i])) < r);
							}
							if((Math.sqrt((x-feedx[i])*(x-feedx[i])+(y-feedy[i])*(y-feedy[i]))) < r){
								feedx[i]=0;
								feedy[i]=0;
								ball[j].addR(1);
							}
						}
						if(j>0 && Math.sqrt((ball[0].x-ball[j].x)*(ball[0].x-ball[j].x)+(ball[0].y-ball[j].y)*(ball[0].y-ball[j].y)) < Math.abs(ball[0].r-ball[j].r)){
							if(ball[0].r<ball[j].r){//gameOver
								System.out.println("gameOver");
								gameOver();
							}else if(ball[0].r>ball[j].r){    // have ate ball[j] ball
								ball[j].x = -1000;
								ball[j].y = -1000;
								aiDie[j] = false;
								ball[0].addR((int)ball[j].r);
								ateAI++;
								if(ateAI==BALLMAX-1){
									gameOver();
								}
							}
						}
					}
					for(int i=1;i<BALLMAX;i++){//Hit
						if(Math.sqrt((ball[i].x-bullet[0].x)*(ball[i].x-bullet[0].x)+(ball[i].y-bullet[0].y)*(ball[i].y-bullet[0].y)) < ball[i].r+ 5){
							if(!ball[i].wskill){
								if(ball[i].r>13){
									ball[i].behit(1);
									hitAI++;
								}
								else{
									ball[i].x = -1000;
									ball[i].y = -1000;
									aiDie[i] = false;
									ateAI++;
									if(ateAI==BALLMAX-1){
										gameOver();
									}
								}
							}
							
							bullet[0].x=-10000000;
							bullet[0].y=-10000000;
						}
						if(Math.sqrt((ball[0].x-bullet[i].x)*(ball[0].x-bullet[i].x)+(ball[0].y-bullet[i].y)*(ball[0].y-bullet[i].y)) < ball[0].r+ 5){
							if(!ball[0].wskill){
								if(ball[0].r>13){
									ball[0].behit(1);
									behit++;
								}else{
									repaint();
									gameOver();
								}
							}else{
								wtoBlock++;
							}
							bullet[i].x=-10000000;
							bullet[i].y=-10000000;
						}
					}
					
				}
			}
			
		});
		checkEat.start();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3){//right kick
			if(skill==0){
				ball[0].setTxTy(mosX,mosY);
				pointx=mosX;
				pointy=mosY;
			}
			skill = 0;
		}else if(e.getButton()==MouseEvent.BUTTON1){
			if(qcd&&skill==1){
				bullet[0].shoot(ball[0].x, ball[0].y, mosX, mosY);
				cdR(1);
				shoottime++;
				skill=0;
			}else if(ecd&&skill==3){
				pointx=(int)eX;
				pointy=(int)eY;
				if(Math.sqrt((ball[0].x-mosX)*(ball[0].x-mosX)+(ball[0].y-mosY)*(ball[0].y-mosY))>=300)
					ball[0].eSkill((int)eX,(int)eY);
				else{
					ball[0].eSkill(mosX, mosY);
				}
				ball[0].skill(3);
				cdR(3);
				skill=0;
			}else if(rcd&&skill==4){
				cdR(4);
				skill=0;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mosX=e.getX();
		mosY=e.getY();
	}
	
	
	public void goahead(){
		running = true;
		runner = new Thread(new Runnable(){
			public void run(){
				while(running){
					repaint();
					try {
						Thread.sleep(1000/fps);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		runner.start();
	}

	public void stopfps(){
		running = false;
	}
	
	public void setFps(int fps){
		this.fps=fps;
	}
	
	public boolean getRunning(){
		return running;
	}
	
	public int[] getFeedy(){
		return feedy;
	}
	public int[] getFeedx(){
		return feedx;
	}
	public int getfeedCount(){
		return feedCount;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		if(qcd  && e.getKeyChar()=='q'||e.getKeyChar()=='Q'){
			skill=1;
		}else if(wcd && e.getKeyChar()=='w'||e.getKeyChar()=='W'){
			ball[0].skill(skillWnumber);
			cdR(skillWnumber);
		}else if(ecd&&e.getKeyChar()=='e'||e.getKeyChar()=='E'){
			skill=3;
		}else if(e.getKeyChar()=='r'||e.getKeyChar()=='R'){
			ball[0].rSkill(maxX,maxY);
			cdR(4);
		}else if(e.getKeyCode()==8){//stop
			if(running){
				stopGame();
				repaint();
			}
			else
				startGame();
		}else if(e.getKeyChar()=='n'){
			if(aigo){
				aigo=false;
			}else
				aigo=true;
		}else if(e.getKeyChar()=='m'){
			System.out.println(ball[1].x);
			System.out.println(ball[1].y);
			System.out.println(behit);
			System.out.println(hitAI);
			System.out.println(wtoBlock);
			System.out.println(ateAI);
			System.out.println(shoottime);
			//private int behit,hitAI,wtoBlock,ateAI,shoottime;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void gameOver(){
		running = false;
		skill = 0;
		for(int i=0;i<BALLMAX;i++){
			ball[i].go = false;
		}
		repaint();
	}
	
	public void stopGame(){ //stop
		System.out.println("A");
		skill=0;
		running = false;
		stop = true;
		for(int i=0;i<BALLMAX;i++){
			ball[i].go = false;
		}
	}
	
	public void startGame(){ //stop stop
		if(!running){
			stop = false;
			goahead();
			for(int i=0;i<BALLMAX;i++){
				ball[i].go = true;
				ball[i].walker();
			}
			feedball();
			aiGo();
		}
	}
	
	public void setName(String name){
		this.myName=name;
		nameLong=myName.length();
	}
	
	public void cdR(int n){
		if(qcd && n==1){
			qimg=50;
			cdq = new Thread(new Runnable(){
				public void run() {
					qcd= false;
					for(int i=0;i<50;i++){
						try {
					Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						qimg--;
					}
					qcd= true;
			}
			});
			cdq.start();
		}else if(wcd && n==2){
			cdw = new Thread(new Runnable(){
				public void run() {
						wimg = 50;
						wcd= false;
						for(int i=0;i<50;i++){
						try {
							Thread.sleep(60);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						wimg--;
						}
						wcd= true;
				}
			});
			cdw.start();
		}else if(ecd&&n==3){
			cde = new Thread(new Runnable(){
				public void run() {
					eimg = 50;
					ecd= false;
					for(int i=0;i<50;i++){
					try {
						Thread.sleep(140);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					eimg--;
					}
					ecd= true;
			}
			});
			cde.start();
		}else if(rcd&&n==4){
			cdr = new Thread(new Runnable(){
				public void run() {
					rimg = 50;
					for(int i=0;i<50;i++){
					rcd= false;
					try {
				Thread.sleep(240);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rimg--;
					}
					rcd= true;
			}
			});
			cdr.start();
		}
	}
	public void walker(int BALLMAX) {
		runner = new Thread(new Runnable() {
			public void run() {
				while (!stop) {
					for(int i = 0;i<BALLMAX;i++){
						if ((int) Math.abs(ball[i].x - ball[i].tx) < 3 && (int) Math.abs(ball[i].y - ball[i].ty) < 3) {
							ball[i].speedX = 0;
							ball[i].speedY = 0;
							ball[i].eskill = true;
							}
						ball[i].x += ball[i].speedX;
						ball[i].y += ball[i].speedY;
					}
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

	
	
}