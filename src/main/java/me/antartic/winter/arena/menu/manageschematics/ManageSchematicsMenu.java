package me.antartic.winter.arena.menu.manageschematics;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.arena.ArenaHandler;
import me.antartic.winter.arena.ArenaSchematic;
import me.antartic.winter.arena.menu.manage.ManageMenu;
import me.antartic.winter.util.menu.Button;
import me.antartic.winter.util.menu.Menu;

import me.antartic.winter.util.menu.buttons.BackButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class ManageSchematicsMenu extends Menu {

    public ManageSchematicsMenu() {
        setAutoUpdate(true);
    }

    @Override
    public String getTitle(Player player) {
        return "Manage schematics";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        ArenaHandler arenaHandler = PotPvPRP.getInstance().getArenaHandler();
        Map<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        buttons.put(index++, new BackButton(new ManageMenu()));

        for (ArenaSchematic schematic : arenaHandler.getSchematics()) {
            buttons.put(index++, new ManageSchematicButton(schematic));
        }

        return buttons;
    }

}