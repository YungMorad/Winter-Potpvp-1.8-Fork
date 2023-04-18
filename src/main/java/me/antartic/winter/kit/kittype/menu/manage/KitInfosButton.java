package me.antartic.winter.kit.kittype.menu.manage;

import com.google.common.base.Preconditions;
import me.antartic.winter.PotPvPRP;
import me.antartic.winter.arena.Arena;
import me.antartic.winter.arena.ArenaHandler;
import me.antartic.winter.arena.ArenaSchematic;
import me.antartic.winter.kit.kittype.KitType;
import me.antartic.winter.util.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitInfosButton extends Button {

    private final KitType kitType;

    KitInfosButton(KitType kitType) {
        this.kitType = Preconditions.checkNotNull(kitType, "kittype");
    }

    @Override
    public String getName(Player player) {
        return ChatColor.AQUA + kitType.getDisplayName() + " Infos";
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = new ArrayList<>();

        description.add("");
        description.add(ChatColor.GREEN + "Knockback: " + ChatColor.WHITE + kitType.getKnockbackProfile());
        description.add(ChatColor.GREEN + "Damage Ticks: " + ChatColor.WHITE + kitType.getDamageticks());

        return description;

    }

    @Override
    public int getAmount(Player player) {
        return 1;
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.NAME_TAG;
    }

}
