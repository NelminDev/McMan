package dev.nelmin.mcman

import dev.nelmin.mcman.utils.TextBuilder
import org.bukkit.plugin.java.JavaPlugin

class MMEconomy : JavaPlugin() {
    val pluginManager = this.server.pluginManager
    val consoleSender = this.server.consoleSender

    val PREFIX = "&a&l${McMan.instance.description.name} &8&l»&r"

    companion object {
        lateinit var instance: MMEconomy
    }

    override fun onEnable() {
        instance = this

        Economy.register()

        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }

    override fun onDisable() {
        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }
}
