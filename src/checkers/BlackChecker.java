/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Creates a standard blackChecker piece to be used in the grid.
 * @author saavan
 */
public class BlackChecker extends Piece {
    private ImageIcon myImage;

    /**
     * Only constructor for the Black Checker class. 
     * @param L Location this piece exists in (can be changed by <code> moveTo() </code> method.
     * @param gr Grid in which this piece exists
     */
    public BlackChecker(Location L, Grid gr)
    {
        super(L,gr);
        myImage = new javax.swing.ImageIcon(getClass().getResource("/checkers/blackChecker.jpg"));
    }

    @Override
    public ArrayList<Location> getValidLocations()
    {
        return super.getValidLocations();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public ImageIcon getImage() {
        return myImage;
    }

    public ArrayList<Location> getJumpLocations()
    {
        return super.getJumpLocations(getLocation());
    }
    public Piece clone(Grid gr)
    {
        return new BlackChecker(getLocation(),gr);
    }

}
