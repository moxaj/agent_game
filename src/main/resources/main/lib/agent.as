namespace lib.agent

import std.core as core

// Primitives

#[private, native]
constant STAY       = "agent_game.game.AgentAction.STAY"

#[private, native]
constant MOVE       = "agent_game.game.AgentAction.MOVE"

#[private, native]
constant TURN_LEFT  = "agent_game.game.AgentAction.TURN_LEFT"

#[private, native]
constant TURN_RIGHT = "agent_game.game.AgentAction.TURN_RIGHT"

// Helper

function or(action1, action2)
    if action1 != STAY
        return action1
    else
        return action2
    end
end

function distance_from(x, y, u, v)
    distance = 0
    if x < u
        distance = distance + (u - x)
    else
        distance = distance + (x - u)
    end

    if y < v
        distance = distance + (v - y)
    else
        distance = distance + (y - v)
    end

    return distance
end

// Basic actions

function stay()
    return STAY
end

function move()
    return MOVE
end

function turn_left()
    return TURN_LEFT
end

function turn_right()
    return TURN_RIGHT
end

// Advanced actions

function turn_towards(d1, d2)
    return STAY // TODO
end

function move_towards(x, y, d, u, v)
    if x != u
        // Move horizontally

        d2 = 0
        if x < u
            d2 = 1
        else
            d2 = 3
        end

        return or(turn_towards(d, d2), move())
    else if y != v
        // Move vertically

        d2 = 0
        if y < v
            d2 = 0
        else
            d2 = 2
        end

        return or(turn_towards(d, d2), move())
    else
        return stay()
    end
end