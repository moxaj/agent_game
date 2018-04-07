namespace agent.scaredy

//
// An agent which stays idle until it notices an agent, then it panics.
//

import std.core as core
import std.vector as vector
import std.map as map
import lib.agent as agent

function main(state, memory, team_memory, statistics)
    if vector::size(map::get(state, 'agents')) > 0
        core::panic('Aaah!')
    end

    return agent::turn_left()
end