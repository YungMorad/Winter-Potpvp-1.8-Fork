package net.frozenorb.potpvp.kit.kittype.menu.manage;

import com.google.common.collect.ImmutableList;

import net.frozenorb.potpvp.arena.menu.manage.ManageMenu;
import net.frozenorb.potpvp.command.impl.misc.ManageCommand;
import net.frozenorb.potpvp.kit.kittype.KitType;
import net.frozenorb.potpvp.util.menu.BooleanTraitButton;
import net.frozenorb.potpvp.util.menu.Button;
import net.frozenorb.potpvp.util.menu.Menu;

import net.frozenorb.potpvp.util.menu.buttons.BackButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mazen Kotb
 */
public class ManageKitTypeMenu extends Menu {

    private final KitType type;

    public ManageKitTypeMenu(KitType type) {
        setNoncancellingInventory(true);
        //de base false
        setUpdateAfterClick(true);

        this.type = type;
    }

    @Override
    public String getTitle(Player player) {
        return "Manage " + type.getDisplayName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        // Vertical row
        buttons.put(getSlot(0, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(1, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(2, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(3, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(5, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(6, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(7, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));
        buttons.put(getSlot(8, 0), Button.placeholder(Material.STAINED_GLASS_PANE, (byte)15));


        buttons.put(getSlot(0, 1), new BooleanTraitButton<>(type, "Hidden", KitType::setHidden, KitType::isHidden, KitType::saveAsync));
        buttons.put(getSlot(1, 1), new BooleanTraitButton<>(type, "Editable", KitType::setEditorSpawnAllowed, KitType::isEditorSpawnAllowed, KitType::saveAsync));
        buttons.put(getSlot(2, 1), new BooleanTraitButton<>(type, "Display Health", KitType::setHealthShown, KitType::isHealthShown, KitType::saveAsync));
        buttons.put(getSlot(3, 1), new BooleanTraitButton<>(type, "Allow Building", KitType::setBuildingAllowed, KitType::isBuildingAllowed, KitType::saveAsync));
        buttons.put(getSlot(4, 1), new BooleanTraitButton<>(type, "Hardcore Healing", KitType::setHardcoreHealing, KitType::isHardcoreHealing, KitType::saveAsync));
        buttons.put(getSlot(5, 1), new BooleanTraitButton<>(type, "Pearl Damage", KitType::setPearlDamage, KitType::isPearlDamage, KitType::saveAsync));
        buttons.put(getSlot(6, 1), new BooleanTraitButton<>(type, "Supports Ranked", KitType::setSupportsRanked, KitType::isSupportsRanked, KitType::saveAsync));
        buttons.put(getSlot(7, 1), new BooleanTraitButton<>(type, "No Fall Damage", KitType::setFallDamage, KitType::isFallDamage, KitType::saveAsync));
        buttons.put(getSlot(8, 1), new BooleanTraitButton<>(type, "Boxing", KitType::setBoxing, KitType::isBoxing, KitType::saveAsync));
        buttons.put(getSlot(0, 2), new BooleanTraitButton<>(type, "Points System", KitType::setPointsSystem, KitType::isPointsSystem, KitType::saveAsync));
        buttons.put(getSlot(1, 2), new BooleanTraitButton<>(type, "Sumo", KitType::setSumo, KitType::isSumo, KitType::saveAsync));





        //buttons.put(getSlot(3, 0), new SaveKitTypeButton(type));
        buttons.put(getSlot(4, 0), new BackButton(new ManageMenu()));

        return buttons;
    }

    private Button nonCancellingItem(ItemStack stack) {
        return new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return stack;
            }

            @Override
            public String getName(Player player) {
                return stack.getItemMeta().getDisplayName();
            }

            @Override
            public List<String> getDescription(Player player) {
                return stack.getItemMeta().getLore();
            }

            @Override
            public Material getMaterial(Player player) {
                return stack.getType();
            }

            @Override
            public boolean shouldCancel(Player player, int slot, ClickType clickType) {
                return false;
            }
        };
    }
}
