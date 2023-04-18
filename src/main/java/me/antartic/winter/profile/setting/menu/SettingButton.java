package me.antartic.winter.profile.setting.menu;

import com.google.common.base.Preconditions;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;
import me.antartic.winter.util.menu.Button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Button used by {@link me.antartic.winter.profile.setting.menu.SettingsMenu} to render a {@link Setting}
 */
final class SettingButton extends Button {

    private static final String ENABLED_ARROW = ChatColor.BLUE.toString() + ChatColor.BOLD + "  ► ";
    private static final String DISABLED_SPACER = "    ";

    private final Setting setting;

    SettingButton(Setting setting) {
        this.setting = Preconditions.checkNotNull(setting, "setting");
    }

    @Override
    public String getName(Player player) {
        return setting.getName();
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> description = new ArrayList<>();

        description.add("");
        description.addAll(setting.getDescription());
        description.add("");

        if (PotPvPRP.getInstance().getSettingHandler().getSetting(player, setting)) {
            description.add(ENABLED_ARROW + setting.getEnabledText());
            description.add(DISABLED_SPACER + setting.getDisabledText());
        } else {
            description.add(DISABLED_SPACER + setting.getEnabledText());
            description.add(ENABLED_ARROW + setting.getDisabledText());
        }

        return description;
    }

    @Override
    public Material getMaterial(Player player) {
        return setting.getIcon();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        if (!setting.canUpdate(player)) {
            return;
        }

        SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();

        boolean enabled = !settingHandler.getSetting(player, setting);
        settingHandler.updateSetting(player, setting, enabled);
    }

}