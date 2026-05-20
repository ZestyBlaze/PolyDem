package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.item.AnimalBrushItem;
import dev.zestyblaze.demeter.item.AnimalTagItem;
import dev.zestyblaze.demeter.item.GuidebookItem;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DemeterItems {
    public static final CreativeModeTab DEMETER_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter"))
            .icon(Items.WHEAT::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.DEMETERS_GUIDE);
                output.accept(DemeterItems.ANIMAL_BRUSH);
                output.accept(DemeterItems.ANIMAL_TAG);
                output.accept(DemeterItems.MAPLE_LOG);
                output.accept(DemeterItems.STRIPPED_MAPLE_LOG);
                output.accept(DemeterItems.MAPLE_PLANKS);
            })
            .build();

    public static final Item DEMETERS_GUIDE = new GuidebookItem();
    public static final Item ANIMAL_BRUSH = new AnimalBrushItem();
    public static final Item ANIMAL_TAG = new AnimalTagItem();

    public static final BlockItem MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_log")));
    public static final BlockItem STRIPPED_MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.STRIPPED_MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("stripped_maple_log")));
    public static final BlockItem MAPLE_PLANKS = new PolymerBlockItem(DemeterBlocks.MAPLE_PLANKS,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_planks")));

    public static void init() {
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("tab"), DEMETER_TAB);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("demeters_guide"), DEMETERS_GUIDE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_brush"), ANIMAL_BRUSH);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_tag"), ANIMAL_TAG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_log"), MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("stripped_maple_log"), STRIPPED_MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_planks"), MAPLE_PLANKS);
    }

    public static ResourceKey<Item> createKey(String id) {
        return ResourceKey.create(Registries.ITEM, Demeter.createId(id));
    }
}
