package com.example.c2meflashbackbypass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class C2meFlashbackBypassClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("c2me-flashback-bypass");

    @Override
    public void onInitializeClient() {
        LOGGER.info("C2ME Flashback Bypass initialized. Replays will remain accessible with C2ME loaded.");
    }
}
