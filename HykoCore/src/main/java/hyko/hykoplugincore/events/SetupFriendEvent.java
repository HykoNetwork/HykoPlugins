package hyko.hykoplugincore.events;

import hyko.hykoplugincore.HykoPluginCore;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SetupFriendEvent implements Listener {

    @EventHandler
    public void event(PostLoginEvent e) {

        if(HykoPluginCore.configuration.get("friends." + e.getPlayer().getUniqueId())== null) {
            e.getPlayer().sendMessage(new TextComponent(ChatColor.GREEN + "Setting up your data! :)"));
        }



    }
}
