package da2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ResultPanel extends JPanel {

	private BufferedImage result_bg;
	
	// 評價圖
	private String ranking = "D"; //預設是失敗
	private BufferedImage rank_img;
	
	public ResultPanel() {
		
	}
	
	protected void paintComponent(Graphics g) {
		try {
			result_bg = ImageIO.read(new File("bin/sao/result_bg.jpg"));
			g.drawImage(result_bg, 0, 0, null);
			
			switch(ranking){
			case "SS":
				break;
			case "S":
				break;
			case "A":
				break;
			case "B":
				break;
			case "C":
				break;
			case "D":
				rank_img = ImageIO.read(new File("bin/result/rank/ranking-D.png"));
				g.drawImage(rank_img, 20, 20, null);
				break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
