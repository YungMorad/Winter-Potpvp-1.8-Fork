package me.antartic.winter.match.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.party.event.PartyMemberJoinEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * When a player joins a party, attempt to have them spectate their
 * party's active match, if there is one.
 * https://github.com/FrozenOrb/PotPvP-SI/issues/32
 */
public final class MatchPartySpectateListener implements Listener {

    @EventHandler
    public void onPartyMemberJoin(PartyMemberJoinEvent event) {
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        Match leaderMatch = matchHandler.getMatchPlayingOrSpectating(Bukkit.getPlayer(event.getParty().getLeader()));

        if (leaderMatch != null) {
            leaderMatch.addSpectator(event.getMember(), null);
        }
    }

}