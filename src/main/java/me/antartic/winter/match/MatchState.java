package me.antartic.winter.match;

import me.antartic.winter.match.event.MatchStartEvent;

/**
 * Represents a possible state of a {@link Match}
 */
public enum MatchState {

    /**
     * The match is currently in countdown and will soon transition to
     * {@link MatchState#IN_PROGRESS}
     * @see Match#startCountdown()
     * @see me.antartic.winter.match.event.MatchCountdownStartEvent
     */
    COUNTDOWN,

    /**
     * The match is currently in progress and will transition to
     * {@link MatchState#ENDING} once all players have died (or time
     * has expired)
     * @see Match#checkEnded()
     * @see me.antartic.winter.match.listener.MatchDurationLimitListener
     * @see MatchStartEvent
     */
    IN_PROGRESS,

    /**
     * The match is currently ending (giving players a moment to realize the
     * match has ended and react) and will soon transition to
     * {@link MatchState#TERMINATED}.
     * @see me.antartic.winter.match.event.MatchEndEvent
     */
    ENDING,

    /**
     * The match is completely ended, and all players have been teleported back
     * to the lobby. The match has been removed from {@link MatchHandler#getHostedMatches()}
     * and will soon be garbage collected
     * @see me.antartic.winter.match.event.MatchTerminateEvent
     */
    TERMINATED

}