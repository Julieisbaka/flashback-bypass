package io.julieisbaka.flashbackbypass.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.julieisbaka.flashbackbypass.FlashbackBypassClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Forces C2ME's region file IO to run on the main thread in Flashback replay worlds.
 * Prevents data corruption and race conditions during replay playback.
 */
@Pseudo
@Mixin(targets = "com.kneelawk.c2me.common.util.RegionFileIOManager", remap = false)
public class C2MERegionFileIOManagerMixin {
    @WrapOperation(
        method = "submitTask",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/concurrent/ExecutorService;submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;"
        ),
        require = 0
    )
    private java.util.concurrent.Future<?> flashback_bypass$forceSyncRegionIO(
            java.util.concurrent.ExecutorService executor,
            Runnable task,
            Operation<java.util.concurrent.Future<?>> original
    ) {
        if (isInReplayWorld()) {
            FlashbackBypassClient.LOGGER.info("[FlashbackBypass] Forcing sync region IO for replay world (C2ME)");
            task.run();
            return null; // No future, ran synchronously
        } else {
            return original.call(executor, task);
        }
    }

    private static boolean isInReplayWorld() {
        try {
            Class<?> flashback = Class.forName("com.moulberry.flashback.Flashback");
            return (boolean) flashback.getMethod("isInReplay").invoke(null);
        } catch (Exception e) {
            return false;
        }
    }
}
