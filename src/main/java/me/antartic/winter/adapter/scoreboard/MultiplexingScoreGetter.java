package me.antartic.winter.adapter.scoreboard;

import java.util.List;
import java.util.function.BiConsumer;

import me.antartic.winter.util.scoreboard.construct.ScoreGetter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;
import xyz.refinedev.command.util.CC;;

final class MultiplexingScoreGetter implements ScoreGetter {

    private final BiConsumer<Player, List<String>> matchScoreGetter;
    private final BiConsumer<Player, List<String>> lobbyScoreGetter;

    MultiplexingScoreGetter(
        BiConsumer<Player, List<String>> matchScoreGetter,
        BiConsumer<Player, List<String>> lobbyScoreGetter
    ) {
        this.matchScoreGetter = matchScoreGetter;
        this.lobbyScoreGetter = lobbyScoreGetter;
    }

    @Override
    public void getScores(List<String> scores, Player player) {
        if (PotPvPRP.getInstance() == null) return;
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();

        if (settingHandler.getSetting(player, Setting.SHOW_SCOREBOARD)) {
             scores.add(CC.SB_BAR);
            if (matchHandler.isPlayingOrSpectatingMatch(player)) {
                matchScoreGetter.accept(player, scores);
            } else {
                lobbyScoreGetter.accept(player, scores);
            }
            scores.add("");
            scores.add("&7antartic.com");
            if (player.hasMetadata("ModMode")) {
                scores.add(ChatColor.GRAY.toString() + ChatColor.BOLD + "In Silent Mode");
            }
            scores.add(CC.SB_BAR);
        }
    }

}