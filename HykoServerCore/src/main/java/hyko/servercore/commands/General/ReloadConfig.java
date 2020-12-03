package hyko.servercore.commands.General;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import hyko.servercore.commands.Creative.testcreative;
import hyko.servercore.commands.Hub.testhub;
import hyko.servercore.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfig implements CommandExecutor {

    private final HykoConfigMessagesConvert configConvert;
    private final ServerCore plugin;
    public ReloadConfig(ServerCore plugin) {
        this.plugin = plugin;
        this.configConvert = new HykoConfigMessagesConvert(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("hykoreload")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.isOp()) {
                    p.sendMessage(ChatColor.GREEN + "Hyko Configuration Reloaded.");
                    ConfigManager messagesFile = new ConfigManager(plugin, "messages.yml");
                    messagesFile.saveConfig(plugin, "messages.yml");
                    messagesFile.reloadConfig("messages.yml");

                    testgeneral.reload(plugin);
                    testhub.reload(plugin);
                    testcreative.reload(plugin);
                }else{
                    p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                }
            }else{
                sender.sendMessage(configConvert.getMessage(MessageType.badExecute));
            }
        }
        return false;
    }
}
