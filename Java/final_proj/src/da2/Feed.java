package da2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Feed {
	private int x[];
	private int y[];
	private Color color[];
	private int r=10;
	private int n;
	private boolean running;
	private int maxX;
	private int maxY;
	private Random rand;
	Thread checkEat;
	public Feed(int maxX,int maxY,int n){
		x = new int[n];
		y = new int[n];
		color = new Color[n];
		this.n=n;
		this.maxX=maxX;
		this.maxY=maxY;
		checkEat = new Thread(new Runnable(){
			public void run() {
				rand = new Random();
				while(running){
					for(int i=0;i<n;i++){
						if(x[i]==0){
							x[i]=rand.nextInt(maxX)+1;
							y[i]=rand.nextInt(maxY)+1;
							if(i%5==0){
								color[i]= new Color(174 ,238,238);
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
					for(int i=0;i<n;i++){
						if(x[i]==0){
							
						}
					}
				}
			}
			
		});
		checkEat.start();
	}
	public void draw(Graphics g){
		for(int i=0;i<n;i++){
			if(x[i]!=0){
				g.setColor(color[i]);
				g.fillOval((x[i]-r), (y[i]-r),(int) (r+r),(int) (r+r));
			}
		}
	}
	public void setRunning(boolean running){
		this.running=running;
	}
	public void setMax(int x,int y){
		this.maxX=x;
		this.maxY=y;
	}
}