package net.atlaspvp.atlasfactions.Struct;

import com.google.common.collect.Maps;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Manager {
    public static HashMap<UUID, FPlayer> FPlayers;
    public static HashMap<UUID, Faction> Factions;

    public static void addFaction(Faction faction) {
        Factions.put(faction.getUUID(), faction);
    }

    public static FPlayer getFPlayer(Player player) {
        return FPlayers.get(player.getUniqueId());
    }

    public static Faction getFaction(UUID uuid) {return Factions.get(uuid);}

    public static Faction getFaction(String uuid) {return Factions.get(UUID.fromString(uuid));}
}
