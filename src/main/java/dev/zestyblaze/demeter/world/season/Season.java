package dev.zestyblaze.demeter.world.season;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum Season implements StringRepresentable {
    SPRING, SUMMER, AUTUMN, WINTER, WET, DRY;

    public static final Codec<Season> CODEC = StringRepresentable.fromEnum(Season::values);

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public static ChatFormatting relatedChatColor(Season season) {
        return switch (season) {
            case SPRING -> ChatFormatting.GREEN;
            case SUMMER -> ChatFormatting.YELLOW;
            case AUTUMN -> ChatFormatting.RED;
            case WINTER -> ChatFormatting.WHITE;
            case WET -> ChatFormatting.BLUE;
            case DRY -> ChatFormatting.GOLD;
        };
    }
}
