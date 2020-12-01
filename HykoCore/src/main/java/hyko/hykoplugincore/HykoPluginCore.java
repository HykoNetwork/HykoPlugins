package hyko.hykoplugincore;

import hyko.hykoplugincore.commands.HubCommand;
import hyko.hykoplugincore.commands.TestWorkingCommand;
import hyko.hykoplugincore.commands.WarpCommand;
import net.md_5.bungee.api.plugin.Plugin;

public final class HykoPluginCore extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially loaded successfully.");
        getLogger().info("\n");

        getProxy().getPluginManager().registerCommand(this, new TestWorkingCommand());
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
        getProxy().getPluginManager().registerCommand(this, new WarpCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially been disabled successfully.");
        getLogger().info("\n");
    }
}
