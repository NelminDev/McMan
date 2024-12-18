package dev.nelmin.mcman.commands

import dev.nelmin.mcman.McMan
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class PayCommand : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        val set = mutableSetOf<String>()

        when (args.size) {
            1 -> {
                set.add("*")
                set.addAll(McMan.instance.server.onlinePlayers.map { it.name })
            }
            2 -> {
                set.add("500")
                for (number in 1000..1000 step 1000)
                    set.add(number.toString())
            }
        }

        return set.toMutableList()
    }

}