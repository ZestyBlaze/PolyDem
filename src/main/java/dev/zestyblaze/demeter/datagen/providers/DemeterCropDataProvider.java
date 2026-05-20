package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.data.CropData;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DemeterCropDataProvider extends FabricCodecDataProvider<CropData> {
    public DemeterCropDataProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "demeter/crop_data", CropData.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, CropData> provider, HolderLookup.Provider registryLookup) {
        minecraftCrop(provider, "wheat", 3, 7);
        minecraftCrop(provider, "carrots", 4, 7);
        minecraftCrop(provider, "potatoes", 5, 7);
        minecraftCrop(provider, "beetroots", 3, 3);
        minecraftCrop(provider, "pitcher_crop", 2, 4, 3);
        minecraftCrop(provider, "torchflower_crop", 2, 2, Blocks.TORCHFLOWER);
    }

    @Override
    public String getName() {
        return "Demeter CropData";
    }

    private void minecraftCrop(BiConsumer<Identifier, CropData> provider, String cropName, Integer daysToGrow, Integer maxAge) {
        provider.accept(Identifier.withDefaultNamespace(cropName), new CropData(daysToGrow, maxAge));
    }

    private void minecraftCrop(BiConsumer<Identifier, CropData> provider, String cropName, Integer daysToGrow, Integer maxAge, Block block) {
        provider.accept(Identifier.withDefaultNamespace(cropName), new CropData(daysToGrow, maxAge, "age", block));
    }

    private void minecraftCrop(BiConsumer<Identifier, CropData> provider, String cropName, Integer daysToGrow, Integer maxAge, Integer daysToDouble) {
        provider.accept(Identifier.withDefaultNamespace(cropName), new CropData(daysToGrow, maxAge, "age", Optional.empty(), Optional.of(daysToDouble)));
    }
}
