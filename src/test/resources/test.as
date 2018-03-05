X = 10
Y = 20

// line comment

/*
  block comment
*/

strategy
    x = 10
    y = false

    if _z
        x = 20
        y = y || true && _z < 30
    else if _u
    else
    end

    while _z > 0
        x = x + 1
        _z = _z - 1
    end

    :run
end