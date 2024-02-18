package eu.shoroa.packettracer.viewer

import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket

class ViewerEntityAttributesS2C : Viewer<EntityAttributesS2CPacket>(EntityAttributesS2CPacket::class) {
    override fun render(packet: EntityAttributesS2CPacket, x: Float, y: Float, width: Float, height: Float) {

    }
}