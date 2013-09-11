/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers;

import java.awt.*;
import java.util.*;
import javax.swing.ImageIcon;
/**
 * Standard Piece class for this program. This is an all-encompassing class that is used as a model for all its child classes.
 * Performs all basic functions of a checkers piece.
 * @author saavan
 */
public abstract class Piece {
    private Location myLocation;
    private Grid grid;
    private HashMap<Location,ArrayList<Piece>> removeJumps;
    private static int counter;
    private int myCount;
    /**
     * Standard constructor for pieces
     * @param L Location this piece exists
     * @param gr Grid in which this piece exists
     */
    public Piece(Location L, Grid gr)
    {
        counter++;
        myCount=counter;
        myLocation = L;
        grid = gr;
        removeJumps = new HashMap<Location,ArrayList<Piece>>();
    }

    /**
     * Returns current location
     * @return returns current Location
     */
    public Location getLocation()
    {
        return myLocation;
    }
    /**
     * Sets the location of this piece to newL
     * @param newL
     */
    public void setLocation(Location newL)
    {
        myLocation = newL;
        grid.update();
    }
    /**
     * Returns the grid in which this piece exists
     * @return Grid in which this piece exists
     */
    public Grid getGrid()
    {
        return grid;
    }
    /**
     * Returns pieces that this piece can jump from its current location.
     * @return HashMap<Location,ArrayList<Piece>> of all pieces that can be captured by this piece.
     */
    public HashMap<Location,ArrayList<Piece>> getRemovablePieces()
    {
        return removeJumps;
    }

    /**
     * Returns color of this piece
     * @return Color of this piece
     */
    public abstract Color getColor();
    /**
     * Returns images representing this piece
     * @return ImageIcon of this piece
     */
    public abstract ImageIcon getImage();
    /**
     *Returns locations that this piece can jump
     * @return ArrayList<Location> of places this piece can jump to.
     */
    public abstract ArrayList<Location> getJumpLocations();
    
    /**
     * Gets all valid locations this piece can go
     * @return ArrayList<Location> of places this piece can jump to.
     */
    public ArrayList<Location> getValidLocations()
    {
        return getValidLocations(getColor()==Color.RED ? 1 : -1);
    }
    
    /**
     * Get Valid Locations based on the value of rowMultiplier
     * @param multiplier
     * @return The valid locations this piece has
     */
    public ArrayList<Location> getValidLocations(int multiplier)
    {
        
        ArrayList<Location> answers = new ArrayList<Location>();
        Location loc1 = new Location(getLocation().getRow()+multiplier,getLocation().getCol()+1);
        Location loc2 = new Location(getLocation().getRow()+multiplier,getLocation().getCol()-1);
        if(getGrid().isValid(loc1)&&(!getGrid().isPieceAt(loc1))) answers.add(loc1);
        if(getGrid().isValid(loc2)&&(!getGrid().isPieceAt(loc2))) answers.add(loc2);
        answers.addAll(getJumpLocations(getLocation()));
        return answers;
    }
    /**
     * Gets jumpable locations (simpler method)
     * @param L location in which this piece exists
     * @return ArrayList<location> of all places you can go to from specified location.
     */
    public ArrayList<Location> getJumpLocations(Location L)
    {
        return getJumpLocations(L,getColor()==Color.RED ? 1 : -1);
    }
    /**
     * More complex version of other function with the same name. Adds the multiplier parameter which suggest which color to be used.
     * With multiplier = 1, color is Red
     * With multiplier = -1, color is Black
     * @param L Location of this piece (necessary for effective recursion)
     * @param multiplier parameters outlined above
     * @return ArrayList<Location> of all jump locations with the specified parameters.
     */
    public ArrayList<Location> getJumpLocations(Location L, int multiplier)
    {
        ArrayList<Location> answers = new ArrayList<Location>();
        Location loc1 = new Location(L.getRow()+multiplier, L.getCol()+1);
        Location loc2 = new Location(L.getRow()+multiplier, L.getCol()-1);
        if(getGrid().isPieceAt(loc1)&&getGrid().pieceAt(loc1).getColor()!=getColor())
        {
            Location jump = new Location(loc1.getRow()+multiplier, loc1.getCol()+1);
            if((getGrid().isValid(jump))&&!getGrid().isPieceAt(jump))
            {
                //adding the jumped piece to the map
                ArrayList<Piece> temp = new ArrayList<Piece>();
                if(removeJumps.containsKey(L))
                    temp.addAll(removeJumps.get(L));
                temp.add(getGrid().pieceAt(loc1));
                removeJumps.put(jump, temp);
          
                answers.add(jump);
                answers.addAll(getJumpLocations(jump)); 
            }
        }
        if(getGrid().isPieceAt(loc2)&&getGrid().pieceAt(loc2).getColor()!=getColor())
        {
            Location jump = new Location(loc2.getRow()+multiplier, loc2.getCol()-1);
            if((getGrid().isValid(jump))&&!getGrid().isPieceAt(jump))
            { 
                //adding the jumped piece to the map
                ArrayList<Piece> temp = new ArrayList<Piece>();
                if(removeJumps.containsKey(L))
                    temp.addAll(removeJumps.get(L));
                temp.add(getGrid().pieceAt(loc2));
                removeJumps.put(jump, temp);
                answers.add(jump); 
                answers.addAll(getJumpLocations(jump)); 
            }
        }
        return answers;
    }
    /**
     * Removes pieces to be taken when moving from L to newL
     * @param L from Location
     * @param newL to Location
     */
    public void removeJumpedPieces(Location L, Location newL)
    {
        ArrayList<Location> jumpLocations = getJumpLocations(L);
        for(Piece p : getRemovablePieces().get(newL))
        {
            getGrid().removePiece(p.getLocation());
        }
    }
    /**
     * changes the Location to newL, performs various actions needed to move this piece while following all rules of checkers.
     * @param newL
     */
    public void moveTo(Location newL)
    {
        if(getJumpLocations().contains(newL))
        {
            removeJumpedPieces(myLocation,newL);
        }
        myLocation=newL;
        grid.update();
        if((getColor()==Color.BLACK && newL.getRow()==0)||(getColor()==Color.RED && newL.getRow()==7))
        {
           grid.addPiece(new King(new Location(newL.getRow(),newL.getCol()),grid,getColor()));
           grid.update();
        }  
    }
    @Override
    public String toString()
    {
        return ""+ myLocation + " " + (getColor()==Color.RED ? "RED" : "BLACK") + " " + getClass().getSimpleName() + " " + myCount;
    }
    /**
     * Clones this piece into the specified grid
     * @param gr Grid to place the cloned piece
     * @return The piece which has been created
     */
    public abstract Piece clone(Grid gr);
}
