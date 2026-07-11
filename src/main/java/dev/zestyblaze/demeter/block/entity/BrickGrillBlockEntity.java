package dev.zestyblaze.demeter.block.entity;

import dev.zestyblaze.demeter.block.BrickGrillBlock;
import dev.zestyblaze.demeter.recipe.CookingRecipe;
import dev.zestyblaze.demeter.recipe.CookingRecipeInput;
import dev.zestyblaze.demeter.registry.DemeterBlockEntities;
import dev.zestyblaze.demeter.registry.DemeterRecipeTypes;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

public class BrickGrillBlockEntity extends BlockEntity implements BlockEntityExtraListener {
    public final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    public ItemStack output = ItemStack.EMPTY;
    public ItemSetter model;

    private int cookingTime;

    private Ingredient requiredDish;

    private final RecipeManager.CachedCheck<CookingRecipeInput, CookingRecipe> quickCheck = RecipeManager.createCheck(DemeterRecipeTypes.OVEN);

    public BrickGrillBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(DemeterBlockEntities.BRICK_GRILL, worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BrickGrillBlockEntity tile) {
        if (state.getValue(BrickGrillBlock.LIT)) {
            tile.cookingTime++;

            if (tile.cookingTime >= 20) {
                CookingRecipeInput inventory = new CookingRecipeInput(tile.items);
                Optional<RecipeHolder<CookingRecipe>> cookingRecipeRecipeHolder = tile.quickCheck.getRecipeFor(inventory, (ServerLevel) level);

                if (cookingRecipeRecipeHolder.isPresent()) {
                    if (cookingRecipeRecipeHolder.get().value().dish().isPresent()) {
                        tile.requiredDish = cookingRecipeRecipeHolder.get().value().dish().get();
                    }

                    tile.setItem(0, cookingRecipeRecipeHolder.get().value().assemble(inventory), true);
                    tile.clearItems();
                }
                tile.cookingTime = 0;
                level.setBlockAndUpdate(pos, state.setValue(BrickGrillBlock.LIT, false));
            }
        }
    }

    public void setItem(int slot, ItemStack item, boolean isResultItem) {
        if (isResultItem) {
            this.output = item;
            if (this.model != null) {
                this.model.setItem(slot, item, true);
            }
        } else {
            this.items.set(slot, item);
            if (this.model != null) {
                this.model.setItem(slot, item, false);
            }
        }
        this.setChanged();
    }

    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    public void clearItems() {
        for (int i = 0; i < this.items.size(); i++) {
            this.setItem(i, ItemStack.EMPTY, false);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        assert level != null;
        super.preRemoveSideEffects(pos, state);
        Containers.dropContents(level, pos, this.items);
        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), this.output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        ContainerHelper.loadAllItems(input, this.items);

        input.read("output", ItemStack.CODEC).ifPresent(output -> this.output = output);

        if (this.model != null) {
            for (int i = 0; i < this.items.size(); i++) {
                this.model.setItem(i, this.items.get(i), false);
            }
            this.model.setItem(0, this.output, true);
        }

        input.read("dish", Ingredient.CODEC).ifPresent(ingredient -> this.requiredDish = ingredient);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        if (this.output != ItemStack.EMPTY) output.store("output", ItemStack.CODEC, this.output);
        if (requiredDish != null) output.store("dish", Ingredient.CODEC, requiredDish);
    }

    @Override
    public void onListenerUpdate(LevelChunk levelChunk) {
        this.model = (ItemSetter) BlockAwareAttachment.get(levelChunk, worldPosition).holder();
        for (int i = 0; i < this.items.size(); i++) {
            this.model.setItem(i, this.items.get(i), false);
        }
        this.model.setItem(0, this.output, true);
    }

    public Ingredient getRequiredDish() {
        return requiredDish;
    }

    public void setRequiredDish(Ingredient requiredDish) {
        this.requiredDish = requiredDish;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    public ItemStack getOutput() {
        return output;
    }

    public interface ItemSetter {
        void setItem(int slot, ItemStack stack, boolean isResultItem);
    }
}
