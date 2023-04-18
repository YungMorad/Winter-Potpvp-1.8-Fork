package me.antartic.winter.match.rematch.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.event.MatchTerminateEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class RematchGeneralListener implements Listener {

    @EventHandler
    public void onMatchTerminate(MatchTerminateEvent event) {
        PotPvPRP.getInstance().getRematchHandler().registerRematches(event.getMatch());
    }

}