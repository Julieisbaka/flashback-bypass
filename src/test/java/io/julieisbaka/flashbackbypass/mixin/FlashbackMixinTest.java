package io.julieisbaka.flashbackbypass.mixin;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class FlashbackMixinTest {
    @Test
    public void testC2MERemovalFromList() throws Exception {
        // Simulate the logic of the mixin
        List<String> input = Arrays.asList("Concurrent Chunk Management Engine (c2me)", "OtherMod");
        List<String> expected = Arrays.asList("OtherMod");
        List<String> actual = removeC2ME(input);
        assertEquals(expected, actual);
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
