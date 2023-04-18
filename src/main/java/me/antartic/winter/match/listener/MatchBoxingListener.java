package me.antartic.winter.match.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.*;
import me.antartic.winter.match.event.MatchCountdownStartEvent;
import me.antartic.winter.match.event.MatchStartEvent;
import me.antartic.winter.match.postmatchinv.PostMatchPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.refinedev.spigot.knockback.KnockbackProfile;

import java.util.UUID;

public class MatchBoxingListener implements Listener {


    @EventHandler
    public void onMatchStart(MatchCountdownStartEvent event) {
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
    public void onDamage(EntityDamageEvent event) {
        //Boxing patch
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        if (!matchHandler.isPlayingMatch(player)) {
            return;
        }
        Match match = matchHandler.getMatchPlaying(player);
        if (match.getProtectionUntil() > System.currentTimeMillis()) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        Player damager = (Player)event.getDamager();
        Player player = (Player)event.getEntity();
        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();

        if (!matchHandler.isPlayingMatch(player)) {
            return;
        }
        Match match = matchHandler.getMatchPlaying(damager);
        if (match.getKitType().isBoxing() && (match.getState() != MatchState.ENDING || !match.isSpectator(damager.getUniqueId()))) {
            event.setDamage(0.0);
            if (match.getProtectionUntil() > System.currentTimeMillis()) {
                return;
            }
            match.setProtectionUntil(System.currentTimeMillis() + (match.getKitType().getDamageticks() * 50L / 2));
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
            match.setProtectionUntil(0);
            teamB.getAliveMembers().forEach(u -> Bukkit.getPlayer((UUID)u).setHealth(0.0));
            match.endMatch(MatchEndReason.ENEMIES_ELIMINATED);
        } else if (playerB[0] == 100 * teamB.getAllMembers().size()) {
            match.setProtectionUntil(0);
            teamA.getAliveMembers().forEach(u -> Bukkit.getPlayer((UUID)u).setHealth(0.0));
            match.endMatch(MatchEndReason.ENEMIES_ELIMINATED);
        }
    }
}
