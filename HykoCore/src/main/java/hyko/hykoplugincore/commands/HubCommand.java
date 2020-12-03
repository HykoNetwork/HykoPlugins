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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class HubCommand extends Command  {

    public HubCommand() {
        super("hub");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.getServer().getInfo().getName().equalsIgnoreCase("hub")) {
                p.sendMessage(new ComponentBuilder("You are already connected to the Hub!").color(ChatColor.RED).create());
                return;
            }

            ServerInfo target = ProxyServer.getInstance().getServerInfo("Hub");
            p.connect(target);
        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
