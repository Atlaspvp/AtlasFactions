package net.atlaspvp.atlasfactions.Memory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import net.atlaspvp.atlasfactions.Objects.FPlayer;
import net.atlaspvp.atlasfactions.Objects.Faction;
import net.atlaspvp.atlasfactions.Struct.Manager;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.UUID;

import static net.atlaspvp.atlasfactions.Struct.Configuration.factory;

public class Sender {

    public static void sendFPlayer() {
        HashMap<UUID, FPlayer> saving = Manager.FPlayers;
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ

            String QUEUEName = "FPlayerDatabase";
            channel.queueDeclare(QUEUEName, false, false, false, null); // Create a new Queue with the name of the Container Identifier

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(saving);
            out.close();

            // Send the serialized PlayerData object to RabbitMQ
            channel.basicPublish("", QUEUEName, null, bos.toByteArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void sendFaction() {
        HashMap<UUID, Faction> saving = Manager.Factions;
        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel(); // Create a new channel in rabbitMQ


            String QUEUEName = "FactionDatabase";
            channel.queueDeclare(QUEUEName, false, false, false, null); // Create a new Queue with the name of the Container Identifier

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(saving); // Serialize the PlayerData
            out.close();
            byte[] serializedPlayerData = bos.toByteArray(); // Byte array (Serialized) PlayerData

            // Send the serialized PlayerData object to RabbitMQ
            channel.basicPublish("", QUEUEName, null, serializedPlayerData);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
