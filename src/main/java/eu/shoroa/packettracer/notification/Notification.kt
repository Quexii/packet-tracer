package eu.shoroa.packettracer.notification

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.max
import kotlin.math.min

const val TIMEOUT = 2000L

class Notification(val title: String, val message: String, val color: Int, val time: Long = System.currentTimeMillis()) {
    private val client = MinecraftClient.getInstance()
    fun render(ctx: DrawContext, x: Int, y: Int) {
        val progress = (((System.currentTimeMillis() - time).toFloat() / TIMEOUT) * 178).toInt()
        val renderProgress = min(178, max(0, progress))

        ctx.fill(x, y, x + 180, y + 30, 0x80000000.toInt())
        ctx.fill(x, y, x + 1, y + 30, color)
        ctx.fill(x + 179, y, x + 180, y + 30, color)
        ctx.fill(x, y, x + 180, y + 1, color)
        ctx.fill(x, y + 29, x + 180, y + 30, color)
        ctx.fill(x + 1, y + 28, x + 1 + renderProgress, y + 29, -1)
        ctx.drawText(client.textRenderer, title, x + 8, y + 6, -1, true)
        ctx.drawText(client.textRenderer, message, x + 8, y + 16, -1, true)
    }
}

object NotificationManager {
    private val notifications = CopyOnWriteArrayList<Notification>()

    fun tick(client: MinecraftClient) {
        notifications.removeIf { System.currentTimeMillis() - it.time > TIMEOUT }
    }

    fun render(ctx: DrawContext, delta: Float) {
        var y = 10
        for (notification in notifications) {
            notification.render(ctx, MinecraftClient.getInstance().window.scaledWidth - 190, y)
            y += 32
        }
    }

    fun push(title: String, message: String, color: Int) {
        notifications.add(Notification(title, message, color))
    }
}