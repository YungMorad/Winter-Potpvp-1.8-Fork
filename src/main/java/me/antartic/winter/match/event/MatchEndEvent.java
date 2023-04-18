package me.antartic.winter.match.event;

import me.antartic.winter.match.Match;

import org.bukkit.event.HandlerList;

import lombok.Getter;

/**
 * Called when a match is ended (when its {@link me.antartic.winter.match.MatchState} changes
 * to {@link me.antartic.winter.match.MatchState#ENDING})
 * @see me.antartic.winter.match.MatchState#ENDING
 */
public final class MatchEndEvent extends MatchEvent {

    @Getter private static HandlerList handlerList = new HandlerList();

    public MatchEndEvent(Match match) {
        super(match);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}