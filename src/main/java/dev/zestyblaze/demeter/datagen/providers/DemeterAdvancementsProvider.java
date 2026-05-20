package dev.zestyblaze.demeter.datagen.providers;

import dev.zestyblaze.demeter.Demeter;
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
    }
}
