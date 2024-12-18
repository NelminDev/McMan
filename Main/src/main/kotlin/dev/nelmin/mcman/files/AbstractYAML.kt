package dev.nelmin.mcman.files

import dev.nelmin.mcman.McMan
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * Manage YAML Files, mostly used for Configurations
 *
 * ```kt
 * public class MyYAML : AbstractYAML("myYaml") {}
 * ```
 * ```kt
 * val myYaml = YAML("myYaml")
 * ```
 *
 * @author Nelmin
 * @since 1.0.0
 */
abstract class AbstractYAML(private var fileName: String) {
    private val instance = McMan.instance
    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    /**
     * Check if fileName ends with .yaml or .yml, if not, then add it.
     */
    init {
        if (!Regex("\\.ya?ml$").matches(fileName)) {fileName += ".yml"}
    }

    fun get(path: String): Any? {
        return config.get(path)
    }

    fun load() {
        file = File(instance.dataFolder, fileName)

        if (!file.exists()) {
            instance.saveResource(fileName, false)
        }

        config = YamlConfiguration().apply {
            options().parseComments(true)
        }

        try {
            config.load(file)
        } catch (e: Exception) {
            instance.logger.severe(e.localizedMessage)
            e.printStackTrace()
        }
    }

    fun deleteFile() {
        file.delete()
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: Exception) {
            instance.logger.severe(e.localizedMessage)
            e.printStackTrace()
        }
    }

    fun set(path: String, value: Any?, override: Boolean = false): AbstractYAML {
        if (config.get(path) != null && !override) return this

        config.set(path, value)
        save()

        return this
    }
}