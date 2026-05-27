package dev.zestyblaze.demeter.tags;

import dev.zestyblaze.demeter.Demeter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DemeterItemTags {
    public static final TagKey<Item> ANIMAL_BRUSH_TOOL = commonTag("tools/animal_brush");
    public static final TagKey<Item> MAPLE_LOGS = modTag("maple_logs");
    public static final TagKey<Item> QUALITY_PRODUCTS = modTag("quality_products");

    private static TagKey<Item> modTag(String id) {
        return TagKey.create(Registries.ITEM, Demeter.createId(id));
    }

    private static TagKey<Item> commonTag(String id) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", id));
    }
}
