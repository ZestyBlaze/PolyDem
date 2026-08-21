package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.tags.DemeterBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class DemeterBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public DemeterBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    protected BlockAppender builder(TagKey<Block> tag) {
        return new BlockAppender(super.builder(tag));
    }

    private static ResourceKey<Block> key(Block block) {
        return block.builtInRegistryHolder().key();
    }

    private static final class BlockAppender extends BlockItemTagAppender<Block> {
        BlockAppender(BlockItemTagAppender<Block> original) {
            super(original);
        }

        @Override
        protected ResourceKey<Block> convertElement(net.minecraft.references.BlockItemId element) {
            return element.block();
        }

        public BlockAppender add(Block... blocks) {
            for (Block it : blocks) {
                add(key(it));
            }
            return this;
        }

        public BlockAppender addOptional(Block block) {
            addOptional(key(block));
            return this;
        }

        public BlockAppender forceAddTag(TagKey<Block> tag) {
            addOptionalTag(tag);
            return this;
        }
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(DemeterBlockTags.MAPLE_LOGS).add(DemeterBlocks.MAPLE_LOG, DemeterBlocks.STRIPPED_MAPLE_LOG);
        builder(BlockTags.LOGS).addTag(DemeterBlockTags.MAPLE_LOGS);
        builder(ConventionalBlockTags.STRIPPED_LOGS).add(DemeterBlocks.STRIPPED_MAPLE_LOG);
        builder(BlockTags.OVERWORLD_NATURAL_LOGS).add(DemeterBlocks.MAPLE_LOG);
        builder(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(DemeterBlocks.MAPLE_LOG);
        builder(BlockTags.PLANKS).add(DemeterBlocks.MAPLE_PLANKS);
        builder(BlockTags.LEAVES).add(DemeterBlocks.MAPLE_LEAVES);
        builder(BlockTags.MINEABLE_WITH_AXE).add(DemeterBlocks.MAPLE_LOG, DemeterBlocks.STRIPPED_MAPLE_LOG, DemeterBlocks.MAPLE_PLANKS);
        builder(BlockTags.MINEABLE_WITH_HOE).add(DemeterBlocks.MAPLE_LEAVES);
        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(DemeterBlocks.BRICK_GRILL);
        builder(BlockTags.NEEDS_STONE_TOOL).add(DemeterBlocks.BRICK_GRILL);
    }
}
