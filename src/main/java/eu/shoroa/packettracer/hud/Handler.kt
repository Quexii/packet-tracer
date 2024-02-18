package eu.shoroa.packettracer.hud

import eu.shoroa.packettracer.client.PacketTracerClient
import eu.shoroa.packettracer.gui.ScreenCaptured
import eu.shoroa.packettracer.notification.NotificationManager
import eu.shoroa.packettracer.record.Recorder
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.network.listener.PacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.util.ActionResult
import java.util.concurrent.CopyOnWriteArrayList

object Handler {
    private val client = MinecraftClient.getInstance()

    fun tick(client: MinecraftClient) {
        NotificationManager.tick(client)

        if(PacketTracerClient.keyStartRecording.wasPressed()) {
            if(Recorder.recording) return
            NotificationManager.push("Packet Tracer", "Recording started", 0xFF00FF00.toInt())
            Recorder.start()
        }
        if(PacketTracerClient.keyStopRecording.wasPressed()) {
            if(!Recorder.recording) return
            NotificationManager.push("Packet Tracer", "Recording stopped", 0xFFFF0000.toInt())
            Recorder.stop()
        }
        if(PacketTracerClient.keyOpenGui.wasPressed()) {
            client.setScreen(ScreenCaptured())
        }

        if(client.player?.isDead == true) {
            if(!Recorder.recording) return
            NotificationManager.push("Packet Tracer", "Recording stopped", 0xFFFF0000.toInt())
            Recorder.stop()
        }
    }

    fun render(ctx: DrawContext, delta: Float) {
        NotificationManager.render(ctx, delta)

        if(Recorder.recording) {
            ctx.drawText(client.textRenderer, "Recording...", 0, 0, 0xFFFFFF, true)
        }
    }

    fun packetIN(packet: Packet<*>, listener: PacketListener): ActionResult {
        if(Recorder.recording) Recorder.incomingPackets.add(Recorder.RecordedPaket(packet, System.currentTimeMillis()))
        return ActionResult.PASS
    }

    fun packetOUT(packet: Packet<*>): ActionResult {
        if(Recorder.recording) Recorder.outgoingPackets.add(Recorder.RecordedPaket(packet, System.currentTimeMillis()))
        return ActionResult.PASS
    }
}