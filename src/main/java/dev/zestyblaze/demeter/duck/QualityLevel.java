package dev.zestyblaze.demeter.duck;

import com.mojang.serialization.Codec;
import dev.zestyblaze.demeter.Demeter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.Locale;

public enum QualityLevel {
    COPPER(Style.EMPTY.withColor(0xFF8C00)),
    IRON(Style.EMPTY.withColor(ChatFormatting.GRAY)),
    NETHERITE(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY));

    private final Style style;

    public static final Codec<QualityLevel> CODEC = Demeter.enumCodec(QualityLevel.class);

    QualityLevel(Style style) {
        this.style = style;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Style getStyle() {
        return style;
    }

    public Component getQualityTooltip() {
        return Component.translatable("item.demeter.quality_tooltip." + getName()).withStyle(getStyle());
    }
}
