package agent_script.runtime;

import java.util.concurrent.Callable;

public class Delay implements IDelay {
    private final Callable<Object> callable;

    public Delay(Callable<Object> callable) {
        this.callable = callable;
    }

    @Override
    public Object force() {
        try {
            return callable.call();
        } catch (Exception e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }
}
