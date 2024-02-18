package eu.shoroa.packettracer.nvg

import org.lwjgl.nanovg.*
import org.lwjgl.nanovg.NanoVG.*
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

object NVGWrapper {
    const val NVG_PI: Float = 3.1415927f
    const val NVG_CCW: Int = 1
    const val NVG_CW: Int = 2
    const val NVG_SOLID: Int = 1
    const val NVG_HOLE: Int = 2
    const val NVG_BUTT: Int = 0
    const val NVG_ROUND: Int = 1
    const val NVG_SQUARE: Int = 2
    const val NVG_BEVEL: Int = 3
    const val NVG_MITER: Int = 4
    const val NVG_ALIGN_LEFT: Int = 1
    const val NVG_ALIGN_CENTER: Int = 2
    const val NVG_ALIGN_RIGHT: Int = 4
    const val NVG_ALIGN_TOP: Int = 8
    const val NVG_ALIGN_MIDDLE: Int = 16
    const val NVG_ALIGN_BOTTOM: Int = 32
    const val NVG_ALIGN_BASELINE: Int = 64
    const val NVG_ZERO: Int = 1
    const val NVG_ONE: Int = 2
    const val NVG_SRC_COLOR: Int = 4
    const val NVG_ONE_MINUS_SRC_COLOR: Int = 8
    const val NVG_DST_COLOR: Int = 16
    const val NVG_ONE_MINUS_DST_COLOR: Int = 32
    const val NVG_SRC_ALPHA: Int = 64
    const val NVG_ONE_MINUS_SRC_ALPHA: Int = 128
    const val NVG_DST_ALPHA: Int = 256
    const val NVG_ONE_MINUS_DST_ALPHA: Int = 512
    const val NVG_SRC_ALPHA_SATURATE: Int = 1024
    const val NVG_SOURCE_OVER: Int = 0
    const val NVG_SOURCE_IN: Int = 1
    const val NVG_SOURCE_OUT: Int = 2
    const val NVG_ATOP: Int = 3
    const val NVG_DESTINATION_OVER: Int = 4
    const val NVG_DESTINATION_IN: Int = 5
    const val NVG_DESTINATION_OUT: Int = 6
    const val NVG_DESTINATION_ATOP: Int = 7
    const val NVG_LIGHTER: Int = 8
    const val NVG_COPY: Int = 9
    const val NVG_XOR: Int = 10
    const val NVG_IMAGE_GENERATE_MIPMAPS: Int = 1
    const val NVG_IMAGE_REPEATX: Int = 2
    const val NVG_IMAGE_REPEATY: Int = 4
    const val NVG_IMAGE_FLIPY: Int = 8
    const val NVG_IMAGE_PREMULTIPLIED: Int = 16
    const val NVG_IMAGE_NEAREST: Int = 32
    const val NVG_IMAGE_NODELETE: Int = 65536
    const val NVG_ANTIALIAS: Int = 1
    const val NVG_STENCIL_STROKES: Int = 2
    const val NVG_DEBUG: Int = 4

    var ctx = 0L
    private var glType = GLType.GL3

    fun glType(glType: GLType) {
        NVGWrapper.glType = glType
    }

    fun createImageFromHandle(
        textureId: Int,
        w: Int,
        h: Int,
        flags: Int
    ): Int {
        return when(glType) {
            GLType.GL2 -> NanoVGGL2.nvglCreateImageFromHandle(ctx, textureId, w, h, flags)
            GLType.GL3 -> NanoVGGL3.nvglCreateImageFromHandle(ctx, textureId, w, h, flags)
            GLType.GLES2 -> NanoVGGLES2.nvglCreateImageFromHandle(ctx, textureId, w, h, flags)
            GLType.GLES3 -> NanoVGGLES3.nvglCreateImageFromHandle(ctx, textureId, w, h, flags)
        }
    }

    fun imageHandle(image: Int):Int {
        return when(glType) {
            GLType.GL2 -> NanoVGGL2.nvglImageHandle(ctx, image)
            GLType.GL3 -> NanoVGGL3.nvglImageHandle(ctx, image)
            GLType.GLES2 -> NanoVGGLES2.nvglImageHandle(ctx, image)
            GLType.GLES3 -> NanoVGGLES3.nvglImageHandle(ctx, image)
        }
    }

    fun create(flags: Int): Long {
        return when(glType) {
            GLType.GL2 -> NanoVGGL2.nvgCreate(flags)
            GLType.GL3 -> NanoVGGL3.nvgCreate(flags)
            GLType.GLES2 -> NanoVGGLES2.nvgCreate(flags)
            GLType.GLES3 -> NanoVGGLES3.nvgCreate(flags)
        }
    }

    fun delete() {
        when(glType) {
            GLType.GL2 -> NanoVGGL2.nvgDelete(ctx)
            GLType.GL3 -> NanoVGGL3.nvgDelete(ctx)
            GLType.GLES2 -> NanoVGGLES2.nvgDelete(ctx)
            GLType.GLES3 -> NanoVGGLES3.nvgDelete(ctx)
        }
    }

    fun createFramebuffer(
        w: Int,
        h: Int,
        imageFlags: Int
    ) : NVGLUFramebuffer {
        return when(glType) {
            GLType.GL2 -> NanoVGGL2.nvgluCreateFramebuffer(ctx, w, h, imageFlags)!!
            GLType.GL3 -> NanoVGGL3.nvgluCreateFramebuffer(ctx, w, h, imageFlags)!!
            GLType.GLES2 -> NanoVGGLES2.nvgluCreateFramebuffer(ctx, w, h, imageFlags)!!
            GLType.GLES3 -> NanoVGGLES3.nvgluCreateFramebuffer(ctx, w, h, imageFlags)!!
        }
    }

    fun bindFramebuffer(
        fb: NVGLUFramebuffer?
    ) {
        when(glType) {
            GLType.GL2 -> NanoVGGL2.nvgluBindFramebuffer(ctx, fb)
            GLType.GL3 -> NanoVGGL3.nvgluBindFramebuffer(ctx, fb)
            GLType.GLES2 -> NanoVGGLES2.nvgluBindFramebuffer(ctx, fb)
            GLType.GLES3 -> NanoVGGLES3.nvgluBindFramebuffer(ctx, fb)
        }
    }

    fun deleteFramebuffer(
        fb: NVGLUFramebuffer
    ) {
        when(glType) {
            GLType.GL2 -> NanoVGGL2.nvgluDeleteFramebuffer(ctx, fb)
            GLType.GL3 -> NanoVGGL3.nvgluDeleteFramebuffer(ctx, fb)
            GLType.GLES2 -> NanoVGGLES2.nvgluDeleteFramebuffer(ctx, fb)
            GLType.GLES3 -> NanoVGGLES3.nvgluDeleteFramebuffer(ctx, fb)
        }
    }

    enum class GLType {
        GL2, GL3, GLES2, GLES3
    }

    fun beginFrame(
        windowWidth: Float,
        windowHeight: Float,
        devicePixelRatio: Float
    ) = nvgBeginFrame(ctx, windowWidth, windowHeight, devicePixelRatio)

    fun cancelFrame() = nvgCancelFrame(ctx)

    fun endFrame() = nvgEndFrame(ctx)
    
    fun globalCompositeOperation( op: Int) = nvgGlobalCompositeOperation(ctx, op)
    fun globalCompositeBlendFunc( sfactor: Int, dfactor: Int) = nvgGlobalCompositeBlendFunc(ctx, sfactor, dfactor)
    fun globalCompositeBlendFuncSeparate(
        srcRGB: Int,
        dstRGB: Int,
        srcAlpha: Int,
        dstAlpha: Int
    ) = nvgGlobalCompositeBlendFuncSeparate(ctx, srcRGB, dstRGB, srcAlpha, dstAlpha)
    fun RGB(
        r: Byte,
        g: Byte,
        b: Byte,
        __result: NVGColor
    ) = nvgRGB(r, g, b, __result)
    fun RGBf(r: Float, g: Float, b: Float, __result: NVGColor) = nvgRGBf(r, g, b, __result)
    fun RGBA(
        r: Byte,
        g: Byte,
        b: Byte,
        a: Byte,
        __result: NVGColor
    ) = nvgRGBA(r, g, b, a, __result)
    fun RGBAf(r: Float, g: Float, b: Float, a: Float, __result: NVGColor) = nvgRGBAf(r, g, b, a, __result)

    
    fun lerpRGBA(
        c0: NVGColor,
        c1: NVGColor,
        u: Float,
        __result: NVGColor
    ) = nvgLerpRGBA(c0, c1, u, __result)

    
    fun transRGBA(
        c0: NVGColor,
        a: Byte,
        __result: NVGColor
    ) = nvgTransRGBA(c0, a, __result)

    
    fun transRGBAf(
        c0: NVGColor,
        a: Float,
        __result: NVGColor
    ) = nvgTransRGBAf(c0, a, __result)

    
    fun HSL(h: Float, s: Float, l: Float, __result: NVGColor) = nvgHSL(h, s, l, __result)

    
    fun HSLA(
        h: Float,
        s: Float,
        l: Float,
        a: Byte,
        __result: NVGColor
    ) = nvgHSLA(h, s, l, a, __result)

    fun save() = nvgSave(ctx)

    fun restore() = nvgRestore(ctx)

    fun reset() = nvgReset(ctx)

    fun shapeAntiAlias(enabled: Boolean) = nvgShapeAntiAlias(ctx, enabled)

    fun strokeColor( color: NVGColor) = nvgStrokeColor(ctx, color)

    fun strokePaint( paint: NVGPaint) = nvgStrokePaint(ctx, paint)

    fun fillColor( color: NVGColor) = nvgFillColor(ctx, color)

    fun fillPaint( paint: NVGPaint) = nvgFillPaint(ctx, paint)

    fun miterLimit( limit: Float) = nvgMiterLimit(ctx, limit)

    fun strokeWidth( size: Float) = nvgStrokeWidth(ctx, size)

    fun lineCap( cap: Int) = nvgLineCap(ctx, cap)

    fun lineJoin( join: Int) = nvgLineJoin(ctx, join)

    fun globalAlpha( alpha: Float) = nvgGlobalAlpha(ctx, alpha)

    fun resetTransform() = nvgResetTransform(ctx)

    fun transform(
        
        a: Float,
        b: Float,
        c: Float,
        d: Float,
        e: Float,
        f: Float
    ) = nvgTransform(ctx, a, b, c, d, e, f)

    fun translate( x: Float, y: Float) = nvgTranslate(ctx, x, y)

    fun rotate( angle: Float) = nvgRotate(ctx, angle)

    fun skewX( angle: Float) = nvgSkewX(ctx, angle)

    fun skewY( angle: Float) = nvgSkewY(ctx, angle)

    fun scale( x: Float, y: Float) = nvgScale(ctx, x, y)

    fun currentTransform( xform: FloatBuffer) = nvgCurrentTransform(ctx, xform)

    fun transformIdentity(dst: FloatBuffer) = nvgTransformIdentity(dst)

    fun transformTranslate(dst: FloatBuffer, tx: Float, ty: Float) = nvgTransformTranslate(dst, tx, ty)

    fun transformScale(dst: FloatBuffer, sx: Float, sy: Float) = nvgTransformScale(dst, sx, sy)

    fun transformRotate(dst: FloatBuffer, a: Float) = nvgTransformRotate(dst, a)

    fun transformSkewX(dst: FloatBuffer, a: Float) = nvgTransformSkewX(dst, a)

    fun transformSkewY(dst: FloatBuffer, a: Float) = nvgTransformSkewY(dst, a)

    fun transformMultiply(dst: FloatBuffer, src: FloatBuffer) = nvgTransformMultiply(dst, src)

    fun transformPremultiply(
        dst: FloatBuffer,
        src: FloatBuffer
    ) = nvgTransformPremultiply(dst, src)

    fun transformInverse(
        dst: FloatBuffer,
        src: FloatBuffer
    ) = nvgTransformInverse(dst, src)

    fun transformPoint(
        dstx: FloatBuffer,
        dsty: FloatBuffer,
        xform: FloatBuffer,
        srcx: Float,
        srcy: Float
    ) = nvgTransformPoint(
            dstx,
            dsty,
            xform,
            srcx,
            srcy
        )

    fun createImage(
        filename: ByteBuffer,
        imageFlags: Int
    ) = nvgCreateImage(ctx, filename, imageFlags)

    fun createImage(
        filename: CharSequence,
        imageFlags: Int
    ) = nvgCreateImage(ctx, filename, imageFlags)

    fun createImageMem(
        imageFlags: Int,
        data: ByteBuffer
    ) = nvgCreateImageMem(ctx, imageFlags, data)

    fun createImageRGBA(
        w: Int,
        h: Int,
        imageFlags: Int,
        data: ByteBuffer
    ) = nvgCreateImageRGBA(ctx, w, h, imageFlags, data)

    fun updateImage(
        image: Int,
        data: ByteBuffer
    ) = nvgUpdateImage(ctx, image, data)

    fun imageSize(
        
        image: Int,
        w: IntBuffer,
        h: IntBuffer
    ) = nvgImageSize(ctx, image, w, h)

    fun deleteImage( image: Int) = nvgDeleteImage(ctx, image)

    fun linearGradient(
        sx: Float,
        sy: Float,
        ex: Float,
        ey: Float,
        icol: NVGColor,
        ocol: NVGColor,
        __result: NVGPaint
    ) = nvgLinearGradient(ctx, sx, sy, ex, ey, icol, ocol, __result)

    fun boxGradient(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        r: Float,
        f: Float,
        icol: NVGColor,
        ocol: NVGColor,
        __result: NVGPaint
    ) = nvgBoxGradient(ctx, x, y, w, h, r, f, icol, ocol, __result)

    fun radialGradient(
        cx: Float,
        cy: Float,
        inr: Float,
        outr: Float,
        icol: NVGColor,
        ocol: NVGColor,
        __result: NVGPaint
    ): NVGPaint = nvgRadialGradient(ctx, cx, cy, inr, outr, icol, ocol, __result)

    fun imagePattern(
        ox: Float,
        oy: Float,
        ex: Float,
        ey: Float,
        angle: Float,
        image: Int,
        alpha: Float,
        __result: NVGPaint
    ) = nvgImagePattern(ctx, ox, oy, ex, ey, angle, image, alpha, __result)

    fun scissor( x: Float, y: Float, w: Float, h: Float) = nvgScissor(ctx, x, y, w, h)

    fun intersectScissor( x: Float, y: Float, w: Float, h: Float) = nvgIntersectScissor(ctx, x, y, w, h)

    fun resetScissor() = nvgResetScissor(ctx)

    fun beginPath() = nvgBeginPath(ctx)

    fun moveTo( x: Float, y: Float) = nvgMoveTo(ctx, x, y)

    fun lineTo( x: Float, y: Float) = nvgLineTo(ctx, x, y)

    fun bezierTo(
        c1x: Float,
        c1y: Float,
        c2x: Float,
        c2y: Float,
        x: Float,
        y: Float
    ) = nvgBezierTo(ctx, c1x, c1y, c2x, c2y, x, y)

    fun quadTo( cx: Float, cy: Float, x: Float, y: Float) = nvgQuadTo(ctx, cx, cy, x, y)

    fun arcTo( x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) = nvgArcTo(ctx, x1, y1, x2, y2, radius)

    fun closePath() = nvgClosePath(ctx)

    fun pathWinding( dir: Int) = nvgPathWinding(ctx, dir)

    fun arc( cx: Float, cy: Float, r: Float, a0: Float, a1: Float, dir: Int) = nvgArc(ctx, cx, cy, r, a0, a1, dir)

    fun rect( x: Float, y: Float, w: Float, h: Float) = nvgRect(ctx, x, y, w, h)

    fun roundedRect( x: Float, y: Float, w: Float, h: Float, r: Float) = nvgRoundedRect(ctx, x, y, w, h, r)

    fun roundedRectVarying(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        radTopLeft: Float,
        radTopRight: Float,
        radBottomRight: Float,
        radBottomLeft: Float
    ) = nvgRoundedRectVarying(ctx, x, y, w, h, radTopLeft, radTopRight, radBottomRight, radBottomLeft)

    fun ellipse( cx: Float, cy: Float, rx: Float, ry: Float) = nvgEllipse(ctx, cx, cy, rx, ry)

    fun circle( cx: Float, cy: Float, r: Float) = nvgCircle(ctx, cx, cy, r)

    fun fill() = nvgFill(ctx)

    fun stroke() = nvgStroke(ctx)

    fun createFont(
        name: ByteBuffer,
        filename: ByteBuffer
    ) = nvgCreateFont(ctx, name, filename)

    fun createFont(
        name: CharSequence,
        filename: CharSequence
    ) = nvgCreateFont(ctx, name, filename)

    fun createFontAtIndex(
        name: ByteBuffer,
        filename: ByteBuffer,
        fontIndex: Int
    ) = nvgCreateFontAtIndex(ctx, name, filename, fontIndex)

    fun createFontAtIndex(
        name: CharSequence,
        filename: CharSequence,
        fontIndex: Int
    ) = nvgCreateFontAtIndex(ctx, name, filename, fontIndex)

    fun createFontMem(
        name: ByteBuffer,
        data: ByteBuffer,
        freeData: Boolean
    ) = nvgCreateFontMem(ctx,name,data,freeData)

    fun createFontMem(
        name: CharSequence,
        data: ByteBuffer,
        freeData: Boolean
    ) = nvgCreateFontMem(ctx,name,data,freeData)

    fun createFontMemAtIndex(
        name: ByteBuffer,
        data: ByteBuffer,
        freeData: Boolean,
        fontIndex: Int
    ) = nvgCreateFontMemAtIndex(ctx,name,data,freeData,fontIndex)

    fun createFontMemAtIndex(
        name: CharSequence,
        data: ByteBuffer,
        freeData: Boolean,
        fontIndex: Int
    ) = nvgCreateFontMemAtIndex(ctx,name,data,freeData,fontIndex)

    fun findFont( name: ByteBuffer) = nvgFindFont(ctx, name)

    fun findFont( name: CharSequence) = nvgFindFont(ctx, name)

    fun addFallbackFontId( baseFont: Int, fallbackFont: Int) = nvgAddFallbackFontId(ctx, baseFont, fallbackFont)

    fun addFallbackFont(
        baseFont: ByteBuffer,
        fallbackFont: ByteBuffer
    ) = nvgAddFallbackFont(ctx, baseFont, fallbackFont)

    fun addFallbackFont(
        baseFont: CharSequence,
        fallbackFont: CharSequence
    ) = nvgAddFallbackFont(ctx, baseFont, fallbackFont)

    fun resetFallbackFontsId( baseFont: Int) = nvgResetFallbackFontsId(ctx, baseFont)

    fun resetFallbackFonts(
        baseFont: ByteBuffer
    ) = nvgResetFallbackFonts(ctx, baseFont)

    fun resetFallbackFonts(
        baseFont: CharSequence
    ) = nvgResetFallbackFonts(ctx, baseFont)

    fun fontSize( size: Float) = nvgFontSize(ctx, size)

    fun fontBlur( blur: Float) = nvgFontBlur(ctx, blur)

    fun textLetterSpacing( spacing: Float) = nvgTextLetterSpacing(ctx, spacing)

    fun textLineHeight( lineHeight: Float) = nvgTextLineHeight(ctx, lineHeight)

    fun textAlign( align: Int) = nvgTextAlign(ctx, align)

    fun fontFaceId( font: Int) = nvgFontFaceId(ctx, font)

    fun fontFace( font: ByteBuffer) = nvgFontFace(ctx, font)

    fun fontFace( font: CharSequence) = nvgFontFace(ctx, font)

    fun text(
        x: Float,
        y: Float,
        string: ByteBuffer
    ) = nvgText(ctx, x, y, string)

    fun text(
        x: Float,
        y: Float,
        string: CharSequence
    ) = nvgText(ctx, x, y, string)

    fun textBox(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: ByteBuffer
    ) = nvgTextBox(ctx, x, y, breakRowWidth, string)

    fun textBox(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: CharSequence
    ) = nvgTextBox(ctx, x, y, breakRowWidth, string)

    fun textBounds(
        x: Float,
        y: Float,
        string: ByteBuffer,
        bounds: FloatBuffer
    ) = nvgTextBounds(ctx,x,y,string,bounds)

    fun textBounds(
        x: Float,
        y: Float,
        string: CharSequence,
        bounds: FloatBuffer
    ) = nvgTextBounds(ctx,x,y,string,bounds)

    fun textBoxBounds(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: ByteBuffer,
        bounds: FloatBuffer
    ) = nvgTextBoxBounds(ctx,x,y,breakRowWidth,string,bounds)

    fun textBoxBounds(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: CharSequence,
        bounds: FloatBuffer
    ) = nvgTextBoxBounds(ctx,x,y,breakRowWidth,string,bounds)

    fun textGlyphPositions(
        x: Float,
        y: Float,
        string: ByteBuffer,
        positions: NVGGlyphPosition.Buffer
    ) = nvgTextGlyphPositions(ctx,x,y, string, positions)

    fun textGlyphPositions(
        x: Float,
        y: Float,
        string: CharSequence,
        positions: NVGGlyphPosition.Buffer
    ) = nvgTextGlyphPositions(ctx,x,y, string, positions)

    fun textMetrics(
        ascender: FloatBuffer,
        descender: FloatBuffer,
        lineh: FloatBuffer
    ) = nvgTextMetrics(ctx, ascender, descender, lineh)

    fun textBreakLines(
        string: ByteBuffer,
        breakRowWidth: Float,
        rows: NVGTextRow.Buffer
    ) = nvgTextBreakLines(ctx,string, breakRowWidth, rows)

    fun textBreakLines(
        string: CharSequence,
        breakRowWidth: Float,
        rows: NVGTextRow.Buffer
    ) = nvgTextBreakLines(ctx,string,breakRowWidth, rows)

    fun currentTransform( xform: FloatArray) = nvgCurrentTransform(ctx, xform)

    fun transformIdentity(dst: FloatArray) = nvgTransformIdentity(dst)

    fun transformTranslate(dst: FloatArray, tx: Float, ty: Float) = nvgTransformTranslate(dst, tx, ty)

    fun transformScale(dst: FloatArray, sx: Float, sy: Float) = nvgTransformScale(dst, sx, sy)

    fun transformRotate(dst: FloatArray, a: Float) = nvgTransformRotate(dst, a)

    fun transformSkewX(dst: FloatArray, a: Float) = nvgTransformSkewX(dst, a)

    fun transformSkewY(dst: FloatArray, a: Float) = nvgTransformSkewY(dst, a)

    fun transformMultiply(dst: FloatArray, src: FloatArray) = nvgTransformMultiply(dst, src)

    fun transformPremultiply(
        dst: FloatArray,
        src: FloatArray
    ) = nvgTransformPremultiply(dst, src)

    fun transformInverse(
        dst: FloatArray,
        src: FloatArray
    ) = nvgTransformInverse(dst, src)

    fun transformPoint(
        dstx: FloatArray,
        dsty: FloatArray,
        xform: FloatArray,
        srcx: Float,
        srcy: Float
    ) = nvgTransformPoint(dstx, dsty, xform, srcx, srcy)

    fun imageSize(
        
        image: Int,
        w: IntArray,
        h: IntArray
    ) = nvgImageSize(ctx, image, w, h)

    fun textBounds(
        x: Float,
        y: Float,
        string: ByteBuffer,
        bounds: FloatArray
    ) = nvgTextBounds(ctx,x,y,string,bounds)

    fun textBounds(
        x: Float,
        y: Float,
        string: CharSequence,
        bounds: FloatArray
    ) = nvgTextBounds(ctx, x, y, string, bounds)

    fun textBoxBounds(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: ByteBuffer,
        bounds: FloatArray
    ) = nvgTextBoxBounds(ctx,x,y,breakRowWidth,string,bounds)

    fun textBoxBounds(
        x: Float,
        y: Float,
        breakRowWidth: Float,
        string: CharSequence,
        bounds: FloatArray
    ) = nvgTextBoxBounds(ctx,x,y,breakRowWidth,string,bounds)

    fun textMetrics(
        ascender: FloatArray,
        descender: FloatArray,
        lineh: FloatArray
    ) = nvgTextMetrics(ctx, ascender, descender, lineh)
}