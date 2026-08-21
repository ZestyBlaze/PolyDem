package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class DemeterItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public DemeterItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    protected ItemAppender builder(TagKey<Item> tag) {
        return new ItemAppender(super.builder(tag));
    }

    private static ResourceKey<Item> key(ItemLike item) {
        return item.asItem().builtInRegistryHolder().key();
    }

    private static final class ItemAppender extends BlockItemTagAppender<Item> {
        public ItemAppender(BlockItemTagAppender<Item> original) {
            super(original);
        }

        @Override
        protected ResourceKey<Item> convertElement(net.minecraft.references.BlockItemId element) {
            return element.item();
        }

        public ItemAppender add(ItemLike... items) {
            for (ItemLike it : items) {
                add(key(it));
            }
            return this;
        }

        public ItemAppender forceAddTag(TagKey<Item> tag) {
            addOptionalTag(tag);
            return this;
        }
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(ConventionalItemTags.ENCHANTABLES).add(Items.SHEARS);
        builder(ConventionalItemTags.FOODS).add(DemeterItems.MAPLE_SYRUP_BOTTLE);
        builder(ConventionalItemTags.TOOLS).addTag(DemeterItemTags.ANIMAL_BRUSH_TOOL);

        builder(DemeterItemTags.ANIMAL_BRUSH_TOOL).add(DemeterItems.ANIMAL_BRUSH);
        builder(DemeterItemTags.GRILL_STARTERS).add(Items.FLINT_AND_STEEL, Items.FIRE_CHARGE);
        builder(DemeterItemTags.MAPLE_LOGS).add(DemeterItems.MAPLE_LOG, DemeterItems.STRIPPED_MAPLE_LOG);
        builder(DemeterItemTags.QUALITY_PRODUCTS).forceAddTag(ItemTags.WOOL)
                .add(Items.APPLE, Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SLICE, Items.PUMPKIN,
                        Items.BEETROOT, Items.MILK_BUCKET);

        builder(ItemTags.LOGS).addTag(DemeterItemTags.MAPLE_LOGS);
        builder(ItemTags.LOGS_THAT_BURN).addTag(DemeterItemTags.MAPLE_LOGS);
        builder(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(DemeterItems.MAPLE_LOG);
        builder(ItemTags.PLANKS).add(DemeterItems.MAPLE_PLANKS);
    }
}
