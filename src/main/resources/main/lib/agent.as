namespace lib.agent

#[native]
function stay()
    return "agent_game.game.AgentAction.STAY"
end

#[native]
function move()
    return "agent_game.game.AgentAction.MOVE"
end

#[native]
function turn_left()
    return "agent_game.game.AgentAction.TURN_LEFT"
end

#[native]
function turn_right()
    return "agent_game.game.AgentAction.TURN_RIGHT"
end