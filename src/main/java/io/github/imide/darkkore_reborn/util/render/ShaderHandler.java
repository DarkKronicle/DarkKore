package io.github.imide.darkkore_reborn.util.render;

import io.github.imide.darkkore_reborn.DarkKore_reborn;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.ladysnake.satin.api.managed.ManagedCoreShader;
import org.ladysnake.satin.api.managed.ShaderEffectManager;

public class ShaderHandler {

    public final static ManagedCoreShader CHROMA = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of(DarkKore_reborn.MOD_ID, "darkkore_chroma"), VertexFormats.POSITION_COLOR);

    public final static ManagedCoreShader CIRCLE = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of(DarkKore_reborn.MOD_ID, "darkkore_circle"), VertexFormats.POSITION_COLOR);

    public static void initialize() {
        // We just want chroma and circle to load
    }

}
