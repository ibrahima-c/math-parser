package fr.node;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;
import fr.utils.MathUtils;
import fr.variables.IVariable;

/**
 * 
 * Node class for use in a mathematical expression parser
 * http://rhyscitlema.com/algorithms/expression-parsing-algorithm/
 * 
 * @author Ibrahima
 * @version 2018/12/14
 */

public class Node implements INode {

    private Character operator;
    private Double    value;
    private String    function;
    private IVariable variable;
    private Integer   varSign;
    private Integer   funcSign;

    private INode     leftChild;
    private INode     rightChild;
    private INode     parent;

    private boolean   isOperator    = false;
    private boolean   isFunction    = false;
    private boolean   isNumber      = false;
    private boolean   isVariable    = false;
    private boolean   hasLeftChild  = false;
    private boolean   hasRightChild = false;
    private boolean   hasParent     = false;

    private Integer   precedence;

    public Node( Character operator ) {
        this.operator = operator;
        this.isOperator = true;
        this.setPrecedence();
    }

    public Node( Double value ) {
        this.value = value;
        this.isNumber = true;
        this.setPrecedence();
    }

    public Node( IVariable variable, Integer varSign ) {
        this.variable = variable;
        this.varSign = varSign;
        this.isVariable = true;
        this.setPrecedence();
    }

    public Node( String function, Integer funcSign ) {
        this.function = function;
        this.funcSign = funcSign;
        this.isFunction = true;
        this.setPrecedence();
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void modifyPrecedence( Integer precedence ) {
        this.precedence = precedence;
    }

    private void setPrecedence() {
        if ( this.isOperator ) {
            if ( this.operator == '(' || this.operator == ')' )
                this.precedence = 1;
            if ( this.operator == '+' || this.operator == '-' )
                this.precedence = 2;
            if ( this.operator == '*' || this.operator == '/' )
                this.precedence = 4;
            if ( this.operator == '^' )
                this.precedence = 5;
            if ( this.operator == '!' )
                this.precedence = 6;
        } else if ( this.isNumber || this.isVariable || this.isFunction ) {
            this.precedence = 10;
        }
    }

    public void setLeftChild( INode leftChild ) {
        this.leftChild = leftChild;
        leftChild.setParent( this );
        this.hasLeftChild = true;
    }

    public void setRightChild( INode rightChild ) {
        this.rightChild = rightChild;
        rightChild.setParent( this );
        this.hasRightChild = true;
    }

    public void setParent( INode parent ) {
        this.parent = parent;
        this.hasParent = true;
    }

    private void setValue() throws MathFunctionException, DivisionException {
        if ( this.isOperator ) {
            this.doOperation();
        } else if ( this.isVariable ) {
            this.value = this.varSign * this.variable.getValue();
        } else if ( this.isFunction ) {
            if ( this.hasLeftChild && this.hasRightChild ) {
                // math functions with two arguments (atan2, ...)
                try {
                    Double[] args = { leftChild.getValue(), rightChild.getValue() };
                    this.value = this.funcSign * MathUtils.evalMathFunction( this.function, args );
                } catch ( NoSuchMethodException e ) {
                    throw new MathFunctionException( "The function '" + this.function + "' is not found" );
                }
            } else if ( this.hasRightChild ) {
                // math functions with one argument (cos, ...)
                try {
                    Double[] args = { rightChild.getValue() };
                    this.value = this.funcSign * MathUtils.evalMathFunction( this.function, args );
                } catch ( NoSuchMethodException e ) {
                    throw new MathFunctionException( "The function '" + this.function + "' is not found" );
                }
            } else
                throw new MathFunctionException( "The function '" + this.function + "' has no argument(s)" );
        }
    }

    private void doOperation() throws MathFunctionException, DivisionException {
        if ( this.operator == '!' && this.hasLeftChild ) {
            this.value = MathUtils.factorial( (int) Math.round( leftChild.getValue() ) );
        } else if ( this.operator == '!' && !this.hasLeftChild ) {
            throw new MathFunctionException( "The factorial operator ! has no argument" );
        } else if ( this.hasLeftChild && this.hasRightChild ) {
            switch ( this.operator ) {
            case '-':
                this.value = this.leftChild.getValue() - this.rightChild.getValue();
                break;
            case '+':
                this.value = this.leftChild.getValue() + this.rightChild.getValue();
                break;
            case '*':

                this.value = this.leftChild.getValue() * this.rightChild.getValue();
                break;
            case '/':
                if ( this.rightChild.getValue() == 0.0 )
                    throw new DivisionException( "/ by zero" );
                this.value = this.leftChild.getValue() / this.rightChild.getValue();
                break;
            case '^':
                this.value = Math.pow( this.leftChild.getValue(), this.rightChild.getValue() );
                break;
            }
        }
    }

    public Double getValue() throws MathFunctionException, DivisionException {
        this.setValue();
        return this.value;
    }

    public INode getRightChild() {
        return rightChild;
    }

    public INode getLeftChild() {
        return leftChild;
    }

    public INode getParent() {
        return this.parent;
    }

    public IVariable getVariable() {
        return variable;
    }

    public static void printNode( INode node ) {
        if ( node != null ) {
            System.out.print( " " + node );
            printNode( node.getLeftChild() );
            printNode( node.getRightChild() );
        } else {
            System.out.println();
        }
    }

    @Override
    public String toString() {
        String str = null;

        if ( this.isOperator )
            str = this.operator + "";
        else if ( this.isNumber )
            str = this.value + "";
        else if ( this.isFunction )
            str = ( this.funcSign > 0 ? "" : "-" ) + this.function + "";
        else
            str = "var:" + ( this.varSign > 0 ? "" : "-" ) + variable.getValue();

        return str;
    }

}
