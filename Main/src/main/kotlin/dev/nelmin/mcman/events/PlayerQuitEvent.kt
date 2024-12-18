package dev.nelmin.mcman.events

import dev.nelmin.mcman.McMan
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitEvent() : Listener {

    @EventHandler
    fun onPlayerDisconnect(event: PlayerQuitEvent) {
        McMan.instance.saveUserData(event.player.uniqueId)
    }
}