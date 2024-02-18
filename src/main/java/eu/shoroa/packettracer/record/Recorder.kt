package eu.shoroa.packettracer.record

import net.minecraft.network.packet.Packet

object Recorder {
    val incomingPackets = mutableListOf<RecordedPaket>()
    val outgoingPackets = mutableListOf<RecordedPaket>()

    var recording = false

    var startTime = 0L
    var endTime = 0L

    fun start() {
        incomingPackets.clear()
        outgoingPackets.clear()
        recording = true
        startTime = System.currentTimeMillis()
    }

    fun stop() {
        recording = false
        endTime = System.currentTimeMillis()
    }

    class RecordedPaket(val packet: Packet<*>, val time: Long)
}