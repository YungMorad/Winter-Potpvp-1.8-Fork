package me.antartic.winter.command.impl.misc;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Require;
import xyz.refinedev.command.annotation.Sender;

public class SetSpawnCommand implements PotPvPCommand {


    @Command(name = "", desc = "Set spawn command")
    @Require("potpvp.setspawn")
    public void setSpawn(@Sender Player sender) {
        PotPvPRP plugin = PotPvPRP.getInstance();
        Location loc = sender.getLocation();


        plugin.getConfig().set("LOBBY.X", loc.getX());
        plugin.getConfig().set("LOBBY.Y", loc.getY());
        plugin.getConfig().set("LOBBY.Z", loc.getZ());
        plugin.getConfig().set("LOBBY.YAW", loc.getYaw());
        plugin.getConfig().set("LOBBY.PITCH", loc.getPitch());
        plugin.saveConfig();

        sender.sendMessage(ChatColor.YELLOW + "Spawn point updated!");
    }

    @Override
    public String getCommandName() {
        return "setspawn";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }
}