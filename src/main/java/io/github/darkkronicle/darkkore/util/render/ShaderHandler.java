package io.github.darkkronicle.darkkore.util.render;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ShaderHandler {

        public final static Shader CHROMA = ((resourceManager, extraShaderList) -> new Shader(resourceManager, "darkkore_chroma", VertexFormats.POSITION_COLOR));
        CIRCLE((resourceManager, extraShaderList) -> new Shader(resourceManager, "darkkore_circle", VertexFormats.POSITION_COLOR))
}
