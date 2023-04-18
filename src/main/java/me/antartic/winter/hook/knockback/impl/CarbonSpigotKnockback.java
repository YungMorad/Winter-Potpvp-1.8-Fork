package me.antartic.winter.hook.knockback.impl;

import me.antartic.winter.hook.knockback.AbstractKnockback;
import org.bukkit.entity.Player;
import xyz.refinedev.spigot.api.knockback.KnockbackAPI;
import xyz.refinedev.spigot.knockback.KnockbackProfile;

public class CarbonSpigotKnockback extends AbstractKnockback {
    @Override
    public void applyKnockback(Player player, String knockback) {
        KnockbackAPI api = KnockbackAPI.getInstance();
        KnockbackProfile profile = api.getProfile(knockback);
        if (profile == null) {
            profile = api.getDefaultProfile();
        }
        api.setPlayerProfile(player, profile);
    }
}