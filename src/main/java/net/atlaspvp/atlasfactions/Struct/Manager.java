package net.atlaspvp.atlasfactions.Struct;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.UUID;

public class Manager {
    public static HashMap<UUID, FPlayer> FPlayers;
    public static HashMap<UUID, Faction> Factions;
    public static HashMap<String, String> Claims; // Saved like <ChunkCoordinates as string x;z , FactionUUID;System Time When Made>

    public static void addFaction(Faction faction) {
        Factions.put(faction.getUUID(), faction);
    }

    public static FPlayer getFPlayer(Player player) {
        return FPlayers.get(player.getUniqueId());
    }

    public static Faction getFaction(UUID uuid) {return Factions.get(uuid);}

    public static Faction getFaction(String uuid) {return Factions.get(UUID.fromString(uuid));}

    public static Faction getFactionByName(String name) {
        for (Faction faction : Factions.values()) {
            if (faction.getName().equals(name)) {
                return faction;
            }
        }
        return null;
    }

    public static void claim (int x, int z, String uuid, Long time) {
        Claims.put(mergeCoordinates(x, z), mergeFactionAndTime(uuid, time));
    }

    public static void claim (Chunk chunk, String uuid, Long time) {
        Claims.put(mergeCoordinates(chunk.getX(), chunk.getZ()), mergeFactionAndTime(uuid, time));
    }

    public static Faction getClaimOwner(Chunk chunk) {
        String merged = Claims.get(chunk.getX() + ";" + chunk.getZ());

        if (merged == null) {
            return new Faction("Wilderness", null);
        }

        String[] split = merged.split(";");
        AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<>(split[0], split[1]);
        return getFaction(pair.getKey());
    }

    public static Faction getClaimOwner(int chunkX, int chunkZ) {
        String merged = Claims.get(mergeCoordinates(chunkX, chunkZ));

        if (merged == null) {
            return new Faction("Wilderness", null);
        }

        String[] split = merged.split(";");
        AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<>(split[0], split[1]);
        return getFaction(pair.getKey());
    }

    public static Long getClaimedTime(Chunk chunk) {
        String merged = Claims.get(chunk.getX() + ";" + chunk.getZ());
        String[] split = merged.split(";");
        AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<>(split[0], split[1]);
        return Long.parseLong(pair.getValue());
    }

    public static Long getClaimedTime(int chunkX, int chunkZ) {
        String merged = Claims.get(mergeCoordinates(chunkX, chunkZ));
        String[] split = merged.split(";");
        AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<>(split[0], split[1]);
        return Long.parseLong(pair.getValue());
    }

    public static boolean isClaimed(Chunk chunk) {
        String merged = mergeCoordinates(chunk.getX(), chunk.getZ());
        return Claims.containsKey(merged);
    }

    public static boolean isClaimed(int x, int z) {
        String merged = mergeCoordinates(x, z);
        return Claims.containsKey(merged);
    }

    private static String mergeCoordinates(int x, int z) {
        return x + ";" + z;
    }

    private static String mergeFactionAndTime(String factionUUID, long time) {
        return factionUUID + ";" + time;
    }
}
