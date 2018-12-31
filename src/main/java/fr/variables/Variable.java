package fr.variables;

public class Variable implements IVariable {

    private Double value;

    public Variable() {
    }

    public Variable( Double value ) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue( Double value ) {
        this.value = value;
    }

}
