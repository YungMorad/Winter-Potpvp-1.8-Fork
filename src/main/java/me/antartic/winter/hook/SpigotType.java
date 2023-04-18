package me.antartic.winter.hook;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.antartic.winter.hook.knockback.AbstractKnockback;
import me.antartic.winter.hook.knockback.impl.CarbonSpigotKnockback;
import me.antartic.winter.hook.movementhandler.AbstractMovementHandler;
import me.antartic.winter.hook.movementhandler.impl.CarbonSpigotMovementHandler;

import java.util.Arrays;

@AllArgsConstructor
public enum SpigotType {

    CARBON_SPIGOT("xyz.refinedev.spigot.config.SettingsConfig", CarbonSpigotKnockback.class, CarbonSpigotMovementHandler.class),
    ;

    private final String package_;
    public final Class<?> knockback;
    public final Class<?> movementHandler;

    public String getPackage() {
        return package_;
    }

    /**
     * Detect which spigot is being used and initialize
     * @author Drizzy
     */
    public static SpigotType get() {
        return Arrays
                .stream(SpigotType.values())
                .filter(type -> !type.equals(SpigotType.CARBON_SPIGOT) && check(type.getPackage()))
                .findFirst()
                .orElse(SpigotType.CARBON_SPIGOT);
    }

    @SneakyThrows
    public static AbstractKnockback getKnockback() {
        return (AbstractKnockback) get().knockback.newInstance();
    }

    @SneakyThrows
    public static AbstractMovementHandler getMovementHandler() {
        return (AbstractMovementHandler) get().movementHandler.newInstance();
    }

    public static boolean check(String string) {
        try {
            Class.forName(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
