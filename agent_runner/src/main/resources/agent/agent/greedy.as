namespace agent.greedy

//
// An agent which runs to the nearest energy drink.
//

import std.vector as vector
import std.map as map
import lib.agent as agent

function main(state, memory, team_memory, statistics)
    position = map::get(state, 'position')
    direction = map::get(state, 'direction')

    target = map::get(memory, 'target')
    if target != nil && position == target
        target = nil
    end

    if target == nil
        drinks = map::get(state, 'drinks')
        if vector::size(drinks) > 0
            target = vector::get(drinks, 0)
        end
    end

    map::set(memory, 'target', target)

    if target != nil
        return agent::move_towards(position, direction, target)
    else
        return agent::turn_left()
    end
end