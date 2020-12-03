package hyko.hykoplugincore.commands.friends;

import hyko.hykoplugincore.HykoPluginCore;
import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.util.List;

public class FriendCommand extends Command {

    /*
    /friend add <playername>
    /friend remove <playername>
    /friend update
     */
    public FriendCommand() {
        super("friend");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length != 2) { //friend add <playername>
                p.sendMessage(new TextComponent(ChatColor.RED + "Incorrect Usage."));
                return;
            }
            List<String> friends = HykoPluginCore.configuration.getStringList("friends." + p.getUniqueId());
            if(args[0].equalsIgnoreCase("add")) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(new TextComponent(ChatColor.RED + "Could not find player " + args[1] + "."));
                    return;
                }
                if(friends.contains(target.getUniqueId().toString())) {
                    p.sendMessage(new TextComponent(ChatColor.RED + "You are already friends with this player!"));
                    return;
                }
                p.sendMessage(new TextComponent(ChatColor.GREEN + "You are now friends with " + args[1]));
                friends.add(target.getUniqueId().toString());
                HykoPluginCore.configuration.set("friends." + p.getUniqueId(), friends);
                try {


                    ConfigurationProvider.getProvider(YamlConfiguration.class).save(HykoPluginCore.configuration, HykoPluginCore.file);
                    HykoPluginCore.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(HykoPluginCore.file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                p.sendMessage(new TextComponent(ChatColor.RED + "Unknown Usage."));
            }

        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
