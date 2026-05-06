package dev.zestyblaze.demeter.managers;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.duck.CropData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class DemeterCropStatsManager extends SimpleJsonResourceReloadListener<CropData> {
    public static final Map<Block, CropData> newMap = new HashMap<>();

    public DemeterCropStatsManager(HolderLookup.Provider registries) {
        super(registries, CropData.CODEC, ResourceKey.createRegistryKey(Demeter.createId("crop_data")));
    }

    @Override
    protected void apply(Map<Identifier, CropData> map, ResourceManager manager, ProfilerFiller profiler) {
        newMap.clear();
        for (Map.Entry<Identifier, CropData> entry : map.entrySet()) {
            if (BuiltInRegistries.BLOCK.containsKey(entry.getKey())) {
                newMap.put(BuiltInRegistries.BLOCK.getValue(entry.getKey()), entry.getValue());
            } else {
                if (!entry.getKey().getNamespace().equals("minecraft") && !entry.getKey().getNamespace().equals("demeter")) {
                    Demeter.LOGGER.warn("Did not register entry \"{}\", as no mod with id \"{}\" is loaded", entry.getKey(), entry.getKey().getNamespace());
                } else {
                    Demeter.LOGGER.warn("\"{}\" was not registered as it is not a path to a valid block", entry.getKey());
                }
            }
        }
    }

    public static CropData getData(Block block) {
        return newMap.get(block);
    }
}
