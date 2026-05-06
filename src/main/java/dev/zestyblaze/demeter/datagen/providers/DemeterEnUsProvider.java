package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.DemeterItemTags;
import dev.zestyblaze.demeter.duck.QualityLevel;
import dev.zestyblaze.demeter.registry.DemeterEnchantments;
import dev.zestyblaze.demeter.util.DataLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class DemeterEnUsProvider extends DataLanguageProvider {
    public DemeterEnUsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        QualityLevel[] qualities = QualityLevel.values();

        builder.addEnchantment(DemeterEnchantments.BARBER, "Barber");
        builder.add("item.demeter.quality_tooltip", "Quality: %s");
        Arrays.stream(qualities).forEach(quality -> builder.add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
        builder.add(DemeterItemTags.QUALITY_PRODUCTS, "Quality Products");
    }
}
