package fr.node;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;

/**
 * <p>
 * INode interface for use in a mathematical expression parser. The parsing
 * algorithm is based on the precedence order. <br>
 * <a href=
 * "http://rhyscitlema.com/algorithms/expression-parsing-algorithm/">expression
 * parsing algorithm</a>
 * 
 * <p>
 * VALUES OF PRECEDENCE : <br>
 * ( and ) -> 1 <br>
 * + and - -> 2<br>
 * * and / -> 4<br>
 * ^ -> 5<br>
 * factorial ! -> 6<br>
 * Functions and numbers -> 10<br>
 * 
 * @author Ibrahima
 * @version 2018/12/30
 */

public interface INode {

    /**
     * 
     * @return The value of the node
     * @throws MathFunctionException
     * @throws DivisionException
     */
    Double getValue() throws MathFunctionException, DivisionException;

    /**
     * 
     * @return The right hand child of the node
     */
    INode getRightChild();

    /**
     * 
     * @return The left hand child of the node
     */
    INode getLeftChild();

    /**
     * 
     * @return The precedence value of the node
     * 
     */
    Integer getPrecedence();

    /**
     * 
     * @return The parent of the node
     */
    INode getParent();

    /**
     * Sets the right hand child of the node
     * 
     * @param node
     */

    void setRightChild( INode node );

    /**
     * Sets the left hand child of the node
     * 
     * @param node
     */

    void setLeftChild( INode node );

    /**
     * Sets the parent of the node
     * 
     * @param node
     */

    void setParent( INode node );

    /**
     * This method is used to modify the precedence of elements between
     * parentheses in the Tree class: after the parenthesis if closed, the
     * precedence of the elements is set to 100 (or any value above 10)
     * 
     * @param precedence
     */
    void modifyPrecedence( Integer precedence );

}
