package io.github.darkkronicle.darkkore.mixins;

import io.github.darkkronicle.darkkore.hotkeys.InputHandler;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MixinMouse {

    @Shadow private boolean cursorLocked;

    @Inject(method = "onMouseButton", at = @At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;getOverlay()Lnet/minecraft/client/gui/screen/Overlay;", ordinal = 0), cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (InputHandler.getInstance().onMouse(button, action, mods)) {
            ci.cancel();
        }
    }

}
