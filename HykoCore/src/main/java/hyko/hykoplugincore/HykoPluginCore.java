package hyko.hykoplugincore;

import hyko.hykoplugincore.SQL.SQLManager;
import hyko.hykoplugincore.SQL.TableType;
import hyko.hykoplugincore.commands.*;
import hyko.hykoplugincore.commands.friends.FriendCommand;
import hyko.hykoplugincore.commands.friends.FriendsCommand;
import hyko.hykoplugincore.events.NetworkQuitEvent;
import hyko.hykoplugincore.events.ServerJoinEvent;
import hyko.hykoplugincore.events.SetupFriendEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public final class HykoPluginCore extends Plugin {

    public static File file;
    public static Configuration configuration;
    public static HykoPluginCore instance;
    public static SQLManager playerDatabase;

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
        getProxy().getPluginManager().registerCommand(this, new FriendCommand());
        getProxy().getPluginManager().registerCommand(this, new FriendsCommand());
        getProxy().getPluginManager().registerListener(this, new SetupFriendEvent());
        getProxy().getPluginManager().registerListener(this, new ServerJoinEvent());
        getProxy().getPluginManager().registerListener(this, new NetworkQuitEvent());

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

        playerDatabase = new SQLManager("manager.aquatis.host", "Interryne_HykoBungeePlayers", "Interryne", "1162dover");
        playerDatabase.getConnection(); // Connect
        playerDatabase.createDatabase(TableType.PLAYER_DATABASE);
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
}
