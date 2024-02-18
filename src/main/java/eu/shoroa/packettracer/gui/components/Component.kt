package eu.shoroa.packettracer.gui.components

import net.minecraft.client.gui.DrawContext

abstract class Component(var x: Float, var y: Float, var width: Float, var height: Float) {
    abstract fun render(ctx: DrawContext, delta: Float, mouseX: Float, mouseY: Float)
    abstract fun click(mouseX: Double, mouseY: Double, button: Int)

    open fun scroll(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double) {}

    fun isHovered(mouseX: Number, mouseY: Number): Boolean {
        return mouseX.toFloat() >= x && mouseX.toFloat() <= x + width && mouseY.toFloat() >= y && mouseY.toFloat() <= y + height
    }

    fun isHovered(mouseX: Number, mouseY: Number, x: Number, y: Number, width: Number, height: Number): Boolean {
        return mouseX.toFloat() >= x.toFloat() && mouseX.toFloat() <= x.toFloat() + width.toFloat() && mouseY.toFloat() >= y.toFloat() && mouseY.toFloat() <= y.toFloat() + height.toFloat()
    }
}