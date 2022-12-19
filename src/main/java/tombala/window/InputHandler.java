package tombala.window;

import tombala.game.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputHandler {
	private JButton setupGameBTN;
	private JButton mainMenuBTN;
	private JButton quitGameBTN;
	private JButton pollNumberBTN;
	private Frame frame;
	private GameLogic tombala;

	public InputHandler(GameLogic tombala, Frame frame){
		this.tombala = tombala;
		this.frame = frame;
		initButtons();
		frame.initInteraction(setupGameBTN, mainMenuBTN, quitGameBTN, pollNumberBTN);
		frame.GameMenuUI();
	}

	private void initButtons(){
		setupGameBTN = new JButton("Begin");
		gotoSetupGame(setupGameBTN);
		mainMenuBTN = new JButton("Go Back");
		gotoMainMenu(mainMenuBTN);
		quitGameBTN = new JButton("Quit");
		quitGame(quitGameBTN);
		pollNumberBTN = new JButton("Poll");
		pollNumber(pollNumberBTN);
	}

	private void pollNumber(JButton btn){
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tombala.pollFromList();
				frame.refreshGame(tombala);
			}
		});
	}

	private void quitGame(JButton btn){
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	private void gotoSetupGame(JButton btn){
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.GameSetupUI(tombala);
			}
		});
	}

	private void gotoMainMenu(JButton btn){
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.GameMenuUI();
			}
		});
	}

}
