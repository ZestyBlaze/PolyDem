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
        minecraftAnimal(provider, "camel", 10, 14, 11, 13);
        minecraftAnimal(provider, "cat", 10, 20, 5, 7);
        minecraftAnimal(provider, "cow", 12, 20, 9, 14);
        minecraftAnimal(provider, "donkey", 25, 30, 24, 12);
        minecraftAnimal(provider, "fox", 3, 8, 6, 7);
        minecraftAnimal(provider, "goat", 15, 18, 7, 9);
        minecraftAnimal(provider, "horse", 25, 30, 24, 12);
        minecraftAnimal(provider, "llama", 15, 25, 11, 10);
        minecraftAnimal(provider, "mooshroom", 13, 21, 10, 21);
        minecraftAnimal(provider, "mule", 25, 30, 24, 12);
        minecraftAnimal(provider, "ocelot", 6, 12, 6, 7);
        minecraftAnimal(provider, "panda", 15, 25, 12, 10);
        minecraftAnimal(provider, "parrot", 25, 50, 3, 36);
        minecraftAnimal(provider, "pig", 6, 10, 4, 4, 4, 7);
        minecraftAnimal(provider, "polar_bear", 12, 16, 14, 8);
        minecraftAnimal(provider, "rabbit", 8, 12, 4, 3);
        minecraftAnimal(provider, "sheep", 8, 12, 5, 4);
        minecraftAnimal(provider, "wolf", 10, 13, 5, 7);
    }

    @Override
    public String getName() {
        return "Demeter AnimalData";
    }

    private void minecraftAnimal(BiConsumer<Identifier, AnimalData> provider, String animalName,
                                 int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp) {
        minecraftAnimal(provider, animalName, minLifespan, maxLifespan, daysPregnant, daysToGrowUp, 1, 1);
    }

    private void minecraftAnimal(BiConsumer<Identifier, AnimalData> provider, String animalName,
                                 int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp,
                                 int minChildrenPerBirth, int maxChildrenPerBirth) {
        provider.accept(Identifier.withDefaultNamespace(animalName), new AnimalData(minLifespan, maxLifespan, daysPregnant, daysToGrowUp,
                minChildrenPerBirth, maxChildrenPerBirth));
    }
}
