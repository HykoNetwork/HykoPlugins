package hyko.servercore.commands.General.NetworkCurrency;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import me.cedox.api.API;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Coins implements CommandExecutor {

    private static HykoConfigMessagesConvert configConvert;
    final String consolePrefix = ChatColor.RED + "[CONSOLE] ";
    private final ServerCore plugin;

    public Coins(ServerCore plugin) {
        this.plugin = plugin;
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    public static void reload(ServerCore plugin) {
        configConvert = new HykoConfigMessagesConvert(plugin);
    }

    // hyko.admin permission will allow total editing of coins.
    // /coins and /coins <player> require no permission.
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("coins")) {
            if (!(commandSender instanceof Player)) {
                if (args.length == 3 || args.length == 4) {
                    // (/coins set <player> <amount>

                    if (args[0].equalsIgnoreCase("set")) {
                        if (playerExistsAndCommandArgumentsAreValid(args, commandSender)) return true;

                        if (Bukkit.getPlayerExact(args[1]) != null) {
                            if (args.length == 4) {
                                if (args[3].equalsIgnoreCase("-n")) {
                                    commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Set Network currency of player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "' to " + ChatColor.AQUA + args[2] + ChatColor.WHITE + "!");
                                    API.setCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]));
                                } else {
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Valid Command Signals: \"-n\"");
                                }
                                return true;
                            }
                            Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.WHITE + "Your " + ChatColor.AQUA + "Network Currency" + ChatColor.WHITE + " has been updated to: " + ChatColor.AQUA + args[2]);
                        }
                        commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Set Network currency of player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "' to " + ChatColor.AQUA + args[2] + ChatColor.WHITE + "!");
                        API.setCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]));


                    } else if (args[0].equalsIgnoreCase("add")) {
                        if (playerExistsAndCommandArgumentsAreValid(args, commandSender)) return true;

                        if (Bukkit.getPlayerExact(args[1]) != null) {
                            if (args.length == 4) {
                                if (args[3].equalsIgnoreCase("-n")) {
                                    commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Added Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " to player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                                    API.addCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);
                                } else {
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Valid Command Signals: \"-n\"");
                                }
                                return true;
                            }
                            Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.AQUA + args[2] + ChatColor.WHITE + " Network Coins have been added to your account!");
                        }
                        commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Added Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " to player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                        API.addCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);

                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (playerExistsAndCommandArgumentsAreValid(args, commandSender)) return true;

                        if (Bukkit.getPlayerExact(args[1]) != null) {
                            if (args.length == 4) {
                                if (args[3].equalsIgnoreCase("-n")) {
                                    commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Removed Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " from player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                                    API.removeCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);
                                } else {
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                    commandSender.sendMessage(consolePrefix + ChatColor.RED + "Valid Command Signals: \"-n\"");
                                }
                                return true;
                            }
                            Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.AQUA + args[2] + ChatColor.WHITE + " Network Coins have been removed from your account!");

                        }
                        commandSender.sendMessage(consolePrefix + ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Removed Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " from player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                        API.removeCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);

                    } else {
                        showUsageMessage(commandSender);
                    }

                } else {
                    showUsageMessage(commandSender);
                }
                return true;
            }
            Player p = (Player) commandSender;
            if (args.length == 0) {
                // (/coins)
                p.sendMessage(ChatColor.GRAY + "Your Network Coins: " + ChatColor.YELLOW + API.getCoins(p.getUniqueId().toString()));
                if(!p.getWorld().getName().equalsIgnoreCase("Hub")) {
                    return true;
                }
                TextComponent learnMore = new TextComponent("[Click here to learn more about Network Coins!]");
                learnMore.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                learnMore.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click to view information about Coins!")));
                learnMore.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/currencyinfo " + p.getName()));
                p.spigot().sendMessage(learnMore);
            } else if (args.length == 1) {
                if (!playerExists(args[0])) {
                    p.sendMessage(ChatColor.RED + "Player " + args[0] + " has never logged onto the network before!");
                    return true;
                }
                p.sendMessage(ChatColor.GRAY + "Player " + args[0] + "'s Network Coins: " + ChatColor.YELLOW + API.getCoins(getUUIDFromPlayerName(args[0])));
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("get")) {
                    if (!playerExists(args[1])) {
                        p.sendMessage(ChatColor.RED + "Player " + args[1] + " has never logged onto the network before!");
                        return true;
                    }
                    p.sendMessage(ChatColor.GRAY + "Player " + args[1] + "'s Network Coins: " + ChatColor.YELLOW + API.getCoins(getUUIDFromPlayerName(args[1])));
                } else {
                    showUsageMessage(commandSender);
                }
            } else if (args.length == 3 || args.length == 4) {
                if (!(p.hasPermission("hyko.admin"))) {
                    p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                    return true;
                }
                // (/coins set <player> <amount>
                if (args[0].equalsIgnoreCase("set")) {
                    if (playerExistsAndCommandArgumentsAreValid(args, p)) return true;

                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        if (args.length == 4) {
                            if (args[3].equalsIgnoreCase("-n")) {
                                p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Set Network currency of player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "' to " + ChatColor.AQUA + args[2] + ChatColor.WHITE + "!");
                                API.setCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]));
                            } else {
                                p.sendMessage(ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                p.sendMessage(ChatColor.RED + "Valid Command Signals: \"-n\"");
                            }
                            return true;
                        }
                        Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.WHITE + "Your " + ChatColor.AQUA + "Network Currency" + ChatColor.WHITE + " has been updated to: " + ChatColor.AQUA + args[2]);
                    }
                    p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Set Network currency of player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "' to " + ChatColor.AQUA + args[2] + ChatColor.WHITE + "!");
                    API.setCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]));


                } else if (args[0].equalsIgnoreCase("add")) {
                    if (playerExistsAndCommandArgumentsAreValid(args, p)) return true;

                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        if (args.length == 4) {
                            if (args[3].equalsIgnoreCase("-n")) {
                                p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Added Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " to player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                                API.addCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);
                            } else {
                                p.sendMessage(ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                p.sendMessage(ChatColor.RED + "Valid Command Signals: \"-n\"");
                            }
                            return true;
                        }
                        Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.AQUA + args[2] + ChatColor.WHITE + " Network Coins have been added to your account!");
                    }
                    p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Added Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " to player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                    API.addCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);

                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (playerExistsAndCommandArgumentsAreValid(args, p)) return true;

                    if (Bukkit.getPlayerExact(args[1]) != null) {
                        if (args.length == 4) {
                            if (args[3].equalsIgnoreCase("-n")) {
                                p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Removed Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " from player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                                API.removeCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);
                            } else {
                                p.sendMessage(ChatColor.RED + "Error: Bad command signal... -> " + args[3]);
                                p.sendMessage(ChatColor.RED + "Valid Command Signals: \"-n\"");
                            }
                            return true;
                        }
                        Objects.requireNonNull(Bukkit.getPlayerExact(args[1])).sendMessage(ChatColor.AQUA + args[2] + ChatColor.WHITE + " Network Coins have been removed from your account!");
                    }
                    p.sendMessage(ChatColor.AQUA + "[COINS] " + ChatColor.WHITE + "Removed Network Currency of " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " from player '" + ChatColor.AQUA + args[1] + ChatColor.WHITE + "'.");
                    API.removeCoins(getUUIDFromPlayerName(args[1]), Integer.parseInt(args[2]), false);

                } else {
                    showUsageMessage(commandSender);
                }
            } else {
                showUsageMessage(commandSender);
            }
        }
        return false;
    }

    private boolean playerExistsAndCommandArgumentsAreValid(String[] args, CommandSender p) {
        if (!playerExists(args[1])) {
            p.sendMessage(ChatColor.RED + "Player " + args[1] + " has never logged onto the network before!");
            return true;
        }
        int amount = -1;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Error: Amount must be an integer.");
            return true;
        }
        if (amount < 1) {
            p.sendMessage(ChatColor.RED + "Error: Amount must an integer greater than 1.");
            return true;
        }
        return false;
    }

    public void showUsageMessage(CommandSender sender) {
        sender.sendMessage("\n");
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Usages: ");
        sender.sendMessage(ChatColor.AQUA + "/coins");
        sender.sendMessage(ChatColor.AQUA + "/coins <player name>");
        if (sender.hasPermission("hyko.admin")) {
            sender.sendMessage(ChatColor.GRAY + "?[<Arg>] are optional arguments");
            sender.sendMessage(ChatColor.RED + "/coins set <player name> <amount> ?<notify>");
            sender.sendMessage(ChatColor.RED + "/coins add <player name> <amount> ?<notify>");
            sender.sendMessage(ChatColor.RED + "/coins remove <player name> <amount> ?<notify>");
        }
        sender.sendMessage("\n");
    }

    public String getUUIDFromPlayerName(String playerName) {
        if (playerExists(playerName)) {
            try {
                ResultSet rs = ServerCore.playerDatabase.getResult("SELECT * FROM " + "hyko_player_logger" + " WHERE PLAYER_NAME= '" + playerName + "'");
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
            ResultSet rs = ServerCore.playerDatabase.getResult("SELECT * FROM " + "hyko_player_logger" + " WHERE PLAYER_NAME= '" + name + "'");
            if (rs.next())
                return (rs.getString("UUID") != null);
        } catch (SQLException e) {
            ServerCore.playerDatabase.getConnection();
            e.printStackTrace();
        }
        return false;
    }
}
