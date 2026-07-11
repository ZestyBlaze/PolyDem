package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DemeterRecipeSerializers {
    public static final RecipeSerializer<CookingRecipe> OVEN = new RecipeSerializer<>(CookingRecipe.CODEC, CookingRecipe.STREAM_CODEC);

    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Demeter.createId("oven"), OVEN);
    }
}
