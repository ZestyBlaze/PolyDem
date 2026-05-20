package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.data.AnimalData;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class DemeterAnimalDataProvider extends FabricCodecDataProvider<AnimalData> {
    public DemeterAnimalDataProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, "demeter/animal_data", AnimalData.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, AnimalData> provider, HolderLookup.Provider registryLookup) {
        minecraftAnimal(provider, "cow", 14);
    }

    @Override
    public String getName() {
        return "Demeter AnimalData";
    }

    private void minecraftAnimal(BiConsumer<Identifier, AnimalData> provider, String animalName,
                                 int daysToGrowUp) {
        provider.accept(Identifier.withDefaultNamespace(animalName), new AnimalData(daysToGrowUp));
    }
}
