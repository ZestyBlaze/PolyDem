package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.registry.DemeterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public class DemeterModelsProvider extends FabricModelProvider {
    public DemeterModelsProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        logWithHorizontal(blockModelGenerators, DemeterBlocks.MAPLE_LOG);
        logWithHorizontal(blockModelGenerators, DemeterBlocks.STRIPPED_MAPLE_LOG);
        leavesModel(blockModelGenerators, DemeterBlocks.MAPLE_LEAVES);
        cubeModel(blockModelGenerators, DemeterBlocks.MAPLE_PLANKS);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(DemeterItems.ANIMAL_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.ANIMAL_TAG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.MAPLE_SYRUP_BOTTLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.MILK_BOTTLE, ModelTemplates.FLAT_ITEM);
    }

    public void cubeModel(BlockModelGenerators blockModelGenerators, Block block) {
        Identifier model = TexturedModel.CUBE.create(block, blockModelGenerators.modelOutput);
        blockModelGenerators.plainModel(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    public void leavesModel(BlockModelGenerators blockModelGenerators, Block block) {
        Identifier model = TexturedModel.LEAVES.create(block, blockModelGenerators.modelOutput);
        blockModelGenerators.plainModel(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    public void logWithHorizontal(BlockModelGenerators blockModelGenerators, Block block) {
        TextureMapping logMapping = TextureMapping.logColumn(block);
        Identifier model = ModelTemplates.CUBE_COLUMN.create(block, logMapping, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }
}
