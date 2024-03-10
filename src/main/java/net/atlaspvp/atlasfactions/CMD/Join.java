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

public class Join {
    Long TWELVE_HOURS = 43200000L;

    @Command({"f join"})
    @CommandPermission("faction.join")
    public void join(Player player, String name) {
        if (player != null) {
            FPlayer fPlayer = Manager.getFPlayer(player);
            if (fPlayer.getFaction() == null || fPlayer.getFaction().getName().equalsIgnoreCase("Wilderness")) {
                for (Faction faction : Manager.Factions.values()) {
                    if (faction.getName().equalsIgnoreCase(name)) {
                        if (faction.getInvitedplayers().containsKey(fPlayer)) {
                            if (System.currentTimeMillis() - faction.getInvitedplayers().get(fPlayer) < TWELVE_HOURS) {
                                faction.getMembers().put(fPlayer, Role.RECRUIT);
                                faction.getInvitedplayers().remove(fPlayer);
                                fPlayer.setFaction(faction);
                                player.sendMessage("Joined faction " + faction.getName());
                            } else {
                                player.sendMessage("Invitation expired");
                                faction.getInvitedplayers().remove(fPlayer);
                            }
                        } else {
                            player.sendMessage("You are not invited to that faction");
                        }
                        return;
                    }
                }

                for (FPlayer searchedFPlayer : Manager.FPlayers.values()) {
                    if (Objects.requireNonNull(Bukkit.getOfflinePlayer(searchedFPlayer.getUuid()).getName()).equalsIgnoreCase(name)) {
                        if (searchedFPlayer.getFaction() != null) {
                            Faction faction = searchedFPlayer.getFaction();
                            if (faction.getInvitedplayers().containsKey(fPlayer)) {
                                if (System.currentTimeMillis() - faction.getInvitedplayers().get(fPlayer) < TWELVE_HOURS) {
                                    faction.getMembers().put(fPlayer, Role.RECRUIT);
                                    faction.getInvitedplayers().remove(fPlayer);
                                    fPlayer.setFaction(faction);
                                    player.sendMessage("Joined faction " + faction.getName());
                                } else {
                                    player.sendMessage("Invitation expired");
                                    faction.getInvitedplayers().remove(fPlayer);
                                }
                            } else {
                                player.sendMessage("You are not invited to that faction");
                            }
                            return;
                        }
                    }
                }
                player.sendMessage("Faction not found");
            } else {
                player.sendMessage("You are already in a faction");
            }
        }
    }
}
