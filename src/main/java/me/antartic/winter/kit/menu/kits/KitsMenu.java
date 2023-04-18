package me.antartic.winter.kit.menu.kits;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.kit.Kit;
import me.antartic.winter.kit.KitHandler;
import me.antartic.winter.kit.kittype.KitType;
import me.antartic.winter.kit.kittype.menu.select.SelectKitTypeMenu;
import me.antartic.winter.util.InventoryUtils;
import me.antartic.winter.util.menu.Button;
import me.antartic.winter.util.menu.Menu;

import me.antartic.winter.util.menu.buttons.BackButton;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class KitsMenu extends Menu {

    private final KitType kitType;

    public KitsMenu(KitType kitType) {
        setPlaceholder(true);
        setAutoUpdate(true);

        this.kitType = kitType;
    }

    @Override
    public void onClose(Player player) {
        InventoryUtils.resetInventoryDelayed(player);
    }

    @Override
    public String getTitle(Player player) {
        return ChatColor.RED + "Viewing " + kitType.getDisplayName() + " kits";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        KitHandler kitHandler = PotPvPRP.getInstance().getKitHandler();
        Map<Integer, Button> buttons = new HashMap<>();

        // kit slots are 1-indexed
        for (int kitSlot = 1; kitSlot <= KitHandler.KITS_PER_TYPE; kitSlot++) {
            Optional<Kit> kitOpt = kitHandler.getKit(player, kitType, kitSlot);
            int column = kitSlot + 1; // + 1 to compensate for this being 0-indexed

            if (kitOpt.isPresent()) {
                buttons.put(getSlot(column, 1), new KitEditButton(kitOpt, kitType, kitSlot));
            } else {
                buttons.put(getSlot(column, 1), new KitIconButton(kitOpt, kitType, kitSlot));
            }
        }

        buttons.put(getSlot(8, 2), new BackButton(new SelectKitTypeMenu(kitType -> new KitsMenu(kitType).openMenu(player), "Select a kit type...")));

        return buttons;
    }


    @Override
    public int size(Player player) {
        return 9 * 3;
    }

}