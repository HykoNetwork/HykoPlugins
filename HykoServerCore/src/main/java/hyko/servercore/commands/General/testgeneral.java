package hyko.servercore.commands.General;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testgeneral implements CommandExecutor {

    private static HykoConfigMessagesConvert configConvert;
    public testgeneral(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    public static void reload(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

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
                sender.sendMessage(configConvert.getMessage(MessageType.badExecute));
            }
        }
        return false;
    }
}
