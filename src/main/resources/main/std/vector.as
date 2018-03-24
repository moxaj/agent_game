namespace std.vector

#[native]
function new()
    return "new java.util.ArrayList<Object>()"
end

#[native]
function size(vector)
    return "((java.util.List<Object>) {vector}).size()"
end

function contains(vector, value)
    size = size(vector)
    index = 0
    while index < size
        if get(vector, index) == value
            return true
        end
    end

    return false
end

#[native]
function get(vector, index)
    return "((java.util.List<Object>) {vector}).get((int) {index})"
end

#[native]
function add(vector, value)
    "((java.util.List<Object>) {vector}).add({value})"
end

#[native]
function set(vector, index, value)
    "((java.util.List<Object>) {vector}).set((int) {index}, {value})"
end