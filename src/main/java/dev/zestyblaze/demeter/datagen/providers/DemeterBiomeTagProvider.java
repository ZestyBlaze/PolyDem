package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.tags.DemeterBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class DemeterBiomeTagProvider extends FabricTagsProvider<Biome> {
    public DemeterBiomeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.BIOME, registryLookupFuture);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider registries) {
        builder(DemeterBiomeTags.HAS_MAPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST);
    }
}
