package agent_script.compiler.analyzer;

import agent_script.compiler.DefaultCompiler;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a script symbol.
 */
public final class Symbol {
    /**
     * The string which separates the namespace name from the name.
     */
    private static final String NAMESPACE_SEPARATOR = "::";

    /**
     * The string which separates the namespace name fragments.
     */
    private static final String NAMESPACE_FRAGMENT_SEPARATOR = ".";

    /**
     * The string which separates java package names.
     */
    private static final String JAVA_PACKAGE_SEPARATOR = ".";

    /**
     * The file system path separator.
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     * The namespace fragments of the symbol.
     */
    private final String[] namespaceFragments;

    /**
     * The name fragment of the symbol.
     */
    private final String nameFragment;

    private Symbol(String[] namespaceFragments, String nameFragment) {
        this.namespaceFragments = namespaceFragments;
        this.nameFragment = nameFragment;
    }

    /**
     * Creates a symbol from the given namespace fragments and name fragment.
     *
     * @param namespaceFragments the namespace fragments
     * @param nameFragment       the name fragment
     * @return the created symbol
     */
    public static Symbol create(String[] namespaceFragments, String nameFragment) {
        return new Symbol(namespaceFragments, nameFragment);
    }

    /**
     * Parses the string as a namespace symbol.
     *
     * @param s the string to parse
     * @return the namespace symbol or null
     */
    public static Symbol parseNamespaceSymbol(String s) {
        return s.contains(NAMESPACE_SEPARATOR) ? null : create(s.split(Pattern.quote(NAMESPACE_FRAGMENT_SEPARATOR)), null);
    }

    /**
     * Parses the string as a name symbol.
     *
     * @param s the string to parse
     * @return the name symbol or null
     */
    public static Symbol parseNameSymbol(String s) {
        return s.contains(NAMESPACE_SEPARATOR)
                ? null
                : s.contains(NAMESPACE_FRAGMENT_SEPARATOR) ? null : create(new String[0], s);
    }

    /**
     * Parses the string as a potentially qualified symbol.
     *
     * @param s the string to parse
     * @return the potentially qualified symbol or null
     */
    public static Symbol parseMaybeQualifiedSymbol(String s) {
        String[] fragments = s.split(NAMESPACE_SEPARATOR);
        if (fragments.length == 1) {
            fragments = fragments[0].split(Pattern.quote(NAMESPACE_FRAGMENT_SEPARATOR));
            return fragments.length != 1
                    ? null
                    : new Symbol(new String[0], fragments[0]);
        }

        return new Symbol(fragments[0].split(Pattern.quote(NAMESPACE_FRAGMENT_SEPARATOR)), fragments[1]);
    }

    /**
     * Parses the string as a qualified symbol.
     *
     * @param s the string to parse
     * @return the qualified symbol or null
     */
    public static Symbol parseQualifiedSymbolUnsafe(String s) {
        String[] fragments = s.split(NAMESPACE_SEPARATOR);
        if (fragments.length != 2) {
            return null;
        }

        String[] namespaceFragments = fragments[0].split(NAMESPACE_FRAGMENT_SEPARATOR);
        fragments = fragments[1].split(NAMESPACE_FRAGMENT_SEPARATOR);
        if (fragments.length > 0) {
            return null;
        }

        return create(namespaceFragments, fragments[0]);
    }

    /**
     * @return the value of {@link #namespaceFragments}
     */
    public String[] getNamespaceFragments() {
        return namespaceFragments;
    }

    /**
     * @return the value of {@link #nameFragment}
     */
    public String getNameFragment() {
        return nameFragment;
    }

    /**
     * @return the namespace symbol of the symbol
     */
    public Symbol getNamespaceSymbol() {
        return Symbol.create(namespaceFragments, null);
    }

    /**
     * @return the name symbol of the symbol
     */
    public Symbol getNameSymbol() {
        return Symbol.create(new String[0], nameFragment);
    }

    /**
     * @return whether the symbol represents a namespace
     */
    public boolean isNamespaceSymbol() {
        return nameFragment == null;
    }

    /**
     * @return whether the symbol an unqualified symbol
     */
    public boolean isNameSymbol() {
        return namespaceFragments.length == 0;
    }

    /**
     * @return a java package name created from the namespace fragments
     */
    public String getJavaPackageName() {
        String[] namespaceParts = getNamespaceFragments();
        assert namespaceParts.length > 1;
        return Arrays.stream(Arrays.copyOf(namespaceParts, namespaceParts.length - 1))
                .map(DefaultCompiler::munge)
                .collect(Collectors.joining(JAVA_PACKAGE_SEPARATOR));
    }

    /**
     * @return a java class name created from the namespace fragments
     */
    public String getJavaClassName() {
        String[] namespaceParts = getNamespaceFragments();
        assert namespaceParts.length > 1;
        return DefaultCompiler.munge(namespaceParts[namespaceParts.length - 1]);
    }


    /**
     * @return the qualified java class name
     */
    public String getQualifiedJavaClassName() {
        return getJavaPackageName() + JAVA_PACKAGE_SEPARATOR + getJavaClassName();
    }

    /**
     * @return the path matching the qualified class name
     */
    public String getJavaPath() {
        return getQualifiedJavaClassName().replaceAll(Pattern.quote(JAVA_PACKAGE_SEPARATOR), PATH_SEPARATOR);
    }

    /**
     * Qualifies a symbol.
     *
     * @param namespaceSymbol the namespace symbol lto qualify with
     * @return the qualified symbol
     */
    public Symbol qualify(Symbol namespaceSymbol) {
        assert namespaceSymbol.isNamespaceSymbol();
        assert isNameSymbol();
        return create(namespaceSymbol.getNamespaceFragments(), nameFragment);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nameFragment);
        result = 31 * result + Arrays.hashCode(namespaceFragments);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol that = (Symbol) o;
        return Arrays.equals(namespaceFragments, that.namespaceFragments) && Objects.equals(nameFragment, that.nameFragment);
    }

    @Override
    public String toString() {
        return namespaceFragments.length == 0
                ? nameFragment
                : String.join(NAMESPACE_FRAGMENT_SEPARATOR, namespaceFragments)
                + (nameFragment == null ? "" : (NAMESPACE_SEPARATOR + nameFragment));
    }
}
