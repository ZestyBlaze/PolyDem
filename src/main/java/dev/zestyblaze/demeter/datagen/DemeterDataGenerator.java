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
		pack.addProvider(DemeterBiomeTagProvider::new);
		pack.addProvider(DemeterBlockLootProvider::new);
		pack.addProvider(DemeterBlockTagsProvider::new);
		pack.addProvider(DemeterCropDataProvider::new);
		pack.addProvider(DemeterEnchantmentProvider::new);
		pack.addProvider(DemeterEnchantmentTagsProvider::new);
		pack.addProvider(DemeterEnUsProvider::new);
		pack.addProvider(DemeterItemTagsProvider::new);
		pack.addProvider(DemeterModelsProvider::new);
		pack.addProvider(DemeterRecipeProvider::new);
		pack.addProvider(DemeterWorldGenProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, DemeterWorldGenProvider::bootstrapConfiguredFeatures);
		registryBuilder.add(Registries.ENCHANTMENT, DemeterEnchantmentProvider::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, DemeterWorldGenProvider::bootstrapPlacedFeatures);
	}
}
