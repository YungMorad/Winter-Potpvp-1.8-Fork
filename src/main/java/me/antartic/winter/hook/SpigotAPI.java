package me.antartic.winter.hook;

import lombok.Getter;
import lombok.Setter;
import me.antartic.winter.hook.knockback.AbstractKnockback;
import me.antartic.winter.hook.movementhandler.AbstractMovementHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SpigotAPI {

    public static SpigotAPI INSTANCE;
    public static JavaPlugin PLUGIN;

    @Setter private SpigotType spigotType;
    private AbstractKnockback knockback;
    @Setter private AbstractMovementHandler movementHandler;

    public SpigotAPI init(JavaPlugin plugin) {
        INSTANCE = this;
        PLUGIN = plugin;

        spigotType = SpigotType.get();
        knockback = SpigotType.getKnockback();
        movementHandler = SpigotType.getMovementHandler();

        return this;
    }

}
