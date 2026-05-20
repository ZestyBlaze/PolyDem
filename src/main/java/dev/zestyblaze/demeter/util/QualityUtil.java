package dev.zestyblaze.demeter.util;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.DemeterItemTags;
import dev.zestyblaze.demeter.component.QualityComponent;
import dev.zestyblaze.demeter.registry.DemeterComponents;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class QualityUtil {
    private static final Random random = new Random();

    public static void randomiseQuality(ItemStack stack) {
        if (!stack.is(DemeterItemTags.QUALITY_PRODUCTS)) return;
        int value = random.nextInt(100);
        if (value <= Demeter.config.qualityConfig.netheriteQualityChance.get()) {
            writeQuality(stack, QualityLevel.NETHERITE);
        }
        if (value <= Demeter.config.qualityConfig.ironQualityChance.get() && value > Demeter.config.qualityConfig.netheriteQualityChance.get()) {
            writeQuality(stack, QualityLevel.IRON);
        }
        if (value <= Demeter.config.qualityConfig.copperQualityChance.get() && value > Demeter.config.qualityConfig.ironQualityChance.get()) {
            writeQuality(stack, QualityLevel.COPPER);
        }
    }

    public static void writeQuality(ItemStack stack, QualityLevel quality) {
        stack.set(DemeterComponents.QUALITY, new QualityComponent(quality));
    }
}
