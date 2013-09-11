/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

import java.awt.*;
import java.util.*;

/**
 * Creates a computer "AI" to play against the player.
 * This class packages all functions of a computer player.
 * @author saavan
 */
public class AI {
    private Color myColor;
    private Grid playerGrid;
    private Grid dummy;
    LinkedList moves;
    String hi;
    Calendar cal;

    /**
     * Constructor for the AI class, no other constructors available.
     * @param c The color given to this AI
     * @param gr The grid in which this AI will make its moves
     */
    public AI(Color c, Grid gr)
    {
        myColor = c;
        playerGrid = gr;
        dummy = gr.clone();
        moves = new LinkedList();
        cal = Calendar.getInstance();
    }
    
    /**
     * Makes the next move by moving a piece in the grid given on construction
     * Evaluates all possible moves and makes the one it deems to be most "correct"
     */
    public void getNextMove()
    {
       dummy = playerGrid.clone();
       Move toMake = pickMove();
       playerGrid.pieceAt(toMake.getFrom()).moveTo(toMake.getTo());
       playerGrid.update();
       ButtonListener.playerTurn=true;
       System.out.printf("AI moved %s", toMake);
       System.out.println("______________________________");
    }

    /**
     * Subset of <code> getNextMove() </code>, This method gets the move to be made next, but doesn't make it.
     * @return Move to be made
     */
    public Move pickMove()
    {

        Node<Move,Grid> root = new Node(new Move(new RedChecker(new Location(20,20),dummy),null,null),dummy);
        Node<Move,Grid> antiroot = new Node(new Move(new BlackChecker(new Location(50,50),dummy),null,null),dummy);
        System.out.println("Recursing possible moves");
        long t1 = Calendar.getInstance().getTimeInMillis();
        recurse(antiroot,3);
        long t2 = Calendar.getInstance().getTimeInMillis();
        System.out.printf("Time took for recursion: %s \n",t2-t1);

        System.out.println(antiroot.getAllElements());
        System.out.printf("Size of root: %d \n",antiroot.getAllElements().size());
        System.out.println("evaluating moves");
        t1 = Calendar.getInstance().getTimeInMillis();
        Move toMake = evaluateMoves(antiroot);
        t2 = Calendar.getInstance().getTimeInMillis();
        System.out.printf("Time took for move evalutation: %s \n", t2-t1);
        return toMake;
    }
    /**
     * Recurses through all possible moves to the depth given.
     * The color it starts off with is the opposite of the color given in the root object.
     * @param root Root node for things to be attached to.
     * @param depth How far you want it to go.
     */
    public void recurse(Node<Move,Grid> root, int depth)
    {
        if(depth<=0)
            return;

        Color myCol = Grid.oppositeColor(root.getObject().getPiece().getColor());
        Grid myGr = root.getObject1();
        for(Piece p : myGr.getPieces(myCol))
        {
            for(Location L : (myGr.getAllJumps(myCol).isEmpty() ? p.getValidLocations() : p.getJumpLocations()))
            {
                Grid clone = myGr.clone();
                Node<Move,Grid> temp = new Node<Move,Grid>(new Move(clone.pieceAt(p.getLocation()),p.getLocation(),L),clone);
                temp.getObject().getPiece().moveTo(L);
                temp.getObject1().update();
                root.addChild(temp);
                recurse(temp,depth-1);
            }
        }
    }
    /**
     * Evaluates all moves in the given node, and returns the one that gets the higest ranking from <code> rankMove() </code>
     * @param root Root node from which to rank
     * @return Move that receives highest score that is a child of root node.
     */
    public Move evaluateMoves(Node<Move,Grid> root)
    {
        LinkedList<Node<Move,Grid>> children = root.getChildren();
        Double[] rankings = new Double[children.size()];
        double high = Integer.MIN_VALUE;
        Node<Move,Grid> highest = null;
        for(int i = 0;i<children.size();i++)
        {
            rankings[i] = rankMove(children.get(i),1);
            if(rankings[i]>high)
            {
                highest = children.get(i);
                high = rankings[i];
            }


        }
        System.out.println(arrayToString((rankings)));
        return highest.getObject();
    }
    /**
     * Assigns a number to this board state based on criteria.
     * @param ranked
     * @param multiplier positive if calculating AI, negative for player
     * @return a double of the rank of this Move.
     */
    public double rankMove(Node<Move,Grid> ranked, double multiplier)
    {
        double rank = 0;
        Color myCol = myColor;
        Color oppCol = Grid.oppositeColor(myCol);
        // Subtracts pieces lost
        int mypieces = ranked.getObject1().getPieces(myCol).size();
        int previousPieces = ranked.getParent().getObject1().numPieces(myCol);
        rank -= (previousPieces - mypieces) * multiplier;

        // Adds pieces taken
        int oppPieces = ranked.getObject1().getPieces(oppCol).size();
        int oppPreviousPieces = ranked.getParent().getObject1().getPieces(oppCol).size();
        rank += (oppPreviousPieces - oppPieces) * multiplier;

        //Adds kings made
        int myKings = ranked.getObject1().getKings(myCol).size();
        int previousKings = ranked.getParent().getObject1().getKings(myCol).size();
        rank += (previousKings - myKings) * multiplier;

        //subtracts opponents kings made
        int oppKings = ranked.getObject1().getKings(oppCol).size();
        int opppreviousKings = ranked.getParent().getObject1().getKings(oppCol).size();
        rank -= (opppreviousKings - oppKings) * multiplier;

        //Adds .2 * my jump locations
        int myJumps = 0;
        for(Piece p : ranked.getObject1().getPieces(myColor))
        {
            myJumps += (p.getJumpLocations().size());
        }
        rank += .2* myJumps * multiplier;

        //Subtracts .2 * their jump locations
        int oppJumps = 0;
        for(Piece p : ranked.getObject1().getPieces(oppCol))
        {
            oppJumps+= (p.getJumpLocations().size()) ;
        }
        rank-= .2 * oppJumps * multiplier;

        //adds board position

        //Recursively adds all child ranks to this rank
        for(Node<Move,Grid> child : ranked.getChildren())
        {
            rank+= (rankMove(child,multiplier/2));
        }

        return rank;
    }
    /**
     * Returns a "readable" version of the given Object array.
     * Calls the <code>toString()</code> of all elements in the array in proper order.
     * @param arr Array to be returned as string
     * @return A readable String of all elements in the array.
     */
    public static String arrayToString(Object[] arr)
    {
        String answer = "[";
        for(int i = 0;i<arr.length;i++)
        {
           answer+=arr[i] + ",";
        }
        answer+= "]";
        return answer;
    }
}

