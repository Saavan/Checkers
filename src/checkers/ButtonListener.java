/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * This class is an umbrella class that handles all events involving buttons clicked.
 * It gets the action clicked, and then appropriately relays to either the AI or to the player for the next move.
 * @author saavan
 */
public class ButtonListener implements ActionListener{
    private Location myLocation;
    /**
     * Tells whether it is the player's turn or the AI's turn. Used by both AI and player to switch between turns.
     */
    public static boolean playerTurn = true;
    private Player player;
    // Reference to Bicentennial man :)
    private AI andrewMartin;
    private Grid myGrid;
    private GUI myGui;
    
/**
 * Default constructor for this class
 * @param L Location clicked
 * @param myGrid Grid in which this location was clicked (used to update the grid after moves are made)
 * @param gui GUI used in the clicking (used to repaint the gui after moves are made)
 */
    public ButtonListener(Location L, Grid myGrid, GUI gui)
    {
        myLocation = L;
        player = new Player(myLocation,myGrid);
        andrewMartin = new AI(Color.RED,myGrid);
        this.myGrid=myGrid;
        myGui=gui;

    }

    public void actionPerformed(ActionEvent e) {

        if(playerTurn&&!Player.hasClicked)
        {
            player.getNextMove();
        }
        else
        {
           player.getNextMove();
           if(!playerTurn&&!Player.hasClicked)
                andrewMartin.getNextMove();
           else
               player.getNextMove();
        }
        if(myGrid.getPieces(Color.RED).isEmpty()||myGrid.getPieces(Color.BLACK).isEmpty())
        {
            System.exit(0);
        }
        myGui.update(((JButton)e.getSource()).getGraphics());
    }
    
    
    
}
