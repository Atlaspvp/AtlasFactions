package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Struct.Manager;
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

        ChatColor directionColor = ChatColor.BLUE;
        ChatColor cornerColor = ChatColor.GREEN;

        for (int z = centerChunkZ - radius; z <= centerChunkZ + radius; z++) {
            StringBuilder line = new StringBuilder();
            for (int x = centerChunkX - radius; x <= centerChunkX + radius; x++) {
                if (x == centerChunkX && z == centerChunkZ) {
                    line.append(ChatColor.RED).append("x");
                } else if (chunks.contains(player.getWorld().getChunkAt(x, z))) {
                    line.append("-");
                } else {
                    if (z == centerChunkZ - radius) {
                        if (x == centerChunkX - radius || x == centerChunkX || x == centerChunkX + radius)
                            line.append(directionColor).append("N");
                        else
                            line.append("-");
                    } else if (z == centerChunkZ) {
                        if (x == centerChunkX - radius)
                            line.append(directionColor).append("W");
                        else if (x == centerChunkX)
                            line.append("-");
                        else if (x == centerChunkX + radius)
                            line.append("E");
                        else
                            line.append("-");
                    } else if (z == centerChunkZ + radius) {
                        if (x == centerChunkX - radius || x == centerChunkX || x == centerChunkX + radius)
                            line.append(directionColor).append("S");
                        else
                            line.append("-");
                    } else {
                        line.append("-");
                    }
                }
            }
            player.sendMessage(line.toString());
        }
    }
}


