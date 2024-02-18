package eu.shoroa.packettracer.viewer

import eu.shoroa.packettracer.ui.Material
import eu.shoroa.packettracer.ui.UI
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
import java.awt.Color

class ViewerPlayerMoveC2S() : Viewer<PlayerMoveC2SPacket>(PlayerMoveC2SPacket::class) {
    override fun render(packet: PlayerMoveC2SPacket, x: Float, y: Float, width: Float, height: Float) {
        UI.apply {
            text("PlayerMoveC2SPacket", x + width / 2f, y + 10, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)
            arrayOf(
                    Pair("X", packet.getX(0.0).toString()),
                    Pair("Y", packet.getY(0.0).toString()),
                    Pair("Z", packet.getZ(0.0).toString()),
                    Pair("Yaw", packet.getYaw(0f).toString()),
                    Pair("Pitch", packet.getPitch(0f).toString()),
                    Pair("On Ground", packet.isOnGround.toString()),
                    Pair("On Ground", packet.changesPosition().toString()),
                    Pair("On Ground", packet.changesLook().toString())
            ).forEachIndexed { index, s ->
                val lineH = 10f
                if(index % 2 == 0 || index == 0)
                    rrect(x, y + 10 + lineH * (index + 1), width, lineH, 2f, Material.SOLID(Color(0x303030)))
                text("$index", x + 2, y + 20 + lineH * (index + 1) - lineH / 2f + 1, 8f, "regular", Color(0xE1E1E1), UI.Alignment.LEFT_MIDDLE)
                text(s.first, x + 12, y + 20 + lineH * (index + 1) - lineH / 2f + 1, 8f, "regular", Color(0xE1E1E1), UI.Alignment.LEFT_MIDDLE)
                text(s.second, x + width - 2, y + 20 + lineH * (index + 1) - lineH / 2f + 1, 8f, "regular", Color(0xE1E1E1), UI.Alignment.RIGHT_MIDDLE)
            }
        }
    }
}