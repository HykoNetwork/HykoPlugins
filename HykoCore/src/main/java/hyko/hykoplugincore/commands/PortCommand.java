package hyko.hykoplugincore.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PortCommand extends Command {
    public PortCommand() {
        super("port", "hyko.mod");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length != 1) {
                p.sendMessage(new TextComponent("/port <player>"));
                return;
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if(target == null) {
                p.sendMessage(new TextComponent("Could not find " + args[0] + " on network."));
                return;
            }
            p.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "Proxied to " + args[0] + "'s server."));
            p.connect(target.getServer().getInfo());
        }
    }
}
