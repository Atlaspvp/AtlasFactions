package net.atlaspvp.atlasfactions.Memory;

import com.rabbitmq.client.*;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Configuration;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void pullFPlayers() {
        ConnectionFactory factory = Configuration.factory;
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ

            String QUEUEName = "FPlayerDatabase";

            channel.queueDeclare(QUEUEName, false, false, false, null);

            System.out.println("Waiting for messages...");

            GetResponse response = channel.basicGet(QUEUEName, true);
            if (response != null) {
                Manager.FPlayers = deserializeFPlayers(response.getBody());
                System.out.println("This Worked | FPlayers:");
                for (UUID uuid : Manager.FPlayers.keySet()) {
                    System.out.println(uuid + " " + Bukkit.getOfflinePlayer(uuid).getName() + " " + Manager.FPlayers.get(uuid).getFaction().getName());
                }
            } else {
                System.out.println("No message received");
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void pullFaction() {
        ConnectionFactory factory = Configuration.factory;
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ

            String QUEUEName = "FactionDatabase";

            channel.queueDeclare(QUEUEName, false, false, false, null);

            System.out.println("Waiting for messages...");

            GetResponse response = channel.basicGet(QUEUEName, true);
            if (response != null) {
                Manager.Factions = deserializeFactions(response.getBody());
                System.out.println("This Worked | Factions:");
                for (UUID uuid : Manager.Factions.keySet()) {
                    System.out.println(uuid + " " + Manager.Factions.get(uuid).getName());
                    for (FPlayer player : Manager.Factions.get(uuid).getMembers().keySet()) {
                        System.out.println(player.getUuid() + " " + Bukkit.getOfflinePlayer(player.getUuid()).getName());
                    }
                }
            } else {
                System.out.println("No message received");
            }

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<UUID, Faction> deserializeFactions(byte[] byteArray) {
        HashMap<UUID, Faction> hashMap = new HashMap<>();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            // Read the object from the byte array
            hashMap = (HashMap<UUID, Faction>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

    @SuppressWarnings("unchecked")
    public static HashMap<UUID, FPlayer> deserializeFPlayers(byte[] byteArray) {
        HashMap<UUID, FPlayer> hashMap = new HashMap<>();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            // Read the object from the byte array
            hashMap = (HashMap<UUID, FPlayer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}
