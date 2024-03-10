package net.atlaspvp.atlasfactions.Objects;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class FPlayer implements Serializable {

    private UUID uuid;
    private Faction faction;
    private int power;
    private boolean isMapOn;

    public FPlayer(Player Player){
        this.uuid = Player.getUniqueId();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isMapOn() {
        return isMapOn;
    }

    public void setMapOn(boolean mapOn) {
        isMapOn = mapOn;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
