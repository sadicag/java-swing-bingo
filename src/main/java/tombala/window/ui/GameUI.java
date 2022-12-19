package tombala.window.ui;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JPanel {

	private static JPanel panel = new JPanel();
	private static JPanel center = new JPanel();
	private static JPanel empty = new JPanel();
	private static JButton pollNumberBTN;
	private static JButton gotoMainMenuBTN;

	private static void initCenter(){
		center.setLayout(new BorderLayout());
		center.add(gotoMainMenuBTN, BorderLayout.EAST);
		center.add(pollNumberBTN, BorderLayout.WEST);
		center.setOpaque(false);
	}

	private static void initPanel(){
		initCenter();
		panel.setLayout(new BorderLayout());
		panel.add(center,BorderLayout.SOUTH);
	}

	public static JPanel createPanel(JButton btn1, JButton btn2){
		gotoMainMenuBTN = btn1;
		pollNumberBTN = btn2;
		empty.setOpaque(false);
		initPanel();
		panel.setBounds(0,0,1200,800);
		panel.setOpaque(false);
		panel.setVisible(false);
		return panel;
	}

}
