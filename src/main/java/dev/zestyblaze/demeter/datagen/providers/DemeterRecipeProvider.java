package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class DemeterRecipeProvider extends FabricRecipeProvider {
    public DemeterRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);

                ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, DemeterItems.ANIMAL_BRUSH)
                        .define('W', Items.WHEAT).define('S', Items.STICK)
                        .pattern(" W").pattern("S ")
                        .unlockedBy("has_item", has(Items.WHEAT))
                        .save(output);

                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, DemeterItems.ANIMAL_TAG)
                        .requires(Items.PAPER).requires(Items.INK_SAC)
                        .unlockedBy("has_item", has(Items.PAPER))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Demeter Recipes";
    }
}
