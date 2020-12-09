package hyko.hykoplugincore;

import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyAPI;
import de.simonsator.partyandfriends.api.party.PartyManager;
import hyko.hykoplugincore.SQL.SQLManager;
import hyko.hykoplugincore.SQL.TableType;
import hyko.hykoplugincore.commands.*;
import hyko.hykoplugincore.events.NetworkQuitEvent;
import hyko.hykoplugincore.events.ServerJoinEvent;
import hyko.hykoplugincore.events.SetupJoinEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class HykoPluginCore extends Plugin {

    public static File file;
    public static Configuration configuration;
    public static HykoPluginCore instance;
    public static SQLManager playerDatabase;
    public static SQLManager staffRankDatabase;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially loaded successfully.");
        getLogger().info("\n");

        getProxy().getPluginManager().registerCommand(this, new TestWorkingCommand());
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
        getProxy().getPluginManager().registerCommand(this, new WarpCommand());
        getProxy().getPluginManager().registerCommand(this, new ApplyCommand());
        getProxy().getPluginManager().registerCommand(this, new HelpCommand());
        getProxy().getPluginManager().registerCommand(this, new OpMeCommand());
        getProxy().getPluginManager().registerCommand(this, new CleanMessageCommand());
        getProxy().getPluginManager().registerListener(this, new SetupJoinEvent());
        getProxy().getPluginManager().registerListener(this, new ServerJoinEvent());
        getProxy().getPluginManager().registerListener(this, new NetworkQuitEvent());
        getProxy().getPluginManager().registerCommand(this, new PortCommand());
        getProxy().getPluginManager().registerCommand(this, new ProxyKickCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand());
        getProxy().getPluginManager().registerCommand(this, new RankCommand());
        file = new File(ProxyServer.getInstance().getPluginsFolder() + "/data.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerDatabase = new SQLManager("185.236.137.201", "mc78201", "mc78201", "189678353c");
        playerDatabase.getConnection(); // Connect
        playerDatabase.createDatabase("hyko_player_logger", TableType.PLAYER_DATABASE);

        staffRankDatabase = new SQLManager("185.236.137.201", "mc78201", "mc78201", "189678353c");
        staffRankDatabase.getConnection(); // Connect
        staffRankDatabase.createDatabase("hyko_staff", TableType.STAFF_DATABASE);

        addPartyProxyCoins();
    }

    public static HykoPluginCore getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially been disabled successfully.");
        getLogger().info("\n");
        try {
            playerDatabase.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void executeCommand(String cmd) {
        ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), cmd);
    }

    /**
     * Adds coins if players are in party.
     * Testing = 20 Seconds
     * Real Time = 10 Minutes
     */
    private  void addPartyProxyCoins() {
        final int DEFAULT_COINS = 6;
        final float PARTY_BONUS = 1.25F;
        //TODO: Eventually take off main thread
        HykoPluginCore.getInstance().getProxy().getScheduler().schedule(HykoPluginCore.getInstance(), () -> {
            for(ProxiedPlayer p: ProxyServer.getInstance().getPlayers()) {
                p.getUniqueId();
                if(PartyAPI.getParty(PAFPlayerManager.getInstance().getPlayer(p)) != null) {
                    if(PartyAPI.getParty(PAFPlayerManager.getInstance().getPlayer(p)).getAllPlayers().size() != 1) {
                        // Is in Party + Bonus
                        //executeCommand("gexecute " + p.getServer().getInfo().getName() + " coins add " + p.getName() + " " + (int)(DEFAULT_COINS * PARTY_BONUS) + " -n");

                        TextComponent message = new TextComponent("+" + (int)(DEFAULT_COINS * PARTY_BONUS) + " Coins");
                        message.setColor(ChatColor.GOLD);
                        TextComponent partyBonus = new TextComponent(" (Party Bonus)");
                        partyBonus.setColor(ChatColor.GRAY);
                        message.addExtra(partyBonus);
                        int calculateParty = (int)(DEFAULT_COINS * PARTY_BONUS) - DEFAULT_COINS;
                        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( ChatColor.GOLD + "+" + DEFAULT_COINS + " Coins (Playtime Bonus)\n" + ChatColor.GOLD + "+" + calculateParty + " Coins (Party Bonus)" ) ) );

                        p.sendMessage(message);
                        continue;
                    }
                }
                    //Not in party + Default
                //executeCommand("gexecute " + p.getServer().getInfo().getName() + " coins add " + p.getName() + " " + (int)(DEFAULT_COINS) + " -n");
                executeCommand("gexecute " + p.getServer().getInfo().getName() + " coins add " + p.getName() + " 6 -n");
                TextComponent message = new TextComponent("+" + (DEFAULT_COINS) + " Coins");
                message.setColor(ChatColor.GOLD);
                message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text( ChatColor.GOLD + "+" + DEFAULT_COINS + " Coins (Playtime Bonus)" ) ) );
                p.sendMessage(message);
            }
        }, 0L, 10L, TimeUnit.MINUTES);
    }
}
