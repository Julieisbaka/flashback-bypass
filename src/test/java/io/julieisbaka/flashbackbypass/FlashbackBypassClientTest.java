package io.julieisbaka.flashbackbypass;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlashbackBypassClientTest {
    @Test
    public void testLoggerNotNull() {
        assertNotNull(FlashbackBypassClient.LOGGER, "Logger should not be null");
    }

    @Test
    public void testOnInitializeClientDoesNotThrow() {
        FlashbackBypassClient client = new FlashbackBypassClient();
        assertDoesNotThrow(client::onInitializeClient, "onInitializeClient should not throw");
    }
}
