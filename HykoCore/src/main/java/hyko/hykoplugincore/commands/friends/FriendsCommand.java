package hyko.hykoplugincore.commands.friends;

import hyko.hykoplugincore.HykoPluginCore;
import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class FriendsCommand extends Command {
    /*
    /friends -> View friend
     */

    public FriendsCommand() {
        super("friends");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            List<String> friends = HykoPluginCore.configuration.getStringList("friends." + p.getUniqueId());
            p.sendMessage(new TextComponent("Your Friends: "));
            p.sendMessage(new TextComponent(friends.toString()));
        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
