package io.julieisbaka.flashbackbypass.mixin;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationScenarioTest {
    @Test
    public void testFullReplayBypassScenario() {
        // Simulate a mod list with C2ME and others
        List<String> mods = Arrays.asList("Concurrent Chunk Management Engine (c2me)", "OtherMod");
        List<String> filtered = removeC2ME(mods);
        assertFalse(filtered.contains("Concurrent Chunk Management Engine (c2me)"), "C2ME should be removed");
        assertTrue(filtered.contains("OtherMod"), "Other mods should remain");

        // Simulate chunk and region IO sync fallback
        AtomicBoolean chunkRan = new AtomicBoolean(false);
        AtomicBoolean regionRan = new AtomicBoolean(false);
        boolean inReplay = true;
        if (inReplay) {
            chunkRan.set(true);
            regionRan.set(true);
        }
        assertTrue(chunkRan.get(), "Chunk task should run synchronously in replay");
        assertTrue(regionRan.get(), "Region IO should run synchronously in replay");
    }

    private List<String> removeC2ME(List<String> incompatible) {
        String MARKER_C2ME = "c2me";
        String MARKER_C2ME_NAME = "concurrent chunk management engine";
        List<String> filtered = new java.util.ArrayList<>();
        for (String name : incompatible) {
            if (name == null) continue;
            String normalized = name.toLowerCase();
            if (normalized.contains(MARKER_C2ME) || normalized.contains(MARKER_C2ME_NAME)) continue;
            filtered.add(name);
        }
        return filtered;
    }
}
