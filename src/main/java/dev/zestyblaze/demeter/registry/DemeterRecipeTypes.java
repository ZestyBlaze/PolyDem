package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

public class DemeterRecipeTypes {
    public static final RecipeType<CookingRecipe> OVEN = new RecipeType<>() {};

    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Demeter.createId("oven"), OVEN);
    }
}
