package dev.nelmin.mcman.utils

import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

abstract class Command : CommandExecutor, TabCompleter {

    fun getSyntax() {}

}