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
        PersistentDataContainer pdc = event.getBlock().getLocation().getChunk().getPersistentDataContainer();
        Faction faction = Manager.getFPlayer(event.getPlayer()).getFaction();
        if (pdc.getKeys().contains(Faction.CLAIM_KEY)) {
            String claimingfaction = pdc.get(Faction.CLAIM_KEY, PersistentDataType.STRING);
            Faction faction1 = Manager.getFaction(claimingfaction);
            if (faction1 != faction) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("You cannot place blocks in " + Manager.getFaction(claimingfaction).getName());
                System.out.println(UUID.fromString(claimingfaction) + " Comparing to " + faction.getUUID().toString());
            }
        }
    }
}
