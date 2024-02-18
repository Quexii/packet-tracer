package eu.shoroa.packettracer.viewer

import net.minecraft.network.listener.PacketListener
import net.minecraft.network.packet.Packet
import kotlin.reflect.KClass

abstract class Viewer<T : Packet<*>>(val packetClass: KClass<T>) {
    companion object {
        private val viewers = mutableSetOf<Viewer<*>>()
        fun init() {
            viewers.add(ViewerPlayerMoveC2S())
            viewers.add(ViewerEntityDamageS2C())
        }

        @Suppress("UNCHECKED_CAST")
        fun <T : Packet<*>> getViewer(packet: T): Viewer<T>? {
            for(viewer in viewers) {
                if(packet::class == viewer.packetClass || packet.javaClass.superclass == viewer.packetClass.java) {
                    return viewer as Viewer<T>
                }
            }
            return null
        }
    }

    abstract fun render(packet: T, x: Float, y: Float, width: Float, height: Float)
}