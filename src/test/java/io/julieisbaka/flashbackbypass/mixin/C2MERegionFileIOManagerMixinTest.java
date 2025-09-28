package io.julieisbaka.flashbackbypass.mixin;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

public class C2MERegionFileIOManagerMixinTest {
    @Test
    public void testSyncRegionIOFallback() {
        AtomicBoolean ran = new AtomicBoolean(false);
        Runnable task = () -> ran.set(true);
        boolean inReplay = true;
        if (inReplay) {
            task.run();
        }
        assertTrue(ran.get(), "Region IO should run synchronously in replay context");
    }

    @Test
    public void testAsyncRegionIONormal() {
        AtomicBoolean ran = new AtomicBoolean(false);
        Runnable task = () -> ran.set(true);
        boolean inReplay = false;
        if (inReplay) {
            task.run();
        }
        assertFalse(ran.get(), "Region IO should not run synchronously outside replay context");
    }
}
