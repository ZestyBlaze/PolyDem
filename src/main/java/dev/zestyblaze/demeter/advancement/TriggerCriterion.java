package dev.zestyblaze.demeter.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.registry.DemeterAdvancementCriterion;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

/**
 * A class taken from FactoryTools for usage here, licensed under GPL 3.0
 * Code found here {@link <a href="https://github.com/Patbox/FactoryTools/blob/master/src/main/java/eu/pb4/factorytools/api/advancement/TriggerCriterion.java">...</a>}
 */
public class TriggerCriterion extends SimpleCriterionTrigger<TriggerCriterion.Condition> {
    public static Criterion<?> of(Identifier id) {
        return DemeterAdvancementCriterion.TRIGGER.createCriterion(new Condition(id));
    }

    public static void trigger(ServerPlayer player, Identifier identifier) {
        DemeterAdvancementCriterion.TRIGGER.trigger(player, condition -> condition.identifier().equals(identifier));
    }

    @Override
    public Codec<Condition> codec() {
        return Condition.CODEC;
    }

    public record Condition(Identifier identifier) implements SimpleInstance {
        public static final Codec<Condition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("trigger").forGetter(Condition::identifier)
        ).apply(instance, Condition::new));

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
