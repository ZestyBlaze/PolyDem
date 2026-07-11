package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.advancement.DemeterTriggers;
import dev.zestyblaze.demeter.advancement.TriggerCriterion;
import dev.zestyblaze.demeter.registry.DemeterItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DemeterAdvancementsProvider extends FabricAdvancementProvider {
    public DemeterAdvancementsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        DemeterItems.ANIMAL_BRUSH,
                        Component.translatable("advancements.demeter.root.title"),
                        Component.translatable("advancements.demeter.root.description"),
                        Identifier.withDefaultNamespace("gui/advancements/backgrounds/husbandry"),
                        AdvancementType.TASK,
                        false, false, false
                ).addCriterion("any_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(consumer, "demeter:root");

        AdvancementHolder guideBook = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        DemeterItems.DEMETERS_GUIDE,
                        Component.translatable("advancements.demeter.guidebook.title"),
                        Component.translatable("advancements.demeter.guidebook.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                ).addCriterion("use", TriggerCriterion.of(DemeterTriggers.GUIDEBOOK))
                .save(consumer, "demeter:guidebook");

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        DemeterItems.MAPLE_SYRUP_BOTTLE,
                        Component.translatable("advancement.demeter.obtain_maple_syrup"),
                        Component.translatable("advancement.demeter.obtain_maple_syrup.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                ).addCriterion("obtain_maple_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(DemeterItems.MAPLE_SYRUP_BOTTLE))
                .save(consumer, "demeter:obtain_maple_syrup");

        AdvancementHolder petAnimal = Advancement.Builder.advancement().parent(root).display(
                        Items.COW_SPAWN_EGG,
                        Component.translatable("advancement.demeter.animal_pet"),
                        Component.translatable("advancement.demeter.animal_pet.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                ).addCriterion("pet_animal", TriggerCriterion.of(DemeterTriggers.PET_ANIMAL))
                .save(consumer, "demeter:pet_animal");

        AdvancementHolder brushedAnimal = Advancement.Builder.advancement().parent(petAnimal).display(
                        DemeterItems.ANIMAL_BRUSH,
                        Component.translatable("advancement.demeter.animal_brushed"),
                        Component.translatable("advancement.demeter.animal_brushed.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                ).addCriterion("brushed_animal", TriggerCriterion.of(DemeterTriggers.BRUSHED_ANIMAL))
                .save(consumer, "demeter:brushed_animal");

        AdvancementHolder brushedAnimalSpite = Advancement.Builder.advancement().parent(brushedAnimal).display(
                        DemeterItems.ANIMAL_BRUSH,
                        Component.translatable("advancement.demeter.animal_brushed_spite"),
                        Component.translatable("advancement.demeter.animal_brushed_spite.desc"),
                        null,
                        AdvancementType.CHALLENGE,
                        true, true, true
                ).addCriterion("brushed_animal_spite", TriggerCriterion.of(DemeterTriggers.BRUSHED_ANIMAL_SPITE))
                .save(consumer, "demeter:brushed_animal_spite");

        /*
        AdvancementHolder animalLoveMax = Advancement.Builder.advancement().parent(petAnimal).display(
                        DemeterItems.MIRACLE_POTION,
                        Component.translatable("advancement.demeter.animal_love_max"),
                        Component.translatable("advancement.demeter.animal_love_max.desc"),
                        null,
                        AdvancementType.CHALLENGE,
                        true, true, true
                ).addCriterion("love_max", AnimalLoveMaxTrigger.TriggerInstance.loveMax())
                .rewards(AdvancementRewards.Builder.experience(25))
                .save(consumer, "demeter:love_max");
         */

        /*
        AdvancementHolder acquireFoodPouch = Advancement.Builder.advancement().parent(root).display(
                        DemeterItems.FOOD_POUCH,
                        Component.translatable("advancement.demeter.acquire_food_pouch"),
                        Component.translatable("advancement.demeter.acquire_food_pouch.desc"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                ).addCriterion("acquire_food_pouch", InventoryChangeTrigger.TriggerInstance.hasItems(DemeterItems.FOOD_POUCH))
                .save(consumer, "demeter:acquire_food_pouch");
         */
    }
}
