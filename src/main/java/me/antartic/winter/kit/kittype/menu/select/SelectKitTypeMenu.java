package me.antartic.winter.kit.kittype.menu.select;

import com.google.common.base.Preconditions;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.kit.kittype.KitType;
import me.antartic.winter.util.Callback;
import me.antartic.winter.party.Party;
import me.antartic.winter.util.InventoryUtils;
import me.antartic.winter.util.menu.Button;
import me.antartic.winter.util.menu.Menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public final class SelectKitTypeMenu extends Menu {

    private final boolean reset;
    private final String title;
    private final Callback<KitType> callback;

    public SelectKitTypeMenu(Callback<KitType> callback, String title) {
        this(callback, true, title);
        setPlaceholder(true);
    }

    public SelectKitTypeMenu(Callback<KitType> callback, boolean reset, String title) {
        this.callback = Preconditions.checkNotNull(callback, "callback");
        this.reset = reset;
        this.title = title;
    }
    

    @Override
    public void onClose(Player player) {
        if (reset) {
            InventoryUtils.resetInventoryDelayed(player);
        }
    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.BLUE.toString() + ChatColor.BOLD + title;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        for (KitType kitType : KitType.getAllTypes()) {
            if (!player.isOp() && kitType.isHidden()) {
                continue;
            }

            buttons.put(index++, new KitTypeButton(kitType, callback));
        }

        Party party = PotPvPRP.getInstance().getPartyHandler().getParty(player);
        if (party != null) {
            buttons.put(8, new KitTypeButton(KitType.teamFight, callback));
        }

        return buttons;
    }

}