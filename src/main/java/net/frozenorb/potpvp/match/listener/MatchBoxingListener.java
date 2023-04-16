package net.frozenorb.potpvp.match.listener;

import net.frozenorb.potpvp.PotPvPRP;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchEndReason;
import net.frozenorb.potpvp.match.MatchState;
import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.potpvp.match.event.MatchStartEvent;
import net.frozenorb.potpvp.match.postmatchinv.PostMatchPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class MatchBoxingListener implements Listener {


    @EventHandler
    public void onMatchStart(MatchStartEvent event) {
        Match match = event.getMatch();
        if (!match.getKitType().isBoxing()) return;
        for(MatchTeam matchTeam : match.getTeams()) {
            for(UUID uuid : matchTeam.getAliveMembers()) {
                Player player = Bukkit.getPlayer(uuid);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200000, 1), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200000, 1), true);
            }
        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        Player damager = (Player)event.getDamager();
        Player player = (Player)event.getEntity();
        Match match = PotPvPRP.getInstance().getMatchHandler().getMatchPlaying(player);

        if (match == null || match.getTeam(damager.getUniqueId()) == null) {
            return;
        }
        if (match.getKitType().isBoxing() && (match.getState() != MatchState.ENDING || !match.isSpectator(damager.getUniqueId()))) {
            event.setDamage(0.0);
            match.getBoxingHits().put(damager.getUniqueId(), match.getBoxingHits().getOrDefault(damager.getUniqueId(), 0) + 1);
            checkBoxingHits(match);
        }
    }
    public void checkBoxingHits(Match match) {
        MatchTeam teamA = match.getTeams().get(0);
        MatchTeam teamB = match.getTeams().get(1);
        int[] playerA = new int[]{0};
        teamA.getAllMembers().forEach(u -> {
            playerA[0] = match.boxingHits.getOrDefault(u, 0);
        });
        int[] playerB = new int[]{0};
        teamB.getAllMembers().forEach(u -> {
            playerB[0] = match.boxingHits.getOrDefault(u, 0);
        });
        if (playerA[0] == 100 * teamA.getAllMembers().size()) {
            teamB.getAliveMembers().forEach(u -> Bukkit.getPlayer((UUID)u).setHealth(0.0));
            match.endMatch(MatchEndReason.ENEMIES_ELIMINATED);
        } else if (playerB[0] == 100 * teamB.getAllMembers().size()) {
            teamA.getAliveMembers().forEach(u -> Bukkit.getPlayer((UUID)u).setHealth(0.0));
            match.endMatch(MatchEndReason.ENEMIES_ELIMINATED);
        }
    }
}
