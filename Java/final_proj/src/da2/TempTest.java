package da2;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TempTest {

	private JFrame jf;
	private ResultPanel jp;

	public void init() {
		jf = new JFrame();
		jf.setSize(1080, 620);
		jp = new ResultPanel();
		jf.add(jp);
		jf.setVisible(true);

	}

	public static void main(String[] args) {

		new TempTest().init();

	}

}
