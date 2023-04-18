package me.antartic.winter.match.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.match.Match;
import me.antartic.winter.match.MatchHandler;
import me.antartic.winter.match.MatchTeam;
import me.antartic.winter.match.MatchUtils;
import me.antartic.winter.match.SpectatorItems;
import me.antartic.winter.command.impl.match.LeaveCommand;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;
import me.antartic.winter.util.FancyPlayerInventory;
import me.antartic.winter.util.ItemListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class SpectatorItemListener extends ItemListener {

    private final Map<UUID, Long> toggleVisiblityUsable = new ConcurrentHashMap<>();

    public SpectatorItemListener(MatchHandler matchHandler) {
        setPreProcessPredicate(matchHandler::isSpectatingMatch);

        Consumer<Player> toggleSpectatorsConsumer = player -> {
            SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();
            UUID playerUuid = player.getUniqueId();
            boolean togglePermitted = toggleVisiblityUsable.getOrDefault(playerUuid, 0L) < System.currentTimeMillis();

            if (!togglePermitted) {
                player.sendMessage(ChatColor.RED + "Please wait before doing this again!");
                return;
            }

            boolean enabled = !settingHandler.getSetting(player, Setting.VIEW_OTHER_SPECTATORS);
            settingHandler.updateSetting(player, Setting.VIEW_OTHER_SPECTATORS, enabled);

            if (enabled) {
                player.sendMessage(ChatColor.GREEN + "Now showing other spectators.");
            } else {
                player.sendMessage(ChatColor.RED + "Now hiding other spectators.");
            }

            MatchUtils.resetInventory(player);
            toggleVisiblityUsable.put(playerUuid, System.currentTimeMillis() + 3_000L);
        };

        addHandler(SpectatorItems.RETURN_TO_LOBBY_ITEM, new LeaveCommand()::leave);
        addHandler(SpectatorItems.LEAVE_PARTY_ITEM, new LeaveCommand()::leave);
        addHandler(SpectatorItems.SHOW_SPECTATORS_ITEM, toggleSpectatorsConsumer);
        addHandler(SpectatorItems.HIDE_SPECTATORS_ITEM, toggleSpectatorsConsumer);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }

        MatchHandler matchHandler = PotPvPRP.getInstance().getMatchHandler();
        Match clickerMatch = matchHandler.getMatchSpectating(event.getPlayer());
        Player clicker = event.getPlayer();

        if (clickerMatch == null || !clicker.getItemInHand().isSimilar(SpectatorItems.VIEW_INVENTORY_ITEM)) {
            return;
        }

        Player clicked = (Player) event.getRightClicked();
        MatchTeam clickedTeam = clickerMatch.getTeam(clicked.getUniqueId());

        // should only happen when clicking other spectators
        if (clickedTeam == null) {
            clicker.sendMessage(ChatColor.RED + "Cannot view inventory of " + clicked.getName());
            return;
        }

        boolean bypassPerm = clicker.hasPermission("potpvp.inventory.all");
        boolean sameTeam = clickedTeam.getAllMembers().contains(clicker.getUniqueId());

        if (bypassPerm || sameTeam) {
            clicker.sendMessage(ChatColor.AQUA + "Opening inventory of: " + clicked.getName());
            FancyPlayerInventory.open(clicked, clicker); // show a fancy inventory with armor and stuff!
        } else {
            clicker.sendMessage(ChatColor.RED + clicked.getName() + " is not on your team.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        toggleVisiblityUsable.remove(event.getPlayer().getUniqueId());
    }

}