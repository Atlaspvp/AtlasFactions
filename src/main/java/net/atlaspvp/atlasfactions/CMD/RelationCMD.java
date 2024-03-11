package net.atlaspvp.atlasfactions.CMD;

import net.atlaspvp.atlasfactions.Integration.Relation;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;

import java.util.HashMap;

public class RelationCMD {
    HashMap<Faction, Request> requests = new HashMap<>();

    @Command("f relation") // Will use bukkit aliases to convert /f relation <relation> <target> to /f <relation> <target>
    public void fRelation(final Player player, final String relation, String target) {
        FPlayer fplayer = Manager.getFPlayer(player);
        Faction requesting = fplayer.getFaction();
        Faction beingrequested = Manager.getFactionByName(target);
        System.out.println("going to switch case " + relation + "  " + target);


        switch (relation) {
            case "enemy" :
                System.out.println("you trying to enemy hoe");
                requesting.getRelations().remove(beingrequested);
                beingrequested.getRelations().remove(requesting);

                requesting.getRelations().put(beingrequested, Relation.ENEMY);
                beingrequested.getRelations().put(requesting, Relation.ENEMY);

                for (Faction related : requesting.getRelations().keySet()) {
                    System.out.println(related.getName() + ", " + requesting.getRelations().get(related).getName());
                }

                for (Faction related : beingrequested.getRelations().keySet()) {
                    System.out.println(related.getName() + ", " + beingrequested.getRelations().get(related).getName());
                }
                return;

            case "neutral" :
                System.out.println("you trying to neutral hoe");

                for (Faction related : requesting.getRelations().keySet()) {
                    System.out.println(related.getName() + ", " + requesting.getRelations().get(related).getName());
                }

                for (Faction related : beingrequested.getRelations().keySet()) {
                    System.out.println(related.getName() + ", " + beingrequested.getRelations().get(related).getName());
                }

                requesting.getRelations().remove(beingrequested);
                beingrequested.getRelations().remove(requesting);
                return;
        }
    }

    private class Request {
        private Faction requesting;
        private Faction beingrequested;
        private Relation relation;

        public Request(Faction requesting, Faction beingrequested, Relation relation) {
            this.requesting = requesting;
            this.beingrequested = beingrequested;
            this.relation = relation;
        }

        public Faction getRequesting() {
            return requesting;
        }

        public Faction getBeingRequested() {
            return beingrequested;
        }

        public Relation getRelation() {
            return relation;
        }
    }

}
