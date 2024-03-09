package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.AtlasFactions;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class Claim {

    @Command({"f claim"})
    @CommandPermission("faction.claim")
    public void createClaim(final Player player) {
        if (player != null) {
            Chunk chunk = player.getChunk();
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (!Manager.isClaimed(chunk)){
                    Manager.claim(chunk, fplayer.getFaction().getUUID().toString(), System.currentTimeMillis());
                    player.sendMessage("Claimed chunk");
                } else {
                    // Message
                    player.sendMessage("Is already claimed");
                }
            } else {
                // Message
                player.sendMessage("Not in a faction");
            }
        }
    }

    @Command({"f unclaim"})
    @CommandPermission("faction.unclaim")
    public void removeClaim(Player player) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            Chunk chunk = player.getChunk();
            Faction chunkOwner = Manager.getClaimOwner(chunk);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                if (Faction.isSameFaction(chunkOwner, fplayer.getFaction())) {
                    Manager.Claims.remove(chunk.getX() + ";" + chunk.getZ());
                    player.sendMessage("Unclaimed chunk");
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
