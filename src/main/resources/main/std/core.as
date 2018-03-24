namespace std.core

#[private, native]
constant RANDOM = "new java.util.Random()"

#[native]
function force(delay)
    return "((agent_script.runtime.Delay) {delay}).force()"
end

#[macro]
function or(a, b)
    _a = force(a)
    if _a != nil
        return _a
    else
        return force(b)
    end
end

#[native]
function random()
    return "((java.util.Random) {RANDOM}).nextDouble()"
end

#[native]
function panic(message)
    "agent_script.runtime.Runtime.panic((String) {message})"
end

#[native]
function println(message)
   "agent_script.runtime.Runtime.println({message})"
end