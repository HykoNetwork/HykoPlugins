package hyko.hykoplugincore.events;

import hyko.hykoplugincore.HykoPluginCore;
import hyko.hykoplugincore.SQL.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SetupFriendEvent implements Listener {

    @EventHandler
    public void event(PostLoginEvent e) {
        /**
         * Use a MySQL database to check if the player has logged in before.
         * If they have not then add their Name to the database and have their name point to their UUID.
         *
         * Then we can have the spigot plugin check this database and see if they have logged in before just by uding their name
         * and we can get their UUID from it.
         *
         * This will solve the issue of a player on creative checking a player on hubs currency even if the player on hub has never logged in on creative.
         */

        if(!HykoPluginCore.playerDatabase.playerExists(e.getPlayer().getName())) {
            HykoPluginCore.getInstance().getLogger().info("Player " + e.getPlayer().getName() + " does not exist in SQL database... Adding!");
            HykoPluginCore.playerDatabase.addPlayerToDatabase(e.getPlayer().getName(), e.getPlayer().getUniqueId());
            HykoPluginCore.getInstance().getLogger().info("Success! Player " + e.getPlayer().getName() + " added to database!");
        }

        if(HykoPluginCore.configuration.get("friends." + e.getPlayer().getUniqueId())== null) {
            e.getPlayer().sendMessage(new TextComponent(ChatColor.GREEN + "Setting up your data! :)"));
        }



    }
}
