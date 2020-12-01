package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WarpCommand extends Command {

    public WarpCommand() {
        super("warp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            p.sendMessage(new TextComponent(ChatColor.YELLOW + "Please use the Server Selector to join a server!"));
        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
