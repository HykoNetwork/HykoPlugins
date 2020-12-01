package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoStatic;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TestWorkingCommand extends Command {
    public TestWorkingCommand() {
        super("testworking");
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(p.hasPermission("hyko.admin")) {
                p.sendMessage(new TextComponent(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "Network-Wide Command has successfully executed."));
            }else{
                p.sendMessage(new TextComponent(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "Network-Wide Command has successfully executed. [No Admin]"));
            }
        }else{
            sender.sendMessage(new TextComponent(HykoStatic.badExecute));
        }
    }
}
