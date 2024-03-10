package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Struct.Manager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {

    @Command({"f map"})
    @CommandPermission("faction.map")
    public void showMap(final Player player) {
        presentToPlayer(player, 10);
    }

    public static Set<Chunk> getChunksInRadius(Player player, int radius) {
        Set<Chunk> chunks = new HashSet<>();
        Chunk centerChunk = player.getLocation().getChunk();
        int centerChunkX = centerChunk.getX();
        int centerChunkZ = centerChunk.getZ();

        for (int x = centerChunkX - radius; x <= centerChunkX + radius; x++) {
            for (int z = centerChunkZ - radius; z <= centerChunkZ + radius; z++) {
                chunks.add(player.getWorld().getChunkAt(x, z));
            }
        }
        return chunks;
    }

    private static String getDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
            if (0 <= rotation && rotation < 22.5) {
                return "North";
            }
            if (22.5 <= rotation && rotation < 67.5) {
                return "NorthEast";
            }
            if (67.5 <= rotation && rotation < 112.5) {
                return "East";
            }
            if (112.5 <= rotation && rotation < 157.5) {
                return "SouthEast";
            }
            if (157.5 <= rotation && rotation < 202.5) {
                return "South";
            }
            if (202.5 <= rotation && rotation < 247.5) {
                return "SouthWest";
            }
            if (247.5 <= rotation && rotation < 292.5) {
                return "West";
            }
            if (292.5 <= rotation && rotation < 337.5) {
                return "NorthWest";
            }
            if (337.5 <= rotation && rotation < 359) {
                return "North";
            }
        }
        return null;
    }

    public static void presentToPlayer(Player player, int radius) {
        Set<Chunk> chunks = getChunksInRadius(player, radius);
        NamedTextColor facing;

        Chunk centerChunk = player.getLocation().getChunk();
        int centerChunkX = centerChunk.getX();
        int centerChunkZ = centerChunk.getZ();

        for (int z = centerChunkZ - radius; z <= centerChunkZ + radius; z++) {
            Component line = Component.empty();
            for (int x = centerChunkX - radius; x <= centerChunkX + radius; x++) {
                if (x == centerChunkX && z == centerChunkZ) {
                    line = line.append(Component.text("x", NamedTextColor.RED));
                } else if (chunks.contains(player.getWorld().getChunkAt(x, z))) {
                    if (z <= centerChunkZ - radius + 2 && x <= centerChunkX - radius + 2) {
                        if (x == centerChunkX - radius + 1 && z == centerChunkZ - radius + 1) {
                            line = line.append(Component.text("+", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius && z == centerChunkZ - radius) {
                            if (getDirection(player) == "SouthWest") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("\\", facing));
                        } else if (x == centerChunkX - radius && z == centerChunkZ - radius + 1) {
                            if (getDirection(player) == "West") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("W", facing));

                        } else if (x == centerChunkX - radius && z == centerChunkZ - radius + 2) {
                            if (getDirection(player) == "SouthEast") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("/", facing));
                        } else if (x == centerChunkX - radius + 1 && z == centerChunkZ - radius) {
                            if (getDirection(player) == "North") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("N", facing));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius) {
                            if (getDirection(player) == "NorthWest") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("/", facing));
                        } else if (x == centerChunkX - radius + 1 && z == centerChunkZ - radius + 2) {
                            if (getDirection(player) == "South") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("S", facing));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius + 1) {
                            if (getDirection(player) == "West") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("E", facing));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius + 2) {
                            if (getDirection(player) == "NorthEast") {facing = NamedTextColor.RED;} else {facing = NamedTextColor.BLUE;}
                            line = line.append(Component.text("\\", facing));
                        } else {
                            line = line.append(Component.text("-", NamedTextColor.WHITE));
                        }
                    } else {
                        line = line.append(Component.text("-", NamedTextColor.WHITE));
                    }
                } else {
                    line = line.append(Component.text("-", NamedTextColor.WHITE));
                }
            }
            player.sendMessage(line);
        }
    }
}


