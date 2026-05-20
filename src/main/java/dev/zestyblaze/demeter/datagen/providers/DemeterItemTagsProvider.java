package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.DemeterItemTags;
import dev.zestyblaze.demeter.registry.DemeterItems;
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
        valueLookupBuilder(ConventionalItemTags.TOOLS).addTag(DemeterItemTags.ANIMAL_BRUSH_TOOL);
        valueLookupBuilder(ConventionalItemTags.ENCHANTABLES).add(Items.SHEARS);
        valueLookupBuilder(DemeterItemTags.ANIMAL_BRUSH_TOOL).add(DemeterItems.ANIMAL_BRUSH);
        valueLookupBuilder(DemeterItemTags.QUALITY_PRODUCTS).forceAddTag(ItemTags.WOOL)
                .add(Items.APPLE, Items.WHEAT, Items.CARROT, Items.POTATO, Items.MELON_SLICE, Items.PUMPKIN,
                        Items.BEETROOT, Items.MILK_BUCKET);
    }
}
