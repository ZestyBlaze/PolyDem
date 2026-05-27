package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterEnchantments;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.concurrent.CompletableFuture;

public class DemeterEnchantmentProvider extends FabricDynamicRegistryProvider {
    public DemeterEnchantmentProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemGetter = context.lookup(Registries.ITEM);
        register(context, DemeterEnchantments.BARBER, Enchantment.enchantment(
                Enchantment.definition(
                        itemGetter.getOrThrow(ConventionalItemTags.SHEAR_TOOLS),
                        2, 1,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9), 4,
                        EquipmentSlotGroup.MAINHAND
                )
        ));
        register(context, DemeterEnchantments.COMFORT, Enchantment.enchantment(
                Enchantment.definition(
                        itemGetter.getOrThrow(DemeterItemTags.ANIMAL_BRUSH_TOOL),
                        3, 3,
                        Enchantment.dynamicCost(12, 7),
                        Enchantment.dynamicCost(65, 7), 4,
                        EquipmentSlotGroup.MAINHAND
                )
        ));
        register(context, DemeterEnchantments.SPITE, Enchantment.enchantment(
                Enchantment.definition(
                        itemGetter.getOrThrow(DemeterItemTags.ANIMAL_BRUSH_TOOL),
                        2, 3,
                        Enchantment.dynamicCost(12, 9),
                        Enchantment.dynamicCost(65, 9), 4,
                        EquipmentSlotGroup.MAINHAND
                )
        ));
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT));
    }

    @Override
    public String getName() {
        return "Demeter Enchantment Provider";
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.identifier()));
    }
}
