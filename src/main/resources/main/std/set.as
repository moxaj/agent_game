namespace std.set

//
// Provides the implementation for the set datastructure.
//

#[native]
function new()
    return "new java.util.HashSet<Object>()"
end

#[native]
function size(set)
    return "((java.util.Set<Object>) {set}).size()"
end

#[native]
function contains(set, value)
    return "((java.util.Set<Object>) {set}).contains({value})"
end

#[native]
function add(set, value)
    "((java.util.Set<Object>) {set}).add({value})"
end

#[native]
function remove(set, key)
    "((java.util.Set<Object>) {set}).remove({key})"
end