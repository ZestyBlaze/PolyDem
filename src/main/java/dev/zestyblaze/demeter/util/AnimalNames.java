package dev.zestyblaze.demeter.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record AnimalNames(boolean replace, List<String> names) {
    public static final Codec<AnimalNames> CODEC = RecordCodecBuilder.create(func -> func.group(
            Codec.BOOL.optionalFieldOf("replace", false).forGetter(AnimalNames::replace),
            Codec.STRING.listOf().fieldOf("names").forGetter(AnimalNames::names)
    ).apply(func, AnimalNames::new));

    public static List<String> merge(List<AnimalNames> raws) {
        Set<String> set = new HashSet<>();
        for (AnimalNames raw : raws) {
            if (raw.replace) {
                set = new HashSet<>();
            }
            set.addAll(raw.names());
        }

        List<String> out = new ArrayList<>(set.size());
        out.addAll(set);
        return out;
    }
}
