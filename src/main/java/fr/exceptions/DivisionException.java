package fr.exceptions;

public class DivisionException extends Exception {

    private static final long serialVersionUID = -1238196504178385520L;

    public DivisionException() {
        super();
    }

    public DivisionException( String message ) {
        super( message );
    }

}
