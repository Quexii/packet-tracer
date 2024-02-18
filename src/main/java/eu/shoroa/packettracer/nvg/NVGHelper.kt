package eu.shoroa.packettracer.nvg

import eu.shoroa.packettracer.client.PacketTracerClient
import eu.shoroa.packettracer.ui.Material
import org.lwjgl.BufferUtils
import org.lwjgl.nanovg.NVGColor
import org.lwjgl.nanovg.NVGPaint
import org.lwjgl.system.MemoryUtil
import eu.shoroa.packettracer.nvg.NVGWrapper as nw
import java.awt.Color
import java.io.InputStream
import java.nio.*
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

object NVGHelper {
    private val colorCache = mutableListOf<NVGColor>()
    private val paintCache = mutableListOf<NVGPaint>()
    private val fontCache = mutableListOf<ByteBuffer>()
    val bufferCache = mutableListOf<Buffer>()

    fun nvgcolor(color: Color) =
        NVGColor.calloc().r(color.red / 255f).g(color.green / 255f).b(color.blue / 255f).a(color.alpha / 255f)
            .also { colorCache.add(it) }

    fun nvgpaint() = NVGPaint.calloc().also { paintCache.add(it) }

    fun freeCache() {
        for (x in colorCache) x.free()
        for (x in paintCache) x.free()
        for (x in bufferCache) MemoryUtil.memFree(x)
        colorCache.clear()
        paintCache.clear()
        bufferCache.clear()
    }

    fun path(ui: nw.() -> Unit) {
        nw.beginPath()
        ui.invoke(nw)
        nw.closePath()
    }

    fun path(x: Float, y: Float, ui: nw.() -> Unit) {
        nw.beginPath()
        nw.moveTo(x, y)
        ui.invoke(nw)
        nw.closePath()
    }

    fun finish(color: Color, mat: Material) {
        if (mat.stroke) {
            nw.strokeWidth(mat.strokeWidth)
            nw.strokeColor(nvgcolor(color))
            nw.stroke()
        } else {
            nw.fillColor(nvgcolor(color))
            nw.fill()
        }
    }

    fun finish(paint: NVGPaint, mat: Material) {
        if (mat.stroke) {
            nw.strokeWidth(mat.strokeWidth)
            nw.strokePaint(paint)
            nw.stroke()
        } else {
            nw.fillPaint(paint)
            nw.fill()
        }
    }

    fun initFont(file: String, name: String) = ioToBuffer(file, 1024)?.let {
        nw.createFontMem(name, it, false)
        fontCache.add(it)
    } ?: throw IllegalArgumentException("Font not found")

    fun ioToBuffer(path: String, size: Int): ByteBuffer? {
        var buffer: ByteBuffer
        val source: InputStream? = PacketTracerClient::class.java.getResourceAsStream("/$path")
        val rbc: ReadableByteChannel = Channels.newChannel(source)
        buffer = BufferUtils.createByteBuffer(size)
        while (true) {
            val bytes = rbc.read(buffer)
            if (bytes == -1) {
                break
            }
            if (buffer.remaining() == 0) {
                val newBuffer: ByteBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 3 / 2)
                buffer.flip()
                newBuffer.put(buffer)
                buffer = newBuffer
            }
        }
        buffer.flip()
        return MemoryUtil.memSlice(buffer)
    }

    inline fun <reified T : Buffer> allocBuffer(size: Int): T {
        return T::class.java.getMethod("allocate", Int::class.java).invoke(null, size) as T
//        val buffer = when (T::class.java) {
//            ByteBuffer::class.java -> ByteBuffer.allocate(size) as T
//            FloatBuffer::class.java -> FloatBuffer.allocate(size) as T
//            DoubleBuffer::class.java -> DoubleBuffer.allocate(size) as T
//            IntBuffer::class.java -> IntBuffer.allocate(size) as T
//            ShortBuffer::class.java -> ShortBuffer.allocate(size) as T
//            LongBuffer::class.java -> LongBuffer.allocate(size) as T
//            else -> throw IllegalArgumentException("Unsupported buffer type")
//        }
    }
}