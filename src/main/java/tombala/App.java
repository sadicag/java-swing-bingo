package tombala;

import tombala.game.GameLogic;
import tombala.window.Frame;
import tombala.window.InputHandler;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class App {
    public static void main( String[] args ) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //JFrame.setDefaultLookAndFeelDecorated(true);
        UIManager.put("Button.font", new FontUIResource(new Font("Helvetica", Font.BOLD, 24)));

        Frame frame = new Frame();
        GameLogic tombala = new GameLogic();
        InputHandler handler = new InputHandler(tombala, frame);
    }
}
