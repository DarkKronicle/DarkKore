package io.github.darkkronicle.darkkore.util.render;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ShaderHandler {

    private interface ShaderInit {

        Shader loadShader(ResourceManager resourceManager, List<Pair<Shader, Consumer<Shader>>> extraShaderList) throws IOException;

    }

    public enum CustomShader {
        CHROMA((resourceManager, extraShaderList) -> new Shader(resourceManager, "darkkore_chroma", VertexFormats.POSITION_COLOR))
        ;

        private final ShaderInit init;

        @Getter
        private Shader shader;

        CustomShader(ShaderInit onInit) {
            this.init = onInit;
        }

        private void loadShader(ResourceManager manager, List<Pair<Shader, Consumer<Shader>>> extraShaderList) throws IOException {
            Shader createdShader = init.loadShader(manager, extraShaderList);
            extraShaderList.add(Pair.of(createdShader, innerShader -> shader = innerShader));
        }

    }

    private final static ShaderHandler INSTANCE = new ShaderHandler();

    public static ShaderHandler getInstance() {
        return INSTANCE;
    }

    private ShaderHandler() {}

    public void loadShaders(ResourceManager resourceManager, List<Pair<Shader, Consumer<Shader>>> extraShaderList) throws IOException {
        for (CustomShader custom : CustomShader.values()) {
            custom.loadShader(resourceManager, extraShaderList);
        }
    }

}
