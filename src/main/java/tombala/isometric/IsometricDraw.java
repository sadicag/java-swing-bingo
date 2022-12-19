package tombala.isometric;
import java.awt.*;

/**
 * This class will be used to calculate isometric calculations JPanels.
 */

public class IsometricDraw{
    private static int tileSize=48;

    /**
     * This method creates and returns a polygon, for the upper half of each isometric tile.
     * @param oX is the origin X coordinate
     * @param oY is the origin Y coordinate
     * @param nX is the next X coordinate
     * @param nY is the next X coordinate
     * @return returns the creates polygon
     */
    public static Polygon makeUpPolygon(int oX, int oY, int nX, int nY){
        Polygon p = new Polygon();
        p.addPoint((oX-nY),(oX+nY)/2);
        p.addPoint((nX-oY),(nX+oY)/2);
        p.addPoint((nX-nY),(nX+nY)/2);
        return p;
    }

    /**
     * This method creates and returns a polygon, for the lower half of each isometric tile.
     * @param oX is the origin X coordinate
     * @param oY is the origin Y coordinate
     * @param nX is the next X coordinate
     * @param nY is the next X coordinate
     * @return returns the creates polygon
     */
    public static Polygon makeDownPolygon(int oX, int oY, int nX, int nY){
        Polygon p = new Polygon();
        p.addPoint((oX-nY),(oX+nY)/2);
        p.addPoint((nX-oY),(nX+oY)/2);
        p.addPoint((oX-oY),(oX+oY)/2);
        return p;
    }

    /**
     * This method creates and returns a polygon, for half of the dirt tile underneath.
     * @param lX is the origin X coordinate
     * @param lY is the origin Y coordinate
     * @param bX is the next X coordinate
     * @param bY is the next X coordinate
     * @return returns the creates polygon
     */

    public static Polygon makeDirtPolygon(int lX, int lY, int bX, int bY){
        Polygon p = new Polygon();
        int newLX = lX +tileSize;
        int newLY = lY +tileSize;
        p.addPoint((newLX-newLY),(newLX+newLY)/2);
        p.addPoint((lX-lY),(lX+lY)/2);
        p.addPoint((bX-bY),(bX+bY)/2);
        bX = bX +tileSize;
        bY = bY +tileSize;
        p.addPoint((bX-bY),(bX+bY)/2);
        return p;
    }

}