package eu.shoroa.packettracer.viewer

import eu.shoroa.packettracer.ui.Material
import eu.shoroa.packettracer.ui.UI
import net.minecraft.entity.damage.DamageType
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.math.Vec3d
import java.awt.Color

class ViewerEntityDamageS2C : Viewer<EntityDamageS2CPacket>(EntityDamageS2CPacket::class){
    override fun render(packet: EntityDamageS2CPacket, x: Float, y: Float, width: Float, height: Float) {
        UI.apply {
            val dmgType = """
                IN_FIRE
                LIGHTNING_BOLT
                ON_FIRE
                LAVA
                HOT_FLOOR
                IN_WALL
                CRAMMING
                DROWN
                STARVE
                CACTUS
                FALL
                FLY_INTO_WALL
                OUT_OF_WORLD
                GENERIC
                MAGIC
                WITHER
                DRAGON_BREATH
                DRY_OUT
                SWEET_BERRY_BUSH
                FREEZE
                STALAGMITE
                FALLING_BLOCK
                FALLING_ANVIL
                FALLING_STALACTITE
                STING
                MOB_ATTACK
                MOB_ATTACK_NO_AGGRO
                PLAYER_ATTACK
                ARROW
                TRIDENT
                MOB_PROJECTILE
                FIREWORKS
                UNATTRIBUTED_FIREBALL
                FIREBALL
                WITHER_SKULL
                THROWN
                INDIRECT_MAGIC
                THORNS
                EXPLOSION
                PLAYER_EXPLOSION
                SONIC_BOOM
                BAD_RESPAWN_POINT
                OUTSIDE_BORDER
                GENERIC_KILL
            """.trimIndent().split("\n").toTypedArray()[packet.sourceTypeId]
            text("PlayerMoveC2SPacket", x + width / 2f, y + 10, 10f, "regular", Color(0xE1E1E1), UI.Alignment.CENTER_MIDDLE)
            arrayOf(
                    Pair("Entity ID", packet.entityId.toString()),
                    Pair("Source Type ID", packet.sourceTypeId.toString()),
                    Pair("Source Type", dmgType),
                    Pair("Source Cause ID", packet.sourceCauseId.toString()),
                    Pair("Source Direct ID", packet.sourceDirectId.toString()),
                    Pair("Source Position X", packet.sourcePosition.orElseGet { -> Vec3d(0.0, 0.0, 0.0) }.x.toString()),
                    Pair("Source Position Y", packet.sourcePosition.orElseGet { -> Vec3d(0.0, 0.0, 0.0) }.y.toString()),
                    Pair("Source Position Z", packet.sourcePosition.orElseGet { -> Vec3d(0.0, 0.0, 0.0) }.z.toString()),
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