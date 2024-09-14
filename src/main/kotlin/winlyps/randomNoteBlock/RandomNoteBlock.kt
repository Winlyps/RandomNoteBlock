package winlyps.randomNoteBlock

import org.bukkit.plugin.java.JavaPlugin

class RandomNoteBlock : JavaPlugin() {

    override fun onEnable() {
        // Start the scheduler
        RandomNoteBlockScheduler(this).runTaskTimer(this, 0L, 20L * 3) // Every 5 seconds
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}