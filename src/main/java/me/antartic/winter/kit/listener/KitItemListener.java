package me.antartic.winter.kit.listener;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.kit.KitItems;
import me.antartic.winter.kit.menu.kits.KitsMenu;
import me.antartic.winter.kit.kittype.menu.select.SelectKitTypeMenu;
import me.antartic.winter.lobby.LobbyHandler;
import me.antartic.winter.util.ItemListener;

public final class KitItemListener extends ItemListener {

    public KitItemListener() {
        addHandler(KitItems.OPEN_EDITOR_ITEM, player -> {
            LobbyHandler lobbyHandler = PotPvPRP.getInstance().getLobbyHandler();

            if (lobbyHandler.isInLobby(player)) {
                new SelectKitTypeMenu(kitType -> {
                    new KitsMenu(kitType).openMenu(player);
                }, "Select a kit to edit...").openMenu(player);
            }
        });
    }

}