package me.antartic.winter.party.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.impl.PartyCommands;
import me.antartic.winter.party.Party;
import me.antartic.winter.party.PartyHandler;
import me.antartic.winter.party.PartyItems;
import me.antartic.winter.party.menu.RosterMenu;
import me.antartic.winter.party.menu.otherparties.OtherPartiesMenu;
import me.antartic.winter.util.ItemListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PartyItemListener extends ItemListener {

    public PartyItemListener(PartyHandler partyHandler) {
        addHandler(PartyItems.LEAVE_PARTY_ITEM, new PartyCommands()::leave);
        addHandler(PartyItems.START_TEAM_SPLIT_ITEM, new PartyCommands()::teamSplit);
        addHandler(PartyItems.START_FFA_ITEM, new PartyCommands()::partyFFA);
        addHandler(PartyItems.OTHER_PARTIES_ITEM, p -> new OtherPartiesMenu().openMenu(p));
        addHandler(PartyItems.ASSIGN_CLASSES, p -> new RosterMenu(partyHandler.getParty(p)).openMenu(p));
    }

    // this item changes based on who your party leader is,
    // so we have to manually implement this one.
    @EventHandler
    public void fastPartyIcon(PlayerInteractEvent event) {
        if (!event.hasItem() || !event.getAction().name().contains("RIGHT_")) {
            return;
        }

        if (event.getItem().getType() != PartyItems.ICON_TYPE) {
            return;
        }

        boolean permitted = canUseButton.getOrDefault(event.getPlayer().getUniqueId(), 0L) < System.currentTimeMillis();

        if (permitted) {
            Player player = event.getPlayer();
            Party party = PotPvPRP.getInstance().getPartyHandler().getParty(player);

            if (party != null && PartyItems.icon(party).isSimilar(event.getItem())) {
                event.setCancelled(true);
                new PartyCommands().partyInfo(player, player);
            }

            canUseButton.put(player.getUniqueId(), System.currentTimeMillis() + 500);
        }
    }

}