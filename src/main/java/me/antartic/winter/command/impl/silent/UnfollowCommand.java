package me.antartic.winter.command.impl.silent;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.profile.follow.FollowHandler;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Require;
import xyz.refinedev.command.annotation.Sender;

public class UnfollowCommand implements PotPvPCommand {

    @Command(name = "", desc = "Unfollow the target you are currently following")
    @Require("potpvp.staff.follow")
    public void unfollow(@Sender Player sender) {
        FollowHandler followHandler = PotPvPRP.getInstance().getFollowHandler();
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();

        if (!followHandler.getFollowing(sender).isPresent()) {
            sender.sendMessage(ChatColor.RED + "You're not following anybody.");
            return;
        }

        Match spectating = matchHandler.getMatchSpectating(sender);

        if (spectating != null) {
            spectating.removeSpectator(sender);
        }

        followHandler.stopFollowing(sender);
    }

    @Override
    public String getCommandName() {
        return "unfollow";
    }

    @Override
    public String[] getAliases() {
        return new String[]{};
    }
}