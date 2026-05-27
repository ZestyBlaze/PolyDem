package dev.zestyblaze.demeter.managers;

import dev.zestyblaze.demeter.util.AnimalNames;
import dev.zestyblaze.demeter.util.AnimalSexes;
import dev.zestyblaze.demeter.util.MergeableCodecDataManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;

public class DemeterAnimalNamesManager extends MergeableCodecDataManager<AnimalNames, List<String>> {
    public static final Map<AnimalSexes, List<String>> NAME_LIST = new HashMap<>();

    public DemeterAnimalNamesManager() {
        super("demeter/names", AnimalNames.CODEC, AnimalNames::merge);
    }

    @Override
    protected void apply(Map<Identifier, List<String>> processedData, ResourceManager resourceManager, ProfilerFiller profiler) {
        super.apply(processedData, resourceManager, profiler);

        this.data.forEach((id, string) -> {
            if (Arrays.stream(AnimalSexes.values()).anyMatch(
                    sex -> sex.name().toLowerCase(Locale.ROOT).equals(id.getPath()))) {
                AnimalSexes sex = getSexFromKey(id);
                NAME_LIST.put(sex, string);
            }
        });
    }

    private AnimalSexes getSexFromKey(Identifier key) {
        return AnimalSexes.valueOf(key.getPath().toUpperCase(Locale.ROOT));
    }
}
