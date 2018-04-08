namespace agent.idle

//
// An agent which simply stay idle.
//

import lib.agent as agent

function main(state, memory, team_memory, statistics)
    return agent::stay()
end