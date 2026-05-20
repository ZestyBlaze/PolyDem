package dev.zestyblaze.demeter.event;

import dev.zestyblaze.demeter.attachment.DemeterAnimalAttachment;
import dev.zestyblaze.demeter.data.AnimalData;
import dev.zestyblaze.demeter.managers.DemeterAnimalStatsManager;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.util.AnimalSexes;
import dev.zestyblaze.demeter.util.RemoveableElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import eu.pb4.polymer.virtualentity.api.elements.TextDisplayElement;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EntityEvents {
    public static void init() {
        onEntityAdded();
        onEntityInteract();
    }

    private static void onEntityAdded() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (entity instanceof LivingEntity livingEntity) {
                if (DemeterAnimalStatsManager.getData(livingEntity) == null) return;

                if (!entity.isLoadedFromDisk()) {
                    DemeterAnimalAttachment data = livingEntity.getAttachedOrCreate(DemeterAttachments.ANIMAL_DATA);
                    AnimalSexes sex = AnimalSexes.values()[level.getRandom().nextInt(AnimalSexes.values().length)];

                    data.setSex(sex);
                }
            }
        });
    }

    private static void onEntityInteract() {
        UseEntityCallback.EVENT.register((player, level, _, entity, _) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.isCrouching()) {
                    if (player.getItemInHand(player.getUsedItemHand()).isEmpty()) {
                        TextDisplayElement element = new TextDisplayElement(Component.translatable("message.demeter.pet_animal",
                                        BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath()).append(" ")
                                .append(Component.literal("❤").withColor(0xF7B3CD))
                        );
                        element.setOffset(new Vec3(0, entity.getBbHeight() + 0.25, 0));
                        element.setSeeThrough(true);
                        element.setBillboardMode(Display.BillboardConstraints.CENTER);
                        RemoveableElementHolder holder = new RemoveableElementHolder(40, element);
                        holder.addElement(element);

                        ClientboundAnimatePacket packet = new ClientboundAnimatePacket(serverPlayer, 0);
                        ServerLevel serverLevel = (ServerLevel)level;
                        ServerChunkCache chunkSource = serverLevel.getChunkSource();
                        chunkSource.sendToTrackingPlayersAndSelf(player, packet);

                        chunkSource.sendToTrackingPlayersAndSelf(player, new ClientboundLevelParticlesPacket(ParticleTypes.HEART,
                                true, true, entity.getX(), entity.getY(), entity.getZ(),
                                0.5f, 0.5f, 0.5f, 1, 5));

                        EntityAttachment.ofTicking(holder, entity);
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }
}
