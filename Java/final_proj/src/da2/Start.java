package da2;

import java.awt.Color;

import javax.swing.JFrame;

public class Start {
	
	private static GameFrame game;
	private int width = 1080;
	private int height = 720;
	
	private final static int LOGIN = 1;
	private final static int GAME_PANEL = 2;
	
	public void init(){
		game = new GameFrame(width, height);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);		
		
	}
	
	public static void main(String[] args) {
		new Start().init();
		
	}
	
	public static void changeState(int state){
		game.changeState(state);
	}
	
	public static void setValue(int type, int value){
		game.setValue(type, value);
	}
	
}