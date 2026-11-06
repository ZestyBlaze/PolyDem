package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.data.CropItemSeason;
import dev.zestyblaze.demeter.world.season.Season;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DemeterCropSeasonProvider extends FabricCodecDataProvider<CropItemSeason> {
    public DemeterCropSeasonProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "demeter/crop_seasons", CropItemSeason.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, CropItemSeason> provider, HolderLookup.Provider registryLookup) {
        minecraftCrop(provider, Items.WHEAT_SEEDS, Season.SPRING, Season.SUMMER, Season.AUTUMN);
        minecraftCrop(provider, Items.CARROT, Season.AUTUMN);
        minecraftCrop(provider, Items.POTATO, Season.SPRING);
        minecraftCrop(provider, Items.BEETROOT_SEEDS, Season.AUTUMN);
    }

    private void minecraftCrop(BiConsumer<Identifier, CropItemSeason> provider, Item item, Season... validSeasons) {
        provider.accept(BuiltInRegistries.ITEM.getKey(item), new CropItemSeason(List.of(validSeasons)));
    }

    @Override
    public String getName() {
        return "Demeter Crop Season Provider";
    }
}
