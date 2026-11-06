package dev.zestyblaze.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record AnimalData(int minLifespan, int maxLifespan, int daysPregnant, int daysToGrowUp,
                         int minChildrenPerBirth, int maxChildrenPerBirth) {
    public static final Codec<AnimalData> CODEC = RecordCodecBuilder.create(func -> func.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("minLifespan").forGetter(AnimalData::minLifespan),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxLifespan").forGetter(AnimalData::maxLifespan),
            ExtraCodecs.POSITIVE_INT.fieldOf("daysPregnant").forGetter(AnimalData::daysPregnant),
            ExtraCodecs.POSITIVE_INT.fieldOf("daysToGrowUp").forGetter(AnimalData::daysToGrowUp),
            ExtraCodecs.POSITIVE_INT.fieldOf("minChildrenPerBirth").forGetter(AnimalData::minChildrenPerBirth),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxChildrenPerBirth").forGetter(AnimalData::maxChildrenPerBirth)
    ).apply(func, AnimalData::new));
}