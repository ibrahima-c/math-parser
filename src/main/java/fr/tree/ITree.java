package fr.tree;

import java.util.List;
import java.util.Map;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;
import fr.exceptions.ParenthesisException;
import fr.node.INode;

/**
 * <p>
 * ITree interface for use in a mathematical expression parser. The parsing
 * algorithm is based on the precedence order. <br>
 * <a href=
 * "http://rhyscitlema.com/algorithms/expression-parsing-algorithm/">expression
 * parsing algorithm</a>
 * 
 * 
 * @author Ibrahima
 * @version 2018/12/30
 */

public interface ITree {

    /**
     * This method does following things : <br>
     * - check the correctness of the expression <br>
     * - converts the expression into a list of nodes
     * 
     * @param
     * @return List of INode
     * @throws ParenthesisException
     *             if the parentheses are not correctly opened or closed
     */
    List<INode> convertFormulaToNodes( String expression ) throws ParenthesisException;

    /**
     * Builds a tree from a list of nodes given as argument
     * 
     * @param
     * @throws ParenthesisException
     *             if no elements are found between parentheses
     */
    void buildTreeFromNodes( List<INode> nodes ) throws ParenthesisException;

    /**
     * Prints all the nodes of the tree
     */
    void printTree();

    /**
     * 
     * @return the value of the tree (the value of the root)
     * @throws MathFunctionException
     *             if the mathematical function is not found in java.lang.Math
     * 
     * @see java.lang.Math
     * 
     * @throws DivisionException
     */
    Double getValue() throws MathFunctionException, DivisionException;

    /**
     * 
     * @return the root of the tree
     */
    INode getRoot();

    /**
     * 
     * @return the names of the used arguments (for example 'x', 'y', etc )
     */
    Map<Character, Integer> getArgNames();

}
