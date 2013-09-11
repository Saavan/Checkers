/*
 * Node class to implement a tree
 * Used originally for APCS Checkers project
 */

package checkers;


import java.util.*;


/**
 * This is a class to implement tree structures within a java background. Essentially creates a tree structure, with the types E and F as containers.
 * It uses LinkedLists to house children, and can store an infinite (depending on memory) number of children per parent.
 * Does not support multiple parents.
 * @author Saavan
 * @param <E> The first type of Container used
 * @param <F> The second type of container used.
 */
public class Node<E,F> implements Iterator{

    private LinkedList<Node<E,F>> children;
    private Node parent;
    private E myObject;
    private F myObject1;
    private LinkedList<Node<E,F>> elements;
    private Iterator iter;

    /**
     * Default constructor
     * @param o object of type E, first object, use <code> getObject() </code> to return it.
     * @param o1 object of type F, second object, use <code> getObject1() </code> to return it.
     */
    public Node(E o, F o1)
    {
        myObject = o;
        myObject1 = o1;
        children = new LinkedList<Node<E,F>>();
        elements = new LinkedList<Node<E,F>>();
    }
    /**
     *Returns primary object in Node
     * @return Returns primary object in Node
     */
    public E getObject()
    {
        return myObject;
    }
    /**
     * Returns secondary object in Node
     * @return Returns secondary object in Node
     */
    public F getObject1()
    {
        return myObject1;
    }
    /**
     * Sets this Nodes parent
     * @param p parent node to be set
     */
    public void setParent(Node<E,F> p)
    {
        parent = p;
    }
    /**
     * Returns the parent of this node
     * @return Parent node
     */
    public Node<E,F> getParent()
    {
        return parent;
    }
    /**
     * Returns if this node has a parent or not.
     * @return boolean for parent.
     */
    public boolean hasParent()
    {
        return parent==null;
    }
    /**
     * Adds a child node to this node
     * Child can be accessed from <code>getChildren()</code> function
     * Also default sets the parent to <code> this </code>
     * @param child Child node
     */
    public void addChild(Node<E,F> child)
    {
        children.add(child);
        child.setParent(this);
    }
    /**
     * Returns a LinkedList of all immediate child nodes
     * Note: To get all child elements, use <code>getAllElements</code>
     * @return LinkedList of immediate children
     */
    public LinkedList<Node<E,F>> getChildren()
    {
        return children;
    }
    /**
     * Returns a linked list of all child, grandchildren, great grandchildren etc.
     * nodes related to this node.
     * Note: This is the same as getAllElementsIn(this)
     * @return All children in this node
     */
    public LinkedList<Node<E,F>> getAllElements()
    {
        LinkedList<Node<E,F>> answer = new LinkedList<Node<E,F>>();
        answer.addAll(getElementsIn(this));
        return answer;
    }
    /**
     * Returns a linked list of all children, grandchildren, great grand children etc.
     * related to this node.
     * @param n root node to get elements of
     * @return linked list of all child nodes
     */
    public static LinkedList getElementsIn(Node n)
    {
        if(n==null) return null;
        LinkedList<Node>answer = new LinkedList<Node>();
        answer.add(n);
        LinkedList<Node> childs = n.getChildren();
        for(Node child : childs)
        {
            answer.addAll(getElementsIn(child));
        }
        return answer;
    }
    /**
     * Returns elements in the specified tier of of this node
     * <code> this </code> is tier 0, followed by tier 1 etc.
     * Returns an empty list if tier>number of tiers
     * @param tier tier number to get
     * @return LinkedList of all elements in tier
     */
    public LinkedList<Node<E,F>> getElementsInTier(int tier)
    {
        LinkedList<Node<E,F>> temp1 = new LinkedList<Node<E,F>>();
        LinkedList<Node<E,F>> temp2 = new LinkedList<Node<E,F>>();
        if(tier==0)
        {
            temp2.add(this);
            return temp2;
        }
        int i = 0;
        for(;i<tier;i++)
        {
            if(i==0)temp1=this.getChildren();
            else if(i % 2 == 0)
            {
                temp1 = new LinkedList<Node<E,F>>();
                for(Node n : temp2)
                {
                    temp1.addAll(n.getChildren());
                }
            }
            else
            {
                temp2 = new LinkedList<Node<E,F>>();
                for(Node n : temp1)
                {
                    temp2.addAll(n.getChildren());
                }
            }
        }
        return i%2==0 ? temp2 : temp1;
    }
    /**
     * Returns the final tier in the children of this node
     * @return LinkedList of nodes in final tier
     */
    public LinkedList<Node<E,F>> getFinalTier()
    {
        int tier = 0;
        while(!getElementsInTier(tier).isEmpty())
        {
            tier++;
        }
        return getElementsInTier(tier-1);
    }
    /**
     * Attempts to help with Java garbage disposal
     * Essentially gets rid of any references to or from this node
     */
    public void destroy()
    {
        parent=null;
        children = null;
        elements = null;
        myObject = null;
        myObject1 = null;
    }
    /**
     * Necessary iterator method for going through elements array
     * @return true if there is a next element in <code>getAllElements()</code>
     */
    public boolean hasNext() {
        if(elements.isEmpty())
        {
            elements = getAllElements();
            iter = elements.listIterator();
        }
        return iter.hasNext();
    }
    /**
     * Necessary iterator method for going through elements in
     * @return returns next element in <code>getAllElements()</code>
     */
    public Object next() {
        if(elements.isEmpty())
        {
            elements = getAllElements();
            iter = elements.listIterator();
        }
        return iter.next();
    }
    /**
     * Removes the previously returned node from <code>getAllElements()</code> List
     * Note: Not properly tested yet
     */
    public void remove() {
        iter.remove();
    }
    /**
     * Returns a string of the <code>toString()</code>
     * @return string
     */
    @Override
    public String toString()
    {
        return "" + myObject + " " + myObject1;
    }
}
