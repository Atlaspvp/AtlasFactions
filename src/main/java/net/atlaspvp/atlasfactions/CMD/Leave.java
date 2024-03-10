package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Perm.Role;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.Objects;

public class Leave {
    @Command({"f leave"})
    @CommandPermission("faction.leave")
    public void leave(Player player) {
        if (player != null) {
            FPlayer fPlayer = Manager.getFPlayer(player);
            if (fPlayer.getFaction() != null && !fPlayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                fPlayer.getFaction().getMembers().remove(fPlayer);
                fPlayer.setFaction(null);
                player.sendMessage("You left the faction");
            } else {
                player.sendMessage("You are not in a faction");
            }
        }
    }
}
