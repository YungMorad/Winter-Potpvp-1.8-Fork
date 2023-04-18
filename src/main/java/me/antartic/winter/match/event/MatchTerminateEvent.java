package me.antartic.winter.match.event;

import me.antartic.winter.match.Match;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a match is terminated (when its {@link me.antartic.winter.match.MatchState} changes
 * to {@link me.antartic.winter.match.MatchState#TERMINATED})
 * @see me.antartic.winter.match.MatchState#TERMINATED
 */
public final class MatchTerminateEvent extends MatchEvent {

    @Getter private static HandlerList handlerList = new HandlerList();


    public MatchTerminateEvent(Match match) {
        super(match);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}