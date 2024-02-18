package eu.shoroa.packettracer.ui

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer.TextLayerType
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.*
import net.minecraft.util.math.ColorHelper


object GUI {
    fun rect(x: Float, y: Float, width: Float, height: Float, color: Int) {
        val r1 = ColorHelper.Argb.getAlpha(color).toFloat() / 255.0f
        val g1 = ColorHelper.Argb.getRed(color).toFloat() / 255.0f
        val b1 = ColorHelper.Argb.getGreen(color).toFloat() / 255.0f
        val a1 = ColorHelper.Argb.getBlue(color).toFloat() / 255.0f
        val t: Tessellator = Tessellator.getInstance()
        val bb: BufferBuilder = t.buffer
        RenderSystem.enableBlend()
        RenderSystem.defaultBlendFunc()
        RenderSystem.setShader(GameRenderer::getPositionColorProgram)
        bb.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)
        bb.vertex(x.toDouble(), y.toDouble(), 0.0).color(g1, b1, a1, r1).next()
        bb.vertex(x.toDouble(), (y + height).toDouble(), 0.0).color(g1, b1, a1, r1).next()
        bb.vertex((x + width).toDouble(), (y + height).toDouble(), 0.0).color(g1, b1, a1, r1).next()
        bb.vertex((x + width).toDouble(), y.toDouble(), 0.0).color(g1, b1, a1, r1).next()
        t.draw()
        RenderSystem.disableBlend()
    }

    fun outline(x: Float, y: Float, width: Float, height: Float, color: Int) {
        rect(x, y, width, 1f, color)
        rect(x, y, 1f, height, color)
        rect(x + width - 1f, y, 1f, height, color)
        rect(x, y + height - 1f, width, 1f, color)
    }

    fun text(ctx: DrawContext?, text: String, x: Float, y: Float, color: Int, shadow: Boolean = true) {
        val tx = MinecraftClient.getInstance().textRenderer
        tx.draw(text, x, y, color, shadow, ctx?.matrices?.peek()?.positionMatrix, ctx?.vertexConsumers, TextLayerType.NORMAL, 0, 15728880, tx.isRightToLeft())
    }
}