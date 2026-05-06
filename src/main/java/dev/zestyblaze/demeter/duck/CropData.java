package dev.zestyblaze.demeter.duck;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Optional;

public record CropData(int daysToGrow, int maxAge, String ageProperty, Optional<Block> optionalBlockConvert) {
    public static final Codec<CropData> CODEC = RecordCodecBuilder.create(in -> in.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("daysToGrow").forGetter(CropData::daysToGrow),
            ExtraCodecs.POSITIVE_INT.fieldOf("maxAge").forGetter(CropData::maxAge),
            Codec.STRING.fieldOf("ageProperty").forGetter(CropData::ageProperty),
            BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("optionalBlockConvert").forGetter(CropData::optionalBlockConvert)
    ).apply(in, CropData::new));

    public CropData(int daysToGrow, int maxAge) {
        this(daysToGrow, maxAge, "age", Optional.empty());
    }

    public CropData(int daysToGrow, int maxAge, String ageProperty) {
        this(daysToGrow, maxAge, ageProperty, Optional.empty());
    }

    public IntegerProperty resolveProperty(BlockState state) {
        return (IntegerProperty) state.getProperties().stream()
                .filter(p -> p.getName().equals(this.ageProperty))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Missing property: " + ageProperty));
    }
}
