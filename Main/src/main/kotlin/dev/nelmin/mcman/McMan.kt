package dev.nelmin.mcman

import dev.nelmin.mcman.data.UserData
import dev.nelmin.mcman.events.PlayerJoinEvent
import dev.nelmin.mcman.events.PlayerQuitEvent
import dev.nelmin.mcman.files.YAML
import dev.nelmin.mcman.utils.TextBuilder
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class McMan : JavaPlugin() {
    companion object {
        lateinit var instance: McMan
    }

    val pluginManager = this.server.pluginManager
    val consoleSender = this.server.consoleSender

    val PREFIX = "&a&lMcMan &8&l»&r"

    val dataFolder = "/plugins/McMan/"

    val loadedUserData: MutableSet<UserData> = ConcurrentHashMap.newKeySet()

    val config = YAML("config.yml")
    val messages = YAML("messages.yml")

    override fun onEnable() {
        instance = this

        config.load()
        messages.load()

        UserData.loadAll()

        server.pluginManager.apply {
            registerEvents(PlayerJoinEvent(), this@McMan)
            registerEvents(PlayerQuitEvent(), this@McMan)
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, ::saveUserData, 0L, 5 * 60 * 20L)

        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }

    override fun onDisable() {
        config.save()
        messages.save()

        saveUserData()

        TextBuilder("$PREFIX Successfully loaded McMan!").sendTo(arrayOf(consoleSender), true)
    }

    private fun saveUserData() {
        loadedUserData.forEach(UserData::save)
    }

    fun saveUserData(uuid: UUID) {
        loadedUserData.find { it.uuid == uuid }?.save()
    }
}