package me.antartic.winter.match.event;

import me.antartic.winter.match.Match;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a match's countdown ends (when its {@link me.antartic.winter.match.MatchState} changes
 * to {@link me.antartic.winter.match.MatchState#IN_PROGRESS})
 * @see me.antartic.winter.match.MatchState#IN_PROGRESS
 */
public final class MatchStartEvent extends MatchEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public MatchStartEvent(Match match) {
        super(match);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}