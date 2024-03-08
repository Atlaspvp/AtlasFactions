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
            FPlayer fplayer = Manager.getFPlayer(player);
            if (Faction.isWilderness(player.getChunk())){
                PersistentDataContainer chunkPDC = player.getChunk().getPersistentDataContainer();
                chunkPDC.set(new NamespacedKey(AtlasFactions.getInst(), "claimed"), PersistentDataType.STRING, fplayer.getFaction().getUUID().toString());
            }
        }
    }

    @Command({"f unclaim"})
    @CommandPermission("faction.unclaim")
    public void removeClaim(Player player) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (Faction.isSameFaction(player.getChunk(), fplayer.getFaction())){
                PersistentDataContainer chunkPDC = player.getChunk().getPersistentDataContainer();
                chunkPDC.remove(Faction.CLAIM_KEY);
            }
        }
    }

}
