package me.antartic.winter.command.impl.match;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.arena.Arena;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Sender;

public class MapCommand implements PotPvPCommand {

    @Command(name = "", desc = "")
    public void map(@Sender Player sender) {
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        Match match = matchHandler.getMatchPlayingOrSpectating(sender);

        if (match == null) {
            sender.sendMessage(ChatColor.RED + "You are not in a match.");
            return;
        }

        Arena arena = match.getArena();
        sender.sendMessage(ChatColor.YELLOW + "Playing on copy " + ChatColor.GOLD + arena.getCopy() + ChatColor.YELLOW + " of " + ChatColor.GOLD + arena.getSchematic() + ChatColor.YELLOW + ".");
    }

    @Override
    public String getCommandName() {
        return "map";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }
}