package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Objects.SerLocation;
import net.atlaspvp.atlasfactions.Perm.Role;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;

public class Warps {
    @Command({"f setwarp"})
    @CommandPermission("faction.setwarp")
    public void setWarp(final Player player, final String name) {
        if (name.matches("^[a-zA-Z0-9]{3,10}$")) {
            if (player != null) {
//                IF FACTION HAS MORE WARP ROOM
                FPlayer fplayer = Manager.getFPlayer(player);
                Chunk chunk = player.getChunk();
                Faction chunkOwner = Manager.getClaimOwner(chunk);
                if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                    if (Faction.isSameFaction(chunkOwner, fplayer.getFaction())) {
                        if (fplayer.getFaction().getWarps() == null) {
                            fplayer.getFaction().setWarps(new ArrayList<>());
                        }
                        fplayer.getFaction().getWarps().add(new SerLocation(player.getLocation(), name));
                        player.sendMessage("Set faction warp " + name);
                    } else {
                        // Message
                        player.sendMessage("Not your land");
                    }
                } else {
                    // Message
                    player.sendMessage("No faction");
                }
            }
        }
    }


    @Command({"f delwarp"})
    @CommandPermission("faction.delwarp")
    public void deleteWarp(final Player player, final String name) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (fplayer.getFaction().getWarps() != null) {
                    for (SerLocation warp : fplayer.getFaction().getWarps()) {
                        if (warp.getName().equalsIgnoreCase(name)) {
                            fplayer.getFaction().getWarps().remove(warp);
                            player.sendMessage("Removed faction warp " + name);
                            return;
                        }
                    }
                    player.sendMessage("Faction warp " + name + " not found");
                } else {
                    // Message
                    player.sendMessage("No warps");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }

    @Command({"f warp"})
    @CommandPermission("faction.warp")
    public void goToWarp(final Player player, final String name) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (fplayer.getFaction().getWarps() != null) {
                    for (SerLocation warp : fplayer.getFaction().getWarps()) {
                        if (warp.getName().equalsIgnoreCase(name)) {
                            player.teleport(SerLocation.locationToLocation(warp));
                            player.sendMessage("Teleported to faction warp " + name);
                            return;
                        }
                    }
                    player.sendMessage("Faction warp " + name + " not found");
                    player.sendMessage("Available warps:");
                    for (SerLocation warp : fplayer.getFaction().getWarps()) {
                        player.sendMessage(warp.getName());
                    }
                } else {
                    // Message
                    player.sendMessage("No warps");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }
}
