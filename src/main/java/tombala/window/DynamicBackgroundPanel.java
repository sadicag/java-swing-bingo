package tombala.window;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DynamicBackgroundPanel {
	private static final Logger logger = Logger.getLogger("BackgroundPanelInitialization");
	private static JPanel panel;
	private static JLabel label;
	private static BufferedImage backImage;

	private static void setLabelImageIcon(String filename, int width, int height){
		Image dimg = null;
		char s = File.separatorChar;
		try{
			backImage = ImageIO.read(new File ("textures" + s + filename));
			dimg = backImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e){
			logger.log(Level.SEVERE, "Background Image could not be initialized");
		}
		if (dimg != null){
			label.setIcon(new ImageIcon(dimg));
		}
	}

	private static void initBackgroundPanel(){
		setLabelImageIcon("back.jpg", 1200, 800);
		panel.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int h = e.getComponent().getHeight();
				int w = e.getComponent().getWidth();
				Image tempImg = backImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(tempImg));
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		panel.add(label);
	}

	public static JPanel createDynamicBackground(){
		panel = new JPanel();
		label = new JLabel();
		initBackgroundPanel();
		panel.setBounds(0,0,1200,800);
		panel.setOpaque(true);
		return panel;
	}

}
