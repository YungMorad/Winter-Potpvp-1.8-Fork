package me.antartic.winter.util.scoreboard.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.antartic.winter.util.scoreboard.construct.ScoreGetter;
import me.antartic.winter.util.scoreboard.construct.TitleGetter;

/**
 * Scoreboard Configuration class. This class can be used to
 * create scoreboard objects. This configuration object provides
 * the title/scores, along with some other settings. This should be passed to
 * FrozenScoreboardHandler#setConfiguration.
 */
@NoArgsConstructor
public class ScoreboardConfiguration {

    @Getter @Setter private TitleGetter titleGetter;
    @Getter @Setter private ScoreGetter scoreGetter;

}