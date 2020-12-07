package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoPluginCore;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CleanMessageCommand extends Command {

    public CleanMessageCommand() {
        super("cleanmessage", "hyko.cleanmessage");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0 || args.length == 1) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Clean Message command must have arguments. /cleanmessage <player> <message>"));
            return;
        }
        ProxiedPlayer p = HykoPluginCore.getInstance().getProxy().getPlayer(args[0]);
        if(p == null) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Cannot find player " + args[0]));
            return;
        }
        StringBuilder message = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            message.append(args[i]);
        }
        String messageData = message.toString();
        messageData = ChatColor.translateAlternateColorCodes('&' ,message.toString());
        p.sendMessage(new TextComponent(messageData));
    }
}
