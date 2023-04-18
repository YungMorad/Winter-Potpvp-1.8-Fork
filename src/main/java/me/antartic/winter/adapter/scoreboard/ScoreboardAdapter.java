package me.antartic.winter.adapter.scoreboard;


import me.antartic.winter.util.scoreboard.config.ScoreboardConfiguration;
import me.antartic.winter.util.scoreboard.construct.TitleGetter;
import org.apache.commons.lang.StringEscapeUtils;

public class ScoreboardAdapter extends ScoreboardConfiguration {

    public ScoreboardAdapter() {
        this.setTitleGetter(
                new TitleGetter("&c&lAntartic &7" + StringEscapeUtils.unescapeJava("\u2758") +" &fPractice"));
        this.setScoreGetter(
                new MultiplexingScoreGetter(
                        new MatchScoreGetter(),
                        new LobbyScoreGetter()
                ));
    }

}
