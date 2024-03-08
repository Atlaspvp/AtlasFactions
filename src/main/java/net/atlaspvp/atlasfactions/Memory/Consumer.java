package net.atlaspvp.atlasfactions.Memory;

import com.rabbitmq.client.*;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Configuration;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
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

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] serializedPerson = delivery.getBody();
                ByteArrayInputStream bis = new ByteArrayInputStream(serializedPerson);
                try (ObjectInputStream in = new ObjectInputStream(bis)) {
                    Manager.FPlayers = (HashMap) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume(QUEUEName, true, deliverCallback, consumerTag -> {});


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

            /*DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("We Reached it :D");
                byte[] serializedPerson = delivery.getBody();
                ByteArrayInputStream bis = new ByteArrayInputStream(serializedPerson);
                try (ObjectInputStream in = new ObjectInputStream(bis)) {
                    Manager.Factions = (HashMap) in.readObject();
                    System.out.println("We Reached it :D 1");
                    for (UUID uuid : Manager.Factions.keySet()) {
                        System.out.println(uuid);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            };*/
            channel.basicConsume(QUEUEName, true, (consumerTag, message) -> {
            Manager.Factions = deserialize(message.getBody());
            System.out.println("This Worked");
                for (UUID uuid : Manager.Factions.keySet()) {
                    System.out.println(uuid);
                }

                }, consumerTag -> {
            });

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<UUID, Faction> deserialize(byte[] byteArray) {
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
}
