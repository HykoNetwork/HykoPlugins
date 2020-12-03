package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ApplyCommand extends Command {

    public ApplyCommand() {
        super("apply");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            p.sendMessage(new TextComponent(ChatColor.GOLD + "Hello!"));
            p.sendMessage(new TextComponent(ChatColor.GOLD + "Thank you for taking interest in our server! If you want to apply for staff on our network then you must do it through our discord (/socials)."));
        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
