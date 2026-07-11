package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.world.HerbBlockStateProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class DemeterBlockStateProviders {
    public static final BlockStateProviderType<?> HERBS = new BlockStateProviderType<>(HerbBlockStateProvider.CODEC);

    public static void init() {
        Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, Demeter.createId("herbs"), HERBS);
    }
}
