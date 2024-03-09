package net.atlaspvp.atlasfactions.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;

public class SerLocation implements Serializable {
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String name;
    private String chunkCoords;

    public SerLocation(Location location, String name) {
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.name = name;
        int chunkX = location.getChunk().getX() << 4;
        int chunkZ = location.getChunk().getZ() << 4;
        this.chunkCoords =  chunkX + ";" + chunkZ;
    }

    public static Location locationToLocation(SerLocation location) {
        return new Location(Bukkit.getWorld(location.getWorldName()), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChunkCoords() {
        return chunkCoords;
    }

    public void setChunkCoords(String chunkCoords) {
        this.chunkCoords = chunkCoords;
    }
}
