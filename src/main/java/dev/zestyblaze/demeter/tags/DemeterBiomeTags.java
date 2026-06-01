package dev.zestyblaze.demeter.tags;

import dev.zestyblaze.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class DemeterBiomeTags {
    public static final TagKey<Biome> HAS_BAMBOO_SHOOTS = TagKey.create(Registries.BIOME, Demeter.createId("has_bamboo_shoots"));
    public static final TagKey<Biome> HAS_MAPLE_TREES = TagKey.create(Registries.BIOME, Demeter.createId("has_maple_trees"));
}
