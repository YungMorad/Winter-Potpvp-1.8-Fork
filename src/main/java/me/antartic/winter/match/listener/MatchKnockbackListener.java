package me.antartic.winter.match.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.event.MatchEndEvent;
import me.antartic.winter.match.event.MatchStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MatchKnockbackListener implements Listener {


    @EventHandler (priority = EventPriority.HIGH)
    public void onMatchStart(MatchStartEvent event) {
        Match match = event.getMatch();
        if(match.getKitType().getKnockbackProfile() == null) return;
        match.getTeams().forEach(matchTeam -> {
            matchTeam.getAllMembers().forEach(uuid -> {
                PotPvPRP.getSpigotAPI().getKnockback().applyKnockback(Bukkit.getPlayer(uuid), match.getKitType().getKnockbackProfile());
            });
        });
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onMatchEnd(MatchEndEvent event) {
        Match match = event.getMatch();
        match.getTeams().forEach(matchTeam -> {
            matchTeam.getAllMembers().forEach(uuid -> {
                PotPvPRP.getSpigotAPI().getKnockback().applyKnockback(Bukkit.getPlayer(uuid), "default");
            });
        });
    }
}
