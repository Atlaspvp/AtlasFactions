package net.atlaspvp.atlasfactions;

import net.atlaspvp.atlasfactions.CMD.*;
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

        if (Manager.Claims == null) {
            Manager.Claims = new HashMap<>();
        }

        Consumer.pullFaction();
        Consumer.pullFPlayers();
        Consumer.pullClaims();

        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);

        this.commandHandler = BukkitCommandHandler.create(this);
        commandHandler.register(new Claim());
        commandHandler.register(new Map())
        commandHandler.register(new Create());
        commandHandler.register(new Desc());
        commandHandler.register(new Home());
        commandHandler.register(new Warps());
        commandHandler.register(new Invite());
        commandHandler.register(new Join());
        commandHandler.register(new Leave());
    }

    @Override
    public void onDisable() {
        Sender.sendFaction();
        Sender.sendFPlayer();
        Sender.sendClaims();
    }

    public static AtlasFactions getInst(){
        return instance;
    }
}
