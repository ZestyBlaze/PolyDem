package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.item.*;
import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Compostable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class DemeterItems {
    //Normal Misc Items
    public static final Item DEMETERS_GUIDE = new GuidebookItem();
    public static final Item ANIMAL_BRUSH = new AnimalBrushItem();
    public static final Item ANIMAL_TAG = new AnimalTagItem();
    public static final Item MILK_BOTTLE = new MilkBottleItem();
    public static final Item MIRACLE_POTION = new MiraclePotionItem();

    //Dishes
    public static final Item UNFIRED_MUG = new SimplePolymerItem(new Item.Properties().setId(createKey("unfired_mug")));
    public static final Item UNFIRED_PLATE = new SimplePolymerItem(new Item.Properties().setId(createKey("unfired_plate")));
    public static final Item MUG = new SimplePolymerItem(new Item.Properties().setId(createKey("mug")));
    public static final Item PLATE = new SimplePolymerItem(new Item.Properties().setId(createKey("plate")));
    public static final Item DEEP_BOWL = new SimplePolymerItem(new Item.Properties().setId(createKey("deep_bowl")));
    public static final Item GLASS = new SimplePolymerItem(new Item.Properties().setId(createKey("glass")));
    public static final Item JAM_JAR = new SimplePolymerItem(new Item.Properties().setId(createKey("jam_jar")));
    public static final Item PICKLING_JAR = new SimplePolymerItem(new Item.Properties().setId(createKey("pickling_jar")));

    //Ingredients
    public static final Item BUTTER = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.BUTTER).setId(createKey("butter")));
    public static final Item COOKING_OIL = new SimplePolymerItem(new Item.Properties().setId(createKey("cooking_oil")));
    public static final Item CURRY_POWDER = new SimplePolymerItem(new Item.Properties().setId(createKey("curry_powder")));
    public static final Item DUMPLING_POWDER = new SimplePolymerItem(new Item.Properties().setId(createKey("dumpling_powder")));
    public static final Item FLOUR = new SimplePolymerItem(new Item.Properties().setId(createKey("flour")));
    public static final Item SALT = new SimplePolymerItem(new Item.Properties().setId(createKey("salt")));

    //Meals
    public static final Item APPLE_PIE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.APPLE_PIE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("apple_pie")));
    public static final Item APPLE_SOUFFLE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.APPLE_SOUFFLE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("apple_souffle")));
    public static final Item BAMBOO_RICE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.BAMBOO_RICE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("bamboo_rice")));
    public static final Item BUTTER_BISCUITS = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.BUTTER_BISCUITS)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("butter_biscuits")));
    public static final Item CHIRASHI_SUSHI = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CHIRASHI_SUSHI)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("chirashi_sushi")));
    public static final Item CHOCOLATE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CHOCOLATE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("chocolate")));
    public static final Item CHOCOLATE_CAKE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CHOCOLATE_CAKE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("chocolate_cake")));
    public static final Item CHOCOLATE_COOKIES = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CHOCOLATE_COOKIES)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("chocolate_cookies")));
    public static final Item CURRIED_RICE = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CURRIED_RICE)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("curried_rice")));
    public static final Item CURRY_BREAD = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CURRY_BREAD)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("curry_bread")));
    public static final Item CURRY_NOODLES = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.CURRY_NOODLES)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("curry_noodles")));
    public static final Item DINNER_ROLL = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.DINNER_ROLL)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("dinner_roll")));
    public static final Item DORIA = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.DORIA)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("doria")));
    public static final Item MAPLE_SYRUP_BOTTLE = new SimplePolymerItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)
            .food(DemeterFoods.MAPLE_SYRUP_BOTTLE, Consumables.defaultDrink().build()).usingConvertsTo(Items.GLASS_BOTTLE)
            .stacksTo(16)
            .setId(createKey("maple_syrup_bottle")));
    public static final Item RICEBALL = new SimplePolymerItem(new Item.Properties().food(DemeterFoods.RICEBALL)
            .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_MEDIUM))
            .setId(createKey("riceball")));

    //Season Items
    public static final Item CALENDER = new SimplePolymerItem(new Item.Properties().stacksTo(1).setId(createKey("calender")));

    //World Items
    public static final BlockItem MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_log")));
    public static final BlockItem STRIPPED_MAPLE_LOG = new PolymerBlockItem(DemeterBlocks.STRIPPED_MAPLE_LOG,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("stripped_maple_log")));
    public static final BlockItem MAPLE_LEAVES = new PolymerBlockItem(DemeterBlocks.MAPLE_LEAVES,
            new Item.Properties().useBlockDescriptionPrefix()
                    .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW))
                    .setId(createKey("maple_leaves")));
    public static final BlockItem MAPLE_PLANKS = new PolymerBlockItem(DemeterBlocks.MAPLE_PLANKS,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("maple_planks")));
    public static final BlockItem BAMBOO_SHOOTS = new PolymerBlockItem(DemeterBlocks.BAMBOO_SHOOTS,
            new Item.Properties().useBlockDescriptionPrefix()
                    .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW))
                    .setId(createKey("bamboo_shoots")));
    public static final BlockItem MINT = new PolymerBlockItem(DemeterBlocks.MINT,
            new Item.Properties().useBlockDescriptionPrefix()
                    .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW))
                    .setId(createKey("mint")));
    public static final BlockItem CHAMOMILE = new PolymerBlockItem(DemeterBlocks.CHAMOMILE,
            new Item.Properties().useBlockDescriptionPrefix()
                    .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW))
                    .setId(createKey("chamomile")));
    public static final BlockItem LAVENDER = new PolymerBlockItem(DemeterBlocks.LAVENDER,
            new Item.Properties().useBlockDescriptionPrefix()
                    .component(DataComponents.COMPOSTABLE, new Compostable(ContextIntProviders.COMPOSTABLE_LOW))
                    .setId(createKey("lavender")));

    //Misc Blocks
    public static final BlockItem BRICK_GRILL = new PolymerBlockItem(DemeterBlocks.BRICK_GRILL,
            new Item.Properties().useBlockDescriptionPrefix().setId(createKey("brick_grill")));


    //No Item
    public static final Item TEXT_BOX = new SimplePolymerItem(new Item.Properties().setId(createKey("text_box")));

    public static void init() {
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("demeters_guide"), DEMETERS_GUIDE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_brush"), ANIMAL_BRUSH);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("animal_tag"), ANIMAL_TAG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("milk_bottle"), MILK_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("miracle_potion"), MIRACLE_POTION);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("unfired_mug"), UNFIRED_MUG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("unfired_plate"), UNFIRED_PLATE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("mug"), MUG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("plate"), PLATE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("deep_bowl"), DEEP_BOWL);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("glass"), GLASS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("jam_jar"), JAM_JAR);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("pickling_jar"), PICKLING_JAR);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("butter"), BUTTER);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("cooking_oil"), COOKING_OIL);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("curry_powder"), CURRY_POWDER);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("dumpling_powder"), DUMPLING_POWDER);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("flour"), FLOUR);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("salt"), SALT);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("apple_pie"), APPLE_PIE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("apple_souffle"), APPLE_SOUFFLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("bamboo_rice"), BAMBOO_RICE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("butter_biscuits"), BUTTER_BISCUITS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chirashi_sushi"), CHIRASHI_SUSHI);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chocolate"), CHOCOLATE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chocolate_cake"), CHOCOLATE_CAKE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chocolate_cookies"), CHOCOLATE_COOKIES);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("curried_rice"), CURRIED_RICE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("curry_bread"), CURRY_BREAD);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("curry_noodles"), CURRY_NOODLES);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("dinner_roll"), DINNER_ROLL);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("doria"), DORIA);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_syrup_bottle"), MAPLE_SYRUP_BOTTLE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("riceball"), RICEBALL);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_log"), MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("stripped_maple_log"), STRIPPED_MAPLE_LOG);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_leaves"), MAPLE_LEAVES);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("maple_planks"), MAPLE_PLANKS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("bamboo_shoots"), BAMBOO_SHOOTS);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("mint"), MINT);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("chamomile"), CHAMOMILE);
        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("lavender"), LAVENDER);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("calender"), CALENDER);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("brick_grill"), BRICK_GRILL);

        Registry.register(BuiltInRegistries.ITEM, Demeter.createId("text_box"), TEXT_BOX);
    }

    public static ResourceKey<Item> createKey(String id) {
        return ResourceKey.create(Registries.ITEM, Demeter.createId(id));
    }
}
