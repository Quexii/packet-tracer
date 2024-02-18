package eu.shoroa.packettracer.gui.components

import net.minecraft.client.gui.DrawContext

class ComponentButton(text: String, onClick: () -> Unit, x: Float, y: Float, width: Float, height: Float) : Component(x, y, width, height) {
    override fun render(ctx: DrawContext, delta: Float, mouseX: Float, mouseY: Float) {

    }

    override fun click(mouseX: Double, mouseY: Double, button: Int) {

    }
}