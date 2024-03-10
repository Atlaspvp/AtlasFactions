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

    public static void presentToPlayer(Player player, int radius) {
        Set<Chunk> chunks = getChunksInRadius(player, radius);

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
                        if (x == centerChunkX - radius && z == centerChunkZ - radius) {
                            line = line.append(Component.text("\\", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius && z == centerChunkZ - radius + 1) {
                            line = line.append(Component.text("W", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius && z == centerChunkZ - radius + 2) {
                            line = line.append(Component.text("/", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius + 1 && z == centerChunkZ - radius) {
                            line = line.append(Component.text("N", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius) {
                            line = line.append(Component.text("/", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius + 1 && z == centerChunkZ - radius + 2) {
                            line = line.append(Component.text("S", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius + 1) {
                            line = line.append(Component.text("E", NamedTextColor.BLUE));
                        } else if (x == centerChunkX - radius + 2 && z == centerChunkZ - radius + 2) {
                            line = line.append(Component.text("\\", NamedTextColor.BLUE));
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


