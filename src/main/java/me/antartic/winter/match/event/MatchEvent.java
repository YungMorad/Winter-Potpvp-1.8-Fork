package me.antartic.winter.match.event;

import com.google.common.base.Preconditions;

import me.antartic.winter.match.Match;

import org.bukkit.event.Event;

import lombok.Getter;

/**
 * Represents an event involving a {@link Match}
 */
abstract class MatchEvent extends Event {

    /**
     * The match involved in this event
     */
    @Getter private final Match match;

    MatchEvent(Match match) {
        this.match = Preconditions.checkNotNull(match, "match");
    }

}