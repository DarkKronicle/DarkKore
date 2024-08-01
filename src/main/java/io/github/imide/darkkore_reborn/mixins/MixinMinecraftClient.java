package io.github.imide.darkkore_reborn.mixins;

import io.github.imide.darkkore_reborn.config.ConfigurationManager;
import io.github.imide.darkkore_reborn.intialization.InitializationHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(RunArgs args, CallbackInfo ci) {
        ConfigurationManager.getInstance().load();
        InitializationHandler.getInstance().load();
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void close(CallbackInfo ci) {
        ConfigurationManager.getInstance().save();
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
    private void close(Screen screen, CallbackInfo ci) {
        ConfigurationManager.getInstance().save();
    }

}
