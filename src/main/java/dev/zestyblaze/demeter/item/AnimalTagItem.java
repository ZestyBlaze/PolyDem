package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.registry.DemeterItems;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AnimalTagItem extends SimplePolymerItem {
    public AnimalTagItem() {
        super(new Properties().stacksTo(16).setId(DemeterItems.createKey("animal_tag")));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (!pPlayer.level().isClientSide() && pInteractionTarget instanceof Animal animal) {
            if (pPlayer.getName().getString().equals("ElysiaSilly") || pPlayer.getName().getString().equals("blerbedoob")) {
                if (pPlayer.level().getRandom().nextInt(100) >= 15) {
                    animal.setCustomName(Component.literal("Pippi"));
                    return InteractionResult.SUCCESS_SERVER;
                }
            }

            /*
            AnimalSexes sex = AnimalUtil.getSex(animal);
            List<String> possibleNames = NamesLoader.NAME_LIST.get(sex);
            String name = possibleNames.get(pPlayer.level().getRandom().nextInt(possibleNames.size()));
             */

            animal.setCustomName(Component.literal("name"));

            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }
}
