package me.antartic.winter.command.impl.settings;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.profile.setting.Setting;
import me.antartic.winter.profile.setting.SettingHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Sender;

/**
 * /toggleduels command, allows players to toggle {@link Setting#RECEIVE_DUELS} setting
 */
public class ToggleDuelCommand implements PotPvPCommand {

    @Command(name = "", desc = "Toggle duels for your profile")
    public void toggleDuel(@Sender Player sender) {
        if (!Setting.RECEIVE_DUELS.canUpdate(sender)) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return;
        }

        SettingHandler settingHandler = PotPvPRP.getInstance().getSettingHandler();
        boolean enabled = !settingHandler.getSetting(sender, Setting.RECEIVE_DUELS);

        settingHandler.updateSetting(sender, Setting.RECEIVE_DUELS, enabled);

        if (enabled) {
            sender.sendMessage(ChatColor.GREEN + "Toggled duel requests on.");
        } else {
            sender.sendMessage(ChatColor.RED + "Toggled duel requests off.");
        }
    }

    @Override
    public String getCommandName() {
        return "toggleduels";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"td", "tduels"};
    }
}