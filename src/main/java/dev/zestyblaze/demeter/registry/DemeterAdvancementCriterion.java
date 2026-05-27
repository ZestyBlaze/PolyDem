package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.advancement.TriggerCriterion;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;

/**
 * A class taken from FactoryTools for usage here, licensed under GPL 3.0
 * Code found here {@link <a href="https://github.com/Patbox/FactoryTools/blob/master/src/main/java/eu/pb4/factorytools/api/advancement/FactoryAdvancementCriteria.java">...</a>}
 */
public class DemeterAdvancementCriterion {
    public static final TriggerCriterion TRIGGER = register("trigger", new TriggerCriterion());

    public static void init() {}

    public static <E extends CriterionTriggerInstance, T extends CriterionTrigger<E>> T register(String path, T item) {
        CriteriaTriggers.register(Demeter.MOD_ID + ":" + path, item);
        return item;
    }
}
