/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package checkers;


import java.awt.Color;
import java.util.ArrayList;

/**
 * Player class to get and analyze moves from the player. Calls invalid move and others.
 * @author saavan
 */
public class Player
{
    private Location clicked;
    /**
     * If the player has clicked before. Used in determining if the piece should move
     * or if the program should wait for the next user click.
     */
    public static boolean hasClicked=false;
    private static Location lastClicked;
    private Grid myGrid;

    /**
     * Standard constructor for this class.
     * @param L Location clicked
     * @param gr Grid where the event occurred
     */
    public Player(Location L, Grid gr)
    {
        clicked = L;
        myGrid = gr;
    }
    /**
     * Moves the players next piece forward
     */
    public void getNextMove()
    {
        System.out.println("Player turn ");
        System.out.printf("Location clicked: (%s) \n",clicked);
        if(myGrid.isPieceAt(clicked)&&myGrid.pieceAt(clicked).getColor()!=Color.BLACK)
        {
            checkers.InvalidMove.start();
            hasClicked = false;
        }

        else if(myGrid.isPieceAt(clicked) && myGrid.pieceAt(clicked).getColor() == Color.BLACK)
        {
            System.out.println("I clicked on a piece!");
            System.out.print("Valid Locations for this piece:");
            for(Location L : myGrid.pieceAt(clicked).getValidLocations()) System.out.printf(" (%s) ", L);
            System.out.println();
            System.out.print("Jump locations for this piece:");
            for(Location L : myGrid.pieceAt(clicked).getJumpLocations()) System.out.printf(" (%s) ", L);
            System.out.println("");
            lastClicked = clicked;
            hasClicked=true;
        }
        else if(hasClicked)
        {
            ArrayList<Location> locations = myGrid.pieceAt(lastClicked).getValidLocations();
            for(Location test : myGrid.pieceAt(lastClicked).getValidLocations())
            {
                if(lastClicked!=null)
                {
                    System.out.printf("Is piece at last clicked (%s) \n", myGrid.isPieceAt(lastClicked));
                    if(clicked.equals(test))
                    {
                       System.out.printf("Going from: (%s) to: (%s) \n", lastClicked, clicked);
                       System.out.printf("Is piece at last clicked: (%s) \n", myGrid.isPieceAt(lastClicked));
                       if(myGrid.isPieceAt(lastClicked)) myGrid.pieceAt(lastClicked).moveTo(clicked);
                       myGrid.update();
                       System.out.println("Piece moved");
                       hasClicked=false;
                    }
                }
            }
            ButtonListener.playerTurn=false;
            if(myGrid.pieceAt(lastClicked)!=null)
            {
                if((!myGrid.pieceAt(lastClicked).getValidLocations().contains(clicked))||((!myGrid.pieceAt(lastClicked).getJumpLocations().isEmpty())&&(!myGrid.pieceAt(lastClicked).getJumpLocations().contains(clicked))))
                {
                    InvalidMove.start();
                    ButtonListener.playerTurn = true;
                }
            }

        }
        System.out.println("______________________________");
    }
}
