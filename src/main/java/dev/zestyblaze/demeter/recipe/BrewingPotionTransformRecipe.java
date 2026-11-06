package dev.zestyblaze.demeter.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.registry.DemeterRecipeSerializers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

import java.util.Optional;

public class BrewingPotionTransformRecipe extends BrewingRecipe {
    public static final MapCodec<BrewingRecipe> CONTAINER_MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Ingredient.CODEC.fieldOf("input").forGetter(o -> o.input.ingredient()),
                            Ingredient.CODEC.fieldOf("reagent").forGetter(o -> o.reagent.ingredient()),
                            ItemStackTemplate.CODEC.fieldOf("output").forGetter(o -> o.output)
                    )
                    .apply(i, BrewingPotionTransformRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, BrewingRecipe> CONTAINER_STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.input.ingredient(),
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.reagent.ingredient(),
            ItemStackTemplate.STREAM_CODEC,
            o -> o.output,
            BrewingPotionTransformRecipe::new
    );

    public BrewingPotionTransformRecipe(Ingredient input, Ingredient reagent, ItemStackTemplate output) {
        super(
                new PotionIngredient(input, Optional.empty()),
                new PotionIngredient(reagent, Optional.empty()),
                output
        );
    }

    @Override
    public boolean matches(BrewingInput brew) {
        return this.input.ingredient().test(brew.input()) && this.reagent.test(brew.reagent());
    }

    @Override
    public ItemStack assemble(BrewingInput brew) {
        return this.output.create();
    }

    @Override
    public RecipeSerializer<BrewingRecipe> getSerializer() {
        return DemeterRecipeSerializers.POTION_TRANSFORM;
    }
}
