package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.item.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;

public class DemeterItems {
    public static final CreativeModeTab DEMETER_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter"))
            .icon(Items.WHEAT::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.DEMETERS_GUIDE);
                output.accept(DemeterItems.ANIMAL_BRUSH);
                output.accept(DemeterItems.ANIMAL_TAG);
                output.accept(DemeterItems.MAPLE_SYRUP_BOTTLE);
                output.accept(DemeterItems.MILK_BOTTLE);
                output.accept(DemeterItems.MAPLE_LOG);
                output.accept(DemeterItems.STRIPPED_MAPLE_LOG);
                output.accept(DemeterItems.MAPLE_LEAVES);
                output.accept(DemeterItems.MAPLE_PLANKS);
            })
            .build();

    public static final Item DEMETERS_GUIDE = new GuidebookItem();
    public static final Item ANIMAL_BRUSH = new AnimalBrushItem();
    public static final Item ANIMAL_TAG = new AnimalTagItem();
    public static final Item MAPLE_SYRUP_BOTTLE = new SimplePolymerItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)
            .food(DemeterFoods.MAPLE_SYRUP_BOTTLE, Consumables.defaultDrink().build()).usingConvertsTo(Items.GLASS_BOTTLE)
            .stacksTo(16).setId(createKey("maple_syrup_bottle")));
    public static final Item MILK_BOTTLE = new MilkBottleItem();

    public static final BlockItem MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_log")));
    public static final BlockItem STRIPPED_MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.STRIPPED_MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("stripped_maple_log")));
    public static final BlockItem MAPLE_LEAVES = new PolymerBlockItem(DemeterBlocks.MAPLE_LEAVES,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_leaves")));
    public static final BlockItem MAPLE_PLANKS = new PolymerBlockItem(DemeterBlocks.MAPLE_PLANKS,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_planks")));

    public static void init() {
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("tab"), DEMETER_TAB);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("demeters_guide"), DEMETERS_GUIDE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_brush"), ANIMAL_BRUSH);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_tag"), ANIMAL_TAG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("milk_bottle"), MILK_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_syrup_bottle"), MAPLE_SYRUP_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_log"), MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("stripped_maple_log"), STRIPPED_MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_leaves"), MAPLE_LEAVES);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_planks"), MAPLE_PLANKS);
    }

    public static ResourceKey<Item> createKey(String id) {
        return ResourceKey.create(Registries.ITEM, Demeter.createId(id));
    }
}
