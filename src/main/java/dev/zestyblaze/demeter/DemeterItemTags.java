package dev.zestyblaze.demeter;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DemeterItemTags {
    public static final TagKey<Item> QUALITY_PRODUCTS = TagKey.create(Registries.ITEM, Demeter.createId("quality_products"));
}
