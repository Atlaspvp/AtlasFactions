package net.atlaspvp.atlasfactions.Struct;

import com.rabbitmq.client.ConnectionFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Configuration {

    private static org.bukkit.plugin.Plugin Plugin;
    private static File file;
    private static FileConfiguration config;
    public static ConnectionFactory factory;

    public static int RabbitPort = 0;
    public static String RabbitIP = "";

    public static void Initialize(Plugin plugin) {
        Plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        RabbitIP = config.getString("rabbitmq.ip");
        RabbitPort = config.getInt("rabbitmq.port");

        factory = new ConnectionFactory();
        factory.setHost(getRabbitIP());
        factory.setPort(getRabbitPort());
        factory.setUsername("adfeahajulort");
        factory.setPassword("oyqiwebyif");

    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static String getRabbitIP() {
        return RabbitIP;
    }

    public static int getRabbitPort() {
        return RabbitPort;
    }
}
