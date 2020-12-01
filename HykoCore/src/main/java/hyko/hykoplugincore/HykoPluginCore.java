package hyko.hykoplugincore;

import hyko.hykoplugincore.commands.TestWorkingCommand;
import net.md_5.bungee.api.plugin.Plugin;

public final class HykoPluginCore extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially loaded successfully.");
        getLogger().info("\n");

        getProxy().getPluginManager().registerCommand(this, new TestWorkingCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("\n");
        getLogger().info("Hyko Network Plugin Core (1.16.4) has officially been disabled successfully.");
        getLogger().info("\n");
    }
}
