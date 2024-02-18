package eu.shoroa.packettracer.gui.components

import eu.shoroa.packettracer.gui.ScreenCaptured
import eu.shoroa.packettracer.ui.Icons
import eu.shoroa.packettracer.ui.Material
import eu.shoroa.packettracer.ui.UI
import eu.shoroa.packettracer.viewer.Viewer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket
import java.awt.Color

class ComponentPacketViewer(x: Float, y: Float, width: Float, height: Float) : Component(x, y, width, height) {
    override fun render(ctx: DrawContext, delta: Float, mouseX: Float, mouseY: Float) {
        val packetObject = (MinecraftClient.getInstance().currentScreen as? ScreenCaptured)?.packetList?.selected

        UI.rrect(x, y, width, height, 4f, Material.SOLID(Color(0x1D1D1D)))
        UI.rrect(x, y, width, height, 4f, Material.SOLID(Color(0x404040)).stroke(.5f))

        UI.text("Packet Viewer", x + width / 2f, y + 10, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)
        UI.text(Icons.trash_alt, x + width - 10, y + 10, 12f, "icon", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)

        val text = "Select a packet to view"

        UI.rect(x + 5f, y + 20, width - 10f, height - 30, Material.SOLID(Color(0x181818)))
        if(packetObject != null) {
            Viewer.getViewer(packetObject.packet)?.render(packetObject.packet, x + 5f, y + 20, width - 10f, height - 30)
        } else {
            UI.text(text, x + width / 2f, y + height / 2f, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)
        }
    }

    override fun click(mouseX: Double, mouseY: Double, button: Int) {
        if(button == 0) {
            if(isHovered(mouseX, mouseY, x + width - 20, y, 20, 20)) {
                (MinecraftClient.getInstance().currentScreen as? ScreenCaptured)?.packetList?.selected = null
            }
        }
    }

    override fun scroll(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double) {

    }
}