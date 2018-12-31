package fr.test;

import java.util.List;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;
import fr.exceptions.ParenthesisException;
import fr.node.INode;
import fr.tree.ITree;
import fr.tree.Tree;
import fr.variables.VariableFactory;

public class TestParser {
    public static void main( String[] args ) {

        String expression = "cos(pi*x)";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            VariableFactory.getVariable( 'x' ).setValue( 1.0 );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );

    }
}
