package hyko.servercore;

import hyko.servercore.config.ConfigManager;
import org.bukkit.ChatColor;

import java.util.Objects;

public class HykoConfigMessagesConvert {
    /*
    Class for static variables for Hyko Network Core
     */
    private final ConfigManager messagesFile;

    public HykoConfigMessagesConvert(ServerCore plugin) {
        this.messagesFile = new ConfigManager(plugin, "messages.yml");
    }
    public String getMessage(MessageType type) {
        if(messagesFile.getConfig().getString("badSender") == null) {
            messagesFile.reloadConfig("messages.yml");
        }
        switch(type) {
            case badExecute:
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesFile.getConfig().getString("badSender")));
            case badPermission:
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesFile.getConfig().getString("badPermission")));
            case badServer_Hub:
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesFile.getConfig().getString("badServer_Hub")));
            case badServer_Creative:
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(messagesFile.getConfig().getString("badServer_Creative")));
            default:
                return null;
        }
    }

}
