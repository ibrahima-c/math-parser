package fr.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.exceptions.ParenthesisException;

/**
 * 
 * @author Ibrahima
 *
 */

public class MathUtils {

    /**
     * 
     * checks if the formula is correct (same number of closed and opended
     * parenthesis, ...)
     * 
     * @param formula
     * @throws ParenthesisException
     */
    public static void checkFormula( String formula ) throws ParenthesisException {

        long countOpenParenthesis = formula.chars().filter( ch -> ch == '(' ).count();
        long countCloseParenthesis = formula.chars().filter( ch -> ch == ')' ).count();

        if ( countOpenParenthesis != countCloseParenthesis )
            throw new ParenthesisException(
                    "Some parenthesis are not opened or closed. Please check your math expression..." );
    }

    /**
     * 
     * evaluates de factorial of the given parameter
     * 
     * @param n
     * @return factorial of n
     */
    public static double factorial( int n ) {
        if ( n == 0 )
            return 1;
        else
            return ( n * factorial( n - 1 ) );
    }

    /**
     * 
     * evaluates the math function whoose name is given in parameter by using
     * the java.lang.Math class
     * 
     * @param functionName
     * @param arguments
     * @return
     * @throws NoSuchMethodException
     */
    public static Double evalMathFunction( String functionName, Double[] arguments ) throws NoSuchMethodException {

        Class<?> mathClass;
        Class<?>[] params = new Class<?>[arguments.length];
        Method method;
        Double result = null;

        for ( int i = 0; i < arguments.length; i++ ) {
            params[i] = double.class;
        }

        try {
            mathClass = Class.forName( "java.lang.Math" );
            method = mathClass.getMethod( functionName, params );
            Object mockInstance = null;
            result = (Double) method.invoke( mockInstance, (Object[]) arguments );
        } catch ( ClassNotFoundException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e ) {
            System.out.println( e.getMessage() );
        }
        return result;
    }

}
