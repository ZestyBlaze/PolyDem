package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.tags.DemeterBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class DemeterBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public DemeterBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(DemeterBlockTags.MAPLE_LOGS).add(DemeterBlocks.MAPLE_LOG, DemeterBlocks.STRIPPED_MAPLE_LOG);
        valueLookupBuilder(BlockTags.LOGS).addTag(DemeterBlockTags.MAPLE_LOGS);
        valueLookupBuilder(ConventionalBlockTags.STRIPPED_LOGS).add(DemeterBlocks.STRIPPED_MAPLE_LOG);
        valueLookupBuilder(BlockTags.LOGS_THAT_BURN).addTag(DemeterBlockTags.MAPLE_LOGS);
        valueLookupBuilder(BlockTags.OVERWORLD_NATURAL_LOGS).add(DemeterBlocks.MAPLE_LOG);
        valueLookupBuilder(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(DemeterBlocks.MAPLE_LOG);
        valueLookupBuilder(BlockTags.PLANKS).add(DemeterBlocks.MAPLE_PLANKS);
        valueLookupBuilder(BlockTags.LEAVES).add(DemeterBlocks.MAPLE_LEAVES);
        valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE).add(DemeterBlocks.MAPLE_LOG, DemeterBlocks.STRIPPED_MAPLE_LOG, DemeterBlocks.MAPLE_PLANKS);
        valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE).add(DemeterBlocks.MAPLE_LEAVES);
    }
}
