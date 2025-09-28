package io.julieisbaka.flashbackbypass.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.julieisbaka.flashbackbypass.FlashbackBypassClient;

@Mixin(targets = "com.moulberry.flashback.Flashback", remap = false)
public abstract class FlashbackMixin {
    private static final String MARKER_C2ME = "c2me";
    private static final String MARKER_C2ME_NAME = "concurrent chunk management engine";

    @Inject(method = "getReplayIncompatibleMods", at = @At("RETURN"), cancellable = true)
    private static void flashback_bypass$allowWithC2ME(CallbackInfoReturnable<List<String>> cir) {
        List<String> incompatible = cir.getReturnValue();
        if (incompatible == null || incompatible.isEmpty()) {
            return;
        }

        List<String> filtered = new ArrayList<>();
        boolean removedAny = false;

        for (String name : incompatible) {
            if (name == null) {
                continue;
            }
            String normalized = name.toLowerCase();
            if (normalized.contains(MARKER_C2ME) || normalized.contains(MARKER_C2ME_NAME)) {
                removedAny = true;
                continue;
            }
            filtered.add(name);
        }

        if (removedAny) {
            FlashbackBypassClient.LOGGER.debug("Filtered C2ME from Flashback incompatible mod list");
            cir.setReturnValue(filtered);
        }
    }
}
