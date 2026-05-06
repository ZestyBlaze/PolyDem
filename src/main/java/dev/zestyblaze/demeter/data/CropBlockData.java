package dev.zestyblaze.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CropBlockData {
    public int age;
    public boolean frozen;

    public static final Codec<CropBlockData> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.INT.fieldOf("age").forGetter(c -> c.age),
            Codec.BOOL.fieldOf("frozen").forGetter(c -> c.frozen)
    ).apply(func, CropBlockData::new));

    public CropBlockData() {
        this(0, false);
    }

    public CropBlockData(int age) {
        this(age, false);
    }

    public CropBlockData(int age, boolean frozen) {
        this.age = age;
        this.frozen = frozen;
    }
}
