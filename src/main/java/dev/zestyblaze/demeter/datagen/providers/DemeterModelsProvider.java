package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.registry.DemeterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
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
        createCrossBlock(blockModelGenerators, DemeterBlocks.BAMBOO_SHOOTS, DemeterItems.BAMBOO_SHOOTS, BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlock(blockModelGenerators, DemeterBlocks.MINT, DemeterItems.MINT, BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlock(blockModelGenerators, DemeterBlocks.CHAMOMILE, DemeterItems.CHAMOMILE, BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlock(blockModelGenerators, DemeterBlocks.LAVENDER, DemeterItems.LAVENDER, BlockModelGenerators.PlantType.NOT_TINTED);
        grillModel(blockModelGenerators, DemeterBlocks.BRICK_GRILL);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(DemeterItems.ANIMAL_BRUSH, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.ANIMAL_TAG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.MAPLE_SYRUP_BOTTLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.MILK_BOTTLE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.MIRACLE_POTION, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.CURRY_POWDER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.DUMPLING_POWDER, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.FLOUR, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.SALT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.COOKING_OIL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.RICEBALL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.CHOCOLATE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(DemeterItems.TEXT_BOX, ModelTemplates.FLAT_ITEM);
    }

    public void cubeModel(BlockModelGenerators blockModelGenerators, Block block) {
        Identifier model = TexturedModel.CUBE.create(block, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    public void grillModel(BlockModelGenerators blockModelGenerators, Block block) {
        Identifier model = TexturedModel.ORIENTABLE.create(block, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        Material frontTexture = TextureMapping.getBlockTexture(block, "_front_lit");
        Material topTexture = TextureMapping.getBlockTexture(block, "_top_lit");
        blockModelGenerators.plainVariant(
                TexturedModel.ORIENTABLE.get(block).updateTextures(t -> t.put(TextureSlot.FRONT, frontTexture)
                                .put(TextureSlot.TOP, topTexture))
                        .createWithSuffix(block, "_lit", blockModelGenerators.modelOutput)
        );
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    public void leavesModel(BlockModelGenerators blockModelGenerators, Block block) {
        Identifier model = TexturedModel.LEAVES.create(block, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    public void logWithHorizontal(BlockModelGenerators blockModelGenerators, Block block) {
        TextureMapping logMapping = TextureMapping.logColumn(block);
        Identifier model = ModelTemplates.CUBE_COLUMN.create(block, logMapping, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        blockModelGenerators.registerSimpleItemModel(block, model);
    }

    private void createCrossBlock(BlockModelGenerators blockModelGenerators, Block block, Item plantItem, BlockModelGenerators.PlantType plantType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        Identifier model = plantType.getCross().create(block, texturemapping, blockModelGenerators.modelOutput);
        blockModelGenerators.plainVariant(model);
        blockModelGenerators.registerSimpleItemModel(plantItem, blockModelGenerators.createFlatItemModelWithBlockTexture(plantItem, block));
    }
}
