package hyko.servercore.events.Hub;

import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveMessageEvent implements Listener {

    public PlayerLeaveMessageEvent(ServerCore plugin) {
    }

    @EventHandler
    public void event(PlayerQuitEvent e) {
        e.setQuitMessage("");
    }
}
