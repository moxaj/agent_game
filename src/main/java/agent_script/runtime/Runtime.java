package agent_script.runtime;

import java.io.PrintStream;
import java.util.concurrent.Callable;

/**
 * Represents the language runtime.
 */
@SuppressWarnings("unused")
public final class Runtime {
    /**
     * The output stream.
     */
    private static PrintStream out = System.out;

    /**
     * Coerces a value to a boolean.
     *
     * @param o the value
     * @return the coerced value
     */
    public static boolean asBoolean(Object o) throws RuntimeException {
        try {
            return (boolean) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("Could not cast Object to boolean");
        }
    }

    /**
     * Coerces a value to a number.
     *
     * @param o the value
     * @return the coerced value
     */
    public static double asNumber(Object o) throws RuntimeException {
        try {
            return ((Number) o).doubleValue();
        } catch (ClassCastException e) {
            throw new RuntimeException("Could not cast Object to double");
        }
    }

    /**
     * Coerces a value to a String.
     *
     * @param o the value
     * @return the coerced value
     */
    public static String asString(Object o) throws RuntimeException {
        try {
            return (String) o;
        } catch (ClassCastException e) {
            throw new RuntimeException("Could not cast Object to String");
        }
    }

    /**
     * Checks whether two values are equal.
     *
     * @param a the first value
     * @param b the second value
     * @return whether the values are equal
     */
    public static boolean eq(Object a, Object b) {
        if (a == null) {
            return b == null;
        }

        if (a instanceof Boolean && b instanceof Boolean) {
            return asBoolean(a) == asBoolean(b);
        }

        if (a instanceof Number && b instanceof Number) {
            return asNumber(a) == asNumber(b);
        }

        if (a instanceof String && b instanceof String) {
            return a.equals(b);
        }

        return a.equals(b);
    }

    /**
     * Creates a value which can be lazily evaluated.
     *
     * @param callable a value wrapped in a {@link Callable}
     * @return the delayed value
     */
    public static Delay delayed(Callable<Object> callable) {
        return new Delay(callable);
    }

    /**
     * Throws a {@link ScriptPanicException}.
     *
     * @param message the exception message
     * @throws ScriptPanicException always throws
     */
    public static void panic(String message) throws ScriptPanicException {
        throw new ScriptPanicException(message);
    }

    /**
     * Prints a message to {@link #out}.
     *
     * @param message the message to print
     */
    public static void println(Object message) {
        out.println(message);
    }

    /**
     * Redirects {@link #out} to another {@link PrintStream}.
     *
     * @param out the other stream
     */
    public static void redirectOut(PrintStream out) {
        Runtime.out = out;
    }

    /**
     * Restores {@link #out} to {@link System#out}.
     */
    public static void restoreOut() {
        out = System.out;
    }
}
