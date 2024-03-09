package net.atlaspvp.atlasfactions.Listeners;

import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.atlaspvp.atlasfactions.Objects.Faction.CLAIM_KEY;

public class PlayerListeners implements Listener {
    private final Map<UUID, Chunk> playerSpecificChunkMap = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (Manager.FPlayers.containsKey(player.getUniqueId())) {
            return;
        }

        FPlayer fplayer = new FPlayer(player);
        Manager.FPlayers.put(player.getUniqueId(), fplayer);
    }

//    you can modify/change this however you like, just thought this might be an efficient way to check for chunk switches
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location location = event.getTo();
        Chunk currChunk = location.getChunk();

        currChunk.getX();
        currChunk.getZ();

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        Chunk prevChunk = playerSpecificChunkMap.get(playerUUID);

        if (prevChunk == null || !prevChunk.equals(currChunk)) {
            if (prevChunk != null) {
                PersistentDataContainer prevChunkPDC = prevChunk.getPersistentDataContainer();
                PersistentDataContainer currChunkPDC = currChunk.getPersistentDataContainer();
                if (!prevChunkPDC.getKeys().equals(currChunkPDC.getKeys())) {
                    if (currChunkPDC.getKeys().contains(CLAIM_KEY)) {
                        String factionUUID = currChunkPDC.get(CLAIM_KEY, PersistentDataType.STRING);
                        assert factionUUID != null;
                        Faction faction = Manager.getFaction(UUID.fromString(factionUUID));
                        player.sendMessage("You are now in " + faction.getName() + "'s territory");
                    } else {
                        player.sendMessage("You are now in unclaimed territory");
                    }
                }
            }
            playerSpecificChunkMap.put(playerUUID, currChunk);
        }
    }
}
