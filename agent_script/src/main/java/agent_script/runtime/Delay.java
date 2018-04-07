package agent_script.runtime;

import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public class Delay {
    private final Callable<Object> callable;

    public Delay(Callable<Object> callable) {
        this.callable = callable;
    }

    public Object force() {
        try {
            return callable.call();
        } catch (Exception e) {
            // Should not happen
            throw new RuntimeException(e);
        }
    }
}
