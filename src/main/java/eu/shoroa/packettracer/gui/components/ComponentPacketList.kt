package eu.shoroa.packettracer.gui.components

import eu.shoroa.packettracer.nvg.NVGWrapper
import eu.shoroa.packettracer.record.Recorder
import eu.shoroa.packettracer.ui.Material
import eu.shoroa.packettracer.ui.UI
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket
import java.awt.Color
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date
import kotlin.math.max

class ComponentPacketList(x: Float, y: Float, width: Float, height: Float) : Component(x, y, width, height) {
    private var listInScroll = 0f
    private var _listInScrollRAW = 0f
    private var listInHoverPos = 0f
    private var _listInHoverPosRAW = 0f

    private var listOutScroll = 0f
    private var _listOutScrollRAW = 0f
    private var listOutHoverPos = 0f
    private var _listOutHoverPosRAW = 0f

    var selected: Recorder.RecordedPaket? = null

    override fun render(ctx: DrawContext, delta: Float, mouseX: Float, mouseY: Float) {
        val client = MinecraftClient.getInstance()
        val listWidth = (width - 30f) / 2f
        val visibleHeight = height - 30

        val totalHeightIn = Recorder.incomingPackets.size * 10f
        val scrollableHeightIn = totalHeightIn - visibleHeight
        _listInScrollRAW = _listInScrollRAW.coerceIn(0f, max(0f,scrollableHeightIn))

        val totalHeightOut = Recorder.outgoingPackets.size * 10f
        val scrollableHeightOut = totalHeightOut - visibleHeight
        _listOutScrollRAW = _listOutScrollRAW.coerceIn(0f, max(0f,scrollableHeightOut))


        listInHoverPos = lerp(listInHoverPos, _listInHoverPosRAW, delta)
        listOutHoverPos = lerp(listOutHoverPos, _listOutHoverPosRAW, delta)
        listInScroll = lerp(listInScroll, _listInScrollRAW, delta)
        listOutScroll = lerp(listOutScroll, _listOutScrollRAW, delta)

        UI.rrect(x, y, width, height, 4f, Material.SOLID(Color(0x1D1D1D)))
        UI.rrect(x, y, width, height, 4f, Material.SOLID(Color(0x404040)).stroke(.5f))
        UI.rrect(x + 10, y + 20, listWidth - 20, height - 30, 4f, Material.SOLID(Color(0x181818)))
        UI.text("Incoming", x + listWidth / 2f, y + 10, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)

        val scrollbarHeightIn = (visibleHeight / totalHeightIn) * visibleHeight
        val scrollbarYIn = y + 20 + (listInScroll / scrollableHeightIn) * (visibleHeight - scrollbarHeightIn)
        if(scrollbarYIn < visibleHeight)
            UI.rrect(x + listWidth - 8, scrollbarYIn, 4f, scrollbarHeightIn, 2f, Material.SOLID(Color(0x404040)))

        NVGWrapper.save()
        NVGWrapper.scissor(x + 10, y + 20, listWidth - 20, height - 30)
        UI.rrect(x + 12, y + 22 + listInHoverPos - listInScroll, listWidth - 24, 10f, 2f, Material.SOLID(Color(0x303030)))

        if(!isHovered(mouseX, mouseY,x + 10, y + 20, listWidth - 20, height - 30)) _listInHoverPosRAW = -20f
        var pyIn = 0f
        for (pObj in Recorder.incomingPackets) {
            val hovered = isHovered(mouseX, mouseY, x + 12f, y + 22f + pyIn - listInScroll, listWidth - 24f, 10f)
            val timestamp = pObj.time - Recorder.startTime
            if(hovered)
                _listInHoverPosRAW = pyIn
            if(selected == pObj) {
                UI.rrect(x + 12, y + 22 + pyIn - listInScroll, listWidth - 24, 10f, 2f, Material.SOLID(Color(0x303030)))
            }
            UI.text("${Recorder.incomingPackets.indexOf(pObj)}  ${pObj.packet.javaClass.simpleName}", x + 14, y + 24 + pyIn - listInScroll, 8f, "regular", Color(0xE1E1E1))
            UI.text("$timestamp", x + listWidth - 14, y + 24 + pyIn - listInScroll, 8f, "regular", Color(0xE1E1E1), UI.Alignment.RIGHT_TOP)
            pyIn += 10
        }
        NVGWrapper.restore()

        UI.rrect(x + 20 + listWidth, y + 20, listWidth - 20, height - 30, 4f, Material.SOLID(Color(0x181818)))
        UI.text("Outgoing", x + listWidth * 1.5f, y + 10, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)

        val scrollbarHeightOut = (visibleHeight / totalHeightOut) * visibleHeight
        val scrollbarYOut = y + 20 + (listOutScroll / scrollableHeightOut) * (visibleHeight - scrollbarHeightOut)
        if(scrollbarHeightOut < visibleHeight)
            UI.rrect(x + width - 18, scrollbarYOut, 4f, scrollbarHeightOut, 2f, Material.SOLID(Color(0x404040)))

        NVGWrapper.save()
        NVGWrapper.scissor(x + 20 + listWidth, y + 20, listWidth - 20, height - 30)
        UI.rrect(x + 22 + listWidth, y + 22 + listOutHoverPos - listOutScroll, listWidth - 24, 10f, 2f, Material.SOLID(Color(0x303030)))

        if(!isHovered(mouseX, mouseY,x + 20 + listWidth, y + 20, listWidth - 20, height - 30)) _listOutHoverPosRAW = -20f
        var pyOut = 0f
        for (pObj in Recorder.outgoingPackets) {
            val hovered = isHovered(mouseX, mouseY, x + 22f + listWidth, y + 22f + pyOut - listOutScroll, listWidth - 24f, 10f)
            val timestamp = pObj.time - Recorder.startTime
            if(hovered)
                _listOutHoverPosRAW = pyOut
            if(selected == pObj) {
                UI.rrect(x + 22 + listWidth, y + 22 + pyOut - listOutScroll, listWidth - 24, 10f, 2f, Material.SOLID(Color(0x303030)))
            }
            UI.text("${Recorder.outgoingPackets.indexOf(pObj)}  ${pObj.packet.javaClass.simpleName}", x + 24 + listWidth, y + 24 + pyOut - listOutScroll, 8f, "regular", Color(0xE1E1E1))
            UI.text("$timestamp", x + 10 + listWidth + listWidth - 14, y + 24 + pyOut - listOutScroll, 8f, "regular", Color(0xE1E1E1), UI.Alignment.RIGHT_TOP)
            pyOut += 10
        }
        NVGWrapper.restore()

        val formatter = SimpleDateFormat("ss.SSS")
        UI.text("START: ${formatter.format(Date.from(Instant.ofEpochMilli(Recorder.startTime)))}", 2f, 2f, 7f, "semibold", Color(0xE1E1E1))
        UI.text("END: ${formatter.format(Date.from(Instant.ofEpochMilli(Recorder.endTime)))}", 2f, 10f, 7f, "semibold", Color(0xE1E1E1))
        val recorderFor = Duration.between(Instant.ofEpochMilli(Recorder.startTime), Instant.ofEpochMilli(Recorder.endTime))
        UI.text("TOTAL: ${recorderFor.toSeconds()}.${recorderFor.toMillis()}", 2f, 18f, 7f, "semibold", Color(0xE1E1E1))
        UI.text("COLLECTED: ${Recorder.incomingPackets.size + Recorder.outgoingPackets.size} [${Recorder.incomingPackets.size}/${Recorder.outgoingPackets.size}]", 2f, 26f, 7f, "semibold", Color(0xE1E1E1))
    }

    override fun click(mouseX: Double, mouseY: Double, button: Int) {
        val listWidth = (width - 30f) / 2f
        var pyIn = 0f
        if(button == 0) {
            for (pObj in Recorder.incomingPackets) {
                val hovered = isHovered(mouseX, mouseY, x + 12f, y + 22f + pyIn - listInScroll, listWidth - 24f, 10f)
                if (hovered) {
                    selected = pObj
                }
                pyIn += 10
            }
            var pyOut = 0f
            for (pObj in Recorder.outgoingPackets) {
                val hovered = isHovered(mouseX, mouseY, x + 22f + listWidth, y + 22f + pyOut - listOutScroll, listWidth - 24f, 10f)
                if (hovered) {
                    selected = pObj
                }
                pyOut += 10
            }
        }
    }

    override fun scroll(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double) {
        val listWidth = (width - 30f) / 2f
        if(isHovered(mouseX, mouseY,x + 10, y + 20, listWidth - 20, height - 30)) {
            _listInScrollRAW -= verticalAmount.toFloat() * 30f
        }
    }

    fun lerp(a: Float, b: Float, t: Float): Float {
        return a + (b - a) * t
    }
}