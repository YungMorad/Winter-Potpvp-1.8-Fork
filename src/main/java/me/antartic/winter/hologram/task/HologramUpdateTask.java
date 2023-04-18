package me.antartic.winter.hologram.task;

import lombok.RequiredArgsConstructor;
import me.antartic.winter.PotPvPRP;
import me.antartic.winter.hologram.HologramHandler;
import me.antartic.winter.hologram.PracticeHologram;

/**
 * This Project is property of Refine Development © 2021
 * Redistribution of this Project is not allowed
 *
 * @author Drizzy
 * Created: 9/15/2021
 * Project: PotPvPRP
 */

@RequiredArgsConstructor
public class HologramUpdateTask implements Runnable {

    @Override
    public void run() {
        HologramHandler handler = PotPvPRP.getInstance().getHologramHandler();

        for ( PracticeHologram hologram : handler.getHolograms() ) {
            if (hologram.updateIn <= 0) {
                hologram.update();
                return;
            }

            hologram.updateIn--;
        }
    }
}
