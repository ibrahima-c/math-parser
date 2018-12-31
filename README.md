# Mathematical expression parser


This project is a Java implementation of the [precedence climbing algorithm](http://rhyscitlema.com/algorithms/expression-parsing-algorithm/) in order to parse mathematical expressions. It is used in the following projects:

- [Mathematical expression calculator](https://github.com/ibrahima-c/math-calculator)
- [Mathematical expression plotter](https://github.com/ibrahima-c/math-plotter)


### Syntax


       

- Calculation of _5 - 6/2 + 3*4_

```java
        String expression = "5-6/2+3*4";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );
```

- Calculation of _4*(3-2)+5_

```java
        String expression = "4*(3-2)+5";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );
```

- Calculation of _4*_ _cos(pi*(0.5+0.5))_

```java
        String expression = "4*cos(pi*(0.5+0.5))";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );
```

- Calculation of _-(4!)_

```java
        String expression = "(-1)*(4!)";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );
```

- Calculation of _cos(pi*x)_

```java
        String expression = "cos(pi*x)";
        ITree tree = new Tree();
        Double result = null;

        try {
            List<INode> nodes = tree.convertFormulaToNodes( expression );
            tree.buildTreeFromNodes( nodes );
            VariableFactory.getVariable( 'x' ).setValue( 1.0 );
            result = tree.getValue();
        } catch ( ParenthesisException | MathFunctionException | DivisionException e ) {
            System.out.println( e.getMessage() );
        }

        System.out.println( "The result is " + result );
```

### Supported basic mathematical functions

This parser uses the Java reflection ability and the [java.lang.Math class](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html) in order to instantiate the desired mathematical method. The version 0.2 of this parser supports methods with only one argument of type double. Here is the list of some useful mathematical functions that are recognized in version 0.2 of this parser:

| Mathematical functions | Description |
| ---------------------- | ----------- |
| abs(_x_) | The absolute value of *x* |
| acos(_x_) | The arc cosine of _x_ |
| asin(_x_) | The arc sine of _x_ |
| atan(_x_) | The arc tangent of _x_ |
| cbrt(_x_) | The cube root of _x_ |
| ceil(_x_) | The smallest integer that is greater than or equal to the argument _x_|
| cos(_x_) | The trigonometric cosine of an angle _x_ given in radians|
| cosh(_x_) | The hyperbolic cosine of _x_|
| exp(_x_) | The Euler's number _e_ raised to the power of _x_|
| expml(_x_) | exp(_x_) - 1|
| floor(_x_) | The largest integer that is less than or equal to the argument _x_|
| log(_x_) | The natural logarithm (base _e_) of _x_|
| log10(_x_) | The base 10 logarithm of _x_|
| log1p(_x_) | The natural logarithm of the sum of _x_ and 1|
| signum(_x_) | The signum function of _x_; zero if _x_ = 0, 1.0 if _x_ is greater than zero, -1.0 if _x_ is less than zero|
| sin(_x_) | The trigonometric sine of an angle _x_ given in radians|
| sinh(_x_) | The hyperbolic sine of _x_|
| sqrt(_x_) | The square root of _x_|
| tan(_x_) | The trigonometric tangent of an angle _x_ given in radians|
| tanh(_x_) | The hyperbolic tangent of _x_|
| toDegrees(_x_) | Converts an angle measured in radians to an approximately equivalent angle measured in degrees|
| toRadians(_x_) | Converts an angle measured in degrees to an approximately equivalent angle measured in radians|

#### TODO list (for version next to 0.2)

- Implementation of functions with zero agrgument  [ _random_() ... ]
- Implementation of functions with two arguments [ pow(_x_,_y_) ]