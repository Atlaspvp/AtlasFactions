package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class Desc {

    @Command({"f desc", "f description"})
    @CommandPermission("faction.desc")
    public void createDescription(Player player, String content){
        if (player != null && content != null) {
            FPlayer fplayer = Manager.getFPlayer(player);
            Faction faction = fplayer.getFaction();

            faction.setDescription(content);
        }
    }
}
