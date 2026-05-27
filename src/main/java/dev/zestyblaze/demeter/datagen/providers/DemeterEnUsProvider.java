package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.registry.DemeterBlocks;
import dev.zestyblaze.demeter.registry.DemeterEnchantments;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterBiomeTags;
import dev.zestyblaze.demeter.tags.DemeterBlockTags;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import dev.zestyblaze.demeter.util.QualityLevel;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.enchantment.Enchantment;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class DemeterEnUsProvider extends FabricLanguageProvider {
    public DemeterEnUsProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        QualityLevel[] qualities = QualityLevel.values();

        builder.add("advancements.demeter.root.title", "Welcome to Demeter");
        builder.add("advancements.demeter.root.description", "A Total Farming Overhaul Mod");
        builder.add("advancement.demeter.animal_brushed", "Neat and Tidy");
        builder.add("advancement.demeter.animal_brushed.desc", "Brush an Animal!");
        builder.add("advancement.demeter.animal_brushed_spite", "Malicious Brushing");
        builder.add("advancement.demeter.animal_brushed_spite.desc", "Brush your Animal with Ill Intent");
        builder.add("advancement.demeter.animal_pet", "Easy Does It");
        builder.add("advancement.demeter.animal_pet.desc", "Pet an Animal!");
        builder.add("advancements.demeter.guidebook.title", "Guide of the Goddess");
        builder.add("advancements.demeter.guidebook.description", "Open the Demeter's Guide");
        builder.add("advancement.demeter.obtain_maple_syrup", "Tapped");
        builder.add("advancement.demeter.obtain_maple_syrup.desc", "Obtain a Maple Syrup Bottle from tapping a tree");
        builder.add(DemeterBlocks.MAPLE_LOG, "Maple Log");
        builder.add(DemeterBlocks.STRIPPED_MAPLE_LOG, "Stripped Maple Log");
        builder.add(DemeterBlocks.MAPLE_LEAVES, "Maple Leaves");
        builder.add(DemeterBlocks.MAPLE_PLANKS, "Maple Planks");
        builder.add("booklet_category.demeter.animals", "Information about Animals");
        builder.add("booklet_category.demeter.basics", "Basics of Demeter");
        builder.add("booklet_category.demeter.crops", "Information about Crops");
        addEnchantmentAndDesc(builder, DemeterEnchantments.BARBER, "Barber", "Doubles the drops from shearing a sheep");
        addEnchantmentAndDesc(builder,DemeterEnchantments.COMFORT, "Comfort", "Exponentially increases the love from brushing per level");
        addEnchantmentAndDesc(builder,DemeterEnchantments.SPITE, "Curse of Spite", "Makes animals hate you for brushing them");
        builder.add(DemeterItems.ANIMAL_BRUSH, "Animal Brush");
        builder.add(DemeterItems.ANIMAL_TAG, "Animal Tag");
        builder.add(DemeterItems.DEMETERS_GUIDE, "Demeter's Guide");
        builder.add(DemeterItems.MAPLE_SYRUP_BOTTLE, "Maple Syrup Bottle");
        builder.add(DemeterItems.MILK_BOTTLE, "Milk Bottle");
        builder.add("item.demeter.milk_bottle.desc", "Removes one random negative potion effect");
        builder.add("item.demeter.quality_tooltip", "Quality: %s");
        Arrays.stream(qualities).forEach(quality -> builder.add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
        builder.add("itemGroup.demeter", "Demeter");
        builder.add("message.demeter.pet_animal", "You pet the %s");
        builder.add(DemeterBiomeTags.HAS_MAPLE_TREES, "Has Maple Trees");
        builder.add(DemeterBlockTags.MAPLE_LOGS, "Maple Logs");
        builder.add(DemeterItemTags.ANIMAL_BRUSH_TOOL, "Animal Brush");
        builder.add(DemeterItemTags.MAPLE_LOGS, "Maple Logs");
        builder.add(DemeterItemTags.QUALITY_PRODUCTS, "Quality Products");
    }

    public void addEnchantmentAndDesc(TranslationBuilder builder, ResourceKey<Enchantment> enchantment, String value, String value2) {
        builder.add(Util.makeDescriptionId("enchantment", enchantment.identifier()), value);
        builder.add(Util.makeDescriptionId("enchantment", enchantment.identifier().withSuffix(".desc")), value2);
    }
}
