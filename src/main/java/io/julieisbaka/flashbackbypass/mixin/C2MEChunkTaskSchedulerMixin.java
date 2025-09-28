package io.julieisbaka.flashbackbypass.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Either;
import io.julieisbaka.flashbackbypass.FlashbackBypassClient;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Forces C2ME's async chunk scheduling to run synchronously in Flashback replay worlds.
 * This prevents race conditions and world corruption during replay playback.
 */
@Pseudo
@Mixin(targets = "com.kneelawk.c2me.common.util.AsyncChunkTaskScheduler", remap = false)
public class C2MEChunkTaskSchedulerMixin {
    @WrapOperation(
        method = "scheduleChunkTask",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/concurrent/Executor;execute(Ljava/lang/Runnable;)V"
        ),
        require = 0
    )
    private void flashback_bypass$forceSyncChunkLoad(
            java.util.concurrent.Executor executor,
            Runnable task,
            Operation<Void> original
    ) {
        if (isInReplayWorld()) {
            // Run synchronously
            FlashbackBypassClient.LOGGER.info("[FlashbackBypass] Forcing sync chunk load for replay world (C2ME)");
            task.run();
        } else {
            original.call(executor, task);
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
