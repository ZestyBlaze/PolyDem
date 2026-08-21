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

        builder.add("advancements.demeter.root.title", "Demeter");
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
        builder.add(DemeterBlocks.BAMBOO_SHOOTS, "Bamboo Shoots");
        builder.add(DemeterBlocks.BRICK_GRILL, "Brick Grill");
        builder.add(DemeterBlocks.CHAMOMILE, "Chamomile");
        builder.add(DemeterBlocks.LAVENDER, "Lavender");
        builder.add(DemeterBlocks.MAPLE_LOG, "Maple Log");
        builder.add(DemeterBlocks.MAPLE_LEAVES, "Maple Leaves");
        builder.add(DemeterBlocks.MAPLE_PLANKS, "Maple Planks");
        builder.add(DemeterBlocks.MINT, "Mint");
        builder.add(DemeterBlocks.STRIPPED_MAPLE_LOG, "Stripped Maple Log");
        builder.add("booklet_category.demeter.animals", "Information about Animals");
        builder.add("booklet_category.demeter.basics", "Basics of Demeter");
        builder.add("booklet_category.demeter.crops", "Information about Crops");
        addEnchantmentAndDesc(builder, DemeterEnchantments.BARBER, "Barber", "Doubles the drops from shearing a sheep");
        addEnchantmentAndDesc(builder,DemeterEnchantments.COMFORT, "Comfort", "Exponentially increases the love from brushing per level");
        addEnchantmentAndDesc(builder,DemeterEnchantments.SPITE, "Curse of Spite", "Makes animals hate you for brushing them");
        builder.add(DemeterItems.ANIMAL_BRUSH, "Animal Brush");
        builder.add(DemeterItems.ANIMAL_TAG, "Animal Tag");
        builder.add(DemeterItems.APPLE_PIE, "Apple Pie");
        builder.add(DemeterItems.APPLE_SOUFFLE, "Apple Souffle");
        builder.add(DemeterItems.BAMBOO_RICE, "Bamboo Rice");
        builder.add(DemeterItems.BUTTER, "Butter");
        builder.add(DemeterItems.BUTTER_BISCUITS, "Butter Biscuits");
        builder.add(DemeterItems.CALENDER, "Calender");
        builder.add("item.demeter.calender.desc", "%s %s");
        builder.add(DemeterItems.CHIRASHI_SUSHI, "Chirashi Sushi");
        builder.add(DemeterItems.CHOCOLATE, "Chocolate");
        builder.add(DemeterItems.CHOCOLATE_CAKE, "Chocolate Cake");
        builder.add(DemeterItems.CHOCOLATE_COOKIES, "Chocolate Cookies");
        builder.add(DemeterItems.COOKING_OIL, "Cooking Oil");
        builder.add(DemeterItems.CURRIED_RICE, "Curried Rice");
        builder.add(DemeterItems.CURRY_BREAD, "Curry Bread");
        builder.add(DemeterItems.CURRY_NOODLES, "Curry Noodles");
        builder.add(DemeterItems.CURRY_POWDER, "Curry Powder");
        builder.add(DemeterItems.DEEP_BOWL, "Deep Bowl");
        builder.add(DemeterItems.DEMETERS_GUIDE, "Demeter's Guide");
        builder.add(DemeterItems.DUMPLING_POWDER, "Dumpling Powder");
        builder.add(DemeterItems.FLOUR, "Flour");
        builder.add(DemeterItems.GLASS, "Glass");
        builder.add(DemeterItems.JAM_JAR, "Jam Jar");
        builder.add(DemeterItems.MAPLE_SYRUP_BOTTLE, "Maple Syrup Bottle");
        builder.add(DemeterItems.MILK_BOTTLE, "Milk Bottle");
        builder.add("item.demeter.milk_bottle.desc", "Removes one random negative potion effect");
        builder.add(DemeterItems.MIRACLE_POTION, "Miracle Potion");
        builder.add(DemeterItems.MUG, "Mug");
        builder.add(DemeterItems.PICKLING_JAR, "Pickling Jar");
        builder.add(DemeterItems.PLATE, "Plate");
        builder.add("item.demeter.quality_tooltip", "Quality: %s");
        Arrays.stream(qualities).forEach(quality -> builder.add("item.demeter.quality_tooltip." + quality.getName(), StringUtils.capitalize(quality.getName())));
        builder.add(DemeterItems.RICEBALL, "Riceball");
        builder.add(DemeterItems.SALT, "Salt");
        builder.add(DemeterItems.UNFIRED_MUG, "Unfired Mug");
        builder.add(DemeterItems.UNFIRED_PLATE, "Unfired Plate");
        builder.add("itemGroup.demeter.animals", "Demeter: Animals");
        builder.add("itemGroup.demeter.cooking", "Demeter: Cooking");
        builder.add("itemGroup.demeter.fishing", "Demeter: Fishing");
        builder.add("itemGroup.demeter.seasons", "Demeter: Seasons");
        builder.add("itemGroup.demeter.world", "Demeter: World");
        builder.add("message.demeter.pet_animal", "You pet the %s");
        builder.add("season.demeter.spring", "Spring");
        builder.add(DemeterBiomeTags.HAS_MAPLE_TREES, "Has Maple Trees");
        builder.add(DemeterBlockTags.MAPLE_LOGS, "Maple Logs");
        builder.add(DemeterItemTags.ANIMAL_BRUSH_TOOL, "Animal Brush");
        builder.add(DemeterItemTags.GRILL_STARTERS, "Grill Starters");
        builder.add(DemeterItemTags.MAPLE_LOGS, "Maple Logs");
        builder.add(DemeterItemTags.QUALITY_PRODUCTS, "Quality Products");
    }

    public void addEnchantmentAndDesc(TranslationBuilder builder, ResourceKey<Enchantment> enchantment, String value, String value2) {
        builder.add(Util.makeDescriptionId("enchantment", enchantment.identifier()), value);
        builder.add(Util.makeDescriptionId("enchantment", enchantment.identifier().withSuffix(".desc")), value2);
    }
}
