package dev.nelmin.mcman.data

import dev.nelmin.mcman.McMan
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

/**
 * Version 1.0.0 of this File
 * Currently only supports YAML for saving the userdata
 *
 * @author Nelmin
 * @since 1.0.0
 */
data class UserData(
    var userName: String,
    var uuid: UUID,
    var balance: Double = 1000.00, // Balance will be loaded from the config
    var chatDisabled: Boolean = false
) {
    private val plugin = McMan.instance
    private val file: File = File("${plugin.dataFolder}/userdata/", "$uuid.yml").apply { createNewFile() }
    private val config: YamlConfiguration = YamlConfiguration().apply {
        options().parseComments(true)
        load(file)
    }

    init {
        if (!config.contains("userName")) config.set("userName", userName)
        if (!config.contains("uuid")) config.set("uuid", uuid.toString())
        if (!config.contains("balance")) {
            config.set("balance", plugin.config.get("economy.starter_balance") as Double? ?: 1000.00)
        }
        if (!config.contains("chatDisabled")) config.set("chatDisabled", false)
        save()

        // Remove existing user data for this UUID and load balance
        plugin.loadedUserData.removeIf { it.uuid == uuid }
        balance = config.getDouble("balance", plugin.config.get("economy.starter_balance") as Double? ?: 1000.00)
        plugin.loadedUserData.add(this)
    }

    // Load all user data from files in the userdata directory
    companion object {
        fun loadAll() {
            val directory = File(McMan.instance.dataFolder, "userdata").apply { mkdirs() }
            directory.listFiles()?.forEach { file ->
                val uuid = UUID.fromString(file.nameWithoutExtension)
                val offlinePlayer = Bukkit.getOfflinePlayer(uuid)
                UserData(offlinePlayer.name ?: return@forEach, uuid) // Balance will be loaded later
            }
        }
    }

    // Get a specific configuration value
    fun get(path: String): Any? = config.get(path)

    // Save user data to the configuration file
    fun save() {
        try {
            config.set("balance", balance)
            config.save(file)
            plugin.loadedUserData.removeIf { it.uuid == uuid }
            plugin.loadedUserData.add(this)
        } catch (e: Exception) {
            plugin.logger.severe(e.localizedMessage)
            e.printStackTrace()
        }
    }

    // Set a specific configuration value
    fun set(path: String, value: Any?, override: Boolean) {
        if (override || config.get(path) == null) {
            config.set(path, value)
            save()
        }
    }
}