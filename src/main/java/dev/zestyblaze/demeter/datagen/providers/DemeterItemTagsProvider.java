package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class DemeterItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public DemeterItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(ConventionalItemTags.ENCHANTABLES).add(Items.SHEARS);
        valueLookupBuilder(ConventionalItemTags.FOODS).add(DemeterItems.MAPLE_SYRUP_BOTTLE);
        valueLookupBuilder(ConventionalItemTags.TOOLS).addTag(DemeterItemTags.ANIMAL_BRUSH_TOOL);

        valueLookupBuilder(DemeterItemTags.ANIMAL_BRUSH_TOOL).add(DemeterItems.ANIMAL_BRUSH);
        valueLookupBuilder(DemeterItemTags.MAPLE_LOGS).add(DemeterItems.MAPLE_LOG, DemeterItems.STRIPPED_MAPLE_LOG);
        valueLookupBuilder(DemeterItemTags.QUALITY_PRODUCTS).forceAddTag(ItemTags.WOOL)
                .add(Items.APPLE, Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SLICE, Items.PUMPKIN,
                        Items.BEETROOT, Items.MILK_BUCKET);

        valueLookupBuilder(ItemTags.LOGS).addTag(DemeterItemTags.MAPLE_LOGS);
        valueLookupBuilder(ItemTags.LOGS_THAT_BURN).addTag(DemeterItemTags.MAPLE_LOGS);
        valueLookupBuilder(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(DemeterItems.MAPLE_LOG);
        valueLookupBuilder(ItemTags.PLANKS).add(DemeterItems.MAPLE_PLANKS);
    }
}
