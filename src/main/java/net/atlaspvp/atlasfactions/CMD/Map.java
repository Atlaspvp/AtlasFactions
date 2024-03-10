package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.*;

public class Map {


    @Command({"f map"})
    @CommandPermission("faction.map")
    public void showMap(final Player player, @Optional final String name) {
        if (name != null) {
            if (name.equalsIgnoreCase("on")) {
                presentToPlayer(player, 8);
                Manager.getFPlayer(player).setMapOn(true);
                player.sendMessage("F Map is now on");
            } else if (name.equalsIgnoreCase("off")) {
                Manager.getFPlayer(player).setMapOn(false);
                player.sendMessage("F Map is now off");
            }
        } else {
            presentToPlayer(player, 8);
        }
    }

    public static Set<Chunk> getChunksInRadius(Player player, int width, int height) {
        Set<Chunk> chunks = new HashSet<>();
        Chunk centerChunk = player.getLocation().getChunk();
        int centerChunkX = centerChunk.getX();
        int centerChunkZ = centerChunk.getZ();

        for (int x = centerChunkX - width; x <= centerChunkX + width; x++) {
            for (int z = centerChunkZ - height; z <= centerChunkZ + height; z++) {
                chunks.add(player.getWorld().getChunkAt(x, z));
            }
        }
        return chunks;
    }

    public static void presentToPlayer(Player player, int height) {
        int width = height * 2;
        Set<Chunk> chunks = getChunksInRadius(player, width, height);

        Chunk centerChunk = player.getLocation().getChunk();
        int centerChunkX = centerChunk.getX();
        int centerChunkZ = centerChunk.getZ();

        float yaw = player.getLocation().getYaw();
        yaw = (yaw + 180) % 360;

        NamedTextColor northColor = (yaw >= 337.5 || yaw < 22.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor northEastColor = (yaw >= 22.5 && yaw < 67.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor eastColor = (yaw >= 67.5 && yaw < 112.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor southEastColor = (yaw >= 112.5 && yaw < 157.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor southColor = (yaw >= 157.5 && yaw < 202.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor southWestColor = (yaw >= 202.5 && yaw < 247.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor westColor = (yaw >= 247.5 && yaw < 292.5) ? NamedTextColor.RED : NamedTextColor.BLUE;
        NamedTextColor northWestColor = (yaw >= 292.5 && yaw < 337.5) ? NamedTextColor.RED : NamedTextColor.BLUE;

        HashMap<String, String> factionLetterMap = new HashMap<>();
        char currentLetter = 'A';
        player.sendMessage("");

        for (int z = centerChunkZ - height; z <= centerChunkZ + height; z++) {
            Component line = Component.empty();
            for (int x = centerChunkX - width; x <= centerChunkX + width; x++) {
                Chunk currentChunk = player.getWorld().getChunkAt(x, z);
                if (x == centerChunkX && z == centerChunkZ) {
                    line = line.append(Component.text("x", NamedTextColor.RED));
                } else if (chunks.contains(currentChunk)) {
                    if (z <= centerChunkZ - height + 2 && x <= centerChunkX - width + 2) {
                        if (x == centerChunkX - width + 1 && z == centerChunkZ - height + 1) {
                            line = line.append(Component.text("+", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - width && z == centerChunkZ - height) {
                            line = line.append(Component.text("\\", northWestColor));
                        } else if (x == centerChunkX - width && z == centerChunkZ - height + 1) {
                            line = line.append(Component.text("W", westColor));
                        } else if (x == centerChunkX - width && z == centerChunkZ - height + 2) {
                            line = line.append(Component.text("/", southWestColor));
                        } else if (x == centerChunkX - width + 1 && z == centerChunkZ - height) {
                            line = line.append(Component.text("N", northColor));
                        } else if (x == centerChunkX - width + 2 && z == centerChunkZ - height) {
                            line = line.append(Component.text("/", northEastColor));
                        } else if (x == centerChunkX - width + 1 && z == centerChunkZ - height + 2) {
                            line = line.append(Component.text("S", southColor));
                        } else if (x == centerChunkX - width + 2 && z == centerChunkZ - height + 1) {
                            line = line.append(Component.text("E", eastColor));
                        } else if (x == centerChunkX - width + 2 && z == centerChunkZ - height + 2) {
                            line = line.append(Component.text("\\", southEastColor));
                        } else {
                            Faction faction = Manager.getClaimOwner(currentChunk);
                            if (faction != null && !faction.getName().equals("Wilderness")) {
                                if (!factionLetterMap.containsKey(faction.getUuid().toString())) {
                                    factionLetterMap.put(faction.getUuid().toString(), String.valueOf(currentLetter));
                                    currentLetter++;
                                }
                                line = line.append(Component.text(factionLetterMap.get(faction.getUuid().toString()), NamedTextColor.WHITE));
                            } else {
                                line = line.append(Component.text("-", NamedTextColor.WHITE));
                            }
                        }
                    } else {
                        Faction faction = Manager.getClaimOwner(currentChunk);
                        if (faction != null && !faction.getName().equals("Wilderness")) {
                            if (!factionLetterMap.containsKey(faction.getUuid().toString())) {
                                factionLetterMap.put(faction.getUuid().toString(), String.valueOf(currentLetter));
                                currentLetter++;
                            }
                            line = line.append(Component.text(factionLetterMap.get(faction.getUuid().toString()), NamedTextColor.WHITE));
                        } else {
                            line = line.append(Component.text("-", NamedTextColor.WHITE));
                        }
                    }
                } else {
                    line = line.append(Component.text("-", NamedTextColor.WHITE));
                }
            }
            player.sendMessage(line);
        }

        Component fMapKey = Component.text("Factions on the map:\n", NamedTextColor.WHITE);
        for (java.util.Map.Entry<String, String> entry : factionLetterMap.entrySet()) {
            Faction faction = Manager.getFaction(UUID.fromString(entry.getKey()));
            fMapKey = fMapKey.append(Component.text(faction.getName() + ": " + entry.getValue() + " ", NamedTextColor.WHITE));
        }

        player.sendMessage(fMapKey);
    }
}


