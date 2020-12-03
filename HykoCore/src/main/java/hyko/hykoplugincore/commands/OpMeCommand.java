package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class OpMeCommand extends Command {

    public OpMeCommand() {
        super("opme");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            p.sendMessage(new TextComponent(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "[Server: Opped " + p.getName() + "]"));

        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
