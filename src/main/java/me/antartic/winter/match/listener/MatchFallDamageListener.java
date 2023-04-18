package me.antartic.winter.match.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class MatchFallDamageListener implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();

            if (!matchHandler.isPlayingMatch(player)) {
                return;
            }

            Match match = matchHandler.getMatchPlaying(player);

            if (match.getKitType().isFalldamage()) {
                if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                    event.setCancelled(true);
                }
            }
        }
    }
}
