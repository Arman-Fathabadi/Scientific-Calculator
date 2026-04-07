public class ParserTest {

    private static int passed = 0;
    private static int failed = 0;
    private static int currTestId = 1;

    public static void main(String[] args) {
        System.out.println("Starting FunctionParser Unit Tests...\n");

        // === CATEGORY 1: Basic Arithmetic (10 tests) ===
        System.out.println("--- Basic Arithmetic ---");
        testEquals("Addition", 5.0, "2 + 3");
        testEquals("Subtraction", -1.0, "2 - 3");
        testEquals("Multiplication", 6.0, "2 * 3");
        testEquals("Division", 2.5, "5 / 2");
        testEquals("Multiple Add/Sub", 10.0, "2 + 3 + 5 - 0");
        testEquals("Decimals Add", 3.14, "1.14 + 2.0");
        testEquals("Decimals Mult", 0.5, "0.25 * 2");
        testEquals("Long string of ops", 10.0, "1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1");
        testEquals("Spaces ignored", 4.0, " 2  +    2 ");
        testEquals("Large numbers", 1000000.0, "500000 + 500000");

        // === CATEGORY 2: Operator Precedence & Associativity (7 tests) ===
        System.out.println("\n--- Operator Precedence & Associativity ---");
        testEquals("PEMDAS 1", 14.0, "2 + 3 * 4");
        testEquals("PEMDAS 2", 20.0, "(2 + 3) * 4");
        testEquals("PEMDAS 3", 14.0, "2 + 4 * 3");
        testEquals("Left Associative Subtraction", 0.0, "5 - 3 - 2"); // Not 5 - (3 - 2) = 4
        testEquals("Left Associative Division", 2.0, "8 / 2 / 2"); // Not 8 / (2 / 2) = 8
        testEquals("Power Precedence", 10.0, "2 + 2^3");
        testEquals("Right Associative Power", 512.0, "2^3^2"); // Right to Left: 2^(3^2)

        // === CATEGORY 3: Implicit Multiplication (12 tests) ===
        // This is a highlight of the current parser logic!
        System.out.println("\n--- Implicit Multiplication ---");
        testXY("Num and Variable", 10.0, "2x", 5, 0);
        testXY("Num and Parens", 8.0, "2(3+1)", 0, 0);
        testXY("Variable and Num", 15.0, "y 3", 0, 5);
        testXY("Two Variables", 12.0, "x y", 3, 4);
        testXY("Variables concat", 12.0, "xy", 3, 4); // Supported by current parser!
        testXY("Variable and Parens", 15.0, "x(2+3)", 3, 0);
        testEquals("Parens and Parens", 12.0, "(2+1)(2+2)");
        testEquals("Parens and Num", 10.0, "(2+3)2");
        testXY("Parens and Variable", 15.0, "(1+2)x", 5, 0);
        testXY("Complex Implicit 1", 12.0, "2x y", 2, 3);
        testXY("Complex Implicit 2", 20.0, "2(2+1)x", 10/3.0, 0); // 2*3*3.33 = 20
        testXY("Fraction coeff", 2.5, "0.5x", 5, 0);

        // === CATEGORY 4: Unary Operators (8 tests) ===
        System.out.println("\n--- Unary Operators ---");
        testEquals("Leading Negative", -5.0, "-5");
        testEquals("Leading Positive", 5.0, "+5");
        testEquals("Negative Expression", -10.0, "-(5 + 5)");
        testEquals("Negative after operator", -1.0, "2 + -3");
        testEquals("Negative implicit mult", -6.0, "-2(3)");
        testXY("Negative Variable", -5.0, "-x", 5, 0);
        testEquals("Nested Negatives", 5.0, "-(-5)");
        testEquals("Power with negative", 0.25, "2^-2"); // 2^(-2) = 1/4

        // === CATEGORY 5: Scientific & Math Functions (12 tests) ===
        System.out.println("\n--- Scientific & Math Functions ---");
        testEquals("Sine", 0.0, "sin(0)");
        testEquals("Cosine", 1.0, "cos(0)");
        testEquals("Tangent", 0.0, "tan(0)");
        testEquals("Square Root", 4.0, "sqrt(16)");
        testEquals("Log base 10", 2.0, "log(100)");
        testEquals("Natural Log", 1.0, "ln(e)");
        testEquals("Absolute", 5.0, "abs(-5)");
        testEquals("Ceil", 4.0, "ceil(3.2)");
        testEquals("Floor", 3.0, "floor(3.8)");
        testEquals("Func Implicit Mult", 2.0, "2sin(pi/2)");
        testEquals("Nested Funcs", 5.0, "sqrt(abs(-25))");
        testEquals("Func with Neg arg", Math.log(Math.sqrt(Math.pow(-5, 2) + 1) - 5), "asinh(-5)"); // roughly

        // === CATEGORY 6: Variables & Constants (6 tests) ===
        System.out.println("\n--- Variables & Constants ---");
        testEquals("Constant Pi", Math.PI, "pi");
        testEquals("Constant e", Math.E, "e");
        testXY("Variables x and y", 15.0, "x^2 + y", 3, 6);
        testEquals("Constants math", Math.PI * 2, "2pi");
        testEquals("Constants power", Math.pow(Math.E, 2), "e^2");
        testXY("Mix of all", Math.PI + Math.E + 5, "pi + e + x", 5, 0);

        // === CATEGORY 7: Nested Logic / Stress Tests (5 tests) ===
        System.out.println("\n--- Nested Logic / Stress Tests ---");
        testEquals("Deeply nested parens", 10.0, "(((1+1)+1)+1)*2+2");
        testEquals("Nested funcs and ops", 3.0, "sqrt(2^2 + sin(0)^2) + cos(0)");
        testXY("Stress Implicit", 36.0, "2x(3y) + 6", 2, 2.5); // 2*2*(3*2.5) + 6 = 4*7.5+6 = 30+6
        testEquals("Extremely long expression", 14.0, "1+1+1+1+1+1+1+1+1+1+1+1+1+1");
        testEquals("Fractional power", 3.0, "9^(1/2)");

        // === CATEGORY 8: Error Handling (3 tests) ===
        System.out.println("\n--- Error Handling (Expecting NaN) ---");
        // Java double 5/0 is Infinity! So we expect Infinity.
        testEquals("Divide by zero (Double Infinity)", Double.POSITIVE_INFINITY, "5/0");
        testNaN("Empty Parens", "()");
        testNaN("Invalid tokens", "5 + %");

        System.out.println("\n==================================");
        System.out.println("TEST RUN COMPLETE");
        System.out.println("Passed: " + passed + " / " + (passed + failed));
        if (failed > 0) {
            System.out.println("STATUS: FAILED ❌");
            System.exit(1);
        } else {
            System.out.println("STATUS: ALL GREEN ✅");
        }
    }

    private static void testEquals(String desc, double expected, String expr) {
        testXY(desc, expected, expr, 0, 0);
    }

    private static void testXY(String desc, double expected, String expr, double x, double y) {
        double result = ScientificCalculator.FunctionParser.eval(expr, x, y);
        // Use a strictly bound tolerance for Floating Point errors
        if (Double.isNaN(expected) && Double.isNaN(result)) {
            printPass(desc);
            passed++;
        } else if (!Double.isNaN(expected) && !Double.isNaN(result) && Math.abs(expected - result) < 1e-9) {
            printPass(desc);
            passed++;
        } else if (Double.isInfinite(expected) && Double.isInfinite(result)) {
            printPass(desc);
            passed++;
        } else {
            printFail(desc, expected, result, expr);
            failed++;
        }
        currTestId++;
    }

    private static void testNaN(String desc, String expr) {
        double result = ScientificCalculator.FunctionParser.eval(expr, 0, 0);
        if (Double.isNaN(result)) {
            printPass(desc);
            passed++;
        } else {
            printFail(desc, Double.NaN, result, expr);
            failed++;
        }
        currTestId++;
    }

    private static void printPass(String desc) {
        System.out.println(String.format("  [%02d] PASS: %s", currTestId, desc));
    }

    private static void printFail(String desc, double expected, double actual, String expr) {
        System.out.println(String.format("  [%02d] FAIL: %s", currTestId, desc));
        System.out.println("       Expr:     " + expr);
        System.out.println("       Expected: " + expected);
        System.out.println("       Actual:   " + actual);
    }
}
