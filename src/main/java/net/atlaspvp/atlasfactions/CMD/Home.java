package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class Home {
    @Command({"f sethome"})
    @CommandPermission("faction.sethome")
    public void setHome(Player player) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            Chunk chunk = player.getChunk();
            Faction chunkOwner = Manager.getClaimOwner(chunk);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (Faction.isSameFaction(chunkOwner, fplayer.getFaction())) {
                    fplayer.getFaction().setHome(player.getLocation());
                    player.sendMessage("Set faction home");
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

    @Command({"f delhome"})
    @CommandPermission("faction.delhome")
    public void deleteHome(Player player) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (fplayer.getFaction().getHome() != null) {
                    fplayer.getFaction().setHome(null);
                    player.sendMessage("Removed faction home");
                } else {
                    // Message
                    player.sendMessage("No faction home set");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }

    @Command({"f home"})
    @CommandPermission("faction.home")
    public void goToHome(Player player) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (fplayer.getFaction().getHome() != null) {
                    player.teleport(fplayer.getFaction().getHome());
                    player.sendMessage("Teleported to faction home");
                } else {
                    // Message
                    player.sendMessage("No faction home set");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }
}
