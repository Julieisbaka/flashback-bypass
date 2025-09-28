package io.julieisbaka.flashbackbypass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class FlashbackBypassClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("flashback-bypass");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Flashback Bypass initialized. Replays will remain accessible with C2ME loaded.");
    }
}
