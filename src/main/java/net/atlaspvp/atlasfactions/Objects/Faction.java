package net.atlaspvp.atlasfactions.Objects;

import net.atlaspvp.atlasfactions.AtlasFactions;
import net.atlaspvp.atlasfactions.Integration.Relation;
import net.atlaspvp.atlasfactions.Perm.Role;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;
import java.util.*;

public class Faction implements Serializable {
    private String name;
    private UUID uuid;
    private String description;
    boolean status;

    private int power;
    private int maxpower;
    private int maxplayers;
    private int claims;
    private double bankvalue;
    private double value;

    private Inventory chest;
    private HashMap<FPlayer, Role> members;
    private HashMap<Faction, Relation> relations;
    private SerLocation home;
    private List<SerLocation> warps;
    private HashMap<FPlayer, Long> invitedPlayers;
    private List<FPlayer> bannedPlayers;

    public Faction(String Name, Player player) {
        this.uuid = UUID.randomUUID();
        this.name = Name;
        this.members = new HashMap<>();
        this.invitedPlayers = new HashMap<>();
        this.relations = new HashMap<>();

        if (player != null) {
            this.members.put(Manager.getFPlayer(player), Role.OWNER);
        }
    }
// Relations
    public HashMap<Faction, Relation> getRelations() {
        return relations;
    }

    public void setRelations(HashMap<Faction, Relation> relations) {
        this.relations = relations;
    }
// Relations
    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMaxpower() {
        return maxpower;
    }

    public void setMaxpower(int maxpower) {
        this.maxpower = maxpower;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public double getBankvalue() {
        return bankvalue;
    }

    public void setBankvalue(double bankvalue) {
        this.bankvalue = bankvalue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Inventory getChest() {
        return chest;
    }

    public void setChest(Inventory chest) {
        this.chest = chest;
    }

    public HashMap<FPlayer, Role> getMembers() {
        if (members == null) {
            return new HashMap<>();
        }
        return members;
    }

    public void setMembers(HashMap<FPlayer, Role> members) {
        this.members = members;
    }

    public int getClaims() {
        return claims;
    }

    public void setClaims(int amount) {
        this.claims = amount;
    }

    public SerLocation getHome() {
        return home;
    }

    public void setHome(SerLocation home) {
        this.home = home;
    }

    public List<SerLocation> getWarps() {
        return warps;
    }

    public void setWarps(List<SerLocation> warps) {
        this.warps = warps;
    }

    public HashMap<FPlayer, Long> getInvitedplayers() {
        return invitedPlayers;
    }

    public void setInvitedplayers(HashMap<FPlayer, Long> invitedPlayers) {
        this.invitedPlayers = invitedPlayers;
    }

    public List<FPlayer> getBannedplayers() {
        return bannedPlayers;
    }

    public void setBannedplayers(List<FPlayer> bannedplayers) {
        this.bannedPlayers = bannedplayers;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return description;
    }

    public UUID getUUID(){
        return uuid;
    }


    public static boolean isSameFaction(Faction faction1, Faction faction2){
        return Objects.equals(faction1.getName(), faction2.getName());
    }
}
