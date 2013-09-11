/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 * Piece that implements Kings in checkers programs.
 * Kings have special abilities in that they can move both up and down the board, thus they must be a seperate class.
 * @author saavan
 */
public class King extends Piece {
    private ImageIcon myImage;
    private Color myColor;
    
    /**
     * Default constructor for this class.
     * @param L Location this piece exists at
     * @param gr Grid in which this piece exists
     * @param c Color this piece is
     */
    public King(Location L, Grid gr, Color c)
    {
        super(L,gr);
        myColor = c;
        if(c==Color.BLACK)
        {
            myImage = new ImageIcon(getClass().getResource("/checkers/blackKing.jpg"));
        }
        else
        {
            myImage = new ImageIcon(getClass().getResource("/checkers/redKing.jpg"));
        }
        
    }
    @Override
    public Color getColor() 
    {
        return myColor;
    }

    @Override
    public ImageIcon getImage() 
    {
        return myImage;
    }
    
    @Override
    public ArrayList<Location> getValidLocations()
    {
        ArrayList<Location> answers = new ArrayList<Location>();
        answers.addAll(super.getValidLocations(1));
        answers.addAll(super.getValidLocations(-1));
        answers.addAll(getJumpLocations());
        return answers;
    }
    @Override
    public ArrayList<Location> getJumpLocations()
    {
        ArrayList<Location> answers = new ArrayList<Location>();
        answers.addAll(super.getJumpLocations(getLocation(), 1));
        answers.addAll(super.getJumpLocations(getLocation(), -1));
        return answers;
    }
    
    public Piece clone(Grid gr)
    {
        return new King(getLocation(),gr, getColor());
    }
    
}
