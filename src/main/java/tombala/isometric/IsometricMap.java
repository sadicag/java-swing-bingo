package tombala.isometric;

import tombala.game.GameLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.sqrt;

public class IsometricMap {
	private static final Logger logger = java.util.logging.Logger.getLogger("IsometricMap");
	private static JPanel all = new JPanel();
	private static JPanel gameMap = new JPanel();
	private static JPanel polygonMap = new JPanel();
	private static JPanel borderMap = new JPanel();
	private static JPanel nodeMap = new JPanel();
	private static JPanel dirtTile = new JPanel();
	private static JPanel topPanel = new JPanel();
	private static GameLogic tombala;
	private static int size = 10;
	private static int width = 10;
	private static int height = 10;
	private static int moveup = 65;
	private static int tileSize = 48;//Size of each tile
	private static Image blockTile;
	private static Image tileCoin;
	private static BufferedImage tileGrass;
	private static BufferedImage tileDirt;
	private static char s = File.separatorChar;

	private static int cart2isoX(int x, int y){ //Converts a coordinate into isometric
        int i_X = (x-y);
        return i_X; //returns isometric x value
    }

    private static int cart2isoY(int x, int y){ //Converts a coordinate into isometric
        int i_Y = (x+y)/2;
        return i_Y; //returns isometric y value
    }


	private static void initTileTextures(){
		try {
			tileGrass = ImageIO.read(new File("textures"+s+"tileGrass.png"));
			tileDirt = ImageIO.read(new File("textures"+s+"tileDirt.png"));
			tileCoin = ImageIO.read(new File("textures"+s+"tileCoin.png"));
			BufferedImage dimg = ImageIO.read(new File("textures"+s+"isometricBlock.png"));
			blockTile = dimg.getScaledInstance(tileSize*2, tileSize*2, 0);
		} catch (IOException e){
			logger.log(Level.SEVERE, "MapTiles could not be loaded");
		}
	}

	private static void initPanel(){
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				JLabel label = new JLabel();
				label.setLayout(new FlowLayout());
				label.setIcon(new ImageIcon(blockTile));
				label.setOpaque(false);
				gameMap.add(label);
			}
		}
	}

	private static JPanel createTexturedPolygon(int nX, int nY){
		JPanel tile = new JPanel(){
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				int half = size/2;
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2));
				int o_X = (nX+half*3)*tileSize-((half+2)*tileSize);//o = origin
                int o_Y = (nY+half)*tileSize-((half+2)*tileSize)-moveup;
                int n_X = (((nX+1)+half*3))*tileSize-((half+2)*tileSize);//n = next coordinates
                int n_Y = (((nY+1)+half))*tileSize-((half+2)*tileSize)-moveup;
				Polygon p1 = IsometricDraw.makeUpPolygon(o_X, o_Y, n_X, n_Y);
                Polygon p2 = IsometricDraw.makeDownPolygon(o_X, o_Y, n_X, n_Y);
                Rectangle r = new Rectangle(0,0,tileSize,tileSize);//Creates a rectangle to put the texture in
                TexturePaint txtr = new TexturePaint(tileGrass, r);
                g2.setPaint(txtr);
                g2.fillPolygon(p1);
                g2.fillPolygon(p2);
			}
		};
		return tile;
	}

    private static JPanel createTileBorder(int nX, int nY){
        JPanel tile = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                int half = size/2;//x+half*3,y+half, to center isometric map
                Graphics2D g2 = (Graphics2D) g;
                int o_X = (nX+half*3)*tileSize-((half+2)*tileSize);//o = origin
                int o_Y = (nY+half)*tileSize-((half+2)*tileSize)-moveup;
                int n_X = (((nX+1)+half*3))*tileSize-((half+2)*tileSize);//n = next coordinates
                int n_Y = (((nY+1)+half))*tileSize-((half+2)*tileSize)-moveup;
                g2.setColor(Color.BLACK);//Borders of each tile
				g2.setStroke(new BasicStroke(1));
                g2.drawLine(cart2isoX(o_X,o_Y),cart2isoY(o_X,o_Y),cart2isoX(o_X,n_Y),cart2isoY(o_X,n_Y));
                g2.drawLine(cart2isoX(o_X,o_Y),cart2isoY(o_X,o_Y),cart2isoX(n_X,o_Y),cart2isoY(n_X,o_Y));
                g2.drawLine(cart2isoX(o_X,n_Y),cart2isoY(o_X,n_Y),cart2isoX(n_X,n_Y),cart2isoY(n_X,n_Y));
                g2.drawLine(cart2isoX(n_X,o_Y),cart2isoY(n_X,o_Y),cart2isoX(n_X,n_Y),cart2isoY(n_X,n_Y));
            }
        };
        return tile;
    }

	private static JPanel createTileNode(int nX, int nY){
		JPanel tile = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                int half = size/2;//x+half*3,y+half, to center isometric map
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2));
                int o_X = (nX+half*3)*tileSize-((half+2)*tileSize);//o = origin
                int o_Y = (nY+half)*tileSize-((half+2)*tileSize)-moveup;
                int n_X = (((nX+1)+half*3))*tileSize-((half+2)*tileSize);//n = next coordinates
                int n_Y = (((nY+1)+half))*tileSize-((half+2)*tileSize)-moveup;
 				int num = nX*10+nY+1;
                if (tombala.hasBeenPicked(num)){//we added another 6 down below so tile image would be in center
                    int difY = (cart2isoY(n_X,n_Y)-cart2isoY(o_X,o_Y))+24;//image tiles are 64x64, 64-48=16
                    int gX = (cart2isoX(n_X,n_Y)-32);
                    int gY = (cart2isoY(n_X,n_Y)-difY);
                    g2.drawImage(tileCoin, gX,gY, null);
                    String text = Integer.toString(num);
					int fontSize = 24;
					Font font = g.getFont().deriveFont( Font.BOLD, 24f );
					g2.setFont(font);
					int x_cord = gX+32-(int)sqrt(fontSize);
					if (num > 9){
						x_cord = x_cord-(int)sqrt(fontSize);
					}
					if (num > 99){
						x_cord = x_cord - 8;
					}
					g2.setColor(Color.BLACK);
					FontRenderContext frc = g2.getFontRenderContext();
					TextLayout tl = new TextLayout(text, g.getFont().deriveFont(24F), frc);
					Shape shape = tl.getOutline(null);
					g2.setStroke(new BasicStroke(3f));
					g2.translate(x_cord-4,gY+fontSize+24-4);
					g2.draw(shape);
					g2.setColor(Color.white);
					g2.fill(shape);
				}
            }
        };
        return tile;
	}

	private static void initPolygons(){
		polygonMap.setLayout(new OverlayLayout(polygonMap));
		for (int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				JPanel newTile = createTexturedPolygon(i,j);
				newTile.setOpaque(false);
				polygonMap.add(newTile);
			}
		}
		polygonMap.setOpaque(false);
	}

	private static void initBorders(){
		borderMap.setLayout(new OverlayLayout(borderMap));
		for (int i=0;i<size;i++){
			for (int j=0;j<size;j++){
				JPanel newTile = createTileBorder(i,j);
				newTile.setOpaque(false);
				borderMap.add(newTile);
			}
		}
		borderMap.setOpaque(false);
	}

	private static void initPanelAll(){
		all.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = e.getComponent().getWidth();
				tileSize = (5*w)/(size*11);
			}
		});
	}

 	private static void initDirtTile(){
        dirtTile = new JPanel() {
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
				int half = size/2;//x+half*3,y+half, to center isometric map on panel
				int leftX = ((size)+half*3)*tileSize-((half+2)*tileSize);
				int leftY = ((half)*tileSize-((half+2)*tileSize))-moveup;
				int rightX = (half*3)*tileSize-((half+2)*tileSize);
				int rightY = (((size)+half)*tileSize-((half+2)*tileSize))-moveup;
				int bX = ((size)+half*3)*tileSize-((half+2)*tileSize);
				int bY = (((size)+half)*tileSize-((half+2)*tileSize))-moveup;
                Graphics2D g2 = (Graphics2D) g;
                Polygon p =  IsometricDraw.makeDirtPolygon(leftX, leftY, bX, bY);
                Polygon p1 =  IsometricDraw.makeDirtPolygon(rightX, rightY, bX, bY);
                Rectangle r = new Rectangle(0,0,tileSize,tileSize);//Creates a rectangle to put the texture in
                TexturePaint txtr = new TexturePaint(tileDirt, r);
                g2.setPaint(txtr);
                g2.fillPolygon(p);
                g2.fillPolygon(p1);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(cart2isoX(leftX,leftY),cart2isoY(leftX,leftY),cart2isoX(bX,bY),cart2isoY(bX,bY));
                g2.drawLine(cart2isoX(rightX,rightY),cart2isoY(rightX,rightY),cart2isoX(bX,bY),cart2isoY(bX,bY));
				g2.drawLine(cart2isoX(leftX,leftY),cart2isoY(leftX,leftY),cart2isoX(leftX+48,leftY+48),cart2isoY(leftX+48,leftY+48));
				g2.drawLine(cart2isoX(rightX,rightY),cart2isoY(rightX,rightY),cart2isoX(rightX+48,rightY+48),cart2isoY(rightX+48,rightY+48));
				g2.drawLine(cart2isoX(bX,bY),cart2isoY(bX,bY),cart2isoX(bX+48,bY+48),cart2isoY(bX+48,bY+48));
				g2.drawLine(cart2isoX(leftX+48,leftY+48),cart2isoY(leftX+48,leftY+48),cart2isoX(bX+48,bY+48),cart2isoY(bX+48,bY+48));
				g2.drawLine(cart2isoX(rightX+48,rightY+48),cart2isoY(rightX+48,rightY+48),cart2isoX(bX+48,bY+48),cart2isoY(bX+48,bY+48));
            }
        };
        dirtTile.setOpaque(false);
    }

	private static void initNodes(){
		ArrayList<JPanel> nodeTiles = new ArrayList<>();
		nodeMap.setLayout(new OverlayLayout(nodeMap));
		for (int i=0;i<width;i++){
			for (int j=0;j<height;j++){
				JPanel newTile = createTileNode(i,j);
				newTile.setOpaque(false);
				nodeTiles.add(newTile);
			}
		}
		Collections.reverse(nodeTiles);
		for (JPanel panel : nodeTiles){
			nodeMap.add(panel);
		}
		nodeMap.setOpaque(false);
	}

	private static void initTopPanel(){
		topPanel.setLayout(new OverlayLayout(topPanel));
		JPanel displayLast = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				String text = "Last Poll: " + tombala.getLastPoll();
				g2.setColor(Color.BLACK);
				FontRenderContext frc = g2.getFontRenderContext();
				TextLayout tl = new TextLayout(text, g.getFont().deriveFont(24F), frc);
				Shape shape = tl.getOutline(null);
				g2.setStroke(new BasicStroke(3f));
				g2.translate(24,32);
				g2.draw(shape);
				g2.setColor(Color.white);
				g2.fill(shape);
			}
		};
		displayLast.setOpaque(false);
		topPanel.add(displayLast);
		topPanel.setOpaque(false);
	}

	public static JPanel createMap(GameLogic t){
		tombala = t;
		initPanelAll();
		all.setLayout(new OverlayLayout(all));
		GridLayout gameBoard = new GridLayout(size, size);
		gameMap.setLayout(gameBoard);
		initTileTextures();
		initPanel();
		initPolygons();
		initBorders();
		initDirtTile();
		initNodes();
		initTopPanel();
		gameMap.setOpaque(false);
		gameMap.setVisible(false);
		all.add(gameMap);
		all.add(nodeMap);
		all.add(dirtTile);
		all.add(borderMap);
		all.add(polygonMap);
		all.add(topPanel);
		all.setOpaque(false);
		all.setVisible(true);
		return all;
	}
}
