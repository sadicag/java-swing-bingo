package tombala.window.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameMenuUI extends JPanel {
	private static final Logger logger = Logger.getLogger("GameMenuUI");
	private static JPanel panel = new JPanel();
	private static JPanel center = new JPanel();
	private static JPanel empty = new JPanel();
	private static JLabel logoLabel = new JLabel();
	private static JPanel options = new JPanel();
	private static JButton gotoSetupGameBTN;
	private static JButton quitGameBTN;
	private static BufferedImage logoImg = null;
	private static int logoW = 862;
	private static int logoH = 384;

	private static void setLabelImageIcon(){
		Image dimg = null;
		char s = File.separatorChar;
		try {
			logoImg = ImageIO.read(new File("textures"+s+"logo.png"));
			dimg = logoImg;
		} catch (IOException e){
			logger.log(Level.SEVERE, "Logo Image could not be initialized");
		}
		if (dimg != null){
			logoLabel.setIcon(new ImageIcon(dimg));
			logoLabel.setVisible(true);
		}
	}

	private static void initCenter(){
		center.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridx = 0;
		c1.gridy = 0;
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 0;
		c2.gridy = 1;
		c2.anchor = GridBagConstraints.PAGE_END;
		c2.insets = new Insets(20,7,0,7);
		c2.gridwidth = 1;
		center.add(logoLabel, c1);
		center.add(options, c2);
		center.setOpaque(false);
	}

	private static void initOptions(){
		options.setLayout(new GridLayout(2,1));
		options.add(gotoSetupGameBTN);
		options.add(quitGameBTN);
		options.setOpaque(false);
	}

	private static void initPanel(){
		initCenter();
		panel.setLayout(new GridBagLayout());
		panel.add(center);
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				int h = e.getComponent().getHeight();
				int w = e.getComponent().getWidth();
				if (h < logoH || w < logoW){
					Image tempImg = logoImg.getScaledInstance((w)-(logoW/3), (h/2)-(logoH),Image.SCALE_SMOOTH);
					logoLabel.setIcon(new ImageIcon(tempImg));
				} else {
					Image tempImg = logoImg.getScaledInstance(logoW, logoH,Image.SCALE_SMOOTH);
					logoLabel.setIcon(new ImageIcon(tempImg));
				}
			}
			@Override
			public void componentResized(ComponentEvent e) {
				int h = e.getComponent().getHeight();
				int w = e.getComponent().getWidth();
				if (h < logoH || w < logoW){
					Image tempImg = logoImg.getScaledInstance((w)-(logoW/3), (h/2)-(logoH),Image.SCALE_SMOOTH);
					logoLabel.setIcon(new ImageIcon(tempImg));
				} else {
					Image tempImg = logoImg.getScaledInstance(logoW, logoH,Image.SCALE_SMOOTH);
					logoLabel.setIcon(new ImageIcon(tempImg));
				}
			}
		});

	}

	public static JPanel createPanel(JButton btn1, JButton btn2){
		gotoSetupGameBTN = btn1;
		quitGameBTN = btn2;
		empty.setOpaque(false);
		initOptions();
		setLabelImageIcon();
		initPanel();
		panel.setOpaque(false);
		panel.setVisible(false);
		return panel;
	}

}
