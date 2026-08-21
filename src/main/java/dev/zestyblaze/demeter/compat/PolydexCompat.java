package dev.zestyblaze.demeter.compat;

import dev.zestyblaze.demeter.Demeter;
import net.fabricmc.loader.api.FabricLoader;

public class PolydexCompat {
    public static final boolean IS_PRESENT = FabricLoader.getInstance().isModLoaded("polydex");

    public static void init() {
        if (IS_PRESENT) {
            PolydexCompatImpl.init();
        } else {
            Demeter.LOGGER.warn("[Demeter] Polydex is not installed! It's recommended to install it!");
        }
    }
}
