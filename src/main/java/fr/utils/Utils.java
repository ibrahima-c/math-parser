package fr.utils;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static String[] explode( String formula ) {
        // removes spaces and then splits
        return formula.replaceAll( "\\s", "" ).split( "(?<=[!^()\\-+*/])|(?=[!^()\\-+*/])" );
    }

    public static boolean notNullInList( List<Object> objects ) {

        for ( Object o : objects ) {
            if ( o != null )
                return true;
        }

        return false;
    }

    public static void main( String[] args ) {

        String formula = "B * sin( 2 * pi * f * t )  +cos(2*pi*f)-6 ";
        String[] parts = Utils.explode( formula );
        System.out.println( Arrays.toString( parts ) );

    }

}
