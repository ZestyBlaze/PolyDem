package dev.zestyblaze.demeter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.zestyblaze.demeter.world.season.Season;

import java.util.List;

public record CropItemSeason(List<Season> validSeasons) {
    public static final Codec<CropItemSeason> CODEC = RecordCodecBuilder.create(func -> func.group(
            Season.CODEC.listOf().fieldOf("valid_seasons").forGetter(CropItemSeason::validSeasons)
    ).apply(func, CropItemSeason::new));
}
