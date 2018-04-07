package agent_script.compiler.analyzer.namespace;

import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a top-level element.
 */
public abstract class ElementDefinition {
    /**
     * The name of the element.
     */
    private final Symbol name;

    /**
     * The location of the element.
     */
    private final Location location;

    /**
     * The metadata associated with the element.
     */
    private final Set<Symbol> meta = new HashSet<>();

    public ElementDefinition(Symbol name, Location location) {
        this.name = name;
        this.location = location;
    }

    /**
     * @return the value of {@link #name}
     */
    public Symbol getName() {
        return name;
    }

    /**
     * @return the value of {@link #location}
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return the value of {@link #meta}
     */
    public Set<Symbol> getMeta() {
        return meta;
    }

    /**
     * @return if the element has a 'native' metadata
     */
    public boolean isNative() {
        return getMeta().contains(Symbol.asNameSymbol("native"));
    }

    /**
     * @return if the element has a 'private' metadata
     */
    public boolean isPrivate() {
        return getMeta().contains(Symbol.asNameSymbol("private"));
    }
}
