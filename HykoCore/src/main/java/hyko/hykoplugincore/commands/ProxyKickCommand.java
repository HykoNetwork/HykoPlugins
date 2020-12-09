package hyko.hykoplugincore.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ProxyKickCommand extends Command {

    public ProxyKickCommand() {
        super("proxykick", "hyko.staff");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(args.length < 1) {
                p.sendMessage(new TextComponent("/proxykick <player>"));
                return;
            }
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if(target == null) {
                p.sendMessage(new TextComponent("Could not find " + args[0] + " on network. If this player name is correct and you still want to punish " + args[0] + " then you do not need to proxykick just enact punishment."));
                return;
            }
            target.disconnect(new TextComponent(ChatColor.RED + "Connection forcefully terminated."));
            for(ProxiedPlayer onlineProxy: ProxyServer.getInstance().getPlayers()) {
                if(onlineProxy.hasPermission("hyko.staff")) {
                    onlineProxy.sendMessage(new TextComponent(ChatColor.AQUA + "[PROXY] " + p.getName() + " has kicked " + args[0]));
                }
            }
        }
    }
}
