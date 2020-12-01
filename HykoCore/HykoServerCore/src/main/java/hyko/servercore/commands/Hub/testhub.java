package hyko.servercore.commands.Hub;

import hyko.servercore.HykoStatic;
import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testhub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("testhub")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(ServerCore.isPlayerOnRequiredServer(p) != ServerID.HUB) {
                    p.sendMessage(HykoStatic.badServer_Hub);
                    return true;
                }
                if(p.hasPermission("hyko.admin")) {
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(HUB/LOBBY) Command has successfully executed.");
                }else{
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(HUB/LOBBY) Command has successfully executed. [No Admin]");
                }
            }else{
                sender.sendMessage(HykoStatic.badExecute);
            }
        }
        return false;
    }
}
