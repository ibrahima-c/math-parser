package fr.variables;

import java.util.HashMap;
import java.util.Map;

public class VariableFactory {

    private Map<Character, IVariable> mapVar   = new HashMap<>();
    private static VariableFactory    instance = null;

    private VariableFactory() {
        mapVar.put( 'a', new Variable() );
        mapVar.put( 'b', new Variable() );
        mapVar.put( 'c', new Variable() );
        mapVar.put( 'd', new Variable() );
        mapVar.put( 'e', new Variable() );
        mapVar.put( 'f', new Variable() );
        mapVar.put( 'g', new Variable() );
        mapVar.put( 'h', new Variable() );
        mapVar.put( 'i', new Variable() );
        mapVar.put( 'j', new Variable() );
        mapVar.put( 'k', new Variable() );
        mapVar.put( 'l', new Variable() );
        mapVar.put( 'm', new Variable() );
        mapVar.put( 'n', new Variable() );
        mapVar.put( 'o', new Variable() );
        mapVar.put( 'p', new Variable() );
        mapVar.put( 'q', new Variable() );
        mapVar.put( 'r', new Variable() );
        mapVar.put( 's', new Variable() );
        mapVar.put( 't', new Variable() );
        mapVar.put( 'u', new Variable() );
        mapVar.put( 'v', new Variable() );
        mapVar.put( 'w', new Variable() );
        mapVar.put( 'x', new Variable() );
        mapVar.put( 'y', new Variable() );
        mapVar.put( 'z', new Variable() );
    }

    private Map<Character, IVariable> getMap() {
        return this.mapVar;
    }

    public static IVariable getVariable( Character nomVar ) {
        if ( instance == null ) {
            instance = new VariableFactory();
        }
        return instance.getMap().get( nomVar );
    }

}
