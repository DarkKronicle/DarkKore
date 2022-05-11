package io.github.darkkronicle.darkkore.mixins;

import io.github.darkkronicle.darkkore.config.ConfigurationManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(method = "onGameJoin", at = @At("HEAD"))
    private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        ConfigurationManager.getInstance().save();
    }

}
