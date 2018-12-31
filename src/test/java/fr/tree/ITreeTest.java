package fr.tree;

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
import fr.exceptions.ParenthesisException;
import fr.node.INode;

public class ITreeTest {

    public static List<String>      expressions = new ArrayList<>();
    public static List<List<INode>> nodes       = new ArrayList<>();
    public static List<ITree>       trees       = new ArrayList<>();

    @Rule
    public ExpectedException        thrown      = ExpectedException.none();

    @BeforeClass
    public static void init() {

        expressions.add( "5-6/2+3*4" );
        expressions.add( "4^3^2*5" );
        expressions.add( "4*(3-2)+5" );
        expressions.add( "4*cos(pi*(0.5+0.5))" );
        expressions.add( "((-1)*(4!))" );
        expressions.add( "(2+3" );

        trees.add( new Tree() );
        trees.add( new Tree() );
        trees.add( new Tree() );
        trees.add( new Tree() );
        trees.add( new Tree() );
        trees.add( new Tree() );

        nodes.add( new ArrayList<>() );
        nodes.add( new ArrayList<>() );
        nodes.add( new ArrayList<>() );
        nodes.add( new ArrayList<>() );
        nodes.add( new ArrayList<>() );
        nodes.add( new ArrayList<>() );

    }

    @Test
    public void testConvertFormulaToNodes() throws ParenthesisException {
        assertTrue( trees.get( 0 ).convertFormulaToNodes( expressions.get( 0 ) ).size() == 11 );
        assertTrue( trees.get( 1 ).convertFormulaToNodes( expressions.get( 1 ) ).size() == 9 );
        assertTrue( trees.get( 2 ).convertFormulaToNodes( expressions.get( 2 ) ).size() == 11 );
        assertTrue( trees.get( 3 ).convertFormulaToNodes( expressions.get( 3 ) ).size() == 14 );
        assertTrue( trees.get( 4 ).convertFormulaToNodes( expressions.get( 4 ) ).size() == 12 );

    }

    @Test
    public void parenthesesException() throws ParenthesisException {
        thrown.expect( ParenthesisException.class );
        thrown.expectMessage( JUnitMatchers
                .containsString( "Some parenthesis are not opened or closed. Please check your math expression..." ) );
        trees.get( 5 ).convertFormulaToNodes( expressions.get( 5 ) );
    }

    @Test
    public void testBuildTreeFromNodes() throws ParenthesisException {

        nodes.add( 0, trees.get( 0 ).convertFormulaToNodes( expressions.get( 0 ) ) );
        nodes.add( 1, trees.get( 1 ).convertFormulaToNodes( expressions.get( 1 ) ) );
        nodes.add( 2, trees.get( 2 ).convertFormulaToNodes( expressions.get( 2 ) ) );
        nodes.add( 3, trees.get( 3 ).convertFormulaToNodes( expressions.get( 3 ) ) );
        nodes.add( 4, trees.get( 4 ).convertFormulaToNodes( expressions.get( 4 ) ) );

        trees.get( 0 ).buildTreeFromNodes( nodes.get( 0 ) );
        trees.get( 1 ).buildTreeFromNodes( nodes.get( 1 ) );
        trees.get( 2 ).buildTreeFromNodes( nodes.get( 2 ) );
        trees.get( 3 ).buildTreeFromNodes( nodes.get( 3 ) );
        trees.get( 4 ).buildTreeFromNodes( nodes.get( 4 ) );

        assertNotNull( trees.get( 0 ).getRoot() );
        assertTrue( "+".equals( trees.get( 0 ).getRoot().toString() ) );

        assertNotNull( trees.get( 1 ).getRoot() );
        assertTrue( "*".equals( trees.get( 1 ).getRoot().toString() ) );

        assertNotNull( trees.get( 2 ).getRoot() );
        assertTrue( "+".equals( trees.get( 2 ).getRoot().toString() ) );

        assertNotNull( trees.get( 3 ).getRoot() );
        assertTrue( "*".equals( trees.get( 3 ).getRoot().toString() ) );

        assertNotNull( trees.get( 4 ).getRoot() );
        assertTrue( "*".equals( trees.get( 4 ).getRoot().toString() ) );

    }

    @Test
    public void testGetValue() throws MathFunctionException, DivisionException, ParenthesisException {

        nodes.add( 0, trees.get( 0 ).convertFormulaToNodes( expressions.get( 0 ) ) );
        nodes.add( 1, trees.get( 1 ).convertFormulaToNodes( expressions.get( 1 ) ) );
        nodes.add( 2, trees.get( 2 ).convertFormulaToNodes( expressions.get( 2 ) ) );
        nodes.add( 3, trees.get( 3 ).convertFormulaToNodes( expressions.get( 3 ) ) );
        nodes.add( 4, trees.get( 4 ).convertFormulaToNodes( expressions.get( 4 ) ) );

        trees.get( 0 ).buildTreeFromNodes( nodes.get( 0 ) );
        trees.get( 1 ).buildTreeFromNodes( nodes.get( 1 ) );
        trees.get( 2 ).buildTreeFromNodes( nodes.get( 2 ) );
        trees.get( 3 ).buildTreeFromNodes( nodes.get( 3 ) );
        trees.get( 4 ).buildTreeFromNodes( nodes.get( 4 ) );

        assertTrue( trees.get( 0 ).getValue() == 14.0 );
        assertTrue( trees.get( 1 ).getValue() == 1310720.0 );
        assertTrue( trees.get( 2 ).getValue() == 9.0 );
        assertTrue( trees.get( 3 ).getValue() == -4.0 );
        assertTrue( trees.get( 4 ).getValue() == -24.0 );
    }

}
