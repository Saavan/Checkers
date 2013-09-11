/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

/**
 * Move class packages a single "Move" in checkers.
 * Includes the piece, from, and to locations. 
 * Class used only in AI.
 * @author saavan
 */
public class Move {
    
    private Location from;
    private Location to;
    private Piece myPiece;
    /**
     *
     * @param p Piece to be packaged in this move
     * @param f From location this piece moves from
     * @param t To location this piece is moving to
     */
    public Move(Piece p, Location f, Location t)
    {
        myPiece = p;
        from = f;
        to = t;
    }
    /**
     * Returns the from location
     * @return From location
     */
    public Location getFrom()
    {
        return from;
    }
    /**
     * Returns the to location
     * @return To location
     */
    public Location getTo()
    {
        return to;
    }
    /**
     * Returns the piece packaged in this class
     * @return Piece
     */
    public Piece getPiece()
    {
        return myPiece;
    }
    /**
     * Moves the piece to the "to" location. Doesn't matter where the piece is now.
     * Same as Piece.moveTo(to);
     */
    public void movePiece()
    {
        myPiece.moveTo(to);
    }
    /**
     * Moves the piece to the "from" location. Doesn't matter where the piece is now.
     * Same as Piece.moveTo(from);
     */
    public void unmovePiece()
    {
        myPiece.moveTo(from);
        System.out.printf("Piece moved back from %s to %s \n",to,from);
    }
    @Override
    public String toString()
    {
        return "from: " + from +"to: " + to + "with piece: " + myPiece + "\n";
    }
    
    
}
