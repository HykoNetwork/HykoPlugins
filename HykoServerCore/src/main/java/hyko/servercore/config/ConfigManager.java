package hyko.servercore.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {

    private final JavaPlugin plugin;
    private final File configFile;
    private YamlConfiguration yamlConfig;

    public ConfigManager(JavaPlugin plugin, String filename) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), filename);

        if (!configFile.exists()) {

            saveDefaultConfiguration();
        }

        this.yamlConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(JavaPlugin p, String filename) {
        try {
            File f = new File(p.getDataFolder(), filename);
            this.getConfig().save(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Reloads the config set in the constructor.
     */
    public void reloadConfig(String filename) {
        this.yamlConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), filename));
        // BasicX.getMainClass().getLogger().info("RELOAD CONFIG");
    }

    private void saveDefaultConfiguration() {
        try {
            InputStream input = plugin.getResource(configFile.getName());

            // Output file MUST NOT EXIST! If it does, an IOException is thrown
            java.nio.file.Files.copy(input, configFile.toPath()); // Fully qualified name for clarification
        } catch (IOException e) {
            // Send an error message, etc.
        }
    }

    public YamlConfiguration getConfig() {
        return this.yamlConfig;
    }

}