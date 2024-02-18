package eu.shoroa.packettracer.ui

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import eu.shoroa.packettracer.nvg.NVGHelper.allocBuffer
import eu.shoroa.packettracer.nvg.NVGHelper.path
import eu.shoroa.packettracer.nvg.NVGHelper.nvgpaint
import eu.shoroa.packettracer.nvg.NVGHelper.nvgcolor
import eu.shoroa.packettracer.nvg.NVGHelper.freeCache
import eu.shoroa.packettracer.nvg.NVGHelper.finish
import eu.shoroa.packettracer.nvg.NVGHelper.initFont
import net.minecraft.client.MinecraftClient
import java.awt.Color
import java.nio.FloatBuffer
import eu.shoroa.packettracer.nvg.NVGWrapper as n

object UI {
    private val glTextureCache = mutableMapOf<Int, Int>()
    val client = MinecraftClient.getInstance()

    fun init() {
        n.glType(n.GLType.GL3)
        n.ctx = n.create(n.NVG_ANTIALIAS)

        initFont("assets/packettracer/fonts/regular.ttf", "regular")
        initFont("assets/packettracer/fonts/bold.ttf", "bold")
        initFont("assets/packettracer/fonts/semibold.ttf", "semibold")
        initFont("assets/packettracer/fonts/black.ttf", "black")
        initFont("assets/packettracer/fonts/extrabold.ttf", "extrabold")
        initFont("assets/packettracer/fonts/extralight.ttf", "extralight")
        initFont("assets/packettracer/fonts/thin.ttf", "thin")
        initFont("assets/packettracer/fonts/icon.ttf", "icon")
    }

    fun free() {
        n.delete()
    }

    fun beginFrame() {
        val w = client.window.scaledWidth.toFloat()
        val h = client.window.scaledHeight.toFloat()

        n.beginFrame(w,h, client.window.scaleFactor.toFloat())
    }

    fun endFrame() {
        n.endFrame()
        freeCache()
    }

    fun render(func: UI.() -> Unit) {
        beginFrame()
        func.invoke(this)
        endFrame()
        GlStateManager._disableCull();
        GlStateManager._disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
    }

    private fun shapelessDraw(x: Float, y: Float, w: Float, h: Float, material: Material, f: () -> Unit) {
        val id = material.id
        val paint = nvgpaint()

        path {
            f()
            when (id) {
                Material.ID_GRADIENT_HORIZONTAL -> linearGradient(
                    x, y, x + w, y, nvgcolor(material.data[0] as Color), nvgcolor(material.data[1] as Color), paint
                )

                Material.ID_GRADIENT_VERTICAL -> linearGradient(
                    x, y, x, y + h, nvgcolor(material.data[0] as Color), nvgcolor(material.data[1] as Color), paint
                )
            }
            if (id == 0) finish(material.data[0] as Color, material)
            else finish(paint, material)
        }
    }

    /*
     * Primitive shapes
     */
    fun rect(x: Float, y: Float, w: Float, h: Float, material: Material) = shapelessDraw(x, y, w, h, material) {
        n.rect(x, y, w, h)
    }

    fun rrect(x: Float, y: Float, w: Float, h: Float, r: Float, material: Material) =
        shapelessDraw(x, y, w, h, material) {
            n.roundedRect(x, y, w, h, r)
        }

    fun rrect(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        radTopLeft: Float,
        radTopRight: Float,
        radBottomRight: Float,
        radBottomLeft: Float,
        material: Material
    ) = shapelessDraw(x, y, w, h, material) {
        n.roundedRectVarying(x, y, w, h, radTopLeft, radTopRight, radBottomRight, radBottomLeft)
    }

    fun circle(x: Float, y: Float, r: Float, material: Material) = shapelessDraw(x - r /2f, y - r /2f, r, r, material) {
        n.circle(x, y, r)
    }

    /*
     * Text stuff
     */

    fun text(
            text: String,
            x: Float,
            y: Float,
            size: Float,
            font: String,
            color: Color,
            alignment: Alignment = Alignment.LEFT_TOP,
            blur: Float = 0f
    ) = path {
        fontFace(font)
        fontSize(size)
        fontBlur(blur)
        fillColor(nvgcolor(color))
        textAlign(alignment.alignment)
        text(x, y, text)
    }

    fun textWidth(text: String, size: Float, font: String) : Float {
        var width = 0f
        path {
            fontFace(font)
            fontSize(size)
            width = textBounds(0f, 0f, text, allocBuffer(4))
        }
        return width
    }

    fun textHeight(size: Float, font: String) : Float {
        val lineh = allocBuffer<FloatBuffer>(1)
        path {
            fontFace(font)
            fontSize(size)
            textMetrics(allocBuffer(1), allocBuffer(1), lineh)
        }
        return lineh.get(0)
    }

    /*
     * Image stuff
     */

    fun glImage(x: Float, y: Float, w: Float, h: Float, radius: Float, texture: Int, alpha: Float = 1f) {
        if(!glTextureCache.containsKey(texture)) {
            glTextureCache[texture] = n.createImageFromHandle(texture, w.toInt(), h.toInt(), n.NVG_IMAGE_FLIPY + n.NVG_IMAGE_PREMULTIPLIED + n.NVG_IMAGE_GENERATE_MIPMAPS)
        }
        val image = glTextureCache[texture]!!
        val paint = nvgpaint()
        path {
            imagePattern(x, y, w, h, 0f, image, alpha, paint)
            roundedRect(x, y, w, h, radius)
            fillPaint(paint)
        }
        text("$image", x, y, 10f, "regular", Color.WHITE)
    }

    enum class Alignment(val alignment: Int) {
        LEFT_TOP(n.NVG_ALIGN_LEFT or n.NVG_ALIGN_TOP), CENTER_TOP(n.NVG_ALIGN_CENTER or n.NVG_ALIGN_TOP), RIGHT_TOP(
            n.NVG_ALIGN_RIGHT or n.NVG_ALIGN_TOP
        ),
        LEFT_MIDDLE(n.NVG_ALIGN_LEFT or n.NVG_ALIGN_MIDDLE), CENTER_MIDDLE(n.NVG_ALIGN_CENTER or n.NVG_ALIGN_MIDDLE), RIGHT_MIDDLE(
            n.NVG_ALIGN_RIGHT or n.NVG_ALIGN_MIDDLE
        ),
        LEFT_BOTTOM(n.NVG_ALIGN_LEFT or n.NVG_ALIGN_BOTTOM), CENTER_BOTTOM(n.NVG_ALIGN_CENTER or n.NVG_ALIGN_BOTTOM), RIGHT_BOTTOM(
            n.NVG_ALIGN_RIGHT or n.NVG_ALIGN_BOTTOM
        )
    }
}