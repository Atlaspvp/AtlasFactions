package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Perm.Role;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;

public class Create {


    @Command("f create")
    public void createFaction(final Player player, final String name) {
        if (name.matches("^[a-zA-Z]{3,10}$")) {
            FPlayer fPlayer = Manager.getFPlayer(player);

            if (!fPlayer.getFaction().getName().equalsIgnoreCase("wilderness")) {
                player.sendMessage("You are already in a faction");
                return;
            }

            for (Faction faction : Manager.Factions.values()) {
                if (faction.getName().equalsIgnoreCase(name)) {
                    player.sendMessage("Faction with that name already exists");
                    return;
                }
            }

            Faction faction = new Faction(name, player);
            fPlayer.setFaction(faction);
            faction.getMembers().put(fPlayer, Role.OWNER);

            Manager.addFaction(faction);
            player.sendMessage("Faction UUID: " + faction.getUUID());
        }
    }
}
