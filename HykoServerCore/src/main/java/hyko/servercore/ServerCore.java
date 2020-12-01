package hyko.servercore;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import hyko.servercore.commands.Creative.testcreative;
import hyko.servercore.commands.General.testgeneral;
import hyko.servercore.commands.Hub.testhub;
import hyko.servercore.events.Hub.GameSelectEvent;
import hyko.servercore.events.Hub.HubJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class ServerCore extends JavaPlugin implements PluginMessageListener {

    // Number of players on each server
    // 0 = Creative
    public int[] numOfPlayers = new int[1];

    /**
     * Sends back the server-id the player is on.
     *
     * @param p
     * @return
     */
    public static ServerID isPlayerOnRequiredServer(Player p) {
        if(p.getServer().getPort() == 25566) {
            return ServerID.HUB;
        }else if(p.getServer().getPort() == 25567) {
            return ServerID.CREATIVE;
        }else{
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        getCommand("testgeneral").setExecutor(new testgeneral());
        getCommand("testhub").setExecutor(new testhub());
        getCommand("testcreative").setExecutor(new testcreative());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new HubJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new GameSelectEvent(this), this);
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

            numOfPlayers[0] = playerCount;
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
