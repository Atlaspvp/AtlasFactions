package net.atlaspvp.atlasfactions.Integration;

import net.atlaspvp.atlasfactions.Objects.Faction;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.HashMap;

public enum Relation {
    FACTION(NamedTextColor.GREEN, "&a"),
    ALLY(NamedTextColor.LIGHT_PURPLE, "&d"),
    TRUCE(NamedTextColor.AQUA, "&b"),
    NEUTRAL(NamedTextColor.WHITE, "&f"),
    ENEMY(NamedTextColor.RED, "&c");

    private final NamedTextColor color;
    private final String legacyCode;

    Relation(NamedTextColor color, String legacyCode) {
        this.color = color;
        this.legacyCode = legacyCode;
    }

    // Commons
    public String getName() {
        return name();
    }
    public NamedTextColor getColor() {
        return color;
    }
    public String getLegacyCode() {
        return legacyCode;
    }
    // Commons

    // Compare relations methods
    public static Relation RelactionOfFactions(Faction fac1, Faction fac2){
        HashMap<Faction, Relation> fac1Relations = fac1.getRelations();
        if (fac1Relations.containsKey(fac2)) {
            Relation relationBetween = fac1Relations.get(fac2);
            return relationBetween;
        } else {
            return NEUTRAL;
        }
    }

    public static String RelactionOfFactionsName(Faction fac1, Faction fac2){
        HashMap<Faction, Relation> fac1Relations = fac1.getRelations();
        if (fac1 == fac2) {return FACTION.name();}
        if (fac1Relations.containsKey(fac2)) {
            Relation relationBetween = fac1Relations.get(fac2);
            return relationBetween.name();
        } else {
            return NEUTRAL.name();
        }
    }

    public static String RelactionOfFactionsLegacyColor(Faction fac1, Faction fac2){
        HashMap<Faction, Relation> fac1Relations = fac1.getRelations();
        if (fac1 == fac2) {return FACTION.getLegacyCode();}
        if (fac1Relations.containsKey(fac2)) {
            Relation relationBetween = fac1Relations.get(fac2);
            return relationBetween.getLegacyCode();
        } else {
            return NEUTRAL.getLegacyCode();
        }
    }

    public static NamedTextColor RelactionOfFactionsColor(Faction fac1, Faction fac2){
        HashMap<Faction, Relation> fac1Relations = fac1.getRelations();
        if (fac1 == fac2) {return FACTION.getColor();}
        if (fac1Relations.containsKey(fac2)) {
            Relation relationBetween = fac1Relations.get(fac2);
            return relationBetween.getColor();
        } else {
            return NEUTRAL.getColor();
        }
    }
    // Compare relations methods
}
