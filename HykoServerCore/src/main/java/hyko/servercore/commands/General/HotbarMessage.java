package hyko.servercore.commands.General;

import hyko.servercore.MessageType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

public class HotbarMessage implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("hotbarmessage")) {
            if(!(sender instanceof Player)) {
                Player target = Bukkit.getPlayerExact(args[0]);
                StringBuilder message = new StringBuilder();
                for(int i =1 ; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                String finalMessage = ChatColor.translateAlternateColorCodes('&', message.toString());
                assert target != null;
                target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(finalMessage));
            }else{
                sender.sendMessage("/hotbarmessage");
            }
        }
        return false;
    }
}
