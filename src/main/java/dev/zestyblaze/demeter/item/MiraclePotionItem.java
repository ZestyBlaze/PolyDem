package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.advancement.DemeterTriggers;
import dev.zestyblaze.demeter.advancement.TriggerCriterion;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.util.AnimalSexes;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MiraclePotionItem extends Item {
    public MiraclePotionItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE)
                .stacksTo(1).setId(DemeterItems.createKey("miracle_potion")));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!player.level().isClientSide() && interactionTarget instanceof Animal animal) {
            if (player instanceof ServerPlayer serverPlayer && player.level() instanceof ServerLevel serverLevel) {
                if (interactionTarget instanceof Frog frog) {
                    if (animal.getAttachedOrCreate(DemeterAttachments.ANIMAL_DATA).getSex().equals(AnimalSexes.FEMALE)) {
                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }
                        serverLevel.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
                        serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BOTTLE_EMPTY), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                        TriggerCriterion.trigger(serverPlayer, DemeterTriggers.MIRACLE_USED_ON_FROG);
                        frog.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
                    }
                    return InteractionResult.SUCCESS;
                }

                if (animal.hasAttached(DemeterAttachments.ANIMAL_DATA) &&
                        animal.getAttachedOrCreate(DemeterAttachments.ANIMAL_DATA).getSex().equals(AnimalSexes.FEMALE)) {
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    serverLevel.sendParticles(ParticleTypes.HEART, animal.getX(), animal.getY() + 0.7, animal.getZ(), 4, 0.5, 0, 0.5, animal.getRandom().nextGaussian() * 0.02);
                    serverPlayer.connection.send(new ClientboundSoundPacket(Holder.direct(SoundEvents.BOTTLE_EMPTY), SoundSource.PLAYERS, animal.getX(), animal.getY(), animal.getZ(), 1.0f, 1.0f, 0));
                    TriggerCriterion.trigger(serverPlayer, DemeterTriggers.MIRACLE_POTION_USED);
                    //AnimalUtil.getAnimalData(animal).setPregnant(animal, true, animal);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
