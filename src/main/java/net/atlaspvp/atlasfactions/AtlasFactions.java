package net.atlaspvp.atlasfactions;

import net.atlaspvp.atlasfactions.CMD.Claim;
import net.atlaspvp.atlasfactions.CMD.Create;
import net.atlaspvp.atlasfactions.CMD.Desc;
import net.atlaspvp.atlasfactions.Listeners.BlockListeners;
import net.atlaspvp.atlasfactions.Listeners.PlayerListeners;
import net.atlaspvp.atlasfactions.Memory.Consumer;
import net.atlaspvp.atlasfactions.Memory.Sender;
import net.atlaspvp.atlasfactions.Struct.Configuration;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.HashMap;

public final class AtlasFactions extends JavaPlugin {
    public static AtlasFactions instance;
    private BukkitCommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        Configuration.Initialize(this);

        if (Manager.FPlayers == null) {
            Manager.FPlayers = new HashMap<>();
        }

        if (Manager.Factions == null) {
            Manager.Factions = new HashMap<>();
        }

        Consumer.pullFaction();
        Consumer.pullFPlayers();

        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        this.commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(new Claim());
        commandHandler.register(new Create());
        commandHandler.register(new Desc());
    }

    @Override
    public void onDisable() {
        Sender.sendFaction();
        Sender.sendFPlayer();
    }

    public static AtlasFactions getInst(){
        return instance;
    }
}
