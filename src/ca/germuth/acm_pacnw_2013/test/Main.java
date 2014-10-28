package ca.germuth.acm_pacnw_2013.test;

import java.io.*;
import java.util.*;

/**
 * Solution to Count your Cousins
 * 
 * @author vanb
 */
class Main
{
    public Scanner sc;
    public PrintStream ps;
    
    /**
     * A Node of the tree.
     * records the level, the number of kids, and a link to the parent node.
     * 
     * @author vanb
     */
    public class Node
    {
        public int kids, grandkids;
        public Node parent;
       
        /**
         * Create a Node, specifying its parent.
         * @param parent The Parent of this Node
         */
        public Node( Node parent )
        {            
            // We don't know about any kids yet.
            kids = grandkids = 0;
            
            // This is pretty self-explanatory.
            this.parent = parent;
        }
    }
        
    /**
     * Driver.
     * @throws Exception
     */
    public void doit() throws Exception
    {
        sc = new Scanner( System.in ); //new File( "cousins.judge" ) );
        ps = System.out; //new PrintStream( new FileOutputStream( "cousins.solution" ) );
        
        // We'll use this to match up node numbers with Node objects.
        HashMap<Integer,Node> nodes = new HashMap<Integer,Node>();
        
        // We'll stick all of the nodes into an array, sequentially
        Node tree[] = new Node[1000];

        for(;;)
        {
            int n = sc.nextInt();
            int k = sc.nextInt();
            if( n==0 ) break;
            
            // Start with a fresh tree
            Arrays.fill( tree, null );
            
            // The first one is the root.
            int root = sc.nextInt();
            tree[0] = new Node(null);
            nodes.clear();
            nodes.put( root, tree[0] );
            
            // The root is the first parent. We'll also need to know the previous node number.
            int parent = -1;
            int previous = -1;
            for( int i=1; i<n; i++ )
            {
                // Get the next node number. If it's not sequential (i.e. one more than the previous),
                // then we've moved on to the next node as a parent.
                int nodenum = sc.nextInt();
                if( nodenum>previous+1 ) ++parent;
                
                // Create the node, map its node number to it
                tree[i] = new Node( tree[parent] );
                nodes.put( nodenum, tree[i] );
                
                // The parent now has one more kid
                tree[parent].kids++;

                // And the grandparent has one more grandkid
                if( tree[parent].parent!=null ) tree[parent].parent.grandkids++;
                               
                // This is now the previous node number
                previous = nodenum;
            }
            
            // Find the target node
            Node node = nodes.get( k );
            
            // if it isn't there, the judge data is bad!!
            if( node==null )
            {
                System.err.println( "PANIC!! Node " + k + " isn't in the tree! " + nodes );
            }
            else if( node.parent==null || node.parent.parent==null )
            {
                // Parent is null? Grandparent is null?
                // Then this is the root, or a child of the root, so it has no cousins.
                ps.println(0);
            }
            else 
            {
                // The number of cousins is the number of nodes which share a grandparent, but not a parent.
                ps.println( node.parent.parent.grandkids - node.parent.kids );
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        new Main().doit();        
    }   
}