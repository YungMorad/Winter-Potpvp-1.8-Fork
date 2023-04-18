package me.antartic.winter.command.impl.misc;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.match.postmatchinv.PostMatchInvHandler;
import me.antartic.winter.match.postmatchinv.PostMatchPlayer;
import me.antartic.winter.match.postmatchinv.menu.PostMatchMenu;

import me.antartic.winter.util.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Sender;

import java.util.Map;
import java.util.UUID;

public class CheckPostMatchInvCommand implements PotPvPCommand {

    @Command(name = "" , usage = "<target>", desc = "View inventory menu")
    public void checkPostMatchInv(@Sender Player sender, UUID target) {
        PostMatchInvHandler postMatchInvHandler = PotPvPRP.getInstance().getPostMatchInvHandler();
        Map<UUID, PostMatchPlayer> players = postMatchInvHandler.getPostMatchData(sender.getUniqueId());
        PostMatchPlayer inv = players.get(target);

        if (inv == null) {
            String name = PotPvPRP.getInstance().getUuidCache().name(target);
            sender.sendMessage(ChatColor.RED + "Data for " + name + " not found.");
        }

        Menu menu = new PostMatchMenu(players.get(target));
        menu.openMenu(sender);

    }

    @Override
    public String getCommandName() {
        return "checkPostMatchInv";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"_"};
    }
}