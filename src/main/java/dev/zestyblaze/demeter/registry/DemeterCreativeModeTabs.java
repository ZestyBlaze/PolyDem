package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import eu.pb4.polymer.core.api.item.PolymerCreativeModeTabUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class DemeterCreativeModeTabs {
    public static final CreativeModeTab DEMETER_ANIMAL_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.animals"))
            .icon(() -> new ItemStack(DemeterItems.ANIMAL_BRUSH))
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.DEMETERS_GUIDE);
                output.accept(DemeterItems.ANIMAL_BRUSH);
                output.accept(DemeterItems.ANIMAL_TAG);
                output.accept(DemeterItems.MIRACLE_POTION);

                output.accept(DemeterItems.MAPLE_LOG);
                output.accept(DemeterItems.STRIPPED_MAPLE_LOG);
                output.accept(DemeterItems.MAPLE_LEAVES);
                output.accept(DemeterItems.MAPLE_PLANKS);
            })
            .build();

    public static final CreativeModeTab DEMETER_COOKING_TAB = PolymerCreativeModeTabUtils.builder()
            .title(Component.translatable("itemGroup.demeter.cooking"))
            .icon(() -> new ItemStack(DemeterItems.CURRY_POWDER))
            .displayItems((parameters, output) -> {
                output.accept(DemeterItems.BRICK_GRILL);
                output.accept(DemeterItems.CHOCOLATE);
                output.accept(DemeterItems.COOKING_OIL);
                output.accept(DemeterItems.CURRY_POWDER);
                output.accept(DemeterItems.DUMPLING_POWDER);
                output.accept(DemeterItems.FLOUR);
                output.accept(DemeterItems.MAPLE_SYRUP_BOTTLE);
                output.accept(DemeterItems.MILK_BOTTLE);
                output.accept(DemeterItems.RICEBALL);
                output.accept(DemeterItems.SALT);
                output.accept(DemeterItems.BAMBOO_SHOOTS);
                output.accept(DemeterItems.CHAMOMILE);
                output.accept(DemeterItems.LAVENDER);
                output.accept(DemeterItems.MINT);
            })
            .build();

    public static void init() {
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("animals"), DEMETER_ANIMAL_TAB);
        PolymerCreativeModeTabUtils.registerPolymerCreativeModeTab(Demeter.createId("cooking"), DEMETER_COOKING_TAB);
    }
}
