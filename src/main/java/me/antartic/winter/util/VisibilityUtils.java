package me.antartic.winter.util;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.profile.follow.FollowHandler;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.party.Party;
import me.antartic.winter.party.PartyHandler;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class VisibilityUtils {

    public void updateVisibilityFlicker(Player target) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            target.hidePlayer(otherPlayer);
            otherPlayer.hidePlayer(target);
        }

        Bukkit.getScheduler().runTaskLater(PotPvPRP.getInstance(), () -> updateVisibility(target), 10L);
    }

    public void updateVisibility(Player target) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (shouldSeePlayer(otherPlayer, target)) {
                otherPlayer.showPlayer(target);
            } else {
                otherPlayer.hidePlayer(target);
            }

            if (shouldSeePlayer(target, otherPlayer)) {
                target.showPlayer(otherPlayer);
            } else {
                target.hidePlayer(otherPlayer);
            }
        }
    }

    private boolean shouldSeePlayer(Player viewer, Player target) {
        SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();
        FollowHandler followHandler = PotPvPRP.getInstance().getFollowHandler();
        PartyHandler partyHandler = PotPvPRP.getInstance().getPartyHandler();
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();

        Match targetMatch = matchHandler.getMatchPlayingOrSpectating(target);

        if (targetMatch == null) {
            // we're not in a match so we hide other players based on their party/match
            Party targetParty = partyHandler.getParty(target);
            Optional<UUID> following = followHandler.getFollowing(viewer);

            boolean viewerPlayingMatch = matchHandler.isPlayingOrSpectatingMatch(viewer);
            boolean viewerSameParty = targetParty != null && targetParty.isMember(viewer.getUniqueId());
            boolean viewerFollowingTarget = following.isPresent() && following.get().equals(target.getUniqueId());

            return viewerPlayingMatch || viewerSameParty || viewerFollowingTarget;
        } else {
            // we're in a match so we only hide other spectators (if our settings say so)
            boolean targetIsSpectator = targetMatch.isSpectator(target.getUniqueId());
            boolean viewerSpecSetting = settingHandler.getSetting(viewer, Setting.VIEW_OTHER_SPECTATORS);
            boolean viewerIsSpectator = matchHandler.isSpectatingMatch(viewer);

            return !targetIsSpectator || (viewerSpecSetting && viewerIsSpectator && !target.hasMetadata("ModMode"));
        }
    }

}