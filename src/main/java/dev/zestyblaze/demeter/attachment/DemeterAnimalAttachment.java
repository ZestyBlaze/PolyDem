package dev.zestyblaze.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.util.AnimalSexes;

public class DemeterAnimalAttachment {
    public static final Codec<DemeterAnimalAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            AnimalSexes.CODEC.fieldOf("sex").forGetter(o -> o.sex),
            Codec.INT.fieldOf("love").forGetter(o -> o.love)
    ).apply(func, DemeterAnimalAttachment::new));

    private AnimalSexes sex;
    private int age, love;

    public DemeterAnimalAttachment() {
        this(AnimalSexes.MALE, 0);
    }

    public DemeterAnimalAttachment(AnimalSexes sex, int love) {
        this.sex = sex;
        this.love = love;
    }

    public AnimalSexes getSex() {
        return sex;
    }

    public void setSex(AnimalSexes sex) {
        this.sex = sex;
    }

    public void alterLove(int amount) {
        this.love = Math.clamp(this.love + amount, 0, 100);
    }
}
