package io.github.darkkronicle.darkkore.util.render;

import com.google.common.collect.Lists;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.List;

/*
 * Source from https://github.com/shedaniel/cloth-api/blob/1.16.2/cloth-scissors-api-v1/src/main/java/me/shedaniel/cloth/impl/utils/ScissorsStackImpl.java
 *
 * Author shedaniel
 *
 * Licensed under public domain (https://unlicense.org)
 */

/**
 * Utility class to have scissors stack
 */
public class ScissorsStack {

    private final List<PositionedRectangle> scissorsAreas = Lists.newArrayList();
    private final PositionedRectangle empty = new PositionedRectangle(0, 0, 0, 0);

    private final static ScissorsStack INSTANCE = new ScissorsStack();

    private ScissorsStack() {}

    public static ScissorsStack getInstance() {
        return INSTANCE;
    }

    public ScissorsStack applyScissors(PositionedRectangle rectangle) {
        if (rectangle.isEmpty()) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else {
            Window window = MinecraftClient.getInstance().getWindow();
            double scaleFactor = window.getScaleFactor();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (rectangle.x * scaleFactor), (int) ((window.getScaledHeight() - rectangle.height - rectangle.y) * scaleFactor), (int) (rectangle.width * scaleFactor), (int) (rectangle.height * scaleFactor));
        }
        return this;
    }

    public ScissorsStack push(PositionedRectangle rectangle) {
        scissorsAreas.add(rectangle);
        return this;
    }

    public ScissorsStack pushAll(Collection<PositionedRectangle> rectangles) {
        scissorsAreas.addAll(rectangles);
        return this;
    }

    public ScissorsStack pop() {
        if (scissorsAreas.isEmpty()) {
            throw new IllegalStateException("There are no entries in the stack!");
        }
        scissorsAreas.remove(scissorsAreas.size() - 1);
        return this;
    }

    public List<PositionedRectangle> getCurrentStack() {
        return scissorsAreas;
    }

    public ScissorsStack applyStack() {
        if (scissorsAreas.isEmpty()) {
            return applyScissors(empty);
        }
        PositionedRectangle rectangle = null;
        for (PositionedRectangle area : scissorsAreas) {
            if (rectangle == null) {
                rectangle = area.copy();
            } else {
                rectangle = rectangle.intersection(area);
            }
        }
        return applyScissors(rectangle);
    }

}
