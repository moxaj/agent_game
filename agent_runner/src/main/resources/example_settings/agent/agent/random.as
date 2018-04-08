namespace agent.random

//
// An agent which roams around randomly.
//

import std.core as core
import lib.agent as agent

function main(state, memory, team_memory, statistics)
    r = core::random()
    if r < 0.1
        return agent::stay()
    else if r < 0.2
        return agent::turn_left()
    else if r < 0.3
        return agent::turn_right()
    else
        return agent::move()
    end
end