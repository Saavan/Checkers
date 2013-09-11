/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers;

import java.awt.Color;
import java.util.*;
import javax.swing.*;

/**
 * Standard redChecker class for this program. Extends the Piece class.
 * @author saavan
 */
public class RedChecker extends Piece {
   private ImageIcon myImage;
   /**
    *Standard constructor for this class
    * @param L Location this piece exists
    * @param gr Grid in which this piece exists
    */
   public RedChecker(Location L, Grid gr)
    {
        super(L,gr);
        myImage = new javax.swing.ImageIcon(getClass().getResource("/checkers/redChecker.jpg"));
    }
    
    @Override
    public ArrayList<Location> getValidLocations()
    {
        return super.getValidLocations();
    }

    @Override
    public Color getColor() {
        return Color.RED;
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
        return new RedChecker(getLocation(),gr);
    }
}
