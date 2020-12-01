package hyko.servercore.commands.General;

import hyko.servercore.HykoStatic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testgeneral implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("testgeneral")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("hyko.admin")) {
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(GENERAL) Command has successfully executed.");
                }else{
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(GENERAL) Command has successfully executed. [No Admin]");
                }
            }else{
                sender.sendMessage(HykoStatic.badExecute);
            }
        }
        return false;
    }
}
