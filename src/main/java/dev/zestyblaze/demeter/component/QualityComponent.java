package dev.zestyblaze.demeter.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.duck.QualityLevel;
import dev.zestyblaze.demeter.registry.DemeterComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record QualityComponent(QualityLevel qualityLevel) implements TooltipProvider {
    public static final Codec<QualityComponent> CODEC = RecordCodecBuilder.create(func -> func.group(
            QualityLevel.CODEC.fieldOf("quality").forGetter(QualityComponent::qualityLevel)
    ).apply(func, QualityComponent::new));

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        QualityLevel component = components.get(DemeterComponents.QUALITY).qualityLevel();
        consumer.accept(Component.translatable("item.demeter.quality_tooltip", component.getQualityTooltip()).withStyle(ChatFormatting.DARK_GRAY));
    }
}
