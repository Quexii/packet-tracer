package eu.shoroa.packettracer.ui

import java.awt.Color

/**
 * Represents a Material with various properties and types.
 */
class Material private constructor(
    // Unique identifier for the material
    val id: Int,

    // Additional data associated with the material
    vararg val data: Any?
) {
    // Flag to indicate if stroke should be applied
    var stroke = false

    // Width of the stroke
    var strokeWidth = 1f

    companion object {
        // Material type identifiers
        const val ID_SOLID = 0
        const val ID_GRADIENT_HORIZONTAL = 1
        const val ID_GRADIENT_VERTICAL = 2

        // Factory methods for creating different types of materials
        val SOLID: (Color) -> Material = { color -> Material(ID_SOLID, color) }
        val GRADIENT_H: (Color, Color) -> Material = { from, to -> Material(ID_GRADIENT_HORIZONTAL, from, to) }
        val GRADIENT_V: (Color, Color) -> Material = { from, to -> Material(ID_GRADIENT_VERTICAL, from, to) }
    }

    /**
     * Applies stroke to the material and sets the stroke width.
     */
    fun stroke(sw: Float) = apply {
        stroke = true
        strokeWidth = sw
    }
}