package dev.zestyblaze.demeter.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.world.season.Season;

public class SeasonAttachment {
    private Season season;

    public static Codec<SeasonAttachment> CODEC = RecordCodecBuilder.create(func -> func.group(
            Season.CODEC.fieldOf("season").forGetter(s -> s.season)
    ).apply(func, SeasonAttachment::new));

    public SeasonAttachment(Season season) {
        this.season = season;
    }

    public SeasonAttachment() {
        this(Season.SPRING);
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
