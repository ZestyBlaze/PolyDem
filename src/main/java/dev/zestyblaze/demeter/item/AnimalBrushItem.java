package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.registry.DemeterItems;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.world.item.Items;

public class AnimalBrushItem extends SimplePolymerItem {
    public AnimalBrushItem() {
        super(new Properties()
                .durability(156)
                .stacksTo(1)
                .setId(DemeterItems.createKey("animal_brush")));
    }
}
