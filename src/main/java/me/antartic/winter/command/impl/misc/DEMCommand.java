package me.antartic.winter.command.impl.misc;

import me.antartic.winter.PotPvPRP;
import me.antartic.winter.command.PotPvPCommand;
import me.antartic.winter.profile.setting.Setting;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.refinedev.command.annotation.Command;
import xyz.refinedev.command.annotation.Sender;

/**
 * This Project is property of Refine Development © 2021 - 2022
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 4/14/2022
 * Project: potpvp-reprised
 */

public class DEMCommand implements PotPvPCommand {

    @Command(name = "", desc = "Toggle tournament join messages")
    public void joinMessages(@Sender Player sender) {
        boolean oldValue = PotPvPRP.getInstance().getSettingHandler().getSetting(sender, Setting.SEE_TOURNAMENT_JOIN_MESSAGE);
        if (!oldValue) {
            sender.sendMessage(ChatColor.RED + "You have already disabled tournament join messages.");
            return;
        }

        PotPvPRP.getInstance().getSettingHandler().updateSetting(sender, Setting.SEE_TOURNAMENT_JOIN_MESSAGE, false);
        sender.sendMessage(ChatColor.GREEN + "Disabled tournament join messages.");
    }

    @Override
    public String getCommandName() {
        return "dem";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
