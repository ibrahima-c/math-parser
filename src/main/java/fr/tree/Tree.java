package fr.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;
import fr.exceptions.ParenthesisException;
import fr.node.INode;
import fr.node.Node;
import fr.utils.MathUtils;
import fr.utils.Utils;
import fr.variables.VariableFactory;

public class Tree implements ITree {

    private INode                   root     = null;
    private Map<Character, Integer> argNames = new HashMap<>();

    public Double getValue() throws MathFunctionException, DivisionException {
        return root.getValue();
    }

    public List<INode> convertFormulaToNodes( String expression ) throws ParenthesisException {

        List<INode> nodes = new ArrayList<>();
        boolean isNegative = false;
        expression = "(" + expression + ")";

        // checks if the formula is correct (parenthesis, etc...)
        MathUtils.checkFormula( expression );

        // split the String into an Array of String which contains the
        // parts of the formula
        String[] parts = Utils.explode( expression );

        // creates nodes
        for ( String part : parts ) {
            // possible characters : + - * / ! ( ) ^
            if ( part.matches( "[!^()\\-+*/]" ) ) {
                // case "(- ...)"
                if ( "-".equals( part ) && "(".equals( nodes.get( nodes.size() - 1 ).toString() ) )
                    isNegative = true;
                else
                    nodes.add( new Node( part.charAt( 0 ) ) );

            }
            // for numbers
            else if ( part.matches( "[+-]?([0-9]*[.])?[0-9]+" ) ) {
                Double value = Double.parseDouble( part );
                // case "(-number ...)"
                if ( isNegative ) {
                    nodes.add( new Node( -value ) );
                    isNegative = false;
                } else
                    nodes.add( new Node( value ) );
            }
            // for variables
            else if ( part.matches( "[a-zA-Z]" ) ) {
                // case "(-var ...)"
                if ( isNegative ) {
                    nodes.add( new Node( VariableFactory.getVariable( part.toLowerCase().charAt( 0 ) ), -1 ) );
                    this.argNames.put( part.toLowerCase().charAt( 0 ), this.argNames.size() + 1 );
                    isNegative = false;
                } else {
                    nodes.add( new Node( VariableFactory.getVariable( part.toLowerCase().charAt( 0 ) ), +1 ) );
                    this.argNames.put( part.toLowerCase().charAt( 0 ), this.argNames.size() + 1 );
                }
            }
            // for pi (3.14 ...)
            else if ( "pi".equals( part.toLowerCase() ) ) {
                // case "(-pi ...)"
                if ( isNegative ) {
                    nodes.add( new Node( -Math.PI ) );
                    isNegative = false;
                } else {
                    nodes.add( new Node( Math.PI ) );
                }
            }
            // for mathematical functions
            else {
                // case "(-function ...)"
                if ( isNegative ) {
                    nodes.add( new Node( part, -1 ) );
                    isNegative = false;
                } else {
                    nodes.add( new Node( part, 1 ) );
                }
            }
        }
        return nodes;

    }

    public void buildTreeFromNodes( List<INode> nodes ) throws ParenthesisException {

        INode hostNode, node;

        // set first node in list as root
        this.root = nodes.get( 0 );

        // and then add following nodes to build the tree
        for ( int i = 1; i < nodes.size(); i++ ) {

            // find the node at the bottom right hand
            node = this.findRightSideFreeNode( root );

            // find where to attach the new node
            // don't forget to take care to the particular case of "("
            if ( !"(".equals( nodes.get( i ).toString() ) ) {
                // climb the tree to find the host node
                hostNode = this.climbTreeBeginningFrom( node, nodes.get( i ) );
                // attach the new node
                this.attach( hostNode, nodes.get( i ) );
            } else {
                // don't need to climb the tree if "("
                node.setRightChild( nodes.get( i ) );
            }
            // find new root
            root = this.findTreeRoot( node );
        }

    }

    /**
     * attaches a node to a tree
     * 
     * @param hostNode
     * @param node
     * @throws ParenthesisException
     */
    private void attach( INode hostNode, INode node ) throws ParenthesisException {
        // case hostNode="(" and node=")"
        if ( ( hostNode.getPrecedence() == 1 && node.getPrecedence() == 1 ) ) {
            // case "(" has no parent
            if ( hostNode.getParent() != null ) {
                try {
                    // NullPointerException if nothing between parenthesis
                    // because hostNode.getRightChild() = null
                    hostNode.getParent().setRightChild( hostNode.getRightChild() );
                } catch ( NullPointerException e ) {
                    throw new ParenthesisException(
                            "Nothing is found between parenthesis. Please check your math expression..." );
                }

            }
            // case "(" has a parent
            else {
                try {
                    // NullPointerException if nothing between parenthesis
                    // because hostNode.getRightChild() = null
                    hostNode.getRightChild().setParent( null );
                } catch ( NullPointerException e ) {
                    throw new ParenthesisException(
                            "Nothing is found between parenthesis. Please check your math expression..." );
                }
            }
        } else {
            if ( hostNode.getRightChild() == null && hostNode.getParent() != null ) {
                hostNode.setRightChild( node );
            } else if ( hostNode.getParent() == null ) {
                if ( hostNode.getRightChild() == null ) {
                    if ( ( hostNode.getPrecedence() < node.getPrecedence() ) ) {
                        hostNode.setRightChild( node );
                    } else {
                        node.setLeftChild( hostNode );
                    }
                } else {
                    if ( ( hostNode.getPrecedence() < node.getPrecedence() )
                            || ( hostNode.getPrecedence() == 5 && node.getPrecedence() == 5 ) ) {
                        node.setLeftChild( hostNode.getRightChild() );
                        hostNode.setRightChild( node );
                    } else {
                        node.setLeftChild( hostNode );
                    }

                }
            } else if ( hostNode.getRightChild() != null && hostNode.getParent() != null ) {
                node.setLeftChild( hostNode.getRightChild() );
                hostNode.setRightChild( node );
            }
        }
    }

    // climb the tree and finds where to attach the new node
    private INode climbTreeBeginningFrom( INode currentNode, INode nodeToBeAttached ) {
        INode n = null;

        if ( ( nodeToBeAttached.getPrecedence() > currentNode.getPrecedence() )
                || ( nodeToBeAttached.getPrecedence() == 5 && currentNode.getPrecedence() == 5 )
                || ( nodeToBeAttached.getPrecedence() == 1 && currentNode.getPrecedence() == 1 )
                || currentNode.getParent() == null )
            n = currentNode;
        else {
            n = climbTreeBeginningFrom( currentNode.getParent(), nodeToBeAttached );
            // when closing parentheses
            // set the precedence of all elements between parentheses to 100
            if ( nodeToBeAttached.getPrecedence() == 1 )
                currentNode.modifyPrecedence( 100 );
        }
        return n;
    }

    private INode findTreeRoot( INode node ) {
        INode n = null;
        if ( node.getParent() == null ) {
            n = node;
        } else {
            n = this.findTreeRoot( node.getParent() );
        }
        return n;
    }

    private INode findRightSideFreeNode( INode node ) {
        INode n = null;
        if ( node.getRightChild() == null )
            n = node;
        else
            n = this.findRightSideFreeNode( node.getRightChild() );
        return n;
    }

    public INode getRoot() {
        return this.root;
    }

    public Map<Character, Integer> getArgNames() {
        return argNames;
    }

    public void printTree() {
        System.out.println( "*****************" );
        System.out.println( "*  PRINT TREE   *" );
        System.out.println( "*****************" );

        if ( this.root != null ) {

            String str = "root = " + this.root.toString() + "\n";

            Map<Integer, List<Object>> mapListNodes = new HashMap<>();
            List<Object> listNodes = new ArrayList<>();
            List<Object> temp = new ArrayList<>();

            {
                listNodes.add( root.getLeftChild() );
                listNodes.add( root.getRightChild() );
                mapListNodes.put( 1, new ArrayList<>( listNodes ) );

                str += "[" + root + " -> " + root.getLeftChild() + " ; " + root + " -> " + root.getRightChild() + "] ";
                str += "\n";
                listNodes.clear();
            }

            int i = 1;
            boolean print = true;

            do {
                temp = (ArrayList<Object>) mapListNodes.get( i );
                if ( Utils.notNullInList( temp ) ) {
                    for ( Object o : temp ) {
                        if ( o instanceof Node ) {
                            listNodes.add( ( (Node) o ).getLeftChild() );
                            listNodes.add( ( (Node) o ).getRightChild() );
                            mapListNodes.put( i + 1, new ArrayList<>( listNodes ) );
                            str += "[" + ( (Node) o ) + " -> " + ( (Node) o ).getLeftChild() + " ; " + ( (Node) o )
                                    + " -> "
                                    + ( (Node) o ).getRightChild()
                                    + "] ";
                        }
                    }
                    str += "\n";
                    listNodes.clear();
                    i++;
                } else {
                    print = false;
                }
            } while ( print );

            System.out.print( str );

        } else {
            System.out.println( "no tree found !" );
        }
        System.out.println( "****** END ******" );
    }

    public static void main( String[] args ) {

        ITree tree = new Tree();

        // Test construction arbre
        System.out.println( "\nTest construction arbre" );
        // String formula = "cos(pi)*(2+5)*(1+1)*sin(-pi/2)";
        // String expression = "1/0";
        // String expression = "4^3^2*5";
        String expression = "((-1)*(4!))";
        // VariableFactory.getVariable( 'x' ).setValue( 1.0 );

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            System.out.println( tree.getValue() );
            tree.printTree();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

    }

}
