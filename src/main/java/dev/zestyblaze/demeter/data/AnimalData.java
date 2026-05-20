package dev.zestyblaze.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record AnimalData(int daysToGrowUp) {
    public static final Codec<AnimalData> CODEC = RecordCodecBuilder.create(func -> func.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("daysToGrowUp").forGetter(AnimalData::daysToGrowUp)
    ).apply(func, AnimalData::new));
}
