package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.concurrent.CompletableFuture;

public class DemeterWorldGenProvider extends FabricDynamicRegistryProvider {
    public static final ResourceKey<Feature> BAMBOO_SHOOTS = createFeatureKey("bamboo_shoots");
    public static final ResourceKey<PlacedFeature> BAMBOO_SHOOTS_PF = createPlacedKey("bamboo_shoots");

    public static final ResourceKey<Feature> HERB_PATCH = createFeatureKey("herb_patch");
    public static final ResourceKey<PlacedFeature> HERB_PATCH_PF = createPlacedKey("herb_patch");

    public static final ResourceKey<Feature> MAPLE_TREES = createFeatureKey("maple_trees");
    public static final ResourceKey<PlacedFeature> MAPLE_TREES_PF = createPlacedKey("maple_trees");

    public DemeterWorldGenProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void bootstrapFeatures(BootstrapContext<Feature> context) {
        HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
        Holder<BlockStateProvider> belowTrunkProvider = blockStateProviders.getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE);

        context.register(BAMBOO_SHOOTS, new SimpleBlockFeature(BlockStateProvider.of(DemeterBlocks.BAMBOO_SHOOTS)));
        BlockStateProvider provider = new WeightedStateProvider(WeightedList.<BlockState>builder()
                .add(DemeterBlocks.MINT.defaultBlockState())
                .add(DemeterBlocks.CHAMOMILE.defaultBlockState())
                .add(DemeterBlocks.LAVENDER.defaultBlockState())
        );
        context.register(HERB_PATCH, new SimpleBlockFeature(provider));
        context.register(MAPLE_TREES, createMaple(belowTrunkProvider).build());
    }

    public static void bootstrapPlacements(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);

        PlacementUtils.register(context, BAMBOO_SHOOTS_PF, configuredFeatures.getOrThrow(BAMBOO_SHOOTS),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(5),
                OffsetPlacement.ofTriangle(5, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        PlacementUtils.register(context, HERB_PATCH_PF, configuredFeatures.getOrThrow(HERB_PATCH),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(5),
                OffsetPlacement.ofTriangle(5, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );

        PlacementUtils.register(context, MAPLE_TREES_PF, configuredFeatures.getOrThrow(MAPLE_TREES),
                PlacementUtils.countExtra(0, 0.05F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        );
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return "Demeter Worldgen";
    }

    private static TreeFeature.Builder createStraightBlobTree(Block logBlock, Block leavesBlock, int baseHeight, int heightRandA, int heightRandB, int radius, Holder<BlockStateProvider> belowTrunkProvider) {
        return new TreeFeature.Builder(
                BlockStateProvider.of(logBlock),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB),
                BlockStateProvider.of(leavesBlock),
                new BlobFoliagePlacer(ConstantInt.of(radius),
                        ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                belowTrunkProvider
        );
    }

    private static TreeFeature.Builder createMaple(Holder<BlockStateProvider> belowTrunkProvider) {
        return createStraightBlobTree(DemeterBlocks.MAPLE_LOG,
                DemeterBlocks.MAPLE_LEAVES, 5, 1, 0, 2, belowTrunkProvider)
                .ignoreVines();
    }

    private static ResourceKey<Feature> createFeatureKey(String id) {
        return ResourceKey.create(Registries.FEATURE, Demeter.createId(id));
    }

    private static ResourceKey<PlacedFeature> createPlacedKey(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Demeter.createId(id));
    }
}
