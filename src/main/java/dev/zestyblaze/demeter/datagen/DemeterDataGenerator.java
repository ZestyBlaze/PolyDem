package dev.zestyblaze.demeter.datagen;

import dev.zestyblaze.demeter.datagen.providers.DemeterCropDataProvider;
import dev.zestyblaze.demeter.datagen.providers.DemeterEnUsProvider;
import dev.zestyblaze.demeter.datagen.providers.DemeterEnchantmentProvider;
import dev.zestyblaze.demeter.datagen.providers.DemeterItemTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DemeterDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(DemeterCropDataProvider::new);
		pack.addProvider(DemeterEnchantmentProvider::new);
		pack.addProvider(DemeterEnUsProvider::new);
		pack.addProvider(DemeterItemTagsProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, DemeterEnchantmentProvider::bootstrap);
	}
}
