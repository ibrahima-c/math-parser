package fr.node;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

import fr.exceptions.DivisionException;
import fr.exceptions.MathFunctionException;
import fr.variables.IVariable;
import fr.variables.Variable;

public class INodeTest {

    private static List<INode> nodes    = new ArrayList<>();
    private static IVariable   variable = new Variable( 1.0 );

    @Rule
    public ExpectedException   thrown   = ExpectedException.none();

    @BeforeClass
    public static void init() {

        nodes.add( new Node( '(' ) ); // 0
        nodes.add( new Node( ')' ) ); // 1
        nodes.add( new Node( '+' ) ); // 2
        nodes.add( new Node( '-' ) ); // 3
        nodes.add( new Node( '*' ) ); // 4
        nodes.add( new Node( '/' ) ); // 5
        nodes.add( new Node( '^' ) ); // 6
        nodes.add( new Node( '!' ) ); // 7
        nodes.add( new Node( 2.0 ) ); // 8
        nodes.add( new Node( variable, 1 ) ); // 9
        nodes.add( new Node( variable, -1 ) ); // 10
        nodes.add( new Node( "cos", 1 ) ); // 11
        nodes.add( new Node( "cos", -1 ) ); // 12
        nodes.add( new Node( "coss", 1 ) ); // 13

        // 1+2
        nodes.get( 2 ).setLeftChild( new Node( 1.0 ) );
        nodes.get( 2 ).setRightChild( new Node( 2.0 ) );

        // 1-2
        nodes.get( 3 ).setLeftChild( new Node( 1.0 ) );
        nodes.get( 3 ).setRightChild( new Node( 2.0 ) );

        // 1*2
        nodes.get( 4 ).setLeftChild( new Node( 1.0 ) );
        nodes.get( 4 ).setRightChild( new Node( 2.0 ) );

        // 1/2
        nodes.get( 5 ).setLeftChild( new Node( 1.0 ) );
        nodes.get( 5 ).setRightChild( new Node( 2.0 ) );

        // 2^2
        nodes.get( 6 ).setLeftChild( new Node( 2.0 ) );
        nodes.get( 6 ).setRightChild( new Node( 2.0 ) );

        // 3!
        nodes.get( 7 ).setLeftChild( new Node( 3.0 ) );

        // cos(pi)
        nodes.get( 11 ).setRightChild( new Node( Math.PI ) );

        // -cos(pi)
        nodes.get( 12 ).setRightChild( new Node( Math.PI ) );
    }

    @Test
    public void testGetValue() throws MathFunctionException, DivisionException {
        assertTrue( nodes.get( 2 ).getValue() == 3.0 );
        assertTrue( nodes.get( 3 ).getValue() == -1.0 );
        assertTrue( nodes.get( 4 ).getValue() == 2.0 );
        assertTrue( nodes.get( 5 ).getValue() == 0.5 );
        assertTrue( nodes.get( 6 ).getValue() == 4.0 );
        assertTrue( nodes.get( 7 ).getValue() == 6.0 );
        assertTrue( nodes.get( 8 ).getValue() == 2.0 );
        assertTrue( nodes.get( 9 ).getValue() == 1.0 );
        assertTrue( nodes.get( 10 ).getValue() == -1.0 );
        assertTrue( nodes.get( 11 ).getValue() == -1.0 );
        assertTrue( nodes.get( 12 ).getValue() == 1.0 );

    }

    @Test
    public void divisionByZero() throws MathFunctionException, DivisionException {
        nodes.get( 5 ).setRightChild( new Node( 0.0 ) );
        thrown.expect( DivisionException.class );
        thrown.expectMessage( JUnitMatchers.containsString( "/ by zero" ) );
        nodes.get( 5 ).getValue();
    }

    @Test
    public void noArgFactorial() throws MathFunctionException, DivisionException {
        thrown.expect( MathFunctionException.class );
        thrown.expectMessage( JUnitMatchers.containsString( "The factorial operator ! has no argument" ) );
        ( new Node( '!' ) ).getValue();
    }

    @Test
    public void functionNotFound() throws MathFunctionException, DivisionException {
        nodes.get( 13 ).setRightChild( new Node( Math.PI ) );
        thrown.expect( MathFunctionException.class );
        thrown.expectMessage( JUnitMatchers.containsString( "The function 'coss' is not found" ) );
        nodes.get( 13 ).getValue();
    }

    @Test
    public void noArgFunction() throws MathFunctionException, DivisionException {
        thrown.expect( MathFunctionException.class );
        thrown.expectMessage( JUnitMatchers.containsString( "The function 'cos' has no argument(s)" ) );
        ( new Node( "cos", 1 ) ).getValue();
    }

    @Test
    public void testGetRightChild() {
        assertNotNull( nodes.get( 2 ).getRightChild() );
        assertTrue( "2.0".equals( nodes.get( 2 ).getRightChild().toString() ) );

    }

    @Test
    public void testGetLeftChild() {
        assertNotNull( nodes.get( 2 ).getLeftChild() );
        assertTrue( "1.0".equals( nodes.get( 2 ).getLeftChild().toString() ) );
    }

    @Test
    public void testGetPrecedence() {

        assertTrue( nodes.get( 0 ).getPrecedence() == 1 );
        assertTrue( nodes.get( 1 ).getPrecedence() == 1 );
        assertTrue( nodes.get( 2 ).getPrecedence() == 2 );
        assertTrue( nodes.get( 3 ).getPrecedence() == 2 );
        assertTrue( nodes.get( 4 ).getPrecedence() == 4 );
        assertTrue( nodes.get( 5 ).getPrecedence() == 4 );
        assertTrue( nodes.get( 6 ).getPrecedence() == 5 );
        assertTrue( nodes.get( 7 ).getPrecedence() == 6 );
        assertTrue( nodes.get( 8 ).getPrecedence() == 10 );
        assertTrue( nodes.get( 9 ).getPrecedence() == 10 );
        assertTrue( nodes.get( 11 ).getPrecedence() == 10 );
    }

}
