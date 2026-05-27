package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class DemeterBlockLootProvider extends FabricBlockLootSubProvider {
    public DemeterBlockLootProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelf(DemeterBlocks.MAPLE_LOG);
        dropSelf(DemeterBlocks.STRIPPED_MAPLE_LOG);
        dropSelf(DemeterBlocks.MAPLE_PLANKS);
        add(DemeterBlocks.MAPLE_LEAVES, createLeavesDrops(DemeterBlocks.MAPLE_LEAVES, Blocks.DIAMOND_BLOCK, NORMAL_LEAVES_SAPLING_CHANCES));
    }
}
