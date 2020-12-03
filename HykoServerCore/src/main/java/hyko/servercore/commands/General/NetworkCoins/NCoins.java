package hyko.servercore.commands.General.NetworkCoins;

import hyko.servercore.HykoConfigMessagesConvert;
import hyko.servercore.MessageType;
import hyko.servercore.ServerCore;
import me.cedox.api.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NCoins implements CommandExecutor {

    private final HykoConfigMessagesConvert configConvert;
    private final ServerCore plugin;

    public NCoins(ServerCore plugin) {
        this.plugin = plugin;
        this.configConvert = new HykoConfigMessagesConvert(plugin);
    }

    //TODO: DO OFFLINE PLAYER MODIFICATIONS
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ncoins")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    // /ncoins - Show amount of coins player has
                    p.sendMessage(ChatColor.GRAY + "Your Network Coins: " + ChatColor.YELLOW + API.getCoins(p.getUniqueId().toString()));
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("get")) {
                        if (p.hasPermission("hyko.coins.get")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target == null) {
                                p.sendMessage(ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                                return true;
                            }
                            p.sendMessage(ChatColor.YELLOW + target.getName() + "'s " + ChatColor.GRAY + "Network Coins: " + ChatColor.GREEN + API.getCoins(target.getUniqueId().toString()));
                            return true;
                        } else {
                            p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                        }

                    } else {
                        p.sendMessage(ChatColor.RED + "Usage: /ncoins,\n /ncoins <get> <player>,\n /ncoins <add|set|remove> <player> <amount>");
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (p.hasPermission("hyko.coins.set")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target == null) {
                                p.sendMessage(ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                                return true;
                            }
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be an Integer.");
                                return true;
                            }
                            if (amount < 1) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be >0.");
                                return true;
                            }
                            API.setCoins(target.getUniqueId().toString(), amount);
                            p.sendMessage(ChatColor.GRAY + "Set Network Coins of " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + ".");
                            target.sendMessage(ChatColor.GRAY + "Your Network Coin balance has been changed to " + ChatColor.YELLOW + args[2]);
                            return true;
                        } else {
                            p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                        }
                    } else if (args[0].equalsIgnoreCase("add")) {
                        if (p.hasPermission("hyko.coins.add")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target == null) {
                                p.sendMessage(ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                                return true;
                            }
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be an Integer.");
                                return true;
                            }
                            if (amount < 1) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be >0.");
                                return true;
                            }
                            API.addCoins(target.getUniqueId().toString(), amount, false);
                            p.sendMessage(ChatColor.GRAY + "Added Network Coins of " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                            target.sendMessage(ChatColor.YELLOW + args[2] + ChatColor.GRAY + " Network Coins have been added to your account!");
                            return true;

                        } else {
                            p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                        }

                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (p.hasPermission("hyko.coins.remove")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (target == null) {
                                p.sendMessage(ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                                p.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                                return true;
                            }
                            int amount = -1;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be an Integer.");
                                return true;
                            }
                            if (amount < 1) {
                                p.sendMessage(ChatColor.RED + "Network Coins amount must be >0.");
                                return true;
                            }
                            API.removeCoins(target.getUniqueId().toString(), amount, false);
                            p.sendMessage(ChatColor.GRAY + "Removed Network Coins of " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " from " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                            target.sendMessage(ChatColor.YELLOW + args[2] + ChatColor.GRAY + " Network Coins have been removed from your account!");
                            return true;
                        } else {
                            p.sendMessage(configConvert.getMessage(MessageType.badPermission));
                        }

                    } else {
                        p.sendMessage(ChatColor.RED + "Usage: /ncoins,\n /ncoins <get> <player>,\n /ncoins <add|set|remove> <player> <amount>");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Usage: /ncoins,\n /ncoins <get> <player>,\n /ncoins <add|set|remove> <player> <amount>");
                }
            } else {
                if (args.length == 3) {
                    final String consolePrefix = ChatColor.AQUA + "[CONSOLE] ";
                    if (args[0].equalsIgnoreCase("set")) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                            sender.sendMessage(consolePrefix + ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                            return true;
                        }
                        int amount = -1;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be an Integer.");
                            return true;
                        }
                        if (amount < 1) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be >0.");
                            return true;
                        }
                        API.setCoins(target.getUniqueId().toString(), amount);
                        sender.sendMessage(consolePrefix + ChatColor.GRAY + "Set Network Coins of " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + " to " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + ".");
                        target.sendMessage(ChatColor.GRAY + "Your Network Coin balance has been changed to " + ChatColor.YELLOW + args[2]);
                        return true;
                    } else if (args[0].equalsIgnoreCase("add")) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                            sender.sendMessage(consolePrefix + ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                            return true;
                        }
                        int amount = -1;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be an Integer.");
                            return true;
                        }
                        if (amount < 1) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be >0.");
                            return true;
                        }
                        API.addCoins(target.getUniqueId().toString(), amount, false);
                        sender.sendMessage(consolePrefix + ChatColor.GRAY + "Added Network Coins of " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " to " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                        target.sendMessage(ChatColor.YELLOW + args[2] + ChatColor.GRAY + " Network Coins have been added to your account!");
                        return true;

                    } else if (args[0].equalsIgnoreCase("remove")) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Could not locate player: " + args[1] + ". Are they online?");
                            sender.sendMessage(consolePrefix + ChatColor.GRAY.toString() + ChatColor.ITALIC + "If you would still like to edit offline player " + args[1] + "'s coins then do /unsafecoins instead of /coins!");
                            return true;
                        }
                        int amount = -1;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be an Integer.");
                            return true;
                        }
                        if (amount < 1) {
                            sender.sendMessage(consolePrefix + ChatColor.RED + "Network Coins amount must be >0.");
                            return true;
                        }
                        API.removeCoins(target.getUniqueId().toString(), amount, false);
                        sender.sendMessage(consolePrefix + ChatColor.GRAY + "Removed Network Coins of " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " from " + ChatColor.YELLOW + target.getName() + ChatColor.GRAY + ".");
                        target.sendMessage(ChatColor.YELLOW + args[2] + ChatColor.GRAY + " Network Coins have been removed from your account!");
                        return true;
                    } else {
                        sender.sendMessage(consolePrefix + ChatColor.RED + "Usage: /ncoins,\n /ncoins <get> <player>,\n /ncoins <add|set|remove> <player> <amount>");
                    }
                }else{
                    sender.sendMessage(ChatColor.AQUA + "[CONSOLE] " + ChatColor.RED + "/coins <set|add|remove> <player> <amount>");
                }
            }
        }
        return false;
    }
}
