package io.github.darkkronicle.darkkore.util.render;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ShaderHandler {

    private interface ShaderInit {

        ShaderProgram loadShader(ResourceFactory factory, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> extraShaderList) throws IOException;

    }

    public enum CustomShader {
        CHROMA((resourceManager, extraShaderList) -> new ShaderProgram(resourceManager, "darkkore_chroma", VertexFormats.POSITION_COLOR)),
        CIRCLE((resourceManager, extraShaderList) -> new ShaderProgram(resourceManager, "darkkore_circle", VertexFormats.POSITION_COLOR))
        ;

        private final ShaderInit init;

        @Getter
        private ShaderProgram shader;

        CustomShader(ShaderInit onInit) {
            this.init = onInit;
        }

        private void loadShader(ResourceFactory factory, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> extraShaderList) throws IOException {
            ShaderProgram createdShader = init.loadShader(factory, extraShaderList);
            extraShaderList.add(Pair.of(createdShader, innerShader -> shader = innerShader));
        }

    }

    private final static ShaderHandler INSTANCE = new ShaderHandler();

    public static ShaderHandler getInstance() {
        return INSTANCE;
    }

    private ShaderHandler() {}

    public void loadShaders(ResourceFactory factory, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> extraShaderList) throws IOException {
        for (CustomShader custom : CustomShader.values()) {
            custom.loadShader(factory, extraShaderList);
        }
    }

}
