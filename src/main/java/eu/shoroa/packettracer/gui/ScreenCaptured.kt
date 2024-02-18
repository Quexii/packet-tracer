package eu.shoroa.packettracer.gui

import eu.shoroa.packettracer.gui.components.ComponentPacketList
import eu.shoroa.packettracer.gui.components.ComponentPacketViewer
import eu.shoroa.packettracer.ui.GUI
import eu.shoroa.packettracer.ui.Material
import eu.shoroa.packettracer.ui.UI
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import java.awt.Color

class ScreenCaptured : Screen(Text.empty()) {
    lateinit var packetList: ComponentPacketList
    lateinit var paketViewer: ComponentPacketViewer

    override fun init() {
        // This will be to filter out packet types
        // A search box would also be cool
        val thingWidth = (width - 40) / 3f
        packetList = ComponentPacketList(20f, 20f, width - 60f - thingWidth, height - 40f)
        paketViewer = ComponentPacketViewer(width - thingWidth - 20f, 20f, thingWidth, height - 40f)
        super.init()
    }

    override fun render(ctx: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(ctx, mouseX, mouseY, delta)
        UI.render {
            packetList.render(ctx!!, delta, mouseX.toFloat(), mouseY.toFloat())
            paketViewer.render(ctx, delta, mouseX.toFloat(), mouseY.toFloat())
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        packetList.click(mouseX, mouseY, button)
        paketViewer.click(mouseX, mouseY, button)
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean {
        packetList.scroll(mouseX, mouseY, horizontalAmount, verticalAmount)
        paketViewer.scroll(mouseX, mouseY, horizontalAmount, verticalAmount)
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)
    }
}