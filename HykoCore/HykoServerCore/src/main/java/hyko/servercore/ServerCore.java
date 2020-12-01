package hyko.servercore;

import hyko.servercore.commands.Creative.testcreative;
import hyko.servercore.commands.General.testgeneral;
import hyko.servercore.commands.Hub.testhub;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();
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

    }

    /**
     * Sends back the server-id the player is on.
     * @param p
     * @return
     */
    public static ServerID isPlayerOnRequiredServer(Player p) {
        if(p.getServer().getMotd().toLowerCase().contains("creative")) {
            return ServerID.CREATIVE;
        }
        if(p.getServer().getMotd().toLowerCase().contains("hub")) {
            return ServerID.HUB;
        }
        return ServerID.NULL;
    }
}
