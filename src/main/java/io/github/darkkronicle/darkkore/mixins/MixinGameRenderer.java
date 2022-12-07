package io.github.darkkronicle.darkkore.mixins;

import com.mojang.datafixers.util.Pair;
import io.github.darkkronicle.darkkore.util.render.ShaderHandler;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "loadPrograms", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;clearPrograms()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void registerShaders(
            ResourceFactory factory, CallbackInfo ci, List<ShaderStage> list, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaderList
    ) {
        // https://github.com/BillyGalbreath/FabricTest/blob/master/src/main/java/test/fabrictest/mixin/GameRendererMixin.java
        List<Pair<ShaderProgram, Consumer<ShaderProgram>>> extraShaderList = new ArrayList<>();
        try {
            ShaderHandler.getInstance().loadShaders(factory, extraShaderList);
        } catch (IOException e) {
            extraShaderList.forEach(pair -> pair.getFirst().close());
            throw new RuntimeException("could not reload shaders", e);
        }
        shaderList.addAll(extraShaderList);
    }

}
