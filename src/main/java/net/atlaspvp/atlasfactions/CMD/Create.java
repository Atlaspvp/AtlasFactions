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
            if (!Manager.Factions.containsKey(name)) {
                Faction faction = new Faction(name, player);
                FPlayer Fplayer = Manager.getFPlayer(player);

                faction.getMembers().put(Fplayer, Role.OWNER);
                Fplayer.setFaction(faction);

                Manager.addFaction(faction);
            }
        }
    }
}
