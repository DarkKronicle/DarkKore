package io.github.darkkronicle.darkkore.mixins;

import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import io.github.darkkronicle.darkkore.util.SoundUtil;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClickableWidget.class)
public class MixinClickableWidget {

    @Inject(method = "playDownSound", at = @At("HEAD"), cancellable = true)
    private void playDownSound(SoundManager manager, CallbackInfo ci) {
        if (DarkKoreConfig.getInstance().changeVanillaButtons.getValue()) {
            SoundUtil.playInterfaceSound();
            ci.cancel();
        }
    }

}
