package me.antartic.winter.match.event;

import me.antartic.winter.match.Match;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a match's countdown starts (when its {@link me.antartic.winter.match.MatchState} changes
 * to {@link me.antartic.winter.match.MatchState#COUNTDOWN})
 * @see me.antartic.winter.match.MatchState#COUNTDOWN
 */
public final class MatchCountdownStartEvent extends MatchEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public MatchCountdownStartEvent(Match match) {
        super(match);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}