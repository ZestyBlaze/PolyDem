package dev.zestyblaze.demeter.recipe;

import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class CookingRecipeInput implements RecipeInput {
    private final StackedItemContents stackedContents = new StackedItemContents();
    private final int ingredientAmount;
    private final List<ItemStack> items;

    public CookingRecipeInput(List<ItemStack> items) {
        this.items = items.stream().filter(stack -> !stack.isEmpty()).toList();
        int ingredientAmount = 0;

        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                ingredientAmount++;
                this.stackedContents.accountStack(stack, 1);
            }
        }

        this.ingredientAmount = ingredientAmount;
    }

    public StackedItemContents stackedContents() {
        return stackedContents;
    }

    public int ingredientAmount() {
        return ingredientAmount;
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.ingredientAmount() == 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof CookingRecipeInput input && this.ingredientAmount == input.ingredientAmount
                    && ItemStack.listMatches(this.items, input.items);
        }
    }
}
