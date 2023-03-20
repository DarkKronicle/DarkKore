package io.github.darkkronicle.darkkore.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.darkkronicle.darkkore.util.Color;
import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

import java.util.function.Supplier;

/**
 * Various methods to help make rendering easier
 */
@UtilityClass
public class RenderUtil {

    /**
     * Fills an outline with x/y width/height values
     */
    public void drawOutline(MatrixStack matrices, int x, int y, int width, int height, int color) {
        fillOutline(matrices, x, y, x + width, y + height, color);
    }

    public void drawOutline(MatrixStack matrices, int x, int y, int width, int height, Color color) {
        fillOutline(matrices, x, y, x + width, y + height, color);
    }

    public void fillOutline(MatrixStack matrices, int x, int y, int x2, int y2, Color color) {
        // Top line
        fill(matrices, x, y, x2, y + 1, color);
        // Left line
        fill(matrices, x, y + 1, x + 1, y2 - 1, color);
        // Right line
        fill(matrices, x2 - 1, y + 1, x2, y2 - 1, color);
        // Bottom line
        fill(matrices, x, y2 - 1, x2, y2, color);
    }

    /**
     * Draws an outline with raw x/y values
     */
    public void fillOutline(MatrixStack matrices, int x, int y, int x2, int y2, int color) {
        // Top line
        fill(matrices, x, y, x2, y + 1, color);
        // Left line
        fill(matrices, x, y + 1, x + 1, y2 - 1, color);
        // Right line
        fill(matrices, x2 - 1, y + 1, x2, y2 - 1, color);
        // Bottom line
        fill(matrices, x, y2 - 1, x2, y2, color);
    }

    /**
     * Draws a vertical line
     */
    public void drawVerticalLine(MatrixStack matrices, int x, int y, int height, int color) {
        drawRectangle(matrices, x, y, 1, height, color);
    }

    /**
     * Draws a horizontal line
     */
    public void drawHorizontalLine(MatrixStack matrices, int x, int y, int width, int color) {
        drawRectangle(matrices, x, y, width, 1, color);
    }

    /**
     * Fills in a rectangle with a color. x/y width/height
     */
    public void drawRectangle(MatrixStack matrices, int x, int y, int width, int height, int color) {
        fill(matrices, x, y, x + width, y + height, color);
    }

    public void drawRectangle(MatrixStack matrices, int x, int y, int width, int height, int color, Supplier<ShaderProgram> shaderSupplier) {
        fill(matrices.peek().getPositionMatrix(), x, y, x + width, y + height, color, shaderSupplier);
    }

    /**
     * Fills in a rectangle with a color. Uses raw x/y values. x/y
     */
    public void fill(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
        fill(matrices.peek().getPositionMatrix(), x1, y1, x2, y2, color);
    }

    public void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, int color) {
        fill(matrix, x1, y1, x2, y2, color, GameRenderer::getPositionColorProgram);
    }

    public void drawRectangle(MatrixStack matrices, int x, int y, int width, int height, Color color) {
        fill(matrices, x, y, x + width, y + height, color);
    }

    public void fill(MatrixStack matrix, int x1, int y1, int x2, int y2, Color color) {
        fill(matrix.peek().getPositionMatrix(), x1, y1, x2, y2, color);
    }

    public void drawCircle(MatrixStack matrices, float centerX, float centerY, float radius, int color) {
        drawRingInnerPercent(matrices, centerX, centerY, radius, 0, color);
    }

    public void drawRing(MatrixStack matrices, float centerX, float centerY, float radius, float innerRadius, int color) {
        drawRing(matrices.peek().getPositionMatrix(), centerX - radius, centerY - radius, radius * 2, radius * 2,
                (float) Math.pow(innerRadius / radius, 2), color);
    }

    public void drawRingInnerPercent(MatrixStack matrices, float centerX, float centerY, float radius, float innerPercent, int color) {
        drawRing(matrices.peek().getPositionMatrix(), centerX - radius, centerY - radius, radius * 2, radius * 2, innerPercent, color);
    }

    public void drawRingInnerPercent(MatrixStack matrices, float x, float y, float width, float height, float innerRadiusPercent, int color) {
        drawRing(matrices.peek().getPositionMatrix(), x, y, width, height, innerRadiusPercent, color);
    }

    public void drawRing(Matrix4f matrix, float x, float y, float width, float height, float innerRadiusPercent, int color) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShaderColor(innerRadiusPercent, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.setShader(ShaderHandler.CIRCLE::getProgram);

        float x2 = x + width;

        float y2 = y + height;

        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(matrix, x, y2, 0.0f).texture(0, 1).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0f).texture(1, 1).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y, 0.0f).texture(1, 0).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x, y, 0.0f).texture(0, 0).color(r, g, b, a).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, Color color) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        int colorInt = color.preRender();
        float a = (float)(colorInt >> 24 & 0xFF) / 255.0f;
        float r = (float)(colorInt >> 16 & 0xFF) / 255.0f;
        float g = (float)(colorInt >> 8 & 0xFF) / 255.0f;
        float b = (float)(colorInt & 0xFF) / 255.0f;
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y1, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x1, y1, 0.0f).color(r, g, b, a).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        color.postRender();

    }

    public void fill(Matrix4f matrix, int x1, int y1, int x2, int y2, int color, Supplier<ShaderProgram> shaderSupplier) {
        int i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.setShader(shaderSupplier);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x2, y1, 0.0f).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, x1, y1, 0.0f).color(r, g, b, a).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();
    }


    public void fillGradient(Matrix4f matrix, BufferBuilder builder, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        float a = (float)(colorStart >> 24 & 0xFF) / 255.0f;
        float r = (float)(colorStart >> 16 & 0xFF) / 255.0f;
        float g = (float)(colorStart >> 8 & 0xFF) / 255.0f;
        float b = (float)(colorStart & 0xFF) / 255.0f;
        float a2 = (float)(colorEnd >> 24 & 0xFF) / 255.0f;
        float r2 = (float)(colorEnd >> 16 & 0xFF) / 255.0f;
        float g2 = (float)(colorEnd >> 8 & 0xFF) / 255.0f;
        float b2 = (float)(colorEnd & 0xFF) / 255.0f;
        builder.vertex(matrix, endX, startY, 0).color(r, g, b, a).next();
        builder.vertex(matrix, startX, startY, 0).color(r, g, b, a).next();
        builder.vertex(matrix, startX, endY, 0).color(r2, g2, b2, a2).next();
        builder.vertex(matrix, endX, endY, 0).color(r2, g2, b2, a2).next();
    }

    public void fillGradient(MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        fillGradient(matrices, startX, startY, endX, endY, colorStart, colorEnd, GameRenderer::getPositionColorProgram);
    }

    public void fillGradient(MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, Supplier<ShaderProgram> shaderSupplier) {
        RenderSystem.enableBlend();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        fillGradient(matrices.peek().getPositionMatrix(), bufferBuilder, startX, startY, endX, endY, colorStart, colorEnd);
        tessellator.draw();
        RenderSystem.disableBlend();
    }

    /**
     * Draws an item with the count not showing
     */
    public void drawItem(MatrixStack matrices, ItemStack stack, int x, int y) {
        drawItem(matrices, stack, x, y, false);
    }

    /**
     * Draws an item
     * @param matrices Matrices
     * @param stack {@link ItemStack} to render
     * @param x x
     * @param y y
     * @param showCount Whether item count should show
     */
    public void drawItem(MatrixStack matrices, ItemStack stack, int x, int y, boolean showCount) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderInGuiWithOverrides(matrices, stack, x, y);
        if (showCount) {
            itemRenderer.renderGuiItemOverlay(matrices, MinecraftClient.getInstance().textRenderer, stack, x, y, stack.getCount() > 1 ? String.valueOf(stack.getCount()) : "");
        }
    }

}
