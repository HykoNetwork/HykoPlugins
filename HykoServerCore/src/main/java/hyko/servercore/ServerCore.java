package hyko.servercore;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import hyko.servercore.commands.Creative.testcreative;
import hyko.servercore.commands.General.ReloadConfig;
import hyko.servercore.commands.General.testgeneral;
import hyko.servercore.commands.Hub.testhub;
import hyko.servercore.config.ConfigManager;
import hyko.servercore.events.Hub.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class ServerCore extends JavaPlugin implements PluginMessageListener {

    // Number of players on each server
    // 0 = Creative
    private int playerAmountCreative = 0;


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
        ConfigManager playersFile = new ConfigManager(this, "players.yml");
        saveResource("players.yml", false);
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
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new HubJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new GameSelectEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinMessageEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveMessageEvent(this), this);
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
            int playerCount = in.readInt();

            playerAmountCreative = playerCount;
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
