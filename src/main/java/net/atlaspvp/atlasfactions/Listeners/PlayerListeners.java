package net.atlaspvp.atlasfactions.Listeners;

import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        FPlayer fplayer = new FPlayer(player);

        Manager.FPlayers.put(player.getUniqueId(), fplayer);
        if (fplayer.getFaction() == null) {

        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Manager.FPlayers.remove(player);
    }

}
