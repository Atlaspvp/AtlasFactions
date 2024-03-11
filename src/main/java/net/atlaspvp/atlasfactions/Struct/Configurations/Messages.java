package net.atlaspvp.atlasfactions.Struct.Configurations;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Messages {

    private static File file;
    private static FileConfiguration config;

    private static String[] fShow;
    private static String[] fCreate;
    private static String fInvite;
    private static String fJoin;
    private static String fClaim;
    private static String fMap;
    private static String newRegion;

    public static void Initialize(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        return config;
    }


}
