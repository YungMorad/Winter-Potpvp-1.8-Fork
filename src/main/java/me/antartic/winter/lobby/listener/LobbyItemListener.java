package me.antartic.winter.lobby.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.impl.misc.ManageCommand;
import me.antartic.winter.command.impl.silent.UnfollowCommand;
import me.antartic.winter.lobby.LobbyHandler;
import me.antartic.winter.lobby.LobbyItems;
import me.antartic.winter.lobby.menu.SpectateMenu;
import me.antartic.winter.lobby.menu.StatisticsMenu;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.match.MatchState;
import me.antartic.winter.util.ItemListener;
import me.antartic.winter.validation.PotPvPValidation;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class LobbyItemListener extends ItemListener {

    private final Map<UUID, Long> canUseRandomSpecItem = new HashMap<>();

    public LobbyItemListener(LobbyHandler lobbyHandler) {
        addHandler(LobbyItems.MANAGE_ITEM, p -> {
            // even though we don't shouldn't need to do this
            // we do anyway because of the sensitivity of the manage
            // menu.
            if (p.hasPermission("potpvp.admin")) {
               new ManageCommand().manage(p);
            }
        });

        addHandler(LobbyItems.DISABLE_SPEC_MODE_ITEM, player -> {
            if (lobbyHandler.isInLobby(player)) {
                lobbyHandler.setSpectatorMode(player, false);
            }
        });

        addHandler(LobbyItems.ENABLE_SPEC_MODE_ITEM, player -> {
            if (lobbyHandler.isInLobby(player) && PotPvPValidation.canUseSpectateItem(player)) {
                lobbyHandler.setSpectatorMode(player, true);
            }
        });

        addHandler(LobbyItems.SPECTATE_MENU_ITEM, player -> {
            if (PotPvPValidation.canUseSpectateItemIgnoreMatchSpectating(player)) {
                new SpectateMenu().openMenu(player);
            }
        });

        addHandler(LobbyItems.SPECTATE_RANDOM_ITEM, player -> {
            MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();

            if (!PotPvPValidation.canUseSpectateItemIgnoreMatchSpectating(player)) {
                return;
            }

            if (canUseRandomSpecItem.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis()) {
                player.sendMessage(ChatColor.RED + "Please wait before doing this again!");
                return;
            }

            List<Match> matches = new ArrayList<>(matchHandler.getHostedMatches());
            matches.removeIf(m -> m.isSpectator(player.getUniqueId()) || m.getState() == MatchState.ENDING);

            if (matches.isEmpty()) {
                player.sendMessage(ChatColor.RED + "There are no matches available to spectate.");
            } else {
                Match currentlySpectating = matchHandler.getMatchSpectating(player);
                Match newSpectating = matches.get(ThreadLocalRandom.current().nextInt(matches.size()));

                if (currentlySpectating != null) {
                    currentlySpectating.removeSpectator(player, false);
                }

                newSpectating.addSpectator(player, null);
                canUseRandomSpecItem.put(player.getUniqueId(), System.currentTimeMillis() + 3_000L);
            }
        });

        addHandler(LobbyItems.PLAYER_STATISTICS, player -> {
            new StatisticsMenu().openMenu(player);
        });

        addHandler(LobbyItems.UNFOLLOW_ITEM, new UnfollowCommand()::unfollow);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        canUseRandomSpecItem.remove(event.getPlayer().getUniqueId());
    }

}