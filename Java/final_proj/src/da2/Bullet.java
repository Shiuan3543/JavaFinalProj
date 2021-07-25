package da2;

import java.awt.Graphics;

public class Bullet {
	public  float x, y;
	public  int r=10;
	public int countr;
	private int tx,ty;
	private float speedX,speedY;
	public float speed;
	public int thelong;
	public int cd;
	public boolean work;
	volatile boolean go;
	Thread runner,cdtime;
	public Bullet() {
		work=true;
		cd = 2500;
		x=-100;
		y=-100;
		thelong=800;
		speed=(float) 0.5;
		go=true;
	}

	public void draw(Graphics g){
		g.fillOval((int)(x-r), (int)(y-r),(int) (r+r),(int) (r+r));
	}
	
	public void shoot(float x,float y,int tx,int ty){
		if(work){
			cdtime = new Thread(new Runnable(){
				public void run() {
					try {
						Thread.sleep(cd);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					work = true;
				}
			});
			work = false;
			cdtime.start();
			this.x=x;
			this.y=y;
			this.tx=tx;
			this.ty=ty;
			this.speedX=(float)(speed * (tx-x) /Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)));
			this.speedY=(float)(speed * (ty-y) /Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)));
			walker();
		}
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public void setTX(int tx){
		this.tx=tx;
	}
	public void setTY(int ty){
		this.ty=ty;
	}
	public void addR(int r){
		float nr=(float)r;
			if(countr<30){
				countr+=r;
				this.r+=nr;
			}else if(countr<45){
				countr+=r;
				this.r+=nr/2;
			}else if(countr<70){
				countr+=r;
				this.r+=nr/4;
			}
	}

	public void addSpeed(int speed){
		if(this.speed<1.5){
			this.speed+=(float)speed/10;
			this.speedX=(float)(this.speed * (tx-x) /Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)));
			this.speedY=(float)(this.speed * (ty-y) /Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y)));
		}
	}
	public int getX(){
		return (int)x;
	}
	public int getY(){
		return (int)y;
	}
	public int getR(){
		return (int)r;
	}
	public void walker(){
		runner = new Thread(new Runnable(){
			public void run(){
				for(int i=0;i<thelong;i++){
						x+=speedX;
						y+=speedY;
					try {
				             Thread.sleep(1000/800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				x=-100;
				y=-100;
			}
		});
		runner.start();
	}
	
	
}


