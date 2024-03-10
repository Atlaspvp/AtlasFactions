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
        Chunk currChunk = event.getTo().getChunk();
        UUID playerUUID = event.getPlayer().getUniqueId();

        Chunk prevChunk = playerSpecificChunkMap.get(playerUUID);

        if (prevChunk == null || !prevChunk.equals(currChunk)) {
            if (prevChunk != null) {
                Faction faction1 = Manager.getClaimOwner(prevChunk);
                Faction faction2 = Manager.getClaimOwner(currChunk);
                if (!faction1.equals(faction2)) {
                    if (!faction1.getName().equalsIgnoreCase("Wilderness") && faction2.getName().equalsIgnoreCase("Wilderness")) {
                        event.getPlayer().sendMessage("You have entered the wilderness");
                    } else if (!faction1.getName().equalsIgnoreCase(faction2.getName())) {
                        event.getPlayer().sendMessage("You have entered " + faction2.getName());
                    }
                }
            }
            playerSpecificChunkMap.put(playerUUID, currChunk);
        }

        FPlayer fPlayer = Manager.getFPlayer(event.getPlayer());
        if (fPlayer.isMapOn()) {
            if (!currChunk.equals(prevChunk)) {
                net.atlaspvp.atlasfactions.CMD.Map.presentToPlayer(event.getPlayer(), 8);
            }
        }
    }
}
