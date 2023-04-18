package me.antartic.winter.hologram.impl;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.antartic.winter.PotPvPRP;
import me.antartic.winter.hologram.PracticeHologram;
import me.antartic.winter.kit.kittype.KitType;
import org.bukkit.configuration.Configuration;
import xyz.refinedev.command.util.CC;

import java.util.Map;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/15/2021
 * Project: PotPvPRP
 */

@Getter
@RequiredArgsConstructor
public class KitHologram extends PracticeHologram {

    private final PotPvPRP plugin;
    private final KitType kit;

    /**
     * Spawn the hologram for all players on the server
     * at the given location in the constructor
     */
    public void spawn() {
        Preconditions.checkNotNull(this.meta, "Hologram Meta can not be null!");

        Configuration config = plugin.getConfig();

        Hologram apiHologram = HologramsAPI.createHologram(plugin, meta.getLocation());
        apiHologram.clearLines();
        apiHologram.getVisibilityManager().setVisibleByDefault(true);
        if (!apiHologram.getLocation().getChunk().isLoaded()) {
            apiHologram.getLocation().getChunk().load();
        }

        for ( String line : config.getStringList("SETTINGS.KIT.LINES") ) {
            if (line.contains("<top>")) {
                int position = 1;
                for ( Map.Entry<String, Integer> entry : plugin.getEloHandler().topElo(kit).entrySet()) {

                    apiHologram.appendTextLine(config.getString("SETTINGS.KIT.FORMAT")
                            .replace("<number>", String.valueOf(position))
                            .replace("<value>", String.valueOf(entry.getValue()))
                            .replace("<name>", entry.getKey()));
                    position++;
                }
                continue;
            }

            String replace = line.replace("<kit>", kit.getDisplayName())
                                 .replace("<update>", String.valueOf(updateIn));

            apiHologram.appendTextLine(CC.translate(replace));
        }

        meta.setHologram(apiHologram);
    }

    /**
     * DeSpawn the hologram for all players on the server
     * This method will only deSpawn the hologram but not delete,
     * so after a restart it will be back to its original location
     */
    public void deSpawn() {
        Hologram hologram = meta.getHologram();
        hologram.clearLines();
        hologram.delete();
    }

    /**
     * Update the hologram and its
     * the leaderboard being displayed
     */
    public void update() {
        this.deSpawn();
        this.spawn();
    }
}
