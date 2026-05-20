package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class DemeterEnchantmentTagsProvider extends FabricTagsProvider<Enchantment> {
    public DemeterEnchantmentTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.ENCHANTMENT, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(EnchantmentTags.IN_ENCHANTING_TABLE).add(DemeterEnchantments.BARBER, DemeterEnchantments.COMFORT);
        builder(EnchantmentTags.ON_TRADED_EQUIPMENT).add(DemeterEnchantments.BARBER, DemeterEnchantments.COMFORT, DemeterEnchantments.SPITE);
        builder(EnchantmentTags.ON_RANDOM_LOOT).add(DemeterEnchantments.BARBER, DemeterEnchantments.COMFORT, DemeterEnchantments.SPITE);
        builder(EnchantmentTags.CURSE).add(DemeterEnchantments.SPITE);
    }
}
