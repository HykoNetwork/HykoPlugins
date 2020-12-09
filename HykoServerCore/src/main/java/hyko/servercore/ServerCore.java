package hyko.servercore;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import hyko.servercore.SQL.SQLManager;
import hyko.servercore.SQL.TableType;
import hyko.servercore.commands.Creative.testcreative;
import hyko.servercore.commands.General.HotbarMessage;
import hyko.servercore.commands.General.NetworkCurrency.Coins;
import hyko.servercore.commands.General.NetworkCurrency.CurrencyInfoCommand;
import hyko.servercore.commands.General.ReloadConfig;
import hyko.servercore.commands.General.testgeneral;
import hyko.servercore.commands.Hub.testhub;
import hyko.servercore.config.ConfigManager;
import hyko.servercore.events.Hub.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class ServerCore extends JavaPlugin implements PluginMessageListener {

    // Number of players on each server
    // 0 = Creative
    private int playerAmountCreative = 0;
    public static SQLManager playerDatabase;
    public static ArrayList<UUID> playersUsingCurrencyInfo = new ArrayList<UUID>();




    /**
     * Sends back the server-id the player is on.
     *
     * @param p
     * @return
     */
    public static ServerID getPlayerServerID(Player p) {
        if (p.getServer().getPort() == 25566) {
            return ServerID.HUB;
        } else if (p.getServer().getPort() == 25567) {
            return ServerID.CREATIVE;
        } else {
            return ServerID.NULL;
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        ConfigManager messagesFile = new ConfigManager(this, "messages.yml");
        saveResource("messages.yml", false);

        playerDatabase = new SQLManager(this, "185.236.137.201", "mc78201", "mc78201", "189678353c");
        playerDatabase.getConnection();
        playerDatabase.createDatabase("hyko_player_logger", TableType.PLAYER_DATABASE);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            // Do something
            Bukkit.getConsoleSender().sendMessage("Updating player counts.");
            playerAmountCreative = getPlayerCount(ServerID.CREATIVE);
        }, 0L, (3L * 20L) * 60L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("testgeneral").setExecutor(new testgeneral(this));
        getCommand("testhub").setExecutor(new testhub(this));
        getCommand("testcreative").setExecutor(new testcreative(this));
        getCommand("hykoreload").setExecutor(new ReloadConfig(this));
        getCommand("coins").setExecutor(new Coins(this));
        getCommand("currencyinfo").setExecutor(new CurrencyInfoCommand(this));
        getCommand("hotbarmessage").setExecutor(new HotbarMessage());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new HubJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new GameSelectEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinMessageEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveMessageEvent(this), this);
        //getServer().getPluginManager().registerEvents(new TransferPlayerCurrencyEvent(), this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();

            playerAmountCreative = in.readInt();
        }
    }

    public int getPlayerCount(ServerID type) {
        switch(type) {
            case CREATIVE:
                return playerAmountCreative;
            default:
                return -1;
        }
    }

    public void getCount(Player player, String server) {

        if (server == null) {
            server = "ALL";
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());

    }
}
