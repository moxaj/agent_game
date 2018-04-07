namespace std.map

//
// Provides the implementation for the map datastructure.
//

#[native]
function new()
    return "new java.util.HashMap<Object, Object>()"
end

#[native]
function size(map)
    return "((java.util.Map<Object, Object>) {map}).size()"
end

#[native]
function contains(map, key)
    return "((java.util.Map<Object, Object>) {map}).containsKey({key})"
end

#[native]
function get(map, key)
    return "((java.util.Map<Object, Object>) {map}).get({key})"
end

#[native]
function set(map, key, value)
    "((java.util.Map<Object, Object>) {map}).put({key}, {value})"
end

#[native]
function remove(map, key)
    "((java.util.Map<Object, Object>) {map}).remove({key})"
end