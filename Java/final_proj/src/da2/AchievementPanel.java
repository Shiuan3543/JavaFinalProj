package da2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AchievementPanel extends JPanel implements ActionListener {

	private BufferedImage achievement_bg;
	
	public AchievementPanel() {
		setOpaque(false);
		LogoAnimatorJPanel animation = new LogoAnimatorJPanel();
		add(animation);
		animation.startAnimation(); // begin animation
		
	}
	
	protected void paintComponent(Graphics g) {
		try {
			achievement_bg = ImageIO.read(new File("bin/sao/achievement_bg.jpg"));
			g.drawImage(achievement_bg, 0, 0, null);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
	}

}
