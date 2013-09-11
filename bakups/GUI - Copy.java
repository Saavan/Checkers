/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUI.java
 *
 * Created on May 9, 2011, 8:46:45 PM
 */

package checkers;

import java.awt.*;
import javax.swing.*;

/**
 * Creates a Graphical User Interface to play the game on.
 * Creates a two dimensional array of buttons that correspond to specific locations.
 * @author saavan
 */
public class GUI extends Frame {
    // Variables declaration - do not modify
    private JButton[][] buttons;
    private JPanel jPanel1;

    /**
     * Default Constructor. Calls <code>initComponents()</code> and nothing else.
     */
    public GUI()
    {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents()
    {

        buttons = new JButton[8][8];
        jPanel1 = new javax.swing.JPanel();


        jPanel1.setName("Checkers");

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(400, 400));
        setName("Checkers");
        setLayout(new java.awt.GridLayout(8, 8));
        setSize(400, 400);

        for(int row = 0;row<buttons.length;row++)
        {
            for(int col = 0;col<buttons[row].length;col++)
            {
                buttons[row][col]=new javax.swing.JButton();
                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setMaximumSize(new java.awt.Dimension(60, 60));
                buttons[row][col].setMinimumSize(new java.awt.Dimension(60, 60));
                buttons[row][col].setRolloverEnabled(false);
                buttons[row][col].setName(row+", "+col);
                buttons[row][col].addActionListener(new ButtonListener(new Location(row,col)));
                try {buttons[row][col].setIcon(Main.myGrid.pieceAt(new Location(row,col)).getImage()); } catch(Exception ex) {}

                if(row%2==col%2)
                {
                    buttons[row][col].setForeground(Color.red);
                    buttons[row][col].setBackground(Color.red);
                }
                else
                {
                    buttons[row][col].setForeground(Color.black);
                    buttons[row][col].setBackground(Color.black);
                }
                add(buttons[row][col]);
            }
        }
        pack();
    }// </editor-fold>

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) 
    {
        System.exit(0);
    }

    @Override
    public void update(Graphics g)
    {
        for(int row = 0;row<buttons.length;row++)
        {
            for(int col = 0;col<buttons[row].length;col++)
            {
                try {buttons[row][col].setIcon(Main.myGrid.pieceAt(new Location(row,col)).getImage()); } 
                    catch(NullPointerException ex) {buttons[row][col].setIcon(null);}
                add(buttons[row][col]);
            }
        }
        pack();
        System.out.println("updated!");
    }

    /**
     * Runs the GUI, for testing, and to be run by other main classes.
     * @param args
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}

