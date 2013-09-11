/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Grid class, creates a two dimensional array of type "piece"
 * Also performs functions to update, check validity etc.
 * @author saavan
 */
public class Grid {

    private Piece[][] Grid;
    /**
     * Creates a grid with the specified rows and columns for pieces to exist on.
     * @param numRows Number of rows in this grid.
     * @param numCols Number of columns in this grid
     */
    public Grid(int numRows, int numCols)
    {
        Grid = new Piece[numRows][numCols];
    }
    /**
     * Creates a grid based on another grid (calls <code> clone() </code> method on the other grid to do so)
     * @param another Grid to be copied from.
     */
    public Grid(Grid another)
    {
        this.Grid = another.Grid.clone();
    }
     /**
     * Updates locations of all pieces within the grid. Necessary to redraw the grid
     */
    public void update()
    {
        for(int row = 0;row<Grid.length;row++)
        {
            for(int col = 0;col<Grid[row].length;col++)
            try {
                if(Grid[row][col].getLocation().getRow()!=row||Grid[row][col].getLocation().getCol()!=col)
                {
                    Grid[Grid[row][col].getLocation().getRow()][Grid[row][col].getLocation().getCol()]=Grid[row][col];
                    Grid[row][col]=null;
                }
            } catch(NullPointerException ex) {}
        }
    }
    /**
     * Returns all non-null pieces that are present in the logical grid.
     * Note: Logical Grid is not the same as GUI grid.
     * @return ArrayList of all non-null pieces in the grid
     */
    public ArrayList<Piece> getPieces()
    {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(Piece[] p : Grid)
        {
            for(Piece piece : p)
            {
                if(piece!=null) pieces.add(piece);
            }
        }
        return pieces;
    }
    /**
     * Returns an ArrayList of all non-null pieces of given color in the logical grid.
     * Note: Logical Grid is not the same as GUI grid.
     * @param c Color of the pieces you want returned
     * @return Returns ArrayList of all pieces of given color c
     */
    public ArrayList<Piece> getPieces(Color c)
    {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(Piece[] p : Grid)
        {
            for(Piece piece : p)
            {
                if(piece!=null&&piece.getColor()==c) pieces.add(piece);
            }
        }
        return pieces;
    }
    /**
     * Returns an ArrayList of all Kings of given color in the logical grid.
     * Note: Logical Grid is not the same as GUI grid.
     * @param c Color of kings to be returned
     * @return Arraylist of all kings of given color.
     */
    public ArrayList<King> getKings(Color c)
    {
        ArrayList<Piece> pieces = getPieces(c);
        ArrayList<King> kings = new ArrayList<King>();
        for(Piece p : pieces)
        {
            if(p instanceof King)  kings.add((King)p);
        }
        return kings;
    }
     /**
     * Adds a piece at whatever location is set in the piece
      * Precondition: Location is valid within the grid
      * @param p
      */
    public void addPiece(Piece p)
    {
        Grid[p.getLocation().getRow()][p.getLocation().getCol()]=p;
    }
     /**
     * Removes piece at specified location
      * Precondition: location is valid within the grid
      * @param L
      * @return returns piece you removed
     */
    public Piece removePiece(Location L)
    {
        Piece temp = Grid[L.getRow()][L.getCol()];
        Grid[L.getRow()][L.getCol()]=null;
        return temp;
    }
     /**
     * Checks if the location is valid within the grid,
      * NOTE: does not check whether there is a piece at given location
      * @param L
      * @return
      */
    public boolean isValid(Location L)
    {
        if(L.getRow()>=Grid.length||L.getRow()<0) return false;
        if(L.getCol()>=Grid[0].length||L.getCol()<0) return false;
        return true;
    }
    /**
     * Number of rows within the logical grid. 
     * @return numRows
     */
    public int getRows()
    {
        return Grid.length;
    }
    /**
     * Number of columns in the logical grid
     * @return numCols
     */
    public int getCols()
    {
        return Grid[0].length;
    }
    /**
     * Number of pieces contained in this grid
     * @return number of non-null pieces within the grid.
     */
    public int numPieces()
    {
        int count = 0;
        for(Piece[] p : Grid)
        {
            for(Piece piece : p)
            {
                if(piece!=null)
                    count++;
            }
        }
        return count;
    }
    /**
     * Returns number of pieces of given color, 0 if there are none
     * @param c
     * @return number of pieces in grid with given color
     */
    public int numPieces(Color c)
    {
        int count = 0;
        for(Piece[] p : Grid)
        {
            for(Piece piece: p)
            {
                if(piece!=null)
                {
                    if(piece.getColor()==c)
                        count++;
                }
            }
        }
        return count;
    }
    /**
     * isValid function, also checks if there is piece at specified location
     * Returns true if there is a piece at specified location
     * Returns false if there is no piece at specified location OR specified location is invalid
     * @param L Location to be checked
     * @return True if there is a piece at specified location.
     * False if there is no piece at specified location OR specified location is invalid.
     */
    public boolean isPieceAt(Location L)
    {
        if(isValid(L))
        {
            if(Grid[L.getRow()][L.getCol()]!=null)
                return true;
        }
        return false;
    }
    /**
     * Returns piece at given location
     * Precondition: Location is valid within the grid
     * @param L
     * @return Piece at Location L
     */
    public Piece pieceAt(Location L)
    {
        return Grid[L.getRow()][L.getCol()];
    }
    /**
     * Returns all valid locations for pieces of Color c.
     * Essentially calls <code> getValidLocations() </code> on all pieces and puts them in one LinkedList
     * @param c Color of pieces you want to find
     * @return LinkedList<Location> of all valid locations for all non-null pieces.
     */
    public LinkedList<Location> getAllValidLocs(Color c)
    {
        LinkedList<Location> answers = new LinkedList<Location>();
        for(Piece p : getPieces(c))
        {
            answers.addAll(p.getValidLocations());
        }
        return answers;
    }
    /**
     * Returns all jump locations for all non-null pieces of color c.
     * Essentially calls <code> getJumpLocations() </code> on all pieces and puts them in one LinkedList
     * @param c color you want jump locations of
     * @return LinkedList<Location> of all valid locations for all non-null pieces.
     */
    public LinkedList<Location> getAllJumps(Color c)
    {
        LinkedList<Location> answers = new LinkedList<Location>();
        for(Piece p : getPieces(c))
        {
            answers.addAll(p.getJumpLocations());
        }
        
        return answers;
    }
    @Override
    public String toString()
    {
        return "tis a grid!";
    }
    
    @Override
    public Grid clone()
    {
        Grid clone = new Grid(Grid.length,Grid[0].length);
        for(Piece p : getPieces())
        {
            clone.addPiece(p.clone(clone));
        }
        return clone;
    }
    /**
     * Returns the opposing color of the one specified. Makes it easier to switch colors if you want.
     * @param c Color you want to find the opposite of
     * @return Opposite of color c
     */
    public static Color oppositeColor(Color c)
    {
        return c.equals(Color.red) ? Color.BLACK : Color.RED;
    }



}
