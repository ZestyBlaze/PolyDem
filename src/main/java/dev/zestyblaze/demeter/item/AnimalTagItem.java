package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.managers.DemeterAnimalNamesManager;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.util.AnimalSexes;
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
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand usedHand) {
        if (!player.level().isClientSide() && livingEntity instanceof Animal animal) {
            if (player.getName().getString().equals("ElysiaSilly") || player.getName().getString().equals("blerbedoob")) {
                if (player.level().getRandom().nextInt(100) >= 15) {
                    animal.setCustomName(Component.literal("Pippi"));
                    return InteractionResult.SUCCESS_SERVER;
                }
            }

            AnimalSexes sex = animal.getAttachedOrCreate(DemeterAttachments.ANIMAL_DATA).getSex();
            List<String> possibleNames = DemeterAnimalNamesManager.NAME_LIST.get(sex);
            String name = possibleNames.get(player.level().getRandom().nextInt(possibleNames.size()));

            animal.setCustomName(Component.literal(name));

            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }
}
