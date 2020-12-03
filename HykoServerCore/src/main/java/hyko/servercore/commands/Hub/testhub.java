package hyko.servercore.commands.Hub;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import hyko.servercore.ServerID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testhub implements CommandExecutor {

    private static HykoConfigMessagesConvert configConvert;
    public testhub(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    public static void reload(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("testhub")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(ServerCore.getPlayerServerID(p) != ServerID.HUB) {
                    p.sendMessage(configConvert.getMessage(MessageType.badServer_Hub));
                    return true;
                }
                if(p.hasPermission("hyko.admin")) {
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(HUB/LOBBY) Command has successfully executed.");
                }else{
                    p.sendMessage(ChatColor.YELLOW + "[Hyko Network] " + ChatColor.GREEN + "(HUB/LOBBY) Command has successfully executed. [No Admin]");
                }
            }else{
                sender.sendMessage(configConvert.getMessage(MessageType.badExecute));
            }
        }
        return false;
    }
}
