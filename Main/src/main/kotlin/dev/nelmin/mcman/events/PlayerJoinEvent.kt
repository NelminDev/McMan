package dev.nelmin.mcman.events

import dev.nelmin.mcman.McMan
import dev.nelmin.mcman.data.UserData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinEvent() : Listener {

    @EventHandler
    fun onPlayerFirstJoin(event: PlayerJoinEvent) {
        if (event.player.hasPlayedBefore()) return

        val userData = UserData(event.player.name, event.player.uniqueId)
        McMan.instance.loadedUserData.add(userData)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        UserData(event.player.name, event.player.uniqueId)
    }
}