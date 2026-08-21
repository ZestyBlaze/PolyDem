package dev.zestyblaze.demeter.compat.pages;

import dev.zestyblaze.demeter.recipe.CookingRecipe;
import dev.zestyblaze.demeter.registry.DemeterItems;
import eu.pb4.polydex.api.v1.recipe.PageBuilder;
import eu.pb4.polydex.api.v1.recipe.PolydexEntry;
import eu.pb4.polydex.api.v1.recipe.PolydexIngredient;
import eu.pb4.polydex.impl.book.InternalPageTextures;
import eu.pb4.polydex.impl.book.ui.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CookingRecipePage extends PrioritizedRecipePage<CookingRecipe> {
    private final ItemStack ICON = DemeterItems.BRICK_GRILL.getDefaultInstance();
    private final List<PolydexIngredient<?>> ingredients;

    public CookingRecipePage(RecipeHolder<CookingRecipe> recipe) {
        super(recipe);

        List<PolydexIngredient<?>> list = new ArrayList<>();
        recipe.value().ingredients().forEach(ingredient -> list.add(PolydexIngredient.of(ingredient)));

        this.ingredients = list;
    }

    @Override
    public ItemStack typeIcon(ServerPlayer serverPlayer) {
        return ICON;
    }

    @Override
    public @Nullable Component texture(ServerPlayer player) {
        return InternalPageTextures.CRAFTING;
    }

    @Override
    public List<PolydexIngredient<?>> ingredients() {
        return this.ingredients;
    }

    @Override
    public ItemStack getOutput(@Nullable PolydexEntry polydexEntry, MinecraftServer minecraftServer) {
        return this.recipe.result().create();
    }

    @Override
    public ItemStack entryIcon(@Nullable PolydexEntry entry, ServerPlayer player) {
        return recipe.result().create();
    }

    @Override
    public void createPage(@Nullable PolydexEntry polydexEntry, ServerPlayer serverPlayer, PageBuilder pageBuilder) {
        for (int i = 0; i < ingredients.size(); i++) {
            var x = i % 3;
            var y = i / 3;

            pageBuilder.setIngredient(x + 2,  y + 1, ingredients.get(i));
        }

        pageBuilder.set(6, 4, GuiUtils.flame(serverPlayer)
                .setName(Component.translatable("text.polydex.view.cooking_time", Component.literal((recipe.cookTime() / 20d) + "s")
                        .withStyle(ChatFormatting.WHITE)).withStyle(ChatFormatting.GOLD)));
        pageBuilder.setOutput(6, 2, getOutput(polydexEntry, serverPlayer.level().getServer()));
    }
}
