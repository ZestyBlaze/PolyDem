package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class DemeterEnchantments {
    public static final ResourceKey<Enchantment> BARBER = key("barber");
    public static final ResourceKey<Enchantment> COMFORT = key("comfort");
    public static final ResourceKey<Enchantment> SPITE = key("spite");

    private static ResourceKey<Enchantment> key(String path) {
        return ResourceKey.create(Registries.ENCHANTMENT, Demeter.createId(path));
    }
}
