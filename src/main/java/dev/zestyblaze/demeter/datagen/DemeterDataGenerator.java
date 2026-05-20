package dev.zestyblaze.demeter.datagen;

import dev.zestyblaze.demeter.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DemeterDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(DemeterAdvancementsProvider::new);
		pack.addProvider(DemeterAnimalDataProvider::new);
		pack.addProvider(DemeterCropDataProvider::new);
		pack.addProvider(DemeterEnchantmentProvider::new);
		pack.addProvider(DemeterEnchantmentTagsProvider::new);
		pack.addProvider(DemeterEnUsProvider::new);
		pack.addProvider(DemeterItemTagsProvider::new);
		pack.addProvider(DemeterModelsProvider::new);
		pack.addProvider(DemeterRecipeProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, DemeterEnchantmentProvider::bootstrap);
	}
}
