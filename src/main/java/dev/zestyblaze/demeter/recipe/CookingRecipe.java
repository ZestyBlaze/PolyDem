package dev.zestyblaze.demeter.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.registry.DemeterRecipeSerializers;
import dev.zestyblaze.demeter.registry.DemeterRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record CookingRecipe(ItemStackTemplate result,
                            List<Ingredient> ingredients, Optional<Ingredient> dish, int cookTime) implements Recipe<CookingRecipeInput> {
    public static final MapCodec<CookingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
            Ingredient.CODEC.listOf(1, 4).fieldOf("ingredients").forGetter(r -> r.ingredients),
            Ingredient.CODEC.optionalFieldOf("dish").forGetter(r -> r.dish),
            Codec.INT.fieldOf("cook_time").forGetter(r -> r.cookTime)
    ).apply(instance, CookingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CookingRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemStackTemplate.STREAM_CODEC, CookingRecipe::result,
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list(4)), CookingRecipe::ingredients,
            ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC), CookingRecipe::dish,
            ByteBufCodecs.INT, CookingRecipe::cookTime,
            CookingRecipe::new
    );

    @Override
    public boolean matches(CookingRecipeInput input, Level level) {
        if (input.ingredientAmount() != this.ingredients.size()) {
            return false;
        } else {
            return input.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(input.getItem(0))
                    : input.stackedContents().canCraft(this, null);
        }
    }

    @Override
    public ItemStack assemble(CookingRecipeInput input) {
        return result.create();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Cooking";
    }

    @Override
    public RecipeSerializer<? extends Recipe<CookingRecipeInput>> getSerializer() {
        return DemeterRecipeSerializers.OVEN;
    }

    @Override
    public RecipeType<? extends Recipe<CookingRecipeInput>> getType() {
        return DemeterRecipeTypes.OVEN;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.ingredients);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
