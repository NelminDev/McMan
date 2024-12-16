package dev.nelmin.mcman

import dev.nelmin.mcman.utils.TextBuilder
import org.bukkit.plugin.java.JavaPlugin

class McMan : JavaPlugin() {
    val pluginManager = this.server.pluginManager
    val consoleSender = this.server.consoleSender

    val PREFIX = "&a&lMcMan &8&l»&r"

    companion object {
        lateinit var instance: McMan
    }

    override fun onEnable() {
        instance = this

        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }

    override fun onDisable() {
        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }
}
