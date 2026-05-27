package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.consumable.ClearRandomNegativeEffect;
import dev.zestyblaze.demeter.registry.DemeterItems;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MilkBottleItem extends SimplePolymerItem {
    public static final Consumable MILK_BOTTLE = Consumable.builder().consumeSeconds(1.6F)
            .animation(ItemUseAnimation.DRINK).sound(SoundEvents.GENERIC_DRINK)
            .hasConsumeParticles(false).onConsume(ClearRandomNegativeEffect.INSTANCE).build();

    public MilkBottleItem() {
        super(new Properties().craftRemainder(Items.GLASS_BOTTLE)
                .component(DataComponents.CONSUMABLE, MILK_BOTTLE)
                .usingConvertsTo(Items.GLASS_BOTTLE).stacksTo(16).setId(DemeterItems.createKey("milk_bottle")));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable("item.demeter.milk_bottle.desc").withStyle(ChatFormatting.DARK_GRAY));
    }
}
