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
public class GUI extends JApplet {
    // Variables declaration - do not modify
    private JButton[][] buttons;
    private JPanel jPanel;
    Grid myGrid = new Grid(8,8);

    @Override
    public void init() {
        for(int row = 0;row<myGrid.getRows();row+=1)
        {
            for(int col = (row+1)%2;col<myGrid.getCols();col+=2)
            {
                if(row<3)myGrid.addPiece(new RedChecker(new Location(row, col), myGrid));
                if(row>4)myGrid.addPiece(new BlackChecker(new Location(row,col),myGrid));
            }
        }
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    myGrid.update();
                }
            });
        } catch (Exception ex) {
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents()
    {
        buttons = new JButton[8][8];
        jPanel = new javax.swing.JPanel();
        jPanel.setName("Checkers");
        jPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel.setMinimumSize(new java.awt.Dimension(600, 600));
        jPanel.setSize(600,600);
        jPanel.setLayout(new java.awt.GridLayout(8,8));
        

        for(int row = 0;row<buttons.length;row++)
        {
            for(int col = 0;col<buttons[row].length;col++)
            {
                buttons[row][col]=new javax.swing.JButton();
                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setMaximumSize(new java.awt.Dimension(80, 80));
                buttons[row][col].setMinimumSize(new java.awt.Dimension(80, 80));
                buttons[row][col].setSize(60, 60);
                buttons[row][col].setRolloverEnabled(false);
                buttons[row][col].setName(row+", "+col);
                buttons[row][col].addActionListener(new ButtonListener(new Location(row,col),myGrid,this));
                try {buttons[row][col].setIcon(myGrid.pieceAt(new Location(row,col)).getImage()); } catch(Exception ex) {}

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
                jPanel.add(buttons[row][col]);
            }
        }
        getContentPane().add(jPanel);
        myGrid.update();
        super.repaint();
    }// </editor-fold>

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) 
    {
        System.exit(0);
    }


    @Override
    public void update(Graphics g)
    {
        myGrid.update();
        for(int row = 0;row<buttons.length;row++)
        {
            for(int col = 0;col<buttons[row].length;col++)
            {
                try {buttons[row][col].setIcon(myGrid.pieceAt(new Location(row,col)).getImage()); } 
                    catch(NullPointerException ex) {buttons[row][col].setIcon(null);}
                add(buttons[row][col]);
            }
        }
        getContentPane().add(jPanel);
        System.out.println("updated!");
    }
}

