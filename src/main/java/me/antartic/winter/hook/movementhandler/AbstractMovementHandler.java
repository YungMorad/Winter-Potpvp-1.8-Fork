package me.antartic.winter.hook.movementhandler;

import org.apache.logging.log4j.util.TriConsumer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AbstractMovementHandler {

    public abstract void injectLocationUpdate(TriConsumer<Player, Location, Location> data);

    public abstract void injectRotationUpdate(TriConsumer<Player, Location, Location> data);

}