package dev.zestyblaze.demeter.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum AnimalSexes implements StringRepresentable {
    MALE, FEMALE;

    public static final Codec<AnimalSexes> CODEC = StringRepresentable.fromValues(AnimalSexes::values);

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
