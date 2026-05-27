package dev.zestyblaze.demeter.consumable;

import com.mojang.serialization.MapCodec;
import dev.zestyblaze.demeter.registry.DemeterConsumables;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ClearRandomNegativeEffect implements ConsumeEffect {
    public static final ClearRandomNegativeEffect INSTANCE = new ClearRandomNegativeEffect();
    public static final MapCodec<ClearRandomNegativeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ClearRandomNegativeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return DemeterConsumables.CLEAR_RANDOM_NEGATIVE_EFFECT;
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        List<Holder<MobEffect>> negativeEffects = new ArrayList<>();
        livingEntity.getActiveEffects().forEach(mobEffectInstance -> {
            if (mobEffectInstance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL)) {
                negativeEffects.add(mobEffectInstance.getEffect());
            }
        });
        if (!negativeEffects.isEmpty()) {
            Holder<MobEffect> mobEffect = negativeEffects.get(level.getRandom().nextInt(negativeEffects.size()));
            livingEntity.removeEffect(mobEffect);
            negativeEffects.clear();
            return true;
        }
        return false;
    }
}
