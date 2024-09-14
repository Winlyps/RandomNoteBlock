package winlyps.randomNoteBlock

import org.bukkit.Material
import org.bukkit.Note
import org.bukkit.block.Block
import org.bukkit.block.data.type.NoteBlock
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class RandomNoteBlockScheduler(private val plugin: Plugin) : BukkitRunnable() {

    private val checkRadius = 32 // Radius within which to check for note blocks

    override fun run() {
        // Iterate through all online players
        for (player in plugin.server.onlinePlayers) {
            val playerLocation = player.location
            val minX = playerLocation.blockX - checkRadius
            val maxX = playerLocation.blockX + checkRadius
            val minY = playerLocation.blockY - checkRadius
            val maxY = playerLocation.blockY + checkRadius
            val minZ = playerLocation.blockZ - checkRadius
            val maxZ = playerLocation.blockZ + checkRadius

            // Iterate through the blocks within the radius and filter note blocks
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    for (z in minZ..maxZ) {
                        val block = playerLocation.world!!.getBlockAt(x, y, z)
                        if (block.type == Material.NOTE_BLOCK) {
                            playRandomNote(block)
                        }
                    }
                }
            }
        }
    }

    private val random = Random

    private fun playRandomNote(block: Block) {
        val noteBlock = block.blockData as NoteBlock

        // Precompute the random values
        val octave = random.nextInt(3) // 0 to 2
        val tone = Note.Tone.values()[random.nextInt(Note.Tone.values().size)]

        // Create the note once
        val note = Note.natural(octave, tone)

        // Set the note and play it
        noteBlock.note = note
        block.world.playNote(block.location, noteBlock.instrument, note)
        block.setBlockData(noteBlock)
    }
}