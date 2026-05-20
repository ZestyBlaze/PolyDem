package dev.zestyblaze.demeter.managers;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.data.AnimalData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class DemeterAnimalStatsManager extends SimpleJsonResourceReloadListener<AnimalData> {
    private static final Map<EntityType<?>, AnimalData> newMap = new HashMap<>();

    public DemeterAnimalStatsManager(HolderLookup.Provider registries) {
        super(registries, AnimalData.CODEC, ResourceKey.createRegistryKey(Demeter.createId("animal_stats")));
    }

    @Override
    protected void apply(Map<Identifier, AnimalData> preparations, ResourceManager manager, ProfilerFiller profiler) {
        newMap.clear();
        for (Map.Entry<Identifier, AnimalData> entry : preparations.entrySet()) {
            if (BuiltInRegistries.ENTITY_TYPE.containsKey(entry.getKey())) {
                newMap.put(BuiltInRegistries.ENTITY_TYPE.getValue(entry.getKey()), entry.getValue());
            }
        }
    }

    public static AnimalData getData(LivingEntity livingEntity) {
        return newMap.get(livingEntity.getType());
    }
}
