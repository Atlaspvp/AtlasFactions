package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Objects.SerLocation;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.HashMap;

public class Invite {
    @Command({"f invite"})
    @CommandPermission("faction.invite")
    public void invite(Player player, String name) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                Player invitedPlayer = Bukkit.getPlayer(name);
                if (invitedPlayer != null) {
                    if (invitedPlayer != player) {
                        FPlayer fInvitedPlayer = Manager.getFPlayer(invitedPlayer);
                        if (fInvitedPlayer.getFaction() == null || !fInvitedPlayer.getFaction().equals(fplayer.getFaction())) {
                            if (fplayer.getFaction().getInvitedplayers() == null) {
                                fplayer.getFaction().setInvitedplayers(new HashMap<>());
                            }
                            if (!fplayer.getFaction().getInvitedplayers().containsKey(fInvitedPlayer)) {
                                fplayer.getFaction().getInvitedplayers().put(fInvitedPlayer, System.currentTimeMillis());
                                player.sendMessage("Invited " + invitedPlayer.getName());
                                invitedPlayer.sendMessage("You have been invited to " + fplayer.getFaction().getName());
                            } else {
                                player.sendMessage("Player already invited");
                            }
                        } else {
                            // Message
                            player.sendMessage("Player already in faction");
                        }
                    } else {
                        // Message
                        player.sendMessage("You can't invite yourself");
                    }
                } else {
                    // Message
                    player.sendMessage("Player not found");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }

    @Command({"f deinvite", "f uninvite"})
    @CommandPermission("faction.deinvite")
    public void deinvite(Player player, String name) {
        if (player != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            if (fplayer.getFaction() != null && !fplayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                Player invitedPlayer = Bukkit.getPlayer(name);
                if (invitedPlayer != null) {
                    FPlayer fInvitedPlayer = Manager.getFPlayer(invitedPlayer);
                    if (fplayer.getFaction().getInvitedplayers() != null) {
                        if (fplayer.getFaction().getInvitedplayers().containsKey(fInvitedPlayer)) {
                            fplayer.getFaction().getInvitedplayers().remove(fInvitedPlayer);
                            player.sendMessage("Removed " + invitedPlayer.getName() + "'s invite");
                        } else {
                            // Message
                            player.sendMessage("Player not invited");
                        }
                    }
                } else {
                    // Message
                    player.sendMessage("Player not found");
                }
            } else {
                // Message
                player.sendMessage("No faction");
            }
        }
    }
}
