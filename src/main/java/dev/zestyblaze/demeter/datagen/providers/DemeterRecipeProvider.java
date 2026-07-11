package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.recipe.CookingRecipe;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Optional;
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

                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, DemeterItems.DEMETERS_GUIDE)
                                .requires(Items.BOOK).requires(ConventionalItemTags.CROPS)
                                .unlockedBy("has_item", has(ConventionalItemTags.CROPS))
                                        .save(output);

                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, DemeterItems.MILK_BOTTLE, 3)
                                .requires(Items.MILK_BUCKET).requires(Items.GLASS_BOTTLE, 3)
                                .unlockedBy("has_item", has(Items.MILK_BUCKET))
                                        .save(output);

                planksFromLog(DemeterItems.MAPLE_PLANKS, DemeterItemTags.MAPLE_LOGS, 4);

                ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, DemeterItems.ANIMAL_BRUSH)
                        .define('W', Items.WHEAT).define('S', Items.STICK)
                        .pattern(" W").pattern("S ")
                        .unlockedBy("has_item", has(Items.WHEAT))
                        .save(output);

                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, DemeterItems.ANIMAL_TAG)
                        .requires(Items.PAPER).requires(Items.INK_SAC)
                        .unlockedBy("has_item", has(Items.PAPER))
                        .save(output);


                ShapedRecipeBuilder.shaped(items, RecipeCategory.BREWING, DemeterItems.MIRACLE_POTION)
                        .define('r', new ComponentsIngredient(Ingredient.of(Items.POTION),
                                DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS,
                                                new PotionContents(Potions.LONG_REGENERATION))
                                        .build()).toVanilla())
                        .define('w', Items.WHEAT).define('c', Items.GOLDEN_CARROT)
                        .define('g', Items.GOLDEN_APPLE).define('d', Items.DIAMOND)
                        .pattern(" w ")
                        .pattern("grc")
                        .pattern(" d ")
                        .unlockedBy("has_item", has(Items.GOLDEN_APPLE))
                        .save(output);

                ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, DemeterBlocks.BRICK_GRILL)
                        .define('f', Items.SMOKER).define('b', Items.BRICK).define('c', Items.COAL)
                        .pattern(" c ").pattern("bfb").pattern("bbb").unlockedBy("has_item", has(Items.SMOKER))
                        .save(output);

                ovenRecipe(Items.BAKED_POTATO, Ingredient.of(Items.POTATO));
                ovenRecipe(Items.BREAD, Ingredient.of(items.getOrThrow(ConventionalItemTags.DOUGH_FOODS)));
                ovenRecipe(Items.CAKE, Ingredient.of(Items.MILK_BUCKET), Ingredient.of(items.getOrThrow(ConventionalItemTags.EGGS)), Ingredient.of(Items.SUGAR), Ingredient.of(DemeterItems.FLOUR));
                ovenRecipe(Items.COOKED_BEEF, Ingredient.of(Items.BEEF));
                ovenRecipe(Items.COOKED_CHICKEN, Ingredient.of(Items.CHICKEN));
                ovenRecipe(Items.COOKED_COD, Ingredient.of(Items.COD));
                ovenRecipe(Items.COOKED_MUTTON, Ingredient.of(Items.MUTTON));
                ovenRecipe(Items.COOKED_PORKCHOP, Ingredient.of(Items.PORKCHOP));
                ovenRecipe(Items.COOKED_RABBIT, Ingredient.of(Items.RABBIT));
                ovenRecipe(Items.COOKED_SALMON, Ingredient.of(Items.SALMON));
                ovenRecipe(Items.COOKIE, 4, Optional.empty(), 100, Ingredient.of(DemeterItems.FLOUR), Ingredient.of(DemeterItems.CHOCOLATE));
                ovenRecipe(Items.POPPED_CHORUS_FRUIT, Ingredient.of(Items.CHORUS_FRUIT));
            }

            public void ovenRecipe(ItemLike output, Ingredient... ingredients) {
                oven(output, 1, Optional.empty(), 100, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public void ovenRecipe(ItemLike output, Optional<Ingredient> dish, int cookTime, Ingredient... ingredients) {
                oven(output, 1, dish, cookTime, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public void ovenRecipe(ItemLike output, int quantity, Optional<Ingredient> dish, int cookTime, Ingredient... ingredients) {
                oven(output, quantity, dish, cookTime, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public static SpecialRecipeBuilder oven(ItemLike result, int quantity, Optional<Ingredient> dish, int cookTime, Ingredient... ingredients) {
                return new SpecialRecipeBuilder(() -> new CookingRecipe(new ItemStackTemplate(result.asItem(), quantity), List.of(ingredients), dish, cookTime));
            }

            private String createCookingString(String id) {
                return "oven/" + id;
            }
        };
    }

    @Override
    public String getName() {
        return "Demeter Recipes";
    }
}
