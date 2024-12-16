package dev.nelmin.mcman.utils

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

/**
 * Modify text easily, colorize it and send it to the Console/Players with this TextBuilder
 *
 * ```kt
 * TextBuilder("Hey").prefix("I said \"").suffix("\" to him").replace("Hey", "Hello").sendTo(arrayOf(player))
 * ```
 *
 * @author Nelmin
 * @since 1.0.0
 */
class TextBuilder(private var text: String) {

    /**
     * Add anything in front of the current text
     */
    fun prefix(value: Any) = apply { this.text = value.toString() + this.text }
    /**
     * Add after in front of the current text
     */
    fun suffix(value: Any) = apply { this.text += value }

    /**
     * Replace a "heavy" amount of content using maps
     */
    fun replace(map: Map<Any, Any>): TextBuilder {
        map.forEach { (key, value) -> text.replace(key.toString(), value.toString()) }
        return this
    }
    /**
     * Maybe unnecessary, but now you're indirectly forced to use maps
     */
    fun replace(key: Any, value: Any): TextBuilder = replace(mapOf(key to value))

    /**
     * Gives the text color
     */
    private fun colorize(): String {
        return ChatColor.translateAlternateColorCodes('&', text)
    }
    /**
     * Get text, either uncolored or colored
     */
    fun get(colorized: Boolean = false): String
        = if (colorized) colorize()
        else text


    /**
     * Send a message to Players or to the Console
     */
    fun sendTo(playersOrConsole: Collection<CommandSender>, colorized: Boolean = false) {
        playersOrConsole.forEach { it.sendMessage(get(colorized)) }
    }
    fun sendTo(playersOrConsole: Array<CommandSender>, colorized: Boolean = false) {
        playersOrConsole.forEach { it.sendMessage(get(colorized)) }
    }
}