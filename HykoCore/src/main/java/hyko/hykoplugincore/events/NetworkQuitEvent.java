package hyko.hykoplugincore.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class NetworkQuitEvent implements Listener {

    @EventHandler
    public void event(PlayerDisconnectEvent e) {
        for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()) {
            p.sendMessage(new TextComponent(ChatColor.GRAY + e.getPlayer().getName() + " has left the network. Goodbye!"));
        }
    }
}
