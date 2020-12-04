package hyko.servercore.commands.General.NetworkCurrency;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.ServerCore;
import me.cedox.api.API;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Coins implements CommandExecutor {

    private final ServerCore plugin;
    private static HykoConfigMessagesConvert configConvert;

    public Coins(ServerCore plugin) {
        this.plugin = plugin;
       configConvert = new HykoConfigMessagesConvert(plugin);
    }

    public static void reload(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    final String configPrefix = ChatColor.AQUA + "[CONSOLE] ";

    // hyko.admin permission will allow total editing of coins.
    // /coins and /coins <player> require no permission.
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("coins")) {
            if(!(commandSender instanceof Player)) {
                return true;
            }
            Player p = (Player) commandSender;
            if(args.length == 0) {
                // (/coins)
                p.sendMessage(ChatColor.GRAY + "Your Network Coins: " + ChatColor.YELLOW + API.getCoins(p.getUniqueId().toString()));
            }else if(args.length == 1) {
                if(!playerExists(args[0])) {
                    p.sendMessage(ChatColor.RED + "Player " + args[0] + " has never logged onto the network before!");
                    return true;
                }
                p.sendMessage(ChatColor.GRAY + "Player " + args[0] + "'s Network Coins: " + ChatColor.YELLOW + API.getCoins(getUUIDFromPlayerName(args[0])));
            }else if(args.length == 2) {

            }else if(args.length == 3) {
                // (/coins set <player> <amount>
            }else{
                showUsageMessage(commandSender);
            }
        }
        return false;
    }

    public void showUsageMessage(CommandSender sender) {
        sender.sendMessage("\n");
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Usages: ");
        sender.sendMessage(ChatColor.AQUA + "/coins");
        sender.sendMessage(ChatColor.AQUA + "/coins <player name>");
        if(sender.hasPermission("hyko.admin")) {
            sender.sendMessage(ChatColor.RED + "/coins set <player name> amount>");
            sender.sendMessage(ChatColor.RED + "/coins add <player name> amount>");
            sender.sendMessage(ChatColor.RED + "/coins remove <player name> amount>");
        }
        sender.sendMessage("\n");
    }

    public String getUUIDFromPlayerName(String playerName) {
        if(playerExists(playerName)) {
            try {
                ResultSet rs = ServerCore.playerDatabase.getResult("SELECT * FROM " + ServerCore.playerDatabase.getDatabaseName() + " WHERE PLAYER_NAME= '" + playerName + "'");
                if (rs.next())
                    return rs.getString("UUID");
            } catch (SQLException e) {
                ServerCore.playerDatabase.getConnection();
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean playerExists(String name) {
        plugin.getLogger().info("Player " + name + " does not exist in SQL database... Adding!");
        try {
            ResultSet rs = ServerCore.playerDatabase.getResult("SELECT * FROM " + ServerCore.playerDatabase.getDatabaseName() + " WHERE PLAYER_NAME= '" + name + "'");
            if (rs.next())
                return (rs.getString("UUID") != null);
        } catch (SQLException e) {
            ServerCore.playerDatabase.getConnection();
            e.printStackTrace();
        }
        return false;
    }
}
