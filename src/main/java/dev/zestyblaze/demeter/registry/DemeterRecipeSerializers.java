package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.recipe.BrewingPotionTransformRecipe;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.BrewingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DemeterRecipeSerializers {
    public static final RecipeSerializer<CookingRecipe> OVEN = new RecipeSerializer<>(CookingRecipe.CODEC, CookingRecipe.STREAM_CODEC);
    public static final RecipeSerializer<BrewingRecipe> POTION_TRANSFORM = new RecipeSerializer<>(BrewingPotionTransformRecipe.CONTAINER_MAP_CODEC, BrewingPotionTransformRecipe.CONTAINER_STREAM_CODEC);

    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Demeter.createId("oven"), OVEN);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Demeter.createId("potion_transform"), POTION_TRANSFORM);
    }
}
