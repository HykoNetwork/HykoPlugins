package hyko.hykoplugincore.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerJoinEvent implements Listener {

    @EventHandler
    public void event(ServerConnectEvent e) {
        try {
            for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()) {
                if(p == e.getPlayer()) {
                    p.sendMessage(new TextComponent(ChatColor.GREEN + "[" + e.getPlayer().getName() + "] " + ChatColor.GRAY + e.getPlayer().getServer().getInfo().getName() + ChatColor.DARK_GRAY + " -> " + ChatColor.GRAY + e.getTarget().getName()));
                    continue;
                }
                if(p.getServer().getInfo().getName().equalsIgnoreCase(e.getPlayer().getServer().getInfo().getName())) {
                    if(p == e.getPlayer()) {
                        continue;
                    }
                    p.sendMessage(new TextComponent(ChatColor.RED + "[" + e.getPlayer().getName() + "] " + ChatColor.GRAY + e.getPlayer().getServer().getInfo().getName() + ChatColor.DARK_GRAY + " -> " + ChatColor.GRAY + e.getTarget().getName()));
                    continue;
                }
                if(p.getServer().getInfo().getName().equalsIgnoreCase(e.getTarget().getName())) {
                    p.sendMessage(new TextComponent(ChatColor.GREEN + "[" + e.getPlayer().getName() + "] " + ChatColor.GRAY + e.getPlayer().getServer().getInfo().getName() + ChatColor.DARK_GRAY + " -> " + ChatColor.GRAY + e.getTarget().getName()));
                    continue;
                }
                if(p == e.getPlayer()) {
                    continue;
                }
                p.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "[" + e.getPlayer().getName() + "] " + ChatColor.GRAY + e.getPlayer().getServer().getInfo().getName() + ChatColor.DARK_GRAY + " -> " + ChatColor.GRAY + e.getTarget().getName()));

            }
        } catch(Exception exc) {
            //Ignore because player logged in from Nowhere (hub).
            for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()) {
                p.sendMessage(new TextComponent(ChatColor.GRAY + e.getPlayer().getName() + " has joined the network!"));
            }
        }

    }
}
