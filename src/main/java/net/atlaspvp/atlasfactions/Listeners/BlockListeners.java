package net.atlaspvp.atlasfactions.Listeners;

import net.atlaspvp.atlasfactions.CMD.Claim;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BlockListeners implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Faction faction = Manager.getFPlayer(event.getPlayer()).getFaction();
        if (Manager.isClaimed(event.getBlock().getChunk())) {
            if (!Faction.isSameFaction(faction, Manager.getClaimOwner(event.getBlock().getChunk()))) {
                event.setCancelled(true);
                Faction faction1 = Manager.getClaimOwner(event.getBlock().getChunk());
                event.getPlayer().sendMessage("You cannot place blocks in " + faction1.getName());
            }
        }
    }
}
