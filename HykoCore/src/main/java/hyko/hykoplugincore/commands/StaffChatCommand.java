package hyko.hykoplugincore.commands;

import hyko.hykoplugincore.HykoPluginCore;
import hyko.hykoplugincore.SQL.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super("sc", "hyko.staff");
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;

            TextComponent chatText = new TextComponent("[STAFF] ");
            chatText.setColor(ChatColor.AQUA);

            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }

            TextComponent rankNameText = null;
            try {
                rankNameText = new TextComponent(getPlayerRank(HykoPluginCore.staffRankDatabase, p) + " " + ChatColor.YELLOW+ p.getName()+ChatColor.WHITE+": ");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ChatMgr(chatText, message, rankNameText);
        }else{
            TextComponent chatText = new TextComponent("[STAFF] ");
            chatText.setColor(ChatColor.AQUA);

            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }

            TextComponent rankNameText = new TextComponent(ChatColor.LIGHT_PURPLE + "[CONSOLE]" +ChatColor.WHITE+": ");

            ChatMgr(chatText, message, rankNameText);
        }
    }

    private void ChatMgr(TextComponent chatText, StringBuilder message, TextComponent rankNameText) {
        chatText.addExtra(rankNameText);
        TextComponent messageText = new TextComponent(ChatColor.translateAlternateColorCodes('&', message.toString()));
        chatText.addExtra(messageText);

        for(ProxiedPlayer onlinePlayer: ProxyServer.getInstance().getPlayers()) {

            if(onlinePlayer.hasPermission("hyko.staff")) {
                onlinePlayer.sendMessage(chatText);
            }
        }
    }

    private String getPlayerRank(SQLManager manager, ProxiedPlayer p) throws SQLException {
        ResultSet rs = manager.getResult("SELECT * FROM hyko_staff WHERE UUID='"+p.getUniqueId().toString()+"'");
        if(rs.next()) {
            return RANK.serialize(RANK.valueOf(rs.getString("RANK")));
        }
        return RANK.serialize(RANK.STAFF);
    }


    // Make method to get rank
    public boolean playerIsStaff(SQLManager manager, ProxiedPlayer p) throws SQLException {
            ResultSet rs = manager.getResult("SELECT * FROM hyko_staff WHERE UUID='"+p.getUniqueId().toString()+"'");
            if(rs.next()) {
                return (rs.getString("UUID") != null);
            }
        return false;
    }
}
