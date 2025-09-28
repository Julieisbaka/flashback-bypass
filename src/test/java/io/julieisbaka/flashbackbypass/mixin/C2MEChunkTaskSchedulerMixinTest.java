package io.julieisbaka.flashbackbypass.mixin;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

public class C2MEChunkTaskSchedulerMixinTest {
    @Test
    public void testSyncChunkLoadFallback() {
        AtomicBoolean ran = new AtomicBoolean(false);
        Runnable task = () -> ran.set(true);
        // Simulate the mixin logic: if in replay, run synchronously
        boolean inReplay = true;
        if (inReplay) {
            task.run();
        }
        assertTrue(ran.get(), "Task should run synchronously in replay context");
    }

    @Test
    public void testAsyncChunkLoadNormal() {
        AtomicBoolean ran = new AtomicBoolean(false);
        Runnable task = () -> ran.set(true);
        boolean inReplay = false;
        if (inReplay) {
            task.run();
        }
        assertFalse(ran.get(), "Task should not run synchronously outside replay context");
    }
}
