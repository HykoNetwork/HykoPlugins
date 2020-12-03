package hyko.servercore.events.Hub;

import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinMessageEvent implements Listener {

    public PlayerJoinMessageEvent(ServerCore plugin) {
    }

    @EventHandler
    public void event(PlayerJoinEvent e) {
        e.setJoinMessage("");
    }
}
