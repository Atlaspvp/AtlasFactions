package net.atlaspvp.atlasfactions.Integration;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import net.atlaspvp.atlasfactions.AtlasFactions;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion implements Relational {

    private final AtlasFactions plugin;

    public PlaceholderAPI(AtlasFactions plugin){
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "atlaspvp";
    }

    @Override
    public String getIdentifier() {
        return "factions";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player one, Player two, String identifier) { // Relational placeholder for Chat Colors
        if(one == null || two == null)
            return null;

        switch(identifier) {
            case "relation":
                return Relation.RelactionOfFactionsName(Manager.getFPlayer(one).getFaction(), Manager.getFPlayer(two).getFaction());
            case "relationcolor":
                return Relation.RelactionOfFactionsLegacyColor(Manager.getFPlayer(one).getFaction(), Manager.getFPlayer(two).getFaction());
        }

        return null;
    }
}
