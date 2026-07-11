package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.item.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;

public class DemeterItems {
    public static final Item DEMETERS_GUIDE = new GuidebookItem();
    public static final Item ANIMAL_BRUSH = new AnimalBrushItem();
    public static final Item ANIMAL_TAG = new AnimalTagItem();
    public static final Item MAPLE_SYRUP_BOTTLE = new SimplePolymerItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)
            .food(DemeterFoods.MAPLE_SYRUP_BOTTLE, Consumables.defaultDrink().build()).usingConvertsTo(Items.GLASS_BOTTLE)
            .stacksTo(16).setId(createKey("maple_syrup_bottle")));
    public static final Item MILK_BOTTLE = new MilkBottleItem();
    public static final Item MIRACLE_POTION = new MiraclePotionItem();

    //Ingredients
    public static final Item CURRY_POWDER = new SimplePolymerItem(new Item.Properties().setId(createKey("curry_powder")));
    public static final Item DUMPLING_POWDER = new SimplePolymerItem(new Item.Properties().setId(createKey("dumpling_powder")));
    public static final Item FLOUR = new SimplePolymerItem(new Item.Properties().setId(createKey("flour")));
    public static final Item SALT = new SimplePolymerItem(new Item.Properties().setId(createKey("salt")));
    public static final Item COOKING_OIL = new SimplePolymerItem(new Item.Properties().setId(createKey("cooking_oil")));
    public static final Item RICEBALL = new SimplePolymerItem(new Item.Properties().setId(createKey("riceball")));
    public static final Item CHOCOLATE = new SimplePolymerItem(new Item.Properties().setId(createKey("chocolate")));

    public static final BlockItem MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_log")));
    public static final BlockItem STRIPPED_MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.STRIPPED_MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("stripped_maple_log")));
    public static final BlockItem MAPLE_LEAVES = new PolymerBlockItem(DemeterBlocks.MAPLE_LEAVES,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_leaves")));
    public static final BlockItem MAPLE_PLANKS = new PolymerBlockItem(DemeterBlocks.MAPLE_PLANKS,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_planks")));
    public static final BlockItem BAMBOO_SHOOTS = new PolymerBlockItem(DemeterBlocks.BAMBOO_SHOOTS,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("bamboo_shoots")));
    public static final BlockItem MINT = new PolymerBlockItem(DemeterBlocks.MINT,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("mint")));
    public static final BlockItem CHAMOMILE = new PolymerBlockItem(DemeterBlocks.CHAMOMILE,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("chamomile")));
    public static final BlockItem LAVENDER = new PolymerBlockItem(DemeterBlocks.LAVENDER,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("lavender")));

    public static final BlockItem BRICK_GRILL = new PolymerBlockItem(DemeterBlocks.BRICK_GRILL,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("brick_grill")));


    //No Item
    public static final Item TEXT_BOX = new SimplePolymerItem(new Item.Properties().setId(createKey("text_box")));

    public static void init() {
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("demeters_guide"), DEMETERS_GUIDE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_brush"), ANIMAL_BRUSH);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_tag"), ANIMAL_TAG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_syrup_bottle"), MAPLE_SYRUP_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("milk_bottle"), MILK_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("miracle_potion"), MIRACLE_POTION);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("curry_powder"), CURRY_POWDER);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("dumpling_powder"), DUMPLING_POWDER);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("flour"), FLOUR);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("salt"), SALT);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("cooking_oil"), COOKING_OIL);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("riceball"), RICEBALL);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chocolate"), CHOCOLATE);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_log"), MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("stripped_maple_log"), STRIPPED_MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_leaves"), MAPLE_LEAVES);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_planks"), MAPLE_PLANKS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("bamboo_shoots"), BAMBOO_SHOOTS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("mint"), MINT);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chamomile"), CHAMOMILE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("lavender"), LAVENDER);



        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("brick_grill"), BRICK_GRILL);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("text_box"), TEXT_BOX);
    }

    public static ResourceKey<Item> createKey(String id) {
        return ResourceKey.create(Registries.ITEM, Demeter.createId(id));
    }
}
