namespace lib.agent

//
// Agent related extensions.
//

import std.core as core
import std.vector as vector
import std.math as math

// Primitives

#[private, native]
constant STAY = "agent_game.game.AgentAction.STAY"

#[private, native]
constant MOVE = "agent_game.game.AgentAction.MOVE"

#[private, native]
constant TURN_LEFT = "agent_game.game.AgentAction.TURN_LEFT"

#[private, native]
constant TURN_RIGHT = "agent_game.game.AgentAction.TURN_RIGHT"

// Helper

function action_or(action1, action2)
    if action1 != STAY
        return action1
    else
        return action2
    end
end

function distance_from(position1, position2)
    x = vector::get(position1, 0)
    y = vector::get(position1, 1)
    u = vector::get(position2, 0)
    v = vector::get(position2, 1)
    return math::abs(u - x) + math::abs(y - v)
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

function turn_towards(direction1, direction2)
    // Naive version for now
    if direction1 == direction2
        return stay()
    else
        return turn_left()
    end
end

function move_towards(position1, direction, position2)
    x = vector::get(position1, 0)
    y = vector::get(position1, 1)

    u = vector::get(position2, 0)
    v = vector::get(position2, 1)

    if x != u
        // Move horizontally

        direction2 = 0
        if x < u
            direction2 = 1
        else
            direction2 = 3
        end

        return action_or(turn_towards(direction, direction2), move())
    else if y != v
        // Move vertically

        direction2 = 0
        if y < v
            direction2 = 0
        else
            direction2 = 2
        end

        return action_or(turn_towards(direction, direction2), move())
    else
        return stay()
    end
end