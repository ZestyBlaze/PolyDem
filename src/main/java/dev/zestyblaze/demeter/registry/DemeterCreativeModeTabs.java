package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DemeterCreativeModeTabs {
    public static final CreativeModeTab DEMETER_ANIMAL_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.animals"))
            .icon(() -> new ItemStack(DemeterItems.ANIMAL_BRUSH))
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.DEMETERS_GUIDE);
                output.accept(DemeterItems.ANIMAL_BRUSH);
                output.accept(DemeterItems.ANIMAL_TAG);
                output.accept(DemeterItems.MIRACLE_POTION);
                output.accept(DemeterItems.MILK_BOTTLE);
            })
            .build();

    public static final CreativeModeTab DEMETER_COOKING_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.cooking"))
            .icon(() -> new ItemStack(DemeterItems.CURRY_POWDER))
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.BRICK_GRILL);
                output.accept(DemeterItems.UNFIRED_MUG);
                output.accept(DemeterItems.UNFIRED_PLATE);
                output.accept(DemeterItems.MUG);
                output.accept(DemeterItems.PLATE);
                output.accept(DemeterItems.DEEP_BOWL);
                output.accept(DemeterItems.GLASS);
                output.accept(DemeterItems.JAM_JAR);
                output.accept(DemeterItems.PICKLING_JAR);
                output.accept(DemeterItems.BUTTER);
                output.accept(DemeterItems.COOKING_OIL);
                output.accept(DemeterItems.CURRY_POWDER);
                output.accept(DemeterItems.DUMPLING_POWDER);
                output.accept(DemeterItems.FLOUR);
                output.accept(DemeterItems.SALT);
                output.accept(DemeterItems.APPLE_PIE);
                output.accept(DemeterItems.APPLE_SOUFFLE);
                output.accept(DemeterItems.BAMBOO_RICE);
                output.accept(DemeterItems.BUTTER_BISCUITS);
                output.accept(DemeterItems.CHIRASHI_SUSHI);
                output.accept(DemeterItems.CHOCOLATE);
                output.accept(DemeterItems.CHOCOLATE_CAKE);
                output.accept(DemeterItems.CHOCOLATE_COOKIES);
                output.accept(DemeterItems.CURRIED_RICE);
                output.accept(DemeterItems.CURRY_BREAD);
                output.accept(DemeterItems.CURRY_NOODLES);
                output.accept(DemeterItems.MAPLE_SYRUP_BOTTLE);
                output.accept(DemeterItems.RICEBALL);
            })
            .build();

    public static final CreativeModeTab DEMETER_FISHING_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.fishing"))
            .icon(() -> new ItemStack(Items.COD))
            .displayItems((parameters, output) -> {
                output.accept(Items.COD);
            })
            .build();

    public static final CreativeModeTab DEMETER_SEASONS_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.seasons"))
            .icon(() -> new ItemStack(Items.OAK_LEAVES))
            .displayItems((parameters, output) -> {
                output.accept(Items.OAK_LEAVES);
                output.accept(DemeterItems.CALENDER);
            })
            .build();

    public static final CreativeModeTab DEMETER_WORLD_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.world"))
            .icon(() -> new ItemStack(DemeterItems.MAPLE_LOG))
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.MAPLE_LOG);
                output.accept(DemeterItems.STRIPPED_MAPLE_LOG);
                output.accept(DemeterItems.MAPLE_LEAVES);
                output.accept(DemeterItems.MAPLE_PLANKS);

                output.accept(DemeterItems.BAMBOO_SHOOTS);
                output.accept(DemeterItems.CHAMOMILE);
                output.accept(DemeterItems.LAVENDER);
                output.accept(DemeterItems.MINT);
            })
            .build();

    public static void init() {
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("animals"), DEMETER_ANIMAL_TAB);
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("cooking"), DEMETER_COOKING_TAB);
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("fishing"), DEMETER_FISHING_TAB);
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("seasons"), DEMETER_SEASONS_TAB);
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("world"), DEMETER_WORLD_TAB);
    }
}
