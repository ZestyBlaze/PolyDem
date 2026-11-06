package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.recipe.BrewingPotionTransformRecipe;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DemeterRecipeProvider extends FabricRecipeProvider {
    public DemeterRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
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

                ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, DemeterBlocks.BRICK_GRILL)
                        .define('f', Items.SMOKER).define('b', Items.BRICK).define('c', Items.COAL)
                        .pattern(" c ").pattern("bfb").pattern("bbb").unlockedBy("has_item", has(Items.SMOKER))
                        .save(output);

                ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, DemeterItems.CALENDER)
                        .requires(Items.PAPER).requires(Items.WHEAT).requires(Items.LEAF_LITTER)
                        .unlockedBy("has_item", has(Items.PAPER))
                                .save(output);

                RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
                advancementBuilder.unlockedBy("has_item", has(Items.RABBIT_FOOT));

                this.output.accept(
                        ResourceKey.create(Registries.RECIPE, Demeter.createId("brewing/miracle_potion")),
                        new BrewingPotionTransformRecipe(
                                DefaultCustomIngredients.components(Ingredient.of(Items.POTION), DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.REGENERATION)).build()),
                                        Ingredient.of(Items.RABBIT_FOOT),
                                        new ItemStackTemplate(DemeterItems.MIRACLE_POTION)
                        ),
                        null
                );

                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.UNFIRED_PLATE).define('C', Items.CLAY).pattern("CC").unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output);
                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.UNFIRED_MUG).define('C', Items.CLAY).pattern("C").pattern("C").unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output);
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(DemeterItems.UNFIRED_PLATE), RecipeCategory.FOOD, CookingBookCategory.MISC, DemeterItems.PLATE, 0.3F, 200).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output);
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(DemeterItems.UNFIRED_MUG), RecipeCategory.FOOD, CookingBookCategory.MISC, DemeterItems.MUG, 0.3F, 200).unlockedBy("has_furnace", has(Blocks.FURNACE)).save(output);
                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.DEEP_BOWL, 6).define('P', ItemTags.PLANKS).define('S', ItemTags.WOODEN_SLABS).pattern("P P").pattern("SSS").unlockedBy("has_planks", has(ItemTags.PLANKS)).save(output);
                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.GLASS, 6).define('G', ConventionalItemTags.GLASS_BLOCKS_COLORLESS).pattern("G G").pattern("G G").pattern(" G ").unlockedBy("has_glass", has(Blocks.GLASS)).save(output);
                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.JAM_JAR, 6).define('G', ConventionalItemTags.GLASS_BLOCKS_COLORLESS).define('S', ItemTags.WOODEN_SLABS).pattern("S").pattern("G").unlockedBy("has_glass", has(Blocks.GLASS)).save(output);
                ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, DemeterItems.PICKLING_JAR, 4).define('G', ConventionalItemTags.GLASS_BLOCKS_COLORLESS).define('S', ConventionalItemTags.IRON_NUGGETS).pattern("S").pattern("G").unlockedBy("has_glass", has(Blocks.GLASS)).save(output);

                ovenRecipe(DemeterItems.APPLE_PIE, Ingredient.of(DemeterItems.PLATE), 100, Ingredient.of(Items.APPLE), Ingredient.of(items.getOrThrow(ConventionalItemTags.EGGS)), Ingredient.of(DemeterItems.BUTTER), Ingredient.of(DemeterItems.FLOUR));
                ovenRecipe(Items.BAKED_POTATO, Ingredient.of(Items.POTATO));
                ovenRecipe(Items.BREAD, Ingredient.of(DemeterItems.FLOUR));
                ovenRecipe(DemeterItems.BUTTER_BISCUITS, Ingredient.of(DemeterItems.PLATE), 100, Ingredient.of(items.getOrThrow(ConventionalItemTags.EGGS)), Ingredient.of(DemeterItems.BUTTER), Ingredient.of(DemeterItems.FLOUR));
                ovenRecipe(Items.CAKE, Ingredient.of(Items.MILK_BUCKET), Ingredient.of(items.getOrThrow(ConventionalItemTags.EGGS)), Ingredient.of(Items.SUGAR), Ingredient.of(DemeterItems.FLOUR));
                ovenRecipe(DemeterItems.CHOCOLATE_CAKE, Ingredient.of(DemeterItems.PLATE),100, Ingredient.of(items.getOrThrow(ConventionalItemTags.EGGS)), Ingredient.of(DemeterItems.FLOUR), Ingredient.of(DemeterItems.BUTTER), Ingredient.of(DemeterItems.CHOCOLATE));
                ovenRecipe(Items.COOKED_BEEF, Ingredient.of(Items.BEEF));
                ovenRecipe(Items.COOKED_CHICKEN, Ingredient.of(Items.CHICKEN));
                ovenRecipe(Items.COOKED_COD, Ingredient.of(Items.COD));
                ovenRecipe(Items.COOKED_MUTTON, Ingredient.of(Items.MUTTON));
                ovenRecipe(Items.COOKED_PORKCHOP, Ingredient.of(Items.PORKCHOP));
                ovenRecipe(Items.COOKED_RABBIT, Ingredient.of(Items.RABBIT));
                ovenRecipe(Items.COOKED_SALMON, Ingredient.of(Items.SALMON));
                ovenRecipe(Items.COOKIE, 4, Ingredient.of(DemeterItems.FLOUR), Ingredient.of(DemeterItems.CHOCOLATE));
                ovenRecipe(Items.DRIED_KELP, Ingredient.of(Items.KELP));
                ovenRecipe(Items.POPPED_CHORUS_FRUIT, Ingredient.of(Items.CHORUS_FRUIT));
            }

            public void ovenRecipe(ItemLike output, Ingredient... ingredients) {
                oven(output, 1, null, 100, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public void ovenRecipe(ItemLike output, Ingredient dish, int cookTime, Ingredient... ingredients) {
                oven(output, 1, dish, cookTime, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public void ovenRecipe(ItemLike output, int quantity, Ingredient dish, int cookTime, Ingredient... ingredients) {
                oven(output, quantity, dish, cookTime, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public void ovenRecipe(ItemLike output, int quantity, Ingredient... ingredients) {
                oven(output, quantity, null, 100, ingredients).save(this.output, createCookingString(output.asItem().getDescriptionId().split("\\.")[2]));
            }

            public static SpecialRecipeBuilder oven(ItemLike result, int quantity, Ingredient dish, int cookTime, Ingredient... ingredients) {
                return new SpecialRecipeBuilder(() -> new CookingRecipe(new ItemStackTemplate(result.asItem(), quantity),
                        List.of(ingredients),
                        dish == null ? Optional.empty() : Optional.of(dish),
                        cookTime)
                );
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
