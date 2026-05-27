package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.consumable.ClearRandomNegativeEffect;
import eu.pb4.polymer.core.api.other.PolymerConsumeEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public class DemeterConsumables {
    public static final ConsumeEffect.Type<ClearRandomNegativeEffect> CLEAR_RANDOM_NEGATIVE_EFFECT =
            new ConsumeEffect.Type<>(ClearRandomNegativeEffect.CODEC, ClearRandomNegativeEffect.STREAM_CODEC);

    public static void init() {
        Registry.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, Demeter.createId("clear_random_negative_effect"), CLEAR_RANDOM_NEGATIVE_EFFECT);
        PolymerConsumeEffect.registerConsumeEffect(CLEAR_RANDOM_NEGATIVE_EFFECT);
    }
}
