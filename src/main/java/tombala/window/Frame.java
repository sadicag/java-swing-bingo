package tombala.window;

import tombala.game.GameLogic;
import tombala.isometric.IsometricMap;
import tombala.window.ui.GameUI;
import tombala.window.ui.GameMenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frame {
	private static final Logger logger = Logger.getLogger("WindowFrame");
	private int h;
	private int w;
	private final JFrame frame;
	private final JLayeredPane layers = new JLayeredPane();
	private JPanel userInterface = new JPanel();
	private JPanel mapGrid = new JPanel();
	private JButton gotoSetupGameBTN;
	private JButton gotoMainMenuBTN;
	private JButton quitGameBTN;
	private JButton pollNumberBTN;

	public Frame(){
		this.frame = new JFrame();
		this.h = 800;
		this.w = 1200;
		initLayers();
		initFrame();
	}

	private void initLayers(){
		JPanel backPanel  = DynamicBackgroundPanel.createDynamicBackground();
		layers.add(backPanel, -1 -1);
		layers.add(userInterface, 2, 2);
		layers.add(mapGrid, 0, 0);
		layers.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				h = e.getComponent().getHeight();
				w = e.getComponent().getWidth();
				backPanel.setBounds(0,0, w, h);
				userInterface.setBounds(0, 0, w, h);
				mapGrid.setBounds( 0, 0, w, h);
				logger.log(Level.INFO, "Resized to: "+h+","+w);
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}

	public void initInteraction(JButton btn1, JButton btn2, JButton btn3, JButton btn4){
		this.gotoSetupGameBTN = btn1;
		this.gotoMainMenuBTN = btn2;
		this.quitGameBTN = btn3;
		this.pollNumberBTN = btn4;
	}

	public void GameMenuUI(){
		layers.remove(userInterface);
		layers.remove(mapGrid);
		layers.setVisible(false);
		userInterface = GameMenuUI.createPanel(gotoSetupGameBTN, quitGameBTN);
		userInterface.setBounds(0,0,w,h);
		layers.add(userInterface, 2, 2);
		layers.setVisible(true);
		userInterface.setVisible(true);
	}

	public void GameSetupUI(GameLogic g){
		layers.remove(userInterface);
		layers.setVisible(false);
		userInterface = GameUI.createPanel(gotoMainMenuBTN, pollNumberBTN);
		mapGrid = IsometricMap.createMap(g);
		mapGrid.setOpaque(false);
		userInterface.setBounds(0,0,w,h);
		mapGrid.setBounds(0,0,w,h);
		layers.add(userInterface, 2, 2);
		layers.add(mapGrid, 0, 0);
		refreshFrame();
		layers.setVisible(true);
		userInterface.setVisible(true);
	}

	public void refreshGame(GameLogic g){
		layers.remove(mapGrid);
		layers.setVisible(false);
		mapGrid = IsometricMap.createMap(g);
		mapGrid.setOpaque(false);
		mapGrid.setBounds(0,0,w,h);
		layers.add(mapGrid, 0,0);
		layers.setVisible(true);
	}

   	public void refreshFrame(){
        int oldW = w;
        int oldH = h;
        frame.setVisible(false);
        frame.pack();//The frame needs to pack() again, as there are so many things to paint
        frame.setSize(oldW,oldH);
   		frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

	private void initFrame(){
		frame.pack();
		frame.setTitle("Chagatayli Tombala");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(640,480));
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension d=frame.getSize();
				Dimension minD=frame.getMinimumSize();
				if(d.width<minD.width)
					d.width=minD.width;
				if(d.height<minD.height)
					d.height=minD.height;
				frame.setSize(d);
				}
		});
		frame.setSize(w,h);
		frame.add(layers);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
