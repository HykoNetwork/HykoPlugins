package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoPluginCore;
import hyko.hykoplugincore.SQL.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RankCommand extends Command {

    public RankCommand() {
        super("rank", "hyko.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            //rank set <playername> <rank>
            if(args.length < 2) {
                p.sendMessage(new TextComponent("/rank set <playername> <rank>"));
                p.sendMessage(new TextComponent("/rank clear <playername>"));
                return;
            }
            if(args[0].equalsIgnoreCase("set")) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(new TextComponent("Player " + args[1] + " not found."));
                    return;
                }
                if(args[2].equalsIgnoreCase(RANK.HELPER.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.HELPER);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.HELPER.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.MOD.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.MOD);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.MOD.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.ADMIN.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.ADMIN);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.ADMIN.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.DEVELOPER.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.DEVELOPER);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.DEVELOPER.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.MANAGER.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.MANAGER);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.MANAGER.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.OWNER.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.OWNER);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.OWNER.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else if(args[2].equalsIgnoreCase(RANK.STAFF.toString())) {
                    setPlayerRank(HykoPluginCore.staffRankDatabase, target, RANK.STAFF);
                    p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Set player '" + ChatColor.AQUA + target.getName() + ChatColor.WHITE + "' to rank '" + ChatColor.AQUA + RANK.STAFF.toString() + ChatColor.WHITE + "'."));
                    target.sendMessage(new TextComponent(ChatColor.GREEN + "You rank has been changed!"));
                }else{
                    p.sendMessage(new TextComponent("Could not parse ranks. Ranks allowed: HELPER,MOD,ADMIN,DEVELOPER,MANAGER,OWNER,STAFF"));
                    return;
                }
            }else if(args[0].equalsIgnoreCase("clear")) {
                //rank clear <player>
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                if(target == null) {
                    p.sendMessage(new TextComponent("Player " + args[1] + " not found."));
                    return;
                }
                deletePlayer(HykoPluginCore.staffRankDatabase, target);
                p.sendMessage(new TextComponent(ChatColor.AQUA + "[RANK] " + ChatColor.WHITE + "Removed player '"+ChatColor.AQUA+target.getName()+ChatColor.WHITE+"' from Database."));
            }else{
                p.sendMessage(new TextComponent("/rank set <playername> <rank>"));
                p.sendMessage(new TextComponent("/rank clear <playername>"));
            }
        }
    }

    public void setPlayerRank(SQLManager manager, ProxiedPlayer target, RANK rank) {

        manager.update("DELETE FROM hyko_staff WHERE UUID='" + target.getUniqueId().toString()+"'");
        manager.update("INSERT INTO hyko_staff(UUID, RANK) VALUES ('" + target.getUniqueId().toString() + "', '" + rank.toString() + "');");
    }

    public void deletePlayer(SQLManager manager, ProxiedPlayer target) {
        manager.update("DELETE FROM hyko_staff WHERE UUID='" + target.getUniqueId().toString()+"'");
    }
}


enum RANK {
    STAFF {
        public String toString() {
            return "STAFF";
        }
    },
    HELPER{
        public String toString() {
            return "HELPER";
        }
    },
    MOD {
        public String toString() {
            return "MOD";
        }
    },
    ADMIN {
        public String toString() {
            return "ADMIN";
        }
    },
    DEVELOPER {
        public String toString() {
            return "DEVELOPER";
        }
    },
    MANAGER {
        public String toString() {
            return "MANAGER";
        }
    },
    OWNER {
        public String toString() {
            return "OWNER";
        }
    };

    public static String serialize(RANK rank) {
        switch(rank) {
            case HELPER:
                return ChatColor.GREEN.toString() + ChatColor.BOLD + "HELPER";
            case MOD:
                return ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "MOD";
            case ADMIN:
                return ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ADMIN";
            case DEVELOPER:
                return ChatColor.BLUE.toString() + ChatColor.BOLD + "DEVELOPER";
            case MANAGER:
                return ChatColor.GOLD.toString() + ChatColor.BOLD + "MANAGER";
            case OWNER:
                return ChatColor.RED.toString() + ChatColor.BOLD + "OWNER";
            default:
                return ChatColor.GRAY.toString() + ChatColor.BOLD + "STAFF";
        }
    }

}
