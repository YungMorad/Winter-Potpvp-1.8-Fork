package me.antartic.winter.command.impl.stats;

import java.util.UUID;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.util.menu.menus.ConfirmMenu;
import me.antartic.winter.util.MongoUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Objects;

import me.antartic.winter.PotPvPRP;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Require;
import xyz.refinedev.command.annotation.Sender;

public class StatsResetCommands implements PotPvPCommand {

    @Command(name = "", usage = "<target>",  desc = "Reset a target's stats")
    @Require("potpvp.statsreset")
    public void reset(@Sender Player sender, Player target) {
        Bukkit.getScheduler().runTask(PotPvPRP.getInstance(), () -> {
            new ConfirmMenu("Stats reset", (reset) -> {
                if (reset) {
                    Bukkit.getScheduler().runTaskAsynchronously(PotPvPRP.getInstance(), () -> {
                        PotPvPRP.getInstance().getEloHandler().resetElo(target.getUniqueId());
                        sender.sendMessage(ChatColor.GREEN + "Reset the target's stats!");
                    });
                } else {
                    sender.sendMessage(ChatColor.RED + "Stats reset aborted.");
                }
            }).openMenu(sender);
        });
    }

    @Override
    public String getCommandName() {
        return "statsreset";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
